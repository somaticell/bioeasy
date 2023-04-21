package rx.internal.operators;

import com.facebook.common.time.Clock;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import rx.Notification;
import rx.Observable;
import rx.Producer;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.internal.producers.ProducerArbiter;
import rx.observers.Subscribers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.SerialSubscription;

public final class OnSubscribeRedo<T> implements Observable.OnSubscribe<T> {
    static final Func1<Observable<? extends Notification<?>>, Observable<?>> REDO_INFINITE = new Func1<Observable<? extends Notification<?>>, Observable<?>>() {
        public Observable<?> call(Observable<? extends Notification<?>> ts) {
            return ts.map(new Func1<Notification<?>, Notification<?>>() {
                public Notification<?> call(Notification<?> notification) {
                    return Notification.createOnNext(null);
                }
            });
        }
    };
    private final Func1<? super Observable<? extends Notification<?>>, ? extends Observable<?>> controlHandlerFunction;
    private final Scheduler scheduler;
    final Observable<T> source;
    final boolean stopOnComplete;
    final boolean stopOnError;

    public static final class RedoFinite implements Func1<Observable<? extends Notification<?>>, Observable<?>> {
        final long count;

        public RedoFinite(long count2) {
            this.count = count2;
        }

        public Observable<?> call(Observable<? extends Notification<?>> ts) {
            return ts.map(new Func1<Notification<?>, Notification<?>>() {
                int num = 0;

                public Notification<?> call(Notification<?> terminalNotification) {
                    if (RedoFinite.this.count == 0) {
                        return terminalNotification;
                    }
                    this.num++;
                    if (((long) this.num) <= RedoFinite.this.count) {
                        return Notification.createOnNext(Integer.valueOf(this.num));
                    }
                    return terminalNotification;
                }
            }).dematerialize();
        }
    }

    public static final class RetryWithPredicate implements Func1<Observable<? extends Notification<?>>, Observable<? extends Notification<?>>> {
        final Func2<Integer, Throwable, Boolean> predicate;

        public RetryWithPredicate(Func2<Integer, Throwable, Boolean> predicate2) {
            this.predicate = predicate2;
        }

        public Observable<? extends Notification<?>> call(Observable<? extends Notification<?>> ts) {
            return ts.scan(Notification.createOnNext(0), new Func2<Notification<Integer>, Notification<?>, Notification<Integer>>() {
                public Notification<Integer> call(Notification<Integer> n, Notification<?> term) {
                    int value = n.getValue().intValue();
                    if (RetryWithPredicate.this.predicate.call(Integer.valueOf(value), term.getThrowable()).booleanValue()) {
                        return Notification.createOnNext(Integer.valueOf(value + 1));
                    }
                    return term;
                }
            });
        }
    }

    public static <T> Observable<T> retry(Observable<T> source2) {
        return retry(source2, (Func1<? super Observable<? extends Notification<?>>, ? extends Observable<?>>) REDO_INFINITE);
    }

    public static <T> Observable<T> retry(Observable<T> source2, long count) {
        if (count >= 0) {
            return count == 0 ? source2 : retry(source2, (Func1<? super Observable<? extends Notification<?>>, ? extends Observable<?>>) new RedoFinite(count));
        }
        throw new IllegalArgumentException("count >= 0 expected");
    }

    public static <T> Observable<T> retry(Observable<T> source2, Func1<? super Observable<? extends Notification<?>>, ? extends Observable<?>> notificationHandler) {
        return Observable.create(new OnSubscribeRedo(source2, notificationHandler, true, false, Schedulers.trampoline()));
    }

    public static <T> Observable<T> retry(Observable<T> source2, Func1<? super Observable<? extends Notification<?>>, ? extends Observable<?>> notificationHandler, Scheduler scheduler2) {
        return Observable.create(new OnSubscribeRedo(source2, notificationHandler, true, false, scheduler2));
    }

    public static <T> Observable<T> repeat(Observable<T> source2) {
        return repeat(source2, Schedulers.trampoline());
    }

    public static <T> Observable<T> repeat(Observable<T> source2, Scheduler scheduler2) {
        return repeat(source2, (Func1<? super Observable<? extends Notification<?>>, ? extends Observable<?>>) REDO_INFINITE, scheduler2);
    }

    public static <T> Observable<T> repeat(Observable<T> source2, long count) {
        return repeat(source2, count, Schedulers.trampoline());
    }

    public static <T> Observable<T> repeat(Observable<T> source2, long count, Scheduler scheduler2) {
        if (count == 0) {
            return Observable.empty();
        }
        if (count >= 0) {
            return repeat(source2, (Func1<? super Observable<? extends Notification<?>>, ? extends Observable<?>>) new RedoFinite(count - 1), scheduler2);
        }
        throw new IllegalArgumentException("count >= 0 expected");
    }

    public static <T> Observable<T> repeat(Observable<T> source2, Func1<? super Observable<? extends Notification<?>>, ? extends Observable<?>> notificationHandler) {
        return Observable.create(new OnSubscribeRedo(source2, notificationHandler, false, true, Schedulers.trampoline()));
    }

    public static <T> Observable<T> repeat(Observable<T> source2, Func1<? super Observable<? extends Notification<?>>, ? extends Observable<?>> notificationHandler, Scheduler scheduler2) {
        return Observable.create(new OnSubscribeRedo(source2, notificationHandler, false, true, scheduler2));
    }

    public static <T> Observable<T> redo(Observable<T> source2, Func1<? super Observable<? extends Notification<?>>, ? extends Observable<?>> notificationHandler, Scheduler scheduler2) {
        return Observable.create(new OnSubscribeRedo(source2, notificationHandler, false, false, scheduler2));
    }

    private OnSubscribeRedo(Observable<T> source2, Func1<? super Observable<? extends Notification<?>>, ? extends Observable<?>> f, boolean stopOnComplete2, boolean stopOnError2, Scheduler scheduler2) {
        this.source = source2;
        this.controlHandlerFunction = f;
        this.stopOnComplete = stopOnComplete2;
        this.stopOnError = stopOnError2;
        this.scheduler = scheduler2;
    }

    public void call(Subscriber<? super T> child) {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        final AtomicLong consumerCapacity = new AtomicLong();
        final Scheduler.Worker worker = this.scheduler.createWorker();
        child.add(worker);
        final SerialSubscription sourceSubscriptions = new SerialSubscription();
        child.add(sourceSubscriptions);
        final BehaviorSubject<Notification<?>> terminals = BehaviorSubject.create();
        terminals.subscribe((Subscriber<? super Notification<?>>) Subscribers.empty());
        final ProducerArbiter arbiter = new ProducerArbiter();
        final Subscriber<? super T> subscriber = child;
        Action0 subscribeToSource = new Action0() {
            public void call() {
                if (!subscriber.isUnsubscribed()) {
                    Subscriber<T> terminalDelegatingSubscriber = new Subscriber<T>() {
                        boolean done;

                        public void onCompleted() {
                            if (!this.done) {
                                this.done = true;
                                unsubscribe();
                                terminals.onNext(Notification.createOnCompleted());
                            }
                        }

                        public void onError(Throwable e) {
                            if (!this.done) {
                                this.done = true;
                                unsubscribe();
                                terminals.onNext(Notification.createOnError(e));
                            }
                        }

                        public void onNext(T v) {
                            if (!this.done) {
                                subscriber.onNext(v);
                                decrementConsumerCapacity();
                                arbiter.produced(1);
                            }
                        }

                        /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START, MTH_ENTER_BLOCK] */
                        /* Code decompiled incorrectly, please refer to instructions dump. */
                        private void decrementConsumerCapacity() {
                            /*
                                r6 = this;
                            L_0x0000:
                                rx.internal.operators.OnSubscribeRedo$2 r2 = rx.internal.operators.OnSubscribeRedo.AnonymousClass2.this
                                java.util.concurrent.atomic.AtomicLong r2 = r7
                                long r0 = r2.get()
                                r2 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                                int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                                if (r2 == 0) goto L_0x001f
                                rx.internal.operators.OnSubscribeRedo$2 r2 = rx.internal.operators.OnSubscribeRedo.AnonymousClass2.this
                                java.util.concurrent.atomic.AtomicLong r2 = r7
                                r4 = 1
                                long r4 = r0 - r4
                                boolean r2 = r2.compareAndSet(r0, r4)
                                if (r2 == 0) goto L_0x0000
                            L_0x001f:
                                return
                            */
                            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OnSubscribeRedo.AnonymousClass2.AnonymousClass1.decrementConsumerCapacity():void");
                        }

                        public void setProducer(Producer producer) {
                            arbiter.setProducer(producer);
                        }
                    };
                    sourceSubscriptions.set(terminalDelegatingSubscriber);
                    OnSubscribeRedo.this.source.unsafeSubscribe(terminalDelegatingSubscriber);
                }
            }
        };
        final Observable<?> restarts = (Observable) this.controlHandlerFunction.call(terminals.lift(new Observable.Operator<Notification<?>, Notification<?>>() {
            public Subscriber<? super Notification<?>> call(final Subscriber<? super Notification<?>> filteredTerminals) {
                return new Subscriber<Notification<?>>(filteredTerminals) {
                    public void onCompleted() {
                        filteredTerminals.onCompleted();
                    }

                    public void onError(Throwable e) {
                        filteredTerminals.onError(e);
                    }

                    public void onNext(Notification<?> t) {
                        if (t.isOnCompleted() && OnSubscribeRedo.this.stopOnComplete) {
                            filteredTerminals.onCompleted();
                        } else if (!t.isOnError() || !OnSubscribeRedo.this.stopOnError) {
                            filteredTerminals.onNext(t);
                        } else {
                            filteredTerminals.onError(t.getThrowable());
                        }
                    }

                    public void setProducer(Producer producer) {
                        producer.request(Clock.MAX_TIME);
                    }
                };
            }
        }));
        final Subscriber<? super T> subscriber2 = child;
        final AtomicLong atomicLong = consumerCapacity;
        final Action0 action0 = subscribeToSource;
        worker.schedule(new Action0() {
            public void call() {
                restarts.unsafeSubscribe(new Subscriber<Object>(subscriber2) {
                    public void onCompleted() {
                        subscriber2.onCompleted();
                    }

                    public void onError(Throwable e) {
                        subscriber2.onError(e);
                    }

                    public void onNext(Object t) {
                        if (subscriber2.isUnsubscribed()) {
                            return;
                        }
                        if (atomicLong.get() > 0) {
                            worker.schedule(action0);
                        } else {
                            atomicBoolean.compareAndSet(false, true);
                        }
                    }

                    public void setProducer(Producer producer) {
                        producer.request(Clock.MAX_TIME);
                    }
                });
            }
        });
        final AtomicLong atomicLong2 = consumerCapacity;
        final ProducerArbiter producerArbiter = arbiter;
        final AtomicBoolean atomicBoolean2 = atomicBoolean;
        final Scheduler.Worker worker2 = worker;
        final Action0 action02 = subscribeToSource;
        child.setProducer(new Producer() {
            public void request(long n) {
                if (n > 0) {
                    BackpressureUtils.getAndAddRequest(atomicLong2, n);
                    producerArbiter.request(n);
                    if (atomicBoolean2.compareAndSet(true, false)) {
                        worker2.schedule(action02);
                    }
                }
            }
        });
    }
}

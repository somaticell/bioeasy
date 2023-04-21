package rx.observables;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import rx.Observable;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.annotations.Experimental;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Action3;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func3;
import rx.internal.operators.BufferUntilSubscriber;
import rx.observers.SerializedObserver;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.CompositeSubscription;

@Experimental
public abstract class AsyncOnSubscribe<S, T> implements Observable.OnSubscribe<T> {
    /* access modifiers changed from: protected */
    public abstract S generateState();

    /* access modifiers changed from: protected */
    public abstract S next(S s, long j, Observer<Observable<? extends T>> observer);

    /* access modifiers changed from: protected */
    public void onUnsubscribe(S s) {
    }

    @Experimental
    public static <S, T> AsyncOnSubscribe<S, T> createSingleState(Func0<? extends S> generator, final Action3<? super S, Long, ? super Observer<Observable<? extends T>>> next) {
        return new AsyncOnSubscribeImpl(generator, new Func3<S, Long, Observer<Observable<? extends T>>, S>() {
            public S call(S state, Long requested, Observer<Observable<? extends T>> subscriber) {
                next.call(state, requested, subscriber);
                return state;
            }
        });
    }

    @Experimental
    public static <S, T> AsyncOnSubscribe<S, T> createSingleState(Func0<? extends S> generator, final Action3<? super S, Long, ? super Observer<Observable<? extends T>>> next, Action1<? super S> onUnsubscribe) {
        return new AsyncOnSubscribeImpl(generator, new Func3<S, Long, Observer<Observable<? extends T>>, S>() {
            public S call(S state, Long requested, Observer<Observable<? extends T>> subscriber) {
                next.call(state, requested, subscriber);
                return state;
            }
        }, onUnsubscribe);
    }

    @Experimental
    public static <S, T> AsyncOnSubscribe<S, T> createStateful(Func0<? extends S> generator, Func3<? super S, Long, ? super Observer<Observable<? extends T>>, ? extends S> next, Action1<? super S> onUnsubscribe) {
        return new AsyncOnSubscribeImpl(generator, next, onUnsubscribe);
    }

    @Experimental
    public static <S, T> AsyncOnSubscribe<S, T> createStateful(Func0<? extends S> generator, Func3<? super S, Long, ? super Observer<Observable<? extends T>>, ? extends S> next) {
        return new AsyncOnSubscribeImpl(generator, next);
    }

    @Experimental
    public static <T> AsyncOnSubscribe<Void, T> createStateless(final Action2<Long, ? super Observer<Observable<? extends T>>> next) {
        return new AsyncOnSubscribeImpl(new Func3<Void, Long, Observer<Observable<? extends T>>, Void>() {
            public Void call(Void state, Long requested, Observer<Observable<? extends T>> subscriber) {
                next.call(requested, subscriber);
                return state;
            }
        });
    }

    @Experimental
    public static <T> AsyncOnSubscribe<Void, T> createStateless(final Action2<Long, ? super Observer<Observable<? extends T>>> next, final Action0 onUnsubscribe) {
        return new AsyncOnSubscribeImpl(new Func3<Void, Long, Observer<Observable<? extends T>>, Void>() {
            public Void call(Void state, Long requested, Observer<Observable<? extends T>> subscriber) {
                next.call(requested, subscriber);
                return null;
            }
        }, new Action1<Void>() {
            public void call(Void t) {
                onUnsubscribe.call();
            }
        });
    }

    private static final class AsyncOnSubscribeImpl<S, T> extends AsyncOnSubscribe<S, T> {
        private final Func0<? extends S> generator;
        private final Func3<? super S, Long, ? super Observer<Observable<? extends T>>, ? extends S> next;
        private final Action1<? super S> onUnsubscribe;

        public /* bridge */ /* synthetic */ void call(Object x0) {
            AsyncOnSubscribe.super.call((Subscriber) x0);
        }

        AsyncOnSubscribeImpl(Func0<? extends S> generator2, Func3<? super S, Long, ? super Observer<Observable<? extends T>>, ? extends S> next2, Action1<? super S> onUnsubscribe2) {
            this.generator = generator2;
            this.next = next2;
            this.onUnsubscribe = onUnsubscribe2;
        }

        public AsyncOnSubscribeImpl(Func0<? extends S> generator2, Func3<? super S, Long, ? super Observer<Observable<? extends T>>, ? extends S> next2) {
            this(generator2, next2, (Action1) null);
        }

        public AsyncOnSubscribeImpl(Func3<S, Long, Observer<Observable<? extends T>>, S> next2, Action1<? super S> onUnsubscribe2) {
            this((Func0) null, next2, onUnsubscribe2);
        }

        public AsyncOnSubscribeImpl(Func3<S, Long, Observer<Observable<? extends T>>, S> nextFunc) {
            this((Func0) null, nextFunc, (Action1) null);
        }

        /* access modifiers changed from: protected */
        public S generateState() {
            if (this.generator == null) {
                return null;
            }
            return this.generator.call();
        }

        /* access modifiers changed from: protected */
        public S next(S state, long requested, Observer<Observable<? extends T>> observer) {
            return this.next.call(state, Long.valueOf(requested), observer);
        }

        /* access modifiers changed from: protected */
        public void onUnsubscribe(S state) {
            if (this.onUnsubscribe != null) {
                this.onUnsubscribe.call(state);
            }
        }
    }

    public final void call(final Subscriber<? super T> actualSubscriber) {
        try {
            S state = generateState();
            UnicastSubject<Observable<T>> subject = UnicastSubject.create();
            final AsyncOuterManager<S, T> outerProducer = new AsyncOuterManager<>(this, state, subject);
            Subscriber<T> concatSubscriber = new Subscriber<T>() {
                public void onNext(T t) {
                    actualSubscriber.onNext(t);
                }

                public void onError(Throwable e) {
                    actualSubscriber.onError(e);
                }

                public void onCompleted() {
                    actualSubscriber.onCompleted();
                }

                public void setProducer(Producer p) {
                    outerProducer.setConcatProducer(p);
                }
            };
            subject.onBackpressureBuffer().concatMap(new Func1<Observable<T>, Observable<T>>() {
                public Observable<T> call(Observable<T> v) {
                    return v.onBackpressureBuffer();
                }
            }).unsafeSubscribe(concatSubscriber);
            actualSubscriber.add(concatSubscriber);
            actualSubscriber.add(outerProducer);
            actualSubscriber.setProducer(outerProducer);
        } catch (Throwable ex) {
            actualSubscriber.onError(ex);
        }
    }

    static final class AsyncOuterManager<S, T> implements Producer, Subscription, Observer<Observable<? extends T>> {
        private static final AtomicIntegerFieldUpdater<AsyncOuterManager> IS_UNSUBSCRIBED = AtomicIntegerFieldUpdater.newUpdater(AsyncOuterManager.class, "isUnsubscribed");
        Producer concatProducer;
        boolean emitting;
        long expectedDelivery;
        private boolean hasTerminated;
        private volatile int isUnsubscribed;
        private final UnicastSubject<Observable<T>> merger;
        private boolean onNextCalled;
        private final AsyncOnSubscribe<S, T> parent;
        List<Long> requests;
        private final SerializedObserver<Observable<? extends T>> serializedSubscriber;
        private S state;
        final CompositeSubscription subscriptions = new CompositeSubscription();

        public AsyncOuterManager(AsyncOnSubscribe<S, T> parent2, S initialState, UnicastSubject<Observable<T>> merger2) {
            this.parent = parent2;
            this.serializedSubscriber = new SerializedObserver<>(this);
            this.state = initialState;
            this.merger = merger2;
        }

        public void unsubscribe() {
            if (IS_UNSUBSCRIBED.compareAndSet(this, 0, 1)) {
                synchronized (this) {
                    if (this.emitting) {
                        this.requests = new ArrayList();
                        this.requests.add(0L);
                        return;
                    }
                    this.emitting = true;
                    cleanup();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void setConcatProducer(Producer p) {
            if (this.concatProducer != null) {
                throw new IllegalStateException("setConcatProducer may be called at most once!");
            }
            this.concatProducer = p;
        }

        public boolean isUnsubscribed() {
            return this.isUnsubscribed != 0;
        }

        public void nextIteration(long requestCount) {
            this.state = this.parent.next(this.state, requestCount, this.serializedSubscriber);
        }

        /* access modifiers changed from: package-private */
        public void cleanup() {
            this.subscriptions.unsubscribe();
            try {
                this.parent.onUnsubscribe(this.state);
            } catch (Throwable ex) {
                handleThrownError(ex);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:40:0x0063, code lost:
            r0 = r1.iterator();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x006b, code lost:
            if (r0.hasNext() == false) goto L_0x004b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x007b, code lost:
            if (tryEmit(r0.next().longValue()) == false) goto L_0x0067;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void request(long r10) {
            /*
                r9 = this;
                r6 = 0
                int r3 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r3 != 0) goto L_0x0007
            L_0x0006:
                return
            L_0x0007:
                int r3 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r3 >= 0) goto L_0x0024
                java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "Request can't be negative! "
                java.lang.StringBuilder r6 = r6.append(r7)
                java.lang.StringBuilder r6 = r6.append(r10)
                java.lang.String r6 = r6.toString()
                r3.<init>(r6)
                throw r3
            L_0x0024:
                r2 = 0
                monitor-enter(r9)
                boolean r3 = r9.emitting     // Catch:{ all -> 0x005c }
                if (r3 == 0) goto L_0x0058
                java.util.List<java.lang.Long> r1 = r9.requests     // Catch:{ all -> 0x005c }
                if (r1 != 0) goto L_0x0035
                java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x005c }
                r1.<init>()     // Catch:{ all -> 0x005c }
                r9.requests = r1     // Catch:{ all -> 0x005c }
            L_0x0035:
                java.lang.Long r3 = java.lang.Long.valueOf(r10)     // Catch:{ all -> 0x005c }
                r1.add(r3)     // Catch:{ all -> 0x005c }
                r2 = 1
            L_0x003d:
                monitor-exit(r9)     // Catch:{ all -> 0x005c }
                rx.Producer r3 = r9.concatProducer
                r3.request(r10)
                if (r2 != 0) goto L_0x0006
                boolean r3 = r9.tryEmit(r10)
                if (r3 != 0) goto L_0x0006
            L_0x004b:
                monitor-enter(r9)
                java.util.List<java.lang.Long> r1 = r9.requests     // Catch:{ all -> 0x0055 }
                if (r1 != 0) goto L_0x005f
                r3 = 0
                r9.emitting = r3     // Catch:{ all -> 0x0055 }
                monitor-exit(r9)     // Catch:{ all -> 0x0055 }
                goto L_0x0006
            L_0x0055:
                r3 = move-exception
                monitor-exit(r9)     // Catch:{ all -> 0x0055 }
                throw r3
            L_0x0058:
                r3 = 1
                r9.emitting = r3     // Catch:{ all -> 0x005c }
                goto L_0x003d
            L_0x005c:
                r3 = move-exception
                monitor-exit(r9)     // Catch:{ all -> 0x005c }
                throw r3
            L_0x005f:
                r3 = 0
                r9.requests = r3     // Catch:{ all -> 0x0055 }
                monitor-exit(r9)     // Catch:{ all -> 0x0055 }
                java.util.Iterator r0 = r1.iterator()
            L_0x0067:
                boolean r3 = r0.hasNext()
                if (r3 == 0) goto L_0x004b
                java.lang.Object r3 = r0.next()
                java.lang.Long r3 = (java.lang.Long) r3
                long r4 = r3.longValue()
                boolean r3 = r9.tryEmit(r4)
                if (r3 == 0) goto L_0x0067
                goto L_0x0006
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.observables.AsyncOnSubscribe.AsyncOuterManager.request(long):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0048, code lost:
            if (tryEmit(r10) != false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x004a, code lost:
            monitor-enter(r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
            r1 = r9.requests;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x004d, code lost:
            if (r1 != null) goto L_0x0057;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x004f, code lost:
            r9.emitting = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0052, code lost:
            monitor-exit(r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
            r9.requests = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x005a, code lost:
            monitor-exit(r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x005b, code lost:
            r0 = r1.iterator();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x0063, code lost:
            if (r0.hasNext() == false) goto L_0x004a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x0073, code lost:
            if (tryEmit(r0.next().longValue()) == false) goto L_0x005f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void requestRemaining(long r10) {
            /*
                r9 = this;
                r6 = 0
                int r4 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r4 != 0) goto L_0x0007
            L_0x0006:
                return
            L_0x0007:
                int r4 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r4 >= 0) goto L_0x0024
                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "Request can't be negative! "
                java.lang.StringBuilder r5 = r5.append(r6)
                java.lang.StringBuilder r5 = r5.append(r10)
                java.lang.String r5 = r5.toString()
                r4.<init>(r5)
                throw r4
            L_0x0024:
                monitor-enter(r9)
                boolean r4 = r9.emitting     // Catch:{ all -> 0x003d }
                if (r4 == 0) goto L_0x0040
                java.util.List<java.lang.Long> r1 = r9.requests     // Catch:{ all -> 0x003d }
                if (r1 != 0) goto L_0x0034
                java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x003d }
                r1.<init>()     // Catch:{ all -> 0x003d }
                r9.requests = r1     // Catch:{ all -> 0x003d }
            L_0x0034:
                java.lang.Long r4 = java.lang.Long.valueOf(r10)     // Catch:{ all -> 0x003d }
                r1.add(r4)     // Catch:{ all -> 0x003d }
                monitor-exit(r9)     // Catch:{ all -> 0x003d }
                goto L_0x0006
            L_0x003d:
                r4 = move-exception
                monitor-exit(r9)     // Catch:{ all -> 0x003d }
                throw r4
            L_0x0040:
                r4 = 1
                r9.emitting = r4     // Catch:{ all -> 0x003d }
                monitor-exit(r9)     // Catch:{ all -> 0x003d }
                boolean r4 = r9.tryEmit(r10)
                if (r4 != 0) goto L_0x0006
            L_0x004a:
                monitor-enter(r9)
                java.util.List<java.lang.Long> r1 = r9.requests     // Catch:{ all -> 0x0054 }
                if (r1 != 0) goto L_0x0057
                r4 = 0
                r9.emitting = r4     // Catch:{ all -> 0x0054 }
                monitor-exit(r9)     // Catch:{ all -> 0x0054 }
                goto L_0x0006
            L_0x0054:
                r4 = move-exception
                monitor-exit(r9)     // Catch:{ all -> 0x0054 }
                throw r4
            L_0x0057:
                r4 = 0
                r9.requests = r4     // Catch:{ all -> 0x0054 }
                monitor-exit(r9)     // Catch:{ all -> 0x0054 }
                java.util.Iterator r0 = r1.iterator()
            L_0x005f:
                boolean r4 = r0.hasNext()
                if (r4 == 0) goto L_0x004a
                java.lang.Object r4 = r0.next()
                java.lang.Long r4 = (java.lang.Long) r4
                long r2 = r4.longValue()
                boolean r4 = r9.tryEmit(r2)
                if (r4 == 0) goto L_0x005f
                goto L_0x0006
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.observables.AsyncOnSubscribe.AsyncOuterManager.requestRemaining(long):void");
        }

        /* access modifiers changed from: package-private */
        public boolean tryEmit(long n) {
            if (isUnsubscribed()) {
                cleanup();
                return true;
            }
            try {
                this.onNextCalled = false;
                this.expectedDelivery = n;
                nextIteration(n);
                if (this.hasTerminated || isUnsubscribed()) {
                    cleanup();
                    return true;
                } else if (this.onNextCalled) {
                    return false;
                } else {
                    handleThrownError(new IllegalStateException("No events emitted!"));
                    return true;
                }
            } catch (Throwable ex) {
                handleThrownError(ex);
                return true;
            }
        }

        private void handleThrownError(Throwable ex) {
            if (this.hasTerminated) {
                RxJavaPlugins.getInstance().getErrorHandler().handleError(ex);
                return;
            }
            this.hasTerminated = true;
            this.merger.onError(ex);
            cleanup();
        }

        public void onCompleted() {
            if (this.hasTerminated) {
                throw new IllegalStateException("Terminal event already emitted.");
            }
            this.hasTerminated = true;
            this.merger.onCompleted();
        }

        public void onError(Throwable e) {
            if (this.hasTerminated) {
                throw new IllegalStateException("Terminal event already emitted.");
            }
            this.hasTerminated = true;
            this.merger.onError(e);
        }

        public void onNext(Observable<? extends T> t) {
            if (this.onNextCalled) {
                throw new IllegalStateException("onNext called multiple times!");
            }
            this.onNextCalled = true;
            if (!this.hasTerminated) {
                subscribeBufferToObservable(t);
            }
        }

        private void subscribeBufferToObservable(Observable<? extends T> t) {
            final BufferUntilSubscriber<T> buffer = BufferUntilSubscriber.create();
            final long expected = this.expectedDelivery;
            final Subscriber<T> s = new Subscriber<T>() {
                long remaining = expected;

                public void onNext(T t) {
                    this.remaining--;
                    buffer.onNext(t);
                }

                public void onError(Throwable e) {
                    buffer.onError(e);
                }

                public void onCompleted() {
                    buffer.onCompleted();
                    long r = this.remaining;
                    if (r > 0) {
                        AsyncOuterManager.this.requestRemaining(r);
                    }
                }
            };
            this.subscriptions.add(s);
            t.doOnTerminate(new Action0() {
                public void call() {
                    AsyncOuterManager.this.subscriptions.remove(s);
                }
            }).subscribe(s);
            this.merger.onNext(buffer);
        }
    }

    static final class UnicastSubject<T> extends Observable<T> implements Observer<T> {
        private State<T> state;

        public static <T> UnicastSubject<T> create() {
            return new UnicastSubject<>(new State());
        }

        protected UnicastSubject(State<T> state2) {
            super(state2);
            this.state = state2;
        }

        public void onCompleted() {
            this.state.subscriber.onCompleted();
        }

        public void onError(Throwable e) {
            this.state.subscriber.onError(e);
        }

        public void onNext(T t) {
            this.state.subscriber.onNext(t);
        }

        static final class State<T> implements Observable.OnSubscribe<T> {
            Subscriber<? super T> subscriber;

            State() {
            }

            public void call(Subscriber<? super T> s) {
                synchronized (this) {
                    if (this.subscriber == null) {
                        this.subscriber = s;
                    } else {
                        s.onError(new IllegalStateException("There can be only one subscriber"));
                    }
                }
            }
        }
    }
}

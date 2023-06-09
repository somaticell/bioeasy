package rx.internal.operators;

import com.facebook.common.time.Clock;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import rx.Observable;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func0;
import rx.functions.Func2;
import rx.internal.util.atomic.SpscLinkedAtomicQueue;
import rx.internal.util.unsafe.SpscLinkedQueue;
import rx.internal.util.unsafe.UnsafeAccess;

public final class OperatorScan<R, T> implements Observable.Operator<R, T> {
    private static final Object NO_INITIAL_VALUE = new Object();
    final Func2<R, ? super T, R> accumulator;
    private final Func0<R> initialValueFactory;

    public OperatorScan(final R initialValue, Func2<R, ? super T, R> accumulator2) {
        this(new Func0<R>() {
            public R call() {
                return initialValue;
            }
        }, accumulator2);
    }

    public OperatorScan(Func0<R> initialValueFactory2, Func2<R, ? super T, R> accumulator2) {
        this.initialValueFactory = initialValueFactory2;
        this.accumulator = accumulator2;
    }

    public OperatorScan(Func2<R, ? super T, R> accumulator2) {
        this(NO_INITIAL_VALUE, accumulator2);
    }

    public Subscriber<? super T> call(final Subscriber<? super R> child) {
        final R initialValue = this.initialValueFactory.call();
        if (initialValue == NO_INITIAL_VALUE) {
            return new Subscriber<T>(child) {
                boolean once;
                R value;

                public void onNext(T t) {
                    Object call;
                    if (!this.once) {
                        this.once = true;
                        call = t;
                    } else {
                        try {
                            call = OperatorScan.this.accumulator.call(this.value, t);
                        } catch (Throwable e) {
                            Exceptions.throwOrReport(e, child, t);
                            return;
                        }
                    }
                    this.value = call;
                    child.onNext(call);
                }

                public void onError(Throwable e) {
                    child.onError(e);
                }

                public void onCompleted() {
                    child.onCompleted();
                }
            };
        }
        final InitialProducer<R> ip = new InitialProducer<>(initialValue, child);
        Subscriber<T> parent = new Subscriber<T>() {
            private R value = initialValue;

            public void onNext(T currentValue) {
                try {
                    R v = OperatorScan.this.accumulator.call(this.value, currentValue);
                    this.value = v;
                    ip.onNext(v);
                } catch (Throwable e) {
                    Exceptions.throwOrReport(e, this, currentValue);
                }
            }

            public void onError(Throwable e) {
                ip.onError(e);
            }

            public void onCompleted() {
                ip.onCompleted();
            }

            public void setProducer(Producer producer) {
                ip.setProducer(producer);
            }
        };
        child.add(parent);
        child.setProducer(ip);
        return parent;
    }

    static final class InitialProducer<R> implements Producer, Observer<R> {
        final Subscriber<? super R> child;
        volatile boolean done;
        boolean emitting;
        Throwable error;
        boolean missed;
        long missedRequested;
        volatile Producer producer;
        final Queue<Object> queue;
        final AtomicLong requested;

        public InitialProducer(R initialValue, Subscriber<? super R> child2) {
            Queue<Object> q;
            this.child = child2;
            if (UnsafeAccess.isUnsafeAvailable()) {
                q = new SpscLinkedQueue<>();
            } else {
                q = new SpscLinkedAtomicQueue<>();
            }
            this.queue = q;
            q.offer(NotificationLite.instance().next(initialValue));
            this.requested = new AtomicLong();
        }

        public void onNext(R t) {
            this.queue.offer(NotificationLite.instance().next(t));
            emit();
        }

        /* access modifiers changed from: package-private */
        public boolean checkTerminated(boolean d, boolean empty, Subscriber<? super R> child2) {
            if (child2.isUnsubscribed()) {
                return true;
            }
            if (d) {
                Throwable err = this.error;
                if (err != null) {
                    child2.onError(err);
                    return true;
                } else if (empty) {
                    child2.onCompleted();
                    return true;
                }
            }
            return false;
        }

        public void onError(Throwable e) {
            this.error = e;
            this.done = true;
            emit();
        }

        public void onCompleted() {
            this.done = true;
            emit();
        }

        public void request(long n) {
            if (n < 0) {
                throw new IllegalArgumentException("n >= required but it was " + n);
            } else if (n != 0) {
                BackpressureUtils.getAndAddRequest(this.requested, n);
                Producer p = this.producer;
                if (p == null) {
                    synchronized (this.requested) {
                        p = this.producer;
                        if (p == null) {
                            this.missedRequested = BackpressureUtils.addCap(this.missedRequested, n);
                        }
                    }
                }
                if (p != null) {
                    p.request(n);
                }
                emit();
            }
        }

        public void setProducer(Producer p) {
            long mr;
            if (p == null) {
                throw new NullPointerException();
            }
            synchronized (this.requested) {
                if (this.producer != null) {
                    throw new IllegalStateException("Can't set more than one Producer!");
                }
                mr = this.missedRequested;
                if (mr != Clock.MAX_TIME) {
                    mr--;
                }
                this.missedRequested = 0;
                this.producer = p;
            }
            if (mr > 0) {
                p.request(mr);
            }
            emit();
        }

        /* access modifiers changed from: package-private */
        public void emit() {
            synchronized (this) {
                if (this.emitting) {
                    this.missed = true;
                    return;
                }
                this.emitting = true;
                emitLoop();
            }
        }

        /* access modifiers changed from: package-private */
        public void emitLoop() {
            Subscriber<? super R> child2 = this.child;
            Queue<Object> queue2 = this.queue;
            NotificationLite<R> nl = NotificationLite.instance();
            AtomicLong requested2 = this.requested;
            long r = requested2.get();
            while (true) {
                boolean max = r == Clock.MAX_TIME;
                if (!checkTerminated(this.done, queue2.isEmpty(), child2)) {
                    long e = 0;
                    while (r != 0) {
                        boolean d = this.done;
                        Object o = queue2.poll();
                        boolean empty = o == null;
                        if (checkTerminated(d, empty, child2)) {
                            return;
                        }
                        if (empty) {
                            break;
                        }
                        R v = nl.getValue(o);
                        try {
                            child2.onNext(v);
                            r--;
                            e--;
                        } catch (Throwable ex) {
                            Exceptions.throwOrReport(ex, child2, v);
                            return;
                        }
                    }
                    if (e != 0 && !max) {
                        r = requested2.addAndGet(e);
                    }
                    synchronized (this) {
                        if (!this.missed) {
                            this.emitting = false;
                            return;
                        }
                        this.missed = false;
                    }
                } else {
                    return;
                }
            }
        }
    }
}

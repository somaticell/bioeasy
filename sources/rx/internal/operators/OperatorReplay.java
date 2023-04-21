package rx.internal.operators;

import android.support.v7.widget.ActivityChooserView;
import com.facebook.common.time.Clock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Observer;
import rx.Producer;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Timestamped;
import rx.subscriptions.Subscriptions;

public final class OperatorReplay<T> extends ConnectableObservable<T> {
    static final Func0 DEFAULT_UNBOUNDED_FACTORY = new Func0() {
        public Object call() {
            return new UnboundedReplayBuffer(16);
        }
    };
    final Func0<? extends ReplayBuffer<T>> bufferFactory;
    final AtomicReference<ReplaySubscriber<T>> current;
    final Observable<? extends T> source;

    interface ReplayBuffer<T> {
        void complete();

        void error(Throwable th);

        void next(T t);

        void replay(InnerProducer<T> innerProducer);
    }

    public static <T, U, R> Observable<R> multicastSelector(final Func0<? extends ConnectableObservable<U>> connectableFactory, final Func1<? super Observable<U>, ? extends Observable<R>> selector) {
        return Observable.create(new Observable.OnSubscribe<R>() {
            public void call(final Subscriber<? super R> child) {
                try {
                    ConnectableObservable<U> co = (ConnectableObservable) connectableFactory.call();
                    ((Observable) selector.call(co)).subscribe(child);
                    co.connect(new Action1<Subscription>() {
                        public void call(Subscription t) {
                            child.add(t);
                        }
                    });
                } catch (Throwable e) {
                    Exceptions.throwOrReport(e, (Observer<?>) child);
                }
            }
        });
    }

    public static <T> ConnectableObservable<T> observeOn(final ConnectableObservable<T> co, Scheduler scheduler) {
        final Observable<T> observable = co.observeOn(scheduler);
        return new ConnectableObservable<T>(new Observable.OnSubscribe<T>() {
            public void call(final Subscriber<? super T> child) {
                observable.unsafeSubscribe(new Subscriber<T>(child) {
                    public void onNext(T t) {
                        child.onNext(t);
                    }

                    public void onError(Throwable e) {
                        child.onError(e);
                    }

                    public void onCompleted() {
                        child.onCompleted();
                    }
                });
            }
        }) {
            public void connect(Action1<? super Subscription> connection) {
                co.connect(connection);
            }
        };
    }

    public static <T> ConnectableObservable<T> create(Observable<? extends T> source2) {
        return create(source2, DEFAULT_UNBOUNDED_FACTORY);
    }

    public static <T> ConnectableObservable<T> create(Observable<? extends T> source2, final int bufferSize) {
        if (bufferSize == Integer.MAX_VALUE) {
            return create(source2);
        }
        return create(source2, new Func0<ReplayBuffer<T>>() {
            public ReplayBuffer<T> call() {
                return new SizeBoundReplayBuffer(bufferSize);
            }
        });
    }

    public static <T> ConnectableObservable<T> create(Observable<? extends T> source2, long maxAge, TimeUnit unit, Scheduler scheduler) {
        return create(source2, maxAge, unit, scheduler, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }

    public static <T> ConnectableObservable<T> create(Observable<? extends T> source2, long maxAge, TimeUnit unit, final Scheduler scheduler, final int bufferSize) {
        final long maxAgeInMillis = unit.toMillis(maxAge);
        return create(source2, new Func0<ReplayBuffer<T>>() {
            public ReplayBuffer<T> call() {
                return new SizeAndTimeBoundReplayBuffer(bufferSize, maxAgeInMillis, scheduler);
            }
        });
    }

    static <T> ConnectableObservable<T> create(Observable<? extends T> source2, final Func0<? extends ReplayBuffer<T>> bufferFactory2) {
        final AtomicReference<ReplaySubscriber<T>> curr = new AtomicReference<>();
        return new OperatorReplay(new Observable.OnSubscribe<T>() {
            /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START, MTH_ENTER_BLOCK] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void call(rx.Subscriber<? super T> r6) {
                /*
                    r5 = this;
                L_0x0000:
                    java.util.concurrent.atomic.AtomicReference r3 = r0
                    java.lang.Object r1 = r3.get()
                    rx.internal.operators.OperatorReplay$ReplaySubscriber r1 = (rx.internal.operators.OperatorReplay.ReplaySubscriber) r1
                    if (r1 != 0) goto L_0x0025
                    rx.internal.operators.OperatorReplay$ReplaySubscriber r2 = new rx.internal.operators.OperatorReplay$ReplaySubscriber
                    java.util.concurrent.atomic.AtomicReference r4 = r0
                    rx.functions.Func0 r3 = r4
                    java.lang.Object r3 = r3.call()
                    rx.internal.operators.OperatorReplay$ReplayBuffer r3 = (rx.internal.operators.OperatorReplay.ReplayBuffer) r3
                    r2.<init>(r4, r3)
                    r2.init()
                    java.util.concurrent.atomic.AtomicReference r3 = r0
                    boolean r3 = r3.compareAndSet(r1, r2)
                    if (r3 == 0) goto L_0x0000
                    r1 = r2
                L_0x0025:
                    rx.internal.operators.OperatorReplay$InnerProducer r0 = new rx.internal.operators.OperatorReplay$InnerProducer
                    r0.<init>(r1, r6)
                    r1.add(r0)
                    r6.add(r0)
                    rx.internal.operators.OperatorReplay$ReplayBuffer<T> r3 = r1.buffer
                    r3.replay(r0)
                    r6.setProducer(r0)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorReplay.AnonymousClass7.call(rx.Subscriber):void");
            }
        }, source2, curr, bufferFactory2);
    }

    private OperatorReplay(Observable.OnSubscribe<T> onSubscribe, Observable<? extends T> source2, AtomicReference<ReplaySubscriber<T>> current2, Func0<? extends ReplayBuffer<T>> bufferFactory2) {
        super(onSubscribe);
        this.source = source2;
        this.current = current2;
        this.bufferFactory = bufferFactory2;
    }

    public void connect(Action1<? super Subscription> connection) {
        ReplaySubscriber<T> ps;
        boolean doConnect;
        while (true) {
            ps = this.current.get();
            if (ps != null && !ps.isUnsubscribed()) {
                break;
            }
            ReplaySubscriber<T> u = new ReplaySubscriber<>(this.current, (ReplayBuffer) this.bufferFactory.call());
            u.init();
            if (this.current.compareAndSet(ps, u)) {
                ps = u;
                break;
            }
        }
        if (ps.shouldConnect.get() || !ps.shouldConnect.compareAndSet(false, true)) {
            doConnect = false;
        } else {
            doConnect = true;
        }
        connection.call(ps);
        if (doConnect) {
            this.source.unsafeSubscribe(ps);
        }
    }

    static final class ReplaySubscriber<T> extends Subscriber<T> implements Subscription {
        static final InnerProducer[] EMPTY = new InnerProducer[0];
        static final InnerProducer[] TERMINATED = new InnerProducer[0];
        final ReplayBuffer<T> buffer;
        boolean done;
        boolean emitting;
        long maxChildRequested;
        long maxUpstreamRequested;
        boolean missed;
        final NotificationLite<T> nl = NotificationLite.instance();
        volatile Producer producer;
        final AtomicReference<InnerProducer[]> producers = new AtomicReference<>(EMPTY);
        final AtomicBoolean shouldConnect = new AtomicBoolean();

        public ReplaySubscriber(AtomicReference<ReplaySubscriber<T>> atomicReference, ReplayBuffer<T> buffer2) {
            this.buffer = buffer2;
            request(0);
        }

        /* access modifiers changed from: package-private */
        public void init() {
            add(Subscriptions.create(new Action0() {
                public void call() {
                    ReplaySubscriber.this.producers.getAndSet(ReplaySubscriber.TERMINATED);
                }
            }));
        }

        /* access modifiers changed from: package-private */
        public boolean add(InnerProducer<T> producer2) {
            InnerProducer[] c;
            InnerProducer[] u;
            if (producer2 == null) {
                throw new NullPointerException();
            }
            do {
                c = this.producers.get();
                if (c == TERMINATED) {
                    return false;
                }
                int len = c.length;
                u = new InnerProducer[(len + 1)];
                System.arraycopy(c, 0, u, 0, len);
                u[len] = producer2;
            } while (!this.producers.compareAndSet(c, u));
            return true;
        }

        /* access modifiers changed from: package-private */
        public void remove(InnerProducer<T> producer2) {
            InnerProducer[] c;
            InnerProducer[] u;
            do {
                c = this.producers.get();
                if (c != EMPTY && c != TERMINATED) {
                    int j = -1;
                    int len = c.length;
                    int i = 0;
                    while (true) {
                        if (i >= len) {
                            break;
                        } else if (c[i].equals(producer2)) {
                            j = i;
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (j < 0) {
                        return;
                    }
                    if (len == 1) {
                        u = EMPTY;
                    } else {
                        u = new InnerProducer[(len - 1)];
                        System.arraycopy(c, 0, u, 0, j);
                        System.arraycopy(c, j + 1, u, j, (len - j) - 1);
                    }
                } else {
                    return;
                }
            } while (!this.producers.compareAndSet(c, u));
        }

        public void setProducer(Producer p) {
            if (this.producer != null) {
                throw new IllegalStateException("Only a single producer can be set on a Subscriber.");
            }
            this.producer = p;
            manageRequests();
            replay();
        }

        public void onNext(T t) {
            if (!this.done) {
                this.buffer.next(t);
                replay();
            }
        }

        public void onError(Throwable e) {
            if (!this.done) {
                this.done = true;
                try {
                    this.buffer.error(e);
                    replay();
                } finally {
                    unsubscribe();
                }
            }
        }

        public void onCompleted() {
            if (!this.done) {
                this.done = true;
                try {
                    this.buffer.complete();
                    replay();
                } finally {
                    unsubscribe();
                }
            }
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x002a, code lost:
            if (isUnsubscribed() != false) goto L_0x0006;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x002c, code lost:
            r14 = r22.maxChildRequested;
            r10 = r14;
            r5 = (rx.internal.operators.OperatorReplay.InnerProducer[]) r22.producers.get();
            r9 = r5.length;
            r8 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0040, code lost:
            if (r8 >= r9) goto L_0x0055;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0042, code lost:
            r10 = java.lang.Math.max(r10, r5[r8].totalRequested.get());
            r8 = r8 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0055, code lost:
            r18 = r22.maxUpstreamRequested;
            r12 = r22.producer;
            r6 = r10 - r14;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0065, code lost:
            if (r6 == 0) goto L_0x00b1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0067, code lost:
            r22.maxChildRequested = r10;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x006b, code lost:
            if (r12 == null) goto L_0x009d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0071, code lost:
            if (r18 == 0) goto L_0x0099;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0073, code lost:
            r22.maxUpstreamRequested = 0;
            r12.request(r18 + r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0082, code lost:
            monitor-enter(r22);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0089, code lost:
            if (r22.missed != false) goto L_0x00c7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x008b, code lost:
            r22.emitting = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x0093, code lost:
            monitor-exit(r22);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x0099, code lost:
            r12.request(r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x009d, code lost:
            r16 = r18 + r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x00a3, code lost:
            if (r16 >= 0) goto L_0x00aa;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x00a5, code lost:
            r16 = com.facebook.common.time.Clock.MAX_TIME;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x00aa, code lost:
            r22.maxUpstreamRequested = r16;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x00b5, code lost:
            if (r18 == 0) goto L_0x0082;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x00b7, code lost:
            if (r12 == null) goto L_0x0082;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:0x00b9, code lost:
            r22.maxUpstreamRequested = 0;
            r12.request(r18);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
            r22.missed = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x00cf, code lost:
            monitor-exit(r22);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:59:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void manageRequests() {
            /*
                r22 = this;
                boolean r20 = r22.isUnsubscribed()
                if (r20 == 0) goto L_0x0007
            L_0x0006:
                return
            L_0x0007:
                monitor-enter(r22)
                r0 = r22
                boolean r0 = r0.emitting     // Catch:{ all -> 0x001a }
                r20 = r0
                if (r20 == 0) goto L_0x001d
                r20 = 1
                r0 = r20
                r1 = r22
                r1.missed = r0     // Catch:{ all -> 0x001a }
                monitor-exit(r22)     // Catch:{ all -> 0x001a }
                goto L_0x0006
            L_0x001a:
                r20 = move-exception
                monitor-exit(r22)     // Catch:{ all -> 0x001a }
                throw r20
            L_0x001d:
                r20 = 1
                r0 = r20
                r1 = r22
                r1.emitting = r0     // Catch:{ all -> 0x001a }
                monitor-exit(r22)     // Catch:{ all -> 0x001a }
            L_0x0026:
                boolean r20 = r22.isUnsubscribed()
                if (r20 != 0) goto L_0x0006
                r0 = r22
                java.util.concurrent.atomic.AtomicReference<rx.internal.operators.OperatorReplay$InnerProducer[]> r0 = r0.producers
                r20 = r0
                java.lang.Object r4 = r20.get()
                rx.internal.operators.OperatorReplay$InnerProducer[] r4 = (rx.internal.operators.OperatorReplay.InnerProducer[]) r4
                r0 = r22
                long r14 = r0.maxChildRequested
                r10 = r14
                r5 = r4
                int r9 = r5.length
                r8 = 0
            L_0x0040:
                if (r8 >= r9) goto L_0x0055
                r13 = r5[r8]
                java.util.concurrent.atomic.AtomicLong r0 = r13.totalRequested
                r20 = r0
                long r20 = r20.get()
                r0 = r20
                long r10 = java.lang.Math.max(r10, r0)
                int r8 = r8 + 1
                goto L_0x0040
            L_0x0055:
                r0 = r22
                long r0 = r0.maxUpstreamRequested
                r18 = r0
                r0 = r22
                rx.Producer r12 = r0.producer
                long r6 = r10 - r14
                r20 = 0
                int r20 = (r6 > r20 ? 1 : (r6 == r20 ? 0 : -1))
                if (r20 == 0) goto L_0x00b1
                r0 = r22
                r0.maxChildRequested = r10
                if (r12 == 0) goto L_0x009d
                r20 = 0
                int r20 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
                if (r20 == 0) goto L_0x0099
                r20 = 0
                r0 = r20
                r2 = r22
                r2.maxUpstreamRequested = r0
                long r20 = r18 + r6
                r0 = r20
                r12.request(r0)
            L_0x0082:
                monitor-enter(r22)
                r0 = r22
                boolean r0 = r0.missed     // Catch:{ all -> 0x0096 }
                r20 = r0
                if (r20 != 0) goto L_0x00c7
                r20 = 0
                r0 = r20
                r1 = r22
                r1.emitting = r0     // Catch:{ all -> 0x0096 }
                monitor-exit(r22)     // Catch:{ all -> 0x0096 }
                goto L_0x0006
            L_0x0096:
                r20 = move-exception
                monitor-exit(r22)     // Catch:{ all -> 0x0096 }
                throw r20
            L_0x0099:
                r12.request(r6)
                goto L_0x0082
            L_0x009d:
                long r16 = r18 + r6
                r20 = 0
                int r20 = (r16 > r20 ? 1 : (r16 == r20 ? 0 : -1))
                if (r20 >= 0) goto L_0x00aa
                r16 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            L_0x00aa:
                r0 = r16
                r2 = r22
                r2.maxUpstreamRequested = r0
                goto L_0x0082
            L_0x00b1:
                r20 = 0
                int r20 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
                if (r20 == 0) goto L_0x0082
                if (r12 == 0) goto L_0x0082
                r20 = 0
                r0 = r20
                r2 = r22
                r2.maxUpstreamRequested = r0
                r0 = r18
                r12.request(r0)
                goto L_0x0082
            L_0x00c7:
                r20 = 0
                r0 = r20
                r1 = r22
                r1.missed = r0     // Catch:{ all -> 0x0096 }
                monitor-exit(r22)     // Catch:{ all -> 0x0096 }
                goto L_0x0026
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorReplay.ReplaySubscriber.manageRequests():void");
        }

        /* access modifiers changed from: package-private */
        public void replay() {
            for (InnerProducer<T> rp : (InnerProducer[]) this.producers.get()) {
                this.buffer.replay(rp);
            }
        }
    }

    static final class InnerProducer<T> extends AtomicLong implements Producer, Subscription {
        static final long UNSUBSCRIBED = Long.MIN_VALUE;
        private static final long serialVersionUID = -4453897557930727610L;
        final Subscriber<? super T> child;
        boolean emitting;
        Object index;
        boolean missed;
        final ReplaySubscriber<T> parent;
        final AtomicLong totalRequested = new AtomicLong();

        public InnerProducer(ReplaySubscriber<T> parent2, Subscriber<? super T> child2) {
            this.parent = parent2;
            this.child = child2;
        }

        public void request(long n) {
            long r;
            long u;
            if (n >= 0) {
                do {
                    r = get();
                    if (r == UNSUBSCRIBED) {
                        return;
                    }
                    if (r < 0 || n != 0) {
                        u = r + n;
                        if (u < 0) {
                            u = Clock.MAX_TIME;
                        }
                    } else {
                        return;
                    }
                } while (!compareAndSet(r, u));
                addTotalRequested(n);
                this.parent.manageRequests();
                this.parent.buffer.replay(this);
            }
        }

        /* access modifiers changed from: package-private */
        public void addTotalRequested(long n) {
            long r;
            long u;
            do {
                r = this.totalRequested.get();
                u = r + n;
                if (u < 0) {
                    u = Clock.MAX_TIME;
                }
            } while (!this.totalRequested.compareAndSet(r, u));
        }

        public long produced(long n) {
            long r;
            long u;
            if (n <= 0) {
                throw new IllegalArgumentException("Cant produce zero or less");
            }
            do {
                r = get();
                if (r == UNSUBSCRIBED) {
                    return UNSUBSCRIBED;
                }
                u = r - n;
                if (u < 0) {
                    throw new IllegalStateException("More produced (" + n + ") than requested (" + r + ")");
                }
            } while (!compareAndSet(r, u));
            return u;
        }

        public boolean isUnsubscribed() {
            return get() == UNSUBSCRIBED;
        }

        public void unsubscribe() {
            if (get() != UNSUBSCRIBED && getAndSet(UNSUBSCRIBED) != UNSUBSCRIBED) {
                this.parent.remove(this);
                this.parent.manageRequests();
            }
        }

        /* access modifiers changed from: package-private */
        public <U> U index() {
            return this.index;
        }
    }

    static final class UnboundedReplayBuffer<T> extends ArrayList<Object> implements ReplayBuffer<T> {
        private static final long serialVersionUID = 7063189396499112664L;
        final NotificationLite<T> nl = NotificationLite.instance();
        volatile int size;

        public UnboundedReplayBuffer(int capacityHint) {
            super(capacityHint);
        }

        public void next(T value) {
            add(this.nl.next(value));
            this.size++;
        }

        public void error(Throwable e) {
            add(this.nl.error(e));
            this.size++;
        }

        public void complete() {
            add(this.nl.completed());
            this.size++;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0014, code lost:
            r10 = r14.size;
            r1 = (java.lang.Integer) r15.index();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x001c, code lost:
            if (r1 == null) goto L_0x0051;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x001e, code lost:
            r0 = r1.intValue();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0022, code lost:
            r6 = r15.get();
            r8 = r6;
            r2 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x002d, code lost:
            if (r6 == 0) goto L_0x007a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x002f, code lost:
            if (r0 >= r10) goto L_0x007a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0031, code lost:
            r5 = get(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x003d, code lost:
            if (r14.nl.accept(r15.child, r5) != false) goto L_0x0009;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0043, code lost:
            if (r15.isUnsubscribed() != false) goto L_0x0009;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0045, code lost:
            r0 = r0 + 1;
            r6 = r6 - 1;
            r2 = r2 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0051, code lost:
            r0 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0053, code lost:
            r4 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0054, code lost:
            rx.exceptions.Exceptions.throwIfFatal(r4);
            r15.unsubscribe();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0060, code lost:
            if (r14.nl.isError(r5) != false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x006a, code lost:
            r15.child.onError(rx.exceptions.OnErrorThrowable.addValueAsLastCause(r4, r14.nl.getValue(r5)));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x007e, code lost:
            if (r2 == 0) goto L_0x0092;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x0080, code lost:
            r15.index = java.lang.Integer.valueOf(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x008d, code lost:
            if (r8 == com.facebook.common.time.Clock.MAX_TIME) goto L_0x0092;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x008f, code lost:
            r15.produced(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x0092, code lost:
            monitor-enter(r15);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x0095, code lost:
            if (r15.missed != false) goto L_0x00a0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x0097, code lost:
            r15.emitting = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x009a, code lost:
            monitor-exit(r15);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
            r15.missed = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x00a3, code lost:
            monitor-exit(r15);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:65:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:66:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:67:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:70:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
            if (r15.isUnsubscribed() != false) goto L_0x0009;
         */
        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void replay(rx.internal.operators.OperatorReplay.InnerProducer<T> r15) {
            /*
                r14 = this;
                monitor-enter(r15)
                boolean r11 = r15.emitting     // Catch:{ all -> 0x004e }
                if (r11 == 0) goto L_0x000a
                r11 = 1
                r15.missed = r11     // Catch:{ all -> 0x004e }
                monitor-exit(r15)     // Catch:{ all -> 0x004e }
            L_0x0009:
                return
            L_0x000a:
                r11 = 1
                r15.emitting = r11     // Catch:{ all -> 0x004e }
                monitor-exit(r15)     // Catch:{ all -> 0x004e }
            L_0x000e:
                boolean r11 = r15.isUnsubscribed()
                if (r11 != 0) goto L_0x0009
                int r10 = r14.size
                java.lang.Object r1 = r15.index()
                java.lang.Integer r1 = (java.lang.Integer) r1
                if (r1 == 0) goto L_0x0051
                int r0 = r1.intValue()
            L_0x0022:
                long r6 = r15.get()
                r8 = r6
                r2 = 0
            L_0x0029:
                r12 = 0
                int r11 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1))
                if (r11 == 0) goto L_0x007a
                if (r0 >= r10) goto L_0x007a
                java.lang.Object r5 = r14.get(r0)
                rx.internal.operators.NotificationLite<T> r11 = r14.nl     // Catch:{ Throwable -> 0x0053 }
                rx.Subscriber<? super T> r12 = r15.child     // Catch:{ Throwable -> 0x0053 }
                boolean r11 = r11.accept(r12, r5)     // Catch:{ Throwable -> 0x0053 }
                if (r11 != 0) goto L_0x0009
                boolean r11 = r15.isUnsubscribed()
                if (r11 != 0) goto L_0x0009
                int r0 = r0 + 1
                r12 = 1
                long r6 = r6 - r12
                r12 = 1
                long r2 = r2 + r12
                goto L_0x0029
            L_0x004e:
                r11 = move-exception
                monitor-exit(r15)     // Catch:{ all -> 0x004e }
                throw r11
            L_0x0051:
                r0 = 0
                goto L_0x0022
            L_0x0053:
                r4 = move-exception
                rx.exceptions.Exceptions.throwIfFatal(r4)
                r15.unsubscribe()
                rx.internal.operators.NotificationLite<T> r11 = r14.nl
                boolean r11 = r11.isError(r5)
                if (r11 != 0) goto L_0x0009
                rx.internal.operators.NotificationLite<T> r11 = r14.nl
                boolean r11 = r11.isCompleted(r5)
                if (r11 != 0) goto L_0x0009
                rx.Subscriber<? super T> r11 = r15.child
                rx.internal.operators.NotificationLite<T> r12 = r14.nl
                java.lang.Object r12 = r12.getValue(r5)
                java.lang.Throwable r12 = rx.exceptions.OnErrorThrowable.addValueAsLastCause(r4, r12)
                r11.onError(r12)
                goto L_0x0009
            L_0x007a:
                r12 = 0
                int r11 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1))
                if (r11 == 0) goto L_0x0092
                java.lang.Integer r11 = java.lang.Integer.valueOf(r0)
                r15.index = r11
                r12 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r11 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
                if (r11 == 0) goto L_0x0092
                r15.produced(r2)
            L_0x0092:
                monitor-enter(r15)
                boolean r11 = r15.missed     // Catch:{ all -> 0x009d }
                if (r11 != 0) goto L_0x00a0
                r11 = 0
                r15.emitting = r11     // Catch:{ all -> 0x009d }
                monitor-exit(r15)     // Catch:{ all -> 0x009d }
                goto L_0x0009
            L_0x009d:
                r11 = move-exception
                monitor-exit(r15)     // Catch:{ all -> 0x009d }
                throw r11
            L_0x00a0:
                r11 = 0
                r15.missed = r11     // Catch:{ all -> 0x009d }
                monitor-exit(r15)     // Catch:{ all -> 0x009d }
                goto L_0x000e
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorReplay.UnboundedReplayBuffer.replay(rx.internal.operators.OperatorReplay$InnerProducer):void");
        }
    }

    static final class Node extends AtomicReference<Node> {
        private static final long serialVersionUID = 245354315435971818L;
        final long index;
        final Object value;

        public Node(Object value2, long index2) {
            this.value = value2;
            this.index = index2;
        }
    }

    static class BoundedReplayBuffer<T> extends AtomicReference<Node> implements ReplayBuffer<T> {
        private static final long serialVersionUID = 2346567790059478686L;
        long index;
        final NotificationLite<T> nl = NotificationLite.instance();
        int size;
        Node tail;

        public BoundedReplayBuffer() {
            Node n = new Node((Object) null, 0);
            this.tail = n;
            set(n);
        }

        /* access modifiers changed from: package-private */
        public final void addLast(Node n) {
            this.tail.set(n);
            this.tail = n;
            this.size++;
        }

        /* access modifiers changed from: package-private */
        public final void removeFirst() {
            Node next = (Node) ((Node) get()).get();
            if (next == null) {
                throw new IllegalStateException("Empty list!");
            }
            this.size--;
            setFirst(next);
        }

        /* access modifiers changed from: package-private */
        public final void removeSome(int n) {
            Node head = (Node) get();
            while (n > 0) {
                head = (Node) head.get();
                n--;
                this.size--;
            }
            setFirst(head);
        }

        /* access modifiers changed from: package-private */
        public final void setFirst(Node n) {
            set(n);
        }

        public final void next(T value) {
            Object o = enterTransform(this.nl.next(value));
            long j = this.index + 1;
            this.index = j;
            addLast(new Node(o, j));
            truncate();
        }

        public final void error(Throwable e) {
            Object o = enterTransform(this.nl.error(e));
            long j = this.index + 1;
            this.index = j;
            addLast(new Node(o, j));
            truncateFinal();
        }

        public final void complete() {
            Object o = enterTransform(this.nl.completed());
            long j = this.index + 1;
            this.index = j;
            addLast(new Node(o, j));
            truncateFinal();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0014, code lost:
            r6 = r13.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x001f, code lost:
            if (r6 != com.facebook.common.time.Clock.MAX_TIME) goto L_0x008f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0021, code lost:
            r5 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0022, code lost:
            r0 = 0;
            r3 = (rx.internal.operators.OperatorReplay.Node) r13.index();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x002a, code lost:
            if (r3 != null) goto L_0x0039;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x002c, code lost:
            r3 = (rx.internal.operators.OperatorReplay.Node) get();
            r13.index = r3;
            r13.addTotalRequested(r3.index);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x003d, code lost:
            if (r13.isUnsubscribed() != false) goto L_0x0009;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0043, code lost:
            if (r6 == 0) goto L_0x00a0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0045, code lost:
            r8 = (rx.internal.operators.OperatorReplay.Node) r3.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x004b, code lost:
            if (r8 == null) goto L_0x00a0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x004d, code lost:
            r4 = leaveTransform(r8.value);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x005b, code lost:
            if (r12.nl.accept(r13.child, r4) == false) goto L_0x0091;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x005d, code lost:
            r13.index = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0061, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0062, code lost:
            r13.index = null;
            rx.exceptions.Exceptions.throwIfFatal(r2);
            r13.unsubscribe();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0071, code lost:
            if (r12.nl.isError(r4) != false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x007b, code lost:
            r13.child.onError(rx.exceptions.OnErrorThrowable.addValueAsLastCause(r2, r12.nl.getValue(r4)));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x008f, code lost:
            r5 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x0091, code lost:
            r0 = r0 + 1;
            r6 = r6 - 1;
            r3 = r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x009c, code lost:
            if (r13.isUnsubscribed() == false) goto L_0x003f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x00a4, code lost:
            if (r0 == 0) goto L_0x00ad;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x00a6, code lost:
            r13.index = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x00a8, code lost:
            if (r5 != false) goto L_0x00ad;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x00aa, code lost:
            r13.produced(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x00ad, code lost:
            monitor-enter(r13);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:0x00b0, code lost:
            if (r13.missed != false) goto L_0x00bb;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x00b2, code lost:
            r13.emitting = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x00b5, code lost:
            monitor-exit(r13);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
            r13.missed = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:0x00be, code lost:
            monitor-exit(r13);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:71:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:72:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:73:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:74:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:75:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:76:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:77:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
            if (r13.isUnsubscribed() != false) goto L_0x0009;
         */
        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void replay(rx.internal.operators.OperatorReplay.InnerProducer<T> r13) {
            /*
                r12 = this;
                monitor-enter(r13)
                boolean r9 = r13.emitting     // Catch:{ all -> 0x008c }
                if (r9 == 0) goto L_0x000a
                r9 = 1
                r13.missed = r9     // Catch:{ all -> 0x008c }
                monitor-exit(r13)     // Catch:{ all -> 0x008c }
            L_0x0009:
                return
            L_0x000a:
                r9 = 1
                r13.emitting = r9     // Catch:{ all -> 0x008c }
                monitor-exit(r13)     // Catch:{ all -> 0x008c }
            L_0x000e:
                boolean r9 = r13.isUnsubscribed()
                if (r9 != 0) goto L_0x0009
                long r6 = r13.get()
                r10 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r9 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
                if (r9 != 0) goto L_0x008f
                r5 = 1
            L_0x0022:
                r0 = 0
                java.lang.Object r3 = r13.index()
                rx.internal.operators.OperatorReplay$Node r3 = (rx.internal.operators.OperatorReplay.Node) r3
                if (r3 != 0) goto L_0x0039
                java.lang.Object r3 = r12.get()
                rx.internal.operators.OperatorReplay$Node r3 = (rx.internal.operators.OperatorReplay.Node) r3
                r13.index = r3
                long r10 = r3.index
                r13.addTotalRequested(r10)
            L_0x0039:
                boolean r9 = r13.isUnsubscribed()
                if (r9 != 0) goto L_0x0009
            L_0x003f:
                r10 = 0
                int r9 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
                if (r9 == 0) goto L_0x00a0
                java.lang.Object r8 = r3.get()
                rx.internal.operators.OperatorReplay$Node r8 = (rx.internal.operators.OperatorReplay.Node) r8
                if (r8 == 0) goto L_0x00a0
                java.lang.Object r9 = r8.value
                java.lang.Object r4 = r12.leaveTransform(r9)
                rx.internal.operators.NotificationLite<T> r9 = r12.nl     // Catch:{ Throwable -> 0x0061 }
                rx.Subscriber<? super T> r10 = r13.child     // Catch:{ Throwable -> 0x0061 }
                boolean r9 = r9.accept(r10, r4)     // Catch:{ Throwable -> 0x0061 }
                if (r9 == 0) goto L_0x0091
                r9 = 0
                r13.index = r9     // Catch:{ Throwable -> 0x0061 }
                goto L_0x0009
            L_0x0061:
                r2 = move-exception
                r9 = 0
                r13.index = r9
                rx.exceptions.Exceptions.throwIfFatal(r2)
                r13.unsubscribe()
                rx.internal.operators.NotificationLite<T> r9 = r12.nl
                boolean r9 = r9.isError(r4)
                if (r9 != 0) goto L_0x0009
                rx.internal.operators.NotificationLite<T> r9 = r12.nl
                boolean r9 = r9.isCompleted(r4)
                if (r9 != 0) goto L_0x0009
                rx.Subscriber<? super T> r9 = r13.child
                rx.internal.operators.NotificationLite<T> r10 = r12.nl
                java.lang.Object r10 = r10.getValue(r4)
                java.lang.Throwable r10 = rx.exceptions.OnErrorThrowable.addValueAsLastCause(r2, r10)
                r9.onError(r10)
                goto L_0x0009
            L_0x008c:
                r9 = move-exception
                monitor-exit(r13)     // Catch:{ all -> 0x008c }
                throw r9
            L_0x008f:
                r5 = 0
                goto L_0x0022
            L_0x0091:
                r10 = 1
                long r0 = r0 + r10
                r10 = 1
                long r6 = r6 - r10
                r3 = r8
                boolean r9 = r13.isUnsubscribed()
                if (r9 == 0) goto L_0x003f
                goto L_0x0009
            L_0x00a0:
                r10 = 0
                int r9 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1))
                if (r9 == 0) goto L_0x00ad
                r13.index = r3
                if (r5 != 0) goto L_0x00ad
                r13.produced(r0)
            L_0x00ad:
                monitor-enter(r13)
                boolean r9 = r13.missed     // Catch:{ all -> 0x00b8 }
                if (r9 != 0) goto L_0x00bb
                r9 = 0
                r13.emitting = r9     // Catch:{ all -> 0x00b8 }
                monitor-exit(r13)     // Catch:{ all -> 0x00b8 }
                goto L_0x0009
            L_0x00b8:
                r9 = move-exception
                monitor-exit(r13)     // Catch:{ all -> 0x00b8 }
                throw r9
            L_0x00bb:
                r9 = 0
                r13.missed = r9     // Catch:{ all -> 0x00b8 }
                monitor-exit(r13)     // Catch:{ all -> 0x00b8 }
                goto L_0x000e
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorReplay.BoundedReplayBuffer.replay(rx.internal.operators.OperatorReplay$InnerProducer):void");
        }

        /* access modifiers changed from: package-private */
        public Object enterTransform(Object value) {
            return value;
        }

        /* access modifiers changed from: package-private */
        public Object leaveTransform(Object value) {
            return value;
        }

        /* access modifiers changed from: package-private */
        public void truncate() {
        }

        /* access modifiers changed from: package-private */
        public void truncateFinal() {
        }

        /* access modifiers changed from: package-private */
        public final void collect(Collection<? super T> output) {
            Node n = (Node) get();
            while (true) {
                Node next = (Node) n.get();
                if (next != null) {
                    Object v = leaveTransform(next.value);
                    if (!this.nl.isCompleted(v) && !this.nl.isError(v)) {
                        output.add(this.nl.getValue(v));
                        n = next;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public boolean hasError() {
            return this.tail.value != null && this.nl.isError(leaveTransform(this.tail.value));
        }

        /* access modifiers changed from: package-private */
        public boolean hasCompleted() {
            return this.tail.value != null && this.nl.isCompleted(leaveTransform(this.tail.value));
        }
    }

    static final class SizeBoundReplayBuffer<T> extends BoundedReplayBuffer<T> {
        private static final long serialVersionUID = -5898283885385201806L;
        final int limit;

        public SizeBoundReplayBuffer(int limit2) {
            this.limit = limit2;
        }

        /* access modifiers changed from: package-private */
        public void truncate() {
            if (this.size > this.limit) {
                removeFirst();
            }
        }
    }

    static final class SizeAndTimeBoundReplayBuffer<T> extends BoundedReplayBuffer<T> {
        private static final long serialVersionUID = 3457957419649567404L;
        final int limit;
        final long maxAgeInMillis;
        final Scheduler scheduler;

        public SizeAndTimeBoundReplayBuffer(int limit2, long maxAgeInMillis2, Scheduler scheduler2) {
            this.scheduler = scheduler2;
            this.limit = limit2;
            this.maxAgeInMillis = maxAgeInMillis2;
        }

        /* access modifiers changed from: package-private */
        public Object enterTransform(Object value) {
            return new Timestamped(this.scheduler.now(), value);
        }

        /* access modifiers changed from: package-private */
        public Object leaveTransform(Object value) {
            return ((Timestamped) value).getValue();
        }

        /* access modifiers changed from: package-private */
        public void truncate() {
            long timeLimit = this.scheduler.now() - this.maxAgeInMillis;
            Node prev = (Node) get();
            Node next = (Node) prev.get();
            int e = 0;
            while (next != null) {
                if (this.size <= this.limit) {
                    if (((Timestamped) next.value).getTimestampMillis() > timeLimit) {
                        break;
                    }
                    e++;
                    this.size--;
                    prev = next;
                    next = (Node) next.get();
                } else {
                    e++;
                    this.size--;
                    prev = next;
                    next = (Node) next.get();
                }
            }
            if (e != 0) {
                setFirst(prev);
            }
        }

        /* access modifiers changed from: package-private */
        public void truncateFinal() {
            long timeLimit = this.scheduler.now() - this.maxAgeInMillis;
            Node prev = (Node) get();
            Node next = (Node) prev.get();
            int e = 0;
            while (next != null && this.size > 1 && ((Timestamped) next.value).getTimestampMillis() <= timeLimit) {
                e++;
                this.size--;
                prev = next;
                next = (Node) next.get();
            }
            if (e != 0) {
                setFirst(prev);
            }
        }
    }
}

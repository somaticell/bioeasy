package rx.subjects;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.annotations.Beta;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.internal.operators.NotificationLite;
import rx.internal.util.UtilityFunctions;
import rx.schedulers.Timestamped;
import rx.subjects.SubjectSubscriptionManager;

public final class ReplaySubject<T> extends Subject<T, T> {
    private static final Object[] EMPTY_ARRAY = new Object[0];
    final SubjectSubscriptionManager<T> ssm;
    final ReplayState<T, ?> state;

    interface EvictionPolicy {
        void evict(NodeList<Object> nodeList);

        void evictFinal(NodeList<Object> nodeList);

        boolean test(Object obj, long j);
    }

    interface ReplayState<T, I> {
        void complete();

        void error(Throwable th);

        boolean isEmpty();

        T latest();

        void next(T t);

        boolean replayObserver(SubjectSubscriptionManager.SubjectObserver<? super T> subjectObserver);

        I replayObserverFromIndex(I i, SubjectSubscriptionManager.SubjectObserver<? super T> subjectObserver);

        I replayObserverFromIndexTest(I i, SubjectSubscriptionManager.SubjectObserver<? super T> subjectObserver, long j);

        int size();

        boolean terminated();

        T[] toArray(T[] tArr);
    }

    public static <T> ReplaySubject<T> create() {
        return create(16);
    }

    public static <T> ReplaySubject<T> create(int capacity) {
        final UnboundedReplayState<T> state2 = new UnboundedReplayState<>(capacity);
        SubjectSubscriptionManager<T> ssm2 = new SubjectSubscriptionManager<>();
        ssm2.onStart = new Action1<SubjectSubscriptionManager.SubjectObserver<T>>() {
            public void call(SubjectSubscriptionManager.SubjectObserver<T> o) {
                o.index(Integer.valueOf(state2.replayObserverFromIndex((Integer) 0, o).intValue()));
            }
        };
        ssm2.onAdded = new Action1<SubjectSubscriptionManager.SubjectObserver<T>>() {
            /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
                r2 = r1;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:12:0x0015, code lost:
                r0 = ((java.lang.Integer) r8.index()).intValue();
                r3 = r2.get();
             */
            /* JADX WARNING: Code restructure failed: missing block: B:13:0x0023, code lost:
                if (r0 == r3) goto L_0x0030;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:14:0x0025, code lost:
                r8.index(r2.replayObserverFromIndex(java.lang.Integer.valueOf(r0), (rx.subjects.SubjectSubscriptionManager.SubjectObserver<? super T>) r8));
             */
            /* JADX WARNING: Code restructure failed: missing block: B:15:0x0030, code lost:
                monitor-enter(r8);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:18:0x0035, code lost:
                if (r3 != r2.get()) goto L_0x004a;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:19:0x0037, code lost:
                r8.emitting = false;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:20:0x003b, code lost:
                monitor-exit(r8);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:21:0x003c, code lost:
                if (1 != 0) goto L_?;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:22:0x003e, code lost:
                monitor-enter(r8);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
                r8.emitting = false;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:26:0x0042, code lost:
                monitor-exit(r8);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
                monitor-exit(r8);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:42:0x004f, code lost:
                r5 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:43:0x0050, code lost:
                if (0 == 0) goto L_0x0052;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:44:0x0052, code lost:
                monitor-enter(r8);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
                r8.emitting = false;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:49:0x0057, code lost:
                throw r5;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:61:?, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:62:?, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:63:?, code lost:
                return;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void call(rx.subjects.SubjectSubscriptionManager.SubjectObserver<T> r8) {
                /*
                    r7 = this;
                    monitor-enter(r8)
                    boolean r5 = r8.first     // Catch:{ all -> 0x0047 }
                    if (r5 == 0) goto L_0x0009
                    boolean r5 = r8.emitting     // Catch:{ all -> 0x0047 }
                    if (r5 == 0) goto L_0x000b
                L_0x0009:
                    monitor-exit(r8)     // Catch:{ all -> 0x0047 }
                L_0x000a:
                    return
                L_0x000b:
                    r5 = 0
                    r8.first = r5     // Catch:{ all -> 0x0047 }
                    r5 = 1
                    r8.emitting = r5     // Catch:{ all -> 0x0047 }
                    monitor-exit(r8)     // Catch:{ all -> 0x0047 }
                    r4 = 0
                    rx.subjects.ReplaySubject$UnboundedReplayState r2 = r1     // Catch:{ all -> 0x004f }
                L_0x0015:
                    java.lang.Object r5 = r8.index()     // Catch:{ all -> 0x004f }
                    java.lang.Integer r5 = (java.lang.Integer) r5     // Catch:{ all -> 0x004f }
                    int r0 = r5.intValue()     // Catch:{ all -> 0x004f }
                    int r3 = r2.get()     // Catch:{ all -> 0x004f }
                    if (r0 == r3) goto L_0x0030
                    java.lang.Integer r5 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x004f }
                    java.lang.Integer r1 = r2.replayObserverFromIndex((java.lang.Integer) r5, r8)     // Catch:{ all -> 0x004f }
                    r8.index(r1)     // Catch:{ all -> 0x004f }
                L_0x0030:
                    monitor-enter(r8)     // Catch:{ all -> 0x004f }
                    int r5 = r2.get()     // Catch:{ all -> 0x004c }
                    if (r3 != r5) goto L_0x004a
                    r5 = 0
                    r8.emitting = r5     // Catch:{ all -> 0x004c }
                    r4 = 1
                    monitor-exit(r8)     // Catch:{ all -> 0x004c }
                    if (r4 != 0) goto L_0x000a
                    monitor-enter(r8)
                    r5 = 0
                    r8.emitting = r5     // Catch:{ all -> 0x0044 }
                    monitor-exit(r8)     // Catch:{ all -> 0x0044 }
                    goto L_0x000a
                L_0x0044:
                    r5 = move-exception
                    monitor-exit(r8)     // Catch:{ all -> 0x0044 }
                    throw r5
                L_0x0047:
                    r5 = move-exception
                    monitor-exit(r8)     // Catch:{ all -> 0x0047 }
                    throw r5
                L_0x004a:
                    monitor-exit(r8)     // Catch:{ all -> 0x004c }
                    goto L_0x0015
                L_0x004c:
                    r5 = move-exception
                    monitor-exit(r8)     // Catch:{ all -> 0x004c }
                    throw r5     // Catch:{ all -> 0x004f }
                L_0x004f:
                    r5 = move-exception
                    if (r4 != 0) goto L_0x0057
                    monitor-enter(r8)
                    r6 = 0
                    r8.emitting = r6     // Catch:{ all -> 0x0058 }
                    monitor-exit(r8)     // Catch:{ all -> 0x0058 }
                L_0x0057:
                    throw r5
                L_0x0058:
                    r5 = move-exception
                    monitor-exit(r8)     // Catch:{ all -> 0x0058 }
                    throw r5
                */
                throw new UnsupportedOperationException("Method not decompiled: rx.subjects.ReplaySubject.AnonymousClass2.call(rx.subjects.SubjectSubscriptionManager$SubjectObserver):void");
            }
        };
        ssm2.onTerminated = new Action1<SubjectSubscriptionManager.SubjectObserver<T>>() {
            public void call(SubjectSubscriptionManager.SubjectObserver<T> o) {
                int idx = (Integer) o.index();
                if (idx == null) {
                    idx = 0;
                }
                state2.replayObserverFromIndex(idx, o);
            }
        };
        return new ReplaySubject<>(ssm2, ssm2, state2);
    }

    static <T> ReplaySubject<T> createUnbounded() {
        BoundedState<T> state2 = new BoundedState<>(new EmptyEvictionPolicy(), UtilityFunctions.identity(), UtilityFunctions.identity());
        return createWithState(state2, new DefaultOnAdd(state2));
    }

    public static <T> ReplaySubject<T> createWithSize(int size) {
        BoundedState<T> state2 = new BoundedState<>(new SizeEvictionPolicy(size), UtilityFunctions.identity(), UtilityFunctions.identity());
        return createWithState(state2, new DefaultOnAdd(state2));
    }

    public static <T> ReplaySubject<T> createWithTime(long time, TimeUnit unit, Scheduler scheduler) {
        BoundedState<T> state2 = new BoundedState<>(new TimeEvictionPolicy(unit.toMillis(time), scheduler), new AddTimestamped(scheduler), new RemoveTimestamped());
        return createWithState(state2, new TimedOnAdd(state2, scheduler));
    }

    public static <T> ReplaySubject<T> createWithTimeAndSize(long time, TimeUnit unit, int size, Scheduler scheduler) {
        BoundedState<T> state2 = new BoundedState<>(new PairEvictionPolicy(new SizeEvictionPolicy(size), new TimeEvictionPolicy(unit.toMillis(time), scheduler)), new AddTimestamped(scheduler), new RemoveTimestamped());
        return createWithState(state2, new TimedOnAdd(state2, scheduler));
    }

    static <T> ReplaySubject<T> createWithState(final BoundedState<T> state2, Action1<SubjectSubscriptionManager.SubjectObserver<T>> onStart) {
        SubjectSubscriptionManager<T> ssm2 = new SubjectSubscriptionManager<>();
        ssm2.onStart = onStart;
        ssm2.onAdded = new Action1<SubjectSubscriptionManager.SubjectObserver<T>>() {
            /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
                r0 = (rx.subjects.ReplaySubject.NodeList.Node) r7.index();
                r2 = r2.tail();
             */
            /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
                if (r0 == r2) goto L_0x002a;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:13:0x0021, code lost:
                r7.index(r2.replayObserverFromIndex(r0, r7));
             */
            /* JADX WARNING: Code restructure failed: missing block: B:14:0x002a, code lost:
                monitor-enter(r7);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:17:0x0031, code lost:
                if (r2 != r2.tail()) goto L_0x0046;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:18:0x0033, code lost:
                r7.emitting = false;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:19:0x0037, code lost:
                monitor-exit(r7);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:20:0x0038, code lost:
                if (1 != 0) goto L_?;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:21:0x003a, code lost:
                monitor-enter(r7);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
                r7.emitting = false;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:25:0x003e, code lost:
                monitor-exit(r7);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
                monitor-exit(r7);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:41:0x004b, code lost:
                r4 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:42:0x004c, code lost:
                if (0 == 0) goto L_0x004e;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:43:0x004e, code lost:
                monitor-enter(r7);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
                r7.emitting = false;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:48:0x0053, code lost:
                throw r4;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:61:?, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:62:?, code lost:
                return;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void call(rx.subjects.SubjectSubscriptionManager.SubjectObserver<T> r7) {
                /*
                    r6 = this;
                    monitor-enter(r7)
                    boolean r4 = r7.first     // Catch:{ all -> 0x0043 }
                    if (r4 == 0) goto L_0x0009
                    boolean r4 = r7.emitting     // Catch:{ all -> 0x0043 }
                    if (r4 == 0) goto L_0x000b
                L_0x0009:
                    monitor-exit(r7)     // Catch:{ all -> 0x0043 }
                L_0x000a:
                    return
                L_0x000b:
                    r4 = 0
                    r7.first = r4     // Catch:{ all -> 0x0043 }
                    r4 = 1
                    r7.emitting = r4     // Catch:{ all -> 0x0043 }
                    monitor-exit(r7)     // Catch:{ all -> 0x0043 }
                    r3 = 0
                L_0x0013:
                    java.lang.Object r0 = r7.index()     // Catch:{ all -> 0x004b }
                    rx.subjects.ReplaySubject$NodeList$Node r0 = (rx.subjects.ReplaySubject.NodeList.Node) r0     // Catch:{ all -> 0x004b }
                    rx.subjects.ReplaySubject$BoundedState r4 = r2     // Catch:{ all -> 0x004b }
                    rx.subjects.ReplaySubject$NodeList$Node r2 = r4.tail()     // Catch:{ all -> 0x004b }
                    if (r0 == r2) goto L_0x002a
                    rx.subjects.ReplaySubject$BoundedState r4 = r2     // Catch:{ all -> 0x004b }
                    rx.subjects.ReplaySubject$NodeList$Node r1 = r4.replayObserverFromIndex((rx.subjects.ReplaySubject.NodeList.Node<java.lang.Object>) r0, r7)     // Catch:{ all -> 0x004b }
                    r7.index(r1)     // Catch:{ all -> 0x004b }
                L_0x002a:
                    monitor-enter(r7)     // Catch:{ all -> 0x004b }
                    rx.subjects.ReplaySubject$BoundedState r4 = r2     // Catch:{ all -> 0x0048 }
                    rx.subjects.ReplaySubject$NodeList$Node r4 = r4.tail()     // Catch:{ all -> 0x0048 }
                    if (r2 != r4) goto L_0x0046
                    r4 = 0
                    r7.emitting = r4     // Catch:{ all -> 0x0048 }
                    r3 = 1
                    monitor-exit(r7)     // Catch:{ all -> 0x0048 }
                    if (r3 != 0) goto L_0x000a
                    monitor-enter(r7)
                    r4 = 0
                    r7.emitting = r4     // Catch:{ all -> 0x0040 }
                    monitor-exit(r7)     // Catch:{ all -> 0x0040 }
                    goto L_0x000a
                L_0x0040:
                    r4 = move-exception
                    monitor-exit(r7)     // Catch:{ all -> 0x0040 }
                    throw r4
                L_0x0043:
                    r4 = move-exception
                    monitor-exit(r7)     // Catch:{ all -> 0x0043 }
                    throw r4
                L_0x0046:
                    monitor-exit(r7)     // Catch:{ all -> 0x0048 }
                    goto L_0x0013
                L_0x0048:
                    r4 = move-exception
                    monitor-exit(r7)     // Catch:{ all -> 0x0048 }
                    throw r4     // Catch:{ all -> 0x004b }
                L_0x004b:
                    r4 = move-exception
                    if (r3 != 0) goto L_0x0053
                    monitor-enter(r7)
                    r5 = 0
                    r7.emitting = r5     // Catch:{ all -> 0x0054 }
                    monitor-exit(r7)     // Catch:{ all -> 0x0054 }
                L_0x0053:
                    throw r4
                L_0x0054:
                    r4 = move-exception
                    monitor-exit(r7)     // Catch:{ all -> 0x0054 }
                    throw r4
                */
                throw new UnsupportedOperationException("Method not decompiled: rx.subjects.ReplaySubject.AnonymousClass4.call(rx.subjects.SubjectSubscriptionManager$SubjectObserver):void");
            }
        };
        ssm2.onTerminated = new Action1<SubjectSubscriptionManager.SubjectObserver<T>>() {
            public void call(SubjectSubscriptionManager.SubjectObserver<T> t1) {
                NodeList.Node<Object> l = (NodeList.Node) t1.index();
                if (l == null) {
                    l = state2.head();
                }
                state2.replayObserverFromIndex(l, t1);
            }
        };
        return new ReplaySubject<>(ssm2, ssm2, state2);
    }

    ReplaySubject(Observable.OnSubscribe<T> onSubscribe, SubjectSubscriptionManager<T> ssm2, ReplayState<T, ?> state2) {
        super(onSubscribe);
        this.ssm = ssm2;
        this.state = state2;
    }

    public void onNext(T t) {
        if (this.ssm.active) {
            this.state.next(t);
            for (SubjectSubscriptionManager.SubjectObserver<? super T> o : this.ssm.observers()) {
                if (caughtUp(o)) {
                    o.onNext(t);
                }
            }
        }
    }

    public void onError(Throwable e) {
        if (this.ssm.active) {
            this.state.error(e);
            List<Throwable> errors = null;
            for (SubjectSubscriptionManager.SubjectObserver<? super T> o : this.ssm.terminate(NotificationLite.instance().error(e))) {
                try {
                    if (caughtUp(o)) {
                        o.onError(e);
                    }
                } catch (Throwable e2) {
                    if (errors == null) {
                        errors = new ArrayList<>();
                    }
                    errors.add(e2);
                }
            }
            Exceptions.throwIfAny(errors);
        }
    }

    public void onCompleted() {
        if (this.ssm.active) {
            this.state.complete();
            for (SubjectSubscriptionManager.SubjectObserver<? super T> o : this.ssm.terminate(NotificationLite.instance().completed())) {
                if (caughtUp(o)) {
                    o.onCompleted();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int subscriberCount() {
        return ((SubjectSubscriptionManager.State) this.ssm.get()).observers.length;
    }

    public boolean hasObservers() {
        return this.ssm.observers().length > 0;
    }

    private boolean caughtUp(SubjectSubscriptionManager.SubjectObserver<? super T> o) {
        if (o.caughtUp) {
            return true;
        }
        if (this.state.replayObserver(o)) {
            o.caughtUp = true;
            o.index((Object) null);
        }
        return false;
    }

    static final class UnboundedReplayState<T> extends AtomicInteger implements ReplayState<T, Integer> {
        private final ArrayList<Object> list;
        private final NotificationLite<T> nl = NotificationLite.instance();
        private volatile boolean terminated;

        public UnboundedReplayState(int initialCapacity) {
            this.list = new ArrayList<>(initialCapacity);
        }

        public void next(T n) {
            if (!this.terminated) {
                this.list.add(this.nl.next(n));
                getAndIncrement();
            }
        }

        public void accept(Observer<? super T> o, int idx) {
            this.nl.accept(o, this.list.get(idx));
        }

        public void complete() {
            if (!this.terminated) {
                this.terminated = true;
                this.list.add(this.nl.completed());
                getAndIncrement();
            }
        }

        public void error(Throwable e) {
            if (!this.terminated) {
                this.terminated = true;
                this.list.add(this.nl.error(e));
                getAndIncrement();
            }
        }

        public boolean terminated() {
            return this.terminated;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0014, code lost:
            r6.index(java.lang.Integer.valueOf(replayObserverFromIndex(r1, r6).intValue()));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0040, code lost:
            throw new java.lang.IllegalStateException("failed to find lastEmittedLink for: " + r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            return true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x000c, code lost:
            r1 = (java.lang.Integer) r6.index();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
            if (r1 == null) goto L_0x0028;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean replayObserver(rx.subjects.SubjectSubscriptionManager.SubjectObserver<? super T> r6) {
            /*
                r5 = this;
                r2 = 0
                monitor-enter(r6)
                r3 = 0
                r6.first = r3     // Catch:{ all -> 0x0025 }
                boolean r3 = r6.emitting     // Catch:{ all -> 0x0025 }
                if (r3 == 0) goto L_0x000b
                monitor-exit(r6)     // Catch:{ all -> 0x0025 }
            L_0x000a:
                return r2
            L_0x000b:
                monitor-exit(r6)     // Catch:{ all -> 0x0025 }
                java.lang.Object r1 = r6.index()
                java.lang.Integer r1 = (java.lang.Integer) r1
                if (r1 == 0) goto L_0x0028
                java.lang.Integer r2 = r5.replayObserverFromIndex((java.lang.Integer) r1, r6)
                int r0 = r2.intValue()
                java.lang.Integer r2 = java.lang.Integer.valueOf(r0)
                r6.index(r2)
                r2 = 1
                goto L_0x000a
            L_0x0025:
                r2 = move-exception
                monitor-exit(r6)     // Catch:{ all -> 0x0025 }
                throw r2
            L_0x0028:
                java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "failed to find lastEmittedLink for: "
                java.lang.StringBuilder r3 = r3.append(r4)
                java.lang.StringBuilder r3 = r3.append(r6)
                java.lang.String r3 = r3.toString()
                r2.<init>(r3)
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.subjects.ReplaySubject.UnboundedReplayState.replayObserver(rx.subjects.SubjectSubscriptionManager$SubjectObserver):boolean");
        }

        public Integer replayObserverFromIndex(Integer idx, SubjectSubscriptionManager.SubjectObserver<? super T> observer) {
            int i = idx.intValue();
            while (i < get()) {
                accept(observer, i);
                i++;
            }
            return Integer.valueOf(i);
        }

        public Integer replayObserverFromIndexTest(Integer idx, SubjectSubscriptionManager.SubjectObserver<? super T> observer, long now) {
            return replayObserverFromIndex(idx, observer);
        }

        public int size() {
            int idx = get();
            if (idx <= 0) {
                return idx;
            }
            Object o = this.list.get(idx - 1);
            if (this.nl.isCompleted(o) || this.nl.isError(o)) {
                return idx - 1;
            }
            return idx;
        }

        public boolean isEmpty() {
            return size() == 0;
        }

        public T[] toArray(T[] a) {
            int s = size();
            if (s > 0) {
                if (s > a.length) {
                    a = (Object[]) ((Object[]) Array.newInstance(a.getClass().getComponentType(), s));
                }
                for (int i = 0; i < s; i++) {
                    a[i] = this.list.get(i);
                }
                if (a.length > s) {
                    a[s] = null;
                }
            } else if (a.length > 0) {
                a[0] = null;
            }
            return a;
        }

        public T latest() {
            int idx = get();
            if (idx <= 0) {
                return null;
            }
            Object o = this.list.get(idx - 1);
            if (!this.nl.isCompleted(o) && !this.nl.isError(o)) {
                return this.nl.getValue(o);
            }
            if (idx > 1) {
                return this.nl.getValue(this.list.get(idx - 2));
            }
            return null;
        }
    }

    static final class BoundedState<T> implements ReplayState<T, NodeList.Node<Object>> {
        final Func1<Object, Object> enterTransform;
        final EvictionPolicy evictionPolicy;
        final Func1<Object, Object> leaveTransform;
        final NodeList<Object> list = new NodeList<>();
        final NotificationLite<T> nl = NotificationLite.instance();
        volatile NodeList.Node<Object> tail = this.list.tail;
        volatile boolean terminated;

        public BoundedState(EvictionPolicy evictionPolicy2, Func1<Object, Object> enterTransform2, Func1<Object, Object> leaveTransform2) {
            this.evictionPolicy = evictionPolicy2;
            this.enterTransform = enterTransform2;
            this.leaveTransform = leaveTransform2;
        }

        public void next(T value) {
            if (!this.terminated) {
                this.list.addLast(this.enterTransform.call(this.nl.next(value)));
                this.evictionPolicy.evict(this.list);
                this.tail = this.list.tail;
            }
        }

        public void complete() {
            if (!this.terminated) {
                this.terminated = true;
                this.list.addLast(this.enterTransform.call(this.nl.completed()));
                this.evictionPolicy.evictFinal(this.list);
                this.tail = this.list.tail;
            }
        }

        public void error(Throwable e) {
            if (!this.terminated) {
                this.terminated = true;
                this.list.addLast(this.enterTransform.call(this.nl.error(e)));
                this.evictionPolicy.evictFinal(this.list);
                this.tail = this.list.tail;
            }
        }

        public void accept(Observer<? super T> o, NodeList.Node<Object> node) {
            this.nl.accept(o, this.leaveTransform.call(node.value));
        }

        public void acceptTest(Observer<? super T> o, NodeList.Node<Object> node, long now) {
            Object v = node.value;
            if (!this.evictionPolicy.test(v, now)) {
                this.nl.accept(o, this.leaveTransform.call(v));
            }
        }

        public NodeList.Node<Object> head() {
            return this.list.head;
        }

        public NodeList.Node<Object> tail() {
            return this.tail;
        }

        public boolean replayObserver(SubjectSubscriptionManager.SubjectObserver<? super T> observer) {
            synchronized (observer) {
                observer.first = false;
                if (observer.emitting) {
                    return false;
                }
                observer.index(replayObserverFromIndex((NodeList.Node) observer.index(), observer));
                return true;
            }
        }

        public NodeList.Node<Object> replayObserverFromIndex(NodeList.Node<Object> node, SubjectSubscriptionManager.SubjectObserver<? super T> observer) {
            while (true) {
                NodeList.Node<T> l = node;
                if (l == tail()) {
                    return l;
                }
                accept(observer, l.next);
                l = l.next;
            }
        }

        public NodeList.Node<Object> replayObserverFromIndexTest(NodeList.Node<Object> node, SubjectSubscriptionManager.SubjectObserver<? super T> observer, long now) {
            while (true) {
                NodeList.Node<T> l = node;
                if (l == tail()) {
                    return l;
                }
                acceptTest(observer, l.next, now);
                l = l.next;
            }
        }

        public boolean terminated() {
            return this.terminated;
        }

        public int size() {
            Object value;
            int size = 0;
            NodeList.Node<Object> l = head();
            for (NodeList.Node<T> node = l.next; node != null; node = node.next) {
                size++;
                l = node;
            }
            if (l.value == null || (value = this.leaveTransform.call(l.value)) == null) {
                return size;
            }
            if (this.nl.isError(value) || this.nl.isCompleted(value)) {
                return size - 1;
            }
            return size;
        }

        public boolean isEmpty() {
            NodeList.Node<T> node = head().next;
            if (node == null) {
                return true;
            }
            Object value = this.leaveTransform.call(node.value);
            if (this.nl.isError(value) || this.nl.isCompleted(value)) {
                return true;
            }
            return false;
        }

        public T[] toArray(T[] a) {
            List<T> list2 = new ArrayList<>();
            for (NodeList.Node<T> node = head().next; node != null; node = node.next) {
                Object o = this.leaveTransform.call(node.value);
                if (node.next == null && (this.nl.isError(o) || this.nl.isCompleted(o))) {
                    break;
                }
                list2.add(o);
            }
            return list2.toArray(a);
        }

        public T latest() {
            NodeList.Node<T> node = head().next;
            if (node == null) {
                return null;
            }
            NodeList.Node<T> node2 = null;
            while (node != tail()) {
                node2 = node;
                node = node.next;
            }
            Object value = this.leaveTransform.call(node.value);
            if (!this.nl.isError(value) && !this.nl.isCompleted(value)) {
                return this.nl.getValue(value);
            }
            if (node2 == null) {
                return null;
            }
            return this.nl.getValue(this.leaveTransform.call(node2.value));
        }
    }

    static final class SizeEvictionPolicy implements EvictionPolicy {
        final int maxSize;

        public SizeEvictionPolicy(int maxSize2) {
            this.maxSize = maxSize2;
        }

        public void evict(NodeList<Object> t1) {
            while (t1.size() > this.maxSize) {
                t1.removeFirst();
            }
        }

        public boolean test(Object value, long now) {
            return false;
        }

        public void evictFinal(NodeList<Object> t1) {
            while (t1.size() > this.maxSize + 1) {
                t1.removeFirst();
            }
        }
    }

    static final class TimeEvictionPolicy implements EvictionPolicy {
        final long maxAgeMillis;
        final Scheduler scheduler;

        public TimeEvictionPolicy(long maxAgeMillis2, Scheduler scheduler2) {
            this.maxAgeMillis = maxAgeMillis2;
            this.scheduler = scheduler2;
        }

        public void evict(NodeList<Object> t1) {
            long now = this.scheduler.now();
            while (!t1.isEmpty() && test(t1.head.next.value, now)) {
                t1.removeFirst();
            }
        }

        public void evictFinal(NodeList<Object> t1) {
            long now = this.scheduler.now();
            while (t1.size > 1 && test(t1.head.next.value, now)) {
                t1.removeFirst();
            }
        }

        public boolean test(Object value, long now) {
            return ((Timestamped) value).getTimestampMillis() <= now - this.maxAgeMillis;
        }
    }

    static final class PairEvictionPolicy implements EvictionPolicy {
        final EvictionPolicy first;
        final EvictionPolicy second;

        public PairEvictionPolicy(EvictionPolicy first2, EvictionPolicy second2) {
            this.first = first2;
            this.second = second2;
        }

        public void evict(NodeList<Object> t1) {
            this.first.evict(t1);
            this.second.evict(t1);
        }

        public void evictFinal(NodeList<Object> t1) {
            this.first.evictFinal(t1);
            this.second.evictFinal(t1);
        }

        public boolean test(Object value, long now) {
            return this.first.test(value, now) || this.second.test(value, now);
        }
    }

    static final class AddTimestamped implements Func1<Object, Object> {
        final Scheduler scheduler;

        public AddTimestamped(Scheduler scheduler2) {
            this.scheduler = scheduler2;
        }

        public Object call(Object t1) {
            return new Timestamped(this.scheduler.now(), t1);
        }
    }

    static final class RemoveTimestamped implements Func1<Object, Object> {
        RemoveTimestamped() {
        }

        public Object call(Object t1) {
            return ((Timestamped) t1).getValue();
        }
    }

    static final class DefaultOnAdd<T> implements Action1<SubjectSubscriptionManager.SubjectObserver<T>> {
        final BoundedState<T> state;

        public DefaultOnAdd(BoundedState<T> state2) {
            this.state = state2;
        }

        public void call(SubjectSubscriptionManager.SubjectObserver<T> t1) {
            t1.index(this.state.replayObserverFromIndex(this.state.head(), t1));
        }
    }

    static final class TimedOnAdd<T> implements Action1<SubjectSubscriptionManager.SubjectObserver<T>> {
        final Scheduler scheduler;
        final BoundedState<T> state;

        public TimedOnAdd(BoundedState<T> state2, Scheduler scheduler2) {
            this.state = state2;
            this.scheduler = scheduler2;
        }

        public void call(SubjectSubscriptionManager.SubjectObserver<T> t1) {
            NodeList.Node<Object> l;
            if (!this.state.terminated) {
                l = this.state.replayObserverFromIndexTest(this.state.head(), t1, this.scheduler.now());
            } else {
                l = this.state.replayObserverFromIndex(this.state.head(), t1);
            }
            t1.index(l);
        }
    }

    static final class NodeList<T> {
        final Node<T> head = new Node<>(null);
        int size;
        Node<T> tail = this.head;

        NodeList() {
        }

        static final class Node<T> {
            volatile Node<T> next;
            final T value;

            Node(T value2) {
                this.value = value2;
            }
        }

        public void addLast(T value) {
            Node<T> t = this.tail;
            Node<T> t2 = new Node<>(value);
            t.next = t2;
            this.tail = t2;
            this.size++;
        }

        public T removeFirst() {
            if (this.head.next == null) {
                throw new IllegalStateException("Empty!");
            }
            Node<T> t = this.head.next;
            this.head.next = t.next;
            if (this.head.next == null) {
                this.tail = this.head;
            }
            this.size--;
            return t.value;
        }

        public boolean isEmpty() {
            return this.size == 0;
        }

        public int size() {
            return this.size;
        }

        public void clear() {
            this.tail = this.head;
            this.size = 0;
        }
    }

    static final class EmptyEvictionPolicy implements EvictionPolicy {
        EmptyEvictionPolicy() {
        }

        public boolean test(Object value, long now) {
            return true;
        }

        public void evict(NodeList<Object> nodeList) {
        }

        public void evictFinal(NodeList<Object> nodeList) {
        }
    }

    @Beta
    public boolean hasThrowable() {
        return this.ssm.nl.isError(this.ssm.getLatest());
    }

    @Beta
    public boolean hasCompleted() {
        NotificationLite<T> nl = this.ssm.nl;
        Object o = this.ssm.getLatest();
        return o != null && !nl.isError(o);
    }

    @Beta
    public Throwable getThrowable() {
        NotificationLite<T> nl = this.ssm.nl;
        Object o = this.ssm.getLatest();
        if (nl.isError(o)) {
            return nl.getError(o);
        }
        return null;
    }

    @Beta
    public int size() {
        return this.state.size();
    }

    @Beta
    public boolean hasAnyValue() {
        return !this.state.isEmpty();
    }

    @Beta
    public boolean hasValue() {
        return hasAnyValue();
    }

    @Beta
    public T[] getValues(T[] a) {
        return this.state.toArray(a);
    }

    @Beta
    public Object[] getValues() {
        T[] r = getValues(EMPTY_ARRAY);
        if (r == EMPTY_ARRAY) {
            return new Object[0];
        }
        return r;
    }

    @Beta
    public T getValue() {
        return this.state.latest();
    }
}

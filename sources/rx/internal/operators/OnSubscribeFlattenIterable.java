package rx.internal.operators;

import com.facebook.common.time.Clock;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.exceptions.MissingBackpressureException;
import rx.functions.Func1;
import rx.internal.operators.OnSubscribeFromIterable;
import rx.internal.util.ExceptionsUtils;
import rx.internal.util.RxJavaPluginUtils;
import rx.internal.util.RxRingBuffer;
import rx.internal.util.ScalarSynchronousObservable;
import rx.internal.util.atomic.SpscAtomicArrayQueue;
import rx.internal.util.atomic.SpscLinkedArrayQueue;
import rx.internal.util.unsafe.SpscArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;

public final class OnSubscribeFlattenIterable<T, R> implements Observable.OnSubscribe<R> {
    final Func1<? super T, ? extends Iterable<? extends R>> mapper;
    final int prefetch;
    final Observable<? extends T> source;

    protected OnSubscribeFlattenIterable(Observable<? extends T> source2, Func1<? super T, ? extends Iterable<? extends R>> mapper2, int prefetch2) {
        this.source = source2;
        this.mapper = mapper2;
        this.prefetch = prefetch2;
    }

    public void call(Subscriber<? super R> t) {
        final FlattenIterableSubscriber<T, R> parent = new FlattenIterableSubscriber<>(t, this.mapper, this.prefetch);
        t.add(parent);
        t.setProducer(new Producer() {
            public void request(long n) {
                parent.requestMore(n);
            }
        });
        this.source.unsafeSubscribe(parent);
    }

    public static <T, R> Observable<R> createFrom(Observable<? extends T> source2, Func1<? super T, ? extends Iterable<? extends R>> mapper2, int prefetch2) {
        if (source2 instanceof ScalarSynchronousObservable) {
            return Observable.create(new OnSubscribeScalarFlattenIterable(((ScalarSynchronousObservable) source2).get(), mapper2));
        }
        return Observable.create(new OnSubscribeFlattenIterable(source2, mapper2, prefetch2));
    }

    static final class FlattenIterableSubscriber<T, R> extends Subscriber<T> {
        Iterator<? extends R> active;
        final Subscriber<? super R> actual;
        volatile boolean done;
        final AtomicReference<Throwable> error = new AtomicReference<>();
        final long limit;
        final Func1<? super T, ? extends Iterable<? extends R>> mapper;
        final NotificationLite<T> nl = NotificationLite.instance();
        long produced;
        final Queue<Object> queue;
        final AtomicLong requested = new AtomicLong();
        final AtomicInteger wip = new AtomicInteger();

        public FlattenIterableSubscriber(Subscriber<? super R> actual2, Func1<? super T, ? extends Iterable<? extends R>> mapper2, int prefetch) {
            this.actual = actual2;
            this.mapper = mapper2;
            if (prefetch == Integer.MAX_VALUE) {
                this.limit = Clock.MAX_TIME;
                this.queue = new SpscLinkedArrayQueue(RxRingBuffer.SIZE);
            } else {
                this.limit = (long) (prefetch - (prefetch >> 2));
                if (UnsafeAccess.isUnsafeAvailable()) {
                    this.queue = new SpscArrayQueue(prefetch);
                } else {
                    this.queue = new SpscAtomicArrayQueue(prefetch);
                }
            }
            request((long) prefetch);
        }

        public void onNext(T t) {
            if (!this.queue.offer(this.nl.next(t))) {
                unsubscribe();
                onError(new MissingBackpressureException());
                return;
            }
            drain();
        }

        public void onError(Throwable e) {
            if (ExceptionsUtils.addThrowable(this.error, e)) {
                this.done = true;
                drain();
                return;
            }
            RxJavaPluginUtils.handleException(e);
        }

        public void onCompleted() {
            this.done = true;
            drain();
        }

        /* access modifiers changed from: package-private */
        public void requestMore(long n) {
            if (n > 0) {
                BackpressureUtils.getAndAddRequest(this.requested, n);
                drain();
            } else if (n < 0) {
                throw new IllegalStateException("n >= 0 required but it was " + n);
            }
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            if (this.wip.getAndIncrement() == 0) {
                Subscriber<? super R> actual2 = this.actual;
                Queue<Object> queue2 = this.queue;
                int missed = 1;
                while (true) {
                    Iterator<? extends R> it = this.active;
                    if (it == null) {
                        boolean d = this.done;
                        Object v = queue2.poll();
                        boolean empty = v == null;
                        if (checkTerminated(d, empty, actual2, queue2)) {
                            return;
                        }
                        if (!empty) {
                            long p = this.produced + 1;
                            if (p == this.limit) {
                                this.produced = 0;
                                request(p);
                            } else {
                                this.produced = p;
                            }
                            try {
                                it = ((Iterable) this.mapper.call(this.nl.getValue(v))).iterator();
                                if (it.hasNext()) {
                                    this.active = it;
                                } else {
                                    continue;
                                }
                            } catch (Throwable ex) {
                                Exceptions.throwIfFatal(ex);
                                onError(ex);
                            }
                        }
                    }
                    if (it != null) {
                        long r = this.requested.get();
                        long e = 0;
                        while (true) {
                            if (e == r) {
                                break;
                            }
                            if (!checkTerminated(this.done, false, actual2, queue2)) {
                                try {
                                    actual2.onNext(it.next());
                                    if (!checkTerminated(this.done, false, actual2, queue2)) {
                                        e++;
                                        try {
                                            if (!it.hasNext()) {
                                                it = null;
                                                this.active = null;
                                                break;
                                            }
                                        } catch (Throwable ex2) {
                                            Exceptions.throwIfFatal(ex2);
                                            it = null;
                                            this.active = null;
                                            onError(ex2);
                                        }
                                    } else {
                                        return;
                                    }
                                } catch (Throwable ex3) {
                                    Exceptions.throwIfFatal(ex3);
                                    it = null;
                                    this.active = null;
                                    onError(ex3);
                                }
                            } else {
                                return;
                            }
                        }
                        if (e == r) {
                            if (checkTerminated(this.done, queue2.isEmpty() && it == null, actual2, queue2)) {
                                return;
                            }
                        }
                        if (e != 0) {
                            BackpressureUtils.produced(this.requested, e);
                        }
                        if (it == null) {
                            continue;
                        }
                    }
                    missed = this.wip.addAndGet(-missed);
                    if (missed == 0) {
                        return;
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public boolean checkTerminated(boolean d, boolean empty, Subscriber<?> a, Queue<?> q) {
            if (a.isUnsubscribed()) {
                q.clear();
                this.active = null;
                return true;
            }
            if (d) {
                if (this.error.get() != null) {
                    Throwable ex = ExceptionsUtils.terminate(this.error);
                    unsubscribe();
                    q.clear();
                    this.active = null;
                    a.onError(ex);
                    return true;
                } else if (empty) {
                    a.onCompleted();
                    return true;
                }
            }
            return false;
        }
    }

    static final class OnSubscribeScalarFlattenIterable<T, R> implements Observable.OnSubscribe<R> {
        final Func1<? super T, ? extends Iterable<? extends R>> mapper;
        final T value;

        public OnSubscribeScalarFlattenIterable(T value2, Func1<? super T, ? extends Iterable<? extends R>> mapper2) {
            this.value = value2;
            this.mapper = mapper2;
        }

        public void call(Subscriber<? super R> t) {
            try {
                Iterator<? extends R> itor = ((Iterable) this.mapper.call(this.value)).iterator();
                if (!itor.hasNext()) {
                    t.onCompleted();
                } else {
                    t.setProducer(new OnSubscribeFromIterable.IterableProducer(t, itor));
                }
            } catch (Throwable ex) {
                Exceptions.throwOrReport(ex, t, this.value);
            }
        }
    }
}

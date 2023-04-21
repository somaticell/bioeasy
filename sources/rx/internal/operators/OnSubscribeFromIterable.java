package rx.internal.operators;

import com.facebook.common.time.Clock;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import rx.Observable;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;

public final class OnSubscribeFromIterable<T> implements Observable.OnSubscribe<T> {
    final Iterable<? extends T> is;

    public OnSubscribeFromIterable(Iterable<? extends T> iterable) {
        if (iterable == null) {
            throw new NullPointerException("iterable must not be null");
        }
        this.is = iterable;
    }

    public void call(Subscriber<? super T> o) {
        try {
            Iterator<? extends T> it = this.is.iterator();
            boolean b = it.hasNext();
            if (o.isUnsubscribed()) {
                return;
            }
            if (!b) {
                o.onCompleted();
            } else {
                o.setProducer(new IterableProducer(o, it));
            }
        } catch (Throwable ex) {
            Exceptions.throwOrReport(ex, (Observer<?>) o);
        }
    }

    static final class IterableProducer<T> extends AtomicLong implements Producer {
        private static final long serialVersionUID = -8730475647105475802L;
        private final Iterator<? extends T> it;
        private final Subscriber<? super T> o;

        IterableProducer(Subscriber<? super T> o2, Iterator<? extends T> it2) {
            this.o = o2;
            this.it = it2;
        }

        public void request(long n) {
            if (get() != Clock.MAX_TIME) {
                if (n == Clock.MAX_TIME && compareAndSet(0, Clock.MAX_TIME)) {
                    fastpath();
                } else if (n > 0 && BackpressureUtils.getAndAddRequest(this, n) == 0) {
                    slowpath(n);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void slowpath(long n) {
            Subscriber<? super T> o2 = this.o;
            Iterator<? extends T> it2 = this.it;
            long r = n;
            long e = 0;
            while (true) {
                if (e == r) {
                    r = get();
                    if (e == r) {
                        r = BackpressureUtils.produced(this, e);
                        if (r != 0) {
                            e = 0;
                        } else {
                            return;
                        }
                    } else {
                        continue;
                    }
                } else if (!o2.isUnsubscribed()) {
                    try {
                        o2.onNext(it2.next());
                        if (!o2.isUnsubscribed()) {
                            try {
                                if (it2.hasNext()) {
                                    e++;
                                } else if (!o2.isUnsubscribed()) {
                                    o2.onCompleted();
                                    return;
                                } else {
                                    return;
                                }
                            } catch (Throwable ex) {
                                Exceptions.throwOrReport(ex, (Observer<?>) o2);
                                return;
                            }
                        } else {
                            return;
                        }
                    } catch (Throwable ex2) {
                        Exceptions.throwOrReport(ex2, (Observer<?>) o2);
                        return;
                    }
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void fastpath() {
            Subscriber<? super T> o2 = this.o;
            Iterator<? extends T> it2 = this.it;
            while (!o2.isUnsubscribed()) {
                try {
                    o2.onNext(it2.next());
                    if (!o2.isUnsubscribed()) {
                        try {
                            if (!it2.hasNext()) {
                                if (!o2.isUnsubscribed()) {
                                    o2.onCompleted();
                                    return;
                                }
                                return;
                            }
                        } catch (Throwable ex) {
                            Exceptions.throwOrReport(ex, (Observer<?>) o2);
                            return;
                        }
                    } else {
                        return;
                    }
                } catch (Throwable ex2) {
                    Exceptions.throwOrReport(ex2, (Observer<?>) o2);
                    return;
                }
            }
        }
    }
}

package rx.internal.operators;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.internal.util.RxJavaPluginUtils;

public final class OnSubscribeDetach<T> implements Observable.OnSubscribe<T> {
    final Observable<T> source;

    public OnSubscribeDetach(Observable<T> source2) {
        this.source = source2;
    }

    public void call(Subscriber<? super T> t) {
        DetachSubscriber<T> parent = new DetachSubscriber<>(t);
        DetachProducer<T> producer = new DetachProducer<>(parent);
        t.add(producer);
        t.setProducer(producer);
        this.source.unsafeSubscribe(parent);
    }

    static final class DetachSubscriber<T> extends Subscriber<T> {
        final AtomicReference<Subscriber<? super T>> actual;
        final AtomicReference<Producer> producer = new AtomicReference<>();
        final AtomicLong requested = new AtomicLong();

        public DetachSubscriber(Subscriber<? super T> actual2) {
            this.actual = new AtomicReference<>(actual2);
        }

        public void onNext(T t) {
            Subscriber<? super T> a = this.actual.get();
            if (a != null) {
                a.onNext(t);
            }
        }

        public void onError(Throwable e) {
            this.producer.lazySet(TerminatedProducer.INSTANCE);
            Subscriber<? super T> a = this.actual.getAndSet((Object) null);
            if (a != null) {
                a.onError(e);
            } else {
                RxJavaPluginUtils.handleException(e);
            }
        }

        public void onCompleted() {
            this.producer.lazySet(TerminatedProducer.INSTANCE);
            Subscriber<? super T> a = this.actual.getAndSet((Object) null);
            if (a != null) {
                a.onCompleted();
            }
        }

        /* access modifiers changed from: package-private */
        public void innerRequest(long n) {
            if (n < 0) {
                throw new IllegalArgumentException("n >= 0 required but it was " + n);
            }
            Producer p = this.producer.get();
            if (p != null) {
                p.request(n);
                return;
            }
            BackpressureUtils.getAndAddRequest(this.requested, n);
            Producer p2 = this.producer.get();
            if (p2 != null && p2 != TerminatedProducer.INSTANCE) {
                p2.request(this.requested.getAndSet(0));
            }
        }

        public void setProducer(Producer p) {
            if (this.producer.compareAndSet((Object) null, p)) {
                p.request(this.requested.getAndSet(0));
            } else if (this.producer.get() != TerminatedProducer.INSTANCE) {
                throw new IllegalStateException("Producer already set!");
            }
        }

        /* access modifiers changed from: package-private */
        public void innerUnsubscribe() {
            this.producer.lazySet(TerminatedProducer.INSTANCE);
            this.actual.lazySet((Object) null);
            unsubscribe();
        }
    }

    static final class DetachProducer<T> implements Producer, Subscription {
        final DetachSubscriber<T> parent;

        public DetachProducer(DetachSubscriber<T> parent2) {
            this.parent = parent2;
        }

        public void request(long n) {
            this.parent.innerRequest(n);
        }

        public boolean isUnsubscribed() {
            return this.parent.isUnsubscribed();
        }

        public void unsubscribe() {
            this.parent.innerUnsubscribe();
        }
    }

    enum TerminatedProducer implements Producer {
        INSTANCE;

        public void request(long n) {
        }
    }
}

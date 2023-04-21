package rx.internal.operators;

import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.SerialSubscription;

public final class SingleOnSubscribeDelaySubscriptionOther<T> implements Single.OnSubscribe<T> {
    final Single<? extends T> main;
    final Observable<?> other;

    public SingleOnSubscribeDelaySubscriptionOther(Single<? extends T> main2, Observable<?> other2) {
        this.main = main2;
        this.other = other2;
    }

    public void call(final SingleSubscriber<? super T> subscriber) {
        final SingleSubscriber<T> child = new SingleSubscriber<T>() {
            public void onSuccess(T value) {
                subscriber.onSuccess(value);
            }

            public void onError(Throwable error) {
                subscriber.onError(error);
            }
        };
        final SerialSubscription serial = new SerialSubscription();
        subscriber.add(serial);
        Subscriber<Object> otherSubscriber = new Subscriber<Object>() {
            boolean done;

            public void onNext(Object t) {
                onCompleted();
            }

            public void onError(Throwable e) {
                if (this.done) {
                    RxJavaPlugins.getInstance().getErrorHandler().handleError(e);
                    return;
                }
                this.done = true;
                child.onError(e);
            }

            public void onCompleted() {
                if (!this.done) {
                    this.done = true;
                    serial.set(child);
                    SingleOnSubscribeDelaySubscriptionOther.this.main.subscribe(child);
                }
            }
        };
        serial.set(otherSubscriber);
        this.other.subscribe(otherSubscriber);
    }
}

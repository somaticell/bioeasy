package rx.internal.operators;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func0;
import rx.observers.Subscribers;

public final class OnSubscribeDelaySubscriptionWithSelector<T, U> implements Observable.OnSubscribe<T> {
    final Observable<? extends T> source;
    final Func0<? extends Observable<U>> subscriptionDelay;

    public OnSubscribeDelaySubscriptionWithSelector(Observable<? extends T> source2, Func0<? extends Observable<U>> subscriptionDelay2) {
        this.source = source2;
        this.subscriptionDelay = subscriptionDelay2;
    }

    public void call(final Subscriber<? super T> child) {
        try {
            ((Observable) this.subscriptionDelay.call()).take(1).unsafeSubscribe(new Subscriber<U>() {
                public void onCompleted() {
                    OnSubscribeDelaySubscriptionWithSelector.this.source.unsafeSubscribe(Subscribers.wrap(child));
                }

                public void onError(Throwable e) {
                    child.onError(e);
                }

                public void onNext(U u) {
                }
            });
        } catch (Throwable e) {
            Exceptions.throwOrReport(e, (Observer<?>) child);
        }
    }
}

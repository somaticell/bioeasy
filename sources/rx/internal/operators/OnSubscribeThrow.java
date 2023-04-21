package rx.internal.operators;

import rx.Observable;
import rx.Subscriber;

public final class OnSubscribeThrow<T> implements Observable.OnSubscribe<T> {
    private final Throwable exception;

    public OnSubscribeThrow(Throwable exception2) {
        this.exception = exception2;
    }

    public void call(Subscriber<? super T> observer) {
        observer.onError(this.exception);
    }
}

package rx.internal.operators;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func0;
import rx.observers.Subscribers;

public final class OnSubscribeDefer<T> implements Observable.OnSubscribe<T> {
    final Func0<? extends Observable<? extends T>> observableFactory;

    public OnSubscribeDefer(Func0<? extends Observable<? extends T>> observableFactory2) {
        this.observableFactory = observableFactory2;
    }

    public void call(Subscriber<? super T> s) {
        try {
            ((Observable) this.observableFactory.call()).unsafeSubscribe(Subscribers.wrap(s));
        } catch (Throwable t) {
            Exceptions.throwOrReport(t, (Observer<?>) s);
        }
    }
}

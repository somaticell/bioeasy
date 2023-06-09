package rx.plugins;

import rx.Observable;
import rx.Single;
import rx.Subscription;

public abstract class RxJavaSingleExecutionHook {
    public <T> Single.OnSubscribe<T> onCreate(Single.OnSubscribe<T> f) {
        return f;
    }

    public <T> Observable.OnSubscribe<T> onSubscribeStart(Single<? extends T> single, Observable.OnSubscribe<T> onSubscribe) {
        return onSubscribe;
    }

    public <T> Subscription onSubscribeReturn(Subscription subscription) {
        return subscription;
    }

    public <T> Throwable onSubscribeError(Throwable e) {
        return e;
    }

    public <T, R> Observable.Operator<? extends R, ? super T> onLift(Observable.Operator<? extends R, ? super T> lift) {
        return lift;
    }
}

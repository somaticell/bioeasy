package rx.internal.operators;

import java.util.concurrent.atomic.AtomicBoolean;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.observers.Subscribers;

public final class OnSubscribeUsing<T, Resource> implements Observable.OnSubscribe<T> {
    private final Action1<? super Resource> dispose;
    private final boolean disposeEagerly;
    private final Func1<? super Resource, ? extends Observable<? extends T>> observableFactory;
    private final Func0<Resource> resourceFactory;

    public OnSubscribeUsing(Func0<Resource> resourceFactory2, Func1<? super Resource, ? extends Observable<? extends T>> observableFactory2, Action1<? super Resource> dispose2, boolean disposeEagerly2) {
        this.resourceFactory = resourceFactory2;
        this.observableFactory = observableFactory2;
        this.dispose = dispose2;
        this.disposeEagerly = disposeEagerly2;
    }

    public void call(Subscriber<? super T> subscriber) {
        DisposeAction<Resource> disposeOnceOnly;
        Observable<? extends T> observable;
        try {
            Resource resource = this.resourceFactory.call();
            disposeOnceOnly = new DisposeAction<>(this.dispose, resource);
            subscriber.add(disposeOnceOnly);
            Observable<? extends T> source = (Observable) this.observableFactory.call(resource);
            if (this.disposeEagerly) {
                observable = source.doOnTerminate(disposeOnceOnly);
            } else {
                observable = source;
            }
            observable.unsafeSubscribe(Subscribers.wrap(subscriber));
        } catch (Throwable e) {
            Exceptions.throwOrReport(e, (Observer<?>) subscriber);
        }
    }

    private Throwable disposeEagerlyIfRequested(Action0 disposeOnceOnly) {
        if (!this.disposeEagerly) {
            return null;
        }
        try {
            disposeOnceOnly.call();
            return null;
        } catch (Throwable th) {
            return th;
        }
    }

    private static final class DisposeAction<Resource> extends AtomicBoolean implements Action0, Subscription {
        private static final long serialVersionUID = 4262875056400218316L;
        private Action1<? super Resource> dispose;
        private Resource resource;

        DisposeAction(Action1<? super Resource> dispose2, Resource resource2) {
            this.dispose = dispose2;
            this.resource = resource2;
            lazySet(false);
        }

        public void call() {
            if (compareAndSet(false, true)) {
                try {
                    this.dispose.call(this.resource);
                } finally {
                    this.resource = null;
                    this.dispose = null;
                }
            }
        }

        public boolean isUnsubscribed() {
            return get();
        }

        public void unsubscribe() {
            call();
        }
    }
}

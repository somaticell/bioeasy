package rx.internal.operators;

import java.util.Arrays;
import java.util.Collection;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.exceptions.CompositeException;
import rx.exceptions.Exceptions;

public class OperatorDoOnEach<T> implements Observable.Operator<T, T> {
    final Observer<? super T> doOnEachObserver;

    public OperatorDoOnEach(Observer<? super T> doOnEachObserver2) {
        this.doOnEachObserver = doOnEachObserver2;
    }

    public Subscriber<? super T> call(final Subscriber<? super T> observer) {
        return new Subscriber<T>(observer) {
            private boolean done = false;

            public void onCompleted() {
                if (!this.done) {
                    try {
                        OperatorDoOnEach.this.doOnEachObserver.onCompleted();
                        this.done = true;
                        observer.onCompleted();
                    } catch (Throwable e) {
                        Exceptions.throwOrReport(e, (Observer<?>) this);
                    }
                }
            }

            public void onError(Throwable e) {
                Exceptions.throwIfFatal(e);
                if (!this.done) {
                    this.done = true;
                    try {
                        OperatorDoOnEach.this.doOnEachObserver.onError(e);
                        observer.onError(e);
                    } catch (Throwable e2) {
                        Exceptions.throwIfFatal(e2);
                        observer.onError(new CompositeException((Collection<? extends Throwable>) Arrays.asList(new Throwable[]{e, e2})));
                    }
                }
            }

            public void onNext(T value) {
                if (!this.done) {
                    try {
                        OperatorDoOnEach.this.doOnEachObserver.onNext(value);
                        observer.onNext(value);
                    } catch (Throwable e) {
                        Exceptions.throwOrReport(e, this, value);
                    }
                }
            }
        };
    }
}

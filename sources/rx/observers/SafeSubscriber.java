package rx.observers;

import java.util.Arrays;
import java.util.Collection;
import rx.Observer;
import rx.Subscriber;
import rx.exceptions.CompositeException;
import rx.exceptions.Exceptions;
import rx.exceptions.OnErrorFailedException;
import rx.exceptions.UnsubscribeFailedException;
import rx.internal.util.RxJavaPluginUtils;

public class SafeSubscriber<T> extends Subscriber<T> {
    private final Subscriber<? super T> actual;
    boolean done = false;

    public SafeSubscriber(Subscriber<? super T> actual2) {
        super(actual2);
        this.actual = actual2;
    }

    public void onCompleted() {
        if (!this.done) {
            this.done = true;
            try {
                this.actual.onCompleted();
                try {
                    unsubscribe();
                } catch (Throwable e) {
                    RxJavaPluginUtils.handleException(e);
                    throw new UnsubscribeFailedException(e.getMessage(), e);
                }
            } catch (Throwable th) {
                try {
                    unsubscribe();
                    throw th;
                } catch (Throwable e2) {
                    RxJavaPluginUtils.handleException(e2);
                    throw new UnsubscribeFailedException(e2.getMessage(), e2);
                }
            }
        }
    }

    public void onError(Throwable e) {
        Exceptions.throwIfFatal(e);
        if (!this.done) {
            this.done = true;
            _onError(e);
        }
    }

    public void onNext(T args) {
        try {
            if (!this.done) {
                this.actual.onNext(args);
            }
        } catch (Throwable e) {
            Exceptions.throwOrReport(e, (Observer<?>) this);
        }
    }

    /* access modifiers changed from: protected */
    public void _onError(Throwable e) {
        RxJavaPluginUtils.handleException(e);
        try {
            this.actual.onError(e);
            try {
                unsubscribe();
            } catch (RuntimeException unsubscribeException) {
                RxJavaPluginUtils.handleException(unsubscribeException);
                throw new OnErrorFailedException(unsubscribeException);
            }
        } catch (Throwable unsubscribeException2) {
            RxJavaPluginUtils.handleException(unsubscribeException2);
            throw new OnErrorFailedException("Error occurred when trying to propagate error to Observer.onError and during unsubscription.", new CompositeException((Collection<? extends Throwable>) Arrays.asList(new Throwable[]{e, e2, unsubscribeException2})));
        }
    }

    public Subscriber<? super T> getActual() {
        return this.actual;
    }
}

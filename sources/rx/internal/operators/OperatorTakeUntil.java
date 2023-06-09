package rx.internal.operators;

import com.facebook.common.time.Clock;
import rx.Observable;
import rx.Subscriber;
import rx.observers.SerializedSubscriber;

public final class OperatorTakeUntil<T, E> implements Observable.Operator<T, T> {
    private final Observable<? extends E> other;

    public OperatorTakeUntil(Observable<? extends E> other2) {
        this.other = other2;
    }

    public Subscriber<? super T> call(Subscriber<? super T> child) {
        final Subscriber<T> serial = new SerializedSubscriber<>(child, false);
        final Subscriber<T> main = new Subscriber<T>(false, serial) {
            public void onNext(T t) {
                serial.onNext(t);
            }

            public void onError(Throwable e) {
                try {
                    serial.onError(e);
                } finally {
                    serial.unsubscribe();
                }
            }

            public void onCompleted() {
                try {
                    serial.onCompleted();
                } finally {
                    serial.unsubscribe();
                }
            }
        };
        Subscriber<E> so = new Subscriber<E>() {
            public void onStart() {
                request(Clock.MAX_TIME);
            }

            public void onCompleted() {
                main.onCompleted();
            }

            public void onError(Throwable e) {
                main.onError(e);
            }

            public void onNext(E e) {
                onCompleted();
            }
        };
        serial.add(main);
        serial.add(so);
        child.add(serial);
        this.other.unsafeSubscribe(so);
        return main;
    }
}

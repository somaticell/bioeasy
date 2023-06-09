package rx.internal.operators;

import com.facebook.common.time.Clock;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.internal.operators.OperatorDebounceWithTime;
import rx.observers.SerializedSubscriber;
import rx.subscriptions.SerialSubscription;

public final class OperatorDebounceWithSelector<T, U> implements Observable.Operator<T, T> {
    final Func1<? super T, ? extends Observable<U>> selector;

    public OperatorDebounceWithSelector(Func1<? super T, ? extends Observable<U>> selector2) {
        this.selector = selector2;
    }

    public Subscriber<? super T> call(Subscriber<? super T> child) {
        final SerializedSubscriber<T> s = new SerializedSubscriber<>(child);
        final SerialSubscription ssub = new SerialSubscription();
        child.add(ssub);
        return new Subscriber<T>(child) {
            final Subscriber<?> self = this;
            final OperatorDebounceWithTime.DebounceState<T> state = new OperatorDebounceWithTime.DebounceState<>();

            public void onStart() {
                request(Clock.MAX_TIME);
            }

            public void onNext(T t) {
                try {
                    Observable<U> debouncer = (Observable) OperatorDebounceWithSelector.this.selector.call(t);
                    final int index = this.state.next(t);
                    Subscriber<U> debounceSubscriber = new Subscriber<U>() {
                        public void onNext(U u) {
                            onCompleted();
                        }

                        public void onError(Throwable e) {
                            AnonymousClass1.this.self.onError(e);
                        }

                        public void onCompleted() {
                            AnonymousClass1.this.state.emit(index, s, AnonymousClass1.this.self);
                            unsubscribe();
                        }
                    };
                    ssub.set(debounceSubscriber);
                    debouncer.unsafeSubscribe(debounceSubscriber);
                } catch (Throwable e) {
                    Exceptions.throwOrReport(e, (Observer<?>) this);
                }
            }

            public void onError(Throwable e) {
                s.onError(e);
                unsubscribe();
                this.state.clear();
            }

            public void onCompleted() {
                this.state.emitAndComplete(s, this);
            }
        };
    }
}

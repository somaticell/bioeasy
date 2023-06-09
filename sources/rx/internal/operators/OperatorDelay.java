package rx.internal.operators;

import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;

public final class OperatorDelay<T> implements Observable.Operator<T, T> {
    final long delay;
    final Scheduler scheduler;
    final TimeUnit unit;

    public OperatorDelay(long delay2, TimeUnit unit2, Scheduler scheduler2) {
        this.delay = delay2;
        this.unit = unit2;
        this.scheduler = scheduler2;
    }

    public Subscriber<? super T> call(final Subscriber<? super T> child) {
        final Scheduler.Worker worker = this.scheduler.createWorker();
        child.add(worker);
        return new Subscriber<T>(child) {
            boolean done;

            public void onCompleted() {
                worker.schedule(new Action0() {
                    public void call() {
                        if (!AnonymousClass1.this.done) {
                            AnonymousClass1.this.done = true;
                            child.onCompleted();
                        }
                    }
                }, OperatorDelay.this.delay, OperatorDelay.this.unit);
            }

            public void onError(final Throwable e) {
                worker.schedule(new Action0() {
                    public void call() {
                        if (!AnonymousClass1.this.done) {
                            AnonymousClass1.this.done = true;
                            child.onError(e);
                            worker.unsubscribe();
                        }
                    }
                });
            }

            public void onNext(final T t) {
                worker.schedule(new Action0() {
                    public void call() {
                        if (!AnonymousClass1.this.done) {
                            child.onNext(t);
                        }
                    }
                }, OperatorDelay.this.delay, OperatorDelay.this.unit);
            }
        };
    }
}

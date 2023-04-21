package rx.internal.operators;

import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Action0;

public final class OnSubscribeTimerOnce implements Observable.OnSubscribe<Long> {
    final Scheduler scheduler;
    final long time;
    final TimeUnit unit;

    public OnSubscribeTimerOnce(long time2, TimeUnit unit2, Scheduler scheduler2) {
        this.time = time2;
        this.unit = unit2;
        this.scheduler = scheduler2;
    }

    public void call(final Subscriber<? super Long> child) {
        Scheduler.Worker worker = this.scheduler.createWorker();
        child.add(worker);
        worker.schedule(new Action0() {
            public void call() {
                try {
                    child.onNext(0L);
                    child.onCompleted();
                } catch (Throwable t) {
                    Exceptions.throwOrReport(t, (Observer<?>) child);
                }
            }
        }, this.time, this.unit);
    }
}

package rx.internal.operators;

import com.facebook.common.time.Clock;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.observers.SerializedSubscriber;

public final class OperatorSampleWithTime<T> implements Observable.Operator<T, T> {
    final Scheduler scheduler;
    final long time;
    final TimeUnit unit;

    public OperatorSampleWithTime(long time2, TimeUnit unit2, Scheduler scheduler2) {
        this.time = time2;
        this.unit = unit2;
        this.scheduler = scheduler2;
    }

    public Subscriber<? super T> call(Subscriber<? super T> child) {
        SerializedSubscriber<T> s = new SerializedSubscriber<>(child);
        Scheduler.Worker worker = this.scheduler.createWorker();
        child.add(worker);
        SamplerSubscriber<T> sampler = new SamplerSubscriber<>(s);
        child.add(sampler);
        worker.schedulePeriodically(sampler, this.time, this.time, this.unit);
        return sampler;
    }

    static final class SamplerSubscriber<T> extends Subscriber<T> implements Action0 {
        private static final Object EMPTY_TOKEN = new Object();
        private final Subscriber<? super T> subscriber;
        final AtomicReference<Object> value = new AtomicReference<>(EMPTY_TOKEN);

        public SamplerSubscriber(Subscriber<? super T> subscriber2) {
            this.subscriber = subscriber2;
        }

        public void onStart() {
            request(Clock.MAX_TIME);
        }

        public void onNext(T t) {
            this.value.set(t);
        }

        public void onError(Throwable e) {
            this.subscriber.onError(e);
            unsubscribe();
        }

        public void onCompleted() {
            emitIfNonEmpty();
            this.subscriber.onCompleted();
            unsubscribe();
        }

        public void call() {
            emitIfNonEmpty();
        }

        private void emitIfNonEmpty() {
            T localValue = this.value.getAndSet(EMPTY_TOKEN);
            if (localValue != EMPTY_TOKEN) {
                try {
                    this.subscriber.onNext(localValue);
                } catch (Throwable e) {
                    Exceptions.throwOrReport(e, (Observer<?>) this);
                }
            }
        }
    }
}

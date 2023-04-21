package retrofit2.adapter.rxjava;

import java.lang.reflect.Type;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import rx.Completable;
import rx.Scheduler;
import rx.Subscription;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

final class CompletableHelper {
    CompletableHelper() {
    }

    static CallAdapter<Completable> createCallAdapter(Scheduler scheduler) {
        return new CompletableCallAdapter(scheduler);
    }

    private static final class CompletableCallOnSubscribe implements Completable.CompletableOnSubscribe {
        private final Call originalCall;

        CompletableCallOnSubscribe(Call originalCall2) {
            this.originalCall = originalCall2;
        }

        public void call(Completable.CompletableSubscriber subscriber) {
            final Call call = this.originalCall.clone();
            Subscription subscription = Subscriptions.create(new Action0() {
                public void call() {
                    call.cancel();
                }
            });
            subscriber.onSubscribe(subscription);
            try {
                Response response = call.execute();
                if (subscription.isUnsubscribed()) {
                    return;
                }
                if (response.isSuccessful()) {
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new HttpException(response));
                }
            } catch (Throwable t) {
                Exceptions.throwIfFatal(t);
                if (!subscription.isUnsubscribed()) {
                    subscriber.onError(t);
                }
            }
        }
    }

    static class CompletableCallAdapter implements CallAdapter<Completable> {
        private final Scheduler scheduler;

        CompletableCallAdapter(Scheduler scheduler2) {
            this.scheduler = scheduler2;
        }

        public Type responseType() {
            return Void.class;
        }

        public Completable adapt(Call call) {
            Completable completable = Completable.create(new CompletableCallOnSubscribe(call));
            if (this.scheduler != null) {
                return completable.subscribeOn(this.scheduler);
            }
            return completable;
        }
    }
}

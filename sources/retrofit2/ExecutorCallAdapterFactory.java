package retrofit2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;
import okhttp3.Request;
import retrofit2.CallAdapter;

final class ExecutorCallAdapterFactory extends CallAdapter.Factory {
    final Executor callbackExecutor;

    ExecutorCallAdapterFactory(Executor callbackExecutor2) {
        this.callbackExecutor = callbackExecutor2;
    }

    public CallAdapter<Call<?>> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != Call.class) {
            return null;
        }
        final Type responseType = Utils.getCallResponseType(returnType);
        return new CallAdapter<Call<?>>() {
            public Type responseType() {
                return responseType;
            }

            public <R> Call<R> adapt(Call<R> call) {
                return new ExecutorCallbackCall(ExecutorCallAdapterFactory.this.callbackExecutor, call);
            }
        };
    }

    static final class ExecutorCallbackCall<T> implements Call<T> {
        final Executor callbackExecutor;
        final Call<T> delegate;

        ExecutorCallbackCall(Executor callbackExecutor2, Call<T> delegate2) {
            this.callbackExecutor = callbackExecutor2;
            this.delegate = delegate2;
        }

        public void enqueue(final Callback<T> callback) {
            if (callback == null) {
                throw new NullPointerException("callback == null");
            }
            this.delegate.enqueue(new Callback<T>() {
                public void onResponse(final Call<T> call, final Response<T> response) {
                    ExecutorCallbackCall.this.callbackExecutor.execute(new Runnable() {
                        public void run() {
                            if (ExecutorCallbackCall.this.delegate.isCanceled()) {
                                callback.onFailure(call, new IOException("Canceled"));
                            } else {
                                callback.onResponse(call, response);
                            }
                        }
                    });
                }

                public void onFailure(final Call<T> call, final Throwable t) {
                    ExecutorCallbackCall.this.callbackExecutor.execute(new Runnable() {
                        public void run() {
                            callback.onFailure(call, t);
                        }
                    });
                }
            });
        }

        public boolean isExecuted() {
            return this.delegate.isExecuted();
        }

        public Response<T> execute() throws IOException {
            return this.delegate.execute();
        }

        public void cancel() {
            this.delegate.cancel();
        }

        public boolean isCanceled() {
            return this.delegate.isCanceled();
        }

        public Call<T> clone() {
            return new ExecutorCallbackCall(this.callbackExecutor, this.delegate.clone());
        }

        public Request request() {
            return this.delegate.request();
        }
    }
}

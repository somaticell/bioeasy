package cn.com.bioeasy.app.exception;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;

public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {
    private final RxJavaCallAdapterFactory original = RxJavaCallAdapterFactory.create();

    private RxErrorHandlingCallAdapterFactory() {
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(retrofit, this.original.get(returnType, annotations, retrofit));
    }

    private static class RxCallAdapterWrapper implements CallAdapter<Observable<?>> {
        private final Retrofit retrofit;
        private final CallAdapter<?> wrapped;

        public RxCallAdapterWrapper(Retrofit retrofit3, CallAdapter<?> wrapped2) {
            this.retrofit = retrofit3;
            this.wrapped = wrapped2;
        }

        public Type responseType() {
            return this.wrapped.responseType();
        }

        public <R> Observable<?> adapt(Call<R> call) {
            return ((Observable) this.wrapped.adapt(call)).onErrorResumeNext(new Func1<Throwable, Observable>() {
                public Observable call(Throwable throwable) {
                    return Observable.error(RxCallAdapterWrapper.this.asRetrofitException(throwable));
                }
            });
        }

        /* access modifiers changed from: private */
        public RetrofitException asRetrofitException(Throwable throwable) {
            if (throwable instanceof HttpException) {
                Response response = ((HttpException) throwable).response();
                return RetrofitException.httpError(response.raw().request().url().toString(), response, this.retrofit);
            } else if (throwable instanceof IOException) {
                return RetrofitException.networkError((IOException) throwable);
            } else {
                return RetrofitException.unexpectedError(throwable);
            }
        }
    }
}

package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import retrofit2.CallAdapter;

final class DefaultCallAdapterFactory extends CallAdapter.Factory {
    static final CallAdapter.Factory INSTANCE = new DefaultCallAdapterFactory();

    DefaultCallAdapterFactory() {
    }

    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != Call.class) {
            return null;
        }
        final Type responseType = Utils.getCallResponseType(returnType);
        return new CallAdapter<Call<?>>() {
            public Type responseType() {
                return responseType;
            }

            public <R> Call<R> adapt(Call<R> call) {
                return call;
            }
        };
    }
}

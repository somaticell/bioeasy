package cn.com.bioeasy.healty.app.healthapp.Interceptor;

import com.orhanobut.logger.Logger;
import java.io.IOException;
import javax.inject.Singleton;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class LoggingInterceptor implements Interceptor {
    private static final String TAG = "network request   ";

    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request().newBuilder().addHeader("Content-Type", "application/json; charset=UTF-8").addHeader("Accept-Encoding", "UTF-8").build();
        long t1 = System.nanoTime();
        Logger.e(TAG + String.format("Sending request %s", new Object[]{request.url()}), new Object[0]);
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        Logger.e(TAG + String.format("Received response for %s  body %s", new Object[]{response.request().url(), Double.valueOf(((double) (t2 - t1)) / 1000000.0d), response.headers()}), new Object[0]);
        return response;
    }
}

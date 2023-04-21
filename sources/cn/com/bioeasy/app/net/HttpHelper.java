package cn.com.bioeasy.app.net;

import android.content.Context;
import android.util.Log;
import cn.com.bioeasy.app.common.ACache;
import cn.com.bioeasy.app.common.AppUtils;
import cn.com.bioeasy.app.scope.ActivityContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHelper {
    private static final int DEFAULT_TIMEOUT = 10;
    /* access modifiers changed from: private */
    public Context mContext;
    private HashMap<String, Object> mServiceMap = new HashMap<>();

    @Inject
    public HttpHelper(@ActivityContext Context context) {
        this.mContext = context;
    }

    public <S> S getService(Class<S> serviceClass) {
        if (this.mServiceMap.containsKey(serviceClass.getName())) {
            return this.mServiceMap.get(serviceClass.getName());
        }
        Object obj = createService(serviceClass);
        this.mServiceMap.put(serviceClass.getName(), obj);
        return obj;
    }

    public <S> S getService(Class<S> serviceClass, OkHttpClient client) {
        if (this.mServiceMap.containsKey(serviceClass.getName())) {
            return this.mServiceMap.get(serviceClass.getName());
        }
        Object obj = createService(serviceClass, client);
        this.mServiceMap.put(serviceClass.getName(), obj);
        return obj;
    }

    private <S> S createService(Class<S> serviceClass) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(10, TimeUnit.SECONDS);
        httpClient.writeTimeout(10, TimeUnit.SECONDS);
        httpClient.readTimeout(10, TimeUnit.SECONDS);
        httpClient.addNetworkInterceptor(new LogInterceptor());
        httpClient.addInterceptor(new CacheControlInterceptor());
        return createService(serviceClass, httpClient.build());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <S> S createService(java.lang.Class<S> r8, okhttp3.OkHttpClient r9) {
        /*
            r7 = this;
            java.lang.String r2 = ""
            java.lang.String r5 = "end_point"
            java.lang.reflect.Field r3 = r8.getField(r5)     // Catch:{ NoSuchFieldException -> 0x0036, IllegalAccessException -> 0x003b }
            java.lang.Object r5 = r3.get(r8)     // Catch:{ NoSuchFieldException -> 0x0036, IllegalAccessException -> 0x003b }
            r0 = r5
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ NoSuchFieldException -> 0x0036, IllegalAccessException -> 0x003b }
            r2 = r0
        L_0x0010:
            retrofit2.Retrofit$Builder r5 = new retrofit2.Retrofit$Builder
            r5.<init>()
            retrofit2.Retrofit$Builder r5 = r5.baseUrl((java.lang.String) r2)
            retrofit2.converter.gson.GsonConverterFactory r6 = retrofit2.converter.gson.GsonConverterFactory.create()
            retrofit2.Retrofit$Builder r5 = r5.addConverterFactory(r6)
            retrofit2.adapter.rxjava.RxJavaCallAdapterFactory r6 = retrofit2.adapter.rxjava.RxJavaCallAdapterFactory.create()
            retrofit2.Retrofit$Builder r5 = r5.addCallAdapterFactory(r6)
            retrofit2.Retrofit$Builder r5 = r5.client(r9)
            retrofit2.Retrofit r4 = r5.build()
            java.lang.Object r5 = r4.create(r8)
            return r5
        L_0x0036:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0010
        L_0x003b:
            r1 = move-exception
            r1.getMessage()
            r1.printStackTrace()
            goto L_0x0010
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.bioeasy.app.net.HttpHelper.createService(java.lang.Class, okhttp3.OkHttpClient):java.lang.Object");
    }

    private class LogInterceptor implements Interceptor {
        private LogInterceptor() {
        }

        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Log.v("HttpHelper", String.format("Sending request %s on %s%n%s", new Object[]{request.url(), chain.connection(), request.headers()}));
            Response response = chain.proceed(request);
            Log.v("HttpHelper", String.format("Received response for %s in %.1fms%n%s", new Object[]{response.request().url(), Double.valueOf(((double) (System.nanoTime() - t1)) / 1000000.0d), response.headers()}));
            return response;
        }
    }

    private class CacheControlInterceptor implements Interceptor {
        private CacheControlInterceptor() {
        }

        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            if (!AppUtils.isNetworkConnected(HttpHelper.this.mContext)) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            if (AppUtils.isNetworkConnected(HttpHelper.this.mContext)) {
                response.newBuilder().removeHeader("Pragma").header("Cache-Control", "public, max-age=" + ACache.TIME_HOUR).build();
            } else {
                response.newBuilder().removeHeader("Pragma").header("Cache-Control", "public, only-if-cached, max-stale=" + 2419200).build();
            }
            return response;
        }
    }
}

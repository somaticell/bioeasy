package cn.com.bioeasy.healty.app.healthapp.injection.module;

import android.content.Context;
import cn.com.bioeasy.app.exception.RxErrorHandlingCallAdapterFactory;
import cn.com.bioeasy.app.gson.LongGsonAdapter;
import cn.com.bioeasy.app.gson.NobodyConverterFactory;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.healty.app.healthapp.Interceptor.LoggingInterceptor;
import cn.com.bioeasy.healty.app.healthapp.Interceptor.ParamsInterceptor;
import cn.com.bioeasy.healty.app.healthapp.R;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    public static String APP_SERVER_URL = "";
    public static String TEST_HTTP_URL = "";
    private static final int TIMEOUT = 20000;

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public OkHttpClient privodeOkHttpClient(ParamsInterceptor paramsInterceptor, LoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder().connectTimeout(20000, TimeUnit.MILLISECONDS).readTimeout(20000, TimeUnit.MILLISECONDS).addInterceptor(loggingInterceptor).build();
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public Retrofit privodeRetrofit(Context context, OkHttpClient okHttpClient) {
        Logger.e("NetworkModule: init Retrofit", new Object[0]);
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        builder.registerTypeAdapter(Long.class, new LongGsonAdapter());
        return new Retrofit.Builder().baseUrl(APP_SERVER_URL).addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create(builder.create())).addConverterFactory(NobodyConverterFactory.create()).client(okHttpClient).build();
    }

    private OkHttpClient okTimeOut(OkHttpClient.Builder clientBuilder, int timeOut) {
        return clientBuilder.connectTimeout((long) timeOut, TimeUnit.MILLISECONDS).readTimeout((long) timeOut, TimeUnit.MILLISECONDS).build();
    }

    public static void initialize(Context context) {
        if (StringUtil.isNullOrEmpty(APP_SERVER_URL)) {
            APP_SERVER_URL = context.getString(R.string.api_url);
        }
        if (StringUtil.isNullOrEmpty(TEST_HTTP_URL)) {
            TEST_HTTP_URL = context.getString(R.string.api_test_url);
        }
    }
}

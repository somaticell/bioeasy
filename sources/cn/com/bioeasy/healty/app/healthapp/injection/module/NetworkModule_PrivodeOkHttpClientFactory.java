package cn.com.bioeasy.healty.app.healthapp.injection.module;

import cn.com.bioeasy.healty.app.healthapp.Interceptor.LoggingInterceptor;
import cn.com.bioeasy.healty.app.healthapp.Interceptor.ParamsInterceptor;
import dagger.internal.Factory;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

public final class NetworkModule_PrivodeOkHttpClientFactory implements Factory<OkHttpClient> {
    static final /* synthetic */ boolean $assertionsDisabled = (!NetworkModule_PrivodeOkHttpClientFactory.class.desiredAssertionStatus());
    private final Provider<LoggingInterceptor> loggingInterceptorProvider;
    private final NetworkModule module;
    private final Provider<ParamsInterceptor> paramsInterceptorProvider;

    public NetworkModule_PrivodeOkHttpClientFactory(NetworkModule module2, Provider<ParamsInterceptor> paramsInterceptorProvider2, Provider<LoggingInterceptor> loggingInterceptorProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || paramsInterceptorProvider2 != null) {
                this.paramsInterceptorProvider = paramsInterceptorProvider2;
                if ($assertionsDisabled || loggingInterceptorProvider2 != null) {
                    this.loggingInterceptorProvider = loggingInterceptorProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public OkHttpClient get() {
        OkHttpClient provided = this.module.privodeOkHttpClient(this.paramsInterceptorProvider.get(), this.loggingInterceptorProvider.get());
        if (provided != null) {
            return provided;
        }
        throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<OkHttpClient> create(NetworkModule module2, Provider<ParamsInterceptor> paramsInterceptorProvider2, Provider<LoggingInterceptor> loggingInterceptorProvider2) {
        return new NetworkModule_PrivodeOkHttpClientFactory(module2, paramsInterceptorProvider2, loggingInterceptorProvider2);
    }
}

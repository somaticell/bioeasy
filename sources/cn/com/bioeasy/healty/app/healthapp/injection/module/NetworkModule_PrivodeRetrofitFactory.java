package cn.com.bioeasy.healty.app.healthapp.injection.module;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public final class NetworkModule_PrivodeRetrofitFactory implements Factory<Retrofit> {
    static final /* synthetic */ boolean $assertionsDisabled = (!NetworkModule_PrivodeRetrofitFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final NetworkModule module;
    private final Provider<OkHttpClient> okHttpClientProvider;

    public NetworkModule_PrivodeRetrofitFactory(NetworkModule module2, Provider<Context> contextProvider2, Provider<OkHttpClient> okHttpClientProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || okHttpClientProvider2 != null) {
                    this.okHttpClientProvider = okHttpClientProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public Retrofit get() {
        Retrofit provided = this.module.privodeRetrofit(this.contextProvider.get(), this.okHttpClientProvider.get());
        if (provided != null) {
            return provided;
        }
        throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<Retrofit> create(NetworkModule module2, Provider<Context> contextProvider2, Provider<OkHttpClient> okHttpClientProvider2) {
        return new NetworkModule_PrivodeRetrofitFactory(module2, contextProvider2, okHttpClientProvider2);
    }
}

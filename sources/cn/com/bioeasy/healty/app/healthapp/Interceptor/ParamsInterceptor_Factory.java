package cn.com.bioeasy.healty.app.healthapp.Interceptor;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ParamsInterceptor_Factory implements Factory<ParamsInterceptor> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ParamsInterceptor_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;

    public ParamsInterceptor_Factory(Provider<Context> contextProvider2) {
        if ($assertionsDisabled || contextProvider2 != null) {
            this.contextProvider = contextProvider2;
            return;
        }
        throw new AssertionError();
    }

    public ParamsInterceptor get() {
        return new ParamsInterceptor(this.contextProvider.get());
    }

    public static Factory<ParamsInterceptor> create(Provider<Context> contextProvider2) {
        return new ParamsInterceptor_Factory(contextProvider2);
    }
}

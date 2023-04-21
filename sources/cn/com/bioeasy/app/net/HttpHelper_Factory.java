package cn.com.bioeasy.app.net;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class HttpHelper_Factory implements Factory<HttpHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HttpHelper_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;

    public HttpHelper_Factory(Provider<Context> contextProvider2) {
        if ($assertionsDisabled || contextProvider2 != null) {
            this.contextProvider = contextProvider2;
            return;
        }
        throw new AssertionError();
    }

    public HttpHelper get() {
        return new HttpHelper(this.contextProvider.get());
    }

    public static Factory<HttpHelper> create(Provider<Context> contextProvider2) {
        return new HttpHelper_Factory(contextProvider2);
    }
}

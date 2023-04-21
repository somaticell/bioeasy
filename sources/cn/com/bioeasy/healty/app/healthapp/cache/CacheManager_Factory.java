package cn.com.bioeasy.healty.app.healthapp.cache;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class CacheManager_Factory implements Factory<CacheManager> {
    static final /* synthetic */ boolean $assertionsDisabled = (!CacheManager_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;

    public CacheManager_Factory(Provider<Context> contextProvider2) {
        if ($assertionsDisabled || contextProvider2 != null) {
            this.contextProvider = contextProvider2;
            return;
        }
        throw new AssertionError();
    }

    public CacheManager get() {
        return new CacheManager(this.contextProvider.get());
    }

    public static Factory<CacheManager> create(Provider<Context> contextProvider2) {
        return new CacheManager_Factory(contextProvider2);
    }
}

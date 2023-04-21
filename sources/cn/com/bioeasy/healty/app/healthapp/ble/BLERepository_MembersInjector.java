package cn.com.bioeasy.healty.app.healthapp.ble;

import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class BLERepository_MembersInjector implements MembersInjector<BLERepository> {
    static final /* synthetic */ boolean $assertionsDisabled = (!BLERepository_MembersInjector.class.desiredAssertionStatus());
    private final Provider<CacheManager> cacheManagerProvider;

    public BLERepository_MembersInjector(Provider<CacheManager> cacheManagerProvider2) {
        if ($assertionsDisabled || cacheManagerProvider2 != null) {
            this.cacheManagerProvider = cacheManagerProvider2;
            return;
        }
        throw new AssertionError();
    }

    public void injectMembers(BLERepository instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.cacheManager = this.cacheManagerProvider.get();
    }

    public static MembersInjector<BLERepository> create(Provider<CacheManager> cacheManagerProvider2) {
        return new BLERepository_MembersInjector(cacheManagerProvider2);
    }
}

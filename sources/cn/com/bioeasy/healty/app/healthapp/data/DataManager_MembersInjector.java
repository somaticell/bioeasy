package cn.com.bioeasy.healty.app.healthapp.data;

import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class DataManager_MembersInjector implements MembersInjector<DataManager> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DataManager_MembersInjector.class.desiredAssertionStatus());
    private final Provider<CacheManager> cacheManagerProvider;

    public DataManager_MembersInjector(Provider<CacheManager> cacheManagerProvider2) {
        if ($assertionsDisabled || cacheManagerProvider2 != null) {
            this.cacheManagerProvider = cacheManagerProvider2;
            return;
        }
        throw new AssertionError();
    }

    public void injectMembers(DataManager instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.cacheManager = this.cacheManagerProvider.get();
    }

    public static MembersInjector<DataManager> create(Provider<CacheManager> cacheManagerProvider2) {
        return new DataManager_MembersInjector(cacheManagerProvider2);
    }
}

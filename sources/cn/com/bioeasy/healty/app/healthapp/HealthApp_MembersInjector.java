package cn.com.bioeasy.healty.app.healthapp;

import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import cn.com.bioeasy.healty.app.healthapp.data.DataManager;
import dagger.MembersInjector;
import javax.inject.Provider;
import org.litepal.LitePalApplication;

public final class HealthApp_MembersInjector implements MembersInjector<HealthApp> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HealthApp_MembersInjector.class.desiredAssertionStatus());
    private final Provider<CacheManager> cacheManagerProvider;
    private final Provider<DataManager> dataManagerProvider;
    private final MembersInjector<LitePalApplication> supertypeInjector;

    public HealthApp_MembersInjector(MembersInjector<LitePalApplication> supertypeInjector2, Provider<DataManager> dataManagerProvider2, Provider<CacheManager> cacheManagerProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || dataManagerProvider2 != null) {
                this.dataManagerProvider = dataManagerProvider2;
                if ($assertionsDisabled || cacheManagerProvider2 != null) {
                    this.cacheManagerProvider = cacheManagerProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(HealthApp instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.dataManager = this.dataManagerProvider.get();
        instance.cacheManager = this.cacheManagerProvider.get();
    }

    public static MembersInjector<HealthApp> create(MembersInjector<LitePalApplication> supertypeInjector2, Provider<DataManager> dataManagerProvider2, Provider<CacheManager> cacheManagerProvider2) {
        return new HealthApp_MembersInjector(supertypeInjector2, dataManagerProvider2, cacheManagerProvider2);
    }
}

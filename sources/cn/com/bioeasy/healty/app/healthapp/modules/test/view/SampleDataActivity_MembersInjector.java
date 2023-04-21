package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class SampleDataActivity_MembersInjector implements MembersInjector<SampleDataActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SampleDataActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<CacheManager> cacheManagerProvider;
    private final Provider<SamplePresenter> samplePresenterProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;

    public SampleDataActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<SamplePresenter> samplePresenterProvider2, Provider<CacheManager> cacheManagerProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || samplePresenterProvider2 != null) {
                this.samplePresenterProvider = samplePresenterProvider2;
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

    public void injectMembers(SampleDataActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.samplePresenter = this.samplePresenterProvider.get();
        instance.cacheManager = this.cacheManagerProvider.get();
    }

    public static MembersInjector<SampleDataActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<SamplePresenter> samplePresenterProvider2, Provider<CacheManager> cacheManagerProvider2) {
        return new SampleDataActivity_MembersInjector(supertypeInjector2, samplePresenterProvider2, cacheManagerProvider2);
    }
}

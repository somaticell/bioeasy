package cn.com.bioeasy.healty.app.healthapp.modules.user;

import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.base.BaseView;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import cn.com.bioeasy.healty.app.healthapp.modules.user.modal.UserRepository;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class MainPresenter_MembersInjector implements MembersInjector<MainPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!MainPresenter_MembersInjector.class.desiredAssertionStatus());
    private final Provider<CacheManager> cacheManagerProvider;
    private final MembersInjector<BasePresenter<BaseView, UserRepository>> supertypeInjector;

    public MainPresenter_MembersInjector(MembersInjector<BasePresenter<BaseView, UserRepository>> supertypeInjector2, Provider<CacheManager> cacheManagerProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || cacheManagerProvider2 != null) {
                this.cacheManagerProvider = cacheManagerProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(MainPresenter instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.cacheManager = this.cacheManagerProvider.get();
    }

    public static MembersInjector<MainPresenter> create(MembersInjector<BasePresenter<BaseView, UserRepository>> supertypeInjector2, Provider<CacheManager> cacheManagerProvider2) {
        return new MainPresenter_MembersInjector(supertypeInjector2, cacheManagerProvider2);
    }
}

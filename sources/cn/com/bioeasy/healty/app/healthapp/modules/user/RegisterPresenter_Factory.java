package cn.com.bioeasy.healty.app.healthapp.modules.user;

import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import cn.com.bioeasy.healty.app.healthapp.modules.user.modal.UserRepository;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class RegisterPresenter_Factory implements Factory<RegisterPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!RegisterPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<CacheManager> cacheManagerProvider;
    private final Provider<UserRepository> mRepositoryProvider;
    private final MembersInjector<RegisterPresenter> membersInjector;

    public RegisterPresenter_Factory(MembersInjector<RegisterPresenter> membersInjector2, Provider<CacheManager> cacheManagerProvider2, Provider<UserRepository> mRepositoryProvider2) {
        if ($assertionsDisabled || membersInjector2 != null) {
            this.membersInjector = membersInjector2;
            if ($assertionsDisabled || cacheManagerProvider2 != null) {
                this.cacheManagerProvider = cacheManagerProvider2;
                if ($assertionsDisabled || mRepositoryProvider2 != null) {
                    this.mRepositoryProvider = mRepositoryProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public RegisterPresenter get() {
        RegisterPresenter instance = new RegisterPresenter(this.cacheManagerProvider.get(), this.mRepositoryProvider.get());
        this.membersInjector.injectMembers(instance);
        return instance;
    }

    public static Factory<RegisterPresenter> create(MembersInjector<RegisterPresenter> membersInjector2, Provider<CacheManager> cacheManagerProvider2, Provider<UserRepository> mRepositoryProvider2) {
        return new RegisterPresenter_Factory(membersInjector2, cacheManagerProvider2, mRepositoryProvider2);
    }
}

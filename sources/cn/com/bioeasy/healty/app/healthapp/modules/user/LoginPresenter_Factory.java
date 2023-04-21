package cn.com.bioeasy.healty.app.healthapp.modules.user;

import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import cn.com.bioeasy.healty.app.healthapp.modules.user.modal.UserRepository;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class LoginPresenter_Factory implements Factory<LoginPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!LoginPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<CacheManager> cacheManagerProvider;
    private final MembersInjector<LoginPresenter> membersInjector;
    private final Provider<UserRepository> repositoryProvider;

    public LoginPresenter_Factory(MembersInjector<LoginPresenter> membersInjector2, Provider<CacheManager> cacheManagerProvider2, Provider<UserRepository> repositoryProvider2) {
        if ($assertionsDisabled || membersInjector2 != null) {
            this.membersInjector = membersInjector2;
            if ($assertionsDisabled || cacheManagerProvider2 != null) {
                this.cacheManagerProvider = cacheManagerProvider2;
                if ($assertionsDisabled || repositoryProvider2 != null) {
                    this.repositoryProvider = repositoryProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public LoginPresenter get() {
        LoginPresenter instance = new LoginPresenter(this.cacheManagerProvider.get(), this.repositoryProvider.get());
        this.membersInjector.injectMembers(instance);
        return instance;
    }

    public static Factory<LoginPresenter> create(MembersInjector<LoginPresenter> membersInjector2, Provider<CacheManager> cacheManagerProvider2, Provider<UserRepository> repositoryProvider2) {
        return new LoginPresenter_Factory(membersInjector2, cacheManagerProvider2, repositoryProvider2);
    }
}

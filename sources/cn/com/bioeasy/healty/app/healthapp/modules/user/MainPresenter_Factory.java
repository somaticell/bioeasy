package cn.com.bioeasy.healty.app.healthapp.modules.user;

import cn.com.bioeasy.healty.app.healthapp.modules.user.modal.UserRepository;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class MainPresenter_Factory implements Factory<MainPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!MainPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<UserRepository> mRepositoryProvider;
    private final MembersInjector<MainPresenter> membersInjector;

    public MainPresenter_Factory(MembersInjector<MainPresenter> membersInjector2, Provider<UserRepository> mRepositoryProvider2) {
        if ($assertionsDisabled || membersInjector2 != null) {
            this.membersInjector = membersInjector2;
            if ($assertionsDisabled || mRepositoryProvider2 != null) {
                this.mRepositoryProvider = mRepositoryProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public MainPresenter get() {
        MainPresenter instance = new MainPresenter(this.mRepositoryProvider.get());
        this.membersInjector.injectMembers(instance);
        return instance;
    }

    public static Factory<MainPresenter> create(MembersInjector<MainPresenter> membersInjector2, Provider<UserRepository> mRepositoryProvider2) {
        return new MainPresenter_Factory(membersInjector2, mRepositoryProvider2);
    }
}

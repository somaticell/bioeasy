package cn.com.bioeasy.healty.app.healthapp.modules.home;

import cn.com.bioeasy.healty.app.healthapp.modules.home.modal.ContentRepository;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class HomePresenter_Factory implements Factory<HomePresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HomePresenter_Factory.class.desiredAssertionStatus());
    private final Provider<ContentRepository> mRepositoryProvider;
    private final MembersInjector<HomePresenter> membersInjector;

    public HomePresenter_Factory(MembersInjector<HomePresenter> membersInjector2, Provider<ContentRepository> mRepositoryProvider2) {
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

    public HomePresenter get() {
        HomePresenter instance = new HomePresenter(this.mRepositoryProvider.get());
        this.membersInjector.injectMembers(instance);
        return instance;
    }

    public static Factory<HomePresenter> create(MembersInjector<HomePresenter> membersInjector2, Provider<ContentRepository> mRepositoryProvider2) {
        return new HomePresenter_Factory(membersInjector2, mRepositoryProvider2);
    }
}

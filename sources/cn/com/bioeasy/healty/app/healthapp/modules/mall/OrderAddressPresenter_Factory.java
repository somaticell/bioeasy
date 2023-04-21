package cn.com.bioeasy.healty.app.healthapp.modules.mall;

import cn.com.bioeasy.healty.app.healthapp.modules.user.modal.UserRepository;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class OrderAddressPresenter_Factory implements Factory<OrderAddressPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!OrderAddressPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<UserRepository> mRepositoryProvider;
    private final MembersInjector<OrderAddressPresenter> membersInjector;

    public OrderAddressPresenter_Factory(MembersInjector<OrderAddressPresenter> membersInjector2, Provider<UserRepository> mRepositoryProvider2) {
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

    public OrderAddressPresenter get() {
        OrderAddressPresenter instance = new OrderAddressPresenter(this.mRepositoryProvider.get());
        this.membersInjector.injectMembers(instance);
        return instance;
    }

    public static Factory<OrderAddressPresenter> create(MembersInjector<OrderAddressPresenter> membersInjector2, Provider<UserRepository> mRepositoryProvider2) {
        return new OrderAddressPresenter_Factory(membersInjector2, mRepositoryProvider2);
    }
}

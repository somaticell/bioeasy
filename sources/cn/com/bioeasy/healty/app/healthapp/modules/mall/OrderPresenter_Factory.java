package cn.com.bioeasy.healty.app.healthapp.modules.mall;

import cn.com.bioeasy.healty.app.healthapp.modules.mall.modal.OrderRepository;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class OrderPresenter_Factory implements Factory<OrderPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!OrderPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<OrderRepository> mRepositoryProvider;
    private final MembersInjector<OrderPresenter> membersInjector;

    public OrderPresenter_Factory(MembersInjector<OrderPresenter> membersInjector2, Provider<OrderRepository> mRepositoryProvider2) {
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

    public OrderPresenter get() {
        OrderPresenter instance = new OrderPresenter(this.mRepositoryProvider.get());
        this.membersInjector.injectMembers(instance);
        return instance;
    }

    public static Factory<OrderPresenter> create(MembersInjector<OrderPresenter> membersInjector2, Provider<OrderRepository> mRepositoryProvider2) {
        return new OrderPresenter_Factory(membersInjector2, mRepositoryProvider2);
    }
}

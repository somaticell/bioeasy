package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class OrderStateFragment_MembersInjector implements MembersInjector<OrderStateFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!OrderStateFragment_MembersInjector.class.desiredAssertionStatus());
    private final Provider<OrderPresenter> orderPresenterProvider;
    private final MembersInjector<BIBaseFragment> supertypeInjector;

    public OrderStateFragment_MembersInjector(MembersInjector<BIBaseFragment> supertypeInjector2, Provider<OrderPresenter> orderPresenterProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || orderPresenterProvider2 != null) {
                this.orderPresenterProvider = orderPresenterProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(OrderStateFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.orderPresenter = this.orderPresenterProvider.get();
    }

    public static MembersInjector<OrderStateFragment> create(MembersInjector<BIBaseFragment> supertypeInjector2, Provider<OrderPresenter> orderPresenterProvider2) {
        return new OrderStateFragment_MembersInjector(supertypeInjector2, orderPresenterProvider2);
    }
}

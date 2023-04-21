package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class OrderExpressActivity_MembersInjector implements MembersInjector<OrderExpressActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!OrderExpressActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<OrderPresenter> orderPresenterProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;

    public OrderExpressActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<OrderPresenter> orderPresenterProvider2) {
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

    public void injectMembers(OrderExpressActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.orderPresenter = this.orderPresenterProvider.get();
    }

    public static MembersInjector<OrderExpressActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<OrderPresenter> orderPresenterProvider2) {
        return new OrderExpressActivity_MembersInjector(supertypeInjector2, orderPresenterProvider2);
    }
}

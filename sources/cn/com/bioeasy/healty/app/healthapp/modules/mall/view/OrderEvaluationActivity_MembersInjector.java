package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class OrderEvaluationActivity_MembersInjector implements MembersInjector<OrderEvaluationActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!OrderEvaluationActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<OrderPresenter> orderPresenterProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;

    public OrderEvaluationActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<OrderPresenter> orderPresenterProvider2) {
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

    public void injectMembers(OrderEvaluationActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.orderPresenter = this.orderPresenterProvider.get();
    }

    public static MembersInjector<OrderEvaluationActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<OrderPresenter> orderPresenterProvider2) {
        return new OrderEvaluationActivity_MembersInjector(supertypeInjector2, orderPresenterProvider2);
    }
}

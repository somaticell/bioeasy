package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderAddressPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class AddressActivity_MembersInjector implements MembersInjector<AddressActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AddressActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<OrderAddressPresenter> orderAddressPresenterProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;

    public AddressActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<OrderAddressPresenter> orderAddressPresenterProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || orderAddressPresenterProvider2 != null) {
                this.orderAddressPresenterProvider = orderAddressPresenterProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(AddressActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.orderAddressPresenter = this.orderAddressPresenterProvider.get();
    }

    public static MembersInjector<AddressActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<OrderAddressPresenter> orderAddressPresenterProvider2) {
        return new AddressActivity_MembersInjector(supertypeInjector2, orderAddressPresenterProvider2);
    }
}

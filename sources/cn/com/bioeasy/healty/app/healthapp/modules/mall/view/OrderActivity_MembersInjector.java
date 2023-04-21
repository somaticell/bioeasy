package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class OrderActivity_MembersInjector implements MembersInjector<OrderActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!OrderActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<CacheManager> cacheManagerProvider;
    private final Provider<OrderPresenter> orderPresenterProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;

    public OrderActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<OrderPresenter> orderPresenterProvider2, Provider<CacheManager> cacheManagerProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || orderPresenterProvider2 != null) {
                this.orderPresenterProvider = orderPresenterProvider2;
                if ($assertionsDisabled || cacheManagerProvider2 != null) {
                    this.cacheManagerProvider = cacheManagerProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(OrderActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.orderPresenter = this.orderPresenterProvider.get();
        instance.cacheManager = this.cacheManagerProvider.get();
    }

    public static MembersInjector<OrderActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<OrderPresenter> orderPresenterProvider2, Provider<CacheManager> cacheManagerProvider2) {
        return new OrderActivity_MembersInjector(supertypeInjector2, orderPresenterProvider2, cacheManagerProvider2);
    }
}

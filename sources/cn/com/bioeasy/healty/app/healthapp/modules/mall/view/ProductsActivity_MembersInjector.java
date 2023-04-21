package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.GoodsPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class ProductsActivity_MembersInjector implements MembersInjector<ProductsActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ProductsActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<GoodsPresenter> goodsPresenterProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;

    public ProductsActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<GoodsPresenter> goodsPresenterProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || goodsPresenterProvider2 != null) {
                this.goodsPresenterProvider = goodsPresenterProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(ProductsActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.goodsPresenter = this.goodsPresenterProvider.get();
    }

    public static MembersInjector<ProductsActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<GoodsPresenter> goodsPresenterProvider2) {
        return new ProductsActivity_MembersInjector(supertypeInjector2, goodsPresenterProvider2);
    }
}

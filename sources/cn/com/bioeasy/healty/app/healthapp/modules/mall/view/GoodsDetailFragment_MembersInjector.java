package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.GoodsPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class GoodsDetailFragment_MembersInjector implements MembersInjector<GoodsDetailFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!GoodsDetailFragment_MembersInjector.class.desiredAssertionStatus());
    private final Provider<GoodsPresenter> goodsPresenterProvider;
    private final MembersInjector<BIBaseFragment> supertypeInjector;

    public GoodsDetailFragment_MembersInjector(MembersInjector<BIBaseFragment> supertypeInjector2, Provider<GoodsPresenter> goodsPresenterProvider2) {
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

    public void injectMembers(GoodsDetailFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.goodsPresenter = this.goodsPresenterProvider.get();
    }

    public static MembersInjector<GoodsDetailFragment> create(MembersInjector<BIBaseFragment> supertypeInjector2, Provider<GoodsPresenter> goodsPresenterProvider2) {
        return new GoodsDetailFragment_MembersInjector(supertypeInjector2, goodsPresenterProvider2);
    }
}

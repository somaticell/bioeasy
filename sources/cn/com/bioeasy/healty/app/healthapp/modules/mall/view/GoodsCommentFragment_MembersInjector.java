package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.GoodsPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class GoodsCommentFragment_MembersInjector implements MembersInjector<GoodsCommentFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!GoodsCommentFragment_MembersInjector.class.desiredAssertionStatus());
    private final Provider<GoodsPresenter> goodsPresenterProvider;
    private final MembersInjector<BIBaseFragment> supertypeInjector;

    public GoodsCommentFragment_MembersInjector(MembersInjector<BIBaseFragment> supertypeInjector2, Provider<GoodsPresenter> goodsPresenterProvider2) {
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

    public void injectMembers(GoodsCommentFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.goodsPresenter = this.goodsPresenterProvider.get();
    }

    public static MembersInjector<GoodsCommentFragment> create(MembersInjector<BIBaseFragment> supertypeInjector2, Provider<GoodsPresenter> goodsPresenterProvider2) {
        return new GoodsCommentFragment_MembersInjector(supertypeInjector2, goodsPresenterProvider2);
    }
}

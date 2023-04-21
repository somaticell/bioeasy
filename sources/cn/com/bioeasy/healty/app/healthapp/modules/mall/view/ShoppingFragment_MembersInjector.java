package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import cn.com.bioeasy.healty.app.healthapp.adapter.ScrollImagesViewPager;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.GoodsPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class ShoppingFragment_MembersInjector implements MembersInjector<ShoppingFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ShoppingFragment_MembersInjector.class.desiredAssertionStatus());
    private final Provider<GoodsPresenter> presenterProvider;
    private final Provider<ScrollImagesViewPager> scrollImagesViewPagerProvider;
    private final MembersInjector<BIBaseFragment> supertypeInjector;

    public ShoppingFragment_MembersInjector(MembersInjector<BIBaseFragment> supertypeInjector2, Provider<ScrollImagesViewPager> scrollImagesViewPagerProvider2, Provider<GoodsPresenter> presenterProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || scrollImagesViewPagerProvider2 != null) {
                this.scrollImagesViewPagerProvider = scrollImagesViewPagerProvider2;
                if ($assertionsDisabled || presenterProvider2 != null) {
                    this.presenterProvider = presenterProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(ShoppingFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.scrollImagesViewPager = this.scrollImagesViewPagerProvider.get();
        instance.presenter = this.presenterProvider.get();
    }

    public static MembersInjector<ShoppingFragment> create(MembersInjector<BIBaseFragment> supertypeInjector2, Provider<ScrollImagesViewPager> scrollImagesViewPagerProvider2, Provider<GoodsPresenter> presenterProvider2) {
        return new ShoppingFragment_MembersInjector(supertypeInjector2, scrollImagesViewPagerProvider2, presenterProvider2);
    }
}

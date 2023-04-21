package cn.com.bioeasy.healty.app.healthapp.modules.home;

import cn.com.bioeasy.healty.app.healthapp.adapter.ScrollImagesViewPager;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class HomeFragment_MembersInjector implements MembersInjector<HomeFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HomeFragment_MembersInjector.class.desiredAssertionStatus());
    private final Provider<HomePresenter> homePresenterProvider;
    private final Provider<ScrollImagesViewPager> scrollImagesViewPagerProvider;
    private final MembersInjector<BIBaseFragment> supertypeInjector;

    public HomeFragment_MembersInjector(MembersInjector<BIBaseFragment> supertypeInjector2, Provider<ScrollImagesViewPager> scrollImagesViewPagerProvider2, Provider<HomePresenter> homePresenterProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || scrollImagesViewPagerProvider2 != null) {
                this.scrollImagesViewPagerProvider = scrollImagesViewPagerProvider2;
                if ($assertionsDisabled || homePresenterProvider2 != null) {
                    this.homePresenterProvider = homePresenterProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(HomeFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.scrollImagesViewPager = this.scrollImagesViewPagerProvider.get();
        instance.homePresenter = this.homePresenterProvider.get();
    }

    public static MembersInjector<HomeFragment> create(MembersInjector<BIBaseFragment> supertypeInjector2, Provider<ScrollImagesViewPager> scrollImagesViewPagerProvider2, Provider<HomePresenter> homePresenterProvider2) {
        return new HomeFragment_MembersInjector(supertypeInjector2, scrollImagesViewPagerProvider2, homePresenterProvider2);
    }
}

package cn.com.bioeasy.healty.app.healthapp.injection.component;

import android.content.Context;
import cn.com.bioeasy.app.base.BaseFragment;
import cn.com.bioeasy.app.base.BaseFragment_MembersInjector;
import cn.com.bioeasy.app.widgets.util.ToastUtils;
import cn.com.bioeasy.app.widgets.util.ToastUtils_Factory;
import cn.com.bioeasy.healty.app.healthapp.adapter.ScrollImagesViewPager;
import cn.com.bioeasy.healty.app.healthapp.adapter.ScrollImagesViewPager_Factory;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.base.TabsFragment;
import cn.com.bioeasy.healty.app.healthapp.injection.module.FragmentModule;
import cn.com.bioeasy.healty.app.healthapp.modules.home.HomeFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.home.HomeFragment_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.home.HomePresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.home.HomePresenter_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.home.modal.ContentRepository;
import cn.com.bioeasy.healty.app.healthapp.modules.home.modal.ContentRepository_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.SafeInformationFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.SafeInformationFragment_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.GoodsPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.GoodsPresenter_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderPresenter_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.modal.GoodsRepository;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.modal.GoodsRepository_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.modal.OrderRepository;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.modal.OrderRepository_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsCommentFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsCommentFragment_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsDetailFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsDetailFragment_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsInfoFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsInfoFragment_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsListFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsListFragment_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.ShoppingFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.ShoppingFragment_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.MineFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.OrderStateFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.OrderStateFragment_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.RecordAllFragmentTab;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.RecordAllFragmentTab_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.RecordFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.RecordFragmentTab;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.RecordFragmentTab_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.RecordFragment_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.RecordPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.RecordPresenter_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.slidemenu.SlideMenuFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.test.modal.SampleRepository;
import cn.com.bioeasy.healty.app.healthapp.modules.test.modal.SampleRepository_Factory;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.SelectFragment;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;
import retrofit2.Retrofit;

public final class DaggerFragmentComponent implements FragmentComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerFragmentComponent.class.desiredAssertionStatus());
    private MembersInjector<BIBaseFragment> bIBaseFragmentMembersInjector;
    private MembersInjector<BaseFragment> baseFragmentMembersInjector;
    private Provider<ContentRepository> contentRepositoryProvider;
    private Provider<Context> contextProvider;
    private MembersInjector<GoodsCommentFragment> goodsCommentFragmentMembersInjector;
    private MembersInjector<GoodsDetailFragment> goodsDetailFragmentMembersInjector;
    private MembersInjector<GoodsInfoFragment> goodsInfoFragmentMembersInjector;
    private MembersInjector<GoodsListFragment> goodsListFragmentMembersInjector;
    private Provider<GoodsPresenter> goodsPresenterProvider;
    private Provider<GoodsRepository> goodsRepositoryProvider;
    private MembersInjector<HomeFragment> homeFragmentMembersInjector;
    private Provider<HomePresenter> homePresenterProvider;
    private MembersInjector<MineFragment> mineFragmentMembersInjector;
    private Provider<OrderPresenter> orderPresenterProvider;
    private Provider<OrderRepository> orderRepositoryProvider;
    private MembersInjector<OrderStateFragment> orderStateFragmentMembersInjector;
    private MembersInjector<RecordAllFragmentTab> recordAllFragmentTabMembersInjector;
    private MembersInjector<RecordFragment> recordFragmentMembersInjector;
    private MembersInjector<RecordFragmentTab> recordFragmentTabMembersInjector;
    private Provider<RecordPresenter> recordPresenterProvider;
    private Provider<Retrofit> retrofitProvider;
    private MembersInjector<SafeInformationFragment> safeInformationFragmentMembersInjector;
    private Provider<SampleRepository> sampleRepositoryProvider;
    private Provider<ScrollImagesViewPager> scrollImagesViewPagerProvider;
    private MembersInjector<SelectFragment> selectFragmentMembersInjector;
    private MembersInjector<ShoppingFragment> shoppingFragmentMembersInjector;
    private MembersInjector<SlideMenuFragment> slideMenuFragmentMembersInjector;
    private MembersInjector<TabsFragment> tabsFragmentMembersInjector;
    private Provider<ToastUtils> toastUtilsProvider;

    private DaggerFragmentComponent(Builder builder) {
        if ($assertionsDisabled || builder != null) {
            initialize(builder);
            return;
        }
        throw new AssertionError();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(final Builder builder) {
        this.contextProvider = new Factory<Context>() {
            private final ApplicationComponent applicationComponent = builder.applicationComponent;

            public Context get() {
                Context provided = this.applicationComponent.context();
                if (provided != null) {
                    return provided;
                }
                throw new NullPointerException("Cannot return null from a non-@Nullable component method");
            }
        };
        this.toastUtilsProvider = ToastUtils_Factory.create(this.contextProvider);
        this.baseFragmentMembersInjector = BaseFragment_MembersInjector.create(MembersInjectors.noOp(), this.contextProvider, this.toastUtilsProvider);
        this.bIBaseFragmentMembersInjector = MembersInjectors.delegatingTo(this.baseFragmentMembersInjector);
        this.scrollImagesViewPagerProvider = ScrollImagesViewPager_Factory.create(MembersInjectors.noOp());
        this.retrofitProvider = new Factory<Retrofit>() {
            private final ApplicationComponent applicationComponent = builder.applicationComponent;

            public Retrofit get() {
                Retrofit provided = this.applicationComponent.retrofit();
                if (provided != null) {
                    return provided;
                }
                throw new NullPointerException("Cannot return null from a non-@Nullable component method");
            }
        };
        this.contentRepositoryProvider = ContentRepository_Factory.create(MembersInjectors.noOp(), this.contextProvider, this.retrofitProvider);
        this.homePresenterProvider = HomePresenter_Factory.create(MembersInjectors.noOp(), this.contentRepositoryProvider);
        this.homeFragmentMembersInjector = HomeFragment_MembersInjector.create(this.bIBaseFragmentMembersInjector, this.scrollImagesViewPagerProvider, this.homePresenterProvider);
        this.mineFragmentMembersInjector = MembersInjectors.delegatingTo(this.bIBaseFragmentMembersInjector);
        this.sampleRepositoryProvider = SampleRepository_Factory.create(MembersInjectors.noOp(), this.contextProvider, this.retrofitProvider);
        this.recordPresenterProvider = RecordPresenter_Factory.create(MembersInjectors.noOp(), this.sampleRepositoryProvider);
        this.recordFragmentMembersInjector = RecordFragment_MembersInjector.create(this.bIBaseFragmentMembersInjector, this.recordPresenterProvider);
        this.recordFragmentTabMembersInjector = RecordFragmentTab_MembersInjector.create(this.bIBaseFragmentMembersInjector, this.recordPresenterProvider);
        this.recordAllFragmentTabMembersInjector = RecordAllFragmentTab_MembersInjector.create(this.bIBaseFragmentMembersInjector, this.recordPresenterProvider);
        this.tabsFragmentMembersInjector = MembersInjectors.delegatingTo(this.bIBaseFragmentMembersInjector);
        this.slideMenuFragmentMembersInjector = MembersInjectors.delegatingTo(this.bIBaseFragmentMembersInjector);
        this.goodsRepositoryProvider = GoodsRepository_Factory.create(MembersInjectors.noOp(), this.contextProvider, this.retrofitProvider);
        this.goodsPresenterProvider = GoodsPresenter_Factory.create(MembersInjectors.noOp(), this.goodsRepositoryProvider);
        this.shoppingFragmentMembersInjector = ShoppingFragment_MembersInjector.create(this.bIBaseFragmentMembersInjector, this.scrollImagesViewPagerProvider, this.goodsPresenterProvider);
        this.goodsListFragmentMembersInjector = GoodsListFragment_MembersInjector.create(this.bIBaseFragmentMembersInjector, this.goodsPresenterProvider);
        this.safeInformationFragmentMembersInjector = SafeInformationFragment_MembersInjector.create(this.bIBaseFragmentMembersInjector, this.homePresenterProvider);
        this.selectFragmentMembersInjector = MembersInjectors.delegatingTo(this.bIBaseFragmentMembersInjector);
        this.goodsInfoFragmentMembersInjector = GoodsInfoFragment_MembersInjector.create(this.bIBaseFragmentMembersInjector, this.goodsPresenterProvider);
        this.goodsDetailFragmentMembersInjector = GoodsDetailFragment_MembersInjector.create(this.bIBaseFragmentMembersInjector, this.goodsPresenterProvider);
        this.goodsCommentFragmentMembersInjector = GoodsCommentFragment_MembersInjector.create(this.bIBaseFragmentMembersInjector, this.goodsPresenterProvider);
        this.orderRepositoryProvider = OrderRepository_Factory.create(MembersInjectors.noOp(), this.contextProvider, this.retrofitProvider);
        this.orderPresenterProvider = OrderPresenter_Factory.create(MembersInjectors.noOp(), this.orderRepositoryProvider);
        this.orderStateFragmentMembersInjector = OrderStateFragment_MembersInjector.create(this.bIBaseFragmentMembersInjector, this.orderPresenterProvider);
    }

    public void inject(HomeFragment homeFragment) {
        this.homeFragmentMembersInjector.injectMembers(homeFragment);
    }

    public void inject(MineFragment mineFragment) {
        this.mineFragmentMembersInjector.injectMembers(mineFragment);
    }

    public void inject(RecordFragment recordFragment) {
        this.recordFragmentMembersInjector.injectMembers(recordFragment);
    }

    public void inject(RecordFragmentTab recordFragment) {
        this.recordFragmentTabMembersInjector.injectMembers(recordFragment);
    }

    public void inject(RecordAllFragmentTab recordFragment) {
        this.recordAllFragmentTabMembersInjector.injectMembers(recordFragment);
    }

    public void inject(TabsFragment tabsFragment) {
        this.tabsFragmentMembersInjector.injectMembers(tabsFragment);
    }

    public void inject(SlideMenuFragment slideMenuFragment) {
        this.slideMenuFragmentMembersInjector.injectMembers(slideMenuFragment);
    }

    public void inject(ShoppingFragment shoppingFragment) {
        this.shoppingFragmentMembersInjector.injectMembers(shoppingFragment);
    }

    public void inject(GoodsListFragment goodsListFragment) {
        this.goodsListFragmentMembersInjector.injectMembers(goodsListFragment);
    }

    public void inject(SafeInformationFragment safeInformationFragment) {
        this.safeInformationFragmentMembersInjector.injectMembers(safeInformationFragment);
    }

    public void inject(SelectFragment selectFragment) {
        this.selectFragmentMembersInjector.injectMembers(selectFragment);
    }

    public void inject(GoodsInfoFragment goodsInfoFragment) {
        this.goodsInfoFragmentMembersInjector.injectMembers(goodsInfoFragment);
    }

    public void inject(GoodsDetailFragment goodsDetailFragment) {
        this.goodsDetailFragmentMembersInjector.injectMembers(goodsDetailFragment);
    }

    public void inject(GoodsCommentFragment goodsCommentFragment) {
        this.goodsCommentFragmentMembersInjector.injectMembers(goodsCommentFragment);
    }

    public void inject(OrderStateFragment orderStateFragment) {
        this.orderStateFragmentMembersInjector.injectMembers(orderStateFragment);
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public ApplicationComponent applicationComponent;
        private FragmentModule fragmentModule;

        private Builder() {
        }

        public FragmentComponent build() {
            if (this.fragmentModule == null) {
                throw new IllegalStateException("fragmentModule must be set");
            } else if (this.applicationComponent != null) {
                return new DaggerFragmentComponent(this);
            } else {
                throw new IllegalStateException("applicationComponent must be set");
            }
        }

        public Builder fragmentModule(FragmentModule fragmentModule2) {
            if (fragmentModule2 == null) {
                throw new NullPointerException("fragmentModule");
            }
            this.fragmentModule = fragmentModule2;
            return this;
        }

        public Builder applicationComponent(ApplicationComponent applicationComponent2) {
            if (applicationComponent2 == null) {
                throw new NullPointerException("applicationComponent");
            }
            this.applicationComponent = applicationComponent2;
            return this;
        }
    }
}

package cn.com.bioeasy.healty.app.healthapp.injection.component;

import android.content.Context;
import cn.com.bioeasy.app.base.AppManager;
import cn.com.bioeasy.app.base.BaseActivity;
import cn.com.bioeasy.app.base.BaseActivity_MembersInjector;
import cn.com.bioeasy.app.event.EventPosterHelper;
import cn.com.bioeasy.app.event.EventPosterHelper_Factory;
import cn.com.bioeasy.app.widgets.util.ToastUtils;
import cn.com.bioeasy.app.widgets.util.ToastUtils_Factory;
import cn.com.bioeasy.healty.app.healthapp.MainActivity;
import cn.com.bioeasy.healty.app.healthapp.MainActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import cn.com.bioeasy.healty.app.healthapp.injection.module.ActivityModule;
import cn.com.bioeasy.healty.app.healthapp.modules.home.HomePresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.home.HomePresenter_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.home.modal.ContentRepository;
import cn.com.bioeasy.healty.app.healthapp.modules.home.modal.ContentRepository_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.ContentDetailActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.ContentSearchActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.ContentSearchActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.HotInformationActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.MarketDetailActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.MarketDetailActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.NearbyMarketActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.NearbyMarketActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.NutritionCalculationActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.OfficialReleaseInfoActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.OfficialReleaseInfoActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.GoodsPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.GoodsPresenter_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderAddressPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderAddressPresenter_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderPresenter_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.modal.GoodsRepository;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.modal.GoodsRepository_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.modal.OrderRepository;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.modal.OrderRepository_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.AddOrEditAddressActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.AddOrEditAddressActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.AddressActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.AddressActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsSearchActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsSearchActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.OrderActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.OrderActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.OrderEvaluationActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.OrderEvaluationActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.OrderExpressActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.OrderExpressActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.PayActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.PayActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.ProductsActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.ProductsActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.ShopCartActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.ShopCartActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.DeviceSettingPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.DeviceSettingPresenter_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.AboutMeActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.BindOtherActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.BindingPhoneActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.BindingPhoneActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.DeviceSettingActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.DeviceSettingActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.EditNickNameActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.EditNickNameActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.InfoActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.InfoActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.MyOrderActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.PresentActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.ProtocolActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.VersionActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.MarketSearchActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.MarketSearchActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.TestDetailActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.TestDetailActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.test.modal.SampleRepository;
import cn.com.bioeasy.healty.app.healthapp.modules.test.modal.SampleRepository_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.test.modal.TestItemRepository;
import cn.com.bioeasy.healty.app.healthapp.modules.test.modal.TestItemRepository_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.BlueListActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.BlueListActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.DataUploadResultActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.SampleDataActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.SampleDataActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.SamplePresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.SamplePresenter_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.TestItemActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.TestItemActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.TestItemCategoryActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.TestItemCategoryActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.TestItemPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.TestItemPresenter_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.TestResultActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.TestResultActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.user.LoginPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.user.LoginPresenter_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.user.MainPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.user.MainPresenter_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.user.MainPresenter_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.user.RegisterPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.user.RegisterPresenter_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.user.modal.UserRepository;
import cn.com.bioeasy.healty.app.healthapp.modules.user.modal.UserRepository_Factory;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.ForgetPwdActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.ForgetPwdActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.LoginActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.LoginActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.RegisterActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.RegisterActivity_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.CropActivity;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.ImageGalleryActivity;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.SelectImageActivity;
import cn.com.bioeasy.healty.app.healthapp.widgets.hellochart.HelloChartManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;
import retrofit2.Retrofit;

public final class DaggerActivityComponent implements ActivityComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerActivityComponent.class.desiredAssertionStatus());
    private MembersInjector<AboutMeActivity> aboutMeActivityMembersInjector;
    private MembersInjector<AddOrEditAddressActivity> addOrEditAddressActivityMembersInjector;
    private MembersInjector<AddressActivity> addressActivityMembersInjector;
    private MembersInjector<BIBaseActivity> bIBaseActivityMembersInjector;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private MembersInjector<BindOtherActivity> bindOtherActivityMembersInjector;
    private MembersInjector<BindingPhoneActivity> bindingPhoneActivityMembersInjector;
    private Provider<BLERepository> bleRepositoryProvider;
    private MembersInjector<BlueListActivity> blueListActivityMembersInjector;
    private Provider<CacheManager> cacheManagerProvider;
    private MembersInjector<ContentDetailActivity> contentDetailActivityMembersInjector;
    private Provider<ContentRepository> contentRepositoryProvider;
    private MembersInjector<ContentSearchActivity> contentSearchActivityMembersInjector;
    private Provider<Context> contextProvider;
    private MembersInjector<CropActivity> cropActivityMembersInjector;
    private MembersInjector<DataUploadResultActivity> dataUploadResultActivityMembersInjector;
    private MembersInjector<DeviceSettingActivity> deviceSettingActivityMembersInjector;
    private Provider<DeviceSettingPresenter> deviceSettingPresenterProvider;
    private MembersInjector<EditNickNameActivity> editNickNameActivityMembersInjector;
    private Provider<Bus> eventBusProvider;
    private Provider<EventPosterHelper> eventPosterHelperProvider;
    private MembersInjector<ForgetPwdActivity> forgetPwdActivityMembersInjector;
    private Provider<GoodsPresenter> goodsPresenterProvider;
    private Provider<GoodsRepository> goodsRepositoryProvider;
    private MembersInjector<GoodsSearchActivity> goodsSearchActivityMembersInjector;
    private Provider<HelloChartManager> helloChartManagerProvider;
    private Provider<HomePresenter> homePresenterProvider;
    private MembersInjector<HotInformationActivity> hotInformationActivityMembersInjector;
    private MembersInjector<ImageGalleryActivity> imageGalleryActivityMembersInjector;
    private MembersInjector<InfoActivity> infoActivityMembersInjector;
    private MembersInjector<LoginActivity> loginActivityMembersInjector;
    private Provider<LoginPresenter> loginPresenterProvider;
    private MembersInjector<MainActivity> mainActivityMembersInjector;
    private MembersInjector<MainPresenter> mainPresenterMembersInjector;
    private Provider<MainPresenter> mainPresenterProvider;
    private MembersInjector<MarketDetailActivity> marketDetailActivityMembersInjector;
    private MembersInjector<MarketSearchActivity> marketSearchActivityMembersInjector;
    private MembersInjector<MyOrderActivity> myOrderActivityMembersInjector;
    private MembersInjector<NearbyMarketActivity> nearbyMarketActivityMembersInjector;
    private MembersInjector<NutritionCalculationActivity> nutritionCalculationActivityMembersInjector;
    private MembersInjector<OfficialReleaseInfoActivity> officialReleaseInfoActivityMembersInjector;
    private MembersInjector<OrderActivity> orderActivityMembersInjector;
    private Provider<OrderAddressPresenter> orderAddressPresenterProvider;
    private MembersInjector<OrderEvaluationActivity> orderEvaluationActivityMembersInjector;
    private MembersInjector<OrderExpressActivity> orderExpressActivityMembersInjector;
    private Provider<OrderPresenter> orderPresenterProvider;
    private Provider<OrderRepository> orderRepositoryProvider;
    private Provider<AppManager> pageManagerProvider;
    private MembersInjector<PayActivity> payActivityMembersInjector;
    private MembersInjector<PresentActivity> presentActivityMembersInjector;
    private MembersInjector<ProductsActivity> productsActivityMembersInjector;
    private MembersInjector<ProtocolActivity> protocolActivityMembersInjector;
    private MembersInjector<RegisterActivity> registerActivityMembersInjector;
    private Provider<RegisterPresenter> registerPresenterProvider;
    private Provider<Retrofit> retrofitProvider;
    private MembersInjector<SampleDataActivity> sampleDataActivityMembersInjector;
    private Provider<SamplePresenter> samplePresenterProvider;
    private Provider<SampleRepository> sampleRepositoryProvider;
    private MembersInjector<SelectImageActivity> selectImageActivityMembersInjector;
    private MembersInjector<ShopCartActivity> shopCartActivityMembersInjector;
    private MembersInjector<TestDetailActivity> testDetailActivityMembersInjector;
    private MembersInjector<TestItemActivity> testItemActivityMembersInjector;
    private MembersInjector<TestItemCategoryActivity> testItemCategoryActivityMembersInjector;
    private Provider<TestItemPresenter> testItemPresenterProvider;
    private Provider<TestItemRepository> testItemRepositoryProvider;
    private MembersInjector<TestResultActivity> testResultActivityMembersInjector;
    private Provider<ToastUtils> toastUtilsProvider;
    private Provider<UserRepository> userRepositoryProvider;
    private MembersInjector<VersionActivity> versionActivityMembersInjector;

    private DaggerActivityComponent(Builder builder) {
        if ($assertionsDisabled || builder != null) {
            initialize(builder);
            initialize1(builder);
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
        this.eventBusProvider = new Factory<Bus>() {
            private final ApplicationComponent applicationComponent = builder.applicationComponent;

            public Bus get() {
                Bus provided = this.applicationComponent.eventBus();
                if (provided != null) {
                    return provided;
                }
                throw new NullPointerException("Cannot return null from a non-@Nullable component method");
            }
        };
        this.eventPosterHelperProvider = EventPosterHelper_Factory.create(this.eventBusProvider);
        this.pageManagerProvider = new Factory<AppManager>() {
            private final ApplicationComponent applicationComponent = builder.applicationComponent;

            public AppManager get() {
                AppManager provided = this.applicationComponent.pageManager();
                if (provided != null) {
                    return provided;
                }
                throw new NullPointerException("Cannot return null from a non-@Nullable component method");
            }
        };
        this.baseActivityMembersInjector = BaseActivity_MembersInjector.create(MembersInjectors.noOp(), this.toastUtilsProvider, this.eventPosterHelperProvider, this.contextProvider, this.pageManagerProvider);
        this.bIBaseActivityMembersInjector = MembersInjectors.delegatingTo(this.baseActivityMembersInjector);
        this.cacheManagerProvider = new Factory<CacheManager>() {
            private final ApplicationComponent applicationComponent = builder.applicationComponent;

            public CacheManager get() {
                CacheManager provided = this.applicationComponent.cacheManager();
                if (provided != null) {
                    return provided;
                }
                throw new NullPointerException("Cannot return null from a non-@Nullable component method");
            }
        };
        this.mainPresenterMembersInjector = MainPresenter_MembersInjector.create(MembersInjectors.noOp(), this.cacheManagerProvider);
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
        this.userRepositoryProvider = UserRepository_Factory.create(MembersInjectors.noOp(), this.contextProvider, this.retrofitProvider);
        this.mainPresenterProvider = MainPresenter_Factory.create(this.mainPresenterMembersInjector, this.userRepositoryProvider);
        this.bleRepositoryProvider = new Factory<BLERepository>() {
            private final ApplicationComponent applicationComponent = builder.applicationComponent;

            public BLERepository get() {
                BLERepository provided = this.applicationComponent.bleRepository();
                if (provided != null) {
                    return provided;
                }
                throw new NullPointerException("Cannot return null from a non-@Nullable component method");
            }
        };
        this.mainActivityMembersInjector = MainActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.mainPresenterProvider, this.bleRepositoryProvider);
        this.loginPresenterProvider = LoginPresenter_Factory.create(MembersInjectors.noOp(), this.cacheManagerProvider, this.userRepositoryProvider);
        this.loginActivityMembersInjector = LoginActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.loginPresenterProvider);
        this.registerPresenterProvider = RegisterPresenter_Factory.create(MembersInjectors.noOp(), this.cacheManagerProvider, this.userRepositoryProvider);
        this.forgetPwdActivityMembersInjector = ForgetPwdActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.registerPresenterProvider);
        this.registerActivityMembersInjector = RegisterActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.registerPresenterProvider);
        this.blueListActivityMembersInjector = BlueListActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.bleRepositoryProvider);
        this.testItemRepositoryProvider = TestItemRepository_Factory.create(MembersInjectors.noOp(), this.contextProvider, this.retrofitProvider);
        this.testItemPresenterProvider = TestItemPresenter_Factory.create(MembersInjectors.noOp(), this.testItemRepositoryProvider);
        this.testItemCategoryActivityMembersInjector = TestItemCategoryActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.bleRepositoryProvider, this.testItemPresenterProvider);
        this.helloChartManagerProvider = new Factory<HelloChartManager>() {
            private final ApplicationComponent applicationComponent = builder.applicationComponent;

            public HelloChartManager get() {
                HelloChartManager provided = this.applicationComponent.helloChartManager();
                if (provided != null) {
                    return provided;
                }
                throw new NullPointerException("Cannot return null from a non-@Nullable component method");
            }
        };
        this.sampleRepositoryProvider = SampleRepository_Factory.create(MembersInjectors.noOp(), this.contextProvider, this.retrofitProvider);
        this.samplePresenterProvider = SamplePresenter_Factory.create(MembersInjectors.noOp(), this.bleRepositoryProvider, this.sampleRepositoryProvider);
        this.testResultActivityMembersInjector = TestResultActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.bleRepositoryProvider, this.helloChartManagerProvider, this.samplePresenterProvider);
        this.contentDetailActivityMembersInjector = MembersInjectors.delegatingTo(this.bIBaseActivityMembersInjector);
        this.testItemActivityMembersInjector = TestItemActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.testItemPresenterProvider, this.bleRepositoryProvider);
        this.testDetailActivityMembersInjector = TestDetailActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.samplePresenterProvider, this.helloChartManagerProvider);
        this.deviceSettingPresenterProvider = DeviceSettingPresenter_Factory.create(MembersInjectors.noOp(), this.bleRepositoryProvider, this.userRepositoryProvider);
        this.deviceSettingActivityMembersInjector = DeviceSettingActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.deviceSettingPresenterProvider, this.cacheManagerProvider, this.bleRepositoryProvider);
        this.goodsRepositoryProvider = GoodsRepository_Factory.create(MembersInjectors.noOp(), this.contextProvider, this.retrofitProvider);
        this.goodsPresenterProvider = GoodsPresenter_Factory.create(MembersInjectors.noOp(), this.goodsRepositoryProvider);
        this.productsActivityMembersInjector = ProductsActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.goodsPresenterProvider);
        this.infoActivityMembersInjector = InfoActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.mainPresenterProvider);
        this.editNickNameActivityMembersInjector = EditNickNameActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.loginPresenterProvider);
        this.bindingPhoneActivityMembersInjector = BindingPhoneActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.registerPresenterProvider);
        this.bindOtherActivityMembersInjector = MembersInjectors.delegatingTo(this.bIBaseActivityMembersInjector);
        this.presentActivityMembersInjector = MembersInjectors.delegatingTo(this.bIBaseActivityMembersInjector);
        this.myOrderActivityMembersInjector = MembersInjectors.delegatingTo(this.bIBaseActivityMembersInjector);
        this.orderRepositoryProvider = OrderRepository_Factory.create(MembersInjectors.noOp(), this.contextProvider, this.retrofitProvider);
        this.orderPresenterProvider = OrderPresenter_Factory.create(MembersInjectors.noOp(), this.orderRepositoryProvider);
        this.orderExpressActivityMembersInjector = OrderExpressActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.orderPresenterProvider);
        this.hotInformationActivityMembersInjector = MembersInjectors.delegatingTo(this.bIBaseActivityMembersInjector);
        this.contentRepositoryProvider = ContentRepository_Factory.create(MembersInjectors.noOp(), this.contextProvider, this.retrofitProvider);
        this.homePresenterProvider = HomePresenter_Factory.create(MembersInjectors.noOp(), this.contentRepositoryProvider);
        this.contentSearchActivityMembersInjector = ContentSearchActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.homePresenterProvider);
        this.nearbyMarketActivityMembersInjector = NearbyMarketActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.samplePresenterProvider);
        this.nutritionCalculationActivityMembersInjector = MembersInjectors.delegatingTo(this.bIBaseActivityMembersInjector);
        this.officialReleaseInfoActivityMembersInjector = OfficialReleaseInfoActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.homePresenterProvider);
        this.protocolActivityMembersInjector = MembersInjectors.delegatingTo(this.bIBaseActivityMembersInjector);
        this.aboutMeActivityMembersInjector = MembersInjectors.delegatingTo(this.bIBaseActivityMembersInjector);
        this.sampleDataActivityMembersInjector = SampleDataActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.samplePresenterProvider, this.cacheManagerProvider);
        this.imageGalleryActivityMembersInjector = MembersInjectors.delegatingTo(this.bIBaseActivityMembersInjector);
        this.selectImageActivityMembersInjector = MembersInjectors.delegatingTo(this.bIBaseActivityMembersInjector);
        this.cropActivityMembersInjector = MembersInjectors.delegatingTo(this.bIBaseActivityMembersInjector);
        this.orderAddressPresenterProvider = OrderAddressPresenter_Factory.create(MembersInjectors.noOp(), this.userRepositoryProvider);
        this.addOrEditAddressActivityMembersInjector = AddOrEditAddressActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.orderAddressPresenterProvider);
    }

    private void initialize1(Builder builder) {
        this.addressActivityMembersInjector = AddressActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.orderAddressPresenterProvider);
        this.shopCartActivityMembersInjector = ShopCartActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.goodsPresenterProvider);
        this.orderActivityMembersInjector = OrderActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.orderPresenterProvider, this.cacheManagerProvider);
        this.orderEvaluationActivityMembersInjector = OrderEvaluationActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.orderPresenterProvider);
        this.payActivityMembersInjector = PayActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.orderPresenterProvider);
        this.goodsSearchActivityMembersInjector = GoodsSearchActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.goodsPresenterProvider);
        this.marketSearchActivityMembersInjector = MarketSearchActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.samplePresenterProvider);
        this.marketDetailActivityMembersInjector = MarketDetailActivity_MembersInjector.create(this.bIBaseActivityMembersInjector, this.samplePresenterProvider);
        this.dataUploadResultActivityMembersInjector = MembersInjectors.delegatingTo(this.bIBaseActivityMembersInjector);
        this.versionActivityMembersInjector = MembersInjectors.delegatingTo(this.bIBaseActivityMembersInjector);
    }

    public void inject(BIBaseActivity activity) {
        this.bIBaseActivityMembersInjector.injectMembers(activity);
    }

    public void inject(MainActivity mainActivity) {
        this.mainActivityMembersInjector.injectMembers(mainActivity);
    }

    public void inject(LoginActivity loginActivity) {
        this.loginActivityMembersInjector.injectMembers(loginActivity);
    }

    public void inject(ForgetPwdActivity forgetPwdActivity) {
        this.forgetPwdActivityMembersInjector.injectMembers(forgetPwdActivity);
    }

    public void inject(RegisterActivity registerActivity) {
        this.registerActivityMembersInjector.injectMembers(registerActivity);
    }

    public void inject(BlueListActivity blueListActivity) {
        this.blueListActivityMembersInjector.injectMembers(blueListActivity);
    }

    public void inject(TestItemCategoryActivity testItemCategoryActivity) {
        this.testItemCategoryActivityMembersInjector.injectMembers(testItemCategoryActivity);
    }

    public void inject(TestResultActivity testResultActivity) {
        this.testResultActivityMembersInjector.injectMembers(testResultActivity);
    }

    public void inject(ContentDetailActivity foodDetailActivity) {
        this.contentDetailActivityMembersInjector.injectMembers(foodDetailActivity);
    }

    public void inject(TestItemActivity testItemActivity) {
        this.testItemActivityMembersInjector.injectMembers(testItemActivity);
    }

    public void inject(TestDetailActivity testDetailActivity) {
        this.testDetailActivityMembersInjector.injectMembers(testDetailActivity);
    }

    public void inject(DeviceSettingActivity deviceSettingActivity) {
        this.deviceSettingActivityMembersInjector.injectMembers(deviceSettingActivity);
    }

    public void inject(ProductsActivity productsActivity) {
        this.productsActivityMembersInjector.injectMembers(productsActivity);
    }

    public void inject(InfoActivity infoActivity) {
        this.infoActivityMembersInjector.injectMembers(infoActivity);
    }

    public void inject(EditNickNameActivity editNickNameActivity) {
        this.editNickNameActivityMembersInjector.injectMembers(editNickNameActivity);
    }

    public void inject(BindingPhoneActivity bindingPhoneActivity) {
        this.bindingPhoneActivityMembersInjector.injectMembers(bindingPhoneActivity);
    }

    public void inject(BindOtherActivity bindOtherActivity) {
        this.bindOtherActivityMembersInjector.injectMembers(bindOtherActivity);
    }

    public void inject(PresentActivity presentActivity) {
        this.presentActivityMembersInjector.injectMembers(presentActivity);
    }

    public void inject(MyOrderActivity orderActivity) {
        this.myOrderActivityMembersInjector.injectMembers(orderActivity);
    }

    public void inject(OrderExpressActivity orderExpressActivity) {
        this.orderExpressActivityMembersInjector.injectMembers(orderExpressActivity);
    }

    public void inject(HotInformationActivity hotInformationActivity) {
        this.hotInformationActivityMembersInjector.injectMembers(hotInformationActivity);
    }

    public void inject(ContentSearchActivity contentSearchActivity) {
        this.contentSearchActivityMembersInjector.injectMembers(contentSearchActivity);
    }

    public void inject(NearbyMarketActivity nearbyMarketActivity) {
        this.nearbyMarketActivityMembersInjector.injectMembers(nearbyMarketActivity);
    }

    public void inject(NutritionCalculationActivity nutritionCalculationActivity) {
        this.nutritionCalculationActivityMembersInjector.injectMembers(nutritionCalculationActivity);
    }

    public void inject(OfficialReleaseInfoActivity officialReleaseInfoActivity) {
        this.officialReleaseInfoActivityMembersInjector.injectMembers(officialReleaseInfoActivity);
    }

    public void inject(ProtocolActivity protocolActivity) {
        this.protocolActivityMembersInjector.injectMembers(protocolActivity);
    }

    public void inject(AboutMeActivity aboutMeActivity) {
        this.aboutMeActivityMembersInjector.injectMembers(aboutMeActivity);
    }

    public void inject(SampleDataActivity sampleDataActivity) {
        this.sampleDataActivityMembersInjector.injectMembers(sampleDataActivity);
    }

    public void inject(ImageGalleryActivity imageGalleryActivity) {
        this.imageGalleryActivityMembersInjector.injectMembers(imageGalleryActivity);
    }

    public void inject(SelectImageActivity selectImageActivity) {
        this.selectImageActivityMembersInjector.injectMembers(selectImageActivity);
    }

    public void inject(CropActivity cropActivity) {
        this.cropActivityMembersInjector.injectMembers(cropActivity);
    }

    public void inject(AddOrEditAddressActivity addOrEditAddressActivity) {
        this.addOrEditAddressActivityMembersInjector.injectMembers(addOrEditAddressActivity);
    }

    public void inject(AddressActivity addressActivity) {
        this.addressActivityMembersInjector.injectMembers(addressActivity);
    }

    public void inject(ShopCartActivity shopCartActivity) {
        this.shopCartActivityMembersInjector.injectMembers(shopCartActivity);
    }

    public void inject(OrderActivity orderActivity) {
        this.orderActivityMembersInjector.injectMembers(orderActivity);
    }

    public void inject(OrderEvaluationActivity evaluationActivity) {
        this.orderEvaluationActivityMembersInjector.injectMembers(evaluationActivity);
    }

    public void inject(PayActivity payActivity) {
        this.payActivityMembersInjector.injectMembers(payActivity);
    }

    public void inject(GoodsSearchActivity searchActivity) {
        this.goodsSearchActivityMembersInjector.injectMembers(searchActivity);
    }

    public void inject(MarketSearchActivity searchActivity) {
        this.marketSearchActivityMembersInjector.injectMembers(searchActivity);
    }

    public void inject(MarketDetailActivity searchActivity) {
        this.marketDetailActivityMembersInjector.injectMembers(searchActivity);
    }

    public void inject(DataUploadResultActivity dataUploadResultActivity) {
        this.dataUploadResultActivityMembersInjector.injectMembers(dataUploadResultActivity);
    }

    public void inject(VersionActivity versionActivity) {
        this.versionActivityMembersInjector.injectMembers(versionActivity);
    }

    public static final class Builder {
        private ActivityModule activityModule;
        /* access modifiers changed from: private */
        public ApplicationComponent applicationComponent;

        private Builder() {
        }

        public ActivityComponent build() {
            if (this.activityModule == null) {
                throw new IllegalStateException("activityModule must be set");
            } else if (this.applicationComponent != null) {
                return new DaggerActivityComponent(this);
            } else {
                throw new IllegalStateException("applicationComponent must be set");
            }
        }

        public Builder activityModule(ActivityModule activityModule2) {
            if (activityModule2 == null) {
                throw new NullPointerException("activityModule");
            }
            this.activityModule = activityModule2;
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

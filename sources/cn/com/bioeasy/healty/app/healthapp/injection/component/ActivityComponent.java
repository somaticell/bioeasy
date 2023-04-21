package cn.com.bioeasy.healty.app.healthapp.injection.component;

import cn.com.bioeasy.app.scope.PerActivity;
import cn.com.bioeasy.healty.app.healthapp.MainActivity;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.module.ActivityModule;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.ContentDetailActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.ContentSearchActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.HotInformationActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.MarketDetailActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.NearbyMarketActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.NutritionCalculationActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.OfficialReleaseInfoActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.AddOrEditAddressActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.AddressActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsSearchActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.OrderActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.OrderEvaluationActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.OrderExpressActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.PayActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.ProductsActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.ShopCartActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.AboutMeActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.BindOtherActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.BindingPhoneActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.DeviceSettingActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.EditNickNameActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.InfoActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.MyOrderActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.PresentActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.ProtocolActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.VersionActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.MarketSearchActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.TestDetailActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.BlueListActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.DataUploadResultActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.SampleDataActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.TestItemActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.TestItemCategoryActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.TestResultActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.ForgetPwdActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.LoginActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.RegisterActivity;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.CropActivity;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.ImageGalleryActivity;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.SelectImageActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(MainActivity mainActivity);

    void inject(BIBaseActivity bIBaseActivity);

    void inject(ContentDetailActivity contentDetailActivity);

    void inject(ContentSearchActivity contentSearchActivity);

    void inject(HotInformationActivity hotInformationActivity);

    void inject(MarketDetailActivity marketDetailActivity);

    void inject(NearbyMarketActivity nearbyMarketActivity);

    void inject(NutritionCalculationActivity nutritionCalculationActivity);

    void inject(OfficialReleaseInfoActivity officialReleaseInfoActivity);

    void inject(AddOrEditAddressActivity addOrEditAddressActivity);

    void inject(AddressActivity addressActivity);

    void inject(GoodsSearchActivity goodsSearchActivity);

    void inject(OrderActivity orderActivity);

    void inject(OrderEvaluationActivity orderEvaluationActivity);

    void inject(OrderExpressActivity orderExpressActivity);

    void inject(PayActivity payActivity);

    void inject(ProductsActivity productsActivity);

    void inject(ShopCartActivity shopCartActivity);

    void inject(AboutMeActivity aboutMeActivity);

    void inject(BindOtherActivity bindOtherActivity);

    void inject(BindingPhoneActivity bindingPhoneActivity);

    void inject(DeviceSettingActivity deviceSettingActivity);

    void inject(EditNickNameActivity editNickNameActivity);

    void inject(InfoActivity infoActivity);

    void inject(MyOrderActivity myOrderActivity);

    void inject(PresentActivity presentActivity);

    void inject(ProtocolActivity protocolActivity);

    void inject(VersionActivity versionActivity);

    void inject(MarketSearchActivity marketSearchActivity);

    void inject(TestDetailActivity testDetailActivity);

    void inject(BlueListActivity blueListActivity);

    void inject(DataUploadResultActivity dataUploadResultActivity);

    void inject(SampleDataActivity sampleDataActivity);

    void inject(TestItemActivity testItemActivity);

    void inject(TestItemCategoryActivity testItemCategoryActivity);

    void inject(TestResultActivity testResultActivity);

    void inject(ForgetPwdActivity forgetPwdActivity);

    void inject(LoginActivity loginActivity);

    void inject(RegisterActivity registerActivity);

    void inject(CropActivity cropActivity);

    void inject(ImageGalleryActivity imageGalleryActivity);

    void inject(SelectImageActivity selectImageActivity);
}

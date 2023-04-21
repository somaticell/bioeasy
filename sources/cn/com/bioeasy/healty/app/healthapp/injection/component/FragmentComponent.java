package cn.com.bioeasy.healty.app.healthapp.injection.component;

import cn.com.bioeasy.app.scope.PerFragment;
import cn.com.bioeasy.healty.app.healthapp.base.TabsFragment;
import cn.com.bioeasy.healty.app.healthapp.injection.module.FragmentModule;
import cn.com.bioeasy.healty.app.healthapp.modules.home.HomeFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.SafeInformationFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsCommentFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsDetailFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsInfoFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsListFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.ShoppingFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.MineFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.OrderStateFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.RecordAllFragmentTab;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.RecordFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.RecordFragmentTab;
import cn.com.bioeasy.healty.app.healthapp.modules.slidemenu.SlideMenuFragment;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.SelectFragment;
import dagger.Component;

@PerFragment
@Component(dependencies = {ApplicationComponent.class}, modules = {FragmentModule.class})
public interface FragmentComponent {
    void inject(TabsFragment tabsFragment);

    void inject(HomeFragment homeFragment);

    void inject(SafeInformationFragment safeInformationFragment);

    void inject(GoodsCommentFragment goodsCommentFragment);

    void inject(GoodsDetailFragment goodsDetailFragment);

    void inject(GoodsInfoFragment goodsInfoFragment);

    void inject(GoodsListFragment goodsListFragment);

    void inject(ShoppingFragment shoppingFragment);

    void inject(MineFragment mineFragment);

    void inject(OrderStateFragment orderStateFragment);

    void inject(RecordAllFragmentTab recordAllFragmentTab);

    void inject(RecordFragment recordFragment);

    void inject(RecordFragmentTab recordFragmentTab);

    void inject(SlideMenuFragment slideMenuFragment);

    void inject(SelectFragment selectFragment);
}

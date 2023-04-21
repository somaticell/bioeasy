package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.ShoppingFragment;
import com.flyco.tablayout.SlidingTabLayout;
import com.jude.rollviewpager.RollPagerView;

public class ShoppingFragment$$ViewBinder<T extends ShoppingFragment> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.mTabLayout = (SlidingTabLayout) finder.castView((View) finder.findRequiredView(source, R.id.s_tabLayout, "field 'mTabLayout'"), R.id.s_tabLayout, "field 'mTabLayout'");
        target.rollView = (RollPagerView) finder.castView((View) finder.findRequiredView(source, R.id.roll_view_pager, "field 'rollView'"), R.id.roll_view_pager, "field 'rollView'");
        target.mViewPage = (ViewPager) finder.castView((View) finder.findRequiredView(source, R.id.view_pager, "field 'mViewPage'"), R.id.view_pager, "field 'mViewPage'");
        ((View) finder.findRequiredView(source, R.id.iv_shop_cart, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.fl_search_layout, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.tv_my_order, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.mTabLayout = null;
        target.rollView = null;
        target.mViewPage = null;
    }
}

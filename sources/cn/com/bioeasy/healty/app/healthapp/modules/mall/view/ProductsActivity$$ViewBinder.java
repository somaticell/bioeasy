package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.ProductsActivity;
import cn.com.bioeasy.healty.app.healthapp.widgets.NoScrollViewPager;
import com.gxz.PagerSlidingTabStrip;

public class ProductsActivity$$ViewBinder<T extends ProductsActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.tvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_titles, "field 'tvTitle'"), R.id.tv_titles, "field 'tvTitle'");
        View view = (View) finder.findRequiredView(source, R.id.rl_addshopping_cart, "field 'rlAddShoppingCart' and method 'onClick'");
        target.rlAddShoppingCart = (RelativeLayout) finder.castView(view, R.id.rl_addshopping_cart, "field 'rlAddShoppingCart'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        target.psts_tabs = (PagerSlidingTabStrip) finder.castView((View) finder.findRequiredView(source, R.id.psts_tabs, "field 'psts_tabs'"), R.id.psts_tabs, "field 'psts_tabs'");
        target.vp_content = (NoScrollViewPager) finder.castView((View) finder.findRequiredView(source, R.id.vp_content, "field 'vp_content'"), R.id.vp_content, "field 'vp_content'");
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.rl_shopping_cart, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.rl_buy, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.tvTitle = null;
        target.rlAddShoppingCart = null;
        target.psts_tabs = null;
        target.vp_content = null;
    }
}

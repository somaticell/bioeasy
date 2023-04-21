package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.MyOrderActivity;
import com.flyco.tablayout.SlidingTabLayout;

public class MyOrderActivity$$ViewBinder<T extends MyOrderActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.mTabLayout = (SlidingTabLayout) finder.castView((View) finder.findRequiredView(source, R.id.s_tabLayout, "field 'mTabLayout'"), R.id.s_tabLayout, "field 'mTabLayout'");
        target.mViewPage = (ViewPager) finder.castView((View) finder.findRequiredView(source, R.id.view_pager, "field 'mViewPage'"), R.id.view_pager, "field 'mViewPage'");
        target.mTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'mTitle'"), R.id.tv_title, "field 'mTitle'");
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'OnClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.mTabLayout = null;
        target.mViewPage = null;
        target.mTitle = null;
    }
}

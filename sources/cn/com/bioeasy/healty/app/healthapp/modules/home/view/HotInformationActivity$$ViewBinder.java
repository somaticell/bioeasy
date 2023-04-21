package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.HotInformationActivity;
import com.flyco.tablayout.SlidingTabLayout;

public class HotInformationActivity$$ViewBinder<T extends HotInformationActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.tvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'tvTitle'"), R.id.tv_title, "field 'tvTitle'");
        target.mTabLayout = (SlidingTabLayout) finder.castView((View) finder.findRequiredView(source, R.id.stb_order, "field 'mTabLayout'"), R.id.stb_order, "field 'mTabLayout'");
        target.mViewPage = (ViewPager) finder.castView((View) finder.findRequiredView(source, R.id.view_pager, "field 'mViewPage'"), R.id.view_pager, "field 'mViewPage'");
        View view = (View) finder.findRequiredView(source, R.id.iv_search, "field 'ivSearch' and method 'onClick'");
        target.ivSearch = (ImageView) finder.castView(view, R.id.iv_search, "field 'ivSearch'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.tvTitle = null;
        target.mTabLayout = null;
        target.mViewPage = null;
        target.ivSearch = null;
    }
}

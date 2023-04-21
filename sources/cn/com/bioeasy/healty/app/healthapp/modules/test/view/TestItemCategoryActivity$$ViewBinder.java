package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.TestItemCategoryActivity;

public class TestItemCategoryActivity$$ViewBinder<T extends TestItemCategoryActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.tvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'tvTitle'"), R.id.tv_title, "field 'tvTitle'");
        target.currentDeviceName = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_current_bluename, "field 'currentDeviceName'"), R.id.tv_current_bluename, "field 'currentDeviceName'");
        target.recyclerView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.strip_category_grid, "field 'recyclerView'"), R.id.strip_category_grid, "field 'recyclerView'");
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'onClicks'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClicks(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.bt_change, "method 'onClicks'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClicks(p0);
            }
        });
    }

    public void unbind(T target) {
        target.tvTitle = null;
        target.currentDeviceName = null;
        target.recyclerView = null;
    }
}

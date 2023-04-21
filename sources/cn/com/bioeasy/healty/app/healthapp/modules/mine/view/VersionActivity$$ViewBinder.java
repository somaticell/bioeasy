package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.VersionActivity;

public class VersionActivity$$ViewBinder<T extends VersionActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.mtvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'mtvTitle'"), R.id.tv_title, "field 'mtvTitle'");
        target.recyclerView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.lv_history_list, "field 'recyclerView'"), R.id.lv_history_list, "field 'recyclerView'");
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'OnClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.mtvTitle = null;
        target.recyclerView = null;
    }
}

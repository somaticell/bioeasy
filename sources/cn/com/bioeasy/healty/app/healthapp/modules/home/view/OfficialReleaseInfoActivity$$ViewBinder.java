package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.OfficialReleaseInfoActivity;
import cn.com.bioeasy.healty.app.healthapp.widgets.EmptyLayout;
import com.chanven.lib.cptr.PtrClassicFrameLayout;

public class OfficialReleaseInfoActivity$$ViewBinder<T extends OfficialReleaseInfoActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.mRlView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.rl_view, "field 'mRlView'"), R.id.rl_view, "field 'mRlView'");
        target.tvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'tvTitle'"), R.id.tv_title, "field 'tvTitle'");
        target.ptrClassicFrameLayout = (PtrClassicFrameLayout) finder.castView((View) finder.findRequiredView(source, R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'"), R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'");
        target.emptyLayout = (EmptyLayout) finder.castView((View) finder.findRequiredView(source, R.id.lay_error, "field 'emptyLayout'"), R.id.lay_error, "field 'emptyLayout'");
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.mRlView = null;
        target.tvTitle = null;
        target.ptrClassicFrameLayout = null;
        target.emptyLayout = null;
    }
}

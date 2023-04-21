package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.MarketDetailActivity;
import com.chanven.lib.cptr.PtrClassicFrameLayout;

public class MarketDetailActivity$$ViewBinder<T extends MarketDetailActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.mTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'mTitle'"), R.id.tv_title, "field 'mTitle'");
        target.ptrClassicFrameLayout = (PtrClassicFrameLayout) finder.castView((View) finder.findRequiredView(source, R.id.market_frame_view, "field 'ptrClassicFrameLayout'"), R.id.market_frame_view, "field 'ptrClassicFrameLayout'");
        target.recyclerView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.lv_sample_result, "field 'recyclerView'"), R.id.lv_sample_result, "field 'recyclerView'");
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.mTitle = null;
        target.ptrClassicFrameLayout = null;
        target.recyclerView = null;
    }
}

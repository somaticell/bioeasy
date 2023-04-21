package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.NearbyMarketActivity;
import com.chanven.lib.cptr.PtrClassicFrameLayout;

public class NearbyMarketActivity$$ViewBinder<T extends NearbyMarketActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        View view = (View) finder.findRequiredView(source, R.id.fl_search_layout, "field 'searchView' and method 'onClick'");
        target.searchView = (FrameLayout) finder.castView(view, R.id.fl_search_layout, "field 'searchView'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        target.ptrClassicFrameLayout = (PtrClassicFrameLayout) finder.castView((View) finder.findRequiredView(source, R.id.market_frame_view, "field 'ptrClassicFrameLayout'"), R.id.market_frame_view, "field 'ptrClassicFrameLayout'");
        target.recyclerView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.lv_market, "field 'recyclerView'"), R.id.lv_market, "field 'recyclerView'");
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.searchView = null;
        target.ptrClassicFrameLayout = null;
        target.recyclerView = null;
    }
}

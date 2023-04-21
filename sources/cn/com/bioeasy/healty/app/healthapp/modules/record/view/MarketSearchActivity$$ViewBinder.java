package cn.com.bioeasy.healty.app.healthapp.modules.record.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import cn.com.bioeasy.app.widgets.SearchView;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.MarketSearchActivity;
import com.chanven.lib.cptr.PtrClassicFrameLayout;

public class MarketSearchActivity$$ViewBinder<T extends MarketSearchActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, T target, Object source) {
        target.mSearchView = (SearchView) finder.castView((View) finder.findRequiredView(source, R.id.search_layout, "field 'mSearchView'"), R.id.search_layout, "field 'mSearchView'");
        target.mlvResult = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.lv_search_results, "field 'mlvResult'"), R.id.lv_search_results, "field 'mlvResult'");
        target.mlvHotSearchPanel = (LinearLayout) finder.castView((View) finder.findRequiredView(source, R.id.lv_hot_search_panel, "field 'mlvHotSearchPanel'"), R.id.lv_hot_search_panel, "field 'mlvHotSearchPanel'");
        target.mlvHotSearch = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.lv_hot_search, "field 'mlvHotSearch'"), R.id.lv_hot_search, "field 'mlvHotSearch'");
        target.ptrClassicFrameLayout = (PtrClassicFrameLayout) finder.castView((View) finder.findRequiredView(source, R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'"), R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'");
        target.hostSearch = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_hot_search, "field 'hostSearch'"), R.id.tv_hot_search, "field 'hostSearch'");
    }

    public void unbind(T target) {
        target.mSearchView = null;
        target.mlvResult = null;
        target.mlvHotSearchPanel = null;
        target.mlvHotSearch = null;
        target.ptrClassicFrameLayout = null;
        target.hostSearch = null;
    }
}

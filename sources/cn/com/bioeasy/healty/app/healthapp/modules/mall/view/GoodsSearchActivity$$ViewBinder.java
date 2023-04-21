package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import cn.com.bioeasy.app.widgets.SearchView;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsSearchActivity;
import com.chanven.lib.cptr.PtrClassicFrameLayout;

public class GoodsSearchActivity$$ViewBinder<T extends GoodsSearchActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, T target, Object source) {
        target.mSearchView = (SearchView) finder.castView((View) finder.findRequiredView(source, R.id.search_layout, "field 'mSearchView'"), R.id.search_layout, "field 'mSearchView'");
        target.mlvResult = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.lv_search_results, "field 'mlvResult'"), R.id.lv_search_results, "field 'mlvResult'");
        target.mlvHotSearchPanel = (LinearLayout) finder.castView((View) finder.findRequiredView(source, R.id.lv_hot_search_panel, "field 'mlvHotSearchPanel'"), R.id.lv_hot_search_panel, "field 'mlvHotSearchPanel'");
        target.mlvHotSearch = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.lv_hot_search, "field 'mlvHotSearch'"), R.id.lv_hot_search, "field 'mlvHotSearch'");
        target.ptrClassicFrameLayout = (PtrClassicFrameLayout) finder.castView((View) finder.findRequiredView(source, R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'"), R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'");
    }

    public void unbind(T target) {
        target.mSearchView = null;
        target.mlvResult = null;
        target.mlvHotSearchPanel = null;
        target.mlvHotSearch = null;
        target.ptrClassicFrameLayout = null;
    }
}

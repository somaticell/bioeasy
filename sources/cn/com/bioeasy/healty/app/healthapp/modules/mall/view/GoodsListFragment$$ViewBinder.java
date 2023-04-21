package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsListFragment;
import cn.com.bioeasy.healty.app.healthapp.widgets.EmptyLayout;
import com.chanven.lib.cptr.PtrClassicFrameLayout;

public class GoodsListFragment$$ViewBinder<T extends GoodsListFragment> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, T target, Object source) {
        target.mRecyclerView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.recyclerview, "field 'mRecyclerView'"), R.id.recyclerview, "field 'mRecyclerView'");
        target.emptyLayout = (EmptyLayout) finder.castView((View) finder.findRequiredView(source, R.id.lay_error, "field 'emptyLayout'"), R.id.lay_error, "field 'emptyLayout'");
        target.ptrClassicFrameLayout = (PtrClassicFrameLayout) finder.castView((View) finder.findRequiredView(source, R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'"), R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'");
    }

    public void unbind(T target) {
        target.mRecyclerView = null;
        target.emptyLayout = null;
        target.ptrClassicFrameLayout = null;
    }
}

package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.SafeInformationFragment;
import cn.com.bioeasy.healty.app.healthapp.widgets.EmptyLayout;
import com.chanven.lib.cptr.PtrClassicFrameLayout;

public class SafeInformationFragment$$ViewBinder<T extends SafeInformationFragment> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, T target, Object source) {
        target.mrlView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.recyclerview, "field 'mrlView'"), R.id.recyclerview, "field 'mrlView'");
        target.ptrClassicFrameLayout = (PtrClassicFrameLayout) finder.castView((View) finder.findRequiredView(source, R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'"), R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'");
        target.emptyLayout = (EmptyLayout) finder.castView((View) finder.findRequiredView(source, R.id.lay_error, "field 'emptyLayout'"), R.id.lay_error, "field 'emptyLayout'");
    }

    public void unbind(T target) {
        target.mrlView = null;
        target.ptrClassicFrameLayout = null;
        target.emptyLayout = null;
    }
}

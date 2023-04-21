package cn.com.bioeasy.healty.app.healthapp.modules.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.home.HomeFragment;
import com.chanven.lib.cptr.PtrClassicFrameLayout;

public class HomeFragment$$ViewBinder<T extends HomeFragment> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, T target, Object source) {
        target.mrlView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.recyclerview, "field 'mrlView'"), R.id.recyclerview, "field 'mrlView'");
        target.ptrClassicFrameLayout = (PtrClassicFrameLayout) finder.castView((View) finder.findRequiredView(source, R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'"), R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'");
    }

    public void unbind(T target) {
        target.mrlView = null;
        target.ptrClassicFrameLayout = null;
    }
}

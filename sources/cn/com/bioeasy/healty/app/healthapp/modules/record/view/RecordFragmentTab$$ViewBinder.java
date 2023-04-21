package cn.com.bioeasy.healty.app.healthapp.modules.record.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TableLayout;
import butterknife.ButterKnife;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.RecordFragmentTab;
import com.chanven.lib.cptr.PtrClassicFrameLayout;

public class RecordFragmentTab$$ViewBinder<T extends RecordFragmentTab> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, T target, Object source) {
        target.ptrClassicFrameLayout = (PtrClassicFrameLayout) finder.castView((View) finder.findRequiredView(source, R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'"), R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'");
        target.recyclerView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.rl_view, "field 'recyclerView'"), R.id.rl_view, "field 'recyclerView'");
        target.tableLayout = (TableLayout) finder.castView((View) finder.findRequiredView(source, R.id.table, "field 'tableLayout'"), R.id.table, "field 'tableLayout'");
    }

    public void unbind(T target) {
        target.ptrClassicFrameLayout = null;
        target.recyclerView = null;
        target.tableLayout = null;
    }
}

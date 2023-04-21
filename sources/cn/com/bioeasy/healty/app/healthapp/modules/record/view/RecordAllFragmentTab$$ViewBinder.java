package cn.com.bioeasy.healty.app.healthapp.modules.record.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import butterknife.ButterKnife;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.RecordAllFragmentTab;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import lecho.lib.hellocharts.view.PieChartView;

public class RecordAllFragmentTab$$ViewBinder<T extends RecordAllFragmentTab> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, T target, Object source) {
        target.chart = (PieChartView) finder.castView((View) finder.findRequiredView(source, R.id.chart, "field 'chart'"), R.id.chart, "field 'chart'");
        target.mrlPic = (RelativeLayout) finder.castView((View) finder.findRequiredView(source, R.id.rl_pic, "field 'mrlPic'"), R.id.rl_pic, "field 'mrlPic'");
        target.ptrClassicFrameLayout = (PtrClassicFrameLayout) finder.castView((View) finder.findRequiredView(source, R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'"), R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'");
        target.recyclerView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.rl_view, "field 'recyclerView'"), R.id.rl_view, "field 'recyclerView'");
        target.tableLayout = (TableLayout) finder.castView((View) finder.findRequiredView(source, R.id.table, "field 'tableLayout'"), R.id.table, "field 'tableLayout'");
        target.testCategoryView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.test_category_list, "field 'testCategoryView'"), R.id.test_category_list, "field 'testCategoryView'");
    }

    public void unbind(T target) {
        target.chart = null;
        target.mrlPic = null;
        target.ptrClassicFrameLayout = null;
        target.recyclerView = null;
        target.tableLayout = null;
        target.testCategoryView = null;
    }
}

package cn.com.bioeasy.healty.app.healthapp.modules.record.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.RecordFragment;
import com.chanven.lib.cptr.PtrClassicFrameLayout;

public class RecordFragment$$ViewBinder<T extends RecordFragment> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.mTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'mTitle'"), R.id.tv_title, "field 'mTitle'");
        target.mIvBack = (ImageView) finder.castView((View) finder.findRequiredView(source, R.id.iv_back, "field 'mIvBack'"), R.id.iv_back, "field 'mIvBack'");
        View view = (View) finder.findRequiredView(source, R.id.id_tv_export_record, "field 'exportRecord' and method 'onClick'");
        target.exportRecord = (TextView) finder.castView(view, R.id.id_tv_export_record, "field 'exportRecord'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        target.testTotalCount = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_test_total_count, "field 'testTotalCount'"), R.id.tv_test_total_count, "field 'testTotalCount'");
        target.testNgCount = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_test_ng_count, "field 'testNgCount'"), R.id.tv_test_ng_count, "field 'testNgCount'");
        target.testOkCount = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_test_ok_count, "field 'testOkCount'"), R.id.tv_test_ok_count, "field 'testOkCount'");
        target.testRecordDays = (Spinner) finder.castView((View) finder.findRequiredView(source, R.id.tv_record_days, "field 'testRecordDays'"), R.id.tv_record_days, "field 'testRecordDays'");
        target.ptrClassicFrameLayout = (PtrClassicFrameLayout) finder.castView((View) finder.findRequiredView(source, R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'"), R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'");
        target.recyclerView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.rl_view, "field 'recyclerView'"), R.id.rl_view, "field 'recyclerView'");
        target.tableLayout = (TableLayout) finder.castView((View) finder.findRequiredView(source, R.id.table, "field 'tableLayout'"), R.id.table, "field 'tableLayout'");
    }

    public void unbind(T target) {
        target.mTitle = null;
        target.mIvBack = null;
        target.exportRecord = null;
        target.testTotalCount = null;
        target.testNgCount = null;
        target.testOkCount = null;
        target.testRecordDays = null;
        target.ptrClassicFrameLayout = null;
        target.recyclerView = null;
        target.tableLayout = null;
    }
}

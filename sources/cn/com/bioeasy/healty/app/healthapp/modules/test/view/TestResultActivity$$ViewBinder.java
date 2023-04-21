package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BatteryState;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.TestResultActivity;
import lecho.lib.hellocharts.view.LineChartView;

public class TestResultActivity$$ViewBinder<T extends TestResultActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.tvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'tvTitle'"), R.id.tv_title, "field 'tvTitle'");
        target.lineChart = (LineChartView) finder.castView((View) finder.findRequiredView(source, R.id.line_chart, "field 'lineChart'"), R.id.line_chart, "field 'lineChart'");
        target.mTvCheckResult = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_test_result, "field 'mTvCheckResult'"), R.id.tv_test_result, "field 'mTvCheckResult'");
        target.mTestCategory = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_test_category, "field 'mTestCategory'"), R.id.tv_test_category, "field 'mTestCategory'");
        target.mTestItem = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_test_item, "field 'mTestItem'"), R.id.tv_test_item, "field 'mTestItem'");
        View view = (View) finder.findRequiredView(source, R.id.bt_input_data, "field 'uploadBtn' and method 'onClicks'");
        target.uploadBtn = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClicks(p0);
            }
        });
        View view2 = (View) finder.findRequiredView(source, R.id.bt_three_check, "field 'checkedBtn' and method 'onClicks'");
        target.checkedBtn = view2;
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClicks(p0);
            }
        });
        View view3 = (View) finder.findRequiredView(source, R.id.ll_test_ng_tip, "field 'testNgTip' and method 'onClicks'");
        target.testNgTip = (LinearLayout) finder.castView(view3, R.id.ll_test_ng_tip, "field 'testNgTip'");
        view3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClicks(p0);
            }
        });
        View view4 = (View) finder.findRequiredView(source, R.id.bt_test_again, "field 'testAgain' and method 'onClicks'");
        target.testAgain = view4;
        view4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClicks(p0);
            }
        });
        target.txtSampleNo = (EditText) finder.castView((View) finder.findRequiredView(source, R.id.id_sample_no, "field 'txtSampleNo'"), R.id.id_sample_no, "field 'txtSampleNo'");
        target.powerStatus = (BatteryState) finder.castView((View) finder.findRequiredView(source, R.id.bs_power, "field 'powerStatus'"), R.id.bs_power, "field 'powerStatus'");
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'onClicks'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClicks(p0);
            }
        });
    }

    public void unbind(T target) {
        target.tvTitle = null;
        target.lineChart = null;
        target.mTvCheckResult = null;
        target.mTestCategory = null;
        target.mTestItem = null;
        target.uploadBtn = null;
        target.checkedBtn = null;
        target.testNgTip = null;
        target.testAgain = null;
        target.txtSampleNo = null;
        target.powerStatus = null;
    }
}

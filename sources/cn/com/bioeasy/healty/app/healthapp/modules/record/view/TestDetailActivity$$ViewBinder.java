package cn.com.bioeasy.healty.app.healthapp.modules.record.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.TestDetailActivity;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.widget.TweetPicturesPreviewer;
import lecho.lib.hellocharts.view.LineChartView;

public class TestDetailActivity$$ViewBinder<T extends TestDetailActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.mTvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'mTvTitle'"), R.id.tv_title, "field 'mTvTitle'");
        target.mSampleName = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_sample_name, "field 'mSampleName'"), R.id.tv_sample_name, "field 'mSampleName'");
        target.mTestResult = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_test_result, "field 'mTestResult'"), R.id.tv_test_result, "field 'mTestResult'");
        target.mTestDate = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_date, "field 'mTestDate'"), R.id.tv_date, "field 'mTestDate'");
        target.mAddress = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_address, "field 'mAddress'"), R.id.tv_address, "field 'mAddress'");
        target.mRemark = (EditText) finder.castView((View) finder.findRequiredView(source, R.id.tv_remark, "field 'mRemark'"), R.id.tv_remark, "field 'mRemark'");
        target.mTestItems = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_test_items, "field 'mTestItems'"), R.id.tv_test_items, "field 'mTestItems'");
        target.mTestItemsValue = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_test_items_value, "field 'mTestItemsValue'"), R.id.tv_test_items_value, "field 'mTestItemsValue'");
        View view = (View) finder.findRequiredView(source, R.id.test_upload_btn, "field 'uploadBtn' and method 'OnClick'");
        target.uploadBtn = (Button) finder.castView(view, R.id.test_upload_btn, "field 'uploadBtn'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        target.mLayImages = (TweetPicturesPreviewer) finder.castView((View) finder.findRequiredView(source, R.id.recycler_images, "field 'mLayImages'"), R.id.recycler_images, "field 'mLayImages'");
        View view2 = (View) finder.findRequiredView(source, R.id.iv_picture, "field 'ivAddImage' and method 'OnClick'");
        target.ivAddImage = (ImageView) finder.castView(view2, R.id.iv_picture, "field 'ivAddImage'");
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        target.lineChart = (LineChartView) finder.castView((View) finder.findRequiredView(source, R.id.line_chart, "field 'lineChart'"), R.id.line_chart, "field 'lineChart'");
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'OnClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.mTvTitle = null;
        target.mSampleName = null;
        target.mTestResult = null;
        target.mTestDate = null;
        target.mAddress = null;
        target.mRemark = null;
        target.mTestItems = null;
        target.mTestItemsValue = null;
        target.uploadBtn = null;
        target.mLayImages = null;
        target.ivAddImage = null;
        target.lineChart = null;
    }
}

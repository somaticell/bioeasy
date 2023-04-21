package cn.com.bioeasy.healty.app.healthapp.modules.record.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.SampleData;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.SampleItemData;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestResultItem;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.UploadDataCallback;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.SamplePresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.SampleView;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.widget.TweetPicturesPreviewer;
import cn.com.bioeasy.healty.app.healthapp.widgets.hellochart.HelloChartManager;
import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import lecho.lib.hellocharts.view.LineChartView;
import org.litepal.crud.DataSupport;

public class TestDetailActivity extends BIBaseActivity implements SampleView {
    @Inject
    HelloChartManager helloChartManager;
    @Bind({2131230956})
    ImageView ivAddImage;
    @Bind({2131231059})
    LineChartView lineChart;
    @Bind({2131231070})
    TextView mAddress;
    @Bind({2131230955})
    TweetPicturesPreviewer mLayImages;
    @Bind({2131231071})
    EditText mRemark;
    @Bind({2131231061})
    TextView mSampleName;
    @Bind({2131231062})
    TextView mTestDate;
    @Bind({2131231066})
    TextView mTestItems;
    @Bind({2131231069})
    TextView mTestItemsValue;
    @Bind({2131231063})
    TextView mTestResult;
    @Bind({2131231008})
    TextView mTvTitle;
    private BroadcastReceiver receiver;
    /* access modifiers changed from: private */
    public SampleData sampleData;
    @Inject
    SamplePresenter samplePresenter;
    @Bind({2131231072})
    Button uploadBtn;

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_record_detail;
    }

    @OnClick({2131231050, 2131230956, 2131231072})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.iv_picture:
                this.mLayImages.onLoadMoreClick();
                return;
            case R.id.iv_back:
                finish();
                return;
            case R.id.test_upload_btn:
                this.samplePresenter.uploadData(this.sampleData.getId(), this.sampleData.getMarketName(), this.sampleData.getSampleName(), this.mRemark.getText().toString(), this.mLayImages.getPaths());
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 1:
                this.uploadBtn.setVisibility(8);
                initViewsAndEvents();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        List<TestResultItem> dataList;
        this.mTvTitle.setText(getResources().getString(R.string.record_detail_title));
        int sampleId = getIntent().getIntExtra(IntentActions.SAMPLE_ID, 0);
        if (sampleId > 0) {
            this.sampleData = (SampleData) DataSupport.find(SampleData.class, (long) sampleId);
            if (this.sampleData != null) {
                this.sampleData.setItemDataList(DataSupport.where("sampledata_id = ? ", String.valueOf(sampleId)).find(SampleItemData.class));
            }
        }
        String[] resultStr = {"<font color=\"#fcb813\">" + getString(R.string.weak_positive) + "</font>", "<font color=\"#3bde86\">" + getString(R.string.negative_one) + "</font>", "<font color=\"#ff6260\">" + getString(R.string.positive_one) + "</font>"};
        if (this.sampleData != null) {
            if (this.sampleData.getStatus() == 1) {
                this.uploadBtn.setVisibility(8);
            }
            String sampleName = this.sampleData.getSampleName();
            if (sampleName == null) {
                sampleName = "";
            }
            this.mSampleName.setText(getString(R.string.sample_one) + sampleName);
            this.mTestDate.setText(getString(R.string.test_time) + this.sampleData.getTime());
            this.mTestResult.setText(Html.fromHtml(getString(R.string.test_result_one) + " " + resultStr[this.sampleData.getResult()]));
            this.mAddress.setText(this.sampleData.getAddress());
            if (this.sampleData.getRemark() != null) {
                this.mRemark.setText(this.sampleData.getRemark());
            }
            List<SampleItemData> itemDataList = this.sampleData.getItemDataList();
            if (itemDataList != null) {
                String testItemStr = "";
                for (int i = 0; i < itemDataList.size(); i++) {
                    SampleItemData itemData = itemDataList.get(i);
                    testItemStr = testItemStr + " " + itemData.getItemName() + " " + itemData.getValue() + " " + resultStr[itemData.getResult()];
                    if (i < itemDataList.size() - 1) {
                        testItemStr = testItemStr + "<br/>";
                    }
                }
                this.mTestItems.setText(Html.fromHtml(testItemStr));
            }
            String resultList = this.sampleData.getResultList();
            if (StringUtil.isNullOrEmpty(resultList)) {
                dataList = new ArrayList<>();
            } else {
                dataList = JSON.parseArray(resultList, TestResultItem.class);
            }
            this.helloChartManager.initData(getApplicationContext(), this.lineChart, dataList, (TestResultItem) null);
            String images = this.sampleData.getImages();
            if (!StringUtil.isNullOrEmpty(images)) {
                this.mLayImages.set(images.split("\\|"));
                this.ivAddImage.setVisibility(8);
            }
            this.receiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    if (ActionConstant.ACTION_UPLOAD.equals(intent.getAction())) {
                        int counts = intent.getIntExtra("selected", 0);
                        if (counts > 0 || counts == -1) {
                            TestDetailActivity.this.ivAddImage.setVisibility(8);
                        } else {
                            TestDetailActivity.this.ivAddImage.setVisibility(0);
                        }
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ActionConstant.ACTION_UPLOAD);
            registerReceiver(this.receiver, intentFilter);
        }
    }

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return this.samplePresenter;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.receiver != null) {
            unregisterReceiver(this.receiver);
        }
    }

    public static void startAction(Activity activity) {
    }

    public void uploadDateResult(final UploadDataCallback cb) {
        runOnUiThread(new Runnable() {
            public void run() {
                boolean z = true;
                if (TestDetailActivity.this.sampleData == null) {
                    return;
                }
                if (cb.getResult() == 0) {
                    TestDetailActivity.this.uploadBtn.setVisibility(8);
                    EditText editText = TestDetailActivity.this.mRemark;
                    if (TestDetailActivity.this.sampleData.getStatus() == 1) {
                        z = false;
                    }
                    editText.setEnabled(z);
                    TestDetailActivity.this.ivAddImage.setVisibility(8);
                    TestDetailActivity.this.showMessage((int) R.string.data_upload_success);
                    return;
                }
                TestDetailActivity.this.showMessage((int) R.string.data_upload_failure);
            }
        });
    }
}

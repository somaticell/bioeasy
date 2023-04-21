package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.base.BatteryState;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import cn.com.bioeasy.healty.app.healthapp.ble.IBLEResponse;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestResultItem;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStrip;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStripCategory;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.UploadDataCallback;
import cn.com.bioeasy.healty.app.healthapp.widgets.hellochart.HelloChartManager;
import com.alibaba.fastjson.JSON;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import lecho.lib.hellocharts.view.LineChartView;

public class TestResultActivity extends BIBaseActivity implements IBLEResponse, SampleView {
    @Inject
    BLERepository bleRepository;
    @Bind({2131231143})
    View checkedBtn;
    @Inject
    HelloChartManager helloChartManager;
    @Bind({2131231059})
    LineChartView lineChart;
    private int localSampleId = 0;
    @Bind({2131231136})
    TextView mTestCategory;
    @Bind({2131231137})
    TextView mTestItem;
    @Bind({2131231063})
    TextView mTvCheckResult;
    @Bind({2131231176})
    BatteryState powerStatus;
    /* access modifiers changed from: private */
    public ArrayList<TestResultItem> resultItemList;
    @Inject
    SamplePresenter samplePresenter;
    @Bind({2131231142})
    View testAgain;
    @Bind({2131231139})
    LinearLayout testNgTip;
    private int testResult;
    /* access modifiers changed from: private */
    public TestStrip testStrip;
    @Bind({2131231008})
    TextView tvTitle;
    @Bind({2131231138})
    EditText txtSampleNo;
    @Bind({2131231141})
    View uploadBtn;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return this.samplePresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_test_result;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        initView();
    }

    private void initView() {
        this.tvTitle.setText(R.string.tv_blue_check_result);
        this.testStrip = HealthApp.x.getDataManager().getTestStrip(getIntent().getIntExtra(TestItemActivity.TEST_STRIP_ID, 0));
        if (this.testStrip != null) {
            this.mTestItem.setText(getString(R.string.detection_project_one) + this.testStrip.getName());
            TestStripCategory category = HealthApp.x.getDataManager().getTestStripCategory(this.testStrip.getCategoryId().intValue());
            if (category != null) {
                this.mTestCategory.setText(getString(R.string.detection_category) + category.getName());
            }
        }
        this.bleRepository.addBLEResponse((byte) 1, this);
        this.bleRepository.addBLEResponse((byte) 2, this);
        this.helloChartManager.initData(getApplicationContext(), this.lineChart, new ArrayList(), (List<TestResultItem>) null, (TestResultItem) null);
        this.powerStatus.setVisibility(0);
        this.bleRepository.addBLEResponse(Byte.valueOf(BLEServiceApi.POWER_STATUS_CMD), this);
        this.bleRepository.sendPowerStatusCmd();
    }

    public void onResponse(final Byte cmd, final String[] hexData) {
        runOnUiThread(new Runnable() {
            public void run() {
                float per;
                if (cmd.byteValue() == 38 && BLEServiceApi.RESULT_OK.equals(hexData[0])) {
                    int powerValue = Integer.parseInt(hexData[1] + hexData[2], 16);
                    if (powerValue <= 1800) {
                        per = 0.0f;
                    } else {
                        per = ((float) (((double) (powerValue - 1800)) * 1.0d)) / 1800.0f;
                    }
                    TestResultActivity.this.powerStatus.refreshPower(per);
                }
                if (TestResultActivity.this.testStrip == null) {
                    TestResultActivity.this.hideProgress();
                    TestResultActivity.this.showMessage((int) R.string.no_found_project);
                } else if (hexData == null || hexData.length == 0) {
                    TestResultActivity.this.hideProgress();
                    TestResultActivity.this.showMessage((int) R.string.get_result_fail);
                } else if (hexData.length == 1 && hexData[0].equals(BLEServiceApi.NOT_CONNECTED)) {
                    TestResultActivity.this.hideProgress();
                    TestResultActivity.this.showMessage((int) R.string.current_unconnected);
                } else if (cmd.byteValue() == 1) {
                    if (!BLEServiceApi.RESULT_OK.equals(hexData[0])) {
                        TestResultActivity.this.hideProgress();
                        TestResultActivity.this.showMessage((int) R.string.get_result_fail);
                        return;
                    }
                    TestResultItem unused = TestResultActivity.this.showTestResut(hexData, 1);
                    TestResultActivity.this.hideProgress();
                } else if (cmd.byteValue() == 2) {
                    TestResultActivity.this.hideProgress();
                    if (!BLEServiceApi.RESULT_OK.equals(hexData[0])) {
                        TestResultActivity.this.showMessage((int) R.string.get_result_fail);
                        return;
                    }
                    List<Integer> dataList = new ArrayList<>();
                    int i = 1;
                    while (i < hexData.length) {
                        i++;
                        dataList.add(Integer.valueOf(Integer.parseInt(hexData[i] + hexData[i + 1], 16)));
                        if (dataList.size() == 1536) {
                            break;
                        }
                        i++;
                    }
                    TestResultActivity.this.helloChartManager.initData(TestResultActivity.this.getApplicationContext(), TestResultActivity.this.lineChart, dataList, TestResultActivity.this.resultItemList, TestResultActivity.this.showTestResut(hexData, i + 1));
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public TestResultItem showTestResut(String[] hexData, int index) {
        this.resultItemList = new ArrayList<>();
        int i = index;
        while (i < hexData.length) {
            String positionStr = hexData[i] + hexData[i + 1];
            int i2 = i + 2;
            String heightStr = hexData[i2] + hexData[i2 + 1];
            int i3 = i2 + 2;
            TestResultItem item = new TestResultItem();
            item.setPosition(Integer.valueOf(Integer.parseInt(positionStr, 16)));
            item.setHeight(Integer.valueOf(Integer.parseInt(heightStr, 16)));
            item.setArea(Integer.valueOf(Integer.parseInt(hexData[i3] + hexData[i3 + 1] + hexData[i3 + 2], 16)));
            this.resultItemList.add(item);
            i = i3 + 2 + 1;
        }
        String dataStr = JSON.toJSONString(this.resultItemList);
        TestResultItem ctResultItem = this.testStrip.getCtDirection().intValue() == 1 ? this.resultItemList.remove(this.resultItemList.size() - 1) : this.resultItemList.remove(0);
        int totalResult = 0;
        for (int i4 = 0; i4 < this.resultItemList.size(); i4++) {
            TestResultItem testResultItem = this.resultItemList.get(i4);
            float positiveVal = this.testStrip.getPositiveValue().floatValue();
            float negativeVal = this.testStrip.getNegativeValue().floatValue();
            double compareVal = 0.0d;
            if (this.testStrip.getJudgeType().intValue() == 1) {
                compareVal = ctResultItem.getArea().intValue() > 0 ? (((double) testResultItem.getArea().intValue()) * 1.0d) / ((double) ctResultItem.getArea().intValue()) : 0.0d;
            } else if (this.testStrip.getJudgeType().intValue() == 2) {
                compareVal = ctResultItem.getHeight().intValue() > 0 ? (((double) testResultItem.getHeight().intValue()) * 1.0d) / ((double) ctResultItem.getHeight().intValue()) : 0.0d;
            }
            if (compareVal > 5.0d) {
                compareVal = 5.0d;
            }
            double compareVal2 = new BigDecimal(compareVal).setScale(2, 4).doubleValue();
            testResultItem.setValue(String.format("%.2f", new Object[]{Double.valueOf(compareVal2)}));
            testResultItem.setItem(this.testStrip.getItemList().get((this.testStrip.getItemList().size() - i4) - 1));
            testResultItem.setAreaValue(String.format("%.2f", new Object[]{Double.valueOf(compareVal2)}));
            boolean hasAnoymosVal = positiveVal != negativeVal;
            int result = 0;
            if (this.testStrip.getCtDirection().intValue() == 1) {
                if (compareVal2 > ((double) positiveVal)) {
                    result = 2;
                } else if (!hasAnoymosVal || compareVal2 > ((double) negativeVal)) {
                    result = 1;
                } else {
                    result = 2;
                }
            } else if (this.testStrip.getCtDirection().intValue() == 2) {
                if (compareVal2 < ((double) positiveVal)) {
                    result = 2;
                } else if (!hasAnoymosVal || compareVal2 > ((double) negativeVal)) {
                    result = 1;
                } else {
                    result = 2;
                }
            }
            testResultItem.setResult(Integer.valueOf(result));
            if (totalResult < result) {
                totalResult = result;
            }
        }
        int testResultCode = totalResult;
        this.testResult = testResultCode;
        showTestResult(testResultCode, hexData, ctResultItem);
        this.localSampleId = this.samplePresenter.saveData(this.testResult, this.testStrip.getCategoryId().intValue(), this.resultItemList, dataStr, this.txtSampleNo.getText().toString(), this.testStrip.getName());
        if (!HealthApp.x.isDebug()) {
            this.helloChartManager.initData(getApplicationContext(), this.lineChart, this.resultItemList, ctResultItem);
        }
        if (this.testResult > 1) {
        }
        return ctResultItem;
    }

    @OnClick({2131231050, 2131231141, 2131231143, 2131231139, 2131231142})
    public void onClicks(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                return;
            case R.id.ll_test_ng_tip:
                showNgTip();
                return;
            case R.id.bt_input_data:
                if (this.localSampleId > 0) {
                    Intent intent = new Intent(this, SampleDataActivity.class);
                    intent.putExtra(IntentActions.TEST_LOCAL_SAMPLE_ID, this.localSampleId);
                    startActivityForResult(intent, 0);
                    return;
                }
                return;
            case R.id.bt_test_again:
                initCheck();
                return;
            default:
                return;
        }
    }

    private void showNgTip() {
        View tipView = LayoutInflater.from(this).inflate(R.layout.test_result_ng_tip, (ViewGroup) null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(tipView);
        dialog.show();
        tipView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                dialog.cancel();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 1:
                this.uploadBtn.setVisibility(8);
                return;
            default:
                return;
        }
    }

    private void initCheck() {
        showProgress(getString(R.string.detection));
        if (HealthApp.x.isDebug()) {
            this.bleRepository.sendDebugTestDataCmd(Byte.valueOf((byte) (HealthApp.x.isFreeDebug() ? 0 : Byte.valueOf((byte) this.testStrip.getTestCount().intValue()).byteValue())));
        } else {
            this.bleRepository.sendTestCmd(Byte.valueOf((byte) this.testStrip.getTestCount().intValue()));
        }
    }

    private void showTestResult(int result, String[] hexData, TestResultItem cTestItem) {
        String[] resultStrList = {"<font color=\"#fcb813\">" + getString(R.string.weak_positive) + "</font>", "<font color=\"#3bde86\">" + getString(R.string.negative_one) + "</font>", "<font color=\"#ff6260\">" + getString(R.string.positive_one) + "</font>"};
        String resultStr = getString(R.string.test_result_one) + resultStrList[result];
        if (HealthApp.x.isDebug()) {
            String resultStr2 = (resultStr + "<br/>") + getString(R.string.c_line) + cTestItem.toString() + "<br/>";
            for (int i = 0; i < this.resultItemList.size(); i++) {
                resultStr2 = resultStr2 + "T" + (this.resultItemList.size() - i) + getString(R.string.line) + this.resultItemList.get(i).toString() + "<br/>";
            }
            this.mTvCheckResult.setText(Html.fromHtml(resultStr2));
        } else {
            String resultStr3 = resultStr + "<br/>";
            for (int i2 = 0; i2 < this.resultItemList.size(); i2++) {
                resultStr3 = (resultStr3 + this.resultItemList.get(i2).getItem().getName() + " T/C：" + this.resultItemList.get(i2).getValue()) + resultStrList[this.resultItemList.get(i2).getResult().intValue()];
                if (i2 < this.resultItemList.size() - 1) {
                    resultStr3 = resultStr3 + "<br/>";
                }
            }
            this.mTvCheckResult.setText(Html.fromHtml(resultStr3));
        }
        this.mTvCheckResult.setVisibility(0);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.bleRepository.removeBLEResponse((byte) 1, this);
        this.bleRepository.removeBLEResponse((byte) 2, this);
        this.bleRepository.removeBLEResponse(Byte.valueOf(BLEServiceApi.POWER_STATUS_CMD), this);
    }

    public void uploadDateResult(UploadDataCallback callback) {
    }
}

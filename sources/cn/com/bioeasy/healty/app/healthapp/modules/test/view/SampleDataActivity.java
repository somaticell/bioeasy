package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheKeys;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.UploadDataCallback;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.widget.TweetPicturesPreviewer;
import com.baidu.location.Address;
import javax.inject.Inject;
import net.oschina.common.widget.RichEditText;

public class SampleDataActivity extends BIBaseActivity implements SampleView {
    @Bind({2131230956})
    ImageView add;
    @Bind({2131231144})
    Button btnCity;
    @Bind({2131231145})
    Button btnDistinct;
    @Inject
    CacheManager cacheManager;
    private int localSampleId = 0;
    @Bind({2131230955})
    TweetPicturesPreviewer mLayImages;
    @Bind({2131231146})
    EditText marketText;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (ActionConstant.ACTION_UPLOAD.equals(intent.getAction())) {
                int counts = intent.getIntExtra("selected", 0);
                if (counts > 0 || counts == -1) {
                    SampleDataActivity.this.add.setVisibility(8);
                } else {
                    SampleDataActivity.this.add.setVisibility(0);
                }
            }
        }
    };
    @Bind({2131231148})
    RichEditText remarkText;
    @Bind({2131231147})
    EditText sampleNameText;
    @Inject
    SamplePresenter samplePresenter;
    @Bind({2131231008})
    TextView tvTitle;
    @Bind({2131231149})
    Button uploadBtn;
    private int uploadResultStatus = 0;

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
        return R.layout.activity_updata;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.tvTitle.setText(R.string.upload_title);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ActionConstant.ACTION_UPLOAD);
        registerReceiver(this.receiver, intentFilter);
        this.localSampleId = getIntent().getIntExtra(IntentActions.TEST_LOCAL_SAMPLE_ID, 0);
        Address address = HealthApp.x.getDataManager().getAddress();
        String cityName = address != null ? address.city : "";
        String distinctName = address != null ? address.district : "";
        String marketName = this.cacheManager.getString(CacheKeys.MARKET_NAME);
        if (marketName == null) {
            marketName = null;
        }
        this.marketText.setText(marketName);
        this.btnCity.setText(cityName);
        this.btnDistinct.setText(distinctName);
        checkGPSSetting();
    }

    public void checkGPSSetting() {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") != 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0 || ActivityCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_PHONE_STATE"}, 99);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 99:
                if (grantResults[0] != 0) {
                    showMessage((int) R.string.open_manually);
                    return;
                }
                return;
            default:
                return;
        }
    }

    @OnClick({2131231050, 2131230956, 2131231149})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_picture:
                this.mLayImages.onLoadMoreClick();
                return;
            case R.id.iv_back:
                finish();
                return;
            case R.id.upload:
                String[] paths = this.mLayImages.getPaths();
                String marketName = this.marketText.getText().toString();
                String sampleName = this.sampleNameText.getText().toString();
                if (StringUtil.isNullOrEmpty(sampleName)) {
                    showMessage((int) R.string.enter_sample_name);
                    return;
                }
                String remark = this.remarkText.getText().toString();
                this.cacheManager.saveString(CacheKeys.MARKET_NAME, marketName);
                this.samplePresenter.uploadData(this.localSampleId, marketName, sampleName, remark, paths);
                return;
            default:
                return;
        }
    }

    public void finish() {
        super.finish();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.receiver);
    }

    public void uploadDateResult(UploadDataCallback callback) {
        if (callback.getResult() == 0) {
            this.uploadBtn.setVisibility(8);
            Intent intent = new Intent(this, DataUploadResultActivity.class);
            intent.putExtra("sampleData", callback.getSampleData());
            startActivity(intent);
            showMessage((int) R.string.data_upload_success);
            return;
        }
        showMessage((int) R.string.data_upload_failure);
    }
}

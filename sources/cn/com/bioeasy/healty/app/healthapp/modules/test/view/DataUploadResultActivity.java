package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.MainActivity;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.SampleData;
import cn.sharesdk.onekeyshare.HealthAppShare;

public class DataUploadResultActivity extends BIBaseActivity {
    private SampleData sampleData;
    @Bind({2131231008})
    TextView tvTitle;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return null;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_upload_result;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.tvTitle.setText(R.string.upload_complete);
        this.sampleData = (SampleData) getIntent().getSerializableExtra("sampleData");
    }

    @OnClick({2131231050, 2131231150, 2131231142})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                return;
            case R.id.bt_test_again:
                finish();
                switchPage(MainActivity.class);
                return;
            case R.id.bt_share:
                HealthAppShare.shareTestReesult(this, this.sampleData, (String) null);
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
    }
}

package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;

public class AboutMeActivity extends BIBaseActivity {
    @Bind({2131230880})
    TextView mtBiName;
    @Bind({2131230881})
    TextView mtBiVersion;
    @Bind({2131231008})
    TextView mtvTitle;

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
        return R.layout.activity_about;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.mtvTitle.setText(getString(R.string.about_me));
        HealthApp healthApp = HealthApp.x;
        String versionName = HealthApp.getVersionName(this);
        if (StringUtil.isNullOrEmpty(versionName)) {
            versionName = "-";
        }
        this.mtBiVersion.setText(new SpannableString(String.format(getString(R.string.version), new Object[]{versionName})));
    }

    @OnClick({2131231050, 2131230882, 2131230885})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.rl_version_wrap:
                switchPage(VersionActivity.class);
                return;
            case R.id.rl_protocol_wrap:
                switchPage(ProtocolActivity.class);
                return;
            case R.id.check_version:
                checkVersion();
                return;
            case R.id.iv_back:
                finish();
                return;
            default:
                return;
        }
    }

    private void checkVersion() {
        showMessage((int) R.string.now_latest_version);
    }
}

package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;

public class BindOtherActivity extends BIBaseActivity {
    @Bind({2131231008})
    TextView mTvTitle;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return null;
    }

    @OnClick({2131231050, 2131230890})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.sb_wechat:
                new BindDialog(this).show();
                return;
            case R.id.iv_back:
                finish();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_binding_other;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.mTvTitle.setText(getResources().getString(R.string.bind_other));
    }
}

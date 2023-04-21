package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.text.Html;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;

public class ProtocolActivity extends BIBaseActivity {
    @Bind({2131231058})
    TextView mProtocolContent;
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
        return R.layout.activity_protocol;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.mtvTitle.setText(getString(R.string.protocol));
        this.mProtocolContent.setText(Html.fromHtml(getString(R.string.protocol_detail)));
    }

    @OnClick({2131231050})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                return;
            default:
                return;
        }
    }
}

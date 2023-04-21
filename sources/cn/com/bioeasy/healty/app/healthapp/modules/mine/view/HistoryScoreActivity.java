package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;

public class HistoryScoreActivity extends BIBaseActivity {
    @Bind({2131231008})
    TextView mTvTitle;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject((BIBaseActivity) this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return null;
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

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_my_history_score;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.mTvTitle.setText(getResources().getString(R.string.historyscore_present));
    }

    public static void startAction(Activity activity) {
        activity.startActivity(new Intent(activity, HistoryScoreActivity.class));
    }
}

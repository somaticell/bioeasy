package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import com.flyco.tablayout.SegmentTabLayout;
import java.util.ArrayList;

public class MyScoreActivity extends BIBaseActivity {
    private ArrayList<Fragment> mFragments2 = new ArrayList<>();
    @Bind({2131231005})
    SegmentTabLayout mTb;
    private String[] mTitles = {getString(R.string.day_tasks), getString(R.string.newbie_task)};
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

    @OnClick({2131231050, 2131231007, 2131231000})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.rl_history:
                HistoryScoreActivity.startAction(this);
                return;
            case R.id.bt_exchange:
                PresentActivity.startAction(this);
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
        return R.layout.activity_my_score;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.mTvTitle.setText(getResources().getString(R.string.score_title));
        for (int i = 0; i < this.mTitles.length; i++) {
            this.mFragments2.add(ScoreFragment.getInstance(i));
        }
        this.mTb.setTabData(this.mTitles, this, R.id.fl_change, this.mFragments2);
    }

    public static void startAction(Activity activity) {
        activity.startActivity(new Intent(activity, MyScoreActivity.class));
    }
}

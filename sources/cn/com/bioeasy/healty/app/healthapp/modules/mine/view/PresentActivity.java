package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.adapter.PresentAdapter;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.bean.PresentBean;
import java.util.ArrayList;
import java.util.List;

public class PresentActivity extends BIBaseActivity {
    @Bind({2131231009})
    RecyclerView mRecyclerView;
    @Bind({2131231008})
    TextView mTvTitle;
    PresentAdapter presentAdapter;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
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
        return R.layout.activity_my_score_present;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.mTvTitle.setText(getResources().getString(R.string.score_present));
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(1);
        this.mRecyclerView.setLayoutManager(layoutManager);
        this.presentAdapter = new PresentAdapter(this, initData());
        this.mRecyclerView.setAdapter(this.presentAdapter);
        this.presentAdapter.setOnclick(new PresentAdapter.ItemsOnclickListener() {
            public void onClick(PresentBean presentBean, String url, int pos) {
            }
        });
    }

    private List<PresentBean> initData() {
        List<PresentBean> listDatas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            listDatas.add(new PresentBean(getString(R.string.blood_glucose_meter), "", i, i + 200));
        }
        return listDatas;
    }

    public static void startAction(Activity activity) {
        activity.startActivity(new Intent(activity, PresentActivity.class));
    }
}

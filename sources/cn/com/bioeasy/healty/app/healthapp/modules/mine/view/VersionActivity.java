package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.net.HttpHelper;
import cn.com.bioeasy.app.retrofit.HttpSubscriber;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.data.VersionInfo;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.splash.AppVersionApi;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VersionActivity extends BIBaseActivity {
    private CommonAdapter<VersionInfo> commonAdapter;
    @Bind({2131231008})
    TextView mtvTitle;
    @Bind({2131230961})
    RecyclerView recyclerView;

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
        return R.layout.activity_history_version;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.mtvTitle.setText(getString(R.string.version_history));
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadHistoryVersionInfo();
    }

    private void loadHistoryVersionInfo() {
        ((AppVersionApi) new HttpHelper(getApplicationContext()).getService(AppVersionApi.class)).loadHistoryInfo().subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<List<VersionInfo>>() {
            public void onNext(List<VersionInfo> result) {
                VersionActivity.this.showHistoryData(result);
            }
        });
    }

    /* access modifiers changed from: private */
    public void showHistoryData(List<VersionInfo> dataList) {
        if (dataList != null && dataList.size() != 0) {
            this.commonAdapter = new CommonAdapter<VersionInfo>(this, dataList, R.layout.listitem_version_history) {
                /* access modifiers changed from: protected */
                public void convert(ViewHolder holder, VersionInfo versionInfo, int position) {
                    holder.setText((int) R.id.txt_version, versionInfo.getVersionName());
                    holder.setText((int) R.id.txt_version_desc, versionInfo.getDescription());
                }
            };
            this.recyclerView.setAdapter(this.commonAdapter);
        }
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

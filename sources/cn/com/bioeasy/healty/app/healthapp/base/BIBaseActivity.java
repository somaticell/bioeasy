package cn.com.bioeasy.healty.app.healthapp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import cn.com.bioeasy.app.base.BaseActivity;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.injection.component.DaggerActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.injection.module.ActivityModule;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.LoginActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public abstract class BIBaseActivity extends BaseActivity {
    protected ActivityComponent mActivityComponent;
    protected RequestManager mImageLoader;
    Toolbar mToolbar;

    /* access modifiers changed from: protected */
    public abstract void inject(ActivityComponent activityComponent);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        inject(getActivityComponent());
        super.onCreate(savedInstanceState);
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    public ActivityComponent getActivityComponent() {
        if (this.mActivityComponent == null) {
            this.mActivityComponent = DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).applicationComponent(HealthApp.x.getAppComponent()).build();
        }
        return this.mActivityComponent;
    }

    public void setToolbarTitle(String title) {
        ((TextView) this.mToolbar.findViewById(R.id.tv_title)).setText(title);
    }

    public synchronized RequestManager getImageLoader() {
        if (this.mImageLoader == null) {
            this.mImageLoader = Glide.with((FragmentActivity) this);
        }
        return this.mImageLoader;
    }

    /* access modifiers changed from: protected */
    public void toLoginPage() {
        switchPage(LoginActivity.class);
    }

    /* access modifiers changed from: protected */
    public void switchPage(Class cls) {
        startActivity(new Intent(this, cls));
    }
}

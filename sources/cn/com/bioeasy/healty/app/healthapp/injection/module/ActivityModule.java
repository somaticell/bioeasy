package cn.com.bioeasy.healty.app.healthapp.injection.module;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import cn.com.bioeasy.app.base.BaseActivity;
import cn.com.bioeasy.app.scope.PerActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.home.HomeFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.MineFragment;
import dagger.Module;
import dagger.Provides;
import java.util.ArrayList;
import java.util.List;

@Module
public class ActivityModule {
    private BaseActivity baseActivity;

    public ActivityModule(BaseActivity baseActivity2) {
        this.baseActivity = baseActivity2;
    }

    /* access modifiers changed from: package-private */
    @Provides
    public BaseActivity provideBaseActivity() {
        return this.baseActivity;
    }

    /* access modifiers changed from: package-private */
    @PerActivity
    @Provides
    public FragmentManager provideFragmentManager() {
        return this.baseActivity.getSupportFragmentManager();
    }

    /* access modifiers changed from: package-private */
    @PerActivity
    @Provides
    public List<Fragment> provideFragments() {
        return new ArrayList();
    }

    /* access modifiers changed from: package-private */
    @PerActivity
    @Provides
    public HomeFragment provideHomeFragment() {
        return new HomeFragment();
    }

    /* access modifiers changed from: package-private */
    @PerActivity
    @Provides
    public MineFragment provideMineFragment() {
        return new MineFragment();
    }
}

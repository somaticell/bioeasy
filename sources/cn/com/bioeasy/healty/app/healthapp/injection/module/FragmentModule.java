package cn.com.bioeasy.healty.app.healthapp.injection.module;

import cn.com.bioeasy.app.base.BaseFragment;
import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private BaseFragment baseFragment;

    public FragmentModule(BaseFragment baseFragment2) {
        this.baseFragment = baseFragment2;
    }

    /* access modifiers changed from: package-private */
    @Provides
    public BaseFragment provideBaseFragment() {
        return this.baseFragment;
    }
}

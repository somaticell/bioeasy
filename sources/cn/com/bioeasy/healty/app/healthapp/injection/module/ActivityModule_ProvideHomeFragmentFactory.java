package cn.com.bioeasy.healty.app.healthapp.injection.module;

import cn.com.bioeasy.healty.app.healthapp.modules.home.HomeFragment;
import dagger.internal.Factory;

public final class ActivityModule_ProvideHomeFragmentFactory implements Factory<HomeFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ActivityModule_ProvideHomeFragmentFactory.class.desiredAssertionStatus());
    private final ActivityModule module;

    public ActivityModule_ProvideHomeFragmentFactory(ActivityModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public HomeFragment get() {
        HomeFragment provided = this.module.provideHomeFragment();
        if (provided != null) {
            return provided;
        }
        throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<HomeFragment> create(ActivityModule module2) {
        return new ActivityModule_ProvideHomeFragmentFactory(module2);
    }
}

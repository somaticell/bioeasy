package cn.com.bioeasy.healty.app.healthapp.injection.module;

import android.support.v4.app.FragmentManager;
import dagger.internal.Factory;

public final class ActivityModule_ProvideFragmentManagerFactory implements Factory<FragmentManager> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ActivityModule_ProvideFragmentManagerFactory.class.desiredAssertionStatus());
    private final ActivityModule module;

    public ActivityModule_ProvideFragmentManagerFactory(ActivityModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public FragmentManager get() {
        FragmentManager provided = this.module.provideFragmentManager();
        if (provided != null) {
            return provided;
        }
        throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<FragmentManager> create(ActivityModule module2) {
        return new ActivityModule_ProvideFragmentManagerFactory(module2);
    }
}

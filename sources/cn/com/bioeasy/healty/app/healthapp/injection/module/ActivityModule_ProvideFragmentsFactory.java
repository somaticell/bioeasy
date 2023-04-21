package cn.com.bioeasy.healty.app.healthapp.injection.module;

import android.support.v4.app.Fragment;
import dagger.internal.Factory;
import java.util.List;

public final class ActivityModule_ProvideFragmentsFactory implements Factory<List<Fragment>> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ActivityModule_ProvideFragmentsFactory.class.desiredAssertionStatus());
    private final ActivityModule module;

    public ActivityModule_ProvideFragmentsFactory(ActivityModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public List<Fragment> get() {
        List<Fragment> provided = this.module.provideFragments();
        if (provided != null) {
            return provided;
        }
        throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<List<Fragment>> create(ActivityModule module2) {
        return new ActivityModule_ProvideFragmentsFactory(module2);
    }
}

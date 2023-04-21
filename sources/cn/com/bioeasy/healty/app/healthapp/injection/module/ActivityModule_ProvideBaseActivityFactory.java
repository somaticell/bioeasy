package cn.com.bioeasy.healty.app.healthapp.injection.module;

import cn.com.bioeasy.app.base.BaseActivity;
import dagger.internal.Factory;

public final class ActivityModule_ProvideBaseActivityFactory implements Factory<BaseActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ActivityModule_ProvideBaseActivityFactory.class.desiredAssertionStatus());
    private final ActivityModule module;

    public ActivityModule_ProvideBaseActivityFactory(ActivityModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public BaseActivity get() {
        BaseActivity provided = this.module.provideBaseActivity();
        if (provided != null) {
            return provided;
        }
        throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<BaseActivity> create(ActivityModule module2) {
        return new ActivityModule_ProvideBaseActivityFactory(module2);
    }
}

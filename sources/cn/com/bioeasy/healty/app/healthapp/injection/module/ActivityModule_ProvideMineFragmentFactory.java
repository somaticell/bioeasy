package cn.com.bioeasy.healty.app.healthapp.injection.module;

import cn.com.bioeasy.healty.app.healthapp.modules.mine.MineFragment;
import dagger.internal.Factory;

public final class ActivityModule_ProvideMineFragmentFactory implements Factory<MineFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ActivityModule_ProvideMineFragmentFactory.class.desiredAssertionStatus());
    private final ActivityModule module;

    public ActivityModule_ProvideMineFragmentFactory(ActivityModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public MineFragment get() {
        MineFragment provided = this.module.provideMineFragment();
        if (provided != null) {
            return provided;
        }
        throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<MineFragment> create(ActivityModule module2) {
        return new ActivityModule_ProvideMineFragmentFactory(module2);
    }
}

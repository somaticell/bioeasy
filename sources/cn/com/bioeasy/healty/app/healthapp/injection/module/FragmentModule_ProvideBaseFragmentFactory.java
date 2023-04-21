package cn.com.bioeasy.healty.app.healthapp.injection.module;

import cn.com.bioeasy.app.base.BaseFragment;
import dagger.internal.Factory;

public final class FragmentModule_ProvideBaseFragmentFactory implements Factory<BaseFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!FragmentModule_ProvideBaseFragmentFactory.class.desiredAssertionStatus());
    private final FragmentModule module;

    public FragmentModule_ProvideBaseFragmentFactory(FragmentModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public BaseFragment get() {
        BaseFragment provided = this.module.provideBaseFragment();
        if (provided != null) {
            return provided;
        }
        throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<BaseFragment> create(FragmentModule module2) {
        return new FragmentModule_ProvideBaseFragmentFactory(module2);
    }
}

package cn.com.bioeasy.healty.app.healthapp.injection.module;

import android.view.LayoutInflater;
import dagger.internal.Factory;

public final class ApplicationModule_ProvideLayoutInflaterFactory implements Factory<LayoutInflater> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ApplicationModule_ProvideLayoutInflaterFactory.class.desiredAssertionStatus());
    private final ApplicationModule module;

    public ApplicationModule_ProvideLayoutInflaterFactory(ApplicationModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public LayoutInflater get() {
        LayoutInflater provided = this.module.provideLayoutInflater();
        if (provided != null) {
            return provided;
        }
        throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<LayoutInflater> create(ApplicationModule module2) {
        return new ApplicationModule_ProvideLayoutInflaterFactory(module2);
    }
}

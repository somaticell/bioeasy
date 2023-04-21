package cn.com.bioeasy.healty.app.healthapp.injection.module;

import android.content.Context;
import dagger.internal.Factory;

public final class ApplicationModule_ProvideContextFactory implements Factory<Context> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ApplicationModule_ProvideContextFactory.class.desiredAssertionStatus());
    private final ApplicationModule module;

    public ApplicationModule_ProvideContextFactory(ApplicationModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public Context get() {
        Context provided = this.module.provideContext();
        if (provided != null) {
            return provided;
        }
        throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<Context> create(ApplicationModule module2) {
        return new ApplicationModule_ProvideContextFactory(module2);
    }
}

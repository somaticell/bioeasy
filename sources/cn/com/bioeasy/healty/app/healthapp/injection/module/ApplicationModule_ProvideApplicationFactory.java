package cn.com.bioeasy.healty.app.healthapp.injection.module;

import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import dagger.internal.Factory;

public final class ApplicationModule_ProvideApplicationFactory implements Factory<HealthApp> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ApplicationModule_ProvideApplicationFactory.class.desiredAssertionStatus());
    private final ApplicationModule module;

    public ApplicationModule_ProvideApplicationFactory(ApplicationModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public HealthApp get() {
        HealthApp provided = this.module.provideApplication();
        if (provided != null) {
            return provided;
        }
        throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<HealthApp> create(ApplicationModule module2) {
        return new ApplicationModule_ProvideApplicationFactory(module2);
    }
}

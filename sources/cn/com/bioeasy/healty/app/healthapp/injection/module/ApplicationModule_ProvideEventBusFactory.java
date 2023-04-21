package cn.com.bioeasy.healty.app.healthapp.injection.module;

import com.squareup.otto.Bus;
import dagger.internal.Factory;

public final class ApplicationModule_ProvideEventBusFactory implements Factory<Bus> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ApplicationModule_ProvideEventBusFactory.class.desiredAssertionStatus());
    private final ApplicationModule module;

    public ApplicationModule_ProvideEventBusFactory(ApplicationModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public Bus get() {
        Bus provided = this.module.provideEventBus();
        if (provided != null) {
            return provided;
        }
        throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<Bus> create(ApplicationModule module2) {
        return new ApplicationModule_ProvideEventBusFactory(module2);
    }
}

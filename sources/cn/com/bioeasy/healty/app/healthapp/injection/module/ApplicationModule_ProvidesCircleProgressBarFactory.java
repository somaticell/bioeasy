package cn.com.bioeasy.healty.app.healthapp.injection.module;

import android.content.Context;
import cn.com.bioeasy.app.widgets.CircleProgressBar;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ApplicationModule_ProvidesCircleProgressBarFactory implements Factory<CircleProgressBar> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ApplicationModule_ProvidesCircleProgressBarFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final ApplicationModule module;

    public ApplicationModule_ProvidesCircleProgressBarFactory(ApplicationModule module2, Provider<Context> contextProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public CircleProgressBar get() {
        CircleProgressBar provided = this.module.providesCircleProgressBar(this.contextProvider.get());
        if (provided != null) {
            return provided;
        }
        throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<CircleProgressBar> create(ApplicationModule module2, Provider<Context> contextProvider2) {
        return new ApplicationModule_ProvidesCircleProgressBarFactory(module2, contextProvider2);
    }
}

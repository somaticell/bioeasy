package cn.com.bioeasy.healty.app.healthapp.injection.module;

import android.content.Context;
import android.view.LayoutInflater;
import cn.com.bioeasy.app.widgets.CircleProgressBar;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class ApplicationModule {
    protected final HealthApp mApplication;

    public ApplicationModule(HealthApp application) {
        this.mApplication = application;
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public HealthApp provideApplication() {
        return this.mApplication;
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public Context provideContext() {
        return this.mApplication;
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public Bus provideEventBus() {
        return new Bus();
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(this.mApplication);
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public CircleProgressBar providesCircleProgressBar(Context context) {
        return new CircleProgressBar(context);
    }
}

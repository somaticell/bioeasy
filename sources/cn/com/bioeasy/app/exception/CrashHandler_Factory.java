package cn.com.bioeasy.app.exception;

import android.content.Context;
import cn.com.bioeasy.app.base.AppManager;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class CrashHandler_Factory implements Factory<CrashHandler> {
    static final /* synthetic */ boolean $assertionsDisabled = (!CrashHandler_Factory.class.desiredAssertionStatus());
    private final Provider<AppManager> activityManagerProvider;
    private final Provider<Context> appProvider;

    public CrashHandler_Factory(Provider<Context> appProvider2, Provider<AppManager> activityManagerProvider2) {
        if ($assertionsDisabled || appProvider2 != null) {
            this.appProvider = appProvider2;
            if ($assertionsDisabled || activityManagerProvider2 != null) {
                this.activityManagerProvider = activityManagerProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public CrashHandler get() {
        return new CrashHandler(this.appProvider.get(), this.activityManagerProvider.get());
    }

    public static Factory<CrashHandler> create(Provider<Context> appProvider2, Provider<AppManager> activityManagerProvider2) {
        return new CrashHandler_Factory(appProvider2, activityManagerProvider2);
    }
}

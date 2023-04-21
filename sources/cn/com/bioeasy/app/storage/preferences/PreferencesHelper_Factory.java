package cn.com.bioeasy.app.storage.preferences;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class PreferencesHelper_Factory implements Factory<PreferencesHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!PreferencesHelper_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;

    public PreferencesHelper_Factory(Provider<Context> contextProvider2) {
        if ($assertionsDisabled || contextProvider2 != null) {
            this.contextProvider = contextProvider2;
            return;
        }
        throw new AssertionError();
    }

    public PreferencesHelper get() {
        return new PreferencesHelper(this.contextProvider.get());
    }

    public static Factory<PreferencesHelper> create(Provider<Context> contextProvider2) {
        return new PreferencesHelper_Factory(contextProvider2);
    }
}

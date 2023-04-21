package cn.com.bioeasy.healty.app.healthapp.data;

import android.content.Context;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DataManager_Factory implements Factory<DataManager> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DataManager_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final MembersInjector<DataManager> membersInjector;

    public DataManager_Factory(MembersInjector<DataManager> membersInjector2, Provider<Context> contextProvider2) {
        if ($assertionsDisabled || membersInjector2 != null) {
            this.membersInjector = membersInjector2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public DataManager get() {
        DataManager instance = new DataManager(this.contextProvider.get());
        this.membersInjector.injectMembers(instance);
        return instance;
    }

    public static Factory<DataManager> create(MembersInjector<DataManager> membersInjector2, Provider<Context> contextProvider2) {
        return new DataManager_Factory(membersInjector2, contextProvider2);
    }
}

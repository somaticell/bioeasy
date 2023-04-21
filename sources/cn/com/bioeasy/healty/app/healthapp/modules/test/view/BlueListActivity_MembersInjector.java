package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class BlueListActivity_MembersInjector implements MembersInjector<BlueListActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!BlueListActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<BLERepository> bleRepositoryProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;

    public BlueListActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<BLERepository> bleRepositoryProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || bleRepositoryProvider2 != null) {
                this.bleRepositoryProvider = bleRepositoryProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(BlueListActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.bleRepository = this.bleRepositoryProvider.get();
    }

    public static MembersInjector<BlueListActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<BLERepository> bleRepositoryProvider2) {
        return new BlueListActivity_MembersInjector(supertypeInjector2, bleRepositoryProvider2);
    }
}

package cn.com.bioeasy.healty.app.healthapp.ble;

import dagger.MembersInjector;
import dagger.internal.Factory;

public final class BLERepository_Factory implements Factory<BLERepository> {
    static final /* synthetic */ boolean $assertionsDisabled = (!BLERepository_Factory.class.desiredAssertionStatus());
    private final MembersInjector<BLERepository> membersInjector;

    public BLERepository_Factory(MembersInjector<BLERepository> membersInjector2) {
        if ($assertionsDisabled || membersInjector2 != null) {
            this.membersInjector = membersInjector2;
            return;
        }
        throw new AssertionError();
    }

    public BLERepository get() {
        BLERepository instance = new BLERepository();
        this.membersInjector.injectMembers(instance);
        return instance;
    }

    public static Factory<BLERepository> create(MembersInjector<BLERepository> membersInjector2) {
        return new BLERepository_Factory(membersInjector2);
    }
}

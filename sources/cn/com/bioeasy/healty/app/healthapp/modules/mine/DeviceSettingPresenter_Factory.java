package cn.com.bioeasy.healty.app.healthapp.modules.mine;

import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import cn.com.bioeasy.healty.app.healthapp.modules.user.modal.UserRepository;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DeviceSettingPresenter_Factory implements Factory<DeviceSettingPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DeviceSettingPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<BLERepository> bleRepositoryProvider;
    private final Provider<UserRepository> mRepositoryProvider;
    private final MembersInjector<DeviceSettingPresenter> membersInjector;

    public DeviceSettingPresenter_Factory(MembersInjector<DeviceSettingPresenter> membersInjector2, Provider<BLERepository> bleRepositoryProvider2, Provider<UserRepository> mRepositoryProvider2) {
        if ($assertionsDisabled || membersInjector2 != null) {
            this.membersInjector = membersInjector2;
            if ($assertionsDisabled || bleRepositoryProvider2 != null) {
                this.bleRepositoryProvider = bleRepositoryProvider2;
                if ($assertionsDisabled || mRepositoryProvider2 != null) {
                    this.mRepositoryProvider = mRepositoryProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public DeviceSettingPresenter get() {
        DeviceSettingPresenter instance = new DeviceSettingPresenter(this.bleRepositoryProvider.get(), this.mRepositoryProvider.get());
        this.membersInjector.injectMembers(instance);
        return instance;
    }

    public static Factory<DeviceSettingPresenter> create(MembersInjector<DeviceSettingPresenter> membersInjector2, Provider<BLERepository> bleRepositoryProvider2, Provider<UserRepository> mRepositoryProvider2) {
        return new DeviceSettingPresenter_Factory(membersInjector2, bleRepositoryProvider2, mRepositoryProvider2);
    }
}

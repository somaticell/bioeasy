package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.DeviceSettingPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class DeviceSettingActivity_MembersInjector implements MembersInjector<DeviceSettingActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DeviceSettingActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<BLERepository> bleRepositoryProvider;
    private final Provider<CacheManager> cacheManagerProvider;
    private final Provider<DeviceSettingPresenter> deviceSettingPresenterProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;

    public DeviceSettingActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<DeviceSettingPresenter> deviceSettingPresenterProvider2, Provider<CacheManager> cacheManagerProvider2, Provider<BLERepository> bleRepositoryProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || deviceSettingPresenterProvider2 != null) {
                this.deviceSettingPresenterProvider = deviceSettingPresenterProvider2;
                if ($assertionsDisabled || cacheManagerProvider2 != null) {
                    this.cacheManagerProvider = cacheManagerProvider2;
                    if ($assertionsDisabled || bleRepositoryProvider2 != null) {
                        this.bleRepositoryProvider = bleRepositoryProvider2;
                        return;
                    }
                    throw new AssertionError();
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(DeviceSettingActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.deviceSettingPresenter = this.deviceSettingPresenterProvider.get();
        instance.cacheManager = this.cacheManagerProvider.get();
        instance.bleRepository = this.bleRepositoryProvider.get();
    }

    public static MembersInjector<DeviceSettingActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<DeviceSettingPresenter> deviceSettingPresenterProvider2, Provider<CacheManager> cacheManagerProvider2, Provider<BLERepository> bleRepositoryProvider2) {
        return new DeviceSettingActivity_MembersInjector(supertypeInjector2, deviceSettingPresenterProvider2, cacheManagerProvider2, bleRepositoryProvider2);
    }
}

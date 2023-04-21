package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import cn.com.bioeasy.healty.app.healthapp.modules.test.modal.SampleRepository;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SamplePresenter_Factory implements Factory<SamplePresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SamplePresenter_Factory.class.desiredAssertionStatus());
    private final Provider<BLERepository> bleRepositoryProvider;
    private final Provider<SampleRepository> mRepositoryProvider;
    private final MembersInjector<SamplePresenter> membersInjector;

    public SamplePresenter_Factory(MembersInjector<SamplePresenter> membersInjector2, Provider<BLERepository> bleRepositoryProvider2, Provider<SampleRepository> mRepositoryProvider2) {
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

    public SamplePresenter get() {
        SamplePresenter instance = new SamplePresenter(this.bleRepositoryProvider.get(), this.mRepositoryProvider.get());
        this.membersInjector.injectMembers(instance);
        return instance;
    }

    public static Factory<SamplePresenter> create(MembersInjector<SamplePresenter> membersInjector2, Provider<BLERepository> bleRepositoryProvider2, Provider<SampleRepository> mRepositoryProvider2) {
        return new SamplePresenter_Factory(membersInjector2, bleRepositoryProvider2, mRepositoryProvider2);
    }
}

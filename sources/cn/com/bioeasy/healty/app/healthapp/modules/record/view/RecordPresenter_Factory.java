package cn.com.bioeasy.healty.app.healthapp.modules.record.view;

import cn.com.bioeasy.healty.app.healthapp.modules.test.modal.SampleRepository;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class RecordPresenter_Factory implements Factory<RecordPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!RecordPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<SampleRepository> mRepositoryProvider;
    private final MembersInjector<RecordPresenter> membersInjector;

    public RecordPresenter_Factory(MembersInjector<RecordPresenter> membersInjector2, Provider<SampleRepository> mRepositoryProvider2) {
        if ($assertionsDisabled || membersInjector2 != null) {
            this.membersInjector = membersInjector2;
            if ($assertionsDisabled || mRepositoryProvider2 != null) {
                this.mRepositoryProvider = mRepositoryProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public RecordPresenter get() {
        RecordPresenter instance = new RecordPresenter(this.mRepositoryProvider.get());
        this.membersInjector.injectMembers(instance);
        return instance;
    }

    public static Factory<RecordPresenter> create(MembersInjector<RecordPresenter> membersInjector2, Provider<SampleRepository> mRepositoryProvider2) {
        return new RecordPresenter_Factory(membersInjector2, mRepositoryProvider2);
    }
}

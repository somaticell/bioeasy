package cn.com.bioeasy.healty.app.healthapp.modules.record.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class RecordAllFragmentTab_MembersInjector implements MembersInjector<RecordAllFragmentTab> {
    static final /* synthetic */ boolean $assertionsDisabled = (!RecordAllFragmentTab_MembersInjector.class.desiredAssertionStatus());
    private final Provider<RecordPresenter> recordPresenterProvider;
    private final MembersInjector<BIBaseFragment> supertypeInjector;

    public RecordAllFragmentTab_MembersInjector(MembersInjector<BIBaseFragment> supertypeInjector2, Provider<RecordPresenter> recordPresenterProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || recordPresenterProvider2 != null) {
                this.recordPresenterProvider = recordPresenterProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(RecordAllFragmentTab instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.recordPresenter = this.recordPresenterProvider.get();
    }

    public static MembersInjector<RecordAllFragmentTab> create(MembersInjector<BIBaseFragment> supertypeInjector2, Provider<RecordPresenter> recordPresenterProvider2) {
        return new RecordAllFragmentTab_MembersInjector(supertypeInjector2, recordPresenterProvider2);
    }
}

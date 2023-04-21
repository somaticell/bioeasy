package cn.com.bioeasy.healty.app.healthapp.modules.record.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class RecordFragmentTab_MembersInjector implements MembersInjector<RecordFragmentTab> {
    static final /* synthetic */ boolean $assertionsDisabled = (!RecordFragmentTab_MembersInjector.class.desiredAssertionStatus());
    private final Provider<RecordPresenter> recordPresenterProvider;
    private final MembersInjector<BIBaseFragment> supertypeInjector;

    public RecordFragmentTab_MembersInjector(MembersInjector<BIBaseFragment> supertypeInjector2, Provider<RecordPresenter> recordPresenterProvider2) {
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

    public void injectMembers(RecordFragmentTab instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.recordPresenter = this.recordPresenterProvider.get();
    }

    public static MembersInjector<RecordFragmentTab> create(MembersInjector<BIBaseFragment> supertypeInjector2, Provider<RecordPresenter> recordPresenterProvider2) {
        return new RecordFragmentTab_MembersInjector(supertypeInjector2, recordPresenterProvider2);
    }
}

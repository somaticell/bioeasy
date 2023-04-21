package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.home.HomePresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class SafeInformationFragment_MembersInjector implements MembersInjector<SafeInformationFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SafeInformationFragment_MembersInjector.class.desiredAssertionStatus());
    private final Provider<HomePresenter> homePresenterProvider;
    private final MembersInjector<BIBaseFragment> supertypeInjector;

    public SafeInformationFragment_MembersInjector(MembersInjector<BIBaseFragment> supertypeInjector2, Provider<HomePresenter> homePresenterProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || homePresenterProvider2 != null) {
                this.homePresenterProvider = homePresenterProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(SafeInformationFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.homePresenter = this.homePresenterProvider.get();
    }

    public static MembersInjector<SafeInformationFragment> create(MembersInjector<BIBaseFragment> supertypeInjector2, Provider<HomePresenter> homePresenterProvider2) {
        return new SafeInformationFragment_MembersInjector(supertypeInjector2, homePresenterProvider2);
    }
}

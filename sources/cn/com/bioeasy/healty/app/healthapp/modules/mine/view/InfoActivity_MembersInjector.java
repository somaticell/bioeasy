package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.user.MainPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class InfoActivity_MembersInjector implements MembersInjector<InfoActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!InfoActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<MainPresenter> mainPresenterProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;

    public InfoActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<MainPresenter> mainPresenterProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || mainPresenterProvider2 != null) {
                this.mainPresenterProvider = mainPresenterProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(InfoActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.mainPresenter = this.mainPresenterProvider.get();
    }

    public static MembersInjector<InfoActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<MainPresenter> mainPresenterProvider2) {
        return new InfoActivity_MembersInjector(supertypeInjector2, mainPresenterProvider2);
    }
}

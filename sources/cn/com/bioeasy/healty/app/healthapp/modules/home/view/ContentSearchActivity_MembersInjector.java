package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.home.HomePresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class ContentSearchActivity_MembersInjector implements MembersInjector<ContentSearchActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ContentSearchActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<HomePresenter> homePresenterProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;

    public ContentSearchActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<HomePresenter> homePresenterProvider2) {
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

    public void injectMembers(ContentSearchActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.homePresenter = this.homePresenterProvider.get();
    }

    public static MembersInjector<ContentSearchActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<HomePresenter> homePresenterProvider2) {
        return new ContentSearchActivity_MembersInjector(supertypeInjector2, homePresenterProvider2);
    }
}

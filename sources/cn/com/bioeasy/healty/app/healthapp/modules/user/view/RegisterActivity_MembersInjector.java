package cn.com.bioeasy.healty.app.healthapp.modules.user.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.user.RegisterPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class RegisterActivity_MembersInjector implements MembersInjector<RegisterActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!RegisterActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<RegisterPresenter> registerPresenterProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;

    public RegisterActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<RegisterPresenter> registerPresenterProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || registerPresenterProvider2 != null) {
                this.registerPresenterProvider = registerPresenterProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(RegisterActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.registerPresenter = this.registerPresenterProvider.get();
    }

    public static MembersInjector<RegisterActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<RegisterPresenter> registerPresenterProvider2) {
        return new RegisterActivity_MembersInjector(supertypeInjector2, registerPresenterProvider2);
    }
}

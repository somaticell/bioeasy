package cn.com.bioeasy.healty.app.healthapp.modules.user.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.user.LoginPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class LoginActivity_MembersInjector implements MembersInjector<LoginActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!LoginActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<LoginPresenter> loginPresenterProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;

    public LoginActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<LoginPresenter> loginPresenterProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || loginPresenterProvider2 != null) {
                this.loginPresenterProvider = loginPresenterProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(LoginActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.loginPresenter = this.loginPresenterProvider.get();
    }

    public static MembersInjector<LoginActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<LoginPresenter> loginPresenterProvider2) {
        return new LoginActivity_MembersInjector(supertypeInjector2, loginPresenterProvider2);
    }
}

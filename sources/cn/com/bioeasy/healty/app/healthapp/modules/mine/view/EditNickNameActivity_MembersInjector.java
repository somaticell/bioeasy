package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.user.LoginPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class EditNickNameActivity_MembersInjector implements MembersInjector<EditNickNameActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!EditNickNameActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<LoginPresenter> loginPresenterProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;

    public EditNickNameActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<LoginPresenter> loginPresenterProvider2) {
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

    public void injectMembers(EditNickNameActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.loginPresenter = this.loginPresenterProvider.get();
    }

    public static MembersInjector<EditNickNameActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<LoginPresenter> loginPresenterProvider2) {
        return new EditNickNameActivity_MembersInjector(supertypeInjector2, loginPresenterProvider2);
    }
}

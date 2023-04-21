package cn.com.bioeasy.healty.app.healthapp;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import cn.com.bioeasy.healty.app.healthapp.modules.user.MainPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!MainActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<BLERepository> bleRepositoryProvider;
    private final Provider<MainPresenter> mainPresenterProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;

    public MainActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<MainPresenter> mainPresenterProvider2, Provider<BLERepository> bleRepositoryProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || mainPresenterProvider2 != null) {
                this.mainPresenterProvider = mainPresenterProvider2;
                if ($assertionsDisabled || bleRepositoryProvider2 != null) {
                    this.bleRepositoryProvider = bleRepositoryProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(MainActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.mainPresenter = this.mainPresenterProvider.get();
        instance.bleRepository = this.bleRepositoryProvider.get();
    }

    public static MembersInjector<MainActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<MainPresenter> mainPresenterProvider2, Provider<BLERepository> bleRepositoryProvider2) {
        return new MainActivity_MembersInjector(supertypeInjector2, mainPresenterProvider2, bleRepositoryProvider2);
    }
}

package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class TestItemActivity_MembersInjector implements MembersInjector<TestItemActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!TestItemActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<BLERepository> bleRepositoryProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;
    private final Provider<TestItemPresenter> testItemPresenterProvider;

    public TestItemActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<TestItemPresenter> testItemPresenterProvider2, Provider<BLERepository> bleRepositoryProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || testItemPresenterProvider2 != null) {
                this.testItemPresenterProvider = testItemPresenterProvider2;
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

    public void injectMembers(TestItemActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.testItemPresenter = this.testItemPresenterProvider.get();
        instance.bleRepository = this.bleRepositoryProvider.get();
    }

    public static MembersInjector<TestItemActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<TestItemPresenter> testItemPresenterProvider2, Provider<BLERepository> bleRepositoryProvider2) {
        return new TestItemActivity_MembersInjector(supertypeInjector2, testItemPresenterProvider2, bleRepositoryProvider2);
    }
}

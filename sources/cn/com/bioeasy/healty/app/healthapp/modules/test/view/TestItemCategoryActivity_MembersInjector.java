package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class TestItemCategoryActivity_MembersInjector implements MembersInjector<TestItemCategoryActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!TestItemCategoryActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<BLERepository> bleRepositoryProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;
    private final Provider<TestItemPresenter> testItemPresenterProvider;

    public TestItemCategoryActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<BLERepository> bleRepositoryProvider2, Provider<TestItemPresenter> testItemPresenterProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || bleRepositoryProvider2 != null) {
                this.bleRepositoryProvider = bleRepositoryProvider2;
                if ($assertionsDisabled || testItemPresenterProvider2 != null) {
                    this.testItemPresenterProvider = testItemPresenterProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(TestItemCategoryActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.bleRepository = this.bleRepositoryProvider.get();
        instance.testItemPresenter = this.testItemPresenterProvider.get();
    }

    public static MembersInjector<TestItemCategoryActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<BLERepository> bleRepositoryProvider2, Provider<TestItemPresenter> testItemPresenterProvider2) {
        return new TestItemCategoryActivity_MembersInjector(supertypeInjector2, bleRepositoryProvider2, testItemPresenterProvider2);
    }
}

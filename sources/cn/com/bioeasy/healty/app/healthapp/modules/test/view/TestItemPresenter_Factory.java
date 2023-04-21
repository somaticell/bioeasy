package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import cn.com.bioeasy.healty.app.healthapp.modules.test.modal.TestItemRepository;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class TestItemPresenter_Factory implements Factory<TestItemPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!TestItemPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<TestItemRepository> mRepositoryProvider;
    private final MembersInjector<TestItemPresenter> membersInjector;

    public TestItemPresenter_Factory(MembersInjector<TestItemPresenter> membersInjector2, Provider<TestItemRepository> mRepositoryProvider2) {
        if ($assertionsDisabled || membersInjector2 != null) {
            this.membersInjector = membersInjector2;
            if ($assertionsDisabled || mRepositoryProvider2 != null) {
                this.mRepositoryProvider = mRepositoryProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public TestItemPresenter get() {
        TestItemPresenter instance = new TestItemPresenter(this.mRepositoryProvider.get());
        this.membersInjector.injectMembers(instance);
        return instance;
    }

    public static Factory<TestItemPresenter> create(MembersInjector<TestItemPresenter> membersInjector2, Provider<TestItemRepository> mRepositoryProvider2) {
        return new TestItemPresenter_Factory(membersInjector2, mRepositoryProvider2);
    }
}

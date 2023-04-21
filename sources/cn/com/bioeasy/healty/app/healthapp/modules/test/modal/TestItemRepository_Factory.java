package cn.com.bioeasy.healty.app.healthapp.modules.test.modal;

import android.content.Context;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.inject.Provider;
import retrofit2.Retrofit;

public final class TestItemRepository_Factory implements Factory<TestItemRepository> {
    static final /* synthetic */ boolean $assertionsDisabled = (!TestItemRepository_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final MembersInjector<TestItemRepository> membersInjector;
    private final Provider<Retrofit> retrofitProvider;

    public TestItemRepository_Factory(MembersInjector<TestItemRepository> membersInjector2, Provider<Context> contextProvider2, Provider<Retrofit> retrofitProvider2) {
        if ($assertionsDisabled || membersInjector2 != null) {
            this.membersInjector = membersInjector2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || retrofitProvider2 != null) {
                    this.retrofitProvider = retrofitProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public TestItemRepository get() {
        TestItemRepository instance = new TestItemRepository(this.contextProvider.get(), this.retrofitProvider.get());
        this.membersInjector.injectMembers(instance);
        return instance;
    }

    public static Factory<TestItemRepository> create(MembersInjector<TestItemRepository> membersInjector2, Provider<Context> contextProvider2, Provider<Retrofit> retrofitProvider2) {
        return new TestItemRepository_Factory(membersInjector2, contextProvider2, retrofitProvider2);
    }
}

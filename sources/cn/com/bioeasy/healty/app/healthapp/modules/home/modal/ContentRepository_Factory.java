package cn.com.bioeasy.healty.app.healthapp.modules.home.modal;

import android.content.Context;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.inject.Provider;
import retrofit2.Retrofit;

public final class ContentRepository_Factory implements Factory<ContentRepository> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ContentRepository_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final MembersInjector<ContentRepository> membersInjector;
    private final Provider<Retrofit> retrofitProvider;

    public ContentRepository_Factory(MembersInjector<ContentRepository> membersInjector2, Provider<Context> contextProvider2, Provider<Retrofit> retrofitProvider2) {
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

    public ContentRepository get() {
        ContentRepository instance = new ContentRepository(this.contextProvider.get(), this.retrofitProvider.get());
        this.membersInjector.injectMembers(instance);
        return instance;
    }

    public static Factory<ContentRepository> create(MembersInjector<ContentRepository> membersInjector2, Provider<Context> contextProvider2, Provider<Retrofit> retrofitProvider2) {
        return new ContentRepository_Factory(membersInjector2, contextProvider2, retrofitProvider2);
    }
}

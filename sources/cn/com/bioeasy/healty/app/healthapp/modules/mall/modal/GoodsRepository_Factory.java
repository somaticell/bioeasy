package cn.com.bioeasy.healty.app.healthapp.modules.mall.modal;

import android.content.Context;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.inject.Provider;
import retrofit2.Retrofit;

public final class GoodsRepository_Factory implements Factory<GoodsRepository> {
    static final /* synthetic */ boolean $assertionsDisabled = (!GoodsRepository_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final MembersInjector<GoodsRepository> membersInjector;
    private final Provider<Retrofit> retrofitProvider;

    public GoodsRepository_Factory(MembersInjector<GoodsRepository> membersInjector2, Provider<Context> contextProvider2, Provider<Retrofit> retrofitProvider2) {
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

    public GoodsRepository get() {
        GoodsRepository instance = new GoodsRepository(this.contextProvider.get(), this.retrofitProvider.get());
        this.membersInjector.injectMembers(instance);
        return instance;
    }

    public static Factory<GoodsRepository> create(MembersInjector<GoodsRepository> membersInjector2, Provider<Context> contextProvider2, Provider<Retrofit> retrofitProvider2) {
        return new GoodsRepository_Factory(membersInjector2, contextProvider2, retrofitProvider2);
    }
}

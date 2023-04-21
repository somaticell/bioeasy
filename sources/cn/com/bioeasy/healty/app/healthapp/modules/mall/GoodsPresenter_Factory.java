package cn.com.bioeasy.healty.app.healthapp.modules.mall;

import cn.com.bioeasy.healty.app.healthapp.modules.mall.modal.GoodsRepository;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class GoodsPresenter_Factory implements Factory<GoodsPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!GoodsPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<GoodsRepository> mRepositoryProvider;
    private final MembersInjector<GoodsPresenter> membersInjector;

    public GoodsPresenter_Factory(MembersInjector<GoodsPresenter> membersInjector2, Provider<GoodsRepository> mRepositoryProvider2) {
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

    public GoodsPresenter get() {
        GoodsPresenter instance = new GoodsPresenter(this.mRepositoryProvider.get());
        this.membersInjector.injectMembers(instance);
        return instance;
    }

    public static Factory<GoodsPresenter> create(MembersInjector<GoodsPresenter> membersInjector2, Provider<GoodsRepository> mRepositoryProvider2) {
        return new GoodsPresenter_Factory(membersInjector2, mRepositoryProvider2);
    }
}

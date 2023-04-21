package cn.com.bioeasy.healty.app.healthapp.modules.record.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.SamplePresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class MarketSearchActivity_MembersInjector implements MembersInjector<MarketSearchActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!MarketSearchActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<SamplePresenter> samplePresenterProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;

    public MarketSearchActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<SamplePresenter> samplePresenterProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || samplePresenterProvider2 != null) {
                this.samplePresenterProvider = samplePresenterProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(MarketSearchActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.samplePresenter = this.samplePresenterProvider.get();
    }

    public static MembersInjector<MarketSearchActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<SamplePresenter> samplePresenterProvider2) {
        return new MarketSearchActivity_MembersInjector(supertypeInjector2, samplePresenterProvider2);
    }
}

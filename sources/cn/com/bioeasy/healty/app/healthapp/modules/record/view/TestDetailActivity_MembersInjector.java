package cn.com.bioeasy.healty.app.healthapp.modules.record.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.SamplePresenter;
import cn.com.bioeasy.healty.app.healthapp.widgets.hellochart.HelloChartManager;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class TestDetailActivity_MembersInjector implements MembersInjector<TestDetailActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!TestDetailActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<HelloChartManager> helloChartManagerProvider;
    private final Provider<SamplePresenter> samplePresenterProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;

    public TestDetailActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<SamplePresenter> samplePresenterProvider2, Provider<HelloChartManager> helloChartManagerProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || samplePresenterProvider2 != null) {
                this.samplePresenterProvider = samplePresenterProvider2;
                if ($assertionsDisabled || helloChartManagerProvider2 != null) {
                    this.helloChartManagerProvider = helloChartManagerProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(TestDetailActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.samplePresenter = this.samplePresenterProvider.get();
        instance.helloChartManager = this.helloChartManagerProvider.get();
    }

    public static MembersInjector<TestDetailActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<SamplePresenter> samplePresenterProvider2, Provider<HelloChartManager> helloChartManagerProvider2) {
        return new TestDetailActivity_MembersInjector(supertypeInjector2, samplePresenterProvider2, helloChartManagerProvider2);
    }
}

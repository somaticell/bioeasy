package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import cn.com.bioeasy.healty.app.healthapp.widgets.hellochart.HelloChartManager;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class TestResultActivity_MembersInjector implements MembersInjector<TestResultActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!TestResultActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<BLERepository> bleRepositoryProvider;
    private final Provider<HelloChartManager> helloChartManagerProvider;
    private final Provider<SamplePresenter> samplePresenterProvider;
    private final MembersInjector<BIBaseActivity> supertypeInjector;

    public TestResultActivity_MembersInjector(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<BLERepository> bleRepositoryProvider2, Provider<HelloChartManager> helloChartManagerProvider2, Provider<SamplePresenter> samplePresenterProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || bleRepositoryProvider2 != null) {
                this.bleRepositoryProvider = bleRepositoryProvider2;
                if ($assertionsDisabled || helloChartManagerProvider2 != null) {
                    this.helloChartManagerProvider = helloChartManagerProvider2;
                    if ($assertionsDisabled || samplePresenterProvider2 != null) {
                        this.samplePresenterProvider = samplePresenterProvider2;
                        return;
                    }
                    throw new AssertionError();
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(TestResultActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.bleRepository = this.bleRepositoryProvider.get();
        instance.helloChartManager = this.helloChartManagerProvider.get();
        instance.samplePresenter = this.samplePresenterProvider.get();
    }

    public static MembersInjector<TestResultActivity> create(MembersInjector<BIBaseActivity> supertypeInjector2, Provider<BLERepository> bleRepositoryProvider2, Provider<HelloChartManager> helloChartManagerProvider2, Provider<SamplePresenter> samplePresenterProvider2) {
        return new TestResultActivity_MembersInjector(supertypeInjector2, bleRepositoryProvider2, helloChartManagerProvider2, samplePresenterProvider2);
    }
}

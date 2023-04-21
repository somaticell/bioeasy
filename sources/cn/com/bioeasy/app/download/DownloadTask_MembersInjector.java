package cn.com.bioeasy.app.download;

import cn.com.bioeasy.app.RxBus;
import cn.com.bioeasy.app.net.HttpHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class DownloadTask_MembersInjector implements MembersInjector<DownloadTask> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DownloadTask_MembersInjector.class.desiredAssertionStatus());
    private final Provider<HttpHelper> httpHelperProvider;
    private final Provider<RxBus> rxBusProvider;

    public DownloadTask_MembersInjector(Provider<HttpHelper> httpHelperProvider2, Provider<RxBus> rxBusProvider2) {
        if ($assertionsDisabled || httpHelperProvider2 != null) {
            this.httpHelperProvider = httpHelperProvider2;
            if ($assertionsDisabled || rxBusProvider2 != null) {
                this.rxBusProvider = rxBusProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(DownloadTask instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.httpHelper = this.httpHelperProvider.get();
        instance.rxBus = this.rxBusProvider.get();
    }

    public static MembersInjector<DownloadTask> create(Provider<HttpHelper> httpHelperProvider2, Provider<RxBus> rxBusProvider2) {
        return new DownloadTask_MembersInjector(httpHelperProvider2, rxBusProvider2);
    }
}

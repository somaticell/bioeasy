package cn.com.bioeasy.app.upload;

import cn.com.bioeasy.app.RxBus;
import cn.com.bioeasy.app.net.HttpHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UploadTask_MembersInjector implements MembersInjector<UploadTask> {
    static final /* synthetic */ boolean $assertionsDisabled = (!UploadTask_MembersInjector.class.desiredAssertionStatus());
    private final Provider<RxBus> busProvider;
    private final Provider<HttpHelper> httpHelperProvider;

    public UploadTask_MembersInjector(Provider<RxBus> busProvider2, Provider<HttpHelper> httpHelperProvider2) {
        if ($assertionsDisabled || busProvider2 != null) {
            this.busProvider = busProvider2;
            if ($assertionsDisabled || httpHelperProvider2 != null) {
                this.httpHelperProvider = httpHelperProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(UploadTask instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.bus = this.busProvider.get();
        instance.httpHelper = this.httpHelperProvider.get();
    }

    public static MembersInjector<UploadTask> create(Provider<RxBus> busProvider2, Provider<HttpHelper> httpHelperProvider2) {
        return new UploadTask_MembersInjector(busProvider2, httpHelperProvider2);
    }
}

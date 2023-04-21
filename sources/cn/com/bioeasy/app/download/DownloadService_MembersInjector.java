package cn.com.bioeasy.app.download;

import android.app.Service;
import cn.com.bioeasy.app.RxBus;
import cn.com.bioeasy.app.widgets.util.ToastUtils;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class DownloadService_MembersInjector implements MembersInjector<DownloadService> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DownloadService_MembersInjector.class.desiredAssertionStatus());
    private final Provider<RxBus> busProvider;
    private final MembersInjector<Service> supertypeInjector;
    private final Provider<ToastUtils> toastUtilsProvider;

    public DownloadService_MembersInjector(MembersInjector<Service> supertypeInjector2, Provider<RxBus> busProvider2, Provider<ToastUtils> toastUtilsProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || busProvider2 != null) {
                this.busProvider = busProvider2;
                if ($assertionsDisabled || toastUtilsProvider2 != null) {
                    this.toastUtilsProvider = toastUtilsProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(DownloadService instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.bus = this.busProvider.get();
        instance.toastUtils = this.toastUtilsProvider.get();
    }

    public static MembersInjector<DownloadService> create(MembersInjector<Service> supertypeInjector2, Provider<RxBus> busProvider2, Provider<ToastUtils> toastUtilsProvider2) {
        return new DownloadService_MembersInjector(supertypeInjector2, busProvider2, toastUtilsProvider2);
    }
}

package cn.com.bioeasy.app.upload;

import android.app.Service;
import cn.com.bioeasy.app.RxBus;
import cn.com.bioeasy.app.widgets.util.ToastUtils;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UploadService_MembersInjector implements MembersInjector<UploadService> {
    static final /* synthetic */ boolean $assertionsDisabled = (!UploadService_MembersInjector.class.desiredAssertionStatus());
    private final Provider<RxBus> busProvider;
    private final MembersInjector<Service> supertypeInjector;
    private final Provider<ToastUtils> toastUtilsProvider;

    public UploadService_MembersInjector(MembersInjector<Service> supertypeInjector2, Provider<RxBus> busProvider2, Provider<ToastUtils> toastUtilsProvider2) {
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

    public void injectMembers(UploadService instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.bus = this.busProvider.get();
        instance.toastUtils = this.toastUtilsProvider.get();
    }

    public static MembersInjector<UploadService> create(MembersInjector<Service> supertypeInjector2, Provider<RxBus> busProvider2, Provider<ToastUtils> toastUtilsProvider2) {
        return new UploadService_MembersInjector(supertypeInjector2, busProvider2, toastUtilsProvider2);
    }
}

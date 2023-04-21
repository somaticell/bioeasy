package cn.com.bioeasy.app.upload;

import cn.com.bioeasy.app.RxBus;
import dagger.MembersInjector;
import javax.inject.Provider;
import okhttp3.RequestBody;

public final class UploadRequestBody_MembersInjector implements MembersInjector<UploadRequestBody> {
    static final /* synthetic */ boolean $assertionsDisabled = (!UploadRequestBody_MembersInjector.class.desiredAssertionStatus());
    private final Provider<RxBus> busProvider;
    private final MembersInjector<RequestBody> supertypeInjector;

    public UploadRequestBody_MembersInjector(MembersInjector<RequestBody> supertypeInjector2, Provider<RxBus> busProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || busProvider2 != null) {
                this.busProvider = busProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void injectMembers(UploadRequestBody instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.bus = this.busProvider.get();
    }

    public static MembersInjector<UploadRequestBody> create(MembersInjector<RequestBody> supertypeInjector2, Provider<RxBus> busProvider2) {
        return new UploadRequestBody_MembersInjector(supertypeInjector2, busProvider2);
    }
}

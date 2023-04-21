package cn.com.bioeasy.app.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import cn.com.bioeasy.app.widgets.util.ToastUtils;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class BaseFragment_MembersInjector implements MembersInjector<BaseFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!BaseFragment_MembersInjector.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final MembersInjector<Fragment> supertypeInjector;
    private final Provider<ToastUtils> toastUtilsProvider;

    public BaseFragment_MembersInjector(MembersInjector<Fragment> supertypeInjector2, Provider<Context> contextProvider2, Provider<ToastUtils> toastUtilsProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
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

    public void injectMembers(BaseFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.context = this.contextProvider.get();
        instance.toastUtils = this.toastUtilsProvider.get();
    }

    public static MembersInjector<BaseFragment> create(MembersInjector<Fragment> supertypeInjector2, Provider<Context> contextProvider2, Provider<ToastUtils> toastUtilsProvider2) {
        return new BaseFragment_MembersInjector(supertypeInjector2, contextProvider2, toastUtilsProvider2);
    }
}

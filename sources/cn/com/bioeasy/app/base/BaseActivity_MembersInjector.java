package cn.com.bioeasy.app.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import cn.com.bioeasy.app.event.EventPosterHelper;
import cn.com.bioeasy.app.widgets.util.ToastUtils;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class BaseActivity_MembersInjector implements MembersInjector<BaseActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!BaseActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<AppManager> appManagerProvider;
    private final Provider<Context> contextProvider;
    private final Provider<EventPosterHelper> eventPosterHelperProvider;
    private final MembersInjector<AppCompatActivity> supertypeInjector;
    private final Provider<ToastUtils> toastUtilsProvider;

    public BaseActivity_MembersInjector(MembersInjector<AppCompatActivity> supertypeInjector2, Provider<ToastUtils> toastUtilsProvider2, Provider<EventPosterHelper> eventPosterHelperProvider2, Provider<Context> contextProvider2, Provider<AppManager> appManagerProvider2) {
        if ($assertionsDisabled || supertypeInjector2 != null) {
            this.supertypeInjector = supertypeInjector2;
            if ($assertionsDisabled || toastUtilsProvider2 != null) {
                this.toastUtilsProvider = toastUtilsProvider2;
                if ($assertionsDisabled || eventPosterHelperProvider2 != null) {
                    this.eventPosterHelperProvider = eventPosterHelperProvider2;
                    if ($assertionsDisabled || contextProvider2 != null) {
                        this.contextProvider = contextProvider2;
                        if ($assertionsDisabled || appManagerProvider2 != null) {
                            this.appManagerProvider = appManagerProvider2;
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
        throw new AssertionError();
    }

    public void injectMembers(BaseActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        this.supertypeInjector.injectMembers(instance);
        instance.toastUtils = this.toastUtilsProvider.get();
        instance.eventPosterHelper = this.eventPosterHelperProvider.get();
        instance.context = this.contextProvider.get();
        instance.appManager = this.appManagerProvider.get();
    }

    public static MembersInjector<BaseActivity> create(MembersInjector<AppCompatActivity> supertypeInjector2, Provider<ToastUtils> toastUtilsProvider2, Provider<EventPosterHelper> eventPosterHelperProvider2, Provider<Context> contextProvider2, Provider<AppManager> appManagerProvider2) {
        return new BaseActivity_MembersInjector(supertypeInjector2, toastUtilsProvider2, eventPosterHelperProvider2, contextProvider2, appManagerProvider2);
    }
}

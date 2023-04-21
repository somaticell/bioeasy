package cn.com.bioeasy.app.widgets.util;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ToastUtils_Factory implements Factory<ToastUtils> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ToastUtils_Factory.class.desiredAssertionStatus());
    private final Provider<Context> mContentProvider;

    public ToastUtils_Factory(Provider<Context> mContentProvider2) {
        if ($assertionsDisabled || mContentProvider2 != null) {
            this.mContentProvider = mContentProvider2;
            return;
        }
        throw new AssertionError();
    }

    public ToastUtils get() {
        return new ToastUtils(this.mContentProvider.get());
    }

    public static Factory<ToastUtils> create(Provider<Context> mContentProvider2) {
        return new ToastUtils_Factory(mContentProvider2);
    }
}

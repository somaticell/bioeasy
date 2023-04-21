package com.alipay.sdk.util;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.alipay.android.app.IAlixPay;

final class f implements ServiceConnection {
    final /* synthetic */ e a;

    f(e eVar) {
        this.a = eVar;
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        IAlixPay unused = this.a.c = null;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.a.d) {
            IAlixPay unused = this.a.c = IAlixPay.Stub.asInterface(iBinder);
            this.a.d.notify();
        }
    }
}

package com.baidu.mapapi.utils;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.baidu.mapframework.open.aidl.IComOpenClient;
import com.baidu.mapframework.open.aidl.b;

class d extends b.a {
    final /* synthetic */ c a;

    d(c cVar) {
        this.a = cVar;
    }

    public void a(IBinder iBinder) throws RemoteException {
        Log.d(a.c, "onClientReady");
        if (a.e != null) {
            IComOpenClient unused = a.e = null;
        }
        IComOpenClient unused2 = a.e = IComOpenClient.a.a(iBinder);
        if (!a.t) {
            a.a(a.a);
        }
        boolean unused3 = a.t = true;
    }
}

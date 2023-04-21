package com.baidu.mapapi.utils;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.baidu.mapframework.open.aidl.a;

final class c implements ServiceConnection {
    c() {
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (a.v != null) {
            a.v.interrupt();
        }
        Log.d(a.c, "onServiceConnected " + componentName);
        try {
            if (a.d != null) {
                a unused = a.d = null;
            }
            a unused2 = a.d = a.C0013a.a(iBinder);
            a.d.a(new d(this));
        } catch (RemoteException e) {
            Log.d(a.c, "getComOpenClient ", e);
            if (a.d != null) {
                a unused3 = a.d = null;
            }
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        Log.d(a.c, "onServiceDisconnected " + componentName);
        if (a.d != null) {
            a unused = a.d = null;
            boolean unused2 = a.u = false;
        }
    }
}

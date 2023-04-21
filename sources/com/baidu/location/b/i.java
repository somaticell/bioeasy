package com.baidu.location.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import com.baidu.location.a.h;
import com.baidu.location.f;
import com.baidu.location.f.j;

public class i {
    private static i b = null;
    final Handler a = new Handler();
    private a c = null;
    /* access modifiers changed from: private */
    public boolean d = false;
    private boolean e = false;
    /* access modifiers changed from: private */
    public boolean f = false;
    /* access modifiers changed from: private */
    public boolean g = true;
    private boolean h = false;
    private b i = new b();

    private class a extends BroadcastReceiver {
        private a() {
        }

        public void onReceive(Context context, Intent intent) {
            if (context != null && i.this.a != null) {
                i.this.f();
            }
        }
    }

    private class b implements Runnable {
        private b() {
        }

        public void run() {
            h.a().d();
            if (i.this.d) {
                g.a().c();
            }
            if (!i.this.d || !i.this.g) {
                boolean unused = i.this.f = false;
                return;
            }
            i.this.a.postDelayed(this, (long) j.P);
            boolean unused2 = i.this.f = true;
        }
    }

    private i() {
    }

    public static synchronized i a() {
        i iVar;
        synchronized (i.class) {
            if (b == null) {
                b = new i();
            }
            iVar = b;
        }
        return iVar;
    }

    /* access modifiers changed from: private */
    public void f() {
        NetworkInfo.State state;
        NetworkInfo.State state2 = NetworkInfo.State.UNKNOWN;
        try {
            state = ((ConnectivityManager) f.getServiceContext().getSystemService("connectivity")).getNetworkInfo(1).getState();
        } catch (Exception e2) {
            state = state2;
        }
        if (NetworkInfo.State.CONNECTED != state) {
            this.d = false;
        } else if (!this.d) {
            this.d = true;
            this.a.postDelayed(this.i, (long) j.P);
            this.f = true;
        }
    }

    public synchronized void b() {
        if (f.isServing) {
            if (!this.h) {
                try {
                    this.c = new a();
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                    f.getServiceContext().registerReceiver(this.c, intentFilter);
                    this.e = true;
                    f();
                } catch (Exception e2) {
                }
                this.g = true;
                this.h = true;
            }
        }
    }

    public synchronized void c() {
        if (this.h) {
            try {
                f.getServiceContext().unregisterReceiver(this.c);
            } catch (Exception e2) {
            }
            this.g = false;
            this.h = false;
            this.f = false;
            this.c = null;
        }
    }

    public void d() {
        if (this.h) {
            this.g = true;
            if (!this.f && this.g) {
                this.a.postDelayed(this.i, (long) j.P);
                this.f = true;
            }
        }
    }

    public void e() {
        this.g = false;
    }
}

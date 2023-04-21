package com.loc;

import android.os.Handler;
import android.os.Looper;
import java.util.List;

final class cu extends Thread {
    final /* synthetic */ cb a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    cu(cb cbVar, String str) {
        super(str);
        this.a = cbVar;
    }

    public final void run() {
        try {
            Looper.prepare();
            Looper unused = this.a.y = Looper.myLooper();
            cw unused2 = this.a.z = new cw(this.a);
            try {
                this.a.p.addGpsStatusListener(this.a.z);
                this.a.p.addNmeaListener(this.a.z);
            } catch (Exception e) {
            }
            Handler unused3 = this.a.A = new cv(this);
            List<String> allProviders = this.a.p.getAllProviders();
            if (allProviders != null && allProviders.contains("gps")) {
                allProviders.contains("passive");
            }
            try {
                this.a.p.requestLocationUpdates("passive", 1000, (float) cb.c, this.a.C);
            } catch (Exception e2) {
            }
            Looper.loop();
        } catch (Exception e3) {
        }
    }
}

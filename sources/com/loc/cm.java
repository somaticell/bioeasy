package com.loc;

import android.location.GpsStatus;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import java.util.Timer;

final class cm extends Thread {
    private /* synthetic */ cl a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    cm(cl clVar, String str) {
        super(str);
        this.a = clVar;
    }

    public final void run() {
        try {
            Looper.prepare();
            Looper unused = this.a.F = Looper.myLooper();
            Timer unused2 = this.a.D = new Timer();
            cn unused3 = this.a.y = new cn(this.a, (byte) 0);
            cl.a(this.a, (PhoneStateListener) this.a.y);
            co unused4 = this.a.z = new co(this.a, (byte) 0);
            try {
                cl.a(this.a, (GpsStatus.NmeaListener) this.a.z);
            } catch (Exception e) {
            }
            Looper.loop();
        } catch (Exception e2) {
        }
    }
}

package com.loc;

import android.location.GpsStatus;

final class co implements GpsStatus.NmeaListener {
    private /* synthetic */ cl a;

    private co(cl clVar) {
        this.a = clVar;
    }

    /* synthetic */ co(cl clVar, byte b) {
        this(clVar);
    }

    public final void onNmeaReceived(long j, String str) {
        try {
            long unused = this.a.o = j;
            String unused2 = this.a.p = str;
        } catch (Exception e) {
        }
    }
}

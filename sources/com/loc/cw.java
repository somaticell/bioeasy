package com.loc;

import android.location.GpsSatellite;
import android.location.GpsStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public final class cw implements GpsStatus.Listener, GpsStatus.NmeaListener {
    private long a = 0;
    private long b = 0;
    private boolean c = false;
    private List d = new ArrayList();
    private String e = null;
    private String f = null;
    private String g = null;
    private /* synthetic */ cb h;

    protected cw(cb cbVar) {
        this.h = cbVar;
    }

    public final void a(String str) {
        if (System.currentTimeMillis() - this.b > 400 && this.c && this.d.size() > 0) {
            try {
                dh dhVar = new dh(this.d, this.e, (String) null, this.g);
                if (dhVar.a()) {
                    int unused = this.h.K = cb.a(this.h, dhVar, this.h.H);
                    if (this.h.K > 0) {
                        cb.a(this.h, String.format(Locale.CHINA, "&nmea=%.1f|%.1f&g_tp=%d", new Object[]{Double.valueOf(dhVar.c()), Double.valueOf(dhVar.b()), Integer.valueOf(this.h.K)}));
                    }
                } else {
                    int unused2 = this.h.K = 0;
                }
            } catch (Exception e2) {
                int unused3 = this.h.K = 0;
            }
            this.d.clear();
            this.g = null;
            this.f = null;
            this.e = null;
            this.c = false;
        }
        if (str.startsWith("$GPGGA")) {
            this.c = true;
            this.e = str.trim();
        } else if (str.startsWith("$GPGSV")) {
            this.d.add(str.trim());
        } else if (str.startsWith("$GPGSA")) {
            this.g = str.trim();
        }
        this.b = System.currentTimeMillis();
    }

    public final void onGpsStatusChanged(int i) {
        int i2 = 0;
        try {
            if (this.h.p != null) {
                switch (i) {
                    case 2:
                        int unused = this.h.J = 0;
                        return;
                    case 4:
                        if (cb.a || System.currentTimeMillis() - this.a >= 10000) {
                            if (this.h.F == null) {
                                GpsStatus unused2 = this.h.F = this.h.p.getGpsStatus((GpsStatus) null);
                            } else {
                                this.h.p.getGpsStatus(this.h.F);
                            }
                            int unused3 = this.h.G = 0;
                            int unused4 = this.h.H = 0;
                            HashMap unused5 = this.h.I = new HashMap();
                            int i3 = 0;
                            int i4 = 0;
                            for (GpsSatellite next : this.h.F.getSatellites()) {
                                i3++;
                                if (next.usedInFix()) {
                                    i4++;
                                }
                                if (next.getSnr() > 0.0f) {
                                    i2++;
                                }
                                if (next.getSnr() >= ((float) cb.T)) {
                                    cb.g(this.h);
                                }
                            }
                            if (this.h.l == -1 || ((i4 >= 4 && this.h.l < 4) || (i4 < 4 && this.h.l >= 4))) {
                                int unused6 = this.h.l = i4;
                                if (i4 < 4) {
                                    if (this.h.q != null) {
                                        this.h.q.v();
                                    }
                                } else if (this.h.q != null) {
                                    this.h.q.u();
                                }
                            }
                            int unused7 = this.h.J = i2;
                            int unused8 = this.h.a(this.h.I);
                            if (cb.a) {
                                return;
                            }
                            if ((i4 > 3 || i3 > 15) && this.h.p.getLastKnownLocation("gps") != null) {
                                this.a = System.currentTimeMillis();
                                return;
                            }
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        } catch (Exception e2) {
        }
    }

    public final void onNmeaReceived(long j, String str) {
        try {
            if (cb.a && str != null && !str.equals("") && str.length() >= 9 && str.length() <= 150 && this.h.A != null) {
                this.h.A.sendMessage(this.h.A.obtainMessage(1, str));
            }
        } catch (Exception e2) {
        }
    }
}

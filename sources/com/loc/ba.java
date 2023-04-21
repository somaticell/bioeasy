package com.loc;

import android.text.TextUtils;
import com.autonavi.aps.amapapi.model.AmapLoc;

/* compiled from: LocFilter */
public class ba {
    private static ba a = null;
    private AmapLoc b = null;
    private long c = 0;
    private long d = 0;

    private ba() {
    }

    public static synchronized ba a() {
        ba baVar;
        synchronized (ba.class) {
            if (a == null) {
                a = new ba();
            }
            baVar = a;
        }
        return baVar;
    }

    public synchronized void b() {
        this.b = null;
        this.c = 0;
        this.d = 0;
    }

    public synchronized AmapLoc a(AmapLoc amapLoc) {
        if (!bw.a(this.b) || !bw.a(amapLoc)) {
            this.c = bw.b();
            this.b = amapLoc;
            amapLoc = this.b;
        } else if (amapLoc.k() != this.b.k() || amapLoc.j() >= 300.0f) {
            if (amapLoc.g().equals("gps")) {
                this.c = bw.b();
                this.b = amapLoc;
                amapLoc = this.b;
            } else if (amapLoc.A() != this.b.A()) {
                this.c = bw.b();
                this.b = amapLoc;
                amapLoc = this.b;
            } else if (amapLoc.z().equals(this.b.z()) || TextUtils.isEmpty(amapLoc.z())) {
                float a2 = bw.a(amapLoc, this.b);
                float j = this.b.j();
                float j2 = amapLoc.j();
                float f = j2 - j;
                long b2 = bw.b();
                long j3 = b2 - this.c;
                if ((j < 101.0f && j2 > 299.0f) || (j > 299.0f && j2 > 299.0f)) {
                    if (this.d == 0) {
                        this.d = b2;
                    } else if (b2 - this.d > 30000) {
                        this.c = b2;
                        this.b = amapLoc;
                        this.d = 0;
                        amapLoc = this.b;
                    }
                    this.b = c(this.b);
                    amapLoc = this.b;
                } else if (j2 >= 100.0f || j <= 299.0f) {
                    if (j2 <= 299.0f) {
                        this.d = 0;
                    }
                    if (a2 >= 10.0f || ((double) a2) <= 0.1d) {
                        if (f < 300.0f) {
                            this.c = bw.b();
                            this.b = amapLoc;
                            amapLoc = this.b;
                        } else if (j3 >= 30000) {
                            this.c = bw.b();
                            this.b = amapLoc;
                            amapLoc = this.b;
                        } else {
                            this.b = c(this.b);
                            amapLoc = this.b;
                        }
                    } else if (f >= -300.0f) {
                        this.b = c(this.b);
                        amapLoc = this.b;
                    } else if (j / j2 >= 2.0f) {
                        this.c = b2;
                        this.b = amapLoc;
                        amapLoc = this.b;
                    } else {
                        this.b = c(this.b);
                        amapLoc = this.b;
                    }
                } else {
                    this.c = b2;
                    this.b = amapLoc;
                    this.d = 0;
                    amapLoc = this.b;
                }
            } else {
                this.c = bw.b();
                this.b = amapLoc;
                amapLoc = this.b;
            }
        }
        return amapLoc;
    }

    private AmapLoc c(AmapLoc amapLoc) {
        if (bw.a(amapLoc) && (amapLoc.b() == 5 || amapLoc.b() == 6)) {
            amapLoc.a(2);
        }
        return amapLoc;
    }

    public AmapLoc b(AmapLoc amapLoc) {
        return amapLoc;
    }
}

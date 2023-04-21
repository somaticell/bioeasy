package com.baidu.location.a;

import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.baidu.location.a.i;
import com.baidu.location.d.e;
import com.baidu.location.d.g;
import com.baidu.location.d.h;
import com.baidu.location.f;
import com.baidu.location.f.j;
import java.util.List;

public class l extends i {
    public static boolean h = false;
    private static l i = null;
    private double A;
    private boolean B;
    private long C;
    private long D;
    private a E;
    /* access modifiers changed from: private */
    public boolean F;
    /* access modifiers changed from: private */
    public boolean G;
    private boolean H;
    private boolean I;
    private boolean J;
    private b K;
    /* access modifiers changed from: private */
    public boolean L;
    private int M;
    private long N;
    private boolean O;
    final int e;
    public i.b f;
    public final Handler g;
    private boolean j;
    private String k;
    private BDLocation l;
    private BDLocation m;
    private g n;
    private com.baidu.location.d.a o;
    private g p;
    private com.baidu.location.d.a q;
    private boolean r;
    private volatile boolean s;
    /* access modifiers changed from: private */
    public boolean t;
    private long u;
    private long v;
    private Address w;
    private String x;
    private List<Poi> y;
    private double z;

    private class a implements Runnable {
        final /* synthetic */ l a;

        public void run() {
            if (this.a.F) {
                boolean unused = this.a.F = false;
                if (!this.a.G) {
                }
            }
        }
    }

    private class b implements Runnable {
        private b() {
        }

        public void run() {
            if (l.this.L) {
                boolean unused = l.this.L = false;
            }
            if (l.this.t) {
                boolean unused2 = l.this.t = false;
                l.this.h((Message) null);
            }
        }
    }

    private l() {
        this.e = 1000;
        this.j = true;
        this.f = null;
        this.k = null;
        this.l = null;
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
        this.r = true;
        this.s = false;
        this.t = false;
        this.u = 0;
        this.v = 0;
        this.w = null;
        this.x = null;
        this.y = null;
        this.B = false;
        this.C = 0;
        this.D = 0;
        this.E = null;
        this.F = false;
        this.G = false;
        this.H = true;
        this.g = new i.a();
        this.I = false;
        this.J = false;
        this.K = null;
        this.L = false;
        this.M = 0;
        this.N = 0;
        this.O = true;
        this.f = new i.b();
    }

    private boolean a(com.baidu.location.d.a aVar) {
        this.b = com.baidu.location.d.b.a().f();
        if (this.b == aVar) {
            return false;
        }
        return this.b == null || aVar == null || !aVar.a(this.b);
    }

    private boolean a(g gVar) {
        this.a = h.a().p();
        if (gVar == this.a) {
            return false;
        }
        return this.a == null || gVar == null || !gVar.c(this.a);
    }

    public static synchronized l c() {
        l lVar;
        synchronized (l.class) {
            if (i == null) {
                i = new l();
            }
            lVar = i;
        }
        return lVar;
    }

    private void c(Message message) {
        boolean z2 = message.getData().getBoolean("isWaitingLocTag", false);
        if (z2) {
            h = true;
        }
        if (z2) {
        }
        if (!com.baidu.location.indoor.g.a().g()) {
            int d = a.a().d(message);
            switch (d) {
                case 1:
                    d(message);
                    return;
                case 2:
                    g(message);
                    return;
                case 3:
                    if (e.a().j()) {
                        e(message);
                        return;
                    }
                    return;
                default:
                    throw new IllegalArgumentException(String.format("this type %d is illegal", new Object[]{Integer.valueOf(d)}));
            }
        }
    }

    private void d(Message message) {
        if (e.a().j()) {
            e(message);
            n.a().c();
            return;
        }
        g(message);
        n.a().b();
    }

    private void e(Message message) {
        BDLocation bDLocation = new BDLocation(e.a().g());
        if (j.g.equals("all") || j.h || j.i) {
            float[] fArr = new float[2];
            Location.distanceBetween(this.A, this.z, bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
            if (fArr[0] < 100.0f) {
                if (this.w != null) {
                    bDLocation.setAddr(this.w);
                }
                if (this.x != null) {
                    bDLocation.setLocationDescribe(this.x);
                }
                if (this.y != null) {
                    bDLocation.setPoiList(this.y);
                }
            } else {
                this.B = true;
                g((Message) null);
            }
        }
        this.l = bDLocation;
        this.m = null;
        a.a().a(bDLocation);
    }

    private void f(Message message) {
        if (h.a().g()) {
            this.t = true;
            if (this.K == null) {
                this.K = new b();
            }
            if (this.L && this.K != null) {
                this.g.removeCallbacks(this.K);
            }
            this.g.postDelayed(this.K, 3500);
            this.L = true;
            return;
        }
        h(message);
    }

    private void g(Message message) {
        this.M = 0;
        if (this.r) {
            this.M = 1;
            this.D = SystemClock.uptimeMillis();
            if (h.a().k()) {
                f(message);
            } else {
                h(message);
            }
        } else {
            f(message);
            this.D = SystemClock.uptimeMillis();
        }
    }

    /* access modifiers changed from: private */
    public void h(Message message) {
        long j2 = 0;
        long currentTimeMillis = System.currentTimeMillis() - this.u;
        if (this.s && currentTimeMillis <= 12000) {
            return;
        }
        if (System.currentTimeMillis() - this.u <= 0 || System.currentTimeMillis() - this.u >= 1000) {
            this.s = true;
            this.j = a(this.o);
            if (!a(this.n) && !this.j && this.l != null && !this.B) {
                if (this.m != null && System.currentTimeMillis() - this.v > 30000) {
                    this.l = this.m;
                    this.m = null;
                }
                if (n.a().d()) {
                    this.l.setDirection(n.a().e());
                }
                if (this.l.getLocType() == 62) {
                    long currentTimeMillis2 = System.currentTimeMillis() - this.N;
                    if (currentTimeMillis2 > 0) {
                        j2 = currentTimeMillis2;
                    }
                }
                if (this.l.getLocType() == 61 || this.l.getLocType() == 161 || (this.l.getLocType() == 62 && j2 < 15000)) {
                    a.a().a(this.l);
                    k();
                    return;
                }
            }
            this.u = System.currentTimeMillis();
            String a2 = a((String) null);
            this.J = false;
            if (a2 == null) {
                this.J = true;
                this.N = System.currentTimeMillis();
                String[] j3 = j();
                long currentTimeMillis3 = System.currentTimeMillis();
                if (currentTimeMillis3 - this.C > 60000) {
                    this.C = currentTimeMillis3;
                }
                String m2 = h.a().m();
                a2 = m2 != null ? m2 + b() + j3[0] : "" + b() + j3[0];
                if (!(this.b == null || this.b.g() == null)) {
                    a2 = this.b.g() + a2;
                }
                String a3 = com.baidu.location.f.b.a().a(true);
                if (a3 != null) {
                    a2 = a2 + a3;
                }
            }
            if (this.k != null) {
                a2 = a2 + this.k;
                this.k = null;
            }
            this.f.a(a2);
            this.o = this.b;
            this.n = this.a;
            if (this.r) {
                this.r = false;
                if (!h.j() || message == null || a.a().e(message) < 1000) {
                }
                com.baidu.location.b.b.a().b();
            }
            if (this.M > 0) {
                if (this.M == 2) {
                    h.a().g();
                }
                this.M = 0;
                return;
            }
            return;
        }
        if (this.l != null) {
            a.a().a(this.l);
        }
        k();
    }

    private String[] j() {
        boolean z2;
        String[] strArr = {"", "Location failed beacuse we can not get any loc information!"};
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("&apl=");
        int a2 = j.a(f.getServiceContext());
        if (a2 == 1) {
            strArr[1] = "Location failed beacuse we can not get any loc information in airplane mode, you can turn it off and try again!!";
        }
        stringBuffer.append(a2);
        String c = j.c(f.getServiceContext());
        if (c.contains("0|0|")) {
            strArr[1] = "Location failed beacuse we can not get any loc information without any location permission!";
        }
        stringBuffer.append(c);
        if (Build.VERSION.SDK_INT >= 23) {
            stringBuffer.append("&loc=");
            int b2 = j.b(f.getServiceContext());
            if (b2 == 0) {
                strArr[1] = "Location failed beacuse we can not get any loc information with the phone loc mode is off, you can turn it on and try again!";
                z2 = true;
            } else {
                z2 = false;
            }
            stringBuffer.append(b2);
        } else {
            z2 = false;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            stringBuffer.append("&lmd=");
            int b3 = j.b(f.getServiceContext());
            if (b3 >= 0) {
                stringBuffer.append(b3);
            }
        }
        String g2 = com.baidu.location.d.b.a().g();
        String h2 = h.a().h();
        stringBuffer.append(h2);
        stringBuffer.append(g2);
        stringBuffer.append(j.d(f.getServiceContext()));
        if (a2 == 1) {
            b.a().a(62, 7, "Location failed beacuse we can not get any loc information in airplane mode, you can turn it off and try again!!");
        } else if (c.contains("0|0|")) {
            b.a().a(62, 4, "Location failed beacuse we can not get any loc information without any location permission!");
        } else if (z2) {
            b.a().a(62, 5, "Location failed beacuse we can not get any loc information with the phone loc mode is off, you can turn it on and try again!");
        } else if (g2 == null || h2 == null || !g2.equals("&sim=1") || h2.equals("&wifio=1")) {
            b.a().a(62, 9, "Location failed beacuse we can not get any loc information!");
        } else {
            b.a().a(62, 6, "Location failed beacuse we can not get any loc information , you can insert a sim card or open wifi and try again!");
        }
        strArr[0] = stringBuffer.toString();
        return strArr;
    }

    private void k() {
        this.s = false;
        this.G = false;
        this.H = false;
        this.B = false;
        l();
        if (this.O) {
            this.O = false;
        }
    }

    private void l() {
        if (this.l != null) {
            v.a().c();
            com.baidu.location.b.g.a().c();
        }
    }

    public Address a(BDLocation bDLocation) {
        if (j.g.equals("all") || j.h || j.i) {
            float[] fArr = new float[2];
            Location.distanceBetween(this.A, this.z, bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
            if (fArr[0] >= 100.0f) {
                this.x = null;
                this.y = null;
                this.B = true;
                g((Message) null);
            } else if (this.w != null) {
                return this.w;
            }
        }
        return null;
    }

    public void a() {
        if (this.E != null && this.F) {
            this.F = false;
            this.g.removeCallbacks(this.E);
        }
        if (e.a().j()) {
            BDLocation bDLocation = new BDLocation(e.a().g());
            if (j.g.equals("all") || j.h || j.i) {
                float[] fArr = new float[2];
                Location.distanceBetween(this.A, this.z, bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
                if (fArr[0] < 100.0f) {
                    if (this.w != null) {
                        bDLocation.setAddr(this.w);
                    }
                    if (this.x != null) {
                        bDLocation.setLocationDescribe(this.x);
                    }
                    if (this.y != null) {
                        bDLocation.setPoiList(this.y);
                    }
                }
            }
            a.a().a(bDLocation);
            k();
        } else if (this.G) {
            k();
        } else {
            if (this.j || this.l == null) {
                BDLocation bDLocation2 = new BDLocation();
                bDLocation2.setLocType(63);
                this.l = null;
                a.a().a(bDLocation2);
            } else {
                a.a().a(this.l);
            }
            this.m = null;
            k();
        }
    }

    public void a(Message message) {
        if (this.E != null && this.F) {
            this.F = false;
            this.g.removeCallbacks(this.E);
        }
        BDLocation bDLocation = (BDLocation) message.obj;
        if (bDLocation != null && bDLocation.getLocType() == 167 && this.J) {
            bDLocation.setLocType(62);
        }
        b(bDLocation);
    }

    public void b(Message message) {
        if (this.I) {
            c(message);
        }
    }

    public void b(BDLocation bDLocation) {
        String h2;
        int b2;
        boolean z2 = true;
        new BDLocation(bDLocation);
        if (bDLocation.hasAddr()) {
            this.w = bDLocation.getAddress();
            this.z = bDLocation.getLongitude();
            this.A = bDLocation.getLatitude();
        }
        if (bDLocation.getLocationDescribe() != null) {
            this.x = bDLocation.getLocationDescribe();
            this.z = bDLocation.getLongitude();
            this.A = bDLocation.getLatitude();
        }
        if (bDLocation.getPoiList() != null) {
            this.y = bDLocation.getPoiList();
            this.z = bDLocation.getLongitude();
            this.A = bDLocation.getLatitude();
        }
        if (e.a().j()) {
            BDLocation bDLocation2 = new BDLocation(e.a().g());
            if (j.g.equals("all") || j.h || j.i) {
                float[] fArr = new float[2];
                Location.distanceBetween(this.A, this.z, bDLocation2.getLatitude(), bDLocation2.getLongitude(), fArr);
                if (fArr[0] < 100.0f) {
                    if (this.w != null) {
                        bDLocation2.setAddr(this.w);
                    }
                    if (this.x != null) {
                        bDLocation2.setLocationDescribe(this.x);
                    }
                    if (this.y != null) {
                        bDLocation2.setPoiList(this.y);
                    }
                }
            }
            a.a().a(bDLocation2);
            k();
        } else if (this.G) {
            float[] fArr2 = new float[2];
            if (this.l != null) {
                Location.distanceBetween(this.l.getLatitude(), this.l.getLongitude(), bDLocation.getLatitude(), bDLocation.getLongitude(), fArr2);
            }
            if (fArr2[0] > 10.0f) {
                this.l = bDLocation;
                if (!this.H) {
                    this.H = false;
                    a.a().a(bDLocation);
                }
            } else if (bDLocation.getUserIndoorState() > -1) {
                this.l = bDLocation;
                a.a().a(bDLocation);
            }
            k();
        } else {
            if (bDLocation.getLocType() == 167) {
                b.a().a(167, 8, "NetWork location failed because baidu location service can not caculate the location!");
            } else if (bDLocation.getLocType() == 161) {
                if (Build.VERSION.SDK_INT >= 19 && ((b2 = j.b(f.getServiceContext())) == 0 || b2 == 2)) {
                    b.a().a(161, 1, "NetWork location successful, open gps will be better!");
                } else if (bDLocation.getRadius() >= 100.0f && bDLocation.getNetworkLocationType() != null && bDLocation.getNetworkLocationType().equals("cl") && (h2 = h.a().h()) != null && !h2.equals("&wifio=1")) {
                    b.a().a(161, 2, "NetWork location successful, open wifi will be better!");
                }
            }
            this.m = null;
            if (bDLocation.getLocType() != 161 || !"cl".equals(bDLocation.getNetworkLocationType()) || this.l == null || this.l.getLocType() != 161 || !"wf".equals(this.l.getNetworkLocationType()) || System.currentTimeMillis() - this.v >= 30000) {
                z2 = false;
            } else {
                this.m = bDLocation;
            }
            if (z2) {
                a.a().a(this.l);
            } else {
                a.a().a(bDLocation);
                this.v = System.currentTimeMillis();
            }
            if (!j.a(bDLocation)) {
                this.l = null;
            } else if (!z2) {
                this.l = bDLocation;
            }
            int a2 = j.a(c, "ssid\":\"", "\"");
            if (a2 == Integer.MIN_VALUE || this.n == null) {
                this.k = null;
            } else {
                this.k = this.n.c(a2);
            }
            if (h.j()) {
            }
            k();
        }
    }

    public void c(BDLocation bDLocation) {
        i();
        this.l = bDLocation;
        this.l.setIndoorLocMode(false);
    }

    public void d() {
        this.r = true;
        this.s = false;
        this.I = true;
    }

    public void d(BDLocation bDLocation) {
        this.l = new BDLocation(bDLocation);
    }

    public void e() {
        this.s = false;
        this.t = false;
        this.G = false;
        this.H = true;
        i();
        this.I = false;
    }

    public String f() {
        return this.x;
    }

    public List<Poi> g() {
        return this.y;
    }

    public void h() {
        if (this.t) {
            h((Message) null);
            this.t = false;
            return;
        }
        com.baidu.location.b.b.a().d();
    }

    public void i() {
        this.l = null;
    }
}

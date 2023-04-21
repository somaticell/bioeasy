package com.baidu.location.indoor;

import android.location.Location;
import android.net.wifi.ScanResult;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.com.bioeasy.app.utils.ListUtils;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Jni;
import com.baidu.location.a.l;
import com.baidu.location.a.n;
import com.baidu.location.f.j;
import com.baidu.location.indoor.a;
import com.baidu.location.indoor.d;
import com.baidu.location.indoor.mapversion.b.a;
import com.baidu.location.indoor.o;
import com.baidu.location.indoor.r;
import com.baidu.platform.comapi.location.CoordinateType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class g {
    private static g j;
    /* access modifiers changed from: private */
    public String A;
    /* access modifiers changed from: private */
    public m B;
    /* access modifiers changed from: private */
    public String C;
    /* access modifiers changed from: private */
    public String D;
    /* access modifiers changed from: private */
    public String E;
    /* access modifiers changed from: private */
    public int F;
    private boolean G;
    private int H;
    /* access modifiers changed from: private */
    public c<String> I;
    private int J;
    private c<String> K;
    /* access modifiers changed from: private */
    public double L;
    /* access modifiers changed from: private */
    public double M;
    /* access modifiers changed from: private */
    public double N;
    /* access modifiers changed from: private */
    public double O;
    /* access modifiers changed from: private */
    public boolean P;
    private boolean Q;
    /* access modifiers changed from: private */
    public List<C0010g> R;
    /* access modifiers changed from: private */
    public int S;
    /* access modifiers changed from: private */
    public int T;
    /* access modifiers changed from: private */
    public int U;
    /* access modifiers changed from: private */
    public a V;
    /* access modifiers changed from: private */
    public String W;
    /* access modifiers changed from: private */
    public d X;
    /* access modifiers changed from: private */
    public boolean Y;
    /* access modifiers changed from: private */
    public r Z;
    boolean a;
    private r.a aa;
    /* access modifiers changed from: private */
    public boolean ab;
    /* access modifiers changed from: private */
    public int ac;
    /* access modifiers changed from: private */
    public BDLocation ad;
    /* access modifiers changed from: private */
    public boolean ae;
    /* access modifiers changed from: private */
    public boolean af;
    /* access modifiers changed from: private */
    public boolean ag;
    /* access modifiers changed from: private */
    public List<Float> ah;
    boolean b;
    public e c;
    public SimpleDateFormat d;
    private final int e;
    private boolean f;
    private BDLocationListener g;
    private BDLocationListener h;
    /* access modifiers changed from: private */
    public int i;
    /* access modifiers changed from: private */
    public long k;
    /* access modifiers changed from: private */
    public volatile boolean l;
    /* access modifiers changed from: private */
    public o m;
    private f n;
    /* access modifiers changed from: private */
    public h o;
    /* access modifiers changed from: private */
    public long p;
    /* access modifiers changed from: private */
    public boolean q;
    /* access modifiers changed from: private */
    public boolean r;
    /* access modifiers changed from: private */
    public long s;
    /* access modifiers changed from: private */
    public int t;
    private int u;
    private o.a v;
    /* access modifiers changed from: private */
    public int w;
    /* access modifiers changed from: private */
    public int x;
    private int y;
    /* access modifiers changed from: private */
    public String z;

    class a {
        private HashMap<String, Integer> b = new HashMap<>();
        private double c = 0.0d;

        public a(com.baidu.location.d.g gVar) {
            if (gVar.a != null) {
                for (ScanResult next : gVar.a) {
                    int abs = Math.abs(next.level);
                    this.b.put(next.BSSID, Integer.valueOf(abs));
                    this.c = ((double) ((100 - abs) * (100 - abs))) + this.c;
                }
                this.c = Math.sqrt(this.c + 1.0d);
            }
        }

        /* access modifiers changed from: package-private */
        public double a(a aVar) {
            double d = 0.0d;
            for (String next : this.b.keySet()) {
                int intValue = this.b.get(next).intValue();
                Integer num = aVar.a().get(next);
                if (num != null) {
                    d = ((double) ((100 - num.intValue()) * (100 - intValue))) + d;
                }
            }
            return d / (this.c * aVar.b());
        }

        public HashMap<String, Integer> a() {
            return this.b;
        }

        public double b() {
            return this.c;
        }
    }

    class b {
        double a;
        double b;
        long c;
        int d;
        List<Float> e;
        boolean f;
        String g;
        String h;
        String i;
        boolean j = false;

        public b(double d2, double d3, long j2, int i2, List<Float> list, String str, String str2, String str3) {
            this.a = d2;
            this.b = d3;
            this.c = j2;
            this.d = i2;
            this.f = false;
            this.e = new ArrayList(list);
            this.g = str;
            this.h = str2;
            this.i = str3;
        }

        public double a() {
            return this.a;
        }

        public int a(b bVar) {
            return Math.abs(this.d - bVar.c());
        }

        public void a(double d2) {
            this.a = d2;
        }

        public void a(boolean z) {
            this.f = z;
        }

        public double b() {
            return this.b;
        }

        public float b(b bVar) {
            float[] fArr = new float[1];
            Location.distanceBetween(this.b, this.a, bVar.b, bVar.a, fArr);
            return fArr[0];
        }

        public void b(double d2) {
            this.b = d2;
        }

        public int c() {
            return this.d;
        }

        public boolean c(b bVar) {
            int a2 = a(bVar);
            return a2 != 0 && ((double) (b(bVar) / ((float) a2))) <= 1.0d + (0.5d * Math.pow(1.2d, (double) (1 - a2)));
        }

        public boolean d() {
            return this.f;
        }

        public Double e() {
            if (this.g == null) {
                return null;
            }
            return Double.valueOf(Double.parseDouble(this.g));
        }

        public Double f() {
            if (this.h == null) {
                return null;
            }
            return Double.valueOf(Double.parseDouble(this.h));
        }

        public Double g() {
            if (this.i == null) {
                return null;
            }
            return Double.valueOf(Double.parseDouble(this.i));
        }
    }

    class c {
        private b[] b;
        private int c;
        private int d;

        public c(g gVar) {
            this(5);
        }

        public c(int i) {
            this.b = new b[(i + 1)];
            this.c = 0;
            this.d = 0;
        }

        public b a() {
            return this.b[((this.d - 1) + this.b.length) % this.b.length];
        }

        public b a(int i) {
            return this.b[(((this.d - 1) - i) + this.b.length) % this.b.length];
        }

        public void a(b bVar) {
            if (this.c != this.d) {
                b a2 = a();
                if (a2.c() == bVar.c()) {
                    a2.a((a2.a() + bVar.a()) / 2.0d);
                    a2.b((a2.b() + bVar.b()) / 2.0d);
                    return;
                }
            }
            if (b()) {
                d();
            }
            b(bVar);
        }

        public boolean b() {
            return (this.d + 1) % this.b.length == this.c;
        }

        public boolean b(b bVar) {
            if (b()) {
                return false;
            }
            this.b[this.d] = bVar;
            this.d = (this.d + 1) % this.b.length;
            return true;
        }

        public boolean c() {
            return this.d == this.c;
        }

        public boolean c(b bVar) {
            if (g.this.af && g.this.ag) {
                return true;
            }
            if (c()) {
                return true;
            }
            if (bVar.c(a())) {
                return true;
            }
            if (a().d()) {
                return false;
            }
            for (int i = 0; i < e(); i++) {
                b a2 = a(i);
                if (a2.d() && a2.c(bVar)) {
                    return true;
                }
            }
            return false;
        }

        public boolean d() {
            if (this.c == this.d) {
                return false;
            }
            this.c = (this.c + 1) % this.b.length;
            return true;
        }

        public int e() {
            return ((this.d - this.c) + this.b.length) % this.b.length;
        }

        public String toString() {
            String str = "";
            for (int i = 0; i < e(); i++) {
                str = str + this.b[(this.c + i) % this.b.length].a + ListUtils.DEFAULT_JOIN_SEPARATOR;
            }
            String str2 = str + "  ";
            for (int i2 = 0; i2 < e(); i2++) {
                str2 = str2 + this.b[(this.c + i2) % this.b.length].b + ListUtils.DEFAULT_JOIN_SEPARATOR;
            }
            String str3 = str2 + "  ";
            for (int i3 = 0; i3 < e(); i3++) {
                str3 = str3 + this.b[(this.c + i3) % this.b.length].d + ListUtils.DEFAULT_JOIN_SEPARATOR;
            }
            return str3 + "  ";
        }
    }

    class d {
        private b[] b;
        private int c;
        private int d;

        public d(g gVar) {
            this(5);
        }

        public d(int i) {
            this.b = new b[(i + 1)];
            this.c = 0;
            this.d = 0;
        }

        public b a() {
            return this.b[((this.d - 1) + this.b.length) % this.b.length];
        }

        public boolean a(b bVar) {
            if (bVar.g() == null || bVar.f() == null) {
                return false;
            }
            double doubleValue = bVar.g().doubleValue();
            if (bVar.f().doubleValue() > 1.0d && doubleValue > 8.0d) {
                return false;
            }
            if (d()) {
                return true;
            }
            b a2 = a();
            double doubleValue2 = a2.e().doubleValue();
            double doubleValue3 = bVar.e().doubleValue();
            double a3 = n.a(a2.e);
            double a4 = n.a(bVar.e);
            double a5 = n.a(doubleValue2, doubleValue3);
            double b2 = n.b(a3, a4);
            double abs = Math.abs(Math.abs(a5) - Math.abs(b2));
            if (Math.abs(b2) <= 15.0d) {
                return Math.abs(a5) <= Math.abs(b2) * 2.0d && abs <= 20.0d;
            }
            g.this.o.t.g();
            return false;
        }

        public float b() {
            if (f() < 4) {
                return 0.0f;
            }
            ArrayList arrayList = new ArrayList();
            int i = 2;
            while (true) {
                int i2 = i;
                if (i2 > f()) {
                    return (float) n.a(arrayList);
                }
                b bVar = this.b[(((this.d - i2) + 1) + this.b.length) % this.b.length];
                b bVar2 = this.b[((this.d - i2) + this.b.length) % this.b.length];
                double b2 = n.b(bVar2.b, bVar2.a, bVar.b, bVar.a);
                double degrees = 90.0d - Math.toDegrees(Math.atan(bVar.e().doubleValue()));
                if (Math.abs(n.b(degrees, b2)) >= Math.abs(n.b(degrees + 180.0d, b2))) {
                    degrees += 180.0d;
                }
                arrayList.add(Float.valueOf((float) n.b(n.a(bVar.e), degrees)));
                i = i2 + 1;
            }
        }

        public boolean b(b bVar) {
            if (c()) {
                e();
            }
            return c(bVar);
        }

        public boolean c() {
            return (this.d + 1) % this.b.length == this.c;
        }

        public boolean c(b bVar) {
            if (c()) {
                return false;
            }
            this.b[this.d] = bVar;
            this.d = (this.d + 1) % this.b.length;
            return true;
        }

        public boolean d() {
            return this.d == this.c;
        }

        public boolean e() {
            if (this.c == this.d) {
                return false;
            }
            this.c = (this.c + 1) % this.b.length;
            return true;
        }

        public int f() {
            return ((this.d - this.c) + this.b.length) % this.b.length;
        }

        public void g() {
            this.d = 0;
            this.c = 0;
        }

        public String toString() {
            String str = "";
            for (int i = 0; i < f(); i++) {
                str = str + this.b[(this.c + i) % this.b.length].a + ListUtils.DEFAULT_JOIN_SEPARATOR;
            }
            String str2 = str + "  ";
            for (int i2 = 0; i2 < f(); i2++) {
                str2 = str2 + this.b[(this.c + i2) % this.b.length].b + ListUtils.DEFAULT_JOIN_SEPARATOR;
            }
            String str3 = str2 + "  ";
            for (int i3 = 0; i3 < f(); i3++) {
                str3 = str3 + this.b[(this.c + i3) % this.b.length].d + ListUtils.DEFAULT_JOIN_SEPARATOR;
            }
            return str3 + "  ";
        }
    }

    public class e extends Handler {
        public e() {
        }

        public void handleMessage(Message message) {
            if (com.baidu.location.f.isServing) {
                switch (message.what) {
                    case 21:
                        g.this.a(message);
                        return;
                    case 28:
                        g.this.b(message);
                        return;
                    case 41:
                        g.this.l();
                        return;
                    case 801:
                        g.this.a((BDLocation) message.obj);
                        return;
                    default:
                        super.dispatchMessage(message);
                        return;
                }
            }
        }
    }

    class f extends Thread {
        /* access modifiers changed from: private */
        public volatile boolean b = true;
        private long c = 0;

        f() {
        }

        public void run() {
            while (this.b) {
                if ((((!g.this.l || System.currentTimeMillis() - this.c <= g.this.k) && System.currentTimeMillis() - this.c <= 10000) || g.this.m.c() != 1) && System.currentTimeMillis() - this.c <= 17500) {
                    boolean z = !com.baidu.location.d.e.a().j() ? true : g.this.ae;
                    if (g.this.m.c() != 1 && z) {
                        com.baidu.location.a.a.a().c();
                    }
                } else {
                    com.baidu.location.d.h.a().i();
                    g.this.m.f();
                    this.c = System.currentTimeMillis();
                    boolean unused = g.this.l = false;
                }
                if (System.currentTimeMillis() - g.this.p > 22000) {
                    g.this.c.sendEmptyMessage(41);
                }
                if (System.currentTimeMillis() - g.this.s > 60000) {
                    g.a().d();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    this.b = false;
                    return;
                }
            }
        }
    }

    /* renamed from: com.baidu.location.indoor.g$g  reason: collision with other inner class name */
    private class C0010g {
        public int a;
        public double b;
        public double c;
        public int d = 1;

        public C0010g(int i, double d2, double d3) {
            this.a = i;
            this.b = d2;
            this.c = d3;
        }

        public String toString() {
            return String.format("%d:%.1f:%.2f", new Object[]{Integer.valueOf(this.d), Double.valueOf(this.c), Double.valueOf(this.b)});
        }
    }

    class h extends com.baidu.location.f.e {
        public float a = 0.0f;
        private boolean c = false;
        private boolean d = false;
        private String e = null;
        private String f = null;
        /* access modifiers changed from: private */
        public List<Float> p = new ArrayList();
        private a q = null;
        private c r = null;
        /* access modifiers changed from: private */
        public d s = null;
        /* access modifiers changed from: private */
        public d t = null;
        private int u = -1;
        private long v = 0;
        private long w = 0;

        public h() {
            this.k = new HashMap();
            this.r = new c(g.this);
            this.s = new d(g.this);
            this.t = new d(6);
        }

        private boolean a(com.baidu.location.d.g gVar, double d2) {
            a aVar = new a(gVar);
            if (this.q != null && aVar.a(this.q) > d2) {
                return false;
            }
            this.q = aVar;
            return true;
        }

        public void a() {
            this.h = j.c();
            if (g.this.A == null || g.this.B == null || !g.this.A.equals(g.this.B.a())) {
                this.e = "&nd_idf=1&indoor_polygon=1" + this.e;
            }
            this.i = 1;
            String encodeTp4 = Jni.encodeTp4(this.e);
            this.e = null;
            this.k.put("bloc", encodeTp4);
            this.v = System.currentTimeMillis();
        }

        public void a(boolean z) {
            if (!z || this.j == null) {
                g.H(g.this);
                int unused = g.this.ac = 0;
                boolean unused2 = g.this.ab = true;
                this.c = false;
                if (g.this.t > 40) {
                    g.this.d();
                } else {
                    return;
                }
            } else {
                try {
                    String str = this.j;
                    if (!g.this.q) {
                        this.c = false;
                        return;
                    }
                    BDLocation bDLocation = new BDLocation(str);
                    if (!(bDLocation == null || bDLocation.getLocType() != 161 || bDLocation.getBuildingID() == null)) {
                        BDLocation unused3 = g.this.ad = new BDLocation(bDLocation);
                    }
                    boolean unused4 = g.this.ab = false;
                    String indoorLocationSurpportBuidlingName = bDLocation.getIndoorLocationSurpportBuidlingName();
                    if (indoorLocationSurpportBuidlingName == null) {
                        Log.w(com.baidu.location.f.a.a, "inbldg is null");
                    } else if (!g.this.V.a(indoorLocationSurpportBuidlingName)) {
                        g.this.V.a(indoorLocationSurpportBuidlingName, (a.C0009a) null);
                    }
                    if (g.this.X != null) {
                        g.this.X.a((d.b) new l(this));
                    }
                    n.a().b(true);
                    if (g.this.m.d() == -1) {
                        g.this.b = false;
                    }
                    if (bDLocation.getBuildingName() != null) {
                        String unused5 = g.this.D = bDLocation.getBuildingName();
                    }
                    if (bDLocation.getFloor() != null) {
                        long unused6 = g.this.s = System.currentTimeMillis();
                        this.w = System.currentTimeMillis();
                        int i = (int) (this.w - this.v);
                        if (i > 10000) {
                            int unused7 = g.this.ac = 0;
                        } else if (i < 3000) {
                            int unused8 = g.this.ac = 2;
                        } else {
                            int unused9 = g.this.ac = 1;
                        }
                        if (bDLocation.getFloor().contains("-a")) {
                            boolean unused10 = g.this.P = true;
                            bDLocation.setFloor(bDLocation.getFloor().split("-")[0]);
                        } else {
                            boolean unused11 = g.this.P = false;
                        }
                        g.this.I.add(bDLocation.getFloor());
                    }
                    if (!g.this.a || !g.this.b) {
                        Message obtainMessage = g.this.c.obtainMessage(21);
                        obtainMessage.obj = bDLocation;
                        obtainMessage.sendToTarget();
                    } else {
                        b bVar = new b(bDLocation.getLongitude(), bDLocation.getLatitude(), System.currentTimeMillis(), g.this.m.d(), this.p, bDLocation.getRetFields("gradient"), bDLocation.getRetFields("mean_error"), bDLocation.getRetFields("confidence"));
                        if (this.r.c(bVar)) {
                            bVar.a(true);
                            Message obtainMessage2 = g.this.c.obtainMessage(21);
                            obtainMessage2.obj = bDLocation;
                            obtainMessage2.sendToTarget();
                        } else {
                            g.this.n();
                        }
                        if (bDLocation.getFloor() != null) {
                            this.r.a(bVar);
                        }
                    }
                } catch (Exception e2) {
                }
            }
            if (this.k != null) {
                this.k.clear();
            }
            this.c = false;
        }

        public void b() {
            if (this.c) {
                this.d = true;
                return;
            }
            StringBuffer stringBuffer = new StringBuffer(1024);
            String g = com.baidu.location.d.b.a().f().g();
            String f2 = com.baidu.location.d.e.a().f();
            double unused = g.this.N = 0.5d;
            com.baidu.location.d.g q2 = com.baidu.location.d.h.a().q();
            String a2 = g.this.a(q2);
            String a3 = a2 == null ? q2.a(32, true, false) : a2;
            if (a3 != null && a3.length() >= 10) {
                if (this.f == null || !this.f.equals(a3)) {
                    this.f = a3;
                    int d2 = g.this.m.d();
                    boolean z = this.u < 0 || d2 - this.u > g.this.i;
                    if (!g.this.a || !g.this.b) {
                        if (g.this.a && g.this.r && !a(q2, 0.7d) && !z) {
                            return;
                        }
                    } else if (g.this.r && !a(q2, 0.8d) && !z) {
                        return;
                    }
                    this.u = d2;
                    this.c = true;
                    stringBuffer.append(g);
                    if (f2 != null) {
                        stringBuffer.append(f2);
                    }
                    stringBuffer.append("&coor=gcj02");
                    stringBuffer.append("&lt=1");
                    stringBuffer.append(a3);
                    if (g.this.T <= 2 && g.this.m.h() != null) {
                        stringBuffer.append("&idsl=" + g.this.m.h());
                    }
                    int size = g.this.R.size();
                    stringBuffer.append(g.this.a(size));
                    int unused2 = g.this.S = size;
                    g.L(g.this);
                    stringBuffer.append("&drsi=" + g.this.T);
                    stringBuffer.append("&drc=" + g.this.x);
                    if (!(g.this.L == 0.0d || g.this.M == 0.0d)) {
                        stringBuffer.append("&lst_idl=" + String.format(Locale.CHINA, "%.5f:%.5f", new Object[]{Double.valueOf(g.this.L), Double.valueOf(g.this.M)}));
                    }
                    int unused3 = g.this.x = 0;
                    stringBuffer.append("&idpfv=1");
                    if (g.this.m != null && g.this.m.g()) {
                        stringBuffer.append("&pdr2=1");
                    }
                    if (!(g.this.X == null || g.this.X.e() == null || !g.this.X.g())) {
                        stringBuffer.append("&bleand=").append(g.this.X.e());
                        stringBuffer.append("&bleand_et=").append(g.this.X.f());
                    }
                    g.N(g.this);
                    if (g.this.W != null) {
                        stringBuffer.append(g.this.W);
                        String unused4 = g.this.W = null;
                    }
                    String f3 = com.baidu.location.a.a.a().f();
                    if (f3 != null) {
                        stringBuffer.append(f3);
                    }
                    stringBuffer.append(com.baidu.location.f.b.a().a(true));
                    this.e = stringBuffer.toString();
                    c(j.f);
                }
            }
        }

        public synchronized void c() {
            if (!this.c) {
                if (this.d) {
                    this.d = false;
                    b();
                }
            }
        }
    }

    private g() {
        this.e = 32;
        this.a = false;
        this.b = false;
        this.i = 5;
        this.k = 3000;
        this.l = true;
        this.c = null;
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = 0;
        this.q = false;
        this.r = false;
        this.s = 0;
        this.t = 0;
        this.u = 0;
        this.w = 0;
        this.x = 0;
        this.y = 0;
        this.z = null;
        this.A = null;
        this.B = null;
        this.C = null;
        this.D = null;
        this.E = null;
        this.F = 0;
        this.G = true;
        this.H = 7;
        this.I = null;
        this.J = 20;
        this.K = null;
        this.L = 0.0d;
        this.M = 0.0d;
        this.N = 0.4d;
        this.O = 0.0d;
        this.P = false;
        this.Q = true;
        this.R = Collections.synchronizedList(new ArrayList());
        this.S = -1;
        this.T = 0;
        this.U = 0;
        this.W = null;
        this.X = null;
        this.Y = false;
        this.ab = false;
        this.d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.ac = 2;
        this.ad = null;
        this.ae = false;
        this.af = false;
        this.ag = false;
        this.ah = Collections.synchronizedList(new ArrayList());
        this.f = false;
        this.g = new h(this);
        this.c = new e();
        try {
            com.baidu.location.indoor.mapversion.b.a.a(com.baidu.location.f.getServiceContext());
        } catch (Exception e2) {
        }
        this.Z = new r();
        this.Z.a(800);
        this.aa = new i(this);
        this.v = new j(this);
        this.m = new o(com.baidu.location.f.getServiceContext(), this.v);
        this.o = new h();
        this.I = new c<>(this.H);
        this.K = new c<>(this.J);
        this.V = new a(com.baidu.location.f.getServiceContext());
    }

    static /* synthetic */ int H(g gVar) {
        int i2 = gVar.t;
        gVar.t = i2 + 1;
        return i2;
    }

    static /* synthetic */ int L(g gVar) {
        int i2 = gVar.T;
        gVar.T = i2 + 1;
        return i2;
    }

    static /* synthetic */ int N(g gVar) {
        int i2 = gVar.U;
        gVar.U = i2 + 1;
        return i2;
    }

    public static synchronized g a() {
        g gVar;
        synchronized (g.class) {
            if (j == null) {
                j = new g();
            }
            gVar = j;
        }
        return gVar;
    }

    /* access modifiers changed from: private */
    public String a(int i2) {
        if (this.R.size() == 0) {
            return "&dr=0:0";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("&dr=");
        this.R.get(0).d = 1;
        sb.append(this.R.get(0).toString());
        int i3 = 1;
        int i4 = this.R.get(0).a;
        while (i3 < this.R.size() && i3 <= i2) {
            this.R.get(i3).d = this.R.get(i3).a - i4;
            sb.append(com.alipay.sdk.util.h.b);
            sb.append(this.R.get(i3).toString());
            int i5 = this.R.get(i3).a;
            i3++;
            i4 = i5;
        }
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public String a(com.baidu.location.d.g gVar) {
        int a2 = gVar.a();
        if (a2 <= 32) {
            return gVar.a(32, true, true) + "&aprk=0";
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < a2; i2++) {
            String lowerCase = gVar.a.get(i2).BSSID.replaceAll(":", "").toLowerCase();
            if (this.V == null || !this.V.b(lowerCase)) {
                arrayList2.add(gVar.a.get(i2));
            } else {
                arrayList.add(gVar.a.get(i2));
            }
        }
        String str = arrayList.size() > 0 ? "&aprk=3" : "";
        if (str.equals("")) {
            str = this.V.b() ? "&aprk=2" : "&aprk=1";
        }
        arrayList.addAll(arrayList2);
        gVar.a = arrayList;
        return gVar.a(32, true, true) + str;
    }

    /* access modifiers changed from: private */
    public void a(Message message) {
        if (this.q) {
            BDLocation bDLocation = (BDLocation) message.obj;
            if (bDLocation.getLocType() == 161) {
                n();
                if (!(bDLocation.getIndoorSurpportPolygon() == null || bDLocation.getIndoorLocationSurpportBuidlingID() == null || (this.B != null && this.B.a().equals(bDLocation.getBuildingID())))) {
                    String[] split = bDLocation.getIndoorSurpportPolygon().split("\\|");
                    Location[] locationArr = new Location[split.length];
                    for (int i2 = 0; i2 < split.length; i2++) {
                        String[] split2 = split[i2].split(ListUtils.DEFAULT_JOIN_SEPARATOR);
                        Location location = new Location("gps");
                        location.setLatitude(Double.valueOf(split2[1]).doubleValue());
                        location.setLongitude(Double.valueOf(split2[0]).doubleValue());
                        locationArr[i2] = location;
                    }
                    this.B = new m(bDLocation.getIndoorLocationSurpportBuidlingID(), locationArr);
                }
                if (this.Q && this.X != null) {
                    if ((((bDLocation.getIndoorLocationSource() >> 2) & 1) == 1) && this.X.a()) {
                        this.Q = false;
                        this.X.b();
                    }
                }
                this.t = 0;
                if (bDLocation.getBuildingID() == null) {
                    this.r = false;
                    this.u++;
                    if (this.u > 3) {
                        d();
                    }
                } else {
                    this.w = 0;
                    this.u = 0;
                    this.r = true;
                    bDLocation.setIndoorLocMode(true);
                    if (bDLocation.getRetFields("tp") == null || !bDLocation.getRetFields("tp").equalsIgnoreCase("ble")) {
                        this.Y = false;
                    } else {
                        bDLocation.setRadius(8.0f);
                        bDLocation.setNetworkLocationType("ble");
                        this.Y = true;
                    }
                    if (this.L < 0.1d || this.M < 0.1d) {
                        this.M = bDLocation.getLatitude();
                        this.L = bDLocation.getLongitude();
                    }
                    if (this.z == null) {
                        this.z = bDLocation.getFloor();
                    }
                    a(bDLocation.getBuildingName(), bDLocation.getFloor());
                    String retFields = bDLocation.getRetFields("pdr2");
                    if (retFields != null && retFields.equals(com.alipay.sdk.cons.a.e)) {
                        this.m.a(true);
                    }
                    this.A = bDLocation.getBuildingID();
                    this.C = bDLocation.getBuildingName();
                    this.E = bDLocation.getNetworkLocationType();
                    this.F = bDLocation.isParkAvailable();
                    if (bDLocation.getFloor().equals(m())) {
                        boolean equalsIgnoreCase = bDLocation.getFloor().equalsIgnoreCase(this.z);
                        if (!equalsIgnoreCase && this.af) {
                            com.baidu.location.indoor.mapversion.a.a.b();
                            this.ag = com.baidu.location.indoor.mapversion.a.a.a(bDLocation.getFloor());
                        }
                        this.z = bDLocation.getFloor();
                        if (!equalsIgnoreCase) {
                            j();
                        }
                        if (this.m != null && this.m.e() >= 0.0d && bDLocation.getDirection() <= 0.0f) {
                            bDLocation.setDirection((float) this.m.e());
                        }
                        boolean z2 = false;
                        if (!this.af || (z2 = com.baidu.location.indoor.mapversion.a.a.a(bDLocation))) {
                        }
                        if ((!this.af || !z2) && !this.P && equalsIgnoreCase) {
                            double longitude = (this.L * ((double) 1000000) * this.N) + ((1.0d - this.N) * bDLocation.getLongitude() * ((double) 1000000));
                            bDLocation.setLatitude((((this.M * ((double) 1000000)) * this.N) + ((1.0d - this.N) * (bDLocation.getLatitude() * ((double) 1000000)))) / ((double) 1000000));
                            bDLocation.setLongitude(longitude / ((double) 1000000));
                        }
                        this.M = bDLocation.getLatitude();
                        this.L = bDLocation.getLongitude();
                    } else {
                        return;
                    }
                }
                if (bDLocation.getNetworkLocationType() != null && !bDLocation.getNetworkLocationType().equals("ble")) {
                    l.c().c(bDLocation);
                }
            } else if (bDLocation.getLocType() == 63) {
                this.t++;
                this.r = false;
                this.ab = true;
                if (this.t > 10) {
                    d();
                } else {
                    return;
                }
            } else {
                this.t = 0;
                this.r = false;
            }
            if (this.r) {
                if (bDLocation.getTime() == null) {
                    bDLocation.setTime(this.d.format(new Date()));
                }
                if (bDLocation.getNetworkLocationType().equals("wf")) {
                    b bVar = new b(bDLocation.getLongitude(), bDLocation.getLatitude(), System.currentTimeMillis(), this.m.d(), this.ah, bDLocation.getRetFields("gradient"), bDLocation.getRetFields("mean_error"), bDLocation.getRetFields("confidence"));
                    this.ah.clear();
                    if (!bVar.e.isEmpty()) {
                        if (this.o.s.a(bVar)) {
                            this.o.t.b(bVar);
                        }
                        this.o.a = this.o.t.b();
                        this.o.s.b(bVar);
                    }
                    bDLocation.setDirection((float) this.O);
                }
                BDLocation bDLocation2 = new BDLocation(bDLocation);
                bDLocation2.setNetworkLocationType(bDLocation2.getNetworkLocationType() + "2");
                if (this.Z == null || !this.Z.c()) {
                    a(bDLocation2, 21);
                } else if (((long) this.U) > 2) {
                    this.Z.a(bDLocation2);
                } else {
                    a(bDLocation2, 29);
                }
            }
            this.o.c();
        }
    }

    /* access modifiers changed from: private */
    public void a(BDLocation bDLocation) {
    }

    /* access modifiers changed from: private */
    public void a(BDLocation bDLocation, int i2) {
        if (this.ad != null) {
            if (bDLocation.getAddrStr() == null && this.ad.getAddrStr() != null) {
                bDLocation.setAddr(this.ad.getAddress());
                bDLocation.setAddrStr(this.ad.getAddrStr());
            }
            if (bDLocation.getPoiList() == null && this.ad.getPoiList() != null) {
                bDLocation.setPoiList(this.ad.getPoiList());
            }
            if (bDLocation.getLocationDescribe() == null && this.ad.getLocationDescribe() != null) {
                bDLocation.setLocationDescribe(this.ad.getLocationDescribe());
            }
        }
        if (this.f && this.h != null) {
            bDLocation.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(System.currentTimeMillis())));
            if (bDLocation.getNetworkLocationType().contains("2")) {
                String networkLocationType = bDLocation.getNetworkLocationType();
                bDLocation.setNetworkLocationType(networkLocationType.substring(0, networkLocationType.length() - 1));
                this.h.onReceiveLocation(bDLocation);
                return;
            }
            BDLocation bDLocation2 = new BDLocation(bDLocation);
            Message obtainMessage = this.c.obtainMessage(801);
            obtainMessage.obj = bDLocation2;
            obtainMessage.sendToTarget();
        } else if (bDLocation != null && !com.baidu.location.d.e.a().j()) {
            bDLocation.setUserIndoorState(1);
            bDLocation.setIndoorNetworkState(this.ac);
            com.baidu.location.a.a.a().a(bDLocation);
        } else if (bDLocation != null && com.baidu.location.d.e.a().j() && this.ae) {
            bDLocation.setUserIndoorState(1);
            bDLocation.setIndoorNetworkState(this.ac);
            com.baidu.location.a.a.a().a(bDLocation);
        }
    }

    private void a(String str, String str2) {
        if (this.C == null || !this.C.equals(str) || !this.af) {
            com.baidu.location.indoor.mapversion.b.a a2 = com.baidu.location.indoor.mapversion.b.a.a();
            a2.a(CoordinateType.GCJ02);
            a2.a(str, (a.c) new k(this, str, str2));
        }
    }

    /* access modifiers changed from: private */
    public double[] a(double d2, double d3, double d4, double d5) {
        double radians = Math.toRadians(d2);
        double radians2 = Math.toRadians(d3);
        double radians3 = Math.toRadians(d5);
        double asin = Math.asin((Math.sin(radians) * Math.cos(d4 / 6378137.0d)) + (Math.cos(radians) * Math.sin(d4 / 6378137.0d) * Math.cos(radians3)));
        return new double[]{Math.toDegrees(asin), Math.toDegrees(Math.atan2(Math.sin(radians3) * Math.sin(d4 / 6378137.0d) * Math.cos(radians), Math.cos(d4 / 6378137.0d) - (Math.sin(radians) * Math.sin(asin))) + radians2)};
    }

    /* access modifiers changed from: private */
    public void b(Message message) {
        BDLocation bDLocation = (BDLocation) message.obj;
        if (this.L < 0.1d || this.M < 0.1d) {
            this.M = bDLocation.getLatitude();
            this.L = bDLocation.getLongitude();
        }
        this.I.add(bDLocation.getFloor());
        this.z = m();
        bDLocation.setFloor(this.z);
        double longitude = (this.L * ((double) 1000000) * this.N) + ((1.0d - this.N) * bDLocation.getLongitude() * ((double) 1000000));
        bDLocation.setLatitude((((this.M * ((double) 1000000)) * this.N) + ((1.0d - this.N) * (bDLocation.getLatitude() * ((double) 1000000)))) / ((double) 1000000));
        bDLocation.setLongitude(longitude / ((double) 1000000));
        bDLocation.setTime(this.d.format(new Date()));
        this.M = bDLocation.getLatitude();
        this.L = bDLocation.getLongitude();
        a(bDLocation, 21);
    }

    private void j() {
        this.Z.b();
        this.U = 0;
        this.o.s.g();
        this.o.t.g();
        this.o.a = 0.0f;
        this.o.p.clear();
        this.ah.clear();
        this.R.clear();
    }

    private void k() {
        this.I.clear();
        this.K.clear();
        this.s = 0;
        this.t = 0;
        this.F = 0;
        this.y = 0;
        this.z = null;
        this.ab = false;
        this.A = null;
        this.C = null;
        this.D = null;
        this.E = null;
        this.G = true;
        this.Q = true;
        this.N = 0.4d;
        this.Y = false;
        this.L = 0.0d;
        this.M = 0.0d;
        this.w = 0;
        this.u = 0;
        this.P = false;
        this.T = 0;
        this.x = 0;
        if (this.af) {
            com.baidu.location.indoor.mapversion.a.a.b();
            com.baidu.location.indoor.mapversion.b.a.a().b();
        }
        this.ag = false;
        this.af = false;
        n.a().b(false);
        if (this.X != null) {
            this.X.c();
        }
    }

    /* access modifiers changed from: private */
    public void l() {
        if (this.q) {
            this.l = true;
            this.o.b();
            this.p = System.currentTimeMillis();
        }
    }

    private String m() {
        int i2;
        String str;
        HashMap hashMap = new HashMap();
        int size = this.I.size();
        String str2 = null;
        int i3 = -1;
        int i4 = 0;
        String str3 = "";
        while (i4 < size) {
            try {
                String str4 = (String) this.I.get(i4);
                str3 = str3 + str4 + "|";
                if (hashMap.containsKey(str4)) {
                    hashMap.put(str4, Integer.valueOf(((Integer) hashMap.get(str4)).intValue() + 1));
                } else {
                    hashMap.put(str4, 1);
                }
                i4++;
            } catch (Exception e2) {
                return this.z;
            }
        }
        for (String str5 : hashMap.keySet()) {
            if (((Integer) hashMap.get(str5)).intValue() > i3) {
                str = str5;
                i2 = ((Integer) hashMap.get(str5)).intValue();
            } else {
                i2 = i3;
                str = str2;
            }
            i3 = i2;
            str2 = str;
        }
        return (size != this.H || this.z.equals(str2)) ? str2 == null ? this.z : (size < 3 || size > this.H || !((String) this.I.get(size + -3)).equals(this.I.get(size + -1)) || !((String) this.I.get(size + -2)).equals(this.I.get(size + -1)) || ((String) this.I.get(size + -1)).equals(str2)) ? str2 : (String) this.I.get(size - 1) : (!((String) this.I.get(size + -3)).equals(str2) || !((String) this.I.get(size + -2)).equals(str2) || !((String) this.I.get(size + -1)).equals(str2)) ? this.z : str2;
    }

    /* access modifiers changed from: private */
    public void n() {
        for (int i2 = this.S; i2 >= 0 && this.R.size() > 0; i2--) {
            this.R.remove(0);
        }
        this.S = -1;
    }

    static /* synthetic */ int p(g gVar) {
        int i2 = gVar.w;
        gVar.w = i2 + 1;
        return i2;
    }

    static /* synthetic */ int t(g gVar) {
        int i2 = gVar.x;
        gVar.x = i2 + 1;
        return i2;
    }

    public boolean a(Location location) {
        if (location == null || this.B == null || !this.B.a(location.getLatitude(), location.getLongitude())) {
            this.ae = false;
        } else {
            this.ae = true;
        }
        return this.ae;
    }

    public synchronized void b() {
        if (this.q) {
            this.I.clear();
        }
    }

    public synchronized void c() {
        if (!this.q) {
            this.s = System.currentTimeMillis();
            this.m.a();
            com.baidu.location.a.a.a().d();
            this.n = new f();
            this.n.start();
            this.r = false;
            this.q = true;
            this.X = d.a(com.baidu.location.f.getServiceContext());
            this.T = 0;
            this.x = 0;
            n.a().b(true);
        }
    }

    public synchronized void d() {
        if (this.q) {
            this.m.b();
            if (this.Z != null && this.Z.c()) {
                this.Z.a();
            }
            if (this.V != null) {
                this.V.c();
            }
            if (this.X != null) {
                this.X.d();
            }
            if (this.n != null) {
                boolean unused = this.n.b = false;
                this.n.interrupt();
                this.n = null;
            }
            k();
            this.r = false;
            this.q = false;
            com.baidu.location.a.a.a().e();
        }
    }

    public synchronized void e() {
    }

    public boolean f() {
        return this.q;
    }

    public boolean g() {
        return this.q && this.r;
    }

    public String h() {
        return this.z;
    }

    public String i() {
        return this.A;
    }
}

package com.loc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.alipay.android.phone.mrpc.core.Headers;
import com.facebook.common.util.UriUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class cb {
    private static float L = 1.1f;
    private static float M = 2.2f;
    private static float N = 2.3f;
    private static float O = 3.8f;
    private static int P = 3;
    private static int Q = 10;
    private static int R = 2;
    private static int S = 7;
    /* access modifiers changed from: private */
    public static int T = 20;
    private static int U = 70;
    private static int V = 120;
    protected static boolean a = false;
    protected static boolean b = true;
    /* access modifiers changed from: private */
    public static int c = 10;
    /* access modifiers changed from: private */
    public static int d = 2;
    /* access modifiers changed from: private */
    public static int e = 10;
    /* access modifiers changed from: private */
    public static int f = 10;
    /* access modifiers changed from: private */
    public static int g = 50;
    /* access modifiers changed from: private */
    public static int h = 200;
    private static Object i = new Object();
    private static cb j;
    /* access modifiers changed from: private */
    public volatile Handler A = null;
    private cx B = new cx(this);
    /* access modifiers changed from: private */
    public LocationListener C = new cr(this);
    private BroadcastReceiver D = new cs(this);
    private BroadcastReceiver E = new ct(this);
    /* access modifiers changed from: private */
    public GpsStatus F = null;
    /* access modifiers changed from: private */
    public int G = 0;
    /* access modifiers changed from: private */
    public int H = 0;
    /* access modifiers changed from: private */
    public HashMap I = null;
    /* access modifiers changed from: private */
    public int J = 0;
    /* access modifiers changed from: private */
    public int K = 0;
    private boolean k = false;
    /* access modifiers changed from: private */
    public int l = -1;
    private int m = 0;
    private int n = 0;
    private Context o;
    /* access modifiers changed from: private */
    public LocationManager p;
    /* access modifiers changed from: private */
    public cl q;
    private cz r;
    private df s;
    private ci t;
    private de u;
    /* access modifiers changed from: private */
    public cy v;
    private cc w;
    private Thread x = null;
    /* access modifiers changed from: private */
    public Looper y = null;
    /* access modifiers changed from: private */
    public cw z = null;

    private cb(Context context) {
        this.o = context;
        this.q = cl.a(context);
        this.w = new cc();
        this.r = new cz(this.q);
        this.t = new ci(context);
        this.s = new df(this.t);
        this.u = new de(this.t);
        this.p = (LocationManager) this.o.getSystemService(Headers.LOCATION);
        this.v = cy.a(this.o);
        this.v.a(this.B);
        o();
        List<String> allProviders = this.p.getAllProviders();
        this.k = allProviders != null && allProviders.contains("gps") && allProviders.contains("passive");
        if (context != null) {
            cl.a = context.getPackageName();
        } else {
            Log.d(cl.a, "Error: No SD Card!");
        }
    }

    static /* synthetic */ int a(cb cbVar, dh dhVar, int i2) {
        if (cbVar.J >= Q) {
            return 1;
        }
        if (cbVar.J <= P) {
            return 4;
        }
        double c2 = dhVar.c();
        if (c2 <= ((double) L)) {
            return 1;
        }
        if (c2 >= ((double) M)) {
            return 4;
        }
        double b2 = dhVar.b();
        if (b2 <= ((double) N)) {
            return 1;
        }
        if (b2 >= ((double) O)) {
            return 4;
        }
        if (i2 >= S) {
            return 1;
        }
        if (i2 <= R) {
            return 4;
        }
        if (cbVar.I != null) {
            return cbVar.a(cbVar.I);
        }
        return 3;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v34, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: double[]} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int a(java.util.HashMap r13) {
        /*
            r12 = this;
            int r0 = r12.G
            r1 = 4
            if (r0 <= r1) goto L_0x013d
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r0 = 0
            java.util.Set r1 = r13.entrySet()
            java.util.Iterator r2 = r1.iterator()
            r1 = r0
        L_0x0019:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x0041
            java.lang.Object r0 = r2.next()
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            java.lang.Object r0 = r0.getValue()
            java.util.List r0 = (java.util.List) r0
            if (r0 == 0) goto L_0x013f
            double[] r0 = r12.a((java.util.List) r0)
            if (r0 == 0) goto L_0x013f
            r3.add(r0)
            int r0 = r1 + 1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r4.add(r1)
        L_0x003f:
            r1 = r0
            goto L_0x0019
        L_0x0041:
            boolean r0 = r3.isEmpty()
            if (r0 != 0) goto L_0x013d
            r0 = 2
            double[] r5 = new double[r0]
            int r6 = r3.size()
            r0 = 0
            r2 = r0
        L_0x0050:
            if (r2 >= r6) goto L_0x0087
            java.lang.Object r0 = r3.get(r2)
            r1 = r0
            double[] r1 = (double[]) r1
            java.lang.Object r0 = r4.get(r2)
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            r7 = 0
            r8 = r1[r7]
            double r10 = (double) r0
            double r8 = r8 * r10
            r1[r7] = r8
            r7 = 1
            r8 = r1[r7]
            double r10 = (double) r0
            double r8 = r8 * r10
            r1[r7] = r8
            r0 = 0
            r8 = r5[r0]
            r7 = 0
            r10 = r1[r7]
            double r8 = r8 + r10
            r5[r0] = r8
            r0 = 1
            r8 = r5[r0]
            r7 = 1
            r10 = r1[r7]
            double r8 = r8 + r10
            r5[r0] = r8
            int r0 = r2 + 1
            r2 = r0
            goto L_0x0050
        L_0x0087:
            r0 = 0
            r2 = r5[r0]
            double r8 = (double) r6
            double r2 = r2 / r8
            r5[r0] = r2
            r0 = 1
            r2 = r5[r0]
            double r6 = (double) r6
            double r2 = r2 / r6
            r5[r0] = r2
            r0 = 0
            r2 = r5[r0]
            r0 = 1
            r6 = r5[r0]
            r0 = 0
            int r0 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r0 != 0) goto L_0x0125
            r0 = 0
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 <= 0) goto L_0x0116
            r0 = 4636033603912859648(0x4056800000000000, double:90.0)
        L_0x00ac:
            r4 = 2
            double[] r4 = new double[r4]
            r8 = 0
            double r2 = r2 * r2
            double r6 = r6 * r6
            double r2 = r2 + r6
            double r2 = java.lang.Math.sqrt(r2)
            r4[r8] = r2
            r2 = 1
            r4[r2] = r0
            java.util.Locale r0 = java.util.Locale.CHINA
            java.lang.String r1 = "%d,%d,%d,%d"
            r2 = 4
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            r6 = 0
            r6 = r5[r6]
            r8 = 4636737291354636288(0x4059000000000000, double:100.0)
            double r6 = r6 * r8
            long r6 = java.lang.Math.round(r6)
            java.lang.Long r6 = java.lang.Long.valueOf(r6)
            r2[r3] = r6
            r3 = 1
            r6 = 1
            r6 = r5[r6]
            r8 = 4636737291354636288(0x4059000000000000, double:100.0)
            double r6 = r6 * r8
            long r6 = java.lang.Math.round(r6)
            java.lang.Long r5 = java.lang.Long.valueOf(r6)
            r2[r3] = r5
            r3 = 2
            r5 = 0
            r6 = r4[r5]
            r8 = 4636737291354636288(0x4059000000000000, double:100.0)
            double r6 = r6 * r8
            long r6 = java.lang.Math.round(r6)
            java.lang.Long r5 = java.lang.Long.valueOf(r6)
            r2[r3] = r5
            r3 = 3
            r5 = 1
            r6 = r4[r5]
            r8 = 4636737291354636288(0x4059000000000000, double:100.0)
            double r6 = r6 * r8
            long r6 = java.lang.Math.round(r6)
            java.lang.Long r5 = java.lang.Long.valueOf(r6)
            r2[r3] = r5
            java.lang.String.format(r0, r1, r2)
            r0 = 0
            r0 = r4[r0]
            int r2 = U
            double r2 = (double) r2
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 > 0) goto L_0x0131
            r0 = 1
        L_0x0115:
            return r0
        L_0x0116:
            r0 = 0
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 >= 0) goto L_0x0122
            r0 = 4643457506423603200(0x4070e00000000000, double:270.0)
            goto L_0x00ac
        L_0x0122:
            r0 = 0
            goto L_0x00ac
        L_0x0125:
            double r0 = r2 / r6
            double r0 = java.lang.Math.atan(r0)
            double r0 = java.lang.Math.toDegrees(r0)
            goto L_0x00ac
        L_0x0131:
            r0 = 0
            r0 = r4[r0]
            int r2 = V
            double r2 = (double) r2
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 < 0) goto L_0x013d
            r0 = 4
            goto L_0x0115
        L_0x013d:
            r0 = 3
            goto L_0x0115
        L_0x013f:
            r0 = r1
            goto L_0x003f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cb.a(java.util.HashMap):int");
    }

    public static cb a(Context context) {
        if (j == null) {
            synchronized (i) {
                if (j == null) {
                    j = new cb(context);
                }
            }
        }
        return j;
    }

    static /* synthetic */ String a(cb cbVar, String str) {
        return str;
    }

    public static String a(String str) {
        if (str.equals("version")) {
            return "V1.0.0r";
        }
        if (str.equals("date")) {
            return "COL.15.0929r";
        }
        return null;
    }

    static /* synthetic */ void a(cb cbVar, Location location, int i2, long j2) {
        ca caVar;
        ca caVar2;
        System.currentTimeMillis();
        boolean a2 = cbVar.r.a(location);
        if (a2) {
            cbVar.r.b.b = new Location(location);
        }
        boolean b2 = cbVar.r.b(location);
        if (b2) {
            cbVar.r.a.b = new Location(location);
        }
        int i3 = 0;
        if (a2) {
            i3 = 1;
            if (b2) {
                i3 = 3;
            }
        } else if (b2) {
            i3 = 2;
        }
        try {
            cc ccVar = cbVar.w;
            caVar = cc.a(location, cbVar.q, i3, (byte) cbVar.K, j2, false);
        } catch (Exception e2) {
            caVar = null;
        }
        if (!(caVar == null || cbVar.q == null)) {
            List m2 = cbVar.q.m();
            Long l2 = 0L;
            if (m2 != null && m2.size() > 0) {
                l2 = (Long) m2.get(0);
            }
            cbVar.s.a(l2.longValue(), caVar.a());
        }
        if (cbVar.o != null && cbVar.w != null) {
            SharedPreferences sharedPreferences = cbVar.o.getSharedPreferences("app_pref", 0);
            if (!sharedPreferences.getString("get_sensor", "").equals("true")) {
                try {
                    cc ccVar2 = cbVar.w;
                    caVar2 = cc.a((Location) null, cbVar.q, i3, (byte) cbVar.K, j2, true);
                } catch (Exception e3) {
                    caVar2 = null;
                }
                if (caVar2 != null && cbVar.q != null) {
                    List m3 = cbVar.q.m();
                    Long l3 = 0L;
                    if (m3 != null && m3.size() > 0) {
                        l3 = (Long) m3.get(0);
                    }
                    cbVar.s.a(l3.longValue(), caVar2.a());
                    sharedPreferences.edit().putString("get_sensor", "true").commit();
                }
            }
        }
    }

    private double[] a(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        double[] dArr = new double[2];
        Iterator it = list.iterator();
        while (it.hasNext()) {
            GpsSatellite gpsSatellite = (GpsSatellite) it.next();
            if (gpsSatellite != null) {
                double elevation = (double) (90.0f - gpsSatellite.getElevation());
                double azimuth = (double) gpsSatellite.getAzimuth();
                double[] dArr2 = {Math.sin(Math.toRadians(azimuth)) * elevation, elevation * Math.cos(Math.toRadians(azimuth))};
                dArr[0] = dArr[0] + dArr2[0];
                dArr[1] = dArr[1] + dArr2[1];
            }
        }
        int size = list.size();
        dArr[0] = dArr[0] / ((double) size);
        dArr[1] = dArr[1] / ((double) size);
        return dArr;
    }

    static /* synthetic */ int g(cb cbVar) {
        int i2 = cbVar.H;
        cbVar.H = i2 + 1;
        return i2;
    }

    /* access modifiers changed from: private */
    public void o() {
        this.m = this.v.b() * 1000;
        this.n = this.v.c();
        cz czVar = this.r;
        int i2 = this.m;
        int i3 = this.n;
        cz.a();
    }

    public void a() {
        cl.b = true;
        if (this.k && this.q != null && !a) {
            IntentFilter intentFilter = new IntentFilter("android.location.GPS_ENABLED_CHANGE");
            intentFilter.addAction("android.location.GPS_FIX_CHANGE");
            b = true;
            this.o.registerReceiver(this.E, intentFilter);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.setPriority(1000);
            intentFilter2.addAction("android.intent.action.MEDIA_UNMOUNTED");
            intentFilter2.addAction("android.intent.action.MEDIA_MOUNTED");
            intentFilter2.addAction("android.intent.action.MEDIA_EJECT");
            intentFilter2.addDataScheme(UriUtil.LOCAL_FILE_SCHEME);
            this.o.registerReceiver(this.D, intentFilter2);
            this.p.removeUpdates(this.C);
            if (this.y != null) {
                this.y.quit();
                this.y = null;
            }
            if (this.x != null) {
                this.x.interrupt();
                this.x = null;
            }
            this.x = new cu(this, "");
            this.x.start();
            this.q.a();
            a = true;
        }
    }

    public void a(int i2) {
        if (i2 == 256 || i2 == 8736 || i2 == 768) {
            this.t.a(i2);
            return;
        }
        throw new RuntimeException("invalid Size! must be COLLECTOR_SMALL_SIZE or COLLECTOR_BIG_SIZE or COLLECTOR_MEDIUM_SIZE");
    }

    public void a(ch chVar, String str) {
        NetworkInfo activeNetworkInfo;
        if (!cl.c) {
            boolean a2 = this.v.a(str);
            if (chVar != null) {
                byte[] a3 = chVar.a();
                if (a2 && a3 != null && (activeNetworkInfo = ((ConnectivityManager) this.o.getSystemService("connectivity")).getActiveNetworkInfo()) != null && activeNetworkInfo.isConnected()) {
                    if (activeNetworkInfo.getType() == 1) {
                        this.v.a(a3.length + this.v.e());
                    } else {
                        this.v.b(a3.length + this.v.f());
                    }
                }
                chVar.a(a2);
                this.u.a(chVar);
            }
        }
    }

    public void b() {
        cl.b = false;
        this.A = null;
        cl.c = false;
        if (this.k && this.q != null && a) {
            if (this.E != null) {
                try {
                    this.o.unregisterReceiver(this.E);
                    this.o.unregisterReceiver(this.D);
                } catch (Exception e2) {
                }
            }
            if (this.q != null) {
                this.q.v();
            }
            this.p.removeGpsStatusListener(this.z);
            this.p.removeNmeaListener(this.z);
            this.z = null;
            this.p.removeUpdates(this.C);
            if (this.y != null) {
                this.y.quit();
                this.y = null;
            }
            if (this.x != null) {
                this.x.interrupt();
                this.x = null;
            }
            this.q.b();
            a = false;
        }
    }

    public void b(int i2) {
        if (this.q != null) {
            this.q.a(i2);
        }
    }

    public void c() {
        if (this.k) {
            b();
        }
    }

    public boolean d() {
        return a;
    }

    public ch e() {
        if (this.u == null) {
            return null;
        }
        f();
        if (!this.v.a() || cl.c) {
            return null;
        }
        return this.u.a(this.v.d());
    }

    public boolean f() {
        List m2;
        if (this.q == null || (m2 = this.q.m()) == null || m2.size() <= 0) {
            return false;
        }
        return this.t.b(((Long) m2.get(0)).longValue());
    }

    public int g() {
        if (this.u != null) {
            return this.u.a();
        }
        return 0;
    }
}

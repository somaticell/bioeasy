package com.loc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.text.TextUtils;
import cn.com.bioeasy.app.utils.ListUtils;
import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import com.autonavi.aps.amapapi.model.AmapLoc;
import com.facebook.common.util.UriUtil;
import com.loc.ax;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import org.json.JSONObject;

/* compiled from: APS */
public class aw {
    public static final StringBuilder c = new StringBuilder();
    private boolean A = false;
    private boolean B = false;
    private long C = 0;
    /* access modifiers changed from: private */
    public long D = 0;
    private int E = 0;
    private String F = "00:00:00:00:00:00";
    private String G = null;
    private bt H = null;
    private Timer I = null;
    private TimerTask J = null;
    private int K = 0;
    /* access modifiers changed from: private */
    public cb L = null;
    private ch M = null;
    private int[] N = {0, 0, 0};
    private String O = null;
    private String P = null;
    private long Q = 0;
    private long R = 0;
    private String S = null;
    private be T = null;
    private AmapLoc U = null;
    private String V = null;
    private Timer W = null;
    private TimerTask X = null;
    /* access modifiers changed from: private */
    public String Y = null;
    private int Z = 0;
    public boolean a = false;
    private int aa = 0;
    private boolean ab = true;
    private boolean ac = true;
    private long ad = 0;
    bp b = null;
    ax d;
    int e = -1;
    boolean f = false;
    AmapLoc g = null;
    Object h = new Object();
    public boolean i = false;
    int j = 12;
    boolean k = true;
    a l = new a();
    /* access modifiers changed from: private */
    public Context m = null;
    private ConnectivityManager n = null;
    /* access modifiers changed from: private */
    public bg o = null;
    private bf p;
    private ArrayList<ScanResult> q = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<ScanResult> r = new ArrayList<>();
    private HashMap<String, ArrayList<ScanResult>> s = new HashMap<>();
    private b t = new b();
    private WifiInfo u = null;
    /* access modifiers changed from: private */
    public JSONObject v = null;
    private AmapLoc w = null;
    private long x = 0;
    private long y = 0;
    /* access modifiers changed from: private */
    public long z = 0;

    public synchronized void a(Context context) {
        if (context != null) {
            if (TextUtils.isEmpty(e.k)) {
                e.k = bw.b(context);
            }
            if (this.m == null) {
                this.m = context.getApplicationContext();
                this.b = bp.a(context);
                try {
                    this.L = cb.a(this.m);
                } catch (Exception e2) {
                }
                this.z = bw.b();
                i();
                j();
                e.n = true;
                this.H = new bt();
                this.p.d();
                bj.a().a(context);
                bm.a().a(context);
                this.i = true;
            }
        }
    }

    public void a(String str) {
        if (TextUtils.isEmpty(str) || !str.contains("##")) {
            w();
            return;
        }
        String[] split = str.split("##");
        if (split.length != 4) {
            w();
            return;
        }
        e.e = split[0];
        e.f = split[1];
        e.h = split[2];
        e.i = split[3];
    }

    public void a(JSONObject jSONObject) {
        this.v = jSONObject;
        if (bw.a(jSONObject, "collwifiscan")) {
            try {
                String string = jSONObject.getString("collwifiscan");
                if (TextUtils.isEmpty(string)) {
                    e.m = 20;
                } else {
                    e.m = Integer.parseInt(string) / 1000;
                }
                if (r()) {
                    this.L.b(e.m * 1000);
                }
            } catch (Exception e2) {
            }
        }
        if (this.p != null) {
            this.p.a(jSONObject);
        }
        if (this.o != null) {
            this.o.a(jSONObject);
        }
    }

    public synchronized AmapLoc a(boolean z2) throws Exception {
        boolean z3;
        boolean z4;
        AmapLoc amapLoc;
        be beVar;
        boolean z5;
        String str;
        boolean z6 = true;
        boolean z7 = false;
        synchronized (this) {
            if (c.length() > 0) {
                c.delete(0, c.length());
            }
            if (!E()) {
                amapLoc = new AmapLoc();
                amapLoc.b(1);
                amapLoc.b(c.toString());
            } else {
                if (bw.a(this.v, "reversegeo")) {
                    z3 = this.v.getBoolean("reversegeo");
                } else {
                    z3 = true;
                }
                if (bw.a(this.v, "isOffset")) {
                    z4 = this.v.getBoolean("isOffset");
                } else {
                    z4 = true;
                }
                if (!(z4 == this.ac && z3 == this.ab)) {
                    N();
                }
                this.ac = z4;
                this.ab = z3;
                this.E++;
                this.A = bw.a(this.m);
                if (z2) {
                    amapLoc = G();
                } else {
                    if (this.E == 2) {
                        t();
                        D();
                        if (this.m != null) {
                            SharedPreferences sharedPreferences = this.m.getSharedPreferences("pref", 0);
                            b(sharedPreferences);
                            c(sharedPreferences);
                            a(sharedPreferences);
                        }
                        I();
                    }
                    if (this.E == 1 && p()) {
                        if (this.r.isEmpty()) {
                            this.D = bw.b();
                            List<ScanResult> a2 = this.o.a();
                            synchronized (this.h) {
                                if (!(this.r == null || a2 == null)) {
                                    this.r.addAll(a2);
                                }
                            }
                        }
                        x();
                    }
                    if (!a(this.x) || !bw.a(this.w)) {
                        this.p.f();
                        if (!z2) {
                            try {
                                k();
                                this.y = bw.b();
                            } catch (Throwable th) {
                            }
                        }
                        d();
                        e();
                        String b2 = b(false);
                        if (!TextUtils.isEmpty(b2)) {
                            StringBuilder c2 = c(false);
                            if (!this.A) {
                                beVar = this.p.b();
                            } else {
                                beVar = null;
                            }
                            boolean z8 = !(beVar == null && this.T == null) && (this.T == null || !this.T.a(beVar));
                            boolean m2 = m();
                            if (this.w != null) {
                                int size = this.q.size();
                                if (this.w.j() <= 299.0f || size <= 5) {
                                    z6 = false;
                                }
                                z5 = z6;
                            } else {
                                z5 = false;
                            }
                            if (this.w != null && this.S != null && !z5 && !z8 && ((z7 = bj.a().b(this.S, c2)) || (this.R != 0 && bw.b() - this.R < 3000))) {
                                if (this.p.a(this.A)) {
                                    this.p.h();
                                }
                                if (bw.a(this.w)) {
                                    this.w.f("mem");
                                    this.w.a(2);
                                    amapLoc = this.w;
                                }
                            }
                            if (!z7) {
                                this.R = bw.b();
                            } else {
                                this.R = 0;
                            }
                            if (this.P == null || b2.equals(this.P)) {
                                if (this.P == null) {
                                    this.Q = bw.a();
                                    this.P = b2;
                                    str = b2;
                                } else {
                                    this.Q = bw.a();
                                    str = b2;
                                }
                            } else if (bw.a() - this.Q < 3000) {
                                str = this.P;
                            } else {
                                this.Q = bw.a();
                                this.P = b2;
                                str = b2;
                            }
                            AmapLoc amapLoc2 = null;
                            String str2 = str + com.alipay.sdk.sys.a.b + this.ac + com.alipay.sdk.sys.a.b + this.ab;
                            if (!z5 && !m2) {
                                amapLoc2 = bj.a().a(str2, c2);
                            }
                            if ((!m2 && !bw.a(amapLoc2)) || z5) {
                                this.w = a(f(), false, false);
                                if (bw.a(this.w)) {
                                    this.w.f("new");
                                    this.S = c2.toString();
                                    this.T = beVar;
                                    this.x = bw.b();
                                    H();
                                }
                            } else if (m2) {
                                this.w = a(f(), false, false);
                                if (bw.a(this.w)) {
                                    this.S = c2.toString();
                                    this.T = beVar;
                                    this.x = bw.b();
                                    H();
                                }
                            } else {
                                this.R = 0;
                                amapLoc2.a(4);
                                this.w = amapLoc2;
                                H();
                            }
                            bj.a().a(str2, c2, this.w, this.m, true);
                            bm.a().a(this.m, str, this.w);
                            if (!bw.a(this.w)) {
                                AmapLoc a3 = a(str, c2.toString());
                                if (bw.a(a3)) {
                                    this.S = c2.toString();
                                    AmapLoc amapLoc3 = this.w;
                                    this.w = a3;
                                    this.w.a(8);
                                    this.w.b("离线定位，在线定位失败原因:" + amapLoc3.d());
                                }
                            }
                            c2.delete(0, c2.length());
                            amapLoc = this.w;
                        } else {
                            if (!this.f) {
                                g();
                            }
                            for (int i2 = 4; i2 > 0 && this.e != 0; i2--) {
                                SystemClock.sleep(500);
                            }
                            if (this.e == 0) {
                                this.w = this.d.d();
                                if (this.w != null) {
                                    amapLoc = this.w;
                                }
                            }
                            amapLoc = new AmapLoc();
                            amapLoc.b(this.j);
                            amapLoc.b(c.toString());
                        }
                    } else {
                        this.w.a(2);
                        amapLoc = this.w;
                    }
                }
            }
        }
        return amapLoc;
    }

    public void a() {
        if (bu.a() && bw.b() - bu.c() < bu.b() && this.w != null) {
            if (this.w.b() == 2 || this.w.b() == 4) {
                try {
                    b(false);
                    c(true);
                    a(f(), false, true);
                } catch (Throwable th) {
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public int a(boolean z2, int i2) {
        if (!z2) {
            y();
        } else {
            c(i2);
        }
        if (q()) {
            return this.L.g();
        }
        return -1;
    }

    public AmapLoc a(AmapLoc amapLoc, String... strArr) {
        if (strArr == null || strArr.length == 0) {
            return ba.a().a(amapLoc);
        }
        if (strArr[0].equals("shake")) {
            return ba.a().a(amapLoc);
        }
        if (strArr[0].equals("fusion")) {
            return ba.a().b(amapLoc);
        }
        return amapLoc;
    }

    public synchronized void b() {
        this.i = false;
        e.n = false;
        u();
        this.L = null;
        this.M = null;
        this.S = null;
        H();
        if (this.d != null) {
            this.d.a();
            this.d = null;
            this.f = false;
            this.e = -1;
        }
        y();
        try {
            bk.a().a(this.m, 1);
        } catch (Exception e2) {
        }
        ba.a().b();
        bw.i();
        try {
            if (this.m != null) {
                this.m.unregisterReceiver(this.t);
            }
            this.t = null;
        } catch (Exception e3) {
            this.t = null;
        } catch (Throwable th) {
            this.t = null;
            throw th;
        }
        if (this.p != null) {
            this.p.i();
        }
        bj.a().c();
        bm.a().c();
        bd.a();
        J();
        this.x = 0;
        this.Q = 0;
        n();
        this.w = null;
        this.m = null;
        n.a = -1;
        return;
    }

    public String c() {
        return "2.3.0";
    }

    private void i() {
        try {
            this.o = new bg(this.m, (WifiManager) bw.a(this.m, "wifi"), this.v);
            this.n = (ConnectivityManager) bw.a(this.m, "connectivity");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
            intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
            intentFilter.addAction("android.location.GPS_FIX_CHANGE");
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            this.m.registerReceiver(this.t, intentFilter);
            o();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void j() {
        this.p = new bf(this.m, this.v);
        this.p.h();
    }

    private boolean a(long j2) {
        if (bw.b() - j2 >= 800) {
            return false;
        }
        long j3 = 0;
        if (bw.a(this.w)) {
            j3 = bw.a() - this.w.k();
        }
        if (j3 > 10000) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:65:0x01ab  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01f8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.String b(boolean r9) {
        /*
            r8 = this;
            r3 = 1
            r1 = 0
            monitor-enter(r8)
            boolean r0 = r8.A     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x004e
            com.loc.bf r0 = r8.p     // Catch:{ all -> 0x0054 }
            r0.g()     // Catch:{ all -> 0x0054 }
        L_0x000c:
            java.lang.String r0 = ""
            java.lang.String r2 = ""
            java.lang.String r4 = "network"
            boolean r2 = r8.p()     // Catch:{ all -> 0x0054 }
            if (r2 == 0) goto L_0x0057
            com.loc.bg r2 = r8.o     // Catch:{ all -> 0x0054 }
            android.net.wifi.WifiInfo r2 = r2.b()     // Catch:{ all -> 0x0054 }
            r8.u = r2     // Catch:{ all -> 0x0054 }
        L_0x0020:
            java.lang.String r2 = ""
            com.loc.bf r2 = r8.p     // Catch:{ all -> 0x0054 }
            int r2 = r2.c()     // Catch:{ all -> 0x0054 }
            com.loc.bf r5 = r8.p     // Catch:{ all -> 0x0054 }
            java.util.ArrayList r5 = r5.a()     // Catch:{ all -> 0x0054 }
            java.util.ArrayList<android.net.wifi.ScanResult> r6 = r8.q     // Catch:{ all -> 0x0054 }
            if (r5 == 0) goto L_0x0038
            boolean r7 = r5.isEmpty()     // Catch:{ all -> 0x0054 }
            if (r7 == 0) goto L_0x005b
        L_0x0038:
            if (r6 == 0) goto L_0x0040
            boolean r7 = r6.isEmpty()     // Catch:{ all -> 0x0054 }
            if (r7 == 0) goto L_0x005b
        L_0x0040:
            java.lang.StringBuilder r1 = c     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = "⊗ lstCgi & ⊗ wifis"
            r1.append(r2)     // Catch:{ all -> 0x0054 }
            r1 = 12
            r8.j = r1     // Catch:{ all -> 0x0054 }
        L_0x004c:
            monitor-exit(r8)
            return r0
        L_0x004e:
            com.loc.bf r0 = r8.p     // Catch:{ all -> 0x0054 }
            r0.j()     // Catch:{ all -> 0x0054 }
            goto L_0x000c
        L_0x0054:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        L_0x0057:
            r8.n()     // Catch:{ all -> 0x0054 }
            goto L_0x0020
        L_0x005b:
            switch(r2) {
                case 1: goto L_0x00a0;
                case 2: goto L_0x0103;
                case 9: goto L_0x0171;
                default: goto L_0x005e;
            }     // Catch:{ all -> 0x0054 }
        L_0x005e:
            r1 = 11
            r8.j = r1     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r1 = c     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = "get cgi failure"
            r1.append(r2)     // Catch:{ all -> 0x0054 }
        L_0x0069:
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0054 }
            if (r1 != 0) goto L_0x004c
            java.lang.String r1 = "#"
            boolean r1 = r0.startsWith(r1)     // Catch:{ all -> 0x0054 }
            if (r1 != 0) goto L_0x008a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0054 }
            r1.<init>()     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = "#"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ all -> 0x0054 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0054 }
        L_0x008a:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0054 }
            r1.<init>()     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = com.loc.bw.j()     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ all -> 0x0054 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0054 }
            goto L_0x004c
        L_0x00a0:
            boolean r1 = r5.isEmpty()     // Catch:{ all -> 0x0054 }
            if (r1 != 0) goto L_0x0069
            r0 = 0
            java.lang.Object r0 = r5.get(r0)     // Catch:{ all -> 0x0054 }
            com.loc.be r0 = (com.loc.be) r0     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0054 }
            r1.<init>()     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = r0.a     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r2 = r1.append(r2)     // Catch:{ all -> 0x0054 }
            java.lang.String r3 = "#"
            r2.append(r3)     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = r0.b     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r2 = r1.append(r2)     // Catch:{ all -> 0x0054 }
            java.lang.String r3 = "#"
            r2.append(r3)     // Catch:{ all -> 0x0054 }
            int r2 = r0.c     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r2 = r1.append(r2)     // Catch:{ all -> 0x0054 }
            java.lang.String r3 = "#"
            r2.append(r3)     // Catch:{ all -> 0x0054 }
            int r0 = r0.d     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = "#"
            r0.append(r2)     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r0 = r1.append(r4)     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = "#"
            r0.append(r2)     // Catch:{ all -> 0x0054 }
            boolean r0 = r6.isEmpty()     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x00f5
            android.net.wifi.WifiInfo r0 = r8.u     // Catch:{ all -> 0x0054 }
            boolean r0 = r8.a((android.net.wifi.WifiInfo) r0)     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x0100
        L_0x00f5:
            java.lang.String r0 = "cgiwifi"
        L_0x00f7:
            r1.append(r0)     // Catch:{ all -> 0x0054 }
            java.lang.String r0 = r1.toString()     // Catch:{ all -> 0x0054 }
            goto L_0x0069
        L_0x0100:
            java.lang.String r0 = "cgi"
            goto L_0x00f7
        L_0x0103:
            boolean r1 = r5.isEmpty()     // Catch:{ all -> 0x0054 }
            if (r1 != 0) goto L_0x0069
            r0 = 0
            java.lang.Object r0 = r5.get(r0)     // Catch:{ all -> 0x0054 }
            com.loc.be r0 = (com.loc.be) r0     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0054 }
            r1.<init>()     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = r0.a     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r2 = r1.append(r2)     // Catch:{ all -> 0x0054 }
            java.lang.String r3 = "#"
            r2.append(r3)     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = r0.b     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r2 = r1.append(r2)     // Catch:{ all -> 0x0054 }
            java.lang.String r3 = "#"
            r2.append(r3)     // Catch:{ all -> 0x0054 }
            int r2 = r0.g     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r2 = r1.append(r2)     // Catch:{ all -> 0x0054 }
            java.lang.String r3 = "#"
            r2.append(r3)     // Catch:{ all -> 0x0054 }
            int r2 = r0.h     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r2 = r1.append(r2)     // Catch:{ all -> 0x0054 }
            java.lang.String r3 = "#"
            r2.append(r3)     // Catch:{ all -> 0x0054 }
            int r0 = r0.i     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = "#"
            r0.append(r2)     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r0 = r1.append(r4)     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = "#"
            r0.append(r2)     // Catch:{ all -> 0x0054 }
            boolean r0 = r6.isEmpty()     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x0163
            android.net.wifi.WifiInfo r0 = r8.u     // Catch:{ all -> 0x0054 }
            boolean r0 = r8.a((android.net.wifi.WifiInfo) r0)     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x016e
        L_0x0163:
            java.lang.String r0 = "cgiwifi"
        L_0x0165:
            r1.append(r0)     // Catch:{ all -> 0x0054 }
            java.lang.String r0 = r1.toString()     // Catch:{ all -> 0x0054 }
            goto L_0x0069
        L_0x016e:
            java.lang.String r0 = "cgi"
            goto L_0x0165
        L_0x0171:
            boolean r0 = r6.isEmpty()     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x017f
            android.net.wifi.WifiInfo r0 = r8.u     // Catch:{ all -> 0x0054 }
            boolean r0 = r8.a((android.net.wifi.WifiInfo) r0)     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x0210
        L_0x017f:
            r2 = r3
        L_0x0180:
            if (r9 != 0) goto L_0x020e
            android.net.wifi.WifiInfo r0 = r8.u     // Catch:{ all -> 0x0054 }
            boolean r0 = r8.a((android.net.wifi.WifiInfo) r0)     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x01c1
            boolean r0 = r6.isEmpty()     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x01c1
            r0 = 2
            r8.j = r0     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r0 = c     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = "⊗ around wifi(s) & has access wifi"
            r0.append(r2)     // Catch:{ all -> 0x0054 }
        L_0x019b:
            java.util.Locale r0 = java.util.Locale.US     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = "#%s#"
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0054 }
            r5 = 0
            r3[r5] = r4     // Catch:{ all -> 0x0054 }
            java.lang.String r0 = java.lang.String.format(r0, r2, r3)     // Catch:{ all -> 0x0054 }
            if (r1 == 0) goto L_0x01f8
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0054 }
            r1.<init>()     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ all -> 0x0054 }
            java.lang.String r1 = "wifi"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ all -> 0x0054 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0054 }
            goto L_0x0069
        L_0x01c1:
            int r0 = r6.size()     // Catch:{ all -> 0x0054 }
            if (r0 != r3) goto L_0x020e
            r0 = 2
            r8.j = r0     // Catch:{ all -> 0x0054 }
            android.net.wifi.WifiInfo r0 = r8.u     // Catch:{ all -> 0x0054 }
            boolean r0 = r8.a((android.net.wifi.WifiInfo) r0)     // Catch:{ all -> 0x0054 }
            if (r0 != 0) goto L_0x01db
            java.lang.StringBuilder r0 = c     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = "⊗ access wifi & around wifi 1"
            r0.append(r2)     // Catch:{ all -> 0x0054 }
            goto L_0x019b
        L_0x01db:
            r0 = 0
            java.lang.Object r0 = r6.get(r0)     // Catch:{ all -> 0x0054 }
            android.net.wifi.ScanResult r0 = (android.net.wifi.ScanResult) r0     // Catch:{ all -> 0x0054 }
            java.lang.String r0 = r0.BSSID     // Catch:{ all -> 0x0054 }
            android.net.wifi.WifiInfo r3 = r8.u     // Catch:{ all -> 0x0054 }
            java.lang.String r3 = r3.getBSSID()     // Catch:{ all -> 0x0054 }
            boolean r0 = r3.equals(r0)     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x020e
            java.lang.StringBuilder r0 = c     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = "same access wifi & around wifi 1"
            r0.append(r2)     // Catch:{ all -> 0x0054 }
            goto L_0x019b
        L_0x01f8:
            java.lang.String r1 = "network"
            boolean r1 = r4.equals(r1)     // Catch:{ all -> 0x0054 }
            if (r1 == 0) goto L_0x0069
            java.lang.String r0 = ""
            r1 = 2
            r8.j = r1     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r1 = c     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = "is network & no wifi"
            r1.append(r2)     // Catch:{ all -> 0x0054 }
            goto L_0x0069
        L_0x020e:
            r1 = r2
            goto L_0x019b
        L_0x0210:
            r2 = r1
            goto L_0x0180
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aw.b(boolean):java.lang.String");
    }

    private boolean a(WifiInfo wifiInfo) {
        if (wifiInfo == null || TextUtils.isEmpty(wifiInfo.getBSSID()) || wifiInfo.getSSID() == null || wifiInfo.getBSSID().equals("00:00:00:00:00:00") || wifiInfo.getBSSID().contains(" :") || TextUtils.isEmpty(wifiInfo.getSSID())) {
            return false;
        }
        return true;
    }

    public synchronized StringBuilder c(boolean z2) {
        StringBuilder sb;
        String str;
        boolean z3 = false;
        synchronized (this) {
            bf bfVar = this.p;
            if (this.A) {
                bfVar.g();
            }
            sb = new StringBuilder(ActionConstant.tagComment);
            int c2 = bfVar.c();
            ArrayList<be> a2 = bfVar.a();
            switch (c2) {
                case 1:
                    for (int i2 = 1; i2 < a2.size(); i2++) {
                        sb.append("#").append(a2.get(i2).b);
                        sb.append("|").append(a2.get(i2).c);
                        sb.append("|").append(a2.get(i2).d);
                    }
                    break;
            }
            if (((!z2 && TextUtils.isEmpty(this.F)) || this.F.equals("00:00:00:00:00:00")) && this.u != null) {
                this.F = this.u.getMacAddress();
                v();
                if (TextUtils.isEmpty(this.F)) {
                    this.F = "00:00:00:00:00:00";
                }
            }
            if (p()) {
                if (a(this.u)) {
                    str = this.u.getBSSID();
                } else {
                    str = "";
                }
                ArrayList<ScanResult> arrayList = this.q;
                int size = arrayList.size();
                for (int i3 = 0; i3 < size; i3++) {
                    String str2 = arrayList.get(i3).BSSID;
                    String str3 = "nb";
                    if (str.equals(str2)) {
                        str3 = "access";
                        z3 = true;
                    }
                    sb.append(String.format(Locale.US, "#%s,%s", new Object[]{str2, str3}));
                }
                if (!z3 && !TextUtils.isEmpty(str)) {
                    sb.append("#").append(str);
                    sb.append(",access");
                }
            } else {
                n();
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(0);
            }
        }
        return sb;
    }

    /* JADX WARNING: Removed duplicated region for block: B:134:0x06df  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00d2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.StringBuilder a(java.lang.Object r27) {
        /*
            r26 = this;
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r8 = "0"
            java.lang.String r11 = "0"
            java.lang.String r12 = "0"
            java.lang.String r13 = "0"
            java.lang.String r14 = "0"
            java.lang.String r15 = com.loc.e.i
            java.lang.String r2 = "888888888888888"
            com.loc.e.b = r2
            java.lang.String r2 = "888888888888888"
            com.loc.e.c = r2
            java.lang.String r2 = ""
            com.loc.e.d = r2
            r2 = -32768(0xffffffffffff8000, float:NaN)
            r3 = 32767(0x7fff, float:4.5916E-41)
            int r16 = com.loc.bw.a((int) r2, (int) r3)
            java.lang.String r6 = ""
            java.lang.String r5 = ""
            java.lang.String r7 = ""
            java.lang.String r3 = com.loc.e.e
            java.lang.String r2 = com.loc.e.f
            r0 = r26
            boolean r4 = r0.ac
            if (r4 != 0) goto L_0x06e6
            java.lang.String r3 = "UC_nlp_20131029"
            java.lang.String r2 = "BKZCHMBBSSUK7U8GLUKHBB56CCFF78U"
            r4 = r3
            r3 = r2
        L_0x003b:
            java.lang.StringBuilder r17 = new java.lang.StringBuilder
            r17.<init>()
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            r18.<init>()
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            r19.<init>()
            r0 = r26
            com.loc.bf r2 = r0.p
            int r20 = r2.c()
            android.telephony.TelephonyManager r21 = r2.e()
            java.util.ArrayList r22 = r2.a()
            r2 = 2
            r0 = r20
            if (r0 != r2) goto L_0x06e3
            java.lang.String r2 = "1"
            r9 = r2
        L_0x0062:
            if (r21 == 0) goto L_0x00a4
            java.lang.String r2 = com.loc.e.b
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x02b1
            java.lang.String r2 = "888888888888888"
            com.loc.e.b = r2
            r0 = r26
            android.content.Context r2 = r0.m     // Catch:{ Exception -> 0x06dc }
            java.lang.String r2 = com.loc.q.l(r2)     // Catch:{ Exception -> 0x06dc }
            com.loc.e.b = r2     // Catch:{ Exception -> 0x06dc }
        L_0x007a:
            java.lang.String r2 = com.loc.e.b
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0086
            java.lang.String r2 = "888888888888888"
            com.loc.e.b = r2
        L_0x0086:
            java.lang.String r2 = com.loc.e.c
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x02ce
            java.lang.String r2 = "888888888888888"
            com.loc.e.c = r2
            java.lang.String r2 = r21.getSubscriberId()     // Catch:{ Exception -> 0x06d9 }
            com.loc.e.c = r2     // Catch:{ Exception -> 0x06d9 }
        L_0x0098:
            java.lang.String r2 = com.loc.e.c
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x00a4
            java.lang.String r2 = "888888888888888"
            com.loc.e.c = r2
        L_0x00a4:
            r2 = 0
            r0 = r26
            android.net.ConnectivityManager r8 = r0.n     // Catch:{ Exception -> 0x06d6 }
            android.net.NetworkInfo r2 = r8.getActiveNetworkInfo()     // Catch:{ Exception -> 0x06d6 }
        L_0x00ad:
            int r2 = com.loc.bw.a((android.net.NetworkInfo) r2)
            r8 = -1
            if (r2 == r8) goto L_0x02eb
            java.lang.String r5 = com.loc.bw.b((android.telephony.TelephonyManager) r21)
            boolean r2 = r26.p()
            if (r2 == 0) goto L_0x02e7
            r0 = r26
            android.net.wifi.WifiInfo r2 = r0.u
            r0 = r26
            boolean r2 = r0.a((android.net.wifi.WifiInfo) r2)
            if (r2 == 0) goto L_0x02e7
            java.lang.String r2 = "2"
        L_0x00cc:
            boolean r6 = r26.p()
            if (r6 != 0) goto L_0x06df
            r26.n()
            r6 = r5
            r5 = r2
        L_0x00d7:
            r26.B()
            java.lang.String r2 = "<?xml version=\"1.0\" encoding=\""
            r10.append(r2)
            java.lang.String r2 = "GBK"
            java.lang.StringBuilder r2 = r10.append(r2)
            java.lang.String r8 = "\"?>"
            r2.append(r8)
            java.lang.String r2 = "<Cell_Req ver=\"3.0\"><HDR version=\"3.0\" cdma=\""
            r10.append(r2)
            r10.append(r9)
            java.lang.String r2 = "\" gtype=\""
            java.lang.StringBuilder r2 = r10.append(r2)
            r2.append(r11)
            java.lang.String r2 = "1"
            boolean r2 = r11.equals(r2)
            if (r2 == 0) goto L_0x0114
            java.lang.String r2 = "\" gmock=\""
            java.lang.StringBuilder r8 = r10.append(r2)
            r0 = r26
            boolean r2 = r0.B
            if (r2 == 0) goto L_0x02f2
            java.lang.String r2 = "1"
        L_0x0111:
            r8.append(r2)
        L_0x0114:
            java.lang.String r2 = "\" glong=\""
            java.lang.StringBuilder r2 = r10.append(r2)
            r2.append(r12)
            java.lang.String r2 = "\" glat=\""
            java.lang.StringBuilder r2 = r10.append(r2)
            r2.append(r13)
            java.lang.String r2 = "\" precision=\""
            java.lang.StringBuilder r2 = r10.append(r2)
            r2.append(r14)
            java.lang.String r2 = "\"><src>"
            java.lang.StringBuilder r2 = r10.append(r2)
            r2.append(r4)
            java.lang.String r2 = "</src><license>"
            java.lang.StringBuilder r2 = r10.append(r2)
            r2.append(r3)
            java.lang.String r2 = "</license><key>"
            java.lang.StringBuilder r2 = r10.append(r2)
            r2.append(r15)
            java.lang.String r2 = "</key><clientid>"
            java.lang.StringBuilder r2 = r10.append(r2)
            java.lang.String r8 = com.loc.e.h
            r2.append(r8)
            java.lang.String r2 = "</clientid><imei>"
            java.lang.StringBuilder r2 = r10.append(r2)
            java.lang.String r8 = com.loc.e.b
            r2.append(r8)
            java.lang.String r2 = "</imei><imsi>"
            java.lang.StringBuilder r2 = r10.append(r2)
            java.lang.String r8 = com.loc.e.c
            r2.append(r8)
            java.lang.String r2 = "</imsi><reqid>"
            java.lang.StringBuilder r2 = r10.append(r2)
            r0 = r16
            r2.append(r0)
            java.lang.String r2 = "</reqid><smac>"
            java.lang.StringBuilder r2 = r10.append(r2)
            r0 = r26
            java.lang.String r8 = r0.F
            r2.append(r8)
            java.lang.String r2 = "</smac><sdkv>"
            java.lang.StringBuilder r2 = r10.append(r2)
            java.lang.String r8 = r26.c()
            r2.append(r8)
            java.lang.String r2 = "</sdkv><corv>"
            java.lang.StringBuilder r2 = r10.append(r2)
            java.lang.String r8 = r26.C()
            r2.append(r8)
            java.lang.String r2 = "</corv><poiid>"
            java.lang.StringBuilder r2 = r10.append(r2)
            r0 = r26
            java.lang.String r8 = r0.G
            r2.append(r8)
            java.lang.String r2 = "</poiid></HDR><DRR phnum=\""
            java.lang.StringBuilder r2 = r10.append(r2)
            java.lang.String r8 = com.loc.e.d
            r2.append(r8)
            java.lang.String r2 = "\" nettype=\""
            java.lang.StringBuilder r2 = r10.append(r2)
            r2.append(r6)
            java.lang.String r2 = "\" inftype=\""
            java.lang.StringBuilder r2 = r10.append(r2)
            java.lang.StringBuilder r2 = r2.append(r5)
            java.lang.String r8 = "\">"
            r2.append(r8)
            boolean r2 = r22.isEmpty()
            if (r2 != 0) goto L_0x01ea
            java.lang.StringBuilder r21 = new java.lang.StringBuilder
            r21.<init>()
            switch(r20) {
                case 1: goto L_0x02f6;
                case 2: goto L_0x03c0;
                default: goto L_0x01db;
            }
        L_0x01db:
            r26.K()
            r2 = r7
        L_0x01df:
            r7 = 0
            int r8 = r21.length()
            r0 = r21
            r0.delete(r7, r8)
            r7 = r2
        L_0x01ea:
            boolean r2 = r26.p()
            if (r2 == 0) goto L_0x047d
            r0 = r26
            android.net.wifi.WifiInfo r2 = r0.u
            r0 = r26
            boolean r2 = r0.a((android.net.wifi.WifiInfo) r2)
            if (r2 == 0) goto L_0x025c
            r0 = r26
            android.net.wifi.WifiInfo r2 = r0.u
            java.lang.String r2 = r2.getBSSID()
            r0 = r19
            java.lang.StringBuilder r2 = r0.append(r2)
            java.lang.String r8 = ","
            r2.append(r8)
            r0 = r26
            android.net.wifi.WifiInfo r2 = r0.u
            int r2 = r2.getRssi()
            r8 = -128(0xffffffffffffff80, float:NaN)
            if (r2 >= r8) goto L_0x0476
            r2 = 0
        L_0x021c:
            r0 = r19
            java.lang.StringBuilder r2 = r0.append(r2)
            java.lang.String r8 = ","
            r2.append(r8)
            r0 = r26
            android.net.wifi.WifiInfo r2 = r0.u
            java.lang.String r2 = r2.getSSID()
            r8 = 32
            r0 = r26
            android.net.wifi.WifiInfo r0 = r0.u     // Catch:{ Exception -> 0x06d3 }
            r21 = r0
            java.lang.String r21 = r21.getSSID()     // Catch:{ Exception -> 0x06d3 }
            java.lang.String r22 = "UTF-8"
            byte[] r21 = r21.getBytes(r22)     // Catch:{ Exception -> 0x06d3 }
            r0 = r21
            int r8 = r0.length     // Catch:{ Exception -> 0x06d3 }
        L_0x0244:
            r21 = 32
            r0 = r21
            if (r8 < r0) goto L_0x024d
            java.lang.String r2 = "unkwn"
        L_0x024d:
            java.lang.String r8 = "*"
            java.lang.String r21 = "."
            r0 = r21
            java.lang.String r2 = r2.replace(r8, r0)
            r0 = r19
            r0.append(r2)
        L_0x025c:
            r0 = r26
            java.util.ArrayList<android.net.wifi.ScanResult> r0 = r0.q
            r21 = r0
            r2 = 0
            int r8 = r21.size()
            r22 = 15
            r0 = r22
            int r22 = java.lang.Math.min(r8, r0)
            r8 = r2
        L_0x0270:
            r0 = r22
            if (r8 >= r0) goto L_0x0480
            r0 = r21
            java.lang.Object r2 = r0.get(r8)
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2
            java.lang.String r0 = r2.BSSID
            r23 = r0
            r0 = r18
            r1 = r23
            java.lang.StringBuilder r23 = r0.append(r1)
            java.lang.String r24 = ","
            r23.append(r24)
            int r0 = r2.level
            r23 = r0
            r0 = r18
            r1 = r23
            java.lang.StringBuilder r23 = r0.append(r1)
            java.lang.String r24 = ","
            r23.append(r24)
            java.lang.String r2 = r2.SSID
            r0 = r18
            java.lang.StringBuilder r2 = r0.append(r2)
            java.lang.String r23 = "*"
            r0 = r23
            r2.append(r0)
            int r2 = r8 + 1
            r8 = r2
            goto L_0x0270
        L_0x02b1:
            java.lang.String r2 = "888888888888888"
            java.lang.String r8 = com.loc.e.b
            boolean r2 = r2.equals(r8)
            if (r2 == 0) goto L_0x007a
            java.lang.String r2 = "888888888888888"
            com.loc.e.b = r2
            r0 = r26
            android.content.Context r2 = r0.m     // Catch:{ Exception -> 0x02cb }
            java.lang.String r2 = com.loc.q.l(r2)     // Catch:{ Exception -> 0x02cb }
            com.loc.e.b = r2     // Catch:{ Exception -> 0x02cb }
            goto L_0x007a
        L_0x02cb:
            r2 = move-exception
            goto L_0x007a
        L_0x02ce:
            java.lang.String r2 = "888888888888888"
            java.lang.String r8 = com.loc.e.c
            boolean r2 = r2.equals(r8)
            if (r2 == 0) goto L_0x0098
            java.lang.String r2 = "888888888888888"
            com.loc.e.c = r2
            java.lang.String r2 = r21.getSubscriberId()     // Catch:{ Exception -> 0x02e4 }
            com.loc.e.c = r2     // Catch:{ Exception -> 0x02e4 }
            goto L_0x0098
        L_0x02e4:
            r2 = move-exception
            goto L_0x0098
        L_0x02e7:
            java.lang.String r2 = "1"
            goto L_0x00cc
        L_0x02eb:
            r2 = 0
            r0 = r26
            r0.u = r2
            goto L_0x00d7
        L_0x02f2:
            java.lang.String r2 = "0"
            goto L_0x0111
        L_0x02f6:
            r26.K()
            r2 = 0
            r0 = r22
            java.lang.Object r2 = r0.get(r2)
            com.loc.be r2 = (com.loc.be) r2
            r7 = 0
            int r8 = r21.length()
            r0 = r21
            r0.delete(r7, r8)
            java.lang.String r7 = "<mcc>"
            r0 = r21
            java.lang.StringBuilder r7 = r0.append(r7)
            java.lang.String r8 = r2.a
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = "</mcc>"
            r7.append(r8)
            java.lang.String r7 = "<mnc>"
            r0 = r21
            java.lang.StringBuilder r7 = r0.append(r7)
            java.lang.String r8 = r2.b
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = "</mnc>"
            r7.append(r8)
            java.lang.String r7 = "<lac>"
            r0 = r21
            java.lang.StringBuilder r7 = r0.append(r7)
            int r8 = r2.c
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = "</lac>"
            r7.append(r8)
            java.lang.String r7 = "<cellid>"
            r0 = r21
            java.lang.StringBuilder r7 = r0.append(r7)
            int r8 = r2.d
            r7.append(r8)
            java.lang.String r7 = "</cellid>"
            r0 = r21
            r0.append(r7)
            java.lang.String r7 = "<signal>"
            r0 = r21
            java.lang.StringBuilder r7 = r0.append(r7)
            int r2 = r2.j
            r7.append(r2)
            java.lang.String r2 = "</signal>"
            r0 = r21
            r0.append(r2)
            java.lang.String r8 = r21.toString()
            r2 = 1
            r7 = r2
        L_0x0373:
            int r2 = r22.size()
            if (r7 >= r2) goto L_0x03bd
            r0 = r22
            java.lang.Object r2 = r0.get(r7)
            com.loc.be r2 = (com.loc.be) r2
            int r0 = r2.c
            r23 = r0
            r0 = r17
            r1 = r23
            java.lang.StringBuilder r23 = r0.append(r1)
            java.lang.String r24 = ","
            r23.append(r24)
            int r0 = r2.d
            r23 = r0
            r0 = r17
            r1 = r23
            java.lang.StringBuilder r23 = r0.append(r1)
            java.lang.String r24 = ","
            r23.append(r24)
            int r2 = r2.j
            r0 = r17
            r0.append(r2)
            int r2 = r22.size()
            int r2 = r2 + -1
            if (r7 >= r2) goto L_0x03b9
            java.lang.String r2 = "*"
            r0 = r17
            r0.append(r2)
        L_0x03b9:
            int r2 = r7 + 1
            r7 = r2
            goto L_0x0373
        L_0x03bd:
            r2 = r8
            goto L_0x01df
        L_0x03c0:
            r2 = 0
            r0 = r22
            java.lang.Object r2 = r0.get(r2)
            com.loc.be r2 = (com.loc.be) r2
            r7 = 0
            int r8 = r21.length()
            r0 = r21
            r0.delete(r7, r8)
            java.lang.String r7 = "<mcc>"
            r0 = r21
            java.lang.StringBuilder r7 = r0.append(r7)
            java.lang.String r8 = r2.a
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = "</mcc>"
            r7.append(r8)
            java.lang.String r7 = "<sid>"
            r0 = r21
            java.lang.StringBuilder r7 = r0.append(r7)
            int r8 = r2.g
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = "</sid>"
            r7.append(r8)
            java.lang.String r7 = "<nid>"
            r0 = r21
            java.lang.StringBuilder r7 = r0.append(r7)
            int r8 = r2.h
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = "</nid>"
            r7.append(r8)
            java.lang.String r7 = "<bid>"
            r0 = r21
            java.lang.StringBuilder r7 = r0.append(r7)
            int r8 = r2.i
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = "</bid>"
            r7.append(r8)
            int r7 = r2.f
            if (r7 <= 0) goto L_0x0472
            int r7 = r2.e
            if (r7 <= 0) goto L_0x0472
            int r7 = r2.f
            r0 = r26
            r0.Z = r7
            int r7 = r2.e
            r0 = r26
            r0.aa = r7
            java.lang.String r7 = "<lon>"
            r0 = r21
            java.lang.StringBuilder r7 = r0.append(r7)
            int r8 = r2.f
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = "</lon>"
            r7.append(r8)
            java.lang.String r7 = "<lat>"
            r0 = r21
            java.lang.StringBuilder r7 = r0.append(r7)
            int r8 = r2.e
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = "</lat>"
            r7.append(r8)
        L_0x0459:
            java.lang.String r7 = "<signal>"
            r0 = r21
            java.lang.StringBuilder r7 = r0.append(r7)
            int r2 = r2.j
            java.lang.StringBuilder r2 = r7.append(r2)
            java.lang.String r7 = "</signal>"
            r2.append(r7)
            java.lang.String r2 = r21.toString()
            goto L_0x01df
        L_0x0472:
            r26.K()
            goto L_0x0459
        L_0x0476:
            r8 = 127(0x7f, float:1.78E-43)
            if (r2 <= r8) goto L_0x021c
            r2 = 0
            goto L_0x021c
        L_0x047d:
            r26.n()
        L_0x0480:
            r10.append(r7)
            java.util.Locale r2 = java.util.Locale.US
            java.lang.String r8 = "<nb>%s</nb>"
            r21 = 1
            r0 = r21
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r21 = r0
            r22 = 0
            r21[r22] = r17
            r0 = r21
            java.lang.String r2 = java.lang.String.format(r2, r8, r0)
            r10.append(r2)
            int r2 = r18.length()
            if (r2 != 0) goto L_0x0674
            r18.append(r19)
            java.lang.String r2 = "<macs>"
            r10.append(r2)
            java.util.Locale r2 = java.util.Locale.US
            java.lang.String r8 = "<![CDATA[%s]]>"
            r21 = 1
            r0 = r21
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r21 = r0
            r22 = 0
            r21[r22] = r19
            r0 = r21
            java.lang.String r2 = java.lang.String.format(r2, r8, r0)
            r10.append(r2)
            java.lang.String r2 = "</macs>"
            r10.append(r2)
        L_0x04c8:
            java.lang.String r2 = "<mmac>"
            r10.append(r2)
            java.util.Locale r2 = java.util.Locale.US
            java.lang.String r8 = "<![CDATA[%s]]>"
            r21 = 1
            r0 = r21
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r21 = r0
            r22 = 0
            r21[r22] = r19
            r0 = r21
            java.lang.String r2 = java.lang.String.format(r2, r8, r0)
            r10.append(r2)
            java.lang.String r2 = "</mmac>"
            java.lang.StringBuilder r2 = r10.append(r2)
            java.lang.String r8 = "</DRR></Cell_Req>"
            r2.append(r8)
            r0 = r26
            r0.a((java.lang.StringBuilder) r10)
            r2 = 1
            r0 = r26
            org.json.JSONObject r8 = r0.v
            java.lang.String r21 = "reversegeo"
            r0 = r21
            boolean r8 = com.loc.bw.a((org.json.JSONObject) r8, (java.lang.String) r0)
            if (r8 == 0) goto L_0x0511
            r0 = r26
            org.json.JSONObject r8 = r0.v     // Catch:{ Exception -> 0x06d0 }
            java.lang.String r21 = "reversegeo"
            r0 = r21
            boolean r2 = r8.getBoolean(r0)     // Catch:{ Exception -> 0x06d0 }
        L_0x0511:
            if (r2 != 0) goto L_0x06c0
            r0 = r26
            com.loc.bt r2 = r0.H
            r8 = 2
            r2.b = r8
        L_0x051a:
            r0 = r26
            org.json.JSONObject r2 = r0.v
            java.lang.String r8 = "multi"
            boolean r2 = com.loc.bw.a((org.json.JSONObject) r2, (java.lang.String) r8)
            if (r2 == 0) goto L_0x053f
            r0 = r26
            org.json.JSONObject r2 = r0.v     // Catch:{ Exception -> 0x06cd }
            java.lang.String r8 = "multi"
            java.lang.String r2 = r2.getString(r8)     // Catch:{ Exception -> 0x06cd }
            java.lang.String r8 = "1"
            boolean r2 = r2.equals(r8)     // Catch:{ Exception -> 0x06cd }
            if (r2 == 0) goto L_0x053f
            r0 = r26
            com.loc.bt r2 = r0.H     // Catch:{ Exception -> 0x06cd }
            r8 = 1
            r2.b = r8     // Catch:{ Exception -> 0x06cd }
        L_0x053f:
            r0 = r26
            com.loc.bt r2 = r0.H
            r2.c = r4
            r0 = r26
            com.loc.bt r2 = r0.H
            r2.d = r3
            r0 = r26
            com.loc.bt r2 = r0.H
            java.lang.String r3 = com.loc.bw.f()
            r2.f = r3
            r0 = r26
            com.loc.bt r2 = r0.H
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "android"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = com.loc.bw.g()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.g = r3
            java.lang.String r2 = com.loc.e.k
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0584
            r0 = r26
            android.content.Context r2 = r0.m
            java.lang.String r2 = com.loc.bw.b((android.content.Context) r2)
            com.loc.e.k = r2
        L_0x0584:
            r0 = r26
            com.loc.bt r2 = r0.H
            java.lang.String r3 = com.loc.e.k
            r2.h = r3
            r0 = r26
            com.loc.bt r2 = r0.H
            r2.i = r9
            r0 = r26
            com.loc.bt r2 = r0.H
            r2.j = r11
            r0 = r26
            com.loc.bt r3 = r0.H
            r0 = r26
            boolean r2 = r0.B
            if (r2 == 0) goto L_0x06c9
            java.lang.String r2 = "1"
        L_0x05a4:
            r3.k = r2
            r0 = r26
            com.loc.bt r2 = r0.H
            r2.l = r12
            r0 = r26
            com.loc.bt r2 = r0.H
            r2.m = r13
            r0 = r26
            com.loc.bt r2 = r0.H
            r2.n = r14
            r0 = r26
            com.loc.bt r2 = r0.H
            r2.o = r15
            r0 = r26
            com.loc.bt r2 = r0.H
            java.lang.String r3 = com.loc.e.b
            r2.p = r3
            r0 = r26
            com.loc.bt r2 = r0.H
            java.lang.String r3 = com.loc.e.c
            r2.q = r3
            r0 = r26
            com.loc.bt r2 = r0.H
            java.lang.String r3 = java.lang.String.valueOf(r16)
            r2.s = r3
            r0 = r26
            com.loc.bt r2 = r0.H
            r0 = r26
            java.lang.String r3 = r0.F
            r2.t = r3
            r0 = r26
            com.loc.bt r2 = r0.H
            java.lang.String r3 = r26.c()
            r2.v = r3
            r0 = r26
            com.loc.bt r2 = r0.H
            java.lang.String r3 = r26.C()
            r2.w = r3
            r0 = r26
            com.loc.bt r2 = r0.H
            r0 = r26
            java.lang.String r3 = r0.G
            r2.F = r3
            r0 = r26
            com.loc.bt r2 = r0.H
            java.lang.String r3 = com.loc.e.d
            r2.u = r3
            r0 = r26
            com.loc.bt r2 = r0.H
            r2.x = r6
            r0 = r26
            com.loc.bt r2 = r0.H
            r2.y = r5
            r0 = r26
            com.loc.bt r2 = r0.H
            java.lang.String r3 = java.lang.String.valueOf(r20)
            r2.z = r3
            r0 = r26
            com.loc.bt r2 = r0.H
            r2.A = r7
            r0 = r26
            com.loc.bt r2 = r0.H
            java.lang.String r3 = r17.toString()
            r2.B = r3
            r0 = r26
            com.loc.bt r2 = r0.H
            java.lang.String r3 = r18.toString()
            r2.D = r3
            r0 = r26
            com.loc.bt r2 = r0.H
            long r4 = com.loc.bw.b()
            r0 = r26
            long r6 = r0.D
            long r4 = r4 - r6
            java.lang.String r3 = java.lang.String.valueOf(r4)
            r2.E = r3
            r0 = r26
            com.loc.bt r2 = r0.H
            java.lang.String r3 = r19.toString()
            r2.C = r3
            r2 = 0
            int r3 = r17.length()
            r0 = r17
            r0.delete(r2, r3)
            r2 = 0
            int r3 = r18.length()
            r0 = r18
            r0.delete(r2, r3)
            r2 = 0
            int r3 = r19.length()
            r0 = r19
            r0.delete(r2, r3)
            return r10
        L_0x0674:
            int r2 = r18.length()
            int r2 = r2 + -1
            r0 = r18
            r0.deleteCharAt(r2)
            java.lang.String r2 = "<macs>"
            r10.append(r2)
            java.util.Locale r2 = java.util.Locale.US
            java.lang.String r8 = "<![CDATA[%s]]>"
            r21 = 1
            r0 = r21
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r21 = r0
            r22 = 0
            r21[r22] = r18
            r0 = r21
            java.lang.String r2 = java.lang.String.format(r2, r8, r0)
            r10.append(r2)
            java.lang.String r2 = "</macs>"
            r10.append(r2)
            java.lang.String r2 = "<macsage>"
            java.lang.StringBuilder r2 = r10.append(r2)
            long r22 = com.loc.bw.b()
            r0 = r26
            long r0 = r0.D
            r24 = r0
            long r22 = r22 - r24
            r0 = r22
            r2.append(r0)
            java.lang.String r2 = "</macsage>"
            r10.append(r2)
            goto L_0x04c8
        L_0x06c0:
            r0 = r26
            com.loc.bt r2 = r0.H
            r8 = 0
            r2.b = r8
            goto L_0x051a
        L_0x06c9:
            java.lang.String r2 = "0"
            goto L_0x05a4
        L_0x06cd:
            r2 = move-exception
            goto L_0x053f
        L_0x06d0:
            r8 = move-exception
            goto L_0x0511
        L_0x06d3:
            r21 = move-exception
            goto L_0x0244
        L_0x06d6:
            r8 = move-exception
            goto L_0x00ad
        L_0x06d9:
            r2 = move-exception
            goto L_0x0098
        L_0x06dc:
            r2 = move-exception
            goto L_0x007a
        L_0x06df:
            r6 = r5
            r5 = r2
            goto L_0x00d7
        L_0x06e3:
            r9 = r8
            goto L_0x0062
        L_0x06e6:
            r4 = r3
            r3 = r2
            goto L_0x003b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aw.a(java.lang.Object):java.lang.StringBuilder");
    }

    private void k() {
        List<ScanResult> a2;
        long b2 = bw.b();
        if (l()) {
            ArrayList<ScanResult> arrayList = this.r;
            if (b2 - this.y >= 10000) {
                synchronized (this.h) {
                    arrayList.clear();
                }
            }
            o();
            if (b2 - this.y >= 10000) {
                for (int i2 = 20; i2 > 0 && arrayList.isEmpty(); i2--) {
                    try {
                        Thread.sleep(3000 / ((long) 20));
                    } catch (Exception e2) {
                    }
                }
            }
            synchronized (this.h) {
            }
            if (arrayList.isEmpty() && this.o != null && (a2 = this.o.a()) != null) {
                arrayList.addAll(a2);
            }
        }
    }

    public synchronized void d() {
        ArrayList<ScanResult> arrayList = this.q;
        ArrayList<ScanResult> arrayList2 = this.r;
        arrayList.clear();
        synchronized (this.h) {
            arrayList.addAll(arrayList2);
        }
    }

    public synchronized void e() {
        boolean z2;
        int i2;
        if (this.q != null && !this.q.isEmpty()) {
            if (bw.b() - this.D > 3600000) {
                n();
            }
            boolean h2 = bw.h();
            if (bw.a(this.v, "nbssid")) {
                try {
                    if (this.v.getString("nbssid").equals(com.alipay.sdk.cons.a.e)) {
                        h2 = true;
                    } else if (this.v.getString("nbssid").equals("0")) {
                        h2 = false;
                    }
                    z2 = h2;
                } catch (Exception e2) {
                    z2 = h2;
                }
            } else {
                z2 = h2;
            }
            Hashtable hashtable = new Hashtable();
            ArrayList<ScanResult> arrayList = this.q;
            int size = arrayList.size();
            for (int i3 = 0; i3 < size; i3++) {
                ScanResult scanResult = arrayList.get(i3);
                if (bw.a(scanResult)) {
                    if (size > 20) {
                        if (!a(scanResult.level)) {
                            continue;
                        }
                    }
                    if (TextUtils.isEmpty(scanResult.SSID)) {
                        scanResult.SSID = "unkwn";
                    } else if (z2) {
                        scanResult.SSID = scanResult.SSID.replace("*", ".");
                        try {
                            i2 = scanResult.SSID.getBytes("UTF-8").length;
                        } catch (Exception e3) {
                            i2 = 32;
                        }
                        if (i2 >= 32) {
                            scanResult.SSID = String.valueOf(i3);
                        }
                    } else {
                        scanResult.SSID = String.valueOf(i3);
                    }
                    hashtable.put(Integer.valueOf((scanResult.level * 30) + i3), scanResult);
                }
            }
            TreeMap treeMap = new TreeMap(Collections.reverseOrder());
            treeMap.putAll(hashtable);
            arrayList.clear();
            for (Object obj : treeMap.keySet()) {
                arrayList.add(treeMap.get(obj));
            }
            hashtable.clear();
            treeMap.clear();
        }
    }

    private boolean a(int i2) {
        int i3 = 20;
        try {
            i3 = WifiManager.calculateSignalLevel(i2, 20);
        } catch (ArithmeticException e2) {
        }
        if (i3 >= 1) {
            return true;
        }
        return false;
    }

    public synchronized String f() {
        String str = null;
        synchronized (this) {
            if (this.p.a(this.A)) {
                this.p.h();
            }
            try {
                StringBuilder a2 = a((Object) null);
                if (a2 != null) {
                    str = a2.toString();
                } else {
                    c.append("get parames is null");
                }
            } catch (Throwable th) {
                th.printStackTrace();
                c.append("get parames error:" + th.getMessage());
            }
        }
        return str;
    }

    private boolean l() {
        boolean z2 = false;
        if (!TextUtils.isEmpty(this.G)) {
            return true;
        }
        if (p()) {
            if (this.C == 0) {
                z2 = true;
            } else if (bw.b() - this.C >= 3000 && bw.b() - this.D >= 1500) {
                z2 = true;
            }
        }
        return z2;
    }

    /* compiled from: APS */
    private class b extends BroadcastReceiver {
        private b() {
        }

        /* JADX WARNING: Unknown top exception splitter block from list: {B:34:0x0065=Splitter:B:34:0x0065, B:18:0x003e=Splitter:B:18:0x003e} */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r5, android.content.Intent r6) {
            /*
                r4 = this;
                if (r5 == 0) goto L_0x0004
                if (r6 != 0) goto L_0x0005
            L_0x0004:
                return
            L_0x0005:
                java.lang.String r0 = r6.getAction()     // Catch:{ Throwable -> 0x0048 }
                boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x0048 }
                if (r1 != 0) goto L_0x0004
                com.loc.aw r1 = com.loc.aw.this     // Catch:{ Throwable -> 0x0048 }
                com.loc.bg r1 = r1.o     // Catch:{ Throwable -> 0x0048 }
                java.lang.String r2 = "android.net.wifi.SCAN_RESULTS"
                boolean r2 = r0.equals(r2)     // Catch:{ Throwable -> 0x0048 }
                if (r2 == 0) goto L_0x0050
                if (r1 == 0) goto L_0x0004
                r0 = 0
                java.util.List r0 = r1.a()     // Catch:{ Exception -> 0x00e3 }
            L_0x0024:
                if (r0 == 0) goto L_0x003e
                com.loc.aw r1 = com.loc.aw.this     // Catch:{ Throwable -> 0x0048 }
                java.lang.Object r1 = r1.h     // Catch:{ Throwable -> 0x0048 }
                monitor-enter(r1)     // Catch:{ Throwable -> 0x0048 }
                com.loc.aw r2 = com.loc.aw.this     // Catch:{ all -> 0x004d }
                java.util.ArrayList r2 = r2.r     // Catch:{ all -> 0x004d }
                r2.clear()     // Catch:{ all -> 0x004d }
                com.loc.aw r2 = com.loc.aw.this     // Catch:{ all -> 0x004d }
                java.util.ArrayList r2 = r2.r     // Catch:{ all -> 0x004d }
                r2.addAll(r0)     // Catch:{ all -> 0x004d }
                monitor-exit(r1)     // Catch:{ all -> 0x004d }
            L_0x003e:
                com.loc.aw r0 = com.loc.aw.this     // Catch:{ Throwable -> 0x0048 }
                long r2 = com.loc.bw.b()     // Catch:{ Throwable -> 0x0048 }
                long unused = r0.D = r2     // Catch:{ Throwable -> 0x0048 }
                goto L_0x0004
            L_0x0048:
                r0 = move-exception
                r0.printStackTrace()
                goto L_0x0004
            L_0x004d:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x004d }
                throw r0     // Catch:{ Throwable -> 0x0048 }
            L_0x0050:
                java.lang.String r2 = "android.net.wifi.WIFI_STATE_CHANGED"
                boolean r2 = r0.equals(r2)     // Catch:{ Throwable -> 0x0048 }
                if (r2 == 0) goto L_0x008f
                com.loc.aw r0 = com.loc.aw.this     // Catch:{ Throwable -> 0x0048 }
                com.loc.bg r0 = r0.o     // Catch:{ Throwable -> 0x0048 }
                if (r0 == 0) goto L_0x0004
                r0 = 4
                int r0 = r1.c()     // Catch:{ Exception -> 0x00e6 }
            L_0x0065:
                com.loc.aw r1 = com.loc.aw.this     // Catch:{ Throwable -> 0x0048 }
                java.util.ArrayList r1 = r1.r     // Catch:{ Throwable -> 0x0048 }
                if (r1 != 0) goto L_0x0077
                com.loc.aw r1 = com.loc.aw.this     // Catch:{ Throwable -> 0x0048 }
                java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0048 }
                r2.<init>()     // Catch:{ Throwable -> 0x0048 }
                java.util.ArrayList unused = r1.r = r2     // Catch:{ Throwable -> 0x0048 }
            L_0x0077:
                switch(r0) {
                    case 0: goto L_0x007b;
                    case 1: goto L_0x0081;
                    case 2: goto L_0x007a;
                    case 3: goto L_0x007a;
                    case 4: goto L_0x0088;
                    default: goto L_0x007a;
                }     // Catch:{ Throwable -> 0x0048 }
            L_0x007a:
                goto L_0x0004
            L_0x007b:
                com.loc.aw r0 = com.loc.aw.this     // Catch:{ Throwable -> 0x0048 }
                r0.n()     // Catch:{ Throwable -> 0x0048 }
                goto L_0x0004
            L_0x0081:
                com.loc.aw r0 = com.loc.aw.this     // Catch:{ Throwable -> 0x0048 }
                r0.n()     // Catch:{ Throwable -> 0x0048 }
                goto L_0x0004
            L_0x0088:
                com.loc.aw r0 = com.loc.aw.this     // Catch:{ Throwable -> 0x0048 }
                r0.n()     // Catch:{ Throwable -> 0x0048 }
                goto L_0x0004
            L_0x008f:
                java.lang.String r1 = "android.intent.action.SCREEN_ON"
                boolean r1 = r0.equals(r1)     // Catch:{ Throwable -> 0x0048 }
                if (r1 == 0) goto L_0x009e
                com.loc.aw r0 = com.loc.aw.this     // Catch:{ Throwable -> 0x0048 }
                r1 = 1
                r0.k = r1     // Catch:{ Throwable -> 0x0048 }
                goto L_0x0004
            L_0x009e:
                java.lang.String r1 = "android.intent.action.SCREEN_OFF"
                boolean r1 = r0.equals(r1)     // Catch:{ Throwable -> 0x0048 }
                if (r1 == 0) goto L_0x00ba
                com.loc.aw r0 = com.loc.aw.this     // Catch:{ Throwable -> 0x0048 }
                r1 = 0
                r0.k = r1     // Catch:{ Throwable -> 0x0048 }
                com.loc.aw r0 = com.loc.aw.this     // Catch:{ Throwable -> 0x0048 }
                com.loc.cb r0 = r0.L     // Catch:{ Throwable -> 0x0048 }
                if (r0 == 0) goto L_0x0004
                com.loc.aw r0 = com.loc.aw.this     // Catch:{ Throwable -> 0x0048 }
                r0.u()     // Catch:{ Throwable -> 0x0048 }
                goto L_0x0004
            L_0x00ba:
                java.lang.String r1 = "android.intent.action.AIRPLANE_MODE"
                boolean r1 = r0.equals(r1)     // Catch:{ Throwable -> 0x0048 }
                if (r1 != 0) goto L_0x0004
                java.lang.String r1 = "android.location.GPS_FIX_CHANGE"
                boolean r1 = r0.equals(r1)     // Catch:{ Throwable -> 0x0048 }
                if (r1 != 0) goto L_0x0004
                java.lang.String r1 = "android.net.conn.CONNECTIVITY_CHANGE"
                boolean r0 = r0.equals(r1)     // Catch:{ Throwable -> 0x0048 }
                if (r0 == 0) goto L_0x0004
                com.loc.aw r0 = com.loc.aw.this     // Catch:{ Throwable -> 0x0048 }
                boolean r0 = r0.A()     // Catch:{ Throwable -> 0x0048 }
                if (r0 == 0) goto L_0x0004
                com.loc.aw r0 = com.loc.aw.this     // Catch:{ Throwable -> 0x0048 }
                r1 = 1
                r2 = 2
                int unused = r0.a((boolean) r1, (int) r2)     // Catch:{ Throwable -> 0x0048 }
                goto L_0x0004
            L_0x00e3:
                r1 = move-exception
                goto L_0x0024
            L_0x00e6:
                r1 = move-exception
                goto L_0x0065
            */
            throw new UnsupportedOperationException("Method not decompiled: com.loc.aw.b.onReceive(android.content.Context, android.content.Intent):void");
        }
    }

    private AmapLoc a(String str, boolean z2, boolean z3) throws Exception {
        if (this.m == null) {
            c.append("context is null");
            AmapLoc amapLoc = new AmapLoc();
            amapLoc.b(1);
            amapLoc.b(c.toString());
            return amapLoc;
        } else if (str == null || str.length() == 0) {
            AmapLoc amapLoc2 = new AmapLoc();
            amapLoc2.b(3);
            amapLoc2.b(c.toString());
            return amapLoc2;
        } else {
            new AmapLoc();
            br brVar = new br();
            try {
                byte[] a2 = this.b.a(this.m, this.v, this.H, e.a());
                if (a2 == null) {
                    AmapLoc amapLoc3 = new AmapLoc();
                    amapLoc3.b(4);
                    c.append("please check the network");
                    amapLoc3.b(c.toString());
                    return amapLoc3;
                }
                this.ad = bw.a();
                String str2 = new String(a2, "UTF-8");
                if (str2.contains("\"status\":\"0\"")) {
                    return brVar.b(str2);
                }
                String a3 = bh.a(a2);
                if (a3 == null) {
                    AmapLoc amapLoc4 = new AmapLoc();
                    amapLoc4.b(5);
                    c.append("decrypt response data error");
                    amapLoc4.b(c.toString());
                    return amapLoc4;
                }
                AmapLoc a4 = brVar.a(a3);
                if (bw.a(a4)) {
                    if (a4.D() != null) {
                    }
                    if (a4.a() == 0 && a4.b() == 0) {
                        if ("-5".equals(a4.m()) || com.alipay.sdk.cons.a.e.equals(a4.m()) || "2".equals(a4.m()) || "14".equals(a4.m()) || "24".equals(a4.m()) || "-1".equals(a4.m())) {
                            a4.a(5);
                        } else {
                            a4.a(6);
                        }
                        a4.b(a4.m());
                    }
                    a4.a(this.ac);
                    a4.b(this.ab);
                    return a4;
                } else if (a4 != null) {
                    this.O = a4.n();
                    a4.b(6);
                    c.append("location faile retype:" + a4.m() + " rdesc:" + (this.O != null ? this.O : "null"));
                    a4.b(c.toString());
                    return a4;
                } else {
                    AmapLoc amapLoc5 = new AmapLoc();
                    amapLoc5.b(6);
                    c.append("location is null");
                    amapLoc5.b(c.toString());
                    return amapLoc5;
                }
            } catch (Throwable th) {
                th.printStackTrace();
                AmapLoc amapLoc6 = new AmapLoc();
                amapLoc6.b(4);
                c.append("please check the network");
                amapLoc6.b(c.toString());
                return amapLoc6;
            }
        }
    }

    private void a(StringBuilder sb) {
        if (sb != null) {
            for (String str : new String[]{" phnum=\"\"", " nettype=\"\"", " nettype=\"UNKWN\"", " inftype=\"\"", "<macs><![CDATA[]]></macs>", "<nb></nb>", "<mmac><![CDATA[]]></mmac>", " gtype=\"0\"", " gmock=\"0\"", " glong=\"0.0\"", " glat=\"0.0\"", " precision=\"0.0\"", " glong=\"0\"", " glat=\"0\"", " precision=\"0\"", "<smac>null</smac>", "<smac>00:00:00:00:00:00</smac>", "<imei>000000000000000</imei>", "<imsi>000000000000000</imsi>", "<mcc>000</mcc>", "<mcc>0</mcc>", "<lac>0</lac>", "<cellid>0</cellid>", "<key></key>", "<poiid></poiid>", "<poiid>null</poiid>"}) {
                while (sb.indexOf(str) != -1) {
                    int indexOf = sb.indexOf(str);
                    sb.delete(indexOf, str.length() + indexOf);
                }
            }
            while (sb.indexOf("*<") != -1) {
                sb.deleteCharAt(sb.indexOf("*<"));
            }
        }
    }

    private boolean m() {
        if (this.x != 0 && bw.b() - this.x <= 20000) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void n() {
        this.D = 0;
        this.q.clear();
        this.u = null;
        synchronized (this.h) {
            this.r.clear();
            this.s.clear();
        }
    }

    private void o() {
        boolean z2;
        boolean z3 = false;
        if (p()) {
            if (bw.c() >= 18 || bw.c() <= 3 || !bw.a(this.v, "wifiactivescan")) {
                z2 = false;
            } else {
                try {
                    z2 = com.alipay.sdk.cons.a.e.equals(this.v.getString("wifiactivescan"));
                } catch (Exception e2) {
                    z2 = false;
                }
            }
            if (z2) {
                try {
                    z3 = this.o.e();
                    if (z3) {
                        this.C = bw.b();
                    }
                } catch (Exception e3) {
                }
            }
            if (!z3) {
                try {
                    if (this.o.d()) {
                        this.C = bw.b();
                    }
                } catch (Exception e4) {
                }
            }
        }
    }

    private boolean p() {
        if (this.o != null) {
            return this.o.f();
        }
        return false;
    }

    private boolean q() {
        return this.L != null;
    }

    private boolean r() {
        try {
            if (!q()) {
                return false;
            }
            return this.L.d();
        } catch (Exception e2) {
            return false;
        }
    }

    private void s() {
        boolean z2 = true;
        if (bw.a(this.v, "coll")) {
            try {
                if (this.v.getString("coll").equals("0")) {
                    z2 = false;
                }
            } catch (Exception e2) {
            }
        }
        if (!z2) {
            u();
        } else if (!r()) {
            try {
                this.L.b(e.m * 1000);
                z();
                t();
                this.L.a();
            } catch (Exception e3) {
            }
        }
    }

    private void t() {
        if (q()) {
            if (!q() || this.L.g() <= 0) {
                try {
                    if (!q() || this.L.f()) {
                    }
                } catch (Exception e2) {
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void u() {
        if (r()) {
            e.m = 20;
            try {
                this.L.c();
            } catch (Exception e2) {
            }
        }
    }

    private void a(SharedPreferences sharedPreferences) {
        String str = null;
        if (this.m != null && sharedPreferences != null) {
            if (sharedPreferences.contains("smac")) {
                try {
                    str = r.a(sharedPreferences.getString("smac", (String) null).getBytes("UTF-8"));
                } catch (Exception e2) {
                    sharedPreferences.edit().remove("smac").commit();
                }
            }
            if (!TextUtils.isEmpty(str) && !str.equals("00:00:00:00:00:00")) {
                this.F = str;
            }
        }
    }

    private void v() {
        if (this.m != null && !TextUtils.isEmpty(this.F)) {
            SharedPreferences sharedPreferences = this.m.getSharedPreferences("pref", 0);
            String str = null;
            try {
                str = r.a(this.F.getBytes("UTF-8"));
            } catch (Exception e2) {
            }
            if (!TextUtils.isEmpty(str)) {
                sharedPreferences.edit().putString("smac", str).commit();
            }
        }
    }

    private void w() {
        e.e = "";
        e.f = "";
        e.h = "";
    }

    private void x() {
        ArrayList<ScanResult> arrayList = this.r;
        String str = null;
        try {
            if (bw.a(this.v, "wait1stwifi")) {
                str = this.v.getString("wait1stwifi");
            }
            if (TextUtils.isEmpty(str) || !str.equals(com.alipay.sdk.cons.a.e)) {
                return;
            }
        } catch (Exception e2) {
        }
        synchronized (this.h) {
            arrayList.clear();
        }
        o();
        for (int i2 = 20; i2 > 0 && arrayList.isEmpty(); i2--) {
            try {
                Thread.sleep(3000 / ((long) 20));
            } catch (Exception e3) {
            }
        }
        synchronized (this.h) {
        }
        if (arrayList.isEmpty() && this.o != null) {
            arrayList.addAll(this.o.a());
        }
    }

    /* access modifiers changed from: private */
    public void b(int i2) {
        int i3 = 70254591;
        if (q()) {
            try {
                z();
                switch (i2) {
                    case 1:
                        i3 = 674234367;
                        break;
                    case 2:
                        if (A()) {
                            i3 = 2083520511;
                            break;
                        } else {
                            i3 = 674234367;
                            break;
                        }
                }
                this.L.a((ch) null, a(1, i3, 1));
                this.M = this.L.e();
                if (this.M != null) {
                    byte[] a2 = this.M.a();
                    String a3 = this.b.a(a2, this.m, "http://cgicol.amap.com/collection/writedata?ver=v1.0_ali&", false);
                    if (q()) {
                        if (TextUtils.isEmpty(a3) || !a3.equals("true")) {
                            this.K++;
                            this.L.a(this.M, a(1, i3, 0));
                        } else {
                            this.L.a(this.M, a(1, i3, 1));
                            String a4 = bw.a(0, "yyyyMMdd");
                            if (a4.equals(String.valueOf(this.N[0]))) {
                                int[] iArr = this.N;
                                iArr[1] = a2.length + iArr[1];
                            } else {
                                try {
                                    this.N[0] = Integer.parseInt(a4);
                                } catch (Exception e2) {
                                    this.N[0] = 0;
                                    this.N[1] = 0;
                                    this.N[2] = 0;
                                }
                                this.N[1] = a2.length;
                            }
                            this.N[2] = this.N[2] + 1;
                            F();
                        }
                    }
                }
                t();
                if (q() && this.L.g() == 0) {
                    y();
                } else if (this.K >= 3) {
                    y();
                }
            } catch (Exception e3) {
            }
        }
    }

    private void c(final int i2) {
        t();
        if (this.J == null) {
            this.J = new TimerTask() {
                public void run() {
                    Thread.currentThread().setPriority(1);
                    if (bw.b() - aw.this.z >= 10000) {
                        if (aw.this.A()) {
                            aw.this.b(i2);
                        } else {
                            aw.this.y();
                        }
                    }
                }
            };
        }
        if (this.I == null) {
            this.I = new Timer("T-U", false);
            this.I.schedule(this.J, 2000, 2000);
        }
    }

    /* access modifiers changed from: private */
    public void y() {
        if (this.J != null) {
            this.J.cancel();
            this.J = null;
        }
        if (this.I != null) {
            this.I.cancel();
            this.I.purge();
            this.I = null;
        }
    }

    private void z() {
        if (q()) {
            try {
                this.L.a(768);
            } catch (Exception e2) {
            }
        }
    }

    private String a(int i2, int i3, int i4) throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("e", i2);
        jSONObject.put("d", i3);
        jSONObject.put("u", i4);
        return jSONObject.toString();
    }

    /* access modifiers changed from: private */
    public boolean A() {
        if (this.o == null || this.n == null) {
            return false;
        }
        return this.o.a(this.n);
    }

    private void B() {
        if (bw.a(this.v, "poiid")) {
            try {
                String string = this.v.getString("poiid");
                if (TextUtils.isEmpty(string)) {
                    this.G = null;
                } else if (string.length() > 32) {
                    this.G = null;
                } else {
                    this.G = string;
                }
            } catch (Exception e2) {
            }
        } else {
            this.G = null;
        }
    }

    private String C() {
        try {
            return cb.a("version");
        } catch (Exception e2) {
            return null;
        }
    }

    private void D() {
        if (this.o != null && this.m != null && this.a) {
            this.o.a(this.a);
        }
    }

    private boolean E() {
        if (this.m == null) {
            c.append("context is null");
            return false;
        } else if (TextUtils.isEmpty(e.e)) {
            c.append("src is null");
            return false;
        } else if (!TextUtils.isEmpty(e.f)) {
            return true;
        } else {
            c.append("license is null");
            return false;
        }
    }

    private void b(SharedPreferences sharedPreferences) {
        SharedPreferences sharedPreferences2;
        if (this.m != null && (sharedPreferences2 = this.m.getSharedPreferences("pref", 0)) != null && sharedPreferences2.contains("coluphist")) {
            try {
                String[] split = r.a(sharedPreferences2.getString("coluphist", (String) null).getBytes("UTF-8")).split(ListUtils.DEFAULT_JOIN_SEPARATOR);
                for (int i2 = 0; i2 < 3; i2++) {
                    this.N[i2] = Integer.parseInt(split[i2]);
                }
            } catch (Exception e2) {
                sharedPreferences2.edit().remove("coluphist").commit();
            }
        }
    }

    private void F() {
        SharedPreferences sharedPreferences;
        if (this.m != null && this.N[0] != 0 && (sharedPreferences = this.m.getSharedPreferences("pref", 0)) != null) {
            StringBuilder sb = new StringBuilder();
            for (int append : this.N) {
                sb.append(append).append(ListUtils.DEFAULT_JOIN_SEPARATOR);
            }
            try {
                sb.deleteCharAt(this.N.length - 1);
                sharedPreferences.edit().putString("coluphist", r.a(sb.toString().getBytes("UTF-8")));
            } catch (Exception e2) {
            }
            sb.delete(0, sb.length());
        }
    }

    /* compiled from: APS */
    class a implements ax.a {
        a() {
        }

        public void a(int i) {
            aw.this.e = i;
        }
    }

    public synchronized void g() {
        if (this.E >= 1 && !this.f) {
            if (this.d == null) {
                this.d = new ax(this.m.getApplicationContext());
                this.d.a((ax.a) this.l);
            }
            if (bu.p()) {
                try {
                    if (this.d != null) {
                        this.d.b();
                    }
                    this.f = true;
                } catch (Throwable th) {
                    this.f = true;
                    th.printStackTrace();
                }
            } else {
                this.f = true;
            }
        }
        return;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void b(android.content.Context r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            int r0 = com.loc.n.a     // Catch:{ Throwable -> 0x0019, all -> 0x0016 }
            r1 = -1
            if (r0 != r1) goto L_0x0014
            java.lang.String r0 = "2.3.0"
            com.loc.v r0 = com.loc.e.a((java.lang.String) r0)     // Catch:{ Throwable -> 0x0019, all -> 0x0016 }
            android.content.Context r1 = r2.m     // Catch:{ Throwable -> 0x0019, all -> 0x0016 }
            com.loc.n.b(r1, r0)     // Catch:{ Throwable -> 0x0019, all -> 0x0016 }
            com.loc.bu.a((android.content.Context) r3)     // Catch:{ Throwable -> 0x0019, all -> 0x0016 }
        L_0x0014:
            monitor-exit(r2)
            return
        L_0x0016:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        L_0x0019:
            r0 = move-exception
            goto L_0x0014
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aw.b(android.content.Context):void");
    }

    private AmapLoc G() throws Exception {
        be beVar;
        AmapLoc a2;
        if (c.length() > 0) {
            c.delete(0, c.length());
        }
        try {
            if (!this.A) {
                this.p.f();
                this.p.d();
                beVar = this.p.b();
            } else {
                beVar = null;
            }
            try {
                d();
                ArrayList<ScanResult> arrayList = this.q;
                if (arrayList != null && arrayList.isEmpty()) {
                    this.D = bw.b();
                    List<ScanResult> a3 = this.o.a();
                    if (a3 != null) {
                        arrayList.addAll(a3);
                    }
                }
                e();
            } catch (Throwable th) {
            }
        } catch (Throwable th2) {
            beVar = null;
        }
        String b2 = b(false);
        if (TextUtils.isEmpty(b2)) {
            if (0 == 0) {
                a2 = new AmapLoc();
            } else {
                a2 = null;
            }
            a2.b(this.j);
            a2.b(c.toString());
        } else {
            String str = b2 + com.alipay.sdk.sys.a.b + this.ac + com.alipay.sdk.sys.a.b + this.ab;
            StringBuilder c2 = c(true);
            AmapLoc a4 = bj.a().a(str, c2);
            if (bw.a(a4)) {
                this.R = 0;
                a4.a(4);
                this.w = a4;
                H();
                return a4;
            }
            AmapLoc a5 = a(f(), false, true);
            if (bw.a(a5)) {
                a5.f("new");
                this.S = c2.toString();
                this.T = beVar;
                this.x = bw.b();
                this.w = a5;
                bj.a().a(str, c2, this.w, this.m, true);
                H();
                a2 = a5;
            } else {
                a2 = a(b2, c2.toString());
                if (!bw.a(a2)) {
                    return a5;
                }
                this.S = c2.toString();
                a2.f(UriUtil.LOCAL_FILE_SCHEME);
                a2.a(8);
                a2.b("离线定位结果，在线定位失败原因:" + a5.d());
                this.w = a2;
            }
        }
        return a2;
    }

    private void H() {
        this.U = null;
        this.V = null;
    }

    private AmapLoc a(String str, String str2) {
        ArrayList<String> a2;
        if (!bu.i()) {
            return null;
        }
        if (str != null && str.equals(this.V) && this.U != null) {
            return this.U;
        }
        I();
        ArrayList<bl> b2 = bm.a().b();
        try {
            if (bd.b() && (a2 = bd.a(str, false)) != null) {
                int size = a2.size();
                for (int i2 = 0; i2 < size; i2++) {
                    String str3 = a2.get(i2);
                    AmapLoc a3 = a(str, str2, (double[]) null, str3.substring(str3.lastIndexOf(File.separator) + 1, str3.length()), 0);
                    if (bw.a(a3)) {
                        this.V = str;
                        this.U = a3;
                        return a3;
                    }
                }
            }
            int size2 = b2.size();
            if (size2 != 0) {
                for (int i3 = 0; i3 < size2; i3++) {
                    AmapLoc a4 = a(str, str2, (double[]) null, b2.get(i3).a(), 0);
                    if (bw.a(a4)) {
                        this.V = str;
                        this.U = a4;
                        return a4;
                    }
                }
            }
        } catch (Throwable th) {
        }
        return null;
    }

    private void I() {
        if (!bu.i()) {
            J();
        } else if (bd.a[1] > 2000) {
            J();
        } else if (this.W == null || this.X == null) {
            this.X = new TimerTask() {
                public void run() {
                    boolean z;
                    int size;
                    if (bd.a[1] > 2000) {
                        aw.this.J();
                        return;
                    }
                    Thread.currentThread().setPriority(1);
                    if (bw.a(aw.this.v, "fetchoffdatamobile")) {
                        try {
                            z = com.alipay.sdk.cons.a.e.equals(aw.this.v.getString("fetchoffdatamobile"));
                        } catch (Exception e) {
                            z = false;
                        }
                    } else {
                        z = false;
                    }
                    ArrayList<bl> b = bm.a().b();
                    if (b != null && (size = b.size()) > 0) {
                        if (aw.this.Y == null) {
                            String unused = aw.this.Y = aw.this.b(true);
                        }
                        int i = 0;
                        while (i < size && i < 20) {
                            bd.a(aw.this.m, aw.this.Y, b.get(i).a(), 1, 0, !z, true);
                            i++;
                        }
                    }
                    aw.this.L();
                    try {
                        ArrayList<String> b2 = bk.a().b(aw.this.m, 1);
                        if (b2 != null && b2.size() > 0) {
                            Iterator<String> it = b2.iterator();
                            while (it.hasNext()) {
                                bd.a(aw.this.Y, it.next(), 1, 0);
                            }
                        }
                    } catch (Exception e2) {
                    }
                }
            };
            this.W = new Timer("T-O", false);
            this.W.schedule(this.X, 0, 60000);
        }
    }

    /* access modifiers changed from: private */
    public void J() {
        if (this.X != null) {
            this.X.cancel();
            this.X = null;
        }
        if (this.W != null) {
            this.W.cancel();
            this.W.purge();
            this.W = null;
        }
    }

    private AmapLoc a(String str, String str2, double[] dArr, String str3, int i2) {
        if (!bw.k()) {
            return null;
        }
        if (TextUtils.isEmpty(str3)) {
            if (dArr == null) {
                dArr = M();
            }
            if (dArr[0] == 0.0d || dArr[1] == 0.0d) {
                return null;
            }
        }
        bw.b();
        return bd.a(dArr, str3, str, str2, i2, this.m, new int[]{this.aa, this.Z});
    }

    private void K() {
        this.Z = 0;
        this.aa = 0;
    }

    /* access modifiers changed from: private */
    public void L() {
        SharedPreferences sharedPreferences;
        if (this.m != null && bd.a[0] != 0 && (sharedPreferences = this.m.getSharedPreferences("pref", 0)) != null) {
            StringBuilder sb = new StringBuilder();
            for (int append : bd.a) {
                sb.append(append).append(ListUtils.DEFAULT_JOIN_SEPARATOR);
            }
            try {
                sb.deleteCharAt(sb.length() - 1);
                sharedPreferences.edit().putString("activityoffdl", bw.c(sb.toString())).commit();
            } catch (Exception e2) {
            }
            sb.delete(0, sb.length());
        }
    }

    private double[] M() {
        double[] dArr = new double[2];
        if (bw.a(this.w)) {
            dArr[0] = this.w.i();
            dArr[1] = this.w.h();
        } else if (bw.a(this.g)) {
            dArr[0] = this.g.i();
            dArr[1] = this.g.h();
        } else {
            dArr[0] = 0.0d;
            dArr[1] = 0.0d;
        }
        return dArr;
    }

    private void c(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null && sharedPreferences.contains("activityoffdl")) {
            try {
                String[] split = bw.d(sharedPreferences.getString("activityoffdl", (String) null)).split(ListUtils.DEFAULT_JOIN_SEPARATOR);
                for (int i2 = 0; i2 < 2; i2++) {
                    bd.a[i2] = Integer.parseInt(split[i2]);
                }
            } catch (Exception e2) {
                sharedPreferences.edit().remove("activityoffdl").commit();
            }
        }
    }

    public void h() {
        if (this.k && !r()) {
            s();
        }
    }

    private void N() {
        try {
            this.S = null;
            this.w = null;
            this.R = 0;
            this.x = 0;
            ba.a().b();
        } catch (Exception e2) {
        }
    }
}

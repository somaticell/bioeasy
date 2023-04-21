package com.baidu.location.d;

import android.annotation.TargetApi;
import android.content.Context;
import android.location.GnssStatus;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.alipay.android.phone.mrpc.core.Headers;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.a.g;
import com.baidu.location.a.t;
import com.baidu.location.a.v;
import com.baidu.location.f;
import com.baidu.location.f.j;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class e {
    /* access modifiers changed from: private */
    public static double C = 100.0d;
    private static String D = "";
    private static e c = null;
    /* access modifiers changed from: private */
    public static int m = 0;
    /* access modifiers changed from: private */
    public static int n = 0;
    private static String u = null;
    /* access modifiers changed from: private */
    public int A;
    /* access modifiers changed from: private */
    public int B;
    private long E = 0;
    /* access modifiers changed from: private */
    public ArrayList<ArrayList<Float>> F = new ArrayList<>();
    private final long a = 1000;
    private final long b = 9000;
    private Context d;
    /* access modifiers changed from: private */
    public LocationManager e = null;
    private Location f;
    private c g = null;
    private d h = null;
    /* access modifiers changed from: private */
    public GpsStatus i;
    private a j;
    private boolean k = false;
    private b l = null;
    /* access modifiers changed from: private */
    public long o = 0;
    /* access modifiers changed from: private */
    public boolean p = false;
    /* access modifiers changed from: private */
    public boolean q = false;
    private String r = null;
    private boolean s = false;
    /* access modifiers changed from: private */
    public long t = 0;
    /* access modifiers changed from: private */
    public Handler v = null;
    private final int w = 1;
    private final int x = 2;
    private final int y = 3;
    private final int z = 4;

    @TargetApi(24)
    private class a extends GnssStatus.Callback {
        private a() {
        }

        /* synthetic */ a(e eVar, f fVar) {
            this();
        }

        public void onFirstFix(int i) {
        }

        public void onSatelliteStatusChanged(GnssStatus gnssStatus) {
            int i = 0;
            if (e.this.e != null) {
                int satelliteCount = gnssStatus.getSatelliteCount();
                e.this.F.clear();
                int i2 = 0;
                for (int i3 = 0; i3 < satelliteCount; i3++) {
                    ArrayList arrayList = new ArrayList();
                    if (gnssStatus.usedInFix(i3)) {
                        i2++;
                        if (gnssStatus.getConstellationType(i3) == 1) {
                            i++;
                            arrayList.add(Float.valueOf(gnssStatus.getCn0DbHz(i3)));
                            arrayList.add(Float.valueOf(0.0f));
                            arrayList.add(Float.valueOf(gnssStatus.getAzimuthDegrees(i3)));
                            arrayList.add(Float.valueOf(gnssStatus.getElevationDegrees(i3)));
                            arrayList.add(Float.valueOf(1.0f));
                        }
                        e.this.F.add(arrayList);
                    }
                }
                int unused = e.m = i2;
                int unused2 = e.n = i;
            }
        }

        public void onStarted() {
        }

        public void onStopped() {
            e.this.d((Location) null);
            e.this.b(false);
            int unused = e.m = 0;
            int unused2 = e.n = 0;
        }
    }

    private class b implements GpsStatus.Listener {
        long a;
        private long c;
        private final int d;
        private boolean e;
        private List<String> f;
        private String g;
        private String h;
        private String i;
        private long j;

        private b() {
            this.a = 0;
            this.c = 0;
            this.d = 400;
            this.e = false;
            this.f = new ArrayList();
            this.g = null;
            this.h = null;
            this.i = null;
            this.j = 0;
        }

        /* synthetic */ b(e eVar, f fVar) {
            this();
        }

        public void onGpsStatusChanged(int i2) {
            int i3 = 0;
            if (e.this.e != null) {
                switch (i2) {
                    case 2:
                        e.this.d((Location) null);
                        e.this.b(false);
                        int unused = e.m = 0;
                        int unused2 = e.n = 0;
                        return;
                    case 4:
                        if (e.this.q) {
                            try {
                                if (e.this.i == null) {
                                    GpsStatus unused3 = e.this.i = e.this.e.getGpsStatus((GpsStatus) null);
                                } else {
                                    e.this.e.getGpsStatus(e.this.i);
                                }
                                int unused4 = e.this.A = 0;
                                int unused5 = e.this.B = 0;
                                double d2 = 0.0d;
                                e.this.F.clear();
                                int i4 = 0;
                                for (GpsSatellite next : e.this.i.getSatellites()) {
                                    ArrayList arrayList = new ArrayList();
                                    if (next.usedInFix()) {
                                        i4++;
                                        if (next.getPrn() <= 65) {
                                            i3++;
                                            d2 += (double) next.getSnr();
                                            arrayList.add(Float.valueOf(0.0f));
                                            arrayList.add(Float.valueOf(next.getSnr()));
                                            arrayList.add(Float.valueOf(next.getAzimuth()));
                                            arrayList.add(Float.valueOf(next.getElevation()));
                                            arrayList.add(Float.valueOf(1.0f));
                                        }
                                        e.this.F.add(arrayList);
                                        if (next.getSnr() >= ((float) j.G)) {
                                            e.f(e.this);
                                        }
                                    }
                                }
                                if (i3 > 0) {
                                    int unused6 = e.n = i3;
                                    double unused7 = e.C = d2 / ((double) i3);
                                }
                                if (i4 > 0) {
                                    this.j = System.currentTimeMillis();
                                    int unused8 = e.m = i4;
                                    return;
                                } else if (System.currentTimeMillis() - this.j > 100) {
                                    this.j = System.currentTimeMillis();
                                    int unused9 = e.m = i4;
                                    return;
                                } else {
                                    return;
                                }
                            } catch (Exception e2) {
                                return;
                            }
                        } else {
                            return;
                        }
                    default:
                        return;
                }
            }
        }
    }

    private class c implements LocationListener {
        private c() {
        }

        /* synthetic */ c(e eVar, f fVar) {
            this();
        }

        public void onLocationChanged(Location location) {
            long unused = e.this.t = System.currentTimeMillis();
            e.this.b(true);
            e.this.d(location);
            boolean unused2 = e.this.p = false;
        }

        public void onProviderDisabled(String str) {
            e.this.d((Location) null);
            e.this.b(false);
        }

        public void onProviderEnabled(String str) {
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
            switch (i) {
                case 0:
                    e.this.d((Location) null);
                    e.this.b(false);
                    return;
                case 1:
                    long unused = e.this.o = System.currentTimeMillis();
                    boolean unused2 = e.this.p = true;
                    e.this.b(false);
                    return;
                case 2:
                    boolean unused3 = e.this.p = false;
                    return;
                default:
                    return;
            }
        }
    }

    private class d implements LocationListener {
        private long b;

        private d() {
            this.b = 0;
        }

        /* synthetic */ d(e eVar, f fVar) {
            this();
        }

        public void onLocationChanged(Location location) {
            if (!e.this.q && location != null && location.getProvider() == "gps" && System.currentTimeMillis() - this.b >= 10000 && v.a(location, false)) {
                this.b = System.currentTimeMillis();
                e.this.v.sendMessage(e.this.v.obtainMessage(4, location));
            }
        }

        public void onProviderDisabled(String str) {
        }

        public void onProviderEnabled(String str) {
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
        }
    }

    private e() {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Class.forName("android.location.GnssStatus");
                this.k = true;
            } catch (ClassNotFoundException e2) {
                this.k = false;
            }
        }
    }

    public static synchronized e a() {
        e eVar;
        synchronized (e.class) {
            if (c == null) {
                c = new e();
            }
            eVar = c;
        }
        return eVar;
    }

    public static String a(Location location) {
        float f2 = -1.0f;
        if (location == null) {
            return null;
        }
        float speed = (float) (((double) location.getSpeed()) * 3.6d);
        if (!location.hasSpeed()) {
            speed = -1.0f;
        }
        int accuracy = (int) (location.hasAccuracy() ? location.getAccuracy() : -1.0f);
        double altitude = location.hasAltitude() ? location.getAltitude() : 555.0d;
        if (location.hasBearing()) {
            f2 = location.getBearing();
        }
        return String.format(Locale.CHINA, "&ll=%.5f|%.5f&s=%.1f&d=%.1f&ll_r=%d&ll_n=%d&ll_h=%.2f&ll_t=%d&ll_sn=%d|%d&ll_snr=%.1f", new Object[]{Double.valueOf(location.getLongitude()), Double.valueOf(location.getLatitude()), Float.valueOf(speed), Float.valueOf(f2), Integer.valueOf(accuracy), Integer.valueOf(m), Double.valueOf(altitude), Long.valueOf(location.getTime() / 1000), Integer.valueOf(m), Integer.valueOf(n), Double.valueOf(C)});
    }

    private void a(double d2, double d3, float f2) {
        int i2 = 0;
        if (com.baidu.location.b.e.a().f) {
            if (d2 >= 73.146973d && d2 <= 135.252686d && d3 <= 54.258807d && d3 >= 14.604847d && f2 <= 18.0f) {
                int i3 = (int) ((d2 - j.s) * 1000.0d);
                int i4 = (int) ((j.t - d3) * 1000.0d);
                if (i3 <= 0 || i3 >= 50 || i4 <= 0 || i4 >= 50) {
                    j.q = d2;
                    j.r = d3;
                    com.baidu.location.b.e.a().a(String.format(Locale.CHINA, "&ll=%.5f|%.5f", new Object[]{Double.valueOf(d2), Double.valueOf(d3)}) + "&im=" + com.baidu.location.f.b.a().b());
                } else {
                    int i5 = i3 + (i4 * 50);
                    int i6 = i5 >> 2;
                    int i7 = i5 & 3;
                    if (j.w) {
                        i2 = (j.v[i6] >> (i7 * 2)) & 3;
                    }
                }
            }
            if (j.u != i2) {
                j.u = i2;
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, Location location) {
        if (location != null) {
            String str2 = str + com.baidu.location.a.a.a().f();
            boolean f2 = h.a().f();
            t.a(new a(b.a().f()));
            t.a(System.currentTimeMillis());
            t.a(new Location(location));
            t.a(str2);
            if (!f2) {
                v.a(t.c(), (g) null, t.d(), str2);
            }
        }
    }

    public static boolean a(Location location, Location location2, boolean z2) {
        if (location == location2) {
            return false;
        }
        if (location == null || location2 == null) {
            return true;
        }
        float speed = location2.getSpeed();
        if (z2 && ((j.u == 3 || !com.baidu.location.f.d.a().a(location2.getLongitude(), location2.getLatitude())) && speed < 5.0f)) {
            return true;
        }
        float distanceTo = location2.distanceTo(location);
        return speed > j.K ? distanceTo > j.M : speed > j.J ? distanceTo > j.L : distanceTo > 5.0f;
    }

    public static String b(Location location) {
        String a2 = a(location);
        return a2 != null ? a2 + "&g_tp=0" : a2;
    }

    /* access modifiers changed from: private */
    public void b(boolean z2) {
        this.s = z2;
        if (!z2 || !j()) {
        }
    }

    public static String c(Location location) {
        String a2 = a(location);
        return a2 != null ? a2 + u : a2;
    }

    /* access modifiers changed from: private */
    public void d(Location location) {
        this.v.sendMessage(this.v.obtainMessage(1, location));
    }

    /* access modifiers changed from: private */
    public void e(Location location) {
        Location location2;
        if (location != null) {
            int i2 = m;
            if (i2 == 0) {
                try {
                    i2 = location.getExtras().getInt("satellites");
                } catch (Exception e2) {
                }
            }
            if (i2 != 0 || j.l) {
                this.f = location;
                int i3 = m;
                if (this.f == null) {
                    this.r = null;
                    location2 = null;
                } else {
                    Location location3 = new Location(this.f);
                    long currentTimeMillis = System.currentTimeMillis();
                    this.f.setTime(currentTimeMillis);
                    float speed = (float) (((double) this.f.getSpeed()) * 3.6d);
                    if (!this.f.hasSpeed()) {
                        speed = -1.0f;
                    }
                    if (i3 == 0) {
                        try {
                            i3 = this.f.getExtras().getInt("satellites");
                        } catch (Exception e3) {
                        }
                    }
                    this.r = String.format(Locale.CHINA, "&ll=%.5f|%.5f&s=%.1f&d=%.1f&ll_n=%d&ll_t=%d", new Object[]{Double.valueOf(this.f.getLongitude()), Double.valueOf(this.f.getLatitude()), Float.valueOf(speed), Float.valueOf(this.f.getBearing()), Integer.valueOf(i3), Long.valueOf(currentTimeMillis)});
                    a(this.f.getLongitude(), this.f.getLatitude(), speed);
                    location2 = location3;
                }
                try {
                    g.a().a(this.f);
                } catch (Exception e4) {
                }
                if (location2 != null) {
                    com.baidu.location.a.d.a().a(location2);
                }
                if (j() && this.f != null) {
                    D = k();
                    if (com.baidu.location.indoor.g.a().g() && !com.baidu.location.indoor.g.a().a(this.f)) {
                        com.baidu.location.a.a.a().a(g());
                    } else if (!com.baidu.location.indoor.g.a().g()) {
                        com.baidu.location.a.a.a().a(g());
                    } else {
                        com.baidu.location.a.a.a().a(g());
                    }
                    if (m > 2 && v.a(this.f, true)) {
                        boolean f2 = h.a().f();
                        t.a(new a(b.a().f()));
                        t.a(System.currentTimeMillis());
                        t.a(new Location(this.f));
                        t.a(com.baidu.location.a.a.a().f());
                        if (!f2) {
                            v.a(t.c(), (g) null, t.d(), com.baidu.location.a.a.a().f());
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        this.f = null;
    }

    static /* synthetic */ int f(e eVar) {
        int i2 = eVar.B;
        eVar.B = i2 + 1;
        return i2;
    }

    private String k() {
        StringBuilder sb = new StringBuilder();
        if (this.F.size() > 32 || this.F.size() == 0) {
            return sb.toString();
        }
        Iterator<ArrayList<Float>> it = this.F.iterator();
        boolean z2 = true;
        while (it.hasNext()) {
            ArrayList next = it.next();
            if (next.size() == 5) {
                if (z2) {
                    z2 = false;
                } else {
                    sb.append("|");
                }
                sb.append(String.format("%.1f;", new Object[]{next.get(0)}));
                sb.append(String.format("%.1f;", new Object[]{next.get(2)}));
                sb.append(String.format("%.0f;", new Object[]{next.get(2)}));
                sb.append(String.format("%.0f;", new Object[]{next.get(3)}));
                sb.append(String.format("%.0f", new Object[]{next.get(4)}));
            }
        }
        return sb.toString();
    }

    public void a(boolean z2) {
        if (z2) {
            c();
        } else {
            d();
        }
    }

    public synchronized void b() {
        if (f.isServing) {
            this.d = f.getServiceContext();
            try {
                this.e = (LocationManager) this.d.getSystemService(Headers.LOCATION);
                if (!this.k) {
                    this.l = new b(this, (f) null);
                    this.e.addGpsStatusListener(this.l);
                } else {
                    this.j = new a(this, (f) null);
                    this.e.registerGnssStatusCallback(this.j);
                }
                this.h = new d(this, (f) null);
                this.e.requestLocationUpdates("passive", 9000, 0.0f, this.h);
            } catch (Exception e2) {
            }
            this.v = new f(this);
        }
    }

    public void c() {
        Log.d(com.baidu.location.f.a.a, "start gps...");
        if (!this.q) {
            try {
                this.g = new c(this, (f) null);
                try {
                    this.e.sendExtraCommand("gps", "force_xtra_injection", new Bundle());
                } catch (Exception e2) {
                }
                this.e.requestLocationUpdates("gps", 1000, 0.0f, this.g);
                this.E = System.currentTimeMillis();
                this.q = true;
            } catch (Exception e3) {
            }
        }
    }

    public void d() {
        if (this.q) {
            if (this.e != null) {
                try {
                    if (this.g != null) {
                        this.e.removeUpdates(this.g);
                    }
                } catch (Exception e2) {
                }
            }
            j.d = 0;
            j.u = 0;
            this.g = null;
            this.q = false;
            b(false);
        }
    }

    public synchronized void e() {
        d();
        if (this.e != null) {
            try {
                if (this.l != null) {
                    this.e.removeGpsStatusListener(this.l);
                }
                if (this.k && this.j != null) {
                    this.e.unregisterGnssStatusCallback(this.j);
                }
                this.e.removeUpdates(this.h);
            } catch (Exception e2) {
            }
            this.l = null;
            this.e = null;
        }
    }

    public String f() {
        if (!j() || this.f == null) {
            return null;
        }
        return String.format("%s&idgps_tp=%s", new Object[]{a(this.f).replaceAll("ll", "idll").replaceAll("&d=", "&idd=").replaceAll("&s", "&ids="), this.f.getProvider()});
    }

    public String g() {
        double[] dArr;
        boolean z2;
        if (this.f == null) {
            return null;
        }
        String str = "{\"result\":{\"time\":\"" + j.a() + "\",\"error\":\"61\"},\"content\":{\"point\":{\"x\":" + "\"%f\",\"y\":\"%f\"},\"radius\":\"%d\",\"d\":\"%f\"," + "\"s\":\"%f\",\"n\":\"%d\"";
        int accuracy = (int) (this.f.hasAccuracy() ? this.f.getAccuracy() : 10.0f);
        float speed = (float) (((double) this.f.getSpeed()) * 3.6d);
        if (!this.f.hasSpeed()) {
            speed = -1.0f;
        }
        double[] dArr2 = new double[2];
        if (com.baidu.location.f.d.a().a(this.f.getLongitude(), this.f.getLatitude())) {
            double[] coorEncrypt = Jni.coorEncrypt(this.f.getLongitude(), this.f.getLatitude(), BDLocation.BDLOCATION_WGS84_TO_GCJ02);
            if (coorEncrypt[0] > 0.0d || coorEncrypt[1] > 0.0d) {
                dArr = coorEncrypt;
                z2 = true;
            } else {
                coorEncrypt[0] = this.f.getLongitude();
                coorEncrypt[1] = this.f.getLatitude();
                dArr = coorEncrypt;
                z2 = true;
            }
        } else {
            dArr2[0] = this.f.getLongitude();
            dArr2[1] = this.f.getLatitude();
            dArr = dArr2;
            z2 = false;
        }
        String format = String.format(Locale.CHINA, str, new Object[]{Double.valueOf(dArr[0]), Double.valueOf(dArr[1]), Integer.valueOf(accuracy), Float.valueOf(this.f.getBearing()), Float.valueOf(speed), Integer.valueOf(m)});
        if (!z2) {
            format = format + ",\"in_cn\":\"0\"";
        }
        if (!this.f.hasAltitude()) {
            return format + "}}";
        }
        return format + String.format(Locale.CHINA, ",\"h\":%.2f}}", new Object[]{Double.valueOf(this.f.getAltitude())});
    }

    public Location h() {
        if (this.f != null && Math.abs(System.currentTimeMillis() - this.f.getTime()) <= 60000) {
            return this.f;
        }
        return null;
    }

    public boolean i() {
        try {
            return (this.f == null || this.f.getLatitude() == 0.0d || this.f.getLongitude() == 0.0d || (m <= 2 && this.f.getExtras().getInt("satellites", 3) <= 2)) ? false : true;
        } catch (Exception e2) {
            return (this.f == null || this.f.getLatitude() == 0.0d || this.f.getLongitude() == 0.0d) ? false : true;
        }
    }

    public boolean j() {
        if (!i() || System.currentTimeMillis() - this.t > 10000) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (!this.p || currentTimeMillis - this.o >= 3000) {
            return this.s;
        }
        return true;
    }
}

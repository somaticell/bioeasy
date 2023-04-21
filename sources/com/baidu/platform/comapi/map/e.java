package com.baidu.platform.comapi.map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import com.baidu.mapapi.UIMsg;
import com.baidu.mapapi.common.EnvironmentUtilities;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapBaseIndoorMapInfo;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.model.ParcelItem;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.j;
import com.baidu.platform.comjni.map.basemap.BaseMapCallback;
import com.baidu.platform.comjni.map.basemap.JNIBaseMap;
import com.baidu.platform.comjni.map.basemap.b;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"NewApi"})
public class e implements b {
    private static int N;
    private static int O;
    private static List<JNIBaseMap> ar;
    static long k = 0;
    private static final String o = j.class.getSimpleName();
    private Context A;
    private List<d> B;
    private aa C;
    private g D;
    private o E;
    private ah F;
    private ak G;
    private s H;
    private n I;
    private p J;
    private a K;
    private q L;
    private ai M;
    private int P;
    private int Q;
    private int R;
    private j.a S = new j.a();
    private VelocityTracker T;
    private long U;
    private long V;
    private long W;
    private long X;
    private int Y;
    private float Z;
    public float a = 22.0f;
    private float aa;
    private boolean ab;
    private long ac;
    private long ad;
    private boolean ae = false;
    private boolean af = false;
    private float ag;
    private float ah;
    private float ai;
    private float aj;
    private long ak = 0;
    private long al = 0;
    private f am;
    private String an;
    private int ao;
    private b ap;
    private c aq;
    private boolean as = false;
    private Queue<a> at = new LinkedList();
    public float b = 3.0f;
    public float c = 22.0f;
    boolean d = true;
    boolean e = true;
    List<l> f;
    com.baidu.platform.comjni.map.basemap.a g;
    long h;
    boolean i;
    public int j;
    boolean l;
    boolean m;
    boolean n;
    private boolean p;
    private boolean q;
    private boolean r = true;
    private boolean s = false;
    private boolean t = false;
    private boolean u = false;
    private boolean v = true;
    private boolean w = true;
    private boolean x = false;
    private am y;
    private al z;

    public static class a {
        public long a;
        public int b;
        public int c;
        public int d;
        public Bundle e;

        public a(long j, int i, int i2, int i3) {
            this.a = j;
            this.b = i;
            this.c = i2;
            this.d = i3;
        }

        public a(Bundle bundle) {
            this.e = bundle;
        }
    }

    public e(Context context, String str, int i2) {
        this.A = context;
        this.f = new ArrayList();
        this.an = str;
        this.ao = i2;
    }

    private void P() {
        if (this.t || this.q || this.p || this.u) {
            if (this.a > 20.0f) {
                this.a = 20.0f;
            }
            if (E().a > 20.0f) {
                ae E2 = E();
                E2.a = 20.0f;
                a(E2);
                return;
            }
            return;
        }
        this.a = this.c;
    }

    private boolean Q() {
        if (this.g == null || !this.i) {
            return true;
        }
        this.af = false;
        if (!this.d) {
            return false;
        }
        long j2 = this.al - this.ak;
        float abs = (Math.abs(this.ai - this.ag) * 1000.0f) / ((float) j2);
        float abs2 = (Math.abs(this.aj - this.ah) * 1000.0f) / ((float) j2);
        float sqrt = (float) Math.sqrt((double) ((abs2 * abs2) + (abs * abs)));
        if (sqrt <= 500.0f) {
            return false;
        }
        A();
        a(34, (int) (sqrt * 0.6f), (((int) this.aj) << 16) | ((int) this.ai));
        M();
        return true;
    }

    private Activity a(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return a(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    private void a(String str, String str2, long j2) {
        try {
            Class<?> cls = Class.forName(str);
            Object newInstance = cls.newInstance();
            cls.getMethod(str2, new Class[]{Long.TYPE}).invoke(newInstance, new Object[]{Long.valueOf(j2)});
        } catch (Exception e2) {
        }
    }

    private boolean e(float f2, float f3) {
        if (this.g == null || !this.i) {
            return true;
        }
        this.ae = false;
        GeoPoint b2 = b((int) f2, (int) f3);
        if (b2 == null) {
            return false;
        }
        for (l b3 : this.f) {
            b3.b(b2);
        }
        if (!this.e) {
            return false;
        }
        ae E2 = E();
        E2.a += 1.0f;
        E2.d = b2.getLongitudeE6();
        E2.e = b2.getLatitudeE6();
        a(E2, 300);
        k = System.currentTimeMillis();
        return true;
    }

    private boolean e(Bundle bundle) {
        if (this.g == null) {
            return false;
        }
        return this.g.e(bundle);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0008, code lost:
        r0 = r4.g.d(r5);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean f(android.os.Bundle r5) {
        /*
            r4 = this;
            r0 = 0
            if (r5 != 0) goto L_0x0004
        L_0x0003:
            return r0
        L_0x0004:
            com.baidu.platform.comjni.map.basemap.a r1 = r4.g
            if (r1 == 0) goto L_0x0003
            com.baidu.platform.comjni.map.basemap.a r0 = r4.g
            boolean r0 = r0.d((android.os.Bundle) r5)
            if (r0 == 0) goto L_0x0003
            r4.d((boolean) r0)
            com.baidu.platform.comjni.map.basemap.a r1 = r4.g
            com.baidu.platform.comapi.map.am r2 = r4.y
            long r2 = r2.a
            r1.b((long) r2)
            goto L_0x0003
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comapi.map.e.f(android.os.Bundle):boolean");
    }

    private void g(Bundle bundle) {
        if (bundle.get(com.alipay.sdk.authjs.a.f) != null) {
            Bundle bundle2 = (Bundle) bundle.get(com.alipay.sdk.authjs.a.f);
            int i2 = bundle2.getInt("type");
            if (i2 == h.ground.ordinal()) {
                bundle2.putLong("layer_addr", this.E.a);
            } else if (i2 >= h.arc.ordinal()) {
                bundle2.putLong("layer_addr", this.I.a);
            } else if (i2 == h.popup.ordinal()) {
                bundle2.putLong("layer_addr", this.H.a);
            } else {
                bundle2.putLong("layer_addr", this.G.a);
            }
        } else {
            int i3 = bundle.getInt("type");
            if (i3 == h.ground.ordinal()) {
                bundle.putLong("layer_addr", this.E.a);
            } else if (i3 >= h.arc.ordinal()) {
                bundle.putLong("layer_addr", this.I.a);
            } else if (i3 == h.popup.ordinal()) {
                bundle.putLong("layer_addr", this.H.a);
            } else {
                bundle.putLong("layer_addr", this.G.a);
            }
        }
    }

    public static void k(boolean z2) {
        ar = com.baidu.platform.comjni.map.basemap.a.b();
        if (ar == null || ar.size() == 0) {
            com.baidu.platform.comjni.map.basemap.a.b(0, z2);
            return;
        }
        com.baidu.platform.comjni.map.basemap.a.b(ar.get(0).a, z2);
        for (JNIBaseMap next : ar) {
            next.ClearLayer(next.a, -1);
        }
    }

    /* access modifiers changed from: package-private */
    public void A() {
        if (!this.l && !this.m) {
            this.m = true;
            for (l a2 : this.f) {
                a2.a(E());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void B() {
        this.m = false;
        this.l = false;
        for (l c2 : this.f) {
            c2.c(E());
        }
    }

    public boolean C() {
        if (this.g != null) {
            return this.g.a(this.F.a);
        }
        return false;
    }

    public boolean D() {
        if (this.g != null) {
            return this.g.a(this.aq.a);
        }
        return false;
    }

    public ae E() {
        if (this.g == null) {
            return null;
        }
        Bundle h2 = this.g.h();
        ae aeVar = new ae();
        aeVar.a(h2);
        return aeVar;
    }

    public LatLngBounds F() {
        if (this.g == null) {
            return null;
        }
        Bundle i2 = this.g.i();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        int i3 = i2.getInt("maxCoorx");
        int i4 = i2.getInt("minCoorx");
        builder.include(CoordUtil.mc2ll(new GeoPoint((double) i2.getInt("minCoory"), (double) i3))).include(CoordUtil.mc2ll(new GeoPoint((double) i2.getInt("maxCoory"), (double) i4)));
        return builder.build();
    }

    public int G() {
        return this.P;
    }

    public int H() {
        return this.Q;
    }

    public ae I() {
        if (this.g == null) {
            return null;
        }
        Bundle j2 = this.g.j();
        ae aeVar = new ae();
        aeVar.a(j2);
        return aeVar;
    }

    public double J() {
        return E().m;
    }

    /* access modifiers changed from: package-private */
    public void K() {
        if (!this.l) {
            this.l = true;
            this.m = false;
            for (l a2 : this.f) {
                a2.a(E());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void L() {
        this.l = false;
        if (!this.m) {
            for (l c2 : this.f) {
                c2.c(E());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void M() {
        this.R = 0;
        this.S.e = false;
        this.S.h = 0.0d;
    }

    public Queue<a> N() {
        return this.at;
    }

    public void O() {
        if (!this.at.isEmpty()) {
            a poll = this.at.poll();
            if (poll.e == null) {
                com.baidu.platform.comjni.map.basemap.a.a(poll.a, poll.b, poll.c, poll.d);
            } else if (this.g != null) {
                A();
                this.g.a(poll.e);
            }
        }
    }

    public float a(int i2, int i3, int i4, int i5, int i6, int i7) {
        if (!this.i) {
            return 12.0f;
        }
        if (this.g == null) {
            return 0.0f;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("left", i2);
        bundle.putInt("right", i4);
        bundle.putInt("bottom", i5);
        bundle.putInt("top", i3);
        bundle.putInt("hasHW", 1);
        bundle.putInt("width", i6);
        bundle.putInt("height", i7);
        return this.g.c(bundle);
    }

    /* access modifiers changed from: package-private */
    public int a(int i2, int i3, int i4) {
        if (!this.as) {
            return com.baidu.platform.comjni.map.basemap.a.a(this.h, i2, i3, i4);
        }
        this.at.add(new a(this.h, i2, i3, i4));
        return 0;
    }

    public int a(Bundle bundle, long j2, int i2, Bundle bundle2) {
        if (j2 == this.D.a) {
            bundle.putString("jsondata", this.D.a());
            bundle.putBundle(com.alipay.sdk.authjs.a.f, this.D.b());
            return this.D.g;
        } else if (j2 == this.C.a) {
            bundle.putString("jsondata", this.C.a());
            bundle.putBundle(com.alipay.sdk.authjs.a.f, this.C.b());
            return this.C.g;
        } else if (j2 == this.J.a) {
            bundle.putBundle(com.alipay.sdk.authjs.a.f, this.L.a(bundle2.getInt("x"), bundle2.getInt("y"), bundle2.getInt("zoom")));
            return this.J.g;
        } else if (j2 != this.y.a) {
            return 0;
        } else {
            bundle.putBundle(com.alipay.sdk.authjs.a.f, this.z.a(bundle2.getInt("x"), bundle2.getInt("y"), bundle2.getInt("zoom"), this.A));
            return this.y.g;
        }
    }

    public Point a(GeoPoint geoPoint) {
        return this.M.a(geoPoint);
    }

    /* access modifiers changed from: package-private */
    public void a() {
        this.B = new ArrayList();
        this.am = new f();
        a((d) this.am);
        this.ap = new b();
        a((d) this.ap);
        this.E = new o();
        a((d) this.E);
        this.J = new p();
        a((d) this.J);
        this.K = new a();
        a((d) this.K);
        a((d) new r());
        this.F = new ah();
        a((d) this.F);
        this.aq = new c();
        a((d) this.aq);
        if (this.g != null) {
            this.g.e(false);
        }
        this.I = new n();
        a((d) this.I);
        this.G = new ak();
        a((d) this.G);
        this.D = new g();
        a((d) this.D);
        this.C = new aa();
        a((d) this.C);
        this.H = new s();
        a((d) this.H);
    }

    public void a(float f2, float f3) {
        this.a = f2;
        this.c = f2;
        this.b = f3;
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        this.g = new com.baidu.platform.comjni.map.basemap.a();
        this.g.a(i2);
        this.h = this.g.a();
        a("com.baidu.platform.comapi.wnplatform.walkmap.WNaviBaiduMap", "setId", this.h);
        if (SysOSUtil.getDensityDpi() < 180) {
            this.j = 18;
        } else if (SysOSUtil.getDensityDpi() < 240) {
            this.j = 25;
        } else if (SysOSUtil.getDensityDpi() < 320) {
            this.j = 37;
        } else {
            this.j = 50;
        }
        String moduleFileName = SysOSUtil.getModuleFileName();
        String appSDCardPath = EnvironmentUtilities.getAppSDCardPath();
        String appCachePath = EnvironmentUtilities.getAppCachePath();
        String appSecondCachePath = EnvironmentUtilities.getAppSecondCachePath();
        int mapTmpStgMax = EnvironmentUtilities.getMapTmpStgMax();
        int domTmpStgMax = EnvironmentUtilities.getDomTmpStgMax();
        int itsTmpStgMax = EnvironmentUtilities.getItsTmpStgMax();
        String str = SysOSUtil.getDensityDpi() >= 180 ? "/h/" : "/l/";
        String str2 = moduleFileName + "/cfg";
        String str3 = appSDCardPath + "/vmp";
        String str4 = str2 + "/a/";
        String str5 = str2 + "/a/";
        String str6 = str2 + "/idrres/";
        String str7 = str3 + str;
        String str8 = str3 + str;
        String str9 = appCachePath + "/tmp/";
        String str10 = appSecondCachePath + "/tmp/";
        Activity a2 = a(this.A);
        if (a2 != null) {
            Display defaultDisplay = a2.getWindowManager().getDefaultDisplay();
            this.g.a(str4, str7, str9, str10, str8, str5, this.an, this.ao, str6, defaultDisplay.getWidth(), defaultDisplay.getHeight(), SysOSUtil.getDensityDpi(), mapTmpStgMax, domTmpStgMax, itsTmpStgMax, 0);
            this.g.d();
            return;
        }
        throw new RuntimeException("Please give the right context.");
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3) {
        this.P = i2;
        this.Q = i3;
    }

    public void a(long j2, long j3, long j4, long j5, boolean z2) {
        this.g.a(j2, j3, j4, j5, z2);
    }

    public void a(Bitmap bitmap) {
        Bundle bundle;
        int i2 = 0;
        if (this.g != null) {
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject.put("type", 0);
                jSONObject2.put("x", N);
                jSONObject2.put("y", O);
                jSONObject2.put("hidetime", 1000);
                jSONArray.put(jSONObject2);
                jSONObject.put("data", jSONArray);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            if (bitmap == null) {
                bundle = null;
            } else {
                Bundle bundle2 = new Bundle();
                ArrayList arrayList = new ArrayList();
                ParcelItem parcelItem = new ParcelItem();
                Bundle bundle3 = new Bundle();
                ByteBuffer allocate = ByteBuffer.allocate(bitmap.getWidth() * bitmap.getHeight() * 4);
                bitmap.copyPixelsToBuffer(allocate);
                bundle3.putByteArray("imgdata", allocate.array());
                bundle3.putInt("imgindex", bitmap.hashCode());
                bundle3.putInt("imgH", bitmap.getHeight());
                bundle3.putInt("imgW", bitmap.getWidth());
                bundle3.putInt("hasIcon", 1);
                parcelItem.setBundle(bundle3);
                arrayList.add(parcelItem);
                if (arrayList.size() > 0) {
                    ParcelItem[] parcelItemArr = new ParcelItem[arrayList.size()];
                    while (true) {
                        int i3 = i2;
                        if (i3 >= arrayList.size()) {
                            break;
                        }
                        parcelItemArr[i3] = (ParcelItem) arrayList.get(i3);
                        i2 = i3 + 1;
                    }
                    bundle2.putParcelableArray("icondata", parcelItemArr);
                }
                bundle = bundle2;
            }
            b(jSONObject.toString(), bundle);
            this.g.b(this.D.a);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Handler handler) {
        MessageCenter.registMessage(UIMsg.m_AppUI.MSG_APP_SAVESCREEN, handler);
        MessageCenter.registMessage(39, handler);
        MessageCenter.registMessage(41, handler);
        MessageCenter.registMessage(49, handler);
        MessageCenter.registMessage(UIMsg.m_AppUI.V_WM_VDATAENGINE, handler);
        MessageCenter.registMessage(50, handler);
        MessageCenter.registMessage(999, handler);
        BaseMapCallback.addLayerDataInterface(this.h, this);
    }

    public void a(LatLngBounds latLngBounds) {
        if (latLngBounds != null && this.g != null) {
            LatLng latLng = latLngBounds.northeast;
            LatLng latLng2 = latLngBounds.southwest;
            GeoPoint ll2mc = CoordUtil.ll2mc(latLng);
            GeoPoint ll2mc2 = CoordUtil.ll2mc(latLng2);
            int latitudeE6 = (int) ll2mc2.getLatitudeE6();
            int longitudeE6 = (int) ll2mc2.getLongitudeE6();
            int latitudeE62 = (int) ll2mc.getLatitudeE6();
            Bundle bundle = new Bundle();
            bundle.putInt("maxCoorx", (int) ll2mc.getLongitudeE6());
            bundle.putInt("minCoory", latitudeE6);
            bundle.putInt("minCoorx", longitudeE6);
            bundle.putInt("maxCoory", latitudeE62);
            this.g.b(bundle);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(ac acVar) {
        new ae();
        if (acVar == null) {
            acVar = new ac();
        }
        ae aeVar = acVar.a;
        this.v = acVar.f;
        this.w = acVar.d;
        this.d = acVar.e;
        this.e = acVar.g;
        this.g.a(aeVar.a(this));
        this.g.c(ab.DEFAULT.ordinal());
        this.r = acVar.b;
        if (!acVar.b) {
            this.g.a(this.D.a, false);
        } else {
            N = (int) (SysOSUtil.getDensity() * 40.0f);
            O = (int) (SysOSUtil.getDensity() * 40.0f);
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("x", N);
                jSONObject2.put("y", O);
                jSONObject2.put("hidetime", 1000);
                jSONArray.put(jSONObject2);
                jSONObject.put("data", jSONArray);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            this.D.a(jSONObject.toString());
            this.g.a(this.D.a, true);
        }
        int i2 = acVar.c;
        if (i2 == 2) {
            a(true);
        }
        if (i2 == 3) {
            this.g.a(this.am.a, false);
            this.g.a(this.aq.a, false);
            this.g.a(this.F.a, false);
            this.g.e(false);
        }
    }

    public void a(ae aeVar) {
        if (this.g != null && aeVar != null) {
            Bundle a2 = aeVar.a(this);
            a2.putInt("animation", 0);
            a2.putInt("animatime", 0);
            this.g.a(a2);
        }
    }

    public void a(ae aeVar, int i2) {
        if (this.g != null) {
            Bundle a2 = aeVar.a(this);
            a2.putInt("animation", 1);
            a2.putInt("animatime", i2);
            if (this.as) {
                this.at.add(new a(a2));
                return;
            }
            A();
            this.g.a(a2);
        }
    }

    public void a(al alVar) {
        this.z = alVar;
    }

    /* access modifiers changed from: package-private */
    public void a(d dVar) {
        if (this.g != null) {
            dVar.a = this.g.a(dVar.c, dVar.d, dVar.b);
            this.B.add(dVar);
        }
    }

    public void a(l lVar) {
        this.f.add(lVar);
    }

    public void a(q qVar) {
        this.L = qVar;
    }

    public void a(String str, Bundle bundle) {
        if (this.g != null) {
            this.C.a(str);
            this.C.a(bundle);
            this.g.b(this.C.a);
        }
    }

    public void a(List<Bundle> list) {
        if (this.g != null && list != null) {
            int size = list.size();
            Bundle[] bundleArr = new Bundle[list.size()];
            for (int i2 = 0; i2 < size; i2++) {
                g(list.get(i2));
                bundleArr[i2] = list.get(i2);
            }
            this.g.a(bundleArr);
        }
    }

    public void a(boolean z2) {
        if (this.g != null) {
            if (!this.g.a(this.am.a)) {
                this.g.a(this.am.a, true);
            }
            this.q = z2;
            P();
            this.g.a(this.q);
        }
    }

    public boolean a(float f2, float f3, float f4, float f5) {
        float f6 = ((float) this.Q) - f3;
        float f7 = ((float) this.Q) - f5;
        if (this.S.e) {
            if (this.R == 0) {
                if ((this.S.c - f6 <= 0.0f || this.S.d - f7 <= 0.0f) && (this.S.c - f6 >= 0.0f || this.S.d - f7 >= 0.0f)) {
                    this.R = 2;
                } else {
                    double atan2 = Math.atan2((double) (f7 - f6), (double) (f4 - f2)) - Math.atan2((double) (this.S.d - this.S.c), (double) (this.S.b - this.S.a));
                    double sqrt = Math.sqrt((double) (((f4 - f2) * (f4 - f2)) + ((f7 - f6) * (f7 - f6)))) / this.S.h;
                    int log = (int) ((Math.log(sqrt) / Math.log(2.0d)) * 10000.0d);
                    int i2 = (int) ((atan2 * 180.0d) / 3.1416d);
                    if ((sqrt <= 0.0d || (log <= 3000 && log >= -3000)) && Math.abs(i2) < 10) {
                        this.R = 1;
                    } else {
                        this.R = 2;
                    }
                }
                if (this.R == 0) {
                    return true;
                }
            }
            if (this.R != 1 || !this.v) {
                if (this.R == 2 || this.R == 4 || this.R == 3) {
                    double atan22 = Math.atan2((double) (f7 - f6), (double) (f4 - f2)) - Math.atan2((double) (this.S.d - this.S.c), (double) (this.S.b - this.S.a));
                    double sqrt2 = Math.sqrt((double) (((f4 - f2) * (f4 - f2)) + ((f7 - f6) * (f7 - f6)))) / this.S.h;
                    int log2 = (int) ((Math.log(sqrt2) / Math.log(2.0d)) * 10000.0d);
                    double atan23 = Math.atan2((double) (this.S.g - this.S.c), (double) (this.S.f - this.S.a));
                    double sqrt3 = Math.sqrt((double) (((this.S.f - this.S.a) * (this.S.f - this.S.a)) + ((this.S.g - this.S.c) * (this.S.g - this.S.c))));
                    float cos = (float) ((Math.cos(atan23 + atan22) * sqrt3 * sqrt2) + ((double) f2));
                    float sin = (float) ((Math.sin(atan23 + atan22) * sqrt3 * sqrt2) + ((double) f6));
                    int i3 = (int) ((atan22 * 180.0d) / 3.1416d);
                    if (sqrt2 > 0.0d && (3 == this.R || (Math.abs(log2) > 2000 && 2 == this.R))) {
                        this.R = 3;
                        float f8 = E().a;
                        if (this.e) {
                            if (sqrt2 > 1.0d) {
                                if (f8 >= this.a) {
                                    return false;
                                }
                                K();
                                a((int) UIMsg.k_event.V_WM_ROTATE, 3, log2);
                            } else if (f8 <= this.b) {
                                return false;
                            } else {
                                K();
                                a((int) UIMsg.k_event.V_WM_ROTATE, 3, log2);
                            }
                        }
                    } else if (i3 != 0 && (4 == this.R || (Math.abs(i3) > 10 && 2 == this.R))) {
                        this.R = 4;
                        if (this.w) {
                            BaiduMap.mapStatusReason |= 1;
                            K();
                            a((int) UIMsg.k_event.V_WM_ROTATE, 1, i3);
                        }
                    }
                    this.S.f = cos;
                    this.S.g = sin;
                }
            } else if (this.S.c - f6 > 0.0f && this.S.d - f7 > 0.0f) {
                K();
                a(1, 83, 0);
            } else if (this.S.c - f6 < 0.0f && this.S.d - f7 < 0.0f) {
                K();
                a(1, 87, 0);
            }
        }
        if (2 != this.R) {
            this.S.c = f6;
            this.S.d = f7;
            this.S.a = f2;
            this.S.b = f4;
        }
        if (!this.S.e) {
            this.S.f = (float) (this.P / 2);
            this.S.g = (float) (this.Q / 2);
            this.S.e = true;
            if (0.0d == this.S.h) {
                this.S.h = Math.sqrt((double) (((this.S.b - this.S.a) * (this.S.b - this.S.a)) + ((this.S.d - this.S.c) * (this.S.d - this.S.c))));
            }
        }
        return true;
    }

    public boolean a(long j2) {
        for (d dVar : this.B) {
            if (dVar.a == j2) {
                return true;
            }
        }
        return false;
    }

    public boolean a(Point point) {
        if (point == null || this.g == null || point.x < 0 || point.y < 0) {
            return false;
        }
        N = point.x;
        O = point.y;
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("x", N);
            jSONObject2.put("y", O);
            jSONObject2.put("hidetime", 1000);
            jSONArray.put(jSONObject2);
            jSONObject.put("data", jSONArray);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        this.D.a(jSONObject.toString());
        this.g.b(this.D.a);
        return true;
    }

    public boolean a(Bundle bundle) {
        if (this.g == null) {
            return false;
        }
        this.y = new am();
        long a2 = this.g.a(this.y.c, this.y.d, this.y.b);
        if (a2 == 0) {
            return false;
        }
        this.y.a = a2;
        this.B.add(this.y);
        bundle.putLong("sdktileaddr", a2);
        return e(bundle) && f(bundle);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0036, code lost:
        if (c((int) r21.getX(1), (int) r21.getY(1)) == false) goto L_0x0038;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(android.view.MotionEvent r21) {
        /*
            r20 = this;
            r2 = 1
            int r3 = r21.getPointerCount()
            r4 = 2
            if (r3 != r4) goto L_0x0039
            r4 = 0
            r0 = r21
            float r4 = r0.getX(r4)
            int r4 = (int) r4
            r5 = 0
            r0 = r21
            float r5 = r0.getY(r5)
            int r5 = (int) r5
            r0 = r20
            boolean r4 = r0.c((int) r4, (int) r5)
            if (r4 == 0) goto L_0x0038
            r4 = 1
            r0 = r21
            float r4 = r0.getX(r4)
            int r4 = (int) r4
            r5 = 1
            r0 = r21
            float r5 = r0.getY(r5)
            int r5 = (int) r5
            r0 = r20
            boolean r4 = r0.c((int) r4, (int) r5)
            if (r4 != 0) goto L_0x0039
        L_0x0038:
            r3 = 1
        L_0x0039:
            r4 = 2
            if (r3 != r4) goto L_0x0506
            r0 = r20
            int r2 = r0.Q
            float r2 = (float) r2
            r3 = 0
            r0 = r21
            float r3 = r0.getY(r3)
            float r4 = r2 - r3
            r0 = r20
            int r2 = r0.Q
            float r2 = (float) r2
            r3 = 1
            r0 = r21
            float r3 = r0.getY(r3)
            float r5 = r2 - r3
            r2 = 0
            r0 = r21
            float r6 = r0.getX(r2)
            r2 = 1
            r0 = r21
            float r7 = r0.getX(r2)
            int r2 = r21.getAction()
            switch(r2) {
                case 5: goto L_0x01a1;
                case 6: goto L_0x01c9;
                case 261: goto L_0x01b5;
                case 262: goto L_0x01dd;
                default: goto L_0x006d;
            }
        L_0x006d:
            r0 = r20
            android.view.VelocityTracker r2 = r0.T
            if (r2 != 0) goto L_0x007b
            android.view.VelocityTracker r2 = android.view.VelocityTracker.obtain()
            r0 = r20
            r0.T = r2
        L_0x007b:
            r0 = r20
            android.view.VelocityTracker r2 = r0.T
            r0 = r21
            r2.addMovement(r0)
            int r2 = android.view.ViewConfiguration.getMinimumFlingVelocity()
            int r3 = android.view.ViewConfiguration.getMaximumFlingVelocity()
            r0 = r20
            android.view.VelocityTracker r8 = r0.T
            r9 = 1000(0x3e8, float:1.401E-42)
            float r3 = (float) r3
            r8.computeCurrentVelocity(r9, r3)
            r0 = r20
            android.view.VelocityTracker r3 = r0.T
            r8 = 1
            float r3 = r3.getXVelocity(r8)
            r0 = r20
            android.view.VelocityTracker r8 = r0.T
            r9 = 1
            float r8 = r8.getYVelocity(r9)
            r0 = r20
            android.view.VelocityTracker r9 = r0.T
            r10 = 2
            float r9 = r9.getXVelocity(r10)
            r0 = r20
            android.view.VelocityTracker r10 = r0.T
            r11 = 2
            float r10 = r10.getYVelocity(r11)
            float r3 = java.lang.Math.abs(r3)
            float r11 = (float) r2
            int r3 = (r3 > r11 ? 1 : (r3 == r11 ? 0 : -1))
            if (r3 > 0) goto L_0x00de
            float r3 = java.lang.Math.abs(r8)
            float r8 = (float) r2
            int r3 = (r3 > r8 ? 1 : (r3 == r8 ? 0 : -1))
            if (r3 > 0) goto L_0x00de
            float r3 = java.lang.Math.abs(r9)
            float r8 = (float) r2
            int r3 = (r3 > r8 ? 1 : (r3 == r8 ? 0 : -1))
            if (r3 > 0) goto L_0x00de
            float r3 = java.lang.Math.abs(r10)
            float r2 = (float) r2
            int r2 = (r3 > r2 ? 1 : (r3 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x0497
        L_0x00de:
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            boolean r2 = r2.e
            r3 = 1
            if (r2 != r3) goto L_0x0234
            r0 = r20
            int r2 = r0.R
            if (r2 != 0) goto L_0x01fd
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            float r2 = r2.c
            float r2 = r2 - r4
            r3 = 0
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L_0x0105
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            float r2 = r2.d
            float r2 = r2 - r5
            r3 = 0
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 > 0) goto L_0x011d
        L_0x0105:
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            float r2 = r2.c
            float r2 = r2 - r4
            r3 = 0
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 >= 0) goto L_0x01f7
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            float r2 = r2.d
            float r2 = r2 - r5
            r3 = 0
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 >= 0) goto L_0x01f7
        L_0x011d:
            float r2 = r5 - r4
            double r2 = (double) r2
            float r8 = r7 - r6
            double r8 = (double) r8
            double r2 = java.lang.Math.atan2(r2, r8)
            r0 = r20
            com.baidu.platform.comapi.map.j$a r8 = r0.S
            float r8 = r8.d
            r0 = r20
            com.baidu.platform.comapi.map.j$a r9 = r0.S
            float r9 = r9.c
            float r8 = r8 - r9
            double r8 = (double) r8
            r0 = r20
            com.baidu.platform.comapi.map.j$a r10 = r0.S
            float r10 = r10.b
            r0 = r20
            com.baidu.platform.comapi.map.j$a r11 = r0.S
            float r11 = r11.a
            float r10 = r10 - r11
            double r10 = (double) r10
            double r8 = java.lang.Math.atan2(r8, r10)
            double r2 = r2 - r8
            float r8 = r7 - r6
            float r9 = r7 - r6
            float r8 = r8 * r9
            float r9 = r5 - r4
            float r10 = r5 - r4
            float r9 = r9 * r10
            float r8 = r8 + r9
            double r8 = (double) r8
            double r8 = java.lang.Math.sqrt(r8)
            r0 = r20
            com.baidu.platform.comapi.map.j$a r10 = r0.S
            double r10 = r10.h
            double r8 = r8 / r10
            double r10 = java.lang.Math.log(r8)
            r12 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r12 = java.lang.Math.log(r12)
            double r10 = r10 / r12
            r12 = 4666723172467343360(0x40c3880000000000, double:10000.0)
            double r10 = r10 * r12
            int r10 = (int) r10
            r12 = 4640537203540230144(0x4066800000000000, double:180.0)
            double r2 = r2 * r12
            r12 = 4614256673094690983(0x400921ff2e48e8a7, double:3.1416)
            double r2 = r2 / r12
            int r2 = (int) r2
            r12 = 0
            int r3 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
            if (r3 <= 0) goto L_0x018c
            r3 = 3000(0xbb8, float:4.204E-42)
            if (r10 > r3) goto L_0x0194
            r3 = -3000(0xfffffffffffff448, float:NaN)
            if (r10 < r3) goto L_0x0194
        L_0x018c:
            int r2 = java.lang.Math.abs(r2)
            r3 = 10
            if (r2 < r3) goto L_0x01f1
        L_0x0194:
            r2 = 2
            r0 = r20
            r0.R = r2
        L_0x0199:
            r0 = r20
            int r2 = r0.R
            if (r2 != 0) goto L_0x01fd
            r2 = 1
        L_0x01a0:
            return r2
        L_0x01a1:
            long r2 = r21.getEventTime()
            r0 = r20
            r0.V = r2
            r0 = r20
            int r2 = r0.Y
            int r2 = r2 + -1
            r0 = r20
            r0.Y = r2
            goto L_0x006d
        L_0x01b5:
            long r2 = r21.getEventTime()
            r0 = r20
            r0.U = r2
            r0 = r20
            int r2 = r0.Y
            int r2 = r2 + -1
            r0 = r20
            r0.Y = r2
            goto L_0x006d
        L_0x01c9:
            long r2 = r21.getEventTime()
            r0 = r20
            r0.X = r2
            r0 = r20
            int r2 = r0.Y
            int r2 = r2 + 1
            r0 = r20
            r0.Y = r2
            goto L_0x006d
        L_0x01dd:
            long r2 = r21.getEventTime()
            r0 = r20
            r0.W = r2
            r0 = r20
            int r2 = r0.Y
            int r2 = r2 + 1
            r0 = r20
            r0.Y = r2
            goto L_0x006d
        L_0x01f1:
            r2 = 1
            r0 = r20
            r0.R = r2
            goto L_0x0199
        L_0x01f7:
            r2 = 2
            r0 = r20
            r0.R = r2
            goto L_0x0199
        L_0x01fd:
            r0 = r20
            int r2 = r0.R
            r3 = 1
            if (r2 != r3) goto L_0x02f9
            r0 = r20
            boolean r2 = r0.v
            if (r2 == 0) goto L_0x02f9
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            float r2 = r2.c
            float r2 = r2 - r4
            r3 = 0
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L_0x02cd
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            float r2 = r2.d
            float r2 = r2 - r5
            r3 = 0
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L_0x02cd
            int r2 = com.baidu.mapapi.map.BaiduMap.mapStatusReason
            r2 = r2 | 1
            com.baidu.mapapi.map.BaiduMap.mapStatusReason = r2
            r20.K()
            r2 = 1
            r3 = 83
            r8 = 0
            r0 = r20
            r0.a((int) r2, (int) r3, (int) r8)
        L_0x0234:
            r2 = 2
            r0 = r20
            int r3 = r0.R
            if (r2 == r3) goto L_0x0253
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            r2.c = r4
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            r2.d = r5
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            r2.a = r6
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            r2.b = r7
        L_0x0253:
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            boolean r2 = r2.e
            if (r2 != 0) goto L_0x02ca
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            r0 = r20
            int r3 = r0.P
            int r3 = r3 / 2
            float r3 = (float) r3
            r2.f = r3
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            r0 = r20
            int r3 = r0.Q
            int r3 = r3 / 2
            float r3 = (float) r3
            r2.g = r3
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            r3 = 1
            r2.e = r3
            r2 = 0
            r0 = r20
            com.baidu.platform.comapi.map.j$a r4 = r0.S
            double r4 = r4.h
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 != 0) goto L_0x02ca
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            float r2 = r2.b
            r0 = r20
            com.baidu.platform.comapi.map.j$a r3 = r0.S
            float r3 = r3.a
            float r2 = r2 - r3
            r0 = r20
            com.baidu.platform.comapi.map.j$a r3 = r0.S
            float r3 = r3.b
            r0 = r20
            com.baidu.platform.comapi.map.j$a r4 = r0.S
            float r4 = r4.a
            float r3 = r3 - r4
            float r2 = r2 * r3
            r0 = r20
            com.baidu.platform.comapi.map.j$a r3 = r0.S
            float r3 = r3.d
            r0 = r20
            com.baidu.platform.comapi.map.j$a r4 = r0.S
            float r4 = r4.c
            float r3 = r3 - r4
            r0 = r20
            com.baidu.platform.comapi.map.j$a r4 = r0.S
            float r4 = r4.d
            r0 = r20
            com.baidu.platform.comapi.map.j$a r5 = r0.S
            float r5 = r5.c
            float r4 = r4 - r5
            float r3 = r3 * r4
            float r2 = r2 + r3
            double r2 = (double) r2
            double r2 = java.lang.Math.sqrt(r2)
            r0 = r20
            com.baidu.platform.comapi.map.j$a r4 = r0.S
            r4.h = r2
        L_0x02ca:
            r2 = 1
            goto L_0x01a0
        L_0x02cd:
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            float r2 = r2.c
            float r2 = r2 - r4
            r3 = 0
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 >= 0) goto L_0x0234
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            float r2 = r2.d
            float r2 = r2 - r5
            r3 = 0
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 >= 0) goto L_0x0234
            int r2 = com.baidu.mapapi.map.BaiduMap.mapStatusReason
            r2 = r2 | 1
            com.baidu.mapapi.map.BaiduMap.mapStatusReason = r2
            r20.K()
            r2 = 1
            r3 = 87
            r8 = 0
            r0 = r20
            r0.a((int) r2, (int) r3, (int) r8)
            goto L_0x0234
        L_0x02f9:
            r0 = r20
            int r2 = r0.R
            r3 = 2
            if (r2 == r3) goto L_0x030e
            r0 = r20
            int r2 = r0.R
            r3 = 4
            if (r2 == r3) goto L_0x030e
            r0 = r20
            int r2 = r0.R
            r3 = 3
            if (r2 != r3) goto L_0x0234
        L_0x030e:
            float r2 = r5 - r4
            double r2 = (double) r2
            float r8 = r7 - r6
            double r8 = (double) r8
            double r2 = java.lang.Math.atan2(r2, r8)
            r0 = r20
            com.baidu.platform.comapi.map.j$a r8 = r0.S
            float r8 = r8.d
            r0 = r20
            com.baidu.platform.comapi.map.j$a r9 = r0.S
            float r9 = r9.c
            float r8 = r8 - r9
            double r8 = (double) r8
            r0 = r20
            com.baidu.platform.comapi.map.j$a r10 = r0.S
            float r10 = r10.b
            r0 = r20
            com.baidu.platform.comapi.map.j$a r11 = r0.S
            float r11 = r11.a
            float r10 = r10 - r11
            double r10 = (double) r10
            double r8 = java.lang.Math.atan2(r8, r10)
            double r2 = r2 - r8
            float r8 = r7 - r6
            float r9 = r7 - r6
            float r8 = r8 * r9
            float r9 = r5 - r4
            float r10 = r5 - r4
            float r9 = r9 * r10
            float r8 = r8 + r9
            double r8 = (double) r8
            double r8 = java.lang.Math.sqrt(r8)
            r0 = r20
            com.baidu.platform.comapi.map.j$a r10 = r0.S
            double r10 = r10.h
            double r8 = r8 / r10
            double r10 = java.lang.Math.log(r8)
            r12 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r12 = java.lang.Math.log(r12)
            double r10 = r10 / r12
            r12 = 4666723172467343360(0x40c3880000000000, double:10000.0)
            double r10 = r10 * r12
            int r10 = (int) r10
            r0 = r20
            com.baidu.platform.comapi.map.j$a r11 = r0.S
            float r11 = r11.g
            r0 = r20
            com.baidu.platform.comapi.map.j$a r12 = r0.S
            float r12 = r12.c
            float r11 = r11 - r12
            double r12 = (double) r11
            r0 = r20
            com.baidu.platform.comapi.map.j$a r11 = r0.S
            float r11 = r11.f
            r0 = r20
            com.baidu.platform.comapi.map.j$a r14 = r0.S
            float r14 = r14.a
            float r11 = r11 - r14
            double r14 = (double) r11
            double r12 = java.lang.Math.atan2(r12, r14)
            r0 = r20
            com.baidu.platform.comapi.map.j$a r11 = r0.S
            float r11 = r11.f
            r0 = r20
            com.baidu.platform.comapi.map.j$a r14 = r0.S
            float r14 = r14.a
            float r11 = r11 - r14
            r0 = r20
            com.baidu.platform.comapi.map.j$a r14 = r0.S
            float r14 = r14.f
            r0 = r20
            com.baidu.platform.comapi.map.j$a r15 = r0.S
            float r15 = r15.a
            float r14 = r14 - r15
            float r11 = r11 * r14
            r0 = r20
            com.baidu.platform.comapi.map.j$a r14 = r0.S
            float r14 = r14.g
            r0 = r20
            com.baidu.platform.comapi.map.j$a r15 = r0.S
            float r15 = r15.c
            float r14 = r14 - r15
            r0 = r20
            com.baidu.platform.comapi.map.j$a r15 = r0.S
            float r15 = r15.g
            r0 = r20
            com.baidu.platform.comapi.map.j$a r0 = r0.S
            r16 = r0
            r0 = r16
            float r0 = r0.c
            r16 = r0
            float r15 = r15 - r16
            float r14 = r14 * r15
            float r11 = r11 + r14
            double r14 = (double) r11
            double r14 = java.lang.Math.sqrt(r14)
            double r16 = r12 + r2
            double r16 = java.lang.Math.cos(r16)
            double r16 = r16 * r14
            double r16 = r16 * r8
            double r0 = (double) r6
            r18 = r0
            double r16 = r16 + r18
            r0 = r16
            float r11 = (float) r0
            double r12 = r12 + r2
            double r12 = java.lang.Math.sin(r12)
            double r12 = r12 * r14
            double r12 = r12 * r8
            double r14 = (double) r4
            double r12 = r12 + r14
            float r12 = (float) r12
            r14 = 4640537203540230144(0x4066800000000000, double:180.0)
            double r2 = r2 * r14
            r14 = 4614256673094690983(0x400921ff2e48e8a7, double:3.1416)
            double r2 = r2 / r14
            int r2 = (int) r2
            r14 = 0
            int r3 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1))
            if (r3 <= 0) goto L_0x0468
            r3 = 3
            r0 = r20
            int r13 = r0.R
            if (r3 == r13) goto L_0x040a
            int r3 = java.lang.Math.abs(r10)
            r13 = 2000(0x7d0, float:2.803E-42)
            if (r3 <= r13) goto L_0x0468
            r3 = 2
            r0 = r20
            int r13 = r0.R
            if (r3 != r13) goto L_0x0468
        L_0x040a:
            r2 = 3
            r0 = r20
            r0.R = r2
            com.baidu.platform.comapi.map.ae r2 = r20.E()
            float r2 = r2.a
            r0 = r20
            boolean r3 = r0.e
            if (r3 == 0) goto L_0x043d
            r14 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            int r3 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1))
            if (r3 <= 0) goto L_0x044b
            r0 = r20
            float r3 = r0.a
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 < 0) goto L_0x042c
            r2 = 0
            goto L_0x01a0
        L_0x042c:
            int r2 = com.baidu.mapapi.map.BaiduMap.mapStatusReason
            r2 = r2 | 1
            com.baidu.mapapi.map.BaiduMap.mapStatusReason = r2
            r20.K()
            r2 = 8193(0x2001, float:1.1481E-41)
            r3 = 3
            r0 = r20
            r0.a((int) r2, (int) r3, (int) r10)
        L_0x043d:
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            r2.f = r11
            r0 = r20
            com.baidu.platform.comapi.map.j$a r2 = r0.S
            r2.g = r12
            goto L_0x0234
        L_0x044b:
            r0 = r20
            float r3 = r0.b
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 > 0) goto L_0x0456
            r2 = 0
            goto L_0x01a0
        L_0x0456:
            int r2 = com.baidu.mapapi.map.BaiduMap.mapStatusReason
            r2 = r2 | 1
            com.baidu.mapapi.map.BaiduMap.mapStatusReason = r2
            r20.K()
            r2 = 8193(0x2001, float:1.1481E-41)
            r3 = 3
            r0 = r20
            r0.a((int) r2, (int) r3, (int) r10)
            goto L_0x043d
        L_0x0468:
            if (r2 == 0) goto L_0x043d
            r3 = 4
            r0 = r20
            int r8 = r0.R
            if (r3 == r8) goto L_0x0480
            int r3 = java.lang.Math.abs(r2)
            r8 = 10
            if (r3 <= r8) goto L_0x043d
            r3 = 2
            r0 = r20
            int r8 = r0.R
            if (r3 != r8) goto L_0x043d
        L_0x0480:
            r3 = 4
            r0 = r20
            r0.R = r3
            r0 = r20
            boolean r3 = r0.w
            if (r3 == 0) goto L_0x043d
            r20.K()
            r3 = 8193(0x2001, float:1.1481E-41)
            r8 = 1
            r0 = r20
            r0.a((int) r3, (int) r8, (int) r2)
            goto L_0x043d
        L_0x0497:
            r0 = r20
            int r2 = r0.R
            if (r2 != 0) goto L_0x0234
            r0 = r20
            int r2 = r0.Y
            if (r2 != 0) goto L_0x0234
            r0 = r20
            long r2 = r0.W
            r0 = r20
            long r8 = r0.X
            int r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r2 <= 0) goto L_0x04fc
            r0 = r20
            long r2 = r0.W
        L_0x04b3:
            r0 = r20
            r0.W = r2
            r0 = r20
            long r2 = r0.U
            r0 = r20
            long r8 = r0.V
            int r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r2 >= 0) goto L_0x0501
            r0 = r20
            long r2 = r0.V
        L_0x04c7:
            r0 = r20
            r0.U = r2
            r0 = r20
            long r2 = r0.W
            r0 = r20
            long r8 = r0.U
            long r2 = r2 - r8
            r8 = 200(0xc8, double:9.9E-322)
            int r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r2 >= 0) goto L_0x0234
            r0 = r20
            boolean r2 = r0.e
            if (r2 == 0) goto L_0x0234
            com.baidu.platform.comapi.map.ae r2 = r20.E()
            if (r2 == 0) goto L_0x0234
            float r3 = r2.a
            r8 = 1065353216(0x3f800000, float:1.0)
            float r3 = r3 - r8
            r2.a = r3
            int r3 = com.baidu.mapapi.map.BaiduMap.mapStatusReason
            r3 = r3 | 1
            com.baidu.mapapi.map.BaiduMap.mapStatusReason = r3
            r3 = 300(0x12c, float:4.2E-43)
            r0 = r20
            r0.a((com.baidu.platform.comapi.map.ae) r2, (int) r3)
            goto L_0x0234
        L_0x04fc:
            r0 = r20
            long r2 = r0.X
            goto L_0x04b3
        L_0x0501:
            r0 = r20
            long r2 = r0.U
            goto L_0x04c7
        L_0x0506:
            int r3 = r21.getAction()
            switch(r3) {
                case 0: goto L_0x0510;
                case 1: goto L_0x0515;
                case 2: goto L_0x051b;
                default: goto L_0x050d;
            }
        L_0x050d:
            r2 = 0
            goto L_0x01a0
        L_0x0510:
            r20.b((android.view.MotionEvent) r21)
            goto L_0x01a0
        L_0x0515:
            boolean r2 = r20.d((android.view.MotionEvent) r21)
            goto L_0x01a0
        L_0x051b:
            r20.c((android.view.MotionEvent) r21)
            goto L_0x01a0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comapi.map.e.a(android.view.MotionEvent):boolean");
    }

    public boolean a(String str, String str2) {
        return this.g.a(str, str2);
    }

    public GeoPoint b(int i2, int i3) {
        return this.M.a(i2, i3);
    }

    /* access modifiers changed from: package-private */
    public void b(float f2, float f3) {
        if (!this.S.e) {
            this.ad = System.currentTimeMillis();
            if (this.ad - this.ac >= 400) {
                this.ac = this.ad;
            } else if (Math.abs(f2 - this.Z) >= 120.0f || Math.abs(f3 - this.aa) >= 120.0f) {
                this.ac = this.ad;
            } else {
                this.ac = 0;
                this.ae = true;
            }
            this.Z = f2;
            this.aa = f3;
            a(4, 0, ((int) f2) | (((int) f3) << 16));
            this.ab = true;
        }
    }

    /* access modifiers changed from: package-private */
    public void b(int i2) {
        if (this.g != null) {
            this.g.b(i2);
            this.g = null;
        }
    }

    public void b(Bundle bundle) {
        if (this.g != null) {
            g(bundle);
            this.g.f(bundle);
        }
    }

    /* access modifiers changed from: package-private */
    public void b(Handler handler) {
        MessageCenter.unregistMessage(UIMsg.m_AppUI.MSG_APP_SAVESCREEN, handler);
        MessageCenter.unregistMessage(41, handler);
        MessageCenter.unregistMessage(49, handler);
        MessageCenter.unregistMessage(39, handler);
        MessageCenter.unregistMessage(UIMsg.m_AppUI.V_WM_VDATAENGINE, handler);
        MessageCenter.unregistMessage(50, handler);
        MessageCenter.unregistMessage(999, handler);
        BaseMapCallback.removeLayerDataInterface(this.h);
    }

    /* access modifiers changed from: package-private */
    public void b(MotionEvent motionEvent) {
        if (!this.S.e) {
            this.ad = motionEvent.getDownTime();
            if (this.ad - this.ac >= 400) {
                this.ac = this.ad;
            } else if (Math.abs(motionEvent.getX() - this.Z) >= 120.0f || Math.abs(motionEvent.getY() - this.aa) >= 120.0f) {
                this.ac = this.ad;
            } else {
                this.ac = 0;
            }
            this.Z = motionEvent.getX();
            this.aa = motionEvent.getY();
            a(4, 0, ((int) motionEvent.getX()) | (((int) motionEvent.getY()) << 16));
            this.ab = true;
        }
    }

    public void b(String str, Bundle bundle) {
        if (this.g != null) {
            this.D.a(str);
            this.D.a(bundle);
            this.g.b(this.D.a);
        }
    }

    public void b(boolean z2) {
        this.x = z2;
    }

    public boolean b() {
        return this.x;
    }

    public void c() {
        if (this.g != null) {
            for (d dVar : this.B) {
                this.g.a(dVar.a, false);
            }
        }
    }

    public void c(Bundle bundle) {
        if (this.g != null) {
            g(bundle);
            this.g.g(bundle);
        }
    }

    public void c(boolean z2) {
        if (this.g != null) {
            this.g.a(this.D.a, z2);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean c(float f2, float f3) {
        if (this.S.e) {
            return true;
        }
        if (System.currentTimeMillis() - k < 300) {
            return true;
        }
        if (this.n) {
            for (l d2 : this.f) {
                d2.d(b((int) f2, (int) f3));
            }
            return true;
        }
        float abs = Math.abs(f2 - this.Z);
        float abs2 = Math.abs(f3 - this.aa);
        float density = (float) (((double) SysOSUtil.getDensity()) > 1.5d ? ((double) SysOSUtil.getDensity()) * 1.5d : (double) SysOSUtil.getDensity());
        if (this.ab && abs / density <= 3.0f && abs2 / density <= 3.0f) {
            return true;
        }
        this.ab = false;
        int i2 = (int) f2;
        int i3 = (int) f3;
        if (i2 < 0) {
            i2 = 0;
        }
        if (i3 < 0) {
            i3 = 0;
        }
        if (!this.d) {
            return false;
        }
        this.ag = this.ai;
        this.ah = this.aj;
        this.ai = f2;
        this.aj = f3;
        this.ak = this.al;
        this.al = System.currentTimeMillis();
        this.af = true;
        K();
        a(3, 0, (i3 << 16) | i2);
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean c(int i2, int i3) {
        return i2 >= 0 && i2 <= this.P + 0 && i3 >= 0 && i3 <= this.Q + 0;
    }

    /* access modifiers changed from: package-private */
    public boolean c(MotionEvent motionEvent) {
        if (this.S.e) {
            return true;
        }
        if (System.currentTimeMillis() - k < 300) {
            return true;
        }
        if (this.n) {
            for (l d2 : this.f) {
                d2.d(b((int) motionEvent.getX(), (int) motionEvent.getY()));
            }
            return true;
        }
        float abs = Math.abs(motionEvent.getX() - this.Z);
        float abs2 = Math.abs(motionEvent.getY() - this.aa);
        float density = (float) (((double) SysOSUtil.getDensity()) > 1.5d ? ((double) SysOSUtil.getDensity()) * 1.5d : (double) SysOSUtil.getDensity());
        if (this.ab && abs / density <= 3.0f && abs2 / density <= 3.0f) {
            return true;
        }
        this.ab = false;
        int x2 = (int) motionEvent.getX();
        int y2 = (int) motionEvent.getY();
        if (x2 < 0) {
            x2 = 0;
        }
        if (y2 < 0) {
            y2 = 0;
        }
        if (!this.d) {
            return false;
        }
        BaiduMap.mapStatusReason |= 1;
        K();
        a(3, 0, (y2 << 16) | x2);
        return false;
    }

    public void d() {
        if (this.g != null) {
            for (d next : this.B) {
                if ((next instanceof aa) || (next instanceof a) || (next instanceof p)) {
                    this.g.a(next.a, false);
                } else {
                    this.g.a(next.a, true);
                }
            }
            this.g.c(false);
        }
    }

    public void d(Bundle bundle) {
        if (this.g != null) {
            g(bundle);
            this.g.h(bundle);
        }
    }

    public void d(boolean z2) {
        if (this.g != null) {
            this.g.a(this.y.a, z2);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean d(float f2, float f3) {
        if (this.n) {
            for (l e2 : this.f) {
                e2.e(b((int) f2, (int) f3));
            }
            this.n = false;
            return true;
        }
        if (!this.S.e) {
            if (this.ae) {
                return e(f2, f3);
            }
            if (this.af) {
                return Q();
            }
            if (System.currentTimeMillis() - this.ad < 400 && Math.abs(f2 - this.Z) < 10.0f && Math.abs(f3 - this.aa) < 10.0f) {
                M();
                return true;
            }
        }
        M();
        int i2 = (int) f2;
        int i3 = (int) f3;
        if (i2 < 0) {
            i2 = 0;
        }
        if (i3 < 0) {
            i3 = 0;
        }
        a(5, 0, (i3 << 16) | i2);
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean d(MotionEvent motionEvent) {
        if (this.n) {
            for (l e2 : this.f) {
                e2.e(b((int) motionEvent.getX(), (int) motionEvent.getY()));
            }
            this.n = false;
            return true;
        }
        boolean z2 = !this.S.e && motionEvent.getEventTime() - this.ad < 400 && Math.abs(motionEvent.getX() - this.Z) < 10.0f && Math.abs(motionEvent.getY() - this.aa) < 10.0f;
        M();
        int x2 = (int) motionEvent.getX();
        int y2 = (int) motionEvent.getY();
        if (z2) {
            return false;
        }
        if (x2 < 0) {
            x2 = 0;
        }
        a(5, 0, ((y2 < 0 ? 0 : y2) << 16) | x2);
        return true;
    }

    public void e(boolean z2) {
        if (this.g != null) {
            this.g.a(this.am.a, z2);
        }
    }

    public boolean e() {
        if (this.y == null || this.g == null) {
            return false;
        }
        return this.g.c(this.y.a);
    }

    /* access modifiers changed from: package-private */
    public void f() {
        if (this.g != null) {
            this.M = new ai(this.g);
        }
    }

    public void f(boolean z2) {
        if (this.g != null) {
            this.u = z2;
            this.g.b(this.u);
        }
    }

    public void g(boolean z2) {
        if (this.g != null) {
            this.p = z2;
            this.g.c(this.p);
        }
    }

    public boolean g() {
        return this.p;
    }

    public String h() {
        if (this.g == null) {
            return null;
        }
        return this.g.e(this.D.a);
    }

    public void h(boolean z2) {
        if (this.g != null) {
            this.g.d(z2);
        }
    }

    public void i(boolean z2) {
        if (this.g != null) {
            this.r = z2;
            this.g.a(this.D.a, z2);
        }
    }

    public boolean i() {
        return this.u;
    }

    public void j(boolean z2) {
        this.g.e(z2);
        this.g.d(this.ap.a);
        this.g.d(this.aq.a);
    }

    public boolean j() {
        if (this.g == null) {
            return false;
        }
        return this.g.k();
    }

    public boolean k() {
        return this.q;
    }

    public void l(boolean z2) {
        if (this.g != null) {
            this.s = z2;
            this.g.a(this.C.a, z2);
        }
    }

    public boolean l() {
        return this.g.a(this.am.a);
    }

    public void m(boolean z2) {
        if (this.g != null) {
            this.t = z2;
            this.g.a(this.J.a, z2);
        }
    }

    public boolean m() {
        if (this.g == null) {
            return false;
        }
        return this.g.o();
    }

    public void n() {
        if (this.g != null) {
            this.g.d(this.E.a);
            this.g.d(this.I.a);
            this.g.d(this.G.a);
            this.g.d(this.H.a);
        }
    }

    public void n(boolean z2) {
        this.d = z2;
    }

    public void o() {
        if (this.g != null) {
            this.g.p();
            this.g.b(this.J.a);
        }
    }

    public void o(boolean z2) {
        this.e = z2;
    }

    public MapBaseIndoorMapInfo p() {
        return this.g.q();
    }

    public void p(boolean z2) {
        this.w = z2;
    }

    public void q(boolean z2) {
        this.v = z2;
    }

    public boolean q() {
        return this.g.r();
    }

    public void r(boolean z2) {
        if (this.g != null) {
            this.g.a(this.F.a, z2);
        }
    }

    public boolean r() {
        return this.r;
    }

    public void s(boolean z2) {
        if (this.g != null) {
            this.g.a(this.aq.a, z2);
        }
    }

    public boolean s() {
        return this.s;
    }

    public void t() {
        if (this.g != null) {
            this.g.b(this.J.a);
        }
    }

    public void t(boolean z2) {
        this.as = z2;
    }

    public void u() {
        if (this.g != null) {
            this.g.e();
        }
    }

    public void v() {
        if (this.g != null) {
            this.g.f();
        }
    }

    public boolean w() {
        return this.d;
    }

    public boolean x() {
        return this.e;
    }

    public boolean y() {
        return this.w;
    }

    public boolean z() {
        return this.v;
    }
}

package com.autonavi.aps.amapapi.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import cn.com.bioeasy.app.utils.ListUtils;
import com.alipay.sdk.cons.a;
import com.loc.bw;
import org.json.JSONObject;

@SuppressLint({"NewApi"})
public class AmapLoc implements Parcelable {
    public static final Parcelable.Creator<AmapLoc> CREATOR = new a();
    private String A = "";
    private String B = "";
    private int C = -1;
    private String D = "";
    private String E = "";
    private boolean F = true;
    private boolean G = true;
    private JSONObject H = null;
    private String a = "";
    private double b = 0.0d;
    private double c = 0.0d;
    private double d = 0.0d;
    private float e = 0.0f;
    private float f = 0.0f;
    private float g = 0.0f;
    private long h = 0;
    private String i = "new";
    private int j = 0;
    private String k = "success";
    private int l = 0;
    private String m = "";
    private String n = "";
    private String o = "";
    private String p = "";
    private String q = "";
    private String r = "";
    private String s = "";
    private String t = "";
    private String u = "";
    private String v = "";
    private String w = "";
    private String x = "";
    private String y = "";
    private String z = null;

    public int a() {
        return this.j;
    }

    public int b() {
        return this.l;
    }

    public void a(int i2) {
        this.l = i2;
    }

    public void b(int i2) {
        if (this.j == 0) {
            switch (i2) {
                case 0:
                    this.k = "success";
                    break;
                case 1:
                    this.k = "重要参数为空";
                    break;
                case 2:
                    this.k = "WIFI信息不足";
                    break;
                case 3:
                    this.k = "请求参数获取出现异常";
                    break;
                case 4:
                    this.k = "网络连接异常";
                    break;
                case 5:
                    this.k = "解析XML出错";
                    break;
                case 6:
                    this.k = "定位结果错误";
                    break;
                case 7:
                    this.k = "KEY错误";
                    break;
                case 8:
                    this.k = "其他错误";
                    break;
                case 9:
                    this.k = "初始化异常";
                    break;
                case 10:
                    this.k = "定位服务启动失败";
                    break;
                case 11:
                    this.k = "错误的基站信息，请检查是否插入SIM卡";
                    break;
                case 12:
                    this.k = "缺少定位权限";
                    break;
            }
            this.j = i2;
        }
    }

    public String c() {
        return this.k;
    }

    public void a(String str) {
        this.k = str;
    }

    public String d() {
        return this.m;
    }

    public void b(String str) {
        if (this.m == null || this.m.length() == 0) {
            this.m = str;
        }
    }

    public boolean e() {
        return this.F;
    }

    public void a(boolean z2) {
        this.F = z2;
    }

    public boolean f() {
        return this.G;
    }

    public void b(boolean z2) {
        this.G = z2;
    }

    public AmapLoc() {
    }

    public AmapLoc(Parcel parcel) {
        boolean z2;
        boolean z3 = true;
        this.a = parcel.readString();
        this.i = parcel.readString();
        this.k = parcel.readString();
        this.j = parcel.readInt();
        this.g = parcel.readFloat();
        this.f = parcel.readFloat();
        this.e = parcel.readFloat();
        this.b = parcel.readDouble();
        this.c = parcel.readDouble();
        this.d = parcel.readDouble();
        this.h = parcel.readLong();
        this.n = parcel.readString();
        this.o = parcel.readString();
        this.p = parcel.readString();
        this.q = parcel.readString();
        this.r = parcel.readString();
        this.s = parcel.readString();
        this.t = parcel.readString();
        this.u = parcel.readString();
        this.v = parcel.readString();
        this.w = parcel.readString();
        this.x = parcel.readString();
        this.y = parcel.readString();
        this.z = parcel.readString();
        this.A = parcel.readString();
        this.B = parcel.readString();
        this.D = parcel.readString();
        this.m = parcel.readString();
        this.C = parcel.readInt();
        this.l = parcel.readInt();
        this.E = parcel.readString();
        if (parcel.readByte() != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.F = z2;
        this.G = parcel.readByte() == 0 ? false : z3;
    }

    public AmapLoc(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                if (bw.a(jSONObject, "provider")) {
                    c(jSONObject.getString("provider"));
                }
                if (bw.a(jSONObject, "lon")) {
                    a(jSONObject.getDouble("lon"));
                }
                if (bw.a(jSONObject, "lat")) {
                    b(jSONObject.getDouble("lat"));
                }
                if (bw.a(jSONObject, "altitude")) {
                    c(jSONObject.getDouble("altitude"));
                }
                if (bw.a(jSONObject, "acc")) {
                    y(jSONObject.getString("acc"));
                }
                if (bw.a(jSONObject, "accuracy")) {
                    a((float) jSONObject.getLong("accuracy"));
                }
                if (bw.a(jSONObject, "speed")) {
                    b((float) jSONObject.getLong("speed"));
                }
                if (bw.a(jSONObject, "dir")) {
                    c((float) jSONObject.getLong("dir"));
                }
                if (bw.a(jSONObject, "bearing")) {
                    c((float) jSONObject.getLong("bearing"));
                }
                if (bw.a(jSONObject, "type")) {
                    f(jSONObject.getString("type"));
                }
                if (bw.a(jSONObject, "retype")) {
                    g(jSONObject.getString("retype"));
                }
                if (bw.a(jSONObject, "citycode")) {
                    i(jSONObject.getString("citycode"));
                }
                if (bw.a(jSONObject, "desc")) {
                    j(jSONObject.getString("desc"));
                }
                if (bw.a(jSONObject, "adcode")) {
                    k(jSONObject.getString("adcode"));
                }
                if (bw.a(jSONObject, "country")) {
                    l(jSONObject.getString("country"));
                }
                if (bw.a(jSONObject, "province")) {
                    m(jSONObject.getString("province"));
                }
                if (bw.a(jSONObject, "city")) {
                    n(jSONObject.getString("city"));
                }
                if (bw.a(jSONObject, "road")) {
                    p(jSONObject.getString("road"));
                }
                if (bw.a(jSONObject, "street")) {
                    q(jSONObject.getString("street"));
                }
                if (bw.a(jSONObject, "number")) {
                    r(jSONObject.getString("number"));
                }
                if (bw.a(jSONObject, "poiname")) {
                    s(jSONObject.getString("poiname"));
                }
                if (bw.a(jSONObject, "errorCode")) {
                    b(jSONObject.getInt("errorCode"));
                }
                if (bw.a(jSONObject, "errorInfo")) {
                    a(jSONObject.getString("errorInfo"));
                }
                if (bw.a(jSONObject, "locationType")) {
                    a(jSONObject.getInt("locationType"));
                }
                if (bw.a(jSONObject, "locationDetail")) {
                    b(jSONObject.getString("locationDetail"));
                }
                if (bw.a(jSONObject, "cens")) {
                    t(jSONObject.getString("cens"));
                }
                if (bw.a(jSONObject, "poiid")) {
                    u(jSONObject.getString("poiid"));
                }
                if (bw.a(jSONObject, "pid")) {
                    u(jSONObject.getString("pid"));
                }
                if (bw.a(jSONObject, "floor")) {
                    v(jSONObject.getString("floor"));
                }
                if (bw.a(jSONObject, "flr")) {
                    v(jSONObject.getString("flr"));
                }
                if (bw.a(jSONObject, "coord")) {
                    w(jSONObject.getString("coord"));
                }
                if (bw.a(jSONObject, "mcell")) {
                    x(jSONObject.getString("mcell"));
                }
                if (bw.a(jSONObject, "time")) {
                    a(jSONObject.getLong("time"));
                }
                if (bw.a(jSONObject, "district")) {
                    o(jSONObject.getString("district"));
                }
                if (bw.a(jSONObject, "isOffset")) {
                    a(jSONObject.getBoolean("isOffset"));
                }
                if (bw.a(jSONObject, "isReversegeo")) {
                    b(jSONObject.getBoolean("isReversegeo"));
                }
            } catch (Exception e2) {
            }
        }
    }

    public String g() {
        return this.a;
    }

    public void c(String str) {
        this.a = str;
    }

    public double h() {
        return this.b;
    }

    public void a(double d2) {
        d(bw.a((Object) Double.valueOf(d2), "#.000000"));
    }

    public void d(String str) {
        this.b = Double.parseDouble(str);
    }

    public double i() {
        return this.c;
    }

    public void b(double d2) {
        e(bw.a((Object) Double.valueOf(d2), "#.000000"));
    }

    public void e(String str) {
        this.c = Double.parseDouble(str);
    }

    public void c(double d2) {
        this.d = d2;
    }

    public float j() {
        return this.e;
    }

    public void a(float f2) {
        y(String.valueOf(Math.round(f2)));
    }

    private void y(String str) {
        this.e = Float.parseFloat(str);
    }

    public void b(float f2) {
        z(bw.a((Object) Float.valueOf(f2), "#.0"));
    }

    private void z(String str) {
        this.f = Float.parseFloat(str);
        if (this.f > 100.0f) {
            this.f = 0.0f;
        }
    }

    public void c(float f2) {
        A(bw.a((Object) Float.valueOf(f2), "#.0"));
    }

    private void A(String str) {
        this.g = Float.parseFloat(str);
    }

    public long k() {
        return this.h;
    }

    public void a(long j2) {
        this.h = j2;
    }

    public String l() {
        return this.i;
    }

    public void f(String str) {
        this.i = str;
    }

    public String m() {
        return this.n;
    }

    public void g(String str) {
        this.n = str;
    }

    public String n() {
        return this.o;
    }

    public void h(String str) {
        this.o = str;
    }

    public String o() {
        return this.p;
    }

    public void i(String str) {
        this.p = str;
    }

    public String p() {
        return this.q;
    }

    public void j(String str) {
        this.q = str;
    }

    public String q() {
        return this.r;
    }

    public void k(String str) {
        this.r = str;
    }

    public String r() {
        return this.s;
    }

    public void l(String str) {
        this.s = str;
    }

    public String s() {
        return this.t;
    }

    public void m(String str) {
        this.t = str;
    }

    public String t() {
        return this.u;
    }

    public void n(String str) {
        this.u = str;
    }

    public String u() {
        return this.v;
    }

    public void o(String str) {
        this.v = str;
    }

    public String v() {
        return this.w;
    }

    public void p(String str) {
        this.w = str;
    }

    public String w() {
        return this.x;
    }

    public void q(String str) {
        this.x = str;
    }

    public String x() {
        return this.E;
    }

    public void r(String str) {
        this.E = str;
    }

    public String y() {
        return this.y;
    }

    public void s(String str) {
        this.y = str;
    }

    public void t(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split("\\*");
            int length = split.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                String str2 = split[i2];
                if (!TextUtils.isEmpty(str2)) {
                    String[] split2 = str2.split(ListUtils.DEFAULT_JOIN_SEPARATOR);
                    a(Double.parseDouble(split2[0]));
                    b(Double.parseDouble(split2[1]));
                    a((float) Integer.parseInt(split2[2]));
                    break;
                }
                i2++;
            }
            this.z = str;
        }
    }

    public String z() {
        return this.A;
    }

    public void u(String str) {
        this.A = str;
    }

    public void v(String str) {
        if (!TextUtils.isEmpty(str)) {
            str = str.replace("F", "");
            try {
                Integer.parseInt(str);
            } catch (Exception e2) {
                str = null;
            }
        }
        this.B = str;
    }

    public int A() {
        return this.C;
    }

    public void w(String str) {
        if (TextUtils.isEmpty(str)) {
            this.C = -1;
        } else if (this.a.equals("gps")) {
            this.C = 0;
        } else if (str.equals("0")) {
            this.C = 0;
        } else if (str.equals(a.e)) {
            this.C = 1;
        } else {
            this.C = -1;
        }
    }

    public String B() {
        return this.D;
    }

    public AmapLoc C() {
        String B2 = B();
        if (TextUtils.isEmpty(B2)) {
            return null;
        }
        String[] split = B2.split(ListUtils.DEFAULT_JOIN_SEPARATOR);
        if (split.length != 3) {
            return null;
        }
        AmapLoc amapLoc = new AmapLoc();
        amapLoc.c(g());
        amapLoc.d(split[0]);
        amapLoc.e(split[1]);
        amapLoc.a(Float.parseFloat(split[2]));
        amapLoc.i(o());
        amapLoc.k(q());
        amapLoc.l(r());
        amapLoc.m(s());
        amapLoc.n(t());
        amapLoc.a(k());
        amapLoc.f(l());
        amapLoc.w(String.valueOf(A()));
        if (bw.a(amapLoc)) {
            return amapLoc;
        }
        return null;
    }

    public void x(String str) {
        this.D = str;
    }

    public JSONObject D() {
        return this.H;
    }

    public void a(JSONObject jSONObject) {
        this.H = jSONObject;
    }

    public String E() {
        return c(1);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x00cf, code lost:
        r1.put("time", r6.h);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x00d6, code lost:
        r1.put("provider", r6.a);
        r1.put("lon", r6.b);
        r1.put("lat", r6.c);
        r1.put("accuracy", (double) r6.e);
        r1.put("type", r6.i);
        r1.put("isOffset", r6.F);
        r1.put("isReversegeo", r6.G);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String c(int r7) {
        /*
            r6 = this;
            r0 = 0
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x010b }
            r1.<init>()     // Catch:{ Exception -> 0x010b }
            switch(r7) {
                case 1: goto L_0x000c;
                case 2: goto L_0x00cf;
                case 3: goto L_0x00d6;
                default: goto L_0x0009;
            }     // Catch:{ Exception -> 0x010b }
        L_0x0009:
            if (r1 != 0) goto L_0x010f
        L_0x000b:
            return r0
        L_0x000c:
            java.lang.String r2 = "altitude"
            double r4 = r6.d     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r4)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "speed"
            float r3 = r6.f     // Catch:{ Exception -> 0x010b }
            double r4 = (double) r3     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r4)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "bearing"
            float r3 = r6.g     // Catch:{ Exception -> 0x010b }
            double r4 = (double) r3     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r4)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "retype"
            java.lang.String r3 = r6.n     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "citycode"
            java.lang.String r3 = r6.p     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "desc"
            java.lang.String r3 = r6.q     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "adcode"
            java.lang.String r3 = r6.r     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "country"
            java.lang.String r3 = r6.s     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "province"
            java.lang.String r3 = r6.t     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "city"
            java.lang.String r3 = r6.u     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "district"
            java.lang.String r3 = r6.v     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "road"
            java.lang.String r3 = r6.w     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "street"
            java.lang.String r3 = r6.x     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "number"
            java.lang.String r3 = r6.E     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "poiname"
            java.lang.String r3 = r6.y     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "cens"
            java.lang.String r3 = r6.z     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "poiid"
            java.lang.String r3 = r6.A     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "floor"
            java.lang.String r3 = r6.B     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "coord"
            int r3 = r6.C     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "mcell"
            java.lang.String r3 = r6.D     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "errorCode"
            int r3 = r6.j     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "errorInfo"
            java.lang.String r3 = r6.k     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "locationType"
            int r3 = r6.l     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "locationDetail"
            java.lang.String r3 = r6.m     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            org.json.JSONObject r2 = r6.H     // Catch:{ Exception -> 0x010b }
            if (r2 == 0) goto L_0x00cf
            java.lang.String r2 = "offpct"
            boolean r2 = com.loc.bw.a((org.json.JSONObject) r1, (java.lang.String) r2)     // Catch:{ Exception -> 0x010b }
            if (r2 == 0) goto L_0x00cf
            java.lang.String r2 = "offpct"
            org.json.JSONObject r3 = r6.H     // Catch:{ Exception -> 0x010b }
            java.lang.String r4 = "offpct"
            java.lang.String r3 = r3.getString(r4)     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
        L_0x00cf:
            java.lang.String r2 = "time"
            long r4 = r6.h     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r4)     // Catch:{ Exception -> 0x010b }
        L_0x00d6:
            java.lang.String r2 = "provider"
            java.lang.String r3 = r6.a     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "lon"
            double r4 = r6.b     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r4)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "lat"
            double r4 = r6.c     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r4)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "accuracy"
            float r3 = r6.e     // Catch:{ Exception -> 0x010b }
            double r4 = (double) r3     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r4)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "type"
            java.lang.String r3 = r6.i     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "isOffset"
            boolean r3 = r6.F     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            java.lang.String r2 = "isReversegeo"
            boolean r3 = r6.G     // Catch:{ Exception -> 0x010b }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x010b }
            goto L_0x0009
        L_0x010b:
            r1 = move-exception
            r1 = r0
            goto L_0x0009
        L_0x010f:
            java.lang.String r0 = r1.toString()
            goto L_0x000b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.aps.amapapi.model.AmapLoc.c(int):java.lang.String");
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        byte b2;
        byte b3 = 1;
        parcel.writeString(this.a);
        parcel.writeString(this.i);
        parcel.writeString(this.k);
        parcel.writeInt(this.j);
        parcel.writeFloat(this.g);
        parcel.writeFloat(this.f);
        parcel.writeFloat(this.e);
        parcel.writeDouble(this.b);
        parcel.writeDouble(this.c);
        parcel.writeDouble(this.d);
        parcel.writeLong(this.h);
        parcel.writeString(this.n);
        parcel.writeString(this.o);
        parcel.writeString(this.p);
        parcel.writeString(this.q);
        parcel.writeString(this.r);
        parcel.writeString(this.s);
        parcel.writeString(this.t);
        parcel.writeString(this.u);
        parcel.writeString(this.v);
        parcel.writeString(this.w);
        parcel.writeString(this.x);
        parcel.writeString(this.y);
        parcel.writeString(this.z);
        parcel.writeString(this.A);
        parcel.writeString(this.B);
        parcel.writeString(this.D);
        parcel.writeString(this.m);
        parcel.writeInt(this.C);
        parcel.writeInt(this.l);
        parcel.writeString(this.E);
        if (this.F) {
            b2 = 1;
        } else {
            b2 = 0;
        }
        parcel.writeByte(b2);
        if (!this.G) {
            b3 = 0;
        }
        parcel.writeByte(b3);
    }
}

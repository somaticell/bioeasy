package com.loc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import com.loc.p;
import java.lang.reflect.Method;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: AuthUtil */
public class bu {
    private static Method A;
    private static Context a;
    private static String b = "提示信息";
    private static String c = "确认";
    private static String d = "取消";
    private static String e = "";
    private static String f = "";
    private static String g = "";
    private static boolean h = false;
    private static long i = 0;
    private static long j = 0;
    private static boolean k = false;
    private static int l = 0;
    private static boolean m = false;
    private static int n = 0;
    private static boolean o = false;
    private static String p = com.alipay.sdk.cons.a.e;
    private static String q = com.alipay.sdk.cons.a.e;
    private static int r = -1;
    private static long s = 0;
    private static String t;
    private static String u;
    private static String v = "0";
    private static SharedPreferences w = null;
    private static SharedPreferences.Editor x = null;
    private static SharedPreferences y = null;
    private static SharedPreferences.Editor z = null;

    public static synchronized boolean a(Context context) {
        boolean z2;
        synchronized (bu.class) {
            a = context;
            z2 = false;
            try {
                p.a a2 = p.a(context, e.a("2.3.0"), "callamappro;fast;sdkupdate;sdkcoordinate;locate;opflag");
                if (a2 != null) {
                    z2 = a(a2);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return z2;
    }

    public static boolean a() {
        return h;
    }

    public static long b() {
        return i;
    }

    public static long c() {
        return j;
    }

    public static boolean d() {
        return k;
    }

    public static int e() {
        return l;
    }

    public static boolean f() {
        return m;
    }

    public static int g() {
        return n;
    }

    public static boolean h() {
        return o;
    }

    public static boolean i() {
        boolean equals = com.alipay.sdk.cons.a.e.equals(p);
        az.a = equals;
        return equals;
    }

    public static String j() {
        return b;
    }

    public static String k() {
        return c;
    }

    public static String l() {
        return d;
    }

    public static String m() {
        return e;
    }

    public static String n() {
        return f;
    }

    public static String o() {
        return g;
    }

    private static String a(JSONObject jSONObject, String str) {
        if (jSONObject == null) {
            return null;
        }
        try {
            if (!jSONObject.has(str) || jSONObject.getString(str).equals("[]")) {
                return "";
            }
            return jSONObject.optString(str);
        } catch (JSONException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    private static boolean a(p.a aVar) {
        boolean z2;
        try {
            JSONObject jSONObject = aVar.a;
            if (jSONObject != null && jSONObject.has("opflag")) {
                p = jSONObject.getString("opflag");
            }
            JSONObject jSONObject2 = aVar.b;
            if (jSONObject2 != null) {
                if (jSONObject2.has("callamapflag")) {
                    q = jSONObject2.getString("callamapflag");
                }
                if (jSONObject2.has("count")) {
                    r = jSONObject2.getInt("count");
                }
                if (jSONObject2.has("nowtime")) {
                    s = jSONObject2.getLong("nowtime");
                }
                if (!(r == -1 || s == 0)) {
                    if (!bw.a(s, e(a))) {
                        c(a);
                    }
                }
            }
            JSONObject jSONObject3 = aVar.e;
            if (jSONObject3 != null) {
                if (jSONObject3.has("f")) {
                    v = a(jSONObject3, "f");
                    if (com.alipay.sdk.cons.a.e.equals(v)) {
                        long b2 = b(a);
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        if (elapsedRealtime - b2 > 3600000) {
                            a(a, elapsedRealtime);
                        }
                        if (elapsedRealtime > b2 && elapsedRealtime - b2 < 3600000) {
                            v = "0";
                        }
                        if (elapsedRealtime < b2) {
                            v = "0";
                            a(a, elapsedRealtime);
                        }
                    } else {
                        v = "0";
                    }
                }
                if (jSONObject3.has("a")) {
                    b = a(jSONObject3, "a");
                }
                if (jSONObject3.has("o")) {
                    c = a(jSONObject3, "o");
                }
                if (jSONObject3.has("c")) {
                    d = a(jSONObject3, "c");
                }
                if (jSONObject3.has("i")) {
                    e = a(jSONObject3, "i");
                }
                if (jSONObject3.has("u")) {
                    f = a(jSONObject3, "u");
                }
                if (jSONObject3.has("t")) {
                    g = a(jSONObject3, "t");
                }
                if (("".equals(e) || e == null) && ("".equals(f) || f == null)) {
                    v = "0";
                }
            }
            v a2 = e.a("2.3.0");
            p.a.c cVar = aVar.g;
            if (cVar != null) {
                String str = cVar.b;
                String str2 = cVar.a;
                String str3 = cVar.c;
                if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
                    new ad(a, (ae) null, a2).a();
                } else {
                    new ad(a, new ae(str2, str, str3), a2).a();
                }
            } else {
                new ad(a, (ae) null, a2).a();
            }
            p.a.b bVar = aVar.h;
            if (bVar != null) {
                t = bVar.a;
                u = bVar.b;
                if (!TextUtils.isEmpty(t) && !TextUtils.isEmpty(u)) {
                    new u(a, "loc", t, u).a();
                }
            }
            JSONObject jSONObject4 = aVar.c;
            if (jSONObject4 == null) {
                return true;
            }
            a b3 = b(jSONObject4, "fs");
            if (b3 != null) {
                k = b3.c.equals(com.alipay.sdk.cons.a.e);
                try {
                    l = Integer.parseInt(b3.b);
                } catch (Exception e2) {
                }
            }
            a b4 = b(jSONObject4, "us");
            if (b4 != null) {
                m = b4.c.equals(com.alipay.sdk.cons.a.e);
                if (b4.a.equals("0")) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                o = z2;
                try {
                    n = Integer.parseInt(b4.b);
                } catch (Exception e3) {
                }
            }
            a b5 = b(jSONObject4, "rs");
            if (b5 == null) {
                return true;
            }
            h = b5.c.equals(com.alipay.sdk.cons.a.e);
            if (h) {
                j = bw.b();
            }
            try {
                i = (long) (Integer.parseInt(b5.b) * 1000);
                return true;
            } catch (Exception e4) {
                return true;
            }
        } catch (Throwable th) {
            v = "0";
            th.printStackTrace();
            return false;
        }
    }

    private static a b(JSONObject jSONObject, String str) {
        JSONObject jSONObject2;
        if (jSONObject != null) {
            try {
                if (jSONObject.has(str) && (jSONObject2 = jSONObject.getJSONObject(str)) != null) {
                    a aVar = new a();
                    try {
                        if (jSONObject2.has("b")) {
                            aVar.a = a(jSONObject2, "b");
                        }
                        if (jSONObject2.has("t")) {
                            aVar.b = a(jSONObject2, "t");
                        }
                        if (!jSONObject2.has("st")) {
                            return aVar;
                        }
                        aVar.c = a(jSONObject2, "st");
                        return aVar;
                    } catch (JSONException e2) {
                        return aVar;
                    }
                }
            } catch (JSONException e3) {
                return null;
            }
        }
        return null;
    }

    /* compiled from: AuthUtil */
    static class a {
        String a = "0";
        String b = "0";
        String c = "0";

        a() {
        }
    }

    public static boolean p() {
        if (!com.alipay.sdk.cons.a.e.equals(q)) {
            return false;
        }
        if (r == -1 || s == 0) {
            return true;
        }
        if (!bw.a(s, e(a))) {
            c(a);
            a(a, 1);
            return true;
        }
        int d2 = d(a);
        if (d2 >= r) {
            return false;
        }
        a(a, d2 + 1);
        return true;
    }

    public static synchronized boolean q() {
        boolean equals;
        synchronized (bu.class) {
            equals = com.alipay.sdk.cons.a.e.equals(v);
        }
        return equals;
    }

    public static synchronized void a(String str) {
        synchronized (bu.class) {
            v = str;
        }
    }

    private static void a(Context context, long j2) {
        try {
            if (w == null) {
                w = context.getSharedPreferences("abcd", 0);
            }
            if (x == null) {
                x = w.edit();
            }
            x.putLong("abc", j2);
            a(x);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private static long b(Context context) {
        try {
            return context.getSharedPreferences("abcd", 0).getLong("abc", 0);
        } catch (Throwable th) {
            return 0;
        }
    }

    private static void a(SharedPreferences.Editor editor) {
        if (editor != null) {
            if (Build.VERSION.SDK_INT >= 9) {
                try {
                    if (A == null) {
                        A = SharedPreferences.Editor.class.getDeclaredMethod("apply", new Class[0]);
                    }
                    A.invoke(editor, new Object[0]);
                } catch (Throwable th) {
                    th.printStackTrace();
                    editor.commit();
                }
            } else {
                editor.commit();
            }
        }
    }

    private static void c(Context context) {
        try {
            if (y == null) {
                y = context.getSharedPreferences("pref", 0);
            }
            if (z == null) {
                z = y.edit();
            }
            if (s == 0) {
                z.remove("nowtime");
            } else {
                z.putLong("nowtime", s);
            }
        } catch (Throwable th) {
            th.printStackTrace();
            return;
        }
        if (r == -1) {
            z.remove("count");
        } else {
            z.putInt("count", 0);
        }
        a(z);
    }

    private static int d(Context context) {
        try {
            if (y == null) {
                y = context.getSharedPreferences("pref", 0);
            }
            return y.getInt("count", 0);
        } catch (Throwable th) {
            return 0;
        }
    }

    private static void a(Context context, int i2) {
        try {
            if (y == null) {
                y = context.getSharedPreferences("pref", 0);
            }
            if (z == null) {
                z = y.edit();
            }
            z.putInt("count", i2);
            a(z);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private static long e(Context context) {
        try {
            if (y == null) {
                y = context.getSharedPreferences("pref", 0);
            }
            return y.getLong("nowtime", 0);
        } catch (Throwable th) {
            return 0;
        }
    }
}

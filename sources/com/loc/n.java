package com.loc;

import android.content.Context;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: AuthManager */
public class n {
    public static int a = -1;
    public static String b = "";
    private static v c;
    private static String d = "http://apiinit.amap.com/v3/log/init";
    private static String e = null;

    private static boolean a(Context context, v vVar, boolean z) {
        c = vVar;
        try {
            String a2 = a();
            HashMap hashMap = new HashMap();
            hashMap.put("Content-Type", "application/x-www-form-urlencoded");
            hashMap.put("Accept-Encoding", "gzip");
            hashMap.put("Connection", "Keep-Alive");
            hashMap.put("User-Agent", c.b);
            hashMap.put("X-INFO", o.a(context, c, (Map<String, String>) null, z));
            hashMap.put("logversion", "2.1");
            hashMap.put("platinfo", String.format("platform=Android&sdkversion=%s&product=%s", new Object[]{c.a, c.c}));
            ap a3 = ap.a();
            x xVar = new x();
            xVar.a(t.a(context));
            xVar.a((Map<String, String>) hashMap);
            xVar.b(a(context));
            xVar.a(a2);
            return a(a3.b(xVar));
        } catch (Throwable th) {
            y.a(th, "Auth", "getAuth");
            return true;
        }
    }

    public static synchronized boolean a(Context context, v vVar) {
        boolean a2;
        synchronized (n.class) {
            a2 = a(context, vVar, true);
        }
        return a2;
    }

    public static synchronized boolean b(Context context, v vVar) {
        boolean a2;
        synchronized (n.class) {
            a2 = a(context, vVar, false);
        }
        return a2;
    }

    public static void a(String str) {
        m.a(str);
    }

    private static String a() {
        return d;
    }

    private static boolean a(byte[] bArr) {
        String str;
        if (bArr == null) {
            return true;
        }
        try {
            str = new String(bArr, "UTF-8");
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("status")) {
                    int i = jSONObject.getInt("status");
                    if (i == 1) {
                        a = 1;
                    } else if (i == 0) {
                        a = 0;
                    }
                }
                if (jSONObject.has("info")) {
                    b = jSONObject.getString("info");
                }
                if (a == 0) {
                    Log.i("AuthFailure", b);
                }
                if (a != 1) {
                    return false;
                }
                return true;
            } catch (JSONException e2) {
                y.a(e2, "Auth", "lData");
            } catch (Throwable th) {
                y.a(th, "Auth", "lData");
            }
        } catch (UnsupportedEncodingException e3) {
            str = new String(bArr);
        }
        return false;
    }

    private static Map<String, String> a(Context context) {
        HashMap hashMap = new HashMap();
        try {
            hashMap.put("resType", "json");
            hashMap.put("encode", "UTF-8");
            String a2 = o.a();
            hashMap.put("ts", a2);
            hashMap.put("key", m.e(context));
            hashMap.put("scode", o.a(context, a2, w.a("resType=json&encode=UTF-8&key=" + m.e(context))));
        } catch (Throwable th) {
            y.a(th, "Auth", "gParams");
        }
        return hashMap;
    }
}

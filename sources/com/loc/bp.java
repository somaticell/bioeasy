package com.loc;

import android.content.Context;
import android.net.NetworkInfo;
import com.alipay.sdk.cons.a;
import com.loc.v;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: LocNetManager */
public class bp {
    private static bp e = null;
    v a = null;
    String b = null;
    ap c = null;
    aq d = null;
    private long f = 0;
    private int g = e.j;
    private int h = e.j;

    private bp(Context context) {
        try {
            this.a = new v.a("loc", "2.3.0", "AMAP_Location_SDK_Android 2.3.0").a(e.b()).a();
        } catch (l e2) {
            e2.printStackTrace();
        }
        this.b = o.a(context, this.a, new HashMap(), true);
        this.c = ap.a();
    }

    public static synchronized bp a(Context context) {
        bp bpVar;
        synchronized (bp.class) {
            if (e == null) {
                e = new bp(context);
            }
            bpVar = e;
        }
        return bpVar;
    }

    public byte[] a(Context context, JSONObject jSONObject, bt btVar, String str) throws Exception {
        if (bw.a(jSONObject, "httptimeout")) {
            try {
                this.g = jSONObject.getInt("httptimeout");
            } catch (JSONException e2) {
            }
        }
        if (a(bw.c(context)) == -1) {
            return null;
        }
        HashMap hashMap = new HashMap();
        bq bqVar = new bq();
        hashMap.clear();
        hashMap.put("Content-Type", "application/octet-stream");
        hashMap.put("Accept-Encoding", "gzip");
        hashMap.put("gzipped", a.e);
        hashMap.put("Connection", "Keep-Alive");
        hashMap.put("User-Agent", "AMAP_Location_SDK_Android 2.3.0");
        hashMap.put("X-INFO", this.b);
        hashMap.put("KEY", m.e(context));
        hashMap.put("enginever", "4.2");
        String a2 = o.a();
        String a3 = o.a(context, a2, "key=" + m.e(context));
        hashMap.put("ts", a2);
        hashMap.put("scode", a3);
        hashMap.put("platinfo", String.format(Locale.US, "platform=Android&sdkversion=%s&product=%s", new Object[]{"2.3.0", "loc"}));
        hashMap.put("logversion", "2.1");
        hashMap.put("encr", a.e);
        bqVar.a((Map<String, String>) hashMap);
        bqVar.a(str);
        bqVar.a(bw.a(btVar.a()));
        bqVar.a(t.a(context));
        bqVar.a(this.g);
        bqVar.b(this.g);
        return this.c.b(bqVar);
    }

    public String a(byte[] bArr, Context context, String str, boolean z) {
        byte[] b2;
        if (a(bw.c(context)) == -1) {
            return null;
        }
        HashMap hashMap = new HashMap();
        bq bqVar = new bq();
        hashMap.clear();
        hashMap.put("Content-Type", "application/x-www-form-urlencoded");
        hashMap.put("Connection", "Keep-Alive");
        if (z) {
            hashMap.put("Accept-Encoding", "gzip");
            hashMap.put("User-Agent", "AMAP_Location_SDK_Android 2.3.0");
            hashMap.put("platinfo", String.format(Locale.US, "platform=Android&sdkversion=%s&product=%s", new Object[]{"2.3.0", "loc"}));
            hashMap.put("logversion", "2.1");
        }
        bqVar.a((Map<String, String>) hashMap);
        bqVar.a(str);
        bqVar.a(bArr);
        bqVar.a(t.a(context));
        bqVar.a(e.j);
        bqVar.b(e.j);
        if (z) {
            try {
                b2 = this.c.a(bqVar);
            } catch (l e2) {
                e2.printStackTrace();
                return null;
            } catch (Throwable th) {
                th.printStackTrace();
                return null;
            }
        } else {
            b2 = this.c.b(bqVar);
        }
        return new String(b2, "utf-8");
    }

    public static int a(NetworkInfo networkInfo) {
        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
            return networkInfo.getType();
        }
        return -1;
    }

    public HttpURLConnection a(Context context, String str, HashMap<String, String> hashMap, byte[] bArr) throws Exception {
        try {
            if (a(bw.c(context)) == -1) {
                return null;
            }
            boolean z = false;
            bq bqVar = new bq();
            bqVar.a((Map<String, String>) hashMap);
            bqVar.a(str);
            bqVar.a(bArr);
            bqVar.a(t.a(context));
            bqVar.a(e.j);
            bqVar.b(e.j);
            if (str.toLowerCase(Locale.US).startsWith("https")) {
                z = true;
            }
            return this.c.a(bqVar, z);
        } catch (Throwable th) {
            return null;
        }
    }
}

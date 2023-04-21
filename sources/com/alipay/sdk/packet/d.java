package com.alipay.sdk.packet;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.widget.TextView;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import com.alipay.sdk.data.c;
import com.alipay.sdk.net.a;
import com.alipay.sdk.sys.b;
import com.alipay.sdk.util.h;
import com.alipay.sdk.util.k;
import com.alipay.sdk.util.l;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class d {
    public static final String a = "msp-gzip";
    public static final String b = "Msp-Param";
    public static final String c = "Operation-Type";
    public static final String d = "content-type";
    public static final String e = "Version";
    public static final String f = "AppId";
    public static final String g = "des-mode";
    public static final String h = "namespace";
    public static final String i = "api_name";
    public static final String j = "api_version";
    public static final String k = "data";
    public static final String l = "params";
    public static final String m = "public_key";
    public static final String n = "device";
    public static final String o = "action";
    public static final String p = "type";
    public static final String q = "method";
    private static a t;
    protected boolean r = true;
    protected boolean s = true;

    public abstract JSONObject a() throws JSONException;

    public List<Header> a(boolean z, String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BasicHeader(a, String.valueOf(z)));
        arrayList.add(new BasicHeader(c, "alipay.msp.cashier.dispatch.bytes"));
        arrayList.add(new BasicHeader("content-type", "application/octet-stream"));
        arrayList.add(new BasicHeader(e, "2.0"));
        arrayList.add(new BasicHeader(f, "TAOBAO"));
        arrayList.add(new BasicHeader(b, a.a(str)));
        arrayList.add(new BasicHeader(g, "CBC"));
        return arrayList;
    }

    public String b() {
        return "4.9.0";
    }

    public String c() throws JSONException {
        HashMap hashMap = new HashMap();
        hashMap.put(n, Build.MODEL);
        hashMap.put("namespace", "com.alipay.mobilecashier");
        hashMap.put(i, "com.alipay.mcpay");
        hashMap.put(j, b());
        return a((HashMap<String, String>) hashMap, (HashMap<String, String>) new HashMap());
    }

    public static JSONObject a(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("type", str);
        jSONObject2.put(q, str2);
        jSONObject.put(o, jSONObject2);
        return jSONObject;
    }

    public String a(String str, JSONObject jSONObject) {
        b a2 = b.a();
        com.alipay.sdk.tid.b a3 = com.alipay.sdk.tid.b.a();
        JSONObject a4 = com.alipay.sdk.util.b.a(new JSONObject(), jSONObject);
        try {
            a4.put(com.alipay.sdk.cons.b.c, a3.a);
            c a5 = c.a();
            Context context = b.a().a;
            com.alipay.sdk.util.a a6 = com.alipay.sdk.util.a.a(context);
            if (TextUtils.isEmpty(a5.a)) {
                String b2 = l.b();
                String c2 = l.c();
                String f2 = l.f(context);
                String a7 = k.a(context);
                a5.a = "Msp/15.3.6" + " (" + b2 + h.b + c2 + h.b + f2 + h.b + a7.substring(0, a7.indexOf("://")) + h.b + l.g(context) + h.b + Float.toString(new TextView(context).getTextSize());
            }
            String str2 = com.alipay.sdk.util.a.b(context).p;
            String a8 = a6.a();
            String b3 = a6.b();
            Context context2 = b.a().a;
            SharedPreferences sharedPreferences = context2.getSharedPreferences("virtualImeiAndImsi", 0);
            String string = sharedPreferences.getString("virtual_imsi", (String) null);
            if (TextUtils.isEmpty(string)) {
                if (TextUtils.isEmpty(com.alipay.sdk.tid.b.a().a)) {
                    String c3 = b.a().c();
                    string = TextUtils.isEmpty(c3) ? c.b() : c3.substring(3, 18);
                } else {
                    string = com.alipay.sdk.util.a.a(context2).a();
                }
                sharedPreferences.edit().putString("virtual_imsi", string).commit();
            }
            String str3 = string;
            Context context3 = b.a().a;
            SharedPreferences sharedPreferences2 = context3.getSharedPreferences("virtualImeiAndImsi", 0);
            String string2 = sharedPreferences2.getString("virtual_imei", (String) null);
            if (TextUtils.isEmpty(string2)) {
                string2 = TextUtils.isEmpty(com.alipay.sdk.tid.b.a().a) ? c.b() : com.alipay.sdk.util.a.a(context3).b();
                sharedPreferences2.edit().putString("virtual_imei", string2).commit();
            }
            String str4 = string2;
            if (a3 != null) {
                a5.c = a3.b;
            }
            String replace = Build.MANUFACTURER.replace(h.b, " ");
            String replace2 = Build.MODEL.replace(h.b, " ");
            boolean b4 = b.b();
            String str5 = a6.a;
            WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
            String ssid = connectionInfo != null ? connectionInfo.getSSID() : "-1";
            WifiInfo connectionInfo2 = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
            String bssid = connectionInfo2 != null ? connectionInfo2.getBSSID() : BLEServiceApi.RESULT_NG;
            StringBuilder sb = new StringBuilder();
            sb.append(a5.a).append(h.b).append(str2).append(h.b).append("-1;-1").append(h.b).append(com.alipay.sdk.cons.a.e).append(h.b).append(a8).append(h.b).append(b3).append(h.b).append(a5.c).append(h.b).append(replace).append(h.b).append(replace2).append(h.b).append(b4).append(h.b).append(str5).append(";-1;-1;").append(a5.b).append(h.b).append(str3).append(h.b).append(str4).append(h.b).append(ssid).append(h.b).append(bssid);
            if (a3 != null) {
                HashMap hashMap = new HashMap();
                hashMap.put(com.alipay.sdk.cons.b.c, a3.a);
                hashMap.put(com.alipay.sdk.cons.b.g, b.a().c());
                String b5 = a5.b(context, hashMap);
                if (!TextUtils.isEmpty(b5)) {
                    sb.append(h.b).append(b5);
                }
            }
            sb.append(")");
            a4.put(com.alipay.sdk.cons.b.b, sb.toString());
            a4.put(com.alipay.sdk.cons.b.e, l.c(a2.a));
            a4.put(com.alipay.sdk.cons.b.f, l.b(a2.a));
            a4.put(com.alipay.sdk.cons.b.d, str);
            a4.put(com.alipay.sdk.cons.b.h, com.alipay.sdk.cons.a.d);
            a4.put(com.alipay.sdk.cons.b.g, a2.c());
            a4.put(com.alipay.sdk.cons.b.j, a3.b);
        } catch (Throwable th) {
        }
        return a4.toString();
    }

    private static boolean a(HttpResponse httpResponse) {
        Header[] allHeaders;
        String name;
        String str = null;
        if (httpResponse != null && (allHeaders = httpResponse.getAllHeaders()) != null && allHeaders.length > 0) {
            int length = allHeaders.length;
            int i2 = 0;
            while (true) {
                if (i2 < length) {
                    Header header = allHeaders[i2];
                    if (header != null && (name = header.getName()) != null && name.equalsIgnoreCase(a)) {
                        str = header.getValue();
                        break;
                    }
                    i2++;
                } else {
                    break;
                }
            }
        }
        return Boolean.valueOf(str).booleanValue();
    }

    private static String a(HttpResponse httpResponse, String str) {
        Header[] allHeaders;
        String name;
        if (httpResponse == null || (allHeaders = httpResponse.getAllHeaders()) == null || allHeaders.length <= 0) {
            return null;
        }
        for (Header header : allHeaders) {
            if (header != null && (name = header.getName()) != null && name.equalsIgnoreCase(str)) {
                return header.getValue();
            }
        }
        return null;
    }

    public static String a(HashMap<String, String> hashMap, HashMap<String, String> hashMap2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        for (Map.Entry next : hashMap.entrySet()) {
            jSONObject2.put((String) next.getKey(), next.getValue());
        }
        JSONObject jSONObject3 = new JSONObject();
        for (Map.Entry next2 : hashMap2.entrySet()) {
            jSONObject3.put((String) next2.getKey(), next2.getValue());
        }
        jSONObject2.put("params", jSONObject3);
        jSONObject.put("data", jSONObject2);
        return jSONObject.toString();
    }

    private static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject("data");
            if (!jSONObject.has("params")) {
                return false;
            }
            String optString = jSONObject.getJSONObject("params").optString(m, (String) null);
            if (TextUtils.isEmpty(optString)) {
                return false;
            }
            b.a();
            c.a().a(optString);
            return true;
        } catch (JSONException e2) {
            return false;
        }
    }

    private static a b(Context context, String str) {
        if (t == null) {
            t = new a(context, str);
        } else if (!TextUtils.equals(str, t.b)) {
            t.b = str;
        }
        return t;
    }

    private b a(Context context) throws Throwable {
        return a(context, "", k.a(context), true);
    }

    public b a(Context context, String str) throws Throwable {
        return a(context, str, k.a(context), true);
    }

    private b a(Context context, String str, String str2) throws Throwable {
        return a(context, str, str2, true);
    }

    public final b a(Context context, String str, String str2, boolean z) throws Throwable {
        String name;
        String str3 = null;
        int i2 = 0;
        e eVar = new e(this.s);
        c a2 = eVar.a(new b(c(), a(str, a())), this.r);
        if (t == null) {
            t = new a(context, str2);
        } else if (!TextUtils.equals(str2, t.b)) {
            t.b = str2;
        }
        HttpResponse a3 = t.a(a2.b, a(a2.a, str));
        if (a3 != null) {
            Header[] allHeaders = a3.getAllHeaders();
            if (allHeaders != null && allHeaders.length > 0) {
                int length = allHeaders.length;
                while (true) {
                    if (i2 < length) {
                        Header header = allHeaders[i2];
                        if (header != null && (name = header.getName()) != null && name.equalsIgnoreCase(a)) {
                            str3 = header.getValue();
                            break;
                        }
                        i2++;
                    } else {
                        break;
                    }
                }
            }
        }
        b a4 = eVar.a(new c(Boolean.valueOf(str3).booleanValue(), b(a3)));
        if (a4 == null || !a(a4.a) || !z) {
            return a4;
        }
        return a(context, str, str2, false);
    }

    private static byte[] b(HttpResponse httpResponse) throws IllegalStateException, IOException {
        InputStream inputStream;
        ByteArrayOutputStream byteArrayOutputStream = null;
        byte[] bArr = new byte[1024];
        try {
            inputStream = httpResponse.getEntity().getContent();
            try {
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                while (true) {
                    try {
                        int read = inputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        byteArrayOutputStream2.write(bArr, 0, read);
                    } catch (Throwable th) {
                        th = th;
                        byteArrayOutputStream = byteArrayOutputStream2;
                    }
                }
                byte[] byteArray = byteArrayOutputStream2.toByteArray();
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e2) {
                    }
                }
                try {
                    byteArrayOutputStream2.close();
                } catch (Exception e3) {
                }
                return byteArray;
            } catch (Throwable th2) {
                th = th2;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e4) {
                    }
                }
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Exception e5) {
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            inputStream = null;
        }
    }
}

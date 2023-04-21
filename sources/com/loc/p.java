package com.loc;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.alipay.sdk.util.j;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ConfigManager */
public class p {

    /* compiled from: ConfigManager */
    public static class a {
        public JSONObject a;
        public JSONObject b;
        public JSONObject c;
        public JSONObject d;
        public JSONObject e;
        public C0024a f;
        public c g;
        public b h;

        /* renamed from: com.loc.p$a$a  reason: collision with other inner class name */
        /* compiled from: ConfigManager */
        public static class C0024a {
            public boolean a;
            public boolean b;
        }

        /* compiled from: ConfigManager */
        public static class b {
            public String a;
            public String b;
        }

        /* compiled from: ConfigManager */
        public static class c {
            public String a;
            public String b;
            public String c;
        }
    }

    public static a a(byte[] bArr) {
        JSONObject jSONObject;
        boolean z;
        boolean z2 = false;
        a aVar = new a();
        if (bArr != null) {
            try {
                if (bArr.length != 0) {
                    JSONObject jSONObject2 = new JSONObject(new String(bArr, "UTF-8"));
                    if (com.alipay.sdk.cons.a.e.equals(a(jSONObject2, "status")) && jSONObject2.has(j.c) && (jSONObject = jSONObject2.getJSONObject(j.c)) != null) {
                        if (w.a(jSONObject, "exception")) {
                            z = b(jSONObject.getJSONObject("exception"));
                        } else {
                            z = false;
                        }
                        if (w.a(jSONObject, "common")) {
                            z2 = a(jSONObject.getJSONObject("common"));
                        }
                        a.C0024a aVar2 = new a.C0024a();
                        aVar2.a = z;
                        aVar2.b = z2;
                        aVar.f = aVar2;
                        if (jSONObject.has("sdkupdate")) {
                            JSONObject jSONObject3 = jSONObject.getJSONObject("sdkupdate");
                            a.c cVar = new a.c();
                            a(jSONObject3, cVar);
                            aVar.g = cVar;
                        }
                        if (w.a(jSONObject, "sdkcoordinate")) {
                            JSONObject jSONObject4 = jSONObject.getJSONObject("sdkcoordinate");
                            a.b bVar = new a.b();
                            a(jSONObject4, bVar);
                            aVar.h = bVar;
                        }
                        if (w.a(jSONObject, "callamap")) {
                            aVar.d = jSONObject.getJSONObject("callamap");
                        }
                        if (w.a(jSONObject, "ca")) {
                            aVar.e = jSONObject.getJSONObject("ca");
                        }
                        if (w.a(jSONObject, "locate")) {
                            aVar.c = jSONObject.getJSONObject("locate");
                        }
                        if (w.a(jSONObject, "callamappro")) {
                            aVar.b = jSONObject.getJSONObject("callamappro");
                        }
                        if (w.a(jSONObject, "opflag")) {
                            aVar.a = jSONObject.getJSONObject("opflag");
                        }
                    }
                }
            } catch (JSONException e) {
                y.a(e, "ConfigManager", "loadConfig");
            } catch (UnsupportedEncodingException e2) {
                y.a(e2, "ConfigManager", "loadConfig");
            } catch (Throwable th) {
                y.a(th, "ConfigManager", "loadConfig");
            }
        }
        return aVar;
    }

    public static a a(Context context, v vVar, String str) {
        try {
            return a(new ap().a(new b(context, vVar, str)));
        } catch (l e) {
            y.a(e, "ConfigManager", "loadConfig");
        } catch (Throwable th) {
            y.a(th, "ConfigManager", "loadConfig");
        }
        return new a();
    }

    /* compiled from: ConfigManager */
    static class b extends at {
        private Context d;
        private v e;
        private String f = "";

        b(Context context, v vVar, String str) {
            this.d = context;
            this.e = vVar;
            this.f = str;
        }

        public Map<String, String> a() {
            HashMap hashMap = new HashMap();
            hashMap.put("User-Agent", this.e.c());
            hashMap.put("platinfo", String.format("platform=Android&sdkversion=%s&product=%s", new Object[]{this.e.b(), this.e.a()}));
            hashMap.put("logversion", "2.0");
            return hashMap;
        }

        public Map<String, String> b() {
            String l = q.l(this.d);
            if (!TextUtils.isEmpty(l)) {
                l = s.b(new StringBuilder(l).reverse().toString());
            }
            HashMap hashMap = new HashMap();
            hashMap.put("key", m.e(this.d));
            hashMap.put("opertype", this.f);
            hashMap.put("plattype", "android");
            hashMap.put("product", this.e.a());
            hashMap.put("version", this.e.b());
            hashMap.put("output", "json");
            hashMap.put("androidversion", Build.VERSION.SDK_INT + "");
            hashMap.put("deviceId", l);
            hashMap.put("abitype", Build.CPU_ABI);
            hashMap.put("ext", this.e.d());
            String a = o.a();
            String a2 = o.a(this.d, a, w.b((Map<String, String>) hashMap));
            hashMap.put("ts", a);
            hashMap.put("scode", a2);
            return hashMap;
        }

        public String c() {
            return "https://restapi.amap.com/v3/config/resource?";
        }
    }

    private static boolean a(String str) {
        if (str != null && str.equals(com.alipay.sdk.cons.a.e)) {
            return true;
        }
        return false;
    }

    public static String a(JSONObject jSONObject, String str) throws JSONException {
        if (jSONObject != null && jSONObject.has(str) && !jSONObject.getString(str).equals("[]")) {
            return jSONObject.optString(str);
        }
        return "";
    }

    private static void a(JSONObject jSONObject, a.b bVar) {
        if (jSONObject != null) {
            try {
                String a2 = a(jSONObject, "md5");
                String a3 = a(jSONObject, "url");
                bVar.b = a2;
                bVar.a = a3;
            } catch (JSONException e) {
                y.a(e, "ConfigManager", "parseSDKCoordinate");
            } catch (Throwable th) {
                y.a(th, "ConfigManager", "parseSDKCoordinate");
            }
        }
    }

    private static void a(JSONObject jSONObject, a.c cVar) {
        if (jSONObject != null) {
            try {
                String a2 = a(jSONObject, "md5");
                String a3 = a(jSONObject, "url");
                String a4 = a(jSONObject, "sdkversion");
                if (!TextUtils.isEmpty(a2) && !TextUtils.isEmpty(a3) && !TextUtils.isEmpty(a4)) {
                    cVar.a = a3;
                    cVar.b = a2;
                    cVar.c = a4;
                }
            } catch (JSONException e) {
                y.a(e, "ConfigManager", "parseSDKUpdate");
            } catch (Throwable th) {
                y.a(th, "ConfigManager", "parseSDKUpdate");
            }
        }
    }

    private static boolean a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return false;
        }
        try {
            return a(a(jSONObject.getJSONObject("commoninfo"), "com_isupload"));
        } catch (JSONException e) {
            y.a(e, "ConfigManager", "parseCommon");
            return false;
        } catch (Throwable th) {
            y.a(th, "ConfigManager", "parseCommon");
            return false;
        }
    }

    private static boolean b(JSONObject jSONObject) {
        if (jSONObject == null) {
            return false;
        }
        try {
            return a(a(jSONObject.getJSONObject("exceptinfo"), "ex_isupload"));
        } catch (JSONException e) {
            y.a(e, "ConfigManager", "parseException");
            return false;
        } catch (Throwable th) {
            y.a(th, "ConfigManager", "parseException");
            return false;
        }
    }
}

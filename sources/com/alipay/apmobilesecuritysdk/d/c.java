package com.alipay.apmobilesecuritysdk.d;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.f.e;
import com.alipay.apmobilesecuritysdk.f.f;
import com.alipay.b.a.a.a.a;
import com.alipay.b.a.a.b.b;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public final class c {
    public static Map<String, String> a(Context context) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        b a = b.a();
        HashMap hashMap = new HashMap();
        f a2 = e.a(context);
        String a3 = b.a(context);
        String b = b.b(context);
        String l = b.l(context);
        String o = b.o(context);
        String n = b.n(context);
        if (a2 != null) {
            if (a.a(a3)) {
                a3 = a2.a();
            }
            if (a.a(b)) {
                b = a2.b();
            }
            if (a.a(l)) {
                l = a2.c();
            }
            if (a.a(o)) {
                o = a2.d();
            }
            if (a.a(n)) {
                n = a2.e();
            }
            str = n;
            str2 = o;
            str3 = l;
            str4 = b;
            str5 = a3;
        } else {
            str = n;
            str2 = o;
            str3 = l;
            str4 = b;
            str5 = a3;
        }
        f fVar = new f(str5, str4, str3, str2, str);
        if (context != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("imei", fVar.a());
                jSONObject.put("imsi", fVar.b());
                jSONObject.put("mac", fVar.c());
                jSONObject.put("bluetoothmac", fVar.d());
                jSONObject.put("gsi", fVar.e());
                String jSONObject2 = jSONObject.toString();
                com.alipay.apmobilesecuritysdk.g.a.a("device_feature_file_name", "device_feature_file_key", jSONObject2);
                com.alipay.apmobilesecuritysdk.g.a.a(context, "device_feature_prefs_name", "device_feature_prefs_key", jSONObject2);
            } catch (Exception e) {
                com.alipay.apmobilesecuritysdk.c.a.a((Throwable) e);
            }
        }
        hashMap.put("AD1", str5);
        hashMap.put("AD2", str4);
        hashMap.put("AD3", b.g(context));
        hashMap.put("AD5", b.i(context));
        hashMap.put("AD6", b.j(context));
        hashMap.put("AD7", b.k(context));
        hashMap.put("AD8", str3);
        hashMap.put("AD9", b.m(context));
        hashMap.put("AD10", str);
        hashMap.put("AD11", b.d());
        hashMap.put("AD12", a.e());
        hashMap.put("AD13", b.f());
        hashMap.put("AD14", b.h());
        hashMap.put("AD15", b.i());
        hashMap.put("AD16", b.j());
        hashMap.put("AD17", "");
        hashMap.put("AD18", str2);
        hashMap.put("AD19", b.p(context));
        hashMap.put("AD20", b.k());
        hashMap.put("AD21", b.f(context));
        hashMap.put("AD22", "");
        hashMap.put("AD23", b.l());
        hashMap.put("AD24", a.f(b.h(context)));
        hashMap.put("AD26", b.e(context));
        hashMap.put("AD27", b.q());
        hashMap.put("AD28", b.s());
        hashMap.put("AD29", b.u());
        hashMap.put("AD30", b.r());
        hashMap.put("AD31", b.t());
        hashMap.put("AD32", b.o());
        hashMap.put("AD33", b.p());
        hashMap.put("AD34", b.s(context));
        hashMap.put("AD35", b.t(context));
        hashMap.put("AD36", b.r(context));
        hashMap.put("AD37", b.n());
        hashMap.put("AD38", b.m());
        hashMap.put("AD39", b.c(context));
        hashMap.put("AD40", b.d(context));
        hashMap.put("AD41", b.b());
        hashMap.put("AD42", b.c());
        hashMap.put("AL3", b.q(context));
        return hashMap;
    }
}

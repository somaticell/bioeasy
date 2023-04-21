package com.alipay.sdk.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.TextView;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import com.alipay.mobilesecuritysdk.face.SecurityClientMobile;
import com.alipay.sdk.cons.a;
import com.alipay.sdk.sys.b;
import com.alipay.sdk.util.h;
import com.alipay.sdk.util.k;
import com.alipay.sdk.util.l;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class c {
    private static final String d = "virtualImeiAndImsi";
    private static final String e = "virtual_imei";
    private static final String f = "virtual_imsi";
    private static c g;
    public String a;
    public String b = "sdk-and-lite";
    public String c;

    private String c() {
        return this.c;
    }

    private c() {
    }

    public static synchronized c a() {
        c cVar;
        synchronized (c.class) {
            if (g == null) {
                g = new c();
            }
            cVar = g;
        }
        return cVar;
    }

    public final synchronized void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            PreferenceManager.getDefaultSharedPreferences(b.a().a).edit().putString(com.alipay.sdk.cons.b.i, str).commit();
            a.c = str;
        }
    }

    private static String d() {
        return a.e;
    }

    private static String a(Context context) {
        return Float.toString(new TextView(context).getTextSize());
    }

    private static String e() {
        return "-1;-1";
    }

    private String a(com.alipay.sdk.tid.b bVar) {
        Context context = b.a().a;
        com.alipay.sdk.util.a a2 = com.alipay.sdk.util.a.a(context);
        if (TextUtils.isEmpty(this.a)) {
            String b2 = l.b();
            String c2 = l.c();
            String f2 = l.f(context);
            String a3 = k.a(context);
            this.a = "Msp/15.3.6" + " (" + b2 + h.b + c2 + h.b + f2 + h.b + a3.substring(0, a3.indexOf("://")) + h.b + l.g(context) + h.b + Float.toString(new TextView(context).getTextSize());
        }
        String str = com.alipay.sdk.util.a.b(context).p;
        String a4 = a2.a();
        String b3 = a2.b();
        Context context2 = b.a().a;
        SharedPreferences sharedPreferences = context2.getSharedPreferences(d, 0);
        String string = sharedPreferences.getString(f, (String) null);
        if (TextUtils.isEmpty(string)) {
            if (TextUtils.isEmpty(com.alipay.sdk.tid.b.a().a)) {
                String c3 = b.a().c();
                string = TextUtils.isEmpty(c3) ? b() : c3.substring(3, 18);
            } else {
                string = com.alipay.sdk.util.a.a(context2).a();
            }
            sharedPreferences.edit().putString(f, string).commit();
        }
        String str2 = string;
        Context context3 = b.a().a;
        SharedPreferences sharedPreferences2 = context3.getSharedPreferences(d, 0);
        String string2 = sharedPreferences2.getString(e, (String) null);
        if (TextUtils.isEmpty(string2)) {
            if (TextUtils.isEmpty(com.alipay.sdk.tid.b.a().a)) {
                string2 = b();
            } else {
                string2 = com.alipay.sdk.util.a.a(context3).b();
            }
            sharedPreferences2.edit().putString(e, string2).commit();
        }
        String str3 = string2;
        if (bVar != null) {
            this.c = bVar.b;
        }
        String replace = Build.MANUFACTURER.replace(h.b, " ");
        String replace2 = Build.MODEL.replace(h.b, " ");
        boolean b4 = b.b();
        String str4 = a2.a;
        WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        String ssid = connectionInfo != null ? connectionInfo.getSSID() : "-1";
        WifiInfo connectionInfo2 = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        String bssid = connectionInfo2 != null ? connectionInfo2.getBSSID() : BLEServiceApi.RESULT_NG;
        StringBuilder sb = new StringBuilder();
        sb.append(this.a).append(h.b).append(str).append(h.b).append("-1;-1").append(h.b).append(a.e).append(h.b).append(a4).append(h.b).append(b3).append(h.b).append(this.c).append(h.b).append(replace).append(h.b).append(replace2).append(h.b).append(b4).append(h.b).append(str4).append(";-1;-1;").append(this.b).append(h.b).append(str2).append(h.b).append(str3).append(h.b).append(ssid).append(h.b).append(bssid);
        if (bVar != null) {
            HashMap hashMap = new HashMap();
            hashMap.put(com.alipay.sdk.cons.b.c, bVar.a);
            hashMap.put(com.alipay.sdk.cons.b.g, b.a().c());
            String b5 = b(context, hashMap);
            if (!TextUtils.isEmpty(b5)) {
                sb.append(h.b).append(b5);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    private static String f() {
        Context context = b.a().a;
        SharedPreferences sharedPreferences = context.getSharedPreferences(d, 0);
        String string = sharedPreferences.getString(e, (String) null);
        if (TextUtils.isEmpty(string)) {
            if (TextUtils.isEmpty(com.alipay.sdk.tid.b.a().a)) {
                string = b();
            } else {
                string = com.alipay.sdk.util.a.a(context).b();
            }
            sharedPreferences.edit().putString(e, string).commit();
        }
        return string;
    }

    private static String g() {
        Context context = b.a().a;
        SharedPreferences sharedPreferences = context.getSharedPreferences(d, 0);
        String string = sharedPreferences.getString(f, (String) null);
        if (TextUtils.isEmpty(string)) {
            if (TextUtils.isEmpty(com.alipay.sdk.tid.b.a().a)) {
                String c2 = b.a().c();
                if (TextUtils.isEmpty(c2)) {
                    string = b();
                } else {
                    string = c2.substring(3, 18);
                }
            } else {
                string = com.alipay.sdk.util.a.a(context).a();
            }
            sharedPreferences.edit().putString(f, string).commit();
        }
        return string;
    }

    public static String b() {
        return Long.toHexString(System.currentTimeMillis()) + (new Random().nextInt(9000) + 1000);
    }

    private static String b(Context context) {
        WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        if (connectionInfo != null) {
            return connectionInfo.getSSID();
        }
        return "-1";
    }

    private static String c(Context context) {
        WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        if (connectionInfo != null) {
            return connectionInfo.getBSSID();
        }
        return BLEServiceApi.RESULT_NG;
    }

    /* access modifiers changed from: package-private */
    public static String a(Context context, HashMap<String, String> hashMap) {
        String str = "";
        try {
            str = SecurityClientMobile.GetApdid(context, hashMap);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.e, com.alipay.sdk.app.statistic.c.g, th);
        }
        if (TextUtils.isEmpty(str)) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.e, com.alipay.sdk.app.statistic.c.h, "apdid == null");
        }
        return str;
    }

    public final String b(Context context, HashMap<String, String> hashMap) {
        try {
            return (String) Executors.newFixedThreadPool(2).submit(new d(this, context, hashMap)).get(3000, TimeUnit.MILLISECONDS);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.e, com.alipay.sdk.app.statistic.c.i, th);
            return "";
        }
    }
}

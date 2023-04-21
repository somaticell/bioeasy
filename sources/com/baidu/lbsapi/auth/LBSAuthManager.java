package com.baidu.lbsapi.auth;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import com.alipay.sdk.cons.a;
import com.baidu.android.bbalbs.common.util.CommonParam;
import com.baidu.lbsapi.auth.c;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class LBSAuthManager {
    public static final int CODE_AUTHENTICATE_SUCC = 0;
    public static final int CODE_AUTHENTICATING = 602;
    public static final int CODE_INNER_ERROR = -1;
    public static final int CODE_KEY_NOT_EXIST = 101;
    public static final int CODE_NETWORK_FAILED = -11;
    public static final int CODE_NETWORK_INVALID = -10;
    public static final int CODE_UNAUTHENTICATE = 601;
    public static final String VERSION = "1.0.20";
    /* access modifiers changed from: private */
    public static Context a;
    /* access modifiers changed from: private */
    public static l d = null;
    private static int e = 0;
    /* access modifiers changed from: private */
    public static Hashtable<String, LBSAuthManagerListener> f = new Hashtable<>();
    private static LBSAuthManager g;
    private c b = null;
    private e c = null;
    private final Handler h = new h(this, Looper.getMainLooper());

    private LBSAuthManager(Context context) {
        a = context;
        if (d != null && !d.isAlive()) {
            d = null;
        }
        a.b("BaiduApiAuth SDK Version:1.0.20");
        d();
    }

    private int a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.has("status")) {
                jSONObject.put("status", -1);
            }
            int i = jSONObject.getInt("status");
            if (jSONObject.has("current") && i == 0) {
                long j = jSONObject.getLong("current");
                long currentTimeMillis = System.currentTimeMillis();
                if (((double) (currentTimeMillis - j)) / 3600000.0d >= 24.0d) {
                    i = 601;
                } else {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    if (!simpleDateFormat.format(Long.valueOf(currentTimeMillis)).equals(simpleDateFormat.format(Long.valueOf(j)))) {
                        i = 601;
                    }
                }
            }
            if (jSONObject.has("current") && i == 602) {
                if (((double) ((System.currentTimeMillis() - jSONObject.getLong("current")) / 1000)) > 180.0d) {
                    return CODE_UNAUTHENTICATE;
                }
            }
            return i;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x007c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String a(int r7) throws java.io.IOException {
        /*
            r6 = this;
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ FileNotFoundException -> 0x0042, IOException -> 0x0056, all -> 0x006a }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x0042, IOException -> 0x0056, all -> 0x006a }
            r2.<init>()     // Catch:{ FileNotFoundException -> 0x0042, IOException -> 0x0056, all -> 0x006a }
            java.lang.String r3 = "/proc/"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ FileNotFoundException -> 0x0042, IOException -> 0x0056, all -> 0x006a }
            java.lang.StringBuilder r2 = r2.append(r7)     // Catch:{ FileNotFoundException -> 0x0042, IOException -> 0x0056, all -> 0x006a }
            java.lang.String r3 = "/cmdline"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ FileNotFoundException -> 0x0042, IOException -> 0x0056, all -> 0x006a }
            java.lang.String r2 = r2.toString()     // Catch:{ FileNotFoundException -> 0x0042, IOException -> 0x0056, all -> 0x006a }
            r1.<init>(r2)     // Catch:{ FileNotFoundException -> 0x0042, IOException -> 0x0056, all -> 0x006a }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0042, IOException -> 0x0056, all -> 0x006a }
            r3.<init>(r1)     // Catch:{ FileNotFoundException -> 0x0042, IOException -> 0x0056, all -> 0x006a }
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ FileNotFoundException -> 0x0096, IOException -> 0x008d, all -> 0x0080 }
            r2.<init>(r3)     // Catch:{ FileNotFoundException -> 0x0096, IOException -> 0x008d, all -> 0x0080 }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x0091, all -> 0x0086 }
            r1.<init>(r2)     // Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x0091, all -> 0x0086 }
            java.lang.String r0 = r1.readLine()     // Catch:{ FileNotFoundException -> 0x009d, IOException -> 0x0094, all -> 0x008b }
            if (r1 == 0) goto L_0x0037
            r1.close()
        L_0x0037:
            if (r2 == 0) goto L_0x003c
            r2.close()
        L_0x003c:
            if (r3 == 0) goto L_0x0041
            r3.close()
        L_0x0041:
            return r0
        L_0x0042:
            r1 = move-exception
            r1 = r0
            r2 = r0
            r3 = r0
        L_0x0046:
            if (r1 == 0) goto L_0x004b
            r1.close()
        L_0x004b:
            if (r2 == 0) goto L_0x0050
            r2.close()
        L_0x0050:
            if (r3 == 0) goto L_0x0041
            r3.close()
            goto L_0x0041
        L_0x0056:
            r1 = move-exception
            r1 = r0
            r2 = r0
            r3 = r0
        L_0x005a:
            if (r1 == 0) goto L_0x005f
            r1.close()
        L_0x005f:
            if (r2 == 0) goto L_0x0064
            r2.close()
        L_0x0064:
            if (r3 == 0) goto L_0x0041
            r3.close()
            goto L_0x0041
        L_0x006a:
            r1 = move-exception
            r2 = r0
            r3 = r0
            r5 = r1
            r1 = r0
            r0 = r5
        L_0x0070:
            if (r1 == 0) goto L_0x0075
            r1.close()
        L_0x0075:
            if (r2 == 0) goto L_0x007a
            r2.close()
        L_0x007a:
            if (r3 == 0) goto L_0x007f
            r3.close()
        L_0x007f:
            throw r0
        L_0x0080:
            r1 = move-exception
            r2 = r0
            r5 = r0
            r0 = r1
            r1 = r5
            goto L_0x0070
        L_0x0086:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L_0x0070
        L_0x008b:
            r0 = move-exception
            goto L_0x0070
        L_0x008d:
            r1 = move-exception
            r1 = r0
            r2 = r0
            goto L_0x005a
        L_0x0091:
            r1 = move-exception
            r1 = r0
            goto L_0x005a
        L_0x0094:
            r4 = move-exception
            goto L_0x005a
        L_0x0096:
            r1 = move-exception
            r1 = r0
            r2 = r0
            goto L_0x0046
        L_0x009a:
            r1 = move-exception
            r1 = r0
            goto L_0x0046
        L_0x009d:
            r4 = move-exception
            goto L_0x0046
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.lbsapi.auth.LBSAuthManager.a(int):java.lang.String");
    }

    private String a(Context context) {
        int myPid = Process.myPid();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo next : runningAppProcesses) {
                if (next.pid == myPid) {
                    return next.processName;
                }
            }
        }
        String str = null;
        try {
            str = a(myPid);
        } catch (IOException e2) {
        }
        return str == null ? a.getPackageName() : str;
    }

    private String a(Context context, String str) {
        LBSAuthManagerListener lBSAuthManagerListener;
        String str2 = "";
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo.metaData == null) {
                LBSAuthManagerListener lBSAuthManagerListener2 = f.get(str);
                if (lBSAuthManagerListener2 != null) {
                    lBSAuthManagerListener2.onAuthResult(101, ErrorMessage.a(101, "AndroidManifest.xml的application中没有meta-data标签"));
                }
                return str2;
            }
            str2 = applicationInfo.metaData.getString("com.baidu.lbsapi.API_KEY");
            if ((str2 == null || str2.equals("")) && (lBSAuthManagerListener = f.get(str)) != null) {
                lBSAuthManagerListener.onAuthResult(101, ErrorMessage.a(101, "无法在AndroidManifest.xml中获取com.baidu.android.lbs.API_KEY的值"));
            }
            return str2;
        } catch (PackageManager.NameNotFoundException e2) {
            LBSAuthManagerListener lBSAuthManagerListener3 = f.get(str);
            if (lBSAuthManagerListener3 != null) {
                lBSAuthManagerListener3.onAuthResult(101, ErrorMessage.a(101, "无法在AndroidManifest.xml中获取com.baidu.android.lbs.API_KEY的值"));
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void a(String str, String str2) {
        if (str == null) {
            str = e();
        }
        Message obtainMessage = this.h.obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.has("status")) {
                jSONObject.put("status", -1);
            }
            if (!jSONObject.has("current")) {
                jSONObject.put("current", System.currentTimeMillis());
            }
            c(jSONObject.toString());
            if (jSONObject.has("current")) {
                jSONObject.remove("current");
            }
            obtainMessage.what = jSONObject.getInt("status");
            obtainMessage.obj = jSONObject.toString();
            Bundle bundle = new Bundle();
            bundle.putString("listenerKey", str2);
            obtainMessage.setData(bundle);
            this.h.sendMessage(obtainMessage);
        } catch (JSONException e2) {
            e2.printStackTrace();
            obtainMessage.what = -1;
            obtainMessage.obj = new JSONObject();
            Bundle bundle2 = new Bundle();
            bundle2.putString("listenerKey", str2);
            obtainMessage.setData(bundle2);
            this.h.sendMessage(obtainMessage);
        }
        d.c();
        e--;
        if (a.a) {
            a.a("httpRequest called mAuthCounter-- = " + e);
        }
        if (e == 0) {
            d.a();
            if (d != null) {
                d = null;
            }
        }
        return;
    }

    /* access modifiers changed from: private */
    public void a(boolean z, String str, Hashtable<String, String> hashtable, String str2) {
        String a2 = a(a, str2);
        if (a2 != null && !a2.equals("")) {
            HashMap hashMap = new HashMap();
            hashMap.put("url", "https://api.map.baidu.com/sdkcs/verify");
            a.a("url:https://api.map.baidu.com/sdkcs/verify");
            hashMap.put("output", "json");
            hashMap.put("ak", a2);
            a.a("ak:" + a2);
            hashMap.put("mcode", b.a(a));
            hashMap.put("from", "lbs_yunsdk");
            if (hashtable != null && hashtable.size() > 0) {
                for (Map.Entry next : hashtable.entrySet()) {
                    String str3 = (String) next.getKey();
                    String str4 = (String) next.getValue();
                    if (!TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str4)) {
                        hashMap.put(str3, str4);
                    }
                }
            }
            String str5 = "";
            try {
                str5 = CommonParam.a(a);
            } catch (Exception e2) {
            }
            a.a("cuid:" + str5);
            if (!TextUtils.isEmpty(str5)) {
                hashMap.put("cuid", str5);
            } else {
                hashMap.put("cuid", "");
            }
            hashMap.put("pcn", a.getPackageName());
            hashMap.put("version", VERSION);
            String str6 = "";
            try {
                str6 = b.c(a);
            } catch (Exception e3) {
            }
            if (!TextUtils.isEmpty(str6)) {
                hashMap.put("macaddr", str6);
            } else {
                hashMap.put("macaddr", "");
            }
            String str7 = "";
            try {
                str7 = b.a();
            } catch (Exception e4) {
            }
            if (!TextUtils.isEmpty(str7)) {
                hashMap.put("language", str7);
            } else {
                hashMap.put("language", "");
            }
            if (z) {
                hashMap.put("force", z ? a.e : "0");
            }
            if (str == null) {
                hashMap.put("from_service", "");
            } else {
                hashMap.put("from_service", str);
            }
            this.b = new c(a);
            this.b.a((HashMap<String, String>) hashMap, (c.a<String>) new j(this, str2));
        }
    }

    /* access modifiers changed from: private */
    public void a(boolean z, String str, Hashtable<String, String> hashtable, String[] strArr, String str2) {
        String a2 = a(a, str2);
        if (a2 != null && !a2.equals("")) {
            HashMap hashMap = new HashMap();
            hashMap.put("url", "https://api.map.baidu.com/sdkcs/verify");
            hashMap.put("output", "json");
            hashMap.put("ak", a2);
            hashMap.put("from", "lbs_yunsdk");
            if (hashtable != null && hashtable.size() > 0) {
                for (Map.Entry next : hashtable.entrySet()) {
                    String str3 = (String) next.getKey();
                    String str4 = (String) next.getValue();
                    if (!TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str4)) {
                        hashMap.put(str3, str4);
                    }
                }
            }
            String str5 = "";
            try {
                str5 = CommonParam.a(a);
            } catch (Exception e2) {
            }
            if (!TextUtils.isEmpty(str5)) {
                hashMap.put("cuid", str5);
            } else {
                hashMap.put("cuid", "");
            }
            hashMap.put("pcn", a.getPackageName());
            hashMap.put("version", VERSION);
            String str6 = "";
            try {
                str6 = b.c(a);
            } catch (Exception e3) {
            }
            if (!TextUtils.isEmpty(str6)) {
                hashMap.put("macaddr", str6);
            } else {
                hashMap.put("macaddr", "");
            }
            String str7 = "";
            try {
                str7 = b.a();
            } catch (Exception e4) {
            }
            if (!TextUtils.isEmpty(str7)) {
                hashMap.put("language", str7);
            } else {
                hashMap.put("language", "");
            }
            if (z) {
                hashMap.put("force", z ? a.e : "0");
            }
            if (str == null) {
                hashMap.put("from_service", "");
            } else {
                hashMap.put("from_service", str);
            }
            this.c = new e(a);
            this.c.a(hashMap, strArr, new k(this, str2));
        }
    }

    /* access modifiers changed from: private */
    public boolean b(String str) {
        String str2;
        String a2 = a(a, str);
        try {
            JSONObject jSONObject = new JSONObject(e());
            if (!jSONObject.has("ak")) {
                return true;
            }
            str2 = jSONObject.getString("ak");
            return (a2 == null || str2 == null || a2.equals(str2)) ? false : true;
        } catch (JSONException e2) {
            e2.printStackTrace();
            str2 = "";
        }
    }

    private void c(String str) {
        a.getSharedPreferences("authStatus_" + a(a), 0).edit().putString("status", str).commit();
    }

    private void d() {
        synchronized (LBSAuthManager.class) {
            if (d == null) {
                d = new l(com.alipay.sdk.app.statistic.c.d);
                d.start();
                while (d.a == null) {
                    try {
                        if (a.a) {
                            a.a("wait for create auth thread.");
                        }
                        Thread.sleep(3);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    private String e() {
        return a.getSharedPreferences("authStatus_" + a(a), 0).getString("status", "{\"status\":601}");
    }

    public static LBSAuthManager getInstance(Context context) {
        if (g == null) {
            synchronized (LBSAuthManager.class) {
                if (g == null) {
                    g = new LBSAuthManager(context);
                }
            }
        } else if (context != null) {
            a = context;
        } else if (a.a) {
            a.c("input context is null");
            new RuntimeException("here").printStackTrace();
        }
        return g;
    }

    public int authenticate(boolean z, String str, Hashtable<String, String> hashtable, LBSAuthManagerListener lBSAuthManagerListener) {
        int i;
        synchronized (LBSAuthManager.class) {
            String str2 = System.currentTimeMillis() + "";
            if (lBSAuthManagerListener != null) {
                f.put(str2, lBSAuthManagerListener);
            }
            String a2 = a(a, str2);
            if (a2 == null || a2.equals("")) {
                i = 101;
            } else {
                e++;
                if (a.a) {
                    a.a(" mAuthCounter  ++ = " + e);
                }
                String e2 = e();
                if (a.a) {
                    a.a("getAuthMessage from cache:" + e2);
                }
                i = a(e2);
                if (i == 601) {
                    try {
                        c(new JSONObject().put("status", CODE_AUTHENTICATING).toString());
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                    }
                }
                d();
                if (a.a) {
                    a.a("mThreadLooper.mHandler = " + d.a);
                }
                if (d == null || d.a == null) {
                    i = -1;
                } else {
                    d.a.post(new i(this, i, z, str2, str, hashtable));
                }
            }
        }
        return i;
    }

    public String getCUID() {
        if (a == null) {
            return "";
        }
        try {
            return CommonParam.a(a);
        } catch (Exception e2) {
            if (!a.a) {
                return "";
            }
            e2.printStackTrace();
            return "";
        }
    }

    public String getKey() {
        if (a == null) {
            return "";
        }
        try {
            return getPublicKey(a);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public String getMCode() {
        return a == null ? "" : b.a(a);
    }

    public String getPublicKey(Context context) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("com.baidu.lbsapi.API_KEY");
    }
}

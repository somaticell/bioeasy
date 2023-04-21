package com.alipay.sdk.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import cn.com.bioeasy.app.utils.ListUtils;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.h;
import com.alipay.sdk.app.i;
import com.alipay.sdk.app.statistic.c;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

@SuppressLint({"SetJavaScriptEnabled", "DefaultLocale"})
public final class l {
    static final String a = "com.alipay.android.app";
    public static final int b = 99;
    public static final int c = 73;
    private static final String d = "com.eg.android.AlipayGphone";
    private static final String e = "com.eg.android.AlipayGphoneRC";

    public static String a() {
        if (EnvUtils.isSandBox()) {
            return "com.eg.android.AlipayGphoneRC";
        }
        return d;
    }

    public static Map<String, String> a(String str) {
        HashMap hashMap = new HashMap();
        for (String str2 : str.split(com.alipay.sdk.sys.a.b)) {
            int indexOf = str2.indexOf("=", 1);
            hashMap.put(str2.substring(0, indexOf), URLDecoder.decode(str2.substring(indexOf + 1)));
        }
        return hashMap;
    }

    public static String a(String str, String str2, String str3) {
        try {
            int length = str.length() + str3.indexOf(str);
            if (length <= str.length()) {
                return "";
            }
            int i = 0;
            if (!TextUtils.isEmpty(str2)) {
                i = str3.indexOf(str2, length);
            }
            if (i <= 0) {
                return str3.substring(length);
            }
            return str3.substring(length, i);
        } catch (Throwable th) {
            return "";
        }
    }

    public static String b(String str, String str2, String str3) {
        try {
            int length = str.length() + str3.indexOf(str);
            int i = 0;
            if (!TextUtils.isEmpty(str2)) {
                i = str3.indexOf(str2, length);
            }
            if (i <= 0) {
                return str3.substring(length);
            }
            return str3.substring(length, i);
        } catch (Throwable th) {
            return "";
        }
    }

    public static String a(byte[] bArr) {
        try {
            String obj = ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr))).getPublicKey().toString();
            if (obj.indexOf("modulus") != -1) {
                return obj.substring(obj.indexOf("modulus") + 8, obj.lastIndexOf(ListUtils.DEFAULT_JOIN_SEPARATOR)).trim();
            }
        } catch (Exception e2) {
            com.alipay.sdk.app.statistic.a.a(c.d, c.n, (Throwable) e2);
        }
        return null;
    }

    public static a a(Context context) {
        return a(context, a());
    }

    private static a a(Context context, String str) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 192);
            if (!a(packageInfo)) {
                try {
                    packageInfo = c(context, str);
                } catch (Throwable th) {
                    com.alipay.sdk.app.statistic.a.a(c.d, c.m, th);
                }
            }
        } catch (Throwable th2) {
            com.alipay.sdk.app.statistic.a.a(c.d, c.m, th2);
            packageInfo = null;
        }
        if (!a(packageInfo) || packageInfo == null) {
            return null;
        }
        a aVar = new a();
        aVar.a = packageInfo.signatures;
        aVar.b = packageInfo.versionCode;
        return aVar;
    }

    private static boolean a(PackageInfo packageInfo) {
        String str = "";
        boolean z = false;
        if (packageInfo == null) {
            str = str + "info == null";
        } else if (packageInfo.signatures == null) {
            str = str + "info.signatures == null";
        } else if (packageInfo.signatures.length <= 0) {
            str = str + "info.signatures.length <= 0";
        } else {
            z = true;
        }
        if (!z) {
            com.alipay.sdk.app.statistic.a.a(c.d, c.l, str);
        }
        return z;
    }

    private static PackageInfo b(Context context, String str) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getPackageInfo(str, 192);
    }

    private static PackageInfo c(Context context, String str) {
        for (PackageInfo next : context.getPackageManager().getInstalledPackages(192)) {
            if (next.packageName.equals(str)) {
                return next;
            }
        }
        return null;
    }

    private static a b(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return null;
        }
        a aVar = new a();
        aVar.a = packageInfo.signatures;
        aVar.b = packageInfo.versionCode;
        return aVar;
    }

    public static class a {
        public Signature[] a;
        public int b;

        public final boolean a() {
            if (this.a == null || this.a.length <= 0) {
                return false;
            }
            int i = 0;
            while (i < this.a.length) {
                String a2 = l.a(this.a[i].toByteArray());
                if (a2 == null || TextUtils.equals(a2, com.alipay.sdk.cons.a.h)) {
                    i++;
                } else {
                    com.alipay.sdk.app.statistic.a.a(c.b, c.t, a2);
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean b(Context context) {
        try {
            if (context.getPackageManager().getPackageInfo(a, 128) == null) {
                return false;
            }
            return true;
        } catch (PackageManager.NameNotFoundException e2) {
            return false;
        }
    }

    public static boolean c(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(a(), 128);
            if (packageInfo != null && packageInfo.versionCode > 73) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(c.b, c.B, th);
            return false;
        }
    }

    public static boolean d(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(a(), 128);
            if (packageInfo != null && packageInfo.versionCode < 99) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    public static String e(Context context) {
        String b2 = b();
        String c2 = c();
        String f = f(context);
        return " (" + b2 + h.b + c2 + h.b + f + ";;" + g(context) + ")(sdk android)";
    }

    public static String b() {
        return "Android " + Build.VERSION.RELEASE;
    }

    public static WebView a(Activity activity, String str, String str2) {
        Context applicationContext = activity.getApplicationContext();
        if (!TextUtils.isEmpty(str2)) {
            CookieSyncManager.createInstance(applicationContext).sync();
            CookieManager.getInstance().setCookie(str, str2);
            CookieSyncManager.getInstance().sync();
        }
        LinearLayout linearLayout = new LinearLayout(applicationContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        linearLayout.setOrientation(1);
        activity.setContentView(linearLayout, layoutParams);
        WebView webView = new WebView(applicationContext);
        layoutParams.weight = 1.0f;
        webView.setVisibility(0);
        linearLayout.addView(webView, layoutParams);
        WebSettings settings = webView.getSettings();
        settings.setUserAgentString(settings.getUserAgentString() + e(applicationContext));
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMinimumFontSize(settings.getMinimumFontSize() + 8);
        settings.setAllowFileAccess(false);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        webView.setVerticalScrollbarOverlay(true);
        webView.setDownloadListener(new m(applicationContext));
        if (Build.VERSION.SDK_INT >= 7) {
            try {
                Method method = webView.getSettings().getClass().getMethod("setDomStorageEnabled", new Class[]{Boolean.TYPE});
                if (method != null) {
                    method.invoke(webView.getSettings(), new Object[]{true});
                }
            } catch (Exception e2) {
            }
        }
        try {
            webView.removeJavascriptInterface("searchBoxJavaBridge_");
            webView.removeJavascriptInterface("accessibility");
            webView.removeJavascriptInterface("accessibilityTraversal");
        } catch (Throwable th) {
        }
        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setCacheMode(2);
        }
        webView.loadUrl(str);
        return webView;
    }

    public static String c() {
        String e2 = e();
        int indexOf = e2.indexOf("-");
        if (indexOf != -1) {
            e2 = e2.substring(0, indexOf);
        }
        int indexOf2 = e2.indexOf("\n");
        if (indexOf2 != -1) {
            e2 = e2.substring(0, indexOf2);
        }
        return "Linux " + e2;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String e() {
        /*
            r3 = 4
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ IOException -> 0x002e }
            java.io.FileReader r1 = new java.io.FileReader     // Catch:{ IOException -> 0x002e }
            java.lang.String r2 = "/proc/version"
            r1.<init>(r2)     // Catch:{ IOException -> 0x002e }
            r2 = 256(0x100, float:3.59E-43)
            r0.<init>(r1, r2)     // Catch:{ IOException -> 0x002e }
            java.lang.String r1 = r0.readLine()     // Catch:{ all -> 0x0029 }
            r0.close()     // Catch:{ IOException -> 0x002e }
            java.lang.String r0 = "\\w+\\s+\\w+\\s+([^\\s]+)\\s+\\(([^\\s@]+(?:@[^\\s.]+)?)[^)]*\\)\\s+\\((?:[^(]*\\([^)]*\\))?[^)]*\\)\\s+([^\\s]+)\\s+(?:PREEMPT\\s+)?(.+)"
            java.util.regex.Pattern r0 = java.util.regex.Pattern.compile(r0)     // Catch:{ IOException -> 0x002e }
            java.util.regex.Matcher r0 = r0.matcher(r1)     // Catch:{ IOException -> 0x002e }
            boolean r1 = r0.matches()     // Catch:{ IOException -> 0x002e }
            if (r1 != 0) goto L_0x0032
            java.lang.String r0 = "Unavailable"
        L_0x0028:
            return r0
        L_0x0029:
            r1 = move-exception
            r0.close()     // Catch:{ IOException -> 0x002e }
            throw r1     // Catch:{ IOException -> 0x002e }
        L_0x002e:
            r0 = move-exception
            java.lang.String r0 = "Unavailable"
            goto L_0x0028
        L_0x0032:
            int r1 = r0.groupCount()     // Catch:{ IOException -> 0x002e }
            if (r1 >= r3) goto L_0x003b
            java.lang.String r0 = "Unavailable"
            goto L_0x0028
        L_0x003b:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x002e }
            r2 = 1
            java.lang.String r2 = r0.group(r2)     // Catch:{ IOException -> 0x002e }
            r1.<init>(r2)     // Catch:{ IOException -> 0x002e }
            java.lang.String r2 = "\n"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ IOException -> 0x002e }
            r2 = 2
            java.lang.String r2 = r0.group(r2)     // Catch:{ IOException -> 0x002e }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ IOException -> 0x002e }
            java.lang.String r2 = " "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ IOException -> 0x002e }
            r2 = 3
            java.lang.String r2 = r0.group(r2)     // Catch:{ IOException -> 0x002e }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ IOException -> 0x002e }
            java.lang.String r2 = "\n"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ IOException -> 0x002e }
            r2 = 4
            java.lang.String r0 = r0.group(r2)     // Catch:{ IOException -> 0x002e }
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ IOException -> 0x002e }
            java.lang.String r0 = r0.toString()     // Catch:{ IOException -> 0x002e }
            goto L_0x0028
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.util.l.e():java.lang.String");
    }

    public static String f(Context context) {
        return context.getResources().getConfiguration().locale.toString();
    }

    public static String g(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels + "*" + displayMetrics.heightPixels;
    }

    private static DisplayMetrics j(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    private static String k(Context context) {
        String a2 = k.a(context);
        return a2.substring(0, a2.indexOf("://"));
    }

    private static String f() {
        return "-1;-1";
    }

    public static String d() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 24; i++) {
            switch (random.nextInt(3)) {
                case 0:
                    sb.append(String.valueOf((char) ((int) Math.round((Math.random() * 25.0d) + 65.0d))));
                    break;
                case 1:
                    sb.append(String.valueOf((char) ((int) Math.round((Math.random() * 25.0d) + 97.0d))));
                    break;
                case 2:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }
        }
        return sb.toString();
    }

    public static boolean b(String str) {
        return Pattern.compile("^http(s)?://([a-z0-9_\\-]+\\.)*(alipaydev|alipay|taobao)\\.(com|net)(:\\d+)?(/.*)?$").matcher(str).matches();
    }

    public static String h(Context context) {
        String str;
        String str2 = "";
        try {
            for (ActivityManager.RunningAppProcessInfo next : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
                if (next.processName.equals(a())) {
                    str2 = str2 + "#M";
                } else {
                    if (next.processName.startsWith(a() + ":")) {
                        str = str2 + "#" + next.processName.replace(a() + ":", "");
                    } else {
                        str = str2;
                    }
                    str2 = str;
                }
            }
        } catch (Throwable th) {
            str2 = "";
        }
        if (str2.length() > 0) {
            str2 = str2.substring(1);
        }
        if (str2.length() == 0) {
            return "N";
        }
        return str2;
    }

    public static String i(Context context) {
        boolean z;
        try {
            List<PackageInfo> installedPackages = context.getPackageManager().getInstalledPackages(0);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < installedPackages.size(); i++) {
                PackageInfo packageInfo = installedPackages.get(i);
                int i2 = packageInfo.applicationInfo.flags;
                if ((i2 & 1) == 0 && (i2 & 128) == 0) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    if (packageInfo.packageName.equals(a())) {
                        sb.append(packageInfo.packageName).append(packageInfo.versionCode).append("-");
                    } else if (!packageInfo.packageName.contains("theme") && !packageInfo.packageName.startsWith("com.google.") && !packageInfo.packageName.startsWith("com.android.")) {
                        sb.append(packageInfo.packageName).append("-");
                    }
                }
            }
            return sb.toString();
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(c.b, "GetInstalledAppEx", th);
            return "";
        }
    }

    @SuppressLint({"InlinedApi"})
    private static boolean c(PackageInfo packageInfo) {
        int i = packageInfo.applicationInfo.flags;
        return (i & 1) == 0 && (i & 128) == 0;
    }

    public static boolean a(WebView webView, String str, Activity activity) {
        String substring;
        if (!TextUtils.isEmpty(str)) {
            if (str.toLowerCase().startsWith(com.alipay.sdk.cons.a.i.toLowerCase()) || str.toLowerCase().startsWith(com.alipay.sdk.cons.a.j.toLowerCase())) {
                try {
                    a a2 = a((Context) activity);
                    if (a2 != null && !a2.a()) {
                        if (str.startsWith("intent://platformapi/startapp")) {
                            str = str.replaceFirst("intent://platformapi/startapp\\?", com.alipay.sdk.cons.a.i);
                        }
                        activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                    }
                } catch (Throwable th) {
                }
            } else if (TextUtils.equals(str, com.alipay.sdk.cons.a.l) || TextUtils.equals(str, com.alipay.sdk.cons.a.m)) {
                h.a = h.a();
                activity.finish();
            } else if (str.startsWith(com.alipay.sdk.cons.a.k)) {
                try {
                    String substring2 = str.substring(str.indexOf(com.alipay.sdk.cons.a.k) + 24);
                    int parseInt = Integer.parseInt(substring2.substring(substring2.lastIndexOf(com.alipay.sdk.cons.a.n) + 10));
                    if (parseInt == i.SUCCEEDED.h || parseInt == i.PAY_WAITTING.h) {
                        if (com.alipay.sdk.cons.a.r) {
                            StringBuilder sb = new StringBuilder();
                            String decode = URLDecoder.decode(str);
                            String decode2 = URLDecoder.decode(decode);
                            String str2 = decode2.substring(decode2.indexOf(com.alipay.sdk.cons.a.k) + 24, decode2.lastIndexOf(com.alipay.sdk.cons.a.n)).split(com.alipay.sdk.cons.a.p)[0];
                            int indexOf = decode.indexOf(com.alipay.sdk.cons.a.p) + 12;
                            sb.append(str2).append(com.alipay.sdk.cons.a.p).append(decode.substring(indexOf, decode.indexOf(com.alipay.sdk.sys.a.b, indexOf))).append(decode.substring(decode.indexOf(com.alipay.sdk.sys.a.b, indexOf)));
                            substring = sb.toString();
                        } else {
                            String decode3 = URLDecoder.decode(str);
                            substring = decode3.substring(decode3.indexOf(com.alipay.sdk.cons.a.k) + 24, decode3.lastIndexOf(com.alipay.sdk.cons.a.n));
                        }
                        i a3 = i.a(parseInt);
                        h.a = h.a(a3.h, a3.i, substring);
                        activity.runOnUiThread(new n(activity));
                    } else {
                        i a4 = i.a(i.FAILED.h);
                        h.a = h.a(a4.h, a4.i, "");
                        activity.runOnUiThread(new n(activity));
                    }
                } catch (Exception e2) {
                    i a5 = i.a(i.PARAMS_ERROR.h);
                    h.a = h.a(a5.h, a5.i, "");
                }
            } else {
                webView.loadUrl(str);
            }
        }
        return true;
    }
}

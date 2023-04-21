package com.loc;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/* compiled from: AppInfo */
public class m {
    private static String a = "";
    private static String b = "";
    private static String c = "";
    private static String d = "";
    private static String e = null;

    public static String a(Context context) {
        try {
            if (!"".equals(a)) {
                return a;
            }
            PackageManager packageManager = context.getPackageManager();
            a = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(context.getPackageName(), 0));
            return a;
        } catch (PackageManager.NameNotFoundException e2) {
            y.a(e2, "AppInfo", "getApplicationName");
        } catch (Throwable th) {
            y.a(th, "AppInfo", "getApplicationName");
        }
    }

    public static String b(Context context) {
        try {
            if (b != null && !"".equals(b)) {
                return b;
            }
            b = context.getApplicationContext().getPackageName();
            return b;
        } catch (Throwable th) {
            y.a(th, "AppInfo", "getPackageName");
        }
    }

    public static String c(Context context) {
        try {
            if (!"".equals(c)) {
                return c;
            }
            c = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            return c;
        } catch (PackageManager.NameNotFoundException e2) {
            y.a(e2, "AppInfo", "getApplicationVersion");
        } catch (Throwable th) {
            y.a(th, "AppInfo", "getApplicationVersion");
        }
    }

    public static String d(Context context) {
        try {
            if (e != null && !"".equals(e)) {
                return e;
            }
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 64);
            byte[] digest = MessageDigest.getInstance("SHA1").digest(packageInfo.signatures[0].toByteArray());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b2 : digest) {
                String upperCase = Integer.toHexString(b2 & BLEServiceApi.CONNECTED_STATUS).toUpperCase(Locale.US);
                if (upperCase.length() == 1) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(upperCase);
                stringBuffer.append(":");
            }
            stringBuffer.append(packageInfo.packageName);
            e = stringBuffer.toString();
            return e;
        } catch (PackageManager.NameNotFoundException e2) {
            y.a(e2, "AppInfo", "getSHA1AndPackage");
            return e;
        } catch (NoSuchAlgorithmException e3) {
            y.a(e3, "AppInfo", "getSHA1AndPackage");
            return e;
        } catch (Throwable th) {
            y.a(th, "AppInfo", "getSHA1AndPackage");
            return e;
        }
    }

    static void a(String str) {
        d = str;
    }

    private static String f(Context context) throws PackageManager.NameNotFoundException {
        if (d == null || d.equals("")) {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo == null) {
                return d;
            }
            d = applicationInfo.metaData.getString("com.amap.api.v2.apikey");
        }
        return d;
    }

    public static String e(Context context) {
        try {
            return f(context);
        } catch (PackageManager.NameNotFoundException e2) {
            y.a(e2, "AppInfo", "getKey");
        } catch (Throwable th) {
            y.a(th, "AppInfo", "getKey");
        }
        return d;
    }
}

package com.alipay.b.a.a.b;

import android.app.KeyguardManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.os.EnvironmentCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import com.alipay.b.a.a.a.a;
import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONObject;

public final class b {
    private static b a = new b();

    private b() {
    }

    public static b a() {
        return a;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001e A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(android.content.Context r2) {
        /*
            r1 = 0
            java.lang.String r0 = "android.permission.READ_PHONE_STATE"
            boolean r0 = a(r2, r0)
            if (r0 == 0) goto L_0x000c
            java.lang.String r0 = ""
        L_0x000b:
            return r0
        L_0x000c:
            if (r2 == 0) goto L_0x0022
            java.lang.String r0 = "phone"
            java.lang.Object r0 = r2.getSystemService(r0)     // Catch:{ Throwable -> 0x0021 }
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0     // Catch:{ Throwable -> 0x0021 }
            if (r0 == 0) goto L_0x0022
            java.lang.String r0 = r0.getDeviceId()     // Catch:{ Throwable -> 0x0021 }
        L_0x001c:
            if (r0 != 0) goto L_0x000b
            java.lang.String r0 = ""
            goto L_0x000b
        L_0x0021:
            r0 = move-exception
        L_0x0022:
            r0 = r1
            goto L_0x001c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.b.a.a.b.b.a(android.content.Context):java.lang.String");
    }

    private static boolean a(Context context, String str) {
        return !(context.getPackageManager().checkPermission(str, context.getPackageName()) == 0);
    }

    public static String b() {
        long j = 0;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            j = ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
        }
        return String.valueOf(j);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001f A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String b(android.content.Context r2) {
        /*
            java.lang.String r1 = ""
            java.lang.String r0 = "android.permission.READ_PHONE_STATE"
            boolean r0 = a(r2, r0)
            if (r0 == 0) goto L_0x000d
            java.lang.String r0 = ""
        L_0x000c:
            return r0
        L_0x000d:
            if (r2 == 0) goto L_0x0023
            java.lang.String r0 = "phone"
            java.lang.Object r0 = r2.getSystemService(r0)     // Catch:{ Throwable -> 0x0022 }
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0     // Catch:{ Throwable -> 0x0022 }
            if (r0 == 0) goto L_0x0023
            java.lang.String r0 = r0.getSubscriberId()     // Catch:{ Throwable -> 0x0022 }
        L_0x001d:
            if (r0 != 0) goto L_0x000c
            java.lang.String r0 = ""
            goto L_0x000c
        L_0x0022:
            r0 = move-exception
        L_0x0023:
            r0 = r1
            goto L_0x001d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.b.a.a.b.b.b(android.content.Context):java.lang.String");
    }

    public static String c() {
        long j = 0;
        try {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                StatFs statFs = new StatFs(a.a().getPath());
                j = ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
            }
        } catch (Throwable th) {
        }
        return String.valueOf(j);
    }

    public static String c(Context context) {
        int i = 0;
        try {
            i = Settings.System.getInt(context.getContentResolver(), "airplane_mode_on", 0);
        } catch (Throwable th) {
        }
        return i == 1 ? com.alipay.sdk.cons.a.e : "0";
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.io.LineNumberReader} */
    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v5, types: [java.io.InputStreamReader] */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: type inference failed for: r1v14 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x004d A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x005b A[SYNTHETIC, Splitter:B:32:0x005b] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0060 A[SYNTHETIC, Splitter:B:35:0x0060] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0065 A[SYNTHETIC, Splitter:B:38:0x0065] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0072 A[SYNTHETIC, Splitter:B:46:0x0072] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0077 A[SYNTHETIC, Splitter:B:49:0x0077] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x007c A[SYNTHETIC, Splitter:B:52:0x007c] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:78:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String d() {
        /*
            r1 = 0
            java.lang.String r4 = "0000000000000000"
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0056, all -> 0x006d }
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x0056, all -> 0x006d }
            java.lang.String r2 = "/proc/cpuinfo"
            r0.<init>(r2)     // Catch:{ Throwable -> 0x0056, all -> 0x006d }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x0056, all -> 0x006d }
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0098, all -> 0x008e }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0098, all -> 0x008e }
            java.io.LineNumberReader r0 = new java.io.LineNumberReader     // Catch:{ Throwable -> 0x009c, all -> 0x0091 }
            r0.<init>(r2)     // Catch:{ Throwable -> 0x009c, all -> 0x0091 }
            r1 = 1
        L_0x001a:
            r5 = 100
            if (r1 >= r5) goto L_0x00a7
            java.lang.String r5 = r0.readLine()     // Catch:{ Throwable -> 0x00a1, all -> 0x0093 }
            if (r5 == 0) goto L_0x00a7
            java.lang.String r6 = "Serial"
            int r6 = r5.indexOf(r6)     // Catch:{ Throwable -> 0x00a1, all -> 0x0093 }
            if (r6 < 0) goto L_0x0050
            java.lang.String r1 = ":"
            int r1 = r5.indexOf(r1)     // Catch:{ Throwable -> 0x00a1, all -> 0x0093 }
            int r1 = r1 + 1
            int r6 = r5.length()     // Catch:{ Throwable -> 0x00a1, all -> 0x0093 }
            java.lang.String r1 = r5.substring(r1, r6)     // Catch:{ Throwable -> 0x00a1, all -> 0x0093 }
            java.lang.String r4 = r1.trim()     // Catch:{ Throwable -> 0x00a1, all -> 0x0093 }
            r1 = r4
        L_0x0041:
            r0.close()     // Catch:{ Throwable -> 0x0080 }
        L_0x0044:
            r2.close()     // Catch:{ Throwable -> 0x0082 }
        L_0x0047:
            r3.close()     // Catch:{ Throwable -> 0x0053 }
            r0 = r1
        L_0x004b:
            if (r0 != 0) goto L_0x004f
            java.lang.String r0 = ""
        L_0x004f:
            return r0
        L_0x0050:
            int r1 = r1 + 1
            goto L_0x001a
        L_0x0053:
            r0 = move-exception
            r0 = r1
            goto L_0x004b
        L_0x0056:
            r0 = move-exception
            r0 = r1
            r2 = r1
        L_0x0059:
            if (r0 == 0) goto L_0x005e
            r0.close()     // Catch:{ Throwable -> 0x0084 }
        L_0x005e:
            if (r1 == 0) goto L_0x0063
            r1.close()     // Catch:{ Throwable -> 0x0086 }
        L_0x0063:
            if (r2 == 0) goto L_0x00a5
            r2.close()     // Catch:{ Throwable -> 0x006a }
            r0 = r4
            goto L_0x004b
        L_0x006a:
            r0 = move-exception
            r0 = r4
            goto L_0x004b
        L_0x006d:
            r0 = move-exception
            r2 = r1
            r3 = r1
        L_0x0070:
            if (r1 == 0) goto L_0x0075
            r1.close()     // Catch:{ Throwable -> 0x0088 }
        L_0x0075:
            if (r2 == 0) goto L_0x007a
            r2.close()     // Catch:{ Throwable -> 0x008a }
        L_0x007a:
            if (r3 == 0) goto L_0x007f
            r3.close()     // Catch:{ Throwable -> 0x008c }
        L_0x007f:
            throw r0
        L_0x0080:
            r0 = move-exception
            goto L_0x0044
        L_0x0082:
            r0 = move-exception
            goto L_0x0047
        L_0x0084:
            r0 = move-exception
            goto L_0x005e
        L_0x0086:
            r0 = move-exception
            goto L_0x0063
        L_0x0088:
            r1 = move-exception
            goto L_0x0075
        L_0x008a:
            r1 = move-exception
            goto L_0x007a
        L_0x008c:
            r1 = move-exception
            goto L_0x007f
        L_0x008e:
            r0 = move-exception
            r2 = r1
            goto L_0x0070
        L_0x0091:
            r0 = move-exception
            goto L_0x0070
        L_0x0093:
            r1 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
            goto L_0x0070
        L_0x0098:
            r0 = move-exception
            r0 = r1
            r2 = r3
            goto L_0x0059
        L_0x009c:
            r0 = move-exception
            r0 = r1
            r1 = r2
            r2 = r3
            goto L_0x0059
        L_0x00a1:
            r1 = move-exception
            r1 = r2
            r2 = r3
            goto L_0x0059
        L_0x00a5:
            r0 = r4
            goto L_0x004b
        L_0x00a7:
            r1 = r4
            goto L_0x0041
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.b.a.a.b.b.d():java.lang.String");
    }

    public static String d(Context context) {
        int i = 1;
        JSONObject jSONObject = new JSONObject();
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService("audio");
            if (audioManager.getRingerMode() != 0) {
                i = 0;
            }
            int streamVolume = audioManager.getStreamVolume(0);
            int streamVolume2 = audioManager.getStreamVolume(1);
            int streamVolume3 = audioManager.getStreamVolume(2);
            int streamVolume4 = audioManager.getStreamVolume(3);
            int streamVolume5 = audioManager.getStreamVolume(4);
            jSONObject.put("ringermode", String.valueOf(i));
            jSONObject.put("call", String.valueOf(streamVolume));
            jSONObject.put("system", String.valueOf(streamVolume2));
            jSONObject.put("ring", String.valueOf(streamVolume3));
            jSONObject.put("music", String.valueOf(streamVolume4));
            jSONObject.put("alarm", String.valueOf(streamVolume5));
        } catch (Throwable th) {
        }
        return jSONObject.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001b A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String e(android.content.Context r2) {
        /*
            r1 = 0
            if (r2 == 0) goto L_0x001f
            java.lang.String r0 = "phone"
            java.lang.Object r0 = r2.getSystemService(r0)     // Catch:{ Throwable -> 0x001e }
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0     // Catch:{ Throwable -> 0x001e }
            if (r0 == 0) goto L_0x001f
            java.lang.String r0 = r0.getNetworkOperatorName()     // Catch:{ Throwable -> 0x001e }
        L_0x0011:
            if (r0 == 0) goto L_0x001b
            java.lang.String r1 = "null"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x001d
        L_0x001b:
            java.lang.String r0 = ""
        L_0x001d:
            return r0
        L_0x001e:
            r0 = move-exception
        L_0x001f:
            r0 = r1
            goto L_0x0011
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.b.a.a.b.b.e(android.content.Context):java.lang.String");
    }

    public static String f() {
        String w = w();
        return !a.a(w) ? w : x();
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x001f A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String f(android.content.Context r2) {
        /*
            java.lang.String r0 = "android.permission.READ_PHONE_STATE"
            boolean r0 = a(r2, r0)
            if (r0 == 0) goto L_0x000b
            java.lang.String r0 = ""
        L_0x000a:
            return r0
        L_0x000b:
            java.lang.String r1 = ""
            if (r2 == 0) goto L_0x0023
            java.lang.String r0 = "phone"
            java.lang.Object r0 = r2.getSystemService(r0)     // Catch:{ Throwable -> 0x0022 }
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0     // Catch:{ Throwable -> 0x0022 }
            if (r0 == 0) goto L_0x0023
            java.lang.String r0 = r0.getLine1Number()     // Catch:{ Throwable -> 0x0022 }
        L_0x001d:
            if (r0 != 0) goto L_0x000a
            java.lang.String r0 = ""
            goto L_0x000a
        L_0x0022:
            r0 = move-exception
        L_0x0023:
            r0 = r1
            goto L_0x001d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.b.a.a.b.b.f(android.content.Context):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0035 A[SYNTHETIC, Splitter:B:23:0x0035] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x003a A[SYNTHETIC, Splitter:B:26:0x003a] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0047 A[SYNTHETIC, Splitter:B:32:0x0047] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x004c A[SYNTHETIC, Splitter:B:35:0x004c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String g() {
        /*
            r0 = 0
            r5 = 1
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Throwable -> 0x0031, all -> 0x0040 }
            java.lang.String r1 = "/proc/cpuinfo"
            r2.<init>(r1)     // Catch:{ Throwable -> 0x0031, all -> 0x0040 }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0065, all -> 0x005e }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0065, all -> 0x005e }
            java.lang.String r0 = r1.readLine()     // Catch:{ Throwable -> 0x0068, all -> 0x0063 }
            java.lang.String r3 = ":\\s+"
            r4 = 2
            java.lang.String[] r0 = r0.split(r3, r4)     // Catch:{ Throwable -> 0x0068, all -> 0x0063 }
            if (r0 == 0) goto L_0x0028
            int r3 = r0.length     // Catch:{ Throwable -> 0x0068, all -> 0x0063 }
            if (r3 <= r5) goto L_0x0028
            r3 = 1
            r0 = r0[r3]     // Catch:{ Throwable -> 0x0068, all -> 0x0063 }
            r2.close()     // Catch:{ Throwable -> 0x0050 }
        L_0x0024:
            r1.close()     // Catch:{ Throwable -> 0x0052 }
        L_0x0027:
            return r0
        L_0x0028:
            r2.close()     // Catch:{ Throwable -> 0x0054 }
        L_0x002b:
            r1.close()     // Catch:{ Throwable -> 0x0056 }
        L_0x002e:
            java.lang.String r0 = ""
            goto L_0x0027
        L_0x0031:
            r1 = move-exception
            r1 = r0
        L_0x0033:
            if (r1 == 0) goto L_0x0038
            r1.close()     // Catch:{ Throwable -> 0x0058 }
        L_0x0038:
            if (r0 == 0) goto L_0x002e
            r0.close()     // Catch:{ Throwable -> 0x003e }
            goto L_0x002e
        L_0x003e:
            r0 = move-exception
            goto L_0x002e
        L_0x0040:
            r1 = move-exception
            r2 = r0
            r6 = r0
            r0 = r1
            r1 = r6
        L_0x0045:
            if (r2 == 0) goto L_0x004a
            r2.close()     // Catch:{ Throwable -> 0x005a }
        L_0x004a:
            if (r1 == 0) goto L_0x004f
            r1.close()     // Catch:{ Throwable -> 0x005c }
        L_0x004f:
            throw r0
        L_0x0050:
            r2 = move-exception
            goto L_0x0024
        L_0x0052:
            r1 = move-exception
            goto L_0x0027
        L_0x0054:
            r0 = move-exception
            goto L_0x002b
        L_0x0056:
            r0 = move-exception
            goto L_0x002e
        L_0x0058:
            r1 = move-exception
            goto L_0x0038
        L_0x005a:
            r2 = move-exception
            goto L_0x004a
        L_0x005c:
            r1 = move-exception
            goto L_0x004f
        L_0x005e:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
            goto L_0x0045
        L_0x0063:
            r0 = move-exception
            goto L_0x0045
        L_0x0065:
            r1 = move-exception
            r1 = r2
            goto L_0x0033
        L_0x0068:
            r0 = move-exception
            r0 = r1
            r1 = r2
            goto L_0x0033
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.b.a.a.b.b.g():java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0049 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String g(android.content.Context r5) {
        /*
            r1 = 0
            if (r5 == 0) goto L_0x0046
            java.lang.String r0 = "sensor"
            java.lang.Object r0 = r5.getSystemService(r0)     // Catch:{ Throwable -> 0x0045 }
            android.hardware.SensorManager r0 = (android.hardware.SensorManager) r0     // Catch:{ Throwable -> 0x0045 }
            if (r0 == 0) goto L_0x0046
            r2 = -1
            java.util.List r0 = r0.getSensorList(r2)     // Catch:{ Throwable -> 0x0045 }
            if (r0 == 0) goto L_0x0046
            int r2 = r0.size()     // Catch:{ Throwable -> 0x0045 }
            if (r2 <= 0) goto L_0x0046
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0045 }
            r2.<init>()     // Catch:{ Throwable -> 0x0045 }
            java.util.Iterator r3 = r0.iterator()     // Catch:{ Throwable -> 0x0045 }
        L_0x0023:
            boolean r0 = r3.hasNext()     // Catch:{ Throwable -> 0x0045 }
            if (r0 == 0) goto L_0x004c
            java.lang.Object r0 = r3.next()     // Catch:{ Throwable -> 0x0045 }
            android.hardware.Sensor r0 = (android.hardware.Sensor) r0     // Catch:{ Throwable -> 0x0045 }
            java.lang.String r4 = r0.getName()     // Catch:{ Throwable -> 0x0045 }
            r2.append(r4)     // Catch:{ Throwable -> 0x0045 }
            int r4 = r0.getVersion()     // Catch:{ Throwable -> 0x0045 }
            r2.append(r4)     // Catch:{ Throwable -> 0x0045 }
            java.lang.String r0 = r0.getVendor()     // Catch:{ Throwable -> 0x0045 }
            r2.append(r0)     // Catch:{ Throwable -> 0x0045 }
            goto L_0x0023
        L_0x0045:
            r0 = move-exception
        L_0x0046:
            r0 = r1
        L_0x0047:
            if (r0 != 0) goto L_0x004b
            java.lang.String r0 = ""
        L_0x004b:
            return r0
        L_0x004c:
            java.lang.String r0 = r2.toString()     // Catch:{ Throwable -> 0x0045 }
            java.lang.String r0 = com.alipay.b.a.a.a.a.d(r0)     // Catch:{ Throwable -> 0x0045 }
            goto L_0x0047
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.b.a.a.b.b.g(android.content.Context):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0034 A[SYNTHETIC, Splitter:B:19:0x0034] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0039 A[SYNTHETIC, Splitter:B:22:0x0039] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0044 A[SYNTHETIC, Splitter:B:28:0x0044] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0049 A[SYNTHETIC, Splitter:B:31:0x0049] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String h() {
        /*
            r3 = 0
            java.lang.String r4 = "/proc/meminfo"
            r0 = 0
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Throwable -> 0x0030, all -> 0x003f }
            r2.<init>(r4)     // Catch:{ Throwable -> 0x0030, all -> 0x003f }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x005c, all -> 0x0057 }
            r5 = 8192(0x2000, float:1.14794E-41)
            r4.<init>(r2, r5)     // Catch:{ Throwable -> 0x005c, all -> 0x0057 }
            java.lang.String r3 = r4.readLine()     // Catch:{ Throwable -> 0x005e, all -> 0x005a }
            if (r3 == 0) goto L_0x0025
            java.lang.String r5 = "\\s+"
            java.lang.String[] r3 = r3.split(r5)     // Catch:{ Throwable -> 0x005e, all -> 0x005a }
            r5 = 1
            r3 = r3[r5]     // Catch:{ Throwable -> 0x005e, all -> 0x005a }
            int r0 = java.lang.Integer.parseInt(r3)     // Catch:{ Throwable -> 0x005e, all -> 0x005a }
            long r0 = (long) r0
        L_0x0025:
            r2.close()     // Catch:{ Throwable -> 0x004d }
        L_0x0028:
            r4.close()     // Catch:{ Throwable -> 0x004f }
        L_0x002b:
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x0030:
            r2 = move-exception
            r2 = r3
        L_0x0032:
            if (r2 == 0) goto L_0x0037
            r2.close()     // Catch:{ Throwable -> 0x0051 }
        L_0x0037:
            if (r3 == 0) goto L_0x002b
            r3.close()     // Catch:{ Throwable -> 0x003d }
            goto L_0x002b
        L_0x003d:
            r2 = move-exception
            goto L_0x002b
        L_0x003f:
            r0 = move-exception
            r2 = r3
            r4 = r3
        L_0x0042:
            if (r2 == 0) goto L_0x0047
            r2.close()     // Catch:{ Throwable -> 0x0053 }
        L_0x0047:
            if (r4 == 0) goto L_0x004c
            r4.close()     // Catch:{ Throwable -> 0x0055 }
        L_0x004c:
            throw r0
        L_0x004d:
            r2 = move-exception
            goto L_0x0028
        L_0x004f:
            r2 = move-exception
            goto L_0x002b
        L_0x0051:
            r2 = move-exception
            goto L_0x0037
        L_0x0053:
            r1 = move-exception
            goto L_0x0047
        L_0x0055:
            r1 = move-exception
            goto L_0x004c
        L_0x0057:
            r0 = move-exception
            r4 = r3
            goto L_0x0042
        L_0x005a:
            r0 = move-exception
            goto L_0x0042
        L_0x005c:
            r4 = move-exception
            goto L_0x0032
        L_0x005e:
            r3 = move-exception
            r3 = r4
            goto L_0x0032
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.b.a.a.b.b.h():java.lang.String");
    }

    public static String h(Context context) {
        List<Sensor> sensorList;
        JSONArray jSONArray = new JSONArray();
        if (context != null) {
            try {
                SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
                if (!(sensorManager == null || (sensorList = sensorManager.getSensorList(-1)) == null || sensorList.size() <= 0)) {
                    for (Sensor next : sensorList) {
                        if (next != null) {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("name", next.getName());
                            jSONObject.put("version", next.getVersion());
                            jSONObject.put("vendor", next.getVendor());
                            jSONArray.put(jSONObject);
                        }
                    }
                }
            } catch (Throwable th) {
            }
        }
        return jSONArray.toString();
    }

    public static String i() {
        long j = 0;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            j = ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
        }
        return String.valueOf(j);
    }

    public static String i(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return Integer.toString(displayMetrics.widthPixels) + "*" + Integer.toString(displayMetrics.heightPixels);
        } catch (Throwable th) {
            return "";
        }
    }

    public static String j() {
        long j = 0;
        try {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                j = ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
            }
        } catch (Throwable th) {
        }
        return String.valueOf(j);
    }

    public static String j(Context context) {
        try {
            return new StringBuilder().append(context.getResources().getDisplayMetrics().widthPixels).toString();
        } catch (Throwable th) {
            return "";
        }
    }

    public static String k() {
        String str;
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            str = (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls.newInstance(), new Object[]{"gsm.version.baseband", "no message"});
        } catch (Throwable th) {
            str = "";
        }
        return str == null ? "" : str;
    }

    public static String k(Context context) {
        try {
            return new StringBuilder().append(context.getResources().getDisplayMetrics().heightPixels).toString();
        } catch (Throwable th) {
            return "";
        }
    }

    public static String l() {
        String str = "";
        try {
            str = Build.SERIAL;
        } catch (Throwable th) {
        }
        return str == null ? "" : str;
    }

    public static String l(Context context) {
        if (a(context, "android.permission.ACCESS_WIFI_STATE")) {
            return "";
        }
        try {
            String macAddress = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            if (macAddress != null) {
                try {
                    if (macAddress.length() != 0 && !"02:00:00:00:00:00".equals(macAddress)) {
                        return macAddress;
                    }
                } catch (Throwable th) {
                    return macAddress;
                }
            }
            return v();
        } catch (Throwable th2) {
            return "";
        }
    }

    public static String m() {
        String str = "";
        try {
            str = Locale.getDefault().toString();
        } catch (Throwable th) {
        }
        return str == null ? "" : str;
    }

    public static String m(Context context) {
        if (a(context, "android.permission.READ_PHONE_STATE")) {
            return "";
        }
        try {
            String simSerialNumber = ((TelephonyManager) context.getSystemService("phone")).getSimSerialNumber();
            if (simSerialNumber != null) {
                if (simSerialNumber == null) {
                    return simSerialNumber;
                }
                try {
                    return simSerialNumber.length() == 0 ? "" : simSerialNumber;
                } catch (Throwable th) {
                    return simSerialNumber;
                }
            }
        } catch (Throwable th2) {
            return "";
        }
    }

    public static String n() {
        String str = "";
        try {
            str = TimeZone.getDefault().getDisplayName(false, 0);
        } catch (Throwable th) {
        }
        return str == null ? "" : str;
    }

    public static String n(Context context) {
        String str = "";
        try {
            str = Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Throwable th) {
        }
        return str == null ? "" : str;
    }

    public static String o() {
        try {
            long currentTimeMillis = System.currentTimeMillis() - SystemClock.elapsedRealtime();
            return new StringBuilder().append(currentTimeMillis - (currentTimeMillis % 1000)).toString();
        } catch (Throwable th) {
            return "";
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001d, code lost:
        if ("02:00:00:00:00:00".equals(r0) == false) goto L_0x0029;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String o(android.content.Context r3) {
        /*
            java.lang.String r0 = "android.permission.BLUETOOTH"
            boolean r0 = a(r3, r0)
            if (r0 == 0) goto L_0x000b
            java.lang.String r0 = ""
        L_0x000a:
            return r0
        L_0x000b:
            java.lang.String r0 = y()
            if (r0 == 0) goto L_0x001f
            int r1 = r0.length()     // Catch:{ Throwable -> 0x002e }
            if (r1 == 0) goto L_0x001f
            java.lang.String r1 = "02:00:00:00:00:00"
            boolean r1 = r1.equals(r0)     // Catch:{ Throwable -> 0x002e }
            if (r1 == 0) goto L_0x0029
        L_0x001f:
            android.content.ContentResolver r1 = r3.getContentResolver()     // Catch:{ Throwable -> 0x002e }
            java.lang.String r2 = "bluetooth_address"
            java.lang.String r0 = android.provider.Settings.Secure.getString(r1, r2)     // Catch:{ Throwable -> 0x002e }
        L_0x0029:
            if (r0 != 0) goto L_0x000a
            java.lang.String r0 = ""
            goto L_0x000a
        L_0x002e:
            r1 = move-exception
            goto L_0x000a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.b.a.a.b.b.o(android.content.Context):java.lang.String");
    }

    public static String p() {
        try {
            return new StringBuilder().append(SystemClock.elapsedRealtime()).toString();
        } catch (Throwable th) {
            return "";
        }
    }

    public static String p(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                return String.valueOf(telephonyManager.getNetworkType());
            }
        } catch (Throwable th) {
        }
        return "";
    }

    public static String q() {
        try {
            StringBuilder sb = new StringBuilder();
            String[] strArr = {"/dev/qemu_pipe", "/dev/socket/qemud", "/system/lib/libc_malloc_debug_qemu.so", "/sys/qemu_trace", "/system/bin/qemu-props", "/dev/socket/genyd", "/dev/socket/baseband_genyd"};
            sb.append(BLEServiceApi.RESULT_NG + ":");
            for (int i = 0; i < 7; i++) {
                if (new File(strArr[i]).exists()) {
                    sb.append(com.alipay.sdk.cons.a.e);
                } else {
                    sb.append("0");
                }
            }
            return sb.toString();
        } catch (Throwable th) {
            return "";
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0026 A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String q(android.content.Context r3) {
        /*
            java.lang.String r1 = ""
            java.lang.String r0 = "android.permission.ACCESS_WIFI_STATE"
            boolean r0 = a(r3, r0)
            if (r0 == 0) goto L_0x000d
            java.lang.String r0 = ""
        L_0x000c:
            return r0
        L_0x000d:
            java.lang.String r0 = "wifi"
            java.lang.Object r0 = r3.getSystemService(r0)     // Catch:{ Throwable -> 0x0029 }
            android.net.wifi.WifiManager r0 = (android.net.wifi.WifiManager) r0     // Catch:{ Throwable -> 0x0029 }
            boolean r2 = r0.isWifiEnabled()     // Catch:{ Throwable -> 0x0029 }
            if (r2 == 0) goto L_0x002a
            android.net.wifi.WifiInfo r0 = r0.getConnectionInfo()     // Catch:{ Throwable -> 0x0029 }
            java.lang.String r0 = r0.getBSSID()     // Catch:{ Throwable -> 0x0029 }
        L_0x0024:
            if (r0 != 0) goto L_0x000c
            java.lang.String r0 = ""
            goto L_0x000c
        L_0x0029:
            r0 = move-exception
        L_0x002a:
            r0 = r1
            goto L_0x0024
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.b.a.a.b.b.q(android.content.Context):java.lang.String");
    }

    public static String r() {
        String[] strArr = {"dalvik.system.Taint"};
        StringBuilder sb = new StringBuilder();
        sb.append(BLEServiceApi.RESULT_NG);
        sb.append(":");
        for (int i = 0; i <= 0; i++) {
            try {
                Class.forName(strArr[0]);
                sb.append(com.alipay.sdk.cons.a.e);
            } catch (Throwable th) {
                sb.append("0");
            }
        }
        return sb.toString();
    }

    public static String r(Context context) {
        try {
            String u = u(context);
            return (!a.b(u) || !a.b(z())) ? "" : u + ":" + z();
        } catch (Throwable th) {
            return "";
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0085 A[SYNTHETIC, Splitter:B:20:0x0085] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0091 A[SYNTHETIC, Splitter:B:26:0x0091] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0041 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String s() {
        /*
            r2 = 48
            java.lang.String r0 = "00"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.util.LinkedHashMap r5 = new java.util.LinkedHashMap
            r5.<init>()
            java.lang.String r1 = "/system/build.prop"
            java.lang.String r3 = "ro.product.name=sdk"
            r5.put(r1, r3)
            java.lang.String r1 = "/proc/tty/drivers"
            java.lang.String r3 = "goldfish"
            r5.put(r1, r3)
            java.lang.String r1 = "/proc/cpuinfo"
            java.lang.String r3 = "goldfish"
            r5.put(r1, r3)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r1 = ":"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r4.append(r0)
            java.util.Set r0 = r5.keySet()
            java.util.Iterator r6 = r0.iterator()
        L_0x0041:
            boolean r0 = r6.hasNext()
            if (r0 == 0) goto L_0x0095
            java.lang.Object r0 = r6.next()
            java.lang.String r0 = (java.lang.String) r0
            r1 = 0
            java.io.LineNumberReader r3 = new java.io.LineNumberReader     // Catch:{ Throwable -> 0x007e, all -> 0x008b }
            java.io.InputStreamReader r7 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x007e, all -> 0x008b }
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x007e, all -> 0x008b }
            r8.<init>(r0)     // Catch:{ Throwable -> 0x007e, all -> 0x008b }
            r7.<init>(r8)     // Catch:{ Throwable -> 0x007e, all -> 0x008b }
            r3.<init>(r7)     // Catch:{ Throwable -> 0x007e, all -> 0x008b }
        L_0x005d:
            java.lang.String r1 = r3.readLine()     // Catch:{ Throwable -> 0x009f, all -> 0x009c }
            if (r1 == 0) goto L_0x00a2
            java.lang.String r7 = r1.toLowerCase()     // Catch:{ Throwable -> 0x009f, all -> 0x009c }
            java.lang.Object r1 = r5.get(r0)     // Catch:{ Throwable -> 0x009f, all -> 0x009c }
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1     // Catch:{ Throwable -> 0x009f, all -> 0x009c }
            boolean r1 = r7.contains(r1)     // Catch:{ Throwable -> 0x009f, all -> 0x009c }
            if (r1 == 0) goto L_0x005d
            r0 = 49
        L_0x0075:
            r4.append(r0)
            r3.close()     // Catch:{ Throwable -> 0x007c }
            goto L_0x0041
        L_0x007c:
            r0 = move-exception
            goto L_0x0041
        L_0x007e:
            r0 = move-exception
            r0 = r1
        L_0x0080:
            r4.append(r2)
            if (r0 == 0) goto L_0x0041
            r0.close()     // Catch:{ Throwable -> 0x0089 }
            goto L_0x0041
        L_0x0089:
            r0 = move-exception
            goto L_0x0041
        L_0x008b:
            r0 = move-exception
        L_0x008c:
            r4.append(r2)
            if (r1 == 0) goto L_0x0094
            r1.close()     // Catch:{ Throwable -> 0x009a }
        L_0x0094:
            throw r0
        L_0x0095:
            java.lang.String r0 = r4.toString()
            return r0
        L_0x009a:
            r1 = move-exception
            goto L_0x0094
        L_0x009c:
            r0 = move-exception
            r1 = r3
            goto L_0x008c
        L_0x009f:
            r0 = move-exception
            r0 = r3
            goto L_0x0080
        L_0x00a2:
            r0 = r2
            goto L_0x0075
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.b.a.a.b.b.s():java.lang.String");
    }

    public static String s(Context context) {
        try {
            if (!((KeyguardManager) context.getSystemService("keyguard")).isKeyguardSecure()) {
                return "0:0";
            }
            String[] strArr = {"/data/system/password.key", "/data/system/gesture.key", "/data/system/gatekeeper.password.key", "/data/system/gatekeeper.gesture.key", "/data/system/gatekeeper.pattern.key"};
            int i = 0;
            long j = 0;
            while (i < 5) {
                long j2 = -1;
                try {
                    j2 = new File(strArr[i]).lastModified();
                } catch (Throwable th) {
                }
                i++;
                j = Math.max(j2, j);
            }
            return "1:" + j;
        } catch (Throwable th2) {
            return "";
        }
    }

    public static String t() {
        char c = '0';
        StringBuilder sb = new StringBuilder();
        sb.append(BLEServiceApi.RESULT_NG + ":");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("BRAND", "generic");
        linkedHashMap.put("BOARD", EnvironmentCompat.MEDIA_UNKNOWN);
        linkedHashMap.put("DEVICE", "generic");
        linkedHashMap.put("HARDWARE", "goldfish");
        linkedHashMap.put("PRODUCT", "sdk");
        linkedHashMap.put("MODEL", "sdk");
        for (String str : linkedHashMap.keySet()) {
            try {
                String str2 = (String) Build.class.getField(str).get((Object) null);
                String str3 = (String) linkedHashMap.get(str);
                String lowerCase = str2 != null ? str2.toLowerCase() : null;
                c = (lowerCase == null || !lowerCase.contains(str3)) ? c : '1';
            } catch (Throwable th) {
            } finally {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String t(Context context) {
        try {
            Intent registerReceiver = context.registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            int intExtra = registerReceiver.getIntExtra("level", -1);
            int intExtra2 = registerReceiver.getIntExtra("status", -1);
            return (intExtra2 == 2 || intExtra2 == 5 ? com.alipay.sdk.cons.a.e : "0") + ":" + intExtra;
        } catch (Throwable th) {
            return "";
        }
    }

    public static String u() {
        StringBuilder sb = new StringBuilder();
        sb.append(BLEServiceApi.RESULT_NG + ":");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("ro.hardware", "goldfish");
        linkedHashMap.put("ro.kernel.qemu", com.alipay.sdk.cons.a.e);
        linkedHashMap.put("ro.product.device", "generic");
        linkedHashMap.put("ro.product.model", "sdk");
        linkedHashMap.put("ro.product.brand", "generic");
        linkedHashMap.put("ro.product.name", "sdk");
        linkedHashMap.put("ro.build.fingerprint", "test-keys");
        linkedHashMap.put("ro.product.manufacturer", "unknow");
        for (String str : linkedHashMap.keySet()) {
            String str2 = (String) linkedHashMap.get(str);
            String b = a.b(str, "");
            sb.append((b == null || !b.contains(str2)) ? '0' : '1');
        }
        return sb.toString();
    }

    private static String u(Context context) {
        String str;
        if (a(context, "android.permission.ACCESS_NETWORK_STATE")) {
            return "";
        }
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return null;
            }
            if (activeNetworkInfo.getType() == 1) {
                return "WIFI";
            }
            if (activeNetworkInfo.getType() == 0) {
                int subtype = activeNetworkInfo.getSubtype();
                if (subtype == 4 || subtype == 1 || subtype == 2 || subtype == 7 || subtype == 11) {
                    return "2G";
                }
                if (subtype == 3 || subtype == 5 || subtype == 6 || subtype == 8 || subtype == 9 || subtype == 10 || subtype == 12 || subtype == 14 || subtype == 15) {
                    return "3G";
                }
                if (subtype == 13) {
                    return "4G";
                }
                str = "UNKNOW";
            } else {
                str = null;
            }
            return str;
        } catch (Throwable th) {
            return null;
        }
    }

    private static String v() {
        try {
            ArrayList<T> list = Collections.list(NetworkInterface.getNetworkInterfaces());
            if (list != null) {
                for (T t : list) {
                    if (t != null && t.getName() != null && t.getName().equalsIgnoreCase("wlan0")) {
                        byte[] hardwareAddress = t.getHardwareAddress();
                        if (hardwareAddress == null) {
                            return "02:00:00:00:00:00";
                        }
                        StringBuilder sb = new StringBuilder();
                        int length = hardwareAddress.length;
                        for (int i = 0; i < length; i++) {
                            sb.append(String.format("%02X:", new Object[]{Integer.valueOf(hardwareAddress[i] & BLEServiceApi.CONNECTED_STATUS)}));
                        }
                        if (sb.length() > 0) {
                            sb.deleteCharAt(sb.length() - 1);
                        }
                        return sb.toString();
                    }
                }
            }
        } catch (Throwable th) {
        }
        return "02:00:00:00:00:00";
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0031 A[SYNTHETIC, Splitter:B:21:0x0031] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0036 A[SYNTHETIC, Splitter:B:24:0x0036] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0043 A[SYNTHETIC, Splitter:B:30:0x0043] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0048 A[SYNTHETIC, Splitter:B:33:0x0048] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String w() {
        /*
            r0 = 0
            java.lang.String r1 = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Throwable -> 0x002d, all -> 0x003c }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x002d, all -> 0x003c }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0061, all -> 0x005a }
            r3 = 8192(0x2000, float:1.14794E-41)
            r1.<init>(r2, r3)     // Catch:{ Throwable -> 0x0061, all -> 0x005a }
            java.lang.String r0 = r1.readLine()     // Catch:{ Throwable -> 0x0064, all -> 0x005f }
            boolean r3 = com.alipay.b.a.a.a.a.a((java.lang.String) r0)     // Catch:{ Throwable -> 0x0064, all -> 0x005f }
            if (r3 != 0) goto L_0x0024
            java.lang.String r0 = r0.trim()     // Catch:{ Throwable -> 0x0064, all -> 0x005f }
            r1.close()     // Catch:{ Throwable -> 0x004c }
        L_0x0020:
            r2.close()     // Catch:{ Throwable -> 0x004e }
        L_0x0023:
            return r0
        L_0x0024:
            r1.close()     // Catch:{ Throwable -> 0x0050 }
        L_0x0027:
            r2.close()     // Catch:{ Throwable -> 0x0052 }
        L_0x002a:
            java.lang.String r0 = ""
            goto L_0x0023
        L_0x002d:
            r1 = move-exception
            r1 = r0
        L_0x002f:
            if (r0 == 0) goto L_0x0034
            r0.close()     // Catch:{ Throwable -> 0x0054 }
        L_0x0034:
            if (r1 == 0) goto L_0x002a
            r1.close()     // Catch:{ Throwable -> 0x003a }
            goto L_0x002a
        L_0x003a:
            r0 = move-exception
            goto L_0x002a
        L_0x003c:
            r1 = move-exception
            r2 = r0
            r4 = r0
            r0 = r1
            r1 = r4
        L_0x0041:
            if (r1 == 0) goto L_0x0046
            r1.close()     // Catch:{ Throwable -> 0x0056 }
        L_0x0046:
            if (r2 == 0) goto L_0x004b
            r2.close()     // Catch:{ Throwable -> 0x0058 }
        L_0x004b:
            throw r0
        L_0x004c:
            r1 = move-exception
            goto L_0x0020
        L_0x004e:
            r1 = move-exception
            goto L_0x0023
        L_0x0050:
            r0 = move-exception
            goto L_0x0027
        L_0x0052:
            r0 = move-exception
            goto L_0x002a
        L_0x0054:
            r0 = move-exception
            goto L_0x0034
        L_0x0056:
            r1 = move-exception
            goto L_0x0046
        L_0x0058:
            r1 = move-exception
            goto L_0x004b
        L_0x005a:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
            goto L_0x0041
        L_0x005f:
            r0 = move-exception
            goto L_0x0041
        L_0x0061:
            r1 = move-exception
            r1 = r2
            goto L_0x002f
        L_0x0064:
            r0 = move-exception
            r0 = r1
            r1 = r2
            goto L_0x002f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.b.a.a.b.b.w():java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0046 A[SYNTHETIC, Splitter:B:25:0x0046] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x004b A[SYNTHETIC, Splitter:B:28:0x004b] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0056 A[SYNTHETIC, Splitter:B:34:0x0056] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x005b A[SYNTHETIC, Splitter:B:37:0x005b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String x() {
        /*
            r2 = 0
            r6 = 1
            java.lang.String r1 = "/proc/cpuinfo"
            java.lang.String r0 = ""
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Throwable -> 0x0042, all -> 0x0051 }
            r3.<init>(r1)     // Catch:{ Throwable -> 0x0042, all -> 0x0051 }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x006e, all -> 0x0069 }
            r4 = 8192(0x2000, float:1.14794E-41)
            r1.<init>(r3, r4)     // Catch:{ Throwable -> 0x006e, all -> 0x0069 }
        L_0x0012:
            java.lang.String r2 = r1.readLine()     // Catch:{ Throwable -> 0x0072, all -> 0x006c }
            if (r2 == 0) goto L_0x003b
            boolean r4 = com.alipay.b.a.a.a.a.a((java.lang.String) r2)     // Catch:{ Throwable -> 0x0072, all -> 0x006c }
            if (r4 != 0) goto L_0x0012
            java.lang.String r4 = ":"
            java.lang.String[] r2 = r2.split(r4)     // Catch:{ Throwable -> 0x0072, all -> 0x006c }
            if (r2 == 0) goto L_0x0012
            int r4 = r2.length     // Catch:{ Throwable -> 0x0072, all -> 0x006c }
            if (r4 <= r6) goto L_0x0012
            r4 = 0
            r4 = r2[r4]     // Catch:{ Throwable -> 0x0072, all -> 0x006c }
            java.lang.String r5 = "BogoMIPS"
            boolean r4 = r4.contains(r5)     // Catch:{ Throwable -> 0x0072, all -> 0x006c }
            if (r4 == 0) goto L_0x0012
            r4 = 1
            r2 = r2[r4]     // Catch:{ Throwable -> 0x0072, all -> 0x006c }
            java.lang.String r0 = r2.trim()     // Catch:{ Throwable -> 0x0072, all -> 0x006c }
        L_0x003b:
            r3.close()     // Catch:{ Throwable -> 0x005f }
        L_0x003e:
            r1.close()     // Catch:{ Throwable -> 0x0061 }
        L_0x0041:
            return r0
        L_0x0042:
            r1 = move-exception
            r1 = r2
        L_0x0044:
            if (r2 == 0) goto L_0x0049
            r2.close()     // Catch:{ Throwable -> 0x0063 }
        L_0x0049:
            if (r1 == 0) goto L_0x0041
            r1.close()     // Catch:{ Throwable -> 0x004f }
            goto L_0x0041
        L_0x004f:
            r1 = move-exception
            goto L_0x0041
        L_0x0051:
            r0 = move-exception
            r1 = r2
            r3 = r2
        L_0x0054:
            if (r3 == 0) goto L_0x0059
            r3.close()     // Catch:{ Throwable -> 0x0065 }
        L_0x0059:
            if (r1 == 0) goto L_0x005e
            r1.close()     // Catch:{ Throwable -> 0x0067 }
        L_0x005e:
            throw r0
        L_0x005f:
            r2 = move-exception
            goto L_0x003e
        L_0x0061:
            r1 = move-exception
            goto L_0x0041
        L_0x0063:
            r2 = move-exception
            goto L_0x0049
        L_0x0065:
            r2 = move-exception
            goto L_0x0059
        L_0x0067:
            r1 = move-exception
            goto L_0x005e
        L_0x0069:
            r0 = move-exception
            r1 = r2
            goto L_0x0054
        L_0x006c:
            r0 = move-exception
            goto L_0x0054
        L_0x006e:
            r1 = move-exception
            r1 = r2
            r2 = r3
            goto L_0x0044
        L_0x0072:
            r2 = move-exception
            r2 = r3
            goto L_0x0044
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.b.a.a.b.b.x():java.lang.String");
    }

    private static String y() {
        String str = "";
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null && !defaultAdapter.isEnabled()) {
                return "";
            }
            str = defaultAdapter.getAddress();
            return str == null ? "" : str;
        } catch (Throwable th) {
        }
    }

    private static String z() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (true) {
                    if (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement = inetAddresses.nextElement();
                        if (!nextElement.isLoopbackAddress() && (nextElement instanceof Inet4Address)) {
                            return nextElement.getHostAddress().toString();
                        }
                    }
                }
            }
        } catch (Throwable th) {
        }
        return "";
    }

    public final String e() {
        try {
            return String.valueOf(new File("/sys/devices/system/cpu/").listFiles(new c(this)).length);
        } catch (Throwable th) {
            return com.alipay.sdk.cons.a.e;
        }
    }
}

package com.loc;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.location.Location;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Base64;
import cn.com.bioeasy.app.utils.ListUtils;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import com.amap.api.location.AMapLocation;
import com.autonavi.aps.amapapi.model.AmapLoc;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Random;
import org.json.JSONObject;

/* compiled from: Utils */
public class bw {
    private static int a = 0;
    private static String[] b = null;
    private static Hashtable<String, Long> c = new Hashtable<>();
    private static DecimalFormat d = null;
    private static SimpleDateFormat e = null;

    private bw() {
    }

    public static boolean a(String str) {
        if (!TextUtils.isEmpty(str) && TextUtils.isDigitsOnly(str)) {
            return ",111,123,134,199,202,204,206,208,212,213,214,216,218,219,220,222,225,226,228,230,231,232,234,235,238,240,242,244,246,247,248,250,255,257,259,260,262,266,268,270,272,274,276,278,280,282,283,284,286,288,289,290,292,293,294,295,297,302,308,310,311,312,313,314,315,316,310,330,332,334,338,340,342,344,346,348,350,352,354,356,358,360,362,363,364,365,366,368,370,372,374,376,400,401,402,404,405,406,410,412,413,414,415,416,417,418,419,420,421,422,424,425,426,427,428,429,430,431,432,434,436,437,438,440,441,450,452,454,455,456,457,466,467,470,472,502,505,510,514,515,520,525,528,530,534,535,536,537,539,540,541,542,543,544,545,546,547,548,549,550,551,552,553,555,560,598,602,603,604,605,606,607,608,609,610,611,612,613,614,615,616,617,618,619,620,621,622,623,624,625,626,627,628,629,630,631,632,633,634,635,636,637,638,639,640,641,642,643,645,646,647,648,649,650,651,652,653,654,655,657,659,665,702,704,706,708,710,712,714,716,722,724,730,732,734,736,738,740,742,744,746,748,750,850,901,".contains(ListUtils.DEFAULT_JOIN_SEPARATOR + str + ListUtils.DEFAULT_JOIN_SEPARATOR);
        }
        return false;
    }

    public static boolean a(double d2) {
        if (d2 <= 180.0d && d2 >= -180.0d) {
            return true;
        }
        return false;
    }

    public static boolean b(double d2) {
        if (d2 <= 90.0d && d2 >= -90.0d) {
            return true;
        }
        return false;
    }

    public static boolean a(AMapLocation aMapLocation) {
        if (aMapLocation == null || aMapLocation.getErrorCode() != 0) {
            return false;
        }
        double longitude = aMapLocation.getLongitude();
        double latitude = aMapLocation.getLatitude();
        float accuracy = aMapLocation.getAccuracy();
        if ((longitude != 0.0d || latitude != 0.0d || ((double) accuracy) != 0.0d) && longitude <= 180.0d && latitude <= 90.0d && longitude >= -180.0d && latitude >= -90.0d) {
            return true;
        }
        return false;
    }

    public static boolean a(AmapLoc amapLoc) {
        if (amapLoc == null || amapLoc.m().equals("8") || amapLoc.m().equals("5") || amapLoc.m().equals("6")) {
            return false;
        }
        double h = amapLoc.h();
        double i = amapLoc.i();
        float j = amapLoc.j();
        if ((h != 0.0d || i != 0.0d || ((double) j) != 0.0d) && h <= 180.0d && i <= 90.0d && h >= -180.0d && i >= -90.0d) {
            return true;
        }
        return false;
    }

    public static int a(int i) {
        return (i * 2) - 113;
    }

    public static String[] a(TelephonyManager telephonyManager) {
        boolean z;
        int i;
        String str = null;
        if (telephonyManager != null) {
            str = telephonyManager.getNetworkOperator();
        }
        String[] strArr = {"0", "0"};
        if (TextUtils.isEmpty(str)) {
            z = false;
        } else if (!TextUtils.isDigitsOnly(str)) {
            z = false;
        } else if (str.length() <= 4) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            strArr[0] = str.substring(0, 3);
            char[] charArray = str.substring(3).toCharArray();
            int i2 = 0;
            while (i2 < charArray.length && Character.isDigit(charArray[i2])) {
                i2++;
            }
            strArr[1] = str.substring(3, i2 + 3);
        }
        try {
            i = Integer.parseInt(strArr[0]);
        } catch (Exception e2) {
            i = 0;
        }
        if (i == 0) {
            strArr[0] = "0";
        }
        if (strArr[0].equals("0") || strArr[1].equals("0")) {
            return (!strArr[0].equals("0") || !strArr[1].equals("0") || b == null) ? strArr : b;
        }
        b = strArr;
        return strArr;
    }

    public static int a(CellLocation cellLocation, Context context) {
        if (a(context) || cellLocation == null) {
            return 9;
        }
        if (cellLocation instanceof GsmCellLocation) {
            return 1;
        }
        try {
            Class.forName("android.telephony.cdma.CdmaCellLocation");
            return 2;
        } catch (Exception e2) {
            return 9;
        }
    }

    public static long a() {
        return System.currentTimeMillis();
    }

    public static long b() {
        return SystemClock.elapsedRealtime();
    }

    public static boolean a(Context context) {
        boolean z = true;
        if (context == null) {
            return false;
        }
        ContentResolver contentResolver = context.getContentResolver();
        if (c() < 17) {
            try {
                return ((Integer) bv.a("android.provider.Settings$System", "getInt", new Object[]{contentResolver, ((String) bv.a("android.provider.Settings$System", "AIRPLANE_MODE_ON")).toString()}, new Class[]{ContentResolver.class, String.class})).intValue() == 1;
            } catch (Exception e2) {
                return false;
            }
        } else {
            try {
                if (((Integer) bv.a("android.provider.Settings$Global", "getInt", new Object[]{contentResolver, ((String) bv.a("android.provider.Settings$Global", "AIRPLANE_MODE_ON")).toString()}, new Class[]{ContentResolver.class, String.class})).intValue() != 1) {
                    z = false;
                }
                return z;
            } catch (Exception e3) {
                return false;
            }
        }
    }

    public static float a(double[] dArr) {
        if (dArr.length != 4) {
            return 0.0f;
        }
        float[] fArr = new float[1];
        Location.distanceBetween(dArr[0], dArr[1], dArr[2], dArr[3], fArr);
        return fArr[0];
    }

    public static float a(AmapLoc amapLoc, AmapLoc amapLoc2) {
        return a(new double[]{amapLoc.i(), amapLoc.h(), amapLoc2.i(), amapLoc2.h()});
    }

    public static Object a(Context context, String str) {
        if (context == null) {
            return null;
        }
        try {
            return context.getApplicationContext().getSystemService(str);
        } catch (Throwable th) {
            return null;
        }
    }

    public static int c() {
        if (a > 0) {
            return a;
        }
        try {
            return bv.b("android.os.Build$VERSION", "SDK_INT");
        } catch (Exception e2) {
            try {
                return Integer.parseInt(bv.a("android.os.Build$VERSION", "SDK").toString());
            } catch (Exception e3) {
                return 0;
            }
        }
    }

    public static byte[] a(byte[] bArr) {
        try {
            return w.a(bArr);
        } catch (Exception e2) {
            return null;
        }
    }

    public static String d() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String e() {
        StringBuilder sb = new StringBuilder();
        sb.append(d()).append(File.separator);
        sb.append("amaplocationapi").append(File.separator);
        return sb.toString();
    }

    public static String f() {
        return Build.MODEL;
    }

    public static String g() {
        return Build.VERSION.RELEASE;
    }

    public static String b(Context context) {
        PackageInfo packageInfo;
        CharSequence charSequence = null;
        if (context == null) {
            return null;
        }
        if (!TextUtils.isEmpty(e.k)) {
            return e.k;
        }
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 64);
        } catch (Exception e2) {
            packageInfo = null;
        }
        try {
            if (TextUtils.isEmpty(e.l)) {
                e.l = null;
            }
        } catch (Exception e3) {
        }
        StringBuilder sb = new StringBuilder();
        if (packageInfo != null) {
            if (packageInfo.applicationInfo != null) {
                charSequence = packageInfo.applicationInfo.loadLabel(context.getPackageManager());
            }
            if (charSequence != null) {
                sb.append(charSequence.toString());
            }
            if (!TextUtils.isEmpty(packageInfo.versionName)) {
                sb.append(packageInfo.versionName);
            }
        }
        if (!TextUtils.isEmpty(e.h)) {
            sb.append(ListUtils.DEFAULT_JOIN_SEPARATOR).append(e.h);
        }
        if (!TextUtils.isEmpty(e.l)) {
            sb.append(ListUtils.DEFAULT_JOIN_SEPARATOR).append(e.l);
        }
        return sb.toString();
    }

    public static NetworkInfo c(Context context) {
        try {
            return q.i(context);
        } catch (Exception e2) {
            return null;
        }
    }

    public static boolean h() {
        return a(0, 1) == 1;
    }

    public static int a(int i, int i2) {
        return new Random().nextInt((i2 - i) + 1) + i;
    }

    public static boolean a(JSONObject jSONObject, String str) {
        return w.a(jSONObject, str);
    }

    public static boolean a(ScanResult scanResult) {
        if (scanResult == null || TextUtils.isEmpty(scanResult.BSSID) || scanResult.BSSID.equals("00:00:00:00:00:00") || scanResult.BSSID.contains(" :")) {
            return false;
        }
        return true;
    }

    public static void i() {
        c.clear();
    }

    public static String j() {
        try {
            return r.a(e.f.getBytes("UTF-8")).substring(20);
        } catch (Exception e2) {
            return "";
        }
    }

    public static int a(NetworkInfo networkInfo) {
        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
            return networkInfo.getType();
        }
        return -1;
    }

    public static String b(TelephonyManager telephonyManager) {
        int i = 0;
        if (telephonyManager != null) {
            i = telephonyManager.getNetworkType();
        }
        return e.r.get(i, "UNKWN");
    }

    public static boolean d(Context context) {
        try {
            for (ActivityManager.RunningAppProcessInfo next : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
                if (next.processName.equals(context.getPackageName())) {
                    if (next.importance != 100) {
                        return true;
                    }
                    return false;
                }
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return true;
        }
    }

    public static final long b(byte[] bArr) {
        long j = 0;
        for (int i = 0; i < 8; i++) {
            j = (j << 8) | ((long) (bArr[i] & BLEServiceApi.CONNECTED_STATUS));
        }
        return j;
    }

    public static final int c(byte[] bArr) {
        return ((bArr[0] & BLEServiceApi.CONNECTED_STATUS) << 8) | (bArr[1] & BLEServiceApi.CONNECTED_STATUS);
    }

    public static final int d(byte[] bArr) {
        byte b2 = 0;
        for (int i = 0; i < 4; i++) {
            b2 = (b2 << 8) | (bArr[i] & BLEServiceApi.CONNECTED_STATUS);
        }
        return b2;
    }

    public static final byte[] a(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new IOException("can't operate on null");
        }
        byte[] bArr = new byte[2048];
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int read = fileInputStream.read(bArr);
            if (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                fileInputStream.close();
                byteArrayOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    public static byte[] b(String str) {
        byte[] bArr = new byte[6];
        String[] split = str.split(":");
        for (int i = 0; i < split.length; i++) {
            bArr[i] = (byte) Integer.parseInt(split[i], 16);
        }
        return bArr;
    }

    public static String c(String str) {
        return a(str, 0);
    }

    public static String a(String str, int i) {
        byte[] bArr = null;
        try {
            bArr = str.getBytes("UTF-8");
        } catch (Exception e2) {
        }
        return Base64.encodeToString(bArr, i);
    }

    public static String d(String str) {
        try {
            return new String(Base64.decode((String) null, 0), "UTF-8");
        } catch (Exception e2) {
            return null;
        }
    }

    public static byte[] a(long j) {
        byte[] bArr = new byte[8];
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) ((int) ((j >> (i * 8)) & 255));
        }
        return bArr;
    }

    public static byte[] e(String str) {
        return b(Integer.parseInt(str));
    }

    public static byte[] b(int i) {
        byte[] bArr = new byte[2];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = (byte) ((i >> (i2 * 8)) & 255);
        }
        return bArr;
    }

    public static synchronized String a(long j, String str) {
        String format;
        synchronized (bw.class) {
            if (TextUtils.isEmpty(str)) {
                str = "yyyy-MM-dd HH:mm:ss";
            }
            if (e == null) {
                try {
                    e = new SimpleDateFormat(str, Locale.CHINA);
                } catch (Exception e2) {
                }
            } else {
                e.applyPattern(str);
            }
            if (j <= 0) {
                j = a();
            }
            if (e == null) {
                format = "NULL";
            } else {
                format = e.format(Long.valueOf(j));
            }
        }
        return format;
    }

    public static synchronized boolean a(long j, long j2) {
        boolean z;
        synchronized (bw.class) {
            z = false;
            if (e == null) {
                try {
                    e = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
                } catch (Exception e2) {
                }
            } else {
                e.applyPattern("yyyyMMdd");
            }
            try {
                if (e != null) {
                    z = e.format(Long.valueOf(j)).equals(e.format(Long.valueOf(j2)));
                }
            } catch (Throwable th) {
            }
        }
        return z;
    }

    public static byte[] f(String str) {
        return c(Integer.parseInt(str));
    }

    public static byte[] c(int i) {
        byte[] bArr = new byte[4];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = (byte) ((i >> (i2 * 8)) & 255);
        }
        return bArr;
    }

    public static boolean k() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static String a(Object obj, String str) {
        Locale.setDefault(Locale.US);
        if (d == null) {
            d = new DecimalFormat("#");
        }
        d.applyPattern(str);
        return d.format(obj);
    }
}

package com.baidu.location.f;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.provider.Settings;
import android.support.v7.widget.ActivityChooserView;
import android.text.TextUtils;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import com.alipay.sdk.util.h;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.b.d;
import com.baidu.location.d.a;
import com.baidu.location.d.b;
import com.baidu.location.d.e;
import com.baidu.location.d.g;
import com.baidu.location.f;
import com.baidu.mapapi.UIMsg;
import com.baidu.platform.comapi.location.CoordinateType;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;

public class j {
    public static float A = 2.3f;
    public static float B = 3.8f;
    public static int C = 3;
    public static int D = 10;
    public static int E = 2;
    public static int F = 7;
    public static int G = 20;
    public static int H = 70;
    public static int I = 120;
    public static float J = 2.0f;
    public static float K = 10.0f;
    public static float L = 50.0f;
    public static float M = 200.0f;
    public static int N = 16;
    public static float O = 0.9f;
    public static int P = 10000;
    public static float Q = 0.5f;
    public static float R = 0.0f;
    public static float S = 0.1f;
    public static int T = 30;
    public static int U = 100;
    public static int V = 0;
    public static int W = 0;
    public static int X = 0;
    public static int Y = 420000;
    public static boolean Z = true;
    public static boolean a = false;
    private static String aA = "http://loc.map.baidu.com/iofd.php";
    private static String aB = "http://loc.map.baidu.com/wloc";
    public static boolean aa = true;
    public static int ab = 20;
    public static int ac = 300;
    public static int ad = 1000;
    public static int ae = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    public static long af = 900000;
    public static long ag = 420000;
    public static long ah = 180000;
    public static long ai = 0;
    public static long aj = 15;
    public static long ak = 300000;
    public static int al = 1000;
    public static int am = 0;
    public static int an = UIMsg.m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT;
    public static int ao = UIMsg.m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT;
    public static float ap = 10.0f;
    public static float aq = 6.0f;
    public static float ar = 10.0f;
    public static int as = 60;
    public static int at = 70;
    public static int au = 6;
    private static String av = "http://loc.map.baidu.com/sdk.php";
    private static String aw = "http://loc.map.baidu.com/user_err.php";
    private static String ax = "http://loc.map.baidu.com/oqur.php";
    private static String ay = "http://loc.map.baidu.com/tcu.php";
    private static String az = "http://loc.map.baidu.com/rtbu.php";
    public static boolean b = false;
    public static boolean c = false;
    public static int d = 0;
    public static String e = "http://loc.map.baidu.com/sdk_ep.php";
    public static String f = "https://loc.map.baidu.com/sdk.php";
    public static String g = "no";
    public static boolean h = false;
    public static boolean i = false;
    public static boolean j = false;
    public static boolean k = false;
    public static boolean l = false;
    public static String m = CoordinateType.GCJ02;
    public static String n = "";
    public static boolean o = true;
    public static int p = 3;
    public static double q = 0.0d;
    public static double r = 0.0d;
    public static double s = 0.0d;
    public static double t = 0.0d;
    public static int u = 0;
    public static byte[] v = null;
    public static boolean w = false;
    public static int x = 0;
    public static float y = 1.1f;
    public static float z = 2.2f;

    public static int a(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(), "airplane_mode_on", 0);
        } catch (Exception e2) {
            return 2;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0014, code lost:
        r1 = r1 + r5.length();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int a(java.lang.String r4, java.lang.String r5, java.lang.String r6) {
        /*
            r3 = -1
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r4 == 0) goto L_0x000d
            java.lang.String r1 = ""
            boolean r1 = r4.equals(r1)
            if (r1 == 0) goto L_0x000e
        L_0x000d:
            return r0
        L_0x000e:
            int r1 = r4.indexOf(r5)
            if (r1 == r3) goto L_0x000d
            int r2 = r5.length()
            int r1 = r1 + r2
            int r2 = r4.indexOf(r6, r1)
            if (r2 == r3) goto L_0x000d
            java.lang.String r1 = r4.substring(r1, r2)
            if (r1 == 0) goto L_0x000d
            java.lang.String r2 = ""
            boolean r2 = r1.equals(r2)
            if (r2 != 0) goto L_0x000d
            int r0 = java.lang.Integer.parseInt(r1)     // Catch:{ NumberFormatException -> 0x0032 }
            goto L_0x000d
        L_0x0032:
            r1 = move-exception
            goto L_0x000d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.f.j.a(java.lang.String, java.lang.String, java.lang.String):int");
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

    public static Object a(Object obj, String str, Object... objArr) throws Exception {
        Class<?> cls = obj.getClass();
        Class<Integer>[] clsArr = new Class[objArr.length];
        int length = objArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            clsArr[i2] = objArr[i2].getClass();
            if (clsArr[i2] == Integer.class) {
                clsArr[i2] = Integer.TYPE;
            }
        }
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        if (!declaredMethod.isAccessible()) {
            declaredMethod.setAccessible(true);
        }
        return declaredMethod.invoke(obj, objArr);
    }

    public static String a() {
        Calendar instance = Calendar.getInstance();
        int i2 = instance.get(5);
        int i3 = instance.get(1);
        int i4 = instance.get(11);
        int i5 = instance.get(12);
        int i6 = instance.get(13);
        return String.format(Locale.CHINA, "%d-%02d-%02d %02d:%02d:%02d", new Object[]{Integer.valueOf(i3), Integer.valueOf(instance.get(2) + 1), Integer.valueOf(i2), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6)});
    }

    public static String a(a aVar, g gVar, Location location, String str, int i2) {
        return a(aVar, gVar, location, str, i2, false);
    }

    public static String a(a aVar, g gVar, Location location, String str, int i2, boolean z2) {
        String a2;
        String b2;
        StringBuffer stringBuffer = new StringBuffer(1024);
        if (!(aVar == null || (b2 = b.a().b(aVar)) == null)) {
            stringBuffer.append(b2);
        }
        if (gVar != null) {
            String b3 = i2 == 0 ? z2 ? gVar.b() : gVar.c() : gVar.d();
            if (b3 != null) {
                stringBuffer.append(b3);
            }
        }
        if (location != null) {
            String b4 = (d == 0 || i2 == 0) ? e.b(location) : e.c(location);
            if (b4 != null) {
                stringBuffer.append(b4);
            }
        }
        boolean z3 = false;
        if (i2 == 0) {
            z3 = true;
        }
        String a3 = b.a().a(z3);
        if (a3 != null) {
            stringBuffer.append(a3);
        }
        if (str != null) {
            stringBuffer.append(str);
        }
        String d2 = d.a().d();
        if (!TextUtils.isEmpty(d2)) {
            stringBuffer.append("&bc=").append(d2);
        }
        if (i2 == 0) {
        }
        if (!(aVar == null || (a2 = b.a().a(aVar)) == null || a2.length() + stringBuffer.length() >= 750)) {
            stringBuffer.append(a2);
        }
        String stringBuffer2 = stringBuffer.toString();
        if (location == null || gVar == null) {
            p = 3;
        } else {
            try {
                float speed = location.getSpeed();
                int i3 = d;
                int g2 = gVar.g();
                int a4 = gVar.a();
                boolean h2 = gVar.h();
                if (speed < aq && ((i3 == 1 || i3 == 0) && (g2 < as || h2))) {
                    p = 1;
                } else if (speed >= ar || (!(i3 == 1 || i3 == 0 || i3 == 3) || (g2 >= at && a4 <= au))) {
                    p = 3;
                } else {
                    p = 2;
                }
            } catch (Exception e2) {
                p = 3;
            }
        }
        return stringBuffer2;
    }

    public static String a(File file, String str) {
        if (!file.isFile()) {
            return null;
        }
        byte[] bArr = new byte[1024];
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            FileInputStream fileInputStream = new FileInputStream(file);
            while (true) {
                int read = fileInputStream.read(bArr, 0, 1024);
                if (read != -1) {
                    instance.update(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    return new BigInteger(1, instance.digest()).toString(16);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String a(String str) {
        return Jni.en1(n + h.b + str);
    }

    public static boolean a(BDLocation bDLocation) {
        int locType = bDLocation.getLocType();
        return (locType > 100 && locType < 200) || locType == 62;
    }

    public static int b(Context context) {
        if (Build.VERSION.SDK_INT < 19) {
            return -2;
        }
        try {
            return Settings.Secure.getInt(context.getContentResolver(), "location_mode", -1);
        } catch (Exception e2) {
            return -1;
        }
    }

    private static int b(Context context, String str) {
        boolean z2;
        try {
            z2 = context.checkPermission(str, Process.myPid(), Process.myUid()) == 0;
        } catch (Exception e2) {
            z2 = true;
        }
        return !z2 ? 0 : 1;
    }

    public static int b(Object obj, String str, Object... objArr) throws Exception {
        Class<?> cls = obj.getClass();
        Class<Integer>[] clsArr = new Class[objArr.length];
        int length = objArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            clsArr[i2] = objArr[i2].getClass();
            if (clsArr[i2] == Integer.class) {
                clsArr[i2] = Integer.TYPE;
            }
        }
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        if (!declaredMethod.isAccessible()) {
            declaredMethod.setAccessible(true);
        }
        return ((Integer) declaredMethod.invoke(obj, objArr)).intValue();
    }

    public static String b() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (true) {
                    if (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement = inetAddresses.nextElement();
                        if (!nextElement.isLoopbackAddress() && (nextElement instanceof Inet4Address)) {
                            byte[] address = nextElement.getAddress();
                            String str = "";
                            int i2 = 0;
                            while (true) {
                                int i3 = i2;
                                String str2 = str;
                                if (i3 >= address.length) {
                                    return str2;
                                }
                                String hexString = Integer.toHexString(address[i3] & BLEServiceApi.CONNECTED_STATUS);
                                if (hexString.length() == 1) {
                                    hexString = '0' + hexString;
                                }
                                str = str2 + hexString;
                                i2 = i3 + 1;
                            }
                        }
                    }
                }
            }
        } catch (Exception e2) {
        }
        return null;
    }

    public static boolean b(String str, String str2, String str3) {
        try {
            PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(com.baidu.android.bbalbs.common.a.b.a(str3.getBytes())));
            Signature instance = Signature.getInstance("SHA1WithRSA");
            instance.initVerify(generatePublic);
            instance.update(str.getBytes());
            return instance.verify(com.baidu.android.bbalbs.common.a.b.a(str2.getBytes()));
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static String c() {
        return av;
    }

    public static String c(Context context) {
        int b2 = b(context, "android.permission.ACCESS_COARSE_LOCATION");
        int b3 = b(context, "android.permission.ACCESS_FINE_LOCATION");
        return "&per=" + b2 + "|" + b3 + "|" + b(context, "android.permission.READ_PHONE_STATE");
    }

    public static String d() {
        return ay;
    }

    public static String d(Context context) {
        int i2;
        int i3 = -1;
        if (context != null) {
            try {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                    i2 = activeNetworkInfo.getType();
                    i3 = i2;
                    return "&netc=" + i3;
                }
            } catch (Exception e2) {
            }
        }
        i2 = -1;
        i3 = i2;
        return "&netc=" + i3;
    }

    public static String e() {
        return "https://daup.map.baidu.com/cltr/rcvr";
    }

    public static String f() {
        try {
            if (!Environment.getExternalStorageState().equals("mounted")) {
                return null;
            }
            String path = Environment.getExternalStorageDirectory().getPath();
            File file = new File(path + "/baidu/tempdata");
            if (file.exists()) {
                return path;
            }
            file.mkdirs();
            return path;
        } catch (Exception e2) {
            return null;
        }
    }

    public static String g() {
        String f2 = f();
        if (f2 == null) {
            return null;
        }
        return f2 + "/baidu/tempdata";
    }

    public static String h() {
        try {
            File file = new File(f.getServiceContext().getFilesDir() + File.separator + "lldt");
            if (!file.exists()) {
                file.mkdirs();
            }
            return file.getAbsolutePath();
        } catch (Exception e2) {
            return null;
        }
    }
}

package cn.sharesdk.framework;

import android.content.Context;
import android.graphics.Bitmap;
import com.mob.tools.utils.DeviceHelper;
import java.util.HashMap;

public class ShareSDK {
    public static final String SDK_TAG = "SHARESDK";
    public static final int SDK_VERSION_INT = 72;
    private static f a;
    private static boolean b = true;

    public static void initSDK(Context context) {
        initSDK(context, (String) null, true);
    }

    public static void initSDK(Context context, boolean enableStatistics) {
        initSDK(context, (String) null, enableStatistics);
    }

    public static void initSDK(Context context, String SDKAppKey) {
        initSDK(context, SDKAppKey, true);
    }

    public static void initSDK(Context context, String SDKAppKey, boolean enableStatistics) {
        if (DeviceHelper.getInstance(context) == null) {
            throw new RuntimeException("The param of context is null which in ShareSDK.initSDK(context)!");
        } else if (a == null) {
            f fVar = new f(context, SDKAppKey);
            fVar.a(enableStatistics);
            fVar.startThread();
            a = fVar;
        }
    }

    public static void stopSDK(Context context) {
        stopSDK();
    }

    public static void stopSDK() {
    }

    private static void b() {
        if (a == null) {
            throw new RuntimeException("Please call ShareSDK.initSDK(Context) in the main process before any action.");
        }
    }

    public static void registerService(Class<? extends Service> service) {
        b();
        a.a(service);
    }

    public static void unregisterService(Class<? extends Service> service) {
        b();
        a.b(service);
    }

    public static <T extends Service> T getService(Class<T> name) {
        b();
        return a.c(name);
    }

    public static void registerPlatform(Class<? extends CustomPlatform> platform) {
        b();
        a.d(platform);
    }

    public static void unregisterPlatform(Class<? extends CustomPlatform> platform) {
        b();
        a.e(platform);
    }

    public static String getSDKVersionName() {
        b();
        return a.b();
    }

    public static int getSDKVersionCode() {
        return 72;
    }

    public static void setConnTimeout(int timeout) {
        b();
        a.a(timeout);
    }

    public static void setReadTimeout(int timeout) {
        b();
        a.b(timeout);
    }

    public static void removeCookieOnAuthorize(boolean remove) {
        b();
        a.b(remove);
    }

    public static void deleteCache() {
        b();
        a.e();
    }

    @Deprecated
    public static synchronized Platform[] getPlatformList(Context context) {
        Platform[] platformList;
        synchronized (ShareSDK.class) {
            platformList = getPlatformList();
        }
        return platformList;
    }

    public static synchronized Platform[] getPlatformList() {
        Platform[] a2;
        synchronized (ShareSDK.class) {
            b();
            a2 = a.a();
        }
        return a2;
    }

    @Deprecated
    public static Platform getPlatform(Context context, String name) {
        b();
        return a.a(name);
    }

    public static Platform getPlatform(String name) {
        b();
        return a.a(name);
    }

    public static void logDemoEvent(int id, Platform platform) {
        b();
        a.a(id, platform);
    }

    public static void logApiEvent(String api, int platformId) {
        b();
        a.a(api, platformId);
    }

    public static void setPlatformDevInfo(String platform, HashMap<String, Object> devInfo) {
        b();
        a.a(platform, devInfo);
    }

    public static String platformIdToName(int platformId) {
        b();
        return a.c(platformId);
    }

    public static int platformNameToId(String platformName) {
        b();
        return a.b(platformName);
    }

    public static boolean isRemoveCookieOnAuthorize() {
        b();
        return a.c();
    }

    public static void closeDebug() {
        b = false;
    }

    public static boolean isDebug() {
        return b;
    }

    static void a(String str, String str2) {
        b();
        a.a(str, str2);
    }

    static void a(int i, int i2) {
        b();
        a.a(i, i2);
    }

    static String b(String str, String str2) {
        b();
        return a.b(str, str2);
    }

    static String a(int i, String str) {
        b();
        return a.a(i, str);
    }

    static boolean a() {
        b();
        return a.d();
    }

    static boolean a(HashMap<String, Object> hashMap) {
        b();
        return a.a(hashMap);
    }

    static boolean b(HashMap<String, Object> hashMap) {
        b();
        return a.b(hashMap);
    }

    static String a(String str, boolean z, int i, String str2) {
        b();
        return a.a(str, z, i, str2);
    }

    static String a(String str) {
        b();
        return a.c(str);
    }

    static String a(Bitmap bitmap) {
        b();
        return a.a(bitmap);
    }
}

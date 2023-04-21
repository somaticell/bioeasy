package com.mob.commons;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import cn.com.bioeasy.app.common.ACache;
import com.alipay.sdk.cons.c;
import com.baidu.mapapi.UIMsg;
import com.mob.MobSDK;
import com.mob.tools.MobLog;
import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ReflectHelper;
import com.mob.tools.utils.ResHelper;
import java.util.ArrayList;
import java.util.HashMap;
import org.achartengine.chart.TimeChart;

/* compiled from: CommonConfig */
public class a {
    /* access modifiers changed from: private */
    public static HashMap<String, Object> a;
    private static long b;
    private static long c;
    /* access modifiers changed from: private */
    public static boolean d;

    public static long a(Context context) {
        long j;
        q(context);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        try {
            j = Long.valueOf(String.valueOf(a.get("deviceTime"))).longValue();
        } catch (Throwable th) {
            j = 0;
        }
        return ((Long) ResHelper.forceCast(a.get("serverTime"), 0L)).longValue() + (elapsedRealtime - j);
    }

    public static boolean b(Context context) {
        q(context);
        return 1 == ((Integer) ResHelper.forceCast(a.get("rt"), 0)).intValue();
    }

    public static int c(Context context) {
        q(context);
        return ((Integer) ResHelper.forceCast(a.get("rtsr"), 300)).intValue();
    }

    public static boolean d(Context context) {
        q(context);
        return 1 == ((Integer) ResHelper.forceCast(a.get("in"), 0)).intValue();
    }

    public static boolean e(Context context) {
        q(context);
        return 1 == ((Integer) ResHelper.forceCast(a.get("all"), 0)).intValue();
    }

    public static boolean f(Context context) {
        q(context);
        return 1 == ((Integer) ResHelper.forceCast(a.get("un"), 0)).intValue();
    }

    public static long g(Context context) {
        q(context);
        return ((Long) ResHelper.forceCast(a.get("aspa"), 2592000L)).longValue();
    }

    public static boolean h(Context context) {
        q(context);
        return 1 == ((Integer) ResHelper.forceCast(a.get("di"), 0)).intValue();
    }

    public static boolean i(Context context) {
        q(context);
        return 1 == ((Integer) ResHelper.forceCast(a.get("ext"), 0)).intValue();
    }

    public static boolean j(Context context) {
        q(context);
        return 1 == ((Integer) ResHelper.forceCast(a.get("bs"), 0)).intValue();
    }

    public static int k(Context context) {
        q(context);
        return ((Integer) ResHelper.forceCast(a.get("bsgap"), Integer.valueOf(ACache.TIME_DAY))).intValue();
    }

    public static boolean l(Context context) {
        q(context);
        return 1 == ((Integer) ResHelper.forceCast(a.get("l"), 0)).intValue();
    }

    public static int m(Context context) {
        q(context);
        return ((Integer) ResHelper.forceCast(a.get("lgap"), Integer.valueOf(ACache.TIME_DAY))).intValue();
    }

    public static boolean n(Context context) {
        q(context);
        return 1 == ((Integer) ResHelper.forceCast(a.get("wi"), 0)).intValue();
    }

    public static long o(Context context) {
        q(context);
        return (((long) ((Integer) ResHelper.forceCast(a.get("adle"), 172800)).intValue()) * 1000) + a(context);
    }

    private static synchronized void q(Context context) {
        synchronized (a.class) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (a == null) {
                if (r(context)) {
                    b = elapsedRealtime;
                }
            } else if (elapsedRealtime - b >= 60000 && s(context)) {
                b = elapsedRealtime;
            }
        }
    }

    private static boolean r(Context context) {
        String t = t(context);
        if (TextUtils.isEmpty(t)) {
            b();
            return false;
        }
        b(t);
        e.d(context, new Hashon().fromHashMap(a));
        return true;
    }

    private static boolean s(Context context) {
        String e = e.e(context);
        if (TextUtils.isEmpty(e)) {
            return r(context);
        }
        b(e);
        if (((Long) ResHelper.forceCast(a.get("timestamp"), 0L)).longValue() - c >= TimeChart.DAY) {
            u(context);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static String t(Context context) {
        HashMap fromJson;
        try {
            NetworkHelper networkHelper = new NetworkHelper();
            ArrayList<MobProduct> products = MobProductCollector.getProducts();
            if (products.isEmpty()) {
                return null;
            }
            Object invokeStaticMethod = ReflectHelper.invokeStaticMethod("DeviceHelper", "getInstance", context);
            ArrayList arrayList = new ArrayList();
            String appkey = MobSDK.getAppkey();
            if (appkey == null) {
                appkey = products.get(0).getProductAppkey();
            }
            arrayList.add(new KVPair(com.alipay.sdk.sys.a.f, appkey));
            arrayList.add(new KVPair("plat", String.valueOf(ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getPlatformCode", new Object[0]))));
            arrayList.add(new KVPair("apppkg", String.valueOf(ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getPackageName", new Object[0]))));
            arrayList.add(new KVPair("appver", String.valueOf(ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getAppVersionName", new Object[0]))));
            arrayList.add(new KVPair("networktype", String.valueOf(ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getDetailNetworkTypeForStatic", new Object[0]))));
            String a2 = new com.mob.commons.authorize.a().a(context);
            if (!TextUtils.isEmpty(a2)) {
                arrayList.add(new KVPair("duid", a2));
            }
            NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
            networkTimeOut.readTimout = UIMsg.m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT;
            networkTimeOut.connectionTimeout = UIMsg.m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT;
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(new KVPair("User-Identity", MobProductCollector.getUserIdentity(context, products)));
            String httpGet = networkHelper.httpGet(v(context), arrayList, arrayList2, networkTimeOut);
            Hashon hashon = new Hashon();
            HashMap fromJson2 = hashon.fromJson(httpGet);
            if (fromJson2 == null) {
                return null;
            }
            if (!"200".equals(String.valueOf(fromJson2.get("status")))) {
                e.e(context, (String) null);
                e.f(context, (String) null);
                throw new Throwable("response is illegal: " + httpGet);
            }
            String str = (String) ResHelper.forceCast(fromJson2.get("sr"));
            if (!(str == null || (fromJson = hashon.fromJson(Data.AES128Decode("FYsAXMqlWJLCDpnc", Base64.decode(str, 2)))) == null)) {
                HashMap hashMap = (HashMap) ResHelper.forceCast(fromJson.get("cdata"));
                if (hashMap != null) {
                    String str2 = (String) ResHelper.forceCast(hashMap.get(c.f));
                    int intValue = ((Integer) ResHelper.forceCast(hashMap.get("httpport"), 0)).intValue();
                    String str3 = (String) ResHelper.forceCast(hashMap.get("path"));
                    if (str2 == null || intValue == 0 || str3 == null) {
                        e.e(context, (String) null);
                    } else {
                        e.e(context, "http://" + str2 + ":" + intValue + str3);
                    }
                } else {
                    e.e(context, (String) null);
                }
                HashMap hashMap2 = (HashMap) ResHelper.forceCast(fromJson.get("cconf"));
                if (hashMap2 != null) {
                    String str4 = (String) ResHelper.forceCast(hashMap2.get(c.f));
                    int intValue2 = ((Integer) ResHelper.forceCast(hashMap2.get("httpport"), 0)).intValue();
                    String str5 = (String) ResHelper.forceCast(hashMap2.get("path"));
                    if (str4 == null || intValue2 == 0 || str5 == null) {
                        e.f(context, (String) null);
                    } else {
                        e.f(context, "http://" + str4 + ":" + intValue2 + str5);
                    }
                } else {
                    e.f(context, (String) null);
                }
            }
            String str6 = (String) ResHelper.forceCast(fromJson2.get("sc"));
            if (str6 == null) {
                throw new Throwable("response is illegal: " + httpGet);
            }
            HashMap fromJson3 = hashon.fromJson(Data.AES128Decode("FYsAXMqlWJLCDpnc", Base64.decode(str6, 2)));
            if (fromJson3 == null) {
                throw new Throwable("response is illegal: " + httpGet);
            }
            long longValue = ((Long) ResHelper.forceCast(fromJson2.get("timestamp"), 0L)).longValue();
            fromJson3.put("deviceTime", Long.valueOf(SystemClock.elapsedRealtime()));
            fromJson3.put("serverTime", Long.valueOf(longValue));
            return hashon.fromHashMap(fromJson3);
        } catch (Throwable th) {
            e.e(context, (String) null);
            e.f(context, (String) null);
            MobLog.getInstance().w(th);
            return null;
        }
    }

    private static void b() {
        a = new HashMap<>();
        a.put("in", 0);
        a.put("all", 0);
        a.put("aspa", 2592000L);
        a.put("un", 0);
        a.put("rt", 0);
        a.put("rtsr", 300000);
        a.put("mi", 0);
        a.put("ext", 0);
        a.put("bs", 0);
        a.put("bsgap", Integer.valueOf(ACache.TIME_DAY));
        a.put("di", 0);
        a.put("l", 0);
        a.put("lgap", Integer.valueOf(ACache.TIME_DAY));
        a.put("wi", 0);
        a.put("adle", 172800);
    }

    /* access modifiers changed from: private */
    public static void b(String str) {
        try {
            a = new Hashon().fromJson(str);
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
    }

    private static void u(final Context context) {
        if (!d) {
            d = true;
            new Thread() {
                public void run() {
                    String p = a.t(context);
                    if (!TextUtils.isEmpty(p)) {
                        a.b(p);
                        e.d(context, new Hashon().fromHashMap(a.a));
                    }
                    boolean unused = a.d = false;
                }
            }.start();
        }
    }

    private static String v(Context context) {
        String str = null;
        try {
            str = e.g(context);
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
        return TextUtils.isEmpty(str) ? "http://m.data.mob.com/v2/cconf" : str;
    }
}

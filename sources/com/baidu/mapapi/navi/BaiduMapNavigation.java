package com.baidu.mapapi.navi;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import cn.com.bioeasy.app.utils.ListUtils;
import com.alipay.sdk.cons.a;
import com.baidu.mapapi.VersionInfo;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.utils.OpenClientUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BaiduMapNavigation {
    private static boolean a = true;

    private static String a(Context context) {
        PackageManager packageManager;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            try {
                applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
            }
        } catch (PackageManager.NameNotFoundException e2) {
            packageManager = null;
        }
        return (String) packageManager.getApplicationLabel(applicationInfo);
    }

    private static void a(NaviParaOption naviParaOption, Context context) throws IllegalNaviArgumentException {
        if (naviParaOption == null || context == null) {
            throw new IllegalNaviArgumentException("para or context can not be null.");
        } else if (naviParaOption.a == null || naviParaOption.c == null) {
            throw new IllegalNaviArgumentException("you must set start and end point.");
        } else {
            GeoPoint ll2mc = CoordUtil.ll2mc(naviParaOption.a);
            GeoPoint ll2mc2 = CoordUtil.ll2mc(naviParaOption.c);
            StringBuilder sb = new StringBuilder();
            sb.append("http://app.navi.baidu.com/mobile/#navi/naving/");
            sb.append("&sy=0");
            sb.append("&endp=");
            sb.append("&start=");
            sb.append("&startwd=");
            sb.append("&endwd=");
            sb.append("&fromprod=map_sdk");
            sb.append("&app_version=");
            sb.append(VersionInfo.VERSION_INFO);
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject.put("type", a.e);
                if (naviParaOption.b == null || naviParaOption.b.equals("")) {
                    jSONObject.put("keyword", "");
                } else {
                    jSONObject.put("keyword", naviParaOption.b);
                }
                jSONObject.put("xy", String.valueOf(ll2mc.getLongitudeE6()) + ListUtils.DEFAULT_JOIN_SEPARATOR + String.valueOf(ll2mc.getLatitudeE6()));
                jSONArray.put(jSONObject);
                jSONObject2.put("type", a.e);
                if (naviParaOption.d == null || naviParaOption.d.equals("")) {
                    jSONObject.put("keyword", "");
                } else {
                    jSONObject.put("keyword", naviParaOption.d);
                }
                jSONObject2.put("xy", String.valueOf(ll2mc2.getLongitudeE6()) + ListUtils.DEFAULT_JOIN_SEPARATOR + String.valueOf(ll2mc2.getLatitudeE6()));
                jSONArray.put(jSONObject2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jSONArray.length() > 0) {
                sb.append("&positions=");
                sb.append(jSONArray.toString());
            }
            sb.append("&ctrl_type=");
            sb.append("&mrsl=");
            sb.append("/vt=map&state=entry");
            Uri parse = Uri.parse(sb.toString());
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setFlags(268435456);
            intent.setData(parse);
            context.startActivity(intent);
        }
    }

    public static void finish(Context context) {
        if (context != null) {
            com.baidu.mapapi.utils.a.a(context);
        }
    }

    public static boolean openBaiduMapBikeNavi(NaviParaOption naviParaOption, Context context) {
        if (naviParaOption == null || context == null) {
            throw new IllegalNaviArgumentException("para or context can not be null.");
        } else if (naviParaOption.c == null || naviParaOption.a == null) {
            throw new IllegalNaviArgumentException("start point or end point can not be null.");
        } else {
            int baiduMapVersion = OpenClientUtil.getBaiduMapVersion(context);
            if (baiduMapVersion == 0) {
                Log.e("baidumapsdk", "BaiduMap app is not installed.");
                return false;
            } else if (baiduMapVersion >= 869) {
                return com.baidu.mapapi.utils.a.a(naviParaOption, context, 8);
            } else {
                Log.e("baidumapsdk", "Baidumap app version is too lowl.Version is greater than 8.6.6");
                return false;
            }
        }
    }

    public static boolean openBaiduMapNavi(NaviParaOption naviParaOption, Context context) {
        if (naviParaOption == null || context == null) {
            throw new IllegalNaviArgumentException("para or context can not be null.");
        } else if (naviParaOption.c == null || naviParaOption.a == null) {
            throw new IllegalNaviArgumentException("start point or end point can not be null.");
        } else {
            int baiduMapVersion = OpenClientUtil.getBaiduMapVersion(context);
            if (baiduMapVersion == 0) {
                Log.e("baidumapsdk", "BaiduMap app is not installed.");
                if (a) {
                    a(naviParaOption, context);
                    return true;
                }
                throw new BaiduMapAppNotSupportNaviException("BaiduMap app is not installed.");
            } else if (baiduMapVersion >= 830) {
                return com.baidu.mapapi.utils.a.a(naviParaOption, context, 5);
            } else {
                Log.e("baidumapsdk", "Baidumap app version is too lowl.Version is greater than 8.2");
                if (a) {
                    a(naviParaOption, context);
                    return true;
                }
                throw new BaiduMapAppNotSupportNaviException("Baidumap app version is too lowl.Version is greater than 8.2");
            }
        }
    }

    public static boolean openBaiduMapWalkNavi(NaviParaOption naviParaOption, Context context) {
        if (naviParaOption == null || context == null) {
            throw new IllegalNaviArgumentException("para or context can not be null.");
        } else if (naviParaOption.c == null || naviParaOption.a == null) {
            throw new IllegalNaviArgumentException("start point or end point can not be null.");
        } else {
            int baiduMapVersion = OpenClientUtil.getBaiduMapVersion(context);
            if (baiduMapVersion == 0) {
                Log.e("baidumapsdk", "BaiduMap app is not installed.");
                return false;
            } else if (baiduMapVersion >= 869) {
                return com.baidu.mapapi.utils.a.a(naviParaOption, context, 7);
            } else {
                Log.e("baidumapsdk", "Baidumap app version is too lowl.Version is greater than 8.6.6");
                return false;
            }
        }
    }

    public static boolean openBaiduMapWalkNaviAR(NaviParaOption naviParaOption, Context context) {
        if (naviParaOption == null || context == null) {
            throw new IllegalNaviArgumentException("para or context can not be null.");
        } else if (naviParaOption.c == null || naviParaOption.a == null) {
            throw new IllegalNaviArgumentException("start point or end point can not be null.");
        } else {
            int baiduMapVersion = OpenClientUtil.getBaiduMapVersion(context);
            if (baiduMapVersion == 0) {
                Log.e("baidumapsdk", "BaiduMap app is not installed.");
                return false;
            } else if (baiduMapVersion >= 869) {
                return com.baidu.mapapi.utils.a.a(naviParaOption, context, 9);
            } else {
                Log.e("baidumapsdk", "Baidumap app version is too lowl.Version is greater than 8.6.6");
                return false;
            }
        }
    }

    @Deprecated
    public static void openWebBaiduMapNavi(NaviParaOption naviParaOption, Context context) throws IllegalNaviArgumentException {
        if (naviParaOption == null || context == null) {
            throw new IllegalNaviArgumentException("para or context can not be null.");
        } else if (naviParaOption.a != null && naviParaOption.c != null) {
            GeoPoint ll2mc = CoordUtil.ll2mc(naviParaOption.a);
            GeoPoint ll2mc2 = CoordUtil.ll2mc(naviParaOption.c);
            Uri parse = Uri.parse("http://daohang.map.baidu.com/mobile/#navi/naving/start=" + ll2mc.getLongitudeE6() + ListUtils.DEFAULT_JOIN_SEPARATOR + ll2mc.getLatitudeE6() + "&endp=" + ll2mc2.getLongitudeE6() + ListUtils.DEFAULT_JOIN_SEPARATOR + ll2mc2.getLatitudeE6() + "&fromprod=" + a(context) + "/vt=map&state=entry");
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setFlags(268435456);
            intent.setData(parse);
            context.startActivity(intent);
        } else if (naviParaOption.b == null || naviParaOption.b.equals("") || naviParaOption.d == null || naviParaOption.d.equals("")) {
            throw new IllegalNaviArgumentException("you must set start and end point or set the start and end name.");
        } else {
            Uri parse2 = Uri.parse("http://daohang.map.baidu.com/mobile/#search/search/qt=nav&sn=2$$$$$$" + naviParaOption.b + "$$$$$$&en=2$$$$$$" + naviParaOption.d + "$$$$$$&fromprod=" + a(context));
            Intent intent2 = new Intent();
            intent2.setAction("android.intent.action.VIEW");
            intent2.setData(parse2);
            context.startActivity(intent2);
        }
    }

    public static void setSupportWebNavi(boolean z) {
        a = z;
    }
}

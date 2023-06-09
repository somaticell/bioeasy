package com.baidu.mapapi.utils.poi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import cn.com.bioeasy.app.utils.ListUtils;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.http.HttpClient;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.baidu.mapapi.utils.a;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.platform.comapi.pano.PanoStateError;
import com.baidu.platform.comapi.pano.a;
import com.baidu.platform.comapi.pano.c;
import com.baidu.platform.comapi.util.CoordTrans;
import java.util.List;

public class BaiduMapPoiSearch {
    private static boolean a = true;

    /* renamed from: com.baidu.mapapi.utils.poi.BaiduMapPoiSearch$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[PanoStateError.values().length];
        static final /* synthetic */ int[] b = new int[HttpClient.HttpStateError.values().length];

        static {
            try {
                b[HttpClient.HttpStateError.NETWORK_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                b[HttpClient.HttpStateError.INNER_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[PanoStateError.PANO_UID_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[PanoStateError.PANO_NOT_FOUND.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[PanoStateError.PANO_NO_TOKEN.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[PanoStateError.PANO_NO_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    private static void a(PoiParaOption poiParaOption, Context context) {
        Uri parse = Uri.parse("http://api.map.baidu.com/place/detail?" + "uid=" + poiParaOption.a + "&output=html" + "&src=" + context.getPackageName());
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setFlags(268435456);
        intent.setData(parse);
        context.startActivity(intent);
    }

    private static void b(PoiParaOption poiParaOption, Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://api.map.baidu.com/place/search?");
        sb.append("query=");
        sb.append(poiParaOption.b);
        sb.append("&location=");
        LatLng latLng = poiParaOption.c;
        if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
            latLng = CoordTrans.gcjToBaidu(latLng);
        }
        sb.append(latLng.latitude);
        sb.append(ListUtils.DEFAULT_JOIN_SEPARATOR);
        sb.append(latLng.longitude);
        sb.append("&radius=");
        sb.append(poiParaOption.d);
        sb.append("&output=html");
        sb.append("&src=");
        sb.append(context.getPackageName());
        Uri parse = Uri.parse(sb.toString());
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setFlags(268435456);
        intent.setData(parse);
        context.startActivity(intent);
    }

    /* access modifiers changed from: private */
    public static void b(String str, Context context) {
        if (str == null || str.length() == 0) {
            throw new RuntimeException("pano id can not be null.");
        } else if (context == null) {
            throw new RuntimeException("context cannot be null.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("baidumap://map/streetscape?");
            sb.append("panoid=").append(str);
            sb.append("&pid=").append(str);
            sb.append("&panotype=").append("street");
            sb.append("&src=").append("sdk_[" + context.getPackageName() + "]");
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(sb.toString()));
            intent.setFlags(268435456);
            if (intent == null || intent.resolveActivity(context.getPackageManager()) == null) {
                throw new RuntimeException("BaiduMap app is not installed.");
            }
            context.startActivity(intent);
        }
    }

    public static boolean dispatchPoiToBaiduMap(List<DispathcPoiData> list, Context context) throws Exception {
        if (list.isEmpty() || list.size() <= 0) {
            throw new NullPointerException("dispatch poidata is null");
        }
        int baiduMapVersion = OpenClientUtil.getBaiduMapVersion(context);
        if (baiduMapVersion == 0) {
            Log.e("baidumapsdk", "BaiduMap app is not installed.");
        } else if (baiduMapVersion >= 840) {
            return a.a(list, context, 6);
        } else {
            Log.e("baidumapsdk", "Baidumap app version is too lowl.Version is greater than 8.4");
        }
        return false;
    }

    public static void finish(Context context) {
        if (context != null) {
            a.a(context);
        }
    }

    public static void openBaiduMapPanoShow(String str, Context context) {
        new com.baidu.platform.comapi.pano.a().a(str, (a.C0017a<c>) new a(context));
    }

    public static boolean openBaiduMapPoiDetialsPage(PoiParaOption poiParaOption, Context context) {
        if (poiParaOption == null || context == null) {
            throw new IllegalPoiSearchArgumentException("para or context can not be null.");
        } else if (poiParaOption.a == null) {
            throw new IllegalPoiSearchArgumentException("poi uid can not be null.");
        } else if (poiParaOption.a.equals("")) {
            Log.e(BaiduMapRoutePlan.class.getName(), "poi uid can not be empty string");
            return false;
        } else {
            int baiduMapVersion = OpenClientUtil.getBaiduMapVersion(context);
            if (baiduMapVersion == 0) {
                Log.e("baidumapsdk", "BaiduMap app is not installed.");
                if (a) {
                    a(poiParaOption, context);
                    return true;
                }
                throw new IllegalPoiSearchArgumentException("BaiduMap app is not installed.");
            } else if (baiduMapVersion >= 810) {
                return com.baidu.mapapi.utils.a.a(poiParaOption, context, 3);
            } else {
                Log.e("baidumapsdk", "Baidumap app version is too lowl.Version is greater than 8.1");
                if (a) {
                    a(poiParaOption, context);
                    return true;
                }
                throw new IllegalPoiSearchArgumentException("Baidumap app version is too lowl.Version is greater than 8.1");
            }
        }
    }

    public static boolean openBaiduMapPoiNearbySearch(PoiParaOption poiParaOption, Context context) {
        if (poiParaOption == null || context == null) {
            throw new IllegalPoiSearchArgumentException("para or context can not be null.");
        } else if (poiParaOption.b == null) {
            throw new IllegalPoiSearchArgumentException("poi search key can not be null.");
        } else if (poiParaOption.c == null) {
            throw new IllegalPoiSearchArgumentException("poi search center can not be null.");
        } else if (poiParaOption.c.longitude == 0.0d || poiParaOption.c.latitude == 0.0d) {
            throw new IllegalPoiSearchArgumentException("poi search center longitude or latitude can not be 0.");
        } else if (poiParaOption.d == 0) {
            throw new IllegalPoiSearchArgumentException("poi search radius larger than 0.");
        } else if (poiParaOption.b.equals("")) {
            Log.e(BaiduMapRoutePlan.class.getName(), "poi key can not be empty string");
            return false;
        } else {
            int baiduMapVersion = OpenClientUtil.getBaiduMapVersion(context);
            if (baiduMapVersion == 0) {
                Log.e("baidumapsdk", "BaiduMap app is not installed.");
                if (a) {
                    b(poiParaOption, context);
                    return true;
                }
                throw new IllegalPoiSearchArgumentException("BaiduMap app is not installed.");
            } else if (baiduMapVersion >= 810) {
                return com.baidu.mapapi.utils.a.a(poiParaOption, context, 4);
            } else {
                Log.e("baidumapsdk", "Baidumap app version is too lowl.Version is greater than 8.1");
                if (a) {
                    b(poiParaOption, context);
                    return true;
                }
                throw new IllegalPoiSearchArgumentException("Baidumap app version is too lowl.Version is greater than 8.1");
            }
        }
    }

    public static void setSupportWebPoi(boolean z) {
        a = z;
    }
}

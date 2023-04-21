package com.baidu.platform.core.e;

import cn.com.bioeasy.app.utils.ListUtils;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.inner.Point;
import com.baidu.mapapi.search.share.RouteShareURLOption;
import com.baidu.platform.comjni.util.AppMD5;
import com.baidu.platform.domain.c;
import com.baidu.platform.util.a;

public class e extends com.baidu.platform.base.e {
    public e(RouteShareURLOption routeShareURLOption) {
        a(routeShareURLOption);
    }

    private int a(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void a(RouteShareURLOption routeShareURLOption) {
        a aVar = new a();
        Point ll2point = CoordUtil.ll2point(routeShareURLOption.mFrom.getLocation());
        Point ll2point2 = CoordUtil.ll2point(routeShareURLOption.mTo.getLocation());
        String str = ll2point != null ? "1$$$$" + ll2point.x + ListUtils.DEFAULT_JOIN_SEPARATOR + ll2point.y + "$$" : "2$$$$$$";
        String name = routeShareURLOption.mFrom.getName();
        if (name == null || name.equals("")) {
            name = "起点";
        }
        String str2 = str + name + "$$0$$$$";
        String str3 = ll2point2 != null ? "1$$$$" + ll2point2.x + ListUtils.DEFAULT_JOIN_SEPARATOR + ll2point2.y + "$$" : "2$$$$$$";
        String name2 = routeShareURLOption.mTo.getName();
        if (name2 == null || name2.equals("")) {
            name2 = "终点";
        }
        String str4 = str3 + name2 + "$$0$$$$";
        String str5 = "";
        String str6 = "";
        switch (routeShareURLOption.mMode.ordinal()) {
            case 0:
                str6 = "&sharecallbackflag=carRoute";
                str5 = "nav";
                aVar.a("sc", a(routeShareURLOption.mFrom.getCity()) + "");
                aVar.a("ec", a(routeShareURLOption.mTo.getCity()) + "");
                break;
            case 1:
                str6 = "&sharecallbackflag=footRoute";
                str5 = "walk";
                aVar.a("sc", a(routeShareURLOption.mFrom.getCity()) + "");
                aVar.a("ec", a(routeShareURLOption.mTo.getCity()) + "");
                break;
            case 2:
                str6 = "&sharecallbackflag=cycleRoute";
                str5 = "cycle";
                aVar.a("sc", a(routeShareURLOption.mFrom.getCity()) + "");
                aVar.a("ec", a(routeShareURLOption.mTo.getCity()) + "");
                break;
            case 3:
                str6 = "&i=" + routeShareURLOption.mPn + ",1,1&sharecallbackflag=busRoute";
                aVar.a("c", routeShareURLOption.mCityCode + "");
                str5 = "bt";
                break;
        }
        aVar.a("sn", str2);
        aVar.a("en", str4);
        this.a.a("url", "http://map.baidu.com/?newmap=1&s=" + str5 + (AppMD5.encodeUrlParamsValue(com.alipay.sdk.sys.a.b + aVar.a() + ("&start=" + name + "&end=" + name2)) + str6));
        this.a.a("from", "android_map_sdk");
    }

    public String a(c cVar) {
        return cVar.r();
    }
}

package com.baidu.platform.core.d;

import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.platform.base.e;
import com.baidu.platform.domain.c;

public class m extends e {
    public m(TransitRoutePlanOption transitRoutePlanOption) {
        a(transitRoutePlanOption);
    }

    private void a(TransitRoutePlanOption transitRoutePlanOption) {
        this.a.a("qt", "bus");
        this.a.a("sy", transitRoutePlanOption.mPolicy.getInt() + "");
        this.a.a("ie", "utf-8");
        this.a.a("lrn", "20");
        this.a.a("version", "3");
        this.a.a("rp_format", "json");
        this.a.a("rp_filter", "mobile");
        this.a.a("ic_info", "2");
        this.a.a("sn", a(transitRoutePlanOption.mFrom));
        this.a.a("en", a(transitRoutePlanOption.mTo));
        if (transitRoutePlanOption.mCityName != null) {
            this.a.a("c", transitRoutePlanOption.mCityName);
        }
        if (TransitRoutePlanOption.TransitPolicy.EBUS_NO_SUBWAY == transitRoutePlanOption.mPolicy) {
            this.a.a("f", "[0,2,4,7,5,8,9,10,11]");
        }
    }

    public String a(c cVar) {
        return cVar.h();
    }
}

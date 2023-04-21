package com.baidu.platform.core.b;

import cn.com.bioeasy.app.utils.ListUtils;
import com.alipay.android.phone.mrpc.core.Headers;
import com.alipay.sdk.cons.a;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.platform.base.e;
import com.baidu.platform.comapi.util.CoordTrans;
import com.baidu.platform.domain.c;

public class f extends e {
    public f(ReverseGeoCodeOption reverseGeoCodeOption) {
        a(reverseGeoCodeOption);
    }

    private void a(ReverseGeoCodeOption reverseGeoCodeOption) {
        if (reverseGeoCodeOption.mLocation != null) {
            LatLng latLng = new LatLng(reverseGeoCodeOption.mLocation.latitude, reverseGeoCodeOption.mLocation.longitude);
            if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
                latLng = CoordTrans.gcjToBaidu(latLng);
            }
            this.a.a(Headers.LOCATION, latLng.latitude + ListUtils.DEFAULT_JOIN_SEPARATOR + latLng.longitude);
        }
        this.a.a("coordtype", "bd09ll");
        this.a.a("pois", a.e);
        this.a.a("output", "json");
        this.a.a("from", "android_map_sdk");
        this.a.a("latest_admin", String.valueOf(reverseGeoCodeOption.latest_admin));
    }

    public String a(c cVar) {
        return cVar.e();
    }
}

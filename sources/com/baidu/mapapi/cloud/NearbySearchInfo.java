package com.baidu.mapapi.cloud;

import cn.com.bioeasy.app.utils.ListUtils;
import com.alipay.android.phone.mrpc.core.Headers;
import com.alipay.sdk.sys.a;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.http.HttpClient;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.util.CoordTrans;

public class NearbySearchInfo extends BaseCloudSearchInfo {
    public String location;
    public int radius;

    public NearbySearchInfo() {
        if (HttpClient.isHttpsEnable) {
            this.a = "https://api.map.baidu.com/geosearch/v2/nearby";
        } else {
            this.a = "http://api.map.baidu.com/geosearch/v2/nearby";
        }
        this.radius = 1000;
    }

    /* access modifiers changed from: package-private */
    public String a() {
        StringBuilder sb = new StringBuilder();
        if (super.a() == null) {
            return null;
        }
        sb.append(super.a());
        if (this.location == null || this.location.equals("")) {
            return null;
        }
        if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
            String[] split = this.location.split(ListUtils.DEFAULT_JOIN_SEPARATOR);
            try {
                LatLng gcjToBaidu = CoordTrans.gcjToBaidu(new LatLng(Double.parseDouble(split[1]), Double.parseDouble(split[0])));
                this.location = gcjToBaidu.longitude + ListUtils.DEFAULT_JOIN_SEPARATOR + gcjToBaidu.latitude;
            } catch (Exception e) {
            }
        }
        sb.append(a.b);
        sb.append(Headers.LOCATION);
        sb.append("=");
        sb.append(this.location);
        if (this.radius >= 0) {
            sb.append(a.b);
            sb.append("radius");
            sb.append("=");
            sb.append(this.radius);
        }
        return sb.toString();
    }
}

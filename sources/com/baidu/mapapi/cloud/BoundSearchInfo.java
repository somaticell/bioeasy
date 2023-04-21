package com.baidu.mapapi.cloud;

import cn.com.bioeasy.app.utils.ListUtils;
import com.alipay.sdk.sys.a;
import com.alipay.sdk.util.h;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.http.HttpClient;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.util.CoordTrans;

public class BoundSearchInfo extends BaseCloudSearchInfo {
    public String bound;

    public BoundSearchInfo() {
        if (HttpClient.isHttpsEnable) {
            this.a = "https://api.map.baidu.com/geosearch/v2/bound";
        } else {
            this.a = "http://api.map.baidu.com/geosearch/v2/bound";
        }
    }

    /* access modifiers changed from: package-private */
    public String a() {
        StringBuilder sb = new StringBuilder();
        if (super.a() == null) {
            return null;
        }
        sb.append(super.a());
        if (this.bound == null || this.bound.equals("")) {
            return null;
        }
        if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
            try {
                String[] split = this.bound.split(h.b);
                String[] split2 = split[0].split(ListUtils.DEFAULT_JOIN_SEPARATOR);
                String[] split3 = split[1].split(ListUtils.DEFAULT_JOIN_SEPARATOR);
                LatLng latLng = new LatLng(Double.parseDouble(split2[1]), Double.parseDouble(split2[0]));
                LatLng latLng2 = new LatLng(Double.parseDouble(split3[1]), Double.parseDouble(split3[0]));
                LatLng gcjToBaidu = CoordTrans.gcjToBaidu(latLng);
                LatLng gcjToBaidu2 = CoordTrans.gcjToBaidu(latLng2);
                this.bound = gcjToBaidu.longitude + ListUtils.DEFAULT_JOIN_SEPARATOR + gcjToBaidu.latitude + h.b + gcjToBaidu2.longitude + ListUtils.DEFAULT_JOIN_SEPARATOR + gcjToBaidu2.latitude;
            } catch (Exception e) {
            }
        }
        sb.append(a.b);
        sb.append("bounds");
        sb.append("=");
        sb.append(this.bound);
        return sb.toString();
    }
}

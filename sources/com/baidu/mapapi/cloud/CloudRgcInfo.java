package com.baidu.mapapi.cloud;

import cn.com.bioeasy.app.utils.ListUtils;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.http.HttpClient;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.util.CoordTrans;

public class CloudRgcInfo {
    public int geoTableId;
    public String location;

    /* access modifiers changed from: package-private */
    public String a() {
        StringBuilder sb = new StringBuilder();
        if (HttpClient.isHttpsEnable) {
            sb.append("https://api.map.baidu.com/sdkproxy/lbs_androidsdk/cloudrgc/v1");
        } else {
            sb.append("http://api.map.baidu.com/sdkproxy/lbs_androidsdk/cloudrgc/v1");
        }
        sb.append('?');
        if (this.location == null || this.location.equals("")) {
            return null;
        }
        if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
            String[] split = this.location.split(ListUtils.DEFAULT_JOIN_SEPARATOR);
            try {
                LatLng gcjToBaidu = CoordTrans.gcjToBaidu(new LatLng(Double.parseDouble(split[0]), Double.parseDouble(split[1])));
                this.location = gcjToBaidu.latitude + ListUtils.DEFAULT_JOIN_SEPARATOR + gcjToBaidu.longitude;
            } catch (Exception e) {
            }
        }
        sb.append("location=" + this.location);
        if (this.geoTableId == 0) {
            return null;
        }
        sb.append("&geotable_id=" + this.geoTableId);
        sb.append("&coord_type=bd09ll&extensions=pois&from=android_map_sdk");
        return sb.toString();
    }
}

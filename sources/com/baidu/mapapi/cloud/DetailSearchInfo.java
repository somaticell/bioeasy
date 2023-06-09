package com.baidu.mapapi.cloud;

import com.alipay.sdk.sys.a;
import com.baidu.mapapi.http.HttpClient;

public class DetailSearchInfo extends BaseSearchInfo {
    public String poiId;
    public int uid;

    public DetailSearchInfo() {
        if (HttpClient.isHttpsEnable) {
            this.a = "https://api.map.baidu.com/geosearch/v2/detail/";
        } else {
            this.a = "http://api.map.baidu.com/geosearch/v2/detail/";
        }
    }

    /* access modifiers changed from: package-private */
    public String a() {
        if (this.uid == 0 && (this.poiId == null || this.poiId.equals(""))) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        if (this.poiId == null || this.poiId.equals("")) {
            sb.append(this.uid).append('?');
        } else {
            sb.append(this.poiId).append('?');
        }
        if (this.ak == null || this.ak.equals("") || this.ak.length() > 50) {
            return null;
        }
        sb.append("ak");
        sb.append("=");
        sb.append(this.ak);
        if (this.geoTableId == 0) {
            return null;
        }
        sb.append(a.b);
        sb.append("geotable_id");
        sb.append("=");
        sb.append(this.geoTableId);
        if (this.sn != null && !this.sn.equals("") && this.sn.length() <= 50) {
            sb.append(a.b);
            sb.append("sn");
            sb.append("=");
            sb.append(this.sn);
        }
        return sb.toString();
    }
}

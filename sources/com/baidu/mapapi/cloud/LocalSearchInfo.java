package com.baidu.mapapi.cloud;

import com.alipay.sdk.sys.a;
import com.baidu.mapapi.http.HttpClient;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LocalSearchInfo extends BaseCloudSearchInfo {
    public String region;

    public LocalSearchInfo() {
        if (HttpClient.isHttpsEnable) {
            this.a = "https://api.map.baidu.com/geosearch/v2/local";
        } else {
            this.a = "http://api.map.baidu.com/geosearch/v2/local";
        }
    }

    /* access modifiers changed from: package-private */
    public String a() {
        StringBuilder sb = new StringBuilder();
        if (super.a() == null) {
            return null;
        }
        sb.append(super.a());
        if (this.region == null || this.region.equals("") || this.region.length() > 25) {
            return null;
        }
        sb.append(a.b);
        sb.append("region");
        sb.append("=");
        try {
            sb.append(URLEncoder.encode(this.region, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}

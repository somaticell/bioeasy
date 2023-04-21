package com.baidu.mapapi.cloud;

import com.alipay.sdk.sys.a;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public abstract class BaseCloudSearchInfo extends BaseSearchInfo {
    public String filter;
    public int pageIndex;
    public int pageSize = 10;
    public String q;
    public String sortby;
    public String tags;

    /* access modifiers changed from: package-private */
    public String a() {
        StringBuilder sb = new StringBuilder();
        if (super.a() == null) {
            return null;
        }
        sb.append(super.a());
        if (this.q != null && !this.q.equals("") && this.q.length() <= 45) {
            sb.append(a.b);
            sb.append("q");
            sb.append("=");
            try {
                sb.append(URLEncoder.encode(this.q, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (this.tags != null && !this.tags.equals("") && this.tags.length() <= 45) {
            sb.append(a.b);
            sb.append("tags");
            sb.append("=");
            try {
                sb.append(URLEncoder.encode(this.tags, "UTF-8"));
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
        }
        if (this.sortby != null && !this.sortby.equals("")) {
            sb.append(a.b);
            sb.append("sortby");
            sb.append("=");
            sb.append(this.sortby);
        }
        if (this.filter != null && !this.filter.equals("")) {
            sb.append(a.b);
            sb.append("filter");
            sb.append("=");
            sb.append(this.filter);
        }
        if (this.pageIndex >= 0) {
            sb.append(a.b);
            sb.append("page_index");
            sb.append("=");
            sb.append(this.pageIndex);
        }
        if (this.pageSize >= 0 && this.pageSize <= 50) {
            sb.append(a.b);
            sb.append("page_size");
            sb.append("=");
            sb.append(this.pageSize);
        }
        return sb.toString();
    }
}

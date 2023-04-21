package com.alipay.android.phone.mrpc.core;

import java.util.List;
import org.apache.http.Header;

public class RpcParams {
    private String gwUrl;
    private boolean gzip;
    private List<Header> headers;

    public String getGwUrl() {
        return this.gwUrl;
    }

    public List<Header> getHeaders() {
        return this.headers;
    }

    public boolean isGzip() {
        return this.gzip;
    }

    public void setGwUrl(String str) {
        this.gwUrl = str;
    }

    public void setGzip(boolean z) {
        this.gzip = z;
    }

    public void setHeaders(List<Header> list) {
        this.headers = list;
    }
}

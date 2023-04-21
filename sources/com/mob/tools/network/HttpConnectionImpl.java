package com.mob.tools.network;

import cn.com.bioeasy.app.utils.ListUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;

public class HttpConnectionImpl implements HttpConnection {
    private HttpResponse response;

    public HttpConnectionImpl(HttpResponse httpResponse) {
        this.response = httpResponse;
    }

    public int getResponseCode() throws IOException {
        return this.response.getStatusLine().getStatusCode();
    }

    public InputStream getInputStream() throws IOException {
        return this.response.getEntity().getContent();
    }

    public InputStream getErrorStream() throws IOException {
        return this.response.getEntity().getContent();
    }

    public Map<String, List<String>> getHeaderFields() throws IOException {
        Map<String, List<String>> map = null;
        Header[] header = this.response.getAllHeaders();
        if (header != null) {
            map = new HashMap<>();
            for (Header h : header) {
                List<String> list = new ArrayList<>();
                map.put(h.getName(), list);
                String[] values = h.getValue().split(ListUtils.DEFAULT_JOIN_SEPARATOR);
                if (values != null) {
                    for (String v : values) {
                        list.add(v.trim());
                    }
                }
            }
        }
        return map;
    }
}

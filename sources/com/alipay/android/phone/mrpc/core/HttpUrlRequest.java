package com.alipay.android.phone.mrpc.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;

public class HttpUrlRequest extends Request {
    private String mContentType;
    private ArrayList<Header> mHeaders;
    private byte[] mReqData;
    private boolean mResetCookie;
    private Map<String, String> mTags;
    private String mUrl;

    public HttpUrlRequest(String str) {
        this.mUrl = str;
        this.mHeaders = new ArrayList<>();
        this.mTags = new HashMap();
        this.mContentType = "application/x-www-form-urlencoded";
    }

    public HttpUrlRequest(String str, byte[] bArr, ArrayList<Header> arrayList, HashMap<String, String> hashMap) {
        this.mUrl = str;
        this.mReqData = bArr;
        this.mHeaders = arrayList;
        this.mTags = hashMap;
        this.mContentType = "application/x-www-form-urlencoded";
    }

    public void addHeader(Header header) {
        this.mHeaders.add(header);
    }

    public void addTags(String str, String str2) {
        if (this.mTags == null) {
            this.mTags = new HashMap();
        }
        this.mTags.put(str, str2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        HttpUrlRequest httpUrlRequest = (HttpUrlRequest) obj;
        if (this.mReqData == null) {
            if (httpUrlRequest.mReqData != null) {
                return false;
            }
        } else if (!this.mReqData.equals(httpUrlRequest.mReqData)) {
            return false;
        }
        return this.mUrl == null ? httpUrlRequest.mUrl == null : this.mUrl.equals(httpUrlRequest.mUrl);
    }

    public String getContentType() {
        return this.mContentType;
    }

    public ArrayList<Header> getHeaders() {
        return this.mHeaders;
    }

    public String getKey() {
        return getUrl() + Integer.toHexString(getReqData().hashCode());
    }

    public byte[] getReqData() {
        return this.mReqData;
    }

    public String getTag(String str) {
        if (this.mTags == null) {
            return null;
        }
        return this.mTags.get(str);
    }

    public String getUrl() {
        return this.mUrl;
    }

    public int hashCode() {
        int i = 1;
        if (this.mTags != null && this.mTags.containsKey("id")) {
            i = this.mTags.get("id").hashCode() + 31;
        }
        return (this.mUrl == null ? 0 : this.mUrl.hashCode()) + (i * 31);
    }

    public boolean isResetCookie() {
        return this.mResetCookie;
    }

    public void setContentType(String str) {
        this.mContentType = str;
    }

    public void setHeaders(ArrayList<Header> arrayList) {
        this.mHeaders = arrayList;
    }

    public void setReqData(byte[] bArr) {
        this.mReqData = bArr;
    }

    public void setResetCookie(boolean z) {
        this.mResetCookie = z;
    }

    public void setTags(Map<String, String> map) {
        this.mTags = map;
    }

    public String setUrl(String str) {
        this.mUrl = str;
        return str;
    }

    public String toString() {
        return String.format("Url : %s,HttpHeader: %s", new Object[]{getUrl(), getHeaders()});
    }
}

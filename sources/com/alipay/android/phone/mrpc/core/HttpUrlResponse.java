package com.alipay.android.phone.mrpc.core;

public class HttpUrlResponse extends Response {
    private String mCharset;
    private int mCode;
    private long mCreateTime;
    private HttpUrlHeader mHeader;
    private String mMsg;
    private long mPeriod;

    public HttpUrlResponse(HttpUrlHeader httpUrlHeader, int i, String str, byte[] bArr) {
        this.mHeader = httpUrlHeader;
        this.mCode = i;
        this.mMsg = str;
        this.mResData = bArr;
    }

    public String getCharset() {
        return this.mCharset;
    }

    public int getCode() {
        return this.mCode;
    }

    public long getCreateTime() {
        return this.mCreateTime;
    }

    public HttpUrlHeader getHeader() {
        return this.mHeader;
    }

    public String getMsg() {
        return this.mMsg;
    }

    public long getPeriod() {
        return this.mPeriod;
    }

    public void setCharset(String str) {
        this.mCharset = str;
    }

    public void setCreateTime(long j) {
        this.mCreateTime = j;
    }

    public void setHeader(HttpUrlHeader httpUrlHeader) {
        this.mHeader = httpUrlHeader;
    }

    public void setPeriod(long j) {
        this.mPeriod = j;
    }
}

package com.alipay.android.phone.mrpc.core;

public class Response {
    protected String mContentType;
    protected byte[] mResData;

    public String getContentType() {
        return this.mContentType;
    }

    public byte[] getResData() {
        return this.mResData;
    }

    public void setContentType(String str) {
        this.mContentType = str;
    }

    public void setResData(byte[] bArr) {
        this.mResData = bArr;
    }
}

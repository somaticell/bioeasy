package com.loc;

import android.text.TextUtils;
import com.alipay.sdk.data.a;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.util.Map;

/* compiled from: Request */
public abstract class at {
    int a = a.d;
    int b = a.d;
    Proxy c = null;

    public abstract Map<String, String> a();

    public abstract Map<String, String> b();

    public abstract String c();

    /* access modifiers changed from: package-private */
    public String d() {
        byte[] f = f();
        if (f == null || f.length == 0) {
            return c();
        }
        Map<String, String> b2 = b();
        if (b2 == null) {
            return c();
        }
        String a2 = ar.a(b2);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(c()).append("?").append(a2);
        return stringBuffer.toString();
    }

    /* access modifiers changed from: package-private */
    public byte[] e() {
        byte[] f = f();
        if (f != null && f.length != 0) {
            return f;
        }
        String a2 = ar.a(b());
        try {
            if (!TextUtils.isEmpty(a2)) {
                return a2.getBytes("UTF-8");
            }
            return f;
        } catch (UnsupportedEncodingException e) {
            UnsupportedEncodingException unsupportedEncodingException = e;
            byte[] bytes = a2.getBytes();
            y.a(unsupportedEncodingException, "Request", "getConnectionDatas");
            return bytes;
        }
    }

    public final void a(int i) {
        this.a = i;
    }

    public final void b(int i) {
        this.b = i;
    }

    public byte[] f() {
        return null;
    }

    public final void a(Proxy proxy) {
        this.c = proxy;
    }
}

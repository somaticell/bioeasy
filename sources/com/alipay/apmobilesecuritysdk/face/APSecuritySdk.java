package com.alipay.apmobilesecuritysdk.face;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.a.a;
import com.alipay.apmobilesecuritysdk.f.d;
import com.alipay.apmobilesecuritysdk.f.g;
import com.alipay.apmobilesecuritysdk.f.h;
import com.alipay.apmobilesecuritysdk.f.i;
import com.alipay.sdk.cons.b;
import java.util.HashMap;
import java.util.Map;

public class APSecuritySdk {
    private static APSecuritySdk a;
    private static Object c = new Object();
    /* access modifiers changed from: private */
    public Context b;

    public interface InitResultListener {
        void onResult(TokenResult tokenResult);
    }

    public class TokenResult {
        public String apdid;
        public String apdidToken;
        public String clientKey;
        public String umidToken;

        public TokenResult() {
        }
    }

    private APSecuritySdk(Context context) {
        this.b = context;
    }

    public static APSecuritySdk getInstance(Context context) {
        if (a == null) {
            synchronized (c) {
                if (a == null) {
                    a = new APSecuritySdk(context);
                }
            }
        }
        return a;
    }

    public static String getUtdid(Context context) {
        return "";
    }

    public String getApdidToken() {
        return a.a(this.b, "");
    }

    public String getSdkName() {
        return "security-sdk-token";
    }

    public String getSdkVersion() {
        return "3.2.2-20160830";
    }

    public synchronized TokenResult getTokenResult() {
        TokenResult tokenResult;
        tokenResult = new TokenResult();
        try {
            tokenResult.apdidToken = a.a(this.b, "");
            tokenResult.clientKey = h.f(this.b);
            tokenResult.apdid = a.a(this.b);
            Context context = this.b;
            tokenResult.umidToken = com.alipay.apmobilesecuritysdk.e.a.a();
        } catch (Throwable th) {
        }
        return tokenResult;
    }

    public void initToken(int i, Map<String, String> map, final InitResultListener initResultListener) {
        com.alipay.apmobilesecuritysdk.b.a.a().a(i);
        String c2 = h.c(this.b);
        String c3 = com.alipay.apmobilesecuritysdk.b.a.a().c();
        if (com.alipay.b.a.a.a.a.b(c2) && !com.alipay.b.a.a.a.a.a(c2, c3)) {
            com.alipay.apmobilesecuritysdk.f.a.a(this.b);
            d.a(this.b);
            g.a(this.b);
            i.h();
        }
        if (!com.alipay.b.a.a.a.a.a(c2, c3)) {
            h.c(this.b, c3);
        }
        String a2 = com.alipay.b.a.a.a.a.a(map, b.g, "");
        String a3 = com.alipay.b.a.a.a.a.a(map, b.c, "");
        String a4 = com.alipay.b.a.a.a.a.a(map, "userId", "");
        if (com.alipay.b.a.a.a.a.a(a2)) {
            Context context = this.b;
            a2 = "";
        }
        final HashMap hashMap = new HashMap();
        hashMap.put(b.g, a2);
        hashMap.put(b.c, a3);
        hashMap.put("userId", a4);
        hashMap.put("appName", "");
        hashMap.put("appKeyClient", "");
        hashMap.put("appchannel", "");
        com.alipay.apmobilesecuritysdk.g.b.a().a((Runnable) new Runnable() {
            public void run() {
                new a(APSecuritySdk.this.b).a((Map<String, String>) hashMap);
                if (initResultListener != null) {
                    initResultListener.onResult(APSecuritySdk.this.getTokenResult());
                }
            }
        });
    }
}

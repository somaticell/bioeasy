package cn.sharesdk.tencent.qq;

import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import cn.sharesdk.framework.authorize.b;
import cn.sharesdk.framework.authorize.e;
import cn.sharesdk.framework.utils.d;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;

/* compiled from: QQAuthorizeWebviewClient */
public class a extends b {
    public a(e eVar) {
        super(eVar);
    }

    public boolean shouldOverrideUrlLoading(WebView view, final String url) {
        if (url.startsWith(this.b)) {
            view.setVisibility(4);
            view.stopLoading();
            this.a.finish();
            new Thread() {
                public void run() {
                    try {
                        a.this.a(url);
                    } catch (Throwable th) {
                        d.a().d(th);
                    }
                }
            }.start();
        } else {
            view.loadUrl(url);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void a(String str) {
        if (str.startsWith(this.b)) {
            str = str.substring(str.indexOf(35) + 1);
        }
        String[] split = str.split(com.alipay.sdk.sys.a.b);
        HashMap hashMap = new HashMap();
        for (String split2 : split) {
            String[] split3 = split2.split("=");
            if (split3.length < 2) {
                hashMap.put(URLDecoder.decode(split3[0]), "");
            } else {
                hashMap.put(URLDecoder.decode(split3[0]), URLDecoder.decode(split3[1] == null ? "" : split3[1]));
            }
        }
        a((HashMap<String, String>) hashMap);
    }

    private void a(HashMap<String, String> hashMap) {
        String str = hashMap.get("access_token");
        String str2 = hashMap.get("expires_in");
        String str3 = hashMap.get("error");
        String str4 = hashMap.get("error_description");
        String str5 = hashMap.get("pf");
        String str6 = hashMap.get("pfkey");
        String str7 = hashMap.get("pay_token");
        if (!TextUtils.isEmpty(str)) {
            try {
                HashMap<String, Object> c = b.a(this.a.a().getPlatform()).c(str);
                if (c == null || c.size() <= 0) {
                    if (this.c != null) {
                        this.c.onError(new Throwable());
                    }
                } else if (c.containsKey("openid")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("access_token", str);
                    bundle.putString("open_id", String.valueOf(c.get("openid")));
                    bundle.putString("expires_in", str2);
                    bundle.putString("pf", str5);
                    bundle.putString("pfkey", str6);
                    bundle.putString("pay_token", str7);
                    if (this.c != null) {
                        this.c.onComplete(bundle);
                    }
                } else if (this.c != null) {
                    this.c.onError(new Throwable());
                }
            } catch (Throwable th) {
                if (this.c != null) {
                    this.c.onError(th);
                }
            }
        } else if (!TextUtils.isEmpty(str3)) {
            String str8 = str4 + " (" + str3 + ")";
            if (this.c != null) {
                this.c.onError(new Throwable(str8));
            }
        } else {
            this.c.onError(new Throwable());
        }
    }

    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        try {
            Method method = handler.getClass().getMethod("proceed", new Class[0]);
            method.setAccessible(true);
            method.invoke(handler, new Object[0]);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}

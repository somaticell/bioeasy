package cn.sharesdk.sina.weibo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.authorize.e;
import cn.sharesdk.framework.utils.d;
import com.baidu.mapapi.SDKInitializer;
import com.mob.tools.utils.ResHelper;

/* compiled from: SinaWeiboAuthorizeWebviewClient */
public class b extends cn.sharesdk.framework.authorize.b {
    private boolean d;

    public b(e eVar) {
        super(eVar);
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (!TextUtils.isEmpty(this.b) && url.startsWith(this.b)) {
            view.stopLoading();
            this.a.finish();
            a(url);
            return true;
        } else if (!url.startsWith("sms:")) {
            return super.shouldOverrideUrlLoading(view, url);
        } else {
            String substring = url.substring(4);
            try {
                Intent b = b(substring);
                b.setPackage("com.android.mms");
                view.getContext().startActivity(b);
                return true;
            } catch (Throwable th) {
                if (this.c == null) {
                    return true;
                }
                this.c.onError(th);
                return true;
            }
        }
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (!TextUtils.isEmpty(this.b) && url.startsWith(this.b)) {
            view.stopLoading();
            this.a.finish();
            a(url);
        } else if (url.startsWith("sms:")) {
            String substring = url.substring(4);
            try {
                Intent b = b(substring);
                b.setPackage("com.android.mms");
                view.getContext().startActivity(b);
            } catch (Throwable th) {
                if (this.c != null) {
                    this.c.onError(th);
                }
            }
        } else {
            super.onPageStarted(view, url, favicon);
        }
    }

    /* access modifiers changed from: protected */
    public void a(String str) {
        if (!this.d) {
            this.d = true;
            Bundle urlToBundle = ResHelper.urlToBundle(str);
            String string = urlToBundle.getString("error");
            String string2 = urlToBundle.getString(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE);
            if (this.c == null) {
                return;
            }
            if (string == null && string2 == null) {
                String string3 = urlToBundle.getString("code");
                if (TextUtils.isEmpty(string3)) {
                    this.c.onError(new Throwable("Authorize code is empty"));
                }
                a(this.a.a().getPlatform(), string3);
            } else if (string.equals("access_denied")) {
                this.c.onCancel();
            } else {
                int i = 0;
                try {
                    i = ResHelper.parseInt(string2);
                } catch (Throwable th) {
                    d.a().d(th);
                }
                this.c.onError(new Throwable(string + " (" + i + ")"));
            }
        }
    }

    private void a(final Platform platform, final String str) {
        new Thread() {
            /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r4 = this;
                    cn.sharesdk.framework.Platform r0 = r2     // Catch:{ Throwable -> 0x0087 }
                    cn.sharesdk.sina.weibo.d r0 = cn.sharesdk.sina.weibo.d.a((cn.sharesdk.framework.Platform) r0)     // Catch:{ Throwable -> 0x0087 }
                    cn.sharesdk.framework.Platform r1 = r2     // Catch:{ Throwable -> 0x0025 }
                    android.content.Context r1 = r1.getContext()     // Catch:{ Throwable -> 0x0025 }
                    java.lang.String r2 = r3     // Catch:{ Throwable -> 0x0025 }
                    java.lang.String r0 = r0.a((android.content.Context) r1, (java.lang.String) r2)     // Catch:{ Throwable -> 0x0025 }
                L_0x0012:
                    if (r0 != 0) goto L_0x0031
                    cn.sharesdk.sina.weibo.b r0 = cn.sharesdk.sina.weibo.b.this     // Catch:{ Throwable -> 0x0087 }
                    cn.sharesdk.framework.authorize.AuthorizeListener r0 = r0.c     // Catch:{ Throwable -> 0x0087 }
                    java.lang.Throwable r1 = new java.lang.Throwable     // Catch:{ Throwable -> 0x0087 }
                    java.lang.String r2 = "Authorize token is empty"
                    r1.<init>(r2)     // Catch:{ Throwable -> 0x0087 }
                    r0.onError(r1)     // Catch:{ Throwable -> 0x0087 }
                L_0x0024:
                    return
                L_0x0025:
                    r0 = move-exception
                    cn.sharesdk.sina.weibo.b r1 = cn.sharesdk.sina.weibo.b.this     // Catch:{ Throwable -> 0x0087 }
                    cn.sharesdk.framework.authorize.AuthorizeListener r1 = r1.c     // Catch:{ Throwable -> 0x0087 }
                    r1.onError(r0)     // Catch:{ Throwable -> 0x0087 }
                    r0 = 0
                    goto L_0x0012
                L_0x0031:
                    com.mob.tools.utils.Hashon r1 = new com.mob.tools.utils.Hashon     // Catch:{ Throwable -> 0x0087 }
                    r1.<init>()     // Catch:{ Throwable -> 0x0087 }
                    java.util.HashMap r0 = r1.fromJson((java.lang.String) r0)     // Catch:{ Throwable -> 0x0087 }
                    android.os.Bundle r1 = new android.os.Bundle     // Catch:{ Throwable -> 0x0087 }
                    r1.<init>()     // Catch:{ Throwable -> 0x0087 }
                    java.lang.String r2 = "uid"
                    java.lang.String r3 = "uid"
                    java.lang.Object r3 = r0.get(r3)     // Catch:{ Throwable -> 0x0087 }
                    java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x0087 }
                    r1.putString(r2, r3)     // Catch:{ Throwable -> 0x0087 }
                    java.lang.String r2 = "remind_in"
                    java.lang.String r3 = "remind_in"
                    java.lang.Object r3 = r0.get(r3)     // Catch:{ Throwable -> 0x0087 }
                    java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x0087 }
                    r1.putString(r2, r3)     // Catch:{ Throwable -> 0x0087 }
                    java.lang.String r2 = "expires_in"
                    java.lang.String r3 = "expires_in"
                    java.lang.Object r3 = r0.get(r3)     // Catch:{ Throwable -> 0x0087 }
                    java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x0087 }
                    r1.putString(r2, r3)     // Catch:{ Throwable -> 0x0087 }
                    java.lang.String r2 = "access_token"
                    java.lang.String r3 = "access_token"
                    java.lang.Object r0 = r0.get(r3)     // Catch:{ Throwable -> 0x0087 }
                    java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ Throwable -> 0x0087 }
                    r1.putString(r2, r0)     // Catch:{ Throwable -> 0x0087 }
                    cn.sharesdk.sina.weibo.b r0 = cn.sharesdk.sina.weibo.b.this     // Catch:{ Throwable -> 0x0087 }
                    cn.sharesdk.framework.authorize.AuthorizeListener r0 = r0.c     // Catch:{ Throwable -> 0x0087 }
                    r0.onComplete(r1)     // Catch:{ Throwable -> 0x0087 }
                    goto L_0x0024
                L_0x0087:
                    r0 = move-exception
                    com.mob.tools.log.NLog r1 = cn.sharesdk.framework.utils.d.a()
                    r1.d(r0)
                    goto L_0x0024
                */
                throw new UnsupportedOperationException("Method not decompiled: cn.sharesdk.sina.weibo.b.AnonymousClass1.run():void");
            }
        }.start();
    }

    private Intent b(String str) {
        Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + str));
        intent.putExtra("sms_body", "");
        intent.setFlags(268435456);
        return intent;
    }
}

package cn.sharesdk.framework.authorize;

import android.webkit.WebView;
import cn.sharesdk.framework.d;

/* compiled from: AuthorizeWebviewClient */
public abstract class b extends d {
    protected e a;
    protected String b;
    protected AuthorizeListener c;

    public b(e eVar) {
        this.a = eVar;
        AuthorizeHelper a2 = eVar.a();
        this.b = a2.getRedirectUri();
        this.c = a2.getAuthorizeListener();
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        view.stopLoading();
        AuthorizeListener authorizeListener = this.a.a().getAuthorizeListener();
        this.a.finish();
        if (authorizeListener != null) {
            authorizeListener.onError(new Throwable(description + " (" + errorCode + "): " + failingUrl));
        }
    }
}

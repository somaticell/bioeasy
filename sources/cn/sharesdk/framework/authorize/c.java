package cn.sharesdk.framework.authorize;

import android.content.Intent;

/* compiled from: SSOAuthorizeActivity */
public class c extends a {
    protected SSOListener b;
    private d c;

    public void a(SSOListener sSOListener) {
        this.b = sSOListener;
    }

    public void onCreate() {
        this.c = this.a.getSSOProcessor(this);
        if (this.c == null) {
            finish();
            AuthorizeListener authorizeListener = this.a.getAuthorizeListener();
            if (authorizeListener != null) {
                authorizeListener.onError(new Throwable("Failed to start SSO for " + this.a.getPlatform().getName()));
                return;
            }
            return;
        }
        this.c.a(32973);
        this.c.a();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.c.a(requestCode, resultCode, data);
    }

    public void onNewIntent(Intent intent) {
        this.c.a(intent);
    }
}

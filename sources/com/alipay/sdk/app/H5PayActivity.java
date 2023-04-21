package com.alipay.sdk.app;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alipay.sdk.app.statistic.a;
import com.alipay.sdk.app.statistic.c;
import com.alipay.sdk.util.l;

public class H5PayActivity extends Activity {
    private WebView a;
    private WebViewClient b;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        try {
            super.requestWindowFeature(1);
        } catch (Throwable th) {
        }
        super.onCreate(bundle);
        try {
            Bundle extras = getIntent().getExtras();
            String string = extras.getString("url");
            if (!l.b(string)) {
                finish();
                return;
            }
            try {
                this.a = l.a((Activity) this, string, extras.getString("cookie"));
                this.b = new b(this);
                this.a.setWebViewClient(this.b);
            } catch (Throwable th2) {
                a.a(c.b, "GetInstalledAppEx", th2);
                finish();
            }
        } catch (Exception e) {
            finish();
        }
    }

    private void b() {
        try {
            super.requestWindowFeature(1);
        } catch (Throwable th) {
        }
    }

    public void onBackPressed() {
        if (!this.a.canGoBack()) {
            h.a = h.a();
            finish();
        } else if (((b) this.b).c) {
            i a2 = i.a(i.NETWORK_ERROR.h);
            h.a = h.a(a2.h, a2.i, "");
            finish();
        }
    }

    public void finish() {
        a();
        super.finish();
    }

    public void a() {
        Object obj = PayTask.a;
        synchronized (obj) {
            try {
                obj.notify();
            } catch (Exception e) {
            }
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.a != null) {
            this.a.removeAllViews();
            ((ViewGroup) this.a.getParent()).removeAllViews();
            try {
                this.a.destroy();
            } catch (Throwable th) {
            }
            this.a = null;
        }
        if (this.b != null) {
            b bVar = (b) this.b;
            bVar.b = null;
            bVar.a = null;
        }
    }
}

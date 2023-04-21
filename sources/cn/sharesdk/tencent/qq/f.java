package cn.sharesdk.tencent.qq;

import android.app.Activity;
import android.app.Instrumentation;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.authorize.RegisterView;
import cn.sharesdk.framework.utils.d;
import com.alipay.sdk.util.j;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ResHelper;

/* compiled from: WebShareActivity */
public class f extends FakeActivity {
    private String a;
    /* access modifiers changed from: private */
    public PlatformActionListener b;
    /* access modifiers changed from: private */
    public String c;
    private QQWebShareAdapter d;
    private RegisterView e;
    private WebView f;
    private boolean g;
    /* access modifiers changed from: private */
    public boolean h;

    public void a(String str) {
        this.a = str;
    }

    public void a(PlatformActionListener platformActionListener) {
        this.b = platformActionListener;
    }

    public void b(String str) {
        this.c = "tencent" + str;
    }

    public void setActivity(Activity activity) {
        super.setActivity(activity);
        if (this.d == null) {
            this.d = b();
            if (this.d == null) {
                this.d = new QQWebShareAdapter();
            }
        }
        this.d.setActivity(activity);
    }

    private QQWebShareAdapter b() {
        try {
            String string = this.activity.getPackageManager().getActivityInfo(this.activity.getComponentName(), 128).metaData.getString("QQWebShareAdapter");
            if (string == null || string.length() <= 0) {
                return null;
            }
            Object newInstance = Class.forName(string).newInstance();
            if (!(newInstance instanceof QQWebShareAdapter)) {
                return null;
            }
            return (QQWebShareAdapter) newInstance;
        } catch (Throwable th) {
            d.a().d(th);
            return null;
        }
    }

    public void onCreate() {
        this.e = a();
        try {
            int stringRes = ResHelper.getStringRes(getContext(), "ssdk_share_to_qq");
            if (stringRes > 0) {
                this.e.c().getTvTitle().setText(stringRes);
            }
        } catch (Throwable th) {
            d.a().d(th);
            this.e.c().setVisibility(8);
        }
        this.d.setBodyView(this.e.d());
        this.d.setWebView(this.e.b());
        this.d.setTitleView(this.e.c());
        this.d.onCreate();
        disableScreenCapture();
        this.activity.setContentView(this.e);
        if ("none".equals(DeviceHelper.getInstance(this.activity).getDetailNetworkTypeForStatic())) {
            this.g = true;
            finish();
            this.b.onError((Platform) null, 0, new Throwable("failed to load webpage, network disconnected."));
            return;
        }
        this.e.b().loadUrl(this.a);
    }

    /* access modifiers changed from: protected */
    public RegisterView a() {
        RegisterView registerView = new RegisterView(this.activity);
        registerView.c().getChildAt(registerView.c().getChildCount() - 1).setVisibility(8);
        registerView.a().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        try {
                            new Instrumentation().sendKeyDownUpSync(4);
                        } catch (Throwable th) {
                            d.a().d(th);
                            f.this.finish();
                            f.this.b.onCancel((Platform) null, 0);
                        }
                    }
                }.start();
            }
        });
        this.f = registerView.b();
        WebSettings settings = this.f.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(1);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setSavePassword(false);
        settings.setDatabasePath(this.activity.getDir("database", 0).getPath());
        this.f.setVerticalScrollBarEnabled(false);
        this.f.setHorizontalScrollBarEnabled(false);
        this.f.setWebViewClient(new cn.sharesdk.framework.d() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && url.startsWith(f.this.c)) {
                    f.this.c(url);
                } else if (url != null && url.startsWith("http://www.myapp.com/down/")) {
                    boolean unused = f.this.h = true;
                } else if (url != null && url.startsWith("wtloginmqq://")) {
                    int stringRes = ResHelper.getStringRes(f.this.activity, "ssdk_use_login_button");
                    if (stringRes <= 0) {
                        return true;
                    }
                    Toast.makeText(f.this.activity, stringRes, 0).show();
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (url == null || !url.startsWith("wtloginmqq://")) {
                    super.onPageStarted(view, url, favicon);
                    return;
                }
                int stringRes = ResHelper.getStringRes(f.this.activity, "ssdk_use_login_button");
                if (stringRes > 0) {
                    Toast.makeText(f.this.activity, stringRes, 0).show();
                }
            }
        });
        return registerView;
    }

    /* access modifiers changed from: private */
    public void c(String str) {
        String str2 = str == null ? "" : new String(str);
        Bundle urlToBundle = ResHelper.urlToBundle(str);
        if (urlToBundle == null) {
            this.g = true;
            finish();
            this.b.onError((Platform) null, 0, new Throwable("failed to parse callback uri: " + str2));
            return;
        }
        String string = urlToBundle.getString(com.alipay.sdk.packet.d.o);
        if ("share".equals(string) || "shareToQQ".equals(string)) {
            String string2 = urlToBundle.getString(j.c);
            if ("cancel".equals(string2)) {
                finish();
                this.b.onCancel((Platform) null, 0);
            } else if (!"complete".equals(string2)) {
                this.g = true;
                finish();
                this.b.onError((Platform) null, 0, new Throwable("operation failed: " + str2));
            } else {
                String string3 = urlToBundle.getString("response");
                if (TextUtils.isEmpty(string3)) {
                    this.g = true;
                    finish();
                    this.b.onError((Platform) null, 0, new Throwable("response empty" + str2));
                    return;
                }
                this.h = true;
                finish();
                this.b.onComplete((Platform) null, 0, new Hashon().fromJson(string3));
            }
        } else {
            this.g = true;
            finish();
            this.b.onError((Platform) null, 0, new Throwable("action error: " + str2));
        }
    }

    public void onStart() {
        if (this.d != null) {
            this.d.onStart();
        }
    }

    public void onPause() {
        if (this.d != null) {
            this.d.onPause();
        }
    }

    public void onResume() {
        if (this.d != null) {
            this.d.onResume();
        }
    }

    public void onStop() {
        if (this.d != null) {
            this.d.onStop();
        }
    }

    public void onRestart() {
        if (this.d != null) {
            this.d.onRestart();
        }
    }

    public void onDestroy() {
        if (!this.g && !this.h) {
            this.b.onCancel((Platform) null, 0);
        }
        if (this.d != null) {
            this.d.onDestroy();
        }
    }

    public boolean onFinish() {
        if (this.d != null) {
            return this.d.onFinish();
        }
        return super.onFinish();
    }
}

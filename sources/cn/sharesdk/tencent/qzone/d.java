package cn.sharesdk.tencent.qzone;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.authorize.RegisterView;
import com.alipay.sdk.util.j;
import com.mob.tools.FakeActivity;
import com.mob.tools.MobUIShell;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ResHelper;
import java.util.List;

/* compiled from: ShareActivity */
public class d extends FakeActivity {
    private String a;
    private boolean b;
    /* access modifiers changed from: private */
    public PlatformActionListener c;
    private RegisterView d;
    private WebView e;
    /* access modifiers changed from: private */
    public String f;
    private boolean g;
    private boolean h;
    private QZoneWebShareAdapter i;

    public void a(String str, boolean z) {
        this.a = str;
        this.b = z;
    }

    public void a(PlatformActionListener platformActionListener) {
        this.c = platformActionListener;
    }

    public void a(String str) {
        this.f = "tencent" + str;
    }

    public void setActivity(Activity activity) {
        super.setActivity(activity);
        if (this.i == null) {
            this.i = b();
            if (this.i == null) {
                this.i = new QZoneWebShareAdapter();
            }
        }
        this.i.setActivity(activity);
    }

    private QZoneWebShareAdapter b() {
        try {
            String string = this.activity.getPackageManager().getActivityInfo(this.activity.getComponentName(), 128).metaData.getString("QZoneWebShareAdapter");
            if (string == null || string.length() <= 0) {
                return null;
            }
            Object newInstance = Class.forName(string).newInstance();
            if (!(newInstance instanceof QZoneWebShareAdapter)) {
                return null;
            }
            return (QZoneWebShareAdapter) newInstance;
        } catch (Throwable th) {
            cn.sharesdk.framework.utils.d.a().d(th);
            return null;
        }
    }

    public void onCreate() {
        Intent intent = this.activity.getIntent();
        String scheme = intent.getScheme();
        if (scheme != null && scheme.startsWith(this.f)) {
            finish();
            Bundle urlToBundle = ResHelper.urlToBundle(intent.getDataString());
            String valueOf = String.valueOf(urlToBundle.get(j.c));
            String valueOf2 = String.valueOf(urlToBundle.get(com.alipay.sdk.packet.d.o));
            if ("shareToQQ".equals(valueOf2) || "shareToQzone".equals(valueOf2)) {
                if ("complete".equals(valueOf)) {
                    if (this.c != null) {
                        this.c.onComplete((Platform) null, 9, new Hashon().fromJson(String.valueOf(urlToBundle.get("response"))));
                    }
                } else if ("error".equals(valueOf)) {
                    if (this.c != null) {
                        this.c.onError((Platform) null, 9, new Throwable(String.valueOf(urlToBundle.get("response"))));
                    }
                } else if (this.c != null) {
                    this.c.onCancel((Platform) null, 9);
                }
            }
            Intent intent2 = new Intent("android.intent.action.VIEW");
            intent2.setClass(this.activity, MobUIShell.class);
            intent2.setFlags(335544320);
            startActivity(intent2);
        } else if (this.b) {
            c();
        } else {
            d();
        }
    }

    private void c() {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(this.a));
            intent.putExtra("pkg_name", this.activity.getPackageName());
            if (Build.VERSION.SDK_INT >= 11) {
                intent.setFlags(268468224);
            }
            this.activity.startActivity(intent);
            FakeActivity.registerExecutor(this.f, this);
            finish();
        } catch (Throwable th) {
            if (this.c != null) {
                this.c.onError((Platform) null, 0, th);
            }
        }
    }

    private void d() {
        this.d = a();
        try {
            int stringRes = ResHelper.getStringRes(getContext(), "ssdk_share_to_qzone");
            if (stringRes > 0) {
                this.d.c().getTvTitle().setText(stringRes);
            }
        } catch (Throwable th) {
            cn.sharesdk.framework.utils.d.a().d(th);
            this.d.c().setVisibility(8);
        }
        this.i.setBodyView(this.d.d());
        this.i.setWebView(this.d.b());
        this.i.setTitleView(this.d.c());
        this.i.onCreate();
        this.activity.setContentView(this.d);
        if ("none".equals(DeviceHelper.getInstance(this.activity).getDetailNetworkTypeForStatic())) {
            this.h = true;
            finish();
            this.c.onError((Platform) null, 0, new Throwable("failed to load webpage, network disconnected."));
            return;
        }
        this.d.b().loadUrl(this.a);
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
                            cn.sharesdk.framework.utils.d.a().d(th);
                            d.this.finish();
                            d.this.c.onCancel((Platform) null, 0);
                        }
                    }
                }.start();
            }
        });
        this.e = registerView.b();
        WebSettings settings = this.e.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(1);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDatabasePath(this.activity.getDir("database", 0).getPath());
        settings.setSavePassword(false);
        this.e.setVerticalScrollBarEnabled(false);
        this.e.setHorizontalScrollBarEnabled(false);
        this.e.setWebViewClient(new cn.sharesdk.framework.d() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && url.startsWith(d.this.f)) {
                    d.this.b(url);
                } else if (url != null && url.startsWith("mqzone://")) {
                    d.this.c(url);
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        return registerView;
    }

    /* access modifiers changed from: private */
    public void b(String str) {
        String str2 = str == null ? "" : new String(str);
        Bundle urlToBundle = ResHelper.urlToBundle(str);
        if (urlToBundle == null) {
            this.h = true;
            finish();
            this.c.onError((Platform) null, 0, new Throwable("failed to parse callback uri: " + str2));
            return;
        }
        String string = urlToBundle.getString(com.alipay.sdk.packet.d.o);
        if ("share".equals(string) || "shareToQzone".equals(string)) {
            String string2 = urlToBundle.getString(j.c);
            if ("cancel".equals(string2)) {
                finish();
                this.c.onCancel((Platform) null, 0);
            } else if (!"complete".equals(string2)) {
                this.h = true;
                finish();
                this.c.onError((Platform) null, 0, new Throwable("operation failed: " + str2));
            } else {
                String string3 = urlToBundle.getString("response");
                if (TextUtils.isEmpty(string3)) {
                    this.h = true;
                    finish();
                    this.c.onError((Platform) null, 0, new Throwable("response empty" + str2));
                    return;
                }
                this.g = true;
                finish();
                this.c.onComplete((Platform) null, 0, new Hashon().fromJson(string3));
            }
        } else {
            this.h = true;
            finish();
            this.c.onError((Platform) null, 0, new Throwable("action error: " + str2));
        }
    }

    /* access modifiers changed from: private */
    public void c(String str) {
        List<ResolveInfo> list;
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        try {
            list = this.activity.getPackageManager().queryIntentActivities(intent, 1);
        } catch (Throwable th) {
            cn.sharesdk.framework.utils.d.a().d(th);
            list = null;
        }
        if (list != null && list.size() > 0) {
            startActivity(intent);
        }
    }

    public void onStart() {
        if (this.i != null) {
            this.i.onStart();
        }
    }

    public void onPause() {
        if (this.i != null) {
            this.i.onPause();
        }
    }

    public void onResume() {
        if (this.i != null) {
            this.i.onResume();
        }
    }

    public void onStop() {
        if (this.i != null) {
            this.i.onStop();
        }
    }

    public void onRestart() {
        if (this.i != null) {
            this.i.onRestart();
        }
    }

    public void onDestroy() {
        if (!this.b && !this.h && !this.g) {
            this.c.onCancel((Platform) null, 0);
        }
        if (this.i != null) {
            this.i.onDestroy();
        }
    }

    public boolean onFinish() {
        if (this.i != null) {
            return this.i.onFinish();
        }
        return super.onFinish();
    }
}

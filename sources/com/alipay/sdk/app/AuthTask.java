package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.sdk.data.c;
import com.alipay.sdk.sys.b;
import com.alipay.sdk.util.e;
import com.alipay.sdk.util.j;
import com.alipay.sdk.util.l;
import com.alipay.sdk.widget.a;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AuthTask {
    static final Object a = e.class;
    private static final int b = 73;
    private Activity c;
    private a d;

    public AuthTask(Activity activity) {
        this.c = activity;
        b a2 = b.a();
        Activity activity2 = this.c;
        c.a();
        a2.a((Context) activity2);
        com.alipay.sdk.app.statistic.a.a(activity);
        this.d = new a(activity, a.c);
    }

    private e.a a() {
        return new a(this);
    }

    private void b() {
        if (this.d != null) {
            this.d.a();
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        if (this.d != null) {
            this.d.b();
        }
    }

    public synchronized Map<String, String> authV2(String str, boolean z) {
        return j.a(auth(str, z));
    }

    public synchronized String auth(String str, boolean z) {
        String str2;
        if (z) {
            b();
        }
        b a2 = b.a();
        Activity activity = this.c;
        c.a();
        a2.a((Context) activity);
        String a3 = h.a();
        try {
            Activity activity2 = this.c;
            String a4 = new com.alipay.sdk.sys.a(this.c).a(str);
            if (a((Context) activity2)) {
                str2 = new e(activity2, new a(this)).a(a4);
                if (!TextUtils.equals(str2, e.b)) {
                    if (TextUtils.isEmpty(str2)) {
                        str2 = h.a();
                    }
                    com.alipay.sdk.data.a.b().a((Context) this.c);
                    c();
                    com.alipay.sdk.app.statistic.a.a((Context) this.c, str);
                }
            }
            str2 = b(activity2, a4);
            com.alipay.sdk.data.a.b().a((Context) this.c);
            c();
            com.alipay.sdk.app.statistic.a.a((Context) this.c, str);
        } catch (Exception e) {
            com.alipay.sdk.data.a.b().a((Context) this.c);
            c();
            com.alipay.sdk.app.statistic.a.a((Context) this.c, str);
            str2 = a3;
        } catch (Throwable th) {
            com.alipay.sdk.data.a.b().a((Context) this.c);
            c();
            com.alipay.sdk.app.statistic.a.a((Context) this.c, str);
            throw th;
        }
        return str2;
    }

    private String a(Activity activity, String str) {
        String a2 = new com.alipay.sdk.sys.a(this.c).a(str);
        if (!a((Context) activity)) {
            return b(activity, a2);
        }
        String a3 = new e(activity, new a(this)).a(a2);
        if (TextUtils.equals(a3, e.b)) {
            return b(activity, a2);
        }
        if (TextUtils.isEmpty(a3)) {
            return h.a();
        }
        return a3;
    }

    private String b(Activity activity, String str) {
        i iVar;
        b();
        try {
            List<com.alipay.sdk.protocol.b> a2 = com.alipay.sdk.protocol.b.a(new com.alipay.sdk.packet.impl.a().a((Context) activity, str).a().optJSONObject(com.alipay.sdk.cons.c.c).optJSONObject(com.alipay.sdk.cons.c.d));
            c();
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= a2.size()) {
                    c();
                    iVar = null;
                    break;
                } else if (a2.get(i2).a == com.alipay.sdk.protocol.a.WapPay) {
                    String a3 = a(a2.get(i2));
                    c();
                    return a3;
                } else {
                    i = i2 + 1;
                }
            }
        } catch (IOException e) {
            i a4 = i.a(i.NETWORK_ERROR.h);
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.a, (Throwable) e);
            c();
            iVar = a4;
        } catch (Throwable th) {
            c();
            throw th;
        }
        if (iVar == null) {
            iVar = i.a(i.FAILED.h);
        }
        return h.a(iVar.h, iVar.i, "");
    }

    private String a(com.alipay.sdk.protocol.b bVar) {
        String[] strArr = bVar.b;
        Bundle bundle = new Bundle();
        bundle.putString("url", strArr[0]);
        Intent intent = new Intent(this.c, H5AuthActivity.class);
        intent.putExtras(bundle);
        this.c.startActivity(intent);
        synchronized (a) {
            try {
                a.wait();
            } catch (InterruptedException e) {
                return h.a();
            }
        }
        String str = h.a;
        if (TextUtils.isEmpty(str)) {
            return h.a();
        }
        return str;
    }

    private static boolean a(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(l.a(), 128);
            if (packageInfo != null && packageInfo.versionCode >= 73) {
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}

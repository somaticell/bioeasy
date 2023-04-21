package cn.sharesdk.tencent.qq;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.utils.d;
import com.alipay.sdk.cons.a;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ResHelper;
import java.io.File;
import org.achartengine.ChartFactory;

/* compiled from: ShareActivity */
public class e extends FakeActivity {
    private Platform a;
    private String b;
    private PlatformActionListener c;

    public void a(Platform platform, PlatformActionListener platformActionListener) {
        this.a = platform;
        this.c = platformActionListener;
    }

    public void a(String str) {
        this.b = "tencent" + str;
    }

    public void onCreate() {
        Bundle extras = this.activity.getIntent().getExtras();
        final String string = extras.getString(ChartFactory.TITLE);
        final String string2 = extras.getString("titleUrl");
        final String string3 = extras.getString("summary");
        final String string4 = extras.getString("imageUrl");
        final String string5 = extras.getString("musicUrl");
        final String appName = DeviceHelper.getInstance(this.activity).getAppName();
        final String string6 = extras.getString("appId");
        final int i = extras.getInt("hidden");
        String string7 = extras.getString("imagePath");
        if (!TextUtils.isEmpty(string) || !TextUtils.isEmpty(string3) || !TextUtils.isEmpty(string2) || ((!TextUtils.isEmpty(string7) && new File(string7).exists()) || TextUtils.isEmpty(string4))) {
            a(string, string2, string3, string4, string7, string5, appName, string6, i);
        } else {
            new Thread() {
                /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                        r10 = this;
                        cn.sharesdk.tencent.qq.e r0 = cn.sharesdk.tencent.qq.e.this     // Catch:{ Throwable -> 0x0022 }
                        android.app.Activity r0 = r0.activity     // Catch:{ Throwable -> 0x0022 }
                        java.lang.String r1 = r3     // Catch:{ Throwable -> 0x0022 }
                        java.lang.String r5 = com.mob.tools.utils.BitmapHelper.downloadBitmap(r0, r1)     // Catch:{ Throwable -> 0x0022 }
                    L_0x000c:
                        cn.sharesdk.tencent.qq.e r0 = cn.sharesdk.tencent.qq.e.this     // Catch:{ Throwable -> 0x002c }
                        java.lang.String r1 = r4     // Catch:{ Throwable -> 0x002c }
                        java.lang.String r2 = r5     // Catch:{ Throwable -> 0x002c }
                        java.lang.String r3 = r6     // Catch:{ Throwable -> 0x002c }
                        java.lang.String r4 = r3     // Catch:{ Throwable -> 0x002c }
                        java.lang.String r6 = r7     // Catch:{ Throwable -> 0x002c }
                        java.lang.String r7 = r8     // Catch:{ Throwable -> 0x002c }
                        java.lang.String r8 = r9     // Catch:{ Throwable -> 0x002c }
                        int r9 = r10     // Catch:{ Throwable -> 0x002c }
                        r0.a(r1, r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ Throwable -> 0x002c }
                    L_0x0021:
                        return
                    L_0x0022:
                        r0 = move-exception
                        com.mob.tools.log.NLog r1 = cn.sharesdk.framework.utils.d.a()     // Catch:{ Throwable -> 0x002c }
                        r1.d(r0)     // Catch:{ Throwable -> 0x002c }
                        r5 = 0
                        goto L_0x000c
                    L_0x002c:
                        r0 = move-exception
                        com.mob.tools.log.NLog r1 = cn.sharesdk.framework.utils.d.a()
                        r1.d(r0)
                        goto L_0x0021
                    */
                    throw new UnsupportedOperationException("Method not decompiled: cn.sharesdk.tencent.qq.e.AnonymousClass1.run():void");
                }
            }.start();
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i) {
        String b2 = b(str, str2, str3, str4, str5, str6, str7, str8, i);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(b2));
        try {
            int[] a2 = a();
            d dVar = new d();
            dVar.a(this.b);
            dVar.a(this.a, this.c);
            FakeActivity.registerExecutor(this.b, dVar);
            if (a2.length <= 1 || (a2[0] < 4 && a2[1] < 6)) {
                intent.putExtra("key_request_code", 0);
            }
            intent.putExtra("pkg_name", this.activity.getPackageName());
            this.activity.startActivityForResult(intent, 0);
        } catch (Throwable th) {
            this.activity.finish();
            if (this.c != null) {
                this.c.onError(this.a, 9, th);
            }
        }
    }

    private String b(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i) {
        String str9;
        String str10 = "mqqapi://share/to_fri?src_type=app&version=1&file_type=news";
        if (!TextUtils.isEmpty(str4)) {
            if (!TextUtils.isEmpty(str5)) {
                str4 = "";
            } else if (str4.startsWith("https")) {
                try {
                    str5 = BitmapHelper.downloadBitmap(this.activity, str4);
                    str4 = "";
                } catch (Throwable th) {
                    d.a().d(th);
                    str5 = null;
                }
            }
        }
        if (!TextUtils.isEmpty(str4)) {
            str10 = str10 + "&image_url=" + Base64.encodeToString(str4.getBytes(), 2);
        }
        if (!TextUtils.isEmpty(str5)) {
            str10 = str10 + "&file_data=" + Base64.encodeToString(str5.getBytes(), 2);
        }
        if (!TextUtils.isEmpty(str)) {
            str10 = str10 + "&title=" + Base64.encodeToString(str.getBytes(), 2);
        }
        if (!TextUtils.isEmpty(str3)) {
            str10 = str10 + "&description=" + Base64.encodeToString(str3.getBytes(), 2);
        }
        if (!TextUtils.isEmpty(str7)) {
            if (str7.length() > 20) {
                str7 = str7.substring(0, 20) + "...";
            }
            str10 = str10 + "&app_name=" + Base64.encodeToString(str7.getBytes(), 2);
        }
        if (!TextUtils.isEmpty(str8)) {
            str10 = str10 + "&share_id=" + str8;
        }
        if (!TextUtils.isEmpty(str2)) {
            str10 = str10 + "&url=" + Base64.encodeToString(str2.getBytes(), 2);
        }
        if (!TextUtils.isEmpty(str3)) {
            str10 = str10 + "&share_qq_ext_str=" + Base64.encodeToString(str3.getBytes(), 2);
        }
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str3) && TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str5)) {
            str9 = str10 + "&req_type=" + Base64.encodeToString("5".getBytes(), 2);
        } else if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str3) && TextUtils.isEmpty(str2)) {
            str9 = str10 + "&req_type=" + Base64.encodeToString("6".getBytes(), 2);
        } else if (!TextUtils.isEmpty(str6)) {
            str9 = (str10 + "&req_type=" + Base64.encodeToString("2".getBytes(), 2)) + "&audioUrl=" + Base64.encodeToString(str6.getBytes(), 2);
        } else {
            str9 = str10 + "&req_type=" + Base64.encodeToString(a.e.getBytes(), 2);
        }
        return str9 + "&cflag=" + Base64.encodeToString((i == 1 ? "10" : BLEServiceApi.RESULT_NG).getBytes(), 2);
    }

    private int[] a() {
        String str;
        try {
            str = this.a.getContext().getPackageManager().getPackageInfo("com.tencent.mobileqq", 0).versionName;
        } catch (Throwable th) {
            d.a().d(th);
            str = "0";
        }
        String[] split = str.split("\\.");
        int[] iArr = new int[split.length];
        for (int i = 0; i < iArr.length; i++) {
            try {
                iArr[i] = ResHelper.parseInt(split[i]);
            } catch (Throwable th2) {
                d.a().d(th2);
                iArr[i] = 0;
            }
        }
        return iArr;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}

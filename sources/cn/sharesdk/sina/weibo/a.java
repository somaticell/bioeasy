package cn.sharesdk.sina.weibo;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.authorize.AuthorizeListener;
import cn.sharesdk.framework.utils.d;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.UIHandler;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.UUID;

/* compiled from: SinaActivity */
public class a extends FakeActivity implements Handler.Callback {
    private long a = 2097152;
    private boolean b;
    private String c;
    private Platform.ShareParams d;
    private AuthorizeListener e;

    public void onCreate() {
        if (c()) {
            UIHandler.sendEmptyMessageDelayed(1, 700, new Handler.Callback() {
                public boolean handleMessage(Message msg) {
                    a.this.a();
                    return true;
                }
            });
        }
    }

    public void a(String str) {
        this.c = str;
    }

    public void a(Platform.ShareParams shareParams) {
        this.d = shareParams;
    }

    public void a(AuthorizeListener authorizeListener) {
        this.e = authorizeListener;
    }

    /* access modifiers changed from: private */
    public void a() {
        Bundle bundle = new Bundle();
        bundle.putInt("_weibo_command_type", 1);
        bundle.putString("_weibo_transaction", String.valueOf(System.currentTimeMillis()));
        if (!TextUtils.isEmpty(this.d.getText())) {
            bundle.putParcelable("_weibo_message_text", e());
            bundle.putString("_weibo_message_text_extra", "");
        }
        if (!TextUtils.isEmpty(this.d.getUrl())) {
            this.a = PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID;
            WebpageObject f = f();
            if (f.checkArgs()) {
                bundle.putParcelable("_weibo_message_media", f);
                String str = "";
                if (!TextUtils.isEmpty(f.defaultText)) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("extra_key_defaulttext", f.defaultText);
                    str = new Hashon().fromHashMap(hashMap);
                }
                bundle.putString("_weibo_message_media_extra", str);
            }
        } else if (this.d.getImageData() != null || !TextUtils.isEmpty(this.d.getImagePath())) {
            this.a = 2097152;
            ImageObject g = g();
            if (g.checkArgs()) {
                bundle.putParcelable("_weibo_message_image", g);
                bundle.putString("_weibo_message_image_extra", "");
            }
        }
        try {
            a(this.activity, e.a((Context) this.activity).a(), this.c, bundle);
        } catch (Throwable th) {
            if (this.e != null) {
                this.e.onError(new Throwable(th));
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        d.a().d("sina activity requestCode = %s, resultCode = %s", Integer.valueOf(requestCode), Integer.valueOf(requestCode));
        b();
    }

    public void onNewIntent(Intent intent) {
        this.b = true;
        Bundle extras = intent.getExtras();
        d.a().i("onNewIntent ==>>", extras.toString());
        String stringExtra = intent.getStringExtra("_weibo_appPackage");
        String stringExtra2 = intent.getStringExtra("_weibo_transaction");
        if (TextUtils.isEmpty(stringExtra)) {
            d.a().e("handleWeiboResponse faild appPackage is null", new Object[0]);
            return;
        }
        d.a().d("handleWeiboResponse getCallingPackage : " + this.activity.getCallingPackage(), new Object[0]);
        if (TextUtils.isEmpty(stringExtra2)) {
            d.a().e("handleWeiboResponse faild intent _weibo_transaction is null", new Object[0]);
        } else if (e.a((Context) this.activity, stringExtra) || stringExtra.equals(this.activity.getPackageName())) {
            a(extras.getInt("_weibo_resp_errcode"), extras.getString("_weibo_resp_errstr"));
        } else {
            d.a().e("handleWeiboResponse faild appPackage validateSign faild", new Object[0]);
        }
    }

    private void a(int i, String str) {
        switch (i) {
            case 0:
                if (this.e != null) {
                    this.e.onComplete((Bundle) null);
                    break;
                }
                break;
            case 1:
                if (this.e != null) {
                    this.e.onCancel();
                    break;
                }
                break;
            case 2:
                if (this.e != null) {
                    this.e.onError(new Throwable(str));
                    break;
                }
                break;
        }
        finish();
    }

    private void b() {
        UIHandler.sendEmptyMessageDelayed(1, 200, this);
    }

    public boolean handleMessage(Message msg) {
        if (msg.what != 1) {
            return false;
        }
        if (!this.b && this.e != null) {
            this.e.onCancel();
        }
        finish();
        return false;
    }

    private boolean a(Activity activity, String str, String str2, Bundle bundle) {
        if (activity == null || TextUtils.isEmpty("com.sina.weibo.sdk.action.ACTION_WEIBO_ACTIVITY") || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            d.a().e("launchWeiboActivity fail, invalid arguments", new Object[0]);
            return false;
        }
        String packageName = activity.getPackageName();
        Intent intent = new Intent();
        intent.setPackage(str);
        intent.setAction("com.sina.weibo.sdk.action.ACTION_WEIBO_ACTIVITY");
        intent.putExtra("_weibo_sdkVersion", "0031405000");
        intent.putExtra("_weibo_appPackage", packageName);
        intent.putExtra("_weibo_appKey", str2);
        intent.putExtra("_weibo_flag", 538116905);
        intent.putExtra("_weibo_sign", a((Context) activity, packageName));
        intent.putExtra("_weibo_transaction", String.valueOf(System.currentTimeMillis()));
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        try {
            d.a().d("launchWeiboActivity intent=" + intent + ", extra=" + intent.getExtras(), new Object[0]);
            activity.startActivityForResult(intent, 765);
            return true;
        } catch (ActivityNotFoundException e2) {
            d.a().e(e2.getMessage(), new Object[0]);
            return false;
        }
    }

    private boolean c() {
        Intent intent = new Intent();
        intent.setAction("com.sina.weibo.sdk.Intent.ACTION_WEIBO_REGISTER");
        String packageName = this.activity.getPackageName();
        intent.putExtra("_weibo_sdkVersion", "0031405000");
        intent.putExtra("_weibo_appPackage", packageName);
        intent.putExtra("_weibo_appKey", this.c);
        intent.putExtra("_weibo_flag", 538116905);
        intent.putExtra("_weibo_sign", a((Context) this.activity, packageName));
        d.a().d("intent=" + intent + ", extra=" + intent.getExtras(), new Object[0]);
        this.activity.sendBroadcast(intent, "com.sina.weibo.permission.WEIBO_SDK_PERMISSION");
        return true;
    }

    private String a(Context context, String str) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 64);
            for (Signature byteArray : packageInfo.signatures) {
                byte[] byteArray2 = byteArray.toByteArray();
                if (byteArray2 != null) {
                    return Data.MD5(byteArray2);
                }
            }
            return null;
        } catch (PackageManager.NameNotFoundException e2) {
            return null;
        }
    }

    private String d() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private TextObject e() {
        TextObject textObject = new TextObject();
        textObject.text = this.d.getText();
        return textObject;
    }

    private WebpageObject f() {
        WebpageObject webpageObject = new WebpageObject();
        webpageObject.identify = d();
        webpageObject.title = this.d.getTitle();
        webpageObject.description = this.d.getText();
        webpageObject.actionUrl = this.d.getUrl();
        webpageObject.defaultText = this.d.getText();
        try {
            if (this.d.getImageData() != null) {
                webpageObject.thumbData = a((Context) this.activity, this.d.getImageData());
            } else if (!TextUtils.isEmpty(this.d.getImagePath())) {
                webpageObject.thumbData = b((Context) this.activity, this.d.getImagePath());
            }
        } catch (Throwable th) {
            d.a().d(th);
            webpageObject.setThumbImage((Bitmap) null);
        }
        return webpageObject;
    }

    private ImageObject g() {
        ImageObject imageObject = new ImageObject();
        try {
            if (this.d.getImageData() != null) {
                imageObject.imageData = a((Context) this.activity, this.d.getImageData());
            } else if (!TextUtils.isEmpty(this.d.getImagePath())) {
                DeviceHelper instance = DeviceHelper.getInstance(this.activity);
                if (instance.getSdcardState() && this.d.getImagePath().startsWith(instance.getSdcardPath())) {
                    File file = new File(this.d.getImagePath());
                    if (file.exists() && file.length() != 0 && file.length() < 10485760) {
                        imageObject.imagePath = this.d.getImagePath();
                    }
                }
                imageObject.imageData = b((Context) this.activity, this.d.getImagePath());
            }
        } catch (Throwable th) {
            d.a().d(th);
        }
        return imageObject;
    }

    private byte[] a(Context context, Bitmap bitmap) throws Throwable {
        if (bitmap == null) {
            throw new RuntimeException("checkArgs fail, thumbData is null");
        } else if (!bitmap.isRecycled()) {
            return b(context, bitmap);
        } else {
            throw new RuntimeException("checkArgs fail, thumbData is recycled");
        }
    }

    private byte[] b(Context context, String str) throws Throwable {
        if (new File(str).exists()) {
            return b(context, BitmapHelper.getBitmap(str));
        }
        throw new FileNotFoundException();
    }

    private byte[] b(Context context, Bitmap bitmap) throws Throwable {
        if (bitmap == null) {
            throw new RuntimeException("checkArgs fail, thumbData is null");
        } else if (bitmap.isRecycled()) {
            throw new RuntimeException("checkArgs fail, thumbData is recycled");
        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, byteArrayOutputStream);
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            int length = byteArray.length;
            while (((long) length) > this.a) {
                bitmap = a(bitmap, ((double) length) / ((double) this.a));
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, byteArrayOutputStream2);
                byteArrayOutputStream2.flush();
                byteArrayOutputStream2.close();
                byteArray = byteArrayOutputStream2.toByteArray();
                length = byteArray.length;
            }
            return byteArray;
        }
    }

    private Bitmap a(Bitmap bitmap, double d2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        double sqrt = Math.sqrt(d2);
        return Bitmap.createScaledBitmap(bitmap, (int) (((double) width) / sqrt), (int) (((double) height) / sqrt), true);
    }
}

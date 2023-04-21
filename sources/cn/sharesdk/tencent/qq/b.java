package cn.sharesdk.tencent.qq;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.ImageGalleryActivity;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.a.a;
import cn.sharesdk.framework.authorize.AuthorizeListener;
import cn.sharesdk.framework.authorize.SSOListener;
import cn.sharesdk.framework.authorize.c;
import cn.sharesdk.framework.authorize.e;
import cn.sharesdk.framework.utils.d;
import com.alipay.sdk.util.h;
import com.facebook.common.util.UriUtil;
import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ResHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.achartengine.ChartFactory;

/* compiled from: QQHelper */
public class b extends cn.sharesdk.framework.b {
    private static final String[] b = {"get_user_info", "get_simple_userinfo", "get_user_profile", "get_app_friends", "add_share", "list_album", "upload_pic", "add_album", "set_user_face", "get_vip_info", "get_vip_rich_info", "get_intimate_friends_weibo", "match_nick_tips_weibo", "add_t", "add_pic_t"};
    private static b c;
    private String d;
    private String[] e;
    private String f;
    private String g;

    public static b a(Platform platform) {
        if (c == null) {
            c = new b(platform);
        }
        return c;
    }

    private b(Platform platform) {
        super(platform);
    }

    public void a(String str) {
        this.d = str;
    }

    public void a(String[] strArr) {
        this.e = strArr;
    }

    public void a(final AuthorizeListener authorizeListener, boolean z) {
        if (z) {
            b(authorizeListener);
        } else {
            a(new SSOListener() {
                public void onFailed(Throwable t) {
                    b.this.b(authorizeListener);
                }

                public void onComplete(Bundle values) {
                    authorizeListener.onComplete(values);
                }

                public void onCancel() {
                    authorizeListener.onCancel();
                }
            });
        }
    }

    public void b(String str) {
        this.f = str;
    }

    public HashMap<String, Object> c(String str) throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("access_token", str));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new KVPair("User-Agent", System.getProperties().getProperty("http.agent") + " ArzenAndroidSDK"));
        String a = a.a().a("https://graph.qq.com/oauth2.0/me", (ArrayList<KVPair<String>>) arrayList, (ArrayList<KVPair<String>>) arrayList2, (NetworkHelper.NetworkTimeOut) null, "/oauth2.0/me", c());
        if (a.startsWith(com.alipay.sdk.authjs.a.c)) {
            while (!a.startsWith("{") && a.length() > 0) {
                a = a.substring(1);
            }
            while (!a.endsWith(h.d) && a.length() > 0) {
                a = a.substring(0, a.length() - 1);
            }
        }
        if (a == null || a.length() <= 0) {
            return null;
        }
        return new Hashon().fromJson(a);
    }

    public void d(String str) {
        this.g = str;
    }

    public String getAuthorizeUrl() {
        String redirectUri;
        ShareSDK.logApiEvent("/oauth2.0/authorize", c());
        String b2 = b();
        try {
            redirectUri = Data.urlEncode(getRedirectUri(), "utf-8");
        } catch (Throwable th) {
            d.a().d(th);
            redirectUri = getRedirectUri();
        }
        return "https://graph.qq.com/oauth2.0/m_authorize?response_type=token&client_id=" + this.d + "&redirect_uri=" + redirectUri + "&display=mobile&scope=" + b2;
    }

    public String getRedirectUri() {
        return "auth://tauth.qq.com/";
    }

    public cn.sharesdk.framework.authorize.b getAuthorizeWebviewClient(e webAct) {
        return new a(webAct);
    }

    private String b() {
        String[] strArr = this.e == null ? b : this.e;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String str : strArr) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(str);
            i++;
        }
        return sb.toString();
    }

    public cn.sharesdk.framework.authorize.d getSSOProcessor(c ssoAct) {
        c cVar = new c(ssoAct);
        cVar.a(5656);
        cVar.a(this.d, b());
        return cVar;
    }

    public HashMap<String, Object> e(String str) throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("access_token", this.g));
        arrayList.add(new KVPair("oauth_consumer_key", this.d));
        arrayList.add(new KVPair("openid", this.f));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new KVPair("User-Agent", System.getProperties().getProperty("http.agent") + " ArzenAndroidSDK"));
        String a = a.a().a("https://graph.qq.com/user/get_simple_userinfo", (ArrayList<KVPair<String>>) arrayList, (ArrayList<KVPair<String>>) arrayList2, (NetworkHelper.NetworkTimeOut) null, "/user/get_simple_userinfo", c());
        if (a == null || a.length() <= 0) {
            return null;
        }
        return new Hashon().fromJson(a);
    }

    public void a(String str, String str2, String str3, String str4, String str5, String str6, boolean z, PlatformActionListener platformActionListener, boolean z2, int i) {
        if (z2) {
            b(str, str2, str3, str4, str5, str6, platformActionListener);
        } else if (!z || !a()) {
            a(str, str2, str3, str4, str5, str6, platformActionListener);
        } else {
            if (!TextUtils.isEmpty(str4)) {
                File file = new File(str4);
                if (file.exists() && str4.startsWith("/data/")) {
                    String absolutePath = new File(ResHelper.getCachePath(this.a.getContext(), ImageGalleryActivity.KEY_IMAGE), System.currentTimeMillis() + file.getName()).getAbsolutePath();
                    if (ResHelper.copyFile(str4, absolutePath)) {
                        str4 = absolutePath;
                    } else {
                        str4 = null;
                    }
                }
            }
            Intent intent = new Intent();
            intent.putExtra(ChartFactory.TITLE, str);
            intent.putExtra("titleUrl", str2);
            intent.putExtra("summary", str3);
            intent.putExtra("imagePath", str4);
            intent.putExtra("imageUrl", str5);
            intent.putExtra("musicUrl", str6);
            intent.putExtra("appId", this.d);
            intent.putExtra("hidden", i);
            e eVar = new e();
            eVar.a(this.a, platformActionListener);
            eVar.a(this.d);
            eVar.show(this.a.getContext(), intent);
        }
    }

    public boolean a() {
        String str = null;
        try {
            str = this.a.getContext().getPackageManager().getPackageInfo("com.tencent.mobileqq", 0).versionName;
        } catch (Throwable th) {
            try {
                str = this.a.getContext().getPackageManager().getPackageInfo("com.tencent.qqlite", 0).versionName;
            } catch (Throwable th2) {
                try {
                    str = this.a.getContext().getPackageManager().getPackageInfo("com.tencent.minihd.qq", 0).versionName;
                } catch (Throwable th3) {
                }
            }
        }
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return true;
    }

    private void a(String str, String str2, String str3, String str4, String str5, String str6, PlatformActionListener platformActionListener) {
        String str7;
        if (str5 == null && str4 != null && new File(str4).exists()) {
            str5 = ((QQ) this.a).uploadImageToFileServer(str4);
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("http://openmobile.qq.com/api/check?");
            sb.append("page=shareindex.html&");
            sb.append("style=9&");
            sb.append("action=shareToQQ&");
            sb.append("sdkv=2.2.1&");
            sb.append("sdkp=a&");
            sb.append("appId=").append(this.d).append(com.alipay.sdk.sys.a.b);
            DeviceHelper instance = DeviceHelper.getInstance(this.a.getContext());
            sb.append("status_os=").append(Data.urlEncode(instance.getOSVersionName(), "utf-8")).append(com.alipay.sdk.sys.a.b);
            sb.append("status_machine=").append(Data.urlEncode(instance.getModel(), "utf-8")).append(com.alipay.sdk.sys.a.b);
            sb.append("status_version=").append(Data.urlEncode(String.valueOf(instance.getOSVersionInt()), "utf-8")).append(com.alipay.sdk.sys.a.b);
            String appName = instance.getAppName();
            if (!TextUtils.isEmpty(appName)) {
                sb.append("site=").append(Data.urlEncode(appName, "utf-8")).append(com.alipay.sdk.sys.a.b);
            }
            if (!TextUtils.isEmpty(str)) {
                if (str.length() > 40) {
                    str7 = str.substring(40) + "...";
                } else {
                    str7 = str;
                }
                if (str7.length() > 80) {
                    str7 = str7.substring(80) + "...";
                }
                sb.append("title=").append(Data.urlEncode(str7, "utf-8")).append(com.alipay.sdk.sys.a.b);
            }
            if (!TextUtils.isEmpty(str3)) {
                sb.append("summary=").append(Data.urlEncode(str3, "utf-8")).append(com.alipay.sdk.sys.a.b);
            }
            if (!TextUtils.isEmpty(str2)) {
                sb.append("targeturl=").append(Data.urlEncode(str2, "utf-8")).append(com.alipay.sdk.sys.a.b);
            }
            if (!TextUtils.isEmpty(str5)) {
                sb.append("imageUrl=").append(Data.urlEncode(str5, "utf-8")).append(com.alipay.sdk.sys.a.b);
            }
            sb.append("type=1");
            f fVar = new f();
            fVar.a(sb.toString());
            fVar.a(platformActionListener);
            fVar.b(this.d);
            fVar.show(this.a.getContext(), (Intent) null);
        } catch (Throwable th) {
            if (platformActionListener != null) {
                platformActionListener.onError(this.a, 9, th);
            }
        }
    }

    private void b(String str, String str2, String str3, String str4, String str5, String str6, PlatformActionListener platformActionListener) {
        String b2;
        try {
            boolean z = !TextUtils.isEmpty(str4) || !TextUtils.isEmpty(str5);
            String str7 = !z ? "/t/add_t" : "/t/add_pic_t";
            String str8 = "https://graph.qq.com" + str7;
            ArrayList arrayList = new ArrayList();
            arrayList.add(new KVPair("oauth_consumer_key", this.d));
            arrayList.add(new KVPair("access_token", this.g));
            arrayList.add(new KVPair("openid", this.f));
            arrayList.add(new KVPair("format", "json"));
            arrayList.add(new KVPair(UriUtil.LOCAL_CONTENT_SCHEME, str3));
            if (z) {
                if (TextUtils.isEmpty(str4)) {
                    str4 = BitmapHelper.downloadBitmap(this.a.getContext(), str5);
                }
                b2 = a.a().a(str8, arrayList, new KVPair("pic", str4), str7, c());
            } else {
                b2 = a.a().b(str8, arrayList, str7, c());
            }
            if (b2 != null && b2.length() > 0 && platformActionListener != null) {
                HashMap fromJson = new Hashon().fromJson(b2);
                if (((Integer) fromJson.get("ret")).intValue() != 0) {
                    platformActionListener.onError(this.a, 9, new Exception(b2));
                } else {
                    platformActionListener.onComplete(this.a, 9, fromJson);
                }
            }
        } catch (Throwable th) {
            if (platformActionListener != null) {
                platformActionListener.onError(this.a, 9, th);
            }
        }
    }
}

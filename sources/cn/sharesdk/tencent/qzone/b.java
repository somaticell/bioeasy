package cn.sharesdk.tencent.qzone;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
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
import com.alibaba.fastjson.asm.Opcodes;
import com.alipay.sdk.util.h;
import com.facebook.common.util.UriUtil;
import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ReflectHelper;
import com.mob.tools.utils.ResHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: QZoneHelper */
public class b extends cn.sharesdk.framework.b {
    private static final String[] b = {"get_user_info", "get_simple_userinfo", "get_user_profile", "get_app_friends", "add_share", "list_album", "upload_pic", "add_album", "set_user_face", "get_vip_info", "get_vip_rich_info", "get_intimate_friends_weibo", "match_nick_tips_weibo", "add_t", "add_pic_t"};
    private static b c;
    private String d;
    private String e;
    private String f;
    private a g = a.a();
    private String[] h;

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
        this.h = strArr;
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
        String[] strArr = this.h == null ? b : this.h;
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

    public void b(String str) {
        this.e = str;
    }

    public void c(String str) {
        this.f = str;
    }

    public HashMap<String, Object> d(String str) throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("access_token", this.f));
        arrayList.add(new KVPair("oauth_consumer_key", this.d));
        arrayList.add(new KVPair("openid", this.e));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new KVPair("User-Agent", System.getProperties().getProperty("http.agent") + " ArzenAndroidSDK"));
        String a = this.g.a("https://graph.qq.com/user/get_simple_userinfo", (ArrayList<KVPair<String>>) arrayList, (ArrayList<KVPair<String>>) arrayList2, (NetworkHelper.NetworkTimeOut) null, "/user/get_simple_userinfo", c());
        if (a == null || a.length() <= 0) {
            return null;
        }
        return new Hashon().fromJson(a);
    }

    public void a(int i, String str, String str2, String str3, String str4, String str5, String str6, PlatformActionListener platformActionListener) throws Throwable {
        String str7;
        String str8;
        if (TextUtils.isEmpty(str5)) {
            str5 = DeviceHelper.getInstance(this.a.getContext()).getAppName();
        }
        if (str5.length() > 20) {
            str7 = str5.substring(0, 20) + "...";
        } else {
            str7 = str5;
        }
        if (TextUtils.isEmpty(str) || str.length() <= 200) {
            str8 = str;
        } else {
            str8 = str.substring(0, 200);
        }
        b(i, str8, str2, str3, str4, str7, str6, platformActionListener);
    }

    public void b(int i, String str, String str2, String str3, String str4, String str5, String str6, PlatformActionListener platformActionListener) throws Throwable {
        String str7 = com.alipay.sdk.cons.a.e;
        if (i == 6 && !TextUtils.isEmpty(str6)) {
            str7 = "4";
        } else if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
            str7 = "3";
        } else if (TextUtils.isEmpty(str2)) {
            if (platformActionListener != null) {
                platformActionListener.onError((Platform) null, 9, new Throwable("The param of title or titleUrl is null !"));
                return;
            }
            return;
        }
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
        if (!TextUtils.isEmpty(str3) && str3.length() > 600) {
            str3 = str3.substring(0, 600);
        }
        StringBuilder sb = new StringBuilder();
        if (str7 == "3" || str7 == "4") {
            sb.append("mqqapi://qzone/publish?src_type=app&version=1&file_type=news");
        } else {
            sb.append("mqqapi://share/to_qzone?src_type=app&version=1&file_type=news");
        }
        if (!TextUtils.isEmpty(str4)) {
            sb.append("&image_url=").append(Base64.encodeToString(str4.getBytes("utf-8"), 2));
        }
        if (i == 6 && !TextUtils.isEmpty(str6) && str7.equals("4")) {
            ResHelper.getFileSize(str6);
            String valueOf = String.valueOf(str5);
            String f2 = f(str6);
            sb.append("&videoPath=").append(Base64.encodeToString(str6.getBytes("utf-8"), 2));
            sb.append("&videoSize=").append(Base64.encodeToString(valueOf.getBytes("utf-8"), 2));
            if (!TextUtils.isEmpty(f2)) {
                sb.append("&videoDuration=").append(Base64.encodeToString(f2.getBytes("utf-8"), 2));
            }
        }
        if (!TextUtils.isEmpty(str)) {
            sb.append("&title=").append(Base64.encodeToString(str.getBytes("utf-8"), 2));
        }
        if (!TextUtils.isEmpty(str3)) {
            sb.append("&description=").append(Base64.encodeToString(str3.getBytes("utf-8"), 2));
        }
        sb.append("&share_id=").append(this.d);
        if (!TextUtils.isEmpty(str2)) {
            sb.append("&url=").append(Base64.encodeToString(str2.getBytes("utf-8"), 2));
        }
        sb.append("&app_name=").append(Base64.encodeToString(str5.getBytes("utf-8"), 2));
        if (!TextUtils.isEmpty(str3)) {
            sb.append("&share_qq_ext_str=").append(Base64.encodeToString(str3.getBytes(), 2));
        }
        sb.append("&req_type=").append(Base64.encodeToString(str7.getBytes("utf-8"), 2));
        sb.append("&cflag=").append(Base64.encodeToString((a() ? com.alipay.sdk.cons.a.e : "0").getBytes("utf-8"), 2));
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(sb.toString()));
        if (this.a.getContext().getPackageManager().resolveActivity(intent, 1) != null) {
            d dVar = new d();
            dVar.a(sb.toString(), true);
            dVar.a(platformActionListener);
            dVar.a(this.d);
            dVar.show(this.a.getContext(), (Intent) null);
        }
    }

    private String f(String str) {
        if (Build.VERSION.SDK_INT <= 10) {
            return "";
        }
        try {
            Class<?> cls = ReflectHelper.getClass("android.media.MediaMetadataRetriever");
            Object newInstance = cls.newInstance();
            cls.getMethod("setDataSource", new Class[]{String.class}).invoke(newInstance, new Object[]{str});
            return (String) ReflectHelper.invokeInstanceMethod(newInstance, "extractMetadata", 9);
        } catch (Throwable th) {
            return "";
        }
    }

    public boolean a() {
        String str;
        try {
            str = this.a.getContext().getPackageManager().getPackageInfo("com.tencent.mobileqq", 0).versionName;
        } catch (Throwable th) {
            try {
                str = this.a.getContext().getPackageManager().getPackageInfo("com.tencent.minihd.qq", 0).versionName;
            } catch (Throwable th2) {
                d.a().d(th);
                str = null;
            }
        }
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return true;
    }

    public HashMap<String, Object> a(String str, String str2) throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("access_token", this.f));
        arrayList.add(new KVPair("oauth_consumer_key", this.d));
        arrayList.add(new KVPair("openid", this.e));
        arrayList.add(new KVPair("format", "json"));
        if (!TextUtils.isEmpty(str2)) {
            if (str2.length() > 200) {
                str2 = str2.substring(0, Opcodes.IFNONNULL) + this.a.getContext().getString(ResHelper.getStringRes(this.a.getContext(), "ssdk_symbol_ellipsis"));
            }
            arrayList.add(new KVPair("photodesc", str2));
        }
        arrayList.add(new KVPair("mobile", com.alipay.sdk.cons.a.e));
        KVPair kVPair = new KVPair("picture", str);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new KVPair("User-Agent", System.getProperties().getProperty("http.agent") + " ArzenAndroidSDK"));
        String a = this.g.a("https://graph.qq.com/photo/upload_pic", (ArrayList<KVPair<String>>) arrayList, (KVPair<String>) kVPair, (ArrayList<KVPair<String>>) arrayList2, "/photo/upload_pic", c());
        if (a == null || a.length() <= 0) {
            return null;
        }
        return new Hashon().fromJson(a);
    }

    public HashMap<String, Object> e(String str) throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("access_token", str));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new KVPair("User-Agent", System.getProperties().getProperty("http.agent") + " ArzenAndroidSDK"));
        String a = this.g.a("https://graph.qq.com/oauth2.0/me", (ArrayList<KVPair<String>>) arrayList, (ArrayList<KVPair<String>>) arrayList2, (NetworkHelper.NetworkTimeOut) null, "/oauth2.0/me", c());
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

    public HashMap<String, Object> a(String str, String str2, HashMap<String, Object> hashMap, HashMap<String, String> hashMap2) {
        KVPair kVPair;
        String str3;
        if (str2 == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (hashMap != null && hashMap.size() > 0) {
            for (Map.Entry next : hashMap.entrySet()) {
                arrayList.add(new KVPair((String) next.getKey(), String.valueOf(next.getValue())));
            }
        }
        arrayList.add(new KVPair("access_token", this.f));
        arrayList.add(new KVPair("oauth_consumer_key", this.d));
        arrayList.add(new KVPair("openid", this.e));
        arrayList.add(new KVPair("format", "json"));
        if (hashMap2 == null || hashMap2.size() <= 0) {
            kVPair = null;
        } else {
            KVPair kVPair2 = null;
            for (Map.Entry next2 : hashMap2.entrySet()) {
                kVPair2 = new KVPair((String) next2.getKey(), next2.getValue());
            }
            kVPair = kVPair2;
        }
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new KVPair("User-Agent", System.getProperties().getProperty("http.agent") + " ArzenAndroidSDK"));
        try {
            if ("GET".equals(str2.toUpperCase())) {
                str3 = new NetworkHelper().httpGet(str, arrayList, arrayList2, (NetworkHelper.NetworkTimeOut) null);
            } else {
                if ("POST".equals(str2.toUpperCase())) {
                    str3 = new NetworkHelper().httpPost(str, arrayList, kVPair, arrayList2, (NetworkHelper.NetworkTimeOut) null);
                }
                str3 = null;
            }
        } catch (Throwable th) {
            d.a().d(th);
        }
        if (str3 == null || str3.length() <= 0) {
            return null;
        }
        return new Hashon().fromJson(str3);
    }

    public HashMap<String, Object> b(String str, String str2) throws Throwable {
        String b2;
        boolean z = !TextUtils.isEmpty(str);
        String str3 = z ? "/t/add_pic_t" : "/t/add_t";
        String str4 = "https://graph.qq.com" + str3;
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("oauth_consumer_key", this.d));
        arrayList.add(new KVPair("access_token", this.f));
        arrayList.add(new KVPair("openid", this.e));
        arrayList.add(new KVPair("format", "json"));
        arrayList.add(new KVPair(UriUtil.LOCAL_CONTENT_SCHEME, str2));
        if (z) {
            b2 = this.g.a(str4, arrayList, new KVPair("pic", str), str3, c());
        } else {
            b2 = this.g.b(str4, arrayList, str3, c());
        }
        if (b2 == null || b2.length() <= 0) {
            return null;
        }
        HashMap<String, Object> fromJson = new Hashon().fromJson(b2);
        if (((Integer) fromJson.get("ret")).intValue() == 0) {
            return fromJson;
        }
        throw new Throwable(b2);
    }
}

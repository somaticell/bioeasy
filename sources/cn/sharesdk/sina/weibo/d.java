package cn.sharesdk.sina.weibo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import cn.com.bioeasy.app.utils.ListUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.a.a;
import cn.sharesdk.framework.authorize.AuthorizeListener;
import cn.sharesdk.framework.authorize.SSOListener;
import cn.sharesdk.framework.authorize.c;
import cn.sharesdk.framework.authorize.e;
import cn.sharesdk.framework.b;
import com.baidu.mapapi.SDKInitializer;
import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ResHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Weibo */
public class d extends b {
    private static d b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String[] g = {"follow_app_official_microblog"};
    private a h = a.a();
    private Context i;

    public static synchronized d a(Platform platform) {
        d dVar;
        synchronized (d.class) {
            if (b == null) {
                b = new d(platform);
            }
            dVar = b;
        }
        return dVar;
    }

    private d(Platform platform) {
        super(platform);
        this.i = platform.getContext();
    }

    public void a(String str, String str2) {
        this.c = str;
        this.d = str2;
    }

    public void a(String str) {
        this.e = str;
    }

    public void a(String[] strArr) {
        this.g = strArr;
    }

    public cn.sharesdk.framework.authorize.b getAuthorizeWebviewClient(e webAct) {
        return new b(webAct);
    }

    public cn.sharesdk.framework.authorize.d getSSOProcessor(c ssoAct) {
        c cVar = new c(ssoAct);
        cVar.a(32973);
        cVar.a(this.c, this.e, this.g);
        return cVar;
    }

    public void a(final AuthorizeListener authorizeListener, boolean z) {
        if (z) {
            b(authorizeListener);
        } else {
            a(new SSOListener() {
                public void onFailed(Throwable t) {
                    cn.sharesdk.framework.utils.d.a().d(t);
                    d.this.b(authorizeListener);
                }

                public void onComplete(Bundle values) {
                    try {
                        ResHelper.parseLong(values.getString("expires_in"));
                        authorizeListener.onComplete(values);
                    } catch (Throwable th) {
                        onFailed(th);
                    }
                }

                public void onCancel() {
                    authorizeListener.onCancel();
                }
            });
        }
    }

    public String getAuthorizeUrl() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("client_id", this.c));
        arrayList.add(new KVPair("response_type", "code"));
        arrayList.add(new KVPair("redirect_uri", this.e));
        if (this.g != null && this.g.length > 0) {
            arrayList.add(new KVPair("scope", TextUtils.join(ListUtils.DEFAULT_JOIN_SEPARATOR, this.g)));
        }
        arrayList.add(new KVPair("display", "mobile"));
        String str = "https://open.weibo.cn/oauth2/authorize?" + ResHelper.encodeUrl((ArrayList<KVPair<String>>) arrayList);
        ShareSDK.logApiEvent("/oauth2/authorize", c());
        return str;
    }

    public String getRedirectUri() {
        return TextUtils.isEmpty(this.e) ? "https://api.weibo.com/oauth2/default.html" : this.e;
    }

    public String a(Context context, String str) throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("client_id", this.c));
        arrayList.add(new KVPair("client_secret", this.d));
        arrayList.add(new KVPair("redirect_uri", this.e));
        arrayList.add(new KVPair("grant_type", "authorization_code"));
        arrayList.add(new KVPair("code", str));
        String b2 = this.h.b("https://api.weibo.com/oauth2/access_token", arrayList, "/oauth2/access_token", c());
        ShareSDK.logApiEvent("/oauth2/access_token", c());
        return b2;
    }

    public boolean a() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("client_id", this.c));
        arrayList.add(new KVPair("client_secret", this.d));
        arrayList.add(new KVPair("redirect_uri", this.e));
        arrayList.add(new KVPair("grant_type", "refresh_token"));
        arrayList.add(new KVPair("refresh_token", this.a.getDb().get("refresh_token")));
        try {
            String b2 = this.h.b("https://api.weibo.com/oauth2/access_token", arrayList, "/oauth2/access_token", c());
            if (TextUtils.isEmpty(b2) || b2.contains("error") || b2.contains(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE)) {
                return false;
            }
            e(b2);
            return true;
        } catch (Throwable th) {
            cn.sharesdk.framework.utils.d.a().d(th);
            return false;
        }
    }

    public void b(String str) {
        this.f = str;
    }

    public HashMap<String, Object> c(String str) throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("source", this.c));
        if (this.f != null) {
            arrayList.add(new KVPair("access_token", this.f));
        }
        boolean z = true;
        try {
            ResHelper.parseLong(str);
        } catch (Throwable th) {
            z = false;
        }
        if (z) {
            arrayList.add(new KVPair("uid", str));
        } else {
            arrayList.add(new KVPair("screen_name", str));
        }
        String a = this.h.a("https://api.weibo.com/2/users/show.json", arrayList, "/2/users/show.json", c());
        if (a != null) {
            return new Hashon().fromJson(a);
        }
        return null;
    }

    public boolean b() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setPackage("com.sina.weibo");
        intent.setType("image/*");
        ResolveInfo resolveActivity = this.a.getContext().getPackageManager().resolveActivity(intent, 0);
        if (resolveActivity == null) {
            Intent intent2 = new Intent("android.intent.action.SEND");
            intent2.setPackage("com.sina.weibog3");
            intent2.setType("image/*");
            resolveActivity = this.a.getContext().getPackageManager().resolveActivity(intent2, 0);
        }
        if (resolveActivity != null) {
            return true;
        }
        return false;
    }

    public void a(final Platform.ShareParams shareParams, final PlatformActionListener platformActionListener) {
        if (shareParams.getImageData() == null && TextUtils.isEmpty(shareParams.getImagePath()) && !TextUtils.isEmpty(shareParams.getImageUrl())) {
            try {
                File file = new File(BitmapHelper.downloadBitmap(this.i, shareParams.getImageUrl()));
                if (file.exists()) {
                    shareParams.setImagePath(file.getAbsolutePath());
                }
            } catch (Throwable th) {
                cn.sharesdk.framework.utils.d.a().d(th);
            }
        }
        String text = shareParams.getText();
        if (!TextUtils.isEmpty(text)) {
            shareParams.setText(getPlatform().getShortLintk(text, false));
        }
        AnonymousClass2 r0 = new AuthorizeListener() {
            public void onError(Throwable t) {
                if (platformActionListener != null) {
                    platformActionListener.onError(d.this.a, 9, t);
                }
            }

            public void onComplete(Bundle bundle) {
                if (platformActionListener != null) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("ShareParams", shareParams);
                    platformActionListener.onComplete(d.this.a, 9, hashMap);
                }
            }

            public void onCancel() {
                if (platformActionListener != null) {
                    platformActionListener.onCancel(d.this.a, 9);
                }
            }
        };
        a aVar = new a();
        aVar.a(this.c);
        aVar.a(shareParams);
        aVar.a((AuthorizeListener) r0);
        aVar.show(this.i, (Intent) null, true);
    }

    public HashMap<String, Object> a(String str, String str2, String str3, float f2, float f3) throws Throwable {
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            throw new Throwable("weibo content can not be null!");
        } else if (!TextUtils.isEmpty(str3)) {
            return b(str3, str, f2, f3);
        } else {
            if (!TextUtils.isEmpty(str2)) {
                return a(str, str2, f2, f3);
            }
            return a(str, f2, f3);
        }
    }

    public HashMap<String, Object> a(String str, String str2, String str3, String str4) throws Throwable {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            throw new Throwable("weibo content can not be null!");
        } else if (!TextUtils.isEmpty(str4)) {
            return a(str, str2, str4);
        } else {
            if (!TextUtils.isEmpty(str3)) {
                return b(str, str2, str3);
            }
            return b(str, str2);
        }
    }

    private HashMap<String, Object> b(String str, String str2) throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("access_token", this.f));
        arrayList.add(new KVPair("status", str + str2));
        String b2 = this.h.b("https://api.weibo.com/2/statuses/share.json", arrayList, "/2/statuses/share.json", c());
        if (b2 != null) {
            return new Hashon().fromJson(b2);
        }
        return null;
    }

    private HashMap<String, Object> a(String str, String str2, String str3) throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("access_token", this.f));
        arrayList.add(new KVPair("status", str + str2));
        String a = this.h.a("https://api.weibo.com/2/statuses/share.json", arrayList, new KVPair("pic", str3), "/2/statuses/share.json", c());
        if (a != null) {
            return new Hashon().fromJson(a);
        }
        return null;
    }

    private HashMap<String, Object> b(String str, String str2, String str3) throws Throwable {
        String downloadBitmap = BitmapHelper.downloadBitmap(this.i, str3);
        if (!TextUtils.isEmpty(downloadBitmap)) {
            return a(str, str2, downloadBitmap);
        }
        return null;
    }

    private HashMap<String, Object> a(String str, String str2, float f2, float f3) throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("source", this.c));
        arrayList.add(new KVPair("access_token", this.f));
        arrayList.add(new KVPair("status", str));
        arrayList.add(new KVPair("long", String.valueOf(f2)));
        arrayList.add(new KVPair("lat", String.valueOf(f3)));
        arrayList.add(new KVPair("url", str2));
        String b2 = this.h.b("https://api.weibo.com/2/statuses/upload_url_text.json", arrayList, "/2/statuses/upload_url_text.json", c());
        if (b2 != null) {
            return new Hashon().fromJson(b2);
        }
        return null;
    }

    private HashMap<String, Object> b(String str, String str2, float f2, float f3) throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("source", this.c));
        arrayList.add(new KVPair("access_token", this.f));
        arrayList.add(new KVPair("status", str2));
        arrayList.add(new KVPair("long", String.valueOf(f2)));
        arrayList.add(new KVPair("lat", String.valueOf(f3)));
        String a = this.h.a("https://api.weibo.com/2/statuses/upload.json", arrayList, new KVPair("pic", str), "/2/statuses/upload.json", c());
        if (a != null) {
            return new Hashon().fromJson(a);
        }
        return null;
    }

    private HashMap<String, Object> a(String str, float f2, float f3) throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("source", this.c));
        arrayList.add(new KVPair("access_token", this.f));
        arrayList.add(new KVPair("status", str));
        arrayList.add(new KVPair("long", String.valueOf(f2)));
        arrayList.add(new KVPair("lat", String.valueOf(f3)));
        String b2 = this.h.b("https://api.weibo.com/2/statuses/update.json", arrayList, "/2/statuses/update.json", c());
        if (b2 != null) {
            return new Hashon().fromJson(b2);
        }
        return null;
    }

    public HashMap<String, Object> d(String str) throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("source", this.c));
        arrayList.add(new KVPair("access_token", this.f));
        boolean z = true;
        try {
            ResHelper.parseLong(str);
        } catch (Throwable th) {
            z = false;
        }
        if (z) {
            arrayList.add(new KVPair("uid", str));
        } else {
            arrayList.add(new KVPair("screen_name", str));
        }
        String b2 = this.h.b("https://api.weibo.com/2/friendships/create.json", arrayList, "/2/friendships/create.json", c());
        if (b2 != null) {
            return new Hashon().fromJson(b2);
        }
        return null;
    }

    public HashMap<String, Object> a(int i2, int i3, String str) throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("source", this.c));
        boolean z = true;
        try {
            ResHelper.parseLong(str);
        } catch (Throwable th) {
            z = false;
        }
        if (z) {
            arrayList.add(new KVPair("uid", str));
        } else {
            arrayList.add(new KVPair("screen_name", str));
        }
        arrayList.add(new KVPair("count", String.valueOf(i2)));
        arrayList.add(new KVPair("page", String.valueOf(i3)));
        String a = this.h.a("https://api.weibo.com/2/statuses/user_timeline.json", arrayList, "/2/statuses/user_timeline.json", c());
        if (a != null) {
            return new Hashon().fromJson(a);
        }
        return null;
    }

    public HashMap<String, Object> b(int i2, int i3, String str) throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("source", this.c));
        if (this.f != null) {
            arrayList.add(new KVPair("access_token", this.f));
        }
        boolean z = true;
        try {
            ResHelper.parseLong(str);
        } catch (Throwable th) {
            z = false;
        }
        if (z) {
            arrayList.add(new KVPair("uid", str));
        } else {
            arrayList.add(new KVPair("screen_name", str));
        }
        arrayList.add(new KVPair("count", String.valueOf(i2)));
        arrayList.add(new KVPair("cursor", String.valueOf(i3)));
        String a = this.h.a("https://api.weibo.com/2/friendships/friends.json", arrayList, "/2/friendships/friends.json", c());
        if (a != null) {
            return new Hashon().fromJson(a);
        }
        return null;
    }

    public HashMap<String, Object> c(int i2, int i3, String str) throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("source", this.c));
        if (this.f != null) {
            arrayList.add(new KVPair("access_token", this.f));
        }
        boolean z = true;
        try {
            ResHelper.parseLong(str);
        } catch (Throwable th) {
            z = false;
        }
        if (z) {
            arrayList.add(new KVPair("uid", str));
        } else {
            arrayList.add(new KVPair("screen_name", str));
        }
        arrayList.add(new KVPair("count", String.valueOf(i2)));
        arrayList.add(new KVPair("page", String.valueOf(i3)));
        String a = this.h.a("https://api.weibo.com/2/friendships/friends/bilateral.json", arrayList, "/2/friendships/friends/bilateral.json", c());
        if (a != null) {
            return new Hashon().fromJson(a);
        }
        return null;
    }

    public HashMap<String, Object> d(int i2, int i3, String str) throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("source", this.c));
        if (this.f != null) {
            arrayList.add(new KVPair("access_token", this.f));
        }
        boolean z = true;
        try {
            ResHelper.parseLong(str);
        } catch (Throwable th) {
            z = false;
        }
        if (z) {
            arrayList.add(new KVPair("uid", str));
        } else {
            arrayList.add(new KVPair("screen_name", str));
        }
        arrayList.add(new KVPair("count", String.valueOf(i2)));
        arrayList.add(new KVPair("cursor", String.valueOf(i3)));
        String a = this.h.a("https://api.weibo.com/2/friendships/followers.json", arrayList, "/2/friendships/followers.json", c());
        if (a != null) {
            return new Hashon().fromJson(a);
        }
        return null;
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
        arrayList.add(new KVPair("source", this.c));
        if (this.f != null) {
            arrayList.add(new KVPair("access_token", this.f));
        }
        if (hashMap2 == null || hashMap2.size() <= 0) {
            kVPair = null;
        } else {
            KVPair kVPair2 = null;
            for (Map.Entry next2 : hashMap2.entrySet()) {
                kVPair2 = new KVPair((String) next2.getKey(), next2.getValue());
            }
            kVPair = kVPair2;
        }
        try {
            if ("GET".equals(str2.toUpperCase())) {
                str3 = new NetworkHelper().httpGet(str, arrayList, (ArrayList<KVPair<String>>) null, (NetworkHelper.NetworkTimeOut) null);
            } else {
                if ("POST".equals(str2.toUpperCase())) {
                    str3 = new NetworkHelper().httpPost(str, arrayList, kVPair, (ArrayList<KVPair<String>>) null, (NetworkHelper.NetworkTimeOut) null);
                }
                str3 = null;
            }
        } catch (Throwable th) {
            cn.sharesdk.framework.utils.d.a().d(th);
        }
        if (str3 == null || str3.length() <= 0) {
            return null;
        }
        return new Hashon().fromJson(str3);
    }

    private void e(String str) {
        HashMap fromJson = new Hashon().fromJson(str);
        String valueOf = String.valueOf(fromJson.get("uid"));
        String valueOf2 = String.valueOf(fromJson.get("expires_in"));
        String valueOf3 = String.valueOf(fromJson.get("access_token"));
        String valueOf4 = String.valueOf(fromJson.get("refresh_token"));
        String valueOf5 = String.valueOf(fromJson.get("remind_in"));
        this.a.getDb().putUserId(valueOf);
        this.a.getDb().putExpiresIn(Long.valueOf(valueOf2).longValue());
        this.a.getDb().putToken(valueOf3);
        this.a.getDb().put("refresh_token", valueOf4);
        this.a.getDb().put("remind_in", valueOf5);
    }
}

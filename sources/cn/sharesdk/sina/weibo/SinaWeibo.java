package cn.sharesdk.sina.weibo;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.authorize.AuthorizeListener;
import cn.sharesdk.framework.b.b.f;
import cn.sharesdk.framework.utils.d;
import com.alipay.sdk.cons.a;
import com.alipay.sdk.cons.b;
import com.baidu.mapapi.SDKInitializer;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ResHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SinaWeibo extends Platform {
    public static final String NAME = SinaWeibo.class.getSimpleName();
    private String a;
    private String b;
    private String c;
    private boolean d;
    private boolean e;

    public static class ShareParams extends Platform.ShareParams {
        @Deprecated
        public String imageUrl;
        @Deprecated
        public float latitude;
        @Deprecated
        public float longitude;
    }

    public SinaWeibo(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void initDevInfo(String name) {
        this.a = getDevinfo("AppKey");
        this.b = getDevinfo("AppSecret");
        this.c = getDevinfo("RedirectUrl");
        this.d = "true".equals(getDevinfo("ShareByAppClient"));
        this.e = "true".equals(getDevinfo("isNewApi"));
    }

    public String getName() {
        return NAME;
    }

    public int getVersion() {
        return 1;
    }

    /* access modifiers changed from: protected */
    public int getPlatformId() {
        return 1;
    }

    /* access modifiers changed from: protected */
    public void setNetworkDevinfo() {
        this.a = getNetworkDevinfo(b.h, "AppKey");
        this.b = getNetworkDevinfo("app_secret", "AppSecret");
        this.c = getNetworkDevinfo("redirect_uri", "RedirectUrl");
    }

    public boolean isClientValid() {
        return d.a((Platform) this).b();
    }

    private boolean c() {
        if (TextUtils.isEmpty(getDb().get("refresh_token"))) {
            return false;
        }
        d a2 = d.a((Platform) this);
        a2.a(this.a, this.b);
        a2.a(this.c);
        return a2.a();
    }

    /* access modifiers changed from: protected */
    public boolean checkAuthorize(int action, Object extra) {
        d a2 = d.a((Platform) this);
        if (action == 9 && this.d && a2.b()) {
            return true;
        }
        if (isAuthValid() || c()) {
            a2.a(this.a, this.b);
            a2.b(this.db.getToken());
            return true;
        }
        innerAuthorize(action, extra);
        return false;
    }

    /* access modifiers changed from: protected */
    public void doAuthorize(String[] permissions) {
        final d a2 = d.a((Platform) this);
        a2.a(this.a, this.b);
        a2.a(this.c);
        a2.a(permissions);
        a2.a((AuthorizeListener) new AuthorizeListener() {
            public void onComplete(Bundle values) {
                long j;
                String string = values.getString("uid");
                String string2 = values.getString("access_token");
                String string3 = values.getString("expires_in");
                String string4 = values.getString("refresh_token");
                SinaWeibo.this.db.put("nickname", values.getString("userName"));
                SinaWeibo.this.db.put("remind_in", values.getString("remind_in"));
                SinaWeibo.this.db.putToken(string2);
                try {
                    j = ResHelper.parseLong(string3);
                } catch (Throwable th) {
                    j = 0;
                }
                SinaWeibo.this.db.putExpiresIn(j);
                SinaWeibo.this.db.put("refresh_token", string4);
                SinaWeibo.this.db.putUserId(string);
                a2.b(string2);
                SinaWeibo.this.afterRegister(1, (Object) null);
            }

            public void onError(Throwable e) {
                if (SinaWeibo.this.listener != null) {
                    SinaWeibo.this.listener.onError(SinaWeibo.this, 1, e);
                }
            }

            public void onCancel() {
                if (SinaWeibo.this.listener != null) {
                    SinaWeibo.this.listener.onCancel(SinaWeibo.this, 1);
                }
            }
        }, isSSODisable());
    }

    /* access modifiers changed from: protected */
    public void follow(String account) {
        try {
            HashMap<String, Object> d2 = d.a((Platform) this).d(account);
            if (d2 == null) {
                if (this.listener != null) {
                    this.listener.onError(this, 6, new Throwable());
                }
            } else if (!d2.containsKey(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE) || ((Integer) d2.get(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE)).intValue() == 0) {
                if (this.listener != null) {
                    this.listener.onComplete(this, 6, d2);
                }
            } else if (this.listener != null) {
                this.listener.onError(this, 6, new Throwable(new Hashon().fromHashMap(d2)));
            }
        } catch (Throwable th) {
            this.listener.onError(this, 6, th);
        }
    }

    /* access modifiers changed from: protected */
    public void timeline(int count, int page, String account) {
        if (TextUtils.isEmpty(account)) {
            account = this.db.getUserId();
        }
        if (TextUtils.isEmpty(account)) {
            account = this.db.get("nickname");
        }
        if (!TextUtils.isEmpty(account)) {
            try {
                HashMap<String, Object> a2 = d.a((Platform) this).a(count, page, account);
                if (a2 == null) {
                    if (this.listener != null) {
                        this.listener.onError(this, 7, new Throwable());
                    }
                } else if (!a2.containsKey(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE) || ((Integer) a2.get(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE)).intValue() == 0) {
                    if (this.listener != null) {
                        this.listener.onComplete(this, 7, a2);
                    }
                } else if (this.listener != null) {
                    this.listener.onError(this, 7, new Throwable(new Hashon().fromHashMap(a2)));
                }
            } catch (Throwable th) {
                this.listener.onError(this, 7, th);
            }
        } else if (this.listener != null) {
            this.listener.onError(this, 7, new RuntimeException("Both weibo id and screen_name are null"));
        }
    }

    /* access modifiers changed from: protected */
    public void userInfor(String account) {
        boolean z = true;
        boolean z2 = false;
        if (TextUtils.isEmpty(account)) {
            account = this.db.getUserId();
            z2 = true;
        }
        if (TextUtils.isEmpty(account)) {
            account = this.db.get("nickname");
        } else {
            z = z2;
        }
        if (!TextUtils.isEmpty(account)) {
            try {
                HashMap<String, Object> c2 = d.a((Platform) this).c(account);
                if (c2 == null) {
                    if (this.listener != null) {
                        this.listener.onError(this, 8, new Throwable());
                    }
                } else if (!c2.containsKey(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE) || ((Integer) c2.get(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE)).intValue() == 0) {
                    if (z) {
                        this.db.putUserId(String.valueOf(c2.get("id")));
                        this.db.put("nickname", String.valueOf(c2.get("screen_name")));
                        this.db.put("icon", String.valueOf(c2.get("avatar_hd")));
                        if (String.valueOf(c2.get("verified")).equals("true")) {
                            this.db.put("secretType", a.e);
                        } else {
                            this.db.put("secretType", "0");
                        }
                        this.db.put("secret", String.valueOf(c2.get("verified_reason")));
                        String valueOf = String.valueOf(c2.get("gender"));
                        if (valueOf.equals("m")) {
                            this.db.put("gender", "0");
                        } else if (valueOf.equals("f")) {
                            this.db.put("gender", a.e);
                        } else {
                            this.db.put("gender", "2");
                        }
                        this.db.put("snsUserUrl", "http://weibo.com/" + String.valueOf(c2.get("profile_url")));
                        this.db.put("resume", String.valueOf(c2.get("description")));
                        this.db.put("followerCount", String.valueOf(c2.get("followers_count")));
                        this.db.put("favouriteCount", String.valueOf(c2.get("friends_count")));
                        this.db.put("shareCount", String.valueOf(c2.get("statuses_count")));
                        this.db.put("snsregat", String.valueOf(ResHelper.dateToLong(String.valueOf(c2.get("created_at")))));
                    }
                    if (this.listener != null) {
                        this.listener.onComplete(this, 8, c2);
                    }
                } else if (this.listener != null) {
                    this.listener.onError(this, 8, new Throwable(new Hashon().fromHashMap(c2)));
                }
            } catch (Throwable th) {
                this.listener.onError(this, 8, th);
            }
        } else if (this.listener != null) {
            this.listener.onError(this, 8, new RuntimeException("Both weibo id and screen_name are null"));
        }
    }

    /* access modifiers changed from: protected */
    public void getFriendList(int count, int cursor, String account) {
        if (TextUtils.isEmpty(account)) {
            account = this.db.getUserId();
        }
        if (TextUtils.isEmpty(account)) {
            account = this.db.get("nickname");
        }
        if (!TextUtils.isEmpty(account)) {
            try {
                HashMap<String, Object> b2 = d.a((Platform) this).b(count, cursor, account);
                if (b2 == null) {
                    if (this.listener != null) {
                        this.listener.onError(this, 2, new Throwable());
                    }
                } else if (!b2.containsKey(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE) || ((Integer) b2.get(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE)).intValue() == 0) {
                    if (this.listener != null) {
                        this.listener.onComplete(this, 2, b2);
                    }
                } else if (this.listener != null) {
                    this.listener.onError(this, 2, new Throwable(new Hashon().fromHashMap(b2)));
                }
            } catch (Throwable th) {
                this.listener.onError(this, 2, th);
            }
        } else if (this.listener != null) {
            this.listener.onError(this, 2, new RuntimeException("Both weibo id and screen_name are null"));
        }
    }

    /* access modifiers changed from: protected */
    public void doCustomerProtocol(String url, String method, int customerAction, HashMap<String, Object> values, HashMap<String, String> filePathes) {
        try {
            HashMap<String, Object> a2 = d.a((Platform) this).a(url, method, values, filePathes);
            if (a2 == null || a2.size() <= 0) {
                if (this.listener != null) {
                    this.listener.onError(this, customerAction, new Throwable());
                }
            } else if (!a2.containsKey(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE) || ((Integer) a2.get(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE)).intValue() == 0) {
                if (this.listener != null) {
                    this.listener.onComplete(this, customerAction, a2);
                }
            } else if (this.listener != null) {
                this.listener.onError(this, customerAction, new Throwable(new Hashon().fromHashMap(a2)));
            }
        } catch (Throwable th) {
            this.listener.onError(this, customerAction, th);
        }
    }

    /* access modifiers changed from: protected */
    public void doShare(Platform.ShareParams params) {
        String str;
        HashMap<String, Object> a2;
        int stringRes;
        String text = params.getText();
        if (!TextUtils.isEmpty(text) || (stringRes = ResHelper.getStringRes(getContext(), "ssdk_weibo_upload_content")) <= 0) {
            str = text;
        } else {
            str = getContext().getString(stringRes);
        }
        d a3 = d.a((Platform) this);
        a3.a(this.a, this.b);
        String imagePath = params.getImagePath();
        String imageUrl = params.getImageUrl();
        String url = params.getUrl();
        if (!this.d || !a3.b()) {
            try {
                float latitude = params.getLatitude();
                float longitude = params.getLongitude();
                String shortLintk = getShortLintk(str, false);
                if (this.e) {
                    a2 = a3.a(shortLintk, url, imageUrl, imagePath);
                } else {
                    a2 = a3.a(shortLintk, imageUrl, imagePath, longitude, latitude);
                }
                if (a2 == null) {
                    if (this.listener != null) {
                        this.listener.onError(this, 9, new Throwable());
                    }
                } else if (!a2.containsKey(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE) || ((Integer) a2.get(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE)).intValue() == 0) {
                    a2.put("ShareParams", params);
                    if (this.listener != null) {
                        this.listener.onComplete(this, 9, a2);
                    }
                } else if (this.listener != null) {
                    this.listener.onError(this, 9, new Throwable(new Hashon().fromHashMap(a2)));
                }
            } catch (Throwable th) {
                this.listener.onError(this, 9, th);
            }
        } else {
            try {
                a3.a(params, this.listener);
            } catch (Throwable th2) {
                this.listener.onError(this, 9, th2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public f.a filterShareContent(Platform.ShareParams params, HashMap<String, Object> res) {
        f.a aVar = new f.a();
        aVar.b = params.getText();
        if (res != null) {
            aVar.a = String.valueOf(res.get("id"));
            aVar.d.add(String.valueOf(res.get("original_pic")));
            aVar.g = res;
        }
        return aVar;
    }

    /* access modifiers changed from: protected */
    public HashMap<String, Object> getFollowings(int count, int cursor, String account) {
        if (TextUtils.isEmpty(account)) {
            account = this.db.getUserId();
        }
        if (TextUtils.isEmpty(account)) {
            account = this.db.get("nickname");
        }
        if (TextUtils.isEmpty(account)) {
            return null;
        }
        try {
            HashMap<String, Object> b2 = d.a((Platform) this).b(count, cursor, account);
            if (b2 == null || b2.containsKey(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE)) {
                return null;
            }
            b2.put("current_cursor", Integer.valueOf(cursor));
            return filterFriendshipInfo(2, b2);
        } catch (Throwable th) {
            d.a().d(th);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public HashMap<String, Object> getBilaterals(int count, int page, String account) {
        if (TextUtils.isEmpty(account)) {
            account = this.db.getUserId();
        }
        if (TextUtils.isEmpty(account)) {
            account = this.db.get("nickname");
        }
        if (TextUtils.isEmpty(account)) {
            return null;
        }
        try {
            HashMap<String, Object> c2 = d.a((Platform) this).c(count, page, account);
            if (c2 == null || c2.containsKey(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE)) {
                return null;
            }
            c2.put("page_count", Integer.valueOf(count));
            c2.put("current_cursor", Integer.valueOf(page));
            return filterFriendshipInfo(10, c2);
        } catch (Throwable th) {
            d.a().d(th);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public HashMap<String, Object> getFollowers(int count, int cursor, String account) {
        if (TextUtils.isEmpty(account)) {
            account = this.db.getUserId();
        }
        if (TextUtils.isEmpty(account)) {
            account = this.db.get("nickname");
        }
        if (TextUtils.isEmpty(account)) {
            return null;
        }
        try {
            HashMap<String, Object> d2 = d.a((Platform) this).d(count, cursor, account);
            if (d2 == null || d2.containsKey(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE)) {
                return null;
            }
            d2.put("current_cursor", Integer.valueOf(cursor));
            return filterFriendshipInfo(11, d2);
        } catch (Throwable th) {
            d.a().d(th);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public HashMap<String, Object> filterFriendshipInfo(int action, HashMap<String, Object> res) {
        String str;
        HashMap<String, Object> hashMap = new HashMap<>();
        switch (action) {
            case 2:
                hashMap.put("type", "FOLLOWING");
                break;
            case 10:
                hashMap.put("type", "FRIENDS");
                break;
            case 11:
                hashMap.put("type", "FOLLOWERS");
                break;
            default:
                return null;
        }
        hashMap.put("snsplat", Integer.valueOf(getPlatformId()));
        hashMap.put("snsuid", this.db.getUserId());
        int parseInt = Integer.parseInt(String.valueOf(res.get("current_cursor")));
        int parseInt2 = Integer.parseInt(String.valueOf(res.get("total_number")));
        if (parseInt2 == 0) {
            return null;
        }
        Object obj = res.get("users");
        if (obj == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) obj;
        if (arrayList2.size() <= 0) {
            return null;
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            HashMap hashMap2 = (HashMap) it.next();
            if (hashMap2 != null) {
                HashMap hashMap3 = new HashMap();
                hashMap3.put("snsuid", String.valueOf(hashMap2.get("id")));
                hashMap3.put("nickname", String.valueOf(hashMap2.get("screen_name")));
                hashMap3.put("icon", String.valueOf(hashMap2.get("avatar_hd")));
                if (String.valueOf(hashMap2.get("verified")).equals("true")) {
                    hashMap3.put("secretType", a.e);
                } else {
                    hashMap3.put("secretType", "0");
                }
                hashMap3.put("secret", String.valueOf(hashMap2.get("verified_reason")));
                String valueOf = String.valueOf(hashMap2.get("gender"));
                if (valueOf.equals("m")) {
                    hashMap3.put("gender", "0");
                } else if (valueOf.equals("f")) {
                    hashMap3.put("gender", a.e);
                } else {
                    hashMap3.put("gender", "2");
                }
                hashMap3.put("snsUserUrl", "http://weibo.com/" + String.valueOf(hashMap2.get("profile_url")));
                hashMap3.put("resume", String.valueOf(hashMap2.get("description")));
                hashMap3.put("followerCount", String.valueOf(hashMap2.get("followers_count")));
                hashMap3.put("favouriteCount", String.valueOf(hashMap2.get("friends_count")));
                hashMap3.put("shareCount", String.valueOf(hashMap2.get("statuses_count")));
                hashMap3.put("snsregat", String.valueOf(ResHelper.dateToLong(String.valueOf(hashMap2.get("created_at")))));
                arrayList.add(hashMap3);
            }
        }
        if (arrayList == null || arrayList.size() <= 0) {
            return null;
        }
        if (10 == action) {
            hashMap.put("nextCursor", ((Integer) res.get("page_count")).intValue() * (parseInt + 1) >= parseInt2 ? parseInt + "_true" : (parseInt + 1) + "_false");
        } else {
            int size = arrayList.size() + parseInt;
            if (size >= parseInt2) {
                str = parseInt2 + "_true";
            } else {
                str = size + "_false";
            }
            hashMap.put("nextCursor", str);
        }
        hashMap.put("list", arrayList);
        return hashMap;
    }

    public boolean hasShareCallback() {
        return true;
    }
}

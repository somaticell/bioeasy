package cn.sharesdk.framework;

import android.content.Context;
import android.graphics.Bitmap;
import cn.sharesdk.framework.b.b.f;
import java.util.HashMap;

public abstract class Platform {
    public static final int ACTION_AUTHORIZING = 1;
    protected static final int ACTION_CUSTOMER = 655360;
    public static final int ACTION_FOLLOWING_USER = 6;
    protected static final int ACTION_GETTING_BILATERAL_LIST = 10;
    protected static final int ACTION_GETTING_FOLLOWER_LIST = 11;
    public static final int ACTION_GETTING_FRIEND_LIST = 2;
    public static final int ACTION_SENDING_DIRECT_MESSAGE = 5;
    public static final int ACTION_SHARE = 9;
    public static final int ACTION_TIMELINE = 7;
    public static final int ACTION_USER_INFOR = 8;
    public static final int CUSTOMER_ACTION_MASK = 65535;
    public static final int SHARE_APPS = 7;
    public static final int SHARE_EMOJI = 9;
    public static final int SHARE_FILE = 8;
    public static final int SHARE_IMAGE = 2;
    public static final int SHARE_MUSIC = 5;
    public static final int SHARE_TEXT = 1;
    public static final int SHARE_VIDEO = 6;
    public static final int SHARE_WEBPAGE = 4;
    public static final int SHARE_WXMINIPROGRAM = 11;
    public static final int SHARE_ZHIFUBAO = 10;
    private c a;
    protected final Context context;
    /* access modifiers changed from: protected */
    public final PlatformDb db = this.a.g();
    /* access modifiers changed from: protected */
    public PlatformActionListener listener = this.a.i();

    /* access modifiers changed from: protected */
    public abstract boolean checkAuthorize(int i, Object obj);

    /* access modifiers changed from: protected */
    public abstract void doAuthorize(String[] strArr);

    /* access modifiers changed from: protected */
    public abstract void doCustomerProtocol(String str, String str2, int i, HashMap<String, Object> hashMap, HashMap<String, String> hashMap2);

    /* access modifiers changed from: protected */
    public abstract void doShare(ShareParams shareParams);

    /* access modifiers changed from: protected */
    public abstract HashMap<String, Object> filterFriendshipInfo(int i, HashMap<String, Object> hashMap);

    /* access modifiers changed from: protected */
    public abstract f.a filterShareContent(ShareParams shareParams, HashMap<String, Object> hashMap);

    /* access modifiers changed from: protected */
    public abstract void follow(String str);

    /* access modifiers changed from: protected */
    public abstract HashMap<String, Object> getBilaterals(int i, int i2, String str);

    /* access modifiers changed from: protected */
    public abstract HashMap<String, Object> getFollowers(int i, int i2, String str);

    /* access modifiers changed from: protected */
    public abstract HashMap<String, Object> getFollowings(int i, int i2, String str);

    /* access modifiers changed from: protected */
    public abstract void getFriendList(int i, int i2, String str);

    public abstract String getName();

    /* access modifiers changed from: protected */
    public abstract int getPlatformId();

    public abstract int getVersion();

    public abstract boolean hasShareCallback();

    /* access modifiers changed from: protected */
    public abstract void initDevInfo(String str);

    /* access modifiers changed from: protected */
    public abstract void setNetworkDevinfo();

    /* access modifiers changed from: protected */
    public abstract void timeline(int i, int i2, String str);

    /* access modifiers changed from: protected */
    public abstract void userInfor(String str);

    public Platform(Context context2) {
        this.context = context2;
        this.a = new c(this, context2);
    }

    /* access modifiers changed from: package-private */
    public void a() {
        this.a.a(false);
        this.a.a(getName());
    }

    /* access modifiers changed from: protected */
    public void copyDevinfo(String src, String dst) {
        ShareSDK.a(src, dst);
    }

    /* access modifiers changed from: protected */
    public void copyNetworkDevinfo(int src, int dst) {
        ShareSDK.a(src, dst);
    }

    public String getDevinfo(String field) {
        return getDevinfo(getName(), field);
    }

    public String getDevinfo(String name, String field) {
        return ShareSDK.b(name, field);
    }

    /* access modifiers changed from: protected */
    public String getNetworkDevinfo(String onlineField, String offlineField) {
        return getNetworkDevinfo(getPlatformId(), onlineField, offlineField);
    }

    /* access modifiers changed from: protected */
    public String getNetworkDevinfo(int platformId, String onlineField, String offlineField) {
        return this.a.a(platformId, onlineField, offlineField);
    }

    public Context getContext() {
        return this.context;
    }

    public int getId() {
        return this.a.a();
    }

    public int getSortId() {
        return this.a.b();
    }

    public void setPlatformActionListener(PlatformActionListener l) {
        this.a.a(l);
    }

    public PlatformActionListener getPlatformActionListener() {
        return this.a.c();
    }

    public boolean isAuthValid() {
        return this.a.d();
    }

    public boolean isClientValid() {
        return false;
    }

    @Deprecated
    public boolean isValid() {
        return this.a.d();
    }

    public void SSOSetting(boolean disable) {
        this.a.a(disable);
    }

    public boolean isSSODisable() {
        return this.a.e();
    }

    /* access modifiers changed from: package-private */
    public boolean b() {
        return this.a.f();
    }

    public void authorize() {
        authorize((String[]) null);
    }

    public void authorize(String[] permissions) {
        this.a.a(permissions);
    }

    /* access modifiers changed from: protected */
    public void innerAuthorize(int action, Object extra) {
        this.a.a(action, extra);
    }

    public void share(ShareParams params) {
        this.a.a(params);
    }

    public void followFriend(String account) {
        this.a.b(account);
    }

    public void getTimeLine(String account, int count, int page) {
        this.a.a(account, count, page);
    }

    public void showUser(String account) {
        this.a.c(account);
    }

    public void listFriend(int count, int page, String account) {
        this.a.a(count, page, account);
    }

    public void customerProtocol(String url, String method, short customerAction, HashMap<String, Object> values, HashMap<String, String> filePathes) {
        this.a.a(url, method, customerAction, values, filePathes);
    }

    /* access modifiers changed from: protected */
    public void afterRegister(int action, Object extra) {
        this.a.b(action, extra);
    }

    public PlatformDb getDb() {
        return this.db;
    }

    @Deprecated
    public void removeAccount() {
        this.a.h();
    }

    public void removeAccount(boolean removeCookie) {
        this.a.h();
        ShareSDK.removeCookieOnAuthorize(removeCookie);
    }

    public String getShortLintk(String text, boolean checkHref) {
        return this.a.a(text, checkHref);
    }

    /* access modifiers changed from: protected */
    public String uploadImageToFileServer(String imagePath) {
        return this.a.d(imagePath);
    }

    /* access modifiers changed from: protected */
    public String uploadImageToFileServer(Bitmap imageData) {
        return this.a.a(imageData);
    }

    public static class ShareParams extends InnerShareParams {
        @Deprecated
        public String imagePath;
        @Deprecated
        public String text;

        public ShareParams() {
        }

        public ShareParams(HashMap<String, Object> params) {
            super(params);
        }

        public ShareParams(String jsonParams) {
            super(jsonParams);
        }
    }
}

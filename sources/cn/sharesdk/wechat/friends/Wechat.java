package cn.sharesdk.wechat.friends;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.authorize.AuthorizeListener;
import cn.sharesdk.framework.b.b.f;
import cn.sharesdk.wechat.utils.WechatClientNotExistException;
import cn.sharesdk.wechat.utils.WechatHelper;
import cn.sharesdk.wechat.utils.g;
import cn.sharesdk.wechat.utils.i;
import com.alipay.sdk.packet.d;
import com.facebook.common.util.UriUtil;
import java.util.HashMap;
import org.achartengine.ChartFactory;

public class Wechat extends Platform {
    public static final String NAME = Wechat.class.getSimpleName();
    private String a;
    private String b;
    private boolean c;
    private String d;
    private String e;

    public Wechat(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void initDevInfo(String name) {
        this.a = getDevinfo(d.f);
        this.b = getDevinfo("AppSecret");
        this.c = "true".equals(getDevinfo("BypassApproval"));
        this.d = getDevinfo("userName");
        this.e = getDevinfo("path");
        WechatHelper a2 = WechatHelper.a();
        a2.a(this.e);
        a2.b(this.d);
        if (this.a == null || this.a.length() <= 0) {
            this.a = getDevinfo("WechatMoments", d.f);
            this.c = "true".equals(getDevinfo("WechatMoments", "BypassApproval"));
            if (this.a == null || this.a.length() <= 0) {
                this.a = getDevinfo("WechatFavorite", d.f);
                if (this.a != null && this.a.length() > 0) {
                    copyDevinfo("WechatFavorite", NAME);
                    this.a = getDevinfo(d.f);
                    cn.sharesdk.framework.utils.d.a().d("Try to use the dev info of WechatFavorite, this will cause Id and SortId field are always 0.", new Object[0]);
                    return;
                }
                return;
            }
            copyDevinfo("WechatMoments", NAME);
            this.a = getDevinfo(d.f);
            this.c = "true".equals(getDevinfo("BypassApproval"));
            cn.sharesdk.framework.utils.d.a().d("Try to use the dev info of WechatMoments, this will cause Id and SortId field are always 0.", new Object[0]);
        }
    }

    public int getPlatformId() {
        return 22;
    }

    public String getName() {
        return NAME;
    }

    public int getVersion() {
        return 1;
    }

    /* access modifiers changed from: protected */
    public void setNetworkDevinfo() {
        this.a = getNetworkDevinfo("app_id", d.f);
        this.b = getNetworkDevinfo("app_secret", "AppSecret");
        if (this.a == null || this.a.length() <= 0) {
            this.a = getNetworkDevinfo(23, "app_id", d.f);
            if (this.a == null || this.a.length() <= 0) {
                this.a = getNetworkDevinfo(37, "app_id", d.f);
                if (this.a != null && this.a.length() > 0) {
                    copyNetworkDevinfo(37, 22);
                    this.a = getNetworkDevinfo("app_id", d.f);
                    cn.sharesdk.framework.utils.d.a().d("Try to use the dev info of WechatFavorite, this will cause Id and SortId field are always 0.", new Object[0]);
                }
            } else {
                copyNetworkDevinfo(23, 22);
                this.a = getNetworkDevinfo("app_id", d.f);
                cn.sharesdk.framework.utils.d.a().d("Try to use the dev info of WechatMoments, this will cause Id and SortId field are always 0.", new Object[0]);
            }
        }
        if (this.b == null || this.b.length() <= 0) {
            this.b = getNetworkDevinfo(23, "app_secret", "AppSecret");
            if (this.b == null || this.b.length() <= 0) {
                this.b = getNetworkDevinfo(37, "app_secret", "AppSecret");
                if (this.b != null && this.b.length() > 0) {
                    copyNetworkDevinfo(37, 22);
                    this.b = getNetworkDevinfo("app_secret", "AppSecret");
                    cn.sharesdk.framework.utils.d.a().d("Try to use the dev info of WechatFavorite, this will cause Id and SortId field are always 0.", new Object[0]);
                    return;
                }
                return;
            }
            copyNetworkDevinfo(23, 22);
            this.b = getNetworkDevinfo("app_secret", "AppSecret");
            cn.sharesdk.framework.utils.d.a().d("Try to use the dev info of WechatMoments, this will cause Id and SortId field are always 0.", new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    public void doAuthorize(String[] permission) {
        if (!TextUtils.isEmpty(this.a) && !TextUtils.isEmpty(this.b)) {
            WechatHelper a2 = WechatHelper.a();
            a2.a(this.context, this.a);
            if (a2.c()) {
                g gVar = new g(this, 22);
                gVar.a(this.a, this.b);
                i iVar = new i(this);
                iVar.a(gVar);
                iVar.a((AuthorizeListener) new AuthorizeListener() {
                    public void onError(Throwable e) {
                        if (Wechat.this.listener != null) {
                            Wechat.this.listener.onError(Wechat.this, 1, e);
                        }
                    }

                    public void onComplete(Bundle values) {
                        Wechat.this.afterRegister(1, (Object) null);
                    }

                    public void onCancel() {
                        if (Wechat.this.listener != null) {
                            Wechat.this.listener.onCancel(Wechat.this, 1);
                        }
                    }
                });
                try {
                    a2.a(iVar);
                } catch (Throwable th) {
                    if (this.listener != null) {
                        this.listener.onError(this, 1, th);
                    }
                }
            } else if (this.listener != null) {
                this.listener.onError(this, 1, new WechatClientNotExistException());
            }
        } else if (this.listener != null) {
            this.listener.onError(this, 8, new Throwable("The params of appID or appSecret is missing !"));
        }
    }

    @Deprecated
    public boolean isValid() {
        WechatHelper a2 = WechatHelper.a();
        a2.a(this.context, this.a);
        if (!a2.c() || !super.isValid()) {
            return false;
        }
        return true;
    }

    public boolean isClientValid() {
        WechatHelper a2 = WechatHelper.a();
        a2.a(this.context, this.a);
        if (a2.c()) {
            return true;
        }
        return false;
    }

    private boolean c() {
        if (TextUtils.isEmpty(getDb().get("refresh_token"))) {
            return false;
        }
        g gVar = new g(this, 22);
        gVar.a(this.a, this.b);
        return gVar.a();
    }

    /* access modifiers changed from: protected */
    public boolean checkAuthorize(int action, Object extra) {
        if (!isClientValid()) {
            if (this.listener == null) {
                return false;
            }
            this.listener.onError(this, action, new WechatClientNotExistException());
            return false;
        } else if (action == 9 || isAuthValid() || c()) {
            return true;
        } else {
            innerAuthorize(action, extra);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void doShare(Platform.ShareParams params) {
        params.set("scene", 0);
        WechatHelper a2 = WechatHelper.a();
        a2.a(this.context, this.a);
        i iVar = new i(this);
        if (this.c) {
            try {
                a2.a(iVar, params, this.listener);
            } catch (Throwable th) {
                if (this.listener != null) {
                    this.listener.onError(this, 9, th);
                }
            }
        } else {
            iVar.a(params, this.listener);
            try {
                a2.b(iVar);
            } catch (Throwable th2) {
                if (this.listener != null) {
                    this.listener.onError(this, 9, th2);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void follow(String account) {
        if (this.listener != null) {
            this.listener.onCancel(this, 6);
        }
    }

    /* access modifiers changed from: protected */
    public void timeline(int count, int page, String account) {
        if (this.listener != null) {
            this.listener.onCancel(this, 7);
        }
    }

    /* access modifiers changed from: protected */
    public void userInfor(String account) {
        if (!TextUtils.isEmpty(this.a) && !TextUtils.isEmpty(this.b)) {
            g gVar = new g(this, 22);
            gVar.a(this.a, this.b);
            try {
                gVar.a(this.listener);
            } catch (Throwable th) {
                cn.sharesdk.framework.utils.d.a().d(th);
                if (this.listener != null) {
                    this.listener.onError(this, 8, th);
                }
            }
        } else if (this.listener != null) {
            this.listener.onError(this, 8, new Throwable("The params of appID or appSecret is missing !"));
        }
    }

    /* access modifiers changed from: protected */
    public void getFriendList(int count, int page, String account) {
        if (this.listener != null) {
            this.listener.onCancel(this, 2);
        }
    }

    /* access modifiers changed from: protected */
    public void doCustomerProtocol(String url, String method, int customerAction, HashMap<String, Object> hashMap, HashMap<String, String> hashMap2) {
        if (this.listener != null) {
            this.listener.onCancel(this, customerAction);
        }
    }

    /* access modifiers changed from: protected */
    public f.a filterShareContent(Platform.ShareParams params, HashMap<String, Object> hashMap) {
        f.a aVar = new f.a();
        String text = params.getText();
        aVar.b = text;
        String imageUrl = params.getImageUrl();
        String imagePath = params.getImagePath();
        Bitmap imageData = params.getImageData();
        if (!TextUtils.isEmpty(imageUrl)) {
            aVar.d.add(imageUrl);
        } else if (imagePath != null) {
            aVar.e.add(imagePath);
        } else if (imageData != null) {
            aVar.f.add(imageData);
        }
        String url = params.getUrl();
        if (url != null) {
            aVar.c.add(url);
        }
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put(ChartFactory.TITLE, params.getTitle());
        hashMap2.put("url", url);
        hashMap2.put("extInfo", (Object) null);
        hashMap2.put(UriUtil.LOCAL_CONTENT_SCHEME, text);
        hashMap2.put("image", aVar.d);
        hashMap2.put("musicFileUrl", url);
        aVar.g = hashMap2;
        return aVar;
    }

    /* access modifiers changed from: protected */
    public HashMap<String, Object> getFollowings(int count, int page, String account) {
        return null;
    }

    /* access modifiers changed from: protected */
    public HashMap<String, Object> getFollowers(int count, int cursor, String account) {
        return null;
    }

    /* access modifiers changed from: protected */
    public HashMap<String, Object> getBilaterals(int count, int cursor, String account) {
        return null;
    }

    /* access modifiers changed from: protected */
    public HashMap<String, Object> filterFriendshipInfo(int action, HashMap<String, Object> hashMap) {
        return null;
    }

    public static class ShareParams extends WechatHelper.ShareParams {
        public ShareParams() {
            this.scene = 0;
        }
    }

    public boolean hasShareCallback() {
        return !this.c;
    }
}

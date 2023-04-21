package cn.sharesdk.tencent.qq;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.authorize.AuthorizeListener;
import cn.sharesdk.framework.b.b.f;
import com.alipay.sdk.cons.a;
import com.alipay.sdk.packet.d;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ResHelper;
import java.util.HashMap;
import org.achartengine.ChartFactory;

public class QQ extends Platform {
    public static final String NAME = QQ.class.getSimpleName();
    private String a;
    private boolean b;
    private boolean c = true;

    public static class ShareParams extends Platform.ShareParams {
        @Deprecated
        public String imageUrl;
        @Deprecated
        public String musicUrl;
        @Deprecated
        public String title;
        @Deprecated
        public String titleUrl;
    }

    public QQ(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void initDevInfo(String name) {
        this.a = getDevinfo(d.f);
        this.b = "true".equals(getDevinfo("ShareByAppClient"));
        if (this.a == null || this.a.length() <= 0) {
            this.a = getDevinfo("QZone", d.f);
            if (this.a != null && this.a.length() > 0) {
                copyDevinfo("QZone", NAME);
                this.a = getDevinfo(d.f);
                this.b = "true".equals(getDevinfo("ShareByAppClient"));
                cn.sharesdk.framework.utils.d.a().d("Try to use the dev info of QZone, this will cause Id and SortId field are always 0.", new Object[0]);
            }
        }
    }

    public int getPlatformId() {
        return 24;
    }

    public String getName() {
        return NAME;
    }

    public int getVersion() {
        return 2;
    }

    /* access modifiers changed from: protected */
    public void setNetworkDevinfo() {
        this.a = getNetworkDevinfo("app_id", d.f);
        if (this.a == null || this.a.length() <= 0) {
            this.a = getNetworkDevinfo(6, "app_id", d.f);
            if (this.a != null && this.a.length() > 0) {
                copyNetworkDevinfo(6, 24);
                this.a = getNetworkDevinfo("app_id", d.f);
                cn.sharesdk.framework.utils.d.a().d("Try to use the dev info of QZone, this will cause Id and SortId field are always 0.", new Object[0]);
            }
        }
    }

    public boolean isClientValid() {
        b a2 = b.a((Platform) this);
        a2.a(this.a);
        return a2.a();
    }

    /* access modifiers changed from: protected */
    public void doAuthorize(String[] permissions) {
        final b a2 = b.a((Platform) this);
        a2.a(this.a);
        a2.a(permissions);
        a2.a((AuthorizeListener) new AuthorizeListener() {
            public void onError(Throwable e) {
                if (QQ.this.listener != null) {
                    QQ.this.listener.onError(QQ.this, 1, e);
                }
            }

            public void onComplete(Bundle values) {
                String string = values.getString("open_id");
                String string2 = values.getString("access_token");
                String string3 = values.getString("expires_in");
                QQ.this.db.putToken(string2);
                QQ.this.db.putTokenSecret("");
                try {
                    QQ.this.db.putExpiresIn(ResHelper.parseLong(string3));
                } catch (Throwable th) {
                    cn.sharesdk.framework.utils.d.a().d(th);
                }
                QQ.this.db.putUserId(string);
                String string4 = values.getString("pf");
                String string5 = values.getString("pfkey");
                String string6 = values.getString("pay_token");
                QQ.this.db.put("pf", string4);
                QQ.this.db.put("pfkey", string5);
                QQ.this.db.put("pay_token", string6);
                a2.b(string);
                a2.d(string2);
                QQ.this.afterRegister(1, (Object) null);
            }

            public void onCancel() {
                if (QQ.this.listener != null) {
                    QQ.this.listener.onCancel(QQ.this, 1);
                }
            }
        }, isSSODisable());
    }

    /* access modifiers changed from: protected */
    public boolean checkAuthorize(int action, Object extra) {
        if (isAuthValid() || (action == 9 && extra != null && (extra instanceof Platform.ShareParams) && !((Platform.ShareParams) extra).isShareTencentWeibo())) {
            b a2 = b.a((Platform) this);
            a2.a(this.a);
            a2.b(this.db.getUserId());
            a2.d(this.db.getToken());
            return true;
        }
        innerAuthorize(action, extra);
        return false;
    }

    /* access modifiers changed from: protected */
    public void doShare(final Platform.ShareParams params) {
        String title = params.getTitle();
        String text = params.getText();
        String imagePath = params.getImagePath();
        String imageUrl = params.getImageUrl();
        String musicUrl = params.getMusicUrl();
        String titleUrl = params.getTitleUrl();
        boolean isShareTencentWeibo = params.isShareTencentWeibo();
        int hidden = params.getHidden();
        if (!TextUtils.isEmpty(title) || !TextUtils.isEmpty(text) || !TextUtils.isEmpty(imagePath) || !TextUtils.isEmpty(imageUrl) || !TextUtils.isEmpty(musicUrl)) {
            if (!TextUtils.isEmpty(titleUrl)) {
                titleUrl = getShortLintk(titleUrl, false);
                params.setTitleUrl(titleUrl);
            }
            if (!TextUtils.isEmpty(text)) {
                text = getShortLintk(text, false);
                params.setText(text);
            }
            b.a((Platform) this).a(title, titleUrl, text, imagePath, imageUrl, musicUrl, this.b, new PlatformActionListener() {
                public void onError(Platform platform, int action, Throwable t) {
                    if (QQ.this.listener != null) {
                        QQ.this.listener.onError(QQ.this, 9, t);
                    }
                }

                public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
                    HashMap hashMap = new HashMap();
                    if (res != null) {
                        hashMap.putAll(res);
                    }
                    hashMap.put("ShareParams", params);
                    if (QQ.this.listener != null) {
                        QQ.this.listener.onComplete(QQ.this, 9, hashMap);
                    }
                }

                public void onCancel(Platform platform, int action) {
                    if (QQ.this.listener != null) {
                        QQ.this.listener.onCancel(QQ.this, 9);
                    }
                }
            }, isShareTencentWeibo, hidden);
        } else if (this.listener != null) {
            this.listener.onError(this, 9, new Throwable("qq share must have one param at least"));
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
        if (account == null || account.length() < 0) {
            account = this.db.getUserId();
        }
        if (account != null && account.length() >= 0) {
            try {
                HashMap<String, Object> e = b.a((Platform) this).e(account);
                if (e == null || e.size() <= 0) {
                    if (this.listener != null) {
                        this.listener.onError(this, 8, new Throwable());
                    }
                } else if (!e.containsKey("ret")) {
                    if (this.listener != null) {
                        this.listener.onError(this, 8, new Throwable());
                    }
                } else if (((Integer) e.get("ret")).intValue() == 0) {
                    if (account == this.db.getUserId()) {
                        this.db.put("nickname", String.valueOf(e.get("nickname")));
                        if (e.containsKey("figureurl_qq_1")) {
                            this.db.put("icon", String.valueOf(e.get("figureurl_qq_1")));
                        } else if (e.containsKey("figureurl_qq_2")) {
                            this.db.put("icon", String.valueOf(e.get("figureurl_qq_2")));
                        }
                        if (e.containsKey("figureurl_2")) {
                            this.db.put("iconQzone", String.valueOf(e.get("figureurl_2")));
                        } else if (e.containsKey("figureurl_1")) {
                            this.db.put("iconQzone", String.valueOf(e.get("figureurl_1")));
                        } else if (e.containsKey("figureurl")) {
                            this.db.put("iconQzone", String.valueOf(e.get("figureurl")));
                        }
                        this.db.put("secretType", String.valueOf(e.get("is_yellow_vip")));
                        if (String.valueOf(e.get("is_yellow_vip")).equals(a.e)) {
                            this.db.put("snsUserLevel", String.valueOf(e.get("level")));
                        }
                        String valueOf = String.valueOf(e.get("gender"));
                        if (valueOf.equals(this.context.getString(ResHelper.getStringRes(this.context, "ssdk_gender_male")))) {
                            this.db.put("gender", "0");
                        } else if (valueOf.equals(this.context.getString(ResHelper.getStringRes(this.context, "ssdk_gender_female")))) {
                            this.db.put("gender", a.e);
                        } else {
                            this.db.put("gender", "2");
                        }
                    }
                    if (this.listener != null) {
                        this.listener.onComplete(this, 8, e);
                    }
                } else if (this.listener != null) {
                    this.listener.onError(this, 8, new Throwable(new Hashon().fromHashMap(e)));
                }
            } catch (Throwable th) {
                if (this.listener != null) {
                    this.listener.onError(this, 8, th);
                }
            }
        } else if (this.listener != null) {
            this.listener.onError(this, 8, new RuntimeException("qq account is null"));
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
        String titleUrl = params.getTitleUrl();
        aVar.c.add(titleUrl);
        aVar.a = this.a;
        String text = params.getText();
        if (!TextUtils.isEmpty(text)) {
            aVar.b = text;
        }
        String imageUrl = params.getImageUrl();
        String imagePath = params.getImagePath();
        if (!TextUtils.isEmpty(imagePath)) {
            aVar.e.add(imagePath);
        } else if (!TextUtils.isEmpty(imageUrl)) {
            aVar.d.add(imageUrl);
        }
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put(ChartFactory.TITLE, params.getTitle());
        hashMap2.put("url", titleUrl);
        hashMap2.put("imageLocalUrl", imagePath);
        hashMap2.put("summary", text);
        hashMap2.put("appName", DeviceHelper.getInstance(this.context).getAppName());
        aVar.g = hashMap2;
        return aVar;
    }

    /* access modifiers changed from: protected */
    public String uploadImageToFileServer(String imagePath) {
        return super.uploadImageToFileServer(imagePath);
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

    public boolean hasShareCallback() {
        return this.c;
    }
}

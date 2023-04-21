package cn.sharesdk.tencent.qzone;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.authorize.AuthorizeListener;
import cn.sharesdk.framework.b.b.f;
import com.alipay.sdk.cons.a;
import com.alipay.sdk.packet.d;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ResHelper;
import java.io.File;
import java.util.HashMap;
import org.achartengine.ChartFactory;

public class QZone extends Platform {
    public static final String NAME = QZone.class.getSimpleName();
    private String a;

    public static class ShareParams extends Platform.ShareParams {
        @Deprecated
        public String comment;
        @Deprecated
        public String imageUrl;
        @Deprecated
        public String site;
        @Deprecated
        public String siteUrl;
        @Deprecated
        public String title;
        @Deprecated
        public String titleUrl;
        @Deprecated
        boolean webShare;
    }

    public QZone(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void initDevInfo(String name) {
        this.a = getDevinfo(d.f);
        if (this.a == null || this.a.length() <= 0) {
            this.a = getDevinfo("QQ", d.f);
            if (this.a != null && this.a.length() > 0) {
                copyDevinfo("QQ", NAME);
                this.a = getDevinfo(d.f);
                cn.sharesdk.framework.utils.d.a().d("Try to use the dev info of QQ, this will cause Id and SortId field are always 0.", new Object[0]);
            }
        }
    }

    public int getPlatformId() {
        return 6;
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
            this.a = getNetworkDevinfo(24, "app_id", d.f);
            if (this.a != null && this.a.length() > 0) {
                copyNetworkDevinfo(24, 6);
                this.a = getNetworkDevinfo("app_id", d.f);
                cn.sharesdk.framework.utils.d.a().d("Try to use the dev info of QQ, this will cause Id and SortId field are always 0.", new Object[0]);
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
                if (QZone.this.listener != null) {
                    QZone.this.listener.onError(QZone.this, 1, e);
                }
            }

            public void onComplete(Bundle values) {
                String string = values.getString("open_id");
                String string2 = values.getString("access_token");
                String string3 = values.getString("expires_in");
                QZone.this.db.putToken(string2);
                QZone.this.db.putTokenSecret("");
                try {
                    QZone.this.db.putExpiresIn(ResHelper.parseLong(string3));
                } catch (Throwable th) {
                    cn.sharesdk.framework.utils.d.a().d(th);
                }
                QZone.this.db.putUserId(string);
                String string4 = values.getString("pf");
                String string5 = values.getString("pfkey");
                String string6 = values.getString("pay_token");
                QZone.this.db.put("pf", string4);
                QZone.this.db.put("pfkey", string5);
                QZone.this.db.put("pay_token", string6);
                a2.b(string);
                a2.c(string2);
                QZone.this.afterRegister(1, (Object) null);
            }

            public void onCancel() {
                if (QZone.this.listener != null) {
                    QZone.this.listener.onCancel(QZone.this, 1);
                }
            }
        }, isSSODisable());
    }

    /* access modifiers changed from: protected */
    public boolean checkAuthorize(int action, Object extra) {
        if (isAuthValid() || action == 9) {
            b a2 = b.a((Platform) this);
            a2.a(this.a);
            a2.b(this.db.getUserId());
            a2.c(this.db.getToken());
            return true;
        }
        innerAuthorize(action, extra);
        return false;
    }

    /* access modifiers changed from: protected */
    public void doShare(Platform.ShareParams params) {
        if (params.isShareTencentWeibo()) {
            a(params);
        } else {
            b(params);
        }
    }

    private void a(final Platform.ShareParams shareParams) {
        HashMap<String, Object> a2;
        String imageUrl = shareParams.getImageUrl();
        String imagePath = shareParams.getImagePath();
        boolean isShareTencentWeibo = shareParams.isShareTencentWeibo();
        try {
            if (TextUtils.isEmpty(imagePath) && !TextUtils.isEmpty(imageUrl)) {
                shareParams.setImagePath(BitmapHelper.downloadBitmap(this.context, imageUrl));
                doShare(shareParams);
            } else if (!isAuthValid()) {
                final PlatformActionListener platformActionListener = getPlatformActionListener();
                setPlatformActionListener(new PlatformActionListener() {
                    public void onError(Platform platform, int action, Throwable t) {
                        if (platformActionListener != null) {
                            platformActionListener.onError(platform, 9, t);
                        }
                    }

                    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
                        QZone.this.setPlatformActionListener(platformActionListener);
                        QZone.this.doShare(shareParams);
                    }

                    public void onCancel(Platform platform, int action) {
                        if (platformActionListener != null) {
                            platformActionListener.onCancel(platform, 9);
                        }
                    }
                });
                authorize();
            } else {
                String text = shareParams.getText();
                if (!TextUtils.isEmpty(text)) {
                    String shortLintk = getShortLintk(text, false);
                    shareParams.setText(shortLintk);
                    b a3 = b.a((Platform) this);
                    if (isShareTencentWeibo) {
                        a2 = a3.b(imagePath, shortLintk);
                    } else {
                        a2 = a3.a(imagePath, shortLintk);
                    }
                    if (a2 == null && this.listener != null) {
                        this.listener.onError(this, 9, new Throwable("response is empty"));
                    }
                    a2.put("ShareParams", shareParams);
                    if (this.listener != null) {
                        this.listener.onComplete(this, 9, a2);
                    }
                } else if (this.listener != null) {
                    this.listener.onError(this, 9, new Throwable("share params' value of text is empty!"));
                }
            }
        } catch (Throwable th) {
            if (this.listener != null) {
                this.listener.onError(this, 9, th);
            }
        }
    }

    private void b(final Platform.ShareParams shareParams) {
        try {
            String imageUrl = shareParams.getImageUrl();
            String imagePath = shareParams.getImagePath();
            if (isClientValid()) {
                if (TextUtils.isEmpty(imagePath) || !new File(imagePath).exists()) {
                    imagePath = imageUrl;
                }
                String title = shareParams.getTitle();
                String titleUrl = shareParams.getTitleUrl();
                String site = shareParams.getSite();
                String text = shareParams.getText();
                String filePath = shareParams.getFilePath();
                int shareType = shareParams.getShareType();
                if (!TextUtils.isEmpty(text)) {
                    text = getShortLintk(text, false);
                    shareParams.setText(text);
                }
                if (!TextUtils.isEmpty(titleUrl)) {
                    titleUrl = getShortLintk(titleUrl, false);
                    shareParams.setTitleUrl(titleUrl);
                }
                b.a((Platform) this).a(shareType, title, titleUrl, text, imagePath, site, filePath, new PlatformActionListener() {
                    public void onError(Platform platform, int action, Throwable t) {
                        if (QZone.this.listener != null) {
                            QZone.this.listener.onError(QZone.this, 9, t);
                        }
                    }

                    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
                        if (QZone.this.listener != null) {
                            res.put("ShareParams", shareParams);
                            QZone.this.listener.onComplete(QZone.this, 9, res);
                        }
                    }

                    public void onCancel(Platform platform, int action) {
                        if (QZone.this.listener != null) {
                            QZone.this.listener.onCancel(QZone.this, 9);
                        }
                    }
                });
            } else if (this.listener != null) {
                this.listener.onError(this, 9, new QQClientNotExistException());
            }
        } catch (Throwable th) {
            if (this.listener != null) {
                this.listener.onError(this, 9, th);
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
        if (account == null || account.length() < 0) {
            account = this.db.getUserId();
        }
        if (account != null && account.length() >= 0) {
            try {
                HashMap<String, Object> d = b.a((Platform) this).d(account);
                if (d == null || d.size() <= 0) {
                    if (this.listener != null) {
                        this.listener.onError(this, 8, new Throwable());
                    }
                } else if (!d.containsKey("ret")) {
                    if (this.listener != null) {
                        this.listener.onError(this, 8, new Throwable());
                    }
                } else if (((Integer) d.get("ret")).intValue() == 0) {
                    if (account == this.db.getUserId()) {
                        this.db.put("nickname", String.valueOf(d.get("nickname")));
                        if (d.containsKey("figureurl_qq_1")) {
                            this.db.put("iconQQ", String.valueOf(d.get("figureurl_qq_1")));
                        } else if (d.containsKey("figureurl_qq_2")) {
                            this.db.put("iconQQ", String.valueOf(d.get("figureurl_qq_2")));
                        }
                        if (d.containsKey("figureurl_2")) {
                            this.db.put("icon", String.valueOf(d.get("figureurl_2")));
                        } else if (d.containsKey("figureurl_1")) {
                            this.db.put("icon", String.valueOf(d.get("figureurl_1")));
                        } else if (d.containsKey("figureurl")) {
                            this.db.put("icon", String.valueOf(d.get("figureurl")));
                        }
                        this.db.put("secretType", String.valueOf(d.get("is_yellow_vip")));
                        if (String.valueOf(d.get("is_yellow_vip")).equals(a.e)) {
                            this.db.put("snsUserLevel", String.valueOf(d.get("level")));
                        }
                        String valueOf = String.valueOf(d.get("gender"));
                        if (valueOf.equals(this.context.getString(ResHelper.getStringRes(this.context, "ssdk_gender_male")))) {
                            this.db.put("gender", "0");
                        } else if (valueOf.equals(this.context.getString(ResHelper.getStringRes(this.context, "ssdk_gender_female")))) {
                            this.db.put("gender", a.e);
                        } else {
                            this.db.put("gender", "2");
                        }
                    }
                    if (this.listener != null) {
                        this.listener.onComplete(this, 8, d);
                    }
                } else if (this.listener != null) {
                    this.listener.onError(this, 8, new Throwable(new Hashon().fromHashMap(d)));
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
    public void doCustomerProtocol(String url, String method, int customerAction, HashMap<String, Object> values, HashMap<String, String> filePathes) {
        HashMap<String, Object> a2 = b.a((Platform) this).a(url, method, values, filePathes);
        if (a2 == null || a2.size() <= 0) {
            if (this.listener != null) {
                this.listener.onError(this, customerAction, new Throwable());
            }
        } else if (!a2.containsKey("ret")) {
            if (this.listener != null) {
                this.listener.onError(this, customerAction, new Throwable());
            }
        } else if (((Integer) a2.get("ret")).intValue() != 0) {
            if (this.listener != null) {
                this.listener.onError(this, customerAction, new Throwable(new Hashon().fromHashMap(a2)));
            }
        } else if (this.listener != null) {
            this.listener.onComplete(this, customerAction, a2);
        }
    }

    /* access modifiers changed from: protected */
    public f.a filterShareContent(Platform.ShareParams params, HashMap<String, Object> res) {
        f.a aVar = new f.a();
        aVar.b = params.getText();
        String imageUrl = params.getImageUrl();
        String imagePath = params.getImagePath();
        if (imagePath != null) {
            aVar.e.add(imagePath);
        } else if (res.get("large_url") != null) {
            aVar.d.add(String.valueOf(res.get("large_url")));
        } else if (res.get("small_url") != null) {
            aVar.d.add(String.valueOf(res.get("small_url")));
        } else if (imageUrl != null) {
            aVar.d.add(imageUrl);
        }
        String titleUrl = params.getTitleUrl();
        if (titleUrl != null) {
            aVar.c.add(titleUrl);
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(ChartFactory.TITLE, params.getTitle());
        hashMap.put("titleUrl", params.getTitleUrl());
        hashMap.put("site", params.getSite());
        aVar.g = hashMap;
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

    public boolean hasShareCallback() {
        return true;
    }
}

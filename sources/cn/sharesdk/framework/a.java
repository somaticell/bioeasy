package cn.sharesdk.framework;

import android.text.TextUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.b.b.b;
import cn.sharesdk.framework.b.b.c;
import cn.sharesdk.framework.b.b.f;
import cn.sharesdk.framework.b.d;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.Hashon;
import java.util.HashMap;

/* compiled from: InnerPlatformActionListener */
public class a implements PlatformActionListener {
    /* access modifiers changed from: private */
    public PlatformActionListener a;
    private HashMap<Platform, Platform.ShareParams> b = new HashMap<>();

    a() {
    }

    /* access modifiers changed from: package-private */
    public void a(PlatformActionListener platformActionListener) {
        this.a = platformActionListener;
    }

    /* access modifiers changed from: package-private */
    public PlatformActionListener a() {
        return this.a;
    }

    public void a(Platform platform, Platform.ShareParams shareParams) {
        this.b.put(platform, shareParams);
    }

    public void onError(Platform platform, int action, Throwable t) {
        if (this.a != null) {
            this.a.onError(platform, action, t);
            this.a = null;
        }
    }

    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        if (!(platform instanceof CustomPlatform)) {
            switch (action) {
                case 1:
                    a(platform, action, res);
                    return;
                case 9:
                    b(platform, action, res);
                    return;
                default:
                    if (this.a != null) {
                        this.a.onComplete(platform, action, res);
                        if (!"Wechat".equals(platform.getName())) {
                            this.a = null;
                            return;
                        }
                        return;
                    }
                    return;
            }
        } else if (this.a != null) {
            this.a.onComplete(platform, action, res);
            this.a = null;
        }
    }

    public void onCancel(Platform platform, int action) {
        if (this.a != null) {
            this.a.onCancel(platform, action);
            this.a = null;
        }
    }

    private void a(Platform platform, final int i, final HashMap<String, Object> hashMap) {
        final PlatformActionListener platformActionListener = this.a;
        this.a = new PlatformActionListener() {
            public void onComplete(Platform platform, int actionInner, HashMap<String, Object> resInner) {
                PlatformActionListener unused = a.this.a = platformActionListener;
                if (a.this.a != null) {
                    a.this.a.onComplete(platform, i, hashMap);
                    if (!"Wechat".equals(platform.getName())) {
                        PlatformActionListener unused2 = a.this.a = null;
                    }
                }
                b bVar = new b();
                bVar.a = platform.getPlatformId();
                bVar.b = "TencentWeibo".equals(platform.getName()) ? platform.getDb().get("name") : platform.getDb().getUserId();
                bVar.c = new Hashon().fromHashMap(resInner);
                bVar.d = a.this.a(platform);
                d a2 = d.a(platform.getContext(), bVar.g);
                if (a2 != null) {
                    a2.a((c) bVar);
                }
                a.this.a(1, platform);
            }

            public void onError(Platform platform, int actionInner, Throwable t) {
                cn.sharesdk.framework.utils.d.a().w(t);
                PlatformActionListener unused = a.this.a = platformActionListener;
                if (a.this.a != null) {
                    a.this.a.onComplete(platform, i, hashMap);
                    PlatformActionListener unused2 = a.this.a = null;
                }
            }

            public void onCancel(Platform platform, int actionInner) {
                PlatformActionListener unused = a.this.a = platformActionListener;
                if (a.this.a != null) {
                    a.this.a.onComplete(platform, i, hashMap);
                    PlatformActionListener unused2 = a.this.a = null;
                }
            }
        };
        platform.showUser((String) null);
    }

    private void b(Platform platform, int i, HashMap<String, Object> hashMap) {
        Platform.ShareParams shareParams;
        HashMap<String, Object> hashMap2;
        Platform.ShareParams remove = this.b.remove(platform);
        if (hashMap != null) {
            shareParams = (Platform.ShareParams) hashMap.remove("ShareParams");
        } else {
            shareParams = remove;
        }
        try {
            hashMap2 = (HashMap) hashMap.clone();
        } catch (Throwable th) {
            cn.sharesdk.framework.utils.d.a().d(th);
            hashMap2 = hashMap;
        }
        if (shareParams != null) {
            f fVar = new f();
            fVar.o = shareParams.getCustomFlag();
            String userId = platform.getDb().getUserId();
            if (("WechatMoments".equals(platform.getName()) || "WechatFavorite".equals(platform.getName())) && TextUtils.isEmpty(userId)) {
                Platform platform2 = ShareSDK.getPlatform("Wechat");
                if (platform2 != null) {
                    userId = platform2.getDb().getUserId();
                }
            } else if ("TencentWeibo".equals(platform.getName())) {
                userId = platform.getDb().get("name");
            }
            fVar.b = userId;
            fVar.a = platform.getPlatformId();
            f.a filterShareContent = platform.filterShareContent(shareParams, hashMap2);
            if (filterShareContent != null) {
                fVar.c = filterShareContent.a;
                fVar.d = filterShareContent;
            }
            fVar.n = b(platform);
            d a2 = d.a(platform.getContext(), fVar.g);
            if (a2 != null) {
                a2.a((c) fVar);
            }
        }
        if (this.a != null) {
            try {
                this.a.onComplete(platform, i, hashMap);
                this.a = null;
            } catch (Throwable th2) {
                cn.sharesdk.framework.utils.d.a().d(th2);
            }
        }
        a(9, platform);
    }

    /* access modifiers changed from: private */
    public void a(int i, Platform platform) {
    }

    /* access modifiers changed from: package-private */
    public void a(Platform platform, final int i, final Object obj) {
        final PlatformActionListener platformActionListener = this.a;
        this.a = new PlatformActionListener() {
            public void onError(Platform platform, int action, Throwable t) {
                PlatformActionListener unused = a.this.a = platformActionListener;
                if (a.this.a != null) {
                    a.this.a.onError(platform, action, t);
                    PlatformActionListener unused2 = a.this.a = null;
                }
            }

            public void onComplete(Platform platform, int actionInner, HashMap<String, Object> hashMap) {
                PlatformActionListener unused = a.this.a = platformActionListener;
                platform.afterRegister(i, obj);
            }

            public void onCancel(Platform platform, int actionInner) {
                PlatformActionListener unused = a.this.a = platformActionListener;
                if (a.this.a != null) {
                    a.this.a.onCancel(platform, i);
                    PlatformActionListener unused2 = a.this.a = null;
                }
            }
        };
        platform.doAuthorize((String[]) null);
    }

    /* access modifiers changed from: private */
    public String a(Platform platform) {
        try {
            return a(platform.getDb(), new String[]{"nickname", "icon", "gender", "snsUserUrl", "resume", "secretType", "secret", "birthday", "followerCount", "favouriteCount", "shareCount", "snsregat", "snsUserLevel", "educationJSONArrayStr", "workJSONArrayStr"});
        } catch (Throwable th) {
            cn.sharesdk.framework.utils.d.a().w(th);
            return null;
        }
    }

    private String b(Platform platform) {
        Platform platform2;
        PlatformDb db = platform.getDb();
        if (("WechatMoments".equals(platform.getName()) || "WechatFavorite".equals(platform.getName())) && TextUtils.isEmpty(db.getUserGender()) && (platform2 = ShareSDK.getPlatform("Wechat")) != null) {
            db = platform2.getDb();
        }
        try {
            return a(db, new String[]{"gender", "birthday", "secretType", "educationJSONArrayStr", "workJSONArrayStr"});
        } catch (Throwable th) {
            cn.sharesdk.framework.utils.d.a().w(th);
            return null;
        }
    }

    private String a(PlatformDb platformDb, String[] strArr) throws Throwable {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        int i = 0;
        for (String str : strArr) {
            if (i > 0) {
                sb2.append('|');
                sb.append('|');
            }
            i++;
            String str2 = platformDb.get(str);
            if (!TextUtils.isEmpty(str2)) {
                sb.append(str2);
                sb2.append(Data.urlEncode(str2, "utf-8"));
            }
        }
        cn.sharesdk.framework.utils.d.a().i("======UserData: " + sb.toString(), new Object[0]);
        return sb2.toString();
    }
}

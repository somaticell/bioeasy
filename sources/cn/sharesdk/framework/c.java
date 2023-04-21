package cn.sharesdk.framework;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.utils.d;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ResHelper;
import java.lang.reflect.Field;
import java.util.HashMap;

/* compiled from: PlatformImpl */
public class c {
    /* access modifiers changed from: private */
    public Platform a;
    private Context b;
    private PlatformDb c;
    private a d;
    private int e;
    private int f;
    private boolean g;
    private boolean h = true;
    private boolean i;

    public c(Platform platform, Context context) {
        this.a = platform;
        this.b = context;
        String name = platform.getName();
        this.c = new PlatformDb(context, name, platform.getVersion());
        a(name);
        this.d = new a();
    }

    public void a(String str) {
        try {
            this.e = ResHelper.parseInt(String.valueOf(ShareSDK.b(str, "Id")).trim());
        } catch (Throwable th) {
            if (!(this.a instanceof CustomPlatform)) {
                d.a().d(this.a.getName() + " failed to parse Id, this will cause method getId() always returens 0", new Object[0]);
            }
        }
        try {
            this.f = ResHelper.parseInt(String.valueOf(ShareSDK.b(str, "SortId")).trim());
        } catch (Throwable th2) {
            if (!(this.a instanceof CustomPlatform)) {
                d.a().d(this.a.getName() + " failed to parse SortId, this won't cause any problem, don't worry", new Object[0]);
            }
        }
        String b2 = ShareSDK.b(str, "Enable");
        if (b2 == null) {
            this.i = true;
            if (!(this.a instanceof CustomPlatform)) {
                d.a().d(this.a.getName() + " failed to parse Enable, this will cause platform always be enable", new Object[0]);
            }
        } else {
            this.i = "true".equals(b2.trim());
        }
        this.a.initDevInfo(str);
    }

    public int a() {
        return this.e;
    }

    public int b() {
        return this.f;
    }

    public void a(PlatformActionListener platformActionListener) {
        this.d.a(platformActionListener);
    }

    public PlatformActionListener c() {
        return this.d.a();
    }

    public boolean d() {
        return this.c.isValid();
    }

    public void a(boolean z) {
        this.g = z;
    }

    public boolean e() {
        return this.g;
    }

    public boolean f() {
        return this.i;
    }

    /* access modifiers changed from: private */
    public boolean j() {
        boolean z;
        boolean z2 = false;
        if (ShareSDK.a()) {
            String a2 = a(this.a.getPlatformId(), "covert_url", (String) null);
            if (a2 != null) {
                a2.trim();
            }
            if (!"false".equals(a2)) {
                z2 = true;
            }
            this.h = z2;
            this.a.setNetworkDevinfo();
            return true;
        }
        try {
            HashMap hashMap = new HashMap();
            if (!ShareSDK.a((HashMap<String, Object>) hashMap)) {
                return false;
            }
            if (!ShareSDK.b(hashMap)) {
                d.a().i("Failed to parse network dev-info: " + new Hashon().fromHashMap(hashMap), new Object[0]);
                return false;
            }
            String a3 = a(this.a.getPlatformId(), "covert_url", (String) null);
            if (a3 != null) {
                a3.trim();
            }
            if (!"false".equals(a3)) {
                z = true;
            } else {
                z = false;
            }
            this.h = z;
            this.a.setNetworkDevinfo();
            return true;
        } catch (Throwable th) {
            d.a().w(th);
            return false;
        }
    }

    public String a(int i2, String str, String str2) {
        String a2 = ShareSDK.a(i2, str);
        if (TextUtils.isEmpty(a2) || "null".equals(a2)) {
            return this.a.getDevinfo(this.a.getName(), str2);
        }
        return a2;
    }

    public void a(int i2, Object obj) {
        this.d.a(this.a, i2, obj);
    }

    /* access modifiers changed from: protected */
    public void b(int i2, Object obj) {
        Object obj2;
        switch (i2) {
            case 1:
                if (this.d != null) {
                    this.d.onComplete(this.a, 1, (HashMap<String, Object>) null);
                    return;
                }
                return;
            case 2:
                Object[] objArr = (Object[]) obj;
                this.a.getFriendList(((Integer) objArr[0]).intValue(), ((Integer) objArr[1]).intValue(), (String) objArr[2]);
                return;
            case 6:
                this.a.follow((String) obj);
                return;
            case 7:
                Object[] objArr2 = (Object[]) obj;
                this.a.timeline(((Integer) objArr2[0]).intValue(), ((Integer) objArr2[1]).intValue(), (String) objArr2[2]);
                return;
            case 8:
                this.a.userInfor(obj == null ? null : (String) obj);
                return;
            case 9:
                Platform.ShareParams shareParams = (Platform.ShareParams) obj;
                HashMap<String, Object> map = shareParams.toMap();
                for (Field field : shareParams.getClass().getFields()) {
                    if (map.get(field.getName()) == null) {
                        field.setAccessible(true);
                        try {
                            obj2 = field.get(shareParams);
                        } catch (Throwable th) {
                            d.a().w(th);
                            obj2 = null;
                        }
                        if (obj2 != null) {
                            map.put(field.getName(), obj2);
                        }
                    }
                }
                if (this.d instanceof a) {
                    this.d.a(this.a, shareParams);
                }
                this.a.doShare(shareParams);
                return;
            default:
                Object[] objArr3 = (Object[]) obj;
                this.a.doCustomerProtocol(String.valueOf(objArr3[0]), String.valueOf(objArr3[1]), i2, (HashMap) objArr3[2], (HashMap) objArr3[3]);
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void c(final int i2, final Object obj) {
        new Thread() {
            public void run() {
                try {
                    boolean unused = c.this.j();
                    if (c.this.a.checkAuthorize(i2, obj)) {
                        c.this.b(i2, obj);
                    }
                } catch (Throwable th) {
                    d.a().w(th);
                }
            }
        }.start();
    }

    public void a(final String[] strArr) {
        new Thread() {
            public void run() {
                try {
                    boolean unused = c.this.j();
                    c.this.a.doAuthorize(strArr);
                } catch (Throwable th) {
                    d.a().w(th);
                }
            }
        }.start();
    }

    public void a(Platform.ShareParams shareParams) {
        if (shareParams != null) {
            c(9, shareParams);
        } else if (this.d != null) {
            this.d.onError(this.a, 9, new NullPointerException());
        }
    }

    public void b(String str) {
        c(6, str);
    }

    public void a(String str, int i2, int i3) {
        c(7, new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), str});
    }

    public void c(String str) {
        c(8, str);
    }

    public void a(int i2, int i3, String str) {
        c(2, new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), str});
    }

    public void a(String str, String str2, short s, HashMap<String, Object> hashMap, HashMap<String, String> hashMap2) {
        c(655360 | s, new Object[]{str, str2, hashMap, hashMap2});
    }

    public PlatformDb g() {
        return this.c;
    }

    public void h() {
        this.c.removeAccount();
    }

    public String a(String str, boolean z) {
        long currentTimeMillis = System.currentTimeMillis();
        if (!this.h) {
            d.a().i("getShortLintk use time: " + (System.currentTimeMillis() - currentTimeMillis), new Object[0]);
            return str;
        } else if (TextUtils.isEmpty(str)) {
            d.a().i("getShortLintk use time: " + (System.currentTimeMillis() - currentTimeMillis), new Object[0]);
            return str;
        } else {
            String a2 = ShareSDK.a(str, z, this.a.getPlatformId(), k());
            d.a().i("getShortLintk use time: " + (System.currentTimeMillis() - currentTimeMillis), new Object[0]);
            return a2;
        }
    }

    private String k() {
        StringBuilder sb = new StringBuilder();
        try {
            if ("TencentWeibo".equals(this.a.getName())) {
                d.a().i("user id %s ==>>", g().getUserName());
                sb.append(Data.urlEncode(g().getUserName(), "utf-8"));
            } else {
                sb.append(Data.urlEncode(g().getUserId(), "utf-8"));
            }
            sb.append("|").append(Data.urlEncode(g().get("secretType"), "utf-8"));
            sb.append("|").append(Data.urlEncode(g().get("gender"), "utf-8"));
            sb.append("|").append(Data.urlEncode(g().get("birthday"), "utf-8"));
            sb.append("|").append(Data.urlEncode(g().get("educationJSONArrayStr"), "utf-8"));
            sb.append("|").append(Data.urlEncode(g().get("workJSONArrayStr"), "utf-8"));
        } catch (Throwable th) {
            d.a().w(th);
        }
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public PlatformActionListener i() {
        return this.d;
    }

    public String d(String str) {
        return ShareSDK.a(str);
    }

    public String a(Bitmap bitmap) {
        return ShareSDK.a(bitmap);
    }
}

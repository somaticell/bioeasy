package cn.sharesdk.sina.weibo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.net.Uri;
import android.text.TextUtils;
import cn.sharesdk.framework.utils.d;
import com.mob.tools.utils.Data;
import java.util.List;

/* compiled from: WeiboAppManager */
public class e {
    private static final Uri a = Uri.parse("content://com.sina.weibo.sdkProvider/query/package");
    private static e b;
    private static a c = null;
    private Context d;

    /* compiled from: WeiboAppManager */
    public static class a {
        private String a;
        private int b;

        /* access modifiers changed from: private */
        public void a(String str) {
            this.a = str;
        }

        public String a() {
            return this.a;
        }

        /* access modifiers changed from: private */
        public void a(int i) {
            this.b = i;
        }

        public int b() {
            return this.b;
        }

        public String toString() {
            return "WeiboInfo: PackageName = " + this.a + ", supportApi = " + this.b;
        }
    }

    private e(Context context) {
        this.d = context.getApplicationContext();
    }

    public static synchronized e a(Context context) {
        e eVar;
        synchronized (e.class) {
            if (b == null) {
                b = new e(context);
            }
            eVar = b;
        }
        return eVar;
    }

    public synchronized String a() {
        if (c == null) {
            c = b(this.d);
        }
        return c.a();
    }

    private a b(Context context) {
        boolean z = true;
        a c2 = c(context);
        a d2 = d(context);
        boolean z2 = c2 != null;
        if (d2 == null) {
            z = false;
        }
        if (!z2 || !z) {
            if (z2) {
                return c2;
            }
            if (z) {
                return d2;
            }
            return null;
        } else if (c2.b() >= d2.b()) {
            return c2;
        } else {
            return d2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x0080  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private cn.sharesdk.sina.weibo.e.a c(android.content.Context r8) {
        /*
            r7 = this;
            r6 = 0
            android.content.ContentResolver r0 = r8.getContentResolver()
            android.net.Uri r1 = a     // Catch:{ Exception -> 0x0066, all -> 0x007c }
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5)     // Catch:{ Exception -> 0x0066, all -> 0x007c }
            if (r1 != 0) goto L_0x0018
            if (r1 == 0) goto L_0x0016
            r1.close()
        L_0x0016:
            r0 = r6
        L_0x0017:
            return r0
        L_0x0018:
            java.lang.String r0 = "support_api"
            int r2 = r1.getColumnIndex(r0)     // Catch:{ Exception -> 0x0086 }
            java.lang.String r0 = "package"
            int r3 = r1.getColumnIndex(r0)     // Catch:{ Exception -> 0x0086 }
            boolean r0 = r1.moveToFirst()     // Catch:{ Exception -> 0x0086 }
            if (r0 == 0) goto L_0x005f
            r0 = -1
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Exception -> 0x0086 }
            int r0 = java.lang.Integer.parseInt(r2)     // Catch:{ NumberFormatException -> 0x0055 }
            r2 = r0
        L_0x0034:
            java.lang.String r3 = r1.getString(r3)     // Catch:{ Exception -> 0x0086 }
            boolean r0 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x0086 }
            if (r0 != 0) goto L_0x005f
            boolean r0 = a((android.content.Context) r8, (java.lang.String) r3)     // Catch:{ Exception -> 0x0086 }
            if (r0 == 0) goto L_0x005f
            cn.sharesdk.sina.weibo.e$a r0 = new cn.sharesdk.sina.weibo.e$a     // Catch:{ Exception -> 0x0086 }
            r0.<init>()     // Catch:{ Exception -> 0x0086 }
            r0.a((java.lang.String) r3)     // Catch:{ Exception -> 0x0086 }
            r0.a((int) r2)     // Catch:{ Exception -> 0x0086 }
            if (r1 == 0) goto L_0x0017
            r1.close()
            goto L_0x0017
        L_0x0055:
            r2 = move-exception
            com.mob.tools.log.NLog r4 = cn.sharesdk.framework.utils.d.a()     // Catch:{ Exception -> 0x0086 }
            r4.d(r2)     // Catch:{ Exception -> 0x0086 }
            r2 = r0
            goto L_0x0034
        L_0x005f:
            if (r1 == 0) goto L_0x0064
            r1.close()
        L_0x0064:
            r0 = r6
            goto L_0x0017
        L_0x0066:
            r0 = move-exception
            r1 = r6
        L_0x0068:
            com.mob.tools.log.NLog r2 = cn.sharesdk.framework.utils.d.a()     // Catch:{ all -> 0x0084 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x0084 }
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0084 }
            r2.e(r0, r3)     // Catch:{ all -> 0x0084 }
            if (r1 == 0) goto L_0x0064
            r1.close()
            goto L_0x0064
        L_0x007c:
            r0 = move-exception
            r1 = r6
        L_0x007e:
            if (r1 == 0) goto L_0x0083
            r1.close()
        L_0x0083:
            throw r0
        L_0x0084:
            r0 = move-exception
            goto L_0x007e
        L_0x0086:
            r0 = move-exception
            goto L_0x0068
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.sharesdk.sina.weibo.e.c(android.content.Context):cn.sharesdk.sina.weibo.e$a");
    }

    private a d(Context context) {
        a aVar;
        a aVar2 = null;
        Intent intent = new Intent("com.sina.weibo.action.sdkidentity");
        intent.addCategory("android.intent.category.DEFAULT");
        List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(intent, 0);
        if (queryIntentServices != null && !queryIntentServices.isEmpty()) {
            for (ResolveInfo next : queryIntentServices) {
                if (next.serviceInfo == null || next.serviceInfo.applicationInfo == null || TextUtils.isEmpty(next.serviceInfo.applicationInfo.packageName) || (aVar = a(next.serviceInfo.applicationInfo.packageName)) == null || (aVar2 != null && aVar2.b() >= aVar.b())) {
                    aVar = aVar2;
                }
                aVar2 = aVar;
            }
        }
        return aVar2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x00c7 A[SYNTHETIC, Splitter:B:40:0x00c7] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00e1 A[SYNTHETIC, Splitter:B:47:0x00e1] */
    /* JADX WARNING: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:37:0x00b7=Splitter:B:37:0x00b7, B:10:0x0039=Splitter:B:10:0x0039} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public cn.sharesdk.sina.weibo.e.a a(java.lang.String r10) {
        /*
            r9 = this;
            r8 = -1
            r0 = 0
            r7 = 0
            boolean r1 = android.text.TextUtils.isEmpty(r10)
            if (r1 == 0) goto L_0x000a
        L_0x0009:
            return r0
        L_0x000a:
            android.content.Context r1 = r9.d     // Catch:{ NameNotFoundException -> 0x00f8, Exception -> 0x00b5, all -> 0x00dc }
            r2 = 2
            android.content.Context r1 = r1.createPackageContext(r10, r2)     // Catch:{ NameNotFoundException -> 0x00f8, Exception -> 0x00b5, all -> 0x00dc }
            r2 = 4096(0x1000, float:5.74E-42)
            byte[] r3 = new byte[r2]     // Catch:{ NameNotFoundException -> 0x00f8, Exception -> 0x00b5, all -> 0x00dc }
            android.content.res.AssetManager r1 = r1.getAssets()     // Catch:{ NameNotFoundException -> 0x00f8, Exception -> 0x00b5, all -> 0x00dc }
            java.lang.String r2 = "weibo_for_sdk.json"
            java.io.InputStream r2 = r1.open(r2)     // Catch:{ NameNotFoundException -> 0x00f8, Exception -> 0x00b5, all -> 0x00dc }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ NameNotFoundException -> 0x0038, Exception -> 0x00f6 }
            r1.<init>()     // Catch:{ NameNotFoundException -> 0x0038, Exception -> 0x00f6 }
        L_0x0025:
            r4 = 0
            r5 = 4096(0x1000, float:5.74E-42)
            int r4 = r2.read(r3, r4, r5)     // Catch:{ NameNotFoundException -> 0x0038, Exception -> 0x00f6 }
            if (r4 == r8) goto L_0x005c
            java.lang.String r5 = new java.lang.String     // Catch:{ NameNotFoundException -> 0x0038, Exception -> 0x00f6 }
            r6 = 0
            r5.<init>(r3, r6, r4)     // Catch:{ NameNotFoundException -> 0x0038, Exception -> 0x00f6 }
            r1.append(r5)     // Catch:{ NameNotFoundException -> 0x0038, Exception -> 0x00f6 }
            goto L_0x0025
        L_0x0038:
            r1 = move-exception
        L_0x0039:
            com.mob.tools.log.NLog r3 = cn.sharesdk.framework.utils.d.a()     // Catch:{ all -> 0x00f4 }
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x00f4 }
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x00f4 }
            r3.e(r1, r4)     // Catch:{ all -> 0x00f4 }
            if (r2 == 0) goto L_0x0009
            r2.close()     // Catch:{ IOException -> 0x004d }
            goto L_0x0009
        L_0x004d:
            r1 = move-exception
            com.mob.tools.log.NLog r2 = cn.sharesdk.framework.utils.d.a()
            java.lang.String r1 = r1.getMessage()
            java.lang.Object[] r3 = new java.lang.Object[r7]
            r2.e(r1, r3)
            goto L_0x0009
        L_0x005c:
            java.lang.String r3 = r1.toString()     // Catch:{ NameNotFoundException -> 0x0038, Exception -> 0x00f6 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ NameNotFoundException -> 0x0038, Exception -> 0x00f6 }
            if (r3 != 0) goto L_0x006e
            android.content.Context r3 = r9.d     // Catch:{ NameNotFoundException -> 0x0038, Exception -> 0x00f6 }
            boolean r3 = a((android.content.Context) r3, (java.lang.String) r10)     // Catch:{ NameNotFoundException -> 0x0038, Exception -> 0x00f6 }
            if (r3 != 0) goto L_0x0083
        L_0x006e:
            if (r2 == 0) goto L_0x0009
            r2.close()     // Catch:{ IOException -> 0x0074 }
            goto L_0x0009
        L_0x0074:
            r1 = move-exception
            com.mob.tools.log.NLog r2 = cn.sharesdk.framework.utils.d.a()
            java.lang.String r1 = r1.getMessage()
            java.lang.Object[] r3 = new java.lang.Object[r7]
            r2.e(r1, r3)
            goto L_0x0009
        L_0x0083:
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ NameNotFoundException -> 0x0038, Exception -> 0x00f6 }
            java.lang.String r1 = r1.toString()     // Catch:{ NameNotFoundException -> 0x0038, Exception -> 0x00f6 }
            r3.<init>(r1)     // Catch:{ NameNotFoundException -> 0x0038, Exception -> 0x00f6 }
            java.lang.String r1 = "support_api"
            r4 = -1
            int r3 = r3.optInt(r1, r4)     // Catch:{ NameNotFoundException -> 0x0038, Exception -> 0x00f6 }
            cn.sharesdk.sina.weibo.e$a r1 = new cn.sharesdk.sina.weibo.e$a     // Catch:{ NameNotFoundException -> 0x0038, Exception -> 0x00f6 }
            r1.<init>()     // Catch:{ NameNotFoundException -> 0x0038, Exception -> 0x00f6 }
            r1.a((java.lang.String) r10)     // Catch:{ NameNotFoundException -> 0x0038, Exception -> 0x00f6 }
            r1.a((int) r3)     // Catch:{ NameNotFoundException -> 0x0038, Exception -> 0x00f6 }
            if (r2 == 0) goto L_0x00a3
            r2.close()     // Catch:{ IOException -> 0x00a6 }
        L_0x00a3:
            r0 = r1
            goto L_0x0009
        L_0x00a6:
            r0 = move-exception
            com.mob.tools.log.NLog r2 = cn.sharesdk.framework.utils.d.a()
            java.lang.String r0 = r0.getMessage()
            java.lang.Object[] r3 = new java.lang.Object[r7]
            r2.e(r0, r3)
            goto L_0x00a3
        L_0x00b5:
            r1 = move-exception
            r2 = r0
        L_0x00b7:
            com.mob.tools.log.NLog r3 = cn.sharesdk.framework.utils.d.a()     // Catch:{ all -> 0x00f4 }
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x00f4 }
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x00f4 }
            r3.e(r1, r4)     // Catch:{ all -> 0x00f4 }
            if (r2 == 0) goto L_0x0009
            r2.close()     // Catch:{ IOException -> 0x00cc }
            goto L_0x0009
        L_0x00cc:
            r1 = move-exception
            com.mob.tools.log.NLog r2 = cn.sharesdk.framework.utils.d.a()
            java.lang.String r1 = r1.getMessage()
            java.lang.Object[] r3 = new java.lang.Object[r7]
            r2.e(r1, r3)
            goto L_0x0009
        L_0x00dc:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x00df:
            if (r2 == 0) goto L_0x00e4
            r2.close()     // Catch:{ IOException -> 0x00e5 }
        L_0x00e4:
            throw r0
        L_0x00e5:
            r1 = move-exception
            com.mob.tools.log.NLog r2 = cn.sharesdk.framework.utils.d.a()
            java.lang.String r1 = r1.getMessage()
            java.lang.Object[] r3 = new java.lang.Object[r7]
            r2.e(r1, r3)
            goto L_0x00e4
        L_0x00f4:
            r0 = move-exception
            goto L_0x00df
        L_0x00f6:
            r1 = move-exception
            goto L_0x00b7
        L_0x00f8:
            r1 = move-exception
            r2 = r0
            goto L_0x0039
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.sharesdk.sina.weibo.e.a(java.lang.String):cn.sharesdk.sina.weibo.e$a");
    }

    public static boolean a(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            return a(context.getPackageManager().getPackageInfo(str, 64).signatures, "18da2bf10352443a00a5e046d9fca6bd");
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private static boolean a(Signature[] signatureArr, String str) {
        if (signatureArr == null || str == null) {
            return false;
        }
        for (Signature byteArray : signatureArr) {
            if (str.equals(Data.MD5(byteArray.toByteArray()))) {
                d.a().d("check pass", new Object[0]);
                return true;
            }
        }
        return false;
    }
}

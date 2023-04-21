package com.alipay.apmobilesecuritysdk.f;

import android.content.Context;
import android.content.SharedPreferences;
import com.alipay.b.a.a.a.a;
import com.alipay.b.a.a.a.a.c;
import com.alipay.b.a.a.d.d;

public final class g {
    public static synchronized String a(Context context, String str) {
        String b;
        synchronized (g.class) {
            String a = d.a(context, "openapi_file_pri", "openApi" + str, "");
            if (a.a(a)) {
                b = "";
            } else {
                b = c.b(c.a(), a);
                if (a.a(b)) {
                    b = "";
                }
            }
        }
        return b;
    }

    public static synchronized void a() {
        synchronized (g.class) {
        }
    }

    public static synchronized void a(Context context) {
        synchronized (g.class) {
            SharedPreferences.Editor edit = context.getSharedPreferences("openapi_file_pri", 0).edit();
            if (edit != null) {
                edit.clear();
                edit.commit();
            }
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void a(android.content.Context r4, java.lang.String r5, java.lang.String r6) {
        /*
            java.lang.Class<com.alipay.apmobilesecuritysdk.f.g> r1 = com.alipay.apmobilesecuritysdk.f.g.class
            monitor-enter(r1)
            java.lang.String r0 = "openapi_file_pri"
            r2 = 0
            android.content.SharedPreferences r0 = r4.getSharedPreferences(r0, r2)     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            android.content.SharedPreferences$Editor r0 = r0.edit()     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            if (r0 == 0) goto L_0x002d
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            java.lang.String r3 = "openApi"
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            java.lang.String r3 = com.alipay.b.a.a.a.a.c.a()     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            java.lang.String r3 = com.alipay.b.a.a.a.a.c.a((java.lang.String) r3, (java.lang.String) r6)     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            r0.putString(r2, r3)     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            r0.commit()     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
        L_0x002d:
            monitor-exit(r1)
            return
        L_0x002f:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        L_0x0032:
            r0 = move-exception
            goto L_0x002d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.apmobilesecuritysdk.f.g.a(android.content.Context, java.lang.String, java.lang.String):void");
    }
}

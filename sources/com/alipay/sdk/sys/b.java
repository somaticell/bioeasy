package com.alipay.sdk.sys;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.app.statistic.a;
import com.alipay.sdk.data.c;
import com.ta.utdid2.device.UTDevice;
import java.io.File;

public final class b {
    private static b b;
    public Context a;

    private b() {
    }

    public static b a() {
        if (b == null) {
            b = new b();
        }
        return b;
    }

    private Context d() {
        return this.a;
    }

    public final void a(Context context) {
        this.a = context.getApplicationContext();
    }

    private static c e() {
        return c.a();
    }

    public static boolean b() {
        String[] strArr = {"/system/xbin/", "/system/bin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        int i = 0;
        while (i < 5) {
            try {
                String str = strArr[i] + "su";
                if (new File(str).exists()) {
                    String a2 = a(new String[]{"ls", "-l", str});
                    if (TextUtils.isEmpty(a2) || a2.indexOf("root") == a2.lastIndexOf("root")) {
                        return false;
                    }
                    return true;
                }
                i++;
            } catch (Exception e) {
            }
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004a, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004d, code lost:
        r0 = "";
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004a A[ExcHandler: all (th java.lang.Throwable), Splitter:B:3:0x0010] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(java.lang.String[] r6) {
        /*
            java.lang.String r2 = ""
            r0 = 0
            java.lang.ProcessBuilder r1 = new java.lang.ProcessBuilder     // Catch:{ Throwable -> 0x0035, all -> 0x003e }
            r1.<init>(r6)     // Catch:{ Throwable -> 0x0035, all -> 0x003e }
            r3 = 0
            r1.redirectErrorStream(r3)     // Catch:{ Throwable -> 0x0035, all -> 0x003e }
            java.lang.Process r1 = r1.start()     // Catch:{ Throwable -> 0x0035, all -> 0x003e }
            java.io.DataOutputStream r3 = new java.io.DataOutputStream     // Catch:{ Throwable -> 0x004c, all -> 0x004a }
            java.io.OutputStream r0 = r1.getOutputStream()     // Catch:{ Throwable -> 0x004c, all -> 0x004a }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x004c, all -> 0x004a }
            java.io.DataInputStream r0 = new java.io.DataInputStream     // Catch:{ Throwable -> 0x004c, all -> 0x004a }
            java.io.InputStream r4 = r1.getInputStream()     // Catch:{ Throwable -> 0x004c, all -> 0x004a }
            r0.<init>(r4)     // Catch:{ Throwable -> 0x004c, all -> 0x004a }
            java.lang.String r0 = r0.readLine()     // Catch:{ Throwable -> 0x004c, all -> 0x004a }
            java.lang.String r2 = "exit\n"
            r3.writeBytes(r2)     // Catch:{ Throwable -> 0x004f, all -> 0x004a }
            r3.flush()     // Catch:{ Throwable -> 0x004f, all -> 0x004a }
            r1.waitFor()     // Catch:{ Throwable -> 0x004f, all -> 0x004a }
            r1.destroy()     // Catch:{ Exception -> 0x0046 }
        L_0x0034:
            return r0
        L_0x0035:
            r1 = move-exception
            r1 = r0
            r0 = r2
        L_0x0038:
            r1.destroy()     // Catch:{ Exception -> 0x003c }
            goto L_0x0034
        L_0x003c:
            r1 = move-exception
            goto L_0x0034
        L_0x003e:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
        L_0x0042:
            r1.destroy()     // Catch:{ Exception -> 0x0048 }
        L_0x0045:
            throw r0
        L_0x0046:
            r1 = move-exception
            goto L_0x0034
        L_0x0048:
            r1 = move-exception
            goto L_0x0045
        L_0x004a:
            r0 = move-exception
            goto L_0x0042
        L_0x004c:
            r0 = move-exception
            r0 = r2
            goto L_0x0038
        L_0x004f:
            r2 = move-exception
            goto L_0x0038
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.sys.b.a(java.lang.String[]):java.lang.String");
    }

    public final String c() {
        try {
            return UTDevice.getUtdid(this.a);
        } catch (Throwable th) {
            a.a(com.alipay.sdk.app.statistic.c.e, com.alipay.sdk.app.statistic.c.j, th);
            return "";
        }
    }
}

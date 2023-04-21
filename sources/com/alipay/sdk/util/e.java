package com.alipay.sdk.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import com.alipay.android.app.IAlixPay;
import com.alipay.android.app.IRemoteServiceCallback;
import com.alipay.sdk.app.statistic.c;
import com.alipay.sdk.util.l;

public class e {
    public static final String b = "failed";
    public Activity a;
    /* access modifiers changed from: private */
    public IAlixPay c;
    /* access modifiers changed from: private */
    public final Object d = IAlixPay.class;
    private boolean e;
    private a f;
    private ServiceConnection g = new f(this);
    private IRemoteServiceCallback h = new g(this);

    public interface a {
        void a();
    }

    public e(Activity activity, a aVar) {
        this.a = activity;
        this.f = aVar;
    }

    public final String a(String str) {
        try {
            l.a a2 = l.a((Context) this.a);
            if (a2.a()) {
                return b;
            }
            if (a2 != null && a2.b > 78) {
                String a3 = l.a();
                Intent intent = new Intent();
                intent.setClassName(a3, "com.alipay.android.app.TransProcessPayActivity");
                this.a.startActivity(intent);
                Thread.sleep(200);
            }
            return b(str);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(c.b, c.C, th);
        }
    }

    private void a(l.a aVar) throws InterruptedException {
        if (aVar != null && aVar.b > 78) {
            String a2 = l.a();
            Intent intent = new Intent();
            intent.setClassName(a2, "com.alipay.android.app.TransProcessPayActivity");
            this.a.startActivity(intent);
            Thread.sleep(200);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:63:0x0137  */
    /* JADX WARNING: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String b(java.lang.String r9) {
        /*
            r8 = this;
            r7 = 0
            r6 = 0
            android.content.Intent r0 = new android.content.Intent
            r0.<init>()
            java.lang.String r1 = com.alipay.sdk.util.l.a()
            r0.setPackage(r1)
            java.lang.String r1 = "com.eg.android.AlipayGphone.IAlixPay"
            r0.setAction(r1)
            android.app.Activity r1 = r8.a
            java.lang.String r1 = com.alipay.sdk.util.l.h(r1)
            android.app.Activity r2 = r8.a     // Catch:{ Throwable -> 0x00a1 }
            android.content.Context r2 = r2.getApplicationContext()     // Catch:{ Throwable -> 0x00a1 }
            android.content.ServiceConnection r3 = r8.g     // Catch:{ Throwable -> 0x00a1 }
            r4 = 1
            r2.bindService(r0, r3, r4)     // Catch:{ Throwable -> 0x00a1 }
            java.lang.Object r2 = r8.d
            monitor-enter(r2)
            com.alipay.android.app.IAlixPay r0 = r8.c     // Catch:{ all -> 0x00b5 }
            if (r0 != 0) goto L_0x003a
            java.lang.Object r0 = r8.d     // Catch:{ InterruptedException -> 0x00ac }
            com.alipay.sdk.data.a r3 = com.alipay.sdk.data.a.b()     // Catch:{ InterruptedException -> 0x00ac }
            int r3 = r3.a()     // Catch:{ InterruptedException -> 0x00ac }
            long r4 = (long) r3     // Catch:{ InterruptedException -> 0x00ac }
            r0.wait(r4)     // Catch:{ InterruptedException -> 0x00ac }
        L_0x003a:
            monitor-exit(r2)     // Catch:{ all -> 0x00b5 }
            com.alipay.android.app.IAlixPay r0 = r8.c     // Catch:{ Throwable -> 0x0109 }
            if (r0 != 0) goto L_0x00b8
            android.app.Activity r0 = r8.a     // Catch:{ Throwable -> 0x0109 }
            java.lang.String r0 = com.alipay.sdk.util.l.h(r0)     // Catch:{ Throwable -> 0x0109 }
            android.app.Activity r2 = r8.a     // Catch:{ Throwable -> 0x0109 }
            java.lang.String r2 = com.alipay.sdk.util.l.i(r2)     // Catch:{ Throwable -> 0x0109 }
            java.lang.String r3 = "biz"
            java.lang.String r4 = "ClientBindFailed"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0109 }
            r5.<init>()     // Catch:{ Throwable -> 0x0109 }
            java.lang.StringBuilder r1 = r5.append(r1)     // Catch:{ Throwable -> 0x0109 }
            java.lang.String r5 = "|"
            java.lang.StringBuilder r1 = r1.append(r5)     // Catch:{ Throwable -> 0x0109 }
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ Throwable -> 0x0109 }
            java.lang.String r1 = "|"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ Throwable -> 0x0109 }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x0109 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0109 }
            com.alipay.sdk.app.statistic.a.a((java.lang.String) r3, (java.lang.String) r4, (java.lang.String) r0)     // Catch:{ Throwable -> 0x0109 }
            java.lang.String r0 = "failed"
            com.alipay.android.app.IAlixPay r1 = r8.c     // Catch:{ Throwable -> 0x017c }
            com.alipay.android.app.IRemoteServiceCallback r2 = r8.h     // Catch:{ Throwable -> 0x017c }
            r1.unregisterCallback(r2)     // Catch:{ Throwable -> 0x017c }
        L_0x007e:
            android.app.Activity r1 = r8.a     // Catch:{ Throwable -> 0x0179 }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Throwable -> 0x0179 }
            android.content.ServiceConnection r2 = r8.g     // Catch:{ Throwable -> 0x0179 }
            r1.unbindService(r2)     // Catch:{ Throwable -> 0x0179 }
        L_0x0089:
            r8.f = r6
            r8.h = r6
            r8.g = r6
            r8.c = r6
            boolean r1 = r8.e
            if (r1 == 0) goto L_0x00a0
            android.app.Activity r1 = r8.a
            if (r1 == 0) goto L_0x00a0
            android.app.Activity r1 = r8.a
            r1.setRequestedOrientation(r7)
            r8.e = r7
        L_0x00a0:
            return r0
        L_0x00a1:
            r0 = move-exception
            java.lang.String r1 = "biz"
            java.lang.String r2 = "ClientBindServiceFailed"
            com.alipay.sdk.app.statistic.a.a((java.lang.String) r1, (java.lang.String) r2, (java.lang.Throwable) r0)
            java.lang.String r0 = "failed"
            goto L_0x00a0
        L_0x00ac:
            r0 = move-exception
            java.lang.String r3 = "biz"
            java.lang.String r4 = "BindWaitTimeoutEx"
            com.alipay.sdk.app.statistic.a.a((java.lang.String) r3, (java.lang.String) r4, (java.lang.Throwable) r0)     // Catch:{ all -> 0x00b5 }
            goto L_0x003a
        L_0x00b5:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        L_0x00b8:
            com.alipay.sdk.util.e$a r0 = r8.f     // Catch:{ Throwable -> 0x0109 }
            if (r0 == 0) goto L_0x00c1
            com.alipay.sdk.util.e$a r0 = r8.f     // Catch:{ Throwable -> 0x0109 }
            r0.a()     // Catch:{ Throwable -> 0x0109 }
        L_0x00c1:
            android.app.Activity r0 = r8.a     // Catch:{ Throwable -> 0x0109 }
            int r0 = r0.getRequestedOrientation()     // Catch:{ Throwable -> 0x0109 }
            if (r0 != 0) goto L_0x00d2
            android.app.Activity r0 = r8.a     // Catch:{ Throwable -> 0x0109 }
            r1 = 1
            r0.setRequestedOrientation(r1)     // Catch:{ Throwable -> 0x0109 }
            r0 = 1
            r8.e = r0     // Catch:{ Throwable -> 0x0109 }
        L_0x00d2:
            com.alipay.android.app.IAlixPay r0 = r8.c     // Catch:{ Throwable -> 0x0109 }
            com.alipay.android.app.IRemoteServiceCallback r1 = r8.h     // Catch:{ Throwable -> 0x0109 }
            r0.registerCallback(r1)     // Catch:{ Throwable -> 0x0109 }
            com.alipay.android.app.IAlixPay r0 = r8.c     // Catch:{ Throwable -> 0x0109 }
            java.lang.String r0 = r0.Pay(r9)     // Catch:{ Throwable -> 0x0109 }
            com.alipay.android.app.IAlixPay r1 = r8.c     // Catch:{ Throwable -> 0x0176 }
            com.alipay.android.app.IRemoteServiceCallback r2 = r8.h     // Catch:{ Throwable -> 0x0176 }
            r1.unregisterCallback(r2)     // Catch:{ Throwable -> 0x0176 }
        L_0x00e6:
            android.app.Activity r1 = r8.a     // Catch:{ Throwable -> 0x0173 }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Throwable -> 0x0173 }
            android.content.ServiceConnection r2 = r8.g     // Catch:{ Throwable -> 0x0173 }
            r1.unbindService(r2)     // Catch:{ Throwable -> 0x0173 }
        L_0x00f1:
            r8.f = r6
            r8.h = r6
            r8.g = r6
            r8.c = r6
            boolean r1 = r8.e
            if (r1 == 0) goto L_0x00a0
            android.app.Activity r1 = r8.a
            if (r1 == 0) goto L_0x00a0
            android.app.Activity r1 = r8.a
            r1.setRequestedOrientation(r7)
            r8.e = r7
            goto L_0x00a0
        L_0x0109:
            r0 = move-exception
            java.lang.String r1 = "biz"
            java.lang.String r2 = "ClientBindException"
            com.alipay.sdk.app.statistic.a.a((java.lang.String) r1, (java.lang.String) r2, (java.lang.Throwable) r0)     // Catch:{ all -> 0x0140 }
            java.lang.String r0 = com.alipay.sdk.app.h.a()     // Catch:{ all -> 0x0140 }
            com.alipay.android.app.IAlixPay r1 = r8.c     // Catch:{ Throwable -> 0x0171 }
            com.alipay.android.app.IRemoteServiceCallback r2 = r8.h     // Catch:{ Throwable -> 0x0171 }
            r1.unregisterCallback(r2)     // Catch:{ Throwable -> 0x0171 }
        L_0x011c:
            android.app.Activity r1 = r8.a     // Catch:{ Throwable -> 0x016f }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Throwable -> 0x016f }
            android.content.ServiceConnection r2 = r8.g     // Catch:{ Throwable -> 0x016f }
            r1.unbindService(r2)     // Catch:{ Throwable -> 0x016f }
        L_0x0127:
            r8.f = r6
            r8.h = r6
            r8.g = r6
            r8.c = r6
            boolean r1 = r8.e
            if (r1 == 0) goto L_0x00a0
            android.app.Activity r1 = r8.a
            if (r1 == 0) goto L_0x00a0
            android.app.Activity r1 = r8.a
            r1.setRequestedOrientation(r7)
            r8.e = r7
            goto L_0x00a0
        L_0x0140:
            r0 = move-exception
            com.alipay.android.app.IAlixPay r1 = r8.c     // Catch:{ Throwable -> 0x016d }
            com.alipay.android.app.IRemoteServiceCallback r2 = r8.h     // Catch:{ Throwable -> 0x016d }
            r1.unregisterCallback(r2)     // Catch:{ Throwable -> 0x016d }
        L_0x0148:
            android.app.Activity r1 = r8.a     // Catch:{ Throwable -> 0x016b }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Throwable -> 0x016b }
            android.content.ServiceConnection r2 = r8.g     // Catch:{ Throwable -> 0x016b }
            r1.unbindService(r2)     // Catch:{ Throwable -> 0x016b }
        L_0x0153:
            r8.f = r6
            r8.h = r6
            r8.g = r6
            r8.c = r6
            boolean r1 = r8.e
            if (r1 == 0) goto L_0x016a
            android.app.Activity r1 = r8.a
            if (r1 == 0) goto L_0x016a
            android.app.Activity r1 = r8.a
            r1.setRequestedOrientation(r7)
            r8.e = r7
        L_0x016a:
            throw r0
        L_0x016b:
            r1 = move-exception
            goto L_0x0153
        L_0x016d:
            r1 = move-exception
            goto L_0x0148
        L_0x016f:
            r1 = move-exception
            goto L_0x0127
        L_0x0171:
            r1 = move-exception
            goto L_0x011c
        L_0x0173:
            r1 = move-exception
            goto L_0x00f1
        L_0x0176:
            r1 = move-exception
            goto L_0x00e6
        L_0x0179:
            r1 = move-exception
            goto L_0x0089
        L_0x017c:
            r1 = move-exception
            goto L_0x007e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.util.e.b(java.lang.String):java.lang.String");
    }

    private void a() {
        this.a = null;
    }
}

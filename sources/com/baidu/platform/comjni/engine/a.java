package com.baidu.platform.comjni.engine;

import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import java.util.List;

public class a {
    private static final String a = a.class.getSimpleName();
    private static SparseArray<List<Handler>> b = new SparseArray<>();

    public static void a(int i, int i2, int i3, long j) {
        synchronized (b) {
            List<Handler> list = b.get(i);
            if (list != null && !list.isEmpty()) {
                for (Handler obtain : list) {
                    Message.obtain(obtain, i, i2, i3, Long.valueOf(j)).sendToTarget();
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(int r3, android.os.Handler r4) {
        /*
            android.util.SparseArray<java.util.List<android.os.Handler>> r1 = b
            monitor-enter(r1)
            if (r4 != 0) goto L_0x0007
            monitor-exit(r1)     // Catch:{ all -> 0x001c }
        L_0x0006:
            return
        L_0x0007:
            android.util.SparseArray<java.util.List<android.os.Handler>> r0 = b     // Catch:{ all -> 0x001c }
            java.lang.Object r0 = r0.get(r3)     // Catch:{ all -> 0x001c }
            java.util.List r0 = (java.util.List) r0     // Catch:{ all -> 0x001c }
            if (r0 == 0) goto L_0x001f
            boolean r2 = r0.contains(r4)     // Catch:{ all -> 0x001c }
            if (r2 != 0) goto L_0x001a
            r0.add(r4)     // Catch:{ all -> 0x001c }
        L_0x001a:
            monitor-exit(r1)     // Catch:{ all -> 0x001c }
            goto L_0x0006
        L_0x001c:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001c }
            throw r0
        L_0x001f:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x001c }
            r0.<init>()     // Catch:{ all -> 0x001c }
            r0.add(r4)     // Catch:{ all -> 0x001c }
            android.util.SparseArray<java.util.List<android.os.Handler>> r2 = b     // Catch:{ all -> 0x001c }
            r2.put(r3, r0)     // Catch:{ all -> 0x001c }
            goto L_0x001a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comjni.engine.a.a(int, android.os.Handler):void");
    }

    public static void b(int i, Handler handler) {
        synchronized (b) {
            if (handler != null) {
                handler.removeCallbacksAndMessages((Object) null);
                List list = b.get(i);
                if (list != null) {
                    list.remove(handler);
                }
            }
        }
    }
}

package com.loc;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: SimpleLruCache */
public class bo<K, V> {
    private final LinkedHashMap<K, V> a;
    private int b;
    private int c;

    public bo(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.c = i;
        this.a = new LinkedHashMap<>(0, 0.75f, true);
    }

    public final V b(K k, V v) {
        V put;
        if (k == null || v == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.b += c(k, v);
            put = this.a.put(k, v);
            if (put != null) {
                this.b -= c(k, put);
            }
        }
        if (put != null) {
            a(false, k, put, v);
        }
        a(this.c);
        return put;
    }

    /* access modifiers changed from: protected */
    public V a(K k) {
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
        if (r1 != null) goto L_0x0021;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001c, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0021, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r0 = r4.a.put(r5, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0028, code lost:
        if (r0 == null) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002a, code lost:
        r4.a.put(r5, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x002f, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0030, code lost:
        if (r0 == null) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0032, code lost:
        a(false, r5, r1, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r4.b += c(r5, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0044, code lost:
        a(r4.c);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0016, code lost:
        r1 = a(r5);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final V b(K r5) {
        /*
            r4 = this;
            if (r5 != 0) goto L_0x000a
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "key == null"
            r0.<init>(r1)
            throw r0
        L_0x000a:
            monitor-enter(r4)
            java.util.LinkedHashMap<K, V> r0 = r4.a     // Catch:{ all -> 0x001e }
            java.lang.Object r0 = r0.get(r5)     // Catch:{ all -> 0x001e }
            if (r0 == 0) goto L_0x0015
            monitor-exit(r4)     // Catch:{ all -> 0x001e }
        L_0x0014:
            return r0
        L_0x0015:
            monitor-exit(r4)     // Catch:{ all -> 0x001e }
            java.lang.Object r1 = r4.a(r5)
            if (r1 != 0) goto L_0x0021
            r0 = 0
            goto L_0x0014
        L_0x001e:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x001e }
            throw r0
        L_0x0021:
            monitor-enter(r4)
            java.util.LinkedHashMap<K, V> r0 = r4.a     // Catch:{ all -> 0x0041 }
            java.lang.Object r0 = r0.put(r5, r1)     // Catch:{ all -> 0x0041 }
            if (r0 == 0) goto L_0x0037
            java.util.LinkedHashMap<K, V> r2 = r4.a     // Catch:{ all -> 0x0041 }
            r2.put(r5, r0)     // Catch:{ all -> 0x0041 }
        L_0x002f:
            monitor-exit(r4)     // Catch:{ all -> 0x0041 }
            if (r0 == 0) goto L_0x0044
            r2 = 0
            r4.a(r2, r5, r1, r0)
            goto L_0x0014
        L_0x0037:
            int r2 = r4.b     // Catch:{ all -> 0x0041 }
            int r3 = r4.c(r5, r1)     // Catch:{ all -> 0x0041 }
            int r2 = r2 + r3
            r4.b = r2     // Catch:{ all -> 0x0041 }
            goto L_0x002f
        L_0x0041:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0041 }
            throw r0
        L_0x0044:
            int r0 = r4.c
            r4.a((int) r0)
            r0 = r1
            goto L_0x0014
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bo.b(java.lang.Object):java.lang.Object");
    }

    public final V c(K k) {
        V remove;
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            remove = this.a.remove(k);
            if (remove != null) {
                this.b -= c(k, remove);
            }
        }
        if (remove != null) {
            a(false, k, remove, (V) null);
        }
        return remove;
    }

    /* access modifiers changed from: protected */
    public void a(boolean z, K k, V v, V v2) {
    }

    private void a(int i) {
        Object key;
        Object value;
        while (true) {
            synchronized (this) {
                if (this.b >= 0 && (!this.a.isEmpty() || this.b == 0)) {
                    if (this.b > i) {
                        Iterator<Map.Entry<K, V>> it = this.a.entrySet().iterator();
                        Map.Entry entry = null;
                        while (it.hasNext()) {
                            entry = it.next();
                        }
                        if (entry != null) {
                            key = entry.getKey();
                            value = entry.getValue();
                            this.a.remove(key);
                            this.b -= c(key, value);
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
            a(true, key, value, (Object) null);
        }
        throw new IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
    }

    private int c(K k, V v) {
        int a2 = a(k, v);
        if (a2 >= 0) {
            return a2;
        }
        throw new IllegalStateException("Negative size: " + k + "=" + v);
    }

    /* access modifiers changed from: protected */
    public int a(K k, V v) {
        return 1;
    }

    public final void a() {
        a(-1);
    }
}

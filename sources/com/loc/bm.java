package com.loc;

import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import org.json.JSONObject;

/* compiled from: HeatMap */
public class bm {
    private static bm a = null;
    private Hashtable<String, JSONObject> b = new Hashtable<>();
    private boolean c = false;

    private bm() {
    }

    public static synchronized bm a() {
        bm bmVar;
        synchronized (bm.class) {
            if (a == null) {
                a = new bm();
            }
            bmVar = a;
        }
        return bmVar;
    }

    public synchronized void a(Context context, String str, String str2, int i, long j, boolean z) {
        if (context != null) {
            if (!TextUtils.isEmpty(str)) {
                if (az.a) {
                    JSONObject jSONObject = this.b.get(str);
                    if (jSONObject == null) {
                        jSONObject = new JSONObject();
                    }
                    try {
                        jSONObject.put("x", str2);
                        jSONObject.put("time", j);
                        if (this.b.containsKey(str)) {
                            jSONObject.put("num", jSONObject.getInt("num") + i);
                        } else {
                            jSONObject.put("num", i);
                        }
                    } catch (Exception e) {
                    }
                    this.b.put(str, jSONObject);
                    if (z) {
                        try {
                            bk.a().a(context, str, str2, j);
                        } catch (Exception e2) {
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002c, code lost:
        if (r9.b.containsKey(r3) != false) goto L_0x002e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(android.content.Context r10, java.lang.String r11, com.autonavi.aps.amapapi.model.AmapLoc r12) {
        /*
            r9 = this;
            monitor-enter(r9)
            r3 = 0
            boolean r0 = com.loc.bw.a((com.autonavi.aps.amapapi.model.AmapLoc) r12)     // Catch:{ all -> 0x006a }
            if (r0 == 0) goto L_0x000e
            if (r10 == 0) goto L_0x000e
            boolean r0 = com.loc.az.a     // Catch:{ all -> 0x006a }
            if (r0 != 0) goto L_0x0010
        L_0x000e:
            monitor-exit(r9)
            return
        L_0x0010:
            java.util.Hashtable<java.lang.String, org.json.JSONObject> r0 = r9.b     // Catch:{ all -> 0x006a }
            int r0 = r0.size()     // Catch:{ all -> 0x006a }
            r1 = 500(0x1f4, float:7.0E-43)
            if (r0 <= r1) goto L_0x002e
            double r0 = r12.i()     // Catch:{ all -> 0x006a }
            double r2 = r12.h()     // Catch:{ all -> 0x006a }
            java.lang.String r3 = com.loc.bc.a((double) r0, (double) r2)     // Catch:{ all -> 0x006a }
            java.util.Hashtable<java.lang.String, org.json.JSONObject> r0 = r9.b     // Catch:{ all -> 0x006a }
            boolean r0 = r0.containsKey(r3)     // Catch:{ all -> 0x006a }
            if (r0 == 0) goto L_0x000e
        L_0x002e:
            if (r3 != 0) goto L_0x003c
            double r0 = r12.i()     // Catch:{ all -> 0x006a }
            double r2 = r12.h()     // Catch:{ all -> 0x006a }
            java.lang.String r3 = com.loc.bc.a((double) r0, (double) r2)     // Catch:{ all -> 0x006a }
        L_0x003c:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ all -> 0x006a }
            r0.<init>()     // Catch:{ all -> 0x006a }
            java.lang.String r1 = "key"
            r0.put(r1, r11)     // Catch:{ Exception -> 0x0068 }
            java.lang.String r1 = "lat"
            double r4 = r12.i()     // Catch:{ Exception -> 0x0068 }
            r0.put(r1, r4)     // Catch:{ Exception -> 0x0068 }
            java.lang.String r1 = "lon"
            double r4 = r12.h()     // Catch:{ Exception -> 0x0068 }
            r0.put(r1, r4)     // Catch:{ Exception -> 0x0068 }
            java.lang.String r4 = r0.toString()     // Catch:{ Exception -> 0x0068 }
            r5 = 1
            long r6 = com.loc.bw.a()     // Catch:{ Exception -> 0x0068 }
            r8 = 1
            r1 = r9
            r2 = r10
            r1.a(r2, r3, r4, r5, r6, r8)     // Catch:{ Exception -> 0x0068 }
            goto L_0x000e
        L_0x0068:
            r0 = move-exception
            goto L_0x000e
        L_0x006a:
            r0 = move-exception
            monitor-exit(r9)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bm.a(android.content.Context, java.lang.String, com.autonavi.aps.amapapi.model.AmapLoc):void");
    }

    public synchronized ArrayList<bl> b() {
        ArrayList<bl> arrayList;
        ArrayList<bl> arrayList2 = new ArrayList<>();
        if (this.b.isEmpty()) {
            arrayList = arrayList2;
        } else {
            Hashtable<String, JSONObject> hashtable = this.b;
            ArrayList arrayList3 = new ArrayList(hashtable.keySet());
            Iterator it = arrayList3.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                try {
                    JSONObject jSONObject = hashtable.get(str);
                    int i = jSONObject.getInt("num");
                    String string = jSONObject.getString("x");
                    long j = jSONObject.getLong("time");
                    if (i >= 120) {
                        arrayList2.add(new bl(str, j, i, string));
                    }
                } catch (Exception e) {
                }
            }
            Collections.sort(arrayList2, new Comparator<bl>() {
                /* renamed from: a */
                public int compare(bl blVar, bl blVar2) {
                    return blVar2.b() - blVar.b();
                }
            });
            arrayList3.clear();
            arrayList = arrayList2;
        }
        return arrayList;
    }

    private void d() {
        if (!this.b.isEmpty()) {
            this.b.clear();
        }
    }

    public void a(Context context) {
        if (az.a && !this.c) {
            bw.b();
            try {
                bk.a().b(context);
            } catch (Exception e) {
            }
            this.c = true;
        }
    }

    public void c() {
        a().d();
        this.c = false;
    }
}

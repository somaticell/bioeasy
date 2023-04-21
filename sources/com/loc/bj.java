package com.loc;

import android.content.Context;
import android.text.TextUtils;
import com.autonavi.aps.amapapi.model.AmapLoc;
import java.util.ArrayList;
import java.util.Hashtable;

/* compiled from: Cache */
public class bj {
    private static bj a = null;
    private Hashtable<String, ArrayList<a>> b = new Hashtable<>();
    private long c = 0;
    private boolean d = false;

    private bj() {
    }

    public static synchronized bj a() {
        bj bjVar;
        synchronized (bj.class) {
            if (a == null) {
                a = new bj();
            }
            bjVar = a;
        }
        return bjVar;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0083, code lost:
        if (r0 < 8) goto L_0x0085;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(java.lang.String r7, java.lang.StringBuilder r8, com.autonavi.aps.amapapi.model.AmapLoc r9, android.content.Context r10, boolean r11) {
        /*
            r6 = this;
            r0 = 0
            monitor-enter(r6)
            boolean r1 = r6.a((java.lang.String) r7, (com.autonavi.aps.amapapi.model.AmapLoc) r9)     // Catch:{ all -> 0x0144 }
            if (r1 != 0) goto L_0x000a
        L_0x0008:
            monitor-exit(r6)
            return
        L_0x000a:
            java.lang.String r1 = r9.l()     // Catch:{ all -> 0x0144 }
            java.lang.String r2 = "mem"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0144 }
            if (r1 != 0) goto L_0x0008
            java.lang.String r1 = r9.l()     // Catch:{ all -> 0x0144 }
            java.lang.String r2 = "file"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0144 }
            if (r1 != 0) goto L_0x0008
            java.lang.String r1 = r9.m()     // Catch:{ all -> 0x0144 }
            java.lang.String r2 = "-3"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0144 }
            if (r1 != 0) goto L_0x0008
            boolean r1 = r6.b()     // Catch:{ all -> 0x0144 }
            if (r1 == 0) goto L_0x0037
            r6.c()     // Catch:{ all -> 0x0144 }
        L_0x0037:
            org.json.JSONObject r1 = r9.D()     // Catch:{ all -> 0x0144 }
            java.lang.String r2 = "offpct"
            boolean r2 = com.loc.bw.a((org.json.JSONObject) r1, (java.lang.String) r2)     // Catch:{ all -> 0x0144 }
            if (r2 == 0) goto L_0x004b
            java.lang.String r2 = "offpct"
            r1.remove(r2)     // Catch:{ all -> 0x0144 }
            r9.a((org.json.JSONObject) r1)     // Catch:{ all -> 0x0144 }
        L_0x004b:
            java.lang.String r1 = "wifi"
            boolean r1 = r7.contains(r1)     // Catch:{ all -> 0x0144 }
            if (r1 == 0) goto L_0x0112
            boolean r1 = android.text.TextUtils.isEmpty(r8)     // Catch:{ all -> 0x0144 }
            if (r1 != 0) goto L_0x0008
            float r1 = r9.j()     // Catch:{ all -> 0x0144 }
            r2 = 1133903872(0x43960000, float:300.0)
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 < 0) goto L_0x0106
            java.lang.String r1 = r8.toString()     // Catch:{ all -> 0x0144 }
            java.lang.String r2 = "#"
            java.lang.String[] r2 = r1.split(r2)     // Catch:{ all -> 0x0144 }
            int r3 = r2.length     // Catch:{ all -> 0x0144 }
            r1 = r0
        L_0x0070:
            if (r1 >= r3) goto L_0x0081
            r4 = r2[r1]     // Catch:{ all -> 0x0144 }
            java.lang.String r5 = ","
            boolean r4 = r4.contains(r5)     // Catch:{ all -> 0x0144 }
            if (r4 == 0) goto L_0x007e
            int r0 = r0 + 1
        L_0x007e:
            int r1 = r1 + 1
            goto L_0x0070
        L_0x0081:
            r1 = 8
            if (r0 >= r1) goto L_0x0008
        L_0x0085:
            java.lang.String r0 = "cgiwifi"
            boolean r0 = r7.contains(r0)     // Catch:{ all -> 0x0144 }
            if (r0 == 0) goto L_0x00b4
            java.lang.String r0 = r9.B()     // Catch:{ all -> 0x0144 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0144 }
            if (r0 != 0) goto L_0x00b4
            java.lang.String r0 = "cgiwifi"
            java.lang.String r1 = "cgi"
            java.lang.String r1 = r7.replace(r0, r1)     // Catch:{ all -> 0x0144 }
            com.autonavi.aps.amapapi.model.AmapLoc r3 = r9.C()     // Catch:{ all -> 0x0144 }
            boolean r0 = com.loc.bw.a((com.autonavi.aps.amapapi.model.AmapLoc) r3)     // Catch:{ all -> 0x0144 }
            if (r0 == 0) goto L_0x00b4
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0144 }
            r2.<init>()     // Catch:{ all -> 0x0144 }
            r5 = 1
            r0 = r6
            r4 = r10
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ all -> 0x0144 }
        L_0x00b4:
            com.autonavi.aps.amapapi.model.AmapLoc r0 = r6.a((java.lang.String) r7, (java.lang.StringBuilder) r8)     // Catch:{ all -> 0x0144 }
            boolean r1 = com.loc.bw.a((com.autonavi.aps.amapapi.model.AmapLoc) r0)     // Catch:{ all -> 0x0144 }
            if (r1 == 0) goto L_0x00cd
            java.lang.String r0 = r0.E()     // Catch:{ all -> 0x0144 }
            r1 = 3
            java.lang.String r1 = r9.c((int) r1)     // Catch:{ all -> 0x0144 }
            boolean r0 = r0.equals(r1)     // Catch:{ all -> 0x0144 }
            if (r0 != 0) goto L_0x0008
        L_0x00cd:
            long r0 = com.loc.bw.b()     // Catch:{ all -> 0x0144 }
            r6.c = r0     // Catch:{ all -> 0x0144 }
            com.loc.bj$a r1 = new com.loc.bj$a     // Catch:{ all -> 0x0144 }
            r1.<init>()     // Catch:{ all -> 0x0144 }
            r1.a((com.autonavi.aps.amapapi.model.AmapLoc) r9)     // Catch:{ all -> 0x0144 }
            boolean r0 = android.text.TextUtils.isEmpty(r8)     // Catch:{ all -> 0x0144 }
            if (r0 == 0) goto L_0x0131
            r0 = 0
        L_0x00e2:
            r1.a((java.lang.String) r0)     // Catch:{ all -> 0x0144 }
            java.util.Hashtable<java.lang.String, java.util.ArrayList<com.loc.bj$a>> r0 = r6.b     // Catch:{ all -> 0x0144 }
            boolean r0 = r0.containsKey(r7)     // Catch:{ all -> 0x0144 }
            if (r0 == 0) goto L_0x0136
            java.util.Hashtable<java.lang.String, java.util.ArrayList<com.loc.bj$a>> r0 = r6.b     // Catch:{ all -> 0x0144 }
            java.lang.Object r0 = r0.get(r7)     // Catch:{ all -> 0x0144 }
            java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch:{ all -> 0x0144 }
            r0.add(r1)     // Catch:{ all -> 0x0144 }
        L_0x00f8:
            if (r11 == 0) goto L_0x0008
            com.loc.bk r0 = com.loc.bk.a()     // Catch:{ Exception -> 0x0103 }
            r0.a((java.lang.String) r7, (com.autonavi.aps.amapapi.model.AmapLoc) r9, (java.lang.StringBuilder) r8, (android.content.Context) r10)     // Catch:{ Exception -> 0x0103 }
            goto L_0x0008
        L_0x0103:
            r0 = move-exception
            goto L_0x0008
        L_0x0106:
            float r0 = r9.j()     // Catch:{ all -> 0x0144 }
            r1 = 1092616192(0x41200000, float:10.0)
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 > 0) goto L_0x0085
            goto L_0x0008
        L_0x0112:
            java.lang.String r0 = "cgi"
            boolean r0 = r7.contains(r0)     // Catch:{ all -> 0x0144 }
            if (r0 == 0) goto L_0x00b4
            java.lang.String r0 = ","
            int r0 = r8.indexOf(r0)     // Catch:{ all -> 0x0144 }
            r1 = -1
            if (r0 != r1) goto L_0x0008
            java.lang.String r0 = r9.m()     // Catch:{ all -> 0x0144 }
            java.lang.String r1 = "4"
            boolean r0 = r0.equals(r1)     // Catch:{ all -> 0x0144 }
            if (r0 == 0) goto L_0x00b4
            goto L_0x0008
        L_0x0131:
            java.lang.String r0 = r8.toString()     // Catch:{ all -> 0x0144 }
            goto L_0x00e2
        L_0x0136:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x0144 }
            r0.<init>()     // Catch:{ all -> 0x0144 }
            r0.add(r1)     // Catch:{ all -> 0x0144 }
            java.util.Hashtable<java.lang.String, java.util.ArrayList<com.loc.bj$a>> r1 = r6.b     // Catch:{ all -> 0x0144 }
            r1.put(r7, r0)     // Catch:{ all -> 0x0144 }
            goto L_0x00f8
        L_0x0144:
            r0 = move-exception
            monitor-exit(r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bj.a(java.lang.String, java.lang.StringBuilder, com.autonavi.aps.amapapi.model.AmapLoc, android.content.Context, boolean):void");
    }

    public synchronized AmapLoc a(String str, StringBuilder sb) {
        a aVar;
        AmapLoc amapLoc;
        if (str.contains("gps")) {
            amapLoc = null;
        } else if (b()) {
            c();
            amapLoc = null;
        } else if (this.b.isEmpty()) {
            amapLoc = null;
        } else {
            if (str.contains("cgiwifi")) {
                aVar = a(sb, str);
                if (aVar != null) {
                }
            } else if (str.contains("wifi")) {
                aVar = a(sb, str);
                if (aVar != null) {
                }
            } else if (str.contains("cgi")) {
                if (this.b.containsKey(str)) {
                    aVar = (a) this.b.get(str).get(0);
                } else {
                    aVar = null;
                }
                if (aVar != null) {
                }
            } else {
                aVar = null;
            }
            if (aVar == null || !bw.a(aVar.a())) {
                amapLoc = null;
            } else {
                aVar.a().f("mem");
                if (TextUtils.isEmpty(e.g)) {
                    e.g = String.valueOf(aVar.a().A());
                }
                amapLoc = aVar.a();
            }
        }
        return amapLoc;
    }

    public boolean b() {
        long b2 = bw.b() - this.c;
        if (this.c == 0) {
            return false;
        }
        if (this.b.size() > 360) {
            return true;
        }
        if (b2 > 36000000) {
            return true;
        }
        return false;
    }

    public boolean a(String str, AmapLoc amapLoc) {
        if (TextUtils.isEmpty(str) || !bw.a(amapLoc) || str.startsWith("#") || !str.contains("network")) {
            return false;
        }
        return true;
    }

    public void c() {
        this.c = 0;
        if (!this.b.isEmpty()) {
            this.b.clear();
        }
        this.d = false;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: com.loc.bj$a} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized com.loc.bj.a a(java.lang.StringBuilder r19, java.lang.String r20) {
        /*
            r18 = this;
            monitor-enter(r18)
            r0 = r18
            java.util.Hashtable<java.lang.String, java.util.ArrayList<com.loc.bj$a>> r2 = r0.b     // Catch:{ all -> 0x00ab }
            boolean r2 = r2.isEmpty()     // Catch:{ all -> 0x00ab }
            if (r2 != 0) goto L_0x0011
            boolean r2 = android.text.TextUtils.isEmpty(r19)     // Catch:{ all -> 0x00ab }
            if (r2 == 0) goto L_0x0014
        L_0x0011:
            r2 = 0
        L_0x0012:
            monitor-exit(r18)
            return r2
        L_0x0014:
            r0 = r18
            java.util.Hashtable<java.lang.String, java.util.ArrayList<com.loc.bj$a>> r2 = r0.b     // Catch:{ all -> 0x00ab }
            r0 = r20
            boolean r2 = r2.containsKey(r0)     // Catch:{ all -> 0x00ab }
            if (r2 != 0) goto L_0x0022
            r2 = 0
            goto L_0x0012
        L_0x0022:
            r5 = 0
            java.util.Hashtable r11 = new java.util.Hashtable     // Catch:{ all -> 0x00ab }
            r11.<init>()     // Catch:{ all -> 0x00ab }
            java.util.Hashtable r12 = new java.util.Hashtable     // Catch:{ all -> 0x00ab }
            r12.<init>()     // Catch:{ all -> 0x00ab }
            java.util.Hashtable r13 = new java.util.Hashtable     // Catch:{ all -> 0x00ab }
            r13.<init>()     // Catch:{ all -> 0x00ab }
            r0 = r18
            java.util.Hashtable<java.lang.String, java.util.ArrayList<com.loc.bj$a>> r2 = r0.b     // Catch:{ all -> 0x00ab }
            r0 = r20
            java.lang.Object r2 = r2.get(r0)     // Catch:{ all -> 0x00ab }
            java.util.ArrayList r2 = (java.util.ArrayList) r2     // Catch:{ all -> 0x00ab }
            int r3 = r2.size()     // Catch:{ all -> 0x00ab }
            int r3 = r3 + -1
            r7 = r3
        L_0x0045:
            if (r7 < 0) goto L_0x0155
            java.lang.Object r3 = r2.get(r7)     // Catch:{ all -> 0x00ab }
            r0 = r3
            com.loc.bj$a r0 = (com.loc.bj.a) r0     // Catch:{ all -> 0x00ab }
            r4 = r0
            java.lang.String r3 = r4.b()     // Catch:{ all -> 0x00ab }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x00ab }
            if (r3 == 0) goto L_0x005d
        L_0x0059:
            int r3 = r7 + -1
            r7 = r3
            goto L_0x0045
        L_0x005d:
            r3 = 0
            java.lang.String r6 = r4.b()     // Catch:{ all -> 0x00ab }
            r0 = r18
            r1 = r19
            boolean r6 = r0.c(r6, r1)     // Catch:{ all -> 0x00ab }
            if (r6 == 0) goto L_0x0152
            r3 = 1
            java.lang.String r6 = r4.b()     // Catch:{ all -> 0x00ab }
            r0 = r18
            r1 = r19
            boolean r6 = r0.b(r6, r1)     // Catch:{ all -> 0x00ab }
            if (r6 != 0) goto L_0x00ae
            r6 = r3
        L_0x007c:
            java.lang.String r3 = r4.b()     // Catch:{ all -> 0x00ab }
            r0 = r18
            r0.a((java.lang.String) r3, (java.util.Hashtable<java.lang.String, java.lang.String>) r11)     // Catch:{ all -> 0x00ab }
            java.lang.String r3 = r19.toString()     // Catch:{ all -> 0x00ab }
            r0 = r18
            r0.a((java.lang.String) r3, (java.util.Hashtable<java.lang.String, java.lang.String>) r12)     // Catch:{ all -> 0x00ab }
            r13.clear()     // Catch:{ all -> 0x00ab }
            java.util.Set r3 = r11.keySet()     // Catch:{ all -> 0x00ab }
            java.util.Iterator r8 = r3.iterator()     // Catch:{ all -> 0x00ab }
        L_0x0099:
            boolean r3 = r8.hasNext()     // Catch:{ all -> 0x00ab }
            if (r3 == 0) goto L_0x00ba
            java.lang.Object r3 = r8.next()     // Catch:{ all -> 0x00ab }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x00ab }
            java.lang.String r9 = ""
            r13.put(r3, r9)     // Catch:{ all -> 0x00ab }
            goto L_0x0099
        L_0x00ab:
            r2 = move-exception
            monitor-exit(r18)
            throw r2
        L_0x00ae:
            r2 = r4
        L_0x00af:
            r11.clear()     // Catch:{ all -> 0x00ab }
            r12.clear()     // Catch:{ all -> 0x00ab }
            r13.clear()     // Catch:{ all -> 0x00ab }
            goto L_0x0012
        L_0x00ba:
            java.util.Set r3 = r12.keySet()     // Catch:{ all -> 0x00ab }
            java.util.Iterator r8 = r3.iterator()     // Catch:{ all -> 0x00ab }
        L_0x00c2:
            boolean r3 = r8.hasNext()     // Catch:{ all -> 0x00ab }
            if (r3 == 0) goto L_0x00d4
            java.lang.Object r3 = r8.next()     // Catch:{ all -> 0x00ab }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x00ab }
            java.lang.String r9 = ""
            r13.put(r3, r9)     // Catch:{ all -> 0x00ab }
            goto L_0x00c2
        L_0x00d4:
            java.util.Set r14 = r13.keySet()     // Catch:{ all -> 0x00ab }
            int r3 = r14.size()     // Catch:{ all -> 0x00ab }
            double[] r15 = new double[r3]     // Catch:{ all -> 0x00ab }
            int r3 = r14.size()     // Catch:{ all -> 0x00ab }
            double[] r0 = new double[r3]     // Catch:{ all -> 0x00ab }
            r16 = r0
            r3 = 0
            java.util.Iterator r17 = r14.iterator()     // Catch:{ all -> 0x00ab }
            r10 = r3
        L_0x00ec:
            if (r17 == 0) goto L_0x0118
            boolean r3 = r17.hasNext()     // Catch:{ all -> 0x00ab }
            if (r3 == 0) goto L_0x0118
            java.lang.Object r3 = r17.next()     // Catch:{ all -> 0x00ab }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x00ab }
            boolean r8 = r11.containsKey(r3)     // Catch:{ all -> 0x00ab }
            if (r8 == 0) goto L_0x0112
            r8 = 4607182418800017408(0x3ff0000000000000, double:1.0)
        L_0x0102:
            r15[r10] = r8     // Catch:{ all -> 0x00ab }
            boolean r3 = r12.containsKey(r3)     // Catch:{ all -> 0x00ab }
            if (r3 == 0) goto L_0x0115
            r8 = 4607182418800017408(0x3ff0000000000000, double:1.0)
        L_0x010c:
            r16[r10] = r8     // Catch:{ all -> 0x00ab }
            int r3 = r10 + 1
            r10 = r3
            goto L_0x00ec
        L_0x0112:
            r8 = 0
            goto L_0x0102
        L_0x0115:
            r8 = 0
            goto L_0x010c
        L_0x0118:
            r14.clear()     // Catch:{ all -> 0x00ab }
            r0 = r18
            r1 = r16
            double[] r3 = r0.a((double[]) r15, (double[]) r1)     // Catch:{ all -> 0x00ab }
            r8 = 0
            r8 = r3[r8]     // Catch:{ all -> 0x00ab }
            r14 = 4605380979056443392(0x3fe99999a0000000, double:0.800000011920929)
            int r8 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1))
            if (r8 < 0) goto L_0x0132
            r2 = r4
            goto L_0x00af
        L_0x0132:
            r8 = 1
            r8 = r3[r8]     // Catch:{ all -> 0x00ab }
            r14 = 4603741668684706349(0x3fe3c6a7ef9db22d, double:0.618)
            int r8 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1))
            if (r8 < 0) goto L_0x0141
            r2 = r4
            goto L_0x00af
        L_0x0141:
            if (r6 == 0) goto L_0x0059
            r6 = 0
            r8 = r3[r6]     // Catch:{ all -> 0x00ab }
            r14 = 4603741668684706349(0x3fe3c6a7ef9db22d, double:0.618)
            int r3 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1))
            if (r3 < 0) goto L_0x0059
            r2 = r4
            goto L_0x00af
        L_0x0152:
            r6 = r3
            goto L_0x007c
        L_0x0155:
            r2 = r5
            goto L_0x00af
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bj.a(java.lang.StringBuilder, java.lang.String):com.loc.bj$a");
    }

    private boolean c(String str, StringBuilder sb) {
        String str2;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(sb)) {
            return false;
        }
        if (!str.contains(",access") || sb.indexOf(",access") == -1) {
            return false;
        }
        String[] split = str.split(",access");
        if (split[0].contains("#")) {
            str2 = split[0].substring(split[0].lastIndexOf("#") + 1);
        } else {
            str2 = split[0];
        }
        if (TextUtils.isEmpty(str2)) {
            return false;
        }
        return sb.toString().contains(str2 + ",access");
    }

    private void a(String str, Hashtable<String, String> hashtable) {
        if (!TextUtils.isEmpty(str)) {
            hashtable.clear();
            for (String str2 : str.split("#")) {
                if (!TextUtils.isEmpty(str2) && !str2.contains("|")) {
                    hashtable.put(str2, "");
                }
            }
        }
    }

    private double[] a(double[] dArr, double[] dArr2) {
        double[] dArr3 = new double[3];
        double d2 = 0.0d;
        double d3 = 0.0d;
        double d4 = 0.0d;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < dArr.length; i3++) {
            d3 += dArr[i3] * dArr[i3];
            d4 += dArr2[i3] * dArr2[i3];
            d2 += dArr[i3] * dArr2[i3];
            if (dArr2[i3] == 1.0d) {
                i++;
                if (dArr[i3] == 1.0d) {
                    i2++;
                }
            }
        }
        dArr3[0] = d2 / (Math.sqrt(d4) * Math.sqrt(d3));
        dArr3[1] = (1.0d * ((double) i2)) / ((double) i);
        dArr3[2] = (double) i2;
        for (int i4 = 0; i4 < dArr3.length - 1; i4++) {
            if (dArr3[i4] > 1.0d) {
                dArr3[i4] = 1.0d;
            }
            dArr3[i4] = Double.parseDouble(bw.a((Object) Double.valueOf(dArr3[i4]), "#.00"));
        }
        return dArr3;
    }

    public boolean b(String str, StringBuilder sb) {
        String[] split = str.split("#");
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < split.length; i++) {
            if (split[i].contains(",nb") || split[i].contains(",access")) {
                arrayList.add(split[i]);
            }
        }
        String[] split2 = sb.toString().split("#");
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < split2.length; i4++) {
            if (split2[i4].contains(",nb") || split2[i4].contains(",access")) {
                i2++;
                if (arrayList.contains(split2[i4])) {
                    i3++;
                }
            }
        }
        if (((double) (i3 * 2)) >= ((double) (arrayList.size() + i2)) * 0.618d) {
            return true;
        }
        return false;
    }

    public void a(Context context) {
        if (!this.d) {
            bw.b();
            try {
                bk.a().a(context);
            } catch (Exception e) {
            }
            this.d = true;
        }
    }

    /* compiled from: Cache */
    public class a {
        private AmapLoc b = null;
        private String c = null;

        protected a() {
        }

        public AmapLoc a() {
            return this.b;
        }

        public void a(AmapLoc amapLoc) {
            this.b = amapLoc;
        }

        public String b() {
            return this.c;
        }

        public void a(String str) {
            if (TextUtils.isEmpty(str)) {
                this.c = null;
            } else {
                this.c = str.replace("##", "#");
            }
        }
    }
}

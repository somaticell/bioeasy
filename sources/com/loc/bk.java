package com.loc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.alipay.sdk.sys.a;
import com.alipay.sdk.util.h;
import com.autonavi.aps.amapapi.model.AmapLoc;
import org.json.JSONObject;

/* compiled from: DB */
public class bk {
    private static bk a = null;
    private String b = "2.0.201501131131".replace(".", "");
    private String c = null;

    public static synchronized bk a() {
        bk bkVar;
        synchronized (bk.class) {
            if (a == null) {
                a = new bk();
            }
            bkVar = a;
        }
        return bkVar;
    }

    /* access modifiers changed from: protected */
    public void a(String str, AmapLoc amapLoc, StringBuilder sb, Context context) throws Exception {
        String str2;
        if (context != null) {
            if (this.c == null) {
                this.c = bh.a("MD5", context.getPackageName());
            }
            JSONObject jSONObject = new JSONObject();
            if (str.contains(a.b)) {
                str = str.substring(0, str.indexOf(a.b));
            }
            String substring = str.substring(str.lastIndexOf("#") + 1);
            if (substring.equals("cgi")) {
                jSONObject.put("cgi", str.substring(0, str.length() - (("network".length() + 2) + "cgi".length())));
            } else if (!TextUtils.isEmpty(sb) && sb.indexOf("access") != -1) {
                jSONObject.put("cgi", str.substring(0, str.length() - (substring.length() + ("network".length() + 2))));
                String[] split = sb.toString().split(",access");
                if (split[0].contains("#")) {
                    str2 = split[0].substring(split[0].lastIndexOf("#") + 1);
                } else {
                    str2 = split[0];
                }
                jSONObject.put("mmac", str2);
            }
            if (bw.a(jSONObject, "cgi") || bw.a(jSONObject, "mmac")) {
                StringBuilder sb2 = new StringBuilder();
                SQLiteDatabase openOrCreateDatabase = context.openOrCreateDatabase("hmdb", 0, (SQLiteDatabase.CursorFactory) null);
                sb2.append("CREATE TABLE IF NOT EXISTS ").append("hist");
                sb2.append(this.b);
                sb2.append(" (feature VARCHAR PRIMARY KEY, nb VARCHAR, loc VARCHAR, time VARCHAR);");
                openOrCreateDatabase.execSQL(sb2.toString());
                sb2.delete(0, sb2.length());
                sb2.append("REPLACE INTO ");
                sb2.append("hist").append(this.b);
                sb2.append(" VALUES (?, ?, ?, ?)");
                Object[] objArr = {bh.c(jSONObject.toString().getBytes("UTF-8"), this.c), bh.c(sb.toString().getBytes("UTF-8"), this.c), bh.c(amapLoc.E().getBytes("UTF-8"), this.c), Long.valueOf(amapLoc.k())};
                for (int i = 0; i < objArr.length - 1; i++) {
                    objArr[i] = r.a((byte[]) objArr[i]);
                }
                openOrCreateDatabase.execSQL(sb2.toString(), objArr);
                sb2.delete(0, sb2.length());
                sb2.append("SELECT COUNT(*) AS total FROM ");
                sb2.append("hist").append(this.b).append(h.b);
                Cursor rawQuery = openOrCreateDatabase.rawQuery(sb2.toString(), (String[]) null);
                if (rawQuery == null || rawQuery.moveToFirst()) {
                }
                if (rawQuery != null) {
                    rawQuery.close();
                }
                sb2.delete(0, sb2.length());
                if (openOrCreateDatabase != null && openOrCreateDatabase.isOpen()) {
                    openOrCreateDatabase.close();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x02c0 A[LOOP:0: B:29:0x007f->B:79:0x02c0, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x016b A[EDGE_INSN: B:80:0x016b->B:47:0x016b ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(android.content.Context r11) throws java.lang.Exception {
        /*
            r10 = this;
            r2 = 0
            r1 = 0
            monitor-enter(r10)
            if (r11 != 0) goto L_0x0007
        L_0x0005:
            monitor-exit(r10)
            return
        L_0x0007:
            java.lang.String r0 = "hmdb"
            r3 = 0
            r4 = 0
            android.database.sqlite.SQLiteDatabase r8 = r11.openOrCreateDatabase(r0, r3, r4)     // Catch:{ all -> 0x0023 }
            java.lang.String r0 = "hist"
            boolean r0 = r10.a((android.database.sqlite.SQLiteDatabase) r8, (java.lang.String) r0)     // Catch:{ all -> 0x0023 }
            if (r0 != 0) goto L_0x0026
            if (r8 == 0) goto L_0x0005
            boolean r0 = r8.isOpen()     // Catch:{ all -> 0x0023 }
            if (r0 == 0) goto L_0x0005
            r8.close()     // Catch:{ all -> 0x0023 }
            goto L_0x0005
        L_0x0023:
            r0 = move-exception
            monitor-exit(r10)
            throw r0
        L_0x0026:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x0023 }
            r9.<init>()     // Catch:{ all -> 0x0023 }
            java.lang.String r0 = "SELECT feature, nb, loc FROM "
            r9.append(r0)     // Catch:{ all -> 0x0023 }
            java.lang.String r0 = "hist"
            java.lang.StringBuilder r0 = r9.append(r0)     // Catch:{ all -> 0x0023 }
            java.lang.String r3 = r10.b     // Catch:{ all -> 0x0023 }
            r0.append(r3)     // Catch:{ all -> 0x0023 }
            long r4 = com.loc.bw.a()     // Catch:{ all -> 0x0023 }
            r6 = 259200000(0xf731400, double:1.280618154E-315)
            long r4 = r4 - r6
            java.lang.String r0 = " WHERE time > "
            java.lang.StringBuilder r0 = r9.append(r0)     // Catch:{ all -> 0x0023 }
            r0.append(r4)     // Catch:{ all -> 0x0023 }
            java.lang.String r0 = " ORDER BY time ASC"
            java.lang.StringBuilder r0 = r9.append(r0)     // Catch:{ all -> 0x0023 }
            java.lang.String r3 = ";"
            r0.append(r3)     // Catch:{ all -> 0x0023 }
            java.lang.String r0 = r9.toString()     // Catch:{ SQLiteException -> 0x018d }
            r3 = 0
            android.database.Cursor r0 = r8.rawQuery(r0, r3)     // Catch:{ SQLiteException -> 0x018d }
            r7 = r0
        L_0x0061:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0023 }
            r2.<init>()     // Catch:{ all -> 0x0023 }
            java.lang.String r0 = r10.c     // Catch:{ all -> 0x0023 }
            if (r0 != 0) goto L_0x0076
            java.lang.String r0 = "MD5"
            java.lang.String r3 = r11.getPackageName()     // Catch:{ all -> 0x0023 }
            java.lang.String r0 = com.loc.bh.a((java.lang.String) r0, (java.lang.String) r3)     // Catch:{ all -> 0x0023 }
            r10.c = r0     // Catch:{ all -> 0x0023 }
        L_0x0076:
            if (r7 == 0) goto L_0x016b
            boolean r0 = r7.moveToFirst()     // Catch:{ all -> 0x0023 }
            if (r0 == 0) goto L_0x016b
            r3 = r1
        L_0x007f:
            r0 = 0
            java.lang.String r0 = r7.getString(r0)     // Catch:{ all -> 0x0023 }
            java.lang.String r1 = "{"
            boolean r0 = r0.startsWith(r1)     // Catch:{ all -> 0x0023 }
            if (r0 == 0) goto L_0x01c1
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ all -> 0x0023 }
            r0 = 0
            java.lang.String r0 = r7.getString(r0)     // Catch:{ all -> 0x0023 }
            r1.<init>(r0)     // Catch:{ all -> 0x0023 }
            r0 = 0
            int r4 = r2.length()     // Catch:{ all -> 0x0023 }
            r2.delete(r0, r4)     // Catch:{ all -> 0x0023 }
            r0 = 1
            java.lang.String r0 = r7.getString(r0)     // Catch:{ all -> 0x0023 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0023 }
            if (r0 != 0) goto L_0x01a3
            r0 = 1
            java.lang.String r0 = r7.getString(r0)     // Catch:{ all -> 0x0023 }
            r2.append(r0)     // Catch:{ all -> 0x0023 }
        L_0x00b2:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ all -> 0x0023 }
            r4 = 2
            java.lang.String r4 = r7.getString(r4)     // Catch:{ all -> 0x0023 }
            r0.<init>(r4)     // Catch:{ all -> 0x0023 }
            java.lang.String r4 = "type"
            boolean r4 = com.loc.bw.a((org.json.JSONObject) r0, (java.lang.String) r4)     // Catch:{ all -> 0x0023 }
            if (r4 == 0) goto L_0x00cd
            java.lang.String r4 = "type"
            java.lang.String r5 = "new"
            r0.put(r4, r5)     // Catch:{ all -> 0x0023 }
        L_0x00cd:
            int r6 = r3 + 1
            com.autonavi.aps.amapapi.model.AmapLoc r3 = new com.autonavi.aps.amapapi.model.AmapLoc     // Catch:{ all -> 0x0023 }
            r3.<init>((org.json.JSONObject) r0)     // Catch:{ all -> 0x0023 }
            java.lang.String r0 = ""
            java.lang.String r0 = "mmac"
            boolean r0 = com.loc.bw.a((org.json.JSONObject) r1, (java.lang.String) r0)     // Catch:{ all -> 0x0023 }
            if (r0 == 0) goto L_0x0269
            java.lang.String r0 = "cgi"
            boolean r0 = com.loc.bw.a((org.json.JSONObject) r1, (java.lang.String) r0)     // Catch:{ all -> 0x0023 }
            if (r0 == 0) goto L_0x0269
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0023 }
            r0.<init>()     // Catch:{ all -> 0x0023 }
            java.lang.String r4 = "cgi"
            java.lang.String r4 = r1.getString(r4)     // Catch:{ all -> 0x0023 }
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x0023 }
            java.lang.String r4 = "#"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x0023 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0023 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0023 }
            r4.<init>()     // Catch:{ all -> 0x0023 }
            java.lang.StringBuilder r0 = r4.append(r0)     // Catch:{ all -> 0x0023 }
            java.lang.String r4 = "network#"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x0023 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0023 }
            java.lang.String r4 = "cgi"
            java.lang.String r1 = r1.getString(r4)     // Catch:{ all -> 0x0023 }
            java.lang.String r4 = "#"
            boolean r1 = r1.contains(r4)     // Catch:{ all -> 0x0023 }
            if (r1 == 0) goto L_0x0253
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0023 }
            r1.<init>()     // Catch:{ all -> 0x0023 }
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ all -> 0x0023 }
            java.lang.String r1 = "cgiwifi"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ all -> 0x0023 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0023 }
        L_0x0133:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0023 }
            r1.<init>()     // Catch:{ all -> 0x0023 }
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ all -> 0x0023 }
            java.lang.String r1 = "&"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ all -> 0x0023 }
            boolean r1 = r3.e()     // Catch:{ all -> 0x0023 }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ all -> 0x0023 }
            java.lang.String r1 = "&"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ all -> 0x0023 }
            boolean r1 = r3.f()     // Catch:{ all -> 0x0023 }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ all -> 0x0023 }
            java.lang.String r1 = r0.toString()     // Catch:{ all -> 0x0023 }
            com.loc.bj r0 = com.loc.bj.a()     // Catch:{ all -> 0x0023 }
            r5 = 0
            r4 = r11
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ all -> 0x0023 }
        L_0x0165:
            boolean r0 = r7.moveToNext()     // Catch:{ all -> 0x0023 }
            if (r0 != 0) goto L_0x02c0
        L_0x016b:
            r0 = 0
            int r1 = r2.length()     // Catch:{ all -> 0x0023 }
            r2.delete(r0, r1)     // Catch:{ all -> 0x0023 }
            if (r7 == 0) goto L_0x0178
            r7.close()     // Catch:{ all -> 0x0023 }
        L_0x0178:
            r0 = 0
            int r1 = r9.length()     // Catch:{ all -> 0x0023 }
            r9.delete(r0, r1)     // Catch:{ all -> 0x0023 }
            if (r8 == 0) goto L_0x0005
            boolean r0 = r8.isOpen()     // Catch:{ all -> 0x0023 }
            if (r0 == 0) goto L_0x0005
            r8.close()     // Catch:{ all -> 0x0023 }
            goto L_0x0005
        L_0x018d:
            r0 = move-exception
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x0023 }
            boolean r3 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0023 }
            if (r3 != 0) goto L_0x01a0
            java.lang.String r3 = "no such table"
            boolean r0 = r0.contains(r3)     // Catch:{ all -> 0x0023 }
            if (r0 == 0) goto L_0x01a0
        L_0x01a0:
            r7 = r2
            goto L_0x0061
        L_0x01a3:
            java.lang.String r0 = "mmac"
            boolean r0 = com.loc.bw.a((org.json.JSONObject) r1, (java.lang.String) r0)     // Catch:{ all -> 0x0023 }
            if (r0 == 0) goto L_0x00b2
            java.lang.String r0 = "#"
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ all -> 0x0023 }
            java.lang.String r4 = "mmac"
            java.lang.String r4 = r1.getString(r4)     // Catch:{ all -> 0x0023 }
            r0.append(r4)     // Catch:{ all -> 0x0023 }
            java.lang.String r0 = ",access"
            r2.append(r0)     // Catch:{ all -> 0x0023 }
            goto L_0x00b2
        L_0x01c1:
            r0 = 0
            java.lang.String r0 = r7.getString(r0)     // Catch:{ all -> 0x0023 }
            byte[] r0 = com.loc.r.a((java.lang.String) r0)     // Catch:{ all -> 0x0023 }
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ all -> 0x0023 }
            java.lang.String r4 = new java.lang.String     // Catch:{ all -> 0x0023 }
            java.lang.String r5 = r10.c     // Catch:{ all -> 0x0023 }
            byte[] r0 = com.loc.bh.d(r0, r5)     // Catch:{ all -> 0x0023 }
            java.lang.String r5 = "UTF-8"
            r4.<init>(r0, r5)     // Catch:{ all -> 0x0023 }
            r1.<init>(r4)     // Catch:{ all -> 0x0023 }
            r0 = 0
            int r4 = r2.length()     // Catch:{ all -> 0x0023 }
            r2.delete(r0, r4)     // Catch:{ all -> 0x0023 }
            r0 = 1
            java.lang.String r0 = r7.getString(r0)     // Catch:{ all -> 0x0023 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0023 }
            if (r0 != 0) goto L_0x0236
            r0 = 1
            java.lang.String r0 = r7.getString(r0)     // Catch:{ all -> 0x0023 }
            byte[] r0 = com.loc.r.a((java.lang.String) r0)     // Catch:{ all -> 0x0023 }
            java.lang.String r4 = new java.lang.String     // Catch:{ all -> 0x0023 }
            java.lang.String r5 = r10.c     // Catch:{ all -> 0x0023 }
            byte[] r0 = com.loc.bh.d(r0, r5)     // Catch:{ all -> 0x0023 }
            java.lang.String r5 = "UTF-8"
            r4.<init>(r0, r5)     // Catch:{ all -> 0x0023 }
            r2.append(r4)     // Catch:{ all -> 0x0023 }
        L_0x0208:
            r0 = 2
            java.lang.String r0 = r7.getString(r0)     // Catch:{ all -> 0x0023 }
            byte[] r4 = com.loc.r.a((java.lang.String) r0)     // Catch:{ all -> 0x0023 }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ all -> 0x0023 }
            java.lang.String r5 = new java.lang.String     // Catch:{ all -> 0x0023 }
            java.lang.String r6 = r10.c     // Catch:{ all -> 0x0023 }
            byte[] r4 = com.loc.bh.d(r4, r6)     // Catch:{ all -> 0x0023 }
            java.lang.String r6 = "UTF-8"
            r5.<init>(r4, r6)     // Catch:{ all -> 0x0023 }
            r0.<init>(r5)     // Catch:{ all -> 0x0023 }
            java.lang.String r4 = "type"
            boolean r4 = com.loc.bw.a((org.json.JSONObject) r0, (java.lang.String) r4)     // Catch:{ all -> 0x0023 }
            if (r4 == 0) goto L_0x00cd
            java.lang.String r4 = "type"
            java.lang.String r5 = "new"
            r0.put(r4, r5)     // Catch:{ all -> 0x0023 }
            goto L_0x00cd
        L_0x0236:
            java.lang.String r0 = "mmac"
            boolean r0 = com.loc.bw.a((org.json.JSONObject) r1, (java.lang.String) r0)     // Catch:{ all -> 0x0023 }
            if (r0 == 0) goto L_0x0208
            java.lang.String r0 = "#"
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ all -> 0x0023 }
            java.lang.String r4 = "mmac"
            java.lang.String r4 = r1.getString(r4)     // Catch:{ all -> 0x0023 }
            r0.append(r4)     // Catch:{ all -> 0x0023 }
            java.lang.String r0 = ",access"
            r2.append(r0)     // Catch:{ all -> 0x0023 }
            goto L_0x0208
        L_0x0253:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0023 }
            r1.<init>()     // Catch:{ all -> 0x0023 }
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ all -> 0x0023 }
            java.lang.String r1 = "wifi"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ all -> 0x0023 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0023 }
            goto L_0x0133
        L_0x0269:
            java.lang.String r0 = "cgi"
            boolean r0 = com.loc.bw.a((org.json.JSONObject) r1, (java.lang.String) r0)     // Catch:{ all -> 0x0023 }
            if (r0 == 0) goto L_0x0165
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0023 }
            r0.<init>()     // Catch:{ all -> 0x0023 }
            java.lang.String r4 = "cgi"
            java.lang.String r4 = r1.getString(r4)     // Catch:{ all -> 0x0023 }
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x0023 }
            java.lang.String r4 = "#"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x0023 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0023 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0023 }
            r4.<init>()     // Catch:{ all -> 0x0023 }
            java.lang.StringBuilder r0 = r4.append(r0)     // Catch:{ all -> 0x0023 }
            java.lang.String r4 = "network#"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x0023 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0023 }
            java.lang.String r4 = "cgi"
            java.lang.String r1 = r1.getString(r4)     // Catch:{ all -> 0x0023 }
            java.lang.String r4 = "#"
            boolean r1 = r1.contains(r4)     // Catch:{ all -> 0x0023 }
            if (r1 == 0) goto L_0x0165
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0023 }
            r1.<init>()     // Catch:{ all -> 0x0023 }
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ all -> 0x0023 }
            java.lang.String r1 = "cgi"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ all -> 0x0023 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0023 }
            goto L_0x0133
        L_0x02c0:
            r3 = r6
            goto L_0x007f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bk.a(android.content.Context):void");
    }

    public void a(Context context, int i) throws Exception {
        String str;
        String[] strArr = null;
        if (context != null) {
            SQLiteDatabase openOrCreateDatabase = context.openOrCreateDatabase("hmdb", 0, (SQLiteDatabase.CursorFactory) null);
            if (a(openOrCreateDatabase, "hist")) {
                switch (i) {
                    case 1:
                        str = "time<?";
                        strArr = new String[]{String.valueOf(bw.a() - 259200000)};
                        break;
                    case 2:
                        str = com.alipay.sdk.cons.a.e;
                        break;
                    default:
                        str = null;
                        break;
                }
                try {
                    openOrCreateDatabase.delete("hist" + this.b, str, strArr);
                } catch (SQLiteException e) {
                    String message = e.getMessage();
                    if (TextUtils.isEmpty(message) || message.contains("no such table")) {
                    }
                }
                if (openOrCreateDatabase != null && openOrCreateDatabase.isOpen()) {
                    openOrCreateDatabase.close();
                }
            } else if (openOrCreateDatabase != null && openOrCreateDatabase.isOpen()) {
                openOrCreateDatabase.close();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004f, code lost:
        r0 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0057, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0058, code lost:
        if (r2 != null) goto L_0x005a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x005a, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x005d, code lost:
        throw r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0057 A[ExcHandler: all (r0v1 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:2:0x000a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(android.database.sqlite.SQLiteDatabase r7, java.lang.String r8) {
        /*
            r6 = this;
            r2 = 0
            r1 = 1
            r0 = 0
            boolean r3 = android.text.TextUtils.isEmpty(r8)
            if (r3 == 0) goto L_0x000a
        L_0x0009:
            return r0
        L_0x000a:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x004e, all -> 0x0057 }
            r3.<init>()     // Catch:{ Exception -> 0x004e, all -> 0x0057 }
            java.lang.String r4 = "SELECT count(*) as c FROM sqlite_master WHERE type = 'table' AND name = '"
            r3.append(r4)     // Catch:{ Exception -> 0x004e, all -> 0x0057 }
            java.lang.String r4 = r8.trim()     // Catch:{ Exception -> 0x004e, all -> 0x0057 }
            java.lang.StringBuilder r4 = r3.append(r4)     // Catch:{ Exception -> 0x004e, all -> 0x0057 }
            java.lang.String r5 = r6.b     // Catch:{ Exception -> 0x004e, all -> 0x0057 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x004e, all -> 0x0057 }
            java.lang.String r5 = "' "
            r4.append(r5)     // Catch:{ Exception -> 0x004e, all -> 0x0057 }
            java.lang.String r4 = r3.toString()     // Catch:{ Exception -> 0x004e, all -> 0x0057 }
            r5 = 0
            android.database.Cursor r2 = r7.rawQuery(r4, r5)     // Catch:{ Exception -> 0x004e, all -> 0x0057 }
            if (r2 == 0) goto L_0x0040
            boolean r4 = r2.moveToFirst()     // Catch:{ Exception -> 0x005e, all -> 0x0057 }
            if (r4 == 0) goto L_0x0040
            r4 = 0
            int r4 = r2.getInt(r4)     // Catch:{ Exception -> 0x005e, all -> 0x0057 }
            if (r4 <= 0) goto L_0x0040
            r0 = r1
        L_0x0040:
            r4 = 0
            int r5 = r3.length()     // Catch:{ Exception -> 0x005e, all -> 0x0057 }
            r3.delete(r4, r5)     // Catch:{ Exception -> 0x005e, all -> 0x0057 }
            if (r2 == 0) goto L_0x0009
            r2.close()
            goto L_0x0009
        L_0x004e:
            r0 = move-exception
            r0 = r2
        L_0x0050:
            if (r0 == 0) goto L_0x0061
            r0.close()
            r0 = r1
            goto L_0x0009
        L_0x0057:
            r0 = move-exception
            if (r2 == 0) goto L_0x005d
            r2.close()
        L_0x005d:
            throw r0
        L_0x005e:
            r0 = move-exception
            r0 = r2
            goto L_0x0050
        L_0x0061:
            r0 = r1
            goto L_0x0009
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bk.a(android.database.sqlite.SQLiteDatabase, java.lang.String):boolean");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x007d A[Catch:{ SQLiteException -> 0x00ea, all -> 0x013c }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00cf A[Catch:{ SQLiteException -> 0x00ea, all -> 0x013c }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00dc A[SYNTHETIC, Splitter:B:28:0x00dc] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00ff A[Catch:{ SQLiteException -> 0x00ea, all -> 0x013c }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x013f A[SYNTHETIC, Splitter:B:45:0x013f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(android.content.Context r9, java.lang.String r10, java.lang.String r11, long r12) throws java.lang.Exception {
        /*
            r8 = this;
            r2 = 0
            r3 = 0
            monitor-enter(r8)
            boolean r0 = android.text.TextUtils.isEmpty(r10)     // Catch:{ all -> 0x00e7 }
            if (r0 != 0) goto L_0x000b
            if (r9 != 0) goto L_0x000d
        L_0x000b:
            monitor-exit(r8)
            return
        L_0x000d:
            java.lang.String r4 = com.loc.bw.c((java.lang.String) r10)     // Catch:{ all -> 0x00e7 }
            java.lang.String r5 = com.loc.bw.c((java.lang.String) r11)     // Catch:{ all -> 0x00e7 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0149 }
            r6.<init>()     // Catch:{ all -> 0x0149 }
            java.lang.String r0 = "hmdb"
            r1 = 0
            r7 = 0
            android.database.sqlite.SQLiteDatabase r1 = r9.openOrCreateDatabase(r0, r1, r7)     // Catch:{ all -> 0x0149 }
            java.lang.String r0 = "CREATE TABLE IF NOT EXISTS "
            java.lang.StringBuilder r0 = r6.append(r0)     // Catch:{ all -> 0x013c }
            java.lang.String r7 = "hm"
            r0.append(r7)     // Catch:{ all -> 0x013c }
            java.lang.String r0 = r8.b     // Catch:{ all -> 0x013c }
            r6.append(r0)     // Catch:{ all -> 0x013c }
            java.lang.String r0 = " (hash VARCHAR PRIMARY KEY, num INTEGER, extra VARCHAR, time VARCHAR);"
            r6.append(r0)     // Catch:{ all -> 0x013c }
            java.lang.String r0 = r6.toString()     // Catch:{ all -> 0x013c }
            r1.execSQL(r0)     // Catch:{ all -> 0x013c }
            r0 = 0
            int r7 = r6.length()     // Catch:{ all -> 0x013c }
            r6.delete(r0, r7)     // Catch:{ all -> 0x013c }
            java.lang.String r0 = "SELECT num FROM "
            java.lang.StringBuilder r0 = r6.append(r0)     // Catch:{ all -> 0x013c }
            java.lang.String r7 = "hm"
            r0.append(r7)     // Catch:{ all -> 0x013c }
            java.lang.String r0 = r8.b     // Catch:{ all -> 0x013c }
            r6.append(r0)     // Catch:{ all -> 0x013c }
            java.lang.String r0 = " WHERE hash = '"
            java.lang.StringBuilder r0 = r6.append(r0)     // Catch:{ all -> 0x013c }
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x013c }
            java.lang.String r7 = "';"
            r0.append(r7)     // Catch:{ all -> 0x013c }
            java.lang.String r0 = r6.toString()     // Catch:{ SQLiteException -> 0x00ea }
            r7 = 0
            android.database.Cursor r2 = r1.rawQuery(r0, r7)     // Catch:{ SQLiteException -> 0x00ea }
        L_0x006e:
            if (r2 == 0) goto L_0x014c
            boolean r0 = r2.moveToNext()     // Catch:{ all -> 0x013c }
            if (r0 == 0) goto L_0x014c
            r0 = 0
            int r0 = r2.getInt(r0)     // Catch:{ all -> 0x013c }
        L_0x007b:
            if (r0 <= 0) goto L_0x00ff
            int r0 = r0 + 1
            android.content.ContentValues r3 = new android.content.ContentValues     // Catch:{ all -> 0x013c }
            r3.<init>()     // Catch:{ all -> 0x013c }
            java.lang.String r7 = "num"
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x013c }
            r3.put(r7, r0)     // Catch:{ all -> 0x013c }
            java.lang.String r0 = "extra"
            r3.put(r0, r5)     // Catch:{ all -> 0x013c }
            java.lang.String r0 = "time"
            java.lang.Long r5 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x013c }
            r3.put(r0, r5)     // Catch:{ all -> 0x013c }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x013c }
            r0.<init>()     // Catch:{ all -> 0x013c }
            java.lang.String r5 = "hash = '"
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch:{ all -> 0x013c }
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x013c }
            java.lang.String r4 = "'"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x013c }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x013c }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x013c }
            r4.<init>()     // Catch:{ all -> 0x013c }
            java.lang.String r5 = "hm"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x013c }
            java.lang.String r5 = r8.b     // Catch:{ all -> 0x013c }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x013c }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x013c }
            r5 = 0
            r1.update(r4, r3, r0, r5)     // Catch:{ all -> 0x013c }
        L_0x00cd:
            if (r2 == 0) goto L_0x00d2
            r2.close()     // Catch:{ all -> 0x013c }
        L_0x00d2:
            r0 = 0
            int r2 = r6.length()     // Catch:{ all -> 0x013c }
            r6.delete(r0, r2)     // Catch:{ all -> 0x013c }
            if (r1 == 0) goto L_0x000b
            boolean r0 = r1.isOpen()     // Catch:{ all -> 0x00e7 }
            if (r0 == 0) goto L_0x000b
            r1.close()     // Catch:{ all -> 0x00e7 }
            goto L_0x000b
        L_0x00e7:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        L_0x00ea:
            r0 = move-exception
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x013c }
            boolean r7 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x013c }
            if (r7 != 0) goto L_0x006e
            java.lang.String r7 = "no such table"
            boolean r0 = r0.contains(r7)     // Catch:{ all -> 0x013c }
            if (r0 == 0) goto L_0x006e
            goto L_0x006e
        L_0x00ff:
            r0 = 1
            r3 = 0
            int r7 = r6.length()     // Catch:{ all -> 0x013c }
            r6.delete(r3, r7)     // Catch:{ all -> 0x013c }
            java.lang.String r3 = "REPLACE INTO "
            r6.append(r3)     // Catch:{ all -> 0x013c }
            java.lang.String r3 = "hm"
            java.lang.StringBuilder r3 = r6.append(r3)     // Catch:{ all -> 0x013c }
            java.lang.String r7 = r8.b     // Catch:{ all -> 0x013c }
            r3.append(r7)     // Catch:{ all -> 0x013c }
            java.lang.String r3 = " VALUES (?, ?, ?, ?)"
            r6.append(r3)     // Catch:{ all -> 0x013c }
            r3 = 4
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x013c }
            r7 = 0
            r3[r7] = r4     // Catch:{ all -> 0x013c }
            r4 = 1
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x013c }
            r3[r4] = r0     // Catch:{ all -> 0x013c }
            r0 = 2
            r3[r0] = r5     // Catch:{ all -> 0x013c }
            r0 = 3
            java.lang.Long r4 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x013c }
            r3[r0] = r4     // Catch:{ all -> 0x013c }
            java.lang.String r0 = r6.toString()     // Catch:{ all -> 0x013c }
            r1.execSQL(r0, r3)     // Catch:{ all -> 0x013c }
            goto L_0x00cd
        L_0x013c:
            r0 = move-exception
        L_0x013d:
            if (r1 == 0) goto L_0x0148
            boolean r2 = r1.isOpen()     // Catch:{ all -> 0x00e7 }
            if (r2 == 0) goto L_0x0148
            r1.close()     // Catch:{ all -> 0x00e7 }
        L_0x0148:
            throw r0     // Catch:{ all -> 0x00e7 }
        L_0x0149:
            r0 = move-exception
            r1 = r2
            goto L_0x013d
        L_0x014c:
            r0 = r3
            goto L_0x007b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bk.a(android.content.Context, java.lang.String, java.lang.String, long):void");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00e4 A[SYNTHETIC, Splitter:B:59:0x00e4] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void b(android.content.Context r12) throws java.lang.Exception {
        /*
            r11 = this;
            r1 = 0
            r2 = 0
            monitor-enter(r11)
            boolean r0 = com.loc.az.a     // Catch:{ all -> 0x0033 }
            if (r0 == 0) goto L_0x0009
            if (r12 != 0) goto L_0x000b
        L_0x0009:
            monitor-exit(r11)
            return
        L_0x000b:
            java.lang.String r0 = "hmdb"
            r3 = 0
            r4 = 0
            android.database.sqlite.SQLiteDatabase r9 = r12.openOrCreateDatabase(r0, r3, r4)     // Catch:{ all -> 0x00e1 }
            java.lang.String r0 = "hm"
            boolean r0 = r11.a((android.database.sqlite.SQLiteDatabase) r9, (java.lang.String) r0)     // Catch:{ all -> 0x00ee }
            if (r0 != 0) goto L_0x0036
            if (r9 == 0) goto L_0x0026
            boolean r0 = r9.isOpen()     // Catch:{ all -> 0x00ee }
            if (r0 == 0) goto L_0x0026
            r9.close()     // Catch:{ all -> 0x00ee }
        L_0x0026:
            r0 = 0
            if (r1 == 0) goto L_0x0009
            boolean r1 = r0.isOpen()     // Catch:{ all -> 0x0033 }
            if (r1 == 0) goto L_0x0009
            r0.close()     // Catch:{ all -> 0x0033 }
            goto L_0x0009
        L_0x0033:
            r0 = move-exception
            monitor-exit(r11)
            throw r0
        L_0x0036:
            long r4 = com.loc.bw.a()     // Catch:{ all -> 0x00ee }
            r6 = 1209600000(0x48190800, double:5.97621805E-315)
            long r4 = r4 - r6
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ee }
            r3.<init>()     // Catch:{ all -> 0x00ee }
            java.lang.String r0 = "SELECT hash, num, extra, time FROM "
            r3.append(r0)     // Catch:{ all -> 0x00ee }
            java.lang.String r0 = "hm"
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch:{ all -> 0x00ee }
            java.lang.String r6 = r11.b     // Catch:{ all -> 0x00ee }
            r0.append(r6)     // Catch:{ all -> 0x00ee }
            java.lang.String r0 = " WHERE time > "
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch:{ all -> 0x00ee }
            r0.append(r4)     // Catch:{ all -> 0x00ee }
            java.lang.String r0 = " ORDER BY num DESC LIMIT 0,"
            r3.append(r0)     // Catch:{ all -> 0x00ee }
            r0 = 500(0x1f4, float:7.0E-43)
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch:{ all -> 0x00ee }
            java.lang.String r4 = ";"
            r0.append(r4)     // Catch:{ all -> 0x00ee }
            java.lang.String r0 = r3.toString()     // Catch:{ SQLiteException -> 0x00cc }
            r4 = 0
            android.database.Cursor r1 = r9.rawQuery(r0, r4)     // Catch:{ SQLiteException -> 0x00cc }
            r10 = r1
        L_0x0076:
            r0 = 0
            int r1 = r3.length()     // Catch:{ all -> 0x00ee }
            r3.delete(r0, r1)     // Catch:{ all -> 0x00ee }
            if (r10 == 0) goto L_0x00ba
            r10.moveToFirst()     // Catch:{ all -> 0x00ee }
            r0 = r2
        L_0x0084:
            int r0 = r0 + 1
            r1 = 0
            java.lang.String r3 = r10.getString(r1)     // Catch:{ all -> 0x00ee }
            r1 = 1
            int r5 = r10.getInt(r1)     // Catch:{ all -> 0x00ee }
            r1 = 2
            java.lang.String r4 = r10.getString(r1)     // Catch:{ all -> 0x00ee }
            r1 = 3
            long r6 = r10.getLong(r1)     // Catch:{ all -> 0x00ee }
            java.lang.String r1 = "{"
            boolean r1 = r4.startsWith(r1)     // Catch:{ all -> 0x00ee }
            if (r1 != 0) goto L_0x00ab
            java.lang.String r3 = com.loc.bw.d((java.lang.String) r3)     // Catch:{ all -> 0x00ee }
            java.lang.String r4 = com.loc.bw.d((java.lang.String) r4)     // Catch:{ all -> 0x00ee }
        L_0x00ab:
            com.loc.bm r1 = com.loc.bm.a()     // Catch:{ all -> 0x00ee }
            r8 = 0
            r2 = r12
            r1.a(r2, r3, r4, r5, r6, r8)     // Catch:{ all -> 0x00ee }
            boolean r1 = r10.moveToNext()     // Catch:{ all -> 0x00ee }
            if (r1 != 0) goto L_0x0084
        L_0x00ba:
            if (r10 == 0) goto L_0x00bf
            r10.close()     // Catch:{ all -> 0x00ee }
        L_0x00bf:
            if (r9 == 0) goto L_0x0009
            boolean r0 = r9.isOpen()     // Catch:{ all -> 0x0033 }
            if (r0 == 0) goto L_0x0009
            r9.close()     // Catch:{ all -> 0x0033 }
            goto L_0x0009
        L_0x00cc:
            r0 = move-exception
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x00ee }
            boolean r4 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x00ee }
            if (r4 != 0) goto L_0x00df
            java.lang.String r4 = "no such table"
            boolean r0 = r0.contains(r4)     // Catch:{ all -> 0x00ee }
            if (r0 == 0) goto L_0x00df
        L_0x00df:
            r10 = r1
            goto L_0x0076
        L_0x00e1:
            r0 = move-exception
        L_0x00e2:
            if (r1 == 0) goto L_0x00ed
            boolean r2 = r1.isOpen()     // Catch:{ all -> 0x0033 }
            if (r2 == 0) goto L_0x00ed
            r1.close()     // Catch:{ all -> 0x0033 }
        L_0x00ed:
            throw r0     // Catch:{ all -> 0x0033 }
        L_0x00ee:
            r0 = move-exception
            r1 = r9
            goto L_0x00e2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bk.b(android.content.Context):void");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0052 A[Catch:{ SQLiteException -> 0x00f2, all -> 0x00ce }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00a7 A[Catch:{ SQLiteException -> 0x00f2, all -> 0x00ce }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00ac A[SYNTHETIC, Splitter:B:44:0x00ac] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00e2 A[SYNTHETIC, Splitter:B:61:0x00e2] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.util.ArrayList<java.lang.String> b(android.content.Context r12, int r13) throws java.lang.Exception {
        /*
            r11 = this;
            r0 = 0
            monitor-enter(r11)
            if (r12 != 0) goto L_0x0006
        L_0x0004:
            monitor-exit(r11)
            return r0
        L_0x0006:
            java.lang.String r1 = "hmdb"
            r2 = 0
            r3 = 0
            android.database.sqlite.SQLiteDatabase r2 = r12.openOrCreateDatabase(r1, r2, r3)     // Catch:{ all -> 0x0106 }
            java.lang.String r1 = "hm"
            boolean r1 = r11.a((android.database.sqlite.SQLiteDatabase) r2, (java.lang.String) r1)     // Catch:{ all -> 0x00ce }
            if (r1 != 0) goto L_0x0031
            if (r2 == 0) goto L_0x0021
            boolean r1 = r2.isOpen()     // Catch:{ all -> 0x00ce }
            if (r1 == 0) goto L_0x0021
            r2.close()     // Catch:{ all -> 0x00ce }
        L_0x0021:
            r1 = 0
            if (r0 == 0) goto L_0x0004
            boolean r2 = r1.isOpen()     // Catch:{ all -> 0x002e }
            if (r2 == 0) goto L_0x0004
            r1.close()     // Catch:{ all -> 0x002e }
            goto L_0x0004
        L_0x002e:
            r0 = move-exception
            monitor-exit(r11)
            throw r0
        L_0x0031:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x00ce }
            r1.<init>()     // Catch:{ all -> 0x00ce }
            switch(r13) {
                case 1: goto L_0x00b8;
                case 2: goto L_0x00dc;
                default: goto L_0x0039;
            }
        L_0x0039:
            r3 = r0
            r4 = r0
        L_0x003b:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x00f2 }
            r5.<init>()     // Catch:{ SQLiteException -> 0x00f2 }
            java.lang.String r6 = "hm"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ SQLiteException -> 0x00f2 }
            java.lang.String r6 = r11.b     // Catch:{ SQLiteException -> 0x00f2 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ SQLiteException -> 0x00f2 }
            java.lang.String r5 = r5.toString()     // Catch:{ SQLiteException -> 0x00f2 }
            if (r3 == 0) goto L_0x00e2
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x00f2 }
            r4.<init>()     // Catch:{ SQLiteException -> 0x00f2 }
            java.lang.String r6 = "SELECT hash, num, extra FROM "
            r4.append(r6)     // Catch:{ SQLiteException -> 0x00f2 }
            r4.append(r5)     // Catch:{ SQLiteException -> 0x00f2 }
            java.lang.String r5 = " WHERE time < "
            java.lang.StringBuilder r5 = r4.append(r5)     // Catch:{ SQLiteException -> 0x00f2 }
            r6 = 0
            r3 = r3[r6]     // Catch:{ SQLiteException -> 0x00f2 }
            java.lang.StringBuilder r3 = r5.append(r3)     // Catch:{ SQLiteException -> 0x00f2 }
            java.lang.String r5 = ";"
            r3.append(r5)     // Catch:{ SQLiteException -> 0x00f2 }
            java.lang.String r3 = r4.toString()     // Catch:{ SQLiteException -> 0x00f2 }
            r4 = 0
            android.database.Cursor r4 = r2.rawQuery(r3, r4)     // Catch:{ SQLiteException -> 0x00f2 }
            if (r4 == 0) goto L_0x00a5
            boolean r3 = r4.moveToFirst()     // Catch:{ SQLiteException -> 0x00f2 }
            if (r3 == 0) goto L_0x00a5
        L_0x0082:
            r3 = 0
            java.lang.String r3 = r4.getString(r3)     // Catch:{ SQLiteException -> 0x00f2 }
            r5 = 2
            java.lang.String r5 = r4.getString(r5)     // Catch:{ SQLiteException -> 0x00f2 }
            java.lang.String r6 = "{"
            boolean r6 = r5.startsWith(r6)     // Catch:{ SQLiteException -> 0x00f2 }
            if (r6 != 0) goto L_0x009c
            java.lang.String r3 = com.loc.bw.d((java.lang.String) r3)     // Catch:{ SQLiteException -> 0x00f2 }
            com.loc.bw.d((java.lang.String) r5)     // Catch:{ SQLiteException -> 0x00f2 }
        L_0x009c:
            r1.add(r3)     // Catch:{ SQLiteException -> 0x00f2 }
            boolean r3 = r4.moveToNext()     // Catch:{ SQLiteException -> 0x00f2 }
            if (r3 != 0) goto L_0x0082
        L_0x00a5:
            if (r4 == 0) goto L_0x00aa
            r4.close()     // Catch:{ SQLiteException -> 0x00f2 }
        L_0x00aa:
            if (r2 == 0) goto L_0x00b5
            boolean r0 = r2.isOpen()     // Catch:{ all -> 0x002e }
            if (r0 == 0) goto L_0x00b5
            r2.close()     // Catch:{ all -> 0x002e }
        L_0x00b5:
            r0 = r1
            goto L_0x0004
        L_0x00b8:
            java.lang.String r4 = "time<?"
            r3 = 1
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ all -> 0x00ce }
            r5 = 0
            long r6 = com.loc.bw.a()     // Catch:{ all -> 0x00ce }
            r8 = 1209600000(0x48190800, double:5.97621805E-315)
            long r6 = r6 - r8
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ all -> 0x00ce }
            r3[r5] = r6     // Catch:{ all -> 0x00ce }
            goto L_0x003b
        L_0x00ce:
            r0 = move-exception
            r1 = r2
        L_0x00d0:
            if (r1 == 0) goto L_0x00db
            boolean r2 = r1.isOpen()     // Catch:{ all -> 0x002e }
            if (r2 == 0) goto L_0x00db
            r1.close()     // Catch:{ all -> 0x002e }
        L_0x00db:
            throw r0     // Catch:{ all -> 0x002e }
        L_0x00dc:
            java.lang.String r3 = "1"
            r4 = r3
            r3 = r0
            goto L_0x003b
        L_0x00e2:
            r2.delete(r5, r4, r3)     // Catch:{ SQLiteException -> 0x00f2 }
        L_0x00e5:
            if (r2 == 0) goto L_0x0004
            boolean r1 = r2.isOpen()     // Catch:{ all -> 0x002e }
            if (r1 == 0) goto L_0x0004
            r2.close()     // Catch:{ all -> 0x002e }
            goto L_0x0004
        L_0x00f2:
            r1 = move-exception
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x00ce }
            boolean r3 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x00ce }
            if (r3 != 0) goto L_0x00e5
            java.lang.String r3 = "no such table"
            boolean r1 = r1.contains(r3)     // Catch:{ all -> 0x00ce }
            if (r1 == 0) goto L_0x00e5
            goto L_0x00e5
        L_0x0106:
            r1 = move-exception
            r10 = r1
            r1 = r0
            r0 = r10
            goto L_0x00d0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bk.b(android.content.Context, int):java.util.ArrayList");
    }
}

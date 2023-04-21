package com.baidu.location.a;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.baidu.location.Jni;
import com.baidu.location.d.b;
import com.baidu.location.d.g;
import com.baidu.location.f.e;
import com.baidu.location.f.j;
import com.facebook.common.util.UriUtil;
import java.io.File;
import java.util.HashMap;
import org.json.JSONObject;

public class h {
    private static Object c = new Object();
    private static h d = null;
    private static final String e = (j.h() + "/hst.db");
    a a = null;
    a b = null;
    /* access modifiers changed from: private */
    public SQLiteDatabase f = null;
    /* access modifiers changed from: private */
    public boolean g = false;

    class a extends e {
        private String b = null;
        private String c = null;
        private boolean d = true;
        private boolean e = false;

        a() {
            this.k = new HashMap();
        }

        public void a() {
            this.i = 1;
            this.h = j.c();
            String encodeTp4 = Jni.encodeTp4(this.c);
            this.c = null;
            this.k.put("bloc", encodeTp4);
        }

        public void a(String str, String str2) {
            if (!h.this.g) {
                boolean unused = h.this.g = true;
                this.b = str;
                this.c = str2;
                c(j.f);
            }
        }

        public void a(boolean z) {
            JSONObject jSONObject = null;
            if (z && this.j != null) {
                try {
                    String str = this.j;
                    if (this.d) {
                        JSONObject jSONObject2 = new JSONObject(str);
                        if (jSONObject2.has(UriUtil.LOCAL_CONTENT_SCHEME)) {
                            jSONObject = jSONObject2.getJSONObject(UriUtil.LOCAL_CONTENT_SCHEME);
                        }
                        if (jSONObject != null && jSONObject.has("imo")) {
                            Long valueOf = Long.valueOf(jSONObject.getJSONObject("imo").getString("mac"));
                            int i = jSONObject.getJSONObject("imo").getInt("mv");
                            if (Jni.encode3(this.b).longValue() == valueOf.longValue()) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("tt", Integer.valueOf((int) (System.currentTimeMillis() / 1000)));
                                contentValues.put("hst", Integer.valueOf(i));
                                try {
                                    if (h.this.f.update("hstdata", contentValues, "id = \"" + valueOf + "\"", (String[]) null) <= 0) {
                                        contentValues.put("id", valueOf);
                                        h.this.f.insert("hstdata", (String) null, contentValues);
                                    }
                                } catch (Exception e2) {
                                }
                                Bundle bundle = new Bundle();
                                bundle.putByteArray("mac", this.b.getBytes());
                                bundle.putInt("hotspot", i);
                                h.this.a(bundle);
                            }
                        }
                    }
                } catch (Exception e3) {
                }
            } else if (this.d) {
                h.this.f();
            }
            if (this.k != null) {
                this.k.clear();
            }
            boolean unused = h.this.g = false;
        }
    }

    public static h a() {
        h hVar;
        synchronized (c) {
            if (d == null) {
                d = new h();
            }
            hVar = d;
        }
        return hVar;
    }

    private String a(boolean z) {
        com.baidu.location.d.a f2 = b.a().f();
        g p = com.baidu.location.d.h.a().p();
        StringBuffer stringBuffer = new StringBuffer(1024);
        if (f2 != null && f2.b()) {
            stringBuffer.append(f2.g());
        }
        if (p != null && p.a() > 1) {
            stringBuffer.append(p.a(15));
        } else if (com.baidu.location.d.h.a().m() != null) {
            stringBuffer.append(com.baidu.location.d.h.a().m());
        }
        if (z) {
            stringBuffer.append("&imo=1");
        }
        stringBuffer.append(com.baidu.location.f.b.a().a(false));
        stringBuffer.append(a.a().f());
        return stringBuffer.toString();
    }

    /* access modifiers changed from: private */
    public void a(Bundle bundle) {
        a.a().a(bundle, 406);
    }

    /* access modifiers changed from: private */
    public void f() {
        Bundle bundle = new Bundle();
        bundle.putInt("hotspot", -1);
        a(bundle);
    }

    public void a(String str) {
        JSONObject jSONObject = null;
        if (!this.g) {
            try {
                JSONObject jSONObject2 = new JSONObject(str);
                if (jSONObject2.has(UriUtil.LOCAL_CONTENT_SCHEME)) {
                    jSONObject = jSONObject2.getJSONObject(UriUtil.LOCAL_CONTENT_SCHEME);
                }
                if (jSONObject != null && jSONObject.has("imo")) {
                    Long valueOf = Long.valueOf(jSONObject.getJSONObject("imo").getString("mac"));
                    int i = jSONObject.getJSONObject("imo").getInt("mv");
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("tt", Integer.valueOf((int) (System.currentTimeMillis() / 1000)));
                    contentValues.put("hst", Integer.valueOf(i));
                    try {
                        if (this.f.update("hstdata", contentValues, "id = \"" + valueOf + "\"", (String[]) null) <= 0) {
                            contentValues.put("id", valueOf);
                            this.f.insert("hstdata", (String) null, contentValues);
                        }
                    } catch (Exception e2) {
                    }
                }
            } catch (Exception e3) {
            }
        }
    }

    public void b() {
        try {
            File file = new File(e);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.exists()) {
                this.f = SQLiteDatabase.openOrCreateDatabase(file, (SQLiteDatabase.CursorFactory) null);
                this.f.execSQL("CREATE TABLE IF NOT EXISTS hstdata(id Long PRIMARY KEY,hst INT,tt INT);");
                this.f.setVersion(1);
            }
        } catch (Exception e2) {
            this.f = null;
        }
    }

    public void c() {
        if (this.f != null) {
            try {
                this.f.close();
            } catch (Exception e2) {
            } finally {
                this.f = null;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0069, code lost:
        if (r1 != null) goto L_0x006b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x007f, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0080, code lost:
        r6 = r2;
        r2 = r1;
        r1 = r6;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0060 A[SYNTHETIC, Splitter:B:21:0x0060] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0068 A[ExcHandler: Exception (e java.lang.Exception), Splitter:B:13:0x0031] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0077 A[SYNTHETIC, Splitter:B:33:0x0077] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int d() {
        /*
            r7 = this;
            r1 = 0
            r0 = -3
            boolean r2 = r7.g
            if (r2 == 0) goto L_0x0007
        L_0x0006:
            return r0
        L_0x0007:
            boolean r2 = com.baidu.location.d.h.j()     // Catch:{ Exception -> 0x007b }
            if (r2 == 0) goto L_0x0006
            android.database.sqlite.SQLiteDatabase r2 = r7.f     // Catch:{ Exception -> 0x007b }
            if (r2 == 0) goto L_0x0006
            com.baidu.location.d.h r2 = com.baidu.location.d.h.a()     // Catch:{ Exception -> 0x007b }
            android.net.wifi.WifiInfo r2 = r2.l()     // Catch:{ Exception -> 0x007b }
            if (r2 == 0) goto L_0x0006
            java.lang.String r3 = r2.getBSSID()     // Catch:{ Exception -> 0x007b }
            if (r3 == 0) goto L_0x0006
            java.lang.String r2 = r2.getBSSID()     // Catch:{ Exception -> 0x007b }
            java.lang.String r3 = ":"
            java.lang.String r4 = ""
            java.lang.String r2 = r2.replace(r3, r4)     // Catch:{ Exception -> 0x007b }
            java.lang.Long r2 = com.baidu.location.Jni.encode3(r2)     // Catch:{ Exception -> 0x007b }
            android.database.sqlite.SQLiteDatabase r3 = r7.f     // Catch:{ Exception -> 0x0068, all -> 0x0071 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0068, all -> 0x0071 }
            r4.<init>()     // Catch:{ Exception -> 0x0068, all -> 0x0071 }
            java.lang.String r5 = "select * from hstdata where id = \""
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0068, all -> 0x0071 }
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ Exception -> 0x0068, all -> 0x0071 }
            java.lang.String r4 = "\";"
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x0068, all -> 0x0071 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0068, all -> 0x0071 }
            r4 = 0
            android.database.Cursor r1 = r3.rawQuery(r2, r4)     // Catch:{ Exception -> 0x0068, all -> 0x0071 }
            if (r1 == 0) goto L_0x0066
            boolean r2 = r1.moveToFirst()     // Catch:{ Exception -> 0x0068, all -> 0x007f }
            if (r2 == 0) goto L_0x0066
            r2 = 1
            int r0 = r1.getInt(r2)     // Catch:{ Exception -> 0x0068, all -> 0x007f }
        L_0x005e:
            if (r1 == 0) goto L_0x0006
            r1.close()     // Catch:{ Exception -> 0x0064 }
            goto L_0x0006
        L_0x0064:
            r1 = move-exception
            goto L_0x0006
        L_0x0066:
            r0 = -2
            goto L_0x005e
        L_0x0068:
            r2 = move-exception
            if (r1 == 0) goto L_0x0006
            r1.close()     // Catch:{ Exception -> 0x006f }
            goto L_0x0006
        L_0x006f:
            r1 = move-exception
            goto L_0x0006
        L_0x0071:
            r2 = move-exception
            r6 = r2
            r2 = r1
            r1 = r6
        L_0x0075:
            if (r2 == 0) goto L_0x007a
            r2.close()     // Catch:{ Exception -> 0x007d }
        L_0x007a:
            throw r1     // Catch:{ Exception -> 0x007b }
        L_0x007b:
            r1 = move-exception
            goto L_0x0006
        L_0x007d:
            r2 = move-exception
            goto L_0x007a
        L_0x007f:
            r2 = move-exception
            r6 = r2
            r2 = r1
            r1 = r6
            goto L_0x0075
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.a.h.d():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00be, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00bf, code lost:
        if (r2 != null) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0077 A[SYNTHETIC, Splitter:B:24:0x0077] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x007c A[SYNTHETIC, Splitter:B:27:0x007c] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00be A[ExcHandler: all (r0v2 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:13:0x0032] */
    /* JADX WARNING: Removed duplicated region for block: B:58:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void e() {
        /*
            r10 = this;
            r2 = 0
            r0 = 1
            boolean r1 = r10.g
            if (r1 == 0) goto L_0x0007
        L_0x0006:
            return
        L_0x0007:
            boolean r1 = com.baidu.location.d.h.j()     // Catch:{ Exception -> 0x0097 }
            if (r1 == 0) goto L_0x00ca
            android.database.sqlite.SQLiteDatabase r1 = r10.f     // Catch:{ Exception -> 0x0097 }
            if (r1 == 0) goto L_0x00ca
            com.baidu.location.d.h r1 = com.baidu.location.d.h.a()     // Catch:{ Exception -> 0x0097 }
            android.net.wifi.WifiInfo r1 = r1.l()     // Catch:{ Exception -> 0x0097 }
            if (r1 == 0) goto L_0x00c5
            java.lang.String r3 = r1.getBSSID()     // Catch:{ Exception -> 0x0097 }
            if (r3 == 0) goto L_0x00c5
            java.lang.String r1 = r1.getBSSID()     // Catch:{ Exception -> 0x0097 }
            java.lang.String r3 = ":"
            java.lang.String r4 = ""
            java.lang.String r3 = r1.replace(r3, r4)     // Catch:{ Exception -> 0x0097 }
            java.lang.Long r4 = com.baidu.location.Jni.encode3(r3)     // Catch:{ Exception -> 0x0097 }
            r1 = 0
            android.database.sqlite.SQLiteDatabase r5 = r10.f     // Catch:{ Exception -> 0x00b4, all -> 0x00be }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b4, all -> 0x00be }
            r6.<init>()     // Catch:{ Exception -> 0x00b4, all -> 0x00be }
            java.lang.String r7 = "select * from hstdata where id = \""
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x00b4, all -> 0x00be }
            java.lang.StringBuilder r4 = r6.append(r4)     // Catch:{ Exception -> 0x00b4, all -> 0x00be }
            java.lang.String r6 = "\";"
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Exception -> 0x00b4, all -> 0x00be }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x00b4, all -> 0x00be }
            r6 = 0
            android.database.Cursor r2 = r5.rawQuery(r4, r6)     // Catch:{ Exception -> 0x00b4, all -> 0x00be }
            if (r2 == 0) goto L_0x00b2
            boolean r4 = r2.moveToFirst()     // Catch:{ Exception -> 0x00d3, all -> 0x00be }
            if (r4 == 0) goto L_0x00b2
            r4 = 1
            int r4 = r2.getInt(r4)     // Catch:{ Exception -> 0x00d3, all -> 0x00be }
            r5 = 2
            int r5 = r2.getInt(r5)     // Catch:{ Exception -> 0x00d3, all -> 0x00be }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00d3, all -> 0x00be }
            r8 = 1000(0x3e8, double:4.94E-321)
            long r6 = r6 / r8
            long r8 = (long) r5
            long r6 = r6 - r8
            r8 = 259200(0x3f480, double:1.28062E-318)
            int r5 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r5 <= 0) goto L_0x009a
        L_0x0074:
            r1 = r0
        L_0x0075:
            if (r2 == 0) goto L_0x007a
            r2.close()     // Catch:{ Exception -> 0x00cf }
        L_0x007a:
            if (r1 == 0) goto L_0x0006
            com.baidu.location.a.h$a r0 = r10.a     // Catch:{ Exception -> 0x0097 }
            if (r0 != 0) goto L_0x0087
            com.baidu.location.a.h$a r0 = new com.baidu.location.a.h$a     // Catch:{ Exception -> 0x0097 }
            r0.<init>()     // Catch:{ Exception -> 0x0097 }
            r10.a = r0     // Catch:{ Exception -> 0x0097 }
        L_0x0087:
            com.baidu.location.a.h$a r0 = r10.a     // Catch:{ Exception -> 0x0097 }
            if (r0 == 0) goto L_0x0006
            com.baidu.location.a.h$a r0 = r10.a     // Catch:{ Exception -> 0x0097 }
            r1 = 1
            java.lang.String r1 = r10.a((boolean) r1)     // Catch:{ Exception -> 0x0097 }
            r0.a(r3, r1)     // Catch:{ Exception -> 0x0097 }
            goto L_0x0006
        L_0x0097:
            r0 = move-exception
            goto L_0x0006
        L_0x009a:
            android.os.Bundle r0 = new android.os.Bundle     // Catch:{ Exception -> 0x00d3, all -> 0x00be }
            r0.<init>()     // Catch:{ Exception -> 0x00d3, all -> 0x00be }
            java.lang.String r5 = "mac"
            byte[] r6 = r3.getBytes()     // Catch:{ Exception -> 0x00d3, all -> 0x00be }
            r0.putByteArray(r5, r6)     // Catch:{ Exception -> 0x00d3, all -> 0x00be }
            java.lang.String r5 = "hotspot"
            r0.putInt(r5, r4)     // Catch:{ Exception -> 0x00d3, all -> 0x00be }
            r10.a((android.os.Bundle) r0)     // Catch:{ Exception -> 0x00d3, all -> 0x00be }
            r0 = r1
            goto L_0x0074
        L_0x00b2:
            r1 = r0
            goto L_0x0075
        L_0x00b4:
            r0 = move-exception
            r0 = r2
        L_0x00b6:
            if (r0 == 0) goto L_0x007a
            r0.close()     // Catch:{ Exception -> 0x00bc }
            goto L_0x007a
        L_0x00bc:
            r0 = move-exception
            goto L_0x007a
        L_0x00be:
            r0 = move-exception
            if (r2 == 0) goto L_0x00c4
            r2.close()     // Catch:{ Exception -> 0x00d1 }
        L_0x00c4:
            throw r0     // Catch:{ Exception -> 0x0097 }
        L_0x00c5:
            r10.f()     // Catch:{ Exception -> 0x0097 }
            goto L_0x0006
        L_0x00ca:
            r10.f()     // Catch:{ Exception -> 0x0097 }
            goto L_0x0006
        L_0x00cf:
            r0 = move-exception
            goto L_0x007a
        L_0x00d1:
            r1 = move-exception
            goto L_0x00c4
        L_0x00d3:
            r0 = move-exception
            r0 = r2
            goto L_0x00b6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.a.h.e():void");
    }
}

package com.baidu.location.b;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.com.bioeasy.app.utils.ListUtils;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.f.b;
import com.baidu.location.f.e;
import com.baidu.location.f.j;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONObject;

public class a {
    private static Object b = new Object();
    private static a c = null;
    private static final String d = (j.h() + "/gal.db");
    C0006a a = null;
    /* access modifiers changed from: private */
    public SQLiteDatabase e = null;
    /* access modifiers changed from: private */
    public boolean f = false;
    private String g = null;
    private double h = Double.MAX_VALUE;
    private double i = Double.MAX_VALUE;

    /* renamed from: com.baidu.location.b.a$a  reason: collision with other inner class name */
    class C0006a extends e {
        int a;
        int b;
        int c;
        int d;
        double e;

        C0006a() {
            this.k = new HashMap();
        }

        public void a() {
            String str;
            this.h = "http://loc.map.baidu.com/gpsz";
            String format = String.format(Locale.CHINESE, "&x=%d&y=%d%s", new Object[]{Integer.valueOf(this.a), Integer.valueOf(this.b), b.a().c()});
            String encode = Jni.encode(format);
            if (encode.contains("err!")) {
                try {
                    str = com.baidu.android.bbalbs.common.a.b.a(format.toString().getBytes(), "UTF-8");
                } catch (Exception e2) {
                    str = "err2!";
                }
                this.k.put("gpszb", str);
                return;
            }
            this.k.put("gpsz", encode);
        }

        public void a(double d2, double d3, double d4) {
            if (!a.this.f) {
                double[] coorEncrypt = Jni.coorEncrypt(d2, d3, "gcj2wgs");
                this.a = (int) Math.floor(coorEncrypt[0] * 100.0d);
                this.b = (int) Math.floor(coorEncrypt[1] * 100.0d);
                this.c = (int) Math.floor(d2 * 100.0d);
                this.d = (int) Math.floor(d3 * 100.0d);
                this.e = d4;
                boolean unused = a.this.f = true;
                e();
            }
        }

        public void a(boolean z) {
            if (z && this.j != null) {
                try {
                    JSONObject jSONObject = new JSONObject(this.j);
                    if (jSONObject != null && jSONObject.has("height")) {
                        String string = jSONObject.getString("height");
                        if (string.contains(ListUtils.DEFAULT_JOIN_SEPARATOR)) {
                            String[] split = string.trim().split(ListUtils.DEFAULT_JOIN_SEPARATOR);
                            int length = split.length;
                            int sqrt = (int) Math.sqrt((double) length);
                            if (sqrt * sqrt == length) {
                                int i = this.c - ((sqrt - 1) / 2);
                                int i2 = this.d - ((sqrt - 1) / 2);
                                for (int i3 = 0; i3 < sqrt; i3++) {
                                    for (int i4 = 0; i4 < sqrt; i4++) {
                                        ContentValues contentValues = new ContentValues();
                                        if (split[(i3 * sqrt) + i4].contains("E")) {
                                            contentValues.put("aldata", Double.valueOf(10000.0d));
                                            contentValues.put("sigma", Double.valueOf(10000.0d));
                                        } else if (!split[(i3 * sqrt) + i4].contains(":")) {
                                            contentValues.put("aldata", Double.valueOf(split[(i3 * sqrt) + i4]));
                                            contentValues.put("sigma", Double.valueOf(10000.0d));
                                        } else {
                                            String[] split2 = split[(i3 * sqrt) + i4].split(":");
                                            if (split2.length == 2) {
                                                contentValues.put("aldata", Double.valueOf(split2[0]));
                                                contentValues.put("sigma", split2[1]);
                                            } else {
                                                contentValues.put("aldata", Double.valueOf(10000.0d));
                                                contentValues.put("sigma", Double.valueOf(10000.0d));
                                            }
                                        }
                                        contentValues.put("tt", Integer.valueOf((int) (System.currentTimeMillis() / 1000)));
                                        String format = String.format(Locale.CHINESE, "%d,%d", new Object[]{Integer.valueOf(i + i4), Integer.valueOf(i2 + i3)});
                                        try {
                                            if (a.this.e.update("galdata_new", contentValues, "id = \"" + format + "\"", (String[]) null) <= 0) {
                                                contentValues.put("id", format);
                                                a.this.e.insert("galdata_new", (String) null, contentValues);
                                            }
                                        } catch (Exception e2) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e3) {
                }
            }
            if (this.k != null) {
                this.k.clear();
            }
            boolean unused = a.this.f = false;
        }
    }

    public static a a() {
        a aVar;
        synchronized (b) {
            if (c == null) {
                c = new a();
            }
            aVar = c;
        }
        return aVar;
    }

    private void a(double d2, double d3, double d4) {
        if (this.a == null) {
            this.a = new C0006a();
        }
        this.a.a(d2, d3, d4);
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x00bd A[SYNTHETIC, Splitter:B:28:0x00bd] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00e7 A[SYNTHETIC, Splitter:B:45:0x00e7] */
    /* JADX WARNING: Removed duplicated region for block: B:54:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public double a(double r14, double r16) {
        /*
            r13 = this;
            r8 = 9218868437227405311(0x7fefffffffffffff, double:1.7976931348623157E308)
            android.database.sqlite.SQLiteDatabase r0 = r13.e
            if (r0 == 0) goto L_0x00f3
            r0 = 4591870180066957722(0x3fb999999999999a, double:0.1)
            int r0 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1))
            if (r0 <= 0) goto L_0x00f3
            r0 = 4591870180066957722(0x3fb999999999999a, double:0.1)
            int r0 = (r16 > r0 ? 1 : (r16 == r0 ? 0 : -1))
            if (r0 <= 0) goto L_0x00f3
            r0 = 0
            java.util.Locale r1 = java.util.Locale.CHINESE
            java.lang.String r2 = "%d,%d"
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r4 = 0
            r6 = 4636737291354636288(0x4059000000000000, double:100.0)
            double r6 = r6 * r14
            double r6 = java.lang.Math.floor(r6)
            int r5 = (int) r6
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r3[r4] = r5
            r4 = 1
            r6 = 4636737291354636288(0x4059000000000000, double:100.0)
            double r6 = r6 * r16
            double r6 = java.lang.Math.floor(r6)
            int r5 = (int) r6
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r3[r4] = r5
            java.lang.String r11 = java.lang.String.format(r1, r2, r3)
            java.lang.String r1 = r13.g
            if (r1 == 0) goto L_0x0055
            java.lang.String r1 = r13.g
            boolean r1 = r1.equals(r11)
            if (r1 == 0) goto L_0x0055
            double r0 = r13.h
        L_0x0054:
            return r0
        L_0x0055:
            android.database.sqlite.SQLiteDatabase r1 = r13.e     // Catch:{ Exception -> 0x00d5, all -> 0x00e2 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00d5, all -> 0x00e2 }
            r2.<init>()     // Catch:{ Exception -> 0x00d5, all -> 0x00e2 }
            java.lang.String r3 = "select * from galdata_new where id = \""
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x00d5, all -> 0x00e2 }
            java.lang.StringBuilder r2 = r2.append(r11)     // Catch:{ Exception -> 0x00d5, all -> 0x00e2 }
            java.lang.String r3 = "\";"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x00d5, all -> 0x00e2 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00d5, all -> 0x00e2 }
            r3 = 0
            android.database.Cursor r10 = r1.rawQuery(r2, r3)     // Catch:{ Exception -> 0x00d5, all -> 0x00e2 }
            if (r10 == 0) goto L_0x00c3
            boolean r0 = r10.moveToFirst()     // Catch:{ Exception -> 0x00ef, all -> 0x00ed }
            if (r0 == 0) goto L_0x00c3
            r0 = 1
            double r8 = r10.getDouble(r0)     // Catch:{ Exception -> 0x00ef, all -> 0x00ed }
            r0 = 3
            int r0 = r10.getInt(r0)     // Catch:{ Exception -> 0x00ef, all -> 0x00ed }
            r2 = 4666723172467343360(0x40c3880000000000, double:10000.0)
            int r1 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r1 != 0) goto L_0x0095
            r8 = 9218868437227405311(0x7fefffffffffffff, double:1.7976931348623157E308)
        L_0x0095:
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00ef, all -> 0x00ed }
            r4 = 1000(0x3e8, double:4.94E-321)
            long r2 = r2 / r4
            long r0 = (long) r0     // Catch:{ Exception -> 0x00ef, all -> 0x00ed }
            long r0 = r2 - r0
            boolean r2 = r13.f     // Catch:{ Exception -> 0x00ef, all -> 0x00ed }
            if (r2 != 0) goto L_0x00b6
            r2 = 604800(0x93a80, double:2.98811E-318)
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x00b6
            r6 = 4620164044962136064(0x401e1eb860000000, double:7.53000020980835)
            r1 = r13
            r2 = r14
            r4 = r16
            r1.a(r2, r4, r6)     // Catch:{ Exception -> 0x00ef, all -> 0x00ed }
        L_0x00b6:
            r13.g = r11     // Catch:{ Exception -> 0x00ef, all -> 0x00ed }
            r13.h = r8     // Catch:{ Exception -> 0x00ef, all -> 0x00ed }
            r0 = r8
        L_0x00bb:
            if (r10 == 0) goto L_0x0054
            r10.close()     // Catch:{ Exception -> 0x00c1 }
            goto L_0x0054
        L_0x00c1:
            r2 = move-exception
            goto L_0x0054
        L_0x00c3:
            boolean r0 = r13.f     // Catch:{ Exception -> 0x00ef, all -> 0x00ed }
            if (r0 != 0) goto L_0x00d3
            r6 = 4620164044962136064(0x401e1eb860000000, double:7.53000020980835)
            r1 = r13
            r2 = r14
            r4 = r16
            r1.a(r2, r4, r6)     // Catch:{ Exception -> 0x00ef, all -> 0x00ed }
        L_0x00d3:
            r0 = r8
            goto L_0x00bb
        L_0x00d5:
            r1 = move-exception
            r2 = r0
            r0 = r8
        L_0x00d8:
            if (r2 == 0) goto L_0x0054
            r2.close()     // Catch:{ Exception -> 0x00df }
            goto L_0x0054
        L_0x00df:
            r2 = move-exception
            goto L_0x0054
        L_0x00e2:
            r1 = move-exception
            r10 = r0
            r0 = r1
        L_0x00e5:
            if (r10 == 0) goto L_0x00ea
            r10.close()     // Catch:{ Exception -> 0x00eb }
        L_0x00ea:
            throw r0
        L_0x00eb:
            r1 = move-exception
            goto L_0x00ea
        L_0x00ed:
            r0 = move-exception
            goto L_0x00e5
        L_0x00ef:
            r0 = move-exception
            r2 = r10
            r0 = r8
            goto L_0x00d8
        L_0x00f3:
            r0 = r8
            goto L_0x0054
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.b.a.a(double, double):double");
    }

    public int a(BDLocation bDLocation) {
        double d2;
        float f2;
        if (bDLocation != null) {
            f2 = bDLocation.getRadius();
            d2 = bDLocation.getAltitude();
        } else {
            d2 = 0.0d;
            f2 = 0.0f;
        }
        if (this.e != null && f2 > 0.0f && d2 > 0.0d) {
            double a2 = a(bDLocation.getLongitude(), bDLocation.getLatitude());
            if (a2 != Double.MAX_VALUE) {
                double gpsSwiftRadius = Jni.getGpsSwiftRadius(f2, d2, a2);
                if (gpsSwiftRadius > 50.0d) {
                    return 3;
                }
                return gpsSwiftRadius > 20.0d ? 2 : 1;
            }
        }
        return 0;
    }

    public void b() {
        try {
            File file = new File(d);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.exists()) {
                this.e = SQLiteDatabase.openOrCreateDatabase(file, (SQLiteDatabase.CursorFactory) null);
                Cursor rawQuery = this.e.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='galdata'", (String[]) null);
                if (rawQuery.moveToFirst()) {
                    if (rawQuery.getInt(0) == 0) {
                        this.e.execSQL("CREATE TABLE IF NOT EXISTS galdata_new(id CHAR(40) PRIMARY KEY,aldata DOUBLE, sigma DOUBLE,tt INT);");
                    } else {
                        this.e.execSQL("DROP TABLE galdata");
                        this.e.execSQL("CREATE TABLE galdata_new(id CHAR(40) PRIMARY KEY,aldata DOUBLE, sigma DOUBLE,tt INT);");
                    }
                }
                this.e.setVersion(1);
                rawQuery.close();
            }
        } catch (Exception e2) {
            this.e = null;
        }
    }

    public void c() {
        if (this.e != null) {
            try {
                this.e.close();
            } catch (Exception e2) {
            } finally {
                this.e = null;
            }
        }
    }
}

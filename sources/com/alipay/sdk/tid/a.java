package com.alipay.sdk.tid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.alipay.sdk.encrypt.b;
import java.lang.ref.WeakReference;

public final class a extends SQLiteOpenHelper {
    private static final String a = "msp.db";
    private static final int b = 1;
    private WeakReference<Context> c;

    public a(Context context) {
        super(context, a, (SQLiteDatabase.CursorFactory) null, 1);
        this.c = new WeakReference<>(context);
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("create table if not exists tb_tid (name text primary key, tid text, key_tid text, dt datetime);");
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("drop table if exists tb_tid");
        onCreate(sQLiteDatabase);
    }

    public final void a(String str, String str2, String str3, String str4) {
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabase = getWritableDatabase();
            if (a(sQLiteDatabase, str, str2)) {
                a(sQLiteDatabase, str, str2, str3, str4);
            } else {
                sQLiteDatabase.execSQL("insert into tb_tid (name, tid, key_tid, dt) values (?, ?, ?, datetime('now', 'localtime'))", new Object[]{c(str, str2), b.a(1, str3, com.alipay.sdk.util.a.c((Context) this.c.get())), str4});
                Cursor rawQuery = sQLiteDatabase.rawQuery("select name from tb_tid where tid!='' order by dt asc", (String[]) null);
                if (rawQuery.getCount() <= 14) {
                    rawQuery.close();
                } else {
                    int count = rawQuery.getCount() - 14;
                    String[] strArr = new String[count];
                    if (rawQuery.moveToFirst()) {
                        int i = 0;
                        do {
                            strArr[i] = rawQuery.getString(0);
                            i++;
                            if (!rawQuery.moveToNext()) {
                                break;
                            }
                        } while (count > i);
                    }
                    rawQuery.close();
                    for (int i2 = 0; i2 < count; i2++) {
                        if (!TextUtils.isEmpty(strArr[i2])) {
                            a(sQLiteDatabase, strArr[i2]);
                        }
                    }
                }
            }
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                sQLiteDatabase.close();
            }
        } catch (Exception e) {
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                sQLiteDatabase.close();
            }
        } catch (Throwable th) {
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                sQLiteDatabase.close();
            }
            throw th;
        }
    }

    private void d(String str, String str2) {
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabase = getWritableDatabase();
            a(sQLiteDatabase, str, str2, "", "");
            a(sQLiteDatabase, c(str, str2));
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                sQLiteDatabase.close();
            }
        } catch (Exception e) {
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                sQLiteDatabase.close();
            }
        } catch (Throwable th) {
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                sQLiteDatabase.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String a(java.lang.String r8, java.lang.String r9) {
        /*
            r7 = this;
            r0 = 0
            java.lang.String r1 = "select tid from tb_tid where name=?"
            android.database.sqlite.SQLiteDatabase r2 = r7.getReadableDatabase()     // Catch:{ Exception -> 0x0049, all -> 0x005e }
            r3 = 1
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ Exception -> 0x007b, all -> 0x0074 }
            r4 = 0
            java.lang.String r5 = c(r8, r9)     // Catch:{ Exception -> 0x007b, all -> 0x0074 }
            r3[r4] = r5     // Catch:{ Exception -> 0x007b, all -> 0x0074 }
            android.database.Cursor r1 = r2.rawQuery(r1, r3)     // Catch:{ Exception -> 0x007b, all -> 0x0074 }
            boolean r3 = r1.moveToFirst()     // Catch:{ Exception -> 0x007e, all -> 0x0079 }
            if (r3 == 0) goto L_0x0020
            r3 = 0
            java.lang.String r0 = r1.getString(r3)     // Catch:{ Exception -> 0x007e, all -> 0x0079 }
        L_0x0020:
            if (r1 == 0) goto L_0x0025
            r1.close()
        L_0x0025:
            if (r2 == 0) goto L_0x0030
            boolean r1 = r2.isOpen()
            if (r1 == 0) goto L_0x0030
            r2.close()
        L_0x0030:
            r1 = r0
        L_0x0031:
            boolean r0 = android.text.TextUtils.isEmpty(r1)
            if (r0 != 0) goto L_0x0048
            java.lang.ref.WeakReference<android.content.Context> r0 = r7.c
            java.lang.Object r0 = r0.get()
            android.content.Context r0 = (android.content.Context) r0
            java.lang.String r0 = com.alipay.sdk.util.a.c(r0)
            r2 = 2
            java.lang.String r1 = com.alipay.sdk.encrypt.b.a(r2, r1, r0)
        L_0x0048:
            return r1
        L_0x0049:
            r1 = move-exception
            r1 = r0
            r2 = r0
        L_0x004c:
            if (r1 == 0) goto L_0x0051
            r1.close()
        L_0x0051:
            if (r2 == 0) goto L_0x005c
            boolean r1 = r2.isOpen()
            if (r1 == 0) goto L_0x005c
            r2.close()
        L_0x005c:
            r1 = r0
            goto L_0x0031
        L_0x005e:
            r1 = move-exception
            r2 = r0
            r6 = r0
            r0 = r1
            r1 = r6
        L_0x0063:
            if (r1 == 0) goto L_0x0068
            r1.close()
        L_0x0068:
            if (r2 == 0) goto L_0x0073
            boolean r1 = r2.isOpen()
            if (r1 == 0) goto L_0x0073
            r2.close()
        L_0x0073:
            throw r0
        L_0x0074:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
            goto L_0x0063
        L_0x0079:
            r0 = move-exception
            goto L_0x0063
        L_0x007b:
            r1 = move-exception
            r1 = r0
            goto L_0x004c
        L_0x007e:
            r3 = move-exception
            goto L_0x004c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.tid.a.a(java.lang.String, java.lang.String):java.lang.String");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: android.database.Cursor} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x005e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private long e(java.lang.String r9, java.lang.String r10) {
        /*
            r8 = this;
            r2 = 0
            r0 = 0
            java.lang.String r4 = "select dt from tb_tid where name=?"
            android.database.sqlite.SQLiteDatabase r3 = r8.getReadableDatabase()     // Catch:{ Exception -> 0x0047, all -> 0x005a }
            r5 = 1
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch:{ Exception -> 0x006f, all -> 0x006d }
            r6 = 0
            java.lang.String r7 = c(r9, r10)     // Catch:{ Exception -> 0x006f, all -> 0x006d }
            r5[r6] = r7     // Catch:{ Exception -> 0x006f, all -> 0x006d }
            android.database.Cursor r2 = r3.rawQuery(r4, r5)     // Catch:{ Exception -> 0x006f, all -> 0x006d }
            boolean r4 = r2.moveToFirst()     // Catch:{ Exception -> 0x006f, all -> 0x006d }
            if (r4 == 0) goto L_0x0036
            java.text.SimpleDateFormat r4 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x006f, all -> 0x006d }
            java.lang.String r5 = "yyyy-MM-dd HH:mm:ss"
            java.util.Locale r6 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x006f, all -> 0x006d }
            r4.<init>(r5, r6)     // Catch:{ Exception -> 0x006f, all -> 0x006d }
            r5 = 0
            java.lang.String r5 = r2.getString(r5)     // Catch:{ Exception -> 0x006f, all -> 0x006d }
            java.util.Date r4 = r4.parse(r5)     // Catch:{ Exception -> 0x006f, all -> 0x006d }
            long r0 = r4.getTime()     // Catch:{ Exception -> 0x006f, all -> 0x006d }
        L_0x0036:
            if (r2 == 0) goto L_0x003b
            r2.close()
        L_0x003b:
            if (r3 == 0) goto L_0x0046
            boolean r2 = r3.isOpen()
            if (r2 == 0) goto L_0x0046
            r3.close()
        L_0x0046:
            return r0
        L_0x0047:
            r3 = move-exception
            r3 = r2
        L_0x0049:
            if (r2 == 0) goto L_0x004e
            r2.close()
        L_0x004e:
            if (r3 == 0) goto L_0x0046
            boolean r2 = r3.isOpen()
            if (r2 == 0) goto L_0x0046
            r3.close()
            goto L_0x0046
        L_0x005a:
            r0 = move-exception
            r3 = r2
        L_0x005c:
            if (r2 == 0) goto L_0x0061
            r2.close()
        L_0x0061:
            if (r3 == 0) goto L_0x006c
            boolean r1 = r3.isOpen()
            if (r1 == 0) goto L_0x006c
            r3.close()
        L_0x006c:
            throw r0
        L_0x006d:
            r0 = move-exception
            goto L_0x005c
        L_0x006f:
            r4 = move-exception
            goto L_0x0049
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.tid.a.e(java.lang.String, java.lang.String):long");
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0063  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<java.lang.String> a() {
        /*
            r7 = this;
            r0 = 0
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            android.database.sqlite.SQLiteDatabase r2 = r7.getReadableDatabase()     // Catch:{ Exception -> 0x0079, all -> 0x005c }
            java.lang.String r1 = "select tid from tb_tid"
            r4 = 0
            android.database.Cursor r1 = r2.rawQuery(r1, r4)     // Catch:{ Exception -> 0x007c, all -> 0x0072 }
        L_0x0011:
            boolean r0 = r1.moveToNext()     // Catch:{ Exception -> 0x0037, all -> 0x0077 }
            if (r0 == 0) goto L_0x004b
            r0 = 0
            java.lang.String r4 = r1.getString(r0)     // Catch:{ Exception -> 0x0037, all -> 0x0077 }
            boolean r0 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x0037, all -> 0x0077 }
            if (r0 != 0) goto L_0x0011
            java.lang.ref.WeakReference<android.content.Context> r0 = r7.c     // Catch:{ Exception -> 0x0037, all -> 0x0077 }
            java.lang.Object r0 = r0.get()     // Catch:{ Exception -> 0x0037, all -> 0x0077 }
            android.content.Context r0 = (android.content.Context) r0     // Catch:{ Exception -> 0x0037, all -> 0x0077 }
            java.lang.String r0 = com.alipay.sdk.util.a.c(r0)     // Catch:{ Exception -> 0x0037, all -> 0x0077 }
            r5 = 2
            java.lang.String r0 = com.alipay.sdk.encrypt.b.a(r5, r4, r0)     // Catch:{ Exception -> 0x0037, all -> 0x0077 }
            r3.add(r0)     // Catch:{ Exception -> 0x0037, all -> 0x0077 }
            goto L_0x0011
        L_0x0037:
            r0 = move-exception
            r0 = r1
            r1 = r2
        L_0x003a:
            if (r0 == 0) goto L_0x003f
            r0.close()
        L_0x003f:
            if (r1 == 0) goto L_0x004a
            boolean r0 = r1.isOpen()
            if (r0 == 0) goto L_0x004a
            r1.close()
        L_0x004a:
            return r3
        L_0x004b:
            if (r1 == 0) goto L_0x0050
            r1.close()
        L_0x0050:
            if (r2 == 0) goto L_0x004a
            boolean r0 = r2.isOpen()
            if (r0 == 0) goto L_0x004a
            r2.close()
            goto L_0x004a
        L_0x005c:
            r1 = move-exception
            r2 = r0
            r6 = r0
            r0 = r1
            r1 = r6
        L_0x0061:
            if (r1 == 0) goto L_0x0066
            r1.close()
        L_0x0066:
            if (r2 == 0) goto L_0x0071
            boolean r1 = r2.isOpen()
            if (r1 == 0) goto L_0x0071
            r2.close()
        L_0x0071:
            throw r0
        L_0x0072:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
            goto L_0x0061
        L_0x0077:
            r0 = move-exception
            goto L_0x0061
        L_0x0079:
            r1 = move-exception
            r1 = r0
            goto L_0x003a
        L_0x007c:
            r1 = move-exception
            r1 = r2
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.tid.a.a():java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x004c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String b(java.lang.String r8, java.lang.String r9) {
        /*
            r7 = this;
            r0 = 0
            java.lang.String r1 = "select key_tid from tb_tid where name=?"
            android.database.sqlite.SQLiteDatabase r2 = r7.getReadableDatabase()     // Catch:{ Exception -> 0x0031, all -> 0x0045 }
            r3 = 1
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ Exception -> 0x0062, all -> 0x005b }
            r4 = 0
            java.lang.String r5 = c(r8, r9)     // Catch:{ Exception -> 0x0062, all -> 0x005b }
            r3[r4] = r5     // Catch:{ Exception -> 0x0062, all -> 0x005b }
            android.database.Cursor r1 = r2.rawQuery(r1, r3)     // Catch:{ Exception -> 0x0062, all -> 0x005b }
            boolean r3 = r1.moveToFirst()     // Catch:{ Exception -> 0x0065, all -> 0x0060 }
            if (r3 == 0) goto L_0x0020
            r3 = 0
            java.lang.String r0 = r1.getString(r3)     // Catch:{ Exception -> 0x0065, all -> 0x0060 }
        L_0x0020:
            if (r1 == 0) goto L_0x0025
            r1.close()
        L_0x0025:
            if (r2 == 0) goto L_0x0030
            boolean r1 = r2.isOpen()
            if (r1 == 0) goto L_0x0030
            r2.close()
        L_0x0030:
            return r0
        L_0x0031:
            r1 = move-exception
            r1 = r0
            r2 = r0
        L_0x0034:
            if (r1 == 0) goto L_0x0039
            r1.close()
        L_0x0039:
            if (r2 == 0) goto L_0x0030
            boolean r1 = r2.isOpen()
            if (r1 == 0) goto L_0x0030
            r2.close()
            goto L_0x0030
        L_0x0045:
            r1 = move-exception
            r2 = r0
            r6 = r0
            r0 = r1
            r1 = r6
        L_0x004a:
            if (r1 == 0) goto L_0x004f
            r1.close()
        L_0x004f:
            if (r2 == 0) goto L_0x005a
            boolean r1 = r2.isOpen()
            if (r1 == 0) goto L_0x005a
            r2.close()
        L_0x005a:
            throw r0
        L_0x005b:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
            goto L_0x004a
        L_0x0060:
            r0 = move-exception
            goto L_0x004a
        L_0x0062:
            r1 = move-exception
            r1 = r0
            goto L_0x0034
        L_0x0065:
            r3 = move-exception
            goto L_0x0034
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.tid.a.b(java.lang.String, java.lang.String):java.lang.String");
    }

    private static boolean a(SQLiteDatabase sQLiteDatabase, String str, String str2) {
        int i;
        int i2;
        Cursor cursor = null;
        try {
            Cursor rawQuery = sQLiteDatabase.rawQuery("select count(*) from tb_tid where name=?", new String[]{c(str, str2)});
            if (rawQuery.moveToFirst()) {
                i2 = rawQuery.getInt(0);
            } else {
                i2 = 0;
            }
            if (rawQuery != null) {
                rawQuery.close();
            }
            i = i2;
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
            i = 0;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        if (i > 0) {
            return true;
        }
        return false;
    }

    static String c(String str, String str2) {
        return str + str2;
    }

    private void b(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String str4) {
        sQLiteDatabase.execSQL("insert into tb_tid (name, tid, key_tid, dt) values (?, ?, ?, datetime('now', 'localtime'))", new Object[]{c(str, str2), b.a(1, str3, com.alipay.sdk.util.a.c((Context) this.c.get())), str4});
        Cursor rawQuery = sQLiteDatabase.rawQuery("select name from tb_tid where tid!='' order by dt asc", (String[]) null);
        if (rawQuery.getCount() <= 14) {
            rawQuery.close();
            return;
        }
        int count = rawQuery.getCount() - 14;
        String[] strArr = new String[count];
        if (rawQuery.moveToFirst()) {
            int i = 0;
            do {
                strArr[i] = rawQuery.getString(0);
                i++;
                if (!rawQuery.moveToNext()) {
                    break;
                }
            } while (count > i);
        }
        rawQuery.close();
        for (int i2 = 0; i2 < count; i2++) {
            if (!TextUtils.isEmpty(strArr[i2])) {
                a(sQLiteDatabase, strArr[i2]);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void a(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String str4) {
        sQLiteDatabase.execSQL("update tb_tid set tid=?, key_tid=?, dt=datetime('now', 'localtime') where name=?", new Object[]{b.a(1, str3, com.alipay.sdk.util.a.c((Context) this.c.get())), str4, c(str, str2)});
    }

    static void a(SQLiteDatabase sQLiteDatabase, String str) {
        try {
            sQLiteDatabase.delete("tb_tid", "name=?", new String[]{str});
        } catch (Exception e) {
        }
    }

    private static void a(SQLiteDatabase sQLiteDatabase) {
        Cursor rawQuery = sQLiteDatabase.rawQuery("select name from tb_tid where tid!='' order by dt asc", (String[]) null);
        if (rawQuery.getCount() <= 14) {
            rawQuery.close();
            return;
        }
        int count = rawQuery.getCount() - 14;
        String[] strArr = new String[count];
        if (rawQuery.moveToFirst()) {
            int i = 0;
            do {
                strArr[i] = rawQuery.getString(0);
                i++;
                if (!rawQuery.moveToNext()) {
                    break;
                }
            } while (count > i);
        }
        rawQuery.close();
        for (int i2 = 0; i2 < count; i2++) {
            if (!TextUtils.isEmpty(strArr[i2])) {
                a(sQLiteDatabase, strArr[i2]);
            }
        }
    }
}

package com.loc;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: DBOperation */
public class aa {
    private ac a;
    private SQLiteDatabase b;
    private z c;

    public aa(Context context, z zVar) {
        try {
            this.a = new ac(context, zVar.a(), (SQLiteDatabase.CursorFactory) null, zVar.b(), zVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        this.c = zVar;
    }

    private SQLiteDatabase a(boolean z) {
        try {
            this.b = this.a.getReadableDatabase();
        } catch (Throwable th) {
            if (!z) {
                y.a(th, "DBOperation", "getReadAbleDataBase");
            } else {
                th.printStackTrace();
            }
        }
        return this.b;
    }

    private SQLiteDatabase b(boolean z) {
        try {
            this.b = this.a.getWritableDatabase();
        } catch (Throwable th) {
            y.a(th, "DBOperation", "getReadAbleDataBase");
        }
        return this.b;
    }

    public static String a(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        Iterator<String> it = map.keySet().iterator();
        while (true) {
            boolean z2 = z;
            if (!it.hasNext()) {
                return sb.toString();
            }
            String next = it.next();
            if (z2) {
                sb.append(next).append(" = '").append(map.get(next)).append("'");
                z = false;
            } else {
                sb.append(" and ").append(next).append(" = '").append(map.get(next)).append("'");
                z = z2;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        return;
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> void a(java.lang.String r5, com.loc.ab<T> r6) {
        /*
            r4 = this;
            com.loc.z r1 = r4.c
            monitor-enter(r1)
            java.lang.String r0 = r6.b()     // Catch:{ all -> 0x0026 }
            if (r0 == 0) goto L_0x000b
            if (r5 != 0) goto L_0x000d
        L_0x000b:
            monitor-exit(r1)     // Catch:{ all -> 0x0026 }
        L_0x000c:
            return
        L_0x000d:
            android.database.sqlite.SQLiteDatabase r0 = r4.b     // Catch:{ all -> 0x0026 }
            if (r0 == 0) goto L_0x0019
            android.database.sqlite.SQLiteDatabase r0 = r4.b     // Catch:{ all -> 0x0026 }
            boolean r0 = r0.isReadOnly()     // Catch:{ all -> 0x0026 }
            if (r0 == 0) goto L_0x0020
        L_0x0019:
            r0 = 0
            android.database.sqlite.SQLiteDatabase r0 = r4.b(r0)     // Catch:{ all -> 0x0026 }
            r4.b = r0     // Catch:{ all -> 0x0026 }
        L_0x0020:
            android.database.sqlite.SQLiteDatabase r0 = r4.b     // Catch:{ all -> 0x0026 }
            if (r0 != 0) goto L_0x0029
            monitor-exit(r1)     // Catch:{ all -> 0x0026 }
            goto L_0x000c
        L_0x0026:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0026 }
            throw r0
        L_0x0029:
            android.database.sqlite.SQLiteDatabase r0 = r4.b     // Catch:{ Throwable -> 0x0041 }
            java.lang.String r2 = r6.b()     // Catch:{ Throwable -> 0x0041 }
            r3 = 0
            r0.delete(r2, r5, r3)     // Catch:{ Throwable -> 0x0041 }
            android.database.sqlite.SQLiteDatabase r0 = r4.b     // Catch:{ all -> 0x0026 }
            if (r0 == 0) goto L_0x003f
            android.database.sqlite.SQLiteDatabase r0 = r4.b     // Catch:{ all -> 0x0026 }
            r0.close()     // Catch:{ all -> 0x0026 }
            r0 = 0
            r4.b = r0     // Catch:{ all -> 0x0026 }
        L_0x003f:
            monitor-exit(r1)     // Catch:{ all -> 0x0026 }
            goto L_0x000c
        L_0x0041:
            r0 = move-exception
            java.lang.String r2 = "DataBase"
            java.lang.String r3 = "deleteData"
            com.loc.y.a(r0, r2, r3)     // Catch:{ all -> 0x0056 }
            android.database.sqlite.SQLiteDatabase r0 = r4.b     // Catch:{ all -> 0x0026 }
            if (r0 == 0) goto L_0x003f
            android.database.sqlite.SQLiteDatabase r0 = r4.b     // Catch:{ all -> 0x0026 }
            r0.close()     // Catch:{ all -> 0x0026 }
            r0 = 0
            r4.b = r0     // Catch:{ all -> 0x0026 }
            goto L_0x003f
        L_0x0056:
            r0 = move-exception
            android.database.sqlite.SQLiteDatabase r2 = r4.b     // Catch:{ all -> 0x0026 }
            if (r2 == 0) goto L_0x0063
            android.database.sqlite.SQLiteDatabase r2 = r4.b     // Catch:{ all -> 0x0026 }
            r2.close()     // Catch:{ all -> 0x0026 }
            r2 = 0
            r4.b = r2     // Catch:{ all -> 0x0026 }
        L_0x0063:
            throw r0     // Catch:{ all -> 0x0026 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aa.a(java.lang.String, com.loc.ab):void");
    }

    public <T> void b(String str, ab<T> abVar) {
        a(str, abVar, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
        return;
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> void a(java.lang.String r6, com.loc.ab<T> r7, boolean r8) {
        /*
            r5 = this;
            com.loc.z r1 = r5.c
            monitor-enter(r1)
            if (r7 == 0) goto L_0x000d
            if (r6 == 0) goto L_0x000d
            java.lang.String r0 = r7.b()     // Catch:{ all -> 0x0017 }
            if (r0 != 0) goto L_0x000f
        L_0x000d:
            monitor-exit(r1)     // Catch:{ all -> 0x0017 }
        L_0x000e:
            return
        L_0x000f:
            android.content.ContentValues r0 = r7.a()     // Catch:{ all -> 0x0017 }
            if (r0 != 0) goto L_0x001a
            monitor-exit(r1)     // Catch:{ all -> 0x0017 }
            goto L_0x000e
        L_0x0017:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0017 }
            throw r0
        L_0x001a:
            android.database.sqlite.SQLiteDatabase r2 = r5.b     // Catch:{ all -> 0x0017 }
            if (r2 == 0) goto L_0x0026
            android.database.sqlite.SQLiteDatabase r2 = r5.b     // Catch:{ all -> 0x0017 }
            boolean r2 = r2.isReadOnly()     // Catch:{ all -> 0x0017 }
            if (r2 == 0) goto L_0x002c
        L_0x0026:
            android.database.sqlite.SQLiteDatabase r2 = r5.b(r8)     // Catch:{ all -> 0x0017 }
            r5.b = r2     // Catch:{ all -> 0x0017 }
        L_0x002c:
            android.database.sqlite.SQLiteDatabase r2 = r5.b     // Catch:{ all -> 0x0017 }
            if (r2 != 0) goto L_0x0032
            monitor-exit(r1)     // Catch:{ all -> 0x0017 }
            goto L_0x000e
        L_0x0032:
            android.database.sqlite.SQLiteDatabase r2 = r5.b     // Catch:{ Throwable -> 0x004a }
            java.lang.String r3 = r7.b()     // Catch:{ Throwable -> 0x004a }
            r4 = 0
            r2.update(r3, r0, r6, r4)     // Catch:{ Throwable -> 0x004a }
            android.database.sqlite.SQLiteDatabase r0 = r5.b     // Catch:{ all -> 0x0017 }
            if (r0 == 0) goto L_0x0048
            android.database.sqlite.SQLiteDatabase r0 = r5.b     // Catch:{ all -> 0x0017 }
            r0.close()     // Catch:{ all -> 0x0017 }
            r0 = 0
            r5.b = r0     // Catch:{ all -> 0x0017 }
        L_0x0048:
            monitor-exit(r1)     // Catch:{ all -> 0x0017 }
            goto L_0x000e
        L_0x004a:
            r0 = move-exception
            if (r8 != 0) goto L_0x0062
            java.lang.String r2 = "DataBase"
            java.lang.String r3 = "updateData"
            com.loc.y.a(r0, r2, r3)     // Catch:{ all -> 0x0066 }
        L_0x0055:
            android.database.sqlite.SQLiteDatabase r0 = r5.b     // Catch:{ all -> 0x0017 }
            if (r0 == 0) goto L_0x0048
            android.database.sqlite.SQLiteDatabase r0 = r5.b     // Catch:{ all -> 0x0017 }
            r0.close()     // Catch:{ all -> 0x0017 }
            r0 = 0
            r5.b = r0     // Catch:{ all -> 0x0017 }
            goto L_0x0048
        L_0x0062:
            r0.printStackTrace()     // Catch:{ all -> 0x0066 }
            goto L_0x0055
        L_0x0066:
            r0 = move-exception
            android.database.sqlite.SQLiteDatabase r2 = r5.b     // Catch:{ all -> 0x0017 }
            if (r2 == 0) goto L_0x0073
            android.database.sqlite.SQLiteDatabase r2 = r5.b     // Catch:{ all -> 0x0017 }
            r2.close()     // Catch:{ all -> 0x0017 }
            r2 = 0
            r5.b = r2     // Catch:{ all -> 0x0017 }
        L_0x0073:
            throw r0     // Catch:{ all -> 0x0017 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aa.a(java.lang.String, com.loc.ab, boolean):void");
    }

    public <T> void a(ab<T> abVar, String str) {
        synchronized (this.c) {
            List<T> c2 = c(str, abVar);
            if (c2 == null || c2.size() == 0) {
                a(abVar);
            } else {
                b(str, abVar);
            }
        }
    }

    public <T> void a(ab<T> abVar) {
        a(abVar, false);
    }

    /* JADX INFO: finally extract failed */
    public <T> void a(ab<T> abVar, boolean z) {
        synchronized (this.c) {
            if (this.b == null || this.b.isReadOnly()) {
                this.b = b(z);
            }
            if (this.b != null) {
                try {
                    a(this.b, abVar);
                    if (this.b != null) {
                        this.b.close();
                        this.b = null;
                    }
                } catch (Throwable th) {
                    if (this.b != null) {
                        this.b.close();
                        this.b = null;
                    }
                    throw th;
                }
            }
        }
    }

    private <T> void a(SQLiteDatabase sQLiteDatabase, ab<T> abVar) {
        ContentValues a2;
        if (abVar != null && sQLiteDatabase != null && (a2 = abVar.a()) != null && abVar.b() != null) {
            sQLiteDatabase.insert(abVar.b(), (String) null, a2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:?, code lost:
        return r8;
     */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0080 A[SYNTHETIC, Splitter:B:50:0x0080] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0087 A[Catch:{ Throwable -> 0x009e }] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:21:0x0041=Splitter:B:21:0x0041, B:52:0x0083=Splitter:B:52:0x0083, B:44:0x0079=Splitter:B:44:0x0079, B:25:0x004d=Splitter:B:25:0x004d, B:40:0x006d=Splitter:B:40:0x006d, B:56:0x008f=Splitter:B:56:0x008f} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> java.util.List<T> b(java.lang.String r12, com.loc.ab<T> r13, boolean r14) {
        /*
            r11 = this;
            r9 = 0
            com.loc.z r10 = r11.c
            monitor-enter(r10)
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch:{ all -> 0x0090 }
            r8.<init>()     // Catch:{ all -> 0x0090 }
            android.database.sqlite.SQLiteDatabase r0 = r11.b     // Catch:{ all -> 0x0090 }
            if (r0 != 0) goto L_0x0013
            android.database.sqlite.SQLiteDatabase r0 = r11.a((boolean) r14)     // Catch:{ all -> 0x0090 }
            r11.b = r0     // Catch:{ all -> 0x0090 }
        L_0x0013:
            android.database.sqlite.SQLiteDatabase r0 = r11.b     // Catch:{ all -> 0x0090 }
            if (r0 == 0) goto L_0x001f
            java.lang.String r0 = r13.b()     // Catch:{ all -> 0x0090 }
            if (r0 == 0) goto L_0x001f
            if (r12 != 0) goto L_0x0022
        L_0x001f:
            monitor-exit(r10)     // Catch:{ all -> 0x0090 }
            r0 = r8
        L_0x0021:
            return r0
        L_0x0022:
            android.database.sqlite.SQLiteDatabase r0 = r11.b     // Catch:{ Throwable -> 0x0102, all -> 0x007c }
            java.lang.String r1 = r13.b()     // Catch:{ Throwable -> 0x0102, all -> 0x007c }
            r2 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r3 = r12
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x0102, all -> 0x007c }
            if (r1 != 0) goto L_0x0050
            android.database.sqlite.SQLiteDatabase r0 = r11.b     // Catch:{ Throwable -> 0x005e }
            r0.close()     // Catch:{ Throwable -> 0x005e }
            r0 = 0
            r11.b = r0     // Catch:{ Throwable -> 0x005e }
            if (r1 == 0) goto L_0x0041
            r1.close()     // Catch:{ Throwable -> 0x00bf }
        L_0x0041:
            android.database.sqlite.SQLiteDatabase r0 = r11.b     // Catch:{ Throwable -> 0x00cb }
            if (r0 == 0) goto L_0x004d
            android.database.sqlite.SQLiteDatabase r0 = r11.b     // Catch:{ Throwable -> 0x00cb }
            r0.close()     // Catch:{ Throwable -> 0x00cb }
            r0 = 0
            r11.b = r0     // Catch:{ Throwable -> 0x00cb }
        L_0x004d:
            monitor-exit(r10)     // Catch:{ all -> 0x0090 }
            r0 = r8
            goto L_0x0021
        L_0x0050:
            boolean r0 = r1.moveToNext()     // Catch:{ Throwable -> 0x005e }
            if (r0 == 0) goto L_0x00d7
            java.lang.Object r0 = r13.a(r1)     // Catch:{ Throwable -> 0x005e }
            r8.add(r0)     // Catch:{ Throwable -> 0x005e }
            goto L_0x0050
        L_0x005e:
            r0 = move-exception
        L_0x005f:
            if (r14 != 0) goto L_0x0068
            java.lang.String r2 = "DataBase"
            java.lang.String r3 = "searchListData"
            com.loc.y.a(r0, r2, r3)     // Catch:{ all -> 0x00ff }
        L_0x0068:
            if (r1 == 0) goto L_0x006d
            r1.close()     // Catch:{ Throwable -> 0x00a9 }
        L_0x006d:
            android.database.sqlite.SQLiteDatabase r0 = r11.b     // Catch:{ Throwable -> 0x00b4 }
            if (r0 == 0) goto L_0x0079
            android.database.sqlite.SQLiteDatabase r0 = r11.b     // Catch:{ Throwable -> 0x00b4 }
            r0.close()     // Catch:{ Throwable -> 0x00b4 }
            r0 = 0
            r11.b = r0     // Catch:{ Throwable -> 0x00b4 }
        L_0x0079:
            monitor-exit(r10)     // Catch:{ all -> 0x0090 }
            r0 = r8
            goto L_0x0021
        L_0x007c:
            r0 = move-exception
            r1 = r9
        L_0x007e:
            if (r1 == 0) goto L_0x0083
            r1.close()     // Catch:{ Throwable -> 0x0093 }
        L_0x0083:
            android.database.sqlite.SQLiteDatabase r1 = r11.b     // Catch:{ Throwable -> 0x009e }
            if (r1 == 0) goto L_0x008f
            android.database.sqlite.SQLiteDatabase r1 = r11.b     // Catch:{ Throwable -> 0x009e }
            r1.close()     // Catch:{ Throwable -> 0x009e }
            r1 = 0
            r11.b = r1     // Catch:{ Throwable -> 0x009e }
        L_0x008f:
            throw r0     // Catch:{ all -> 0x0090 }
        L_0x0090:
            r0 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x0090 }
            throw r0
        L_0x0093:
            r1 = move-exception
            if (r14 != 0) goto L_0x0083
            java.lang.String r2 = "DataBase"
            java.lang.String r3 = "searchListData"
            com.loc.y.a(r1, r2, r3)     // Catch:{ all -> 0x0090 }
            goto L_0x0083
        L_0x009e:
            r1 = move-exception
            if (r14 != 0) goto L_0x008f
            java.lang.String r2 = "DataBase"
            java.lang.String r3 = "searchListData"
            com.loc.y.a(r1, r2, r3)     // Catch:{ all -> 0x0090 }
            goto L_0x008f
        L_0x00a9:
            r0 = move-exception
            if (r14 != 0) goto L_0x006d
            java.lang.String r1 = "DataBase"
            java.lang.String r2 = "searchListData"
            com.loc.y.a(r0, r1, r2)     // Catch:{ all -> 0x0090 }
            goto L_0x006d
        L_0x00b4:
            r0 = move-exception
            if (r14 != 0) goto L_0x0079
            java.lang.String r1 = "DataBase"
            java.lang.String r2 = "searchListData"
            com.loc.y.a(r0, r1, r2)     // Catch:{ all -> 0x0090 }
            goto L_0x0079
        L_0x00bf:
            r0 = move-exception
            if (r14 != 0) goto L_0x0041
            java.lang.String r1 = "DataBase"
            java.lang.String r2 = "searchListData"
            com.loc.y.a(r0, r1, r2)     // Catch:{ all -> 0x0090 }
            goto L_0x0041
        L_0x00cb:
            r0 = move-exception
            if (r14 != 0) goto L_0x004d
            java.lang.String r1 = "DataBase"
            java.lang.String r2 = "searchListData"
            com.loc.y.a(r0, r1, r2)     // Catch:{ all -> 0x0090 }
            goto L_0x004d
        L_0x00d7:
            if (r1 == 0) goto L_0x00dc
            r1.close()     // Catch:{ Throwable -> 0x00f4 }
        L_0x00dc:
            android.database.sqlite.SQLiteDatabase r0 = r11.b     // Catch:{ Throwable -> 0x00e9 }
            if (r0 == 0) goto L_0x0079
            android.database.sqlite.SQLiteDatabase r0 = r11.b     // Catch:{ Throwable -> 0x00e9 }
            r0.close()     // Catch:{ Throwable -> 0x00e9 }
            r0 = 0
            r11.b = r0     // Catch:{ Throwable -> 0x00e9 }
            goto L_0x0079
        L_0x00e9:
            r0 = move-exception
            if (r14 != 0) goto L_0x0079
            java.lang.String r1 = "DataBase"
            java.lang.String r2 = "searchListData"
            com.loc.y.a(r0, r1, r2)     // Catch:{ all -> 0x0090 }
            goto L_0x0079
        L_0x00f4:
            r0 = move-exception
            if (r14 != 0) goto L_0x00dc
            java.lang.String r1 = "DataBase"
            java.lang.String r2 = "searchListData"
            com.loc.y.a(r0, r1, r2)     // Catch:{ all -> 0x0090 }
            goto L_0x00dc
        L_0x00ff:
            r0 = move-exception
            goto L_0x007e
        L_0x0102:
            r0 = move-exception
            r1 = r9
            goto L_0x005f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aa.b(java.lang.String, com.loc.ab, boolean):java.util.List");
    }

    public <T> List<T> c(String str, ab<T> abVar) {
        return b(str, abVar, false);
    }
}

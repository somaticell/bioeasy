package com.loc;

import android.database.sqlite.SQLiteDatabase;

/* compiled from: DynamicFileDBCreator */
public class ak implements z {
    private static ak a;

    public static synchronized ak c() {
        ak akVar;
        synchronized (ak.class) {
            if (a == null) {
                a = new ak();
            }
            akVar = a;
        }
        return akVar;
    }

    private ak() {
    }

    public String a() {
        return "dynamicamapfile.db";
    }

    public int b() {
        return 1;
    }

    public void a(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS file (_id integer primary key autoincrement, sdkname  varchar(20), filename varchar(100),md5 varchar(20),version varchar(20),dynamicversion varchar(20),status varchar(20),reservedfield varchar(20));");
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void a(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}

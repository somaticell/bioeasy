package com.loc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* compiled from: DB */
public class ac extends SQLiteOpenHelper {
    private z a;

    public ac(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i, z zVar) {
        super(context, str, cursorFactory, i);
        this.a = zVar;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        this.a.a(sQLiteDatabase);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        this.a.a(sQLiteDatabase, i, i2);
    }
}

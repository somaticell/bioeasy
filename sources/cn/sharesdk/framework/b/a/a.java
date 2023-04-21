package cn.sharesdk.framework.b.a;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* compiled from: DBHelp */
public class a extends SQLiteOpenHelper {
    public a(Context context) {
        super(context.getApplicationContext(), "sharesdk.db", (SQLiteDatabase.CursorFactory) null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" create table  message(_id integer primary key autoincrement,post_time timestamp not null, message_data text not null);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public synchronized void close() {
        super.close();
    }
}

package cn.sharesdk.framework.b.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.sharesdk.framework.utils.d;

/* compiled from: DBProvider */
public class b {
    private static b c = null;
    private Context a;
    private a b = new a(this.a);

    private b(Context context) {
        this.a = context.getApplicationContext();
    }

    public static synchronized b a(Context context) {
        b bVar;
        synchronized (b.class) {
            if (c == null) {
                c = new b(context);
            }
            bVar = c;
        }
        return bVar;
    }

    public Cursor a(String str, String[] strArr, String str2, String[] strArr2, String str3) {
        SQLiteDatabase writableDatabase = this.b.getWritableDatabase();
        d.a().d("Query table: %s", str);
        try {
            return writableDatabase.query(str, strArr, str2, strArr2, (String) null, (String) null, str3);
        } catch (Exception e) {
            d.a().w(e, "when query database occur error table:%s,", str);
            return null;
        }
    }

    public long a(String str, ContentValues contentValues) {
        try {
            return this.b.getWritableDatabase().replace(str, (String) null, contentValues);
        } catch (Exception e) {
            d.a().w(e, "when insert database occur error table:%s,", str);
            return -1;
        }
    }

    public int a(String str, String str2, String[] strArr) {
        Exception e;
        int i;
        try {
            i = this.b.getWritableDatabase().delete(str, str2, strArr);
            try {
                d.a().d("Deleted %d rows from table: %s", Integer.valueOf(i), str);
            } catch (Exception e2) {
                e = e2;
                d.a().w(e, "when delete database occur error table:%s,", str);
                return i;
            }
        } catch (Exception e3) {
            e = e3;
            i = 0;
        }
        return i;
    }

    public int a(String str) {
        Cursor cursor = null;
        int i = 0;
        try {
            cursor = this.b.getWritableDatabase().rawQuery("select count(*) from " + str, (String[]) null);
            if (cursor.moveToNext()) {
                i = cursor.getInt(0);
            }
        } catch (Exception e) {
            d.a().w(e);
        } finally {
            cursor.close();
        }
        return i;
    }
}

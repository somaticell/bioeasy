package org.litepal.tablemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.litepal.LitePalApplication;
import org.litepal.parser.LitePalAttr;
import org.litepal.util.SharedUtil;

class LitePalOpenHelper extends SQLiteOpenHelper {
    public static final String TAG = "LitePalHelper";

    LitePalOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    LitePalOpenHelper(String dbName, int version) {
        this(LitePalApplication.getContext(), dbName, (SQLiteDatabase.CursorFactory) null, version);
    }

    public void onCreate(SQLiteDatabase db) {
        Generator.create(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Generator.upgrade(db);
        SharedUtil.updateVersion(LitePalAttr.getInstance().getExtraKeyName(), newVersion);
    }
}

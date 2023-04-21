package cn.sharesdk.framework.b.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import cn.com.bioeasy.app.utils.ListUtils;
import java.util.ArrayList;

/* compiled from: MessageUtils */
public class d {
    public static synchronized long a(Context context, String str, long j) {
        long j2;
        synchronized (d.class) {
            if (str != null) {
                if (str.trim() != "") {
                    b a = b.a(context);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("post_time", Long.valueOf(j));
                    contentValues.put("message_data", str.toString());
                    j2 = a.a("message", contentValues);
                }
            }
            j2 = -1;
        }
        return j2;
    }

    public static synchronized long a(Context context, ArrayList<String> arrayList) {
        long j;
        synchronized (d.class) {
            if (arrayList == null) {
                j = 0;
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < arrayList.size(); i++) {
                    sb.append("'");
                    sb.append(arrayList.get(i));
                    sb.append("'");
                    sb.append(ListUtils.DEFAULT_JOIN_SEPARATOR);
                }
                int a = b.a(context).a("message", "_id in ( " + sb.toString().substring(0, sb.length() - 1) + " )", (String[]) null);
                cn.sharesdk.framework.utils.d.a().i("delete COUNT == %s", Integer.valueOf(a));
                j = (long) a;
            }
        }
        return j;
    }

    private static synchronized ArrayList<c> a(Context context, String str, String[] strArr) {
        ArrayList<c> arrayList;
        synchronized (d.class) {
            arrayList = new ArrayList<>();
            c cVar = new c();
            StringBuilder sb = new StringBuilder();
            Cursor a = b.a(context).a("message", new String[]{"_id", "post_time", "message_data"}, str, strArr, (String) null);
            StringBuilder sb2 = sb;
            c cVar2 = cVar;
            while (a != null && a.moveToNext()) {
                cVar2.b.add(a.getString(0));
                if (cVar2.b.size() == 100) {
                    sb2.append(a.getString(2));
                    cVar2.a = sb2.toString();
                    arrayList.add(cVar2);
                    cVar2 = new c();
                    sb2 = new StringBuilder();
                } else {
                    sb2.append(a.getString(2) + "\n");
                }
            }
            a.close();
            if (cVar2.b.size() != 0) {
                cVar2.a = sb2.toString().substring(0, sb2.length() - 1);
                arrayList.add(cVar2);
            }
        }
        return arrayList;
    }

    public static synchronized ArrayList<c> a(Context context) {
        ArrayList<c> arrayList;
        synchronized (d.class) {
            if (b.a(context).a("message") > 0) {
                arrayList = a(context, (String) null, (String[]) null);
            } else {
                arrayList = new ArrayList<>();
            }
        }
        return arrayList;
    }
}

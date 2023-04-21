package com.loc;

import android.content.ContentValues;
import android.database.Cursor;
import com.facebook.common.util.UriUtil;
import com.loc.am;
import java.util.HashMap;
import java.util.Map;

/* compiled from: DynamicFileEntity */
public class al implements ab<am> {
    private am a;

    /* renamed from: b */
    public am a(Cursor cursor) {
        String string = cursor.getString(1);
        String string2 = cursor.getString(2);
        String string3 = cursor.getString(3);
        String string4 = cursor.getString(4);
        String string5 = cursor.getString(5);
        return new am.a(string2, string3, string, string4, string5).a(cursor.getString(6)).a();
    }

    public ContentValues a() {
        if (this.a == null) {
            return null;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("filename", this.a.a());
        contentValues.put("md5", this.a.b());
        contentValues.put("sdkname", this.a.c());
        contentValues.put("version", this.a.d());
        contentValues.put("dynamicversion", this.a.e());
        contentValues.put("status", this.a.f());
        return contentValues;
    }

    public String b() {
        return UriUtil.LOCAL_FILE_SCHEME;
    }

    public void a(am amVar) {
        this.a = amVar;
    }

    public static String a(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put("sdkname", str);
        hashMap.put("dynamicversion", str2);
        return aa.a((Map<String, String>) hashMap);
    }

    public static String b(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put("sdkname", str);
        hashMap.put("status", str2);
        return aa.a((Map<String, String>) hashMap);
    }

    public static String a(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("sdkname", str);
        return aa.a((Map<String, String>) hashMap);
    }

    public static String b(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("filename", str);
        return aa.a((Map<String, String>) hashMap);
    }

    public static String a(String str, String str2, String str3, String str4) {
        HashMap hashMap = new HashMap();
        hashMap.put("filename", str);
        hashMap.put("sdkname", str2);
        hashMap.put("dynamicversion", str4);
        hashMap.put("version", str3);
        return aa.a((Map<String, String>) hashMap);
    }
}

package com.loc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import com.amap.api.location.AMapLocation;
import java.lang.reflect.Method;
import org.json.JSONObject;

/* compiled from: LastLocationManager */
public class i {
    private static i e;
    Context a;
    SharedPreferences b = null;
    SharedPreferences.Editor c = null;
    private String d = null;
    private Method f;

    public static i a(Context context) {
        if (e == null) {
            e = new i(context);
        }
        return e;
    }

    private i(Context context) {
        this.a = context;
        if (this.d == null) {
            this.d = bh.a("MD5", this.a.getPackageName());
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: byte[]} */
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(com.amap.api.location.AMapLocation r5) {
        /*
            r4 = this;
            r0 = 0
            android.content.Context r1 = r4.a
            if (r1 == 0) goto L_0x000b
            boolean r1 = com.loc.bw.a((com.amap.api.location.AMapLocation) r5)
            if (r1 != 0) goto L_0x000c
        L_0x000b:
            return
        L_0x000c:
            int r1 = r5.getLocationType()
            r2 = 2
            if (r1 == r2) goto L_0x000b
            android.content.SharedPreferences r1 = r4.b
            if (r1 != 0) goto L_0x0022
            android.content.Context r1 = r4.a
            java.lang.String r2 = "pref"
            r3 = 0
            android.content.SharedPreferences r1 = r1.getSharedPreferences(r2, r3)
            r4.b = r1
        L_0x0022:
            android.content.SharedPreferences$Editor r1 = r4.c
            if (r1 != 0) goto L_0x002e
            android.content.SharedPreferences r1 = r4.b
            android.content.SharedPreferences$Editor r1 = r1.edit()
            r4.c = r1
        L_0x002e:
            java.lang.String r1 = r5.toStr()
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x004c
            java.lang.String r1 = r5.toStr()     // Catch:{ UnsupportedEncodingException -> 0x0072 }
            java.lang.String r2 = "UTF-8"
            byte[] r1 = r1.getBytes(r2)     // Catch:{ UnsupportedEncodingException -> 0x0072 }
            java.lang.String r2 = r4.d     // Catch:{ UnsupportedEncodingException -> 0x0072 }
            byte[] r0 = com.loc.bh.c(r1, r2)     // Catch:{ UnsupportedEncodingException -> 0x0072 }
        L_0x0048:
            java.lang.String r0 = com.loc.r.a((byte[]) r0)
        L_0x004c:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x000b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "lastfix"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = com.loc.e.f
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.content.SharedPreferences$Editor r2 = r4.c
            r2.putString(r1, r0)
            android.content.SharedPreferences$Editor r0 = r4.c
            r4.a((android.content.SharedPreferences.Editor) r0)
            goto L_0x000b
        L_0x0072:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0048
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.i.a(com.amap.api.location.AMapLocation):void");
    }

    private void a(SharedPreferences.Editor editor) {
        if (editor != null) {
            if (Build.VERSION.SDK_INT >= 9) {
                try {
                    if (this.f == null) {
                        this.f = SharedPreferences.Editor.class.getDeclaredMethod("apply", new Class[0]);
                    }
                    this.f.invoke(editor, new Object[0]);
                } catch (Throwable th) {
                    th.printStackTrace();
                    editor.commit();
                }
            } else {
                editor.commit();
            }
        }
    }

    public AMapLocation a() {
        SharedPreferences sharedPreferences;
        String str;
        if (this.a == null || (sharedPreferences = this.a.getSharedPreferences("pref", 0)) == null) {
            return null;
        }
        String str2 = "lastfix" + e.f;
        if (sharedPreferences.contains(str2)) {
            try {
                String string = sharedPreferences.getString(str2, (String) null);
                str = string != null ? new String(bh.d(r.a(string), this.d), "UTF-8") : string;
            } catch (Exception e2) {
                sharedPreferences.edit().remove(str2).commit();
                str = null;
            }
        } else {
            str = null;
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return e.a(new JSONObject(str));
        } catch (Exception e3) {
            return null;
        }
    }
}

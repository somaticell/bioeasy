package com.alipay.sdk.protocol;

import android.content.Context;
import android.text.TextUtils;
import cn.com.bioeasy.app.utils.ListUtils;
import com.alipay.sdk.tid.a;
import com.alipay.sdk.util.h;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public final class b {
    public a a;
    public String[] b;
    private String c;

    private b(String str) {
        this.c = str;
    }

    private b(String str, a aVar) {
        this.c = str;
        this.a = aVar;
    }

    private static void a(b bVar) {
        String[] strArr = bVar.b;
        if (strArr.length == 3 && TextUtils.equals(com.alipay.sdk.cons.b.c, strArr[0])) {
            Context context = com.alipay.sdk.sys.b.a().a;
            com.alipay.sdk.tid.b a2 = com.alipay.sdk.tid.b.a();
            if (!TextUtils.isEmpty(strArr[1]) && !TextUtils.isEmpty(strArr[2])) {
                a2.a = strArr[1];
                a2.b = strArr[2];
                a aVar = new a(context);
                try {
                    aVar.a(com.alipay.sdk.util.a.a(context).a(), com.alipay.sdk.util.a.a(context).b(), a2.a, a2.b);
                } catch (Exception e) {
                } finally {
                    aVar.close();
                }
            }
        }
    }

    public static List<b> a(JSONObject jSONObject) {
        ArrayList arrayList = new ArrayList();
        if (jSONObject == null) {
            return arrayList;
        }
        String optString = jSONObject.optString("name", "");
        String[] strArr = null;
        if (!TextUtils.isEmpty(optString)) {
            strArr = optString.split(h.b);
        }
        for (int i = 0; i < strArr.length; i++) {
            a a2 = a.a(strArr[i]);
            if (a2 != a.None) {
                b bVar = new b(strArr[i], a2);
                bVar.b = a(strArr[i]);
                arrayList.add(bVar);
            }
        }
        return arrayList;
    }

    private static String[] a(String str) {
        ArrayList arrayList = new ArrayList();
        int indexOf = str.indexOf(40);
        int lastIndexOf = str.lastIndexOf(41);
        if (indexOf == -1 || lastIndexOf == -1 || lastIndexOf <= indexOf) {
            return null;
        }
        String[] split = str.substring(indexOf + 1, lastIndexOf).split(ListUtils.DEFAULT_JOIN_SEPARATOR);
        if (split != null) {
            for (int i = 0; i < split.length; i++) {
                if (!TextUtils.isEmpty(split[i])) {
                    arrayList.add(split[i].trim().replaceAll("'", "").replaceAll("\"", ""));
                }
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    private static String[] b(String str) {
        if (!TextUtils.isEmpty(str)) {
            return str.split(h.b);
        }
        return null;
    }

    private String a() {
        return this.c;
    }

    private a b() {
        return this.a;
    }

    private String[] c() {
        return this.b;
    }
}

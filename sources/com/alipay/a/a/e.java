package com.alipay.a.a;

import com.alipay.a.b.a;
import com.alipay.sdk.util.h;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.json.alipay.b;

public final class e {
    static List<i> a;

    static {
        ArrayList arrayList = new ArrayList();
        a = arrayList;
        arrayList.add(new l());
        a.add(new d());
        a.add(new c());
        a.add(new h());
        a.add(new k());
        a.add(new b());
        a.add(new a());
        a.add(new g());
    }

    public static final <T> T a(Object obj, Type type) {
        T a2;
        for (i next : a) {
            if (next.a(a.a(type)) && (a2 = next.a(obj, type)) != null) {
                return a2;
            }
        }
        return null;
    }

    public static final Object a(String str, Type type) {
        if (str == null || str.length() == 0) {
            return null;
        }
        String trim = str.trim();
        return (!trim.startsWith("[") || !trim.endsWith("]")) ? (!trim.startsWith("{") || !trim.endsWith(h.d)) ? a((Object) trim, type) : a((Object) new b(trim), type) : a((Object) new org.json.alipay.a(trim), type);
    }
}

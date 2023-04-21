package com.alipay.sdk.util;

import com.alipay.sdk.app.i;
import com.alipay.sdk.app.statistic.a;
import com.alipay.sdk.app.statistic.c;
import java.util.HashMap;
import java.util.Map;

public final class j {
    public static final String a = "resultStatus";
    public static final String b = "memo";
    public static final String c = "result";

    public static Map<String, String> a(String str) {
        i a2 = i.a(i.CANCELED.h);
        HashMap hashMap = new HashMap();
        hashMap.put(a, Integer.toString(a2.h));
        hashMap.put(b, a2.i);
        hashMap.put(c, "");
        try {
            return b(str);
        } catch (Throwable th) {
            a.a(c.b, c.f, th);
            return hashMap;
        }
    }

    private static Map<String, String> a() {
        i a2 = i.a(i.CANCELED.h);
        HashMap hashMap = new HashMap();
        hashMap.put(a, Integer.toString(a2.h));
        hashMap.put(b, a2.i);
        hashMap.put(c, "");
        return hashMap;
    }

    private static Map<String, String> b(String str) {
        String[] split = str.split(h.b);
        HashMap hashMap = new HashMap();
        for (String str2 : split) {
            String substring = str2.substring(0, str2.indexOf("={"));
            String str3 = substring + "={";
            hashMap.put(substring, str2.substring(str3.length() + str2.indexOf(str3), str2.lastIndexOf(h.d)));
        }
        return hashMap;
    }

    private static String a(String str, String str2) {
        String str3 = str2 + "={";
        return str.substring(str3.length() + str.indexOf(str3), str.lastIndexOf(h.d));
    }
}

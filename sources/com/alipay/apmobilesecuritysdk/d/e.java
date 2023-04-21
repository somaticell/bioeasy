package com.alipay.apmobilesecuritysdk.d;

import android.content.Context;
import com.alipay.b.a.a.a.a.b;
import com.alipay.sdk.sys.a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public final class e {
    private static Map<String, String> a = null;
    private static final String[] b = {"AD1", "AD2", "AD3", "AD5", "AD6", "AD7", "AD8", "AD9", "AD10", "AD11", "AD12", "AD13", "AD14", "AD15", "AD16", "AD18", "AD20", "AD21", "AD23", "AD24", "AD26", "AD27", "AD28", "AD29", "AD30", "AD31", "AD34", "AA1", "AA2", "AA3", "AA4", "AA5", "AC4", "AE1", "AE2", "AE3", "AE4", "AE5", "AE6", "AE7", "AE8", "AE9", "AE10", "AE11", "AE12", "AE13", "AE14", "AE15"};

    private static String a(Map<String, String> map) {
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList arrayList = new ArrayList(map.keySet());
        Collections.sort(arrayList);
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= arrayList.size()) {
                return stringBuffer.toString();
            }
            String str = (String) arrayList.get(i2);
            String str2 = map.get(str);
            if (str2 == null) {
                str2 = "";
            }
            stringBuffer.append((i2 == 0 ? "" : a.b) + str + "=" + str2);
            i = i2 + 1;
        }
    }

    public static synchronized Map<String, String> a(Context context, Map<String, String> map) {
        Map<String, String> map2;
        synchronized (e.class) {
            if (a == null) {
                c(context, map);
            }
            a.putAll(d.a());
            map2 = a;
        }
        return map2;
    }

    public static synchronized void a() {
        synchronized (e.class) {
            a = null;
        }
    }

    public static synchronized String b(Context context, Map<String, String> map) {
        String a2;
        synchronized (e.class) {
            a(context, map);
            TreeMap treeMap = new TreeMap();
            for (String str : b) {
                if (a.containsKey(str)) {
                    treeMap.put(str, a.get(str));
                }
            }
            a2 = b.a(a(treeMap));
        }
        return a2;
    }

    private static synchronized void c(Context context, Map<String, String> map) {
        synchronized (e.class) {
            TreeMap treeMap = new TreeMap();
            a = treeMap;
            treeMap.putAll(b.a(context, map));
            a.putAll(d.a(context));
            a.putAll(c.a(context));
            a.putAll(a.a(context, map));
        }
    }
}

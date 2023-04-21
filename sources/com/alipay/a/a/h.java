package com.alipay.a.a;

import com.alipay.a.b.a;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.json.alipay.b;

public final class h implements i, j {
    private static Map<Object, Object> a(Type type) {
        Type type2 = type;
        while (type2 != Properties.class) {
            if (type2 == Hashtable.class) {
                return new Hashtable();
            }
            if (type2 == IdentityHashMap.class) {
                return new IdentityHashMap();
            }
            if (type2 == SortedMap.class || type2 == TreeMap.class) {
                return new TreeMap();
            }
            if (type2 == ConcurrentMap.class || type2 == ConcurrentHashMap.class) {
                return new ConcurrentHashMap();
            }
            if (type2 == Map.class || type2 == HashMap.class) {
                return new HashMap();
            }
            if (type2 == LinkedHashMap.class) {
                return new LinkedHashMap();
            }
            if (type2 instanceof ParameterizedType) {
                type2 = ((ParameterizedType) type2).getRawType();
            } else {
                Class cls = (Class) type2;
                if (cls.isInterface()) {
                    throw new IllegalArgumentException("unsupport type " + type2);
                }
                try {
                    return (Map) cls.newInstance();
                } catch (Exception e) {
                    throw new IllegalArgumentException("unsupport type " + type2, e);
                }
            }
        }
        return new Properties();
    }

    public final Object a(Object obj) {
        TreeMap treeMap = new TreeMap();
        for (Map.Entry entry : ((Map) obj).entrySet()) {
            if (!(entry.getKey() instanceof String)) {
                throw new IllegalArgumentException("Map key must be String!");
            }
            treeMap.put((String) entry.getKey(), f.b(entry.getValue()));
        }
        return treeMap;
    }

    public final Object a(Object obj, Type type) {
        if (!obj.getClass().equals(b.class)) {
            return null;
        }
        b bVar = (b) obj;
        Map<Object, Object> a = a(type);
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type type2 = parameterizedType.getActualTypeArguments()[0];
            Type type3 = parameterizedType.getActualTypeArguments()[1];
            if (String.class == type2) {
                Iterator a2 = bVar.a();
                while (a2.hasNext()) {
                    String str = (String) a2.next();
                    if (a.a((Class<?>) (Class) type3)) {
                        a.put(str, bVar.a(str));
                    } else {
                        a.put(str, e.a(bVar.a(str), type3));
                    }
                }
                return a;
            }
            throw new IllegalArgumentException("Deserialize Map Key must be String.class");
        }
        throw new IllegalArgumentException("Deserialize Map must be Generics!");
    }

    public final boolean a(Class<?> cls) {
        return Map.class.isAssignableFrom(cls);
    }
}

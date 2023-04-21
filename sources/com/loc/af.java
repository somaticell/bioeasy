package com.loc;

import android.content.Context;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/* compiled from: InstanceFactory */
public class af {
    public static <T> T a(Context context, v vVar, String str, Class cls, Class[] clsArr, Object[] objArr) throws l {
        ai aiVar;
        Class loadClass;
        try {
            aiVar = ai.a(context, vVar, ah.a(context, vVar.a(), vVar.b()), ah.a(context), (String) null, context.getClassLoader());
        } catch (Throwable th) {
            th.printStackTrace();
            aiVar = null;
        }
        if (aiVar != null) {
            try {
                if (aiVar.a() && aiVar.a && (loadClass = aiVar.loadClass(str)) != null) {
                    return loadClass.getConstructor(clsArr).newInstance(objArr);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            } catch (InstantiationException e3) {
                e3.printStackTrace();
            } catch (IllegalAccessException e4) {
                e4.printStackTrace();
            } catch (IllegalArgumentException e5) {
                e5.printStackTrace();
            } catch (InvocationTargetException e6) {
                e6.printStackTrace();
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
        try {
            Constructor constructor = cls.getConstructor(clsArr);
            constructor.setAccessible(true);
            return constructor.newInstance(objArr);
        } catch (NoSuchMethodException e7) {
            e7.printStackTrace();
        } catch (InstantiationException e8) {
            e8.printStackTrace();
        } catch (IllegalAccessException e9) {
            e9.printStackTrace();
        } catch (IllegalArgumentException e10) {
            e10.printStackTrace();
        } catch (InvocationTargetException e11) {
            e11.printStackTrace();
        } catch (Throwable th3) {
            th3.printStackTrace();
        }
        throw new l("获取对象错误");
    }
}

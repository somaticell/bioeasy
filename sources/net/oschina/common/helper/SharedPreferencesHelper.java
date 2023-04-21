package net.oschina.common.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.util.ArrayMap;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;
import net.oschina.common.utils.Logger;

public final class SharedPreferencesHelper {
    private static final String SEPARATOR = "#";
    private static final String TAG = SharedPreferencesHelper.class.getName();

    public static <T> SharedPreferences getSharedPreferences(Context context, Class<T> clx) {
        return context.getSharedPreferences(clx.getName(), 0);
    }

    public static <T> boolean save(Context context, T t) {
        Class<?> clx = t.getClass();
        remove(context, clx);
        Map<String, Data> map = new ArrayMap<>();
        buildValuesToMap(clx, t, "", map);
        SharedPreferences sp = getSharedPreferences(context, clx);
        SharedPreferences.Editor editor = sp.edit();
        Set<String> existKeys = sp.getAll().keySet();
        for (String key : map.keySet()) {
            Data data = map.get(key);
            Class<?> type = data.type;
            Object value = data.value;
            if (value == null) {
                try {
                    removeKeyFamily(editor, existKeys, key);
                } catch (IllegalArgumentException e) {
                    Logger.d(TAG, "Save error:" + e.getMessage());
                }
            } else if (type.equals(Byte.class) || type.equals(Byte.TYPE)) {
                editor.putInt(key, ((Byte) value).byteValue());
            } else if (type.equals(Short.class) || type.equals(Short.TYPE)) {
                editor.putInt(key, ((Short) value).shortValue());
            } else if (type.equals(Integer.class) || type.equals(Integer.TYPE)) {
                editor.putInt(key, ((Integer) value).intValue());
            } else if (type.equals(Long.class) || type.equals(Long.TYPE)) {
                editor.putLong(key, ((Long) value).longValue());
            } else if (type.equals(Float.class) || type.equals(Float.TYPE)) {
                editor.putFloat(key, ((Float) value).floatValue());
            } else if (type.equals(Double.class) || type.equals(Double.TYPE)) {
                editor.putString(key, String.valueOf(value));
            } else if (type.equals(Boolean.class) || type.equals(Boolean.TYPE)) {
                editor.putBoolean(key, ((Boolean) value).booleanValue());
            } else if (type.equals(Character.class) || type.equals(Character.TYPE)) {
                editor.putString(key, value.toString());
            } else if (type.equals(String.class)) {
                editor.putString(key, value.toString());
            } else {
                Logger.d(TAG, String.format("Con't support save this type:%s, value:%s, key:%s", new Object[]{type, value, key}));
            }
        }
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
        return true;
    }

    public static <T> T load(Context context, Class<T> clx) {
        SharedPreferences sp = getSharedPreferences(context, clx);
        Set<String> existKeys = sp.getAll().keySet();
        if (existKeys.size() == 0) {
            return null;
        }
        return buildTargetFromSource(clx, (Object) null, "", existKeys, sp);
    }

    public static <T> T loadFormSource(Context context, Class<T> clx) {
        Class cls;
        Method method;
        SharedPreferences sp = getSharedPreferences(context, clx);
        try {
            cls = sp.getClass();
            method = null;
            method = cls.getMethod("startReloadIfChangedUnexpectedly", new Class[0]);
        } catch (NoSuchMethodException e) {
            do {
                try {
                    method = cls.getDeclaredMethod("startReloadIfChangedUnexpectedly", new Class[0]);
                } catch (NoSuchMethodException e2) {
                }
                cls = cls.getSuperclass();
                if (cls == null) {
                    break;
                }
            } while (method == null);
            if (method == null) {
                throw new NoSuchMethodException();
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        method.setAccessible(true);
        method.invoke(sp, new Object[0]);
        Set<String> existKeys = sp.getAll().keySet();
        if (existKeys.size() == 0) {
            return null;
        }
        return buildTargetFromSource(clx, (Object) null, "", existKeys, sp);
    }

    public static <T> void remove(Context context, Class<T> clx) {
        SharedPreferences.Editor editor = getSharedPreferences(context, clx).edit();
        editor.clear();
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    private static <T> void buildValuesToMap(Class<?> clx, T t, String preFix, Map<String, Data> map) {
        Field[] fields;
        if (clx != null) {
            if (!clx.equals(Object.class) && t != null && (fields = clx.getDeclaredFields()) != null && fields.length != 0) {
                for (Field field : fields) {
                    if (!isContSupport(field)) {
                        String fieldName = field.getName();
                        Class<?> fieldType = field.getType();
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        try {
                            Object value = field.get(t);
                            if (isBasicType(fieldType)) {
                                String key = preFix + fieldName;
                                if (!map.containsKey(key)) {
                                    map.put(key, new Data(fieldType, value));
                                }
                            } else {
                                buildValuesToMap(fieldType, value, preFix + fieldName + SEPARATOR, map);
                            }
                        } catch (IllegalAccessException | IllegalArgumentException e) {
                            Logger.d(TAG, "buildValuesToMap error:" + e.getMessage());
                        }
                    }
                }
                buildValuesToMap(clx.getSuperclass(), t, preFix, map);
            }
        }
    }

    private static <T> Object buildTargetFromSource(Class<T> clx, T target, String preFix, Set<String> existKeys, SharedPreferences sp) {
        if (clx != null) {
            if (!clx.equals(Object.class)) {
                if (target == null) {
                    try {
                        target = clx.newInstance();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                        return null;
                    } catch (IllegalAccessException e2) {
                        e2.printStackTrace();
                        return null;
                    }
                }
                Field[] fields = clx.getDeclaredFields();
                if (fields == null || fields.length == 0) {
                    return target;
                }
                for (Field field : fields) {
                    if (!isContSupport(field)) {
                        String fieldName = field.getName();
                        Class<?> fieldType = field.getType();
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        String key = preFix + fieldName;
                        Object value = null;
                        if (!isBasicType(fieldType)) {
                            value = buildTargetFromSource(fieldType, (Object) null, preFix + fieldName + SEPARATOR, existKeys, sp);
                        } else if (existKeys.contains(key)) {
                            if (fieldType.equals(Byte.class) || fieldType.equals(Byte.TYPE)) {
                                value = Byte.valueOf((byte) sp.getInt(key, 0));
                            } else if (fieldType.equals(Short.class) || fieldType.equals(Short.TYPE)) {
                                value = Short.valueOf((short) sp.getInt(key, 0));
                            } else if (fieldType.equals(Integer.class) || fieldType.equals(Integer.TYPE)) {
                                value = Integer.valueOf(sp.getInt(key, 0));
                            } else if (fieldType.equals(Long.class) || fieldType.equals(Long.TYPE)) {
                                value = Long.valueOf(sp.getLong(key, 0));
                            } else if (fieldType.equals(Float.class) || fieldType.equals(Float.TYPE)) {
                                value = Float.valueOf(sp.getFloat(key, 0.0f));
                            } else if (fieldType.equals(Double.class) || fieldType.equals(Double.TYPE)) {
                                value = Double.valueOf(sp.getString(key, "0.00"));
                            } else if (fieldType.equals(Boolean.class) || fieldType.equals(Boolean.TYPE)) {
                                value = Boolean.valueOf(sp.getBoolean(key, false));
                            } else if (fieldType.equals(Character.class) || fieldType.equals(Character.TYPE)) {
                                value = Character.valueOf(sp.getString(key, "").charAt(0));
                            } else if (fieldType.equals(String.class)) {
                                value = sp.getString(key, "");
                            }
                        }
                        if (value != null) {
                            try {
                                field.set(target, value);
                            } catch (IllegalAccessException e3) {
                                e3.printStackTrace();
                                Logger.d(TAG, String.format("Set field error, Key:%s, type:%s, value:%s", new Object[]{key, fieldType, value}));
                            }
                        } else {
                            Logger.d(TAG, String.format("Get field value error, Key:%s, type:%s", new Object[]{key, fieldType}));
                        }
                    }
                }
                return buildTargetFromSource(clx.getSuperclass(), target, preFix, existKeys, sp);
            }
        }
        return target;
    }

    private static void removeKeyFamily(SharedPreferences.Editor editor, Set<String> existKeys, String removeKey) {
        String preFix = removeKey + SEPARATOR;
        for (String str : existKeys) {
            if (str.equals(removeKey) || str.startsWith(preFix)) {
                editor.remove(str);
            }
        }
    }

    private static boolean isContSupport(Field field) {
        return Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()) || Modifier.isAbstract(field.getModifiers());
    }

    private static boolean isBasicType(Class<?> clx) {
        if (clx.equals(Byte.class) || clx.equals(Byte.TYPE) || clx.equals(Short.class) || clx.equals(Short.TYPE) || clx.equals(Integer.class) || clx.equals(Integer.TYPE) || clx.equals(Long.class) || clx.equals(Long.TYPE) || clx.equals(Float.class) || clx.equals(Float.TYPE) || clx.equals(Double.class) || clx.equals(Double.TYPE) || clx.equals(Boolean.class) || clx.equals(Boolean.TYPE) || clx.equals(Character.class) || clx.equals(Character.TYPE) || clx.equals(String.class)) {
            return true;
        }
        return false;
    }

    private static class Data {
        Class<?> type;
        Object value;

        Data(Class<?> type2, Object value2) {
            this.type = type2;
            this.value = value2;
        }
    }
}

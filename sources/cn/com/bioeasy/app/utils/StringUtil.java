package cn.com.bioeasy.app.utils;

import com.alipay.sdk.util.h;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.Random;

public class StringUtil {
    private static final String[] types = {"int", "java.lang.String", "boolean", "char", "float", "double", "long", "short", "byte"};

    public static boolean isNullOrEmpty(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        return false;
    }

    public static String replaceString(String str, String... args) {
        if (!isNullOrEmpty(str)) {
            for (int i = 1; i <= args.length; i++) {
                str = str.replace("%" + i + "$s", args[i - 1]);
            }
        }
        return str;
    }

    public static <T> String objectToString(T object) {
        String obj;
        String obj2;
        String obj3;
        if (object == null) {
            return "Object{object is null}";
        }
        if (!object.toString().startsWith(object.getClass().getName() + "@")) {
            return object.toString();
        }
        StringBuilder builder = new StringBuilder(object.getClass().getSimpleName() + "{");
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            boolean flag = false;
            String[] strArr = types;
            int length = strArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (field.getType().getName().equalsIgnoreCase(strArr[i])) {
                    flag = true;
                    Object value = null;
                    try {
                        Object value2 = field.get(object);
                        Object[] objArr = new Object[2];
                        objArr[0] = field.getName();
                        if (value2 == null) {
                            obj3 = "null";
                        } else {
                            obj3 = value2.toString();
                        }
                        objArr[1] = obj3;
                        builder.append(String.format("%s=%s, ", objArr));
                    } catch (IllegalAccessException e) {
                        Object value3 = e;
                        Object[] objArr2 = new Object[2];
                        objArr2[0] = field.getName();
                        if (value3 == null) {
                            obj2 = "null";
                        } else {
                            obj2 = value3.toString();
                        }
                        objArr2[1] = obj2;
                        builder.append(String.format("%s=%s, ", objArr2));
                    } catch (Throwable th) {
                        Object[] objArr3 = new Object[2];
                        objArr3[0] = field.getName();
                        if (value == null) {
                            obj = "null";
                        } else {
                            obj = value.toString();
                        }
                        objArr3[1] = obj;
                        builder.append(String.format("%s=%s, ", objArr3));
                    }
                } else {
                    i++;
                }
            }
            if (!flag) {
                builder.append(String.format("%s=%s, ", new Object[]{field.getName(), "Object"}));
            }
        }
        return builder.replace(builder.length() - 2, builder.length() - 1, h.d).toString();
    }

    public static double distance(double long1, double lat1, double long2, double lat2) {
        double lat12 = (3.141592653589793d * lat1) / 180.0d;
        double lat22 = (3.141592653589793d * lat2) / 180.0d;
        double sa2 = Math.sin((lat12 - lat22) / 2.0d);
        double sb2 = Math.sin((((long1 - long2) * 3.141592653589793d) / 180.0d) / 2.0d);
        return 2.0d * 6378137.0d * Math.asin(Math.sqrt((sa2 * sa2) + (Math.cos(lat12) * Math.cos(lat22) * sb2 * sb2)));
    }

    public static String getRandomStringByLength(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append("abcdefghijklmnopqrstuvwxyz0123456789".charAt(random.nextInt("abcdefghijklmnopqrstuvwxyz0123456789".length())));
        }
        return sb.toString();
    }

    public static final String getMessageDigest(String data) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(data.getBytes("UTF-8"));
            char[] str = new char[(j * 2)];
            int k = 0;
            for (byte byte0 : mdTemp.digest()) {
                int k2 = k + 1;
                str[k] = hexDigits[(byte0 >>> 4) & 15];
                k = k2 + 1;
                str[k2] = hexDigits[byte0 & 15];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}

package cn.com.bioeasy.app.utils;

public class ObjectUtils {
    private ObjectUtils() {
        throw new AssertionError();
    }

    public static boolean isEquals(Object actual, Object expected) {
        return actual == expected || (actual != null ? actual.equals(expected) : expected == null);
    }

    public static String nullStrToEmpty(Object str) {
        if (str == null) {
            return "";
        }
        return str instanceof String ? (String) str : str.toString();
    }

    public static Long[] transformLongArray(long[] source) {
        Long[] destin = new Long[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = Long.valueOf(source[i]);
        }
        return destin;
    }

    public static long[] transformLongArray(Long[] source) {
        long[] destin = new long[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = source[i].longValue();
        }
        return destin;
    }

    public static Integer[] transformIntArray(int[] source) {
        Integer[] destin = new Integer[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = Integer.valueOf(source[i]);
        }
        return destin;
    }

    public static int[] transformIntArray(Integer[] source) {
        int[] destin = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = source[i].intValue();
        }
        return destin;
    }

    public static <V> int compare(V v1, V v2) {
        if (v1 == null) {
            return v2 == null ? 0 : -1;
        }
        if (v2 == null) {
            return 1;
        }
        return ((Comparable) v1).compareTo(v2);
    }
}

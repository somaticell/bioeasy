package cn.com.bioeasy.app.utils;

import android.util.Pair;
import java.util.Arrays;

public class ArrayUtils {
    private ArrayUtils() {
        throw new AssertionError();
    }

    public static <V> boolean isEmpty(V[] sourceArray) {
        return sourceArray == null || sourceArray.length == 0;
    }

    public static <V> V getLast(V[] sourceArray, V value, V defaultValue, boolean isCircle) {
        if (isEmpty(sourceArray)) {
            return defaultValue;
        }
        int currentPosition = -1;
        int i = 0;
        while (true) {
            if (i >= sourceArray.length) {
                break;
            } else if (ObjectUtils.isEquals(value, sourceArray[i])) {
                currentPosition = i;
                break;
            } else {
                i++;
            }
        }
        if (currentPosition == -1) {
            return defaultValue;
        }
        if (currentPosition != 0) {
            return sourceArray[currentPosition - 1];
        }
        if (isCircle) {
            return sourceArray[sourceArray.length - 1];
        }
        return defaultValue;
    }

    public static <V> V getNext(V[] sourceArray, V value, V defaultValue, boolean isCircle) {
        if (isEmpty(sourceArray)) {
            return defaultValue;
        }
        int currentPosition = -1;
        int i = 0;
        while (true) {
            if (i >= sourceArray.length) {
                break;
            } else if (ObjectUtils.isEquals(value, sourceArray[i])) {
                currentPosition = i;
                break;
            } else {
                i++;
            }
        }
        if (currentPosition == -1) {
            return defaultValue;
        }
        if (currentPosition != sourceArray.length - 1) {
            return sourceArray[currentPosition + 1];
        }
        if (isCircle) {
            return sourceArray[0];
        }
        return defaultValue;
    }

    public static <V> V getLast(V[] sourceArray, V value, boolean isCircle) {
        return getLast(sourceArray, value, (V) null, isCircle);
    }

    public static <V> V getNext(V[] sourceArray, V value, boolean isCircle) {
        return getNext(sourceArray, value, (V) null, isCircle);
    }

    public static long getLast(long[] sourceArray, long value, long defaultValue, boolean isCircle) {
        if (sourceArray.length != 0) {
            return ((Long) getLast((V[]) ObjectUtils.transformLongArray(sourceArray), Long.valueOf(value), Long.valueOf(defaultValue), isCircle)).longValue();
        }
        throw new IllegalArgumentException("The length of source array must be greater than 0.");
    }

    public static long getNext(long[] sourceArray, long value, long defaultValue, boolean isCircle) {
        if (sourceArray.length != 0) {
            return ((Long) getNext((V[]) ObjectUtils.transformLongArray(sourceArray), Long.valueOf(value), Long.valueOf(defaultValue), isCircle)).longValue();
        }
        throw new IllegalArgumentException("The length of source array must be greater than 0.");
    }

    public static int getLast(int[] sourceArray, int value, int defaultValue, boolean isCircle) {
        if (sourceArray.length != 0) {
            return ((Integer) getLast((V[]) ObjectUtils.transformIntArray(sourceArray), Integer.valueOf(value), Integer.valueOf(defaultValue), isCircle)).intValue();
        }
        throw new IllegalArgumentException("The length of source array must be greater than 0.");
    }

    public static int getNext(int[] sourceArray, int value, int defaultValue, boolean isCircle) {
        if (sourceArray.length != 0) {
            return ((Integer) getNext((V[]) ObjectUtils.transformIntArray(sourceArray), Integer.valueOf(value), Integer.valueOf(defaultValue), isCircle)).intValue();
        }
        throw new IllegalArgumentException("The length of source array must be greater than 0.");
    }

    public static int getArrayDimension(Object objects) {
        int dim = 0;
        int i = 0;
        while (i < objects.toString().length() && objects.toString().charAt(i) == '[') {
            dim++;
            i++;
        }
        return dim;
    }

    public static Pair<Pair<Integer, Integer>, String> arrayToObject(Object object) {
        int vertical;
        int cross;
        int i = 0;
        StringBuilder builder = new StringBuilder();
        if (object instanceof int[][]) {
            int[][] ints = (int[][]) object;
            cross = ints.length;
            vertical = cross == 0 ? 0 : ints[0].length;
            int length = ints.length;
            while (i < length) {
                builder.append(arrayToString(ints[i]).second + "\n");
                i++;
            }
        } else if (object instanceof byte[][]) {
            byte[][] ints2 = (byte[][]) object;
            cross = ints2.length;
            vertical = cross == 0 ? 0 : ints2[0].length;
            int length2 = ints2.length;
            while (i < length2) {
                builder.append(arrayToString(ints2[i]).second + "\n");
                i++;
            }
        } else if (object instanceof short[][]) {
            short[][] ints3 = (short[][]) object;
            cross = ints3.length;
            vertical = cross == 0 ? 0 : ints3[0].length;
            int length3 = ints3.length;
            while (i < length3) {
                builder.append(arrayToString(ints3[i]).second + "\n");
                i++;
            }
        } else if (object instanceof long[][]) {
            long[][] ints4 = (long[][]) object;
            cross = ints4.length;
            vertical = cross == 0 ? 0 : ints4[0].length;
            int length4 = ints4.length;
            while (i < length4) {
                builder.append(arrayToString(ints4[i]).second + "\n");
                i++;
            }
        } else if (object instanceof float[][]) {
            float[][] ints5 = (float[][]) object;
            cross = ints5.length;
            vertical = cross == 0 ? 0 : ints5[0].length;
            int length5 = ints5.length;
            while (i < length5) {
                builder.append(arrayToString(ints5[i]).second + "\n");
                i++;
            }
        } else if (object instanceof double[][]) {
            double[][] ints6 = (double[][]) object;
            cross = ints6.length;
            vertical = cross == 0 ? 0 : ints6[0].length;
            int length6 = ints6.length;
            while (i < length6) {
                builder.append(arrayToString(ints6[i]).second + "\n");
                i++;
            }
        } else if (object instanceof boolean[][]) {
            boolean[][] ints7 = (boolean[][]) object;
            cross = ints7.length;
            vertical = cross == 0 ? 0 : ints7[0].length;
            int length7 = ints7.length;
            while (i < length7) {
                builder.append(arrayToString(ints7[i]).second + "\n");
                i++;
            }
        } else if (object instanceof char[][]) {
            char[][] ints8 = (char[][]) object;
            cross = ints8.length;
            vertical = cross == 0 ? 0 : ints8[0].length;
            int length8 = ints8.length;
            while (i < length8) {
                builder.append(arrayToString(ints8[i]).second + "\n");
                i++;
            }
        } else {
            Object[][] objects = (Object[][]) object;
            cross = objects.length;
            vertical = cross == 0 ? 0 : objects[0].length;
            int length9 = objects.length;
            while (i < length9) {
                builder.append(arrayToString(objects[i]).second + "\n");
                i++;
            }
        }
        return Pair.create(Pair.create(Integer.valueOf(cross), Integer.valueOf(vertical)), builder.toString());
    }

    public static Pair arrayToString(Object object) {
        int length;
        StringBuilder builder = new StringBuilder("[");
        if (object instanceof int[]) {
            int[] ints = (int[]) object;
            length = ints.length;
            int length2 = ints.length;
            for (int i = 0; i < length2; i++) {
                builder.append(ints[i] + ",\t");
            }
        } else if (object instanceof byte[]) {
            byte[] bytes = (byte[]) object;
            length = bytes.length;
            int length3 = bytes.length;
            for (int i2 = 0; i2 < length3; i2++) {
                builder.append(bytes[i2] + ",\t");
            }
        } else if (object instanceof short[]) {
            short[] shorts = (short[]) object;
            length = shorts.length;
            int length4 = shorts.length;
            for (int i3 = 0; i3 < length4; i3++) {
                builder.append(shorts[i3] + ",\t");
            }
        } else if (object instanceof long[]) {
            long[] longs = (long[]) object;
            length = longs.length;
            int length5 = longs.length;
            for (int i4 = 0; i4 < length5; i4++) {
                builder.append(longs[i4] + ",\t");
            }
        } else if (object instanceof float[]) {
            float[] floats = (float[]) object;
            length = floats.length;
            int length6 = floats.length;
            for (int i5 = 0; i5 < length6; i5++) {
                builder.append(floats[i5] + ",\t");
            }
        } else if (object instanceof double[]) {
            double[] doubles = (double[]) object;
            length = doubles.length;
            int length7 = doubles.length;
            for (int i6 = 0; i6 < length7; i6++) {
                builder.append(doubles[i6] + ",\t");
            }
        } else if (object instanceof boolean[]) {
            boolean[] booleans = (boolean[]) object;
            length = booleans.length;
            int length8 = booleans.length;
            for (int i7 = 0; i7 < length8; i7++) {
                builder.append(booleans[i7] + ",\t");
            }
        } else if (object instanceof char[]) {
            char[] chars = (char[]) object;
            length = chars.length;
            int length9 = chars.length;
            for (int i8 = 0; i8 < length9; i8++) {
                builder.append(chars[i8] + ",\t");
            }
        } else {
            Object[] objects = (Object[]) object;
            length = objects.length;
            int length10 = objects.length;
            for (int i9 = 0; i9 < length10; i9++) {
                builder.append(StringUtil.objectToString(objects[i9]) + ",\t");
            }
        }
        return Pair.create(Integer.valueOf(length), builder.replace(builder.length() - 2, builder.length(), "]").toString());
    }

    public static boolean isArray(Object object) {
        return object.getClass().isArray();
    }

    public static char getType(Object object) {
        if (!isArray(object)) {
            return 0;
        }
        String str = object.toString();
        return str.substring(str.lastIndexOf("[") + 1, str.lastIndexOf("[") + 2).charAt(0);
    }

    private static void traverseArray(StringBuilder result, Object object) {
        if (!isArray(object)) {
            result.append(object.toString());
        } else if (getArrayDimension(object) == 1) {
            switch (getType(object)) {
                case 'B':
                    result.append(Arrays.toString((byte[]) object)).append("\n");
                    return;
                case 'D':
                    result.append(Arrays.toString((double[]) object)).append("\n");
                    return;
                case 'F':
                    result.append(Arrays.toString((float[]) object)).append("\n");
                    return;
                case 'I':
                    result.append(Arrays.toString((int[]) object)).append("\n");
                    return;
                case 'J':
                    result.append(Arrays.toString((long[]) object)).append("\n");
                    return;
                case 'L':
                    result.append(Arrays.toString((Object[]) object)).append("\n");
                    return;
                case 'S':
                    result.append(Arrays.toString((short[]) object)).append("\n");
                    return;
                case 'Z':
                    result.append(Arrays.toString((boolean[]) object)).append("\n");
                    return;
                default:
                    return;
            }
        } else {
            for (Object traverseArray : (Object[]) object) {
                traverseArray(result, traverseArray);
            }
        }
    }

    public static String traverseArray(Object object) {
        StringBuilder result = new StringBuilder();
        traverseArray(result, object);
        return result.toString();
    }
}

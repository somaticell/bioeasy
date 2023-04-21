package jxl.biff;

public class DoubleHelper {
    private DoubleHelper() {
    }

    public static double getIEEEDouble(byte[] data, int pos) {
        int num1 = IntegerHelper.getInt(data[pos], data[pos + 1], data[pos + 2], data[pos + 3]);
        int num2 = IntegerHelper.getInt(data[pos + 4], data[pos + 5], data[pos + 6], data[pos + 7]);
        boolean negative = (Integer.MIN_VALUE & num2) != 0;
        double value = Double.longBitsToDouble((((long) (Integer.MAX_VALUE & num2)) * 4294967296L) + (num1 < 0 ? ((long) num1) + 4294967296L : (long) num1));
        if (negative) {
            return -value;
        }
        return value;
    }

    public static void getIEEEBytes(double d, byte[] target, int pos) {
        long val = Double.doubleToLongBits(d);
        target[pos] = (byte) ((int) (255 & val));
        target[pos + 1] = (byte) ((int) ((65280 & val) >> 8));
        target[pos + 2] = (byte) ((int) ((16711680 & val) >> 16));
        target[pos + 3] = (byte) ((int) ((-16777216 & val) >> 24));
        target[pos + 4] = (byte) ((int) ((1095216660480L & val) >> 32));
        target[pos + 5] = (byte) ((int) ((280375465082880L & val) >> 40));
        target[pos + 6] = (byte) ((int) ((71776119061217280L & val) >> 48));
        target[pos + 7] = (byte) ((int) ((-72057594037927936L & val) >> 56));
    }
}

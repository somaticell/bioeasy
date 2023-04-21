package jxl.read.biff;

final class RKHelper {
    private RKHelper() {
    }

    public static double getDouble(int rk) {
        if ((rk & 2) != 0) {
            double value = (double) (rk >> 2);
            if ((rk & 1) != 0) {
                return value / 100.0d;
            }
            return value;
        }
        double value2 = Double.longBitsToDouble(((long) (rk & -4)) << 32);
        if ((rk & 1) != 0) {
            return value2 / 100.0d;
        }
        return value2;
    }
}

package jxl.biff;

import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;

public final class IntegerHelper {
    private IntegerHelper() {
    }

    public static int getInt(byte b1, byte b2) {
        return ((b2 & 255) << 8) | (b1 & 255);
    }

    public static short getShort(byte b1, byte b2) {
        return (short) ((((short) (b2 & BLEServiceApi.CONNECTED_STATUS)) << 8) | ((short) (b1 & BLEServiceApi.CONNECTED_STATUS)));
    }

    public static int getInt(byte b1, byte b2, byte b3, byte b4) {
        return (getInt(b3, b4) << 16) | getInt(b1, b2);
    }

    public static byte[] getTwoBytes(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((65280 & i) >> 8)};
    }

    public static byte[] getFourBytes(int i) {
        byte[] bytes = new byte[4];
        getTwoBytes(i & 65535, bytes, 0);
        getTwoBytes((-65536 & i) >> 16, bytes, 2);
        return bytes;
    }

    public static void getTwoBytes(int i, byte[] target, int pos) {
        byte[] bytes = getTwoBytes(i);
        target[pos] = bytes[0];
        target[pos + 1] = bytes[1];
    }

    public static void getFourBytes(int i, byte[] target, int pos) {
        byte[] bytes = getFourBytes(i);
        target[pos] = bytes[0];
        target[pos + 1] = bytes[1];
        target[pos + 2] = bytes[2];
        target[pos + 3] = bytes[3];
    }
}

package com.lvrenyang.io;

import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import java.util.List;
import java.util.Locale;
import java.util.Random;

class ByteUtils {
    ByteUtils() {
    }

    public static byte[] getRandomByteArray(int nlength) {
        byte[] data = new byte[nlength];
        Random rmByte = new Random(System.currentTimeMillis());
        for (int i = 0; i < nlength; i++) {
            data[i] = (byte) rmByte.nextInt(256);
        }
        return data;
    }

    public static boolean bytesEquals(byte[] d1, int offset1, byte[] d2, int offset2, int length) {
        if (d1 == null || d2 == null || offset1 + length > d1.length || offset2 + length > d2.length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (d1[i + offset1] != d2[i + offset2]) {
                return false;
            }
        }
        return true;
    }

    public static byte[] byteArraysToBytes(byte[][] data) {
        int length = 0;
        for (byte[] length2 : data) {
            length += length2.length;
        }
        byte[] send = new byte[length];
        int k = 0;
        for (int i = 0; i < data.length; i++) {
            int j = 0;
            while (j < data[i].length) {
                send[k] = data[i][j];
                j++;
                k++;
            }
        }
        return send;
    }

    public static byte[] ByteArrayListToBytes(List<byte[]> data) {
        int length = 0;
        for (int i = 0; i < data.size(); i++) {
            length += data.get(i).length;
        }
        byte[] bytes = new byte[length];
        int k = 0;
        for (int i2 = 0; i2 < data.size(); i2++) {
            int j = 0;
            while (j < data.get(i2).length) {
                bytes[k] = data.get(i2)[j];
                j++;
                k++;
            }
        }
        return bytes;
    }

    public static String bytesToStr(byte[] rcs) {
        if (rcs == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < rcs.length; i++) {
            String tmp = Integer.toHexString(rcs[i] & BLEServiceApi.CONNECTED_STATUS).toUpperCase(Locale.getDefault());
            if (tmp.length() == 1) {
                stringBuilder.append("0" + tmp);
            } else {
                stringBuilder.append("" + tmp);
            }
            if (i % 16 != 15) {
                stringBuilder.append(" ");
            } else {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public static byte[] macStringToBytes(String macString) {
        byte[] mac = new byte[6];
        for (int i = 0; i < mac.length; i++) {
            try {
                mac[i] = (byte) Integer.parseInt(macString.substring(i * 3, (i * 3) + 2), 16);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mac;
    }
}

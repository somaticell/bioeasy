package com.ble.api;

import android.util.Log;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import java.util.ArrayList;
import java.util.List;

public class DataUtil {
    private static int a(int i, int i2) {
        if (i2 >= 0) {
            return (i >> i2) & 1;
        }
        throw new IllegalArgumentException("bitIndex must >= 0");
    }

    public static short buildUint16(byte b, byte b2) {
        return (short) ((b << 8) + (b2 & BLEServiceApi.CONNECTED_STATUS));
    }

    public static String byteArrayToHex(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bArr.length);
        for (int i = 0; i < bArr.length; i++) {
            sb.append(String.format("%02X", new Object[]{Byte.valueOf(bArr[i])}));
            if (i < bArr.length - 1) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    public static String byteToBinaryString(byte b) {
        StringBuilder sb = new StringBuilder(8);
        for (int i = 7; i > -1; i--) {
            sb.append(a(b, i));
        }
        Log.d("DataUtil", "byteToBinaryString() - " + b + ": " + sb.toString());
        return sb.toString();
    }

    public static byte[] hexToByteArray(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (str.length() % 2 == 1) {
            str = String.valueOf(0) + str;
        }
        String[] strArr = new String[(str.length() / 2)];
        byte[] bArr = new byte[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = str.substring(i * 2, (i + 1) * 2);
            bArr[i] = (byte) Integer.parseInt(strArr[i], 16);
        }
        return bArr;
    }

    public static byte[] hexToReversedByteArray(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (str.length() % 2 == 1) {
            str = String.valueOf(0) + str;
        }
        String[] strArr = new String[(str.length() / 2)];
        byte[] bArr = new byte[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = str.substring(((strArr.length - 1) - i) * 2, (strArr.length - i) * 2);
            try {
                bArr[i] = (byte) Integer.parseInt(strArr[i], 16);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return bArr;
    }

    public static byte hiUint16(short s) {
        return (byte) (s >> 8);
    }

    public static byte loUint16(short s) {
        return (byte) (s & 255);
    }

    public static List<byte[]> splitData(byte[] bArr, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("subLength must > 0");
        } else if (bArr == null) {
            return null;
        } else {
            ArrayList arrayList = new ArrayList();
            int i2 = 0;
            while (i2 < bArr.length) {
                int length = i2 + i > bArr.length ? bArr.length - i2 : i;
                byte[] bArr2 = new byte[length];
                System.arraycopy(bArr, i2, bArr2, 0, length);
                arrayList.add(bArr2);
                i2 += i;
            }
            return arrayList;
        }
    }
}

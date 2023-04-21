package com.baidu.android.bbalbs.common.a;

import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import java.io.UnsupportedEncodingException;

public final class b {
    private static final byte[] a = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};

    public static String a(byte[] bArr, String str) throws UnsupportedEncodingException {
        int i;
        int length = (bArr.length * 4) / 3;
        byte[] bArr2 = new byte[(length + (length / 76) + 3)];
        int length2 = bArr.length - (bArr.length % 3);
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < length2; i4 += 3) {
            int i5 = i3 + 1;
            bArr2[i3] = a[(bArr[i4] & BLEServiceApi.CONNECTED_STATUS) >> 2];
            int i6 = i5 + 1;
            bArr2[i5] = a[((bArr[i4] & 3) << 4) | ((bArr[i4 + 1] & BLEServiceApi.CONNECTED_STATUS) >> 4)];
            int i7 = i6 + 1;
            bArr2[i6] = a[((bArr[i4 + 1] & 15) << 2) | ((bArr[i4 + 2] & BLEServiceApi.CONNECTED_STATUS) >> 6)];
            int i8 = i7 + 1;
            bArr2[i7] = a[bArr[i4 + 2] & 63];
            if ((i8 - i2) % 76 != 0 || i8 == 0) {
                i3 = i8;
            } else {
                i3 = i8 + 1;
                bArr2[i8] = 10;
                i2++;
            }
        }
        switch (bArr.length % 3) {
            case 1:
                int i9 = i3 + 1;
                bArr2[i3] = a[(bArr[length2] & BLEServiceApi.CONNECTED_STATUS) >> 2];
                int i10 = i9 + 1;
                bArr2[i9] = a[(bArr[length2] & 3) << 4];
                int i11 = i10 + 1;
                bArr2[i10] = 61;
                i = i11 + 1;
                bArr2[i11] = 61;
                break;
            case 2:
                int i12 = i3 + 1;
                bArr2[i3] = a[(bArr[length2] & BLEServiceApi.CONNECTED_STATUS) >> 2];
                int i13 = i12 + 1;
                bArr2[i12] = a[((bArr[length2] & 3) << 4) | ((bArr[length2 + 1] & BLEServiceApi.CONNECTED_STATUS) >> 4)];
                int i14 = i13 + 1;
                bArr2[i13] = a[(bArr[length2 + 1] & 15) << 2];
                i = i14 + 1;
                bArr2[i14] = 61;
                break;
            default:
                i = i3;
                break;
        }
        return new String(bArr2, 0, i, str);
    }

    public static byte[] a(byte[] bArr) {
        return a(bArr, bArr.length);
    }

    public static byte[] a(byte[] bArr, int i) {
        byte b;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = (i / 4) * 3;
        if (i6 == 0) {
            return new byte[0];
        }
        byte[] bArr2 = new byte[i6];
        int i7 = 0;
        while (true) {
            byte b2 = bArr[i - 1];
            if (!(b2 == 10 || b2 == 13 || b2 == 32 || b2 == 9)) {
                if (b2 != 61) {
                    break;
                }
                i7++;
            }
            i--;
        }
        int i8 = 0;
        byte b3 = 0;
        int i9 = 0;
        int i10 = 0;
        while (i8 < i) {
            byte b4 = bArr[i8];
            if (b4 == 10 || b4 == 13 || b4 == 32) {
                b = b3;
                i2 = i10;
                i3 = i9;
            } else if (b4 == 9) {
                b = b3;
                i2 = i10;
                i3 = i9;
            } else {
                if (b4 >= 65 && b4 <= 90) {
                    i4 = b4 - 65;
                } else if (b4 >= 97 && b4 <= 122) {
                    i4 = b4 - 71;
                } else if (b4 >= 48 && b4 <= 57) {
                    i4 = b4 + 4;
                } else if (b4 == 43) {
                    i4 = 62;
                } else if (b4 != 47) {
                    return null;
                } else {
                    i4 = 63;
                }
                byte b5 = (b3 << 6) | ((byte) i4);
                if (i9 % 4 == 3) {
                    int i11 = i10 + 1;
                    bArr2[i10] = (byte) ((16711680 & b5) >> 16);
                    int i12 = i11 + 1;
                    bArr2[i11] = (byte) ((65280 & b5) >> 8);
                    i5 = i12 + 1;
                    bArr2[i12] = (byte) (b5 & BLEServiceApi.CONNECTED_STATUS);
                } else {
                    i5 = i10;
                }
                i3 = i9 + 1;
                byte b6 = b5;
                i2 = i5;
                b = b6;
            }
            i8++;
            i9 = i3;
            i10 = i2;
            b3 = b;
        }
        if (i7 > 0) {
            int i13 = b3 << (i7 * 6);
            int i14 = i10 + 1;
            bArr2[i10] = (byte) ((16711680 & i13) >> 16);
            if (i7 == 1) {
                i10 = i14 + 1;
                bArr2[i14] = (byte) ((65280 & i13) >> 8);
            } else {
                i10 = i14;
            }
        }
        byte[] bArr3 = new byte[i10];
        System.arraycopy(bArr2, 0, bArr3, 0, i10);
        return bArr3;
    }
}

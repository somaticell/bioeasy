package com.alipay.sdk.encrypt;

public final class a {
    private static final int a = 128;
    private static final int b = 64;
    private static final int c = 24;
    private static final int d = 8;
    private static final int e = 16;
    private static final int f = 4;
    private static final int g = -128;
    private static final char h = '=';
    private static final byte[] i = new byte[128];
    private static final char[] j = new char[64];

    static {
        int i2 = 0;
        for (int i3 = 0; i3 < 128; i3++) {
            i[i3] = -1;
        }
        for (int i4 = 90; i4 >= 65; i4--) {
            i[i4] = (byte) (i4 - 65);
        }
        for (int i5 = 122; i5 >= 97; i5--) {
            i[i5] = (byte) ((i5 - 97) + 26);
        }
        for (int i6 = 57; i6 >= 48; i6--) {
            i[i6] = (byte) ((i6 - 48) + 52);
        }
        i[43] = 62;
        i[47] = 63;
        for (int i7 = 0; i7 <= 25; i7++) {
            j[i7] = (char) (i7 + 65);
        }
        int i8 = 26;
        int i9 = 0;
        while (i8 <= 51) {
            j[i8] = (char) (i9 + 97);
            i8++;
            i9++;
        }
        int i10 = 52;
        while (i10 <= 61) {
            j[i10] = (char) (i2 + 48);
            i10++;
            i2++;
        }
        j[62] = '+';
        j[63] = '/';
    }

    private static boolean a(char c2) {
        return c2 == ' ' || c2 == 13 || c2 == 10 || c2 == 9;
    }

    private static boolean b(char c2) {
        return c2 == '=';
    }

    private static boolean c(char c2) {
        return c2 < 128 && i[c2] != -1;
    }

    public static String a(byte[] bArr) {
        int i2;
        int i3 = 0;
        if (bArr == null) {
            return null;
        }
        int length = bArr.length * 8;
        if (length == 0) {
            return "";
        }
        int i4 = length % 24;
        int i5 = length / 24;
        char[] cArr = new char[((i4 != 0 ? i5 + 1 : i5) * 4)];
        int i6 = 0;
        int i7 = 0;
        while (i6 < i5) {
            int i8 = i3 + 1;
            byte b2 = bArr[i3];
            int i9 = i8 + 1;
            byte b3 = bArr[i8];
            int i10 = i9 + 1;
            byte b4 = bArr[i9];
            byte b5 = (byte) (b3 & 15);
            byte b6 = (byte) (b2 & 3);
            byte b7 = (b2 & Byte.MIN_VALUE) == 0 ? (byte) (b2 >> 2) : (byte) ((b2 >> 2) ^ 192);
            byte b8 = (b3 & Byte.MIN_VALUE) == 0 ? (byte) (b3 >> 4) : (byte) ((b3 >> 4) ^ 240);
            if ((b4 & Byte.MIN_VALUE) == 0) {
                i2 = b4 >> 6;
            } else {
                i2 = (b4 >> 6) ^ 252;
            }
            int i11 = i7 + 1;
            cArr[i7] = j[b7];
            int i12 = i11 + 1;
            cArr[i11] = j[b8 | (b6 << 4)];
            int i13 = i12 + 1;
            cArr[i12] = j[((byte) i2) | (b5 << 2)];
            cArr[i13] = j[b4 & 63];
            i6++;
            i7 = i13 + 1;
            i3 = i10;
        }
        if (i4 == 8) {
            byte b9 = bArr[i3];
            byte b10 = (byte) (b9 & 3);
            int i14 = i7 + 1;
            cArr[i7] = j[(b9 & Byte.MIN_VALUE) == 0 ? (byte) (b9 >> 2) : (byte) ((b9 >> 2) ^ 192)];
            int i15 = i14 + 1;
            cArr[i14] = j[b10 << 4];
            cArr[i15] = h;
            cArr[i15 + 1] = h;
        } else if (i4 == 16) {
            byte b11 = bArr[i3];
            byte b12 = bArr[i3 + 1];
            byte b13 = (byte) (b12 & 15);
            byte b14 = (byte) (b11 & 3);
            byte b15 = (b11 & Byte.MIN_VALUE) == 0 ? (byte) (b11 >> 2) : (byte) ((b11 >> 2) ^ 192);
            byte b16 = (b12 & Byte.MIN_VALUE) == 0 ? (byte) (b12 >> 4) : (byte) ((b12 >> 4) ^ 240);
            int i16 = i7 + 1;
            cArr[i7] = j[b15];
            int i17 = i16 + 1;
            cArr[i16] = j[b16 | (b14 << 4)];
            cArr[i17] = j[b13 << 2];
            cArr[i17 + 1] = h;
        }
        return new String(cArr);
    }

    public static byte[] a(String str) {
        int i2;
        int i3;
        if (str == null) {
            return null;
        }
        char[] charArray = str.toCharArray();
        if (charArray == null) {
            i2 = 0;
        } else {
            int length = charArray.length;
            int i4 = 0;
            i2 = 0;
            while (i4 < length) {
                char c2 = charArray[i4];
                if (!(c2 == ' ' || c2 == 13 || c2 == 10 || c2 == 9)) {
                    i3 = i2 + 1;
                    charArray[i2] = charArray[i4];
                } else {
                    i3 = i2;
                }
                i4++;
                i2 = i3;
            }
        }
        if (i2 % 4 != 0) {
            return null;
        }
        int i5 = i2 / 4;
        if (i5 == 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[(i5 * 3)];
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (i8 < i5 - 1) {
            int i9 = i6 + 1;
            char c3 = charArray[i6];
            if (c(c3)) {
                int i10 = i9 + 1;
                char c4 = charArray[i9];
                if (c(c4)) {
                    int i11 = i10 + 1;
                    char c5 = charArray[i10];
                    if (c(c5)) {
                        i6 = i11 + 1;
                        char c6 = charArray[i11];
                        if (c(c6)) {
                            byte b2 = i[c3];
                            byte b3 = i[c4];
                            byte b4 = i[c5];
                            byte b5 = i[c6];
                            int i12 = i7 + 1;
                            bArr[i7] = (byte) ((b2 << 2) | (b3 >> 4));
                            int i13 = i12 + 1;
                            bArr[i12] = (byte) (((b3 & 15) << 4) | ((b4 >> 2) & 15));
                            i7 = i13 + 1;
                            bArr[i13] = (byte) ((b4 << 6) | b5);
                            i8++;
                        }
                    }
                }
            }
            return null;
        }
        int i14 = i6 + 1;
        char c7 = charArray[i6];
        if (c(c7)) {
            int i15 = i14 + 1;
            char c8 = charArray[i14];
            if (c(c8)) {
                byte b6 = i[c7];
                byte b7 = i[c8];
                int i16 = i15 + 1;
                char c9 = charArray[i15];
                char c10 = charArray[i16];
                if (c(c9) && c(c10)) {
                    byte b8 = i[c9];
                    byte b9 = i[c10];
                    int i17 = i7 + 1;
                    bArr[i7] = (byte) ((b6 << 2) | (b7 >> 4));
                    bArr[i17] = (byte) (((b7 & 15) << 4) | ((b8 >> 2) & 15));
                    bArr[i17 + 1] = (byte) (b9 | (b8 << 6));
                    return bArr;
                } else if (!b(c9) || !b(c10)) {
                    if (b(c9) || !b(c10)) {
                        return null;
                    }
                    byte b10 = i[c9];
                    if ((b10 & 3) != 0) {
                        return null;
                    }
                    byte[] bArr2 = new byte[((i8 * 3) + 2)];
                    System.arraycopy(bArr, 0, bArr2, 0, i8 * 3);
                    bArr2[i7] = (byte) ((b6 << 2) | (b7 >> 4));
                    bArr2[i7 + 1] = (byte) (((b7 & 15) << 4) | ((b10 >> 2) & 15));
                    return bArr2;
                } else if ((b7 & 15) != 0) {
                    return null;
                } else {
                    byte[] bArr3 = new byte[((i8 * 3) + 1)];
                    System.arraycopy(bArr, 0, bArr3, 0, i8 * 3);
                    bArr3[i7] = (byte) ((b6 << 2) | (b7 >> 4));
                    return bArr3;
                }
            }
        }
        return null;
    }

    private static int a(char[] cArr) {
        boolean z;
        int i2;
        if (cArr == null) {
            return 0;
        }
        int length = cArr.length;
        int i3 = 0;
        int i4 = 0;
        while (i3 < length) {
            char c2 = cArr[i3];
            if (c2 == ' ' || c2 == 13 || c2 == 10 || c2 == 9) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                i2 = i4 + 1;
                cArr[i4] = cArr[i3];
            } else {
                i2 = i4;
            }
            i3++;
            i4 = i2;
        }
        return i4;
    }
}

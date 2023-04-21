package com.loc;

import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* compiled from: Encrypt */
public class r {
    private static final char[] a = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static final byte[] b = new byte[128];

    static {
        for (int i = 0; i < 128; i++) {
            b[i] = -1;
        }
        for (int i2 = 65; i2 <= 90; i2++) {
            b[i2] = (byte) (i2 - 65);
        }
        for (int i3 = 97; i3 <= 122; i3++) {
            b[i3] = (byte) ((i3 - 97) + 26);
        }
        for (int i4 = 48; i4 <= 57; i4++) {
            b[i4] = (byte) ((i4 - 48) + 52);
        }
        b[43] = 62;
        b[47] = 63;
    }

    static byte[] a(byte[] bArr, Key key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        instance.init(1, key);
        return instance.doFinal(bArr);
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0031 A[LOOP:2: B:16:0x0031->B:15:0x0030, LOOP_START, PHI: r2 
      PHI: (r2v1 int) = (r2v0 int), (r2v11 int) binds: [B:11:0x0023, B:15:0x0030] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x007f A[LOOP:0: B:7:0x0015->B:36:0x007f, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0025 A[EDGE_INSN: B:42:0x0025->B:12:0x0025 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0025 A[EDGE_INSN: B:43:0x0025->B:12:0x0025 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0025 A[EDGE_INSN: B:45:0x0025->B:12:0x0025 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0025 A[EDGE_INSN: B:46:0x0025->B:12:0x0025 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] a(java.lang.String r9) {
        /*
            r8 = 61
            r1 = 0
            r7 = -1
            if (r9 != 0) goto L_0x0009
            byte[] r0 = new byte[r1]
        L_0x0008:
            return r0
        L_0x0009:
            java.lang.String r0 = "UTF-8"
            byte[] r0 = r9.getBytes(r0)     // Catch:{ UnsupportedEncodingException -> 0x002a }
        L_0x000f:
            int r3 = r0.length
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream
            r4.<init>(r3)
        L_0x0015:
            if (r1 >= r3) goto L_0x0025
        L_0x0017:
            byte[] r5 = b
            int r2 = r1 + 1
            byte r1 = r0[r1]
            byte r5 = r5[r1]
            if (r2 >= r3) goto L_0x0023
            if (r5 == r7) goto L_0x008c
        L_0x0023:
            if (r5 != r7) goto L_0x0031
        L_0x0025:
            byte[] r0 = r4.toByteArray()
            goto L_0x0008
        L_0x002a:
            r0 = move-exception
            byte[] r0 = r9.getBytes()
            goto L_0x000f
        L_0x0030:
            r2 = r1
        L_0x0031:
            byte[] r6 = b
            int r1 = r2 + 1
            byte r2 = r0[r2]
            byte r6 = r6[r2]
            if (r1 >= r3) goto L_0x003d
            if (r6 == r7) goto L_0x0030
        L_0x003d:
            if (r6 == r7) goto L_0x0025
            int r2 = r5 << 2
            r5 = r6 & 48
            int r5 = r5 >>> 4
            r2 = r2 | r5
            r4.write(r2)
        L_0x0049:
            int r2 = r1 + 1
            byte r1 = r0[r1]
            if (r1 != r8) goto L_0x0054
            byte[] r0 = r4.toByteArray()
            goto L_0x0008
        L_0x0054:
            byte[] r5 = b
            byte r5 = r5[r1]
            if (r2 >= r3) goto L_0x005c
            if (r5 == r7) goto L_0x008a
        L_0x005c:
            if (r5 == r7) goto L_0x0025
            r1 = r6 & 15
            int r1 = r1 << 4
            r6 = r5 & 60
            int r6 = r6 >>> 2
            r1 = r1 | r6
            r4.write(r1)
        L_0x006a:
            int r1 = r2 + 1
            byte r2 = r0[r2]
            if (r2 != r8) goto L_0x0075
            byte[] r0 = r4.toByteArray()
            goto L_0x0008
        L_0x0075:
            byte[] r6 = b
            byte r2 = r6[r2]
            if (r1 >= r3) goto L_0x007d
            if (r2 == r7) goto L_0x0088
        L_0x007d:
            if (r2 == r7) goto L_0x0025
            r5 = r5 & 3
            int r5 = r5 << 6
            r2 = r2 | r5
            r4.write(r2)
            goto L_0x0015
        L_0x0088:
            r2 = r1
            goto L_0x006a
        L_0x008a:
            r1 = r2
            goto L_0x0049
        L_0x008c:
            r1 = r2
            goto L_0x0017
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.r.a(java.lang.String):byte[]");
    }

    private static String b(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = bArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            int i2 = i + 1;
            byte b2 = bArr[i] & BLEServiceApi.CONNECTED_STATUS;
            if (i2 == length) {
                stringBuffer.append(a[b2 >>> 2]);
                stringBuffer.append(a[(b2 & 3) << 4]);
                stringBuffer.append("==");
                break;
            }
            int i3 = i2 + 1;
            byte b3 = bArr[i2] & BLEServiceApi.CONNECTED_STATUS;
            if (i3 == length) {
                stringBuffer.append(a[b2 >>> 2]);
                stringBuffer.append(a[((b2 & 3) << 4) | ((b3 & 240) >>> 4)]);
                stringBuffer.append(a[(b3 & 15) << 2]);
                stringBuffer.append("=");
                break;
            }
            i = i3 + 1;
            byte b4 = bArr[i3] & BLEServiceApi.CONNECTED_STATUS;
            stringBuffer.append(a[b2 >>> 2]);
            stringBuffer.append(a[((b2 & 3) << 4) | ((b3 & 240) >>> 4)]);
            stringBuffer.append(a[((b3 & 15) << 2) | ((b4 & 192) >>> 6)]);
            stringBuffer.append(a[b4 & 63]);
        }
        return stringBuffer.toString();
    }

    public static String a(byte[] bArr) {
        try {
            return b(bArr);
        } catch (Throwable th) {
            y.a(th, "Encrypt", "encodeBase64");
            return null;
        }
    }
}

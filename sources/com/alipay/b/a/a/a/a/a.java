package com.alipay.b.a.a.a.a;

import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;

public final class a {
    private static char[] a = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static byte[] b = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, BLEServiceApi.LED_CMD, BLEServiceApi.FIRMWARE_CMD, BLEServiceApi.POWER_CMD, 36, 37, BLEServiceApi.POWER_STATUS_CMD, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x007c A[LOOP:0: B:1:0x0010->B:26:0x007c, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0086 A[EDGE_INSN: B:33:0x0086->B:27:0x0086 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0086 A[EDGE_INSN: B:34:0x0086->B:27:0x0086 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0086 A[EDGE_INSN: B:36:0x0086->B:27:0x0086 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0086 A[EDGE_INSN: B:37:0x0086->B:27:0x0086 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x0020 A[LOOP:2: B:6:0x0020->B:30:0x0095, LOOP_START, PHI: r1 
      PHI: (r1v2 int) = (r1v1 int), (r1v16 int) binds: [B:5:0x001e, B:30:0x0095] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] a(java.lang.String r9) {
        /*
            r8 = 61
            r7 = -1
            java.lang.StringBuffer r2 = new java.lang.StringBuffer
            r2.<init>()
            java.lang.String r0 = "US-ASCII"
            byte[] r3 = r9.getBytes(r0)
            int r4 = r3.length
            r0 = 0
        L_0x0010:
            if (r0 >= r4) goto L_0x0086
        L_0x0012:
            byte[] r5 = b
            int r1 = r0 + 1
            byte r0 = r3[r0]
            byte r5 = r5[r0]
            if (r1 >= r4) goto L_0x001e
            if (r5 == r7) goto L_0x0097
        L_0x001e:
            if (r5 == r7) goto L_0x0086
        L_0x0020:
            byte[] r6 = b
            int r0 = r1 + 1
            byte r1 = r3[r1]
            byte r6 = r6[r1]
            if (r0 >= r4) goto L_0x002c
            if (r6 == r7) goto L_0x0095
        L_0x002c:
            if (r6 == r7) goto L_0x0086
            int r1 = r5 << 2
            r5 = r6 & 48
            int r5 = r5 >>> 4
            r1 = r1 | r5
            char r1 = (char) r1
            r2.append(r1)
        L_0x0039:
            int r1 = r0 + 1
            byte r0 = r3[r0]
            if (r0 != r8) goto L_0x004a
            java.lang.String r0 = r2.toString()
            java.lang.String r1 = "iso8859-1"
            byte[] r0 = r0.getBytes(r1)
        L_0x0049:
            return r0
        L_0x004a:
            byte[] r5 = b
            byte r5 = r5[r0]
            if (r1 >= r4) goto L_0x0052
            if (r5 == r7) goto L_0x0093
        L_0x0052:
            if (r5 == r7) goto L_0x0086
            r0 = r6 & 15
            int r0 = r0 << 4
            r6 = r5 & 60
            int r6 = r6 >>> 2
            r0 = r0 | r6
            char r0 = (char) r0
            r2.append(r0)
        L_0x0061:
            int r0 = r1 + 1
            byte r1 = r3[r1]
            if (r1 != r8) goto L_0x0072
            java.lang.String r0 = r2.toString()
            java.lang.String r1 = "iso8859-1"
            byte[] r0 = r0.getBytes(r1)
            goto L_0x0049
        L_0x0072:
            byte[] r6 = b
            byte r1 = r6[r1]
            if (r0 >= r4) goto L_0x007a
            if (r1 == r7) goto L_0x0091
        L_0x007a:
            if (r1 == r7) goto L_0x0086
            r5 = r5 & 3
            int r5 = r5 << 6
            r1 = r1 | r5
            char r1 = (char) r1
            r2.append(r1)
            goto L_0x0010
        L_0x0086:
            java.lang.String r0 = r2.toString()
            java.lang.String r1 = "iso8859-1"
            byte[] r0 = r0.getBytes(r1)
            goto L_0x0049
        L_0x0091:
            r1 = r0
            goto L_0x0061
        L_0x0093:
            r0 = r1
            goto L_0x0039
        L_0x0095:
            r1 = r0
            goto L_0x0020
        L_0x0097:
            r0 = r1
            goto L_0x0012
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.b.a.a.a.a.a.a(java.lang.String):byte[]");
    }
}

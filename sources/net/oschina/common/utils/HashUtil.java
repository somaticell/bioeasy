package net.oschina.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashUtil {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static String convertToHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (byte a : b) {
            sb.append(HEX_DIGITS[(a & 240) >>> 4]);
            sb.append(HEX_DIGITS[a & 15]);
        }
        return sb.toString();
    }

    public static String getMD5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            return convertToHexString(md5.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0041 A[SYNTHETIC, Splitter:B:28:0x0041] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getMD5(java.io.File r8) {
        /*
            r6 = 0
            java.lang.String r7 = "MD5"
            java.security.MessageDigest r4 = java.security.MessageDigest.getInstance(r7)     // Catch:{ NoSuchAlgorithmException -> 0x0024 }
            r2 = 0
            r7 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r7]
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x004d, all -> 0x003e }
            r3.<init>(r8)     // Catch:{ Exception -> 0x004d, all -> 0x003e }
        L_0x0011:
            int r5 = r3.read(r0)     // Catch:{ Exception -> 0x001c, all -> 0x004a }
            if (r5 <= 0) goto L_0x0026
            r7 = 0
            r4.update(r0, r7, r5)     // Catch:{ Exception -> 0x001c, all -> 0x004a }
            goto L_0x0011
        L_0x001c:
            r1 = move-exception
            r2 = r3
        L_0x001e:
            if (r2 == 0) goto L_0x0023
            r2.close()     // Catch:{ IOException -> 0x0039 }
        L_0x0023:
            return r6
        L_0x0024:
            r1 = move-exception
            goto L_0x0023
        L_0x0026:
            byte[] r7 = r4.digest()     // Catch:{ Exception -> 0x001c, all -> 0x004a }
            java.lang.String r6 = convertToHexString(r7)     // Catch:{ Exception -> 0x001c, all -> 0x004a }
            if (r3 == 0) goto L_0x0023
            r3.close()     // Catch:{ IOException -> 0x0034 }
            goto L_0x0023
        L_0x0034:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0023
        L_0x0039:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0023
        L_0x003e:
            r6 = move-exception
        L_0x003f:
            if (r2 == 0) goto L_0x0044
            r2.close()     // Catch:{ IOException -> 0x0045 }
        L_0x0044:
            throw r6
        L_0x0045:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0044
        L_0x004a:
            r6 = move-exception
            r2 = r3
            goto L_0x003f
        L_0x004d:
            r1 = move-exception
            goto L_0x001e
        */
        throw new UnsupportedOperationException("Method not decompiled: net.oschina.common.utils.HashUtil.getMD5(java.io.File):java.lang.String");
    }
}

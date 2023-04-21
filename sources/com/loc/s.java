package com.loc;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: MD5 */
public class s {
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0085 A[SYNTHETIC, Splitter:B:46:0x0085] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r6) {
        /*
            r0 = 0
            r1 = 0
            boolean r2 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Throwable -> 0x0094, all -> 0x0080 }
            if (r2 == 0) goto L_0x0017
            if (r0 == 0) goto L_0x000d
            r1.close()     // Catch:{ IOException -> 0x000e }
        L_0x000d:
            return r0
        L_0x000e:
            r1 = move-exception
            java.lang.String r2 = "MD5"
            java.lang.String r3 = "getMd5FromFile"
            com.loc.y.a(r1, r2, r3)
            goto L_0x000d
        L_0x0017:
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x0094, all -> 0x0080 }
            r3.<init>(r6)     // Catch:{ Throwable -> 0x0094, all -> 0x0080 }
            boolean r2 = r3.isFile()     // Catch:{ Throwable -> 0x0094, all -> 0x0080 }
            if (r2 == 0) goto L_0x0028
            boolean r2 = r3.exists()     // Catch:{ Throwable -> 0x0094, all -> 0x0080 }
            if (r2 != 0) goto L_0x0037
        L_0x0028:
            if (r0 == 0) goto L_0x000d
            r1.close()     // Catch:{ IOException -> 0x002e }
            goto L_0x000d
        L_0x002e:
            r1 = move-exception
            java.lang.String r2 = "MD5"
            java.lang.String r3 = "getMd5FromFile"
            com.loc.y.a(r1, r2, r3)
            goto L_0x000d
        L_0x0037:
            r1 = 2048(0x800, float:2.87E-42)
            byte[] r1 = new byte[r1]     // Catch:{ Throwable -> 0x0094, all -> 0x0080 }
            java.lang.String r2 = "MD5"
            java.security.MessageDigest r4 = java.security.MessageDigest.getInstance(r2)     // Catch:{ Throwable -> 0x0094, all -> 0x0080 }
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0094, all -> 0x0080 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0094, all -> 0x0080 }
        L_0x0046:
            int r3 = r2.read(r1)     // Catch:{ Throwable -> 0x0052 }
            r5 = -1
            if (r3 == r5) goto L_0x0069
            r5 = 0
            r4.update(r1, r5, r3)     // Catch:{ Throwable -> 0x0052 }
            goto L_0x0046
        L_0x0052:
            r1 = move-exception
        L_0x0053:
            java.lang.String r3 = "MD5"
            java.lang.String r4 = "getMd5FromFile"
            com.loc.y.a(r1, r3, r4)     // Catch:{ all -> 0x0092 }
            if (r2 == 0) goto L_0x000d
            r2.close()     // Catch:{ IOException -> 0x0060 }
            goto L_0x000d
        L_0x0060:
            r1 = move-exception
            java.lang.String r2 = "MD5"
            java.lang.String r3 = "getMd5FromFile"
            com.loc.y.a(r1, r2, r3)
            goto L_0x000d
        L_0x0069:
            byte[] r1 = r4.digest()     // Catch:{ Throwable -> 0x0052 }
            java.lang.String r0 = com.loc.w.b((byte[]) r1)     // Catch:{ Throwable -> 0x0052 }
            if (r2 == 0) goto L_0x000d
            r2.close()     // Catch:{ IOException -> 0x0077 }
            goto L_0x000d
        L_0x0077:
            r1 = move-exception
            java.lang.String r2 = "MD5"
            java.lang.String r3 = "getMd5FromFile"
            com.loc.y.a(r1, r2, r3)
            goto L_0x000d
        L_0x0080:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x0083:
            if (r2 == 0) goto L_0x0088
            r2.close()     // Catch:{ IOException -> 0x0089 }
        L_0x0088:
            throw r0
        L_0x0089:
            r1 = move-exception
            java.lang.String r2 = "MD5"
            java.lang.String r3 = "getMd5FromFile"
            com.loc.y.a(r1, r2, r3)
            goto L_0x0088
        L_0x0092:
            r0 = move-exception
            goto L_0x0083
        L_0x0094:
            r1 = move-exception
            r2 = r0
            goto L_0x0053
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.s.a(java.lang.String):java.lang.String");
    }

    public static String b(String str) {
        if (str == null) {
            return null;
        }
        return w.b(c(str));
    }

    public static byte[] a(byte[] bArr, String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            instance.update(bArr);
            return instance.digest();
        } catch (NoSuchAlgorithmException e) {
            y.a(e, "MD5", "getMd5Bytes");
            return null;
        } catch (Throwable th) {
            y.a(th, "MD5", "getMd5Bytes1");
            return null;
        }
    }

    public static byte[] c(String str) {
        try {
            return d(str);
        } catch (NoSuchAlgorithmException e) {
            y.a(e, "MD5", "getMd5Bytes");
        } catch (UnsupportedEncodingException e2) {
            y.a(e2, "MD5", "getMd5Bytes");
        } catch (Throwable th) {
            y.a(th, "MD5", "getMd5Bytes");
        }
        return new byte[0];
    }

    private static byte[] d(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        MessageDigest instance = MessageDigest.getInstance("MD5");
        instance.update(str.getBytes("UTF-8"));
        return instance.digest();
    }
}

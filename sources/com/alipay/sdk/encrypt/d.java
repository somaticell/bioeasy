package com.alipay.sdk.encrypt;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public final class d {
    private static final String a = "RSA";

    private static PublicKey b(String str, String str2) throws NoSuchAlgorithmException, Exception {
        return KeyFactory.getInstance(str).generatePublic(new X509EncodedKeySpec(a.a(str2)));
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0051 A[SYNTHETIC, Splitter:B:19:0x0051] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x005d A[SYNTHETIC, Splitter:B:25:0x005d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] a(java.lang.String r8, java.lang.String r9) {
        /*
            r0 = 0
            java.lang.String r1 = "RSA"
            byte[] r2 = com.alipay.sdk.encrypt.a.a((java.lang.String) r9)     // Catch:{ Exception -> 0x004d, all -> 0x0057 }
            java.security.spec.X509EncodedKeySpec r3 = new java.security.spec.X509EncodedKeySpec     // Catch:{ Exception -> 0x004d, all -> 0x0057 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x004d, all -> 0x0057 }
            java.security.KeyFactory r1 = java.security.KeyFactory.getInstance(r1)     // Catch:{ Exception -> 0x004d, all -> 0x0057 }
            java.security.PublicKey r1 = r1.generatePublic(r3)     // Catch:{ Exception -> 0x004d, all -> 0x0057 }
            java.lang.String r2 = "RSA/ECB/PKCS1Padding"
            javax.crypto.Cipher r5 = javax.crypto.Cipher.getInstance(r2)     // Catch:{ Exception -> 0x004d, all -> 0x0057 }
            r2 = 1
            r5.init(r2, r1)     // Catch:{ Exception -> 0x004d, all -> 0x0057 }
            java.lang.String r1 = "UTF-8"
            byte[] r6 = r8.getBytes(r1)     // Catch:{ Exception -> 0x004d, all -> 0x0057 }
            int r3 = r5.getBlockSize()     // Catch:{ Exception -> 0x004d, all -> 0x0057 }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x004d, all -> 0x0057 }
            r1.<init>()     // Catch:{ Exception -> 0x004d, all -> 0x0057 }
            r2 = 0
            r4 = r2
        L_0x002f:
            int r2 = r6.length     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            if (r4 >= r2) goto L_0x0045
            int r2 = r6.length     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            int r2 = r2 - r4
            if (r2 >= r3) goto L_0x0043
            int r2 = r6.length     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            int r2 = r2 - r4
        L_0x0038:
            byte[] r2 = r5.doFinal(r6, r4, r2)     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            r1.write(r2)     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            int r2 = r4 + r3
            r4 = r2
            goto L_0x002f
        L_0x0043:
            r2 = r3
            goto L_0x0038
        L_0x0045:
            byte[] r0 = r1.toByteArray()     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            r1.close()     // Catch:{ IOException -> 0x0061 }
        L_0x004c:
            return r0
        L_0x004d:
            r1 = move-exception
            r1 = r0
        L_0x004f:
            if (r1 == 0) goto L_0x004c
            r1.close()     // Catch:{ IOException -> 0x0055 }
            goto L_0x004c
        L_0x0055:
            r1 = move-exception
            goto L_0x004c
        L_0x0057:
            r1 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
        L_0x005b:
            if (r1 == 0) goto L_0x0060
            r1.close()     // Catch:{ IOException -> 0x0063 }
        L_0x0060:
            throw r0
        L_0x0061:
            r1 = move-exception
            goto L_0x004c
        L_0x0063:
            r1 = move-exception
            goto L_0x0060
        L_0x0065:
            r0 = move-exception
            goto L_0x005b
        L_0x0067:
            r2 = move-exception
            goto L_0x004f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.encrypt.d.a(java.lang.String, java.lang.String):byte[]");
    }
}

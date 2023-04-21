package com.alipay.sdk.packet;

import com.alipay.sdk.cons.a;
import com.alipay.sdk.encrypt.c;
import com.alipay.sdk.encrypt.d;
import com.alipay.sdk.util.l;
import java.util.Locale;

public final class e {
    private boolean a;
    private String b = l.d();

    public e(boolean z) {
        this.a = z;
    }

    public final c a(b bVar, boolean z) {
        byte[] a2;
        byte[] bytes = bVar.a.getBytes();
        byte[] bytes2 = bVar.b.getBytes();
        if (z) {
            try {
                bytes2 = c.a(bytes2);
            } catch (Exception e) {
                z = false;
            }
        }
        if (this.a) {
            a2 = a(bytes, d.a(this.b, a.c), com.alipay.sdk.encrypt.e.a(this.b, bytes2));
        } else {
            a2 = a(bytes, bytes2);
        }
        return new c(z, a2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0063, code lost:
        r1 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x007a, code lost:
        r0 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0055 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0060 A[SYNTHETIC, Splitter:B:27:0x0060] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x006e A[SYNTHETIC, Splitter:B:35:0x006e] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x007a A[ExcHandler: all (th java.lang.Throwable), Splitter:B:4:0x0009] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.alipay.sdk.packet.b a(com.alipay.sdk.packet.c r7) {
        /*
            r6 = this;
            r0 = 0
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x005b, all -> 0x0068 }
            byte[] r2 = r7.b     // Catch:{ Exception -> 0x005b, all -> 0x0068 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x005b, all -> 0x0068 }
            r2 = 5
            byte[] r2 = new byte[r2]     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            r1.read(r2)     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            java.lang.String r3 = new java.lang.String     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            r3.<init>(r2)     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            int r2 = java.lang.Integer.parseInt(r3)     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            byte[] r3 = new byte[r2]     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            r1.read(r3)     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            java.lang.String r2 = new java.lang.String     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            r2.<init>(r3)     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            r3 = 5
            byte[] r3 = new byte[r3]     // Catch:{ Exception -> 0x007f, all -> 0x007a }
            r1.read(r3)     // Catch:{ Exception -> 0x007f, all -> 0x007a }
            java.lang.String r4 = new java.lang.String     // Catch:{ Exception -> 0x007f, all -> 0x007a }
            r4.<init>(r3)     // Catch:{ Exception -> 0x007f, all -> 0x007a }
            int r3 = java.lang.Integer.parseInt(r4)     // Catch:{ Exception -> 0x007f, all -> 0x007a }
            if (r3 <= 0) goto L_0x0083
            byte[] r3 = new byte[r3]     // Catch:{ Exception -> 0x007f, all -> 0x007a }
            r1.read(r3)     // Catch:{ Exception -> 0x007f, all -> 0x007a }
            boolean r4 = r6.a     // Catch:{ Exception -> 0x007f, all -> 0x007a }
            if (r4 == 0) goto L_0x0041
            java.lang.String r4 = r6.b     // Catch:{ Exception -> 0x007f, all -> 0x007a }
            byte[] r3 = com.alipay.sdk.encrypt.e.b((java.lang.String) r4, (byte[]) r3)     // Catch:{ Exception -> 0x007f, all -> 0x007a }
        L_0x0041:
            boolean r4 = r7.a     // Catch:{ Exception -> 0x007f, all -> 0x007a }
            if (r4 == 0) goto L_0x0081
            byte[] r3 = com.alipay.sdk.encrypt.c.b(r3)     // Catch:{ Exception -> 0x007f, all -> 0x007a }
            r4 = r3
        L_0x004a:
            java.lang.String r3 = new java.lang.String     // Catch:{ Exception -> 0x007f, all -> 0x007a }
            r3.<init>(r4)     // Catch:{ Exception -> 0x007f, all -> 0x007a }
        L_0x004f:
            r1.close()     // Catch:{ Exception -> 0x0058 }
            r1 = r3
        L_0x0053:
            if (r2 != 0) goto L_0x0072
            if (r1 != 0) goto L_0x0072
        L_0x0057:
            return r0
        L_0x0058:
            r1 = move-exception
            r1 = r3
            goto L_0x0053
        L_0x005b:
            r1 = move-exception
            r1 = r0
            r2 = r0
        L_0x005e:
            if (r1 == 0) goto L_0x0066
            r1.close()     // Catch:{ Exception -> 0x0065 }
            r1 = r0
            goto L_0x0053
        L_0x0065:
            r1 = move-exception
        L_0x0066:
            r1 = r0
            goto L_0x0053
        L_0x0068:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
        L_0x006c:
            if (r1 == 0) goto L_0x0071
            r1.close()     // Catch:{ Exception -> 0x0078 }
        L_0x0071:
            throw r0
        L_0x0072:
            com.alipay.sdk.packet.b r0 = new com.alipay.sdk.packet.b
            r0.<init>(r2, r1)
            goto L_0x0057
        L_0x0078:
            r1 = move-exception
            goto L_0x0071
        L_0x007a:
            r0 = move-exception
            goto L_0x006c
        L_0x007c:
            r2 = move-exception
            r2 = r0
            goto L_0x005e
        L_0x007f:
            r3 = move-exception
            goto L_0x005e
        L_0x0081:
            r4 = r3
            goto L_0x004a
        L_0x0083:
            r3 = r0
            goto L_0x004f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.packet.e.a(com.alipay.sdk.packet.c):com.alipay.sdk.packet.b");
    }

    private static byte[] a(String str, String str2) {
        return d.a(str, str2);
    }

    private static byte[] a(String str, byte[] bArr) {
        return com.alipay.sdk.encrypt.e.a(str, bArr);
    }

    private static byte[] b(String str, byte[] bArr) {
        return com.alipay.sdk.encrypt.e.b(str, bArr);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x004e A[SYNTHETIC, Splitter:B:20:0x004e] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0053 A[SYNTHETIC, Splitter:B:23:0x0053] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0060 A[SYNTHETIC, Splitter:B:29:0x0060] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0065 A[SYNTHETIC, Splitter:B:32:0x0065] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] a(byte[]... r10) {
        /*
            r3 = 0
            r0 = 0
            int r1 = r10.length
            if (r1 != 0) goto L_0x0006
        L_0x0005:
            return r0
        L_0x0006:
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0049, all -> 0x0059 }
            r2.<init>()     // Catch:{ Exception -> 0x0049, all -> 0x0059 }
            java.io.DataOutputStream r1 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x0078, all -> 0x0071 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0078, all -> 0x0071 }
        L_0x0010:
            int r4 = r10.length     // Catch:{ Exception -> 0x007b, all -> 0x0076 }
            if (r3 >= r4) goto L_0x0039
            r4 = r10[r3]     // Catch:{ Exception -> 0x007b, all -> 0x0076 }
            int r4 = r4.length     // Catch:{ Exception -> 0x007b, all -> 0x0076 }
            java.util.Locale r5 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x007b, all -> 0x0076 }
            java.lang.String r6 = "%05d"
            r7 = 1
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x007b, all -> 0x0076 }
            r8 = 0
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x007b, all -> 0x0076 }
            r7[r8] = r4     // Catch:{ Exception -> 0x007b, all -> 0x0076 }
            java.lang.String r4 = java.lang.String.format(r5, r6, r7)     // Catch:{ Exception -> 0x007b, all -> 0x0076 }
            byte[] r4 = r4.getBytes()     // Catch:{ Exception -> 0x007b, all -> 0x0076 }
            r1.write(r4)     // Catch:{ Exception -> 0x007b, all -> 0x0076 }
            r4 = r10[r3]     // Catch:{ Exception -> 0x007b, all -> 0x0076 }
            r1.write(r4)     // Catch:{ Exception -> 0x007b, all -> 0x0076 }
            int r3 = r3 + 1
            goto L_0x0010
        L_0x0039:
            r1.flush()     // Catch:{ Exception -> 0x007b, all -> 0x0076 }
            byte[] r0 = r2.toByteArray()     // Catch:{ Exception -> 0x007b, all -> 0x0076 }
            r2.close()     // Catch:{ Exception -> 0x0069 }
        L_0x0043:
            r1.close()     // Catch:{ Exception -> 0x0047 }
            goto L_0x0005
        L_0x0047:
            r1 = move-exception
            goto L_0x0005
        L_0x0049:
            r1 = move-exception
            r1 = r0
            r2 = r0
        L_0x004c:
            if (r2 == 0) goto L_0x0051
            r2.close()     // Catch:{ Exception -> 0x006b }
        L_0x0051:
            if (r1 == 0) goto L_0x0005
            r1.close()     // Catch:{ Exception -> 0x0057 }
            goto L_0x0005
        L_0x0057:
            r1 = move-exception
            goto L_0x0005
        L_0x0059:
            r1 = move-exception
            r2 = r0
            r9 = r0
            r0 = r1
            r1 = r9
        L_0x005e:
            if (r2 == 0) goto L_0x0063
            r2.close()     // Catch:{ Exception -> 0x006d }
        L_0x0063:
            if (r1 == 0) goto L_0x0068
            r1.close()     // Catch:{ Exception -> 0x006f }
        L_0x0068:
            throw r0
        L_0x0069:
            r2 = move-exception
            goto L_0x0043
        L_0x006b:
            r2 = move-exception
            goto L_0x0051
        L_0x006d:
            r2 = move-exception
            goto L_0x0063
        L_0x006f:
            r1 = move-exception
            goto L_0x0068
        L_0x0071:
            r1 = move-exception
            r9 = r1
            r1 = r0
            r0 = r9
            goto L_0x005e
        L_0x0076:
            r0 = move-exception
            goto L_0x005e
        L_0x0078:
            r1 = move-exception
            r1 = r0
            goto L_0x004c
        L_0x007b:
            r3 = move-exception
            goto L_0x004c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.packet.e.a(byte[][]):byte[]");
    }

    private static String a(int i) {
        return String.format(Locale.getDefault(), "%05d", new Object[]{Integer.valueOf(i)});
    }

    private static int a(String str) {
        return Integer.parseInt(str);
    }
}

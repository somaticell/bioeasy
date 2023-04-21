package com.alipay.b.a.a.a;

public final class b {
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0031 A[SYNTHETIC, Splitter:B:12:0x0031] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0045 A[SYNTHETIC, Splitter:B:21:0x0045] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r7, java.lang.String r8) {
        /*
            r0 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.io.File r3 = new java.io.File     // Catch:{ IOException -> 0x004f, all -> 0x003f }
            r3.<init>(r7, r8)     // Catch:{ IOException -> 0x004f, all -> 0x003f }
            boolean r1 = r3.exists()     // Catch:{ IOException -> 0x004f, all -> 0x003f }
            if (r1 != 0) goto L_0x0012
        L_0x0011:
            return r0
        L_0x0012:
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ IOException -> 0x004f, all -> 0x003f }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x004f, all -> 0x003f }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ IOException -> 0x004f, all -> 0x003f }
            r5.<init>(r3)     // Catch:{ IOException -> 0x004f, all -> 0x003f }
            java.lang.String r3 = "UTF-8"
            r4.<init>(r5, r3)     // Catch:{ IOException -> 0x004f, all -> 0x003f }
            r1.<init>(r4)     // Catch:{ IOException -> 0x004f, all -> 0x003f }
        L_0x0023:
            java.lang.String r0 = r1.readLine()     // Catch:{ IOException -> 0x002d, all -> 0x004d }
            if (r0 == 0) goto L_0x0039
            r2.append(r0)     // Catch:{ IOException -> 0x002d, all -> 0x004d }
            goto L_0x0023
        L_0x002d:
            r0 = move-exception
            r0 = r1
        L_0x002f:
            if (r0 == 0) goto L_0x0034
            r0.close()     // Catch:{ Throwable -> 0x0049 }
        L_0x0034:
            java.lang.String r0 = r2.toString()
            goto L_0x0011
        L_0x0039:
            r1.close()     // Catch:{ Throwable -> 0x003d }
            goto L_0x0034
        L_0x003d:
            r0 = move-exception
            goto L_0x0034
        L_0x003f:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
        L_0x0043:
            if (r1 == 0) goto L_0x0048
            r1.close()     // Catch:{ Throwable -> 0x004b }
        L_0x0048:
            throw r0
        L_0x0049:
            r0 = move-exception
            goto L_0x0034
        L_0x004b:
            r1 = move-exception
            goto L_0x0048
        L_0x004d:
            r0 = move-exception
            goto L_0x0043
        L_0x004f:
            r1 = move-exception
            goto L_0x002f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.b.a.a.a.b.a(java.lang.String, java.lang.String):java.lang.String");
    }
}

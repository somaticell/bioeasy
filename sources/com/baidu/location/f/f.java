package com.baidu.location.f;

class f extends Thread {
    final /* synthetic */ e a;

    f(e eVar) {
        this.a = eVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0084  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0089 A[SYNTHETIC, Splitter:B:24:0x0089] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x008e A[SYNTHETIC, Splitter:B:27:0x008e] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0103  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0108 A[SYNTHETIC, Splitter:B:61:0x0108] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x010d A[SYNTHETIC, Splitter:B:64:0x010d] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x011b A[LOOP:0: B:1:0x001b->B:71:0x011b, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x014b  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0095 A[EDGE_INSN: B:89:0x0095->B:31:0x0095 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r13 = this;
            r2 = 0
            r6 = 1
            r5 = 0
            com.baidu.location.f.e r0 = r13.a
            java.lang.String r1 = com.baidu.location.f.j.c()
            r0.h = r1
            com.baidu.location.f.e r0 = r13.a
            r0.b()
            com.baidu.location.f.e r0 = r13.a
            r0.a()
            com.baidu.location.f.e r0 = r13.a
            int r0 = r0.i
            r1 = r2
            r4 = r0
        L_0x001b:
            if (r4 <= 0) goto L_0x0095
            java.net.URL r0 = new java.net.URL     // Catch:{ Exception -> 0x0145, all -> 0x0136 }
            com.baidu.location.f.e r3 = r13.a     // Catch:{ Exception -> 0x0145, all -> 0x0136 }
            java.lang.String r3 = r3.h     // Catch:{ Exception -> 0x0145, all -> 0x0136 }
            r0.<init>(r3)     // Catch:{ Exception -> 0x0145, all -> 0x0136 }
            java.net.URLConnection r0 = r0.openConnection()     // Catch:{ Exception -> 0x0145, all -> 0x0136 }
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Exception -> 0x0145, all -> 0x0136 }
            java.lang.String r1 = "GET"
            r0.setRequestMethod(r1)     // Catch:{ Exception -> 0x0139, all -> 0x00fc }
            r1 = 1
            r0.setDoInput(r1)     // Catch:{ Exception -> 0x0139, all -> 0x00fc }
            r1 = 1
            r0.setDoOutput(r1)     // Catch:{ Exception -> 0x0139, all -> 0x00fc }
            r1 = 0
            r0.setUseCaches(r1)     // Catch:{ Exception -> 0x0139, all -> 0x00fc }
            int r1 = com.baidu.location.f.a.b     // Catch:{ Exception -> 0x0139, all -> 0x00fc }
            r0.setConnectTimeout(r1)     // Catch:{ Exception -> 0x0139, all -> 0x00fc }
            int r1 = com.baidu.location.f.a.b     // Catch:{ Exception -> 0x0139, all -> 0x00fc }
            r0.setReadTimeout(r1)     // Catch:{ Exception -> 0x0139, all -> 0x00fc }
            java.lang.String r1 = "Content-Type"
            java.lang.String r3 = "application/x-www-form-urlencoded; charset=utf-8"
            r0.setRequestProperty(r1, r3)     // Catch:{ Exception -> 0x0139, all -> 0x00fc }
            java.lang.String r1 = "Accept-Charset"
            java.lang.String r3 = "UTF-8"
            r0.setRequestProperty(r1, r3)     // Catch:{ Exception -> 0x0139, all -> 0x00fc }
            int r1 = r0.getResponseCode()     // Catch:{ Exception -> 0x0139, all -> 0x00fc }
            r3 = 200(0xc8, float:2.8E-43)
            if (r1 != r3) goto L_0x00dc
            java.io.InputStream r3 = r0.getInputStream()     // Catch:{ Exception -> 0x0139, all -> 0x00fc }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x013f, all -> 0x0123 }
            r1.<init>()     // Catch:{ Exception -> 0x013f, all -> 0x0123 }
            r7 = 1024(0x400, float:1.435E-42)
            byte[] r7 = new byte[r7]     // Catch:{ Exception -> 0x0076, all -> 0x0128 }
        L_0x006a:
            int r8 = r3.read(r7)     // Catch:{ Exception -> 0x0076, all -> 0x0128 }
            r9 = -1
            if (r8 == r9) goto L_0x00a7
            r9 = 0
            r1.write(r7, r9, r8)     // Catch:{ Exception -> 0x0076, all -> 0x0128 }
            goto L_0x006a
        L_0x0076:
            r7 = move-exception
            r11 = r1
            r1 = r3
            r3 = r0
            r0 = r11
        L_0x007b:
            java.lang.String r7 = com.baidu.location.f.a.a     // Catch:{ all -> 0x012e }
            java.lang.String r8 = "NetworkCommunicationException!"
            android.util.Log.d(r7, r8)     // Catch:{ all -> 0x012e }
            if (r3 == 0) goto L_0x0087
            r3.disconnect()
        L_0x0087:
            if (r1 == 0) goto L_0x008c
            r1.close()     // Catch:{ Exception -> 0x00f0 }
        L_0x008c:
            if (r0 == 0) goto L_0x014b
            r0.close()     // Catch:{ Exception -> 0x00f5 }
            r0 = r5
            r1 = r3
        L_0x0093:
            if (r0 == 0) goto L_0x011b
        L_0x0095:
            if (r4 > 0) goto L_0x0120
            int r0 = com.baidu.location.f.e.o
            int r0 = r0 + 1
            com.baidu.location.f.e.o = r0
            com.baidu.location.f.e r0 = r13.a
            r0.j = r2
            com.baidu.location.f.e r0 = r13.a
            r0.a((boolean) r5)
        L_0x00a6:
            return
        L_0x00a7:
            r3.close()     // Catch:{ Exception -> 0x0076, all -> 0x0128 }
            r1.close()     // Catch:{ Exception -> 0x0076, all -> 0x0128 }
            com.baidu.location.f.e r7 = r13.a     // Catch:{ Exception -> 0x0076, all -> 0x0128 }
            java.lang.String r8 = new java.lang.String     // Catch:{ Exception -> 0x0076, all -> 0x0128 }
            byte[] r9 = r1.toByteArray()     // Catch:{ Exception -> 0x0076, all -> 0x0128 }
            java.lang.String r10 = "utf-8"
            r8.<init>(r9, r10)     // Catch:{ Exception -> 0x0076, all -> 0x0128 }
            r7.j = r8     // Catch:{ Exception -> 0x0076, all -> 0x0128 }
            com.baidu.location.f.e r7 = r13.a     // Catch:{ Exception -> 0x0076, all -> 0x0128 }
            r8 = 1
            r7.a((boolean) r8)     // Catch:{ Exception -> 0x0076, all -> 0x0128 }
            r0.disconnect()     // Catch:{ Exception -> 0x0076, all -> 0x0128 }
            r7 = r3
            r3 = r1
            r1 = r6
        L_0x00c9:
            if (r0 == 0) goto L_0x00ce
            r0.disconnect()
        L_0x00ce:
            if (r7 == 0) goto L_0x00d3
            r7.close()     // Catch:{ Exception -> 0x00e3 }
        L_0x00d3:
            if (r3 == 0) goto L_0x014f
            r3.close()     // Catch:{ Exception -> 0x00e8 }
            r11 = r1
            r1 = r0
            r0 = r11
            goto L_0x0093
        L_0x00dc:
            r0.disconnect()     // Catch:{ Exception -> 0x0139, all -> 0x00fc }
            r1 = r5
            r3 = r2
            r7 = r2
            goto L_0x00c9
        L_0x00e3:
            r7 = move-exception
            r7.printStackTrace()
            goto L_0x00d3
        L_0x00e8:
            r3 = move-exception
            r3.printStackTrace()
            r11 = r1
            r1 = r0
            r0 = r11
            goto L_0x0093
        L_0x00f0:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x008c
        L_0x00f5:
            r0 = move-exception
            r0.printStackTrace()
            r0 = r5
            r1 = r3
            goto L_0x0093
        L_0x00fc:
            r1 = move-exception
            r3 = r2
            r11 = r1
            r1 = r0
            r0 = r11
        L_0x0101:
            if (r1 == 0) goto L_0x0106
            r1.disconnect()
        L_0x0106:
            if (r3 == 0) goto L_0x010b
            r3.close()     // Catch:{ Exception -> 0x0111 }
        L_0x010b:
            if (r2 == 0) goto L_0x0110
            r2.close()     // Catch:{ Exception -> 0x0116 }
        L_0x0110:
            throw r0
        L_0x0111:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x010b
        L_0x0116:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0110
        L_0x011b:
            int r0 = r4 + -1
            r4 = r0
            goto L_0x001b
        L_0x0120:
            com.baidu.location.f.e.o = r5
            goto L_0x00a6
        L_0x0123:
            r1 = move-exception
            r11 = r1
            r1 = r0
            r0 = r11
            goto L_0x0101
        L_0x0128:
            r2 = move-exception
            r11 = r2
            r2 = r1
            r1 = r0
            r0 = r11
            goto L_0x0101
        L_0x012e:
            r2 = move-exception
            r11 = r2
            r2 = r0
            r0 = r11
            r12 = r1
            r1 = r3
            r3 = r12
            goto L_0x0101
        L_0x0136:
            r0 = move-exception
            r3 = r2
            goto L_0x0101
        L_0x0139:
            r1 = move-exception
            r1 = r2
            r3 = r0
            r0 = r2
            goto L_0x007b
        L_0x013f:
            r1 = move-exception
            r1 = r3
            r3 = r0
            r0 = r2
            goto L_0x007b
        L_0x0145:
            r0 = move-exception
            r0 = r2
            r3 = r1
            r1 = r2
            goto L_0x007b
        L_0x014b:
            r0 = r5
            r1 = r3
            goto L_0x0093
        L_0x014f:
            r11 = r1
            r1 = r0
            r0 = r11
            goto L_0x0093
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.f.f.run():void");
    }
}

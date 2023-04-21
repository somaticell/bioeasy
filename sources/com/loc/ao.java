package com.loc;

import android.os.Environment;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* compiled from: FileLogUtils */
public class ao {
    private static String a;

    public static String a() {
        if (a == null) {
            a = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return a;
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x00e0 A[SYNTHETIC, Splitter:B:100:0x00e0] */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x00e5 A[SYNTHETIC, Splitter:B:103:0x00e5] */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x00f9 A[SYNTHETIC, Splitter:B:112:0x00f9] */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x00fe A[SYNTHETIC, Splitter:B:115:0x00fe] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x009e A[SYNTHETIC, Splitter:B:70:0x009e] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x00a3 A[SYNTHETIC, Splitter:B:73:0x00a3] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x00bf A[SYNTHETIC, Splitter:B:85:0x00bf] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x00c4 A[SYNTHETIC, Splitter:B:88:0x00c4] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void a(java.lang.String r9, byte[] r10, boolean r11) {
        /*
            r1 = 0
            r0 = 1
            java.lang.Class<com.loc.ao> r4 = com.loc.ao.class
            monitor-enter(r4)
            r2 = 0
            r3 = 0
            boolean r5 = b()     // Catch:{ FileNotFoundException -> 0x0092, IOException -> 0x00b3, Throwable -> 0x00d4, all -> 0x00f5 }
            if (r5 != 0) goto L_0x001b
            if (r11 != 0) goto L_0x001b
            if (r1 == 0) goto L_0x0014
            r2.close()     // Catch:{ Throwable -> 0x0134 }
        L_0x0014:
            if (r1 == 0) goto L_0x0019
            r3.close()     // Catch:{ Throwable -> 0x013f }
        L_0x0019:
            monitor-exit(r4)
            return
        L_0x001b:
            java.io.File r5 = new java.io.File     // Catch:{ FileNotFoundException -> 0x0092, IOException -> 0x00b3, Throwable -> 0x00d4, all -> 0x00f5 }
            r5.<init>(r9)     // Catch:{ FileNotFoundException -> 0x0092, IOException -> 0x00b3, Throwable -> 0x00d4, all -> 0x00f5 }
            java.io.File r6 = r5.getParentFile()     // Catch:{ FileNotFoundException -> 0x0092, IOException -> 0x00b3, Throwable -> 0x00d4, all -> 0x00f5 }
            boolean r7 = r6.exists()     // Catch:{ FileNotFoundException -> 0x0092, IOException -> 0x00b3, Throwable -> 0x00d4, all -> 0x00f5 }
            if (r7 != 0) goto L_0x002e
            boolean r0 = r6.mkdirs()     // Catch:{ FileNotFoundException -> 0x0092, IOException -> 0x00b3, Throwable -> 0x00d4, all -> 0x00f5 }
        L_0x002e:
            if (r0 != 0) goto L_0x0048
            if (r1 == 0) goto L_0x0035
            r2.close()     // Catch:{ Throwable -> 0x014a }
        L_0x0035:
            if (r1 == 0) goto L_0x0019
            r3.close()     // Catch:{ Throwable -> 0x003b }
            goto L_0x0019
        L_0x003b:
            r0 = move-exception
            java.lang.String r1 = "FileLogUtils"
            java.lang.String r2 = "writeLog2"
            com.loc.y.a(r0, r1, r2)     // Catch:{ all -> 0x0045 }
            goto L_0x0019
        L_0x0045:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        L_0x0048:
            boolean r6 = r5.exists()     // Catch:{ FileNotFoundException -> 0x0092, IOException -> 0x00b3, Throwable -> 0x00d4, all -> 0x00f5 }
            if (r6 != 0) goto L_0x0052
            boolean r0 = r5.createNewFile()     // Catch:{ FileNotFoundException -> 0x0092, IOException -> 0x00b3, Throwable -> 0x00d4, all -> 0x00f5 }
        L_0x0052:
            if (r0 != 0) goto L_0x0069
            if (r1 == 0) goto L_0x0059
            r2.close()     // Catch:{ Throwable -> 0x0155 }
        L_0x0059:
            if (r1 == 0) goto L_0x0019
            r3.close()     // Catch:{ Throwable -> 0x005f }
            goto L_0x0019
        L_0x005f:
            r0 = move-exception
            java.lang.String r1 = "FileLogUtils"
            java.lang.String r2 = "writeLog2"
            com.loc.y.a(r0, r1, r2)     // Catch:{ all -> 0x0045 }
            goto L_0x0019
        L_0x0069:
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x0092, IOException -> 0x00b3, Throwable -> 0x00d4, all -> 0x00f5 }
            r0 = 1
            r2.<init>(r9, r0)     // Catch:{ FileNotFoundException -> 0x0092, IOException -> 0x00b3, Throwable -> 0x00d4, all -> 0x00f5 }
            java.io.DataOutputStream r3 = new java.io.DataOutputStream     // Catch:{ FileNotFoundException -> 0x0189, IOException -> 0x0180, Throwable -> 0x0177, all -> 0x016b }
            r3.<init>(r2)     // Catch:{ FileNotFoundException -> 0x0189, IOException -> 0x0180, Throwable -> 0x0177, all -> 0x016b }
            r3.write(r10)     // Catch:{ FileNotFoundException -> 0x018f, IOException -> 0x0185, Throwable -> 0x017c, all -> 0x016f }
            r3.flush()     // Catch:{ FileNotFoundException -> 0x018f, IOException -> 0x0185, Throwable -> 0x017c, all -> 0x016f }
            r2.flush()     // Catch:{ FileNotFoundException -> 0x018f, IOException -> 0x0185, Throwable -> 0x017c, all -> 0x016f }
            if (r3 == 0) goto L_0x0082
            r3.close()     // Catch:{ Throwable -> 0x0160 }
        L_0x0082:
            if (r2 == 0) goto L_0x0019
            r2.close()     // Catch:{ Throwable -> 0x0088 }
            goto L_0x0019
        L_0x0088:
            r0 = move-exception
            java.lang.String r1 = "FileLogUtils"
            java.lang.String r2 = "writeLog2"
            com.loc.y.a(r0, r1, r2)     // Catch:{ all -> 0x0045 }
            goto L_0x0019
        L_0x0092:
            r0 = move-exception
            r2 = r1
        L_0x0094:
            java.lang.String r3 = "FileLogUtils"
            java.lang.String r5 = "writeLog"
            com.loc.y.a(r0, r3, r5)     // Catch:{ all -> 0x0172 }
            if (r2 == 0) goto L_0x00a1
            r2.close()     // Catch:{ Throwable -> 0x0116 }
        L_0x00a1:
            if (r1 == 0) goto L_0x0019
            r1.close()     // Catch:{ Throwable -> 0x00a8 }
            goto L_0x0019
        L_0x00a8:
            r0 = move-exception
            java.lang.String r1 = "FileLogUtils"
            java.lang.String r2 = "writeLog2"
            com.loc.y.a(r0, r1, r2)     // Catch:{ all -> 0x0045 }
            goto L_0x0019
        L_0x00b3:
            r0 = move-exception
            r3 = r1
        L_0x00b5:
            java.lang.String r2 = "FileLogUtils"
            java.lang.String r5 = "writeLog"
            com.loc.y.a(r0, r2, r5)     // Catch:{ all -> 0x0175 }
            if (r3 == 0) goto L_0x00c2
            r3.close()     // Catch:{ Throwable -> 0x0120 }
        L_0x00c2:
            if (r1 == 0) goto L_0x0019
            r1.close()     // Catch:{ Throwable -> 0x00c9 }
            goto L_0x0019
        L_0x00c9:
            r0 = move-exception
            java.lang.String r1 = "FileLogUtils"
            java.lang.String r2 = "writeLog2"
            com.loc.y.a(r0, r1, r2)     // Catch:{ all -> 0x0045 }
            goto L_0x0019
        L_0x00d4:
            r0 = move-exception
            r3 = r1
        L_0x00d6:
            java.lang.String r2 = "FileLogUtils"
            java.lang.String r5 = "writeLog"
            com.loc.y.a(r0, r2, r5)     // Catch:{ all -> 0x0175 }
            if (r3 == 0) goto L_0x00e3
            r3.close()     // Catch:{ Throwable -> 0x012a }
        L_0x00e3:
            if (r1 == 0) goto L_0x0019
            r1.close()     // Catch:{ Throwable -> 0x00ea }
            goto L_0x0019
        L_0x00ea:
            r0 = move-exception
            java.lang.String r1 = "FileLogUtils"
            java.lang.String r2 = "writeLog2"
            com.loc.y.a(r0, r1, r2)     // Catch:{ all -> 0x0045 }
            goto L_0x0019
        L_0x00f5:
            r0 = move-exception
            r3 = r1
        L_0x00f7:
            if (r3 == 0) goto L_0x00fc
            r3.close()     // Catch:{ Throwable -> 0x0102 }
        L_0x00fc:
            if (r1 == 0) goto L_0x0101
            r1.close()     // Catch:{ Throwable -> 0x010c }
        L_0x0101:
            throw r0     // Catch:{ all -> 0x0045 }
        L_0x0102:
            r2 = move-exception
            java.lang.String r3 = "FileLogUtils"
            java.lang.String r5 = "writeLog1"
            com.loc.y.a(r2, r3, r5)     // Catch:{ all -> 0x0045 }
            goto L_0x00fc
        L_0x010c:
            r1 = move-exception
            java.lang.String r2 = "FileLogUtils"
            java.lang.String r3 = "writeLog2"
            com.loc.y.a(r1, r2, r3)     // Catch:{ all -> 0x0045 }
            goto L_0x0101
        L_0x0116:
            r0 = move-exception
            java.lang.String r2 = "FileLogUtils"
            java.lang.String r3 = "writeLog1"
            com.loc.y.a(r0, r2, r3)     // Catch:{ all -> 0x0045 }
            goto L_0x00a1
        L_0x0120:
            r0 = move-exception
            java.lang.String r2 = "FileLogUtils"
            java.lang.String r3 = "writeLog1"
            com.loc.y.a(r0, r2, r3)     // Catch:{ all -> 0x0045 }
            goto L_0x00c2
        L_0x012a:
            r0 = move-exception
            java.lang.String r2 = "FileLogUtils"
            java.lang.String r3 = "writeLog1"
            com.loc.y.a(r0, r2, r3)     // Catch:{ all -> 0x0045 }
            goto L_0x00e3
        L_0x0134:
            r0 = move-exception
            java.lang.String r2 = "FileLogUtils"
            java.lang.String r5 = "writeLog1"
            com.loc.y.a(r0, r2, r5)     // Catch:{ all -> 0x0045 }
            goto L_0x0014
        L_0x013f:
            r0 = move-exception
            java.lang.String r1 = "FileLogUtils"
            java.lang.String r2 = "writeLog2"
            com.loc.y.a(r0, r1, r2)     // Catch:{ all -> 0x0045 }
            goto L_0x0019
        L_0x014a:
            r0 = move-exception
            java.lang.String r2 = "FileLogUtils"
            java.lang.String r5 = "writeLog1"
            com.loc.y.a(r0, r2, r5)     // Catch:{ all -> 0x0045 }
            goto L_0x0035
        L_0x0155:
            r0 = move-exception
            java.lang.String r2 = "FileLogUtils"
            java.lang.String r5 = "writeLog1"
            com.loc.y.a(r0, r2, r5)     // Catch:{ all -> 0x0045 }
            goto L_0x0059
        L_0x0160:
            r0 = move-exception
            java.lang.String r1 = "FileLogUtils"
            java.lang.String r3 = "writeLog1"
            com.loc.y.a(r0, r1, r3)     // Catch:{ all -> 0x0045 }
            goto L_0x0082
        L_0x016b:
            r0 = move-exception
            r3 = r1
            r1 = r2
            goto L_0x00f7
        L_0x016f:
            r0 = move-exception
            r1 = r2
            goto L_0x00f7
        L_0x0172:
            r0 = move-exception
            r3 = r2
            goto L_0x00f7
        L_0x0175:
            r0 = move-exception
            goto L_0x00f7
        L_0x0177:
            r0 = move-exception
            r3 = r1
            r1 = r2
            goto L_0x00d6
        L_0x017c:
            r0 = move-exception
            r1 = r2
            goto L_0x00d6
        L_0x0180:
            r0 = move-exception
            r3 = r1
            r1 = r2
            goto L_0x00b5
        L_0x0185:
            r0 = move-exception
            r1 = r2
            goto L_0x00b5
        L_0x0189:
            r0 = move-exception
            r8 = r2
            r2 = r1
            r1 = r8
            goto L_0x0094
        L_0x018f:
            r0 = move-exception
            r1 = r2
            r2 = r3
            goto L_0x0094
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.ao.a(java.lang.String, byte[], boolean):void");
    }

    public static synchronized void a(String str, String str2, boolean z) {
        synchronized (ao.class) {
            try {
                StringBuilder sb = new StringBuilder(200);
                sb.delete(0, sb.length());
                sb.append(a(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
                sb.append("#");
                sb.append(str2).append("\r\n");
                a(str, sb.toString().getBytes("UTF-8"), z);
            } catch (Throwable th) {
                y.a(th, "FileLogUtils", "writeLog3");
            }
        }
        return;
    }

    private static boolean b() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    private static String a(long j, String str) {
        return j > 0 ? new SimpleDateFormat(str, Locale.US).format(Long.valueOf(j)) : "0000-00-00-00-00-00";
    }
}

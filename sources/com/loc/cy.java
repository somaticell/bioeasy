package com.loc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Process;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import java.io.File;
import org.achartengine.chart.TimeChart;

public final class cy {
    private Context a = null;
    private boolean b = true;
    private int c = 1270;
    private int d = 310;
    private int e = 4;
    private int f = 200;
    private int g = 1;
    private int h = 0;
    private int i = 0;
    private long j = 0;
    private cx k = null;

    private cy(Context context) {
        this.a = context;
    }

    private static int a(byte[] bArr, int i2) {
        int i3 = 0;
        for (int i4 = 0; i4 < 4; i4++) {
            i3 += (bArr[i4 + i2] & BLEServiceApi.CONNECTED_STATUS) << (i4 << 3);
        }
        return i3;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x005a A[SYNTHETIC, Splitter:B:10:0x005a] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00c3 A[SYNTHETIC, Splitter:B:31:0x00c3] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static com.loc.cy a(android.content.Context r13) {
        /*
            r2 = 1
            r10 = 86400000(0x5265c00, double:4.2687272E-316)
            r3 = 0
            com.loc.cy r4 = new com.loc.cy
            r4.<init>(r13)
            r4.h = r3
            r4.i = r3
            long r0 = java.lang.System.currentTimeMillis()
            r6 = 28800000(0x1b77400, double:1.42290906E-316)
            long r0 = r0 + r6
            long r0 = r0 / r10
            long r0 = r0 * r10
            r4.j = r0
            r1 = 0
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00d0, all -> 0x00c0 }
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x00d0, all -> 0x00c0 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00d0, all -> 0x00c0 }
            r6.<init>()     // Catch:{ Exception -> 0x00d0, all -> 0x00c0 }
            java.lang.String r7 = b((android.content.Context) r13)     // Catch:{ Exception -> 0x00d0, all -> 0x00c0 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x00d0, all -> 0x00c0 }
            java.lang.String r7 = java.io.File.separator     // Catch:{ Exception -> 0x00d0, all -> 0x00c0 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x00d0, all -> 0x00c0 }
            java.lang.String r7 = "data_carrier_status"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x00d0, all -> 0x00c0 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x00d0, all -> 0x00c0 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x00d0, all -> 0x00c0 }
            r0.<init>(r5)     // Catch:{ Exception -> 0x00d0, all -> 0x00c0 }
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            r5.<init>()     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            r1 = 32
            byte[] r1 = new byte[r1]     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
        L_0x004b:
            int r6 = r0.read(r1)     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            r7 = -1
            if (r6 == r7) goto L_0x005e
            r7 = 0
            r5.write(r1, r7, r6)     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            goto L_0x004b
        L_0x0057:
            r1 = move-exception
        L_0x0058:
            if (r0 == 0) goto L_0x005d
            r0.close()     // Catch:{ Exception -> 0x00c7 }
        L_0x005d:
            return r4
        L_0x005e:
            r5.flush()     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            byte[] r6 = r5.toByteArray()     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            if (r6 == 0) goto L_0x00b5
            int r1 = r6.length     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            r7 = 22
            if (r1 < r7) goto L_0x00b5
            r1 = 0
            byte r1 = r6[r1]     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            if (r1 == 0) goto L_0x00be
            r1 = r2
        L_0x0072:
            r4.b = r1     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            r1 = 1
            byte r1 = r6[r1]     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            int r1 = r1 * 10
            int r1 = r1 << 10
            r4.c = r1     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            r1 = 2
            byte r1 = r6[r1]     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            int r1 = r1 * 10
            int r1 = r1 << 10
            r4.d = r1     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            r1 = 3
            byte r1 = r6[r1]     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            r4.e = r1     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            r1 = 4
            byte r1 = r6[r1]     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            int r1 = r1 * 10
            r4.f = r1     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            r1 = 5
            byte r1 = r6[r1]     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            r4.g = r1     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            r1 = 14
            long r2 = b(r6, r1)     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            long r8 = r4.j     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            long r8 = r8 - r2
            int r1 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r1 >= 0) goto L_0x00b5
            r4.j = r2     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            r1 = 6
            int r1 = a(r6, r1)     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            r4.h = r1     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            r1 = 10
            int r1 = a(r6, r1)     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            r4.i = r1     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
        L_0x00b5:
            r5.close()     // Catch:{ Exception -> 0x0057, all -> 0x00cb }
            r0.close()     // Catch:{ Exception -> 0x00bc }
            goto L_0x005d
        L_0x00bc:
            r0 = move-exception
            goto L_0x005d
        L_0x00be:
            r1 = r3
            goto L_0x0072
        L_0x00c0:
            r0 = move-exception
        L_0x00c1:
            if (r1 == 0) goto L_0x00c6
            r1.close()     // Catch:{ Exception -> 0x00c9 }
        L_0x00c6:
            throw r0
        L_0x00c7:
            r0 = move-exception
            goto L_0x005d
        L_0x00c9:
            r1 = move-exception
            goto L_0x00c6
        L_0x00cb:
            r1 = move-exception
            r12 = r1
            r1 = r0
            r0 = r12
            goto L_0x00c1
        L_0x00d0:
            r0 = move-exception
            r0 = r1
            goto L_0x0058
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cy.a(android.content.Context):com.loc.cy");
    }

    private static byte[] a(long j2) {
        byte[] bArr = new byte[8];
        for (int i2 = 0; i2 < 8; i2++) {
            bArr[i2] = (byte) ((int) ((j2 >> (i2 << 3)) & 255));
        }
        return bArr;
    }

    private static long b(byte[] bArr, int i2) {
        int i3 = 0;
        for (int i4 = 0; i4 < 8; i4++) {
            i3 += (bArr[i4 + 14] & BLEServiceApi.CONNECTED_STATUS) << (i4 << 3);
        }
        return (long) i3;
    }

    private static String b(Context context) {
        boolean z = false;
        File file = null;
        if (Process.myUid() != 1000) {
            file = ci.a(context);
        }
        try {
            z = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e2) {
        }
        return ((z || !ci.c()) && file != null) ? file.getPath() : context.getFilesDir().getPath();
    }

    private static byte[] c(int i2) {
        byte[] bArr = new byte[4];
        for (int i3 = 0; i3 < 4; i3++) {
            bArr[i3] = (byte) (i2 >> (i3 << 3));
        }
        return bArr;
    }

    private void g() {
        long currentTimeMillis = System.currentTimeMillis() + 28800000;
        if (currentTimeMillis - this.j > TimeChart.DAY) {
            this.j = (currentTimeMillis / TimeChart.DAY) * TimeChart.DAY;
            this.h = 0;
            this.i = 0;
        }
    }

    /* access modifiers changed from: protected */
    public final void a(int i2) {
        g();
        if (i2 < 0) {
            i2 = 0;
        }
        this.h = i2;
    }

    /* access modifiers changed from: protected */
    public final void a(cx cxVar) {
        this.k = cxVar;
    }

    /* access modifiers changed from: protected */
    public final boolean a() {
        g();
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.a.getSystemService("connectivity")).getActiveNetworkInfo();
        return (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) ? this.b : activeNetworkInfo.getType() == 1 ? this.b && this.h < this.c : this.b && this.i < this.d;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x016d A[SYNTHETIC, Splitter:B:39:0x016d] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0177 A[SYNTHETIC, Splitter:B:45:0x0177] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(java.lang.String r11) {
        /*
            r10 = this;
            r1 = 1
            r2 = 0
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x0162 }
            r3.<init>(r11)     // Catch:{ Exception -> 0x0162 }
            java.lang.String r0 = "e"
            boolean r0 = r3.has(r0)     // Catch:{ Exception -> 0x0162 }
            if (r0 == 0) goto L_0x001a
            java.lang.String r0 = "e"
            int r0 = r3.getInt(r0)     // Catch:{ Exception -> 0x0162 }
            if (r0 == 0) goto L_0x015c
            r0 = r1
        L_0x0018:
            r10.b = r0     // Catch:{ Exception -> 0x0162 }
        L_0x001a:
            java.lang.String r0 = "d"
            boolean r0 = r3.has(r0)     // Catch:{ Exception -> 0x0162 }
            if (r0 == 0) goto L_0x0065
            java.lang.String r0 = "d"
            int r0 = r3.getInt(r0)     // Catch:{ Exception -> 0x0162 }
            r4 = r0 & 127(0x7f, float:1.78E-43)
            int r4 = r4 * 10
            int r4 = r4 << 10
            r10.c = r4     // Catch:{ Exception -> 0x0162 }
            r4 = r0 & 3968(0xf80, float:5.56E-42)
            int r4 = r4 >> 7
            int r4 = r4 * 10
            int r4 = r4 << 10
            r10.d = r4     // Catch:{ Exception -> 0x0162 }
            r4 = 520192(0x7f000, float:7.28944E-40)
            r4 = r4 & r0
            int r4 = r4 >> 12
            r10.e = r4     // Catch:{ Exception -> 0x0162 }
            r4 = 66584576(0x3f80000, float:1.457613E-36)
            r4 = r4 & r0
            int r4 = r4 >> 19
            int r4 = r4 * 10
            r10.f = r4     // Catch:{ Exception -> 0x0162 }
            r4 = 2080374784(0x7c000000, float:2.658456E36)
            r0 = r0 & r4
            int r0 = r0 >> 26
            r10.g = r0     // Catch:{ Exception -> 0x0162 }
            int r0 = r10.g     // Catch:{ Exception -> 0x0162 }
            r4 = 31
            if (r0 != r4) goto L_0x005c
            r0 = 1500(0x5dc, float:2.102E-42)
            r10.g = r0     // Catch:{ Exception -> 0x0162 }
        L_0x005c:
            com.loc.cx r0 = r10.k     // Catch:{ Exception -> 0x0162 }
            if (r0 == 0) goto L_0x0065
            com.loc.cx r0 = r10.k     // Catch:{ Exception -> 0x0162 }
            r0.a()     // Catch:{ Exception -> 0x0162 }
        L_0x0065:
            java.lang.String r0 = "u"
            boolean r0 = r3.has(r0)     // Catch:{ Exception -> 0x0162 }
            if (r0 == 0) goto L_0x0184
            java.lang.String r0 = "u"
            int r0 = r3.getInt(r0)     // Catch:{ Exception -> 0x0162 }
            if (r0 == 0) goto L_0x015f
            r0 = r1
        L_0x0078:
            r4 = 0
            r10.g()     // Catch:{ Exception -> 0x0169, all -> 0x0173 }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0169, all -> 0x0173 }
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x0169, all -> 0x0173 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0169, all -> 0x0173 }
            r6.<init>()     // Catch:{ Exception -> 0x0169, all -> 0x0173 }
            android.content.Context r7 = r10.a     // Catch:{ Exception -> 0x0169, all -> 0x0173 }
            java.lang.String r7 = b((android.content.Context) r7)     // Catch:{ Exception -> 0x0169, all -> 0x0173 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0169, all -> 0x0173 }
            java.lang.String r7 = java.io.File.separator     // Catch:{ Exception -> 0x0169, all -> 0x0173 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0169, all -> 0x0173 }
            java.lang.String r7 = "data_carrier_status"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0169, all -> 0x0173 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0169, all -> 0x0173 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x0169, all -> 0x0173 }
            r3.<init>(r5)     // Catch:{ Exception -> 0x0169, all -> 0x0173 }
            int r4 = r10.h     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            byte[] r4 = c(r4)     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            int r5 = r10.i     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            byte[] r5 = c(r5)     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            long r6 = r10.j     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            byte[] r6 = a((long) r6)     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7 = 22
            byte[] r7 = new byte[r7]     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r8 = 0
            boolean r9 = r10.b     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            if (r9 == 0) goto L_0x0166
        L_0x00c0:
            byte r1 = (byte) r1     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r8] = r1     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 1
            int r2 = r10.c     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            int r2 = r2 / 10240
            byte r2 = (byte) r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 2
            int r2 = r10.d     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            int r2 = r2 / 10240
            byte r2 = (byte) r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 3
            int r2 = r10.e     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            byte r2 = (byte) r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 4
            int r2 = r10.f     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            int r2 = r2 / 10
            byte r2 = (byte) r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 5
            int r2 = r10.g     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            byte r2 = (byte) r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 6
            r2 = 0
            byte r2 = r4[r2]     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 7
            r2 = 1
            byte r2 = r4[r2]     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 8
            r2 = 2
            byte r2 = r4[r2]     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 9
            r2 = 3
            byte r2 = r4[r2]     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 10
            r2 = 0
            byte r2 = r5[r2]     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 11
            r2 = 1
            byte r2 = r5[r2]     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 12
            r2 = 2
            byte r2 = r5[r2]     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 13
            r2 = 3
            byte r2 = r5[r2]     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 14
            r2 = 0
            byte r2 = r6[r2]     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 15
            r2 = 1
            byte r2 = r6[r2]     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 16
            r2 = 2
            byte r2 = r6[r2]     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 17
            r2 = 3
            byte r2 = r6[r2]     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 18
            r2 = 4
            byte r2 = r6[r2]     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 19
            r2 = 5
            byte r2 = r6[r2]     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 20
            r2 = 6
            byte r2 = r6[r2]     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r1 = 21
            r2 = 7
            byte r2 = r6[r2]     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r7[r1] = r2     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r3.write(r7)     // Catch:{ Exception -> 0x0181, all -> 0x017f }
            r3.close()     // Catch:{ Exception -> 0x017b }
        L_0x015b:
            return r0
        L_0x015c:
            r0 = r2
            goto L_0x0018
        L_0x015f:
            r0 = r2
            goto L_0x0078
        L_0x0162:
            r0 = move-exception
            r0 = r2
            goto L_0x0078
        L_0x0166:
            r1 = r2
            goto L_0x00c0
        L_0x0169:
            r1 = move-exception
            r1 = r4
        L_0x016b:
            if (r1 == 0) goto L_0x015b
            r1.close()     // Catch:{ Exception -> 0x0171 }
            goto L_0x015b
        L_0x0171:
            r1 = move-exception
            goto L_0x015b
        L_0x0173:
            r0 = move-exception
            r3 = r4
        L_0x0175:
            if (r3 == 0) goto L_0x017a
            r3.close()     // Catch:{ Exception -> 0x017d }
        L_0x017a:
            throw r0
        L_0x017b:
            r1 = move-exception
            goto L_0x015b
        L_0x017d:
            r1 = move-exception
            goto L_0x017a
        L_0x017f:
            r0 = move-exception
            goto L_0x0175
        L_0x0181:
            r1 = move-exception
            r1 = r3
            goto L_0x016b
        L_0x0184:
            r0 = r2
            goto L_0x0078
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cy.a(java.lang.String):boolean");
    }

    /* access modifiers changed from: protected */
    public final int b() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public final void b(int i2) {
        g();
        if (i2 < 0) {
            i2 = 0;
        }
        this.i = i2;
    }

    /* access modifiers changed from: protected */
    public final int c() {
        return this.f;
    }

    /* access modifiers changed from: protected */
    public final int d() {
        return this.g;
    }

    /* access modifiers changed from: protected */
    public final int e() {
        g();
        return this.h;
    }

    /* access modifiers changed from: protected */
    public final int f() {
        g();
        return this.i;
    }
}

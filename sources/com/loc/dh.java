package com.loc;

import java.util.List;

public final class dh {
    private boolean a = false;
    private String b = "";
    private String c = "";
    private boolean d = false;
    private double e = 0.0d;
    private double f = 0.0d;

    protected dh(List list, String str, String str2, String str3) {
        this.b = str;
        this.c = str3;
        d();
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x003c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void d() {
        /*
            r8 = this;
            r2 = 8
            r7 = 2
            r1 = 1
            r3 = 0
            java.lang.String r4 = r8.c
            if (r4 == 0) goto L_0x012a
            int r0 = r4.length()
            if (r0 <= r2) goto L_0x012a
            r0 = r1
            r2 = r3
        L_0x0011:
            int r5 = r4.length()
            int r5 = r5 + -3
            if (r0 >= r5) goto L_0x0021
            char r5 = r4.charAt(r0)
            r2 = r2 ^ r5
            int r0 = r0 + 1
            goto L_0x0011
        L_0x0021:
            java.lang.String r0 = java.lang.Integer.toHexString(r2)
            int r2 = r4.length()
            int r2 = r2 + -2
            int r5 = r4.length()
            java.lang.String r2 = r4.substring(r2, r5)
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x012a
            r0 = r1
        L_0x003a:
            if (r0 == 0) goto L_0x00c6
            java.lang.String r0 = r8.c
            java.lang.String r2 = r8.c
            int r2 = r2.length()
            int r2 = r2 + -3
            java.lang.String r4 = r0.substring(r3, r2)
            r0 = r3
            r2 = r3
        L_0x004c:
            int r5 = r4.length()
            if (r0 >= r5) goto L_0x005f
            char r5 = r4.charAt(r0)
            r6 = 44
            if (r5 != r6) goto L_0x005c
            int r2 = r2 + 1
        L_0x005c:
            int r0 = r0 + 1
            goto L_0x004c
        L_0x005f:
            java.lang.String r0 = ","
            int r2 = r2 + 1
            java.lang.String[] r0 = r4.split(r0, r2)
            int r2 = r0.length
            r4 = 6
            if (r2 >= r4) goto L_0x006c
        L_0x006b:
            return
        L_0x006c:
            r2 = r0[r7]
            java.lang.String r4 = ""
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L_0x00c6
            int r2 = r0.length
            int r2 = r2 + -3
            r2 = r0[r2]
            java.lang.String r4 = ""
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L_0x00c6
            int r2 = r0.length
            int r2 = r2 + -2
            r2 = r0[r2]
            java.lang.String r4 = ""
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L_0x00c6
            int r2 = r0.length
            int r2 = r2 + -1
            r2 = r0[r2]
            java.lang.String r4 = ""
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L_0x00c6
            r2 = r0[r7]
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r2.intValue()
            int r2 = r0.length
            int r2 = r2 + -3
            r2 = r0[r2]
            java.lang.Double r2 = java.lang.Double.valueOf(r2)
            double r4 = r2.doubleValue()
            r8.e = r4
            int r2 = r0.length
            int r2 = r2 + -2
            r0 = r0[r2]
            java.lang.Double r0 = java.lang.Double.valueOf(r0)
            double r4 = r0.doubleValue()
            r8.f = r4
            r8.d = r1
        L_0x00c6:
            java.lang.String r0 = r8.b     // Catch:{ Exception -> 0x0128 }
            if (r0 == 0) goto L_0x0121
            java.lang.String r0 = r8.b     // Catch:{ Exception -> 0x0128 }
            int r0 = r0.length()     // Catch:{ Exception -> 0x0128 }
            if (r0 < 0) goto L_0x0121
            java.lang.String r0 = r8.b     // Catch:{ Exception -> 0x0128 }
            java.lang.String r2 = "GPGGA"
            boolean r0 = r0.contains(r2)     // Catch:{ Exception -> 0x0128 }
            if (r0 == 0) goto L_0x0121
            java.lang.String r0 = r8.b     // Catch:{ Exception -> 0x0128 }
            java.lang.String r2 = ","
            java.lang.String[] r0 = r0.split(r2)     // Catch:{ Exception -> 0x0128 }
            int r2 = r0.length     // Catch:{ Exception -> 0x0128 }
            r4 = 15
            if (r2 != r4) goto L_0x0121
            r2 = 2
            r2 = r0[r2]     // Catch:{ Exception -> 0x0128 }
            if (r2 == 0) goto L_0x0121
            r2 = 2
            r2 = r0[r2]     // Catch:{ Exception -> 0x0128 }
            int r2 = r2.length()     // Catch:{ Exception -> 0x0128 }
            if (r2 <= 0) goto L_0x0121
            r2 = 4
            r2 = r0[r2]     // Catch:{ Exception -> 0x0128 }
            if (r2 == 0) goto L_0x0121
            r2 = 4
            r2 = r0[r2]     // Catch:{ Exception -> 0x0128 }
            int r2 = r2.length()     // Catch:{ Exception -> 0x0128 }
            if (r2 <= 0) goto L_0x0121
            r2 = 7
            r2 = r0[r2]     // Catch:{ Exception -> 0x0128 }
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ Exception -> 0x0128 }
            r4 = 5
            if (r2 < r4) goto L_0x0121
            r2 = 8
            r0 = r0[r2]     // Catch:{ Exception -> 0x0128 }
            double r4 = java.lang.Double.parseDouble(r0)     // Catch:{ Exception -> 0x0128 }
            r6 = 4613262278296967578(0x400599999999999a, double:2.7)
            int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r0 > 0) goto L_0x0121
            r3 = r1
        L_0x0121:
            boolean r0 = r8.d
            r0 = r0 & r3
            r8.a = r0
            goto L_0x006b
        L_0x0128:
            r0 = move-exception
            goto L_0x0121
        L_0x012a:
            r0 = r3
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.dh.d():void");
    }

    /* access modifiers changed from: protected */
    public final boolean a() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public final double b() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public final double c() {
        return this.f;
    }
}

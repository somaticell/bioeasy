package com.baidu.location.d;

import android.net.wifi.ScanResult;
import android.text.TextUtils;
import com.alipay.sdk.sys.a;
import com.alipay.sdk.util.h;
import com.baidu.location.f.j;
import java.util.List;
import java.util.regex.Pattern;

public class g {
    public List<ScanResult> a = null;
    private long b = 0;
    private long c = 0;
    private boolean d = false;
    private boolean e;

    public g(List<ScanResult> list, long j) {
        this.b = j;
        this.a = list;
        this.c = System.currentTimeMillis();
        l();
    }

    private boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return Pattern.compile("wpa|wep", 2).matcher(str).find();
    }

    private String b(String str) {
        return str != null ? (str.contains(a.b) || str.contains(h.b)) ? str.replace(a.b, "_").replace(h.b, "_") : str : str;
    }

    private void l() {
        boolean z;
        if (a() >= 1) {
            boolean z2 = true;
            for (int size = this.a.size() - 1; size >= 1 && z2; size--) {
                int i = 0;
                z2 = false;
                while (i < size) {
                    if (this.a.get(i).level < this.a.get(i + 1).level) {
                        this.a.set(i + 1, this.a.get(i));
                        this.a.set(i, this.a.get(i + 1));
                        z = true;
                    } else {
                        z = z2;
                    }
                    i++;
                    z2 = z;
                }
            }
        }
    }

    public int a() {
        if (this.a == null) {
            return 0;
        }
        return this.a.size();
    }

    public String a(int i) {
        return a(i, false, false);
    }

    /* JADX WARNING: type inference failed for: r2v82 */
    /* JADX WARNING: type inference failed for: r2v102 */
    /* JADX WARNING: type inference failed for: r2v103 */
    /* JADX WARNING: type inference failed for: r2v121 */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x039f, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00ae, code lost:
        r4 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01f2, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0240, code lost:
        r2 = r4;
        r4 = r6;
        r25 = r5;
        r5 = r7;
        r6 = r8;
        r8 = r3;
        r3 = r25;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x026f A[Catch:{ Error -> 0x01f1, Exception -> 0x039e }] */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x039b A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x03a5  */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x03a9  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0089 A[Catch:{ Error -> 0x01f1, Exception -> 0x039e }] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x01f1 A[ExcHandler: Error (e java.lang.Error), Splitter:B:4:0x000a] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @android.annotation.SuppressLint({"NewApi"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String a(int r27, boolean r28, boolean r29) {
        /*
            r26 = this;
            int r2 = r26.a()
            r3 = 1
            if (r2 >= r3) goto L_0x0009
            r2 = 0
        L_0x0008:
            return r2
        L_0x0009:
            r3 = 0
            java.util.Random r19 = new java.util.Random     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r19.<init>()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.StringBuffer r20 = new java.lang.StringBuffer     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r2 = 512(0x200, float:7.175E-43)
            r0 = r20
            r0.<init>(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.util.ArrayList r21 = new java.util.ArrayList     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r21.<init>()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            com.baidu.location.d.h r2 = com.baidu.location.d.h.a()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            android.net.wifi.WifiInfo r6 = r2.l()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r5 = 0
            r4 = 0
            r2 = -1
            if (r6 == 0) goto L_0x03b7
            java.lang.String r7 = r6.getBSSID()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            if (r7 == 0) goto L_0x03b7
            java.lang.String r2 = r6.getBSSID()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.String r4 = ":"
            java.lang.String r5 = ""
            java.lang.String r5 = r2.replace(r4, r5)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            int r2 = r6.getRssi()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            com.baidu.location.d.h r4 = com.baidu.location.d.h.a()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.String r4 = r4.n()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            if (r2 >= 0) goto L_0x03af
            int r2 = -r2
            r16 = r2
            r17 = r4
            r18 = r5
        L_0x0051:
            r4 = 0
            r8 = 0
            r2 = 0
            int r6 = android.os.Build.VERSION.SDK_INT     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r7 = 17
            if (r6 < r7) goto L_0x03ac
            long r4 = android.os.SystemClock.elapsedRealtimeNanos()     // Catch:{ Error -> 0x00ad, Exception -> 0x039e }
            r6 = 1000(0x3e8, double:4.94E-321)
            long r4 = r4 / r6
        L_0x0063:
            r6 = 0
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x03ac
            r2 = 1
            r14 = r4
        L_0x006b:
            if (r2 == 0) goto L_0x03a9
            if (r2 == 0) goto L_0x00b1
            if (r28 == 0) goto L_0x00b1
            r2 = 1
        L_0x0072:
            r13 = r2
        L_0x0073:
            r7 = 0
            r6 = 0
            r0 = r26
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            int r2 = r2.size()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r5 = 1
            r0 = r27
            if (r2 <= r0) goto L_0x03a5
        L_0x0082:
            r4 = 0
            r2 = 0
            r12 = r2
        L_0x0085:
            r0 = r27
            if (r12 >= r0) goto L_0x026d
            r0 = r26
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.Object r2 = r2.get(r12)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            int r2 = r2.level     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            if (r2 != 0) goto L_0x00b3
            r2 = r4
            r4 = r6
            r25 = r5
            r5 = r7
            r6 = r8
            r8 = r3
            r3 = r25
        L_0x00a0:
            int r9 = r12 + 1
            r12 = r9
            r25 = r3
            r3 = r8
            r8 = r6
            r6 = r4
            r7 = r5
            r4 = r2
            r5 = r25
            goto L_0x0085
        L_0x00ad:
            r4 = move-exception
            r4 = 0
            goto L_0x0063
        L_0x00b1:
            r2 = 0
            goto L_0x0072
        L_0x00b3:
            if (r13 == 0) goto L_0x00d6
            r0 = r26
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a     // Catch:{ Exception -> 0x01c5, Error -> 0x01f1 }
            java.lang.Object r2 = r2.get(r12)     // Catch:{ Exception -> 0x01c5, Error -> 0x01f1 }
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2     // Catch:{ Exception -> 0x01c5, Error -> 0x01f1 }
            long r10 = r2.timestamp     // Catch:{ Exception -> 0x01c5, Error -> 0x01f1 }
            long r10 = r14 - r10
            r22 = 1000000(0xf4240, double:4.940656E-318)
            long r10 = r10 / r22
        L_0x00c8:
            java.lang.Long r2 = java.lang.Long.valueOf(r10)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r21
            r0.add(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            int r2 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r2 <= 0) goto L_0x00d6
            r8 = r10
        L_0x00d6:
            if (r5 == 0) goto L_0x01ca
            r5 = 0
            java.lang.String r2 = "&wf="
            r0 = r20
            r0.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            if (r29 == 0) goto L_0x0101
            java.lang.StringBuffer r4 = new java.lang.StringBuffer     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r4.<init>()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.String r2 = "&wf_ch="
            r4.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r26
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.Object r2 = r2.get(r12)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            int r2 = r2.frequency     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r26
            int r2 = r0.b((int) r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r4.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
        L_0x0101:
            r0 = r26
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.Object r2 = r2.get(r12)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.String r2 = r2.BSSID     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            if (r2 == 0) goto L_0x0262
            java.lang.String r10 = ":"
            java.lang.String r11 = ""
            java.lang.String r10 = r2.replace(r10, r11)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r20
            r0.append(r10)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r26
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.Object r2 = r2.get(r12)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            int r2 = r2.level     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            if (r2 >= 0) goto L_0x012b
            int r2 = -r2
        L_0x012b:
            java.util.Locale r11 = java.util.Locale.CHINA     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.String r22 = ";%d;"
            r23 = 1
            r0 = r23
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r23 = r0
            r24 = 0
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r23[r24] = r2     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r22
            r1 = r23
            java.lang.String r2 = java.lang.String.format(r11, r0, r1)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r20
            r0.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            int r7 = r7 + 1
            r2 = 0
            if (r18 == 0) goto L_0x0171
            r0 = r18
            boolean r10 = r0.equals(r10)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            if (r10 == 0) goto L_0x0171
            r0 = r26
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.Object r2 = r2.get(r12)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.String r2 = r2.capabilities     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r26
            boolean r2 = r0.a((java.lang.String) r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r26
            r0.e = r2     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r2 = 1
            r6 = r7
        L_0x0171:
            if (r2 != 0) goto L_0x024b
            if (r3 != 0) goto L_0x01f5
            r2 = 10
            r0 = r19
            int r2 = r0.nextInt(r2)     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            r10 = 2
            if (r2 != r10) goto L_0x03a2
            r0 = r26
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            java.lang.Object r2 = r2.get(r12)     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            java.lang.String r2 = r2.SSID     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            if (r2 == 0) goto L_0x03a2
            r0 = r26
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            java.lang.Object r2 = r2.get(r12)     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            java.lang.String r2 = r2.SSID     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            int r2 = r2.length()     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            r10 = 30
            if (r2 >= r10) goto L_0x03a2
            r0 = r26
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            java.lang.Object r2 = r2.get(r12)     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            java.lang.String r2 = r2.SSID     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            r0 = r26
            java.lang.String r2 = r0.b((java.lang.String) r2)     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            r0 = r20
            r0.append(r2)     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            r2 = 1
        L_0x01ba:
            r3 = r5
            r5 = r7
            r25 = r6
            r6 = r8
            r8 = r2
            r2 = r4
            r4 = r25
            goto L_0x00a0
        L_0x01c5:
            r2 = move-exception
            r10 = 0
            goto L_0x00c8
        L_0x01ca:
            java.lang.String r2 = "|"
            r0 = r20
            r0.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            if (r29 == 0) goto L_0x0101
            java.lang.String r2 = "|"
            r4.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r26
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.Object r2 = r2.get(r12)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            int r2 = r2.frequency     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r26
            int r2 = r0.b((int) r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r4.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            goto L_0x0101
        L_0x01f1:
            r2 = move-exception
            r2 = 0
            goto L_0x0008
        L_0x01f5:
            r2 = 1
            if (r3 != r2) goto L_0x03a2
            r2 = 20
            r0 = r19
            int r2 = r0.nextInt(r2)     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            r10 = 1
            if (r2 != r10) goto L_0x03a2
            r0 = r26
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            java.lang.Object r2 = r2.get(r12)     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            java.lang.String r2 = r2.SSID     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            if (r2 == 0) goto L_0x03a2
            r0 = r26
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            java.lang.Object r2 = r2.get(r12)     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            java.lang.String r2 = r2.SSID     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            int r2 = r2.length()     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            r10 = 30
            if (r2 >= r10) goto L_0x03a2
            r0 = r26
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            java.lang.Object r2 = r2.get(r12)     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            java.lang.String r2 = r2.SSID     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            r0 = r26
            java.lang.String r2 = r0.b((java.lang.String) r2)     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            r0 = r20
            r0.append(r2)     // Catch:{ Exception -> 0x023f, Error -> 0x01f1 }
            r2 = 2
            goto L_0x01ba
        L_0x023f:
            r2 = move-exception
            r2 = r4
            r4 = r6
            r25 = r5
            r5 = r7
            r6 = r8
            r8 = r3
            r3 = r25
            goto L_0x00a0
        L_0x024b:
            r0 = r26
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.Object r2 = r2.get(r12)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.String r2 = r2.SSID     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r26
            java.lang.String r2 = r0.b((java.lang.String) r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r20
            r0.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
        L_0x0262:
            r2 = r4
            r4 = r6
            r25 = r5
            r5 = r7
            r6 = r8
            r8 = r3
            r3 = r25
            goto L_0x00a0
        L_0x026d:
            if (r5 != 0) goto L_0x039b
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r2.<init>()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.String r3 = "&wf_n="
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.StringBuilder r2 = r2.append(r6)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.String r2 = r2.toString()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r20
            r0.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            if (r18 == 0) goto L_0x02a8
            r2 = -1
            r0 = r16
            if (r0 == r2) goto L_0x02a8
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r2.<init>()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.String r3 = "&wf_rs="
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r16
            java.lang.StringBuilder r2 = r2.append(r0)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.String r2 = r2.toString()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r20
            r0.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
        L_0x02a8:
            r2 = 10
            int r2 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x0330
            int r2 = r21.size()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            if (r2 <= 0) goto L_0x0330
            r2 = 0
            r0 = r21
            java.lang.Object r2 = r0.get(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.Long r2 = (java.lang.Long) r2     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            long r2 = r2.longValue()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r8 = 0
            int r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r2 <= 0) goto L_0x0330
            java.lang.StringBuffer r7 = new java.lang.StringBuffer     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r2 = 128(0x80, float:1.794E-43)
            r7.<init>(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.String r2 = "&wf_ut="
            r7.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r5 = 1
            r2 = 0
            r0 = r21
            java.lang.Object r2 = r0.get(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.Long r2 = (java.lang.Long) r2     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.util.Iterator r8 = r21.iterator()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
        L_0x02e1:
            boolean r3 = r8.hasNext()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            if (r3 == 0) goto L_0x0327
            java.lang.Object r3 = r8.next()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.Long r3 = (java.lang.Long) r3     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            if (r5 == 0) goto L_0x0300
            r5 = 0
            long r10 = r3.longValue()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r7.append(r10)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r3 = r5
        L_0x02f8:
            java.lang.String r5 = "|"
            r7.append(r5)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r5 = r3
            goto L_0x02e1
        L_0x0300:
            long r10 = r3.longValue()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            long r12 = r2.longValue()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            long r10 = r10 - r12
            r12 = 0
            int r3 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r3 == 0) goto L_0x0325
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r3.<init>()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.String r9 = ""
            java.lang.StringBuilder r3 = r3.append(r9)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.StringBuilder r3 = r3.append(r10)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.String r3 = r3.toString()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r7.append(r3)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
        L_0x0325:
            r3 = r5
            goto L_0x02f8
        L_0x0327:
            java.lang.String r2 = r7.toString()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r20
            r0.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
        L_0x0330:
            java.lang.String r2 = "&wf_st="
            r0 = r20
            r0.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r26
            long r2 = r0.b     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r20
            r0.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.String r2 = "&wf_et="
            r0 = r20
            r0.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r26
            long r2 = r0.c     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r20
            r0.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.String r2 = "&wf_vt="
            r0 = r20
            r0.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            long r2 = com.baidu.location.d.h.a     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r20
            r0.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            if (r6 <= 0) goto L_0x0378
            r2 = 1
            r0 = r26
            r0.d = r2     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            java.lang.String r2 = "&wf_en="
            r0 = r20
            r0.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r26
            boolean r2 = r0.e     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            if (r2 == 0) goto L_0x0399
            r2 = 1
        L_0x0373:
            r0 = r20
            r0.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
        L_0x0378:
            if (r17 == 0) goto L_0x0388
            java.lang.String r2 = "&wf_gw="
            r0 = r20
            r0.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r20
            r1 = r17
            r0.append(r1)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
        L_0x0388:
            if (r4 == 0) goto L_0x0393
            java.lang.String r2 = r4.toString()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            r0 = r20
            r0.append(r2)     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
        L_0x0393:
            java.lang.String r2 = r20.toString()     // Catch:{ Error -> 0x01f1, Exception -> 0x039e }
            goto L_0x0008
        L_0x0399:
            r2 = 0
            goto L_0x0373
        L_0x039b:
            r2 = 0
            goto L_0x0008
        L_0x039e:
            r2 = move-exception
            r2 = 0
            goto L_0x0008
        L_0x03a2:
            r2 = r3
            goto L_0x01ba
        L_0x03a5:
            r27 = r2
            goto L_0x0082
        L_0x03a9:
            r13 = r2
            goto L_0x0073
        L_0x03ac:
            r14 = r4
            goto L_0x006b
        L_0x03af:
            r16 = r2
            r17 = r4
            r18 = r5
            goto L_0x0051
        L_0x03b7:
            r16 = r2
            r17 = r4
            r18 = r5
            goto L_0x0051
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.d.g.a(int, boolean, boolean):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(long r20) {
        /*
            r19 = this;
            r10 = 0
            r8 = 0
            r6 = 0
            r4 = 0
            r2 = 0
            int r3 = android.os.Build.VERSION.SDK_INT
            r11 = 17
            if (r3 < r11) goto L_0x009a
            long r8 = android.os.SystemClock.elapsedRealtimeNanos()     // Catch:{ Error -> 0x0021, Exception -> 0x0025 }
            r12 = 1000(0x3e8, double:4.94E-321)
            long r8 = r8 / r12
        L_0x0015:
            r12 = 0
            int r3 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
            if (r3 <= 0) goto L_0x009a
            r2 = 1
            r12 = r2
            r14 = r8
        L_0x001e:
            if (r12 != 0) goto L_0x0029
        L_0x0020:
            return r10
        L_0x0021:
            r3 = move-exception
            r8 = 0
            goto L_0x0015
        L_0x0025:
            r3 = move-exception
            r8 = 0
            goto L_0x0015
        L_0x0029:
            r0 = r19
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a
            if (r2 == 0) goto L_0x0020
            r0 = r19
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a
            int r2 = r2.size()
            if (r2 == 0) goto L_0x0020
            r0 = r19
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a
            int r2 = r2.size()
            r3 = 16
            if (r2 <= r3) goto L_0x0098
            r2 = 16
            r3 = r2
        L_0x0048:
            r2 = 0
            r11 = r2
        L_0x004a:
            if (r11 >= r3) goto L_0x0082
            r0 = r19
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a
            java.lang.Object r2 = r2.get(r11)
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2
            int r2 = r2.level
            if (r2 != 0) goto L_0x005e
        L_0x005a:
            int r2 = r11 + 1
            r11 = r2
            goto L_0x004a
        L_0x005e:
            if (r12 == 0) goto L_0x005a
            r0 = r19
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a     // Catch:{ Exception -> 0x007a, Error -> 0x007e }
            java.lang.Object r2 = r2.get(r11)     // Catch:{ Exception -> 0x007a, Error -> 0x007e }
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2     // Catch:{ Exception -> 0x007a, Error -> 0x007e }
            long r8 = r2.timestamp     // Catch:{ Exception -> 0x007a, Error -> 0x007e }
            long r8 = r14 - r8
            r16 = 1000000(0xf4240, double:4.940656E-318)
            long r8 = r8 / r16
        L_0x0073:
            long r4 = r4 + r8
            int r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r2 <= 0) goto L_0x005a
            r6 = r8
            goto L_0x005a
        L_0x007a:
            r2 = move-exception
            r8 = 0
            goto L_0x0073
        L_0x007e:
            r2 = move-exception
            r8 = 0
            goto L_0x0073
        L_0x0082:
            long r2 = (long) r3
            long r2 = r4 / r2
            r4 = 1000(0x3e8, double:4.94E-321)
            long r4 = r4 * r6
            int r4 = (r4 > r20 ? 1 : (r4 == r20 ? 0 : -1))
            if (r4 > 0) goto L_0x0093
            r4 = 1000(0x3e8, double:4.94E-321)
            long r2 = r2 * r4
            int r2 = (r2 > r20 ? 1 : (r2 == r20 ? 0 : -1))
            if (r2 <= 0) goto L_0x0096
        L_0x0093:
            r2 = 1
        L_0x0094:
            r10 = r2
            goto L_0x0020
        L_0x0096:
            r2 = r10
            goto L_0x0094
        L_0x0098:
            r3 = r2
            goto L_0x0048
        L_0x009a:
            r12 = r2
            r14 = r8
            goto L_0x001e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.d.g.a(long):boolean");
    }

    public boolean a(g gVar) {
        if (this.a == null || gVar == null || gVar.a == null) {
            return false;
        }
        int size = this.a.size() < gVar.a.size() ? this.a.size() : gVar.a.size();
        for (int i = 0; i < size; i++) {
            if (!this.a.get(i).BSSID.equals(gVar.a.get(i).BSSID)) {
                return false;
            }
        }
        return true;
    }

    public int b(int i) {
        if (i <= 2400 || i >= 2500) {
            return (i <= 4900 || i >= 5900) ? 0 : 5;
        }
        return 2;
    }

    public String b() {
        try {
            return a(j.N, true, true);
        } catch (Exception e2) {
            return null;
        }
    }

    public boolean b(g gVar) {
        if (this.a == null || gVar == null || gVar.a == null) {
            return false;
        }
        int size = this.a.size() < gVar.a.size() ? this.a.size() : gVar.a.size();
        for (int i = 0; i < size; i++) {
            String str = this.a.get(i).BSSID;
            int i2 = this.a.get(i).level;
            String str2 = gVar.a.get(i).BSSID;
            int i3 = gVar.a.get(i).level;
            if (!str.equals(str2) || i2 != i3) {
                return false;
            }
        }
        return true;
    }

    public String c() {
        try {
            return a(j.N, true, false);
        } catch (Exception e2) {
            return null;
        }
    }

    public String c(int i) {
        int i2;
        int i3 = 0;
        if (i == 0 || a() < 1) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer(256);
        int size = this.a.size();
        int i4 = size > j.N ? j.N : size;
        int i5 = 1;
        int i6 = 0;
        while (i6 < i4) {
            if ((i5 & i) == 0 || this.a.get(i6).BSSID == null) {
                i2 = i3;
            } else {
                if (i3 == 0) {
                    stringBuffer.append("&ssid=");
                } else {
                    stringBuffer.append("|");
                }
                stringBuffer.append(this.a.get(i6).BSSID.replace(":", ""));
                stringBuffer.append(h.b);
                stringBuffer.append(b(this.a.get(i6).SSID));
                i2 = i3 + 1;
            }
            i5 <<= 1;
            i6++;
            i3 = i2;
        }
        return stringBuffer.toString();
    }

    public boolean c(g gVar) {
        return h.a(gVar, this);
    }

    public String d() {
        try {
            return a(15);
        } catch (Exception e2) {
            return null;
        }
    }

    public boolean e() {
        return a((long) j.ae);
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:46:? A[RETURN, SYNTHETIC] */
    @android.annotation.SuppressLint({"NewApi"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long f() {
        /*
            r18 = this;
            r12 = 0
            r0 = r18
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a
            if (r2 == 0) goto L_0x0012
            r0 = r18
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a
            int r2 = r2.size()
            if (r2 != 0) goto L_0x0013
        L_0x0012:
            return r12
        L_0x0013:
            r4 = 0
            r12 = 0
            r10 = 0
            r8 = 0
            r2 = 0
            int r3 = android.os.Build.VERSION.SDK_INT
            r6 = 17
            if (r3 < r6) goto L_0x0098
            long r4 = android.os.SystemClock.elapsedRealtimeNanos()     // Catch:{ Error -> 0x0056, Exception -> 0x005a }
            r6 = 1000(0x3e8, double:4.94E-321)
            long r4 = r4 / r6
        L_0x0029:
            r6 = 0
            int r3 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r3 <= 0) goto L_0x0098
            r2 = 1
            r3 = r2
        L_0x0031:
            r0 = r18
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a
            int r2 = r2.size()
            r6 = 16
            if (r2 <= r6) goto L_0x0096
            r2 = 16
            r6 = r2
        L_0x0040:
            r2 = 0
            r7 = r2
        L_0x0042:
            if (r7 >= r6) goto L_0x0086
            r0 = r18
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a
            java.lang.Object r2 = r2.get(r7)
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2
            int r2 = r2.level
            if (r2 != 0) goto L_0x005e
        L_0x0052:
            int r2 = r7 + 1
            r7 = r2
            goto L_0x0042
        L_0x0056:
            r3 = move-exception
            r4 = 0
            goto L_0x0029
        L_0x005a:
            r3 = move-exception
            r4 = 0
            goto L_0x0029
        L_0x005e:
            if (r3 == 0) goto L_0x0052
            r0 = r18
            java.util.List<android.net.wifi.ScanResult> r2 = r0.a     // Catch:{ Exception -> 0x007e, Error -> 0x0082 }
            java.lang.Object r2 = r2.get(r7)     // Catch:{ Exception -> 0x007e, Error -> 0x0082 }
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2     // Catch:{ Exception -> 0x007e, Error -> 0x0082 }
            long r14 = r2.timestamp     // Catch:{ Exception -> 0x007e, Error -> 0x0082 }
            long r14 = r4 - r14
            r16 = 1000000(0xf4240, double:4.940656E-318)
            long r14 = r14 / r16
        L_0x0073:
            long r10 = r10 + r14
            r16 = 1
            long r8 = r8 + r16
            int r2 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
            if (r2 <= 0) goto L_0x0052
            r12 = r14
            goto L_0x0052
        L_0x007e:
            r2 = move-exception
            r14 = 0
            goto L_0x0073
        L_0x0082:
            r2 = move-exception
            r14 = 0
            goto L_0x0073
        L_0x0086:
            r2 = 1
            int r2 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x0012
            long r2 = r10 - r12
            r4 = 1
            long r4 = r8 - r4
            long r12 = r2 / r4
            goto L_0x0012
        L_0x0096:
            r6 = r2
            goto L_0x0040
        L_0x0098:
            r3 = r2
            goto L_0x0031
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.d.g.f():long");
    }

    public int g() {
        for (int i = 0; i < a(); i++) {
            int i2 = -this.a.get(i).level;
            if (i2 > 0) {
                return i2;
            }
        }
        return 0;
    }

    public boolean h() {
        return this.d;
    }

    public boolean i() {
        return System.currentTimeMillis() - this.c > 0 && System.currentTimeMillis() - this.c < 5000;
    }

    public boolean j() {
        return System.currentTimeMillis() - this.c > 0 && System.currentTimeMillis() - this.c < 5000;
    }

    public boolean k() {
        return System.currentTimeMillis() - this.c > 0 && System.currentTimeMillis() - this.b < 5000;
    }
}

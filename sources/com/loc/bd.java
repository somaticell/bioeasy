package com.loc;

import android.content.Context;
import android.net.NetworkInfo;
import android.support.v7.widget.ActivityChooserView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import cn.com.bioeasy.app.utils.ListUtils;
import com.autonavi.aps.amapapi.model.AmapLoc;
import com.facebook.common.util.UriUtil;
import com.loc.ay;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/* compiled from: Off */
public class bd {
    public static final int[] a = {0, 0};
    static int b = 213891;
    private static volatile String c = null;
    private static Hashtable<String, Long> d = new Hashtable<>();
    private static bn e = new bn();
    private static Hashtable<String, String> f = new Hashtable<>();
    private static TelephonyManager g = null;

    private bd() {
    }

    public static String[] a(double d2, double d3, String str) {
        String a2;
        String[] strArr = new String[50];
        if (!TextUtils.isEmpty(str)) {
            a2 = str;
        } else {
            a2 = bc.a(d2, d3);
            str = bc.a(d2, d3);
        }
        strArr[0] = a2;
        strArr[25] = str;
        String[] a3 = bc.a(a2);
        for (int i = 1; i < 25; i++) {
            strArr[i] = a3[i - 1];
        }
        String[] a4 = bc.a(str);
        for (int i2 = 26; i2 < 50; i2++) {
            strArr[i2] = a4[i2 - 26];
        }
        return strArr;
    }

    /* JADX WARNING: Removed duplicated region for block: B:156:0x0481  */
    /* JADX WARNING: Removed duplicated region for block: B:195:0x00f4 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.autonavi.aps.amapapi.model.AmapLoc a(double[] r29, java.lang.String r30, java.lang.String r31, java.lang.String r32, int r33, android.content.Context r34, int[] r35) {
        /*
            boolean r4 = android.text.TextUtils.isEmpty(r31)
            if (r4 == 0) goto L_0x0008
            r4 = 0
        L_0x0007:
            return r4
        L_0x0008:
            java.lang.String r4 = "gps"
            r0 = r31
            boolean r4 = r0.contains(r4)
            if (r4 == 0) goto L_0x0014
            r4 = 0
            goto L_0x0007
        L_0x0014:
            int r14 = a((java.lang.String) r31)
            r0 = r31
            java.lang.String r15 = a((int) r14, (java.lang.String) r0)
            java.util.Hashtable r16 = new java.util.Hashtable
            r16.<init>()
            r0 = r31
            r1 = r32
            r2 = r16
            a((int) r14, (java.lang.String) r0, (java.lang.String) r1, (java.util.Hashtable<java.lang.String, java.lang.String>) r2)
            java.util.Hashtable r17 = new java.util.Hashtable
            r17.<init>()
            r0 = r32
            r1 = r17
            a((java.lang.String) r0, (java.util.Hashtable<java.lang.String, java.lang.String>) r1)
            java.lang.StringBuilder r18 = c()
            if (r29 != 0) goto L_0x008b
            r4 = 0
            r6 = 0
            r0 = r30
            java.lang.String[] r4 = a((double) r4, (double) r6, (java.lang.String) r0)
            r6 = r4
        L_0x0049:
            r7 = 0
            r5 = 0
            int r4 = r6.length
            int r19 = r4 / 2
            r4 = 1
            r8 = 1
            r0 = r33
            if (r8 > r0) goto L_0x04c5
            r8 = 3
            r0 = r33
            if (r0 > r8) goto L_0x04c5
        L_0x0059:
            int r4 = r16.size()
            com.loc.e.o = r4
            r4 = 0
            r8 = r5
            r5 = r7
            r7 = r4
        L_0x0063:
            int r4 = r6.length
            if (r7 >= r4) goto L_0x006a
            boolean r4 = com.loc.e.n
            if (r4 != 0) goto L_0x0099
        L_0x006a:
            r4 = 0
            int r5 = r18.length()
            r0 = r18
            r0.delete(r4, r5)
            r4 = 0
            r4 = r35[r4]
            r5 = 1
            r5 = r35[r5]
            r0 = r16
            r1 = r17
            com.autonavi.aps.amapapi.model.AmapLoc r4 = a((java.util.Hashtable<java.lang.String, java.lang.String>) r0, (java.util.Hashtable<java.lang.String, java.lang.String>) r1, (int) r4, (int) r5)
            boolean r5 = com.loc.bw.a((com.autonavi.aps.amapapi.model.AmapLoc) r4)
            if (r5 != 0) goto L_0x0007
            r4 = 0
            goto L_0x0007
        L_0x008b:
            r4 = 0
            r4 = r29[r4]
            r6 = 1
            r6 = r29[r6]
            r0 = r30
            java.lang.String[] r4 = a((double) r4, (double) r6, (java.lang.String) r0)
            r6 = r4
            goto L_0x0049
        L_0x0099:
            r0 = r19
            if (r7 >= r0) goto L_0x00fe
            if (r5 > 0) goto L_0x00a5
            boolean r4 = r16.isEmpty()
            if (r4 == 0) goto L_0x00fe
        L_0x00a5:
            r4 = 1
            r0 = r33
            if (r0 != r4) goto L_0x0111
            if (r7 != 0) goto L_0x006a
        L_0x00ac:
            java.lang.String r4 = r18.toString()
            r0 = r19
            if (r7 >= r0) goto L_0x04b6
            switch(r14) {
                case 1: goto L_0x012a;
                case 2: goto L_0x0132;
                default: goto L_0x00b7;
            }
        L_0x00b7:
            r9 = 0
            r10 = 0
            r35[r9] = r10
            r9 = 1
            r10 = 0
            r35[r9] = r10
        L_0x00bf:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.StringBuilder r4 = r9.append(r4)
            java.lang.String r9 = java.io.File.separator
            java.lang.StringBuilder r4 = r4.append(r9)
            java.lang.String r4 = r4.toString()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.StringBuilder r4 = r9.append(r4)
            r9 = r6[r7]
            java.lang.StringBuilder r4 = r4.append(r9)
            java.lang.String r20 = r4.toString()
            java.lang.String r4 = c
            r0 = r20
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x01e2
            r28 = r8
            r8 = r5
            r5 = r28
        L_0x00f4:
            int r4 = r7 + 1
            r7 = r4
            r28 = r5
            r5 = r8
            r8 = r28
            goto L_0x0063
        L_0x00fe:
            r0 = r19
            if (r7 < r0) goto L_0x010a
            if (r8 > 0) goto L_0x00a5
            boolean r4 = r17.isEmpty()
            if (r4 != 0) goto L_0x00a5
        L_0x010a:
            r0 = r19
            if (r7 < r0) goto L_0x00a5
            if (r5 <= 0) goto L_0x00a5
            goto L_0x00a5
        L_0x0111:
            r4 = 2
            r0 = r33
            if (r0 != r4) goto L_0x00ac
            r4 = 8
            if (r7 <= r4) goto L_0x0124
            r4 = 25
            if (r7 >= r4) goto L_0x0124
            r28 = r8
            r8 = r5
            r5 = r28
            goto L_0x00f4
        L_0x0124:
            r4 = 33
            if (r7 <= r4) goto L_0x00ac
            goto L_0x006a
        L_0x012a:
            r9 = 0
            r10 = 0
            r35[r9] = r10
            r9 = 1
            r10 = 0
            r35[r9] = r10
        L_0x0132:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.StringBuilder r4 = r9.append(r4)
            java.lang.StringBuilder r4 = r4.append(r15)
            java.lang.String r9 = java.io.File.separator
            java.lang.StringBuilder r4 = r4.append(r9)
            java.lang.String r4 = r4.toString()
            r9 = r6[r7]
            java.lang.String r10 = "-"
            boolean r9 = r9.startsWith(r10)
            if (r9 == 0) goto L_0x01a7
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.StringBuilder r4 = r9.append(r4)
            r9 = r6[r7]
            r10 = 0
            r11 = 4
            java.lang.String r9 = r9.substring(r10, r11)
            java.lang.StringBuilder r4 = r4.append(r9)
            java.lang.String r9 = ","
            java.lang.StringBuilder r4 = r4.append(r9)
            java.lang.String r4 = r4.toString()
        L_0x0172:
            r9 = r6[r7]
            java.lang.String r10 = ","
            int r9 = r9.indexOf(r10)
            int r9 = r9 + 1
            r10 = r6[r7]
            int r11 = r9 + 1
            java.lang.String r10 = r10.substring(r9, r11)
            java.lang.String r11 = "-"
            boolean r10 = r10.startsWith(r11)
            if (r10 == 0) goto L_0x01c7
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.StringBuilder r4 = r10.append(r4)
            r10 = r6[r7]
            int r11 = r9 + 4
            java.lang.String r9 = r10.substring(r9, r11)
            java.lang.StringBuilder r4 = r4.append(r9)
            java.lang.String r4 = r4.toString()
            goto L_0x00bf
        L_0x01a7:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.StringBuilder r4 = r9.append(r4)
            r9 = r6[r7]
            r10 = 0
            r11 = 3
            java.lang.String r9 = r9.substring(r10, r11)
            java.lang.StringBuilder r4 = r4.append(r9)
            java.lang.String r9 = ","
            java.lang.StringBuilder r4 = r4.append(r9)
            java.lang.String r4 = r4.toString()
            goto L_0x0172
        L_0x01c7:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.StringBuilder r4 = r10.append(r4)
            r10 = r6[r7]
            int r11 = r9 + 3
            java.lang.String r9 = r10.substring(r9, r11)
            java.lang.StringBuilder r4 = r4.append(r9)
            java.lang.String r4 = r4.toString()
            goto L_0x00bf
        L_0x01e2:
            com.loc.bn r21 = e
            r0 = r21
            r1 = r20
            java.lang.Object r4 = r0.b(r1)
            com.loc.bi r4 = (com.loc.bi) r4
            r9 = 0
            java.io.File r12 = new java.io.File
            r0 = r20
            r12.<init>(r0)
            if (r4 != 0) goto L_0x024d
            java.io.File r4 = r12.getParentFile()
            boolean r4 = r4.exists()
            if (r4 != 0) goto L_0x0209
            r28 = r8
            r8 = r5
            r5 = r28
            goto L_0x00f4
        L_0x0209:
            boolean r4 = r12.isDirectory()
            if (r4 == 0) goto L_0x0216
            r28 = r8
            r8 = r5
            r5 = r28
            goto L_0x00f4
        L_0x0216:
            boolean r4 = r12.exists()
            if (r4 != 0) goto L_0x0223
            r28 = r8
            r8 = r5
            r5 = r28
            goto L_0x00f4
        L_0x0223:
            com.loc.bi$a r10 = new com.loc.bi$a
            r10.<init>()
            com.loc.bi r4 = new com.loc.bi     // Catch:{ FileNotFoundException -> 0x0238, Throwable -> 0x0240 }
            r4.<init>(r12, r10)     // Catch:{ FileNotFoundException -> 0x0238, Throwable -> 0x0240 }
        L_0x022d:
            r10 = r4
        L_0x022e:
            r4 = 0
            if (r10 != 0) goto L_0x0250
            r28 = r8
            r8 = r5
            r5 = r28
            goto L_0x00f4
        L_0x0238:
            r4 = move-exception
            r28 = r8
            r8 = r5
            r5 = r28
            goto L_0x00f4
        L_0x0240:
            r4 = move-exception
            r4 = 0
            r10.a = r4
            com.loc.bi r4 = new com.loc.bi     // Catch:{ Throwable -> 0x024a }
            r4.<init>(r12, r10)     // Catch:{ Throwable -> 0x024a }
            goto L_0x022d
        L_0x024a:
            r4 = move-exception
            r4 = 0
            goto L_0x022d
        L_0x024d:
            r9 = 1
            r10 = r4
            goto L_0x022e
        L_0x0250:
            r22 = 0
            r0 = r22
            r10.a(r0)     // Catch:{ Exception -> 0x029e }
            long r22 = r10.c()     // Catch:{ Exception -> 0x029e }
            r0 = r19
            if (r7 >= r0) goto L_0x0263
            int r4 = r10.d()     // Catch:{ Exception -> 0x029e }
        L_0x0263:
            long r24 = r10.g()     // Catch:{ Exception -> 0x02af }
            r11 = 8
            r0 = r19
            if (r7 >= r0) goto L_0x0273
            int r4 = r4 * 4
            int r4 = r4 + 2
            int r4 = r4 + r11
            r11 = r4
        L_0x0273:
            r26 = 7776000000(0x1cf7c5800, double:3.841854462E-314)
            long r22 = r22 + r26
            long r26 = com.loc.bw.a()
            int r4 = (r22 > r26 ? 1 : (r22 == r26 ? 0 : -1))
            if (r4 >= 0) goto L_0x02c6
            if (r10 == 0) goto L_0x028d
            if (r9 == 0) goto L_0x02c0
            r0 = r21
            r1 = r20
            r0.c(r1)     // Catch:{ Exception -> 0x02c4 }
        L_0x028d:
            r12.delete()
            java.util.Hashtable<java.lang.String, java.lang.Long> r4 = d
            r9 = r6[r7]
            r4.remove(r9)
            r28 = r8
            r8 = r5
            r5 = r28
            goto L_0x00f4
        L_0x029e:
            r4 = move-exception
            if (r9 == 0) goto L_0x02a8
            r0 = r21
            r1 = r20
            r0.c(r1)
        L_0x02a8:
            r28 = r8
            r8 = r5
            r5 = r28
            goto L_0x00f4
        L_0x02af:
            r4 = move-exception
            if (r9 == 0) goto L_0x02b9
            r0 = r21
            r1 = r20
            r0.c(r1)
        L_0x02b9:
            r28 = r8
            r8 = r5
            r5 = r28
            goto L_0x00f4
        L_0x02c0:
            r10.b()     // Catch:{ Exception -> 0x02c4 }
            goto L_0x028d
        L_0x02c4:
            r4 = move-exception
            goto L_0x028d
        L_0x02c6:
            r22 = 8
            int r4 = (r24 > r22 ? 1 : (r24 == r22 ? 0 : -1))
            if (r4 <= 0) goto L_0x02db
            long r0 = (long) r11
            r22 = r0
            long r22 = r24 - r22
            r24 = 16
            long r22 = r22 % r24
            r24 = 0
            int r4 = (r22 > r24 ? 1 : (r22 == r24 ? 0 : -1))
            if (r4 == 0) goto L_0x02f1
        L_0x02db:
            if (r10 == 0) goto L_0x02e0
            r10.b()     // Catch:{ Exception -> 0x04bd }
        L_0x02e0:
            r12.delete()
            java.util.Hashtable<java.lang.String, java.lang.Long> r4 = d
            r9 = r6[r7]
            r4.remove(r9)
            r28 = r8
            r8 = r5
            r5 = r28
            goto L_0x00f4
        L_0x02f1:
            r0 = r19
            if (r7 >= r0) goto L_0x0491
            boolean r4 = r16.isEmpty()
            if (r4 != 0) goto L_0x0491
            int r4 = com.loc.e.o
            if (r5 >= r4) goto L_0x0491
            r4 = 1
            r13 = r4
        L_0x0301:
            r0 = r19
            if (r7 < r0) goto L_0x0495
            boolean r4 = r17.isEmpty()
            if (r4 != 0) goto L_0x0495
            r4 = 15
            if (r8 >= r4) goto L_0x0495
            r4 = 1
            r12 = r4
        L_0x0311:
            if (r13 == 0) goto L_0x04c2
            java.util.Set r4 = r16.entrySet()     // Catch:{ Throwable -> 0x04a1 }
            java.util.Iterator r22 = r4.iterator()     // Catch:{ Throwable -> 0x04a1 }
            r13 = r5
        L_0x031c:
            boolean r4 = r22.hasNext()     // Catch:{ Throwable -> 0x04c0 }
            if (r4 == 0) goto L_0x03c1
            java.lang.Object r4 = r22.next()     // Catch:{ Throwable -> 0x04c0 }
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4     // Catch:{ Throwable -> 0x04c0 }
            java.lang.Object r5 = r4.getKey()     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x04c0 }
            r23 = 0
            r0 = r23
            double[] r5 = a((int) r11, (com.loc.bi) r10, (java.lang.String) r5, (int) r0)     // Catch:{ Throwable -> 0x04c0 }
            if (r5 == 0) goto L_0x0499
            int r13 = r13 + 1
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x04c0 }
            r23.<init>()     // Catch:{ Throwable -> 0x04c0 }
            r24 = 0
            r24 = r5[r24]     // Catch:{ Throwable -> 0x04c0 }
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r24 = "|"
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ Throwable -> 0x04c0 }
            r24 = 1
            r24 = r5[r24]     // Catch:{ Throwable -> 0x04c0 }
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r23 = r23.toString()     // Catch:{ Throwable -> 0x04c0 }
            java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x04c0 }
            r24.<init>()     // Catch:{ Throwable -> 0x04c0 }
            r0 = r24
            r1 = r23
            java.lang.StringBuilder r23 = r0.append(r1)     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r24 = "|"
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ Throwable -> 0x04c0 }
            r24 = 2
            r24 = r5[r24]     // Catch:{ Throwable -> 0x04c0 }
            java.lang.StringBuilder r5 = r23.append(r24)     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r23 = "|"
            r0 = r23
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x04c0 }
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x04c0 }
            r23.<init>()     // Catch:{ Throwable -> 0x04c0 }
            r0 = r23
            java.lang.StringBuilder r23 = r0.append(r5)     // Catch:{ Throwable -> 0x04c0 }
            java.lang.Object r5 = r4.getKey()     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x04c0 }
            r0 = r16
            java.lang.Object r5 = r0.get(r5)     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Throwable -> 0x04c0 }
            r0 = r23
            java.lang.StringBuilder r5 = r0.append(r5)     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x04c0 }
            java.lang.Object r4 = r4.getKey()     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x04c0 }
            r0 = r16
            r0.put(r4, r5)     // Catch:{ Throwable -> 0x04c0 }
            int r4 = com.loc.e.o     // Catch:{ Throwable -> 0x04c0 }
            if (r13 < r4) goto L_0x0499
        L_0x03c1:
            if (r12 == 0) goto L_0x047e
            java.util.Set r4 = r17.entrySet()     // Catch:{ Throwable -> 0x04c0 }
            java.util.Iterator r12 = r4.iterator()     // Catch:{ Throwable -> 0x04c0 }
        L_0x03cb:
            if (r12 == 0) goto L_0x047e
            boolean r4 = r12.hasNext()     // Catch:{ Throwable -> 0x04c0 }
            if (r4 == 0) goto L_0x047e
            java.lang.Object r4 = r12.next()     // Catch:{ Throwable -> 0x04c0 }
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4     // Catch:{ Throwable -> 0x04c0 }
            java.lang.Object r5 = r4.getKey()     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x04c0 }
            r22 = 1
            r0 = r22
            double[] r5 = a((int) r11, (com.loc.bi) r10, (java.lang.String) r5, (int) r0)     // Catch:{ Throwable -> 0x04c0 }
            if (r5 == 0) goto L_0x049d
            int r8 = r8 + 1
            java.lang.StringBuilder r22 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x04c0 }
            r22.<init>()     // Catch:{ Throwable -> 0x04c0 }
            r23 = 0
            r24 = r5[r23]     // Catch:{ Throwable -> 0x04c0 }
            r0 = r22
            r1 = r24
            java.lang.StringBuilder r22 = r0.append(r1)     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r23 = "|"
            java.lang.StringBuilder r22 = r22.append(r23)     // Catch:{ Throwable -> 0x04c0 }
            r23 = 1
            r24 = r5[r23]     // Catch:{ Throwable -> 0x04c0 }
            r0 = r22
            r1 = r24
            java.lang.StringBuilder r22 = r0.append(r1)     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r22 = r22.toString()     // Catch:{ Throwable -> 0x04c0 }
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x04c0 }
            r23.<init>()     // Catch:{ Throwable -> 0x04c0 }
            r0 = r23
            r1 = r22
            java.lang.StringBuilder r22 = r0.append(r1)     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r23 = "|"
            java.lang.StringBuilder r22 = r22.append(r23)     // Catch:{ Throwable -> 0x04c0 }
            r23 = 2
            r24 = r5[r23]     // Catch:{ Throwable -> 0x04c0 }
            r0 = r22
            r1 = r24
            java.lang.StringBuilder r5 = r0.append(r1)     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r22 = "|"
            r0 = r22
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x04c0 }
            java.lang.StringBuilder r22 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x04c0 }
            r22.<init>()     // Catch:{ Throwable -> 0x04c0 }
            r0 = r22
            java.lang.StringBuilder r22 = r0.append(r5)     // Catch:{ Throwable -> 0x04c0 }
            java.lang.Object r5 = r4.getKey()     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x04c0 }
            r0 = r17
            java.lang.Object r5 = r0.get(r5)     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Throwable -> 0x04c0 }
            r0 = r22
            java.lang.StringBuilder r5 = r0.append(r5)     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x04c0 }
            java.lang.Object r4 = r4.getKey()     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x04c0 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x04c0 }
            r0 = r17
            r0.put(r4, r5)     // Catch:{ Throwable -> 0x04c0 }
            r4 = 15
            if (r8 < r4) goto L_0x049d
        L_0x047e:
            r5 = r13
        L_0x047f:
            if (r10 == 0) goto L_0x04b6
            boolean r4 = r10.a()
            if (r4 != 0) goto L_0x04ad
            r10.b()     // Catch:{ Exception -> 0x04a5 }
            r28 = r8
            r8 = r5
            r5 = r28
            goto L_0x00f4
        L_0x0491:
            r4 = 0
            r13 = r4
            goto L_0x0301
        L_0x0495:
            r4 = 0
            r12 = r4
            goto L_0x0311
        L_0x0499:
            r4 = r13
            r13 = r4
            goto L_0x031c
        L_0x049d:
            r4 = r8
            r8 = r4
            goto L_0x03cb
        L_0x04a1:
            r4 = move-exception
            r13 = r5
        L_0x04a3:
            r5 = r13
            goto L_0x047f
        L_0x04a5:
            r4 = move-exception
            r28 = r8
            r8 = r5
            r5 = r28
            goto L_0x00f4
        L_0x04ad:
            if (r9 != 0) goto L_0x04b6
            r0 = r21
            r1 = r20
            r0.b(r1, r10)
        L_0x04b6:
            r28 = r8
            r8 = r5
            r5 = r28
            goto L_0x00f4
        L_0x04bd:
            r4 = move-exception
            goto L_0x02e0
        L_0x04c0:
            r4 = move-exception
            goto L_0x04a3
        L_0x04c2:
            r13 = r5
            goto L_0x03c1
        L_0x04c5:
            r33 = r4
            goto L_0x0059
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bd.a(double[], java.lang.String, java.lang.String, java.lang.String, int, android.content.Context, int[]):com.autonavi.aps.amapapi.model.AmapLoc");
    }

    private static int a(String str) {
        if (TextUtils.isEmpty(str) || !str.contains("cgi")) {
            return 9;
        }
        String[] split = str.split("#");
        if (split.length == 7) {
            return 1;
        }
        if (split.length == 8) {
            return 2;
        }
        return 9;
    }

    private static String a(int i, String str) {
        String[] split = str.split("#");
        switch (i) {
            case 1:
                return split[1] + "_" + split[2];
            case 2:
                return split[3];
            default:
                return null;
        }
    }

    private static void a(int i, String str, String str2, Hashtable<String, String> hashtable) {
        String[] split = str.split("#");
        switch (i) {
            case 1:
                hashtable.put(split[3] + "|" + split[4], "access");
                if (TextUtils.isEmpty(str2) || 0 < str2.split("#").length) {
                }
                return;
            case 2:
                hashtable.put(split[3] + "|" + split[4] + "|" + split[5], "access");
                return;
            default:
                return;
        }
    }

    private static void a(String str, Hashtable<String, String> hashtable) {
        if (!TextUtils.isEmpty(str)) {
            String[] strArr = new String[2];
            for (String str2 : str.split("#")) {
                if (str2.contains(ListUtils.DEFAULT_JOIN_SEPARATOR)) {
                    String[] split = str2.split(ListUtils.DEFAULT_JOIN_SEPARATOR);
                    hashtable.put(split[0], split[1]);
                }
            }
        }
    }

    private static AmapLoc a(Hashtable<String, String> hashtable, Hashtable<String, String> hashtable2, int i, int i2) {
        ArrayList<ay.c> arrayList;
        AmapLoc amapLoc;
        boolean z;
        ay ayVar = new ay();
        try {
            if (!hashtable.isEmpty()) {
                for (Map.Entry<String, String> value : hashtable.entrySet()) {
                    String str = (String) value.getValue();
                    if (str.contains("access")) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (str.contains("|")) {
                        try {
                            ayVar.a(z ? 1 : 2, str.substring(0, str.lastIndexOf("|")));
                        } catch (Throwable th) {
                        }
                    }
                }
            }
            if (!hashtable2.isEmpty()) {
                for (Map.Entry<String, String> value2 : hashtable2.entrySet()) {
                    String str2 = (String) value2.getValue();
                    if (str2.contains("|")) {
                        try {
                            ayVar.a(0, str2.substring(0, str2.lastIndexOf("|")));
                        } catch (Throwable th2) {
                        }
                    }
                }
            }
        } catch (Throwable th3) {
        }
        try {
            arrayList = ayVar.a((double) i2, (double) i);
        } catch (Throwable th4) {
            arrayList = null;
        }
        if (arrayList == null || arrayList.isEmpty()) {
            amapLoc = null;
        } else {
            ay.c cVar = arrayList.get(0);
            if (0 == 0) {
                amapLoc = new AmapLoc();
            } else {
                amapLoc = null;
            }
            amapLoc.c("network");
            amapLoc.b(cVar.a);
            amapLoc.a(cVar.b);
            amapLoc.a((float) cVar.c);
            amapLoc.k(cVar.d);
            amapLoc.w("0");
            amapLoc.a(bw.a());
            arrayList.clear();
        }
        if (!bw.a(amapLoc)) {
            return null;
        }
        amapLoc.f(UriUtil.LOCAL_FILE_SCHEME);
        return amapLoc;
    }

    public static boolean a(Context context, String str, String str2, int i, int i2, boolean z, boolean z2) {
        int i3;
        if (!a(context, str, i, false, z)) {
            return false;
        }
        if (i2 == 0) {
            return a(context, str, str2, i, z2);
        }
        if (i2 == 1) {
            i3 = 8;
        } else {
            i3 = 24;
        }
        String[] a2 = a(0.0d, 0.0d, str2);
        for (int i4 = 1; i4 < i3; i4++) {
            a(context, str, a2[i4], i, z2);
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:102:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:?, code lost:
        r2.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:?, code lost:
        r2.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:?, code lost:
        r2.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:?, code lost:
        r2.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:?, code lost:
        r2.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:0x0279, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:196:0x028a, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:205:0x029f, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:214:0x02b7, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:225:0x02d6, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x010f, code lost:
        r3 = r5;
        r0 = false;
        r1 = r2;
        r2 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:?, code lost:
        r1.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x019e, code lost:
        r1 = r2;
        r3 = r5;
        r2 = r4;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x01b4 A[SYNTHETIC, Splitter:B:101:0x01b4] */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x01b9 A[SYNTHETIC, Splitter:B:104:0x01b9] */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x01be A[SYNTHETIC, Splitter:B:107:0x01be] */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x01ec A[SYNTHETIC, Splitter:B:121:0x01ec] */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x01f1 A[SYNTHETIC, Splitter:B:124:0x01f1] */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x01f6 A[SYNTHETIC, Splitter:B:127:0x01f6] */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x0205 A[SYNTHETIC, Splitter:B:133:0x0205] */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x020a A[SYNTHETIC, Splitter:B:136:0x020a] */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x020f A[SYNTHETIC, Splitter:B:139:0x020f] */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x021e A[SYNTHETIC, Splitter:B:145:0x021e] */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x0223 A[SYNTHETIC, Splitter:B:148:0x0223] */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x0228 A[SYNTHETIC, Splitter:B:151:0x0228] */
    /* JADX WARNING: Removed duplicated region for block: B:157:0x0237 A[SYNTHETIC, Splitter:B:157:0x0237] */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x023c A[SYNTHETIC, Splitter:B:160:0x023c] */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x0241 A[SYNTHETIC, Splitter:B:163:0x0241] */
    /* JADX WARNING: Removed duplicated region for block: B:188:0x0279 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:37:0x00cc] */
    /* JADX WARNING: Removed duplicated region for block: B:206:0x02a2 A[ExcHandler: EOFException (e java.io.EOFException), Splitter:B:71:0x0139] */
    /* JADX WARNING: Removed duplicated region for block: B:215:0x02ba A[ExcHandler: SocketTimeoutException (e java.net.SocketTimeoutException), Splitter:B:71:0x0139] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0115 A[SYNTHETIC, Splitter:B:55:0x0115] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x011a A[SYNTHETIC, Splitter:B:58:0x011a] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x011f A[SYNTHETIC, Splitter:B:61:0x011f] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x012a  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x019d A[Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }, ExcHandler: UnknownHostException (e java.net.UnknownHostException), Splitter:B:71:0x0139] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x01b1 A[ExcHandler: SocketException (e java.net.SocketException), Splitter:B:71:0x0139] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(android.content.Context r11, java.lang.String r12, java.lang.String r13, int r14, boolean r15) {
        /*
            r0 = 2
            java.lang.String[] r6 = new java.lang.String[r0]
            boolean r0 = a((java.lang.String) r12, (java.lang.String) r13, (int) r14, (java.lang.String[]) r6)
            if (r0 != 0) goto L_0x000b
            r1 = 0
        L_0x000a:
            return r1
        L_0x000b:
            java.util.Hashtable<java.lang.String, java.lang.Long> r0 = d
            r1 = 1
            r1 = r6[r1]
            boolean r0 = r0.containsKey(r1)
            if (r0 == 0) goto L_0x003c
            java.util.Hashtable<java.lang.String, java.lang.Long> r0 = d
            r1 = 1
            r1 = r6[r1]
            java.lang.Object r0 = r0.get(r1)
            java.lang.Long r0 = (java.lang.Long) r0
            long r0 = r0.longValue()
            long r2 = com.loc.bw.a()
            long r0 = r2 - r0
            r2 = 86400000(0x5265c00, double:4.2687272E-316)
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 >= 0) goto L_0x0034
            r1 = 0
            goto L_0x000a
        L_0x0034:
            java.util.Hashtable<java.lang.String, java.lang.Long> r0 = d
            r1 = 1
            r1 = r6[r1]
            r0.remove(r1)
        L_0x003c:
            r1 = 0
            r4 = 0
            r3 = 0
            r0 = 0
            com.loc.bw.b()     // Catch:{ UnknownHostException -> 0x02df, SocketException -> 0x02c3, SocketTimeoutException -> 0x01e5, EOFException -> 0x01fe, Exception -> 0x0217, all -> 0x0230 }
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ UnknownHostException -> 0x02df, SocketException -> 0x02c3, SocketTimeoutException -> 0x01e5, EOFException -> 0x01fe, Exception -> 0x0217, all -> 0x0230 }
            r2.<init>()     // Catch:{ UnknownHostException -> 0x02df, SocketException -> 0x02c3, SocketTimeoutException -> 0x01e5, EOFException -> 0x01fe, Exception -> 0x0217, all -> 0x0230 }
            java.lang.String r5 = "v"
            r7 = 1065353216(0x3f800000, float:1.0)
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ UnknownHostException -> 0x02df, SocketException -> 0x02c3, SocketTimeoutException -> 0x01e5, EOFException -> 0x01fe, Exception -> 0x0217, all -> 0x0230 }
            r2.put(r5, r7)     // Catch:{ UnknownHostException -> 0x02df, SocketException -> 0x02c3, SocketTimeoutException -> 0x01e5, EOFException -> 0x01fe, Exception -> 0x0217, all -> 0x0230 }
            com.loc.bp r5 = com.loc.bp.a((android.content.Context) r11)     // Catch:{ UnknownHostException -> 0x02df, SocketException -> 0x02c3, SocketTimeoutException -> 0x01e5, EOFException -> 0x01fe, Exception -> 0x0217, all -> 0x0230 }
            java.lang.String r7 = "https://offline.aps.amap.com/LoadOfflineData/getData"
            r8 = 0
            r8 = r6[r8]     // Catch:{ UnknownHostException -> 0x02df, SocketException -> 0x02c3, SocketTimeoutException -> 0x01e5, EOFException -> 0x01fe, Exception -> 0x0217, all -> 0x0230 }
            java.lang.String r9 = "UTF-8"
            byte[] r8 = r8.getBytes(r9)     // Catch:{ UnknownHostException -> 0x02df, SocketException -> 0x02c3, SocketTimeoutException -> 0x01e5, EOFException -> 0x01fe, Exception -> 0x0217, all -> 0x0230 }
            java.net.HttpURLConnection r2 = r5.a((android.content.Context) r11, (java.lang.String) r7, (java.util.HashMap<java.lang.String, java.lang.String>) r2, (byte[]) r8)     // Catch:{ UnknownHostException -> 0x02df, SocketException -> 0x02c3, SocketTimeoutException -> 0x01e5, EOFException -> 0x01fe, Exception -> 0x0217, all -> 0x0230 }
            if (r2 != 0) goto L_0x007b
            if (r3 == 0) goto L_0x006e
            r3.close()     // Catch:{ Exception -> 0x0245 }
        L_0x006e:
            if (r4 == 0) goto L_0x0073
            r4.close()     // Catch:{ Exception -> 0x0248 }
        L_0x0073:
            if (r2 == 0) goto L_0x000a
            r2.disconnect()     // Catch:{ Exception -> 0x0079 }
            goto L_0x000a
        L_0x0079:
            r0 = move-exception
            goto L_0x000a
        L_0x007b:
            int r0 = r2.getResponseCode()     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            r5 = 200(0xc8, float:2.8E-43)
            if (r0 != r5) goto L_0x01de
            r5 = 0
            java.util.Map r0 = r2.getHeaderFields()     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            java.util.Set r0 = r0.entrySet()     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            java.util.Iterator r7 = r0.iterator()     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
        L_0x0090:
            boolean r0 = r7.hasNext()     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            if (r0 == 0) goto L_0x0300
            java.lang.Object r0 = r7.next()     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            java.lang.String r8 = "code"
            java.lang.Object r9 = r0.getKey()     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            boolean r8 = r8.equals(r9)     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            if (r8 == 0) goto L_0x0090
            java.lang.Object r0 = r0.getValue()     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            java.util.List r0 = (java.util.List) r0     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            r5 = 0
            java.lang.Object r0 = r0.get(r5)     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
        L_0x00b9:
            r5 = 260(0x104, float:3.64E-43)
            if (r0 != r5) goto L_0x01c6
            r0 = 1
            r0 = r6[r0]     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            c = r0     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            r0 = 1
            java.io.InputStream r5 = r2.getInputStream()     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            java.util.zip.GZIPInputStream r4 = new java.util.zip.GZIPInputStream     // Catch:{ UnknownHostException -> 0x02ee, SocketException -> 0x02d0, SocketTimeoutException -> 0x02b1, EOFException -> 0x0299, Exception -> 0x0285, all -> 0x0276 }
            r4.<init>(r5)     // Catch:{ UnknownHostException -> 0x02ee, SocketException -> 0x02d0, SocketTimeoutException -> 0x02b1, EOFException -> 0x0299, Exception -> 0x0285, all -> 0x0276 }
            java.io.File r3 = new java.io.File     // Catch:{ UnknownHostException -> 0x010e, SocketException -> 0x02d5, SocketTimeoutException -> 0x02b6, EOFException -> 0x029e, Exception -> 0x0289, all -> 0x0279 }
            r7 = 1
            r7 = r6[r7]     // Catch:{ UnknownHostException -> 0x010e, SocketException -> 0x02d5, SocketTimeoutException -> 0x02b6, EOFException -> 0x029e, Exception -> 0x0289, all -> 0x0279 }
            r3.<init>(r7)     // Catch:{ UnknownHostException -> 0x010e, SocketException -> 0x02d5, SocketTimeoutException -> 0x02b6, EOFException -> 0x029e, Exception -> 0x0289, all -> 0x0279 }
            boolean r7 = r3.exists()     // Catch:{ UnknownHostException -> 0x010e, SocketException -> 0x02d5, SocketTimeoutException -> 0x02b6, EOFException -> 0x029e, Exception -> 0x0289, all -> 0x0279 }
            if (r7 == 0) goto L_0x00e0
            boolean r7 = r3.delete()     // Catch:{ UnknownHostException -> 0x010e, SocketException -> 0x02d5, SocketTimeoutException -> 0x02b6, EOFException -> 0x029e, Exception -> 0x0289, all -> 0x0279 }
            if (r7 == 0) goto L_0x0130
        L_0x00e0:
            if (r0 == 0) goto L_0x02fd
            boolean r0 = com.loc.e.n     // Catch:{ UnknownHostException -> 0x010e, SocketException -> 0x02d5, SocketTimeoutException -> 0x02b6, EOFException -> 0x029e, Exception -> 0x0289, all -> 0x0279 }
            if (r0 == 0) goto L_0x02fd
            java.io.File r0 = r3.getParentFile()     // Catch:{ UnknownHostException -> 0x010e, SocketException -> 0x02d5, SocketTimeoutException -> 0x02b6, EOFException -> 0x029e, Exception -> 0x0289, all -> 0x0279 }
            boolean r7 = r0.exists()     // Catch:{ UnknownHostException -> 0x010e, SocketException -> 0x02d5, SocketTimeoutException -> 0x02b6, EOFException -> 0x029e, Exception -> 0x0289, all -> 0x0279 }
            if (r7 != 0) goto L_0x00f3
            r0.mkdirs()     // Catch:{ UnknownHostException -> 0x010e, SocketException -> 0x02d5, SocketTimeoutException -> 0x02b6, EOFException -> 0x029e, Exception -> 0x0289, all -> 0x0279 }
        L_0x00f3:
            r0 = 2048(0x800, float:2.87E-42)
            java.io.FileOutputStream r7 = new java.io.FileOutputStream     // Catch:{ UnknownHostException -> 0x010e, SocketException -> 0x02d5, SocketTimeoutException -> 0x02b6, EOFException -> 0x029e, Exception -> 0x0289, all -> 0x0279 }
            r7.<init>(r3)     // Catch:{ UnknownHostException -> 0x010e, SocketException -> 0x02d5, SocketTimeoutException -> 0x02b6, EOFException -> 0x029e, Exception -> 0x0289, all -> 0x0279 }
            java.io.BufferedOutputStream r3 = new java.io.BufferedOutputStream     // Catch:{ UnknownHostException -> 0x010e, SocketException -> 0x02d5, SocketTimeoutException -> 0x02b6, EOFException -> 0x029e, Exception -> 0x0289, all -> 0x0279 }
            r3.<init>(r7, r0)     // Catch:{ UnknownHostException -> 0x010e, SocketException -> 0x02d5, SocketTimeoutException -> 0x02b6, EOFException -> 0x029e, Exception -> 0x0289, all -> 0x0279 }
            byte[] r7 = new byte[r0]     // Catch:{ UnknownHostException -> 0x010e, SocketException -> 0x02d5, SocketTimeoutException -> 0x02b6, EOFException -> 0x029e, Exception -> 0x0289, all -> 0x0279 }
        L_0x0101:
            r8 = 0
            int r8 = r4.read(r7, r8, r0)     // Catch:{ UnknownHostException -> 0x010e, SocketException -> 0x02d5, SocketTimeoutException -> 0x02b6, EOFException -> 0x029e, Exception -> 0x0289, all -> 0x0279 }
            r9 = -1
            if (r8 == r9) goto L_0x0132
            r9 = 0
            r3.write(r7, r9, r8)     // Catch:{ UnknownHostException -> 0x010e, SocketException -> 0x02d5, SocketTimeoutException -> 0x02b6, EOFException -> 0x029e, Exception -> 0x0289, all -> 0x0279 }
            goto L_0x0101
        L_0x010e:
            r0 = move-exception
            r3 = r5
            r0 = r1
            r1 = r2
            r2 = r4
        L_0x0113:
            if (r2 == 0) goto L_0x0118
            r2.close()     // Catch:{ Exception -> 0x0251 }
        L_0x0118:
            if (r3 == 0) goto L_0x011d
            r3.close()     // Catch:{ Exception -> 0x0254 }
        L_0x011d:
            if (r1 == 0) goto L_0x0122
            r1.disconnect()     // Catch:{ Exception -> 0x0257 }
        L_0x0122:
            java.lang.String r1 = c
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x012d
            r1 = 0
            c = r1
        L_0x012d:
            r1 = r0
            goto L_0x000a
        L_0x0130:
            r0 = 0
            goto L_0x00e0
        L_0x0132:
            r3.flush()     // Catch:{ UnknownHostException -> 0x010e, SocketException -> 0x02d5, SocketTimeoutException -> 0x02b6, EOFException -> 0x029e, Exception -> 0x0289, all -> 0x0279 }
            r3.close()     // Catch:{ UnknownHostException -> 0x010e, SocketException -> 0x02d5, SocketTimeoutException -> 0x02b6, EOFException -> 0x029e, Exception -> 0x0289, all -> 0x0279 }
            r0 = 1
            java.util.Hashtable<java.lang.String, java.lang.Long> r1 = d     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            r3 = 1
            r3 = r6[r3]     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            long r8 = com.loc.bw.a()     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            java.lang.Long r7 = java.lang.Long.valueOf(r8)     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            r1.put(r3, r7)     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            r8 = 0
            java.lang.String r1 = "yyyyMMdd"
            java.lang.String r1 = com.loc.bw.a((long) r8, (java.lang.String) r1)     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            int[] r3 = a     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            r7 = 0
            r3 = r3[r7]     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            boolean r3 = r1.equals(r3)     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            if (r3 == 0) goto L_0x018d
            int[] r1 = a     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            r3 = 1
            int[] r7 = a     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            r8 = 1
            r7 = r7[r8]     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            int r7 = r7 + 1
            r1[r3] = r7     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
        L_0x016d:
            r1 = r5
            r3 = r0
            r0 = r4
        L_0x0170:
            if (r15 == 0) goto L_0x0178
            r4 = 1
            r4 = r6[r4]     // Catch:{ UnknownHostException -> 0x02f5, SocketException -> 0x02d9, SocketTimeoutException -> 0x02bd, EOFException -> 0x02a5, Exception -> 0x028e, all -> 0x027b }
            b(r4)     // Catch:{ UnknownHostException -> 0x02f5, SocketException -> 0x02d9, SocketTimeoutException -> 0x02bd, EOFException -> 0x02a5, Exception -> 0x028e, all -> 0x027b }
        L_0x0178:
            r10 = r0
            r0 = r3
            r3 = r10
        L_0x017b:
            if (r3 == 0) goto L_0x0180
            r3.close()     // Catch:{ Exception -> 0x024b }
        L_0x0180:
            if (r1 == 0) goto L_0x0185
            r1.close()     // Catch:{ Exception -> 0x024e }
        L_0x0185:
            if (r2 == 0) goto L_0x0122
            r2.disconnect()     // Catch:{ Exception -> 0x018b }
            goto L_0x0122
        L_0x018b:
            r1 = move-exception
            goto L_0x0122
        L_0x018d:
            int[] r3 = a     // Catch:{ Exception -> 0x01a3, UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, all -> 0x0279 }
            r7 = 0
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ Exception -> 0x01a3, UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, all -> 0x0279 }
            r3[r7] = r1     // Catch:{ Exception -> 0x01a3, UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, all -> 0x0279 }
        L_0x0196:
            int[] r1 = a     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            r3 = 1
            r7 = 1
            r1[r3] = r7     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            goto L_0x016d
        L_0x019d:
            r1 = move-exception
            r1 = r2
            r3 = r5
            r2 = r4
            goto L_0x0113
        L_0x01a3:
            r1 = move-exception
            int[] r1 = a     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            r3 = 0
            r7 = 0
            r1[r3] = r7     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            int[] r1 = a     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            r3 = 1
            r7 = 0
            r1[r3] = r7     // Catch:{ UnknownHostException -> 0x019d, SocketException -> 0x01b1, SocketTimeoutException -> 0x02ba, EOFException -> 0x02a2, Exception -> 0x028c, all -> 0x0279 }
            goto L_0x0196
        L_0x01b1:
            r1 = move-exception
        L_0x01b2:
            if (r4 == 0) goto L_0x01b7
            r4.close()     // Catch:{ Exception -> 0x025a }
        L_0x01b7:
            if (r5 == 0) goto L_0x01bc
            r5.close()     // Catch:{ Exception -> 0x025d }
        L_0x01bc:
            if (r2 == 0) goto L_0x0122
            r2.disconnect()     // Catch:{ Exception -> 0x01c3 }
            goto L_0x0122
        L_0x01c3:
            r1 = move-exception
            goto L_0x0122
        L_0x01c6:
            boolean r0 = com.loc.e.n     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            if (r0 == 0) goto L_0x01da
            java.util.Hashtable<java.lang.String, java.lang.Long> r0 = d     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            r5 = 1
            r5 = r6[r5]     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            long r8 = com.loc.bw.a()     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            java.lang.Long r7 = java.lang.Long.valueOf(r8)     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
            r0.put(r5, r7)     // Catch:{ UnknownHostException -> 0x02e7, SocketException -> 0x02ca, SocketTimeoutException -> 0x02ab, EOFException -> 0x0293, Exception -> 0x0280, all -> 0x0272 }
        L_0x01da:
            r0 = r3
            r3 = r1
            r1 = r4
            goto L_0x0170
        L_0x01de:
            r5 = 404(0x194, float:5.66E-43)
            if (r0 != r5) goto L_0x01e2
        L_0x01e2:
            r0 = r1
            r1 = r4
            goto L_0x017b
        L_0x01e5:
            r2 = move-exception
            r2 = r0
            r5 = r4
            r4 = r3
            r0 = r1
        L_0x01ea:
            if (r4 == 0) goto L_0x01ef
            r4.close()     // Catch:{ Exception -> 0x0260 }
        L_0x01ef:
            if (r5 == 0) goto L_0x01f4
            r5.close()     // Catch:{ Exception -> 0x0262 }
        L_0x01f4:
            if (r2 == 0) goto L_0x0122
            r2.disconnect()     // Catch:{ Exception -> 0x01fb }
            goto L_0x0122
        L_0x01fb:
            r1 = move-exception
            goto L_0x0122
        L_0x01fe:
            r2 = move-exception
            r2 = r0
            r5 = r4
            r4 = r3
            r0 = r1
        L_0x0203:
            if (r4 == 0) goto L_0x0208
            r4.close()     // Catch:{ Exception -> 0x0264 }
        L_0x0208:
            if (r5 == 0) goto L_0x020d
            r5.close()     // Catch:{ Exception -> 0x0266 }
        L_0x020d:
            if (r2 == 0) goto L_0x0122
            r2.disconnect()     // Catch:{ Exception -> 0x0214 }
            goto L_0x0122
        L_0x0214:
            r1 = move-exception
            goto L_0x0122
        L_0x0217:
            r2 = move-exception
            r2 = r0
            r5 = r4
            r4 = r3
            r0 = r1
        L_0x021c:
            if (r4 == 0) goto L_0x0221
            r4.close()     // Catch:{ Exception -> 0x0268 }
        L_0x0221:
            if (r5 == 0) goto L_0x0226
            r5.close()     // Catch:{ Exception -> 0x026a }
        L_0x0226:
            if (r2 == 0) goto L_0x0122
            r2.disconnect()     // Catch:{ Exception -> 0x022d }
            goto L_0x0122
        L_0x022d:
            r1 = move-exception
            goto L_0x0122
        L_0x0230:
            r1 = move-exception
            r2 = r0
            r5 = r4
            r4 = r3
            r0 = r1
        L_0x0235:
            if (r4 == 0) goto L_0x023a
            r4.close()     // Catch:{ Exception -> 0x026c }
        L_0x023a:
            if (r5 == 0) goto L_0x023f
            r5.close()     // Catch:{ Exception -> 0x026e }
        L_0x023f:
            if (r2 == 0) goto L_0x0244
            r2.disconnect()     // Catch:{ Exception -> 0x0270 }
        L_0x0244:
            throw r0
        L_0x0245:
            r0 = move-exception
            goto L_0x006e
        L_0x0248:
            r0 = move-exception
            goto L_0x0073
        L_0x024b:
            r3 = move-exception
            goto L_0x0180
        L_0x024e:
            r1 = move-exception
            goto L_0x0185
        L_0x0251:
            r2 = move-exception
            goto L_0x0118
        L_0x0254:
            r2 = move-exception
            goto L_0x011d
        L_0x0257:
            r1 = move-exception
            goto L_0x0122
        L_0x025a:
            r1 = move-exception
            goto L_0x01b7
        L_0x025d:
            r1 = move-exception
            goto L_0x01bc
        L_0x0260:
            r1 = move-exception
            goto L_0x01ef
        L_0x0262:
            r1 = move-exception
            goto L_0x01f4
        L_0x0264:
            r1 = move-exception
            goto L_0x0208
        L_0x0266:
            r1 = move-exception
            goto L_0x020d
        L_0x0268:
            r1 = move-exception
            goto L_0x0221
        L_0x026a:
            r1 = move-exception
            goto L_0x0226
        L_0x026c:
            r1 = move-exception
            goto L_0x023a
        L_0x026e:
            r1 = move-exception
            goto L_0x023f
        L_0x0270:
            r1 = move-exception
            goto L_0x0244
        L_0x0272:
            r0 = move-exception
            r5 = r4
            r4 = r3
            goto L_0x0235
        L_0x0276:
            r0 = move-exception
            r4 = r3
            goto L_0x0235
        L_0x0279:
            r0 = move-exception
            goto L_0x0235
        L_0x027b:
            r3 = move-exception
            r4 = r0
            r5 = r1
            r0 = r3
            goto L_0x0235
        L_0x0280:
            r0 = move-exception
            r5 = r4
            r0 = r1
            r4 = r3
            goto L_0x021c
        L_0x0285:
            r0 = move-exception
            r4 = r3
            r0 = r1
            goto L_0x021c
        L_0x0289:
            r0 = move-exception
            r0 = r1
            goto L_0x021c
        L_0x028c:
            r1 = move-exception
            goto L_0x021c
        L_0x028e:
            r4 = move-exception
            r4 = r0
            r5 = r1
            r0 = r3
            goto L_0x021c
        L_0x0293:
            r0 = move-exception
            r5 = r4
            r0 = r1
            r4 = r3
            goto L_0x0203
        L_0x0299:
            r0 = move-exception
            r4 = r3
            r0 = r1
            goto L_0x0203
        L_0x029e:
            r0 = move-exception
            r0 = r1
            goto L_0x0203
        L_0x02a2:
            r1 = move-exception
            goto L_0x0203
        L_0x02a5:
            r4 = move-exception
            r4 = r0
            r5 = r1
            r0 = r3
            goto L_0x0203
        L_0x02ab:
            r0 = move-exception
            r5 = r4
            r0 = r1
            r4 = r3
            goto L_0x01ea
        L_0x02b1:
            r0 = move-exception
            r4 = r3
            r0 = r1
            goto L_0x01ea
        L_0x02b6:
            r0 = move-exception
            r0 = r1
            goto L_0x01ea
        L_0x02ba:
            r1 = move-exception
            goto L_0x01ea
        L_0x02bd:
            r4 = move-exception
            r4 = r0
            r5 = r1
            r0 = r3
            goto L_0x01ea
        L_0x02c3:
            r2 = move-exception
            r2 = r0
            r5 = r4
            r4 = r3
            r0 = r1
            goto L_0x01b2
        L_0x02ca:
            r0 = move-exception
            r5 = r4
            r0 = r1
            r4 = r3
            goto L_0x01b2
        L_0x02d0:
            r0 = move-exception
            r4 = r3
            r0 = r1
            goto L_0x01b2
        L_0x02d5:
            r0 = move-exception
            r0 = r1
            goto L_0x01b2
        L_0x02d9:
            r4 = move-exception
            r4 = r0
            r5 = r1
            r0 = r3
            goto L_0x01b2
        L_0x02df:
            r2 = move-exception
            r2 = r3
            r3 = r4
            r10 = r0
            r0 = r1
            r1 = r10
            goto L_0x0113
        L_0x02e7:
            r0 = move-exception
            r0 = r1
            r1 = r2
            r2 = r3
            r3 = r4
            goto L_0x0113
        L_0x02ee:
            r0 = move-exception
            r0 = r1
            r1 = r2
            r2 = r3
            r3 = r5
            goto L_0x0113
        L_0x02f5:
            r4 = move-exception
            r10 = r2
            r2 = r0
            r0 = r3
            r3 = r1
            r1 = r10
            goto L_0x0113
        L_0x02fd:
            r0 = r1
            goto L_0x016d
        L_0x0300:
            r0 = r5
            goto L_0x00b9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bd.a(android.content.Context, java.lang.String, java.lang.String, int, boolean):boolean");
    }

    public static void a() {
        e.a();
        d.clear();
        f.clear();
        a[0] = 0;
        a[1] = 0;
    }

    private static double[] a(int i, bi biVar, String str, int i2) {
        String str2;
        int[] iArr;
        if (i2 == 0) {
            String[] split = str.split("\\|");
            iArr = new int[split.length];
            for (int i3 = 0; i3 < split.length; i3++) {
                iArr[i3] = Integer.parseInt(split[i3]);
            }
            if (split.length == 2) {
                str2 = "gsm";
            } else {
                str2 = "cdma";
            }
        } else {
            str2 = "wifi";
            iArr = null;
        }
        try {
            if (biVar.g() > ((long) i)) {
                biVar.a((long) i);
                int a2 = a(i, biVar, str, iArr, i, ((int) biVar.g()) - 16, str2, 0);
                if (a2 != -1) {
                    biVar.a((long) (a2 + 6));
                    double[] dArr = new double[3];
                    try {
                        dArr[0] = ((double) a(biVar.e())) / 1000000.0d;
                        dArr[1] = ((double) a(biVar.e())) / 1000000.0d;
                        dArr[2] = (double) biVar.d();
                        if (!bw.a(dArr[1])) {
                            return null;
                        }
                        if (!bw.b(dArr[0])) {
                            return null;
                        }
                        return dArr;
                    } catch (Throwable th) {
                        return dArr;
                    }
                }
            }
            return null;
        } catch (Throwable th2) {
            return null;
        }
    }

    private static int a(int i, bi biVar, String str, int[] iArr, int i2, int i3, String str2, int i4) {
        int i5 = i4 + 1;
        if (i5 > 25) {
            return -1;
        }
        int i6 = (((((i2 + i3) / 2) - i) / 16) * 16) + i;
        int a2 = a(biVar, str, iArr, i6, str2);
        if (i2 == i6 && i6 == i3) {
            if (a2 != 0) {
                i2 = -1;
            }
            return i2;
        } else if (a2 == Integer.MAX_VALUE) {
            return -1;
        } else {
            if (a2 == 0) {
                return i6;
            }
            if (a2 < 0) {
                return a(i, biVar, str, iArr, i2, i6, str2, i5);
            }
            return a(i, biVar, str, iArr, i6 + 16, i3, str2, i5);
        }
    }

    private static int a(bi biVar, String str, int[] iArr, int i, String str2) {
        try {
            biVar.a((long) i);
            if (str2.equals("gsm")) {
                int i2 = iArr[0];
                int i3 = iArr[1];
                int d2 = biVar.d();
                int e2 = biVar.e();
                if (i2 < d2) {
                    return -1;
                }
                if (i2 > d2) {
                    return 1;
                }
                if (i3 < e2) {
                    return -1;
                }
                if (i3 > e2) {
                    return 1;
                }
                return 0;
            } else if (str2.equals("cdma")) {
                int[] iArr2 = {iArr[0], iArr[1], iArr[2]};
                int[] iArr3 = new int[3];
                for (int i4 = 0; i4 < 3; i4++) {
                    iArr3[i4] = biVar.d();
                    if (iArr2[i4] < iArr3[i4]) {
                        return -1;
                    }
                    if (iArr2[i4] > iArr3[i4]) {
                        return 1;
                    }
                }
                return 0;
            } else {
                if (str2.equals("wifi")) {
                    byte[] b2 = bw.b(str);
                    int[] iArr4 = new int[6];
                    for (int i5 = 0; i5 < 6; i5++) {
                        iArr4[i5] = b2[i5] < 0 ? b2[i5] + 256 : b2[i5];
                    }
                    int[] iArr5 = new int[6];
                    for (int i6 = 0; i6 < 6; i6++) {
                        iArr5[i6] = biVar.f();
                        if (iArr4[i6] < iArr5[i6]) {
                            return -1;
                        }
                        if (iArr4[i6] > iArr5[i6]) {
                            return 1;
                        }
                    }
                    return 0;
                }
                return ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            }
        } catch (Exception e3) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0092 A[SYNTHETIC, Splitter:B:30:0x0092] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x009c A[SYNTHETIC, Splitter:B:36:0x009c] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00a7 A[SYNTHETIC, Splitter:B:41:0x00a7] */
    /* JADX WARNING: Removed duplicated region for block: B:56:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:57:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void b(java.lang.String r8) {
        /*
            r2 = 0
            java.util.Hashtable<java.lang.String, java.lang.String> r0 = f
            boolean r0 = r0.containsKey(r8)
            if (r0 == 0) goto L_0x0018
            java.util.Hashtable<java.lang.String, java.lang.String> r0 = f
            java.lang.Object r0 = r0.get(r8)
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0018
        L_0x0017:
            return
        L_0x0018:
            java.io.File r3 = new java.io.File
            r3.<init>(r8)
            boolean r0 = r3.exists()
            if (r0 == 0) goto L_0x0017
            boolean r0 = r3.isFile()
            if (r0 == 0) goto L_0x0017
            r1 = 0
            java.io.RandomAccessFile r0 = new java.io.RandomAccessFile     // Catch:{ FileNotFoundException -> 0x008e, Exception -> 0x0098, all -> 0x00a4 }
            java.lang.String r4 = "r"
            r0.<init>(r3, r4)     // Catch:{ FileNotFoundException -> 0x008e, Exception -> 0x0098, all -> 0x00a4 }
            r4 = 8
            r0.seek(r4)     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
            int r3 = r0.readUnsignedShort()     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
            r4.<init>()     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
            r1 = r2
        L_0x0040:
            if (r1 >= r3) goto L_0x0075
            int r2 = r0.readInt()     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
            r5.<init>()     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
            java.lang.String r6 = ","
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
            java.lang.StringBuilder r5 = r5.append(r2)     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
            java.lang.String r5 = r5.toString()     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
            int r5 = r4.indexOf(r5)     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
            r6 = -1
            if (r5 != r6) goto L_0x0069
            java.lang.String r5 = ","
            java.lang.StringBuilder r5 = r4.append(r5)     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
            r5.append(r2)     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
        L_0x0069:
            int r2 = r3 + -1
            if (r1 != r2) goto L_0x0072
            java.lang.String r2 = ","
            r4.append(r2)     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
        L_0x0072:
            int r1 = r1 + 1
            goto L_0x0040
        L_0x0075:
            java.util.Hashtable<java.lang.String, java.lang.String> r1 = f     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
            java.lang.String r2 = r4.toString()     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
            r1.put(r8, r2)     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
            r1 = 0
            int r2 = r4.length()     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
            r4.delete(r1, r2)     // Catch:{ FileNotFoundException -> 0x00b4, Exception -> 0x00b2, all -> 0x00ad }
            if (r0 == 0) goto L_0x0017
            r0.close()     // Catch:{ Exception -> 0x008c }
            goto L_0x0017
        L_0x008c:
            r0 = move-exception
            goto L_0x0017
        L_0x008e:
            r0 = move-exception
            r0 = r1
        L_0x0090:
            if (r0 == 0) goto L_0x0017
            r0.close()     // Catch:{ Exception -> 0x0096 }
            goto L_0x0017
        L_0x0096:
            r0 = move-exception
            goto L_0x0017
        L_0x0098:
            r0 = move-exception
            r0 = r1
        L_0x009a:
            if (r0 == 0) goto L_0x0017
            r0.close()     // Catch:{ Exception -> 0x00a1 }
            goto L_0x0017
        L_0x00a1:
            r0 = move-exception
            goto L_0x0017
        L_0x00a4:
            r0 = move-exception
        L_0x00a5:
            if (r1 == 0) goto L_0x00aa
            r1.close()     // Catch:{ Exception -> 0x00ab }
        L_0x00aa:
            throw r0
        L_0x00ab:
            r1 = move-exception
            goto L_0x00aa
        L_0x00ad:
            r1 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
            goto L_0x00a5
        L_0x00b2:
            r1 = move-exception
            goto L_0x009a
        L_0x00b4:
            r1 = move-exception
            goto L_0x0090
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bd.b(java.lang.String):void");
    }

    public static ArrayList<String> a(String str, boolean z) {
        ArrayList<String> arrayList;
        ArrayList<String> arrayList2 = null;
        if (f.isEmpty()) {
            return null;
        }
        int a2 = a(str);
        String[] split = str.split("#");
        switch (a2) {
            case 1:
            case 2:
                for (String next : f.keySet()) {
                    if (f.get(next).contains(ListUtils.DEFAULT_JOIN_SEPARATOR + split[3] + ListUtils.DEFAULT_JOIN_SEPARATOR)) {
                        if (arrayList2 == null) {
                            arrayList = new ArrayList<>();
                        } else {
                            arrayList = arrayList2;
                        }
                        arrayList.add(next);
                        if (z) {
                            return arrayList;
                        }
                    } else {
                        arrayList = arrayList2;
                    }
                    arrayList2 = arrayList;
                }
                return arrayList2;
            default:
                return null;
        }
    }

    public static boolean b() {
        return !f.isEmpty();
    }

    public static boolean a(String str, String str2, int i, int i2) {
        int i3;
        int i4 = 0;
        if (TextUtils.isEmpty(str2)) {
            return false;
        }
        if (i2 == 0) {
            String a2 = a(str, str2, i);
            if (a2 != null) {
                File file = new File(a2);
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
                if (f.containsKey(a2)) {
                    f.remove(a2);
                }
                if (d.containsKey(a2)) {
                    d.remove(a2);
                }
            }
            return true;
        } else if (i2 != 1 && i2 != 2) {
            return false;
        } else {
            String[] a3 = a(0.0d, 0.0d, str2);
            if (i2 == 1) {
                i3 = 9;
            } else if (i2 == 2) {
                i3 = 25;
            } else {
                i3 = 0;
            }
            if (i != 1) {
                if (i != 2) {
                    return false;
                }
                i4 = 25;
            }
            Hashtable<String, String> hashtable = f;
            Hashtable<String, Long> hashtable2 = d;
            while (i4 < i3) {
                String a4 = a(str, a3[i4], i);
                if (a4 != null) {
                    File file2 = new File(a4);
                    if (file2.exists() && file2.isFile()) {
                        file2.delete();
                    }
                    if (hashtable.containsKey(a4)) {
                        hashtable.remove(a4);
                    }
                    if (hashtable2.containsKey(a4)) {
                        hashtable2.remove(a4);
                    }
                }
                i4++;
            }
            return true;
        }
    }

    private static String a(String str, String str2, int i) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        StringBuilder c2 = c();
        switch (i) {
            case 1:
                c2.append(a(a(str), str)).append(File.separator);
                if (str2.startsWith("-")) {
                    c2.append(str2.substring(0, 4));
                } else {
                    c2.append(str2.substring(0, 3));
                }
                c2.append(ListUtils.DEFAULT_JOIN_SEPARATOR);
                int indexOf = str2.indexOf(ListUtils.DEFAULT_JOIN_SEPARATOR) + 1;
                if (str2.substring(indexOf, indexOf + 1).startsWith("-")) {
                    c2.append(str2.substring(indexOf, indexOf + 4));
                } else {
                    c2.append(str2.substring(indexOf, indexOf + 3));
                }
                c2.append(File.separator).append(str2);
                break;
            case 2:
                c2.append("wifi").append(File.separator);
                c2.append(str2.substring(0, 3)).append(ListUtils.DEFAULT_JOIN_SEPARATOR);
                int indexOf2 = str2.indexOf(ListUtils.DEFAULT_JOIN_SEPARATOR) + 1;
                c2.append(str2.substring(indexOf2, indexOf2 + 3));
                c2.append(File.separator).append(str2);
                break;
            default:
                return null;
        }
        return c2.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x014a A[SYNTHETIC, Splitter:B:47:0x014a] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0155 A[SYNTHETIC, Splitter:B:52:0x0155] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(java.lang.String r9, java.lang.String r10, int r11, java.lang.String[] r12) {
        /*
            r2 = 0
            r8 = 3
            r4 = 1
            r1 = 0
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            if (r0 != 0) goto L_0x0011
            boolean r0 = android.text.TextUtils.isEmpty(r10)
            if (r0 == 0) goto L_0x0013
        L_0x0011:
            r0 = r1
        L_0x0012:
            return r0
        L_0x0013:
            if (r12 == 0) goto L_0x0019
            int r0 = r12.length
            r5 = 2
            if (r0 == r5) goto L_0x001b
        L_0x0019:
            r0 = r1
            goto L_0x0012
        L_0x001b:
            java.lang.StringBuilder r5 = c()
            switch(r11) {
                case 1: goto L_0x0024;
                case 2: goto L_0x0106;
                default: goto L_0x0022;
            }
        L_0x0022:
            r0 = r1
            goto L_0x0012
        L_0x0024:
            int r0 = a((java.lang.String) r9)
            java.lang.String r0 = a((int) r0, (java.lang.String) r9)
            java.lang.StringBuilder r6 = r5.append(r0)
            java.lang.String r7 = java.io.File.separator
            r6.append(r7)
            java.lang.String r6 = "-"
            boolean r6 = r10.startsWith(r6)
            if (r6 == 0) goto L_0x00f2
            r6 = 4
            java.lang.String r6 = r10.substring(r1, r6)
            r5.append(r6)
        L_0x0045:
            java.lang.String r6 = ","
            r5.append(r6)
            java.lang.String r6 = ","
            int r6 = r10.indexOf(r6)
            int r6 = r6 + 1
            int r7 = r6 + 1
            java.lang.String r7 = r10.substring(r6, r7)
            java.lang.String r8 = "-"
            boolean r7 = r7.startsWith(r8)
            if (r7 == 0) goto L_0x00fb
            int r7 = r6 + 4
            java.lang.String r6 = r10.substring(r6, r7)
            r5.append(r6)
        L_0x0069:
            java.lang.String r6 = java.io.File.separator
            java.lang.StringBuilder r6 = r5.append(r6)
            r6.append(r10)
        L_0x0072:
            java.lang.String r6 = r5.toString()
            r12[r4] = r6
            int r6 = r5.length()
            r5.delete(r1, r6)
            java.io.File r7 = new java.io.File
            r5 = r12[r4]
            r7.<init>(r5)
            boolean r5 = r7.exists()
            if (r5 == 0) goto L_0x00a8
            boolean r5 = r7.isFile()
            if (r5 == 0) goto L_0x00a8
            r6 = 0
            java.io.RandomAccessFile r5 = new java.io.RandomAccessFile     // Catch:{ FileNotFoundException -> 0x013b, Exception -> 0x0147, all -> 0x0152 }
            java.lang.String r8 = "r"
            r5.<init>(r7, r8)     // Catch:{ FileNotFoundException -> 0x013b, Exception -> 0x0147, all -> 0x0152 }
            r6 = 0
            r5.seek(r6)     // Catch:{ FileNotFoundException -> 0x0166, Exception -> 0x0163, all -> 0x0160 }
            long r2 = r5.readLong()     // Catch:{ FileNotFoundException -> 0x0166, Exception -> 0x0163, all -> 0x0160 }
            if (r5 == 0) goto L_0x00a8
            r5.close()     // Catch:{ Exception -> 0x0159 }
        L_0x00a8:
            org.json.JSONObject r5 = new org.json.JSONObject
            r5.<init>()
            java.lang.String r6 = "v"
            r7 = 1065353216(0x3f800000, float:1.0)
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ Exception -> 0x015e }
            r5.put(r6, r7)     // Catch:{ Exception -> 0x015e }
            java.lang.String r6 = "geohash"
            r5.put(r6, r10)     // Catch:{ Exception -> 0x015e }
            java.lang.String r6 = "t"
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Exception -> 0x015e }
            r5.put(r6, r2)     // Catch:{ Exception -> 0x015e }
            java.lang.String r2 = "type"
            r5.put(r2, r0)     // Catch:{ Exception -> 0x015e }
            java.lang.String r0 = "imei"
            java.lang.String r2 = com.loc.e.b     // Catch:{ Exception -> 0x015e }
            r5.put(r0, r2)     // Catch:{ Exception -> 0x015e }
            java.lang.String r0 = "imsi"
            java.lang.String r2 = com.loc.e.c     // Catch:{ Exception -> 0x015e }
            r5.put(r0, r2)     // Catch:{ Exception -> 0x015e }
            java.lang.String r0 = "src"
            java.lang.String r2 = com.loc.e.e     // Catch:{ Exception -> 0x015e }
            r5.put(r0, r2)     // Catch:{ Exception -> 0x015e }
            java.lang.String r0 = "license"
            java.lang.String r2 = com.loc.e.f     // Catch:{ Exception -> 0x015e }
            r5.put(r0, r2)     // Catch:{ Exception -> 0x015e }
        L_0x00e9:
            java.lang.String r0 = r5.toString()
            r12[r1] = r0
            r0 = r4
            goto L_0x0012
        L_0x00f2:
            java.lang.String r6 = r10.substring(r1, r8)
            r5.append(r6)
            goto L_0x0045
        L_0x00fb:
            int r7 = r6 + 3
            java.lang.String r6 = r10.substring(r6, r7)
            r5.append(r6)
            goto L_0x0069
        L_0x0106:
            java.lang.String r0 = "wifi"
            java.lang.StringBuilder r6 = r5.append(r0)
            java.lang.String r7 = java.io.File.separator
            r6.append(r7)
            java.lang.String r6 = r10.substring(r1, r8)
            java.lang.StringBuilder r6 = r5.append(r6)
            java.lang.String r7 = ","
            r6.append(r7)
            java.lang.String r6 = ","
            int r6 = r10.indexOf(r6)
            int r6 = r6 + 1
            int r7 = r6 + 3
            java.lang.String r6 = r10.substring(r6, r7)
            r5.append(r6)
            java.lang.String r6 = java.io.File.separator
            java.lang.StringBuilder r6 = r5.append(r6)
            r6.append(r10)
            goto L_0x0072
        L_0x013b:
            r5 = move-exception
            r5 = r6
        L_0x013d:
            if (r5 == 0) goto L_0x00a8
            r5.close()     // Catch:{ Exception -> 0x0144 }
            goto L_0x00a8
        L_0x0144:
            r5 = move-exception
            goto L_0x00a8
        L_0x0147:
            r5 = move-exception
        L_0x0148:
            if (r6 == 0) goto L_0x00a8
            r6.close()     // Catch:{ Exception -> 0x014f }
            goto L_0x00a8
        L_0x014f:
            r5 = move-exception
            goto L_0x00a8
        L_0x0152:
            r0 = move-exception
        L_0x0153:
            if (r6 == 0) goto L_0x0158
            r6.close()     // Catch:{ Exception -> 0x015c }
        L_0x0158:
            throw r0
        L_0x0159:
            r5 = move-exception
            goto L_0x00a8
        L_0x015c:
            r1 = move-exception
            goto L_0x0158
        L_0x015e:
            r0 = move-exception
            goto L_0x00e9
        L_0x0160:
            r0 = move-exception
            r6 = r5
            goto L_0x0153
        L_0x0163:
            r6 = move-exception
            r6 = r5
            goto L_0x0148
        L_0x0166:
            r6 = move-exception
            goto L_0x013d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bd.a(java.lang.String, java.lang.String, int, java.lang.String[]):boolean");
    }

    private static boolean a(Context context, String str, int i, boolean z, boolean z2) {
        boolean z3;
        if (z) {
            z3 = i < 25;
        } else {
            z3 = i == 1;
        }
        if (!str.contains("cgi") && z3) {
            return false;
        }
        if ((!str.contains("wifi") && !z3) || a[1] > 2000) {
            return false;
        }
        NetworkInfo c2 = bw.c(context);
        if (bp.a(c2) == -1) {
            return false;
        }
        if (c2.getType() != 1 && z2) {
            return false;
        }
        if (c2.getType() != 1 && !z2 && g == null) {
            g = (TelephonyManager) bw.a(context, "phone");
        }
        return true;
    }

    private static StringBuilder c() {
        StringBuilder sb = new StringBuilder();
        sb.append(bw.e());
        sb.append("offline").append(File.separator);
        sb.append(bw.j()).append(File.separator).append("s").append(File.separator);
        return sb;
    }

    static int a(int i) {
        int[] iArr = new int[32];
        int i2 = 0;
        for (int i3 = 0; i3 < 4; i3++) {
            iArr[i3] = (i >> (i3 * 8)) & 255;
            iArr[i3] = ((iArr[i3] << 4) & 240) + ((iArr[i3] >> 4) & 15);
            i2 += (iArr[i3] & 255) << ((3 - i3) * 8);
        }
        return b + i2;
    }
}

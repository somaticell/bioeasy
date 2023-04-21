package com.lvrenyang.io;

import android.util.Log;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import com.alipay.android.phone.mrpc.core.RpcException;
import com.lvrenyang.io.base.IO;
import java.util.Random;

public class PrinterChecker {
    private static String TAG = "PrinterChecker";
    private static int nCheckFaildTimes = 0;
    private static int nMaxCheckFailedTimes = 30;

    public static int PTR_CheckEncrypt(IO io) {
        io.mMainLocker.lock();
        int result = -1;
        try {
            Random rmByte = new Random(System.currentTimeMillis());
            byte[] data = {31, 40, 99, 8, 0, 27, 64, -46, -45, -44, -43, 27, 64, 0, 0, 0, 0, 29, 114, 1};
            for (int i = 0; i < 4; i++) {
                data[i + 7] = (byte) rmByte.nextInt(9);
            }
            byte[] cmd = new byte[(data.length + 60)];
            System.arraycopy(data, 0, cmd, 60, data.length);
            io.SkipAvailable();
            if (io.Write(cmd, 0, cmd.length) == cmd.length) {
                byte[] rec = new byte[7];
                while (true) {
                    if (io.Read(rec, 0, 1, RpcException.ErrorCode.SERVER_OPERATIONTYPEMISSED) != 1) {
                        break;
                    }
                    result = 0;
                    if (rec[0] != 99) {
                        if ((rec[0] & 144) == 0) {
                            break;
                        }
                    } else if (io.Read(rec, 1, 5, RpcException.ErrorCode.SERVER_OPERATIONTYPEMISSED) == 5 && rec[1] == 95) {
                        long v1 = ((((long) data[5]) & 255) << 24) | ((((long) data[6]) & 255) << 16) | ((((long) data[7]) & 255) << 8) | (((long) data[8]) & 255);
                        long v2 = ((((long) data[9]) & 255) << 24) | ((((long) data[10]) & 255) << 16) | ((((long) data[11]) & 255) << 8) | (((long) data[12]) & 255);
                        long vadd = (v1 + v2) & 4294967295L;
                        long vxor = (v1 ^ v2) & 4294967295L;
                        long l1 = v1 & 65535;
                        long h2 = (v2 >> 16) & 65535;
                        if ((((vadd - vxor) - (((l1 * l1) - (h2 * h2)) & 4294967295L)) & 4294967295L) == (((((long) rec[2]) & 255) << 24) | ((((long) rec[3]) & 255) << 16) | ((((long) rec[4]) & 255) << 8) | (((long) rec[5]) & 255))) {
                            result = 1;
                        }
                    }
                }
            }
            io.mMainLocker.unlock();
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
            io.mMainLocker.unlock();
        } catch (Throwable th) {
            io.mMainLocker.unlock();
            throw th;
        }
        return result;
    }

    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean PTR_CheckKey(com.lvrenyang.io.base.IO r25) {
        /*
            r0 = r25
            java.util.concurrent.locks.ReentrantLock r0 = r0.mMainLocker
            r22 = r0
            r22.lock()
            r20 = 0
            java.lang.String r22 = "XSH-KCEC"
            byte[] r13 = r22.getBytes()     // Catch:{ Exception -> 0x013d }
            r22 = 8
            r0 = r22
            byte[] r14 = new byte[r0]     // Catch:{ Exception -> 0x013d }
            java.util.Random r21 = new java.util.Random     // Catch:{ Exception -> 0x013d }
            long r22 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x013d }
            r21.<init>(r22)     // Catch:{ Exception -> 0x013d }
            r12 = 0
        L_0x0021:
            r22 = 8
            r0 = r22
            if (r12 >= r0) goto L_0x0037
            r22 = 9
            int r22 = r21.nextInt(r22)     // Catch:{ Exception -> 0x013d }
            r0 = r22
            byte r0 = (byte) r0     // Catch:{ Exception -> 0x013d }
            r22 = r0
            r14[r12] = r22     // Catch:{ Exception -> 0x013d }
            int r12 = r12 + 1
            goto L_0x0021
        L_0x0037:
            r6 = 5
            r22 = 5
            r0 = r22
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x013d }
            r19 = r0
            r17 = 0
            r16 = 0
            r18 = 0
            r22 = 2
            r0 = r22
            byte[] r15 = new byte[r0]     // Catch:{ Exception -> 0x013d }
            r22 = 0
            int r0 = r14.length     // Catch:{ Exception -> 0x013d }
            r23 = r0
            r0 = r23
            r0 = r0 & 255(0xff, float:3.57E-43)
            r23 = r0
            r0 = r23
            byte r0 = (byte) r0     // Catch:{ Exception -> 0x013d }
            r23 = r0
            r15[r22] = r23     // Catch:{ Exception -> 0x013d }
            r22 = 1
            int r0 = r14.length     // Catch:{ Exception -> 0x013d }
            r23 = r0
            int r23 = r23 >> 8
            r0 = r23
            r0 = r0 & 255(0xff, float:3.57E-43)
            r23 = r0
            r0 = r23
            byte r0 = (byte) r0     // Catch:{ Exception -> 0x013d }
            r23 = r0
            r15[r22] = r23     // Catch:{ Exception -> 0x013d }
            r22 = 4
            r0 = r22
            byte[][] r0 = new byte[r0][]     // Catch:{ Exception -> 0x013d }
            r22 = r0
            r23 = 0
            r24 = 3
            r0 = r24
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x013d }
            r24 = r0
            r24 = {31, 31, 2} // fill-array     // Catch:{ Exception -> 0x013d }
            r22[r23] = r24     // Catch:{ Exception -> 0x013d }
            r23 = 1
            r22[r23] = r15     // Catch:{ Exception -> 0x013d }
            r23 = 2
            r22[r23] = r14     // Catch:{ Exception -> 0x013d }
            r23 = 3
            r24 = 2
            r0 = r24
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x013d }
            r24 = r0
            r24 = {27, 64} // fill-array     // Catch:{ Exception -> 0x013d }
            r22[r23] = r24     // Catch:{ Exception -> 0x013d }
            byte[] r7 = com.lvrenyang.io.ByteUtils.byteArraysToBytes(r22)     // Catch:{ Exception -> 0x013d }
            r25.SkipAvailable()     // Catch:{ Exception -> 0x013d }
            r22 = 0
            int r0 = r7.length     // Catch:{ Exception -> 0x013d }
            r23 = r0
            r0 = r25
            r1 = r22
            r2 = r23
            r0.Write(r7, r1, r2)     // Catch:{ Exception -> 0x013d }
            r22 = 0
            r23 = 5
            r24 = 1000(0x3e8, float:1.401E-42)
            r0 = r25
            r1 = r19
            r2 = r22
            r3 = r23
            r4 = r24
            int r16 = r0.Read(r1, r2, r3, r4)     // Catch:{ Exception -> 0x013d }
            r22 = 5
            r0 = r16
            r1 = r22
            if (r0 != r1) goto L_0x0133
            r22 = 3
            byte r22 = r19[r22]     // Catch:{ Exception -> 0x013d }
            r0 = r22
            r0 = r0 & 255(0xff, float:3.57E-43)
            r22 = r0
            r23 = 4
            byte r23 = r19[r23]     // Catch:{ Exception -> 0x013d }
            int r23 = r23 << 8
            r0 = r23
            r0 = r0 & 255(0xff, float:3.57E-43)
            r23 = r0
            int r18 = r22 + r23
            r0 = r18
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x013d }
            r17 = r0
            r22 = 0
            r23 = 1000(0x3e8, float:1.401E-42)
            r0 = r25
            r1 = r17
            r2 = r22
            r3 = r18
            r4 = r23
            int r16 = r0.Read(r1, r2, r3, r4)     // Catch:{ Exception -> 0x013d }
            r0 = r16
            r1 = r18
            if (r0 != r1) goto L_0x0133
            r10 = r17
            int r0 = r10.length     // Catch:{ Exception -> 0x013d }
            r22 = r0
            int r22 = r22 + 1
            r0 = r22
            byte[] r8 = new byte[r0]     // Catch:{ Exception -> 0x013d }
            com.lvrenyang.io.DES2 r9 = new com.lvrenyang.io.DES2     // Catch:{ Exception -> 0x013d }
            r9.<init>()     // Catch:{ Exception -> 0x013d }
            r9.yxyDES2_InitializeKey(r13)     // Catch:{ Exception -> 0x013d }
            int r0 = r10.length     // Catch:{ Exception -> 0x013d }
            r22 = r0
            r0 = r22
            r9.yxyDES2_DecryptAnyLength(r10, r8, r0)     // Catch:{ Exception -> 0x013d }
            r22 = 0
            r23 = 0
            int r0 = r14.length     // Catch:{ Exception -> 0x013d }
            r24 = r0
            r0 = r22
            r1 = r23
            r2 = r24
            boolean r20 = com.lvrenyang.io.ByteUtils.bytesEquals(r14, r0, r8, r1, r2)     // Catch:{ Exception -> 0x013d }
        L_0x0133:
            r0 = r25
            java.util.concurrent.locks.ReentrantLock r0 = r0.mMainLocker
            r22 = r0
            r22.unlock()
        L_0x013c:
            return r20
        L_0x013d:
            r11 = move-exception
            java.lang.String r22 = TAG     // Catch:{ all -> 0x0151 }
            java.lang.String r23 = r11.toString()     // Catch:{ all -> 0x0151 }
            android.util.Log.i(r22, r23)     // Catch:{ all -> 0x0151 }
            r0 = r25
            java.util.concurrent.locks.ReentrantLock r0 = r0.mMainLocker
            r22 = r0
            r22.unlock()
            goto L_0x013c
        L_0x0151:
            r22 = move-exception
            r0 = r25
            java.util.concurrent.locks.ReentrantLock r0 = r0.mMainLocker
            r23 = r0
            r23.unlock()
            throw r22
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lvrenyang.io.PrinterChecker.PTR_CheckKey(com.lvrenyang.io.base.IO):boolean");
    }

    public static int PTR_CheckPrinter(IO io) {
        io.mMainLocker.lock();
        int check = -1;
        int i = 0;
        while (i < 3) {
            try {
                check = PTR_CheckEncrypt(io);
                if (check != -1) {
                    break;
                }
                i++;
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                io.mMainLocker.unlock();
            } catch (Throwable th) {
                io.mMainLocker.unlock();
                throw th;
            }
        }
        if (check == 0 && PTR_CheckKey(io)) {
            check = 1;
        }
        if (check == 1) {
            nCheckFaildTimes = 0;
        } else if (check == 0) {
            nCheckFaildTimes++;
        }
        if (nCheckFaildTimes >= nMaxCheckFailedTimes) {
            byte[] header = {13, 10, 27, 64, 28, BLEServiceApi.POWER_STATUS_CMD, 27, 57, 1};
            byte[] txt = "----Unknow printer----\r\n".getBytes();
            byte[] cmd = new byte[(header.length + txt.length)];
            System.arraycopy(header, 0, cmd, 0, header.length);
            int offset = 0 + header.length;
            System.arraycopy(txt, 0, cmd, offset, txt.length);
            int offset2 = offset + txt.length;
            io.Write(cmd, 0, cmd.length);
        }
        io.mMainLocker.unlock();
        return check;
    }
}

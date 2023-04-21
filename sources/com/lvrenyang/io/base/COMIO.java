package com.lvrenyang.io.base;

import android.util.Log;
import android_serialport_api.SerialPort;
import android_serialport_api.SerialPortFinder;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class COMIO extends IO {
    private static final String TAG = "COMIO";
    private static final SerialPortFinder spFinder = new SerialPortFinder();
    private IOCallBack cb = null;
    private InputStream is = null;
    private AtomicBoolean isOpened = new AtomicBoolean(false);
    private AtomicBoolean isReadyRW = new AtomicBoolean(false);
    private AtomicLong nIdleTime = new AtomicLong(0);
    private OutputStream os = null;
    private Vector<Byte> rxBuffer = new Vector<>();
    private SerialPort sp = null;

    /* JADX WARNING: Removed duplicated region for block: B:24:0x005d A[Catch:{ Exception -> 0x0015, all -> 0x00bf }] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0097 A[Catch:{ Exception -> 0x0015, all -> 0x00bf }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean Open(java.lang.String r8, int r9, int r10) {
        /*
            r7 = this;
            java.util.concurrent.locks.ReentrantLock r4 = r7.mMainLocker
            r4.lock()
            java.util.concurrent.atomic.AtomicBoolean r4 = r7.isOpened     // Catch:{ Exception -> 0x0015 }
            boolean r4 = r4.get()     // Catch:{ Exception -> 0x0015 }
            if (r4 == 0) goto L_0x002b
            java.lang.Exception r4 = new java.lang.Exception     // Catch:{ Exception -> 0x0015 }
            java.lang.String r5 = "Already open"
            r4.<init>(r5)     // Catch:{ Exception -> 0x0015 }
            throw r4     // Catch:{ Exception -> 0x0015 }
        L_0x0015:
            r3 = move-exception
            java.lang.String r4 = "COMIO"
            java.lang.String r5 = r3.toString()     // Catch:{ all -> 0x00bf }
            android.util.Log.i(r4, r5)     // Catch:{ all -> 0x00bf }
            java.util.concurrent.locks.ReentrantLock r4 = r7.mMainLocker
            r4.unlock()
        L_0x0024:
            java.util.concurrent.atomic.AtomicBoolean r4 = r7.isOpened
            boolean r4 = r4.get()
            return r4
        L_0x002b:
            java.util.concurrent.atomic.AtomicBoolean r4 = r7.isReadyRW     // Catch:{ Exception -> 0x0015 }
            r5 = 0
            r4.set(r5)     // Catch:{ Exception -> 0x0015 }
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x00ab }
            r1.<init>(r8)     // Catch:{ Exception -> 0x00ab }
            android_serialport_api.SerialPort r4 = new android_serialport_api.SerialPort     // Catch:{ Exception -> 0x00cc }
            r4.<init>(r1, r9, r10)     // Catch:{ Exception -> 0x00cc }
            r7.sp = r4     // Catch:{ Exception -> 0x00cc }
            android_serialport_api.SerialPort r4 = r7.sp     // Catch:{ Exception -> 0x00cc }
            java.io.OutputStream r4 = r4.getOutputStream()     // Catch:{ Exception -> 0x00cc }
            r7.os = r4     // Catch:{ Exception -> 0x00cc }
            android_serialport_api.SerialPort r4 = r7.sp     // Catch:{ Exception -> 0x00cc }
            java.io.InputStream r4 = r4.getInputStream()     // Catch:{ Exception -> 0x00cc }
            r7.is = r4     // Catch:{ Exception -> 0x00cc }
            java.util.concurrent.atomic.AtomicBoolean r4 = r7.isReadyRW     // Catch:{ Exception -> 0x00cc }
            r5 = 1
            r4.set(r5)     // Catch:{ Exception -> 0x00cc }
            r0 = r1
        L_0x0055:
            java.util.concurrent.atomic.AtomicBoolean r4 = r7.isReadyRW     // Catch:{ Exception -> 0x0015 }
            boolean r4 = r4.get()     // Catch:{ Exception -> 0x0015 }
            if (r4 == 0) goto L_0x0088
            java.lang.String r4 = "COMIO"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0015 }
            r5.<init>()     // Catch:{ Exception -> 0x0015 }
            java.lang.String r6 = "Connected to "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0015 }
            java.lang.String r6 = r0.getAbsolutePath()     // Catch:{ Exception -> 0x0015 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0015 }
            java.lang.String r6 = " "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0015 }
            java.lang.StringBuilder r5 = r5.append(r9)     // Catch:{ Exception -> 0x0015 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0015 }
            android.util.Log.v(r4, r5)     // Catch:{ Exception -> 0x0015 }
            java.util.Vector<java.lang.Byte> r4 = r7.rxBuffer     // Catch:{ Exception -> 0x0015 }
            r4.clear()     // Catch:{ Exception -> 0x0015 }
        L_0x0088:
            java.util.concurrent.atomic.AtomicBoolean r4 = r7.isOpened     // Catch:{ Exception -> 0x0015 }
            java.util.concurrent.atomic.AtomicBoolean r5 = r7.isReadyRW     // Catch:{ Exception -> 0x0015 }
            boolean r5 = r5.get()     // Catch:{ Exception -> 0x0015 }
            r4.set(r5)     // Catch:{ Exception -> 0x0015 }
            com.lvrenyang.io.base.IOCallBack r4 = r7.cb     // Catch:{ Exception -> 0x0015 }
            if (r4 == 0) goto L_0x00a4
            java.util.concurrent.atomic.AtomicBoolean r4 = r7.isOpened     // Catch:{ Exception -> 0x0015 }
            boolean r4 = r4.get()     // Catch:{ Exception -> 0x0015 }
            if (r4 == 0) goto L_0x00c6
            com.lvrenyang.io.base.IOCallBack r4 = r7.cb     // Catch:{ Exception -> 0x0015 }
            r4.OnOpen()     // Catch:{ Exception -> 0x0015 }
        L_0x00a4:
            java.util.concurrent.locks.ReentrantLock r4 = r7.mMainLocker
            r4.unlock()
            goto L_0x0024
        L_0x00ab:
            r2 = move-exception
        L_0x00ac:
            java.lang.String r4 = "COMIO"
            java.lang.String r5 = r2.toString()     // Catch:{ Exception -> 0x0015 }
            android.util.Log.e(r4, r5)     // Catch:{ Exception -> 0x0015 }
            r4 = 0
            r7.sp = r4     // Catch:{ Exception -> 0x0015 }
            r4 = 0
            r7.os = r4     // Catch:{ Exception -> 0x0015 }
            r4 = 0
            r7.is = r4     // Catch:{ Exception -> 0x0015 }
            goto L_0x0055
        L_0x00bf:
            r4 = move-exception
            java.util.concurrent.locks.ReentrantLock r5 = r7.mMainLocker
            r5.unlock()
            throw r4
        L_0x00c6:
            com.lvrenyang.io.base.IOCallBack r4 = r7.cb     // Catch:{ Exception -> 0x0015 }
            r4.OnOpenFailed()     // Catch:{ Exception -> 0x0015 }
            goto L_0x00a4
        L_0x00cc:
            r2 = move-exception
            r0 = r1
            goto L_0x00ac
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lvrenyang.io.base.COMIO.Open(java.lang.String, int, int):boolean");
    }

    public void Close() {
        this.mCloseLocker.lock();
        try {
            if (this.sp != null) {
                this.sp.close();
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        }
        try {
            if (!this.isReadyRW.get()) {
                throw new Exception();
            }
            this.sp = null;
            this.is = null;
            this.os = null;
            this.isReadyRW.set(false);
            if (!this.isOpened.get()) {
                throw new Exception();
            }
            this.isOpened.set(false);
            if (this.cb != null) {
                this.cb.OnClose();
            }
            return;
        } catch (Exception ex2) {
            Log.i(TAG, ex2.toString());
            return;
        } finally {
            this.mCloseLocker.unlock();
        }
    }

    public int Write(byte[] buffer, int offset, int count) {
        if (!this.isReadyRW.get()) {
            return -1;
        }
        this.mMainLocker.lock();
        try {
            this.nIdleTime.set(0);
            this.os.write(buffer, offset, count);
            this.os.flush();
            int nBytesWritten = count;
            this.nIdleTime.set(System.currentTimeMillis());
            return nBytesWritten;
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
            Close();
            return -1;
        } finally {
            this.mMainLocker.unlock();
        }
    }

    public int Read(byte[] buffer, int offset, int count, int timeout) {
        if (!this.isReadyRW.get()) {
            return -1;
        }
        this.mMainLocker.lock();
        int nBytesReaded = 0;
        try {
            this.nIdleTime.set(0);
            long time = System.currentTimeMillis();
            while (System.currentTimeMillis() - time < ((long) timeout)) {
                if (!this.isReadyRW.get()) {
                    throw new Exception("Not Ready For Read Write");
                } else if (nBytesReaded != count) {
                    if (this.rxBuffer.size() > 0) {
                        buffer[offset + nBytesReaded] = this.rxBuffer.get(0).byteValue();
                        this.rxBuffer.remove(0);
                        nBytesReaded++;
                    } else {
                        int available = this.is.available();
                        if (available > 0) {
                            byte[] receive = new byte[available];
                            int nReceived = this.is.read(receive);
                            if (nReceived > 0) {
                                for (int i = 0; i < nReceived; i++) {
                                    this.rxBuffer.add(Byte.valueOf(receive[i]));
                                }
                            }
                        } else {
                            Thread.sleep(1);
                        }
                    }
                }
            }
            this.nIdleTime.set(System.currentTimeMillis());
            return nBytesReaded;
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
            Close();
            return -1;
        } finally {
            this.mMainLocker.unlock();
        }
    }

    public void SkipAvailable() {
        this.mMainLocker.lock();
        try {
            this.rxBuffer.clear();
            this.is.skip((long) this.is.available());
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.mMainLocker.unlock();
        }
    }

    public boolean IsOpened() {
        return this.isOpened.get();
    }

    public void SetCallBack(IOCallBack callBack) {
        this.mMainLocker.lock();
        try {
            this.cb = callBack;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.mMainLocker.unlock();
        }
    }

    public static String[] enumPorts() {
        return spFinder.getAllDevicesPath();
    }
}

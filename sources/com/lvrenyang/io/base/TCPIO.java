package com.lvrenyang.io.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.Log;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@TargetApi(1)
public class TCPIO extends IO {
    private static final String TAG = "TCPBaseIO";
    private String address;
    private IOCallBack cb = null;
    private DataInputStream is = null;
    private AtomicBoolean isOpened = new AtomicBoolean(false);
    private AtomicBoolean isReadyRW = new AtomicBoolean(false);
    private Socket mmClientSocket = null;
    private AtomicLong nIdleTime = new AtomicLong(0);
    private DataOutputStream os = null;
    private Vector<Byte> rxBuffer = new Vector<>();

    public boolean Open(String IPAddress, int PortNumber, int timeout, Context mContext) {
        this.mMainLocker.lock();
        try {
            if (this.isOpened.get()) {
                throw new Exception("Already open");
            } else if (IPAddress == null) {
                throw new Exception("Null Pointer IPAddress");
            } else {
                this.address = IPAddress;
                this.isReadyRW.set(false);
                try {
                    SocketAddress socketAddress = new InetSocketAddress(IPAddress, PortNumber);
                    this.mmClientSocket = new Socket();
                    this.mmClientSocket.connect(socketAddress, timeout);
                    this.os = new DataOutputStream(this.mmClientSocket.getOutputStream());
                    this.is = new DataInputStream(this.mmClientSocket.getInputStream());
                    this.isReadyRW.set(true);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                    try {
                        this.mmClientSocket.close();
                        this.mmClientSocket = null;
                        this.os = null;
                        this.is = null;
                    } catch (Exception closeException) {
                        Log.i(TAG, closeException.toString());
                        this.mmClientSocket = null;
                        this.os = null;
                        this.is = null;
                    } catch (Throwable th) {
                        this.mmClientSocket = null;
                        this.os = null;
                        this.is = null;
                        throw th;
                    }
                }
                if (this.isReadyRW.get()) {
                    Log.v(TAG, "Connected to " + IPAddress + ":" + PortNumber);
                    this.rxBuffer.clear();
                }
                this.isOpened.set(this.isReadyRW.get());
                if (this.cb != null) {
                    if (this.isOpened.get()) {
                        this.cb.OnOpen();
                    } else {
                        this.cb.OnOpenFailed();
                    }
                }
                return this.isOpened.get();
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.mMainLocker.unlock();
        }
    }

    public void Close() {
        this.mCloseLocker.lock();
        try {
            if (this.mmClientSocket != null) {
                this.mmClientSocket.shutdownInput();
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        }
        try {
            if (this.mmClientSocket != null) {
                this.mmClientSocket.shutdownOutput();
            }
        } catch (Exception ex2) {
            Log.i(TAG, ex2.toString());
        }
        try {
            if (this.mmClientSocket != null) {
                this.mmClientSocket.close();
            }
        } catch (Exception ex3) {
            Log.i(TAG, ex3.toString());
        }
        try {
            if (!this.isReadyRW.get()) {
                throw new Exception();
            }
            this.mmClientSocket = null;
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
        } catch (Exception ex4) {
            Log.i(TAG, ex4.toString());
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
}

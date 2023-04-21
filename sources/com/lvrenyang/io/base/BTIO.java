package com.lvrenyang.io.base;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@TargetApi(5)
public class BTIO extends IO {
    private static final String TAG = "BTIO";
    private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    /* access modifiers changed from: private */
    public String address;
    private IOCallBack cb = null;
    private Context context;
    private IntentFilter filter = new IntentFilter();
    private DataInputStream is = null;
    private AtomicBoolean isOpened = new AtomicBoolean(false);
    private AtomicBoolean isReadyRW = new AtomicBoolean(false);
    private BluetoothSocket mmClientSocket = null;
    private BluetoothServerSocket mmServerSocket = null;
    private AtomicLong nIdleTime = new AtomicLong(0);
    private DataOutputStream os = null;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            if (("android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED".equals(action) || "android.bluetooth.device.action.ACL_DISCONNECTED".equals(action)) && device != null && device.getAddress().equalsIgnoreCase(BTIO.this.address)) {
                BTIO.this.Close();
            }
        }
    };
    private Vector<Byte> rxBuffer = new Vector<>();

    private void RegisterReceiver() {
        if (!this.filter.hasAction("android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED")) {
            this.filter.addAction("android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED");
        }
        if (!this.filter.hasAction("android.bluetooth.device.action.ACL_DISCONNECTED")) {
            this.filter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        }
        this.context.registerReceiver(this.receiver, this.filter);
        Log.i(TAG, "RegisterReceiver");
    }

    private void UnregisterReceiver() {
        this.context.unregisterReceiver(this.receiver);
        Log.i(TAG, "UnregisterReceiver");
    }

    public boolean Open(String BTAddress, Context mContext) {
        this.mMainLocker.lock();
        try {
            if (this.isOpened.get()) {
                throw new Exception("Already open");
            } else if (mContext == null) {
                throw new Exception("Null Pointer mContext");
            } else {
                this.context = mContext;
                if (BTAddress == null) {
                    throw new Exception("Null Pointer BTAddress");
                }
                this.address = BTAddress;
                this.isReadyRW.set(false);
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (bluetoothAdapter == null) {
                    throw new Exception("Null BluetoothAdapter");
                }
                bluetoothAdapter.cancelDiscovery();
                BluetoothDevice device = bluetoothAdapter.getRemoteDevice(BTAddress);
                long time = System.currentTimeMillis();
                do {
                    if (System.currentTimeMillis() - time >= 10000) {
                        break;
                    }
                    try {
                        this.mmClientSocket = device.createRfcommSocketToServiceRecord(uuid);
                        this.mmClientSocket.connect();
                        this.os = new DataOutputStream(this.mmClientSocket.getOutputStream());
                        this.is = new DataInputStream(this.mmClientSocket.getInputStream());
                        this.isReadyRW.set(true);
                    } catch (Exception connectException) {
                        Log.i(TAG, connectException.toString());
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
                            throw new Exception("Connect Failed");
                        } catch (Throwable th) {
                            this.mmClientSocket = null;
                            this.os = null;
                            this.is = null;
                            throw th;
                        }
                        throw new Exception("Connect Failed");
                    } catch (Exception ex) {
                        Log.i(TAG, ex.toString());
                    }
                } while (!this.isReadyRW.get());
                if (this.isReadyRW.get()) {
                    Log.v(TAG, "Connected to " + BTAddress);
                    this.rxBuffer.clear();
                    RegisterReceiver();
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
        } catch (Exception ex2) {
            Log.i(TAG, ex2.toString());
        } finally {
            this.mMainLocker.unlock();
        }
    }

    public boolean Listen(String BTAddress, int timeout, Context mContext) {
        this.mMainLocker.lock();
        try {
            if (this.isOpened.get()) {
                throw new Exception("Already open");
            } else if (mContext == null) {
                throw new Exception("Null Pointer mContext");
            } else {
                this.context = mContext;
                if (BTAddress == null) {
                    throw new Exception("Null Pointer BTAddress");
                }
                this.address = BTAddress;
                this.isReadyRW.set(false);
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (bluetoothAdapter == null) {
                    throw new Exception("Null BluetoothAdapter");
                }
                bluetoothAdapter.cancelDiscovery();
                this.mmServerSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord("rfcomm", uuid);
                this.mmClientSocket = this.mmServerSocket.accept(timeout);
                this.os = new DataOutputStream(this.mmClientSocket.getOutputStream());
                this.is = new DataInputStream(this.mmClientSocket.getInputStream());
                this.isReadyRW.set(true);
                if (this.isReadyRW.get()) {
                    Log.v(TAG, "Connected to " + BTAddress);
                    this.rxBuffer.clear();
                    RegisterReceiver();
                }
                this.isOpened.set(this.isReadyRW.get());
                if (this.cb != null) {
                    if (this.isOpened.get()) {
                        this.cb.OnOpen();
                    } else {
                        this.cb.OnOpenFailed();
                    }
                }
                this.mMainLocker.unlock();
                return this.isOpened.get();
            }
        } catch (Exception streamException) {
            Log.i(TAG, streamException.toString());
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
                throw new Exception("Get Stream Failed");
            } catch (Throwable th) {
                this.mmClientSocket = null;
                this.os = null;
                this.is = null;
                throw th;
            }
            throw new Exception("Get Stream Failed");
        } catch (Exception acceptException) {
            Log.i(TAG, acceptException.toString());
            try {
                this.mmServerSocket.close();
                this.mmServerSocket = null;
            } catch (Exception closeException2) {
                Log.i(TAG, closeException2.toString());
                this.mmServerSocket = null;
                throw new Exception("Accept Failed");
            } catch (Throwable th2) {
                this.mmServerSocket = null;
                throw th2;
            }
            throw new Exception("Accept Failed");
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
            this.mMainLocker.unlock();
        } catch (Throwable th3) {
            this.mMainLocker.unlock();
            throw th3;
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|(1:4)|5|6|(1:8)|9|10|(2:12|13)(2:28|(2:30|31)(4:32|(1:34)|35|39))) */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0025, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        android.util.Log.i(TAG, r0.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002f, code lost:
        r3.mCloseLocker.unlock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0037, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0038, code lost:
        r3.mCloseLocker.unlock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x003d, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void Close() {
        /*
            r3 = this;
            java.util.concurrent.locks.ReentrantLock r1 = r3.mCloseLocker
            r1.lock()
            android.bluetooth.BluetoothServerSocket r1 = r3.mmServerSocket     // Catch:{ Exception -> 0x007a, all -> 0x0035 }
            if (r1 == 0) goto L_0x000e
            android.bluetooth.BluetoothServerSocket r1 = r3.mmServerSocket     // Catch:{ Exception -> 0x007a, all -> 0x0035 }
            r1.close()     // Catch:{ Exception -> 0x007a, all -> 0x0035 }
        L_0x000e:
            android.bluetooth.BluetoothSocket r1 = r3.mmClientSocket     // Catch:{ Exception -> 0x0078, all -> 0x003e }
            if (r1 == 0) goto L_0x0017
            android.bluetooth.BluetoothSocket r1 = r3.mmClientSocket     // Catch:{ Exception -> 0x0078, all -> 0x003e }
            r1.close()     // Catch:{ Exception -> 0x0078, all -> 0x003e }
        L_0x0017:
            java.util.concurrent.atomic.AtomicBoolean r1 = r3.isReadyRW     // Catch:{ Exception -> 0x0025 }
            boolean r1 = r1.get()     // Catch:{ Exception -> 0x0025 }
            if (r1 != 0) goto L_0x0040
            java.lang.Exception r1 = new java.lang.Exception     // Catch:{ Exception -> 0x0025 }
            r1.<init>()     // Catch:{ Exception -> 0x0025 }
            throw r1     // Catch:{ Exception -> 0x0025 }
        L_0x0025:
            r0 = move-exception
            java.lang.String r1 = "BTIO"
            java.lang.String r2 = r0.toString()     // Catch:{ all -> 0x0037 }
            android.util.Log.i(r1, r2)     // Catch:{ all -> 0x0037 }
            java.util.concurrent.locks.ReentrantLock r1 = r3.mCloseLocker
            r1.unlock()
        L_0x0034:
            return
        L_0x0035:
            r1 = move-exception
            throw r1     // Catch:{ Exception -> 0x0025 }
        L_0x0037:
            r1 = move-exception
            java.util.concurrent.locks.ReentrantLock r2 = r3.mCloseLocker
            r2.unlock()
            throw r1
        L_0x003e:
            r1 = move-exception
            throw r1     // Catch:{ Exception -> 0x0025 }
        L_0x0040:
            r1 = 0
            r3.mmServerSocket = r1     // Catch:{ Exception -> 0x0025 }
            r1 = 0
            r3.mmClientSocket = r1     // Catch:{ Exception -> 0x0025 }
            r1 = 0
            r3.is = r1     // Catch:{ Exception -> 0x0025 }
            r1 = 0
            r3.os = r1     // Catch:{ Exception -> 0x0025 }
            r3.UnregisterReceiver()     // Catch:{ Exception -> 0x0025 }
            java.util.concurrent.atomic.AtomicBoolean r1 = r3.isReadyRW     // Catch:{ Exception -> 0x0025 }
            r2 = 0
            r1.set(r2)     // Catch:{ Exception -> 0x0025 }
            java.util.concurrent.atomic.AtomicBoolean r1 = r3.isOpened     // Catch:{ Exception -> 0x0025 }
            boolean r1 = r1.get()     // Catch:{ Exception -> 0x0025 }
            if (r1 != 0) goto L_0x0063
            java.lang.Exception r1 = new java.lang.Exception     // Catch:{ Exception -> 0x0025 }
            r1.<init>()     // Catch:{ Exception -> 0x0025 }
            throw r1     // Catch:{ Exception -> 0x0025 }
        L_0x0063:
            java.util.concurrent.atomic.AtomicBoolean r1 = r3.isOpened     // Catch:{ Exception -> 0x0025 }
            r2 = 0
            r1.set(r2)     // Catch:{ Exception -> 0x0025 }
            com.lvrenyang.io.base.IOCallBack r1 = r3.cb     // Catch:{ Exception -> 0x0025 }
            if (r1 == 0) goto L_0x0072
            com.lvrenyang.io.base.IOCallBack r1 = r3.cb     // Catch:{ Exception -> 0x0025 }
            r1.OnClose()     // Catch:{ Exception -> 0x0025 }
        L_0x0072:
            java.util.concurrent.locks.ReentrantLock r1 = r3.mCloseLocker
            r1.unlock()
            goto L_0x0034
        L_0x0078:
            r1 = move-exception
            goto L_0x0017
        L_0x007a:
            r1 = move-exception
            goto L_0x000e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lvrenyang.io.base.BTIO.Close():void");
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

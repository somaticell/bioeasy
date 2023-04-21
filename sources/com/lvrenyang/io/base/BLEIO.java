package com.lvrenyang.io.base;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.util.Log;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@TargetApi(18)
public class BLEIO extends IO {
    /* access modifiers changed from: private */
    public static final UUID CHAR_UUID = UUID.fromString("bef8d6c9-9c21-4c9e-b632-bd58c1009f9f");
    private static final int OP_CONNECTING = 1;
    private static final int OP_DISCOVERING = 2;
    private static final int OP_FINISHED = 4;
    /* access modifiers changed from: private */
    public static final UUID SERV_UUID = UUID.fromString("e7810a71-73ae-499d-8c15-faa9aef0c3f2");
    private static final String TAG = "BLEIO";
    private static final int WP_WRITEERR = 3;
    private static final int WP_WRITEOK = 2;
    private static final int WP_WRITTING = 1;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic c;
    private IOCallBack cb = null;
    private Context context;
    /* access modifiers changed from: private */
    public BluetoothGatt g = null;
    /* access modifiers changed from: private */
    public AtomicBoolean isConnected = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public AtomicBoolean isOpened = new AtomicBoolean(false);
    private AtomicBoolean isReadyRW = new AtomicBoolean(false);
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.i(BLEIO.TAG, "onConnectionStateChange  status:" + status + " newState:" + newState);
            if (newState == 2) {
                BLEIO.this.isConnected.set(true);
                if (BLEIO.this.nOpenProcess.get() == 1) {
                    BLEIO.this.nOpenProcess.set(2);
                    if (!gatt.discoverServices()) {
                        BLEIO.this.nOpenProcess.set(4);
                    }
                }
            } else if (newState == 0) {
                BLEIO.this.isConnected.set(false);
                if (BLEIO.this.nOpenProcess.get() != 4) {
                    BLEIO.this.nOpenProcess.set(4);
                }
                if (BLEIO.this.isOpened.get()) {
                    BLEIO.this.Close();
                }
            }
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            BluetoothGattCharacteristic bgc;
            Log.i(BLEIO.TAG, "onServicesDiscovered  status:" + status);
            if (BLEIO.this.nOpenProcess.get() == 2) {
                BluetoothGattService bgs = gatt.getService(BLEIO.SERV_UUID);
                if (!(bgs == null || (bgc = bgs.getCharacteristic(BLEIO.CHAR_UUID)) == null)) {
                    bgc.setWriteType(2);
                    if (gatt.setCharacteristicNotification(bgc, true)) {
                        BluetoothGatt unused = BLEIO.this.g = gatt;
                        BluetoothGattCharacteristic unused2 = BLEIO.this.c = bgc;
                    }
                }
                BLEIO.this.nOpenProcess.set(4);
            }
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.i(BLEIO.TAG, "onCharacteristicWrite  status:" + status);
            if (status == 0) {
                BLEIO.this.nWriteProcess.set(2);
            } else {
                BLEIO.this.nWriteProcess.set(3);
            }
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            byte[] buffer = characteristic.getValue();
            int bytesRead = buffer.length;
            String s = "Recv " + bytesRead + " Bytes: ";
            for (int i = 0; i < bytesRead; i++) {
                BLEIO.this.rxBuffer.add(Byte.valueOf(buffer[i]));
                s = s + String.format("%02X ", new Object[]{Byte.valueOf(buffer[i])});
            }
            Log.i(BLEIO.TAG, s);
        }

        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            Log.i(BLEIO.TAG, "onDescriptorRead  status:" + status);
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            Log.i(BLEIO.TAG, "onDescriptorWrite  status:" + status);
        }

        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            Log.i(BLEIO.TAG, "onReliableWriteCompleted  status:" + status);
        }

        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            Log.i(BLEIO.TAG, "onReadRemoteRssi  rssi:" + rssi + " status:" + status);
        }
    };
    private int nDefaultWritePacketSize = 20;
    private int nDefaultWriteType = 2;
    private AtomicLong nIdleTime = new AtomicLong(0);
    /* access modifiers changed from: private */
    public AtomicInteger nOpenProcess = new AtomicInteger(4);
    /* access modifiers changed from: private */
    public AtomicInteger nWriteProcess = new AtomicInteger(2);
    /* access modifiers changed from: private */
    public Vector<Byte> rxBuffer = new Vector<>();

    public boolean Open(String BTAddress, Context mContext) {
        this.mMainLocker.lock();
        try {
            if (this.isOpened.get()) {
                throw new Exception("Already open");
            } else if (mContext == null) {
                throw new Exception("Null Pointer mContext");
            } else {
                this.context = mContext;
                this.isReadyRW.set(false);
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (bluetoothAdapter == null) {
                    throw new Exception("Null BluetoothAdapter");
                }
                bluetoothAdapter.cancelDiscovery();
                BluetoothDevice device = bluetoothAdapter.getRemoteDevice(BTAddress);
                long time = System.currentTimeMillis();
                while (true) {
                    if (System.currentTimeMillis() - time >= 10000) {
                        break;
                    }
                    this.nOpenProcess.set(1);
                    BluetoothGatt bluetoothGatt = device.connectGatt((Context) null, false, this.mGattCallback);
                    do {
                    } while (this.nOpenProcess.get() != 4);
                    if (this.g != null && this.c != null) {
                        this.isReadyRW.set(true);
                        break;
                    }
                    bluetoothGatt.close();
                }
                if (this.isReadyRW.get()) {
                    Log.v(TAG, "Connected to " + BTAddress);
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
            if (this.g != null) {
                this.g.close();
            }
            try {
                this.isConnected.set(false);
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                this.mCloseLocker.unlock();
                return;
            } catch (Throwable th) {
                this.mCloseLocker.unlock();
                throw th;
            }
        } catch (Exception ex2) {
            Log.i(TAG, ex2.toString());
            this.isConnected.set(false);
        } catch (Throwable th2) {
            this.isConnected.set(false);
            throw th2;
        }
        if (!this.isReadyRW.get()) {
            throw new Exception();
        }
        this.g = null;
        this.c = null;
        this.isReadyRW.set(false);
        if (!this.isOpened.get()) {
            throw new Exception();
        }
        this.isOpened.set(false);
        if (this.cb != null) {
            this.cb.OnClose();
        }
        this.mCloseLocker.unlock();
    }

    public int WritePack(byte[] pack, int timeout) {
        this.mMainLocker.lock();
        int nBytesWritten = 0;
        try {
            int count = pack.length;
            if (!this.c.setValue(pack)) {
                throw new Exception("c.setValue Failed");
            }
            long time = System.currentTimeMillis();
            while (true) {
                if (System.currentTimeMillis() - time >= ((long) timeout)) {
                    break;
                } else if (!this.isConnected.get()) {
                    throw new Exception("Not Connected");
                } else if (!this.isReadyRW.get()) {
                    throw new Exception("Not Ready For Read Write");
                } else {
                    this.nWriteProcess.set(1);
                    if (this.g.writeCharacteristic(this.c)) {
                        while (this.isConnected.get()) {
                            if (!this.isReadyRW.get()) {
                                throw new Exception("Not Ready For Read Write");
                            } else if (this.nWriteProcess.get() != 1) {
                                if (this.nWriteProcess.get() == 2) {
                                    nBytesWritten = count;
                                    break;
                                }
                                WaitMs(20);
                            }
                        }
                        throw new Exception("Not Connected");
                    }
                    WaitMs(20);
                }
            }
            return nBytesWritten;
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
            Close();
            return -1;
        } finally {
            this.mMainLocker.unlock();
        }
    }

    public int Write(byte[] buffer, int offset, int count) {
        if (!this.isReadyRW.get()) {
            return -1;
        }
        this.mMainLocker.lock();
        int nBytesWritten = 0;
        try {
            this.nIdleTime.set(0);
            while (nBytesWritten < count) {
                int nPacketSize = Math.min(this.nDefaultWritePacketSize, count - nBytesWritten);
                byte[] data = new byte[nPacketSize];
                System.arraycopy(buffer, offset + nBytesWritten, data, 0, nPacketSize);
                int nSended = WritePack(data, 1000);
                if (nSended < 0) {
                    throw new Exception("Write Failed");
                }
                nBytesWritten += nSended;
            }
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

    public void SetDefaultWritePacketSize(int size) {
        this.nDefaultWritePacketSize = size;
    }

    public void SetDefaultWriteType(int type) {
        this.nDefaultWriteType = type;
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
                if (!this.isConnected.get()) {
                    throw new Exception("Not Connected");
                } else if (!this.isReadyRW.get()) {
                    throw new Exception("Not Ready For Read Write");
                } else if (nBytesReaded != count) {
                    if (this.rxBuffer.size() > 0) {
                        buffer[offset + nBytesReaded] = this.rxBuffer.get(0).byteValue();
                        this.rxBuffer.remove(0);
                        nBytesReaded++;
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

    private void WaitMs(long ms) {
        do {
        } while (System.currentTimeMillis() - System.currentTimeMillis() < ms);
    }
}

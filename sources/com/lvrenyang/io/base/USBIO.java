package com.lvrenyang.io.base;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Looper;
import android.support.v7.widget.ActivityChooserView;
import android.util.Log;
import android.widget.Toast;
import com.alipay.sdk.packet.d;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@TargetApi(12)
public class USBIO extends IO {
    private static final String TAG = "USBBaseIO";
    /* access modifiers changed from: private */
    public String address;
    private IOCallBack cb = null;
    private Context context;
    private IntentFilter filter = new IntentFilter();
    private AtomicBoolean isOpened = new AtomicBoolean(false);
    private AtomicBoolean isReadyRW = new AtomicBoolean(false);
    private UsbDeviceConnection mUsbDeviceConnection = null;
    private UsbEndpoint mUsbEndpointIn = null;
    private UsbEndpoint mUsbEndpointOut = null;
    private AtomicLong nIdleTime = new AtomicLong(0);
    private String name;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            UsbDevice device = (UsbDevice) intent.getParcelableExtra(d.n);
            if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action) && device != null && device.getDeviceName().equalsIgnoreCase(USBIO.this.address)) {
                USBIO.this.Close();
            }
        }
    };
    private Vector<Byte> rxBuffer = new Vector<>();

    private void RegisterReceiver() {
        if (!this.filter.hasAction("android.hardware.usb.action.USB_DEVICE_DETACHED")) {
            this.filter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        }
        this.context.registerReceiver(this.receiver, this.filter);
        Log.i(TAG, "RegisterReceiver");
    }

    private void UnregisterReceiver() {
        this.context.unregisterReceiver(this.receiver);
        Log.i(TAG, "UnregisterReceiver");
    }

    private void ShowToast(final Context context2, final String content) {
        new Thread() {
            public void run() {
                Log.i("log", "run");
                Looper.prepare();
                Toast.makeText(context2, content, 1).show();
                Looper.loop();
            }
        }.start();
    }

    public boolean Open(UsbManager manager, UsbDevice device, Context mContext) {
        this.mMainLocker.lock();
        try {
            if (this.isOpened.get()) {
                throw new Exception("Already open");
            } else if (mContext == null) {
                throw new Exception("Null Pointer mContext");
            } else {
                this.context = mContext;
                if (device == null) {
                    throw new Exception("Null Pointer device");
                }
                this.address = device.getDeviceName();
                this.name = "VID" + device.getVendorId() + "PID" + device.getProductId();
                this.isReadyRW.set(false);
                try {
                    if (!manager.hasPermission(device)) {
                        throw new Exception("No Permission");
                    }
                    UsbInterface usbInterface = null;
                    UsbEndpoint usbEndpointOut = null;
                    UsbEndpoint usbEndpointIn = null;
                    for (int k = 0; k < device.getInterfaceCount(); k++) {
                        usbInterface = device.getInterface(k);
                        if (usbInterface != null && (device.getInterfaceCount() <= 1 || (usbInterface.getInterfaceClass() == 7 && usbInterface.getInterfaceSubclass() == 1))) {
                            usbEndpointOut = null;
                            usbEndpointIn = null;
                            for (int j = 0; j < usbInterface.getEndpointCount(); j++) {
                                UsbEndpoint endpoint = usbInterface.getEndpoint(j);
                                if (endpoint.getDirection() == 0 && endpoint.getType() == 2) {
                                    usbEndpointOut = endpoint;
                                } else if (endpoint.getDirection() == 128 && endpoint.getType() == 2) {
                                    usbEndpointIn = endpoint;
                                }
                                if (usbEndpointOut != null && usbEndpointIn != null) {
                                    break;
                                }
                            }
                            if (!(usbEndpointOut == null || usbEndpointIn == null)) {
                                break;
                            }
                        }
                    }
                    if (usbInterface == null || usbEndpointOut == null || usbEndpointIn == null) {
                        throw new Exception("No Endpoint");
                    }
                    UsbDeviceConnection usbDeviceConnection = manager.openDevice(device);
                    if (usbDeviceConnection == null) {
                        throw new Exception("Open Device Failed");
                    } else if (!usbDeviceConnection.claimInterface(usbInterface, true)) {
                        usbDeviceConnection.close();
                        throw new Exception("ClaimInterface Failed");
                    } else {
                        this.mUsbEndpointOut = usbEndpointOut;
                        this.mUsbEndpointIn = usbEndpointIn;
                        this.mUsbDeviceConnection = usbDeviceConnection;
                        this.isReadyRW.set(true);
                        if (this.isReadyRW.get()) {
                            Log.v(TAG, "Connected to USB Device");
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
                } catch (Exception ex) {
                    Log.i(TAG, ex.toString());
                    ShowToast(mContext, ex.toString());
                }
            }
        } catch (Exception ex2) {
            Log.i(TAG, ex2.toString());
            ShowToast(mContext, ex2.toString());
        } finally {
            this.mMainLocker.unlock();
        }
    }

    public void Close() {
        this.mCloseLocker.lock();
        try {
            if (this.mUsbDeviceConnection != null) {
                this.mUsbDeviceConnection.close();
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        }
        try {
            if (!this.isReadyRW.get()) {
                throw new Exception();
            }
            this.mUsbEndpointOut = null;
            this.mUsbEndpointIn = null;
            this.mUsbDeviceConnection = null;
            UnregisterReceiver();
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
        int nBytesWritten = 0;
        try {
            this.nIdleTime.set(0);
            while (nBytesWritten < count) {
                if (!this.isReadyRW.get()) {
                    throw new Exception("Not Ready For Read Write");
                }
                byte[] data = new byte[Math.min(this.mUsbEndpointOut.getMaxPacketSize(), count - nBytesWritten)];
                System.arraycopy(buffer, offset + nBytesWritten, data, 0, data.length);
                int nSended = this.mUsbDeviceConnection.bulkTransfer(this.mUsbEndpointOut, data, data.length, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
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
                        byte[] receive = new byte[this.mUsbEndpointIn.getMaxPacketSize()];
                        int nReceived = this.mUsbDeviceConnection.bulkTransfer(this.mUsbEndpointIn, receive, receive.length, 100);
                        if (nReceived > 0) {
                            for (int i = 0; i < nReceived; i++) {
                                this.rxBuffer.add(Byte.valueOf(receive[i]));
                            }
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

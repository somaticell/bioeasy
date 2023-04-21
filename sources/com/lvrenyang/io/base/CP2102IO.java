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
import android.support.v7.widget.ActivityChooserView;
import android.util.Log;
import com.alipay.sdk.packet.d;
import com.facebook.imagepipeline.memory.BitmapCounterProvider;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@TargetApi(12)
public class CP2102IO extends IO {
    private static final int BAUD_RATE_GEN_FREQ = 3686400;
    private static final int CONTROL_WRITE_DTR = 256;
    private static final int CONTROL_WRITE_RTS = 512;
    private static final int CP210X_SET_CHARS = 25;
    private static final int CP210X_SET_FLOW = 19;
    public static final int DATABITS_5 = 5;
    public static final int DATABITS_6 = 6;
    public static final int DATABITS_7 = 7;
    public static final int DATABITS_8 = 8;
    private static final int DEFAULT_BAUD_RATE = 9600;
    public static final int FLOWCONTROL_NONE = 0;
    public static final int FLOWCONTROL_RTSCTS_IN = 1;
    public static final int FLOWCONTROL_RTSCTS_OUT = 2;
    public static final int FLOWCONTROL_XONXOFF_IN = 4;
    public static final int FLOWCONTROL_XONXOFF_OUT = 8;
    private static final int MCR_ALL = 3;
    private static final int MCR_DTR = 1;
    private static final int MCR_RTS = 2;
    public static final int PARITY_EVEN = 2;
    public static final int PARITY_MARK = 3;
    public static final int PARITY_NONE = 0;
    public static final int PARITY_ODD = 1;
    public static final int PARITY_SPACE = 4;
    private static final int REQTYPE_HOST_TO_DEVICE = 65;
    private static final int SILABSER_IFC_ENABLE_REQUEST_CODE = 0;
    private static final int SILABSER_SET_BAUDDIV_REQUEST_CODE = 1;
    private static final int SILABSER_SET_BAUDRATE = 30;
    private static final int SILABSER_SET_LINE_CTL_REQUEST_CODE = 3;
    private static final int SILABSER_SET_MHS_REQUEST_CODE = 7;
    public static final int STOPBITS_1 = 1;
    public static final int STOPBITS_1_5 = 3;
    public static final int STOPBITS_2 = 2;
    private static final String TAG = "CP2102IO";
    private static final int UART_DISABLE = 0;
    private static final int UART_ENABLE = 1;
    private static final int USB_WRITE_TIMEOUT_MILLIS = 5000;
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
            if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action) && device != null && device.getDeviceName().equalsIgnoreCase(CP2102IO.this.address)) {
                CP2102IO.this.Close();
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
        try {
            this.context.unregisterReceiver(this.receiver);
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        }
        Log.i(TAG, "UnregisterReceiver");
    }

    public boolean Open(UsbManager manager, UsbDevice device, int baudrate, Context mContext) {
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
                        if (usbEndpointOut != null && usbEndpointIn != null) {
                            break;
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
                        setConfigSingle(0, 1);
                        setConfigSingle(7, 771);
                        setConfigSingle(1, BitmapCounterProvider.MAX_BITMAP_COUNT);
                        setParameters(8, 1, 0);
                        setChars();
                        setFlow();
                        setBaudRate(baudrate);
                        this.isReadyRW.set(true);
                        if (this.isReadyRW.get()) {
                            Log.v(TAG, "Connected to CP2102 Device");
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
                }
            }
        } catch (Exception ex2) {
            Log.i(TAG, ex2.toString());
        } finally {
            this.mMainLocker.unlock();
        }
    }

    public void Close() {
        this.mCloseLocker.lock();
        try {
            setConfigSingle(0, 0);
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

    private int setConfigSingle(int request, int value) {
        return this.mUsbDeviceConnection.controlTransfer(65, request, value, 0, (byte[]) null, 0, 5000);
    }

    private void setFlow() throws IOException {
        if (this.mUsbDeviceConnection.controlTransfer(65, 19, 0, 0, new byte[]{0, 0, 0, 0, 3, 0, 0, 0, Byte.MIN_VALUE, 0, 0, 0, Byte.MIN_VALUE, 0, 0, 0}, 16, 5000) < 0) {
            throw new IOException("Error setting flow control.");
        }
    }

    private void setChars() throws IOException {
        byte[] data = {26, 0, 0, 26, 17, 19};
        if (this.mUsbDeviceConnection.controlTransfer(65, 25, 0, 0, data, data.length, 5000) < 0) {
            throw new IOException("Error setting flow control.");
        }
    }

    private void setBaudRate(int baudRate) throws IOException {
        if (this.mUsbDeviceConnection.controlTransfer(65, 30, 0, 0, new byte[]{(byte) (baudRate & 255), (byte) ((baudRate >> 8) & 255), (byte) ((baudRate >> 16) & 255), (byte) ((baudRate >> 24) & 255)}, 4, 5000) < 0) {
            throw new IOException("Error setting baud rate.");
        }
    }

    private void setParameters(int dataBits, int stopBits, int parity) throws IOException {
        int configBits;
        switch (dataBits) {
            case 5:
                configBits = 0 | 1280;
                break;
            case 6:
                configBits = 0 | 1536;
                break;
            case 7:
                configBits = 0 | 1792;
                break;
            case 8:
                configBits = 0 | 2048;
                break;
            default:
                configBits = 0 | 2048;
                break;
        }
        switch (parity) {
            case 1:
                configBits |= 16;
                break;
            case 2:
                configBits |= 32;
                break;
        }
        switch (stopBits) {
            case 1:
                configBits |= 0;
                break;
            case 2:
                configBits |= 2;
                break;
        }
        setConfigSingle(3, configBits);
    }
}

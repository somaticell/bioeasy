package com.lvrenyang.io;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;
import com.lvrenyang.io.base.IOCallBack;
import com.lvrenyang.io.base.USBIO;

public class USBPrinting extends USBIO {
    private static final String TAG = "USBPrinting";
    private IOCallBack cb = null;

    public boolean Open(UsbManager manager, UsbDevice device, Context mContext) {
        this.mMainLocker.lock();
        try {
            super.Open(manager, device, mContext);
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.mMainLocker.unlock();
        }
        if (this.cb != null) {
            if (IsOpened()) {
                this.cb.OnOpen();
            } else {
                this.cb.OnOpenFailed();
            }
        }
        return IsOpened();
    }

    public void Close() {
        this.mCloseLocker.lock();
        try {
            if (IsOpened()) {
                super.Close();
                if (this.cb != null) {
                    this.cb.OnClose();
                }
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.mCloseLocker.unlock();
        }
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

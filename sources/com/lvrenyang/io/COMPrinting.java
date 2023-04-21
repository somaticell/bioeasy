package com.lvrenyang.io;

import android.util.Log;
import com.lvrenyang.io.base.COMIO;
import com.lvrenyang.io.base.IOCallBack;

public class COMPrinting extends COMIO {
    private static final String TAG = "COMPrinting";
    private IOCallBack cb = null;

    public boolean Open(String name, int baudrate, int flags) {
        this.mMainLocker.lock();
        try {
            super.Open(name, baudrate, flags);
        } catch (Throwable t) {
            Log.i(TAG, t.toString());
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

package com.lvrenyang.io;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.lvrenyang.io.base.IOCallBack;
import com.lvrenyang.io.base.TCPIO;

@TargetApi(1)
public class NETPrinting extends TCPIO {
    private static final String TAG = "NETPrinting";
    private IOCallBack cb = null;

    public boolean Open(String IPAddress, int PortNumber, int timeout, Context mContext) {
        this.mMainLocker.lock();
        try {
            super.Open(IPAddress, PortNumber, timeout, mContext);
            if (IsOpened()) {
                boolean bCaysnPrinter = false;
                try {
                    bCaysnPrinter = mContext.getSharedPreferences(TAG, 0).getBoolean(IPAddress, false);
                } catch (Exception ex) {
                    Log.v(TAG, ex.toString());
                }
                if (!bCaysnPrinter) {
                    if (1 == PrinterChecker.PTR_CheckPrinter(this)) {
                        bCaysnPrinter = true;
                    }
                    if (bCaysnPrinter) {
                        try {
                            SharedPreferences.Editor editor = mContext.getSharedPreferences(TAG, 0).edit();
                            editor.putBoolean(IPAddress, bCaysnPrinter);
                            editor.commit();
                        } catch (Exception ex2) {
                            Log.v(TAG, ex2.toString());
                        }
                    }
                }
            }
        } catch (Exception ex3) {
            Log.i(TAG, ex3.toString());
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

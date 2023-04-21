package com.lvrenyang.io;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.lvrenyang.io.base.BTIO;
import com.lvrenyang.io.base.IOCallBack;

@TargetApi(5)
public class BTPrinting extends BTIO {
    private static final String TAG = "BTPrinting";
    private IOCallBack cb = null;

    public boolean Open(String BTAddress, Context mContext) {
        this.mMainLocker.lock();
        try {
            super.Open(BTAddress, mContext);
            if (IsOpened()) {
                boolean bCaysnPrinter = false;
                try {
                    bCaysnPrinter = mContext.getSharedPreferences(TAG, 0).getBoolean(BTAddress, false);
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
                            editor.putBoolean(BTAddress, bCaysnPrinter);
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

    public boolean Listen(String BTAddress, int timeout, Context mContext) {
        this.mMainLocker.lock();
        try {
            super.Listen(BTAddress, timeout, mContext);
            if (IsOpened()) {
                boolean bCaysnPrinter = false;
                try {
                    bCaysnPrinter = mContext.getSharedPreferences(TAG, 0).getBoolean(BTAddress, false);
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
                            editor.putBoolean(BTAddress, bCaysnPrinter);
                            editor.commit();
                        } catch (Exception ex2) {
                            Log.v(TAG, ex2.toString());
                        }
                    }
                }
            }
            if (this.cb != null) {
                if (IsOpened()) {
                    this.cb.OnOpen();
                } else {
                    this.cb.OnOpenFailed();
                }
            }
        } catch (Exception ex3) {
            Log.i(TAG, ex3.toString());
        } finally {
            this.mMainLocker.unlock();
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

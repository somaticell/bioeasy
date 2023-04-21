package com.lvrenyang.io.base;

import android.annotation.TargetApi;
import android.util.Log;
import java.util.Vector;

@TargetApi(9)
public class MEMIO extends IO {
    private static final String TAG = "MEMIO";
    private Vector<Byte> writeBuffer = new Vector<>();

    public int Write(byte[] buffer, int offset, int count) {
        int nBytesWritten = 0;
        while (nBytesWritten < count) {
            try {
                this.writeBuffer.add(Byte.valueOf(buffer[offset + nBytesWritten]));
                nBytesWritten++;
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
                return -1;
            }
        }
        return nBytesWritten;
    }

    public int Read(byte[] buffer, int offset, int count, int timeout) {
        return 0;
    }

    public void SkipAvailable() {
    }

    public boolean IsOpened() {
        return true;
    }

    public void SetCallBack(IOCallBack callBack) {
    }

    public byte[] GetWriteBuffer() {
        int count = this.writeBuffer.size();
        byte[] buffer = new byte[count];
        for (int i = 0; i < count; i++) {
            buffer[i] = this.writeBuffer.get(i).byteValue();
        }
        return buffer;
    }
}

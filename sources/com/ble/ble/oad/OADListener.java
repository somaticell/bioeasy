package com.ble.ble.oad;

public interface OADListener {
    void onBlockWrite(byte[] bArr);

    void onFinished(String str, int i, long j);

    void onInterrupted(String str, int i, int i2, long j);

    void onPrepared(String str);

    void onProgressChanged(String str, int i, int i2, long j);
}

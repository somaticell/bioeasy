package com.ble.api;

public class EncodeUtil {
    static {
        try {
            System.loadLibrary("hy_api");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    public native byte[] decodeMessage(byte[] bArr);

    public native byte[] encodeMessage(byte[] bArr);
}

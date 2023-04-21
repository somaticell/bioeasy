package com.lvrenyang.qrcode;

import java.io.UnsupportedEncodingException;

class QR8BitByte extends QRData {
    public QR8BitByte(String data) {
        super(4, data);
    }

    public void write(BitBuffer buffer) {
        try {
            byte[] data = getData().getBytes(QRUtil.getUTF8Encoding());
            for (byte put : data) {
                buffer.put(put, 8);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int getLength() {
        try {
            return getData().getBytes(QRUtil.getUTF8Encoding()).length;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

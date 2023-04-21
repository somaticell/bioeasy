package com.lvrenyang.qrcode;

import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import java.io.UnsupportedEncodingException;

class QRKanji extends QRData {
    public QRKanji(String data) {
        super(8, data);
    }

    public void write(BitBuffer buffer) {
        int c;
        try {
            byte[] data = getData().getBytes(QRUtil.getJISEncoding());
            int i = 0;
            while (i + 1 < data.length) {
                int c2 = ((data[i] & BLEServiceApi.CONNECTED_STATUS) << 8) | (data[i + 1] & 255);
                if (33088 <= c2 && c2 <= 40956) {
                    c = c2 - 33088;
                } else if (57408 > c2 || c2 > 60351) {
                    throw new IllegalArgumentException("illegal char at " + (i + 1) + "/" + Integer.toHexString(c2));
                } else {
                    c = c2 - 49472;
                }
                buffer.put((((c >>> 8) & 255) * 192) + (c & 255), 13);
                i += 2;
            }
            if (i < data.length) {
                throw new IllegalArgumentException("illegal char at " + (i + 1));
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int getLength() {
        try {
            return getData().getBytes(QRUtil.getJISEncoding()).length / 2;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

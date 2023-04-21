package com.ble.ble.adaption;

class a {
    static int a(byte[] bArr) {
        if (bArr == null || bArr.length < 4) {
            return 0;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(String.format("%02x", new Object[]{Byte.valueOf(bArr[3 - i])}));
        }
        return Integer.valueOf(sb.toString(), 16).intValue();
    }
}

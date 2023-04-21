package com.ble.ble;

import android.os.ParcelUuid;
import java.util.Locale;

class a {
    public static ParcelUuid a(byte[] bArr) {
        String str = "00000000-0000-1000-8000-00805f9b34fb";
        switch (bArr.length) {
            case 2:
                str = String.format(Locale.US, "0000%02x%02x-0000-1000-8000-00805f9b34fb", new Object[]{Byte.valueOf(bArr[1]), Byte.valueOf(bArr[0])});
                break;
            case 4:
                str = String.format(Locale.US, "%02x%02x%02x%02x-0000-1000-8000-00805f9b34fb", new Object[]{Byte.valueOf(bArr[3]), Byte.valueOf(bArr[2]), Byte.valueOf(bArr[1]), Byte.valueOf(bArr[0])});
                break;
            case 16:
                StringBuilder sb = new StringBuilder();
                for (int length = bArr.length - 1; length >= 0; length--) {
                    sb.append(String.format(Locale.US, "%02x", new Object[]{Byte.valueOf(bArr[length])}));
                    if (length == 12 || length == 10 || length == 8 || length == 6) {
                        sb.append('-');
                    }
                }
                break;
        }
        return ParcelUuid.fromString(str);
    }
}

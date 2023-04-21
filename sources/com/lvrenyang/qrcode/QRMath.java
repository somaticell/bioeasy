package com.lvrenyang.qrcode;

import com.baidu.mapapi.UIMsg;

class QRMath {
    private static final int[] EXP_TABLE = new int[256];
    private static final int[] LOG_TABLE = new int[256];

    private QRMath() {
    }

    static {
        for (int i = 0; i < 8; i++) {
            EXP_TABLE[i] = 1 << i;
        }
        for (int i2 = 8; i2 < 256; i2++) {
            EXP_TABLE[i2] = ((EXP_TABLE[i2 - 4] ^ EXP_TABLE[i2 - 5]) ^ EXP_TABLE[i2 - 6]) ^ EXP_TABLE[i2 - 8];
        }
        for (int i3 = 0; i3 < 255; i3++) {
            LOG_TABLE[EXP_TABLE[i3]] = i3;
        }
    }

    public static int glog(int n) {
        if (n >= 1) {
            return LOG_TABLE[n];
        }
        throw new ArithmeticException("log(" + n + ")");
    }

    public static int gexp(int n) {
        while (n < 0) {
            n += 255;
        }
        while (n >= 256) {
            n += UIMsg.m_AppUI.V_WM_ADDLISTUPDATE;
        }
        return EXP_TABLE[n];
    }
}

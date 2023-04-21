package com.lvrenyang.qrcode;

import android.support.v4.media.TransportMediator;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import com.alibaba.fastjson.asm.Opcodes;
import com.flyco.tablayout.BuildConfig;
import java.io.UnsupportedEncodingException;

class QRUtil {
    private static final int G15 = 1335;
    private static final int G15_MASK = 21522;
    private static final int G18 = 7973;
    private static int[][][] MAX_LENGTH = {new int[][]{new int[]{41, 25, 17, 10}, new int[]{34, 20, 14, 8}, new int[]{27, 16, 11, 7}, new int[]{17, 10, 7, 4}}, new int[][]{new int[]{77, 47, 32, 20}, new int[]{63, 38, 26, 16}, new int[]{48, 29, 20, 12}, new int[]{34, 20, 14, 8}}, new int[][]{new int[]{TransportMediator.KEYCODE_MEDIA_PAUSE, 77, 53, 32}, new int[]{101, 61, 42, 26}, new int[]{77, 47, 32, 20}, new int[]{58, 35, 24, 15}}, new int[][]{new int[]{Opcodes.NEW, 114, 78, 48}, new int[]{Opcodes.FCMPL, 90, 62, 38}, new int[]{111, 67, 46, 28}, new int[]{82, 50, 34, 21}}, new int[][]{new int[]{255, Opcodes.IFNE, 106, 65}, new int[]{BuildConfig.VERSION_CODE, 122, 84, 52}, new int[]{144, 87, 60, 37}, new int[]{106, 64, 44, 27}}, new int[][]{new int[]{322, 195, 134, 82}, new int[]{255, Opcodes.IFNE, 106, 65}, new int[]{Opcodes.GETSTATIC, 108, 74, 45}, new int[]{139, 84, 58, 36}}, new int[][]{new int[]{370, 224, Opcodes.IFNE, 95}, new int[]{293, Opcodes.GETSTATIC, 122, 75}, new int[]{207, 125, 86, 53}, new int[]{Opcodes.IFNE, 93, 64, 39}}, new int[][]{new int[]{461, 279, 192, 118}, new int[]{365, 221, 152, 93}, new int[]{259, 157, 108, 66}, new int[]{BuildConfig.VERSION_CODE, 122, 84, 52}}, new int[][]{new int[]{552, 335, 230, 141}, new int[]{432, 262, Opcodes.GETFIELD, 111}, new int[]{312, 189, TransportMediator.KEYCODE_MEDIA_RECORD, 80}, new int[]{235, 143, 98, 60}}, new int[][]{new int[]{652, 395, 271, 167}, new int[]{513, 311, 213, 131}, new int[]{364, 221, Opcodes.DCMPL, 93}, new int[]{288, 174, 119, 74}}, new int[][]{new int[]{652, 395, 271, 167}, new int[]{513, 311, 213, 131}, new int[]{364, 221, Opcodes.DCMPL, 93}, new int[]{288, 174, 119, 74}}, new int[][]{new int[]{652, 395, 271, 167}, new int[]{513, 311, 213, 131}, new int[]{364, 221, Opcodes.DCMPL, 93}, new int[]{288, 174, 119, 74}}, new int[][]{new int[]{652, 395, 271, 167}, new int[]{513, 311, 213, 131}, new int[]{364, 221, Opcodes.DCMPL, 93}, new int[]{288, 174, 119, 74}}, new int[][]{new int[]{652, 395, 271, 167}, new int[]{513, 311, 213, 131}, new int[]{364, 221, Opcodes.DCMPL, 93}, new int[]{288, 174, 119, 74}}, new int[][]{new int[]{652, 395, 271, 167}, new int[]{513, 311, 213, 131}, new int[]{364, 221, Opcodes.DCMPL, 93}, new int[]{288, 174, 119, 74}}, new int[][]{new int[]{652, 395, 271, 167}, new int[]{513, 311, 213, 131}, new int[]{364, 221, Opcodes.DCMPL, 93}, new int[]{288, 174, 119, 74}}, new int[][]{new int[]{652, 395, 271, 167}, new int[]{513, 311, 213, 131}, new int[]{364, 221, Opcodes.DCMPL, 93}, new int[]{288, 174, 119, 74}}, new int[][]{new int[]{652, 395, 271, 167}, new int[]{513, 311, 213, 131}, new int[]{364, 221, Opcodes.DCMPL, 93}, new int[]{288, 174, 119, 74}}, new int[][]{new int[]{652, 395, 271, 167}, new int[]{513, 311, 213, 131}, new int[]{364, 221, Opcodes.DCMPL, 93}, new int[]{288, 174, 119, 74}}, new int[][]{new int[]{652, 395, 271, 167}, new int[]{513, 311, 213, 131}, new int[]{364, 221, Opcodes.DCMPL, 93}, new int[]{288, 174, 119, 74}}};
    private static final int[][] PATTERN_POSITION_TABLE = {new int[0], new int[]{6, 18}, new int[]{6, 22}, new int[]{6, 26}, new int[]{6, 30}, new int[]{6, 34}, new int[]{6, 22, 38}, new int[]{6, 24, 42}, new int[]{6, 26, 46}, new int[]{6, 28, 50}, new int[]{6, 30, 54}, new int[]{6, 32, 58}, new int[]{6, 34, 62}, new int[]{6, 26, 46, 66}, new int[]{6, 26, 48, 70}, new int[]{6, 26, 50, 74}, new int[]{6, 30, 54, 78}, new int[]{6, 30, 56, 82}, new int[]{6, 30, 58, 86}, new int[]{6, 34, 62, 90}, new int[]{6, 28, 50, 72, 94}, new int[]{6, 26, 50, 74, 98}, new int[]{6, 30, 54, 78, 102}, new int[]{6, 28, 54, 80, 106}, new int[]{6, 32, 58, 84, 110}, new int[]{6, 30, 58, 86, 114}, new int[]{6, 34, 62, 90, 118}, new int[]{6, 26, 50, 74, 98, 122}, new int[]{6, 30, 54, 78, 102, 126}, new int[]{6, 26, 52, 78, 104, TransportMediator.KEYCODE_MEDIA_RECORD}, new int[]{6, 30, 56, 82, 108, 134}, new int[]{6, 34, 60, 86, 112, 138}, new int[]{6, 30, 58, 86, 114, 142}, new int[]{6, 34, 62, 90, 118, 146}, new int[]{6, 30, 54, 78, 102, 126, 150}, new int[]{6, 24, 50, 76, 102, 128, Opcodes.IFNE}, new int[]{6, 28, 54, 80, 106, 132, Opcodes.IFLE}, new int[]{6, 32, 58, 84, 110, 136, 162}, new int[]{6, 26, 54, 82, 110, 138, Opcodes.IF_ACMPNE}, new int[]{6, 30, 58, 86, 114, 142, 170}};

    private QRUtil() {
    }

    public static String getJISEncoding() {
        return "SJIS";
    }

    public static String getUTF8Encoding() {
        return "UTF-8";
    }

    public static int[] getPatternPosition(int typeNumber) {
        return PATTERN_POSITION_TABLE[typeNumber - 1];
    }

    public static int getMaxLength(int typeNumber, int mode, int errorCorrectLevel) {
        int e;
        int m;
        int t = typeNumber - 1;
        switch (errorCorrectLevel) {
            case 0:
                e = 1;
                break;
            case 1:
                e = 0;
                break;
            case 2:
                e = 3;
                break;
            case 3:
                e = 2;
                break;
            default:
                throw new IllegalArgumentException("e:" + errorCorrectLevel);
        }
        switch (mode) {
            case 1:
                m = 0;
                break;
            case 2:
                m = 1;
                break;
            case 4:
                m = 2;
                break;
            case 8:
                m = 3;
                break;
            default:
                throw new IllegalArgumentException("m:" + mode);
        }
        return MAX_LENGTH[t][e][m];
    }

    public static Polynomial getErrorCorrectPolynomial(int errorCorrectLength) {
        Polynomial a = new Polynomial(new int[]{1});
        for (int i = 0; i < errorCorrectLength; i++) {
            a = a.multiply(new Polynomial(new int[]{1, QRMath.gexp(i)}));
        }
        return a;
    }

    public static boolean getMask(int maskPattern, int i, int j) {
        switch (maskPattern) {
            case 0:
                if ((i + j) % 2 == 0) {
                    return true;
                }
                return false;
            case 1:
                if (i % 2 != 0) {
                    return false;
                }
                return true;
            case 2:
                if (j % 3 != 0) {
                    return false;
                }
                return true;
            case 3:
                if ((i + j) % 3 != 0) {
                    return false;
                }
                return true;
            case 4:
                if (((i / 2) + (j / 3)) % 2 != 0) {
                    return false;
                }
                return true;
            case 5:
                if (((i * j) % 2) + ((i * j) % 3) != 0) {
                    return false;
                }
                return true;
            case 6:
                if ((((i * j) % 2) + ((i * j) % 3)) % 2 != 0) {
                    return false;
                }
                return true;
            case 7:
                return (((i * j) % 3) + ((i + j) % 2)) % 2 == 0;
            default:
                throw new IllegalArgumentException("mask:" + maskPattern);
        }
    }

    public static int getLostPoint(QRCode qrCode) {
        int moduleCount = qrCode.getModuleCount();
        int lostPoint = 0;
        for (int row = 0; row < moduleCount; row++) {
            for (int col = 0; col < moduleCount; col++) {
                int sameCount = 0;
                boolean dark = qrCode.isDark(row, col);
                for (int r = -1; r <= 1; r++) {
                    if (row + r >= 0 && moduleCount > row + r) {
                        for (int c = -1; c <= 1; c++) {
                            if (col + c >= 0 && moduleCount > col + c && (!(r == 0 && c == 0) && dark == qrCode.isDark(row + r, col + c))) {
                                sameCount++;
                            }
                        }
                    }
                }
                if (sameCount > 5) {
                    lostPoint += (sameCount + 3) - 5;
                }
            }
        }
        for (int row2 = 0; row2 < moduleCount - 1; row2++) {
            for (int col2 = 0; col2 < moduleCount - 1; col2++) {
                int count = 0;
                if (qrCode.isDark(row2, col2)) {
                    count = 0 + 1;
                }
                if (qrCode.isDark(row2 + 1, col2)) {
                    count++;
                }
                if (qrCode.isDark(row2, col2 + 1)) {
                    count++;
                }
                if (qrCode.isDark(row2 + 1, col2 + 1)) {
                    count++;
                }
                if (count == 0 || count == 4) {
                    lostPoint += 3;
                }
            }
        }
        for (int row3 = 0; row3 < moduleCount; row3++) {
            for (int col3 = 0; col3 < moduleCount - 6; col3++) {
                if (qrCode.isDark(row3, col3) && !qrCode.isDark(row3, col3 + 1) && qrCode.isDark(row3, col3 + 2) && qrCode.isDark(row3, col3 + 3) && qrCode.isDark(row3, col3 + 4) && !qrCode.isDark(row3, col3 + 5) && qrCode.isDark(row3, col3 + 6)) {
                    lostPoint += 40;
                }
            }
        }
        for (int col4 = 0; col4 < moduleCount; col4++) {
            for (int row4 = 0; row4 < moduleCount - 6; row4++) {
                if (qrCode.isDark(row4, col4) && !qrCode.isDark(row4 + 1, col4) && qrCode.isDark(row4 + 2, col4) && qrCode.isDark(row4 + 3, col4) && qrCode.isDark(row4 + 4, col4) && !qrCode.isDark(row4 + 5, col4) && qrCode.isDark(row4 + 6, col4)) {
                    lostPoint += 40;
                }
            }
        }
        int darkCount = 0;
        for (int col5 = 0; col5 < moduleCount; col5++) {
            for (int row5 = 0; row5 < moduleCount; row5++) {
                if (qrCode.isDark(row5, col5)) {
                    darkCount++;
                }
            }
        }
        return lostPoint + ((Math.abs((((darkCount * 100) / moduleCount) / moduleCount) - 50) / 5) * 10);
    }

    public static int getMode(String s) {
        if (isAlphaNum(s)) {
            if (isNumber(s)) {
                return 1;
            }
            return 2;
        } else if (isKanji(s)) {
            return 8;
        } else {
            return 4;
        }
    }

    private static boolean isNumber(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ('0' > c || c > '9') {
                return false;
            }
        }
        return true;
    }

    private static boolean isAlphaNum(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (('0' > c || c > '9') && (('A' > c || c > 'Z') && " $%*+-./:".indexOf(c) == -1)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isKanji(String s) {
        try {
            byte[] data = s.getBytes(getJISEncoding());
            int i = 0;
            while (i + 1 < data.length) {
                int c = ((data[i] & BLEServiceApi.CONNECTED_STATUS) << 8) | (data[i + 1] & 255);
                if ((33088 > c || c > 40956) && (57408 > c || c > 60351)) {
                    return false;
                }
                i += 2;
            }
            if (i >= data.length) {
                return true;
            }
            return false;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static int getBCHTypeInfo(int data) {
        int d = data << 10;
        while (getBCHDigit(d) - getBCHDigit(G15) >= 0) {
            d ^= G15 << (getBCHDigit(d) - getBCHDigit(G15));
        }
        return ((data << 10) | d) ^ G15_MASK;
    }

    public static int getBCHTypeNumber(int data) {
        int d = data << 12;
        while (getBCHDigit(d) - getBCHDigit(G18) >= 0) {
            d ^= G18 << (getBCHDigit(d) - getBCHDigit(G18));
        }
        return (data << 12) | d;
    }

    private static int getBCHDigit(int data) {
        int digit = 0;
        while (data != 0) {
            digit++;
            data >>>= 1;
        }
        return digit;
    }
}

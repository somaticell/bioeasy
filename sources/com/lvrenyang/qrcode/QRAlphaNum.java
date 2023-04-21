package com.lvrenyang.qrcode;

class QRAlphaNum extends QRData {
    public QRAlphaNum(String data) {
        super(2, data);
    }

    public void write(BitBuffer buffer) {
        char[] c = getData().toCharArray();
        int i = 0;
        while (i + 1 < c.length) {
            buffer.put((getCode(c[i]) * 45) + getCode(c[i + 1]), 11);
            i += 2;
        }
        if (i < c.length) {
            buffer.put(getCode(c[i]), 6);
        }
    }

    public int getLength() {
        return getData().length();
    }

    private static int getCode(char c) {
        if ('0' <= c && c <= '9') {
            return c - '0';
        }
        if ('A' <= c && c <= 'Z') {
            return (c - 'A') + 10;
        }
        switch (c) {
            case ' ':
                return 36;
            case '$':
                return 37;
            case '%':
                return 38;
            case '*':
                return 39;
            case '+':
                return 40;
            case '-':
                return 41;
            case '.':
                return 42;
            case '/':
                return 43;
            case ':':
                return 44;
            default:
                throw new IllegalArgumentException("illegal char :" + c);
        }
    }
}

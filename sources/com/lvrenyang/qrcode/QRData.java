package com.lvrenyang.qrcode;

abstract class QRData {
    private final String data;
    private final int mode;

    public abstract int getLength();

    public abstract void write(BitBuffer bitBuffer);

    protected QRData(int mode2, String data2) {
        this.mode = mode2;
        this.data = data2;
    }

    public int getMode() {
        return this.mode;
    }

    public String getData() {
        return this.data;
    }

    public int getLengthInBits(int type) {
        if (1 <= type && type < 10) {
            switch (this.mode) {
                case 1:
                    return 10;
                case 2:
                    return 9;
                case 4:
                    return 8;
                case 8:
                    return 8;
                default:
                    throw new IllegalArgumentException("mode:" + this.mode);
            }
        } else if (type < 27) {
            switch (this.mode) {
                case 1:
                    return 12;
                case 2:
                    return 11;
                case 4:
                    return 16;
                case 8:
                    return 10;
                default:
                    throw new IllegalArgumentException("mode:" + this.mode);
            }
        } else if (type < 41) {
            switch (this.mode) {
                case 1:
                    return 14;
                case 2:
                    return 13;
                case 4:
                    return 16;
                case 8:
                    return 12;
                default:
                    throw new IllegalArgumentException("mode:" + this.mode);
            }
        } else {
            throw new IllegalArgumentException("type:" + type);
        }
    }
}

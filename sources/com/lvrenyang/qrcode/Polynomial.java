package com.lvrenyang.qrcode;

import cn.com.bioeasy.app.utils.ListUtils;

class Polynomial {
    private final int[] num;

    public Polynomial(int[] num2) {
        this(num2, 0);
    }

    public Polynomial(int[] num2, int shift) {
        int offset = 0;
        while (offset < num2.length && num2[offset] == 0) {
            offset++;
        }
        this.num = new int[((num2.length - offset) + shift)];
        System.arraycopy(num2, offset, this.num, 0, num2.length - offset);
    }

    public int get(int index) {
        return this.num[index];
    }

    public int getLength() {
        return this.num.length;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < getLength(); i++) {
            if (i > 0) {
                buffer.append(ListUtils.DEFAULT_JOIN_SEPARATOR);
            }
            buffer.append(get(i));
        }
        return buffer.toString();
    }

    public String toLogString() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < getLength(); i++) {
            if (i > 0) {
                buffer.append(ListUtils.DEFAULT_JOIN_SEPARATOR);
            }
            buffer.append(QRMath.glog(get(i)));
        }
        return buffer.toString();
    }

    public Polynomial multiply(Polynomial e) {
        int[] num2 = new int[((getLength() + e.getLength()) - 1)];
        for (int i = 0; i < getLength(); i++) {
            for (int j = 0; j < e.getLength(); j++) {
                int i2 = i + j;
                num2[i2] = num2[i2] ^ QRMath.gexp(QRMath.glog(get(i)) + QRMath.glog(e.get(j)));
            }
        }
        return new Polynomial(num2);
    }

    /* Debug info: failed to restart local var, previous not found, register: 6 */
    public Polynomial mod(Polynomial e) {
        if (getLength() - e.getLength() < 0) {
            return this;
        }
        int ratio = QRMath.glog(get(0)) - QRMath.glog(e.get(0));
        int[] num2 = new int[getLength()];
        for (int i = 0; i < getLength(); i++) {
            num2[i] = get(i);
        }
        for (int i2 = 0; i2 < e.getLength(); i2++) {
            num2[i2] = num2[i2] ^ QRMath.gexp(QRMath.glog(e.get(i2)) + ratio);
        }
        return new Polynomial(num2).mod(e);
    }
}

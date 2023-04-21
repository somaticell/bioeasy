package com.lvrenyang.qrcode;

class BitBuffer {
    private byte[] buffer = new byte[this.inclements];
    private int inclements = 32;
    private int length = 0;

    public byte[] getBuffer() {
        return this.buffer;
    }

    public int getLengthInBits() {
        return this.length;
    }

    public String toString() {
        StringBuilder buffer2 = new StringBuilder();
        for (int i = 0; i < getLengthInBits(); i++) {
            buffer2.append(get(i) ? '1' : '0');
        }
        return buffer2.toString();
    }

    private boolean get(int index) {
        return ((this.buffer[index / 8] >>> (7 - (index % 8))) & 1) == 1;
    }

    public void put(int num, int length2) {
        for (int i = 0; i < length2; i++) {
            put(((num >>> ((length2 - i) + -1)) & 1) == 1);
        }
    }

    public void put(boolean bit) {
        if (this.length == this.buffer.length * 8) {
            byte[] newBuffer = new byte[(this.buffer.length + this.inclements)];
            System.arraycopy(this.buffer, 0, newBuffer, 0, this.buffer.length);
            this.buffer = newBuffer;
        }
        if (bit) {
            byte[] bArr = this.buffer;
            int i = this.length / 8;
            bArr[i] = (byte) (bArr[i] | (128 >>> (this.length % 8)));
        }
        this.length++;
    }
}

package jxl.biff.formula;

import jxl.biff.IntegerHelper;

class Name extends Operand implements ParsedThing {
    public int read(byte[] data, int pos) {
        int i = IntegerHelper.getInt(data[pos], data[pos + 1]);
        int i2 = IntegerHelper.getInt(data[pos + 2], data[pos + 3]);
        return 6;
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        return new byte[6];
    }

    public void getString(StringBuffer buf) {
        buf.append("[Name record not implemented]");
    }
}

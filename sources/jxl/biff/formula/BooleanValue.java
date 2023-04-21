package jxl.biff.formula;

class BooleanValue extends Operand implements ParsedThing {
    private boolean value;

    public BooleanValue() {
    }

    public BooleanValue(String s) {
        this.value = Boolean.valueOf(s).booleanValue();
    }

    public int read(byte[] data, int pos) {
        this.value = data[pos] == 1;
        return 1;
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        int i = 0;
        byte[] data = new byte[2];
        data[0] = Token.BOOL.getCode();
        if (this.value) {
            i = 1;
        }
        data[1] = (byte) i;
        return data;
    }

    public void getString(StringBuffer buf) {
        buf.append(new Boolean(this.value).toString());
    }
}

package jxl.biff.formula;

class MissingArg extends Operand implements ParsedThing {
    public int read(byte[] data, int pos) {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        return new byte[]{Token.MISSING_ARG.getCode()};
    }

    public void getString(StringBuffer buf) {
    }
}

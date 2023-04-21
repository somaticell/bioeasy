package jxl.biff.drawing;

class SplitMenuColors extends EscherAtom {
    private byte[] data;

    public SplitMenuColors(EscherRecordData erd) {
        super(erd);
    }

    public SplitMenuColors() {
        super(EscherRecordType.SPLIT_MENU_COLORS);
        setVersion(0);
        setInstance(4);
        this.data = new byte[]{13, 0, 0, 8, 12, 0, 0, 8, 23, 0, 0, 8, -9, 0, 0, 16};
    }

    /* access modifiers changed from: package-private */
    public byte[] getData() {
        return setHeaderData(this.data);
    }
}

package jxl.biff.drawing;

class Spgr extends EscherAtom {
    private byte[] data;

    public Spgr(EscherRecordData erd) {
        super(erd);
    }

    public Spgr() {
        super(EscherRecordType.SPGR);
        setVersion(1);
        this.data = new byte[16];
    }

    /* access modifiers changed from: package-private */
    public byte[] getData() {
        return setHeaderData(this.data);
    }
}

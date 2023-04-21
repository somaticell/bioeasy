package jxl.biff.drawing;

abstract class EscherRecord {
    protected static final int HEADER_LENGTH = 8;
    protected EscherRecordData data;

    /* access modifiers changed from: package-private */
    public abstract byte[] getData();

    protected EscherRecord(EscherRecordData erd) {
        this.data = erd;
    }

    protected EscherRecord(EscherRecordType type) {
        this.data = new EscherRecordData(type);
    }

    /* access modifiers changed from: protected */
    public void setContainer(boolean cont) {
        this.data.setContainer(cont);
    }

    public int getLength() {
        return this.data.getLength() + 8;
    }

    /* access modifiers changed from: protected */
    public final EscherStream getEscherStream() {
        return this.data.getEscherStream();
    }

    /* access modifiers changed from: protected */
    public final int getPos() {
        return this.data.getPos();
    }

    /* access modifiers changed from: protected */
    public final int getInstance() {
        return this.data.getInstance();
    }

    /* access modifiers changed from: protected */
    public final void setInstance(int i) {
        this.data.setInstance(i);
    }

    /* access modifiers changed from: protected */
    public final void setVersion(int v) {
        this.data.setVersion(v);
    }

    public EscherRecordType getType() {
        return this.data.getType();
    }

    /* access modifiers changed from: package-private */
    public final byte[] setHeaderData(byte[] d) {
        return this.data.setHeaderData(d);
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        return this.data.getBytes();
    }

    /* access modifiers changed from: protected */
    public int getStreamLength() {
        return this.data.getStreamLength();
    }
}

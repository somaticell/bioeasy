package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class IndexRecord extends WritableRecordData {
    private int blocks;
    private int bofPosition;
    private byte[] data = new byte[((this.blocks * 4) + 16)];
    private int dataPos = 16;
    private int rows;

    public IndexRecord(int pos, int r, int bl) {
        super(Type.INDEX);
        this.bofPosition = pos;
        this.rows = r;
        this.blocks = bl;
    }

    /* access modifiers changed from: protected */
    public byte[] getData() {
        IntegerHelper.getFourBytes(this.rows, this.data, 8);
        return this.data;
    }

    /* access modifiers changed from: package-private */
    public void addBlockPosition(int pos) {
        IntegerHelper.getFourBytes(pos - this.bofPosition, this.data, this.dataPos);
        this.dataPos += 4;
    }

    /* access modifiers changed from: package-private */
    public void setDataStartPosition(int pos) {
        IntegerHelper.getFourBytes(pos - this.bofPosition, this.data, 12);
    }
}

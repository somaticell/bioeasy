package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class DimensionRecord extends WritableRecordData {
    private byte[] data = new byte[14];
    private int numCols;
    private int numRows;

    public DimensionRecord(int r, int c) {
        super(Type.DIMENSION);
        this.numRows = r;
        this.numCols = c;
        IntegerHelper.getFourBytes(this.numRows, this.data, 4);
        IntegerHelper.getTwoBytes(this.numCols, this.data, 10);
    }

    /* access modifiers changed from: protected */
    public byte[] getData() {
        return this.data;
    }
}

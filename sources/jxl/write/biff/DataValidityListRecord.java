package jxl.write.biff;

import jxl.biff.DValParser;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class DataValidityListRecord extends WritableRecordData {
    private byte[] data;
    private DValParser dvalParser;

    DataValidityListRecord(jxl.read.biff.DataValidityListRecord dvlr) {
        super(Type.DVAL);
        this.data = dvlr.getData();
    }

    DataValidityListRecord(DataValidityListRecord dvlr) {
        super(Type.DVAL);
        this.data = new byte[dvlr.data.length];
        System.arraycopy(dvlr.data, 0, this.data, 0, this.data.length);
    }

    public byte[] getData() {
        if (this.dvalParser == null) {
            return this.data;
        }
        return this.dvalParser.getData();
    }

    /* access modifiers changed from: package-private */
    public void dvRemoved() {
        if (this.dvalParser == null) {
            this.dvalParser = new DValParser(this.data);
        }
        this.dvalParser.dvRemoved();
    }

    public boolean hasDVRecords() {
        if (this.dvalParser != null && this.dvalParser.getNumberOfDVRecords() <= 0) {
            return false;
        }
        return true;
    }
}

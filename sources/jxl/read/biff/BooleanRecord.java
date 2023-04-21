package jxl.read.biff;

import common.Assert;
import jxl.BooleanCell;
import jxl.CellType;
import jxl.biff.FormattingRecords;

class BooleanRecord extends CellValue implements BooleanCell {
    private boolean error = false;
    private boolean value = false;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BooleanRecord(Record t, FormattingRecords fr, SheetImpl si) {
        super(t, fr, si);
        boolean z = true;
        byte[] data = getRecord().getData();
        this.error = data[7] == 1;
        if (!this.error) {
            this.value = data[6] != 1 ? false : z;
        }
    }

    public boolean isError() {
        return this.error;
    }

    public boolean getValue() {
        return this.value;
    }

    public String getContents() {
        Assert.verify(!isError());
        return new Boolean(this.value).toString();
    }

    public CellType getType() {
        return CellType.BOOLEAN;
    }

    public Record getRecord() {
        return super.getRecord();
    }
}

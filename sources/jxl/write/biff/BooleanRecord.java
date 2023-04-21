package jxl.write.biff;

import jxl.BooleanCell;
import jxl.CellType;
import jxl.biff.Type;
import jxl.format.CellFormat;

public abstract class BooleanRecord extends CellValue {
    private boolean value;

    protected BooleanRecord(int c, int r, boolean val) {
        super(Type.BOOLERR, c, r);
        this.value = val;
    }

    protected BooleanRecord(int c, int r, boolean val, CellFormat st) {
        super(Type.BOOLERR, c, r, st);
        this.value = val;
    }

    protected BooleanRecord(BooleanCell nc) {
        super(Type.BOOLERR, nc);
        this.value = nc.getValue();
    }

    protected BooleanRecord(int c, int r, BooleanRecord br) {
        super(Type.BOOLERR, c, r, (CellValue) br);
        this.value = br.value;
    }

    public boolean getValue() {
        return this.value;
    }

    public String getContents() {
        return new Boolean(this.value).toString();
    }

    public CellType getType() {
        return CellType.BOOLEAN;
    }

    /* access modifiers changed from: protected */
    public void setValue(boolean val) {
        this.value = val;
    }

    public byte[] getData() {
        byte[] celldata = super.getData();
        byte[] data = new byte[(celldata.length + 2)];
        System.arraycopy(celldata, 0, data, 0, celldata.length);
        if (this.value) {
            data[celldata.length] = 1;
        }
        return data;
    }
}

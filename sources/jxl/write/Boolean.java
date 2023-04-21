package jxl.write;

import jxl.BooleanCell;
import jxl.format.CellFormat;
import jxl.write.biff.BooleanRecord;

public class Boolean extends BooleanRecord implements WritableCell, BooleanCell {
    public Boolean(int c, int r, boolean val) {
        super(c, r, val);
    }

    public Boolean(int c, int r, boolean val, CellFormat st) {
        super(c, r, val, st);
    }

    public Boolean(BooleanCell nc) {
        super(nc);
    }

    protected Boolean(int col, int row, Boolean b) {
        super(col, row, (BooleanRecord) b);
    }

    public void setValue(boolean val) {
        super.setValue(val);
    }

    public WritableCell copyTo(int col, int row) {
        return new Boolean(col, row, this);
    }
}

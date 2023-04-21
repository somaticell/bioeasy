package jxl.write;

import jxl.NumberCell;
import jxl.format.CellFormat;
import jxl.write.biff.NumberRecord;

public class Number extends NumberRecord implements WritableCell, NumberCell {
    public Number(int c, int r, double val) {
        super(c, r, val);
    }

    public Number(int c, int r, double val, CellFormat st) {
        super(c, r, val, st);
    }

    public Number(NumberCell nc) {
        super(nc);
    }

    public void setValue(double val) {
        super.setValue(val);
    }

    protected Number(int col, int row, Number n) {
        super(col, row, (NumberRecord) n);
    }

    public WritableCell copyTo(int col, int row) {
        return new Number(col, row, this);
    }
}

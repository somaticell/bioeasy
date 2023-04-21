package jxl.write;

import jxl.format.CellFormat;
import jxl.write.biff.FormulaRecord;

public class Formula extends FormulaRecord implements WritableCell {
    public Formula(int c, int r, String form) {
        super(c, r, form);
    }

    public Formula(int c, int r, String form, CellFormat st) {
        super(c, r, form, st);
    }

    protected Formula(int c, int r, Formula f) {
        super(c, r, (FormulaRecord) f);
    }

    public WritableCell copyTo(int col, int row) {
        return new Formula(col, row, this);
    }
}

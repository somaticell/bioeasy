package jxl.write;

import jxl.LabelCell;
import jxl.format.CellFormat;
import jxl.write.biff.LabelRecord;

public class Label extends LabelRecord implements WritableCell, LabelCell {
    public Label(int c, int r, String cont) {
        super(c, r, cont);
    }

    public Label(int c, int r, String cont, CellFormat st) {
        super(c, r, cont, st);
    }

    protected Label(int col, int row, Label l) {
        super(col, row, (LabelRecord) l);
    }

    public Label(LabelCell lc) {
        super(lc);
    }

    public void setString(String s) {
        super.setString(s);
    }

    public WritableCell copyTo(int col, int row) {
        return new Label(col, row, this);
    }
}

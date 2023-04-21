package jxl.biff;

import jxl.CellFeatures;
import jxl.CellType;
import jxl.format.Alignment;
import jxl.format.CellFormat;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.VerticalAlignment;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;

public class EmptyCell implements WritableCell {
    private int col;
    private int row;

    public EmptyCell(int c, int r) {
        this.row = r;
        this.col = c;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.col;
    }

    public CellType getType() {
        return CellType.EMPTY;
    }

    public String getContents() {
        return "";
    }

    public CellFormat getCellFormat() {
        return null;
    }

    public void setHidden(boolean flag) {
    }

    public void setLocked(boolean flag) {
    }

    public void setAlignment(Alignment align) {
    }

    public void setVerticalAlignment(VerticalAlignment valign) {
    }

    public void setBorder(Border border, BorderLineStyle line) {
    }

    public void setCellFormat(CellFormat cf) {
    }

    public void setCellFormat(jxl.CellFormat cf) {
    }

    public boolean isHidden() {
        return false;
    }

    public WritableCell copyTo(int c, int r) {
        return new EmptyCell(c, r);
    }

    public CellFeatures getCellFeatures() {
        return null;
    }

    public WritableCellFeatures getWritableCellFeatures() {
        return null;
    }

    public void setCellFeatures(WritableCellFeatures wcf) {
    }
}

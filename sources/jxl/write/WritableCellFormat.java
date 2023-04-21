package jxl.write;

import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.format.Pattern;
import jxl.format.VerticalAlignment;
import jxl.write.biff.CellXFRecord;

public class WritableCellFormat extends CellXFRecord {
    public WritableCellFormat() {
        this(WritableWorkbook.ARIAL_10_PT, NumberFormats.DEFAULT);
    }

    public WritableCellFormat(WritableFont font) {
        this(font, NumberFormats.DEFAULT);
    }

    public WritableCellFormat(DisplayFormat format) {
        this(WritableWorkbook.ARIAL_10_PT, format);
    }

    public WritableCellFormat(WritableFont font, DisplayFormat format) {
        super(font, format);
    }

    public WritableCellFormat(CellFormat format) {
        super(format);
    }

    public void setAlignment(Alignment a) throws WriteException {
        super.setAlignment(a);
    }

    public void setVerticalAlignment(VerticalAlignment va) throws WriteException {
        super.setVerticalAlignment(va);
    }

    public void setOrientation(Orientation o) throws WriteException {
        super.setOrientation(o);
    }

    public void setWrap(boolean w) throws WriteException {
        super.setWrap(w);
    }

    public void setBorder(Border b, BorderLineStyle ls) throws WriteException {
        super.setBorder(b, ls, Colour.BLACK);
    }

    public void setBorder(Border b, BorderLineStyle ls, Colour c) throws WriteException {
        super.setBorder(b, ls, c);
    }

    public void setBackground(Colour c) throws WriteException {
        setBackground(c, Pattern.SOLID);
    }

    public void setBackground(Colour c, Pattern p) throws WriteException {
        super.setBackground(c, p);
    }

    public void setShrinkToFit(boolean s) throws WriteException {
        super.setShrinkToFit(s);
    }

    public void setIndentation(int i) throws WriteException {
        super.setIndentation(i);
    }

    public void setLocked(boolean l) throws WriteException {
        super.setLocked(l);
    }
}

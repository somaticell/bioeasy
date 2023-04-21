package jxl.biff;

import common.Logger;
import jxl.Cell;
import jxl.Range;
import jxl.Sheet;

public class RangeImpl implements Range {
    static Class class$jxl$biff$RangeImpl;
    private static Logger logger;
    private int column1;
    private int column2;
    private int row1;
    private int row2;
    private int sheet1;
    private int sheet2;
    private WorkbookMethods workbook;

    static {
        Class cls;
        if (class$jxl$biff$RangeImpl == null) {
            cls = class$("jxl.biff.RangeImpl");
            class$jxl$biff$RangeImpl = cls;
        } else {
            cls = class$jxl$biff$RangeImpl;
        }
        logger = Logger.getLogger(cls);
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    public RangeImpl(WorkbookMethods w, int s1, int c1, int r1, int s2, int c2, int r2) {
        this.workbook = w;
        this.sheet1 = s1;
        this.sheet2 = s2;
        this.row1 = r1;
        this.row2 = r2;
        this.column1 = c1;
        this.column2 = c2;
    }

    public Cell getTopLeft() {
        Sheet s = this.workbook.getReadSheet(this.sheet1);
        if (this.column1 >= s.getColumns() || this.row1 >= s.getRows()) {
            return new EmptyCell(this.column1, this.row1);
        }
        return s.getCell(this.column1, this.row1);
    }

    public Cell getBottomRight() {
        Sheet s = this.workbook.getReadSheet(this.sheet2);
        if (this.column2 >= s.getColumns() || this.row2 >= s.getRows()) {
            return new EmptyCell(this.column2, this.row2);
        }
        return s.getCell(this.column2, this.row2);
    }

    public int getFirstSheetIndex() {
        return this.sheet1;
    }

    public int getLastSheetIndex() {
        return this.sheet2;
    }
}

package jxl.read.biff;

import common.Logger;
import jxl.Cell;
import jxl.CellFeatures;
import jxl.CellType;
import jxl.biff.FormattingRecords;
import jxl.format.CellFormat;

class MulBlankCell implements Cell, CellFeaturesAccessor {
    static Class class$jxl$read$biff$MulBlankCell;
    private static Logger logger;
    private CellFormat cellFormat;
    private int column;
    private CellFeatures features;
    private FormattingRecords formattingRecords;
    private boolean initialized = false;
    private int row;
    private SheetImpl sheet;
    private int xfIndex;

    static {
        Class cls;
        if (class$jxl$read$biff$MulBlankCell == null) {
            cls = class$("jxl.read.biff.MulBlankCell");
            class$jxl$read$biff$MulBlankCell = cls;
        } else {
            cls = class$jxl$read$biff$MulBlankCell;
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

    public MulBlankCell(int r, int c, int xfi, FormattingRecords fr, SheetImpl si) {
        this.row = r;
        this.column = c;
        this.xfIndex = xfi;
        this.formattingRecords = fr;
        this.sheet = si;
    }

    public final int getRow() {
        return this.row;
    }

    public final int getColumn() {
        return this.column;
    }

    public String getContents() {
        return "";
    }

    public CellType getType() {
        return CellType.EMPTY;
    }

    public CellFormat getCellFormat() {
        if (!this.initialized) {
            this.cellFormat = this.formattingRecords.getXFRecord(this.xfIndex);
            this.initialized = true;
        }
        return this.cellFormat;
    }

    public boolean isHidden() {
        ColumnInfoRecord cir = this.sheet.getColumnInfo(this.column);
        if (cir != null && cir.getWidth() == 0) {
            return true;
        }
        RowRecord rr = this.sheet.getRowInfo(this.row);
        if (rr == null || (rr.getRowHeight() != 0 && !rr.isCollapsed())) {
            return false;
        }
        return true;
    }

    public CellFeatures getCellFeatures() {
        return this.features;
    }

    public void setCellFeatures(CellFeatures cf) {
        if (this.features != null) {
            logger.warn("current cell features not null - overwriting");
        }
        this.features = cf;
    }
}

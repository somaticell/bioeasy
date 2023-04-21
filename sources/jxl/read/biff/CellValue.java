package jxl.read.biff;

import common.Logger;
import jxl.Cell;
import jxl.CellFeatures;
import jxl.biff.FormattingRecords;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.XFRecord;
import jxl.format.CellFormat;

public abstract class CellValue extends RecordData implements Cell, CellFeaturesAccessor {
    static Class class$jxl$read$biff$CellValue;
    private static Logger logger;
    private int column;
    private CellFeatures features;
    private XFRecord format;
    private FormattingRecords formattingRecords;
    private boolean initialized = false;
    private int row;
    private SheetImpl sheet;
    private int xfIndex;

    static {
        Class cls;
        if (class$jxl$read$biff$CellValue == null) {
            cls = class$("jxl.read.biff.CellValue");
            class$jxl$read$biff$CellValue = cls;
        } else {
            cls = class$jxl$read$biff$CellValue;
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

    protected CellValue(Record t, FormattingRecords fr, SheetImpl si) {
        super(t);
        byte[] data = getRecord().getData();
        this.row = IntegerHelper.getInt(data[0], data[1]);
        this.column = IntegerHelper.getInt(data[2], data[3]);
        this.xfIndex = IntegerHelper.getInt(data[4], data[5]);
        this.sheet = si;
        this.formattingRecords = fr;
    }

    public final int getRow() {
        return this.row;
    }

    public final int getColumn() {
        return this.column;
    }

    public final int getXFIndex() {
        return this.xfIndex;
    }

    public CellFormat getCellFormat() {
        if (!this.initialized) {
            this.format = this.formattingRecords.getXFRecord(this.xfIndex);
            this.initialized = true;
        }
        return this.format;
    }

    public boolean isHidden() {
        ColumnInfoRecord cir = this.sheet.getColumnInfo(this.column);
        if (cir != null && (cir.getWidth() == 0 || cir.getHidden())) {
            return true;
        }
        RowRecord rr = this.sheet.getRowInfo(this.row);
        if (rr == null || (rr.getRowHeight() != 0 && !rr.isCollapsed())) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public SheetImpl getSheet() {
        return this.sheet;
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

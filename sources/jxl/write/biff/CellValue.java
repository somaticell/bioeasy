package jxl.write.biff;

import common.Assert;
import common.Logger;
import jxl.Cell;
import jxl.CellFeatures;
import jxl.Sheet;
import jxl.biff.FormattingRecords;
import jxl.biff.IntegerHelper;
import jxl.biff.NumFormatRecordsException;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.biff.XFRecord;
import jxl.biff.drawing.Comment;
import jxl.format.CellFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;

public abstract class CellValue extends WritableRecordData implements WritableCell {
    static Class class$jxl$write$biff$CellValue;
    private static Logger logger;
    private int column;
    private boolean copied;
    private WritableCellFeatures features;
    private XFRecord format;
    private FormattingRecords formattingRecords;
    private boolean referenced;
    private int row;
    private WritableSheetImpl sheet;

    static {
        Class cls;
        if (class$jxl$write$biff$CellValue == null) {
            cls = class$("jxl.write.biff.CellValue");
            class$jxl$write$biff$CellValue = cls;
        } else {
            cls = class$jxl$write$biff$CellValue;
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

    protected CellValue(Type t, int c, int r) {
        this(t, c, r, (CellFormat) WritableWorkbookImpl.NORMAL_STYLE);
        this.copied = false;
    }

    protected CellValue(Type t, Cell c) {
        this(t, c.getColumn(), c.getRow());
        this.copied = true;
        this.format = (XFRecord) c.getCellFormat();
        if (c.getCellFeatures() != null) {
            this.features = new WritableCellFeatures(c.getCellFeatures());
            this.features.setWritableCell(this);
        }
    }

    protected CellValue(Type t, int c, int r, CellFormat st) {
        super(t);
        this.row = r;
        this.column = c;
        this.format = (XFRecord) st;
        this.referenced = false;
        this.copied = false;
    }

    protected CellValue(Type t, int c, int r, CellValue cv) {
        super(t);
        this.row = r;
        this.column = c;
        this.format = cv.format;
        this.referenced = false;
        this.copied = false;
        if (cv.features != null) {
            this.features = new WritableCellFeatures(cv.features);
            this.features.setWritableCell(this);
        }
    }

    public void setCellFormat(CellFormat cf) {
        this.format = (XFRecord) cf;
        if (this.referenced) {
            Assert.verify(this.formattingRecords != null);
            addCellFormat();
        }
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
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

    public byte[] getData() {
        byte[] mydata = new byte[6];
        IntegerHelper.getTwoBytes(this.row, mydata, 0);
        IntegerHelper.getTwoBytes(this.column, mydata, 2);
        IntegerHelper.getTwoBytes(this.format.getXFIndex(), mydata, 4);
        return mydata;
    }

    /* access modifiers changed from: package-private */
    public void setCellDetails(FormattingRecords fr, SharedStrings ss, WritableSheetImpl s) {
        this.referenced = true;
        this.sheet = s;
        this.formattingRecords = fr;
        addCellFormat();
        addCellFeatures();
    }

    /* access modifiers changed from: package-private */
    public final boolean isReferenced() {
        return this.referenced;
    }

    /* access modifiers changed from: package-private */
    public final int getXFIndex() {
        return this.format.getXFIndex();
    }

    public CellFormat getCellFormat() {
        return this.format;
    }

    /* access modifiers changed from: package-private */
    public void incrementRow() {
        this.row++;
        if (this.features != null) {
            Comment c = this.features.getCommentDrawing();
            c.setX((double) this.column);
            c.setY((double) this.row);
        }
    }

    /* access modifiers changed from: package-private */
    public void decrementRow() {
        this.row--;
        if (this.features != null) {
            Comment c = this.features.getCommentDrawing();
            c.setX((double) this.column);
            c.setY((double) this.row);
        }
    }

    /* access modifiers changed from: package-private */
    public void incrementColumn() {
        this.column++;
        if (this.features != null) {
            Comment c = this.features.getCommentDrawing();
            c.setX((double) this.column);
            c.setY((double) this.row);
        }
    }

    /* access modifiers changed from: package-private */
    public void decrementColumn() {
        this.column--;
        if (this.features != null) {
            Comment c = this.features.getCommentDrawing();
            c.setX((double) this.column);
            c.setY((double) this.row);
        }
    }

    /* access modifiers changed from: package-private */
    public void columnInserted(Sheet s, int sheetIndex, int col) {
    }

    /* access modifiers changed from: package-private */
    public void columnRemoved(Sheet s, int sheetIndex, int col) {
    }

    /* access modifiers changed from: package-private */
    public void rowInserted(Sheet s, int sheetIndex, int row2) {
    }

    /* access modifiers changed from: package-private */
    public void rowRemoved(Sheet s, int sheetIndex, int row2) {
    }

    /* access modifiers changed from: protected */
    public WritableSheetImpl getSheet() {
        return this.sheet;
    }

    private void addCellFormat() {
        Styles styles = this.sheet.getWorkbook().getStyles();
        this.format = styles.getFormat(this.format);
        try {
            if (!this.format.isInitialized()) {
                this.formattingRecords.addStyle(this.format);
            }
        } catch (NumFormatRecordsException e) {
            logger.warn("Maximum number of format records exceeded.  Using default format.");
            this.format = styles.getNormalStyle();
        }
    }

    public CellFeatures getCellFeatures() {
        return this.features;
    }

    public WritableCellFeatures getWritableCellFeatures() {
        return this.features;
    }

    public void setCellFeatures(WritableCellFeatures cf) {
        if (this.features != null) {
            logger.warn("current cell features not null - overwriting");
        }
        this.features = cf;
        cf.setWritableCell(this);
        if (this.referenced) {
            addCellFeatures();
        }
    }

    public final void addCellFeatures() {
        if (this.features != null) {
            if (this.copied) {
                this.copied = false;
            } else if (this.features.getComment() != null) {
                Comment comment = new Comment(this.features.getComment(), this.column, this.row);
                comment.setWidth(this.features.getCommentWidth());
                comment.setHeight(this.features.getCommentHeight());
                this.sheet.addDrawing(comment);
                this.sheet.getWorkbook().addDrawing(comment);
                this.features.setCommentDrawing(comment);
            }
        }
    }

    public final void removeComment(Comment c) {
        this.sheet.removeDrawing(c);
    }
}

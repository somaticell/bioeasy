package jxl.write.biff;

import common.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import jxl.CellType;
import jxl.biff.CellReferenceHelper;
import jxl.biff.IndexMapping;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.biff.XFRecord;
import jxl.write.Number;

class RowRecord extends WritableRecordData {
    static Class class$jxl$write$biff$RowRecord = null;
    private static int defaultHeightIndicator = 255;
    private static final int growSize = 10;
    private static final Logger logger;
    private static int maxColumns = 256;
    private static final int maxRKValue = 536870911;
    private static final int minRKValue = -536870912;
    private CellValue[] cells = new CellValue[0];
    private boolean collapsed = false;
    private byte[] data;
    private boolean defaultFormat;
    private boolean matchesDefFontHeight = true;
    private int numColumns = 0;
    private int rowHeight = defaultHeightIndicator;
    private int rowNumber;
    private XFRecord style;
    private int xfIndex;

    static {
        Class cls;
        if (class$jxl$write$biff$RowRecord == null) {
            cls = class$("jxl.write.biff.RowRecord");
            class$jxl$write$biff$RowRecord = cls;
        } else {
            cls = class$jxl$write$biff$RowRecord;
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

    public RowRecord(int rn) {
        super(Type.ROW);
        this.rowNumber = rn;
    }

    public void setRowHeight(int h) {
        if (h == 0) {
            setCollapsed(true);
            this.matchesDefFontHeight = false;
            return;
        }
        this.rowHeight = h;
        this.matchesDefFontHeight = false;
    }

    /* access modifiers changed from: package-private */
    public void setRowDetails(int height, boolean mdfh, boolean col, XFRecord xfr) {
        this.rowHeight = height;
        this.collapsed = col;
        this.matchesDefFontHeight = mdfh;
        if (xfr != null) {
            this.defaultFormat = true;
            this.style = xfr;
            this.xfIndex = this.style.getXFIndex();
        }
    }

    public void setCollapsed(boolean c) {
        this.collapsed = c;
    }

    public int getRowNumber() {
        return this.rowNumber;
    }

    public void addCell(CellValue cv) {
        int col = cv.getColumn();
        if (col >= maxColumns) {
            logger.warn(new StringBuffer().append("Could not add cell at ").append(CellReferenceHelper.getCellReference(cv.getRow(), cv.getColumn())).append(" because it exceeds the maximum column limit").toString());
            return;
        }
        if (col >= this.cells.length) {
            CellValue[] oldCells = this.cells;
            this.cells = new CellValue[Math.max(oldCells.length + 10, col + 1)];
            System.arraycopy(oldCells, 0, this.cells, 0, oldCells.length);
        }
        this.cells[col] = cv;
        this.numColumns = Math.max(col + 1, this.numColumns);
    }

    public void removeCell(int col) {
        if (col < this.numColumns) {
            this.cells[col] = null;
        }
    }

    public void write(File outputFile) throws IOException {
        outputFile.write(this);
    }

    public void writeCells(File outputFile) throws IOException {
        ArrayList integerValues = new ArrayList();
        for (int i = 0; i < this.numColumns; i++) {
            boolean integerValue = false;
            if (this.cells[i] != null) {
                if (this.cells[i].getType() == CellType.NUMBER) {
                    Number nc = (Number) this.cells[i];
                    if (nc.getValue() == ((double) ((int) nc.getValue())) && nc.getValue() < 5.36870911E8d && nc.getValue() > -5.36870912E8d && nc.getCellFeatures() == null) {
                        integerValue = true;
                    }
                }
                if (integerValue) {
                    integerValues.add(this.cells[i]);
                } else {
                    writeIntegerValues(integerValues, outputFile);
                    outputFile.write(this.cells[i]);
                    if (this.cells[i].getType() == CellType.STRING_FORMULA) {
                        outputFile.write(new StringRecord(this.cells[i].getContents()));
                    }
                }
            } else {
                writeIntegerValues(integerValues, outputFile);
            }
        }
        writeIntegerValues(integerValues, outputFile);
    }

    private void writeIntegerValues(ArrayList integerValues, File outputFile) throws IOException {
        if (integerValues.size() != 0) {
            if (integerValues.size() >= 3) {
                outputFile.write(new MulRKRecord(integerValues));
            } else {
                Iterator i = integerValues.iterator();
                while (i.hasNext()) {
                    outputFile.write((CellValue) i.next());
                }
            }
            integerValues.clear();
        }
    }

    public byte[] getData() {
        byte[] data2 = new byte[16];
        IntegerHelper.getTwoBytes(this.rowNumber, data2, 0);
        IntegerHelper.getTwoBytes(this.numColumns, data2, 4);
        IntegerHelper.getTwoBytes(this.rowHeight, data2, 6);
        int options = 256;
        if (this.collapsed) {
            options = 256 | 32;
        }
        if (!this.matchesDefFontHeight) {
            options |= 64;
        }
        if (this.defaultFormat) {
            options = options | 128 | (this.xfIndex << 16);
        }
        IntegerHelper.getFourBytes(options, data2, 12);
        return data2;
    }

    public int getMaxColumn() {
        return this.numColumns;
    }

    public CellValue getCell(int col) {
        if (col < 0 || col >= this.numColumns) {
            return null;
        }
        return this.cells[col];
    }

    /* access modifiers changed from: package-private */
    public void incrementRow() {
        this.rowNumber++;
        for (int i = 0; i < this.cells.length; i++) {
            if (this.cells[i] != null) {
                this.cells[i].incrementRow();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void decrementRow() {
        this.rowNumber--;
        for (int i = 0; i < this.cells.length; i++) {
            if (this.cells[i] != null) {
                this.cells[i].decrementRow();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void insertColumn(int col) {
        if (col < this.numColumns) {
            if (this.numColumns >= maxColumns) {
                logger.warn("Could not insert column because maximum column limit has been reached");
                return;
            }
            CellValue[] oldCells = this.cells;
            if (this.numColumns >= this.cells.length - 1) {
                this.cells = new CellValue[(oldCells.length + 10)];
            } else {
                this.cells = new CellValue[oldCells.length];
            }
            System.arraycopy(oldCells, 0, this.cells, 0, col);
            System.arraycopy(oldCells, col, this.cells, col + 1, this.numColumns - col);
            for (int i = col + 1; i <= this.numColumns; i++) {
                if (this.cells[i] != null) {
                    this.cells[i].incrementColumn();
                }
            }
            this.numColumns++;
        }
    }

    /* access modifiers changed from: package-private */
    public void removeColumn(int col) {
        if (col < this.numColumns) {
            CellValue[] oldCells = this.cells;
            this.cells = new CellValue[oldCells.length];
            System.arraycopy(oldCells, 0, this.cells, 0, col);
            System.arraycopy(oldCells, col + 1, this.cells, col, this.numColumns - (col + 1));
            for (int i = col; i < this.numColumns; i++) {
                if (this.cells[i] != null) {
                    this.cells[i].decrementColumn();
                }
            }
            this.numColumns--;
        }
    }

    public boolean isDefaultHeight() {
        return this.rowHeight == defaultHeightIndicator;
    }

    public int getRowHeight() {
        return this.rowHeight;
    }

    public boolean isCollapsed() {
        return this.collapsed;
    }

    /* access modifiers changed from: package-private */
    public void rationalize(IndexMapping xfmapping) {
        if (this.defaultFormat) {
            this.xfIndex = xfmapping.getNewIndex(this.xfIndex);
        }
    }

    /* access modifiers changed from: package-private */
    public XFRecord getStyle() {
        return this.style;
    }

    /* access modifiers changed from: package-private */
    public boolean hasDefaultFormat() {
        return this.defaultFormat;
    }

    /* access modifiers changed from: package-private */
    public boolean matchesDefaultFontHeight() {
        return this.matchesDefFontHeight;
    }
}

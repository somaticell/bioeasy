package jxl.biff.formula;

import common.Logger;
import jxl.Cell;
import jxl.biff.CellReferenceHelper;
import jxl.biff.IntegerHelper;

class CellReference extends Operand implements ParsedThing {
    static Class class$jxl$biff$formula$CellReference;
    private static Logger logger;
    private int column;
    private boolean columnRelative;
    private Cell relativeTo;
    private int row;
    private boolean rowRelative;

    static {
        Class cls;
        if (class$jxl$biff$formula$CellReference == null) {
            cls = class$("jxl.biff.formula.CellReference");
            class$jxl$biff$formula$CellReference = cls;
        } else {
            cls = class$jxl$biff$formula$CellReference;
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

    public CellReference(Cell rt) {
        this.relativeTo = rt;
    }

    public CellReference() {
    }

    public CellReference(String s) {
        this.column = CellReferenceHelper.getColumn(s);
        this.row = CellReferenceHelper.getRow(s);
        this.columnRelative = CellReferenceHelper.isColumnRelative(s);
        this.rowRelative = CellReferenceHelper.isRowRelative(s);
    }

    public int read(byte[] data, int pos) {
        boolean z;
        boolean z2 = true;
        this.row = IntegerHelper.getInt(data[pos], data[pos + 1]);
        int columnMask = IntegerHelper.getInt(data[pos + 2], data[pos + 3]);
        this.column = columnMask & 255;
        if ((columnMask & 16384) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.columnRelative = z;
        if ((32768 & columnMask) == 0) {
            z2 = false;
        }
        this.rowRelative = z2;
        return 4;
    }

    public int getColumn() {
        return this.column;
    }

    public int getRow() {
        return this.row;
    }

    public void getString(StringBuffer buf) {
        boolean z;
        boolean z2 = true;
        int i = this.column;
        if (!this.columnRelative) {
            z = true;
        } else {
            z = false;
        }
        int i2 = this.row;
        if (this.rowRelative) {
            z2 = false;
        }
        CellReferenceHelper.getCellReference(i, z, i2, z2, buf);
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        byte[] data = new byte[5];
        data[0] = !useAlternateCode() ? Token.REF.getCode() : Token.REF.getCode2();
        IntegerHelper.getTwoBytes(this.row, data, 1);
        int grcol = this.column;
        if (this.rowRelative) {
            grcol |= 32768;
        }
        if (this.columnRelative) {
            grcol |= 16384;
        }
        IntegerHelper.getTwoBytes(grcol, data, 3);
        return data;
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        if (this.columnRelative) {
            this.column += colAdjust;
        }
        if (this.rowRelative) {
            this.row += rowAdjust;
        }
    }

    public void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        if (currentSheet && this.column >= col) {
            this.column++;
        }
    }

    /* access modifiers changed from: package-private */
    public void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        if (currentSheet && this.column >= col) {
            this.column--;
        }
    }

    /* access modifiers changed from: package-private */
    public void rowInserted(int sheetIndex, int r, boolean currentSheet) {
        if (currentSheet && this.row >= r) {
            this.row++;
        }
    }

    /* access modifiers changed from: package-private */
    public void rowRemoved(int sheetIndex, int r, boolean currentSheet) {
        if (currentSheet && this.row >= r) {
            this.row--;
        }
    }
}

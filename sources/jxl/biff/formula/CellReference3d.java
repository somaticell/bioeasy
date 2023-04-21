package jxl.biff.formula;

import common.Logger;
import jxl.Cell;
import jxl.biff.CellReferenceHelper;
import jxl.biff.IntegerHelper;

class CellReference3d extends Operand implements ParsedThing {
    static Class class$jxl$biff$formula$CellReference3d;
    private static Logger logger;
    private int column;
    private boolean columnRelative;
    private Cell relativeTo;
    private int row;
    private boolean rowRelative;
    private int sheet;
    private ExternalSheet workbook;

    static {
        Class cls;
        if (class$jxl$biff$formula$CellReference3d == null) {
            cls = class$("jxl.biff.formula.CellReference3d");
            class$jxl$biff$formula$CellReference3d = cls;
        } else {
            cls = class$jxl$biff$formula$CellReference3d;
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

    public CellReference3d(Cell rt, ExternalSheet w) {
        this.relativeTo = rt;
        this.workbook = w;
    }

    public CellReference3d(String s, ExternalSheet w) throws FormulaException {
        this.workbook = w;
        this.columnRelative = true;
        this.rowRelative = true;
        int sep = s.indexOf(33);
        String cellString = s.substring(sep + 1);
        this.column = CellReferenceHelper.getColumn(cellString);
        this.row = CellReferenceHelper.getRow(cellString);
        String sheetName = s.substring(0, sep);
        if (sheetName.charAt(0) == '\'' && sheetName.charAt(sheetName.length() - 1) == '\'') {
            sheetName = sheetName.substring(1, sheetName.length() - 1);
        }
        this.sheet = w.getExternalSheetIndex(sheetName);
        if (this.sheet < 0) {
            throw new FormulaException(FormulaException.sheetRefNotFound, sheetName);
        }
    }

    public int read(byte[] data, int pos) {
        boolean z;
        boolean z2 = true;
        this.sheet = IntegerHelper.getInt(data[pos], data[pos + 1]);
        this.row = IntegerHelper.getInt(data[pos + 2], data[pos + 3]);
        int columnMask = IntegerHelper.getInt(data[pos + 4], data[pos + 5]);
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
        return 6;
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
        int i = this.sheet;
        int i2 = this.column;
        if (!this.columnRelative) {
            z = true;
        } else {
            z = false;
        }
        int i3 = this.row;
        if (this.rowRelative) {
            z2 = false;
        }
        CellReferenceHelper.getCellReference(i, i2, z, i3, z2, this.workbook, buf);
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        byte[] data = new byte[7];
        data[0] = Token.REF3D.getCode();
        IntegerHelper.getTwoBytes(this.sheet, data, 1);
        IntegerHelper.getTwoBytes(this.row, data, 3);
        int grcol = this.column;
        if (this.rowRelative) {
            grcol |= 32768;
        }
        if (this.columnRelative) {
            grcol |= 16384;
        }
        IntegerHelper.getTwoBytes(grcol, data, 5);
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
        if (sheetIndex == this.sheet && this.column >= col) {
            this.column++;
        }
    }

    /* access modifiers changed from: package-private */
    public void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        if (sheetIndex == this.sheet && this.column >= col) {
            this.column--;
        }
    }

    /* access modifiers changed from: package-private */
    public void rowInserted(int sheetIndex, int r, boolean currentSheet) {
        if (sheetIndex == this.sheet && this.row >= r) {
            this.row++;
        }
    }

    /* access modifiers changed from: package-private */
    public void rowRemoved(int sheetIndex, int r, boolean currentSheet) {
        if (sheetIndex == this.sheet && this.row >= r) {
            this.row--;
        }
    }
}

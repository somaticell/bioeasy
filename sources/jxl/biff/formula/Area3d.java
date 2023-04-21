package jxl.biff.formula;

import common.Assert;
import common.Logger;
import jxl.biff.CellReferenceHelper;
import jxl.biff.IntegerHelper;

class Area3d extends Operand implements ParsedThing {
    static Class class$jxl$biff$formula$Area3d;
    private static Logger logger;
    private int columnFirst;
    private boolean columnFirstRelative;
    private int columnLast;
    private boolean columnLastRelative;
    private int rowFirst;
    private boolean rowFirstRelative;
    private int rowLast;
    private boolean rowLastRelative;
    private int sheet;
    private ExternalSheet workbook;

    static {
        Class cls;
        if (class$jxl$biff$formula$Area3d == null) {
            cls = class$("jxl.biff.formula.Area3d");
            class$jxl$biff$formula$Area3d = cls;
        } else {
            cls = class$jxl$biff$formula$Area3d;
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

    Area3d(ExternalSheet es) {
        this.workbook = es;
    }

    Area3d(String s, ExternalSheet es) throws FormulaException {
        boolean z;
        this.workbook = es;
        int seppos = s.lastIndexOf(":");
        if (seppos != -1) {
            z = true;
        } else {
            z = false;
        }
        Assert.verify(z);
        String substring = s.substring(0, seppos);
        String endcell = s.substring(seppos + 1);
        int sep = s.indexOf(33);
        String cellString = s.substring(sep + 1, seppos);
        this.columnFirst = CellReferenceHelper.getColumn(cellString);
        this.rowFirst = CellReferenceHelper.getRow(cellString);
        String sheetName = s.substring(0, sep);
        int lastIndexOf = sheetName.lastIndexOf(93);
        if (sheetName.charAt(0) == '\'' && sheetName.charAt(sheetName.length() - 1) == '\'') {
            sheetName = sheetName.substring(1, sheetName.length() - 1);
        }
        this.sheet = es.getExternalSheetIndex(sheetName);
        if (this.sheet < 0) {
            throw new FormulaException(FormulaException.sheetRefNotFound, sheetName);
        }
        this.columnLast = CellReferenceHelper.getColumn(endcell);
        this.rowLast = CellReferenceHelper.getRow(endcell);
        this.columnFirstRelative = true;
        this.rowFirstRelative = true;
        this.columnLastRelative = true;
        this.rowLastRelative = true;
    }

    /* access modifiers changed from: package-private */
    public int getFirstColumn() {
        return this.columnFirst;
    }

    /* access modifiers changed from: package-private */
    public int getFirstRow() {
        return this.rowFirst;
    }

    /* access modifiers changed from: package-private */
    public int getLastColumn() {
        return this.columnLast;
    }

    /* access modifiers changed from: package-private */
    public int getLastRow() {
        return this.rowLast;
    }

    public int read(byte[] data, int pos) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        this.sheet = IntegerHelper.getInt(data[pos], data[pos + 1]);
        this.rowFirst = IntegerHelper.getInt(data[pos + 2], data[pos + 3]);
        this.rowLast = IntegerHelper.getInt(data[pos + 4], data[pos + 5]);
        int columnMask = IntegerHelper.getInt(data[pos + 6], data[pos + 7]);
        this.columnFirst = columnMask & 255;
        this.columnFirstRelative = (columnMask & 16384) != 0;
        if ((columnMask & 32768) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.rowFirstRelative = z;
        int columnMask2 = IntegerHelper.getInt(data[pos + 8], data[pos + 9]);
        this.columnLast = columnMask2 & 255;
        if ((columnMask2 & 16384) != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.columnLastRelative = z2;
        if ((columnMask2 & 32768) == 0) {
            z3 = false;
        }
        this.rowLastRelative = z3;
        return 10;
    }

    public void getString(StringBuffer buf) {
        CellReferenceHelper.getCellReference(this.sheet, this.columnFirst, this.rowFirst, this.workbook, buf);
        buf.append(':');
        CellReferenceHelper.getCellReference(this.columnLast, this.rowLast, buf);
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        byte[] data = new byte[11];
        data[0] = Token.AREA3D.getCode();
        IntegerHelper.getTwoBytes(this.sheet, data, 1);
        IntegerHelper.getTwoBytes(this.rowFirst, data, 3);
        IntegerHelper.getTwoBytes(this.rowLast, data, 5);
        int grcol = this.columnFirst;
        if (this.rowFirstRelative) {
            grcol |= 32768;
        }
        if (this.columnFirstRelative) {
            grcol |= 16384;
        }
        IntegerHelper.getTwoBytes(grcol, data, 7);
        int grcol2 = this.columnLast;
        if (this.rowLastRelative) {
            grcol2 |= 32768;
        }
        if (this.columnLastRelative) {
            grcol2 |= 16384;
        }
        IntegerHelper.getTwoBytes(grcol2, data, 9);
        return data;
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        if (this.columnFirstRelative) {
            this.columnFirst += colAdjust;
        }
        if (this.columnLastRelative) {
            this.columnLast += colAdjust;
        }
        if (this.rowFirstRelative) {
            this.rowFirst += rowAdjust;
        }
        if (this.rowLastRelative) {
            this.rowLast += rowAdjust;
        }
    }

    public void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        if (sheetIndex == this.sheet) {
            if (this.columnFirst >= col) {
                this.columnFirst++;
            }
            if (this.columnLast >= col) {
                this.columnLast++;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        if (sheetIndex == this.sheet) {
            if (col < this.columnFirst) {
                this.columnFirst--;
            }
            if (col <= this.columnLast) {
                this.columnLast--;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void rowInserted(int sheetIndex, int row, boolean currentSheet) {
        if (sheetIndex == this.sheet) {
            if (row <= this.rowFirst) {
                this.rowFirst++;
            }
            if (row <= this.rowLast) {
                this.rowLast++;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void rowRemoved(int sheetIndex, int row, boolean currentSheet) {
        if (sheetIndex == this.sheet) {
            if (row < this.rowFirst) {
                this.rowFirst--;
            }
            if (row <= this.rowLast) {
                this.rowLast--;
            }
        }
    }
}

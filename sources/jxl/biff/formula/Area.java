package jxl.biff.formula;

import common.Assert;
import common.Logger;
import jxl.biff.CellReferenceHelper;
import jxl.biff.IntegerHelper;

class Area extends Operand implements ParsedThing {
    static Class class$jxl$biff$formula$Area;
    private static Logger logger;
    private int columnFirst;
    private boolean columnFirstRelative;
    private int columnLast;
    private boolean columnLastRelative;
    private int rowFirst;
    private boolean rowFirstRelative;
    private int rowLast;
    private boolean rowLastRelative;

    static {
        Class cls;
        if (class$jxl$biff$formula$Area == null) {
            cls = class$("jxl.biff.formula.Area");
            class$jxl$biff$formula$Area = cls;
        } else {
            cls = class$jxl$biff$formula$Area;
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

    Area() {
    }

    Area(String s) {
        boolean z;
        int seppos = s.indexOf(":");
        if (seppos != -1) {
            z = true;
        } else {
            z = false;
        }
        Assert.verify(z);
        String startcell = s.substring(0, seppos);
        String endcell = s.substring(seppos + 1);
        this.columnFirst = CellReferenceHelper.getColumn(startcell);
        this.rowFirst = CellReferenceHelper.getRow(startcell);
        this.columnLast = CellReferenceHelper.getColumn(endcell);
        this.rowLast = CellReferenceHelper.getRow(endcell);
        this.columnFirstRelative = CellReferenceHelper.isColumnRelative(startcell);
        this.rowFirstRelative = CellReferenceHelper.isRowRelative(startcell);
        this.columnLastRelative = CellReferenceHelper.isColumnRelative(endcell);
        this.rowLastRelative = CellReferenceHelper.isRowRelative(endcell);
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
        this.rowFirst = IntegerHelper.getInt(data[pos], data[pos + 1]);
        this.rowLast = IntegerHelper.getInt(data[pos + 2], data[pos + 3]);
        int columnMask = IntegerHelper.getInt(data[pos + 4], data[pos + 5]);
        this.columnFirst = columnMask & 255;
        this.columnFirstRelative = (columnMask & 16384) != 0;
        if ((columnMask & 32768) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.rowFirstRelative = z;
        int columnMask2 = IntegerHelper.getInt(data[pos + 6], data[pos + 7]);
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
        return 8;
    }

    public void getString(StringBuffer buf) {
        CellReferenceHelper.getCellReference(this.columnFirst, this.rowFirst, buf);
        buf.append(':');
        CellReferenceHelper.getCellReference(this.columnLast, this.rowLast, buf);
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        byte[] data = new byte[9];
        data[0] = !useAlternateCode() ? Token.AREA.getCode() : Token.AREA.getCode2();
        IntegerHelper.getTwoBytes(this.rowFirst, data, 1);
        IntegerHelper.getTwoBytes(this.rowLast, data, 3);
        int grcol = this.columnFirst;
        if (this.rowFirstRelative) {
            grcol |= 32768;
        }
        if (this.columnFirstRelative) {
            grcol |= 16384;
        }
        IntegerHelper.getTwoBytes(grcol, data, 5);
        int grcol2 = this.columnLast;
        if (this.rowLastRelative) {
            grcol2 |= 32768;
        }
        if (this.columnLastRelative) {
            grcol2 |= 16384;
        }
        IntegerHelper.getTwoBytes(grcol2, data, 7);
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

    /* access modifiers changed from: package-private */
    public void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        if (currentSheet) {
            if (col <= this.columnFirst) {
                this.columnFirst++;
            }
            if (col <= this.columnLast) {
                this.columnLast++;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        if (currentSheet) {
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
        if (currentSheet) {
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
        if (currentSheet) {
            if (row < this.rowFirst) {
                this.rowFirst--;
            }
            if (row <= this.rowLast) {
                this.rowLast--;
            }
        }
    }
}

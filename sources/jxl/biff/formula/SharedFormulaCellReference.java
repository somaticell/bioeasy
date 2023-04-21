package jxl.biff.formula;

import jxl.Cell;
import jxl.biff.CellReferenceHelper;
import jxl.biff.IntegerHelper;

class SharedFormulaCellReference extends Operand implements ParsedThing {
    private int column;
    private boolean columnRelative;
    private Cell relativeTo;
    private int row;
    private boolean rowRelative;

    public SharedFormulaCellReference(Cell rt) {
        this.relativeTo = rt;
    }

    public int read(byte[] data, int pos) {
        boolean z = true;
        this.row = IntegerHelper.getShort(data[pos], data[pos + 1]);
        int columnMask = IntegerHelper.getInt(data[pos + 2], data[pos + 3]);
        this.column = (byte) (columnMask & 255);
        this.columnRelative = (columnMask & 16384) != 0;
        if ((32768 & columnMask) == 0) {
            z = false;
        }
        this.rowRelative = z;
        if (this.columnRelative) {
            this.column = this.relativeTo.getColumn() + this.column;
        }
        if (!this.rowRelative) {
            return 4;
        }
        this.row = this.relativeTo.getRow() + this.row;
        return 4;
    }

    public int getColumn() {
        return this.column;
    }

    public int getRow() {
        return this.row;
    }

    public void getString(StringBuffer buf) {
        CellReferenceHelper.getCellReference(this.column, this.row, buf);
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        byte[] data = new byte[5];
        data[0] = Token.REF.getCode();
        IntegerHelper.getTwoBytes(this.row, data, 1);
        int columnMask = this.column;
        if (this.columnRelative) {
            columnMask |= 16384;
        }
        if (this.rowRelative) {
            columnMask |= 32768;
        }
        IntegerHelper.getTwoBytes(columnMask, data, 3);
        return data;
    }
}

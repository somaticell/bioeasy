package jxl.biff.formula;

import jxl.biff.IntegerHelper;
import jxl.biff.WorkbookMethods;

class NameRange extends Operand implements ParsedThing {
    private int index;
    private String name;
    private WorkbookMethods nameTable;

    public NameRange(WorkbookMethods nt) {
        this.nameTable = nt;
    }

    public NameRange(String nm, WorkbookMethods nt) throws FormulaException {
        this.name = nm;
        this.nameTable = nt;
        this.index = this.nameTable.getNameIndex(this.name);
        if (this.index < 0) {
            throw new FormulaException(FormulaException.cellNameNotFound, this.name);
        }
        this.index++;
    }

    public int read(byte[] data, int pos) {
        this.index = IntegerHelper.getInt(data[pos], data[pos + 1]);
        this.name = this.nameTable.getName(this.index - 1);
        return 4;
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        byte[] data = new byte[5];
        data[0] = Token.NAMED_RANGE.getCode();
        IntegerHelper.getTwoBytes(this.index, data, 1);
        return data;
    }

    public void getString(StringBuffer buf) {
        buf.append(this.name);
    }
}

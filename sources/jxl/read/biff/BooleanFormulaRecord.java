package jxl.read.biff;

import common.Assert;
import jxl.BooleanCell;
import jxl.BooleanFormulaCell;
import jxl.CellType;
import jxl.biff.FormattingRecords;
import jxl.biff.FormulaData;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;

class BooleanFormulaRecord extends CellValue implements BooleanCell, FormulaData, BooleanFormulaCell {
    private byte[] data = getRecord().getData();
    private ExternalSheet externalSheet;
    private String formulaString;
    private WorkbookMethods nameTable;
    private boolean value = false;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BooleanFormulaRecord(Record t, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si) {
        super(t, fr, si);
        boolean z;
        boolean z2 = true;
        this.externalSheet = es;
        this.nameTable = nt;
        if (this.data[6] != 2) {
            z = true;
        } else {
            z = false;
        }
        Assert.verify(z);
        this.value = this.data[8] != 1 ? false : z2;
    }

    public boolean getValue() {
        return this.value;
    }

    public String getContents() {
        return new Boolean(this.value).toString();
    }

    public CellType getType() {
        return CellType.BOOLEAN_FORMULA;
    }

    public byte[] getFormulaData() throws FormulaException {
        if (!getSheet().getWorkbookBof().isBiff8()) {
            throw new FormulaException(FormulaException.biff8Supported);
        }
        byte[] d = new byte[(this.data.length - 6)];
        System.arraycopy(this.data, 6, d, 0, this.data.length - 6);
        return d;
    }

    public String getFormula() throws FormulaException {
        if (this.formulaString == null) {
            byte[] tokens = new byte[(this.data.length - 22)];
            System.arraycopy(this.data, 22, tokens, 0, tokens.length);
            FormulaParser fp = new FormulaParser(tokens, this, this.externalSheet, this.nameTable, getSheet().getWorkbook().getSettings());
            fp.parse();
            this.formulaString = fp.getFormula();
        }
        return this.formulaString;
    }
}

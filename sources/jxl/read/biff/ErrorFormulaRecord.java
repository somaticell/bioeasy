package jxl.read.biff;

import common.Assert;
import jxl.CellType;
import jxl.ErrorCell;
import jxl.ErrorFormulaCell;
import jxl.biff.FormattingRecords;
import jxl.biff.FormulaData;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;

class ErrorFormulaRecord extends CellValue implements ErrorCell, FormulaData, ErrorFormulaCell {
    private byte[] data = getRecord().getData();
    private int errorCode;
    private ExternalSheet externalSheet;
    private String formulaString;
    private WorkbookMethods nameTable;

    public ErrorFormulaRecord(Record t, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si) {
        super(t, fr, si);
        this.externalSheet = es;
        this.nameTable = nt;
        Assert.verify(this.data[6] == 2);
        this.errorCode = this.data[8];
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getContents() {
        return new StringBuffer().append("ERROR ").append(this.errorCode).toString();
    }

    public CellType getType() {
        return CellType.FORMULA_ERROR;
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

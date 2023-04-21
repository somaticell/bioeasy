package jxl.read.biff;

import java.text.NumberFormat;
import jxl.CellType;
import jxl.DateCell;
import jxl.DateFormulaCell;
import jxl.biff.FormattingRecords;
import jxl.biff.FormulaData;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;

class DateFormulaRecord extends DateRecord implements DateCell, FormulaData, DateFormulaCell {
    private byte[] data;
    private ExternalSheet externalSheet;
    private String formulaString;
    private WorkbookMethods nameTable;

    public DateFormulaRecord(NumberFormulaRecord t, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, boolean nf, SheetImpl si) throws FormulaException {
        super(t, t.getXFIndex(), fr, nf, si);
        this.externalSheet = es;
        this.nameTable = nt;
        this.data = t.getFormulaData();
    }

    public CellType getType() {
        return CellType.DATE_FORMULA;
    }

    public byte[] getFormulaData() throws FormulaException {
        if (getSheet().getWorkbookBof().isBiff8()) {
            return this.data;
        }
        throw new FormulaException(FormulaException.biff8Supported);
    }

    public String getFormula() throws FormulaException {
        if (this.formulaString == null) {
            byte[] tokens = new byte[(this.data.length - 16)];
            System.arraycopy(this.data, 16, tokens, 0, tokens.length);
            FormulaParser fp = new FormulaParser(tokens, this, this.externalSheet, this.nameTable, getSheet().getWorkbook().getSettings());
            fp.parse();
            this.formulaString = fp.getFormula();
        }
        return this.formulaString;
    }

    public double getValue() {
        return 0.0d;
    }

    public NumberFormat getNumberFormat() {
        return null;
    }
}

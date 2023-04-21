package jxl.read.biff;

import jxl.biff.FormattingRecords;
import jxl.biff.FormulaData;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;

public abstract class BaseSharedFormulaRecord extends CellValue implements FormulaData {
    private ExternalSheet externalSheet;
    private int filePos;
    private String formulaString;
    private WorkbookMethods nameTable;
    private byte[] tokens;

    public BaseSharedFormulaRecord(Record t, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si, int pos) {
        super(t, fr, si);
        this.externalSheet = es;
        this.nameTable = nt;
        this.filePos = pos;
    }

    public String getFormula() throws FormulaException {
        if (this.formulaString == null) {
            FormulaParser fp = new FormulaParser(this.tokens, this, this.externalSheet, this.nameTable, getSheet().getWorkbook().getSettings());
            fp.parse();
            this.formulaString = fp.getFormula();
        }
        return this.formulaString;
    }

    /* access modifiers changed from: package-private */
    public void setTokens(byte[] t) {
        this.tokens = t;
    }

    /* access modifiers changed from: protected */
    public final byte[] getTokens() {
        return this.tokens;
    }

    /* access modifiers changed from: protected */
    public final ExternalSheet getExternalSheet() {
        return this.externalSheet;
    }

    /* access modifiers changed from: protected */
    public final WorkbookMethods getNameTable() {
        return this.nameTable;
    }

    public Record getRecord() {
        return super.getRecord();
    }

    /* access modifiers changed from: package-private */
    public final int getFilePos() {
        return this.filePos;
    }
}

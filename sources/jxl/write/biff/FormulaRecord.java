package jxl.write.biff;

import common.Assert;
import common.Logger;
import jxl.CellReferenceHelper;
import jxl.CellType;
import jxl.Sheet;
import jxl.WorkbookSettings;
import jxl.biff.FormattingRecords;
import jxl.biff.FormulaData;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;
import jxl.format.CellFormat;
import jxl.write.WritableCell;

public class FormulaRecord extends CellValue implements FormulaData {
    static Class class$jxl$write$biff$FormulaRecord;
    private static Logger logger;
    private CellValue copiedFrom;
    private byte[] formulaBytes;
    private String formulaString;
    private String formulaToParse;
    private FormulaParser parser;

    static {
        Class cls;
        if (class$jxl$write$biff$FormulaRecord == null) {
            cls = class$("jxl.write.biff.FormulaRecord");
            class$jxl$write$biff$FormulaRecord = cls;
        } else {
            cls = class$jxl$write$biff$FormulaRecord;
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

    public FormulaRecord(int c, int r, String f) {
        super(Type.FORMULA2, c, r);
        this.formulaToParse = f;
        this.copiedFrom = null;
    }

    public FormulaRecord(int c, int r, String f, CellFormat st) {
        super(Type.FORMULA, c, r, st);
        this.formulaToParse = f;
        this.copiedFrom = null;
    }

    protected FormulaRecord(int c, int r, FormulaRecord fr) {
        super(Type.FORMULA, c, r, (CellValue) fr);
        this.copiedFrom = fr;
        this.formulaBytes = new byte[fr.formulaBytes.length];
        System.arraycopy(fr.formulaBytes, 0, this.formulaBytes, 0, this.formulaBytes.length);
    }

    protected FormulaRecord(int c, int r, ReadFormulaRecord rfr) {
        super(Type.FORMULA, c, r, (CellValue) rfr);
        try {
            this.copiedFrom = rfr;
            byte[] readFormulaData = rfr.getFormulaData();
            this.formulaBytes = new byte[(readFormulaData.length - 16)];
            System.arraycopy(readFormulaData, 16, this.formulaBytes, 0, this.formulaBytes.length);
        } catch (FormulaException e) {
            logger.error("", e);
        }
    }

    private void initialize(WorkbookSettings ws, ExternalSheet es, WorkbookMethods nt) {
        if (this.copiedFrom != null) {
            initializeCopiedFormula(ws, es, nt);
            return;
        }
        this.parser = new FormulaParser(this.formulaToParse, es, nt, ws);
        try {
            this.parser.parse();
            this.formulaString = this.parser.getFormula();
            this.formulaBytes = this.parser.getBytes();
        } catch (FormulaException e) {
            logger.warn(new StringBuffer().append(e.getMessage()).append(" when parsing formula ").append(this.formulaToParse).append(" in cell ").append(getSheet().getName()).append("!").append(CellReferenceHelper.getCellReference(getColumn(), getRow())).toString());
            try {
                this.formulaToParse = "ERROR(1)";
                this.parser = new FormulaParser(this.formulaToParse, es, nt, ws);
                this.parser.parse();
                this.formulaString = this.parser.getFormula();
                this.formulaBytes = this.parser.getBytes();
            } catch (FormulaException e2) {
                logger.error("", e2);
            }
        }
    }

    private void initializeCopiedFormula(WorkbookSettings ws, ExternalSheet es, WorkbookMethods nt) {
        try {
            this.parser = new FormulaParser(this.formulaBytes, this, es, nt, ws);
            this.parser.parse();
            this.parser.adjustRelativeCellReferences(getColumn() - this.copiedFrom.getColumn(), getRow() - this.copiedFrom.getRow());
            this.formulaString = this.parser.getFormula();
            this.formulaBytes = this.parser.getBytes();
        } catch (FormulaException e) {
            try {
                this.formulaToParse = "ERROR(1)";
                this.parser = new FormulaParser(this.formulaToParse, es, nt, ws);
                this.parser.parse();
                this.formulaString = this.parser.getFormula();
                this.formulaBytes = this.parser.getBytes();
            } catch (FormulaException e2) {
                logger.error("", e2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setCellDetails(FormattingRecords fr, SharedStrings ss, WritableSheetImpl s) {
        super.setCellDetails(fr, ss, s);
        initialize(s.getWorkbookSettings(), s.getWorkbook(), s.getWorkbook());
        s.getWorkbook().addRCIRCell(this);
    }

    public byte[] getData() {
        byte[] celldata = super.getData();
        byte[] formulaData = getFormulaData();
        byte[] data = new byte[(formulaData.length + celldata.length)];
        System.arraycopy(celldata, 0, data, 0, celldata.length);
        System.arraycopy(formulaData, 0, data, celldata.length, formulaData.length);
        return data;
    }

    public CellType getType() {
        return CellType.ERROR;
    }

    public String getContents() {
        return this.formulaString;
    }

    public byte[] getFormulaData() {
        byte[] data = new byte[(this.formulaBytes.length + 16)];
        System.arraycopy(this.formulaBytes, 0, data, 16, this.formulaBytes.length);
        data[6] = 16;
        data[7] = 64;
        data[12] = -32;
        data[13] = -4;
        data[8] = (byte) (data[8] | 2);
        IntegerHelper.getTwoBytes(this.formulaBytes.length, data, 14);
        return data;
    }

    public WritableCell copyTo(int col, int row) {
        Assert.verify(false);
        return null;
    }

    /* access modifiers changed from: package-private */
    public void columnInserted(Sheet s, int sheetIndex, int col) {
        this.parser.columnInserted(sheetIndex, col, s == getSheet());
        this.formulaBytes = this.parser.getBytes();
    }

    /* access modifiers changed from: package-private */
    public void columnRemoved(Sheet s, int sheetIndex, int col) {
        this.parser.columnRemoved(sheetIndex, col, s == getSheet());
        this.formulaBytes = this.parser.getBytes();
    }

    /* access modifiers changed from: package-private */
    public void rowInserted(Sheet s, int sheetIndex, int row) {
        this.parser.rowInserted(sheetIndex, row, s == getSheet());
        this.formulaBytes = this.parser.getBytes();
    }

    /* access modifiers changed from: package-private */
    public void rowRemoved(Sheet s, int sheetIndex, int row) {
        this.parser.rowRemoved(sheetIndex, row, s == getSheet());
        this.formulaBytes = this.parser.getBytes();
    }
}

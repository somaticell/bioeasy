package jxl.write.biff;

import common.Assert;
import common.Logger;
import jxl.CellReferenceHelper;
import jxl.CellType;
import jxl.FormulaCell;
import jxl.Sheet;
import jxl.biff.FormattingRecords;
import jxl.biff.FormulaData;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;
import jxl.write.WritableCell;

class ReadFormulaRecord extends CellValue implements FormulaData {
    static Class class$jxl$write$biff$ReadFormulaRecord;
    private static Logger logger;
    private FormulaData formula;
    private FormulaParser parser;

    static {
        Class cls;
        if (class$jxl$write$biff$ReadFormulaRecord == null) {
            cls = class$("jxl.write.biff.ReadFormulaRecord");
            class$jxl$write$biff$ReadFormulaRecord = cls;
        } else {
            cls = class$jxl$write$biff$ReadFormulaRecord;
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

    protected ReadFormulaRecord(FormulaData f) {
        super(Type.FORMULA, f);
        this.formula = f;
    }

    public byte[] getData() {
        byte[] expressiondata;
        byte[] celldata = super.getData();
        try {
            if (this.parser == null) {
                expressiondata = this.formula.getFormulaData();
            } else {
                byte[] formulaBytes = this.parser.getBytes();
                expressiondata = new byte[(formulaBytes.length + 16)];
                IntegerHelper.getTwoBytes(formulaBytes.length, expressiondata, 14);
                System.arraycopy(formulaBytes, 0, expressiondata, 16, formulaBytes.length);
            }
        } catch (FormulaException e) {
            logger.warn(new StringBuffer().append(CellReferenceHelper.getCellReference(getColumn(), getRow())).append(" ").append(e.getMessage()).toString());
            WritableWorkbookImpl w = getSheet().getWorkbook();
            if (this.formula.getType() == CellType.STRING_FORMULA) {
                this.parser = new FormulaParser(new StringBuffer().append("\"").append(getContents()).append("\"").toString(), w, w, w.getSettings());
            } else {
                this.parser = new FormulaParser(getContents(), w, w, w.getSettings());
            }
            try {
                this.parser.parse();
            } catch (FormulaException e2) {
                logger.warn(e2.getMessage());
                this.parser = new FormulaParser("\"ERROR\"", w, w, w.getSettings());
                try {
                    this.parser.parse();
                } catch (FormulaException e3) {
                    Assert.verify(false);
                }
            }
            byte[] formulaBytes2 = this.parser.getBytes();
            expressiondata = new byte[(formulaBytes2.length + 16)];
            IntegerHelper.getTwoBytes(formulaBytes2.length, expressiondata, 14);
            System.arraycopy(formulaBytes2, 0, expressiondata, 16, formulaBytes2.length);
        }
        expressiondata[8] = (byte) (expressiondata[8] | 2);
        byte[] data = new byte[(celldata.length + expressiondata.length)];
        System.arraycopy(celldata, 0, data, 0, celldata.length);
        System.arraycopy(expressiondata, 0, data, celldata.length, expressiondata.length);
        return data;
    }

    public CellType getType() {
        return this.formula.getType();
    }

    public String getContents() {
        return this.formula.getContents();
    }

    public byte[] getFormulaData() throws FormulaException {
        byte[] d = this.formula.getFormulaData();
        byte[] data = new byte[d.length];
        System.arraycopy(d, 0, data, 0, d.length);
        data[8] = (byte) (data[8] | 2);
        return data;
    }

    public WritableCell copyTo(int col, int row) {
        return new FormulaRecord(col, row, this);
    }

    /* access modifiers changed from: package-private */
    public void setCellDetails(FormattingRecords fr, SharedStrings ss, WritableSheetImpl s) {
        super.setCellDetails(fr, ss, s);
        s.getWorkbook().addRCIRCell(this);
    }

    /* access modifiers changed from: package-private */
    public void columnInserted(Sheet s, int sheetIndex, int col) {
        try {
            if (this.parser == null) {
                byte[] formulaData = this.formula.getFormulaData();
                byte[] formulaBytes = new byte[(formulaData.length - 16)];
                System.arraycopy(formulaData, 16, formulaBytes, 0, formulaBytes.length);
                this.parser = new FormulaParser(formulaBytes, this, getSheet().getWorkbook(), getSheet().getWorkbook(), getSheet().getWorkbookSettings());
                this.parser.parse();
            }
            this.parser.columnInserted(sheetIndex, col, s == getSheet());
        } catch (FormulaException e) {
            logger.warn(new StringBuffer().append("cannot insert column within formula:  ").append(e.getMessage()).toString());
        }
    }

    /* access modifiers changed from: package-private */
    public void columnRemoved(Sheet s, int sheetIndex, int col) {
        try {
            if (this.parser == null) {
                byte[] formulaData = this.formula.getFormulaData();
                byte[] formulaBytes = new byte[(formulaData.length - 16)];
                System.arraycopy(formulaData, 16, formulaBytes, 0, formulaBytes.length);
                this.parser = new FormulaParser(formulaBytes, this, getSheet().getWorkbook(), getSheet().getWorkbook(), getSheet().getWorkbookSettings());
                this.parser.parse();
            }
            this.parser.columnRemoved(sheetIndex, col, s == getSheet());
        } catch (FormulaException e) {
            logger.warn(new StringBuffer().append("cannot remove column within formula:  ").append(e.getMessage()).toString());
        }
    }

    /* access modifiers changed from: package-private */
    public void rowInserted(Sheet s, int sheetIndex, int row) {
        try {
            if (this.parser == null) {
                byte[] formulaData = this.formula.getFormulaData();
                byte[] formulaBytes = new byte[(formulaData.length - 16)];
                System.arraycopy(formulaData, 16, formulaBytes, 0, formulaBytes.length);
                this.parser = new FormulaParser(formulaBytes, this, getSheet().getWorkbook(), getSheet().getWorkbook(), getSheet().getWorkbookSettings());
                this.parser.parse();
            }
            this.parser.rowInserted(sheetIndex, row, s == getSheet());
        } catch (FormulaException e) {
            logger.warn(new StringBuffer().append("cannot insert row within formula:  ").append(e.getMessage()).toString());
        }
    }

    /* access modifiers changed from: package-private */
    public void rowRemoved(Sheet s, int sheetIndex, int row) {
        try {
            if (this.parser == null) {
                byte[] formulaData = this.formula.getFormulaData();
                byte[] formulaBytes = new byte[(formulaData.length - 16)];
                System.arraycopy(formulaData, 16, formulaBytes, 0, formulaBytes.length);
                this.parser = new FormulaParser(formulaBytes, this, getSheet().getWorkbook(), getSheet().getWorkbook(), getSheet().getWorkbookSettings());
                this.parser.parse();
            }
            this.parser.rowRemoved(sheetIndex, row, s == getSheet());
        } catch (FormulaException e) {
            logger.warn(new StringBuffer().append("cannot remove row within formula:  ").append(e.getMessage()).toString());
        }
    }

    /* access modifiers changed from: protected */
    public FormulaData getReadFormula() {
        return this.formula;
    }

    public String getFormula() throws FormulaException {
        return ((FormulaCell) this.formula).getFormula();
    }
}

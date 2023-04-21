package jxl.biff.formula;

import jxl.Cell;
import jxl.WorkbookSettings;
import jxl.biff.WorkbookMethods;

public class FormulaParser {
    private Parser parser;

    public FormulaParser(byte[] tokens, Cell rt, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws) throws FormulaException {
        if (es.getWorkbookBof() == null || es.getWorkbookBof().isBiff8()) {
            this.parser = new TokenFormulaParser(tokens, rt, es, nt, ws);
            return;
        }
        throw new FormulaException(FormulaException.biff8Supported);
    }

    public FormulaParser(String form, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws) {
        this.parser = new StringFormulaParser(form, es, nt, ws);
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        this.parser.adjustRelativeCellReferences(colAdjust, rowAdjust);
    }

    public void parse() throws FormulaException {
        this.parser.parse();
    }

    public String getFormula() throws FormulaException {
        return this.parser.getFormula();
    }

    public byte[] getBytes() {
        return this.parser.getBytes();
    }

    public void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        this.parser.columnInserted(sheetIndex, col, currentSheet);
    }

    public void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        this.parser.columnRemoved(sheetIndex, col, currentSheet);
    }

    public void rowInserted(int sheetIndex, int row, boolean currentSheet) {
        this.parser.rowInserted(sheetIndex, row, currentSheet);
    }

    public void rowRemoved(int sheetIndex, int row, boolean currentSheet) {
        this.parser.rowRemoved(sheetIndex, row, currentSheet);
    }
}

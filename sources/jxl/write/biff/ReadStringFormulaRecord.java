package jxl.write.biff;

import jxl.StringFormulaCell;
import jxl.biff.FormulaData;

class ReadStringFormulaRecord extends ReadFormulaRecord implements StringFormulaCell {
    public ReadStringFormulaRecord(FormulaData f) {
        super(f);
    }

    public String getString() {
        return ((StringFormulaCell) getReadFormula()).getString();
    }
}

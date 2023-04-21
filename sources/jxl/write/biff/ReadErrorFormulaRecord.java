package jxl.write.biff;

import jxl.ErrorFormulaCell;
import jxl.biff.FormulaData;

class ReadErrorFormulaRecord extends ReadFormulaRecord implements ErrorFormulaCell {
    public ReadErrorFormulaRecord(FormulaData f) {
        super(f);
    }

    public int getErrorCode() {
        return ((ErrorFormulaCell) getReadFormula()).getErrorCode();
    }
}

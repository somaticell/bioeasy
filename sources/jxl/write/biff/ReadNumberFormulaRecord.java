package jxl.write.biff;

import java.text.NumberFormat;
import jxl.NumberFormulaCell;
import jxl.biff.FormulaData;

class ReadNumberFormulaRecord extends ReadFormulaRecord implements NumberFormulaCell {
    public ReadNumberFormulaRecord(FormulaData f) {
        super(f);
    }

    public double getValue() {
        return ((NumberFormulaCell) getReadFormula()).getValue();
    }

    public NumberFormat getNumberFormat() {
        return ((NumberFormulaCell) getReadFormula()).getNumberFormat();
    }
}

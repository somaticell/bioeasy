package jxl.write.biff;

import java.text.DateFormat;
import java.util.Date;
import jxl.DateFormulaCell;
import jxl.biff.FormulaData;

class ReadDateFormulaRecord extends ReadFormulaRecord implements DateFormulaCell {
    public ReadDateFormulaRecord(FormulaData f) {
        super(f);
    }

    public Date getDate() {
        return ((DateFormulaCell) getReadFormula()).getDate();
    }

    public boolean isTime() {
        return ((DateFormulaCell) getReadFormula()).isTime();
    }

    public DateFormat getDateFormat() {
        return ((DateFormulaCell) getReadFormula()).getDateFormat();
    }
}

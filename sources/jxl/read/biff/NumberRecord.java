package jxl.read.biff;

import common.Logger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import jxl.CellType;
import jxl.NumberCell;
import jxl.biff.DoubleHelper;
import jxl.biff.FormattingRecords;

class NumberRecord extends CellValue implements NumberCell {
    static Class class$jxl$read$biff$NumberRecord;
    private static DecimalFormat defaultFormat = new DecimalFormat("#.###");
    private static Logger logger;
    private NumberFormat format;
    private double value = DoubleHelper.getIEEEDouble(getRecord().getData(), 6);

    static {
        Class cls;
        if (class$jxl$read$biff$NumberRecord == null) {
            cls = class$("jxl.read.biff.NumberRecord");
            class$jxl$read$biff$NumberRecord = cls;
        } else {
            cls = class$jxl$read$biff$NumberRecord;
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

    public NumberRecord(Record t, FormattingRecords fr, SheetImpl si) {
        super(t, fr, si);
        this.format = fr.getNumberFormat(getXFIndex());
        if (this.format == null) {
            this.format = defaultFormat;
        }
    }

    public double getValue() {
        return this.value;
    }

    public String getContents() {
        return this.format.format(this.value);
    }

    public CellType getType() {
        return CellType.NUMBER;
    }

    public NumberFormat getNumberFormat() {
        return this.format;
    }
}

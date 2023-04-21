package jxl.read.biff;

import common.Logger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import jxl.CellType;
import jxl.NumberCell;
import jxl.biff.FormattingRecords;
import jxl.biff.IntegerHelper;

class RKRecord extends CellValue implements NumberCell {
    static Class class$jxl$read$biff$RKRecord;
    private static DecimalFormat defaultFormat = new DecimalFormat("#.###");
    private static Logger logger;
    private NumberFormat format;
    private double value;

    static {
        Class cls;
        if (class$jxl$read$biff$RKRecord == null) {
            cls = class$("jxl.read.biff.RKRecord");
            class$jxl$read$biff$RKRecord = cls;
        } else {
            cls = class$jxl$read$biff$RKRecord;
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

    public RKRecord(Record t, FormattingRecords fr, SheetImpl si) {
        super(t, fr, si);
        byte[] data = getRecord().getData();
        this.value = RKHelper.getDouble(IntegerHelper.getInt(data[6], data[7], data[8], data[9]));
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

package jxl.write.biff;

import common.Logger;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import jxl.CellType;
import jxl.DateCell;
import jxl.biff.DoubleHelper;
import jxl.biff.Type;
import jxl.format.CellFormat;
import jxl.write.DateFormats;
import jxl.write.WritableCellFormat;

public abstract class DateRecord extends CellValue {
    static Class class$jxl$write$biff$DateRecord = null;
    static final WritableCellFormat defaultDateFormat = new WritableCellFormat(DateFormats.DEFAULT);
    private static Logger logger = null;
    private static final long msInADay = 86400000;
    private static final int nonLeapDay = 61;
    private static final int utcOffsetDays = 25569;
    private Date date;
    private boolean time;
    private double value;

    protected static final class GMTDate {
    }

    static {
        Class cls;
        if (class$jxl$write$biff$DateRecord == null) {
            cls = class$("jxl.write.biff.DateRecord");
            class$jxl$write$biff$DateRecord = cls;
        } else {
            cls = class$jxl$write$biff$DateRecord;
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

    protected DateRecord(int c, int r, Date d) {
        this(c, r, d, (CellFormat) defaultDateFormat, true);
    }

    protected DateRecord(int c, int r, Date d, GMTDate a) {
        this(c, r, d, (CellFormat) defaultDateFormat, false);
    }

    protected DateRecord(int c, int r, Date d, CellFormat st) {
        super(Type.NUMBER, c, r, st);
        this.date = d;
        calculateValue(true);
    }

    protected DateRecord(int c, int r, Date d, CellFormat st, GMTDate a) {
        super(Type.NUMBER, c, r, st);
        this.date = d;
        calculateValue(false);
    }

    protected DateRecord(int c, int r, Date d, CellFormat st, boolean tim) {
        super(Type.NUMBER, c, r, st);
        this.date = d;
        this.time = tim;
        calculateValue(false);
    }

    protected DateRecord(DateCell dc) {
        super(Type.NUMBER, dc);
        this.date = dc.getDate();
        this.time = dc.isTime();
        calculateValue(false);
    }

    protected DateRecord(int c, int r, DateRecord dr) {
        super(Type.NUMBER, c, r, (CellValue) dr);
        this.value = dr.value;
        this.time = dr.time;
        this.date = dr.date;
    }

    private void calculateValue(boolean adjust) {
        long zoneOffset = 0;
        long dstOffset = 0;
        if (adjust) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(this.date);
            zoneOffset = (long) cal.get(15);
            dstOffset = (long) cal.get(16);
        }
        this.value = 25569.0d + (((double) ((this.date.getTime() + zoneOffset) + dstOffset)) / 8.64E7d);
        if (!this.time && this.value < 61.0d) {
            this.value -= 1.0d;
        }
        if (this.time) {
            this.value -= (double) ((int) this.value);
        }
    }

    public CellType getType() {
        return CellType.DATE;
    }

    public byte[] getData() {
        byte[] celldata = super.getData();
        byte[] data = new byte[(celldata.length + 8)];
        System.arraycopy(celldata, 0, data, 0, celldata.length);
        DoubleHelper.getIEEEBytes(this.value, data, celldata.length);
        return data;
    }

    public String getContents() {
        return this.date.toString();
    }

    /* access modifiers changed from: protected */
    public void setDate(Date d) {
        this.date = d;
        calculateValue(true);
    }

    /* access modifiers changed from: protected */
    public void setDate(Date d, GMTDate a) {
        this.date = d;
        calculateValue(false);
    }

    public Date getDate() {
        return this.date;
    }

    public boolean isTime() {
        return this.time;
    }

    public DateFormat getDateFormat() {
        return null;
    }
}

package jxl.read.biff;

import common.Assert;
import common.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import jxl.CellFeatures;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.biff.FormattingRecords;
import jxl.format.CellFormat;

class DateRecord implements DateCell, CellFeaturesAccessor {
    static Class class$jxl$read$biff$DateRecord = null;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    private static final TimeZone gmtZone = TimeZone.getTimeZone("GMT");
    private static Logger logger = null;
    private static final long msInADay = 86400000;
    private static final int nonLeapDay = 61;
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static final int utcOffsetDays = 25569;
    private static final int utcOffsetDays1904 = 24107;
    private CellFormat cellFormat;
    private int column;
    private Date date;
    private CellFeatures features;
    private DateFormat format = this.formattingRecords.getDateFormat(this.xfIndex);
    private FormattingRecords formattingRecords;
    private boolean initialized = false;
    private int row;
    private SheetImpl sheet;
    private boolean time;
    private int xfIndex;

    static {
        Class cls;
        if (class$jxl$read$biff$DateRecord == null) {
            cls = class$("jxl.read.biff.DateRecord");
            class$jxl$read$biff$DateRecord = cls;
        } else {
            cls = class$jxl$read$biff$DateRecord;
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

    public DateRecord(NumberCell num, int xfi, FormattingRecords fr, boolean nf, SheetImpl si) {
        this.row = num.getRow();
        this.column = num.getColumn();
        this.xfIndex = xfi;
        this.formattingRecords = fr;
        this.sheet = si;
        double numValue = num.getValue();
        if (Math.abs(numValue) < 1.0d) {
            if (this.format == null) {
                this.format = timeFormat;
            }
            this.time = true;
        } else {
            if (this.format == null) {
                this.format = dateFormat;
            }
            this.time = false;
        }
        if (!nf && !this.time && numValue < 61.0d) {
            numValue += 1.0d;
        }
        this.format.setTimeZone(gmtZone);
        this.date = new Date(Math.round(8.64E7d * (numValue - ((double) (nf ? utcOffsetDays1904 : utcOffsetDays)))));
    }

    public final int getRow() {
        return this.row;
    }

    public final int getColumn() {
        return this.column;
    }

    public Date getDate() {
        return this.date;
    }

    public String getContents() {
        return this.format.format(this.date);
    }

    public CellType getType() {
        return CellType.DATE;
    }

    public boolean isTime() {
        return this.time;
    }

    public DateFormat getDateFormat() {
        Assert.verify(this.format != null);
        return this.format;
    }

    public CellFormat getCellFormat() {
        if (!this.initialized) {
            this.cellFormat = this.formattingRecords.getXFRecord(this.xfIndex);
            this.initialized = true;
        }
        return this.cellFormat;
    }

    public boolean isHidden() {
        ColumnInfoRecord cir = this.sheet.getColumnInfo(this.column);
        if (cir != null && cir.getWidth() == 0) {
            return true;
        }
        RowRecord rr = this.sheet.getRowInfo(this.row);
        if (rr == null || (rr.getRowHeight() != 0 && !rr.isCollapsed())) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public final SheetImpl getSheet() {
        return this.sheet;
    }

    public CellFeatures getCellFeatures() {
        return this.features;
    }

    public void setCellFeatures(CellFeatures cf) {
        this.features = cf;
    }
}

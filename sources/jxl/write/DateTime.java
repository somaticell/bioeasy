package jxl.write;

import java.util.Date;
import jxl.DateCell;
import jxl.format.CellFormat;
import jxl.write.biff.DateRecord;

public class DateTime extends DateRecord implements WritableCell, DateCell {
    public static final DateRecord.GMTDate GMT = new DateRecord.GMTDate();

    public DateTime(int c, int r, Date d) {
        super(c, r, d);
    }

    public DateTime(int c, int r, Date d, DateRecord.GMTDate a) {
        super(c, r, d, a);
    }

    public DateTime(int c, int r, Date d, CellFormat st) {
        super(c, r, d, st);
    }

    public DateTime(int c, int r, Date d, CellFormat st, DateRecord.GMTDate a) {
        super(c, r, d, st, a);
    }

    public DateTime(int c, int r, Date d, CellFormat st, boolean tim) {
        super(c, r, d, st, tim);
    }

    public DateTime(DateCell dc) {
        super(dc);
    }

    protected DateTime(int col, int row, DateTime dt) {
        super(col, row, (DateRecord) dt);
    }

    public void setDate(Date d) {
        super.setDate(d);
    }

    public void setDate(Date d, DateRecord.GMTDate a) {
        super.setDate(d, a);
    }

    public WritableCell copyTo(int col, int row) {
        return new DateTime(col, row, this);
    }
}

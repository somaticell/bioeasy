package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;

public class RowRecord extends RecordData {
    static Class class$jxl$read$biff$RowRecord = null;
    private static final int defaultHeightIndicator = 255;
    private static Logger logger;
    private boolean collapsed;
    private boolean defaultFormat;
    private boolean matchesDefFontHeight;
    private int rowHeight;
    private int rowNumber;
    private int xfIndex;

    static {
        Class cls;
        if (class$jxl$read$biff$RowRecord == null) {
            cls = class$("jxl.read.biff.RowRecord");
            class$jxl$read$biff$RowRecord = cls;
        } else {
            cls = class$jxl$read$biff$RowRecord;
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

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    RowRecord(Record t) {
        super(t);
        boolean z;
        boolean z2;
        boolean z3 = true;
        byte[] data = getRecord().getData();
        this.rowNumber = IntegerHelper.getInt(data[0], data[1]);
        this.rowHeight = IntegerHelper.getInt(data[6], data[7]);
        int options = IntegerHelper.getInt(data[12], data[13], data[14], data[15]);
        if ((options & 32) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.collapsed = z;
        if ((options & 64) == 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.matchesDefFontHeight = z2;
        this.defaultFormat = (options & 128) == 0 ? false : z3;
        this.xfIndex = (268369920 & options) >> 16;
    }

    /* access modifiers changed from: package-private */
    public boolean isDefaultHeight() {
        return this.rowHeight == 255;
    }

    public boolean matchesDefaultFontHeight() {
        return this.matchesDefFontHeight;
    }

    public int getRowNumber() {
        return this.rowNumber;
    }

    public int getRowHeight() {
        return this.rowHeight;
    }

    public boolean isCollapsed() {
        return this.collapsed;
    }

    public int getXFIndex() {
        return this.xfIndex;
    }

    public boolean hasDefaultFormat() {
        return this.defaultFormat;
    }
}

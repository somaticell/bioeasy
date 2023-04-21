package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;

class PaneRecord extends RecordData {
    static Class class$jxl$read$biff$PaneRecord;
    private static Logger logger;
    private int columnsVisible;
    private int rowsVisible;

    static {
        Class cls;
        if (class$jxl$read$biff$PaneRecord == null) {
            cls = class$("jxl.read.biff.PaneRecord");
            class$jxl$read$biff$PaneRecord = cls;
        } else {
            cls = class$jxl$read$biff$PaneRecord;
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

    public PaneRecord(Record t) {
        super(t);
        byte[] data = t.getData();
        this.columnsVisible = IntegerHelper.getInt(data[0], data[1]);
        this.rowsVisible = IntegerHelper.getInt(data[2], data[3]);
    }

    public final int getRowsVisible() {
        return this.rowsVisible;
    }

    public final int getColumnsVisible() {
        return this.columnsVisible;
    }
}

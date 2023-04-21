package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;

class DimensionRecord extends RecordData {
    public static Biff7 biff7 = new Biff7((AnonymousClass1) null);
    static Class class$jxl$read$biff$DimensionRecord;
    private static Logger logger;
    private int numCols;
    private int numRows;

    /* renamed from: jxl.read.biff.DimensionRecord$1  reason: invalid class name */
    static class AnonymousClass1 {
    }

    static {
        Class cls;
        if (class$jxl$read$biff$DimensionRecord == null) {
            cls = class$("jxl.read.biff.DimensionRecord");
            class$jxl$read$biff$DimensionRecord = cls;
        } else {
            cls = class$jxl$read$biff$DimensionRecord;
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

    private static class Biff7 {
        private Biff7() {
        }

        Biff7(AnonymousClass1 x0) {
            this();
        }
    }

    public DimensionRecord(Record t) {
        super(t);
        byte[] data = t.getData();
        if (data.length == 10) {
            read10ByteData(data);
        } else {
            read14ByteData(data);
        }
    }

    public DimensionRecord(Record t, Biff7 biff72) {
        super(t);
        read10ByteData(t.getData());
    }

    private void read10ByteData(byte[] data) {
        this.numRows = IntegerHelper.getInt(data[2], data[3]);
        this.numCols = IntegerHelper.getInt(data[6], data[7]);
    }

    private void read14ByteData(byte[] data) {
        this.numRows = IntegerHelper.getInt(data[4], data[5], data[6], data[7]);
        this.numCols = IntegerHelper.getInt(data[10], data[11]);
    }

    public int getNumberOfRows() {
        return this.numRows;
    }

    public int getNumberOfColumns() {
        return this.numCols;
    }
}

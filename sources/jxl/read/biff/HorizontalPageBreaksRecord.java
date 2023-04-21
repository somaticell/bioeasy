package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;

class HorizontalPageBreaksRecord extends RecordData {
    public static Biff7 biff7 = new Biff7((AnonymousClass1) null);
    static Class class$jxl$read$biff$HorizontalPageBreaksRecord;
    private final Logger logger;
    private int[] rowBreaks;

    /* renamed from: jxl.read.biff.HorizontalPageBreaksRecord$1  reason: invalid class name */
    static class AnonymousClass1 {
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

    public HorizontalPageBreaksRecord(Record t) {
        super(t);
        Class cls;
        if (class$jxl$read$biff$HorizontalPageBreaksRecord == null) {
            cls = class$("jxl.read.biff.HorizontalPageBreaksRecord");
            class$jxl$read$biff$HorizontalPageBreaksRecord = cls;
        } else {
            cls = class$jxl$read$biff$HorizontalPageBreaksRecord;
        }
        this.logger = Logger.getLogger(cls);
        byte[] data = t.getData();
        int numbreaks = IntegerHelper.getInt(data[0], data[1]);
        int pos = 2;
        this.rowBreaks = new int[numbreaks];
        for (int i = 0; i < numbreaks; i++) {
            this.rowBreaks[i] = IntegerHelper.getInt(data[pos], data[pos + 1]);
            pos += 2;
        }
    }

    public HorizontalPageBreaksRecord(Record t, Biff7 biff72) {
        super(t);
        Class cls;
        if (class$jxl$read$biff$HorizontalPageBreaksRecord == null) {
            cls = class$("jxl.read.biff.HorizontalPageBreaksRecord");
            class$jxl$read$biff$HorizontalPageBreaksRecord = cls;
        } else {
            cls = class$jxl$read$biff$HorizontalPageBreaksRecord;
        }
        this.logger = Logger.getLogger(cls);
        byte[] data = t.getData();
        int numbreaks = IntegerHelper.getInt(data[0], data[1]);
        int pos = 2;
        this.rowBreaks = new int[numbreaks];
        for (int i = 0; i < numbreaks; i++) {
            this.rowBreaks[i] = IntegerHelper.getInt(data[pos], data[pos + 1]);
            pos += 2;
        }
    }

    public int[] getRowBreaks() {
        return this.rowBreaks;
    }
}

package jxl.read.biff;

import common.Logger;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.StringHelper;

public class HeaderRecord extends RecordData {
    public static Biff7 biff7 = new Biff7((AnonymousClass1) null);
    static Class class$jxl$read$biff$HeaderRecord;
    private static Logger logger;
    private String header;

    /* renamed from: jxl.read.biff.HeaderRecord$1  reason: invalid class name */
    static class AnonymousClass1 {
    }

    static {
        Class cls;
        if (class$jxl$read$biff$HeaderRecord == null) {
            cls = class$("jxl.read.biff.HeaderRecord");
            class$jxl$read$biff$HeaderRecord = cls;
        } else {
            cls = class$jxl$read$biff$HeaderRecord;
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

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    HeaderRecord(Record t, WorkbookSettings ws) {
        super(t);
        boolean unicode = true;
        byte[] data = getRecord().getData();
        if (data.length != 0) {
            int chars = IntegerHelper.getInt(data[0], data[1]);
            if (data[2] != 1 ? false : unicode) {
                this.header = StringHelper.getUnicodeString(data, chars, 3);
            } else {
                this.header = StringHelper.getString(data, chars, 3, ws);
            }
        }
    }

    HeaderRecord(Record t, WorkbookSettings ws, Biff7 dummy) {
        super(t);
        byte[] data = getRecord().getData();
        if (data.length != 0) {
            this.header = StringHelper.getString(data, data[0], 1, ws);
        }
    }

    /* access modifiers changed from: package-private */
    public String getHeader() {
        return this.header;
    }
}

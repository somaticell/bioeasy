package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;

class CodepageRecord extends RecordData {
    static Class class$jxl$read$biff$CodepageRecord;
    private static Logger logger;
    private int characterSet;

    static {
        Class cls;
        if (class$jxl$read$biff$CodepageRecord == null) {
            cls = class$("jxl.read.biff.CodepageRecord");
            class$jxl$read$biff$CodepageRecord = cls;
        } else {
            cls = class$jxl$read$biff$CodepageRecord;
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

    public CodepageRecord(Record t) {
        super(t);
        byte[] data = t.getData();
        this.characterSet = IntegerHelper.getInt(data[0], data[1]);
    }

    public int getCharacterSet() {
        return this.characterSet;
    }
}

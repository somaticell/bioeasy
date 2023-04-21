package jxl.write.biff;

import common.Logger;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class ArbitraryRecord extends WritableRecordData {
    static Class class$jxl$write$biff$ArbitraryRecord;
    private static Logger logger;
    private byte[] data;

    static {
        Class cls;
        if (class$jxl$write$biff$ArbitraryRecord == null) {
            cls = class$("jxl.write.biff.ArbitraryRecord");
            class$jxl$write$biff$ArbitraryRecord = cls;
        } else {
            cls = class$jxl$write$biff$ArbitraryRecord;
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

    public ArbitraryRecord(int type, byte[] d) {
        super(Type.createType(type));
        this.data = d;
        logger.warn(new StringBuffer().append("ArbitraryRecord of type ").append(type).append(" created").toString());
    }

    public byte[] getData() {
        return this.data;
    }
}

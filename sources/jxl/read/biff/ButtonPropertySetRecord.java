package jxl.read.biff;

import common.Logger;
import jxl.biff.RecordData;

public class ButtonPropertySetRecord extends RecordData {
    static Class class$jxl$read$biff$ButtonPropertySetRecord;
    private static Logger logger;

    static {
        Class cls;
        if (class$jxl$read$biff$ButtonPropertySetRecord == null) {
            cls = class$("jxl.read.biff.ButtonPropertySetRecord");
            class$jxl$read$biff$ButtonPropertySetRecord = cls;
        } else {
            cls = class$jxl$read$biff$ButtonPropertySetRecord;
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

    ButtonPropertySetRecord(Record t) {
        super(t);
    }

    public byte[] getData() {
        return getRecord().getData();
    }
}

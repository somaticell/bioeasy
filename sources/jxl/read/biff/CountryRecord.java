package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;

public class CountryRecord extends RecordData {
    static Class class$jxl$read$biff$CountryRecord;
    private static Logger logger;
    private int language;
    private int regionalSettings;

    static {
        Class cls;
        if (class$jxl$read$biff$CountryRecord == null) {
            cls = class$("jxl.read.biff.CountryRecord");
            class$jxl$read$biff$CountryRecord = cls;
        } else {
            cls = class$jxl$read$biff$CountryRecord;
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

    public CountryRecord(Record t) {
        super(t);
        byte[] data = t.getData();
        this.language = IntegerHelper.getInt(data[0], data[1]);
        this.regionalSettings = IntegerHelper.getInt(data[2], data[3]);
    }

    public int getLanguageCode() {
        return this.language;
    }

    public int getRegionalSettingsCode() {
        return this.regionalSettings;
    }
}

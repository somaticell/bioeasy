package jxl.read.biff;

import common.Logger;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;

public class ExternalSheetRecord extends RecordData {
    public static Biff7 biff7 = new Biff7((AnonymousClass1) null);
    static Class class$jxl$read$biff$ExternalSheetRecord;
    private static Logger logger;
    private XTI[] xtiArray;

    /* renamed from: jxl.read.biff.ExternalSheetRecord$1  reason: invalid class name */
    static class AnonymousClass1 {
    }

    static {
        Class cls;
        if (class$jxl$read$biff$ExternalSheetRecord == null) {
            cls = class$("jxl.read.biff.ExternalSheetRecord");
            class$jxl$read$biff$ExternalSheetRecord = cls;
        } else {
            cls = class$jxl$read$biff$ExternalSheetRecord;
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

    private static class XTI {
        int firstTab;
        int lastTab;
        int supbookIndex;

        XTI(int s, int f, int l) {
            this.supbookIndex = s;
            this.firstTab = f;
            this.lastTab = l;
        }
    }

    ExternalSheetRecord(Record t, WorkbookSettings ws) {
        super(t);
        byte[] data = getRecord().getData();
        int numxtis = IntegerHelper.getInt(data[0], data[1]);
        if (data.length < (numxtis * 6) + 2) {
            this.xtiArray = new XTI[0];
            logger.warn("Could not process external sheets.  Formulas may be compromised.");
            return;
        }
        this.xtiArray = new XTI[numxtis];
        int pos = 2;
        for (int i = 0; i < numxtis; i++) {
            this.xtiArray[i] = new XTI(IntegerHelper.getInt(data[pos], data[pos + 1]), IntegerHelper.getInt(data[pos + 2], data[pos + 3]), IntegerHelper.getInt(data[pos + 4], data[pos + 5]));
            pos += 6;
        }
    }

    ExternalSheetRecord(Record t, WorkbookSettings settings, Biff7 dummy) {
        super(t);
        logger.warn("External sheet record for Biff 7 not supported");
    }

    public int getNumRecords() {
        if (this.xtiArray != null) {
            return this.xtiArray.length;
        }
        return 0;
    }

    public int getSupbookIndex(int index) {
        return this.xtiArray[index].supbookIndex;
    }

    public int getFirstTabIndex(int index) {
        return this.xtiArray[index].firstTab;
    }

    public int getLastTabIndex(int index) {
        return this.xtiArray[index].lastTab;
    }

    public byte[] getData() {
        return getRecord().getData();
    }
}

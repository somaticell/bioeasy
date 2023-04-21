package jxl.write.biff;

import common.Assert;
import common.Logger;
import jxl.WorkbookSettings;
import jxl.biff.EncodedURLHelper;
import jxl.biff.IntegerHelper;
import jxl.biff.StringHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class SupbookRecord extends WritableRecordData {
    public static final SupbookType ADDIN = new SupbookType((AnonymousClass1) null);
    public static final SupbookType EXTERNAL = new SupbookType((AnonymousClass1) null);
    public static final SupbookType INTERNAL = new SupbookType((AnonymousClass1) null);
    public static final SupbookType LINK = new SupbookType((AnonymousClass1) null);
    public static final SupbookType UNKNOWN = new SupbookType((AnonymousClass1) null);
    static Class class$jxl$write$biff$SupbookRecord;
    private static Logger logger;
    private byte[] data;
    private String fileName;
    private int numSheets;
    private String[] sheetNames;
    private SupbookType type;
    private WorkbookSettings workbookSettings;

    /* renamed from: jxl.write.biff.SupbookRecord$1  reason: invalid class name */
    static class AnonymousClass1 {
    }

    static {
        Class cls;
        if (class$jxl$write$biff$SupbookRecord == null) {
            cls = class$("jxl.write.biff.SupbookRecord");
            class$jxl$write$biff$SupbookRecord = cls;
        } else {
            cls = class$jxl$write$biff$SupbookRecord;
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

    private static class SupbookType {
        private SupbookType() {
        }

        SupbookType(AnonymousClass1 x0) {
            this();
        }
    }

    public SupbookRecord(int sheets, WorkbookSettings ws) {
        super(Type.SUPBOOK);
        this.numSheets = sheets;
        this.type = INTERNAL;
        this.workbookSettings = ws;
    }

    public SupbookRecord(String fn, WorkbookSettings ws) {
        super(Type.SUPBOOK);
        this.fileName = fn;
        this.numSheets = 1;
        this.sheetNames = new String[0];
        this.workbookSettings = ws;
        this.type = EXTERNAL;
    }

    public SupbookRecord(jxl.read.biff.SupbookRecord sr, WorkbookSettings ws) {
        super(Type.SUPBOOK);
        this.workbookSettings = ws;
        if (sr.getType() == jxl.read.biff.SupbookRecord.INTERNAL) {
            this.type = INTERNAL;
            this.numSheets = sr.getNumberOfSheets();
        } else if (sr.getType() == jxl.read.biff.SupbookRecord.EXTERNAL) {
            this.type = EXTERNAL;
            this.numSheets = sr.getNumberOfSheets();
            this.fileName = sr.getFileName();
            this.sheetNames = new String[this.numSheets];
            for (int i = 0; i < this.numSheets; i++) {
                this.sheetNames[i] = sr.getSheetName(i);
            }
        }
    }

    private void initInternal(jxl.read.biff.SupbookRecord sr) {
        this.numSheets = sr.getNumberOfSheets();
        initInternal();
    }

    private void initInternal() {
        this.data = new byte[4];
        IntegerHelper.getTwoBytes(this.numSheets, this.data, 0);
        this.data[2] = 1;
        this.data[3] = 4;
        this.type = INTERNAL;
    }

    /* access modifiers changed from: package-private */
    public void adjustInternal(int sheets) {
        Assert.verify(this.type == INTERNAL);
        this.numSheets = sheets;
        initInternal();
    }

    private void initExternal() {
        int totalSheetNameLength = 0;
        for (int i = 0; i < this.numSheets; i++) {
            totalSheetNameLength += this.sheetNames[i].length();
        }
        byte[] fileNameData = EncodedURLHelper.getEncodedURL(this.fileName, this.workbookSettings);
        this.data = new byte[(fileNameData.length + 6 + (this.numSheets * 3) + (totalSheetNameLength * 2))];
        IntegerHelper.getTwoBytes(this.numSheets, this.data, 0);
        IntegerHelper.getTwoBytes(fileNameData.length + 1, this.data, 2);
        this.data[4] = 0;
        this.data[5] = 1;
        System.arraycopy(fileNameData, 0, this.data, 6, fileNameData.length);
        int pos = 2 + fileNameData.length + 4;
        for (int i2 = 0; i2 < this.sheetNames.length; i2++) {
            IntegerHelper.getTwoBytes(this.sheetNames[i2].length(), this.data, pos);
            this.data[pos + 2] = 1;
            StringHelper.getUnicodeBytes(this.sheetNames[i2], this.data, pos + 3);
            pos += (this.sheetNames[i2].length() * 2) + 3;
        }
    }

    public byte[] getData() {
        if (this.type == INTERNAL) {
            initInternal();
        } else if (this.type == EXTERNAL) {
            initExternal();
        } else {
            logger.warn("unsupported supbook type - defaulting to internal");
            initInternal();
        }
        return this.data;
    }

    public SupbookType getType() {
        return this.type;
    }

    public int getNumberOfSheets() {
        return this.numSheets;
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getSheetIndex(String s) {
        boolean found = false;
        int sheetIndex = 0;
        for (int i = 0; i < this.sheetNames.length && !found; i++) {
            if (this.sheetNames[i].equals(s)) {
                found = true;
                sheetIndex = 0;
            }
        }
        if (found) {
            return sheetIndex;
        }
        String[] names = new String[(this.sheetNames.length + 1)];
        names[this.sheetNames.length] = s;
        this.sheetNames = names;
        return this.sheetNames.length - 1;
    }

    public String getSheetName(int s) {
        return this.sheetNames[s];
    }
}

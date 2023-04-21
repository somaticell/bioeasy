package jxl.read.biff;

import common.Logger;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.StringHelper;

public class SupbookRecord extends RecordData {
    public static final Type ADDIN = new Type((AnonymousClass1) null);
    public static final Type EXTERNAL = new Type((AnonymousClass1) null);
    public static final Type INTERNAL = new Type((AnonymousClass1) null);
    public static final Type LINK = new Type((AnonymousClass1) null);
    public static final Type UNKNOWN = new Type((AnonymousClass1) null);
    static Class class$jxl$read$biff$SupbookRecord;
    private static Logger logger;
    private String fileName;
    private int numSheets;
    private String[] sheetNames;
    private Type type;

    /* renamed from: jxl.read.biff.SupbookRecord$1  reason: invalid class name */
    static class AnonymousClass1 {
    }

    static {
        Class cls;
        if (class$jxl$read$biff$SupbookRecord == null) {
            cls = class$("jxl.read.biff.SupbookRecord");
            class$jxl$read$biff$SupbookRecord = cls;
        } else {
            cls = class$jxl$read$biff$SupbookRecord;
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

    private static class Type {
        private Type() {
        }

        Type(AnonymousClass1 x0) {
            this();
        }
    }

    SupbookRecord(Record t, WorkbookSettings ws) {
        super(t);
        byte[] data = getRecord().getData();
        if (data.length == 4) {
            if (data[2] == 1 && data[3] == 4) {
                this.type = INTERNAL;
            } else if (data[2] == 1 && data[3] == 58) {
                this.type = ADDIN;
            } else {
                this.type = UNKNOWN;
            }
        } else if (data[0] == 0 && data[1] == 0) {
            this.type = LINK;
        } else {
            this.type = EXTERNAL;
        }
        if (this.type == INTERNAL) {
            this.numSheets = IntegerHelper.getInt(data[0], data[1]);
        }
        if (this.type == EXTERNAL) {
            readExternal(data, ws);
        }
    }

    private void readExternal(byte[] data, WorkbookSettings ws) {
        int pos;
        this.numSheets = IntegerHelper.getInt(data[0], data[1]);
        int ln = IntegerHelper.getInt(data[2], data[3]) - 1;
        if (data[4] == 0) {
            if (data[5] == 0) {
                this.fileName = StringHelper.getString(data, ln, 6, ws);
                pos = 6 + ln;
            } else {
                this.fileName = getEncodedFilename(data, ln, 6);
                pos = 6 + ln;
            }
        } else if (IntegerHelper.getInt(data[5], data[6]) == 0) {
            this.fileName = StringHelper.getUnicodeString(data, ln, 7);
            pos = 7 + (ln * 2);
        } else {
            this.fileName = getUnicodeEncodedFilename(data, ln, 7);
            pos = 7 + (ln * 2);
        }
        this.sheetNames = new String[this.numSheets];
        for (int i = 0; i < this.sheetNames.length; i++) {
            int ln2 = IntegerHelper.getInt(data[pos], data[pos + 1]);
            if (data[pos + 2] == 0) {
                this.sheetNames[i] = StringHelper.getString(data, ln2, pos + 3, ws);
                pos += ln2 + 3;
            } else if (data[pos + 2] == 1) {
                this.sheetNames[i] = StringHelper.getUnicodeString(data, ln2, pos + 3);
                pos += (ln2 * 2) + 3;
            }
        }
    }

    public Type getType() {
        return this.type;
    }

    public int getNumberOfSheets() {
        return this.numSheets;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getSheetName(int i) {
        return this.sheetNames[i];
    }

    public byte[] getData() {
        return getRecord().getData();
    }

    private String getEncodedFilename(byte[] data, int ln, int pos) {
        StringBuffer buf = new StringBuffer();
        int endpos = pos + ln;
        while (pos < endpos) {
            char c = (char) data[pos];
            if (c == 1) {
                pos++;
                buf.append((char) data[pos]);
                buf.append(":\\\\");
            } else if (c == 2) {
                buf.append('\\');
            } else if (c == 3) {
                buf.append('\\');
            } else if (c == 4) {
                buf.append("..\\");
            } else {
                buf.append(c);
            }
            pos++;
        }
        return buf.toString();
    }

    private String getUnicodeEncodedFilename(byte[] data, int ln, int pos) {
        StringBuffer buf = new StringBuffer();
        int endpos = pos + (ln * 2);
        while (pos < endpos) {
            char c = (char) IntegerHelper.getInt(data[pos], data[pos + 1]);
            if (c == 1) {
                pos += 2;
                buf.append((char) IntegerHelper.getInt(data[pos], data[pos + 1]));
                buf.append(":\\\\");
            } else if (c == 2) {
                buf.append('\\');
            } else if (c == 3) {
                buf.append('\\');
            } else if (c == 4) {
                buf.append("..\\");
            } else {
                buf.append(c);
            }
            pos += 2;
        }
        return buf.toString();
    }
}

package jxl.biff.drawing;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.read.biff.Record;

public class TextObjectRecord extends WritableRecordData {
    static Class class$jxl$biff$drawing$TextObjectRecord;
    private static Logger logger;
    private byte[] data;
    private int textLength;

    static {
        Class cls;
        if (class$jxl$biff$drawing$TextObjectRecord == null) {
            cls = class$("jxl.biff.drawing.TextObjectRecord");
            class$jxl$biff$drawing$TextObjectRecord = cls;
        } else {
            cls = class$jxl$biff$drawing$TextObjectRecord;
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

    TextObjectRecord(String t) {
        super(Type.TXO);
        this.textLength = t.length();
    }

    public TextObjectRecord(Record t) {
        super(t);
        this.data = getRecord().getData();
        this.textLength = IntegerHelper.getInt(this.data[10], this.data[11]);
    }

    public TextObjectRecord(byte[] d) {
        super(Type.TXO);
        this.data = d;
    }

    public byte[] getData() {
        if (this.data != null) {
            return this.data;
        }
        this.data = new byte[18];
        IntegerHelper.getTwoBytes(0 | 2 | 16 | 512, this.data, 0);
        IntegerHelper.getTwoBytes(this.textLength, this.data, 10);
        IntegerHelper.getTwoBytes(16, this.data, 12);
        return this.data;
    }
}

package jxl.biff.drawing;

import common.Logger;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.read.biff.Record;

public class MsoDrawingRecord extends WritableRecordData {
    static Class class$jxl$biff$drawing$MsoDrawingRecord;
    private static Logger logger;
    private byte[] data;
    private boolean first;

    static {
        Class cls;
        if (class$jxl$biff$drawing$MsoDrawingRecord == null) {
            cls = class$("jxl.biff.drawing.MsoDrawingRecord");
            class$jxl$biff$drawing$MsoDrawingRecord = cls;
        } else {
            cls = class$jxl$biff$drawing$MsoDrawingRecord;
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

    public MsoDrawingRecord(Record t) {
        super(t);
        this.data = getRecord().getData();
        this.first = false;
    }

    public MsoDrawingRecord(byte[] d) {
        super(Type.MSODRAWING);
        this.data = d;
        this.first = false;
    }

    public byte[] getData() {
        return this.data;
    }

    public Record getRecord() {
        return super.getRecord();
    }

    public void setFirst() {
        this.first = true;
    }

    public boolean isFirst() {
        return this.first;
    }
}

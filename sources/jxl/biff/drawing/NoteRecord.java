package jxl.biff.drawing;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.read.biff.Record;

public class NoteRecord extends WritableRecordData {
    static Class class$jxl$biff$drawing$NoteRecord;
    private static Logger logger;
    private int column;
    private byte[] data;
    private int objectId;
    private int row;

    static {
        Class cls;
        if (class$jxl$biff$drawing$NoteRecord == null) {
            cls = class$("jxl.biff.drawing.NoteRecord");
            class$jxl$biff$drawing$NoteRecord = cls;
        } else {
            cls = class$jxl$biff$drawing$NoteRecord;
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

    public NoteRecord(Record t) {
        super(t);
        this.data = getRecord().getData();
        this.row = IntegerHelper.getInt(this.data[0], this.data[1]);
        this.column = IntegerHelper.getInt(this.data[2], this.data[3]);
        this.objectId = IntegerHelper.getInt(this.data[6], this.data[7]);
    }

    public NoteRecord(byte[] d) {
        super(Type.NOTE);
        this.data = d;
    }

    public NoteRecord(int c, int r, int id) {
        super(Type.NOTE);
        this.row = r;
        this.column = c;
        this.objectId = id;
    }

    public byte[] getData() {
        if (this.data != null) {
            return this.data;
        }
        this.data = new byte[("".length() + 8 + 4)];
        IntegerHelper.getTwoBytes(this.row, this.data, 0);
        IntegerHelper.getTwoBytes(this.column, this.data, 2);
        IntegerHelper.getTwoBytes(this.objectId, this.data, 6);
        IntegerHelper.getTwoBytes("".length(), this.data, 8);
        return this.data;
    }

    /* access modifiers changed from: package-private */
    public int getRow() {
        return this.row;
    }

    /* access modifiers changed from: package-private */
    public int getColumn() {
        return this.column;
    }

    public int getObjectId() {
        return this.objectId;
    }
}

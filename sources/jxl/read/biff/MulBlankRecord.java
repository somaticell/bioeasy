package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;

class MulBlankRecord extends RecordData {
    static Class class$jxl$read$biff$MulBlankRecord;
    private static Logger logger;
    private int colFirst;
    private int colLast;
    private int numblanks = ((this.colLast - this.colFirst) + 1);
    private int row;
    private int[] xfIndices = new int[this.numblanks];

    static {
        Class cls;
        if (class$jxl$read$biff$MulBlankRecord == null) {
            cls = class$("jxl.read.biff.MulBlankRecord");
            class$jxl$read$biff$MulBlankRecord = cls;
        } else {
            cls = class$jxl$read$biff$MulBlankRecord;
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

    public MulBlankRecord(Record t) {
        super(t);
        byte[] data = getRecord().getData();
        int length = getRecord().getLength();
        this.row = IntegerHelper.getInt(data[0], data[1]);
        this.colFirst = IntegerHelper.getInt(data[2], data[3]);
        this.colLast = IntegerHelper.getInt(data[length - 2], data[length - 1]);
        readBlanks(data);
    }

    private void readBlanks(byte[] data) {
        int pos = 4;
        for (int i = 0; i < this.numblanks; i++) {
            this.xfIndices[i] = IntegerHelper.getInt(data[pos], data[pos + 1]);
            pos += 2;
        }
    }

    public int getRow() {
        return this.row;
    }

    public int getFirstColumn() {
        return this.colFirst;
    }

    public int getNumberOfColumns() {
        return this.numblanks;
    }

    public int getXFIndex(int index) {
        return this.xfIndices[index];
    }
}

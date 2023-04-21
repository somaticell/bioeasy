package jxl.read.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.Type;

public class ColumnInfoRecord extends RecordData {
    private byte[] data;
    private int endColumn = IntegerHelper.getInt(this.data[2], this.data[3]);
    private boolean hidden;
    private int startColumn = IntegerHelper.getInt(this.data[0], this.data[1]);
    private int width = IntegerHelper.getInt(this.data[4], this.data[5]);
    private int xfIndex = IntegerHelper.getInt(this.data[6], this.data[7]);

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ColumnInfoRecord(Record t) {
        super(Type.COLINFO);
        boolean z = true;
        this.data = t.getData();
        this.hidden = (IntegerHelper.getInt(this.data[8], this.data[9]) & 1) == 0 ? false : z;
    }

    public int getStartColumn() {
        return this.startColumn;
    }

    public int getEndColumn() {
        return this.endColumn;
    }

    public int getXFIndex() {
        return this.xfIndex;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean getHidden() {
        return this.hidden;
    }
}

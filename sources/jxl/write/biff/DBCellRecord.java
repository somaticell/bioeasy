package jxl.write.biff;

import java.util.ArrayList;
import java.util.Iterator;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class DBCellRecord extends WritableRecordData {
    private int cellOffset;
    private ArrayList cellRowPositions = new ArrayList(10);
    private int position;
    private int rowPos;

    public DBCellRecord(int rp) {
        super(Type.DBCELL);
        this.rowPos = rp;
    }

    /* access modifiers changed from: package-private */
    public void setCellOffset(int pos) {
        this.cellOffset = pos;
    }

    /* access modifiers changed from: package-private */
    public void addCellRowPosition(int pos) {
        this.cellRowPositions.add(new Integer(pos));
    }

    /* access modifiers changed from: package-private */
    public void setPosition(int pos) {
        this.position = pos;
    }

    /* access modifiers changed from: protected */
    public byte[] getData() {
        byte[] data = new byte[((this.cellRowPositions.size() * 2) + 4)];
        IntegerHelper.getFourBytes(this.position - this.rowPos, data, 0);
        int pos = 4;
        int lastCellPos = this.cellOffset;
        Iterator i = this.cellRowPositions.iterator();
        while (i.hasNext()) {
            int cellPos = ((Integer) i.next()).intValue();
            IntegerHelper.getTwoBytes(cellPos - lastCellPos, data, pos);
            lastCellPos = cellPos;
            pos += 2;
        }
        return data;
    }
}

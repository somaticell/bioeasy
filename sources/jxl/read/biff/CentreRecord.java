package jxl.read.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;

class CentreRecord extends RecordData {
    private boolean centre;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public CentreRecord(Record t) {
        super(t);
        boolean z = true;
        byte[] data = getRecord().getData();
        this.centre = IntegerHelper.getInt(data[0], data[1]) == 0 ? false : z;
    }

    public boolean isCentre() {
        return this.centre;
    }
}

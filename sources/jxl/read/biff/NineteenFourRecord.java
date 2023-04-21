package jxl.read.biff;

import jxl.biff.RecordData;

class NineteenFourRecord extends RecordData {
    private boolean nineteenFour;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NineteenFourRecord(Record t) {
        super(t);
        boolean z = true;
        this.nineteenFour = getRecord().getData()[0] != 1 ? false : z;
    }

    public boolean is1904() {
        return this.nineteenFour;
    }
}

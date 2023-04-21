package jxl.read.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;

class ProtectRecord extends RecordData {
    private boolean prot;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ProtectRecord(Record t) {
        super(t);
        boolean z = true;
        byte[] data = getRecord().getData();
        this.prot = IntegerHelper.getInt(data[0], data[1]) != 1 ? false : z;
    }

    /* access modifiers changed from: package-private */
    public boolean isProtected() {
        return this.prot;
    }
}

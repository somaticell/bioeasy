package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class HideobjRecord extends WritableRecordData {
    private byte[] data = new byte[2];
    private boolean hideAll;

    public HideobjRecord(boolean hide) {
        super(Type.HIDEOBJ);
        this.hideAll = hide;
        if (this.hideAll) {
            IntegerHelper.getTwoBytes(2, this.data, 0);
        }
    }

    public byte[] getData() {
        return this.data;
    }
}

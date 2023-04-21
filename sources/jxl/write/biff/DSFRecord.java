package jxl.write.biff;

import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class DSFRecord extends WritableRecordData {
    private byte[] data = {0, 0};

    public DSFRecord() {
        super(Type.DSF);
    }

    public byte[] getData() {
        return this.data;
    }
}

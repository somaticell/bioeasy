package jxl.write.biff;

import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class InterfaceHeaderRecord extends WritableRecordData {
    public InterfaceHeaderRecord() {
        super(Type.INTERFACEHDR);
    }

    public byte[] getData() {
        return new byte[]{-80, 4};
    }
}

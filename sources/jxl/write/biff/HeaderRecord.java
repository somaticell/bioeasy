package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.StringHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class HeaderRecord extends WritableRecordData {
    private byte[] data;
    private String header;

    public HeaderRecord(String h) {
        super(Type.HEADER);
        this.header = h;
    }

    public HeaderRecord(HeaderRecord hr) {
        super(Type.HEADER);
        this.header = hr.header;
    }

    public byte[] getData() {
        if (this.header == null || this.header.length() == 0) {
            this.data = new byte[0];
            return this.data;
        }
        this.data = new byte[((this.header.length() * 2) + 3)];
        IntegerHelper.getTwoBytes(this.header.length(), this.data, 0);
        this.data[2] = 1;
        StringHelper.getUnicodeBytes(this.header, this.data, 3);
        return this.data;
    }
}

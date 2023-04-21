package jxl.write.biff;

import jxl.Workbook;
import jxl.biff.StringHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class WriteAccessRecord extends WritableRecordData {
    private static final String authorString = "Java Excel API";
    private byte[] data = new byte[112];

    public WriteAccessRecord() {
        super(Type.WRITEACCESS);
        String astring = new StringBuffer().append("Java Excel API v").append(Workbook.getVersion()).toString();
        StringHelper.getBytes(astring, this.data, 0);
        for (int i = astring.length(); i < this.data.length; i++) {
            this.data[i] = 32;
        }
    }

    public byte[] getData() {
        return this.data;
    }
}

package jxl.write.biff;

import jxl.biff.StringHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class BoundsheetRecord extends WritableRecordData {
    private boolean chartOnly = false;
    private byte[] data;
    private boolean hidden = false;
    private String name;

    public BoundsheetRecord(String n) {
        super(Type.BOUNDSHEET);
        this.name = n;
    }

    /* access modifiers changed from: package-private */
    public void setHidden() {
        this.hidden = true;
    }

    /* access modifiers changed from: package-private */
    public void setChartOnly() {
        this.chartOnly = true;
    }

    public byte[] getData() {
        this.data = new byte[((this.name.length() * 2) + 8)];
        if (this.chartOnly) {
            this.data[5] = 2;
        } else {
            this.data[5] = 0;
        }
        if (this.hidden) {
            this.data[4] = 1;
            this.data[5] = 0;
        }
        this.data[6] = (byte) this.name.length();
        this.data[7] = 1;
        StringHelper.getUnicodeBytes(this.name, this.data, 8);
        return this.data;
    }
}

package jxl.biff;

class BuiltInStyle extends WritableRecordData {
    private int styleNumber;
    private int xfIndex;

    public BuiltInStyle(int xfind, int sn) {
        super(Type.STYLE);
        this.xfIndex = xfind;
        this.styleNumber = sn;
    }

    public byte[] getData() {
        byte[] data = new byte[4];
        IntegerHelper.getTwoBytes(this.xfIndex, data, 0);
        data[1] = (byte) (data[1] | 128);
        data[2] = (byte) this.styleNumber;
        data[3] = -1;
        return data;
    }
}

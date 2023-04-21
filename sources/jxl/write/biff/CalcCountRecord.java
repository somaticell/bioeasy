package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class CalcCountRecord extends WritableRecordData {
    private int calcCount;
    private byte[] data;

    public CalcCountRecord(int cnt) {
        super(Type.CALCCOUNT);
        this.calcCount = cnt;
    }

    public byte[] getData() {
        byte[] data2 = new byte[2];
        IntegerHelper.getTwoBytes(this.calcCount, data2, 0);
        return data2;
    }
}

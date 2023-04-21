package jxl.read.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;

public class DataValidityListRecord extends RecordData {
    private int numSettings;

    DataValidityListRecord(Record t) {
        super(t);
        byte[] data = getRecord().getData();
        this.numSettings = IntegerHelper.getInt(data[14], data[15], data[16], data[17]);
    }

    /* access modifiers changed from: package-private */
    public int getNumberOfSettings() {
        return this.numSettings;
    }

    public byte[] getData() {
        return getRecord().getData();
    }
}

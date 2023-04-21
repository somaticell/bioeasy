package jxl.read.biff;

import jxl.biff.RecordData;

public class DataValiditySettingsRecord extends RecordData {
    DataValiditySettingsRecord(Record t) {
        super(t);
    }

    public byte[] getData() {
        return getRecord().getData();
    }
}

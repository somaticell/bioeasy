package jxl.read.biff;

import jxl.biff.RecordData;

public class PaletteRecord extends RecordData {
    PaletteRecord(Record t) {
        super(t);
    }

    public byte[] getData() {
        return getRecord().getData();
    }
}

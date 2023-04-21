package jxl.read.biff;

import jxl.biff.RecordData;

class PrintHeadersRecord extends RecordData {
    private boolean printHeaders;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PrintHeadersRecord(Record ph) {
        super(ph);
        boolean z = true;
        this.printHeaders = ph.getData()[0] != 1 ? false : z;
    }

    public boolean getPrintHeaders() {
        return this.printHeaders;
    }
}

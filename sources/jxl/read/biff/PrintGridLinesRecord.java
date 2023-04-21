package jxl.read.biff;

import jxl.biff.RecordData;

class PrintGridLinesRecord extends RecordData {
    private boolean printGridLines;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PrintGridLinesRecord(Record pgl) {
        super(pgl);
        boolean z = true;
        this.printGridLines = pgl.getData()[0] != 1 ? false : z;
    }

    public boolean getPrintGridLines() {
        return this.printGridLines;
    }
}

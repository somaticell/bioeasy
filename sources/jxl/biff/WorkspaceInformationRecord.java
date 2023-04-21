package jxl.biff;

import jxl.read.biff.Record;

public class WorkspaceInformationRecord extends WritableRecordData {
    private static final int defaultOptions = 1217;
    private static final int fitToPages = 256;
    private int wsoptions;

    public WorkspaceInformationRecord(Record t) {
        super(t);
        byte[] data = getRecord().getData();
        this.wsoptions = IntegerHelper.getInt(data[0], data[1]);
    }

    public WorkspaceInformationRecord() {
        super(Type.WSBOOL);
        this.wsoptions = defaultOptions;
    }

    public boolean getFitToPages() {
        return (this.wsoptions & 256) != 0;
    }

    public void setFitToPages(boolean b) {
        this.wsoptions = b ? this.wsoptions | 256 : this.wsoptions & -257;
    }

    public byte[] getData() {
        byte[] data = new byte[2];
        IntegerHelper.getTwoBytes(this.wsoptions, data, 0);
        return data;
    }
}

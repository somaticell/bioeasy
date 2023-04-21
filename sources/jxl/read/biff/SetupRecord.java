package jxl.read.biff;

import jxl.biff.DoubleHelper;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.Type;

public class SetupRecord extends RecordData {
    private int copies = IntegerHelper.getInt(this.data[32], this.data[33]);
    private byte[] data;
    private int fitHeight = IntegerHelper.getInt(this.data[8], this.data[9]);
    private int fitWidth = IntegerHelper.getInt(this.data[6], this.data[7]);
    private double footerMargin = DoubleHelper.getIEEEDouble(this.data, 24);
    private double headerMargin = DoubleHelper.getIEEEDouble(this.data, 16);
    private int horizontalPrintResolution = IntegerHelper.getInt(this.data[12], this.data[13]);
    private int pageStart = IntegerHelper.getInt(this.data[4], this.data[5]);
    private int paperSize = IntegerHelper.getInt(this.data[0], this.data[1]);
    private boolean portraitOrientation;
    private int scaleFactor = IntegerHelper.getInt(this.data[2], this.data[3]);
    private int verticalPrintResolution = IntegerHelper.getInt(this.data[14], this.data[15]);

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SetupRecord(Record t) {
        super(Type.SETUP);
        boolean z = true;
        this.data = t.getData();
        this.portraitOrientation = (IntegerHelper.getInt(this.data[10], this.data[11]) & 2) == 0 ? false : z;
    }

    public boolean isPortrait() {
        return this.portraitOrientation;
    }

    public double getHeaderMargin() {
        return this.headerMargin;
    }

    public double getFooterMargin() {
        return this.footerMargin;
    }

    public int getPaperSize() {
        return this.paperSize;
    }

    public int getScaleFactor() {
        return this.scaleFactor;
    }

    public int getPageStart() {
        return this.pageStart;
    }

    public int getFitWidth() {
        return this.fitWidth;
    }

    public int getFitHeight() {
        return this.fitHeight;
    }

    public int getHorizontalPrintResolution() {
        return this.horizontalPrintResolution;
    }

    public int getVerticalPrintResolution() {
        return this.verticalPrintResolution;
    }

    public int getCopies() {
        return this.copies;
    }
}

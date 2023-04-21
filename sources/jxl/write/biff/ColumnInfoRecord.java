package jxl.write.biff;

import jxl.biff.FormattingRecords;
import jxl.biff.IndexMapping;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.biff.XFRecord;

class ColumnInfoRecord extends WritableRecordData {
    private int column;
    private byte[] data;
    private boolean hidden;
    private XFRecord style;
    private int width;
    private int xfIndex;

    public ColumnInfoRecord(int col, int w, XFRecord xf) {
        super(Type.COLINFO);
        this.column = col;
        this.width = w;
        this.style = xf;
        this.xfIndex = this.style.getXFIndex();
        this.hidden = false;
    }

    public ColumnInfoRecord(jxl.read.biff.ColumnInfoRecord cir, int col, FormattingRecords fr) {
        super(Type.COLINFO);
        this.column = col;
        this.width = cir.getWidth();
        this.xfIndex = cir.getXFIndex();
        this.style = fr.getXFRecord(this.xfIndex);
    }

    public int getColumn() {
        return this.column;
    }

    public void incrementColumn() {
        this.column++;
    }

    public void decrementColumn() {
        this.column--;
    }

    /* access modifiers changed from: package-private */
    public int getWidth() {
        return this.width;
    }

    public byte[] getData() {
        this.data = new byte[12];
        IntegerHelper.getTwoBytes(this.column, this.data, 0);
        IntegerHelper.getTwoBytes(this.column, this.data, 2);
        IntegerHelper.getTwoBytes(this.width, this.data, 4);
        IntegerHelper.getTwoBytes(this.xfIndex, this.data, 6);
        int options = 6;
        if (this.hidden) {
            options = 6 | 1;
        }
        IntegerHelper.getTwoBytes(options, this.data, 8);
        return this.data;
    }

    public XFRecord getCellFormat() {
        return this.style;
    }

    /* access modifiers changed from: package-private */
    public void rationalize(IndexMapping xfmapping) {
        this.xfIndex = xfmapping.getNewIndex(this.xfIndex);
    }

    /* access modifiers changed from: package-private */
    public void setHidden(boolean h) {
        this.hidden = h;
    }

    /* access modifiers changed from: package-private */
    public boolean getHidden() {
        return this.hidden;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ColumnInfoRecord)) {
            return false;
        }
        ColumnInfoRecord cir = (ColumnInfoRecord) o;
        int i = cir.column;
        if (this.column != cir.column || this.xfIndex != cir.xfIndex || this.width != cir.width || this.hidden != cir.hidden) {
            return false;
        }
        if (this.style == null && cir.style != null) {
            return false;
        }
        if (this.style == null || cir.style != null) {
            return this.style.equals(cir.style);
        }
        return false;
    }

    public int hashCode() {
        int hashValue = ((((((this.column + 10823) * 79) + this.xfIndex) * 79) + this.width) * 79) + (this.hidden ? 1 : 0);
        if (this.style != null) {
            return hashValue ^ this.style.hashCode();
        }
        return hashValue;
    }
}

package jxl.write.biff;

import common.Assert;
import common.Logger;
import jxl.CellType;
import jxl.LabelCell;
import jxl.biff.FormattingRecords;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.format.CellFormat;

public abstract class LabelRecord extends CellValue {
    static Class class$jxl$write$biff$LabelRecord;
    private static Logger logger;
    private String contents;
    private int index;
    private SharedStrings sharedStrings;

    static {
        Class cls;
        if (class$jxl$write$biff$LabelRecord == null) {
            cls = class$("jxl.write.biff.LabelRecord");
            class$jxl$write$biff$LabelRecord = cls;
        } else {
            cls = class$jxl$write$biff$LabelRecord;
        }
        logger = Logger.getLogger(cls);
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    protected LabelRecord(int c, int r, String cont) {
        super(Type.LABELSST, c, r);
        this.contents = cont;
        if (this.contents == null) {
            this.contents = "";
        }
    }

    protected LabelRecord(int c, int r, String cont, CellFormat st) {
        super(Type.LABELSST, c, r, st);
        this.contents = cont;
        if (this.contents == null) {
            this.contents = "";
        }
    }

    protected LabelRecord(int c, int r, LabelRecord lr) {
        super(Type.LABELSST, c, r, (CellValue) lr);
        this.contents = lr.contents;
    }

    protected LabelRecord(LabelCell lc) {
        super(Type.LABELSST, lc);
        this.contents = lc.getString();
        if (this.contents == null) {
            this.contents = "";
        }
    }

    public CellType getType() {
        return CellType.LABEL;
    }

    public byte[] getData() {
        byte[] celldata = super.getData();
        byte[] data = new byte[(celldata.length + 4)];
        System.arraycopy(celldata, 0, data, 0, celldata.length);
        IntegerHelper.getFourBytes(this.index, data, celldata.length);
        return data;
    }

    public String getContents() {
        return this.contents;
    }

    public String getString() {
        return this.contents;
    }

    /* access modifiers changed from: protected */
    public void setString(String s) {
        if (s == null) {
            s = "";
        }
        this.contents = s;
        if (isReferenced()) {
            Assert.verify(this.sharedStrings != null);
            this.index = this.sharedStrings.getIndex(this.contents);
            this.contents = this.sharedStrings.get(this.index);
        }
    }

    /* access modifiers changed from: package-private */
    public void setCellDetails(FormattingRecords fr, SharedStrings ss, WritableSheetImpl s) {
        super.setCellDetails(fr, ss, s);
        this.sharedStrings = ss;
        this.index = this.sharedStrings.getIndex(this.contents);
        this.contents = this.sharedStrings.get(this.index);
    }
}

package jxl.write.biff;

import common.Logger;
import jxl.Cell;
import jxl.CellType;
import jxl.biff.Type;
import jxl.format.CellFormat;

public abstract class BlankRecord extends CellValue {
    static Class class$jxl$write$biff$BlankRecord;
    private static Logger logger;

    static {
        Class cls;
        if (class$jxl$write$biff$BlankRecord == null) {
            cls = class$("jxl.write.biff.BlankRecord");
            class$jxl$write$biff$BlankRecord = cls;
        } else {
            cls = class$jxl$write$biff$BlankRecord;
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

    protected BlankRecord(int c, int r) {
        super(Type.BLANK, c, r);
    }

    protected BlankRecord(int c, int r, CellFormat st) {
        super(Type.BLANK, c, r, st);
    }

    protected BlankRecord(Cell c) {
        super(Type.BLANK, c);
    }

    protected BlankRecord(int c, int r, BlankRecord br) {
        super(Type.BLANK, c, r, (CellValue) br);
    }

    public CellType getType() {
        return CellType.EMPTY;
    }

    public String getContents() {
        return "";
    }
}

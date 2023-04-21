package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;

class Window2Record extends RecordData {
    static Class class$jxl$read$biff$Window2Record;
    private static Logger logger;
    private boolean displayZeroValues;
    private boolean frozenNotSplit;
    private boolean frozenPanes;
    private boolean selected;
    private boolean showGridLines;

    static {
        Class cls;
        if (class$jxl$read$biff$Window2Record == null) {
            cls = class$("jxl.read.biff.Window2Record");
            class$jxl$read$biff$Window2Record = cls;
        } else {
            cls = class$jxl$read$biff$Window2Record;
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

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public Window2Record(Record t) {
        super(t);
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4 = true;
        byte[] data = t.getData();
        int options = IntegerHelper.getInt(data[0], data[1]);
        this.selected = (options & 512) != 0;
        if ((options & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.showGridLines = z;
        if ((options & 8) != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.frozenPanes = z2;
        if ((options & 16) != 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        this.displayZeroValues = z3;
        this.frozenNotSplit = (options & 256) == 0 ? false : z4;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public boolean getShowGridLines() {
        return this.showGridLines;
    }

    public boolean getDisplayZeroValues() {
        return this.displayZeroValues;
    }

    public boolean getFrozen() {
        return this.frozenPanes;
    }

    public boolean getFrozenNotSplit() {
        return this.frozenNotSplit;
    }
}

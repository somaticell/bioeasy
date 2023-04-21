package jxl.read.biff;

import common.Assert;
import common.Logger;
import jxl.CellType;
import jxl.WorkbookSettings;
import jxl.biff.DoubleHelper;
import jxl.biff.FormattingRecords;
import jxl.biff.IntegerHelper;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;

class FormulaRecord extends CellValue {
    static Class class$jxl$read$biff$FormulaRecord;
    public static final IgnoreSharedFormula ignoreSharedFormula = new IgnoreSharedFormula((AnonymousClass1) null);
    private static Logger logger;
    private CellValue formula;
    private boolean shared = false;

    /* renamed from: jxl.read.biff.FormulaRecord$1  reason: invalid class name */
    static class AnonymousClass1 {
    }

    static {
        Class cls;
        if (class$jxl$read$biff$FormulaRecord == null) {
            cls = class$("jxl.read.biff.FormulaRecord");
            class$jxl$read$biff$FormulaRecord = cls;
        } else {
            cls = class$jxl$read$biff$FormulaRecord;
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

    private static class IgnoreSharedFormula {
        private IgnoreSharedFormula() {
        }

        IgnoreSharedFormula(AnonymousClass1 x0) {
            this();
        }
    }

    public FormulaRecord(Record t, File excelFile, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si, WorkbookSettings ws) {
        super(t, fr, si);
        byte[] data = getRecord().getData();
        if ((IntegerHelper.getInt(data[14], data[15]) & 8) != 0) {
            this.shared = true;
            if (data[6] == 0 && data[12] == -1 && data[13] == -1) {
                this.formula = new SharedStringFormulaRecord(t, excelFile, fr, es, nt, si, ws);
                return;
            }
            SharedNumberFormulaRecord snfr = new SharedNumberFormulaRecord(t, excelFile, DoubleHelper.getIEEEDouble(data, 6), fr, es, nt, si);
            snfr.setNumberFormat(fr.getNumberFormat(getXFIndex()));
            this.formula = snfr;
        } else if (data[6] == 0 && data[12] == -1 && data[13] == -1) {
            this.formula = new StringFormulaRecord(t, excelFile, fr, es, nt, si, ws);
        } else if (data[6] == 1 && data[12] == -1 && data[13] == -1) {
            this.formula = new BooleanFormulaRecord(t, fr, es, nt, si);
        } else if (data[6] == 2 && data[12] == -1 && data[13] == -1) {
            this.formula = new ErrorFormulaRecord(t, fr, es, nt, si);
        } else if (data[6] == 3 && data[12] == -1 && data[13] == -1) {
            this.formula = new StringFormulaRecord(t, fr, es, nt, si);
        } else {
            this.formula = new NumberFormulaRecord(t, fr, es, nt, si);
        }
    }

    public FormulaRecord(Record t, File excelFile, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, IgnoreSharedFormula i, SheetImpl si, WorkbookSettings ws) {
        super(t, fr, si);
        byte[] data = getRecord().getData();
        if (data[6] == 0 && data[12] == -1 && data[13] == -1) {
            this.formula = new StringFormulaRecord(t, excelFile, fr, es, nt, si, ws);
        } else if (data[6] == 1 && data[12] == -1 && data[13] == -1) {
            this.formula = new BooleanFormulaRecord(t, fr, es, nt, si);
        } else if (data[6] == 2 && data[12] == -1 && data[13] == -1) {
            this.formula = new ErrorFormulaRecord(t, fr, es, nt, si);
        } else {
            this.formula = new NumberFormulaRecord(t, fr, es, nt, si);
        }
    }

    public String getContents() {
        Assert.verify(false);
        return "";
    }

    public CellType getType() {
        Assert.verify(false);
        return CellType.EMPTY;
    }

    /* access modifiers changed from: package-private */
    public final CellValue getFormula() {
        return this.formula;
    }

    /* access modifiers changed from: package-private */
    public final boolean isShared() {
        return this.shared;
    }
}

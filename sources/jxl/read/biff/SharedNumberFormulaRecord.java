package jxl.read.biff;

import common.Logger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import jxl.CellType;
import jxl.NumberCell;
import jxl.NumberFormulaCell;
import jxl.biff.DoubleHelper;
import jxl.biff.FormattingRecords;
import jxl.biff.FormulaData;
import jxl.biff.IntegerHelper;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;

public class SharedNumberFormulaRecord extends BaseSharedFormulaRecord implements NumberCell, FormulaData, NumberFormulaCell {
    static Class class$jxl$read$biff$SharedNumberFormulaRecord;
    private static DecimalFormat defaultFormat = new DecimalFormat("#.###");
    private static Logger logger;
    private NumberFormat format = defaultFormat;
    private FormattingRecords formattingRecords;
    private double value;

    static {
        Class cls;
        if (class$jxl$read$biff$SharedNumberFormulaRecord == null) {
            cls = class$("jxl.read.biff.SharedNumberFormulaRecord");
            class$jxl$read$biff$SharedNumberFormulaRecord = cls;
        } else {
            cls = class$jxl$read$biff$SharedNumberFormulaRecord;
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

    public SharedNumberFormulaRecord(Record t, File excelFile, double v, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si) {
        super(t, fr, es, nt, si, excelFile.getPos());
        this.value = v;
    }

    /* access modifiers changed from: package-private */
    public final void setNumberFormat(NumberFormat f) {
        if (f != null) {
            this.format = f;
        }
    }

    public double getValue() {
        return this.value;
    }

    public String getContents() {
        return this.format.format(this.value);
    }

    public CellType getType() {
        return CellType.NUMBER_FORMULA;
    }

    public byte[] getFormulaData() throws FormulaException {
        if (!getSheet().getWorkbookBof().isBiff8()) {
            throw new FormulaException(FormulaException.biff8Supported);
        }
        FormulaParser fp = new FormulaParser(getTokens(), this, getExternalSheet(), getNameTable(), getSheet().getWorkbook().getSettings());
        fp.parse();
        byte[] rpnTokens = fp.getBytes();
        byte[] data = new byte[(rpnTokens.length + 22)];
        IntegerHelper.getTwoBytes(getRow(), data, 0);
        IntegerHelper.getTwoBytes(getColumn(), data, 2);
        IntegerHelper.getTwoBytes(getXFIndex(), data, 4);
        DoubleHelper.getIEEEBytes(this.value, data, 6);
        System.arraycopy(rpnTokens, 0, data, 22, rpnTokens.length);
        IntegerHelper.getTwoBytes(rpnTokens.length, data, 20);
        byte[] d = new byte[(data.length - 6)];
        System.arraycopy(data, 6, d, 0, data.length - 6);
        return d;
    }

    public NumberFormat getNumberFormat() {
        return this.format;
    }
}

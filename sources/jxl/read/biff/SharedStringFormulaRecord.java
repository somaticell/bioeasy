package jxl.read.biff;

import common.Assert;
import common.Logger;
import jxl.CellType;
import jxl.LabelCell;
import jxl.StringFormulaCell;
import jxl.WorkbookSettings;
import jxl.biff.FormattingRecords;
import jxl.biff.FormulaData;
import jxl.biff.IntegerHelper;
import jxl.biff.StringHelper;
import jxl.biff.Type;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;

public class SharedStringFormulaRecord extends BaseSharedFormulaRecord implements LabelCell, FormulaData, StringFormulaCell {
    static Class class$jxl$read$biff$SharedStringFormulaRecord;
    private static Logger logger;
    private String value;

    static {
        Class cls;
        if (class$jxl$read$biff$SharedStringFormulaRecord == null) {
            cls = class$("jxl.read.biff.SharedStringFormulaRecord");
            class$jxl$read$biff$SharedStringFormulaRecord = cls;
        } else {
            cls = class$jxl$read$biff$SharedStringFormulaRecord;
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

    public SharedStringFormulaRecord(Record t, File excelFile, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si, WorkbookSettings ws) {
        super(t, fr, es, nt, si, excelFile.getPos());
        int startpos;
        boolean unicode;
        int pos = excelFile.getPos();
        int filepos = excelFile.getPos();
        Record nextRecord = excelFile.next();
        int count = 0;
        while (nextRecord.getType() != Type.STRING && count < 4) {
            nextRecord = excelFile.next();
            count++;
        }
        Assert.verify(count < 4, new StringBuffer().append(" @ ").append(pos).toString());
        byte[] stringData = nextRecord.getData();
        int chars = IntegerHelper.getInt(stringData[0], stringData[1]);
        if (stringData.length == chars + 2) {
            startpos = 2;
            unicode = false;
        } else if (stringData[2] == 1) {
            startpos = 3;
            unicode = true;
        } else {
            startpos = 3;
            unicode = false;
        }
        if (!unicode) {
            this.value = StringHelper.getString(stringData, chars, startpos, ws);
        } else {
            this.value = StringHelper.getUnicodeString(stringData, chars, startpos);
        }
        excelFile.setPos(filepos);
    }

    public String getString() {
        return this.value;
    }

    public String getContents() {
        return this.value;
    }

    public CellType getType() {
        return CellType.STRING_FORMULA;
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
        data[6] = 0;
        data[12] = -1;
        data[13] = -1;
        System.arraycopy(rpnTokens, 0, data, 22, rpnTokens.length);
        IntegerHelper.getTwoBytes(rpnTokens.length, data, 20);
        byte[] d = new byte[(data.length - 6)];
        System.arraycopy(data, 6, d, 0, data.length - 6);
        return d;
    }
}

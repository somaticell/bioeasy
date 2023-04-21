package jxl.read.biff;

import common.Assert;
import common.Logger;
import jxl.CellType;
import jxl.LabelCell;
import jxl.StringFormulaCell;
import jxl.WorkbookSettings;
import jxl.biff.FormattingRecords;
import jxl.biff.FormulaData;
import jxl.biff.Type;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;

class StringFormulaRecord extends CellValue implements LabelCell, FormulaData, StringFormulaCell {
    static Class class$jxl$read$biff$StringFormulaRecord;
    private static Logger logger;
    private byte[] data;
    private ExternalSheet externalSheet;
    private String formulaString;
    private WorkbookMethods nameTable;
    private String value;

    static {
        Class cls;
        if (class$jxl$read$biff$StringFormulaRecord == null) {
            cls = class$("jxl.read.biff.StringFormulaRecord");
            class$jxl$read$biff$StringFormulaRecord = cls;
        } else {
            cls = class$jxl$read$biff$StringFormulaRecord;
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

    public StringFormulaRecord(Record t, File excelFile, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si, WorkbookSettings ws) {
        super(t, fr, si);
        this.externalSheet = es;
        this.nameTable = nt;
        this.data = getRecord().getData();
        int pos = excelFile.getPos();
        Record nextRecord = excelFile.next();
        int count = 0;
        while (nextRecord.getType() != Type.STRING && count < 4) {
            nextRecord = excelFile.next();
            count++;
        }
        Assert.verify(count < 4, new StringBuffer().append(" @ ").append(pos).toString());
        readString(nextRecord.getData(), ws);
    }

    public StringFormulaRecord(Record t, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si) {
        super(t, fr, si);
        this.externalSheet = es;
        this.nameTable = nt;
        this.data = getRecord().getData();
        this.value = "";
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r3v0, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r3v2, types: [byte] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void readString(byte[] r10, jxl.WorkbookSettings r11) {
        /*
            r9 = this;
            r0 = 1
            r6 = 0
            r4 = 0
            byte r7 = r10[r6]
            byte r8 = r10[r0]
            int r1 = jxl.biff.IntegerHelper.getInt(r7, r8)
            if (r1 != 0) goto L_0x0012
            java.lang.String r6 = ""
            r9.value = r6
        L_0x0011:
            return
        L_0x0012:
            int r4 = r4 + 2
            byte r3 = r10[r4]
            int r4 = r4 + 1
            r7 = r3 & 15
            if (r7 == r3) goto L_0x0026
            r4 = 0
            byte r7 = r10[r6]
            int r1 = jxl.biff.IntegerHelper.getInt(r7, r6)
            byte r3 = r10[r0]
            r4 = 2
        L_0x0026:
            r7 = r3 & 4
            if (r7 == 0) goto L_0x0045
            r2 = r0
        L_0x002b:
            r7 = r3 & 8
            if (r7 == 0) goto L_0x0047
            r5 = r0
        L_0x0030:
            if (r5 == 0) goto L_0x0034
            int r4 = r4 + 2
        L_0x0034:
            if (r2 == 0) goto L_0x0038
            int r4 = r4 + 4
        L_0x0038:
            r7 = r3 & 1
            if (r7 != 0) goto L_0x0049
        L_0x003c:
            if (r0 == 0) goto L_0x004b
            java.lang.String r6 = jxl.biff.StringHelper.getString(r10, r1, r4, r11)
            r9.value = r6
            goto L_0x0011
        L_0x0045:
            r2 = r6
            goto L_0x002b
        L_0x0047:
            r5 = r6
            goto L_0x0030
        L_0x0049:
            r0 = r6
            goto L_0x003c
        L_0x004b:
            java.lang.String r6 = jxl.biff.StringHelper.getUnicodeString(r10, r1, r4)
            r9.value = r6
            goto L_0x0011
        */
        throw new UnsupportedOperationException("Method not decompiled: jxl.read.biff.StringFormulaRecord.readString(byte[], jxl.WorkbookSettings):void");
    }

    public String getContents() {
        return this.value;
    }

    public String getString() {
        return this.value;
    }

    public CellType getType() {
        return CellType.STRING_FORMULA;
    }

    public byte[] getFormulaData() throws FormulaException {
        if (!getSheet().getWorkbook().getWorkbookBof().isBiff8()) {
            throw new FormulaException(FormulaException.biff8Supported);
        }
        byte[] d = new byte[(this.data.length - 6)];
        System.arraycopy(this.data, 6, d, 0, this.data.length - 6);
        return d;
    }

    public String getFormula() throws FormulaException {
        if (this.formulaString == null) {
            byte[] tokens = new byte[(this.data.length - 22)];
            System.arraycopy(this.data, 22, tokens, 0, tokens.length);
            FormulaParser fp = new FormulaParser(tokens, this, this.externalSheet, this.nameTable, getSheet().getWorkbook().getSettings());
            fp.parse();
            this.formulaString = fp.getFormula();
        }
        return this.formulaString;
    }
}

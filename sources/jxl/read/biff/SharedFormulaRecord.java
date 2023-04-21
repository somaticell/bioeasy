package jxl.read.biff;

import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import common.Logger;
import java.text.NumberFormat;
import java.util.ArrayList;
import jxl.Cell;
import jxl.CellType;
import jxl.biff.FormattingRecords;
import jxl.biff.IntegerHelper;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;

class SharedFormulaRecord {
    static Class class$jxl$read$biff$SharedFormulaRecord;
    private static Logger logger;
    private ExternalSheet externalSheet;
    private int firstCol;
    private int firstRow;
    private ArrayList formulas = new ArrayList();
    private int lastCol;
    private int lastRow;
    private SheetImpl sheet;
    private BaseSharedFormulaRecord templateFormula;
    private byte[] tokens;

    static {
        Class cls;
        if (class$jxl$read$biff$SharedFormulaRecord == null) {
            cls = class$("jxl.read.biff.SharedFormulaRecord");
            class$jxl$read$biff$SharedFormulaRecord = cls;
        } else {
            cls = class$jxl$read$biff$SharedFormulaRecord;
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

    public SharedFormulaRecord(Record t, BaseSharedFormulaRecord fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si) {
        this.sheet = si;
        byte[] data = t.getData();
        this.firstRow = IntegerHelper.getInt(data[0], data[1]);
        this.lastRow = IntegerHelper.getInt(data[2], data[3]);
        this.firstCol = data[4] & BLEServiceApi.CONNECTED_STATUS;
        this.lastCol = data[5] & BLEServiceApi.CONNECTED_STATUS;
        this.templateFormula = fr;
        this.tokens = new byte[(data.length - 10)];
        System.arraycopy(data, 10, this.tokens, 0, this.tokens.length);
    }

    public boolean add(BaseSharedFormulaRecord fr) {
        if (fr.getRow() < this.firstRow || fr.getRow() > this.lastRow || fr.getColumn() < this.firstCol || fr.getColumn() > this.lastCol) {
            return false;
        }
        this.formulas.add(fr);
        return true;
    }

    /* access modifiers changed from: package-private */
    public Cell[] getFormulas(FormattingRecords fr, boolean nf) {
        Cell[] sfs = new Cell[(this.formulas.size() + 1)];
        if (this.templateFormula == null) {
            logger.warn("Shared formula template formula is null");
            return new Cell[0];
        }
        this.templateFormula.setTokens(this.tokens);
        NumberFormat templateNumberFormat = null;
        if (this.templateFormula.getType() == CellType.NUMBER_FORMULA) {
            SharedNumberFormulaRecord snfr = (SharedNumberFormulaRecord) this.templateFormula;
            templateNumberFormat = snfr.getNumberFormat();
            if (fr.isDate(this.templateFormula.getXFIndex())) {
                this.templateFormula = new SharedDateFormulaRecord(snfr, fr, nf, this.sheet, snfr.getFilePos());
                this.templateFormula.setTokens(snfr.getTokens());
            }
        }
        sfs[0] = this.templateFormula;
        for (int i = 0; i < this.formulas.size(); i++) {
            BaseSharedFormulaRecord f = (BaseSharedFormulaRecord) this.formulas.get(i);
            if (f.getType() == CellType.NUMBER_FORMULA) {
                SharedNumberFormulaRecord snfr2 = (SharedNumberFormulaRecord) f;
                if (fr.isDate(f.getXFIndex())) {
                    f = new SharedDateFormulaRecord(snfr2, fr, nf, this.sheet, snfr2.getFilePos());
                } else {
                    snfr2.setNumberFormat(templateNumberFormat);
                }
            }
            f.setTokens(this.tokens);
            sfs[i + 1] = f;
        }
        return sfs;
    }

    /* access modifiers changed from: package-private */
    public BaseSharedFormulaRecord getTemplateFormula() {
        return this.templateFormula;
    }
}

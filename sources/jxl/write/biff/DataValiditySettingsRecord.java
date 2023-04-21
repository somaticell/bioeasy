package jxl.write.biff;

import common.Logger;
import jxl.WorkbookSettings;
import jxl.biff.DVParser;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.biff.formula.FormulaException;

class DataValiditySettingsRecord extends WritableRecordData {
    static Class class$jxl$write$biff$DataValiditySettingsRecord;
    private static final Logger logger;
    private byte[] data;
    private DVParser dvParser;
    private WritableWorkbookImpl workbook;
    private WorkbookSettings workbookSettings;

    static {
        Class cls;
        if (class$jxl$write$biff$DataValiditySettingsRecord == null) {
            cls = class$("jxl.write.biff.DataValiditySettingsRecord");
            class$jxl$write$biff$DataValiditySettingsRecord = cls;
        } else {
            cls = class$jxl$write$biff$DataValiditySettingsRecord;
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

    DataValiditySettingsRecord(jxl.read.biff.DataValiditySettingsRecord dvsr, WritableWorkbookImpl w, WorkbookSettings ws) {
        super(Type.DV);
        this.workbook = w;
        this.workbookSettings = ws;
        this.data = dvsr.getData();
    }

    DataValiditySettingsRecord(DataValiditySettingsRecord dvsr, WritableWorkbookImpl w, WorkbookSettings ws) {
        super(Type.DV);
        this.workbook = w;
        this.workbookSettings = ws;
        this.data = new byte[dvsr.data.length];
        System.arraycopy(dvsr.data, 0, this.data, 0, this.data.length);
    }

    private void initialize() {
        try {
            if (this.dvParser == null) {
                this.dvParser = new DVParser(this.data, this.workbook, this.workbook, this.workbookSettings);
            }
        } catch (FormulaException e) {
            logger.warn(new StringBuffer().append("Cannot read drop down range ").append(e.getMessage()).toString());
            e.printStackTrace();
        }
    }

    public byte[] getData() {
        if (this.dvParser == null) {
            return this.data;
        }
        return this.dvParser.getData();
    }

    public void insertRow(int row) {
        if (this.dvParser == null) {
            initialize();
        }
        this.dvParser.insertRow(row);
    }

    public void removeRow(int row) {
        if (this.dvParser == null) {
            initialize();
        }
        this.dvParser.removeRow(row);
    }

    public void insertColumn(int col) {
        if (this.dvParser == null) {
            initialize();
        }
        this.dvParser.insertColumn(col);
    }

    public void removeColumn(int col) {
        if (this.dvParser == null) {
            initialize();
        }
        this.dvParser.removeColumn(col);
    }

    public int getFirstColumn() {
        if (this.dvParser == null) {
            initialize();
        }
        return this.dvParser.getFirstColumn();
    }

    public int getLastColumn() {
        if (this.dvParser == null) {
            initialize();
        }
        return this.dvParser.getLastColumn();
    }

    public int getFirstRow() {
        if (this.dvParser == null) {
            initialize();
        }
        return this.dvParser.getFirstRow();
    }

    public int getLastRow() {
        if (this.dvParser == null) {
            initialize();
        }
        return this.dvParser.getLastRow();
    }
}

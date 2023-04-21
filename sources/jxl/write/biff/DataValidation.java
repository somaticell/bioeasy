package jxl.write.biff;

import common.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import jxl.WorkbookSettings;
import jxl.read.biff.DataValiditySettingsRecord;

public class DataValidation {
    static Class class$jxl$write$biff$DataValidation;
    private static final Logger logger;
    private int pos;
    private DataValidityListRecord validityList;
    private ArrayList validitySettings;
    private WritableWorkbookImpl workbook;
    private WorkbookSettings workbookSettings;

    static {
        Class cls;
        if (class$jxl$write$biff$DataValidation == null) {
            cls = class$("jxl.write.biff.DataValidation");
            class$jxl$write$biff$DataValidation = cls;
        } else {
            cls = class$jxl$write$biff$DataValidation;
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

    DataValidation(jxl.read.biff.DataValidation dv, WritableWorkbookImpl w, WorkbookSettings ws) {
        this.workbook = w;
        this.workbookSettings = ws;
        this.validityList = new DataValidityListRecord(dv.getDataValidityList());
        DataValiditySettingsRecord[] settings = dv.getDataValiditySettings();
        this.validitySettings = new ArrayList(settings.length);
        for (DataValiditySettingsRecord dataValiditySettingsRecord : settings) {
            this.validitySettings.add(new DataValiditySettingsRecord(dataValiditySettingsRecord, this.workbook, this.workbookSettings));
        }
    }

    DataValidation(DataValidation dv, WritableWorkbookImpl w, WorkbookSettings ws) {
        this.workbook = w;
        this.workbookSettings = ws;
        this.validityList = new DataValidityListRecord(dv.validityList);
        this.validitySettings = new ArrayList(dv.validitySettings.size());
        Iterator i = dv.validitySettings.iterator();
        while (i.hasNext()) {
            this.validitySettings.add(new DataValiditySettingsRecord((DataValiditySettingsRecord) i.next(), this.workbook, this.workbookSettings));
        }
    }

    public void write(File outputFile) throws IOException {
        if (this.validityList.hasDVRecords()) {
            outputFile.write(this.validityList);
            Iterator i = this.validitySettings.iterator();
            while (i.hasNext()) {
                outputFile.write((DataValiditySettingsRecord) i.next());
            }
        }
    }

    public void insertRow(int row) {
        Iterator i = this.validitySettings.iterator();
        while (i.hasNext()) {
            ((DataValiditySettingsRecord) i.next()).insertRow(row);
        }
    }

    public void removeRow(int row) {
        Iterator i = this.validitySettings.iterator();
        while (i.hasNext()) {
            DataValiditySettingsRecord dv = (DataValiditySettingsRecord) i.next();
            if (dv.getFirstRow() == row && dv.getLastRow() == row) {
                i.remove();
                this.validityList.dvRemoved();
            } else {
                dv.removeRow(row);
            }
        }
    }

    public void insertColumn(int col) {
        Iterator i = this.validitySettings.iterator();
        while (i.hasNext()) {
            ((DataValiditySettingsRecord) i.next()).insertColumn(col);
        }
    }

    public void removeColumn(int col) {
        Iterator i = this.validitySettings.iterator();
        while (i.hasNext()) {
            DataValiditySettingsRecord dv = (DataValiditySettingsRecord) i.next();
            if (dv.getFirstColumn() == col && dv.getLastColumn() == col) {
                i.remove();
                this.validityList.dvRemoved();
            } else {
                dv.removeColumn(col);
            }
        }
    }
}

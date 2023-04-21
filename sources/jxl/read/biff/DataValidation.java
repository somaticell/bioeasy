package jxl.read.biff;

import common.Assert;

public class DataValidation {
    private int pos = 0;
    private DataValidityListRecord validityList;
    private DataValiditySettingsRecord[] validitySettings = new DataValiditySettingsRecord[this.validityList.getNumberOfSettings()];

    DataValidation(DataValidityListRecord dvlr) {
        this.validityList = dvlr;
    }

    /* access modifiers changed from: package-private */
    public void add(DataValiditySettingsRecord dvsr) {
        Assert.verify(this.pos < this.validitySettings.length);
        this.validitySettings[this.pos] = dvsr;
        this.pos++;
    }

    public DataValidityListRecord getDataValidityList() {
        return this.validityList;
    }

    public DataValiditySettingsRecord[] getDataValiditySettings() {
        return this.validitySettings;
    }
}

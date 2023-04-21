package cn.com.bioeasy.healty.app.healthapp.data;

import java.io.Serializable;

public class VersionInfo implements Serializable {
    private int apkVersion;
    private String appDocument;
    private String appPackage;
    private String appServer;
    private String description;
    private String firmware;
    private String firmwarePwd;
    private boolean forceUpdate;
    private boolean refresh;
    private String testDataServer;
    private int versionCode;
    private String versionName;

    public String getFirmwarePwd() {
        return this.firmwarePwd;
    }

    public void setFirmwarePwd(String firmwarePwd2) {
        this.firmwarePwd = firmwarePwd2;
    }

    public int getApkVersion() {
        return this.apkVersion;
    }

    public void setApkVersion(int apkVersion2) {
        this.apkVersion = apkVersion2;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description2) {
        this.description = description2;
    }

    public boolean isForceUpdate() {
        return this.forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate2) {
        this.forceUpdate = forceUpdate2;
    }

    public String getAppPackage() {
        return this.appPackage;
    }

    public void setAppPackage(String appPackage2) {
        this.appPackage = appPackage2;
    }

    public String getAppServer() {
        return this.appServer;
    }

    public void setAppServer(String appServer2) {
        this.appServer = appServer2;
    }

    public String getTestDataServer() {
        return this.testDataServer;
    }

    public void setTestDataServer(String testDataServer2) {
        this.testDataServer = testDataServer2;
    }

    public String getAppDocument() {
        return this.appDocument;
    }

    public void setAppDocument(String appDocument2) {
        this.appDocument = appDocument2;
    }

    public String getFirmware() {
        return this.firmware;
    }

    public void setFirmware(String firmware2) {
        this.firmware = firmware2;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setVersionName(String versionName2) {
        this.versionName = versionName2;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(int versionCode2) {
        this.versionCode = versionCode2;
    }

    public boolean isRefresh() {
        return this.refresh;
    }

    public void setRefresh(boolean refresh2) {
        this.refresh = refresh2;
    }
}

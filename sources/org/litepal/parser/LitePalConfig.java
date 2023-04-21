package org.litepal.parser;

import java.util.ArrayList;
import java.util.List;

public class LitePalConfig {
    private String cases;
    private List<String> classNames;
    private String dbName;
    private String storage;
    private int version;

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version2) {
        this.version = version2;
    }

    public String getDbName() {
        return this.dbName;
    }

    public void setDbName(String dbName2) {
        this.dbName = dbName2;
    }

    public String getStorage() {
        return this.storage;
    }

    public void setStorage(String storage2) {
        this.storage = storage2;
    }

    public List<String> getClassNames() {
        if (this.classNames == null) {
            this.classNames = new ArrayList();
            this.classNames.add("org.litepal.model.Table_Schema");
        } else if (this.classNames.isEmpty()) {
            this.classNames.add("org.litepal.model.Table_Schema");
        }
        return this.classNames;
    }

    public void addClassName(String className) {
        getClassNames().add(className);
    }

    public void setClassNames(List<String> classNames2) {
        this.classNames = classNames2;
    }

    public String getCases() {
        return this.cases;
    }

    public void setCases(String cases2) {
        this.cases = cases2;
    }
}

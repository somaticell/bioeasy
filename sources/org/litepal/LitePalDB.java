package org.litepal;

import java.util.ArrayList;
import java.util.List;
import org.litepal.parser.LitePalConfig;
import org.litepal.parser.LitePalParser;

public class LitePalDB {
    private List<String> classNames;
    private String dbName;
    private boolean isExternalStorage = false;
    private int version;

    public static LitePalDB fromDefault(String dbName2) {
        LitePalConfig config = LitePalParser.parseLitePalConfiguration();
        LitePalDB litePalDB = new LitePalDB(dbName2, config.getVersion());
        litePalDB.setExternalStorage("external".equals(config.getStorage()));
        litePalDB.setClassNames(config.getClassNames());
        return litePalDB;
    }

    public LitePalDB(String dbName2, int version2) {
        this.dbName = dbName2;
        this.version = version2;
    }

    public int getVersion() {
        return this.version;
    }

    public String getDbName() {
        return this.dbName;
    }

    public boolean isExternalStorage() {
        return this.isExternalStorage;
    }

    public void setExternalStorage(boolean isExternalStorage2) {
        this.isExternalStorage = isExternalStorage2;
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

    /* access modifiers changed from: package-private */
    public void setClassNames(List<String> className) {
        this.classNames = className;
    }
}

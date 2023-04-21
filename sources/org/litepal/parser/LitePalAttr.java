package org.litepal.parser;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import org.litepal.exceptions.InvalidAttributesException;
import org.litepal.util.BaseUtility;
import org.litepal.util.Const;
import org.litepal.util.SharedUtil;

public final class LitePalAttr {
    private static LitePalAttr litePalAttr;
    private String cases;
    private List<String> classNames;
    private String dbName;
    private String extraKeyName;
    private String storage;
    private int version;

    private LitePalAttr() {
    }

    public static LitePalAttr getInstance() {
        if (litePalAttr == null) {
            synchronized (LitePalAttr.class) {
                if (litePalAttr == null) {
                    litePalAttr = new LitePalAttr();
                    if (BaseUtility.isLitePalXMLExists()) {
                        LitePalConfig config = LitePalParser.parseLitePalConfiguration();
                        litePalAttr.setDbName(config.getDbName());
                        litePalAttr.setVersion(config.getVersion());
                        litePalAttr.setClassNames(config.getClassNames());
                        litePalAttr.setCases(config.getCases());
                        litePalAttr.setStorage(config.getStorage());
                    }
                }
            }
        }
        return litePalAttr;
    }

    public static void clearInstance() {
        litePalAttr = null;
    }

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

    public String getExtraKeyName() {
        return this.extraKeyName;
    }

    public void setExtraKeyName(String extraKeyName2) {
        this.extraKeyName = extraKeyName2;
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

    public void checkSelfValid() {
        if (TextUtils.isEmpty(this.dbName)) {
            throw new InvalidAttributesException(InvalidAttributesException.DBNAME_IS_EMPTY_OR_NOT_DEFINED);
        }
        if (!this.dbName.endsWith(Const.Config.DB_NAME_SUFFIX)) {
            this.dbName = String.valueOf(this.dbName) + Const.Config.DB_NAME_SUFFIX;
        }
        if (this.version < 1) {
            throw new InvalidAttributesException(InvalidAttributesException.VERSION_OF_DATABASE_LESS_THAN_ONE);
        } else if (this.version < SharedUtil.getLastVersion(this.extraKeyName)) {
            throw new InvalidAttributesException(InvalidAttributesException.VERSION_IS_EARLIER_THAN_CURRENT);
        } else if (TextUtils.isEmpty(this.cases)) {
            this.cases = Const.Config.CASES_LOWER;
        } else if (!this.cases.equals(Const.Config.CASES_UPPER) && !this.cases.equals(Const.Config.CASES_LOWER) && !this.cases.equals(Const.Config.CASES_KEEP)) {
            throw new InvalidAttributesException(String.valueOf(this.cases) + InvalidAttributesException.CASES_VALUE_IS_INVALID);
        }
    }
}

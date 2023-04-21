package org.litepal.tablemanager.model;

import android.text.TextUtils;

public class ColumnModel {
    private String columnName;
    private String columnType;
    private String defaultValue = "";
    private boolean isNullable = true;
    private boolean isUnique = false;

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName2) {
        this.columnName = columnName2;
    }

    public String getColumnType() {
        return this.columnType;
    }

    public void setColumnType(String columnType2) {
        this.columnType = columnType2;
    }

    public boolean isNullable() {
        return this.isNullable;
    }

    public void setIsNullable(boolean isNullable2) {
        this.isNullable = isNullable2;
    }

    public boolean isUnique() {
        return this.isUnique;
    }

    public void setIsUnique(boolean isUnique2) {
        this.isUnique = isUnique2;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue2) {
        if (!"text".equalsIgnoreCase(this.columnType)) {
            this.defaultValue = defaultValue2;
        } else if (!TextUtils.isEmpty(defaultValue2)) {
            this.defaultValue = "'" + defaultValue2 + "'";
        }
    }

    public boolean isIdColumn() {
        return "_id".equalsIgnoreCase(this.columnName) || "id".equalsIgnoreCase(this.columnName);
    }
}

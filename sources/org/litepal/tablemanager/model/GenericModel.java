package org.litepal.tablemanager.model;

public class GenericModel {
    private String getMethodName;
    private String tableName;
    private String valueColumnName;
    private String valueColumnType;
    private String valueIdColumnName;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName2) {
        this.tableName = tableName2;
    }

    public String getValueColumnName() {
        return this.valueColumnName;
    }

    public void setValueColumnName(String valueColumnName2) {
        this.valueColumnName = valueColumnName2;
    }

    public String getValueColumnType() {
        return this.valueColumnType;
    }

    public void setValueColumnType(String valueColumnType2) {
        this.valueColumnType = valueColumnType2;
    }

    public String getValueIdColumnName() {
        return this.valueIdColumnName;
    }

    public void setValueIdColumnName(String valueIdColumnName2) {
        this.valueIdColumnName = valueIdColumnName2;
    }

    public String getGetMethodName() {
        return this.getMethodName;
    }

    public void setGetMethodName(String getMethodName2) {
        this.getMethodName = getMethodName2;
    }
}

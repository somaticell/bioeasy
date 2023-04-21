package org.litepal.tablemanager.typechange;

public class DateOrm extends OrmChange {
    public String object2Relation(String fieldType) {
        if (fieldType == null || !fieldType.equals("java.util.Date")) {
            return null;
        }
        return "integer";
    }
}

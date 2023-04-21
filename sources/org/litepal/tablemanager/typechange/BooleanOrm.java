package org.litepal.tablemanager.typechange;

public class BooleanOrm extends OrmChange {
    public String object2Relation(String fieldType) {
        if (fieldType == null || (!fieldType.equals("boolean") && !fieldType.equals("java.lang.Boolean"))) {
            return null;
        }
        return "integer";
    }
}

package org.litepal.tablemanager.typechange;

public class DecimalOrm extends OrmChange {
    public String object2Relation(String fieldType) {
        if (fieldType == null || (!fieldType.equals("float") && !fieldType.equals("java.lang.Float") && !fieldType.equals("double") && !fieldType.equals("java.lang.Double"))) {
            return null;
        }
        return "real";
    }
}

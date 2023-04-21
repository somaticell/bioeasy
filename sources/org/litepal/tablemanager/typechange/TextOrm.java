package org.litepal.tablemanager.typechange;

public class TextOrm extends OrmChange {
    public String object2Relation(String fieldType) {
        if (fieldType == null || (!fieldType.equals("char") && !fieldType.equals("java.lang.Character") && !fieldType.equals("java.lang.String"))) {
            return null;
        }
        return "text";
    }
}

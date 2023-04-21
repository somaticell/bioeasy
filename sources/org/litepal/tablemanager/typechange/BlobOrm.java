package org.litepal.tablemanager.typechange;

public class BlobOrm extends OrmChange {
    public String object2Relation(String fieldType) {
        if (fieldType == null || !fieldType.equals("[B")) {
            return null;
        }
        return "blob";
    }
}

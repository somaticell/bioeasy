package org.litepal.crud;

import android.database.sqlite.SQLiteDatabase;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.litepal.crud.model.AssociationsInfo;
import org.litepal.exceptions.DataSupportException;
import org.litepal.util.BaseUtility;
import org.litepal.util.DBUtility;

class DeleteHandler extends DataHandler {
    private List<String> foreignKeyTableToDelete;

    DeleteHandler(SQLiteDatabase db) {
        this.mDatabase = db;
    }

    /* access modifiers changed from: package-private */
    public int onDelete(DataSupport baseObj) {
        if (!baseObj.isSaved()) {
            return 0;
        }
        List<Field> supportedGenericFields = getSupportedGenericFields(baseObj.getClassName());
        deleteGenericData(baseObj.getClass(), supportedGenericFields, baseObj.getBaseObjId());
        Collection<AssociationsInfo> associationInfos = analyzeAssociations(baseObj);
        int rowsAffected = deleteCascade(baseObj) + this.mDatabase.delete(baseObj.getTableName(), "id = " + baseObj.getBaseObjId(), (String[]) null);
        clearAssociatedModelSaveState(baseObj, associationInfos);
        return rowsAffected;
    }

    /* access modifiers changed from: package-private */
    public int onDelete(Class<?> modelClass, long id) {
        deleteGenericData(modelClass, getSupportedGenericFields(modelClass.getName()), id);
        analyzeAssociations(modelClass);
        int rowsAffected = deleteCascade(modelClass, id) + this.mDatabase.delete(getTableName(modelClass), "id = " + id, (String[]) null);
        getForeignKeyTableToDelete().clear();
        return rowsAffected;
    }

    /* access modifiers changed from: package-private */
    public int onDeleteAll(String tableName, String... conditions) {
        BaseUtility.checkConditionsCorrect(conditions);
        if (conditions != null && conditions.length > 0) {
            conditions[0] = DBUtility.convertWhereClauseToColumnName(conditions[0]);
        }
        return this.mDatabase.delete(tableName, getWhereClause(conditions), getWhereArgs(conditions));
    }

    /* access modifiers changed from: package-private */
    public int onDeleteAll(Class<?> modelClass, String... conditions) {
        BaseUtility.checkConditionsCorrect(conditions);
        if (conditions != null && conditions.length > 0) {
            conditions[0] = DBUtility.convertWhereClauseToColumnName(conditions[0]);
        }
        List<Field> supportedGenericFields = getSupportedGenericFields(modelClass.getName());
        if (!supportedGenericFields.isEmpty()) {
            List<DataSupport> list = DataSupport.select("id").where(conditions).find(modelClass);
            if (list.size() > 0) {
                long[] ids = new long[list.size()];
                for (int i = 0; i < ids.length; i++) {
                    ids[i] = list.get(i).getBaseObjId();
                }
                deleteGenericData(modelClass, supportedGenericFields, ids);
            }
        }
        analyzeAssociations(modelClass);
        int rowsAffected = deleteAllCascade(modelClass, conditions) + this.mDatabase.delete(getTableName(modelClass), getWhereClause(conditions), getWhereArgs(conditions));
        getForeignKeyTableToDelete().clear();
        return rowsAffected;
    }

    private void analyzeAssociations(Class<?> modelClass) {
        for (AssociationsInfo associationInfo : getAssociationInfo(modelClass.getName())) {
            String associatedTableName = DBUtility.getTableNameByClassName(associationInfo.getAssociatedClassName());
            if (associationInfo.getAssociationType() == 2 || associationInfo.getAssociationType() == 1) {
                if (!modelClass.getName().equals(associationInfo.getClassHoldsForeignKey())) {
                    getForeignKeyTableToDelete().add(associatedTableName);
                }
            } else if (associationInfo.getAssociationType() == 3) {
                getForeignKeyTableToDelete().add(BaseUtility.changeCase(DBUtility.getIntermediateTableName(getTableName(modelClass), associatedTableName)));
            }
        }
    }

    private int deleteCascade(Class<?> modelClass, long id) {
        int rowsAffected = 0;
        for (String associatedTableName : getForeignKeyTableToDelete()) {
            String fkName = getForeignKeyColumnName(getTableName(modelClass));
            rowsAffected += this.mDatabase.delete(associatedTableName, String.valueOf(fkName) + " = " + id, (String[]) null);
        }
        return rowsAffected;
    }

    private int deleteAllCascade(Class<?> modelClass, String... conditions) {
        int rowsAffected = 0;
        for (String associatedTableName : getForeignKeyTableToDelete()) {
            String tableName = getTableName(modelClass);
            String fkName = getForeignKeyColumnName(tableName);
            StringBuilder whereClause = new StringBuilder();
            whereClause.append(fkName).append(" in (select id from ");
            whereClause.append(tableName);
            if (conditions != null && conditions.length > 0) {
                whereClause.append(" where ").append(buildConditionString(conditions));
            }
            whereClause.append(")");
            rowsAffected += this.mDatabase.delete(associatedTableName, BaseUtility.changeCase(whereClause.toString()), (String[]) null);
        }
        return rowsAffected;
    }

    private String buildConditionString(String... conditions) {
        int argCount = conditions.length - 1;
        String whereClause = conditions[0];
        for (int i = 0; i < argCount; i++) {
            whereClause = whereClause.replaceFirst("\\?", "'" + conditions[i + 1] + "'");
        }
        return whereClause;
    }

    private Collection<AssociationsInfo> analyzeAssociations(DataSupport baseObj) {
        try {
            Collection<AssociationsInfo> associationInfos = getAssociationInfo(baseObj.getClassName());
            analyzeAssociatedModels(baseObj, associationInfos);
            return associationInfos;
        } catch (Exception e) {
            throw new DataSupportException(e.getMessage(), e);
        }
    }

    private void clearAssociatedModelSaveState(DataSupport baseObj, Collection<AssociationsInfo> associationInfos) {
        DataSupport model;
        try {
            for (AssociationsInfo associationInfo : associationInfos) {
                if (associationInfo.getAssociationType() == 2 && !baseObj.getClassName().equals(associationInfo.getClassHoldsForeignKey())) {
                    Collection<DataSupport> associatedModels = getAssociatedModels(baseObj, associationInfo);
                    if (associatedModels != null && !associatedModels.isEmpty()) {
                        for (DataSupport model2 : associatedModels) {
                            if (model2 != null) {
                                model2.clearSavedState();
                            }
                        }
                    }
                } else if (associationInfo.getAssociationType() == 1 && (model = getAssociatedModel(baseObj, associationInfo)) != null) {
                    model.clearSavedState();
                }
            }
        } catch (Exception e) {
            throw new DataSupportException(e.getMessage(), e);
        }
    }

    private int deleteCascade(DataSupport baseObj) {
        return deleteAssociatedForeignKeyRows(baseObj) + deleteAssociatedJoinTableRows(baseObj);
    }

    private int deleteAssociatedForeignKeyRows(DataSupport baseObj) {
        int rowsAffected = 0;
        for (String associatedTableName : baseObj.getAssociatedModelsMapWithFK().keySet()) {
            String fkName = getForeignKeyColumnName(baseObj.getTableName());
            rowsAffected += this.mDatabase.delete(associatedTableName, String.valueOf(fkName) + " = " + baseObj.getBaseObjId(), (String[]) null);
        }
        return rowsAffected;
    }

    private int deleteAssociatedJoinTableRows(DataSupport baseObj) {
        int rowsAffected = 0;
        for (String associatedTableName : baseObj.getAssociatedModelsMapForJoinTable().keySet()) {
            rowsAffected += this.mDatabase.delete(DBUtility.getIntermediateTableName(baseObj.getTableName(), associatedTableName), String.valueOf(getForeignKeyColumnName(baseObj.getTableName())) + " = " + baseObj.getBaseObjId(), (String[]) null);
        }
        return rowsAffected;
    }

    private List<String> getForeignKeyTableToDelete() {
        if (this.foreignKeyTableToDelete == null) {
            this.foreignKeyTableToDelete = new ArrayList();
        }
        return this.foreignKeyTableToDelete;
    }

    private void deleteGenericData(Class<?> modelClass, List<Field> supportedGenericFields, long... ids) {
        for (Field field : supportedGenericFields) {
            String tableName = DBUtility.getGenericTableName(modelClass.getName(), field.getName());
            String genericValueIdColumnName = DBUtility.getGenericValueIdColumnName(modelClass.getName());
            StringBuilder whereClause = new StringBuilder();
            boolean needOr = false;
            for (long id : ids) {
                if (needOr) {
                    whereClause.append(" or ");
                }
                whereClause.append(genericValueIdColumnName).append(" = ").append(id);
                needOr = true;
            }
            this.mDatabase.delete(tableName, whereClause.toString(), (String[]) null);
        }
    }
}

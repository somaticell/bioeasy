package org.litepal.crud;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.litepal.crud.model.AssociationsInfo;
import org.litepal.exceptions.DataSupportException;
import org.litepal.util.BaseUtility;
import org.litepal.util.DBUtility;

class SaveHandler extends DataHandler {
    private boolean ignoreAssociations = false;
    private ContentValues values = new ContentValues();

    SaveHandler(SQLiteDatabase db) {
        this.mDatabase = db;
    }

    /* access modifiers changed from: package-private */
    public void onSave(DataSupport baseObj) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String className = baseObj.getClassName();
        List<Field> supportedFields = getSupportedFields(className);
        List<Field> supportedGenericFields = getSupportedGenericFields(className);
        Collection<AssociationsInfo> associationInfos = getAssociationInfo(className);
        if (!baseObj.isSaved()) {
            if (!this.ignoreAssociations) {
                analyzeAssociatedModels(baseObj, associationInfos);
            }
            doSaveAction(baseObj, supportedFields, supportedGenericFields);
            if (!this.ignoreAssociations) {
                analyzeAssociatedModels(baseObj, associationInfos);
                return;
            }
            return;
        }
        if (!this.ignoreAssociations) {
            analyzeAssociatedModels(baseObj, associationInfos);
        }
        doUpdateAction(baseObj, supportedFields, supportedGenericFields);
    }

    /* access modifiers changed from: package-private */
    public void onSaveFast(DataSupport baseObj) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        this.ignoreAssociations = true;
        onSave(baseObj);
    }

    /* access modifiers changed from: package-private */
    public <T extends DataSupport> void onSaveAll(Collection<T> collection) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (collection != null && collection.size() > 0) {
            DataSupport[] array = (DataSupport[]) collection.toArray(new DataSupport[0]);
            String className = array[0].getClassName();
            List<Field> supportedFields = getSupportedFields(className);
            List<Field> supportedGenericFields = getSupportedGenericFields(className);
            Collection<AssociationsInfo> associationInfos = getAssociationInfo(className);
            for (DataSupport baseObj : array) {
                if (!baseObj.isSaved()) {
                    analyzeAssociatedModels(baseObj, associationInfos);
                    doSaveAction(baseObj, supportedFields, supportedGenericFields);
                    analyzeAssociatedModels(baseObj, associationInfos);
                } else {
                    analyzeAssociatedModels(baseObj, associationInfos);
                    doUpdateAction(baseObj, supportedFields, supportedGenericFields);
                }
                baseObj.clearAssociatedData();
            }
        }
    }

    private void doSaveAction(DataSupport baseObj, List<Field> supportedFields, List<Field> supportedGenericFields) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        this.values.clear();
        beforeSave(baseObj, supportedFields, this.values);
        afterSave(baseObj, supportedFields, supportedGenericFields, saving(baseObj, this.values));
    }

    private void beforeSave(DataSupport baseObj, List<Field> supportedFields, ContentValues values2) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        putFieldsValue(baseObj, supportedFields, values2);
        if (!this.ignoreAssociations) {
            putForeignKeyValue(values2, baseObj);
        }
    }

    private long saving(DataSupport baseObj, ContentValues values2) {
        if (values2.size() == 0) {
            values2.putNull("id");
        }
        return this.mDatabase.insert(baseObj.getTableName(), (String) null, values2);
    }

    private void afterSave(DataSupport baseObj, List<Field> supportedFields, List<Field> supportedGenericFields, long id) throws IllegalAccessException, InvocationTargetException {
        throwIfSaveFailed(id);
        assignIdValue(baseObj, getIdField(supportedFields), id);
        updateGenericTables(baseObj, supportedGenericFields, id);
        if (!this.ignoreAssociations) {
            updateAssociatedTableWithFK(baseObj);
            insertIntermediateJoinTableValue(baseObj, false);
        }
    }

    private void doUpdateAction(DataSupport baseObj, List<Field> supportedFields, List<Field> supportedGenericFields) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        this.values.clear();
        beforeUpdate(baseObj, supportedFields, this.values);
        updating(baseObj, this.values);
        afterUpdate(baseObj, supportedGenericFields);
    }

    private void beforeUpdate(DataSupport baseObj, List<Field> supportedFields, ContentValues values2) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        putFieldsValue(baseObj, supportedFields, values2);
        if (!this.ignoreAssociations) {
            putForeignKeyValue(values2, baseObj);
            for (String fkName : baseObj.getListToClearSelfFK()) {
                values2.putNull(fkName);
            }
        }
    }

    private void updating(DataSupport baseObj, ContentValues values2) {
        this.mDatabase.update(baseObj.getTableName(), values2, "id = ?", new String[]{String.valueOf(baseObj.getBaseObjId())});
    }

    private void afterUpdate(DataSupport baseObj, List<Field> supportedGenericFields) throws InvocationTargetException, IllegalAccessException {
        updateGenericTables(baseObj, supportedGenericFields, baseObj.getBaseObjId());
        if (!this.ignoreAssociations) {
            updateAssociatedTableWithFK(baseObj);
            insertIntermediateJoinTableValue(baseObj, true);
            clearFKValueInAssociatedTable(baseObj);
        }
    }

    private Field getIdField(List<Field> supportedFields) {
        for (Field field : supportedFields) {
            if (isIdColumn(field.getName())) {
                return field;
            }
        }
        return null;
    }

    private void throwIfSaveFailed(long id) {
        if (id == -1) {
            throw new DataSupportException(DataSupportException.SAVE_FAILED);
        }
    }

    private void assignIdValue(DataSupport baseObj, Field idField, long id) {
        try {
            giveBaseObjIdValue(baseObj, id);
            if (idField != null) {
                giveModelIdValue(baseObj, idField.getName(), idField.getType(), id);
            }
        } catch (Exception e) {
            throw new DataSupportException(e.getMessage(), e);
        }
    }

    private void giveModelIdValue(DataSupport baseObj, String idName, Class<?> idType, long id) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Object obj;
        if (shouldGiveModelIdValue(idName, idType, id)) {
            if (idType == Integer.TYPE || idType == Integer.class) {
                obj = Integer.valueOf((int) id);
            } else if (idType == Long.TYPE || idType == Long.class) {
                obj = Long.valueOf(id);
            } else {
                throw new DataSupportException(DataSupportException.ID_TYPE_INVALID_EXCEPTION);
            }
            DynamicExecutor.setField(baseObj, idName, obj, baseObj.getClass());
        }
    }

    private void putForeignKeyValue(ContentValues values2, DataSupport baseObj) {
        Map<String, Long> associatedModelMap = baseObj.getAssociatedModelsMapWithoutFK();
        for (String associatedTableName : associatedModelMap.keySet()) {
            values2.put(getForeignKeyColumnName(associatedTableName), associatedModelMap.get(associatedTableName));
        }
    }

    private void updateAssociatedTableWithFK(DataSupport baseObj) {
        Map<String, Set<Long>> associatedModelMap = baseObj.getAssociatedModelsMapWithFK();
        ContentValues values2 = new ContentValues();
        for (String associatedTableName : associatedModelMap.keySet()) {
            values2.clear();
            values2.put(getForeignKeyColumnName(baseObj.getTableName()), Long.valueOf(baseObj.getBaseObjId()));
            Set<Long> ids = associatedModelMap.get(associatedTableName);
            if (ids != null && !ids.isEmpty()) {
                this.mDatabase.update(associatedTableName, values2, getWhereOfIdsWithOr((Collection<Long>) ids), (String[]) null);
            }
        }
    }

    private void clearFKValueInAssociatedTable(DataSupport baseObj) {
        for (String associatedTableName : baseObj.getListToClearAssociatedFK()) {
            String fkColumnName = getForeignKeyColumnName(baseObj.getTableName());
            ContentValues values2 = new ContentValues();
            values2.putNull(fkColumnName);
            this.mDatabase.update(associatedTableName, values2, String.valueOf(fkColumnName) + " = " + baseObj.getBaseObjId(), (String[]) null);
        }
    }

    private void insertIntermediateJoinTableValue(DataSupport baseObj, boolean isUpdate) {
        Map<String, Set<Long>> associatedIdsM2M = baseObj.getAssociatedModelsMapForJoinTable();
        ContentValues values2 = new ContentValues();
        for (String associatedTableName : associatedIdsM2M.keySet()) {
            String joinTableName = getIntermediateTableName(baseObj, associatedTableName);
            if (isUpdate) {
                this.mDatabase.delete(joinTableName, getWhereForJoinTableToDelete(baseObj), new String[]{String.valueOf(baseObj.getBaseObjId())});
            }
            for (Long longValue : associatedIdsM2M.get(associatedTableName)) {
                long associatedId = longValue.longValue();
                values2.clear();
                values2.put(getForeignKeyColumnName(baseObj.getTableName()), Long.valueOf(baseObj.getBaseObjId()));
                values2.put(getForeignKeyColumnName(associatedTableName), Long.valueOf(associatedId));
                this.mDatabase.insert(joinTableName, (String) null, values2);
            }
        }
    }

    private String getWhereForJoinTableToDelete(DataSupport baseObj) {
        return getForeignKeyColumnName(baseObj.getTableName()) + " = ?";
    }

    private boolean shouldGiveModelIdValue(String idName, Class<?> idType, long id) {
        return (idName == null || idType == null || id <= 0) ? false : true;
    }

    private void updateGenericTables(DataSupport baseObj, List<Field> supportedGenericFields, long id) throws IllegalAccessException, InvocationTargetException {
        for (Field field : supportedGenericFields) {
            field.setAccessible(true);
            Collection<?> collection = (Collection) field.get(baseObj);
            if (collection != null) {
                String tableName = DBUtility.getGenericTableName(baseObj.getClassName(), field.getName());
                String genericValueIdColumnName = DBUtility.getGenericValueIdColumnName(baseObj.getClassName());
                this.mDatabase.delete(tableName, String.valueOf(genericValueIdColumnName) + " = ?", new String[]{String.valueOf(id)});
                for (Object object : collection) {
                    ContentValues values2 = new ContentValues();
                    values2.put(genericValueIdColumnName, Long.valueOf(id));
                    DynamicExecutor.send(values2, "put", new Object[]{BaseUtility.changeCase(DBUtility.convertToValidColumnName(field.getName())), object}, values2.getClass(), new Class[]{String.class, getGenericTypeClass(field)});
                    this.mDatabase.insert(tableName, (String) null, values2);
                }
            }
        }
    }
}

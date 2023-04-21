package org.litepal.crud;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.litepal.exceptions.DataSupportException;
import org.litepal.util.BaseUtility;
import org.litepal.util.DBUtility;

class UpdateHandler extends DataHandler {
    UpdateHandler(SQLiteDatabase db) {
        this.mDatabase = db;
    }

    /* access modifiers changed from: package-private */
    public int onUpdate(DataSupport baseObj, long id) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<Field> supportedFields = getSupportedFields(baseObj.getClassName());
        updateGenericTables(baseObj, getSupportedGenericFields(baseObj.getClassName()), id);
        ContentValues values = new ContentValues();
        putFieldsValue(baseObj, supportedFields, values);
        putFieldsToDefaultValue(baseObj, values, id);
        if (values.size() > 0) {
            return this.mDatabase.update(baseObj.getTableName(), values, "id = " + id, (String[]) null);
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int onUpdate(Class<?> modelClass, long id, ContentValues values) {
        if (values.size() <= 0) {
            return 0;
        }
        convertContentValues(values);
        return this.mDatabase.update(getTableName(modelClass), values, "id = " + id, (String[]) null);
    }

    /* access modifiers changed from: package-private */
    public int onUpdateAll(DataSupport baseObj, String... conditions) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        BaseUtility.checkConditionsCorrect(conditions);
        if (conditions != null && conditions.length > 0) {
            conditions[0] = DBUtility.convertWhereClauseToColumnName(conditions[0]);
        }
        List<Field> supportedFields = getSupportedFields(baseObj.getClassName());
        List<Field> supportedGenericFields = getSupportedGenericFields(baseObj.getClassName());
        long[] ids = null;
        if (!supportedGenericFields.isEmpty()) {
            List<DataSupport> list = DataSupport.select("id").where(conditions).find(baseObj.getClass());
            if (list.size() > 0) {
                ids = new long[list.size()];
                for (int i = 0; i < ids.length; i++) {
                    ids[i] = list.get(i).getBaseObjId();
                }
                updateGenericTables(baseObj, supportedGenericFields, ids);
            }
        }
        ContentValues values = new ContentValues();
        putFieldsValue(baseObj, supportedFields, values);
        putFieldsToDefaultValue(baseObj, values, ids);
        return doUpdateAllAction(baseObj.getTableName(), values, conditions);
    }

    /* access modifiers changed from: package-private */
    public int onUpdateAll(String tableName, ContentValues values, String... conditions) {
        BaseUtility.checkConditionsCorrect(conditions);
        if (conditions != null && conditions.length > 0) {
            conditions[0] = DBUtility.convertWhereClauseToColumnName(conditions[0]);
        }
        convertContentValues(values);
        return doUpdateAllAction(tableName, values, conditions);
    }

    private int doUpdateAllAction(String tableName, ContentValues values, String... conditions) {
        BaseUtility.checkConditionsCorrect(conditions);
        if (values.size() > 0) {
            return this.mDatabase.update(tableName, values, getWhereClause(conditions), getWhereArgs(conditions));
        }
        return 0;
    }

    private void putFieldsToDefaultValue(DataSupport baseObj, ContentValues values, long... ids) {
        String fieldName = null;
        try {
            DataSupport emptyModel = getEmptyModel(baseObj);
            Class<?> emptyModelClass = emptyModel.getClass();
            for (String name : baseObj.getFieldsToSetToDefault()) {
                if (!isIdColumn(name)) {
                    fieldName = name;
                    Field field = emptyModelClass.getDeclaredField(fieldName);
                    if (!isCollection(field.getType())) {
                        putContentValuesForUpdate(emptyModel, field, values);
                    } else if (ids != null && ids.length > 0 && BaseUtility.isGenericTypeSupported(getGenericTypeName(field))) {
                        String tableName = DBUtility.getGenericTableName(baseObj.getClassName(), field.getName());
                        String genericValueIdColumnName = DBUtility.getGenericValueIdColumnName(baseObj.getClassName());
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
        } catch (NoSuchFieldException e) {
            throw new DataSupportException(DataSupportException.noSuchFieldExceptioin(baseObj.getClassName(), fieldName), e);
        } catch (Exception e2) {
            throw new DataSupportException(e2.getMessage(), e2);
        }
    }

    private int doUpdateAssociations(DataSupport baseObj, long id, ContentValues values) {
        analyzeAssociations(baseObj);
        updateSelfTableForeignKey(baseObj, values);
        return 0 + updateAssociatedTableForeignKey(baseObj, id);
    }

    private void analyzeAssociations(DataSupport baseObj) {
        try {
            analyzeAssociatedModels(baseObj, getAssociationInfo(baseObj.getClassName()));
        } catch (Exception e) {
            throw new DataSupportException(e.getMessage(), e);
        }
    }

    private void updateSelfTableForeignKey(DataSupport baseObj, ContentValues values) {
        Map<String, Long> associatedModelMap = baseObj.getAssociatedModelsMapWithoutFK();
        for (String associatedTable : associatedModelMap.keySet()) {
            values.put(getForeignKeyColumnName(associatedTable), associatedModelMap.get(associatedTable));
        }
    }

    private int updateAssociatedTableForeignKey(DataSupport baseObj, long id) {
        Map<String, Set<Long>> associatedModelMap = baseObj.getAssociatedModelsMapWithFK();
        ContentValues values = new ContentValues();
        for (String associatedTable : associatedModelMap.keySet()) {
            values.clear();
            values.put(getForeignKeyColumnName(baseObj.getTableName()), Long.valueOf(id));
            Set<Long> ids = associatedModelMap.get(associatedTable);
            if (ids != null && !ids.isEmpty()) {
                return this.mDatabase.update(associatedTable, values, getWhereOfIdsWithOr((Collection<Long>) ids), (String[]) null);
            }
        }
        return 0;
    }

    private void updateGenericTables(DataSupport baseObj, List<Field> supportedGenericFields, long... ids) throws IllegalAccessException, InvocationTargetException {
        if (ids != null && ids.length > 0) {
            for (Field field : supportedGenericFields) {
                field.setAccessible(true);
                Collection<?> collection = (Collection) field.get(baseObj);
                if (collection != null && !collection.isEmpty()) {
                    String tableName = DBUtility.getGenericTableName(baseObj.getClassName(), field.getName());
                    String genericValueIdColumnName = DBUtility.getGenericValueIdColumnName(baseObj.getClassName());
                    for (long id : ids) {
                        this.mDatabase.delete(tableName, String.valueOf(genericValueIdColumnName) + " = ?", new String[]{String.valueOf(id)});
                        for (Object object : collection) {
                            ContentValues values = new ContentValues();
                            values.put(genericValueIdColumnName, Long.valueOf(id));
                            DynamicExecutor.send(values, "put", new Object[]{DBUtility.convertToValidColumnName(BaseUtility.changeCase(field.getName())), object}, values.getClass(), new Class[]{String.class, getGenericTypeClass(field)});
                            this.mDatabase.insert(tableName, (String) null, values);
                        }
                    }
                }
            }
        }
    }

    private void convertContentValues(ContentValues values) {
        if (Build.VERSION.SDK_INT >= 11) {
            Map<String, Object> valuesToConvert = new HashMap<>();
            for (String key : values.keySet()) {
                if (DBUtility.isFieldNameConflictWithSQLiteKeywords(key)) {
                    valuesToConvert.put(key, values.get(key));
                }
            }
            for (String key2 : valuesToConvert.keySet()) {
                String convertedKey = DBUtility.convertToValidColumnName(key2);
                Object object = values.get(key2);
                values.remove(key2);
                if (object == null) {
                    values.putNull(convertedKey);
                } else {
                    String className = object.getClass().getName();
                    if ("java.lang.Byte".equals(className)) {
                        values.put(convertedKey, (Byte) object);
                    } else if ("[B".equals(className)) {
                        values.put(convertedKey, (byte[]) object);
                    } else if ("java.lang.Boolean".equals(className)) {
                        values.put(convertedKey, (Boolean) object);
                    } else if ("java.lang.String".equals(className)) {
                        values.put(convertedKey, (String) object);
                    } else if ("java.lang.Float".equals(className)) {
                        values.put(convertedKey, (Float) object);
                    } else if ("java.lang.Long".equals(className)) {
                        values.put(convertedKey, (Long) object);
                    } else if ("java.lang.Integer".equals(className)) {
                        values.put(convertedKey, (Integer) object);
                    } else if ("java.lang.Short".equals(className)) {
                        values.put(convertedKey, (Short) object);
                    } else if ("java.lang.Double".equals(className)) {
                        values.put(convertedKey, (Double) object);
                    }
                }
            }
        }
    }
}

package org.litepal.crud;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.litepal.exceptions.DataSupportException;
import org.litepal.tablemanager.Connector;
import org.litepal.util.BaseUtility;
import org.litepal.util.DBUtility;

public class DataSupport {
    private Map<String, Set<Long>> associatedModelsMapForJoinTable;
    private Map<String, Set<Long>> associatedModelsMapWithFK;
    private Map<String, Long> associatedModelsMapWithoutFK;
    private long baseObjId;
    private List<String> fieldsToSetToDefault;
    private List<String> listToClearAssociatedFK;
    private List<String> listToClearSelfFK;

    public static synchronized ClusterQuery select(String... columns) {
        ClusterQuery cQuery;
        synchronized (DataSupport.class) {
            cQuery = new ClusterQuery();
            cQuery.mColumns = columns;
        }
        return cQuery;
    }

    public static synchronized ClusterQuery where(String... conditions) {
        ClusterQuery cQuery;
        synchronized (DataSupport.class) {
            cQuery = new ClusterQuery();
            cQuery.mConditions = conditions;
        }
        return cQuery;
    }

    public static synchronized ClusterQuery order(String column) {
        ClusterQuery cQuery;
        synchronized (DataSupport.class) {
            cQuery = new ClusterQuery();
            cQuery.mOrderBy = column;
        }
        return cQuery;
    }

    public static synchronized ClusterQuery limit(int value) {
        ClusterQuery cQuery;
        synchronized (DataSupport.class) {
            cQuery = new ClusterQuery();
            cQuery.mLimit = String.valueOf(value);
        }
        return cQuery;
    }

    public static synchronized ClusterQuery offset(int value) {
        ClusterQuery cQuery;
        synchronized (DataSupport.class) {
            cQuery = new ClusterQuery();
            cQuery.mOffset = String.valueOf(value);
        }
        return cQuery;
    }

    public static synchronized int count(Class<?> modelClass) {
        int count;
        synchronized (DataSupport.class) {
            count = count(BaseUtility.changeCase(DBUtility.getTableNameByClassName(modelClass.getName())));
        }
        return count;
    }

    public static synchronized int count(String tableName) {
        int count;
        synchronized (DataSupport.class) {
            count = new ClusterQuery().count(tableName);
        }
        return count;
    }

    public static synchronized double average(Class<?> modelClass, String column) {
        double average;
        synchronized (DataSupport.class) {
            average = average(BaseUtility.changeCase(DBUtility.getTableNameByClassName(modelClass.getName())), column);
        }
        return average;
    }

    public static synchronized double average(String tableName, String column) {
        double average;
        synchronized (DataSupport.class) {
            average = new ClusterQuery().average(tableName, column);
        }
        return average;
    }

    public static synchronized <T> T max(Class<?> modelClass, String columnName, Class<T> columnType) {
        T max;
        synchronized (DataSupport.class) {
            max = max(BaseUtility.changeCase(DBUtility.getTableNameByClassName(modelClass.getName())), columnName, columnType);
        }
        return max;
    }

    public static synchronized <T> T max(String tableName, String columnName, Class<T> columnType) {
        T max;
        synchronized (DataSupport.class) {
            max = new ClusterQuery().max(tableName, columnName, columnType);
        }
        return max;
    }

    public static synchronized <T> T min(Class<?> modelClass, String columnName, Class<T> columnType) {
        T min;
        synchronized (DataSupport.class) {
            min = min(BaseUtility.changeCase(DBUtility.getTableNameByClassName(modelClass.getName())), columnName, columnType);
        }
        return min;
    }

    public static synchronized <T> T min(String tableName, String columnName, Class<T> columnType) {
        T min;
        synchronized (DataSupport.class) {
            min = new ClusterQuery().min(tableName, columnName, columnType);
        }
        return min;
    }

    public static synchronized <T> T sum(Class<?> modelClass, String columnName, Class<T> columnType) {
        T sum;
        synchronized (DataSupport.class) {
            sum = sum(BaseUtility.changeCase(DBUtility.getTableNameByClassName(modelClass.getName())), columnName, columnType);
        }
        return sum;
    }

    public static synchronized <T> T sum(String tableName, String columnName, Class<T> columnType) {
        T sum;
        synchronized (DataSupport.class) {
            sum = new ClusterQuery().sum(tableName, columnName, columnType);
        }
        return sum;
    }

    public static synchronized <T> T find(Class<T> modelClass, long id) {
        T find;
        synchronized (DataSupport.class) {
            find = find(modelClass, id, false);
        }
        return find;
    }

    public static synchronized <T> T find(Class<T> modelClass, long id, boolean isEager) {
        T onFind;
        synchronized (DataSupport.class) {
            onFind = new QueryHandler(Connector.getDatabase()).onFind(modelClass, id, isEager);
        }
        return onFind;
    }

    public static synchronized <T> T findFirst(Class<T> modelClass) {
        T findFirst;
        synchronized (DataSupport.class) {
            findFirst = findFirst(modelClass, false);
        }
        return findFirst;
    }

    public static synchronized <T> T findFirst(Class<T> modelClass, boolean isEager) {
        T onFindFirst;
        synchronized (DataSupport.class) {
            onFindFirst = new QueryHandler(Connector.getDatabase()).onFindFirst(modelClass, isEager);
        }
        return onFindFirst;
    }

    public static synchronized <T> T findLast(Class<T> modelClass) {
        T findLast;
        synchronized (DataSupport.class) {
            findLast = findLast(modelClass, false);
        }
        return findLast;
    }

    public static synchronized <T> T findLast(Class<T> modelClass, boolean isEager) {
        T onFindLast;
        synchronized (DataSupport.class) {
            onFindLast = new QueryHandler(Connector.getDatabase()).onFindLast(modelClass, isEager);
        }
        return onFindLast;
    }

    public static synchronized <T> List<T> findAll(Class<T> modelClass, long... ids) {
        List<T> findAll;
        synchronized (DataSupport.class) {
            findAll = findAll(modelClass, false, ids);
        }
        return findAll;
    }

    public static synchronized <T> List<T> findAll(Class<T> modelClass, boolean isEager, long... ids) {
        List<T> onFindAll;
        synchronized (DataSupport.class) {
            onFindAll = new QueryHandler(Connector.getDatabase()).onFindAll(modelClass, isEager, ids);
        }
        return onFindAll;
    }

    public static synchronized Cursor findBySQL(String... sql) {
        String[] selectionArgs;
        Cursor cursor = null;
        synchronized (DataSupport.class) {
            BaseUtility.checkConditionsCorrect(sql);
            if (sql != null) {
                if (sql.length > 0) {
                    if (sql.length == 1) {
                        selectionArgs = null;
                    } else {
                        selectionArgs = new String[(sql.length - 1)];
                        System.arraycopy(sql, 1, selectionArgs, 0, sql.length - 1);
                    }
                    cursor = Connector.getDatabase().rawQuery(sql[0], selectionArgs);
                }
            }
        }
        return cursor;
    }

    public static synchronized int delete(Class<?> modelClass, long id) {
        int rowsAffected;
        synchronized (DataSupport.class) {
            SQLiteDatabase db = Connector.getDatabase();
            db.beginTransaction();
            try {
                rowsAffected = new DeleteHandler(db).onDelete(modelClass, id);
                db.setTransactionSuccessful();
                db.endTransaction();
            } catch (Throwable th) {
                db.endTransaction();
                throw th;
            }
        }
        return rowsAffected;
    }

    public static synchronized int deleteAll(Class<?> modelClass, String... conditions) {
        int onDeleteAll;
        synchronized (DataSupport.class) {
            onDeleteAll = new DeleteHandler(Connector.getDatabase()).onDeleteAll(modelClass, conditions);
        }
        return onDeleteAll;
    }

    public static synchronized int deleteAll(String tableName, String... conditions) {
        int onDeleteAll;
        synchronized (DataSupport.class) {
            onDeleteAll = new DeleteHandler(Connector.getDatabase()).onDeleteAll(tableName, conditions);
        }
        return onDeleteAll;
    }

    public static synchronized int update(Class<?> modelClass, ContentValues values, long id) {
        int onUpdate;
        synchronized (DataSupport.class) {
            onUpdate = new UpdateHandler(Connector.getDatabase()).onUpdate(modelClass, id, values);
        }
        return onUpdate;
    }

    public static synchronized int updateAll(Class<?> modelClass, ContentValues values, String... conditions) {
        int updateAll;
        synchronized (DataSupport.class) {
            updateAll = updateAll(BaseUtility.changeCase(DBUtility.getTableNameByClassName(modelClass.getName())), values, conditions);
        }
        return updateAll;
    }

    public static synchronized int updateAll(String tableName, ContentValues values, String... conditions) {
        int onUpdateAll;
        synchronized (DataSupport.class) {
            onUpdateAll = new UpdateHandler(Connector.getDatabase()).onUpdateAll(tableName, values, conditions);
        }
        return onUpdateAll;
    }

    public static synchronized <T extends DataSupport> void saveAll(Collection<T> collection) {
        synchronized (DataSupport.class) {
            SQLiteDatabase db = Connector.getDatabase();
            db.beginTransaction();
            try {
                new SaveHandler(db).onSaveAll(collection);
                db.setTransactionSuccessful();
                db.endTransaction();
            } catch (Exception e) {
                throw new DataSupportException(e.getMessage(), e);
            } catch (Throwable th) {
                db.endTransaction();
                throw th;
            }
        }
    }

    public static <T extends DataSupport> void markAsDeleted(Collection<T> collection) {
        for (T t : collection) {
            t.clearSavedState();
        }
    }

    public static <T> boolean isExist(Class<T> modelClass, String... conditions) {
        if (conditions != null && where(conditions).count((Class<?>) modelClass) > 0) {
            return true;
        }
        return false;
    }

    public synchronized int delete() {
        int rowsAffected;
        SQLiteDatabase db = Connector.getDatabase();
        db.beginTransaction();
        try {
            rowsAffected = new DeleteHandler(db).onDelete(this);
            this.baseObjId = 0;
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Throwable th) {
            db.endTransaction();
            throw th;
        }
        return rowsAffected;
    }

    public synchronized int update(long id) {
        int rowsAffected;
        try {
            rowsAffected = new UpdateHandler(Connector.getDatabase()).onUpdate(this, id);
            getFieldsToSetToDefault().clear();
        } catch (Exception e) {
            throw new DataSupportException(e.getMessage(), e);
        }
        return rowsAffected;
    }

    public synchronized int updateAll(String... conditions) {
        int rowsAffected;
        try {
            rowsAffected = new UpdateHandler(Connector.getDatabase()).onUpdateAll(this, conditions);
            getFieldsToSetToDefault().clear();
        } catch (Exception e) {
            throw new DataSupportException(e.getMessage(), e);
        }
        return rowsAffected;
    }

    public synchronized boolean save() {
        boolean z;
        try {
            saveThrows();
            z = true;
        } catch (Exception e) {
            e.printStackTrace();
            z = false;
        }
        return z;
    }

    public synchronized void saveThrows() {
        SQLiteDatabase db = Connector.getDatabase();
        db.beginTransaction();
        try {
            new SaveHandler(db).onSave(this);
            clearAssociatedData();
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            throw new DataSupportException(e.getMessage(), e);
        } catch (Throwable th) {
            db.endTransaction();
            throw th;
        }
    }

    public synchronized boolean saveIfNotExist(String... conditions) {
        boolean z;
        if (!isExist(getClass(), conditions)) {
            z = save();
        } else {
            z = false;
        }
        return z;
    }

    public synchronized boolean saveFast() {
        boolean z;
        SQLiteDatabase db = Connector.getDatabase();
        db.beginTransaction();
        try {
            new SaveHandler(db).onSaveFast(this);
            db.setTransactionSuccessful();
            db.endTransaction();
            z = true;
        } catch (Exception e) {
            e.printStackTrace();
            db.endTransaction();
            z = false;
        } catch (Throwable th) {
            db.endTransaction();
            throw th;
        }
        return z;
    }

    public boolean isSaved() {
        return this.baseObjId > 0;
    }

    public void clearSavedState() {
        this.baseObjId = 0;
    }

    public void setToDefault(String fieldName) {
        getFieldsToSetToDefault().add(fieldName);
    }

    public void assignBaseObjId(int baseObjId2) {
        this.baseObjId = (long) baseObjId2;
    }

    protected DataSupport() {
    }

    /* access modifiers changed from: protected */
    public long getBaseObjId() {
        return this.baseObjId;
    }

    /* access modifiers changed from: protected */
    public String getClassName() {
        return getClass().getName();
    }

    /* access modifiers changed from: protected */
    public String getTableName() {
        return BaseUtility.changeCase(DBUtility.getTableNameByClassName(getClassName()));
    }

    /* access modifiers changed from: package-private */
    public List<String> getFieldsToSetToDefault() {
        if (this.fieldsToSetToDefault == null) {
            this.fieldsToSetToDefault = new ArrayList();
        }
        return this.fieldsToSetToDefault;
    }

    /* access modifiers changed from: package-private */
    public void addAssociatedModelWithFK(String associatedTableName, long associatedId) {
        Set<Long> associatedIdsWithFKSet = getAssociatedModelsMapWithFK().get(associatedTableName);
        if (associatedIdsWithFKSet == null) {
            Set<Long> associatedIdsWithFKSet2 = new HashSet<>();
            associatedIdsWithFKSet2.add(Long.valueOf(associatedId));
            this.associatedModelsMapWithFK.put(associatedTableName, associatedIdsWithFKSet2);
            return;
        }
        associatedIdsWithFKSet.add(Long.valueOf(associatedId));
    }

    /* access modifiers changed from: package-private */
    public Map<String, Set<Long>> getAssociatedModelsMapWithFK() {
        if (this.associatedModelsMapWithFK == null) {
            this.associatedModelsMapWithFK = new HashMap();
        }
        return this.associatedModelsMapWithFK;
    }

    /* access modifiers changed from: package-private */
    public void addAssociatedModelForJoinTable(String associatedModelName, long associatedId) {
        Set<Long> associatedIdsM2MSet = getAssociatedModelsMapForJoinTable().get(associatedModelName);
        if (associatedIdsM2MSet == null) {
            Set<Long> associatedIdsM2MSet2 = new HashSet<>();
            associatedIdsM2MSet2.add(Long.valueOf(associatedId));
            this.associatedModelsMapForJoinTable.put(associatedModelName, associatedIdsM2MSet2);
            return;
        }
        associatedIdsM2MSet.add(Long.valueOf(associatedId));
    }

    /* access modifiers changed from: package-private */
    public void addEmptyModelForJoinTable(String associatedModelName) {
        if (getAssociatedModelsMapForJoinTable().get(associatedModelName) == null) {
            this.associatedModelsMapForJoinTable.put(associatedModelName, new HashSet<>());
        }
    }

    /* access modifiers changed from: package-private */
    public Map<String, Set<Long>> getAssociatedModelsMapForJoinTable() {
        if (this.associatedModelsMapForJoinTable == null) {
            this.associatedModelsMapForJoinTable = new HashMap();
        }
        return this.associatedModelsMapForJoinTable;
    }

    /* access modifiers changed from: package-private */
    public void addAssociatedModelWithoutFK(String associatedTableName, long associatedId) {
        getAssociatedModelsMapWithoutFK().put(associatedTableName, Long.valueOf(associatedId));
    }

    /* access modifiers changed from: package-private */
    public Map<String, Long> getAssociatedModelsMapWithoutFK() {
        if (this.associatedModelsMapWithoutFK == null) {
            this.associatedModelsMapWithoutFK = new HashMap();
        }
        return this.associatedModelsMapWithoutFK;
    }

    /* access modifiers changed from: package-private */
    public void addFKNameToClearSelf(String foreignKeyName) {
        List<String> list = getListToClearSelfFK();
        if (!list.contains(foreignKeyName)) {
            list.add(foreignKeyName);
        }
    }

    /* access modifiers changed from: package-private */
    public List<String> getListToClearSelfFK() {
        if (this.listToClearSelfFK == null) {
            this.listToClearSelfFK = new ArrayList();
        }
        return this.listToClearSelfFK;
    }

    /* access modifiers changed from: package-private */
    public void addAssociatedTableNameToClearFK(String associatedTableName) {
        List<String> list = getListToClearAssociatedFK();
        if (!list.contains(associatedTableName)) {
            list.add(associatedTableName);
        }
    }

    /* access modifiers changed from: package-private */
    public List<String> getListToClearAssociatedFK() {
        if (this.listToClearAssociatedFK == null) {
            this.listToClearAssociatedFK = new ArrayList();
        }
        return this.listToClearAssociatedFK;
    }

    /* access modifiers changed from: package-private */
    public void clearAssociatedData() {
        clearIdOfModelWithFK();
        clearIdOfModelWithoutFK();
        clearIdOfModelForJoinTable();
        clearFKNameList();
    }

    private void clearIdOfModelWithFK() {
        for (String associatedModelName : getAssociatedModelsMapWithFK().keySet()) {
            this.associatedModelsMapWithFK.get(associatedModelName).clear();
        }
        this.associatedModelsMapWithFK.clear();
    }

    private void clearIdOfModelWithoutFK() {
        getAssociatedModelsMapWithoutFK().clear();
    }

    private void clearIdOfModelForJoinTable() {
        for (String associatedModelName : getAssociatedModelsMapForJoinTable().keySet()) {
            this.associatedModelsMapForJoinTable.get(associatedModelName).clear();
        }
        this.associatedModelsMapForJoinTable.clear();
    }

    private void clearFKNameList() {
        getListToClearSelfFK().clear();
        getListToClearAssociatedFK().clear();
    }
}

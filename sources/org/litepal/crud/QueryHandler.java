package org.litepal.crud;

import android.database.sqlite.SQLiteDatabase;
import com.alipay.sdk.cons.a;
import java.util.List;
import org.litepal.util.BaseUtility;
import org.litepal.util.DBUtility;

class QueryHandler extends DataHandler {
    QueryHandler(SQLiteDatabase db) {
        this.mDatabase = db;
    }

    /* access modifiers changed from: package-private */
    public <T> T onFind(Class<T> modelClass, long id, boolean isEager) {
        List<T> dataList = query(modelClass, (String[]) null, "id = ?", new String[]{String.valueOf(id)}, (String) null, (String) null, (String) null, (String) null, getForeignKeyAssociations(modelClass.getName(), isEager));
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public <T> T onFindFirst(Class<T> modelClass, boolean isEager) {
        List<T> dataList = query(modelClass, (String[]) null, (String) null, (String[]) null, (String) null, (String) null, "id", a.e, getForeignKeyAssociations(modelClass.getName(), isEager));
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public <T> T onFindLast(Class<T> modelClass, boolean isEager) {
        List<T> dataList = query(modelClass, (String[]) null, (String) null, (String[]) null, (String) null, (String) null, "id desc", a.e, getForeignKeyAssociations(modelClass.getName(), isEager));
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public <T> List<T> onFindAll(Class<T> modelClass, boolean isEager, long... ids) {
        if (isAffectAllLines(ids)) {
            return query(modelClass, (String[]) null, (String) null, (String[]) null, (String) null, (String) null, "id", (String) null, getForeignKeyAssociations(modelClass.getName(), isEager));
        }
        return query(modelClass, (String[]) null, getWhereOfIdsWithOr(ids), (String[]) null, (String) null, (String) null, "id", (String) null, getForeignKeyAssociations(modelClass.getName(), isEager));
    }

    /* access modifiers changed from: package-private */
    public <T> List<T> onFind(Class<T> modelClass, String[] columns, String[] conditions, String orderBy, String limit, boolean isEager) {
        BaseUtility.checkConditionsCorrect(conditions);
        if (conditions != null && conditions.length > 0) {
            conditions[0] = DBUtility.convertWhereClauseToColumnName(conditions[0]);
        }
        String orderBy2 = DBUtility.convertOrderByClauseToValidName(orderBy);
        return query(modelClass, columns, getWhereClause(conditions), getWhereArgs(conditions), (String) null, (String) null, orderBy2, limit, getForeignKeyAssociations(modelClass.getName(), isEager));
    }

    /* access modifiers changed from: package-private */
    public int onCount(String tableName, String[] conditions) {
        BaseUtility.checkConditionsCorrect(conditions);
        if (conditions != null && conditions.length > 0) {
            conditions[0] = DBUtility.convertWhereClauseToColumnName(conditions[0]);
        }
        return ((Integer) mathQuery(tableName, new String[]{"count(1)"}, conditions, Integer.TYPE)).intValue();
    }

    /* access modifiers changed from: package-private */
    public double onAverage(String tableName, String column, String[] conditions) {
        BaseUtility.checkConditionsCorrect(conditions);
        if (conditions != null && conditions.length > 0) {
            conditions[0] = DBUtility.convertWhereClauseToColumnName(conditions[0]);
        }
        return ((Double) mathQuery(tableName, new String[]{"avg(" + column + ")"}, conditions, Double.TYPE)).doubleValue();
    }

    /* access modifiers changed from: package-private */
    public <T> T onMax(String tableName, String column, String[] conditions, Class<T> type) {
        BaseUtility.checkConditionsCorrect(conditions);
        if (conditions != null && conditions.length > 0) {
            conditions[0] = DBUtility.convertWhereClauseToColumnName(conditions[0]);
        }
        return mathQuery(tableName, new String[]{"max(" + column + ")"}, conditions, type);
    }

    /* access modifiers changed from: package-private */
    public <T> T onMin(String tableName, String column, String[] conditions, Class<T> type) {
        BaseUtility.checkConditionsCorrect(conditions);
        if (conditions != null && conditions.length > 0) {
            conditions[0] = DBUtility.convertWhereClauseToColumnName(conditions[0]);
        }
        return mathQuery(tableName, new String[]{"min(" + column + ")"}, conditions, type);
    }

    /* access modifiers changed from: package-private */
    public <T> T onSum(String tableName, String column, String[] conditions, Class<T> type) {
        BaseUtility.checkConditionsCorrect(conditions);
        if (conditions != null && conditions.length > 0) {
            conditions[0] = DBUtility.convertWhereClauseToColumnName(conditions[0]);
        }
        return mathQuery(tableName, new String[]{"sum(" + column + ")"}, conditions, type);
    }
}

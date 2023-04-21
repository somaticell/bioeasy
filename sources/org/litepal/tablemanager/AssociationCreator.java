package org.litepal.tablemanager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.litepal.exceptions.DatabaseGenerateException;
import org.litepal.tablemanager.model.AssociationsModel;
import org.litepal.tablemanager.model.ColumnModel;
import org.litepal.tablemanager.model.GenericModel;
import org.litepal.util.BaseUtility;
import org.litepal.util.Const;
import org.litepal.util.DBUtility;
import org.litepal.util.LogUtil;

public abstract class AssociationCreator extends Generator {
    /* access modifiers changed from: protected */
    public abstract void createOrUpgradeTable(SQLiteDatabase sQLiteDatabase, boolean z);

    /* access modifiers changed from: protected */
    public void addOrUpdateAssociation(SQLiteDatabase db, boolean force) {
        addAssociations(getAllAssociations(), db, force);
    }

    /* access modifiers changed from: protected */
    public String generateCreateTableSQL(String tableName, List<ColumnModel> columnModels, boolean autoIncrementId) {
        StringBuilder createTableSQL = new StringBuilder("create table ");
        createTableSQL.append(tableName).append(" (");
        if (autoIncrementId) {
            createTableSQL.append("id integer primary key autoincrement,");
        }
        if (columnModels.size() == 0) {
            createTableSQL.deleteCharAt(createTableSQL.length() - 1);
        }
        boolean needSeparator = false;
        for (ColumnModel columnModel : columnModels) {
            if (!columnModel.isIdColumn()) {
                if (needSeparator) {
                    createTableSQL.append(", ");
                }
                needSeparator = true;
                createTableSQL.append(columnModel.getColumnName()).append(" ").append(columnModel.getColumnType());
                if (!columnModel.isNullable()) {
                    createTableSQL.append(" not null");
                }
                if (columnModel.isUnique()) {
                    createTableSQL.append(" unique");
                }
                String defaultValue = columnModel.getDefaultValue();
                if (!TextUtils.isEmpty(defaultValue)) {
                    createTableSQL.append(" default ").append(defaultValue);
                }
            }
        }
        createTableSQL.append(")");
        LogUtil.d(Generator.TAG, "create table sql is >> " + createTableSQL);
        return createTableSQL.toString();
    }

    /* access modifiers changed from: protected */
    public String generateDropTableSQL(String tableName) {
        return "drop table if exists " + tableName;
    }

    /* access modifiers changed from: protected */
    public String generateAddColumnSQL(String tableName, ColumnModel columnModel) {
        StringBuilder addColumnSQL = new StringBuilder();
        addColumnSQL.append("alter table ").append(tableName);
        addColumnSQL.append(" add column ").append(columnModel.getColumnName());
        addColumnSQL.append(" ").append(columnModel.getColumnType());
        if (!columnModel.isNullable()) {
            addColumnSQL.append(" not null");
        }
        if (columnModel.isUnique()) {
            addColumnSQL.append(" unique");
        }
        String defaultValue = columnModel.getDefaultValue();
        if (!TextUtils.isEmpty(defaultValue)) {
            addColumnSQL.append(" default ").append(defaultValue);
        } else if (!columnModel.isNullable()) {
            if ("integer".equalsIgnoreCase(columnModel.getColumnType())) {
                defaultValue = "0";
            } else if ("text".equalsIgnoreCase(columnModel.getColumnType())) {
                defaultValue = "''";
            } else if ("real".equalsIgnoreCase(columnModel.getColumnType())) {
                defaultValue = "0.0";
            }
            addColumnSQL.append(" default ").append(defaultValue);
        }
        LogUtil.d(Generator.TAG, "add column sql is >> " + addColumnSQL);
        return addColumnSQL.toString();
    }

    /* access modifiers changed from: protected */
    public boolean isForeignKeyColumnFormat(String columnName) {
        if (TextUtils.isEmpty(columnName) || !columnName.toLowerCase().endsWith("_id") || columnName.equalsIgnoreCase("_id")) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void giveTableSchemaACopy(String tableName, int tableType, SQLiteDatabase db) {
        StringBuilder sql = new StringBuilder("select * from ");
        sql.append(Const.TableSchema.TABLE_NAME);
        LogUtil.d(Generator.TAG, "giveTableSchemaACopy SQL is >> " + sql);
        Cursor cursor = null;
        try {
            Cursor cursor2 = db.rawQuery(sql.toString(), (String[]) null);
            if (isNeedtoGiveACopy(cursor2, tableName)) {
                ContentValues values = new ContentValues();
                values.put("name", BaseUtility.changeCase(tableName));
                values.put("type", Integer.valueOf(tableType));
                db.insert(Const.TableSchema.TABLE_NAME, (String) null, values);
            }
            if (cursor2 != null) {
                cursor2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    private boolean isNeedtoGiveACopy(Cursor cursor, String tableName) {
        return !isValueExists(cursor, tableName) && !isSpecialTable(tableName);
    }

    private boolean isValueExists(Cursor cursor, String tableName) {
        if (!cursor.moveToFirst()) {
            return false;
        }
        while (!cursor.getString(cursor.getColumnIndexOrThrow("name")).equalsIgnoreCase(tableName)) {
            if (!cursor.moveToNext()) {
                return false;
            }
        }
        return true;
    }

    private boolean isSpecialTable(String tableName) {
        return Const.TableSchema.TABLE_NAME.equalsIgnoreCase(tableName);
    }

    private void addAssociations(Collection<AssociationsModel> associatedModels, SQLiteDatabase db, boolean force) {
        for (AssociationsModel associationModel : associatedModels) {
            if (2 == associationModel.getAssociationType() || 1 == associationModel.getAssociationType()) {
                addForeignKeyColumn(associationModel.getTableName(), associationModel.getAssociatedTableName(), associationModel.getTableHoldsForeignKey(), db);
            } else if (3 == associationModel.getAssociationType()) {
                createIntermediateTable(associationModel.getTableName(), associationModel.getAssociatedTableName(), db, force);
            }
        }
        for (GenericModel genericModel : getGenericModels()) {
            createGenericTable(genericModel, db, force);
        }
    }

    private void createIntermediateTable(String tableName, String associatedTableName, SQLiteDatabase db, boolean force) {
        List<ColumnModel> columnModelList = new ArrayList<>();
        ColumnModel column1 = new ColumnModel();
        column1.setColumnName(String.valueOf(tableName) + "_id");
        column1.setColumnType("integer");
        ColumnModel column2 = new ColumnModel();
        column2.setColumnName(String.valueOf(associatedTableName) + "_id");
        column2.setColumnType("integer");
        columnModelList.add(column1);
        columnModelList.add(column2);
        String intermediateTableName = DBUtility.getIntermediateTableName(tableName, associatedTableName);
        List<String> sqls = new ArrayList<>();
        if (!DBUtility.isTableExists(intermediateTableName, db)) {
            sqls.add(generateCreateTableSQL(intermediateTableName, columnModelList, false));
        } else if (force) {
            sqls.add(generateDropTableSQL(intermediateTableName));
            sqls.add(generateCreateTableSQL(intermediateTableName, columnModelList, false));
        }
        execute((String[]) sqls.toArray(new String[0]), db);
        giveTableSchemaACopy(intermediateTableName, 1, db);
    }

    private void createGenericTable(GenericModel genericModel, SQLiteDatabase db, boolean force) {
        String tableName = genericModel.getTableName();
        String valueColumnName = genericModel.getValueColumnName();
        String valueColumnType = genericModel.getValueColumnType();
        String valueIdColumnName = genericModel.getValueIdColumnName();
        List<ColumnModel> columnModelList = new ArrayList<>();
        ColumnModel column1 = new ColumnModel();
        column1.setColumnName(valueColumnName);
        column1.setColumnType(valueColumnType);
        ColumnModel column2 = new ColumnModel();
        column2.setColumnName(valueIdColumnName);
        column2.setColumnType("integer");
        columnModelList.add(column1);
        columnModelList.add(column2);
        List<String> sqls = new ArrayList<>();
        if (!DBUtility.isTableExists(tableName, db)) {
            sqls.add(generateCreateTableSQL(tableName, columnModelList, false));
        } else if (force) {
            sqls.add(generateDropTableSQL(tableName));
            sqls.add(generateCreateTableSQL(tableName, columnModelList, false));
        }
        execute((String[]) sqls.toArray(new String[0]), db);
        giveTableSchemaACopy(tableName, 2, db);
    }

    /* access modifiers changed from: protected */
    public void addForeignKeyColumn(String tableName, String associatedTableName, String tableHoldsForeignKey, SQLiteDatabase db) {
        if (!DBUtility.isTableExists(tableName, db)) {
            throw new DatabaseGenerateException(DatabaseGenerateException.TABLE_DOES_NOT_EXIST + tableName);
        } else if (DBUtility.isTableExists(associatedTableName, db)) {
            String foreignKeyColumn = null;
            if (tableName.equals(tableHoldsForeignKey)) {
                foreignKeyColumn = getForeignKeyColumnName(associatedTableName);
            } else if (associatedTableName.equals(tableHoldsForeignKey)) {
                foreignKeyColumn = getForeignKeyColumnName(tableName);
            }
            if (!DBUtility.isColumnExists(foreignKeyColumn, tableHoldsForeignKey, db)) {
                ColumnModel columnModel = new ColumnModel();
                columnModel.setColumnName(foreignKeyColumn);
                columnModel.setColumnType("integer");
                execute(new String[]{generateAddColumnSQL(tableHoldsForeignKey, columnModel)}, db);
                return;
            }
            LogUtil.d(Generator.TAG, "column " + foreignKeyColumn + " is already exist, no need to add one");
        } else {
            throw new DatabaseGenerateException(DatabaseGenerateException.TABLE_DOES_NOT_EXIST + associatedTableName);
        }
    }
}

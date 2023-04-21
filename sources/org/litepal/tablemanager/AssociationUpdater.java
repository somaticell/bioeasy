package org.litepal.tablemanager;

import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.litepal.parser.LitePalAttr;
import org.litepal.tablemanager.model.AssociationsModel;
import org.litepal.tablemanager.model.ColumnModel;
import org.litepal.tablemanager.model.GenericModel;
import org.litepal.tablemanager.model.TableModel;
import org.litepal.util.BaseUtility;
import org.litepal.util.Const;
import org.litepal.util.DBUtility;
import org.litepal.util.LogUtil;

public abstract class AssociationUpdater extends Creator {
    public static final String TAG = "AssociationUpdater";
    private Collection<AssociationsModel> mAssociationModels;
    protected SQLiteDatabase mDb;

    /* access modifiers changed from: protected */
    public abstract void createOrUpgradeTable(SQLiteDatabase sQLiteDatabase, boolean z);

    /* access modifiers changed from: protected */
    public void addOrUpdateAssociation(SQLiteDatabase db, boolean force) {
        this.mAssociationModels = getAllAssociations();
        this.mDb = db;
        removeAssociations();
    }

    /* access modifiers changed from: protected */
    public List<String> getForeignKeyColumns(TableModel tableModel) {
        List<String> foreignKeyColumns = new ArrayList<>();
        for (ColumnModel columnModel : getTableModelFromDB(tableModel.getTableName()).getColumnModels()) {
            String columnName = columnModel.getColumnName();
            if (isForeignKeyColumnFormat(columnModel.getColumnName()) && !tableModel.containsColumn(columnName)) {
                LogUtil.d(TAG, "getForeignKeyColumnNames >> foreign key column is " + columnName);
                foreignKeyColumns.add(columnName);
            }
        }
        return foreignKeyColumns;
    }

    /* access modifiers changed from: protected */
    public boolean isForeignKeyColumn(TableModel tableModel, String columnName) {
        return BaseUtility.containsIgnoreCases(getForeignKeyColumns(tableModel), columnName);
    }

    /* access modifiers changed from: protected */
    public TableModel getTableModelFromDB(String tableName) {
        return DBUtility.findPragmaTableInfo(tableName, this.mDb);
    }

    /* access modifiers changed from: protected */
    public void dropTables(List<String> dropTableNames, SQLiteDatabase db) {
        if (dropTableNames != null && !dropTableNames.isEmpty()) {
            String[] dropTableSQLS = new String[dropTableNames.size()];
            for (int i = 0; i < dropTableSQLS.length; i++) {
                dropTableSQLS[i] = generateDropTableSQL(dropTableNames.get(i));
            }
            execute(dropTableSQLS, db);
        }
    }

    /* access modifiers changed from: protected */
    public void removeColumns(Collection<String> removeColumnNames, String tableName) {
        if (removeColumnNames != null && !removeColumnNames.isEmpty()) {
            execute(getRemoveColumnSQLs(removeColumnNames, tableName), this.mDb);
        }
    }

    /* access modifiers changed from: protected */
    public void clearCopyInTableSchema(List<String> tableNames) {
        if (tableNames != null && !tableNames.isEmpty()) {
            StringBuilder deleteData = new StringBuilder("delete from ");
            deleteData.append(Const.TableSchema.TABLE_NAME).append(" where");
            boolean needOr = false;
            for (String tableName : tableNames) {
                if (needOr) {
                    deleteData.append(" or ");
                }
                needOr = true;
                deleteData.append(" lower(").append("name").append(") ");
                deleteData.append("=").append(" lower('").append(tableName).append("')");
            }
            LogUtil.d(TAG, "clear table schema value sql is " + deleteData);
            execute(new String[]{deleteData.toString()}, this.mDb);
        }
    }

    private void removeAssociations() {
        removeForeignKeyColumns();
        removeIntermediateTables();
        removeGenericTables();
    }

    private void removeForeignKeyColumns() {
        for (String className : LitePalAttr.getInstance().getClassNames()) {
            TableModel tableModel = getTableModel(className);
            removeColumns(findForeignKeyToRemove(tableModel), tableModel.getTableName());
        }
    }

    private void removeIntermediateTables() {
        List<String> tableNamesToDrop = findIntermediateTablesToDrop();
        dropTables(tableNamesToDrop, this.mDb);
        clearCopyInTableSchema(tableNamesToDrop);
    }

    private void removeGenericTables() {
        List<String> tableNamesToDrop = findGenericTablesToDrop();
        dropTables(tableNamesToDrop, this.mDb);
        clearCopyInTableSchema(tableNamesToDrop);
    }

    private List<String> findForeignKeyToRemove(TableModel tableModel) {
        List<String> removeRelations = new ArrayList<>();
        List<String> foreignKeyColumns = getForeignKeyColumns(tableModel);
        String selfTableName = tableModel.getTableName();
        for (String foreignKeyColumn : foreignKeyColumns) {
            if (shouldDropForeignKey(selfTableName, DBUtility.getTableNameByForeignColumn(foreignKeyColumn))) {
                removeRelations.add(foreignKeyColumn);
            }
        }
        LogUtil.d(TAG, "findForeignKeyToRemove >> " + tableModel.getTableName() + " " + removeRelations);
        return removeRelations;
    }

    private List<String> findIntermediateTablesToDrop() {
        List<String> intermediateTables = new ArrayList<>();
        for (String tableName : DBUtility.findAllTableNames(this.mDb)) {
            if (DBUtility.isIntermediateTable(tableName, this.mDb)) {
                boolean dropIntermediateTable = true;
                for (AssociationsModel associationModel : this.mAssociationModels) {
                    if (associationModel.getAssociationType() == 3 && tableName.equalsIgnoreCase(DBUtility.getIntermediateTableName(associationModel.getTableName(), associationModel.getAssociatedTableName()))) {
                        dropIntermediateTable = false;
                    }
                }
                if (dropIntermediateTable) {
                    intermediateTables.add(tableName);
                }
            }
        }
        LogUtil.d(TAG, "findIntermediateTablesToDrop >> " + intermediateTables);
        return intermediateTables;
    }

    private List<String> findGenericTablesToDrop() {
        List<String> genericTablesToDrop = new ArrayList<>();
        for (String tableName : DBUtility.findAllTableNames(this.mDb)) {
            if (DBUtility.isGenericTable(tableName, this.mDb)) {
                boolean dropGenericTable = true;
                for (GenericModel genericModel : getGenericModels()) {
                    if (tableName.equalsIgnoreCase(genericModel.getTableName())) {
                        dropGenericTable = false;
                    }
                }
                if (dropGenericTable) {
                    genericTablesToDrop.add(tableName);
                }
            }
        }
        return genericTablesToDrop;
    }

    /* access modifiers changed from: protected */
    public String generateAlterToTempTableSQL(String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("alter table ").append(tableName).append(" rename to ").append(getTempTableName(tableName));
        return sql.toString();
    }

    private String generateCreateNewTableSQL(Collection<String> removeColumnNames, TableModel tableModel) {
        for (String removeColumnName : removeColumnNames) {
            tableModel.removeColumnModelByName(removeColumnName);
        }
        return generateCreateTableSQL(tableModel);
    }

    /* access modifiers changed from: protected */
    public String generateDataMigrationSQL(TableModel tableModel) {
        String tableName = tableModel.getTableName();
        List<ColumnModel> columnModels = tableModel.getColumnModels();
        if (columnModels.isEmpty()) {
            return null;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append(tableName).append("(");
        boolean needComma = false;
        for (ColumnModel columnModel : columnModels) {
            if (needComma) {
                sql.append(", ");
            }
            needComma = true;
            sql.append(columnModel.getColumnName());
        }
        sql.append(") ");
        sql.append("select ");
        boolean needComma2 = false;
        for (ColumnModel columnModel2 : columnModels) {
            if (needComma2) {
                sql.append(", ");
            }
            needComma2 = true;
            sql.append(columnModel2.getColumnName());
        }
        sql.append(" from ").append(getTempTableName(tableName));
        return sql.toString();
    }

    /* access modifiers changed from: protected */
    public String generateDropTempTableSQL(String tableName) {
        return generateDropTableSQL(getTempTableName(tableName));
    }

    /* access modifiers changed from: protected */
    public String getTempTableName(String tableName) {
        return String.valueOf(tableName) + "_temp";
    }

    private String[] getRemoveColumnSQLs(Collection<String> removeColumnNames, String tableName) {
        TableModel tableModelFromDB = getTableModelFromDB(tableName);
        String alterToTempTableSQL = generateAlterToTempTableSQL(tableName);
        LogUtil.d(TAG, "generateRemoveColumnSQL >> " + alterToTempTableSQL);
        String createNewTableSQL = generateCreateNewTableSQL(removeColumnNames, tableModelFromDB);
        LogUtil.d(TAG, "generateRemoveColumnSQL >> " + createNewTableSQL);
        String dataMigrationSQL = generateDataMigrationSQL(tableModelFromDB);
        LogUtil.d(TAG, "generateRemoveColumnSQL >> " + dataMigrationSQL);
        String dropTempTableSQL = generateDropTempTableSQL(tableName);
        LogUtil.d(TAG, "generateRemoveColumnSQL >> " + dropTempTableSQL);
        return new String[]{alterToTempTableSQL, createNewTableSQL, dataMigrationSQL, dropTempTableSQL};
    }

    private boolean shouldDropForeignKey(String selfTableName, String associatedTableName) {
        for (AssociationsModel associationModel : this.mAssociationModels) {
            if (associationModel.getAssociationType() == 1) {
                if (!selfTableName.equalsIgnoreCase(associationModel.getTableHoldsForeignKey())) {
                    continue;
                } else if (associationModel.getTableName().equalsIgnoreCase(selfTableName)) {
                    if (isRelationCorrect(associationModel, selfTableName, associatedTableName)) {
                        return false;
                    }
                } else if (associationModel.getAssociatedTableName().equalsIgnoreCase(selfTableName) && isRelationCorrect(associationModel, associatedTableName, selfTableName)) {
                    return false;
                }
            } else if (associationModel.getAssociationType() == 2 && isRelationCorrect(associationModel, associatedTableName, selfTableName)) {
                return false;
            }
        }
        return true;
    }

    private boolean isRelationCorrect(AssociationsModel associationModel, String tableName1, String tableName2) {
        return associationModel.getTableName().equalsIgnoreCase(tableName1) && associationModel.getAssociatedTableName().equalsIgnoreCase(tableName2);
    }
}

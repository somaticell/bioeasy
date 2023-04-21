package org.litepal.tablemanager;

import android.database.sqlite.SQLiteDatabase;
import org.litepal.tablemanager.model.TableModel;
import org.litepal.util.DBUtility;

class Creator extends AssociationCreator {
    public static final String TAG = "Creator";

    Creator() {
    }

    /* access modifiers changed from: protected */
    public void createOrUpgradeTable(SQLiteDatabase db, boolean force) {
        for (TableModel tableModel : getAllTableModels()) {
            createOrUpgradeTable(tableModel, db, force);
        }
    }

    /* access modifiers changed from: protected */
    public void createOrUpgradeTable(TableModel tableModel, SQLiteDatabase db, boolean force) {
        execute(getCreateTableSQLs(tableModel, db, force), db);
        giveTableSchemaACopy(tableModel.getTableName(), 0, db);
    }

    /* access modifiers changed from: protected */
    public String[] getCreateTableSQLs(TableModel tableModel, SQLiteDatabase db, boolean force) {
        if (force) {
            return new String[]{generateDropTableSQL(tableModel), generateCreateTableSQL(tableModel)};
        } else if (DBUtility.isTableExists(tableModel.getTableName(), db)) {
            return null;
        } else {
            return new String[]{generateCreateTableSQL(tableModel)};
        }
    }

    /* access modifiers changed from: protected */
    public String generateDropTableSQL(TableModel tableModel) {
        return generateDropTableSQL(tableModel.getTableName());
    }

    /* access modifiers changed from: protected */
    public String generateCreateTableSQL(TableModel tableModel) {
        return generateCreateTableSQL(tableModel.getTableName(), tableModel.getColumnModels(), true);
    }
}

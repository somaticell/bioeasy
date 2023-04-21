package org.litepal.tablemanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.litepal.tablemanager.model.TableModel;
import org.litepal.util.BaseUtility;
import org.litepal.util.Const;
import org.litepal.util.LogUtil;

public class Dropper extends AssociationUpdater {
    private Collection<TableModel> mTableModels;

    /* access modifiers changed from: protected */
    public void createOrUpgradeTable(SQLiteDatabase db, boolean force) {
        this.mTableModels = getAllTableModels();
        this.mDb = db;
        dropTables();
    }

    private void dropTables() {
        List<String> tableNamesToDrop = findTablesToDrop();
        dropTables(tableNamesToDrop, this.mDb);
        clearCopyInTableSchema(tableNamesToDrop);
    }

    private List<String> findTablesToDrop() {
        List<String> dropTableNames = new ArrayList<>();
        Cursor cursor = null;
        try {
            Cursor cursor2 = this.mDb.query(Const.TableSchema.TABLE_NAME, (String[]) null, (String) null, (String[]) null, (String) null, (String) null, (String) null);
            if (cursor2.moveToFirst()) {
                do {
                    String tableName = cursor2.getString(cursor2.getColumnIndexOrThrow("name"));
                    if (shouldDropThisTable(tableName, cursor2.getInt(cursor2.getColumnIndexOrThrow("type")))) {
                        LogUtil.d(AssociationUpdater.TAG, "need to drop " + tableName);
                        dropTableNames.add(tableName);
                    }
                } while (cursor2.moveToNext());
            }
            if (cursor2 != null) {
                cursor2.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return dropTableNames;
    }

    private List<String> pickTableNamesFromTableModels() {
        List<String> tableNames = new ArrayList<>();
        for (TableModel tableModel : this.mTableModels) {
            tableNames.add(tableModel.getTableName());
        }
        return tableNames;
    }

    private boolean shouldDropThisTable(String tableName, int tableType) {
        return !BaseUtility.containsIgnoreCases(pickTableNamesFromTableModels(), tableName) && tableType == 0;
    }
}

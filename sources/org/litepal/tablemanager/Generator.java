package org.litepal.tablemanager;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Collection;
import org.litepal.LitePalBase;
import org.litepal.exceptions.DatabaseGenerateException;
import org.litepal.parser.LitePalAttr;
import org.litepal.tablemanager.model.AssociationsModel;
import org.litepal.tablemanager.model.TableModel;
import org.litepal.util.BaseUtility;

public abstract class Generator extends LitePalBase {
    public static final String TAG = "Generator";
    private Collection<AssociationsModel> mAllRelationModels;
    private Collection<TableModel> mTableModels;

    /* access modifiers changed from: protected */
    public abstract void addOrUpdateAssociation(SQLiteDatabase sQLiteDatabase, boolean z);

    /* access modifiers changed from: protected */
    public abstract void createOrUpgradeTable(SQLiteDatabase sQLiteDatabase, boolean z);

    /* access modifiers changed from: protected */
    public Collection<TableModel> getAllTableModels() {
        if (this.mTableModels == null) {
            this.mTableModels = new ArrayList();
        }
        if (!canUseCache()) {
            this.mTableModels.clear();
            for (String className : LitePalAttr.getInstance().getClassNames()) {
                this.mTableModels.add(getTableModel(className));
            }
        }
        return this.mTableModels;
    }

    /* access modifiers changed from: protected */
    public Collection<AssociationsModel> getAllAssociations() {
        if (this.mAllRelationModels == null || this.mAllRelationModels.isEmpty()) {
            this.mAllRelationModels = getAssociations(LitePalAttr.getInstance().getClassNames());
        }
        return this.mAllRelationModels;
    }

    /* access modifiers changed from: protected */
    public void execute(String[] sqls, SQLiteDatabase db) {
        if (sqls != null) {
            try {
                for (String sql : sqls) {
                    String throwSQL = sql;
                    db.execSQL(BaseUtility.changeCase(sql));
                }
            } catch (SQLException e) {
                throw new DatabaseGenerateException(DatabaseGenerateException.SQL_ERROR + "");
            }
        }
    }

    private static void addAssociation(SQLiteDatabase db, boolean force) {
        new Creator().addOrUpdateAssociation(db, force);
    }

    private static void updateAssociations(SQLiteDatabase db) {
        new Upgrader().addOrUpdateAssociation(db, false);
    }

    private static void upgradeTables(SQLiteDatabase db) {
        new Upgrader().createOrUpgradeTable(db, false);
    }

    private static void create(SQLiteDatabase db, boolean force) {
        new Creator().createOrUpgradeTable(db, force);
    }

    private static void drop(SQLiteDatabase db) {
        new Dropper().createOrUpgradeTable(db, false);
    }

    private boolean canUseCache() {
        if (this.mTableModels != null && this.mTableModels.size() == LitePalAttr.getInstance().getClassNames().size()) {
            return true;
        }
        return false;
    }

    static void create(SQLiteDatabase db) {
        create(db, true);
        addAssociation(db, true);
    }

    static void upgrade(SQLiteDatabase db) {
        drop(db);
        create(db, false);
        updateAssociations(db);
        upgradeTables(db);
        addAssociation(db, false);
    }
}

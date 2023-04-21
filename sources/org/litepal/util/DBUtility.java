package org.litepal.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import cn.com.bioeasy.app.utils.ListUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.litepal.exceptions.DatabaseGenerateException;
import org.litepal.tablemanager.model.ColumnModel;
import org.litepal.tablemanager.model.TableModel;
import org.litepal.util.Const;

public class DBUtility {
    private static final String KEYWORDS_COLUMN_SUFFIX = "_lpcolumn";
    private static final String REG_COLLECTION = "\\s+(not\\s+)?(in|exists)\\s*\\(";
    private static final String REG_FUZZY = "\\s+(not\\s+)?(like|between)\\s+";
    private static final String REG_OPERATOR = "\\s*(=|!=|<>|<|>)";
    private static final String SQLITE_KEYWORDS = ",abort,add,after,all,alter,and,as,asc,autoincrement,before,begin,between,by,cascade,check,collate,column,commit,conflict,constraint,create,cross,database,deferrable,deferred,delete,desc,distinct,drop,each,end,escape,except,exclusive,exists,foreign,from,glob,group,having,in,index,inner,insert,intersect,into,is,isnull,join,like,limit,match,natural,not,notnull,null,of,offset,on,or,order,outer,plan,pragma,primary,query,raise,references,regexp,reindex,release,rename,replace,restrict,right,rollback,row,savepoint,select,set,table,temp,temporary,then,to,transaction,trigger,union,unique,update,using,vacuum,values,view,virtual,when,where,";
    private static final String TAG = "DBUtility";

    private DBUtility() {
    }

    public static String getTableNameByClassName(String className) {
        if (TextUtils.isEmpty(className) || '.' == className.charAt(className.length() - 1)) {
            return null;
        }
        return className.substring(className.lastIndexOf(".") + 1);
    }

    public static List<String> getTableNameListByClassNameList(List<String> classNames) {
        List<String> tableNames = new ArrayList<>();
        if (classNames != null && !classNames.isEmpty()) {
            for (String className : classNames) {
                tableNames.add(getTableNameByClassName(className));
            }
        }
        return tableNames;
    }

    public static String getTableNameByForeignColumn(String foreignColumnName) {
        if (TextUtils.isEmpty(foreignColumnName) || !foreignColumnName.toLowerCase().endsWith("_id")) {
            return null;
        }
        return foreignColumnName.substring(0, foreignColumnName.length() - "_id".length());
    }

    public static String getIntermediateTableName(String tableName, String associatedTableName) {
        if (TextUtils.isEmpty(tableName) || TextUtils.isEmpty(associatedTableName)) {
            return null;
        }
        if (tableName.toLowerCase().compareTo(associatedTableName.toLowerCase()) <= 0) {
            return String.valueOf(tableName) + "_" + associatedTableName;
        }
        return String.valueOf(associatedTableName) + "_" + tableName;
    }

    public static String getGenericTableName(String className, String fieldName) {
        return BaseUtility.changeCase(String.valueOf(getTableNameByClassName(className)) + "_" + fieldName);
    }

    public static String getGenericValueIdColumnName(String className) {
        return BaseUtility.changeCase(String.valueOf(getTableNameByClassName(className)) + "_id");
    }

    public static boolean isIntermediateTable(String tableName, SQLiteDatabase db) {
        if (!TextUtils.isEmpty(tableName) && tableName.matches("[0-9a-zA-Z]+_[0-9a-zA-Z]+")) {
            Cursor cursor = null;
            try {
                cursor = db.query(Const.TableSchema.TABLE_NAME, (String[]) null, (String) null, (String[]) null, (String) null, (String) null, (String) null);
                if (cursor.moveToFirst()) {
                    while (true) {
                        if (!tableName.equalsIgnoreCase(cursor.getString(cursor.getColumnIndexOrThrow("name")))) {
                            if (!cursor.moveToNext()) {
                                break;
                            }
                        } else if (cursor.getInt(cursor.getColumnIndexOrThrow("type")) == 1) {
                            if (cursor != null) {
                                cursor.close();
                            }
                            return true;
                        }
                    }
                }
                if (cursor != null) {
                    cursor.close();
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
        return false;
    }

    public static boolean isGenericTable(String tableName, SQLiteDatabase db) {
        if (!TextUtils.isEmpty(tableName) && tableName.matches("[0-9a-zA-Z]+_[0-9a-zA-Z]+")) {
            Cursor cursor = null;
            try {
                cursor = db.query(Const.TableSchema.TABLE_NAME, (String[]) null, (String) null, (String[]) null, (String) null, (String) null, (String) null);
                if (cursor.moveToFirst()) {
                    while (true) {
                        if (!tableName.equalsIgnoreCase(cursor.getString(cursor.getColumnIndexOrThrow("name")))) {
                            if (!cursor.moveToNext()) {
                                break;
                            }
                        } else if (cursor.getInt(cursor.getColumnIndexOrThrow("type")) == 2) {
                            if (cursor != null) {
                                cursor.close();
                            }
                            return true;
                        }
                    }
                }
                if (cursor != null) {
                    cursor.close();
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
        return false;
    }

    public static boolean isTableExists(String tableName, SQLiteDatabase db) {
        try {
            return BaseUtility.containsIgnoreCases(findAllTableNames(db), tableName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isColumnExists(String columnName, String tableName, SQLiteDatabase db) {
        if (TextUtils.isEmpty(columnName) || TextUtils.isEmpty(tableName)) {
            return false;
        }
        boolean exist = false;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("pragma table_info(" + tableName + ")", (String[]) null);
            if (cursor.moveToFirst()) {
                while (true) {
                    if (!columnName.equalsIgnoreCase(cursor.getString(cursor.getColumnIndexOrThrow("name")))) {
                        if (!cursor.moveToNext()) {
                            break;
                        }
                    } else {
                        exist = true;
                        break;
                    }
                }
            }
            if (cursor == null) {
                return exist;
            }
            cursor.close();
            return exist;
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor == null) {
                return false;
            }
            cursor.close();
            return false;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public static List<String> findAllTableNames(SQLiteDatabase db) {
        List<String> tableNames = new ArrayList<>();
        Cursor cursor = null;
        try {
            Cursor cursor2 = db.rawQuery("select * from sqlite_master where type = ?", new String[]{"table"});
            if (cursor2.moveToFirst()) {
                do {
                    String tableName = cursor2.getString(cursor2.getColumnIndexOrThrow("tbl_name"));
                    if (!tableNames.contains(tableName)) {
                        tableNames.add(tableName);
                    }
                } while (cursor2.moveToNext());
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            return tableNames;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseGenerateException(e.getMessage());
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public static TableModel findPragmaTableInfo(String tableName, SQLiteDatabase db) {
        String defaultValue;
        if (isTableExists(tableName, db)) {
            List<String> uniqueColumns = findUniqueColumns(tableName, db);
            TableModel tableModelDB = new TableModel();
            tableModelDB.setTableName(tableName);
            Cursor cursor = null;
            try {
                Cursor cursor2 = db.rawQuery("pragma table_info(" + tableName + ")", (String[]) null);
                if (cursor2.moveToFirst()) {
                    do {
                        ColumnModel columnModel = new ColumnModel();
                        String name = cursor2.getString(cursor2.getColumnIndexOrThrow("name"));
                        String type = cursor2.getString(cursor2.getColumnIndexOrThrow("type"));
                        boolean nullable = cursor2.getInt(cursor2.getColumnIndexOrThrow("notnull")) != 1;
                        boolean unique = uniqueColumns.contains(name);
                        String defaultValue2 = cursor2.getString(cursor2.getColumnIndexOrThrow("dflt_value"));
                        columnModel.setColumnName(name);
                        columnModel.setColumnType(type);
                        columnModel.setIsNullable(nullable);
                        columnModel.setIsUnique(unique);
                        if (defaultValue2 != null) {
                            defaultValue = defaultValue2.replace("'", "");
                        } else {
                            defaultValue = "";
                        }
                        columnModel.setDefaultValue(defaultValue);
                        tableModelDB.addColumnModel(columnModel);
                    } while (cursor2.moveToNext());
                }
                if (cursor2 != null) {
                    cursor2.close();
                }
                return tableModelDB;
            } catch (Exception e) {
                e.printStackTrace();
                throw new DatabaseGenerateException(e.getMessage());
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } else {
            throw new DatabaseGenerateException(DatabaseGenerateException.TABLE_DOES_NOT_EXIST_WHEN_EXECUTING + tableName);
        }
    }

    public static List<String> findUniqueColumns(String tableName, SQLiteDatabase db) {
        List<String> columns = new ArrayList<>();
        Cursor cursor = null;
        Cursor innerCursor = null;
        try {
            Cursor cursor2 = db.rawQuery("pragma index_list(" + tableName + ")", (String[]) null);
            if (cursor2.moveToFirst()) {
                do {
                    if (cursor2.getInt(cursor2.getColumnIndexOrThrow("unique")) == 1) {
                        innerCursor = db.rawQuery("pragma index_info(" + cursor2.getString(cursor2.getColumnIndexOrThrow("name")) + ")", (String[]) null);
                        if (innerCursor.moveToFirst()) {
                            columns.add(innerCursor.getString(innerCursor.getColumnIndexOrThrow("name")));
                        }
                    }
                } while (cursor2.moveToNext());
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            if (innerCursor != null) {
                innerCursor.close();
            }
            return columns;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseGenerateException(e.getMessage());
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            if (innerCursor != null) {
                innerCursor.close();
            }
            throw th;
        }
    }

    public static boolean isFieldNameConflictWithSQLiteKeywords(String fieldName) {
        if (TextUtils.isEmpty(fieldName) || !SQLITE_KEYWORDS.contains(ListUtils.DEFAULT_JOIN_SEPARATOR + fieldName.toLowerCase() + ListUtils.DEFAULT_JOIN_SEPARATOR)) {
            return false;
        }
        return true;
    }

    public static String convertToValidColumnName(String columnName) {
        if (isFieldNameConflictWithSQLiteKeywords(columnName)) {
            return String.valueOf(columnName) + KEYWORDS_COLUMN_SUFFIX;
        }
        return columnName;
    }

    public static String convertWhereClauseToColumnName(String whereClause) {
        try {
            StringBuffer convertedWhereClause = new StringBuffer();
            Matcher m = Pattern.compile("(\\w+\\s*(=|!=|<>|<|>)|\\w+\\s+(not\\s+)?(like|between)\\s+|\\w+\\s+(not\\s+)?(in|exists)\\s*\\()").matcher(whereClause);
            while (m.find()) {
                String matches = m.group();
                String column = matches.replaceAll("(\\s*(=|!=|<>|<|>)|\\s+(not\\s+)?(like|between)\\s+|\\s+(not\\s+)?(in|exists)\\s*\\()", "");
                m.appendReplacement(convertedWhereClause, String.valueOf(convertToValidColumnName(column)) + matches.replace(column, ""));
            }
            m.appendTail(convertedWhereClause);
            return convertedWhereClause.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return whereClause;
        }
    }

    public static String[] convertSelectClauseToValidNames(String[] columns) {
        if (columns == null || columns.length <= 0) {
            return null;
        }
        String[] convertedColumns = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            convertedColumns[i] = convertToValidColumnName(columns[i]);
        }
        return convertedColumns;
    }

    public static String convertOrderByClauseToValidName(String orderBy) {
        if (TextUtils.isEmpty(orderBy)) {
            return null;
        }
        String orderBy2 = orderBy.trim().toLowerCase();
        if (!orderBy2.contains(ListUtils.DEFAULT_JOIN_SEPARATOR)) {
            return convertOrderByItem(orderBy2);
        }
        String[] orderByItems = orderBy2.split(ListUtils.DEFAULT_JOIN_SEPARATOR);
        StringBuilder builder = new StringBuilder();
        boolean needComma = false;
        for (String orderByItem : orderByItems) {
            if (needComma) {
                builder.append(ListUtils.DEFAULT_JOIN_SEPARATOR);
            }
            builder.append(convertOrderByItem(orderByItem));
            needComma = true;
        }
        return builder.toString();
    }

    private static String convertOrderByItem(String orderByItem) {
        String column;
        String append;
        if (orderByItem.endsWith("asc")) {
            column = orderByItem.replace("asc", "").trim();
            append = " asc";
        } else if (orderByItem.endsWith("desc")) {
            column = orderByItem.replace("desc", "").trim();
            append = " desc";
        } else {
            column = orderByItem;
            append = "";
        }
        return String.valueOf(convertToValidColumnName(column)) + append;
    }
}

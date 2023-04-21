package org.litepal.crud;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.ActivityChooserView;
import android.util.SparseArray;
import com.alipay.sdk.cons.a;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.litepal.LitePalBase;
import org.litepal.crud.model.AssociationsInfo;
import org.litepal.exceptions.DataSupportException;
import org.litepal.exceptions.DatabaseGenerateException;
import org.litepal.tablemanager.model.GenericModel;
import org.litepal.util.BaseUtility;
import org.litepal.util.DBUtility;

abstract class DataHandler extends LitePalBase {
    public static final String TAG = "DataHandler";
    private List<AssociationsInfo> fkInCurrentModel;
    private List<AssociationsInfo> fkInOtherModel;
    SQLiteDatabase mDatabase;
    private DataSupport tempEmptyModel;

    DataHandler() {
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00b0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> java.util.List<T> query(java.lang.Class<T> r23, java.lang.String[] r24, java.lang.String r25, java.lang.String[] r26, java.lang.String r27, java.lang.String r28, java.lang.String r29, java.lang.String r30, java.util.List<org.litepal.crud.model.AssociationsInfo> r31) {
        /*
            r22 = this;
            java.util.ArrayList r14 = new java.util.ArrayList
            r14.<init>()
            r13 = 0
            java.lang.String r4 = r23.getName()     // Catch:{ Exception -> 0x00a1, all -> 0x00b4 }
            r0 = r22
            java.util.List r17 = r0.getSupportedFields(r4)     // Catch:{ Exception -> 0x00a1, all -> 0x00b4 }
            java.lang.String r4 = r23.getName()     // Catch:{ Exception -> 0x00a1, all -> 0x00b4 }
            r0 = r22
            java.util.List r18 = r0.getSupportedGenericFields(r4)     // Catch:{ Exception -> 0x00a1, all -> 0x00b4 }
            r0 = r22
            r1 = r24
            r2 = r18
            r3 = r31
            java.lang.String[] r4 = r0.getCustomizedColumns(r1, r2, r3)     // Catch:{ Exception -> 0x00a1, all -> 0x00b4 }
            java.lang.String[] r6 = org.litepal.util.DBUtility.convertSelectClauseToValidNames(r4)     // Catch:{ Exception -> 0x00a1, all -> 0x00b4 }
            java.lang.String r5 = r22.getTableName(r23)     // Catch:{ Exception -> 0x00a1, all -> 0x00b4 }
            r0 = r22
            android.database.sqlite.SQLiteDatabase r4 = r0.mDatabase     // Catch:{ Exception -> 0x00a1, all -> 0x00b4 }
            r7 = r25
            r8 = r26
            r9 = r27
            r10 = r28
            r11 = r29
            r12 = r30
            android.database.Cursor r11 = r4.query(r5, r6, r7, r8, r9, r10, r11, r12)     // Catch:{ Exception -> 0x00a1, all -> 0x00b4 }
            boolean r4 = r11.moveToFirst()     // Catch:{ Exception -> 0x00b7 }
            if (r4 == 0) goto L_0x009b
            android.util.SparseArray r12 = new android.util.SparseArray     // Catch:{ Exception -> 0x00b7 }
            r12.<init>()     // Catch:{ Exception -> 0x00b7 }
            java.util.HashMap r16 = new java.util.HashMap     // Catch:{ Exception -> 0x00b7 }
            r16.<init>()     // Catch:{ Exception -> 0x00b7 }
        L_0x0052:
            java.lang.Object r8 = r22.createInstanceFromClass(r23)     // Catch:{ Exception -> 0x00b7 }
            r0 = r8
            org.litepal.crud.DataSupport r0 = (org.litepal.crud.DataSupport) r0     // Catch:{ Exception -> 0x00b7 }
            r4 = r0
            java.lang.String r7 = "id"
            int r7 = r11.getColumnIndexOrThrow(r7)     // Catch:{ Exception -> 0x00b7 }
            long r20 = r11.getLong(r7)     // Catch:{ Exception -> 0x00b7 }
            r0 = r22
            r1 = r20
            r0.giveBaseObjIdValue(r4, r1)     // Catch:{ Exception -> 0x00b7 }
            r7 = r22
            r9 = r17
            r10 = r31
            r7.setValueToModel(r8, r9, r10, r11, r12)     // Catch:{ Exception -> 0x00b7 }
            r0 = r8
            org.litepal.crud.DataSupport r0 = (org.litepal.crud.DataSupport) r0     // Catch:{ Exception -> 0x00b7 }
            r4 = r0
            r0 = r22
            r1 = r18
            r2 = r16
            r0.setGenericValueToModel(r4, r1, r2)     // Catch:{ Exception -> 0x00b7 }
            if (r31 == 0) goto L_0x008c
            r0 = r8
            org.litepal.crud.DataSupport r0 = (org.litepal.crud.DataSupport) r0     // Catch:{ Exception -> 0x00b7 }
            r4 = r0
            r0 = r22
            r0.setAssociatedModel(r4)     // Catch:{ Exception -> 0x00b7 }
        L_0x008c:
            r14.add(r8)     // Catch:{ Exception -> 0x00b7 }
            boolean r4 = r11.moveToNext()     // Catch:{ Exception -> 0x00b7 }
            if (r4 != 0) goto L_0x0052
            r12.clear()     // Catch:{ Exception -> 0x00b7 }
            r16.clear()     // Catch:{ Exception -> 0x00b7 }
        L_0x009b:
            if (r11 == 0) goto L_0x00a0
            r11.close()
        L_0x00a0:
            return r14
        L_0x00a1:
            r15 = move-exception
            r11 = r13
        L_0x00a3:
            org.litepal.exceptions.DataSupportException r4 = new org.litepal.exceptions.DataSupportException     // Catch:{ all -> 0x00ad }
            java.lang.String r7 = r15.getMessage()     // Catch:{ all -> 0x00ad }
            r4.<init>(r7, r15)     // Catch:{ all -> 0x00ad }
            throw r4     // Catch:{ all -> 0x00ad }
        L_0x00ad:
            r4 = move-exception
        L_0x00ae:
            if (r11 == 0) goto L_0x00b3
            r11.close()
        L_0x00b3:
            throw r4
        L_0x00b4:
            r4 = move-exception
            r11 = r13
            goto L_0x00ae
        L_0x00b7:
            r15 = move-exception
            goto L_0x00a3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.litepal.crud.DataHandler.query(java.lang.Class, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List):java.util.List");
    }

    /* access modifiers changed from: protected */
    public <T> T mathQuery(String tableName, String[] columns, String[] conditions, Class<T> type) {
        BaseUtility.checkConditionsCorrect(conditions);
        Cursor cursor = null;
        T result = null;
        try {
            Cursor cursor2 = this.mDatabase.query(tableName, columns, getWhereClause(conditions), getWhereArgs(conditions), (String) null, (String) null, (String) null);
            if (cursor2.moveToFirst()) {
                result = cursor2.getClass().getMethod(genGetColumnMethod((Class<?>) type), new Class[]{Integer.TYPE}).invoke(cursor2, new Object[]{0});
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            return result;
        } catch (Exception e) {
            throw new DataSupportException(e.getMessage(), e);
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void giveBaseObjIdValue(DataSupport baseObj, long id) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if (id > 0) {
            DynamicExecutor.set(baseObj, "baseObjId", Long.valueOf(id), DataSupport.class);
        }
    }

    /* access modifiers changed from: protected */
    public void putFieldsValue(DataSupport baseObj, List<Field> supportedFields, ContentValues values) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        for (Field field : supportedFields) {
            if (!isIdColumn(field.getName())) {
                putFieldsValueDependsOnSaveOrUpdate(baseObj, field, values);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void putContentValuesForSave(DataSupport baseObj, Field field, ContentValues values) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object fieldValue = DynamicExecutor.getField(baseObj, field.getName(), baseObj.getClass());
        if (fieldValue != null) {
            if ("java.util.Date".equals(field.getType().getName())) {
                fieldValue = Long.valueOf(((Date) fieldValue).getTime());
            }
            Object[] parameters = {BaseUtility.changeCase(DBUtility.convertToValidColumnName(field.getName())), fieldValue};
            DynamicExecutor.send(values, "put", parameters, values.getClass(), getParameterTypes(field, fieldValue, parameters));
        }
    }

    /* access modifiers changed from: protected */
    public void putContentValuesForUpdate(DataSupport baseObj, Field field, ContentValues values) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object fieldValue = takeGetMethodValueByField(baseObj, field);
        if ("java.util.Date".equals(field.getType().getName()) && fieldValue != null) {
            fieldValue = Long.valueOf(((Date) fieldValue).getTime());
        }
        Object[] parameters = {BaseUtility.changeCase(DBUtility.convertToValidColumnName(field.getName())), fieldValue};
        DynamicExecutor.send(values, "put", parameters, values.getClass(), getParameterTypes(field, fieldValue, parameters));
    }

    /* access modifiers changed from: protected */
    public Object takeGetMethodValueByField(DataSupport dataSupport, Field field) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (shouldGetOrSet(dataSupport, field)) {
            return DynamicExecutor.send(dataSupport, makeGetterMethodName(field), (Object[]) null, dataSupport.getClass(), (Class<?>[]) null);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void putSetMethodValueByField(DataSupport dataSupport, Field field, Object parameter) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (shouldGetOrSet(dataSupport, field)) {
            DynamicExecutor.send(dataSupport, makeSetterMethodName(field), new Object[]{parameter}, dataSupport.getClass(), new Class[]{field.getType()});
        }
    }

    /* access modifiers changed from: protected */
    public void analyzeAssociatedModels(DataSupport baseObj, Collection<AssociationsInfo> associationInfos) {
        try {
            for (AssociationsInfo associationInfo : associationInfos) {
                if (associationInfo.getAssociationType() == 2) {
                    new Many2OneAnalyzer().analyze(baseObj, associationInfo);
                } else if (associationInfo.getAssociationType() == 1) {
                    new One2OneAnalyzer().analyze(baseObj, associationInfo);
                } else if (associationInfo.getAssociationType() == 3) {
                    new Many2ManyAnalyzer().analyze(baseObj, associationInfo);
                }
            }
        } catch (Exception e) {
            throw new DataSupportException(e.getMessage(), e);
        }
    }

    /* access modifiers changed from: protected */
    public DataSupport getAssociatedModel(DataSupport baseObj, AssociationsInfo associationInfo) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return (DataSupport) takeGetMethodValueByField(baseObj, associationInfo.getAssociateOtherModelFromSelf());
    }

    /* access modifiers changed from: protected */
    public Collection<DataSupport> getAssociatedModels(DataSupport baseObj, AssociationsInfo associationInfo) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return (Collection) takeGetMethodValueByField(baseObj, associationInfo.getAssociateOtherModelFromSelf());
    }

    /* access modifiers changed from: protected */
    public DataSupport getEmptyModel(DataSupport baseObj) {
        if (this.tempEmptyModel != null) {
            return this.tempEmptyModel;
        }
        String className = null;
        try {
            className = baseObj.getClassName();
            this.tempEmptyModel = (DataSupport) Class.forName(className).newInstance();
            return this.tempEmptyModel;
        } catch (ClassNotFoundException e) {
            throw new DatabaseGenerateException(DatabaseGenerateException.CLASS_NOT_FOUND + className);
        } catch (InstantiationException e2) {
            throw new DataSupportException(String.valueOf(className) + DataSupportException.INSTANTIATION_EXCEPTION, e2);
        } catch (Exception e3) {
            throw new DataSupportException(e3.getMessage(), e3);
        }
    }

    /* access modifiers changed from: protected */
    public String getWhereClause(String... conditions) {
        if (!isAffectAllLines(conditions) && conditions != null && conditions.length > 0) {
            return conditions[0];
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public String[] getWhereArgs(String... conditions) {
        if (isAffectAllLines(conditions) || conditions == null || conditions.length <= 1) {
            return null;
        }
        String[] whereArgs = new String[(conditions.length - 1)];
        System.arraycopy(conditions, 1, whereArgs, 0, conditions.length - 1);
        return whereArgs;
    }

    /* access modifiers changed from: protected */
    public boolean isAffectAllLines(Object... conditions) {
        if (conditions == null || conditions.length != 0) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public String getWhereOfIdsWithOr(Collection<Long> ids) {
        StringBuilder whereClause = new StringBuilder();
        boolean needOr = false;
        for (Long longValue : ids) {
            long id = longValue.longValue();
            if (needOr) {
                whereClause.append(" or ");
            }
            needOr = true;
            whereClause.append("id = ");
            whereClause.append(id);
        }
        return BaseUtility.changeCase(whereClause.toString());
    }

    /* access modifiers changed from: protected */
    public String getWhereOfIdsWithOr(long... ids) {
        StringBuilder whereClause = new StringBuilder();
        boolean needOr = false;
        for (long id : ids) {
            if (needOr) {
                whereClause.append(" or ");
            }
            needOr = true;
            whereClause.append("id = ");
            whereClause.append(id);
        }
        return BaseUtility.changeCase(whereClause.toString());
    }

    /* access modifiers changed from: protected */
    public boolean shouldGetOrSet(DataSupport dataSupport, Field field) {
        return (dataSupport == null || field == null) ? false : true;
    }

    /* access modifiers changed from: protected */
    public String getIntermediateTableName(DataSupport baseObj, String associatedTableName) {
        return BaseUtility.changeCase(DBUtility.getIntermediateTableName(baseObj.getTableName(), associatedTableName));
    }

    /* access modifiers changed from: protected */
    public String getTableName(Class<?> modelClass) {
        return BaseUtility.changeCase(DBUtility.getTableNameByClassName(modelClass.getName()));
    }

    /* access modifiers changed from: protected */
    public Object createInstanceFromClass(Class<?> modelClass) {
        try {
            Constructor<?> constructor = findBestSuitConstructor(modelClass);
            return constructor.newInstance(getConstructorParams(modelClass, constructor));
        } catch (Exception e) {
            throw new DataSupportException(e.getMessage(), e);
        }
    }

    /* access modifiers changed from: protected */
    public Constructor<?> findBestSuitConstructor(Class<?> modelClass) {
        Constructor<?>[] constructors = modelClass.getDeclaredConstructors();
        SparseArray<Constructor<?>> map = new SparseArray<>();
        int minKey = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        for (Constructor<?> constructor : constructors) {
            int key = constructor.getParameterTypes().length;
            for (Class<?> parameterType : constructor.getParameterTypes()) {
                if (parameterType == modelClass) {
                    key += 10000;
                } else if (parameterType.getName().equals("com.android.tools.fd.runtime.InstantReloadException")) {
                    key += 10000;
                }
            }
            if (map.get(key) == null) {
                map.put(key, constructor);
            }
            if (key < minKey) {
                minKey = key;
            }
        }
        Constructor<?> bestSuitConstructor = map.get(minKey);
        if (bestSuitConstructor != null) {
            bestSuitConstructor.setAccessible(true);
        }
        return bestSuitConstructor;
    }

    /* access modifiers changed from: protected */
    public Object[] getConstructorParams(Class<?> modelClass, Constructor<?> constructor) {
        Class[] paramTypes = constructor.getParameterTypes();
        Object[] params = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            params[i] = getInitParamValue(modelClass, paramTypes[i]);
        }
        return params;
    }

    /* access modifiers changed from: protected */
    public void setValueToModel(Object modelInstance, List<Field> supportedFields, List<AssociationsInfo> foreignKeyAssociations, Cursor cursor, SparseArray<QueryInfoCache> sparseArray) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String columnName;
        int cacheSize = sparseArray.size();
        if (cacheSize > 0) {
            for (int i = 0; i < cacheSize; i++) {
                int columnIndex = sparseArray.keyAt(i);
                QueryInfoCache cache = sparseArray.get(columnIndex);
                setToModelByReflection(modelInstance, cache.field, columnIndex, cache.getMethodName, cursor);
            }
        } else {
            for (Field field : supportedFields) {
                String getMethodName = genGetColumnMethod(field);
                if (isIdColumn(field.getName())) {
                    columnName = "id";
                } else {
                    columnName = DBUtility.convertToValidColumnName(field.getName());
                }
                int columnIndex2 = cursor.getColumnIndex(BaseUtility.changeCase(columnName));
                if (columnIndex2 != -1) {
                    setToModelByReflection(modelInstance, field, columnIndex2, getMethodName, cursor);
                    QueryInfoCache cache2 = new QueryInfoCache();
                    cache2.getMethodName = getMethodName;
                    cache2.field = field;
                    sparseArray.put(columnIndex2, cache2);
                }
            }
        }
        if (foreignKeyAssociations != null) {
            for (AssociationsInfo associationInfo : foreignKeyAssociations) {
                int columnIndex3 = cursor.getColumnIndex(getForeignKeyColumnName(DBUtility.getTableNameByClassName(associationInfo.getAssociatedClassName())));
                if (columnIndex3 != -1) {
                    try {
                        DataSupport associatedObj = (DataSupport) DataSupport.find(Class.forName(associationInfo.getAssociatedClassName()), cursor.getLong(columnIndex3));
                        if (associatedObj != null) {
                            putSetMethodValueByField((DataSupport) modelInstance, associationInfo.getAssociateOtherModelFromSelf(), associatedObj);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setGenericValueToModel(DataSupport baseObj, List<Field> supportedGenericFields, Map<Field, GenericModel> genericModelMap) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String tableName;
        String genericValueColumnName;
        String genericValueIdColumnName;
        String getMethodName;
        Cursor cursor;
        for (Field field : supportedGenericFields) {
            GenericModel genericModel = genericModelMap.get(field);
            if (genericModel == null) {
                tableName = DBUtility.getGenericTableName(baseObj.getClassName(), field.getName());
                genericValueColumnName = DBUtility.convertToValidColumnName(field.getName());
                genericValueIdColumnName = DBUtility.getGenericValueIdColumnName(baseObj.getClassName());
                getMethodName = genGetColumnMethod(field);
                GenericModel model = new GenericModel();
                model.setTableName(tableName);
                model.setValueColumnName(genericValueColumnName);
                model.setValueIdColumnName(genericValueIdColumnName);
                model.setGetMethodName(getMethodName);
                genericModelMap.put(field, model);
            } else {
                tableName = genericModel.getTableName();
                genericValueColumnName = genericModel.getValueColumnName();
                genericValueIdColumnName = genericModel.getValueIdColumnName();
                getMethodName = genericModel.getGetMethodName();
            }
            try {
                cursor = this.mDatabase.query(tableName, (String[]) null, String.valueOf(genericValueIdColumnName) + " = ?", new String[]{String.valueOf(baseObj.getBaseObjId())}, (String) null, (String) null, (String) null);
                try {
                    if (cursor.moveToFirst()) {
                        do {
                            int columnIndex = cursor.getColumnIndex(BaseUtility.changeCase(genericValueColumnName));
                            if (columnIndex != -1) {
                                setToModelByReflection(baseObj, field, columnIndex, getMethodName, cursor);
                            }
                        } while (cursor.moveToNext());
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                cursor = null;
            }
        }
    }

    /* access modifiers changed from: protected */
    public List<AssociationsInfo> getForeignKeyAssociations(String className, boolean isEager) {
        if (!isEager) {
            return null;
        }
        analyzeAssociations(className);
        return this.fkInCurrentModel;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: java.lang.Class<?>[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.lang.Class<?>[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: java.lang.Class<?>[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: java.lang.Class<?>[]} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Class<?>[] getParameterTypes(java.lang.reflect.Field r7, java.lang.Object r8, java.lang.Object[] r9) {
        /*
            r6 = this;
            r5 = 2
            r4 = 0
            r3 = 1
            boolean r1 = r6.isCharType(r7)
            if (r1 == 0) goto L_0x001a
            java.lang.String r1 = java.lang.String.valueOf(r8)
            r9[r3] = r1
            java.lang.Class[] r0 = new java.lang.Class[r5]
            java.lang.Class<java.lang.String> r1 = java.lang.String.class
            r0[r4] = r1
            java.lang.Class<java.lang.String> r1 = java.lang.String.class
            r0[r3] = r1
        L_0x0019:
            return r0
        L_0x001a:
            java.lang.Class r1 = r7.getType()
            boolean r1 = r1.isPrimitive()
            if (r1 == 0) goto L_0x0035
            java.lang.Class[] r0 = new java.lang.Class[r5]
            java.lang.Class<java.lang.String> r1 = java.lang.String.class
            r0[r4] = r1
            java.lang.Class r1 = r7.getType()
            java.lang.Class r1 = r6.getObjectType(r1)
            r0[r3] = r1
            goto L_0x0019
        L_0x0035:
            java.lang.String r1 = "java.util.Date"
            java.lang.Class r2 = r7.getType()
            java.lang.String r2 = r2.getName()
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0050
            java.lang.Class[] r0 = new java.lang.Class[r5]
            java.lang.Class<java.lang.String> r1 = java.lang.String.class
            r0[r4] = r1
            java.lang.Class<java.lang.Long> r1 = java.lang.Long.class
            r0[r3] = r1
            goto L_0x0019
        L_0x0050:
            java.lang.Class[] r0 = new java.lang.Class[r5]
            java.lang.Class<java.lang.String> r1 = java.lang.String.class
            r0[r4] = r1
            java.lang.Class r1 = r7.getType()
            r0[r3] = r1
            goto L_0x0019
        */
        throw new UnsupportedOperationException("Method not decompiled: org.litepal.crud.DataHandler.getParameterTypes(java.lang.reflect.Field, java.lang.Object, java.lang.Object[]):java.lang.Class[]");
    }

    private Class<?> getObjectType(Class<?> primitiveType) {
        if (primitiveType != null && primitiveType.isPrimitive()) {
            String basicTypeName = primitiveType.getName();
            if ("int".equals(basicTypeName)) {
                return Integer.class;
            }
            if ("short".equals(basicTypeName)) {
                return Short.class;
            }
            if ("long".equals(basicTypeName)) {
                return Long.class;
            }
            if ("float".equals(basicTypeName)) {
                return Float.class;
            }
            if ("double".equals(basicTypeName)) {
                return Double.class;
            }
            if ("boolean".equals(basicTypeName)) {
                return Boolean.class;
            }
            if ("char".equals(basicTypeName)) {
                return Character.class;
            }
        }
        return null;
    }

    private Object getInitParamValue(Class<?> modelClass, Class<?> paramType) {
        String paramTypeName = paramType.getName();
        if ("boolean".equals(paramTypeName) || "java.lang.Boolean".equals(paramTypeName)) {
            return false;
        }
        if ("float".equals(paramTypeName) || "java.lang.Float".equals(paramTypeName)) {
            return Float.valueOf(0.0f);
        }
        if ("double".equals(paramTypeName) || "java.lang.Double".equals(paramTypeName)) {
            return Double.valueOf(0.0d);
        }
        if ("int".equals(paramTypeName) || "java.lang.Integer".equals(paramTypeName)) {
            return 0;
        }
        if ("long".equals(paramTypeName) || "java.lang.Long".equals(paramTypeName)) {
            return 0L;
        }
        if ("short".equals(paramTypeName) || "java.lang.Short".equals(paramTypeName)) {
            return 0;
        }
        if ("char".equals(paramTypeName) || "java.lang.Character".equals(paramTypeName)) {
            return ' ';
        }
        if ("[B".equals(paramTypeName) || "[Ljava.lang.Byte;".equals(paramTypeName)) {
            return new byte[0];
        }
        if ("java.lang.String".equals(paramTypeName)) {
            return "";
        }
        if (modelClass == paramType) {
            return null;
        }
        return createInstanceFromClass(paramType);
    }

    private boolean isCharType(Field field) {
        String type = field.getType().getName();
        return type.equals("char") || type.endsWith("Character");
    }

    private boolean isPrimitiveBooleanType(Field field) {
        if ("boolean".equals(field.getType().getName())) {
            return true;
        }
        return false;
    }

    private void putFieldsValueDependsOnSaveOrUpdate(DataSupport baseObj, Field field, ContentValues values) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (isUpdating()) {
            if (!isFieldWithDefaultValue(baseObj, field)) {
                putContentValuesForUpdate(baseObj, field, values);
            }
        } else if (isSaving()) {
            putContentValuesForSave(baseObj, field, values);
        }
    }

    private boolean isUpdating() {
        return UpdateHandler.class.getName().equals(getClass().getName());
    }

    private boolean isSaving() {
        return SaveHandler.class.getName().equals(getClass().getName());
    }

    private boolean isFieldWithDefaultValue(DataSupport baseObj, Field field) throws IllegalAccessException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException {
        DataSupport emptyModel = getEmptyModel(baseObj);
        Object realReturn = takeGetMethodValueByField(baseObj, field);
        Object defaultReturn = takeGetMethodValueByField(emptyModel, field);
        if (realReturn == null || defaultReturn == null) {
            return realReturn == defaultReturn;
        }
        return takeGetMethodValueByField(baseObj, field).toString().equals(takeGetMethodValueByField(emptyModel, field).toString());
    }

    private String makeGetterMethodName(Field field) {
        String getterMethodPrefix;
        String fieldName = field.getName();
        if (isPrimitiveBooleanType(field)) {
            if (fieldName.matches("^is[A-Z]{1}.*$")) {
                fieldName = fieldName.substring(2);
            }
            getterMethodPrefix = "is";
        } else {
            getterMethodPrefix = "get";
        }
        if (fieldName.matches("^[a-z]{1}[A-Z]{1}.*")) {
            return String.valueOf(getterMethodPrefix) + fieldName;
        }
        return String.valueOf(getterMethodPrefix) + BaseUtility.capitalize(fieldName);
    }

    private String makeSetterMethodName(Field field) {
        if (isPrimitiveBooleanType(field) && field.getName().matches("^is[A-Z]{1}.*$")) {
            return String.valueOf("set") + field.getName().substring(2);
        }
        if (field.getName().matches("^[a-z]{1}[A-Z]{1}.*")) {
            return String.valueOf("set") + field.getName();
        }
        return String.valueOf("set") + BaseUtility.capitalize(field.getName());
    }

    private String genGetColumnMethod(Field field) {
        Class<?> fieldType;
        if (isCollection(field.getType())) {
            fieldType = getGenericTypeClass(field);
        } else {
            fieldType = field.getType();
        }
        return genGetColumnMethod(fieldType);
    }

    private String genGetColumnMethod(Class<?> fieldType) {
        String typeName;
        if (fieldType.isPrimitive()) {
            typeName = BaseUtility.capitalize(fieldType.getName());
        } else {
            typeName = fieldType.getSimpleName();
        }
        String methodName = "get" + typeName;
        if ("getBoolean".equals(methodName)) {
            return "getInt";
        }
        if ("getChar".equals(methodName) || "getCharacter".equals(methodName)) {
            return "getString";
        }
        if ("getDate".equals(methodName)) {
            return "getLong";
        }
        if ("getInteger".equals(methodName)) {
            return "getInt";
        }
        if ("getbyte[]".equalsIgnoreCase(methodName)) {
            return "getBlob";
        }
        return methodName;
    }

    private String[] getCustomizedColumns(String[] columns, List<Field> supportedGenericFields, List<AssociationsInfo> foreignKeyAssociations) {
        if (columns == null || columns.length <= 0) {
            return null;
        }
        boolean columnsContainsId = false;
        List<String> columnList = new ArrayList<>(Arrays.asList(columns));
        List<String> supportedGenericFieldNames = new ArrayList<>();
        List<Integer> columnToRemove = new ArrayList<>();
        List<String> genericColumnsForQuery = new ArrayList<>();
        List<Field> tempSupportedGenericFields = new ArrayList<>();
        for (Field supportedGenericField : supportedGenericFields) {
            supportedGenericFieldNames.add(supportedGenericField.getName());
        }
        for (int i = 0; i < columnList.size(); i++) {
            String columnName = columnList.get(i);
            if (BaseUtility.containsIgnoreCases(supportedGenericFieldNames, columnName)) {
                columnToRemove.add(Integer.valueOf(i));
            } else if (isIdColumn(columnName)) {
                columnsContainsId = true;
                if ("_id".equalsIgnoreCase(columnName)) {
                    columnList.set(i, BaseUtility.changeCase("id"));
                }
            }
        }
        for (int i2 = columnToRemove.size() - 1; i2 >= 0; i2--) {
            genericColumnsForQuery.add(columnList.remove(columnToRemove.get(i2).intValue()));
        }
        for (Field supportedGenericField2 : supportedGenericFields) {
            if (BaseUtility.containsIgnoreCases(genericColumnsForQuery, supportedGenericField2.getName())) {
                tempSupportedGenericFields.add(supportedGenericField2);
            }
        }
        supportedGenericFields.clear();
        supportedGenericFields.addAll(tempSupportedGenericFields);
        if (foreignKeyAssociations != null && foreignKeyAssociations.size() > 0) {
            for (int i3 = 0; i3 < foreignKeyAssociations.size(); i3++) {
                columnList.add(getForeignKeyColumnName(DBUtility.getTableNameByClassName(foreignKeyAssociations.get(i3).getAssociatedClassName())));
            }
        }
        if (!columnsContainsId) {
            columnList.add(BaseUtility.changeCase("id"));
        }
        return (String[]) columnList.toArray(new String[columnList.size()]);
    }

    private void analyzeAssociations(String className) {
        Collection<AssociationsInfo> associationInfos = getAssociationInfo(className);
        if (this.fkInCurrentModel == null) {
            this.fkInCurrentModel = new ArrayList();
        } else {
            this.fkInCurrentModel.clear();
        }
        if (this.fkInOtherModel == null) {
            this.fkInOtherModel = new ArrayList();
        } else {
            this.fkInOtherModel.clear();
        }
        for (AssociationsInfo associationInfo : associationInfos) {
            if (associationInfo.getAssociationType() == 2 || associationInfo.getAssociationType() == 1) {
                if (associationInfo.getClassHoldsForeignKey().equals(className)) {
                    this.fkInCurrentModel.add(associationInfo);
                } else {
                    this.fkInOtherModel.add(associationInfo);
                }
            } else if (associationInfo.getAssociationType() == 3) {
                this.fkInOtherModel.add(associationInfo);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x0186  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void setAssociatedModel(org.litepal.crud.DataSupport r31) {
        /*
            r30 = this;
            r0 = r30
            java.util.List<org.litepal.crud.model.AssociationsInfo> r4 = r0.fkInOtherModel
            if (r4 != 0) goto L_0x0007
        L_0x0006:
            return
        L_0x0007:
            r0 = r30
            java.util.List<org.litepal.crud.model.AssociationsInfo> r4 = r0.fkInOtherModel
            java.util.Iterator r27 = r4.iterator()
        L_0x000f:
            boolean r4 = r27.hasNext()
            if (r4 == 0) goto L_0x0006
            java.lang.Object r20 = r27.next()
            org.litepal.crud.model.AssociationsInfo r20 = (org.litepal.crud.model.AssociationsInfo) r20
            r16 = 0
            java.lang.String r13 = r20.getAssociatedClassName()
            int r4 = r20.getAssociationType()
            r6 = 3
            if (r4 != r6) goto L_0x0119
            r22 = 1
        L_0x002a:
            r0 = r30
            java.util.List r24 = r0.getSupportedFields(r13)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r0 = r30
            java.util.List r25 = r0.getSupportedGenericFields(r13)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            if (r22 == 0) goto L_0x011d
            java.lang.String r26 = r31.getTableName()     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.String r14 = org.litepal.util.DBUtility.getTableNameByClassName(r13)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r0 = r26
            java.lang.String r21 = org.litepal.util.DBUtility.getIntermediateTableName(r0, r14)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r23.<init>()     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.String r4 = "select * from "
            r0 = r23
            java.lang.StringBuilder r4 = r0.append(r4)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.StringBuilder r4 = r4.append(r14)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.String r6 = " a inner join "
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r0 = r21
            java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.String r6 = " b on a.id = b."
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.String r7 = java.lang.String.valueOf(r14)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r6.<init>(r7)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.String r7 = "_id"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.String r6 = " where b."
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r0 = r26
            java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.String r6 = "_id = ?"
            r4.append(r6)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r4 = 2
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r6 = 0
            java.lang.String r7 = r23.toString()     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.String r7 = org.litepal.util.BaseUtility.changeCase(r7)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r4[r6] = r7     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r6 = 1
            long r10 = r31.getBaseObjId()     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.String r7 = java.lang.String.valueOf(r10)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r4[r6] = r7     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            android.database.Cursor r8 = org.litepal.crud.DataSupport.findBySQL(r4)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
        L_0x00ae:
            if (r8 == 0) goto L_0x0112
            boolean r4 = r8.moveToFirst()     // Catch:{ Exception -> 0x0176 }
            if (r4 == 0) goto L_0x0112
            android.util.SparseArray r9 = new android.util.SparseArray     // Catch:{ Exception -> 0x0176 }
            r9.<init>()     // Catch:{ Exception -> 0x0176 }
            java.util.HashMap r19 = new java.util.HashMap     // Catch:{ Exception -> 0x0176 }
            r19.<init>()     // Catch:{ Exception -> 0x0176 }
        L_0x00c0:
            java.lang.Class r4 = java.lang.Class.forName(r13)     // Catch:{ Exception -> 0x0176 }
            r0 = r30
            java.lang.Object r5 = r0.createInstanceFromClass(r4)     // Catch:{ Exception -> 0x0176 }
            org.litepal.crud.DataSupport r5 = (org.litepal.crud.DataSupport) r5     // Catch:{ Exception -> 0x0176 }
            java.lang.String r4 = "id"
            int r4 = r8.getColumnIndexOrThrow(r4)     // Catch:{ Exception -> 0x0176 }
            long r6 = r8.getLong(r4)     // Catch:{ Exception -> 0x0176 }
            r0 = r30
            r0.giveBaseObjIdValue(r5, r6)     // Catch:{ Exception -> 0x0176 }
            r7 = 0
            r4 = r30
            r6 = r24
            r4.setValueToModel(r5, r6, r7, r8, r9)     // Catch:{ Exception -> 0x0176 }
            r0 = r30
            r1 = r25
            r2 = r19
            r0.setGenericValueToModel(r5, r1, r2)     // Catch:{ Exception -> 0x0176 }
            int r4 = r20.getAssociationType()     // Catch:{ Exception -> 0x0176 }
            r6 = 2
            if (r4 == r6) goto L_0x00f5
            if (r22 == 0) goto L_0x0163
        L_0x00f5:
            java.lang.reflect.Field r4 = r20.getAssociateOtherModelFromSelf()     // Catch:{ Exception -> 0x0176 }
            r0 = r30
            r1 = r31
            java.lang.Object r15 = r0.takeGetMethodValueByField(r1, r4)     // Catch:{ Exception -> 0x0176 }
            java.util.Collection r15 = (java.util.Collection) r15     // Catch:{ Exception -> 0x0176 }
            r15.add(r5)     // Catch:{ Exception -> 0x0176 }
        L_0x0106:
            boolean r4 = r8.moveToNext()     // Catch:{ Exception -> 0x0176 }
            if (r4 != 0) goto L_0x00c0
            r9.clear()     // Catch:{ Exception -> 0x0176 }
            r19.clear()     // Catch:{ Exception -> 0x0176 }
        L_0x0112:
            if (r8 == 0) goto L_0x000f
            r8.close()
            goto L_0x000f
        L_0x0119:
            r22 = 0
            goto L_0x002a
        L_0x011d:
            java.lang.String r4 = r20.getSelfClassName()     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.String r4 = org.litepal.util.DBUtility.getTableNameByClassName(r4)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r0 = r30
            java.lang.String r18 = r0.getForeignKeyColumnName(r4)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.String r14 = org.litepal.util.DBUtility.getTableNameByClassName(r13)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r0 = r30
            android.database.sqlite.SQLiteDatabase r4 = r0.mDatabase     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.String r5 = org.litepal.util.BaseUtility.changeCase(r14)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r6 = 0
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.String r10 = java.lang.String.valueOf(r18)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r7.<init>(r10)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.String r10 = "=?"
            java.lang.StringBuilder r7 = r7.append(r10)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r10 = 1
            java.lang.String[] r8 = new java.lang.String[r10]     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r10 = 0
            long r28 = r31.getBaseObjId()     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            java.lang.String r11 = java.lang.String.valueOf(r28)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r8[r10] = r11     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            android.database.Cursor r8 = r4.query(r5, r6, r7, r8, r9, r10, r11, r12)     // Catch:{ Exception -> 0x018e, all -> 0x018a }
            goto L_0x00ae
        L_0x0163:
            int r4 = r20.getAssociationType()     // Catch:{ Exception -> 0x0176 }
            r6 = 1
            if (r4 != r6) goto L_0x0106
            java.lang.reflect.Field r4 = r20.getAssociateOtherModelFromSelf()     // Catch:{ Exception -> 0x0176 }
            r0 = r30
            r1 = r31
            r0.putSetMethodValueByField(r1, r4, r5)     // Catch:{ Exception -> 0x0176 }
            goto L_0x0106
        L_0x0176:
            r17 = move-exception
        L_0x0177:
            org.litepal.exceptions.DataSupportException r4 = new org.litepal.exceptions.DataSupportException     // Catch:{ all -> 0x0183 }
            java.lang.String r6 = r17.getMessage()     // Catch:{ all -> 0x0183 }
            r0 = r17
            r4.<init>(r6, r0)     // Catch:{ all -> 0x0183 }
            throw r4     // Catch:{ all -> 0x0183 }
        L_0x0183:
            r4 = move-exception
        L_0x0184:
            if (r8 == 0) goto L_0x0189
            r8.close()
        L_0x0189:
            throw r4
        L_0x018a:
            r4 = move-exception
            r8 = r16
            goto L_0x0184
        L_0x018e:
            r17 = move-exception
            r8 = r16
            goto L_0x0177
        */
        throw new UnsupportedOperationException("Method not decompiled: org.litepal.crud.DataHandler.setAssociatedModel(org.litepal.crud.DataSupport):void");
    }

    private void setToModelByReflection(Object modelInstance, Field field, int columnIndex, String getMethodName, Cursor cursor) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        boolean value = cursor.getClass().getMethod(getMethodName, new Class[]{Integer.TYPE}).invoke(cursor, new Object[]{Integer.valueOf(columnIndex)});
        if (field.getType() == Boolean.TYPE || field.getType() == Boolean.class) {
            if ("0".equals(String.valueOf(value))) {
                value = false;
            } else if (a.e.equals(String.valueOf(value))) {
                value = true;
            }
        } else if (field.getType() == Character.TYPE || field.getType() == Character.class) {
            value = Character.valueOf(((String) value).charAt(0));
        } else if (field.getType() == Date.class) {
            long date = ((Long) value).longValue();
            value = date <= 0 ? null : new Date(date);
        }
        if (isCollection(field.getType())) {
            ((Collection) DynamicExecutor.getField(modelInstance, field.getName(), modelInstance.getClass())).add(value);
        } else {
            DynamicExecutor.setField(modelInstance, field.getName(), value, modelInstance.getClass());
        }
    }

    class QueryInfoCache {
        Field field;
        String getMethodName;

        QueryInfoCache() {
        }
    }
}

package org.litepal;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;
import org.litepal.crud.model.AssociationsInfo;
import org.litepal.exceptions.DatabaseGenerateException;
import org.litepal.parser.LitePalAttr;
import org.litepal.tablemanager.model.AssociationsModel;
import org.litepal.tablemanager.model.ColumnModel;
import org.litepal.tablemanager.model.GenericModel;
import org.litepal.tablemanager.model.TableModel;
import org.litepal.tablemanager.typechange.BlobOrm;
import org.litepal.tablemanager.typechange.BooleanOrm;
import org.litepal.tablemanager.typechange.DateOrm;
import org.litepal.tablemanager.typechange.DecimalOrm;
import org.litepal.tablemanager.typechange.NumericOrm;
import org.litepal.tablemanager.typechange.OrmChange;
import org.litepal.tablemanager.typechange.TextOrm;
import org.litepal.util.BaseUtility;
import org.litepal.util.DBUtility;

public abstract class LitePalBase {
    private static final int GET_ASSOCIATIONS_ACTION = 1;
    private static final int GET_ASSOCIATION_INFO_ACTION = 2;
    public static final String TAG = "LitePalBase";
    private Map<String, List<Field>> classFieldsMap = new HashMap();
    private Map<String, List<Field>> classGenericFieldsMap = new HashMap();
    private Collection<AssociationsInfo> mAssociationInfos;
    private Collection<AssociationsModel> mAssociationModels;
    private Collection<GenericModel> mGenericModels;
    private OrmChange[] typeChangeRules = {new NumericOrm(), new TextOrm(), new BooleanOrm(), new DecimalOrm(), new DateOrm(), new BlobOrm()};

    /* access modifiers changed from: protected */
    public TableModel getTableModel(String className) {
        String tableName = DBUtility.getTableNameByClassName(className);
        TableModel tableModel = new TableModel();
        tableModel.setTableName(tableName);
        tableModel.setClassName(className);
        for (Field field : getSupportedFields(className)) {
            tableModel.addColumnModel(convertFieldToColumnModel(field));
        }
        return tableModel;
    }

    /* access modifiers changed from: protected */
    public Collection<AssociationsModel> getAssociations(List<String> classNames) {
        if (this.mAssociationModels == null) {
            this.mAssociationModels = new HashSet();
        }
        if (this.mGenericModels == null) {
            this.mGenericModels = new HashSet();
        }
        this.mAssociationModels.clear();
        this.mGenericModels.clear();
        for (String className : classNames) {
            analyzeClassFields(className, 1);
        }
        return this.mAssociationModels;
    }

    /* access modifiers changed from: protected */
    public Collection<GenericModel> getGenericModels() {
        return this.mGenericModels;
    }

    /* access modifiers changed from: protected */
    public Collection<AssociationsInfo> getAssociationInfo(String className) {
        if (this.mAssociationInfos == null) {
            this.mAssociationInfos = new HashSet();
        }
        this.mAssociationInfos.clear();
        analyzeClassFields(className, 2);
        return this.mAssociationInfos;
    }

    /* access modifiers changed from: protected */
    public List<Field> getSupportedFields(String className) {
        List<Field> fieldList = this.classFieldsMap.get(className);
        if (fieldList != null) {
            return fieldList;
        }
        List<Field> supportedFields = new ArrayList<>();
        try {
            recursiveSupportedFields(Class.forName(className), supportedFields);
            this.classFieldsMap.put(className, supportedFields);
            return supportedFields;
        } catch (ClassNotFoundException e) {
            throw new DatabaseGenerateException(DatabaseGenerateException.CLASS_NOT_FOUND + className);
        }
    }

    /* access modifiers changed from: protected */
    public List<Field> getSupportedGenericFields(String className) {
        List<Field> genericFieldList = this.classGenericFieldsMap.get(className);
        if (genericFieldList != null) {
            return genericFieldList;
        }
        List<Field> supportedGenericFields = new ArrayList<>();
        try {
            recursiveSupportedGenericFields(Class.forName(className), supportedGenericFields);
            this.classGenericFieldsMap.put(className, supportedGenericFields);
            return supportedGenericFields;
        } catch (ClassNotFoundException e) {
            throw new DatabaseGenerateException(DatabaseGenerateException.CLASS_NOT_FOUND + className);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isCollection(Class<?> fieldType) {
        return isList(fieldType) || isSet(fieldType);
    }

    /* access modifiers changed from: protected */
    public boolean isList(Class<?> fieldType) {
        return List.class.isAssignableFrom(fieldType);
    }

    /* access modifiers changed from: protected */
    public boolean isSet(Class<?> fieldType) {
        return Set.class.isAssignableFrom(fieldType);
    }

    /* access modifiers changed from: protected */
    public boolean isIdColumn(String columnName) {
        return "_id".equalsIgnoreCase(columnName) || "id".equalsIgnoreCase(columnName);
    }

    /* access modifiers changed from: protected */
    public String getForeignKeyColumnName(String associatedTableName) {
        return BaseUtility.changeCase(String.valueOf(associatedTableName) + "_id");
    }

    /* access modifiers changed from: protected */
    public String getColumnType(String fieldType) {
        for (OrmChange ormChange : this.typeChangeRules) {
            String columnType = ormChange.object2Relation(fieldType);
            if (columnType != null) {
                return columnType;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public Class<?> getGenericTypeClass(Field field) {
        Type genericType = field.getGenericType();
        if (genericType == null || !(genericType instanceof ParameterizedType)) {
            return null;
        }
        return (Class) ((ParameterizedType) genericType).getActualTypeArguments()[0];
    }

    private void recursiveSupportedFields(Class<?> clazz, List<Field> supportedFields) {
        if (clazz != DataSupport.class && clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    Column annotation = (Column) field.getAnnotation(Column.class);
                    if ((annotation == null || !annotation.ignore()) && !Modifier.isStatic(field.getModifiers()) && BaseUtility.isFieldTypeSupported(field.getType().getName())) {
                        supportedFields.add(field);
                    }
                }
            }
            recursiveSupportedFields(clazz.getSuperclass(), supportedFields);
        }
    }

    private void recursiveSupportedGenericFields(Class<?> clazz, List<Field> supportedGenericFields) {
        if (clazz != DataSupport.class && clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    Column annotation = (Column) field.getAnnotation(Column.class);
                    if ((annotation == null || !annotation.ignore()) && !Modifier.isStatic(field.getModifiers()) && isCollection(field.getType()) && BaseUtility.isGenericTypeSupported(getGenericTypeName(field))) {
                        supportedGenericFields.add(field);
                    }
                }
            }
            recursiveSupportedGenericFields(clazz.getSuperclass(), supportedGenericFields);
        }
    }

    private void analyzeClassFields(String className, int action) {
        try {
            for (Field field : Class.forName(className).getDeclaredFields()) {
                if (isPrivateAndNonPrimitive(field)) {
                    oneToAnyConditions(className, field, action);
                    manyToAnyConditions(className, field, action);
                }
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new DatabaseGenerateException(DatabaseGenerateException.CLASS_NOT_FOUND + className);
        }
    }

    private boolean isPrivateAndNonPrimitive(Field field) {
        return Modifier.isPrivate(field.getModifiers()) && !field.getType().isPrimitive();
    }

    private void oneToAnyConditions(String className, Field field, int action) throws ClassNotFoundException {
        Class<?> fieldTypeClass = field.getType();
        if (LitePalAttr.getInstance().getClassNames().contains(fieldTypeClass.getName())) {
            Field[] reverseFields = Class.forName(fieldTypeClass.getName()).getDeclaredFields();
            boolean reverseAssociations = false;
            for (int i = 0; i < reverseFields.length; i++) {
                Field reverseField = reverseFields[i];
                if (!Modifier.isStatic(reverseField.getModifiers())) {
                    Class<?> reverseFieldTypeClass = reverseField.getType();
                    if (className.equals(reverseFieldTypeClass.getName())) {
                        if (action == 1) {
                            addIntoAssociationModelCollection(className, fieldTypeClass.getName(), fieldTypeClass.getName(), 1);
                        } else if (action == 2) {
                            addIntoAssociationInfoCollection(className, fieldTypeClass.getName(), fieldTypeClass.getName(), field, reverseField, 1);
                        }
                        reverseAssociations = true;
                    } else if (isCollection(reverseFieldTypeClass)) {
                        if (className.equals(getGenericTypeName(reverseField))) {
                            if (action == 1) {
                                addIntoAssociationModelCollection(className, fieldTypeClass.getName(), className, 2);
                            } else if (action == 2) {
                                addIntoAssociationInfoCollection(className, fieldTypeClass.getName(), className, field, reverseField, 2);
                            }
                            reverseAssociations = true;
                        }
                    }
                }
            }
            if (reverseAssociations) {
                return;
            }
            if (action == 1) {
                addIntoAssociationModelCollection(className, fieldTypeClass.getName(), fieldTypeClass.getName(), 1);
            } else if (action == 2) {
                addIntoAssociationInfoCollection(className, fieldTypeClass.getName(), fieldTypeClass.getName(), field, (Field) null, 1);
            }
        }
    }

    private void manyToAnyConditions(String className, Field field, int action) throws ClassNotFoundException {
        if (isCollection(field.getType())) {
            String genericTypeName = getGenericTypeName(field);
            if (LitePalAttr.getInstance().getClassNames().contains(genericTypeName)) {
                Field[] reverseFields = Class.forName(genericTypeName).getDeclaredFields();
                boolean reverseAssociations = false;
                for (int i = 0; i < reverseFields.length; i++) {
                    Field reverseField = reverseFields[i];
                    if (!Modifier.isStatic(reverseField.getModifiers())) {
                        Class<?> reverseFieldTypeClass = reverseField.getType();
                        if (className.equals(reverseFieldTypeClass.getName())) {
                            if (action == 1) {
                                addIntoAssociationModelCollection(className, genericTypeName, genericTypeName, 2);
                            } else if (action == 2) {
                                addIntoAssociationInfoCollection(className, genericTypeName, genericTypeName, field, reverseField, 2);
                            }
                            reverseAssociations = true;
                        } else if (isCollection(reverseFieldTypeClass)) {
                            if (className.equals(getGenericTypeName(reverseField))) {
                                if (action == 1) {
                                    addIntoAssociationModelCollection(className, genericTypeName, (String) null, 3);
                                } else if (action == 2) {
                                    addIntoAssociationInfoCollection(className, genericTypeName, (String) null, field, reverseField, 3);
                                }
                                reverseAssociations = true;
                            }
                        }
                    }
                }
                if (reverseAssociations) {
                    return;
                }
                if (action == 1) {
                    addIntoAssociationModelCollection(className, genericTypeName, genericTypeName, 2);
                } else if (action == 2) {
                    addIntoAssociationInfoCollection(className, genericTypeName, genericTypeName, field, (Field) null, 2);
                }
            } else if (BaseUtility.isGenericTypeSupported(genericTypeName) && action == 1) {
                Column annotation = (Column) field.getAnnotation(Column.class);
                if (annotation == null || !annotation.ignore()) {
                    GenericModel genericModel = new GenericModel();
                    genericModel.setTableName(DBUtility.getGenericTableName(className, field.getName()));
                    genericModel.setValueColumnName(DBUtility.convertToValidColumnName(field.getName()));
                    genericModel.setValueColumnType(getColumnType(genericTypeName));
                    genericModel.setValueIdColumnName(DBUtility.getGenericValueIdColumnName(className));
                    this.mGenericModels.add(genericModel);
                }
            }
        }
    }

    private void addIntoAssociationModelCollection(String className, String associatedClassName, String classHoldsForeignKey, int associationType) {
        AssociationsModel associationModel = new AssociationsModel();
        associationModel.setTableName(DBUtility.getTableNameByClassName(className));
        associationModel.setAssociatedTableName(DBUtility.getTableNameByClassName(associatedClassName));
        associationModel.setTableHoldsForeignKey(DBUtility.getTableNameByClassName(classHoldsForeignKey));
        associationModel.setAssociationType(associationType);
        this.mAssociationModels.add(associationModel);
    }

    private void addIntoAssociationInfoCollection(String selfClassName, String associatedClassName, String classHoldsForeignKey, Field associateOtherModelFromSelf, Field associateSelfFromOtherModel, int associationType) {
        AssociationsInfo associationInfo = new AssociationsInfo();
        associationInfo.setSelfClassName(selfClassName);
        associationInfo.setAssociatedClassName(associatedClassName);
        associationInfo.setClassHoldsForeignKey(classHoldsForeignKey);
        associationInfo.setAssociateOtherModelFromSelf(associateOtherModelFromSelf);
        associationInfo.setAssociateSelfFromOtherModel(associateSelfFromOtherModel);
        associationInfo.setAssociationType(associationType);
        this.mAssociationInfos.add(associationInfo);
    }

    /* access modifiers changed from: protected */
    public String getGenericTypeName(Field field) {
        Class<?> genericTypeClass = getGenericTypeClass(field);
        if (genericTypeClass != null) {
            return genericTypeClass.getName();
        }
        return null;
    }

    private ColumnModel convertFieldToColumnModel(Field field) {
        String columnType = getColumnType(field.getType().getName());
        boolean nullable = true;
        boolean unique = false;
        String defaultValue = "";
        Column annotation = (Column) field.getAnnotation(Column.class);
        if (annotation != null) {
            nullable = annotation.nullable();
            unique = annotation.unique();
            defaultValue = annotation.defaultValue();
        }
        ColumnModel columnModel = new ColumnModel();
        columnModel.setColumnName(DBUtility.convertToValidColumnName(field.getName()));
        columnModel.setColumnType(columnType);
        columnModel.setIsNullable(nullable);
        columnModel.setIsUnique(unique);
        columnModel.setDefaultValue(defaultValue);
        return columnModel;
    }
}

package com.alibaba.fastjson.util;

import com.alibaba.fastjson.annotation.JSONField;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class FieldInfo implements Comparable<FieldInfo> {
    public final Class<?> declaringClass;
    public final Field field;
    public final boolean fieldAccess;
    private final JSONField fieldAnnotation;
    public final Class<?> fieldClass;
    public final boolean fieldTransient;
    public final Type fieldType;
    public final String format;
    public final boolean getOnly;
    public final boolean isEnum;
    public final boolean jsonDirect;
    public final String label;
    public final Method method;
    private final JSONField methodAnnotation;
    public final String name;
    public final char[] name_chars;
    private int ordinal = 0;
    public final int parserFeatures;
    public final int serialzeFeatures;

    public FieldInfo(String name2, Class<?> declaringClass2, Class<?> fieldClass2, Type fieldType2, Field field2, int ordinal2, int serialzeFeatures2, int parserFeatures2) {
        boolean z;
        this.name = name2;
        this.declaringClass = declaringClass2;
        this.fieldClass = fieldClass2;
        this.fieldType = fieldType2;
        this.method = null;
        this.field = field2;
        this.ordinal = ordinal2;
        this.serialzeFeatures = serialzeFeatures2;
        this.parserFeatures = 0;
        this.isEnum = fieldClass2.isEnum();
        if (field2 != null) {
            int modifiers = field2.getModifiers();
            if ((modifiers & 1) != 0 || this.method == null) {
                z = true;
            } else {
                z = false;
            }
            this.fieldAccess = z;
            this.fieldTransient = Modifier.isTransient(modifiers);
        } else {
            this.fieldTransient = false;
            this.fieldAccess = false;
        }
        this.name_chars = genFieldNameChars();
        if (field2 != null) {
            TypeUtils.setAccessible(field2);
        }
        this.label = "";
        this.fieldAnnotation = null;
        this.methodAnnotation = null;
        this.getOnly = false;
        this.jsonDirect = false;
        this.format = null;
    }

    public FieldInfo(String name2, Method method2, Field field2, Class<?> clazz, Type type, int ordinal2, int serialzeFeatures2, int parserFeatures2, JSONField fieldAnnotation2, JSONField methodAnnotation2, String label2) {
        boolean jsonDirect2;
        Class<?> fieldClass2;
        Type fieldType2;
        Type genericFieldType;
        if (field2 != null) {
            String fieldName = field2.getName();
            if (fieldName.equals(name2)) {
                name2 = fieldName;
            }
        }
        this.name = name2;
        this.method = method2;
        this.field = field2;
        this.ordinal = ordinal2;
        this.serialzeFeatures = serialzeFeatures2;
        this.parserFeatures = parserFeatures2;
        this.fieldAnnotation = fieldAnnotation2;
        this.methodAnnotation = methodAnnotation2;
        if (field2 != null) {
            int modifiers = field2.getModifiers();
            this.fieldAccess = (modifiers & 1) != 0 || method2 == null;
            this.fieldTransient = Modifier.isTransient(modifiers);
        } else {
            this.fieldAccess = false;
            this.fieldTransient = false;
        }
        if (label2 == null || label2.length() <= 0) {
            this.label = "";
        } else {
            this.label = label2;
        }
        String format2 = null;
        JSONField annotation = getAnnotation();
        if (annotation != null) {
            format2 = annotation.format();
            format2 = format2.trim().length() == 0 ? null : format2;
            jsonDirect2 = annotation.jsonDirect();
        } else {
            jsonDirect2 = false;
        }
        this.format = format2;
        this.name_chars = genFieldNameChars();
        if (method2 != null) {
            TypeUtils.setAccessible(method2);
        }
        if (field2 != null) {
            TypeUtils.setAccessible(field2);
        }
        boolean getOnly2 = false;
        if (method2 != null) {
            Class<?>[] types = method2.getParameterTypes();
            if (types.length == 1) {
                fieldClass2 = types[0];
                fieldType2 = method2.getGenericParameterTypes()[0];
            } else {
                fieldClass2 = method2.getReturnType();
                fieldType2 = method2.getGenericReturnType();
                getOnly2 = true;
            }
            this.declaringClass = method2.getDeclaringClass();
        } else {
            fieldClass2 = field2.getType();
            fieldType2 = field2.getGenericType();
            this.declaringClass = field2.getDeclaringClass();
            getOnly2 = Modifier.isFinal(field2.getModifiers());
        }
        this.getOnly = getOnly2;
        this.jsonDirect = jsonDirect2 && fieldClass2 == String.class;
        if (clazz == null || fieldClass2 != Object.class || !(fieldType2 instanceof TypeVariable) || (genericFieldType = getInheritGenericType(clazz, (TypeVariable) fieldType2)) == null) {
            Type genericFieldType2 = fieldType2;
            if (!(fieldType2 instanceof Class)) {
                genericFieldType2 = getFieldType(clazz, type == null ? clazz : type, fieldType2);
                if (genericFieldType2 != fieldType2) {
                    if (genericFieldType2 instanceof ParameterizedType) {
                        fieldClass2 = TypeUtils.getClass(genericFieldType2);
                    } else if (genericFieldType2 instanceof Class) {
                        fieldClass2 = TypeUtils.getClass(genericFieldType2);
                    }
                }
            }
            this.fieldType = genericFieldType2;
            this.fieldClass = fieldClass2;
            this.isEnum = fieldClass2.isEnum();
            return;
        }
        this.fieldClass = TypeUtils.getClass(genericFieldType);
        this.fieldType = genericFieldType;
        this.isEnum = fieldClass2.isEnum();
    }

    /* access modifiers changed from: protected */
    public char[] genFieldNameChars() {
        int nameLen = this.name.length();
        char[] name_chars2 = new char[(nameLen + 3)];
        this.name.getChars(0, this.name.length(), name_chars2, 1);
        name_chars2[0] = '\"';
        name_chars2[nameLen + 1] = '\"';
        name_chars2[nameLen + 2] = ':';
        return name_chars2;
    }

    public <T extends Annotation> T getAnnation(Class<T> annotationClass) {
        if (annotationClass == JSONField.class) {
            return getAnnotation();
        }
        T annotatition = null;
        if (this.method != null) {
            annotatition = this.method.getAnnotation(annotationClass);
        }
        if (annotatition != null || this.field == null) {
            return annotatition;
        }
        return this.field.getAnnotation(annotationClass);
    }

    public static Type getFieldType(Class<?> clazz, Type type, Type fieldType2) {
        if (clazz == null || type == null) {
            return fieldType2;
        }
        if (fieldType2 instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) fieldType2).getGenericComponentType();
            Type componentTypeX = getFieldType(clazz, type, componentType);
            if (componentType != componentTypeX) {
                return Array.newInstance(TypeUtils.getClass(componentTypeX), 0).getClass();
            }
            return fieldType2;
        } else if (!TypeUtils.isGenericParamType(type)) {
            return fieldType2;
        } else {
            if (fieldType2 instanceof TypeVariable) {
                ParameterizedType paramType = (ParameterizedType) TypeUtils.getGenericParamType(type);
                TypeVariable<?> typeVar = (TypeVariable) fieldType2;
                TypeVariable<?>[] typeVariables = TypeUtils.getClass(paramType).getTypeParameters();
                for (int i = 0; i < typeVariables.length; i++) {
                    if (typeVariables[i].getName().equals(typeVar.getName())) {
                        return paramType.getActualTypeArguments()[i];
                    }
                }
            }
            if (fieldType2 instanceof ParameterizedType) {
                ParameterizedType parameterizedFieldType = (ParameterizedType) fieldType2;
                Type[] arguments = parameterizedFieldType.getActualTypeArguments();
                boolean changed = false;
                TypeVariable<?>[] typeVariables2 = null;
                Type[] actualTypes = null;
                ParameterizedType paramType2 = null;
                if (type instanceof ParameterizedType) {
                    paramType2 = (ParameterizedType) type;
                    typeVariables2 = clazz.getTypeParameters();
                } else if (clazz.getGenericSuperclass() instanceof ParameterizedType) {
                    paramType2 = (ParameterizedType) clazz.getGenericSuperclass();
                    typeVariables2 = clazz.getSuperclass().getTypeParameters();
                }
                for (int i2 = 0; i2 < arguments.length && paramType2 != null; i2++) {
                    Type feildTypeArguement = arguments[i2];
                    if (feildTypeArguement instanceof TypeVariable) {
                        TypeVariable<?> typeVar2 = (TypeVariable) feildTypeArguement;
                        for (int j = 0; j < typeVariables2.length; j++) {
                            if (typeVariables2[j].getName().equals(typeVar2.getName())) {
                                if (actualTypes == null) {
                                    actualTypes = paramType2.getActualTypeArguments();
                                }
                                arguments[i2] = actualTypes[j];
                                changed = true;
                            }
                        }
                    }
                }
                if (changed) {
                    return new ParameterizedTypeImpl(arguments, parameterizedFieldType.getOwnerType(), parameterizedFieldType.getRawType());
                }
            }
            return fieldType2;
        }
    }

    public static Type getInheritGenericType(Class<?> clazz, TypeVariable<?> typeVariable) {
        Type type;
        GenericDeclaration gd = typeVariable.getGenericDeclaration();
        do {
            type = clazz.getGenericSuperclass();
            if (type == null) {
                return null;
            }
            if (type instanceof ParameterizedType) {
                ParameterizedType ptype = (ParameterizedType) type;
                if (ptype.getRawType() == gd) {
                    TypeVariable<?>[] tvs = gd.getTypeParameters();
                    Type[] types = ptype.getActualTypeArguments();
                    for (int i = 0; i < tvs.length; i++) {
                        if (tvs[i] == typeVariable) {
                            return types[i];
                        }
                    }
                    return null;
                }
            }
            clazz = TypeUtils.getClass(type);
        } while (type != null);
        return null;
    }

    public String toString() {
        return this.name;
    }

    public Member getMember() {
        if (this.method != null) {
            return this.method;
        }
        return this.field;
    }

    /* access modifiers changed from: protected */
    public Class<?> getDeclaredClass() {
        if (this.method != null) {
            return this.method.getDeclaringClass();
        }
        if (this.field != null) {
            return this.field.getDeclaringClass();
        }
        return null;
    }

    public int compareTo(FieldInfo o) {
        boolean isSampeType;
        boolean oSameType;
        if (this.ordinal < o.ordinal) {
            return -1;
        }
        if (this.ordinal > o.ordinal) {
            return 1;
        }
        int result = this.name.compareTo(o.name);
        if (result != 0) {
            return result;
        }
        Class<?> thisDeclaringClass = getDeclaredClass();
        Class<?> otherDeclaringClass = o.getDeclaredClass();
        if (!(thisDeclaringClass == null || otherDeclaringClass == null || thisDeclaringClass == otherDeclaringClass)) {
            if (thisDeclaringClass.isAssignableFrom(otherDeclaringClass)) {
                return -1;
            }
            if (otherDeclaringClass.isAssignableFrom(thisDeclaringClass)) {
                return 1;
            }
        }
        if (this.field == null || this.field.getType() != this.fieldClass) {
            isSampeType = false;
        } else {
            isSampeType = true;
        }
        if (o.field == null || o.field.getType() != o.fieldClass) {
            oSameType = false;
        } else {
            oSameType = true;
        }
        if (isSampeType && !oSameType) {
            return 1;
        }
        if (oSameType && !isSampeType) {
            return -1;
        }
        if (o.fieldClass.isPrimitive() && !this.fieldClass.isPrimitive()) {
            return 1;
        }
        if (this.fieldClass.isPrimitive() && !o.fieldClass.isPrimitive()) {
            return -1;
        }
        if (o.fieldClass.getName().startsWith("java.") && !this.fieldClass.getName().startsWith("java.")) {
            return 1;
        }
        if (!this.fieldClass.getName().startsWith("java.") || o.fieldClass.getName().startsWith("java.")) {
            return this.fieldClass.getName().compareTo(o.fieldClass.getName());
        }
        return -1;
    }

    public JSONField getAnnotation() {
        if (this.fieldAnnotation != null) {
            return this.fieldAnnotation;
        }
        return this.methodAnnotation;
    }

    public String getFormat() {
        return this.format;
    }

    public Object get(Object javaObject) throws IllegalAccessException, InvocationTargetException {
        if (this.method != null) {
            return this.method.invoke(javaObject, new Object[0]);
        }
        return this.field.get(javaObject);
    }

    public void set(Object javaObject, Object value) throws IllegalAccessException, InvocationTargetException {
        if (this.method != null) {
            this.method.invoke(javaObject, new Object[]{value});
            return;
        }
        this.field.set(javaObject, value);
    }

    public void setAccessible() throws SecurityException {
        if (this.method != null) {
            TypeUtils.setAccessible(this.method);
        } else {
            TypeUtils.setAccessible(this.field);
        }
    }
}

package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.util.FieldInfo;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public class FieldSerializer implements Comparable<FieldSerializer> {
    private final String double_quoted_fieldPrefix;
    protected int features;
    protected BeanContext fieldContext;
    public final FieldInfo fieldInfo;
    private String format;
    private RuntimeSerializerInfo runtimeInfo;
    private String single_quoted_fieldPrefix;
    private String un_quoted_fieldPrefix;
    protected boolean writeEnumUsingName = false;
    protected boolean writeEnumUsingToString = false;
    protected final boolean writeNull;

    public FieldSerializer(Class<?> beanType, FieldInfo fieldInfo2) {
        this.fieldInfo = fieldInfo2;
        this.fieldContext = new BeanContext(beanType, fieldInfo2);
        fieldInfo2.setAccessible();
        this.double_quoted_fieldPrefix = '\"' + fieldInfo2.name + "\":";
        boolean writeNull2 = false;
        JSONField annotation = fieldInfo2.getAnnotation();
        if (annotation != null) {
            SerializerFeature[] serialzeFeatures = annotation.serialzeFeatures();
            int length = serialzeFeatures.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                } else if (serialzeFeatures[i] == SerializerFeature.WriteMapNullValue) {
                    writeNull2 = true;
                    break;
                } else {
                    i++;
                }
            }
            this.format = annotation.format();
            if (this.format.trim().length() == 0) {
                this.format = null;
            }
            for (SerializerFeature feature : annotation.serialzeFeatures()) {
                if (feature == SerializerFeature.WriteEnumUsingToString) {
                    this.writeEnumUsingToString = true;
                } else if (feature == SerializerFeature.WriteEnumUsingName) {
                    this.writeEnumUsingName = true;
                }
            }
            this.features = SerializerFeature.of(annotation.serialzeFeatures());
        }
        this.writeNull = writeNull2;
    }

    public void writePrefix(JSONSerializer serializer) throws IOException {
        SerializeWriter out = serializer.out;
        if (!out.quoteFieldNames) {
            if (this.un_quoted_fieldPrefix == null) {
                this.un_quoted_fieldPrefix = this.fieldInfo.name + ":";
            }
            out.write(this.un_quoted_fieldPrefix);
        } else if (out.useSingleQuotes) {
            if (this.single_quoted_fieldPrefix == null) {
                this.single_quoted_fieldPrefix = '\'' + this.fieldInfo.name + "':";
            }
            out.write(this.single_quoted_fieldPrefix);
        } else {
            out.write(this.double_quoted_fieldPrefix);
        }
    }

    public Object getPropertyValue(Object object) throws InvocationTargetException, IllegalAccessException {
        return this.fieldInfo.get(object);
    }

    public int compareTo(FieldSerializer o) {
        return this.fieldInfo.compareTo(o.fieldInfo);
    }

    public void writeValue(JSONSerializer serializer, Object propertyValue) throws Exception {
        ObjectSerializer valueSerializer;
        Class<?> runtimeFieldClass;
        ObjectSerializer fieldSerializer;
        if (this.runtimeInfo == null) {
            if (propertyValue == null) {
                runtimeFieldClass = this.fieldInfo.fieldClass;
            } else {
                runtimeFieldClass = propertyValue.getClass();
            }
            JSONField fieldAnnotation = this.fieldInfo.getAnnotation();
            if (fieldAnnotation == null || fieldAnnotation.serializeUsing() == Void.class) {
                fieldSerializer = serializer.getObjectWriter(runtimeFieldClass);
            } else {
                fieldSerializer = (ObjectSerializer) fieldAnnotation.serializeUsing().newInstance();
            }
            this.runtimeInfo = new RuntimeSerializerInfo(fieldSerializer, runtimeFieldClass);
        }
        RuntimeSerializerInfo runtimeInfo2 = this.runtimeInfo;
        int fieldFeatures = this.fieldInfo.serialzeFeatures;
        if (propertyValue == null) {
            Class<?> runtimeFieldClass2 = runtimeInfo2.runtimeFieldClass;
            SerializeWriter out = serializer.out;
            if (Number.class.isAssignableFrom(runtimeFieldClass2)) {
                out.writeNull(this.features, SerializerFeature.WriteNullNumberAsZero.mask);
            } else if (String.class == runtimeFieldClass2) {
                out.writeNull(this.features, SerializerFeature.WriteNullStringAsEmpty.mask);
            } else if (Boolean.class == runtimeFieldClass2) {
                out.writeNull(this.features, SerializerFeature.WriteNullBooleanAsFalse.mask);
            } else if (Collection.class.isAssignableFrom(runtimeFieldClass2)) {
                out.writeNull(this.features, SerializerFeature.WriteNullListAsEmpty.mask);
            } else {
                ObjectSerializer fieldSerializer2 = runtimeInfo2.fieldSerializer;
                if (!out.isEnabled(SerializerFeature.WriteMapNullValue) || !(fieldSerializer2 instanceof JavaBeanSerializer)) {
                    fieldSerializer2.write(serializer, (Object) null, this.fieldInfo.name, this.fieldInfo.fieldType, fieldFeatures);
                    return;
                }
                out.writeNull();
            }
        } else {
            if (this.fieldInfo.isEnum) {
                if (this.writeEnumUsingName) {
                    serializer.out.writeString(((Enum) propertyValue).name());
                    return;
                } else if (this.writeEnumUsingToString) {
                    serializer.out.writeString(((Enum) propertyValue).toString());
                    return;
                }
            }
            Class<?> valueClass = propertyValue.getClass();
            if (valueClass == runtimeInfo2.runtimeFieldClass) {
                valueSerializer = runtimeInfo2.fieldSerializer;
            } else {
                valueSerializer = serializer.getObjectWriter(valueClass);
            }
            if (this.format == null) {
                valueSerializer.write(serializer, propertyValue, this.fieldInfo.name, this.fieldInfo.fieldType, fieldFeatures);
            } else if (valueSerializer instanceof ContextObjectSerializer) {
                ((ContextObjectSerializer) valueSerializer).write(serializer, propertyValue, this.fieldContext);
            } else {
                serializer.writeWithFormat(propertyValue, this.format);
            }
        }
    }

    static class RuntimeSerializerInfo {
        ObjectSerializer fieldSerializer;
        Class<?> runtimeFieldClass;

        public RuntimeSerializerInfo(ObjectSerializer fieldSerializer2, Class<?> runtimeFieldClass2) {
            this.fieldSerializer = fieldSerializer2;
            this.runtimeFieldClass = runtimeFieldClass2;
        }
    }
}

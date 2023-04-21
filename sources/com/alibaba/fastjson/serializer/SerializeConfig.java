package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.deserializer.Jdk8DateCodec;
import com.alibaba.fastjson.parser.deserializer.OptionalCodec;
import com.alibaba.fastjson.support.springfox.SwaggerJsonSerializer;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.IdentityHashMap;
import com.alibaba.fastjson.util.ServiceLoader;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.File;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import javax.xml.datatype.XMLGregorianCalendar;

public class SerializeConfig {
    private static boolean awtError = false;
    public static final SerializeConfig globalInstance = new SerializeConfig();
    private static boolean jdk8Error = false;
    private static boolean oracleJdbcError = false;
    private static boolean springfoxError = false;
    private boolean asm;
    private ASMSerializerFactory asmFactory;
    public PropertyNamingStrategy propertyNamingStrategy;
    private final IdentityHashMap<Type, ObjectSerializer> serializers;
    protected String typeKey;

    public String getTypeKey() {
        return this.typeKey;
    }

    public void setTypeKey(String typeKey2) {
        this.typeKey = typeKey2;
    }

    private final JavaBeanSerializer createASMSerializer(SerializeBeanInfo beanInfo) throws Exception {
        JavaBeanSerializer serializer = this.asmFactory.createJavaBeanSerializer(beanInfo);
        for (FieldSerializer fieldDeser : serializer.sortedGetters) {
            Class<?> fieldClass = fieldDeser.fieldInfo.fieldClass;
            if (fieldClass.isEnum() && !(getObjectWriter(fieldClass) instanceof EnumSerializer)) {
                serializer.writeDirect = false;
            }
        }
        return serializer;
    }

    private final ObjectSerializer createJavaBeanSerializer(Class<?> clazz) {
        SerializeBeanInfo beanInfo = TypeUtils.buildBeanInfo(clazz, (Map<String, String>) null, this.propertyNamingStrategy);
        if (beanInfo.fields.length != 0 || !Iterable.class.isAssignableFrom(clazz)) {
            return createJavaBeanSerializer(beanInfo);
        }
        return MiscCodec.instance;
    }

    public ObjectSerializer createJavaBeanSerializer(SerializeBeanInfo beanInfo) {
        int i = 0;
        JSONType jsonType = beanInfo.jsonType;
        if (jsonType != null) {
            Class<?> serializerClass = jsonType.serializer();
            if (serializerClass != Void.class) {
                try {
                    Object seralizer = serializerClass.newInstance();
                    if (seralizer instanceof ObjectSerializer) {
                        return (ObjectSerializer) seralizer;
                    }
                } catch (Throwable th) {
                }
            }
            if (!jsonType.asm()) {
                this.asm = false;
            }
        }
        Class<?> clazz = beanInfo.beanType;
        if (!Modifier.isPublic(beanInfo.beanType.getModifiers())) {
            return new JavaBeanSerializer(beanInfo);
        }
        boolean asm2 = this.asm;
        if ((asm2 && this.asmFactory.classLoader.isExternalClass(clazz)) || clazz == Serializable.class || clazz == Object.class) {
            asm2 = false;
        }
        if (asm2 && !ASMUtils.checkName(clazz.getName())) {
            asm2 = false;
        }
        if (asm2) {
            FieldInfo[] fieldInfoArr = beanInfo.fields;
            int length = fieldInfoArr.length;
            while (true) {
                if (i >= length) {
                    break;
                }
                JSONField annotation = fieldInfoArr[i].getAnnotation();
                if (annotation != null && (!ASMUtils.checkName(annotation.name()) || annotation.format().length() != 0 || annotation.jsonDirect() || annotation.serializeUsing() != Void.class)) {
                    asm2 = false;
                } else {
                    i++;
                }
            }
        }
        if (asm2) {
            try {
                ObjectSerializer asmSerializer = createASMSerializer(beanInfo);
                if (asmSerializer != null) {
                    return asmSerializer;
                }
            } catch (ClassCastException | ClassFormatError e) {
            } catch (Throwable e2) {
                throw new JSONException("create asm serializer error, class " + clazz, e2);
            }
        }
        return new JavaBeanSerializer(beanInfo);
    }

    public boolean isAsmEnable() {
        return this.asm;
    }

    public void setAsmEnable(boolean asmEnable) {
        if (!ASMUtils.IS_ANDROID) {
            this.asm = asmEnable;
        }
    }

    public static SerializeConfig getGlobalInstance() {
        return globalInstance;
    }

    public SerializeConfig() {
        this(1024);
    }

    public SerializeConfig(int tableSize) {
        this.asm = !ASMUtils.IS_ANDROID;
        this.typeKey = JSON.DEFAULT_TYPE_KEY;
        this.serializers = new IdentityHashMap<>(1024);
        try {
            if (this.asm) {
                this.asmFactory = new ASMSerializerFactory();
            }
        } catch (NoClassDefFoundError e) {
            this.asm = false;
        } catch (ExceptionInInitializerError e2) {
            this.asm = false;
        }
        put(Boolean.class, BooleanCodec.instance);
        put(Character.class, CharacterCodec.instance);
        put(Byte.class, IntegerCodec.instance);
        put(Short.class, IntegerCodec.instance);
        put(Integer.class, IntegerCodec.instance);
        put(Long.class, LongCodec.instance);
        put(Float.class, FloatCodec.instance);
        put(Double.class, DoubleSerializer.instance);
        put(BigDecimal.class, BigDecimalCodec.instance);
        put(BigInteger.class, BigIntegerCodec.instance);
        put(String.class, StringCodec.instance);
        put(byte[].class, PrimitiveArraySerializer.instance);
        put(short[].class, PrimitiveArraySerializer.instance);
        put(int[].class, PrimitiveArraySerializer.instance);
        put(long[].class, PrimitiveArraySerializer.instance);
        put(float[].class, PrimitiveArraySerializer.instance);
        put(double[].class, PrimitiveArraySerializer.instance);
        put(boolean[].class, PrimitiveArraySerializer.instance);
        put(char[].class, PrimitiveArraySerializer.instance);
        put(Object[].class, ObjectArrayCodec.instance);
        put(Class.class, MiscCodec.instance);
        put(SimpleDateFormat.class, MiscCodec.instance);
        put(Currency.class, new MiscCodec());
        put(TimeZone.class, MiscCodec.instance);
        put(InetAddress.class, MiscCodec.instance);
        put(Inet4Address.class, MiscCodec.instance);
        put(Inet6Address.class, MiscCodec.instance);
        put(InetSocketAddress.class, MiscCodec.instance);
        put(File.class, MiscCodec.instance);
        put(Appendable.class, AppendableSerializer.instance);
        put(StringBuffer.class, AppendableSerializer.instance);
        put(StringBuilder.class, AppendableSerializer.instance);
        put(Charset.class, ToStringSerializer.instance);
        put(Pattern.class, ToStringSerializer.instance);
        put(Locale.class, ToStringSerializer.instance);
        put(URI.class, ToStringSerializer.instance);
        put(URL.class, ToStringSerializer.instance);
        put(UUID.class, ToStringSerializer.instance);
        put(AtomicBoolean.class, AtomicCodec.instance);
        put(AtomicInteger.class, AtomicCodec.instance);
        put(AtomicLong.class, AtomicCodec.instance);
        put(AtomicReference.class, ReferenceCodec.instance);
        put(AtomicIntegerArray.class, AtomicCodec.instance);
        put(AtomicLongArray.class, AtomicCodec.instance);
        put(WeakReference.class, ReferenceCodec.instance);
        put(SoftReference.class, ReferenceCodec.instance);
    }

    public void addFilter(Class<?> clazz, SerializeFilter filter) {
        ObjectSerializer serializer = getObjectWriter(clazz);
        if (serializer instanceof SerializeFilterable) {
            SerializeFilterable filterable = (SerializeFilterable) serializer;
            if (this == globalInstance || filterable != MapSerializer.instance) {
                filterable.addFilter(filter);
                return;
            }
            MapSerializer newMapSer = new MapSerializer();
            put(clazz, newMapSer);
            newMapSer.addFilter(filter);
        }
    }

    public void config(Class<?> clazz, SerializerFeature feature, boolean value) {
        ObjectSerializer serializer = getObjectWriter(clazz, false);
        if (serializer == null) {
            SerializeBeanInfo beanInfo = TypeUtils.buildBeanInfo(clazz, (Map<String, String>) null, this.propertyNamingStrategy);
            if (value) {
                beanInfo.features |= feature.mask;
            } else {
                beanInfo.features &= feature.mask ^ -1;
            }
            put(clazz, createJavaBeanSerializer(beanInfo));
        } else if (serializer instanceof JavaBeanSerializer) {
            SerializeBeanInfo beanInfo2 = ((JavaBeanSerializer) serializer).beanInfo;
            int originalFeaturs = beanInfo2.features;
            if (value) {
                beanInfo2.features |= feature.mask;
            } else {
                beanInfo2.features &= feature.mask ^ -1;
            }
            if (originalFeaturs != beanInfo2.features && serializer.getClass() != JavaBeanSerializer.class) {
                put(clazz, createJavaBeanSerializer(beanInfo2));
            }
        }
    }

    public ObjectSerializer getObjectWriter(Class<?> clazz) {
        return getObjectWriter(clazz, true);
    }

    private ObjectSerializer getObjectWriter(Class<?> clazz, boolean create) {
        ClassLoader classLoader;
        ObjectSerializer writer = this.serializers.get(clazz);
        if (writer == null) {
            try {
                for (AutowiredObjectSerializer next : ServiceLoader.load(AutowiredObjectSerializer.class, Thread.currentThread().getContextClassLoader())) {
                    if (next instanceof AutowiredObjectSerializer) {
                        AutowiredObjectSerializer autowired = next;
                        for (Type forType : autowired.getAutowiredFor()) {
                            put(forType, autowired);
                        }
                    }
                }
            } catch (ClassCastException e) {
            }
            writer = this.serializers.get(clazz);
        }
        if (writer == null && (classLoader = JSON.class.getClassLoader()) != Thread.currentThread().getContextClassLoader()) {
            try {
                for (AutowiredObjectSerializer next2 : ServiceLoader.load(AutowiredObjectSerializer.class, classLoader)) {
                    if (next2 instanceof AutowiredObjectSerializer) {
                        AutowiredObjectSerializer autowired2 = next2;
                        for (Type forType2 : autowired2.getAutowiredFor()) {
                            put(forType2, autowired2);
                        }
                    }
                }
            } catch (ClassCastException e2) {
            }
            writer = this.serializers.get(clazz);
        }
        if (writer == null) {
            if (Map.class.isAssignableFrom(clazz)) {
                put(clazz, MapSerializer.instance);
            } else if (List.class.isAssignableFrom(clazz)) {
                put(clazz, ListSerializer.instance);
            } else if (Collection.class.isAssignableFrom(clazz)) {
                put(clazz, CollectionCodec.instance);
            } else if (Date.class.isAssignableFrom(clazz)) {
                put(clazz, DateCodec.instance);
            } else if (JSONAware.class.isAssignableFrom(clazz)) {
                put(clazz, JSONAwareSerializer.instance);
            } else if (JSONSerializable.class.isAssignableFrom(clazz)) {
                put(clazz, JSONSerializableSerializer.instance);
            } else if (JSONStreamAware.class.isAssignableFrom(clazz)) {
                put(clazz, MiscCodec.instance);
            } else if (clazz.isEnum() || (clazz.getSuperclass() != null && clazz.getSuperclass().isEnum())) {
                put(clazz, EnumSerializer.instance);
            } else if (clazz.isArray()) {
                Class<?> componentType = clazz.getComponentType();
                put(clazz, new ArraySerializer(componentType, getObjectWriter(componentType)));
            } else if (Throwable.class.isAssignableFrom(clazz)) {
                SerializeBeanInfo beanInfo = TypeUtils.buildBeanInfo(clazz, (Map<String, String>) null, this.propertyNamingStrategy);
                beanInfo.features |= SerializerFeature.WriteClassName.mask;
                put(clazz, new JavaBeanSerializer(beanInfo));
            } else if (TimeZone.class.isAssignableFrom(clazz)) {
                put(clazz, MiscCodec.instance);
            } else if (Appendable.class.isAssignableFrom(clazz)) {
                put(clazz, AppendableSerializer.instance);
            } else if (Charset.class.isAssignableFrom(clazz)) {
                put(clazz, ToStringSerializer.instance);
            } else if (Enumeration.class.isAssignableFrom(clazz)) {
                put(clazz, EnumerationSerializer.instance);
            } else if (Calendar.class.isAssignableFrom(clazz) || XMLGregorianCalendar.class.isAssignableFrom(clazz)) {
                put(clazz, CalendarCodec.instance);
            } else if (Clob.class.isAssignableFrom(clazz)) {
                put(clazz, ClobSeriliazer.instance);
            } else if (TypeUtils.isPath(clazz)) {
                put(clazz, ToStringSerializer.instance);
            } else if (Iterator.class.isAssignableFrom(clazz)) {
                put(clazz, MiscCodec.instance);
            } else {
                String className = clazz.getName();
                if (!className.startsWith("java.awt.") || !AwtCodec.support(clazz)) {
                    if (!jdk8Error && (className.startsWith("java.time.") || className.startsWith("java.util.Optional"))) {
                        try {
                            put(Class.forName("java.time.LocalDateTime"), Jdk8DateCodec.instance);
                            put(Class.forName("java.time.LocalDate"), Jdk8DateCodec.instance);
                            put(Class.forName("java.time.LocalTime"), Jdk8DateCodec.instance);
                            put(Class.forName("java.time.ZonedDateTime"), Jdk8DateCodec.instance);
                            put(Class.forName("java.time.OffsetDateTime"), Jdk8DateCodec.instance);
                            put(Class.forName("java.time.OffsetTime"), Jdk8DateCodec.instance);
                            put(Class.forName("java.time.ZoneOffset"), Jdk8DateCodec.instance);
                            put(Class.forName("java.time.ZoneRegion"), Jdk8DateCodec.instance);
                            put(Class.forName("java.time.Period"), Jdk8DateCodec.instance);
                            put(Class.forName("java.time.Duration"), Jdk8DateCodec.instance);
                            put(Class.forName("java.time.Instant"), Jdk8DateCodec.instance);
                            put(Class.forName("java.util.Optional"), OptionalCodec.instance);
                            put(Class.forName("java.util.OptionalDouble"), OptionalCodec.instance);
                            put(Class.forName("java.util.OptionalInt"), OptionalCodec.instance);
                            put(Class.forName("java.util.OptionalLong"), OptionalCodec.instance);
                            ObjectSerializer writer2 = this.serializers.get(clazz);
                            if (writer2 != null) {
                                return writer2;
                            }
                        } catch (Throwable th) {
                            jdk8Error = true;
                        }
                    }
                    if (!oracleJdbcError && className.startsWith("oracle.sql.")) {
                        try {
                            put(Class.forName("oracle.sql.DATE"), DateCodec.instance);
                            put(Class.forName("oracle.sql.TIMESTAMP"), DateCodec.instance);
                            ObjectSerializer writer3 = this.serializers.get(clazz);
                            if (writer3 != null) {
                                return writer3;
                            }
                        } catch (Throwable th2) {
                            oracleJdbcError = true;
                        }
                    }
                    if (!springfoxError && className.equals("springfox.documentation.spring.web.json.Json")) {
                        try {
                            put(Class.forName("springfox.documentation.spring.web.json.Json"), SwaggerJsonSerializer.instance);
                            ObjectSerializer writer4 = this.serializers.get(clazz);
                            if (writer4 != null) {
                                return writer4;
                            }
                        } catch (ClassNotFoundException e3) {
                            springfoxError = true;
                        }
                    }
                    boolean isCglibProxy = false;
                    boolean isJavassistProxy = false;
                    Class<?>[] interfaces = clazz.getInterfaces();
                    int length = interfaces.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        String interfaceName = interfaces[i].getName();
                        if (interfaceName.equals("net.sf.cglib.proxy.Factory") || interfaceName.equals("org.springframework.cglib.proxy.Factory")) {
                            isCglibProxy = true;
                        } else if (interfaceName.equals("javassist.util.proxy.ProxyObject") || interfaceName.equals("org.apache.ibatis.javassist.util.proxy.ProxyObject")) {
                            isJavassistProxy = true;
                        } else {
                            i++;
                        }
                    }
                    isCglibProxy = true;
                    if (isCglibProxy || isJavassistProxy) {
                        ObjectSerializer superWriter = getObjectWriter(clazz.getSuperclass());
                        putInternal(clazz, superWriter);
                        return superWriter;
                    } else if (create) {
                        putInternal(clazz, createJavaBeanSerializer(clazz));
                    }
                } else {
                    if (!awtError) {
                        try {
                            put(Class.forName("java.awt.Color"), AwtCodec.instance);
                            put(Class.forName("java.awt.Font"), AwtCodec.instance);
                            put(Class.forName("java.awt.Point"), AwtCodec.instance);
                            put(Class.forName("java.awt.Rectangle"), AwtCodec.instance);
                        } catch (Throwable th3) {
                            awtError = true;
                        }
                    }
                    return AwtCodec.instance;
                }
            }
            writer = this.serializers.get(clazz);
        }
        return writer;
    }

    public final ObjectSerializer get(Type key) {
        return this.serializers.get(key);
    }

    public boolean put(Type type, ObjectSerializer value) {
        boolean isEnum = false;
        if (type instanceof Class) {
            isEnum = ((Class) type).isEnum();
        }
        if (isEnum) {
        }
        return putInternal(type, value);
    }

    /* access modifiers changed from: protected */
    public boolean putInternal(Type key, ObjectSerializer value) {
        return this.serializers.put(key, value);
    }
}

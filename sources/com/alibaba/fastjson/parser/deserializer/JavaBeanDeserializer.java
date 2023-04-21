package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JavaBeanDeserializer implements ObjectDeserializer {
    public final JavaBeanInfo beanInfo;
    protected final Class<?> clazz;
    private final FieldDeserializer[] fieldDeserializers;
    protected final FieldDeserializer[] sortedFieldDeserializers;

    public JavaBeanDeserializer(ParserConfig config, Class<?> clazz2) {
        this(config, clazz2, clazz2);
    }

    public JavaBeanDeserializer(ParserConfig config, Class<?> clazz2, Type type) {
        this(config, JavaBeanInfo.build(clazz2, type, config.propertyNamingStrategy));
    }

    public JavaBeanDeserializer(ParserConfig config, JavaBeanInfo beanInfo2) {
        this.clazz = beanInfo2.clazz;
        this.beanInfo = beanInfo2;
        this.sortedFieldDeserializers = new FieldDeserializer[beanInfo2.sortedFields.length];
        int size = beanInfo2.sortedFields.length;
        for (int i = 0; i < size; i++) {
            this.sortedFieldDeserializers[i] = config.createFieldDeserializer(config, beanInfo2, beanInfo2.sortedFields[i]);
        }
        this.fieldDeserializers = new FieldDeserializer[beanInfo2.fields.length];
        int size2 = beanInfo2.fields.length;
        for (int i2 = 0; i2 < size2; i2++) {
            this.fieldDeserializers[i2] = getFieldDeserializer(beanInfo2.fields[i2].name);
        }
    }

    public FieldDeserializer getFieldDeserializer(String key) {
        if (key == null) {
            return null;
        }
        int low = 0;
        int high = this.sortedFieldDeserializers.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = this.sortedFieldDeserializers[mid].fieldInfo.name.compareTo(key);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp <= 0) {
                return this.sortedFieldDeserializers[mid];
            } else {
                high = mid - 1;
            }
        }
        return null;
    }

    public Object createInstance(DefaultJSONParser parser, Type type) {
        String parentName;
        Object object;
        String clsName;
        if ((type instanceof Class) && this.clazz.isInterface()) {
            return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{(Class) type}, new JSONObject());
        } else if (this.beanInfo.defaultConstructor == null) {
            return null;
        } else {
            try {
                Constructor<?> constructor = this.beanInfo.defaultConstructor;
                if (this.beanInfo.defaultConstructorParameterSize == 0) {
                    object = constructor.newInstance(new Object[0]);
                } else {
                    ParseContext context = parser.getContext();
                    parentName = context.object.getClass().getName();
                    String typeName = "";
                    if (type instanceof Class) {
                        typeName = ((Class) type).getName();
                    }
                    if (parentName.length() != typeName.lastIndexOf(36) - 1) {
                        char[] typeChars = typeName.toCharArray();
                        StringBuilder clsNameBuilder = new StringBuilder();
                        clsNameBuilder.append(parentName).append("$");
                        HashMap hashMap = new HashMap();
                        hashMap.put(parentName, context.object);
                        for (int i = parentName.length() + 1; i <= typeName.lastIndexOf(36); i++) {
                            char thisChar = typeChars[i];
                            if (thisChar == '$') {
                                clsName = clsNameBuilder.toString();
                                Object outter = hashMap.get(parentName);
                                Class<?> clazz2 = Class.forName(parentName);
                                if (outter != null) {
                                    Constructor<?> innerClsConstructor = Class.forName(clsName).getDeclaredConstructor(new Class[]{clazz2});
                                    if (!innerClsConstructor.isAccessible()) {
                                        innerClsConstructor.setAccessible(true);
                                    }
                                    hashMap.put(clsName, innerClsConstructor.newInstance(new Object[]{outter}));
                                    parentName = clsName;
                                }
                            }
                            clsNameBuilder.append(thisChar);
                        }
                        object = constructor.newInstance(new Object[]{hashMap.get(parentName)});
                    } else {
                        object = constructor.newInstance(new Object[]{context.object});
                    }
                }
                if (parser != null && parser.lexer.isEnabled(Feature.InitStringFieldAsEmpty)) {
                    FieldInfo[] fieldInfoArr = this.beanInfo.fields;
                    int length = fieldInfoArr.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        FieldInfo fieldInfo = fieldInfoArr[i2];
                        if (fieldInfo.fieldClass == String.class) {
                            try {
                                fieldInfo.set(object, "");
                            } catch (Exception e) {
                                throw new JSONException("create instance error, class " + this.clazz.getName(), e);
                            }
                        }
                    }
                }
                return object;
            } catch (ClassNotFoundException e2) {
                throw new JSONException("unable to find class " + parentName);
            } catch (NoSuchMethodException e3) {
                throw new RuntimeException(e3);
            } catch (InvocationTargetException e4) {
                throw new RuntimeException("can not instantiate " + clsName);
            } catch (IllegalAccessException e5) {
                throw new RuntimeException(e5);
            } catch (InstantiationException e6) {
                throw new RuntimeException(e6);
            } catch (Exception e7) {
                throw new JSONException("create instance error, class " + this.clazz.getName(), e7);
            }
        }
    }

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        return deserialze(parser, type, fieldName, 0);
    }

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName, int features) {
        return deserialze(parser, type, fieldName, (Object) null, features);
    }

    public <T> T deserialzeArrayMapping(DefaultJSONParser parser, Type type, Object fieldName, Object object) {
        Enum value;
        JSONLexer lexer = parser.lexer;
        if (lexer.token() != 14) {
            throw new JSONException("error");
        }
        T createInstance = createInstance(parser, type);
        int i = 0;
        int size = this.sortedFieldDeserializers.length;
        while (i < size) {
            char seperator = i == size + -1 ? ']' : ',';
            FieldDeserializer fieldDeser = this.sortedFieldDeserializers[i];
            Class<?> fieldClass = fieldDeser.fieldInfo.fieldClass;
            if (fieldClass == Integer.TYPE) {
                fieldDeser.setValue((Object) createInstance, lexer.scanInt(seperator));
            } else if (fieldClass == String.class) {
                fieldDeser.setValue((Object) createInstance, lexer.scanString(seperator));
            } else if (fieldClass == Long.TYPE) {
                fieldDeser.setValue((Object) createInstance, lexer.scanLong(seperator));
            } else if (fieldClass.isEnum()) {
                char ch = lexer.getCurrent();
                if (ch == '\"' || ch == 'n') {
                    value = lexer.scanEnum(fieldClass, parser.getSymbolTable(), seperator);
                } else if (ch < '0' || ch > '9') {
                    value = scanEnum(lexer, seperator);
                } else {
                    value = ((EnumDeserializer) ((DefaultFieldDeserializer) fieldDeser).getFieldValueDeserilizer(parser.getConfig())).valueOf(lexer.scanInt(seperator));
                }
                fieldDeser.setValue((Object) createInstance, (Object) value);
            } else if (fieldClass == Boolean.TYPE) {
                fieldDeser.setValue((Object) createInstance, lexer.scanBoolean(seperator));
            } else if (fieldClass == Float.TYPE) {
                fieldDeser.setValue((Object) createInstance, (Object) Float.valueOf(lexer.scanFloat(seperator)));
            } else if (fieldClass == Double.TYPE) {
                fieldDeser.setValue((Object) createInstance, (Object) Double.valueOf(lexer.scanDouble(seperator)));
            } else if (fieldClass == Date.class && lexer.getCurrent() == '1') {
                fieldDeser.setValue((Object) createInstance, (Object) new Date(lexer.scanLong(seperator)));
            } else {
                lexer.nextToken(14);
                fieldDeser.setValue((Object) createInstance, parser.parseObject(fieldDeser.fieldInfo.fieldType));
                check(lexer, seperator == ']' ? 15 : 16);
            }
            i++;
        }
        lexer.nextToken(16);
        return createInstance;
    }

    /* access modifiers changed from: protected */
    public void check(JSONLexer lexer, int token) {
        if (lexer.token() != token) {
            throw new JSONException("syntax error");
        }
    }

    /* access modifiers changed from: protected */
    public Enum<?> scanEnum(JSONLexer lexer, char seperator) {
        throw new JSONException("illegal enum. " + lexer.info());
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:224:0x0444, code lost:
        r14 = r47.getConfig();
        r16 = getSeeAlso(r14, r46.beanInfo, r43);
        r44 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:225:0x0456, code lost:
        if (r16 != null) goto L_0x047e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:226:0x0458, code lost:
        r44 = com.alibaba.fastjson.util.TypeUtils.loadClass(r43, r14.getDefaultClassLoader());
        r19 = com.alibaba.fastjson.util.TypeUtils.getClass(r48);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:227:0x0466, code lost:
        if (r19 == null) goto L_0x0474;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:228:0x0468, code lost:
        if (r44 == null) goto L_0x0497;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:230:0x0472, code lost:
        if (r19.isAssignableFrom(r44) == false) goto L_0x0497;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:231:0x0474, code lost:
        r16 = r47.getConfig().getDeserializer((java.lang.reflect.Type) r44);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:232:0x047e, code lost:
        r12 = r16.deserialze(r47, r44, r49);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:233:0x048a, code lost:
        if (r13 == null) goto L_0x0490;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:234:0x048c, code lost:
        r13.object = r50;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:235:0x0490, code lost:
        r47.setContext(r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:238:0x049f, code lost:
        throw new com.alibaba.fastjson.JSONException("type not match");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:289:0x0590, code lost:
        throw new com.alibaba.fastjson.JSONException("syntax error, unexpect token " + com.alibaba.fastjson.parser.JSONToken.name(r30.token()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:329:0x0640, code lost:
        r17 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:332:0x0665, code lost:
        throw new com.alibaba.fastjson.JSONException("create instance error, " + r46.beanInfo.creatorConstructor.toGenericString(), r17);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:338:0x067c, code lost:
        r17 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:341:0x06a1, code lost:
        throw new com.alibaba.fastjson.JSONException("create factory method error, " + r46.beanInfo.factoryMethod.toString(), r17);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:348:0x06b8, code lost:
        r17 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:351:0x06c2, code lost:
        throw new com.alibaba.fastjson.JSONException("build object error", r17);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:397:?, code lost:
        return r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0110, code lost:
        r4 = th;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:15:0x0042, B:320:0x061f, B:336:0x0670, B:343:0x06a3] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0081 A[Catch:{ Exception -> 0x06b8, Exception -> 0x067c, Exception -> 0x0640, all -> 0x0110 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r47, java.lang.reflect.Type r48, java.lang.Object r49, java.lang.Object r50, int r51) {
        /*
            r46 = this;
            java.lang.Class<com.alibaba.fastjson.JSON> r4 = com.alibaba.fastjson.JSON.class
            r0 = r48
            if (r0 == r4) goto L_0x000c
            java.lang.Class<com.alibaba.fastjson.JSONObject> r4 = com.alibaba.fastjson.JSONObject.class
            r0 = r48
            if (r0 != r4) goto L_0x0011
        L_0x000c:
            java.lang.Object r12 = r47.parse()
        L_0x0010:
            return r12
        L_0x0011:
            r0 = r47
            com.alibaba.fastjson.parser.JSONLexer r0 = r0.lexer
            r30 = r0
            com.alibaba.fastjson.parser.JSONLexerBase r30 = (com.alibaba.fastjson.parser.JSONLexerBase) r30
            int r42 = r30.token()
            r4 = 8
            r0 = r42
            if (r0 != r4) goto L_0x002c
            r4 = 16
            r0 = r30
            r0.nextToken(r4)
            r12 = 0
            goto L_0x0010
        L_0x002c:
            com.alibaba.fastjson.parser.ParseContext r15 = r47.getContext()
            if (r50 == 0) goto L_0x0036
            if (r15 == 0) goto L_0x0036
            com.alibaba.fastjson.parser.ParseContext r15 = r15.parent
        L_0x0036:
            r13 = 0
            r9 = 0
            r4 = 13
            r0 = r42
            if (r0 != r4) goto L_0x0059
            r4 = 16
            r0 = r30
            r0.nextToken(r4)     // Catch:{ all -> 0x0110 }
            if (r50 != 0) goto L_0x004b
            java.lang.Object r50 = r46.createInstance((com.alibaba.fastjson.parser.DefaultJSONParser) r47, (java.lang.reflect.Type) r48)     // Catch:{ all -> 0x0110 }
        L_0x004b:
            if (r13 == 0) goto L_0x0051
            r0 = r50
            r13.object = r0
        L_0x0051:
            r0 = r47
            r0.setContext(r15)
            r12 = r50
            goto L_0x0010
        L_0x0059:
            r4 = 14
            r0 = r42
            if (r0 != r4) goto L_0x0094
            com.alibaba.fastjson.parser.Feature r4 = com.alibaba.fastjson.parser.Feature.SupportArrayToBean     // Catch:{ all -> 0x0110 }
            int r0 = r4.mask     // Catch:{ all -> 0x0110 }
            r31 = r0
            r0 = r46
            com.alibaba.fastjson.util.JavaBeanInfo r4 = r0.beanInfo     // Catch:{ all -> 0x0110 }
            int r4 = r4.parserFeatures     // Catch:{ all -> 0x0110 }
            r4 = r4 & r31
            if (r4 != 0) goto L_0x007d
            com.alibaba.fastjson.parser.Feature r4 = com.alibaba.fastjson.parser.Feature.SupportArrayToBean     // Catch:{ all -> 0x0110 }
            r0 = r30
            boolean r4 = r0.isEnabled((com.alibaba.fastjson.parser.Feature) r4)     // Catch:{ all -> 0x0110 }
            if (r4 != 0) goto L_0x007d
            r4 = r51 & r31
            if (r4 == 0) goto L_0x0091
        L_0x007d:
            r29 = 1
        L_0x007f:
            if (r29 == 0) goto L_0x0094
            java.lang.Object r12 = r46.deserialzeArrayMapping(r47, r48, r49, r50)     // Catch:{ all -> 0x0110 }
            if (r13 == 0) goto L_0x008b
            r0 = r50
            r13.object = r0
        L_0x008b:
            r0 = r47
            r0.setContext(r15)
            goto L_0x0010
        L_0x0091:
            r29 = 0
            goto L_0x007f
        L_0x0094:
            r4 = 12
            r0 = r42
            if (r0 == r4) goto L_0x011d
            r4 = 16
            r0 = r42
            if (r0 == r4) goto L_0x011d
            boolean r4 = r30.isBlankInput()     // Catch:{ all -> 0x0110 }
            if (r4 == 0) goto L_0x00b4
            r12 = 0
            if (r13 == 0) goto L_0x00ad
            r0 = r50
            r13.object = r0
        L_0x00ad:
            r0 = r47
            r0.setContext(r15)
            goto L_0x0010
        L_0x00b4:
            r4 = 4
            r0 = r42
            if (r0 != r4) goto L_0x00d4
            java.lang.String r41 = r30.stringVal()     // Catch:{ all -> 0x0110 }
            int r4 = r41.length()     // Catch:{ all -> 0x0110 }
            if (r4 != 0) goto L_0x00d4
            r30.nextToken()     // Catch:{ all -> 0x0110 }
            r12 = 0
            if (r13 == 0) goto L_0x00cd
            r0 = r50
            r13.object = r0
        L_0x00cd:
            r0 = r47
            r0.setContext(r15)
            goto L_0x0010
        L_0x00d4:
            java.lang.StringBuffer r4 = new java.lang.StringBuffer     // Catch:{ all -> 0x0110 }
            r4.<init>()     // Catch:{ all -> 0x0110 }
            java.lang.String r5 = "syntax error, expect {, actual "
            java.lang.StringBuffer r4 = r4.append(r5)     // Catch:{ all -> 0x0110 }
            java.lang.String r5 = r30.tokenName()     // Catch:{ all -> 0x0110 }
            java.lang.StringBuffer r4 = r4.append(r5)     // Catch:{ all -> 0x0110 }
            java.lang.String r5 = ", pos "
            java.lang.StringBuffer r4 = r4.append(r5)     // Catch:{ all -> 0x0110 }
            int r5 = r30.pos()     // Catch:{ all -> 0x0110 }
            java.lang.StringBuffer r10 = r4.append(r5)     // Catch:{ all -> 0x0110 }
            r0 = r49
            boolean r4 = r0 instanceof java.lang.String     // Catch:{ all -> 0x0110 }
            if (r4 == 0) goto L_0x0106
            java.lang.String r4 = ", fieldName "
            java.lang.StringBuffer r4 = r10.append(r4)     // Catch:{ all -> 0x0110 }
            r0 = r49
            r4.append(r0)     // Catch:{ all -> 0x0110 }
        L_0x0106:
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0110 }
            java.lang.String r5 = r10.toString()     // Catch:{ all -> 0x0110 }
            r4.<init>(r5)     // Catch:{ all -> 0x0110 }
            throw r4     // Catch:{ all -> 0x0110 }
        L_0x0110:
            r4 = move-exception
        L_0x0111:
            if (r13 == 0) goto L_0x0117
            r0 = r50
            r13.object = r0
        L_0x0117:
            r0 = r47
            r0.setContext(r15)
            throw r4
        L_0x011d:
            r0 = r47
            int r4 = r0.resolveStatus     // Catch:{ all -> 0x0110 }
            r5 = 2
            if (r4 != r5) goto L_0x0129
            r4 = 0
            r0 = r47
            r0.resolveStatus = r4     // Catch:{ all -> 0x0110 }
        L_0x0129:
            r22 = 0
            r27 = r9
        L_0x012d:
            r6 = 0
            r21 = 0
            r23 = 0
            r20 = 0
            r0 = r46
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer[] r4 = r0.sortedFieldDeserializers     // Catch:{ all -> 0x034d }
            int r4 = r4.length     // Catch:{ all -> 0x034d }
            r0 = r22
            if (r0 >= r4) goto L_0x014f
            r0 = r46
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer[] r4 = r0.sortedFieldDeserializers     // Catch:{ all -> 0x034d }
            r21 = r4[r22]     // Catch:{ all -> 0x034d }
            r0 = r21
            com.alibaba.fastjson.util.FieldInfo r0 = r0.fieldInfo     // Catch:{ all -> 0x034d }
            r23 = r0
            r0 = r23
            java.lang.Class<?> r0 = r0.fieldClass     // Catch:{ all -> 0x034d }
            r20 = r0
        L_0x014f:
            r33 = 0
            r45 = 0
            r26 = 0
            if (r21 == 0) goto L_0x017f
            r0 = r23
            char[] r0 = r0.name_chars     // Catch:{ all -> 0x034d }
            r34 = r0
            java.lang.Class r4 = java.lang.Integer.TYPE     // Catch:{ all -> 0x034d }
            r0 = r20
            if (r0 == r4) goto L_0x0169
            java.lang.Class<java.lang.Integer> r4 = java.lang.Integer.class
            r0 = r20
            if (r0 != r4) goto L_0x01d2
        L_0x0169:
            r0 = r30
            r1 = r34
            int r4 = r0.scanFieldInt(r1)     // Catch:{ all -> 0x034d }
            java.lang.Integer r26 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x034d }
            r0 = r30
            int r4 = r0.matchStat     // Catch:{ all -> 0x034d }
            if (r4 <= 0) goto L_0x01c3
            r33 = 1
            r45 = 1
        L_0x017f:
            if (r33 != 0) goto L_0x04a8
            r0 = r47
            com.alibaba.fastjson.parser.SymbolTable r4 = r0.symbolTable     // Catch:{ all -> 0x034d }
            r0 = r30
            java.lang.String r6 = r0.scanSymbol(r4)     // Catch:{ all -> 0x034d }
            if (r6 != 0) goto L_0x0311
            int r42 = r30.token()     // Catch:{ all -> 0x034d }
            r4 = 13
            r0 = r42
            if (r0 != r4) goto L_0x02fd
            r4 = 16
            r0 = r30
            r0.nextToken(r4)     // Catch:{ all -> 0x034d }
            r9 = r27
        L_0x01a0:
            if (r50 != 0) goto L_0x0629
            if (r9 != 0) goto L_0x0591
            java.lang.Object r50 = r46.createInstance((com.alibaba.fastjson.parser.DefaultJSONParser) r47, (java.lang.reflect.Type) r48)     // Catch:{ all -> 0x0110 }
            if (r13 != 0) goto L_0x01b4
            r0 = r47
            r1 = r50
            r2 = r49
            com.alibaba.fastjson.parser.ParseContext r13 = r0.setContext(r15, r1, r2)     // Catch:{ all -> 0x0110 }
        L_0x01b4:
            if (r13 == 0) goto L_0x01ba
            r0 = r50
            r13.object = r0
        L_0x01ba:
            r0 = r47
            r0.setContext(r15)
            r12 = r50
            goto L_0x0010
        L_0x01c3:
            r0 = r30
            int r4 = r0.matchStat     // Catch:{ all -> 0x034d }
            r5 = -2
            if (r4 != r5) goto L_0x017f
            r9 = r27
        L_0x01cc:
            int r22 = r22 + 1
            r27 = r9
            goto L_0x012d
        L_0x01d2:
            java.lang.Class r4 = java.lang.Long.TYPE     // Catch:{ all -> 0x034d }
            r0 = r20
            if (r0 == r4) goto L_0x01de
            java.lang.Class<java.lang.Long> r4 = java.lang.Long.class
            r0 = r20
            if (r0 != r4) goto L_0x01ff
        L_0x01de:
            r0 = r30
            r1 = r34
            long r4 = r0.scanFieldLong(r1)     // Catch:{ all -> 0x034d }
            java.lang.Long r26 = java.lang.Long.valueOf(r4)     // Catch:{ all -> 0x034d }
            r0 = r30
            int r4 = r0.matchStat     // Catch:{ all -> 0x034d }
            if (r4 <= 0) goto L_0x01f5
            r33 = 1
            r45 = 1
            goto L_0x017f
        L_0x01f5:
            r0 = r30
            int r4 = r0.matchStat     // Catch:{ all -> 0x034d }
            r5 = -2
            if (r4 != r5) goto L_0x017f
            r9 = r27
            goto L_0x01cc
        L_0x01ff:
            java.lang.Class<java.lang.String> r4 = java.lang.String.class
            r0 = r20
            if (r0 != r4) goto L_0x0223
            r0 = r30
            r1 = r34
            java.lang.String r26 = r0.scanFieldString(r1)     // Catch:{ all -> 0x034d }
            r0 = r30
            int r4 = r0.matchStat     // Catch:{ all -> 0x034d }
            if (r4 <= 0) goto L_0x0219
            r33 = 1
            r45 = 1
            goto L_0x017f
        L_0x0219:
            r0 = r30
            int r4 = r0.matchStat     // Catch:{ all -> 0x034d }
            r5 = -2
            if (r4 != r5) goto L_0x017f
            r9 = r27
            goto L_0x01cc
        L_0x0223:
            java.lang.Class r4 = java.lang.Boolean.TYPE     // Catch:{ all -> 0x034d }
            r0 = r20
            if (r0 == r4) goto L_0x022f
            java.lang.Class<java.lang.Boolean> r4 = java.lang.Boolean.class
            r0 = r20
            if (r0 != r4) goto L_0x0252
        L_0x022f:
            r0 = r30
            r1 = r34
            boolean r4 = r0.scanFieldBoolean(r1)     // Catch:{ all -> 0x034d }
            java.lang.Boolean r26 = java.lang.Boolean.valueOf(r4)     // Catch:{ all -> 0x034d }
            r0 = r30
            int r4 = r0.matchStat     // Catch:{ all -> 0x034d }
            if (r4 <= 0) goto L_0x0247
            r33 = 1
            r45 = 1
            goto L_0x017f
        L_0x0247:
            r0 = r30
            int r4 = r0.matchStat     // Catch:{ all -> 0x034d }
            r5 = -2
            if (r4 != r5) goto L_0x017f
            r9 = r27
            goto L_0x01cc
        L_0x0252:
            java.lang.Class r4 = java.lang.Float.TYPE     // Catch:{ all -> 0x034d }
            r0 = r20
            if (r0 == r4) goto L_0x025e
            java.lang.Class<java.lang.Float> r4 = java.lang.Float.class
            r0 = r20
            if (r0 != r4) goto L_0x0281
        L_0x025e:
            r0 = r30
            r1 = r34
            float r4 = r0.scanFieldFloat(r1)     // Catch:{ all -> 0x034d }
            java.lang.Float r26 = java.lang.Float.valueOf(r4)     // Catch:{ all -> 0x034d }
            r0 = r30
            int r4 = r0.matchStat     // Catch:{ all -> 0x034d }
            if (r4 <= 0) goto L_0x0276
            r33 = 1
            r45 = 1
            goto L_0x017f
        L_0x0276:
            r0 = r30
            int r4 = r0.matchStat     // Catch:{ all -> 0x034d }
            r5 = -2
            if (r4 != r5) goto L_0x017f
            r9 = r27
            goto L_0x01cc
        L_0x0281:
            java.lang.Class r4 = java.lang.Double.TYPE     // Catch:{ all -> 0x034d }
            r0 = r20
            if (r0 == r4) goto L_0x028d
            java.lang.Class<java.lang.Double> r4 = java.lang.Double.class
            r0 = r20
            if (r0 != r4) goto L_0x02b0
        L_0x028d:
            r0 = r30
            r1 = r34
            double r4 = r0.scanFieldDouble(r1)     // Catch:{ all -> 0x034d }
            java.lang.Double r26 = java.lang.Double.valueOf(r4)     // Catch:{ all -> 0x034d }
            r0 = r30
            int r4 = r0.matchStat     // Catch:{ all -> 0x034d }
            if (r4 <= 0) goto L_0x02a5
            r33 = 1
            r45 = 1
            goto L_0x017f
        L_0x02a5:
            r0 = r30
            int r4 = r0.matchStat     // Catch:{ all -> 0x034d }
            r5 = -2
            if (r4 != r5) goto L_0x017f
            r9 = r27
            goto L_0x01cc
        L_0x02b0:
            boolean r4 = r20.isEnum()     // Catch:{ all -> 0x034d }
            if (r4 == 0) goto L_0x02ef
            com.alibaba.fastjson.parser.ParserConfig r4 = r47.getConfig()     // Catch:{ all -> 0x034d }
            r0 = r20
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r4 = r4.getDeserializer((java.lang.reflect.Type) r0)     // Catch:{ all -> 0x034d }
            boolean r4 = r4 instanceof com.alibaba.fastjson.parser.deserializer.EnumDeserializer     // Catch:{ all -> 0x034d }
            if (r4 == 0) goto L_0x02ef
            r0 = r47
            com.alibaba.fastjson.parser.SymbolTable r4 = r0.symbolTable     // Catch:{ all -> 0x034d }
            r0 = r30
            r1 = r34
            java.lang.String r18 = r0.scanFieldSymbol(r1, r4)     // Catch:{ all -> 0x034d }
            r0 = r30
            int r4 = r0.matchStat     // Catch:{ all -> 0x034d }
            if (r4 <= 0) goto L_0x02e4
            r33 = 1
            r45 = 1
            r0 = r20
            r1 = r18
            java.lang.Enum r26 = java.lang.Enum.valueOf(r0, r1)     // Catch:{ all -> 0x034d }
            goto L_0x017f
        L_0x02e4:
            r0 = r30
            int r4 = r0.matchStat     // Catch:{ all -> 0x034d }
            r5 = -2
            if (r4 != r5) goto L_0x017f
            r9 = r27
            goto L_0x01cc
        L_0x02ef:
            r0 = r30
            r1 = r34
            boolean r4 = r0.matchField(r1)     // Catch:{ all -> 0x034d }
            if (r4 == 0) goto L_0x06cb
            r33 = 1
            goto L_0x017f
        L_0x02fd:
            r4 = 16
            r0 = r42
            if (r0 != r4) goto L_0x0311
            com.alibaba.fastjson.parser.Feature r4 = com.alibaba.fastjson.parser.Feature.AllowArbitraryCommas     // Catch:{ all -> 0x034d }
            r0 = r30
            boolean r4 = r0.isEnabled((com.alibaba.fastjson.parser.Feature) r4)     // Catch:{ all -> 0x034d }
            if (r4 == 0) goto L_0x0311
            r9 = r27
            goto L_0x01cc
        L_0x0311:
            java.lang.String r4 = "$ref"
            if (r4 != r6) goto L_0x040b
            r4 = 4
            r0 = r30
            r0.nextTokenWithColon(r4)     // Catch:{ all -> 0x034d }
            int r42 = r30.token()     // Catch:{ all -> 0x034d }
            r4 = 4
            r0 = r42
            if (r0 != r4) goto L_0x03cf
            java.lang.String r38 = r30.stringVal()     // Catch:{ all -> 0x034d }
            java.lang.String r4 = "@"
            r0 = r38
            boolean r4 = r4.equals(r0)     // Catch:{ all -> 0x034d }
            if (r4 == 0) goto L_0x0352
            java.lang.Object r0 = r15.object     // Catch:{ all -> 0x034d }
            r50 = r0
        L_0x0336:
            r4 = 13
            r0 = r30
            r0.nextToken(r4)     // Catch:{ all -> 0x034d }
            int r4 = r30.token()     // Catch:{ all -> 0x034d }
            r5 = 13
            if (r4 == r5) goto L_0x03ec
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x034d }
            java.lang.String r5 = "illegal ref"
            r4.<init>(r5)     // Catch:{ all -> 0x034d }
            throw r4     // Catch:{ all -> 0x034d }
        L_0x034d:
            r4 = move-exception
            r9 = r27
            goto L_0x0111
        L_0x0352:
            java.lang.String r4 = ".."
            r0 = r38
            boolean r4 = r4.equals(r0)     // Catch:{ all -> 0x034d }
            if (r4 == 0) goto L_0x0381
            com.alibaba.fastjson.parser.ParseContext r0 = r15.parent     // Catch:{ all -> 0x034d }
            r37 = r0
            r0 = r37
            java.lang.Object r4 = r0.object     // Catch:{ all -> 0x034d }
            if (r4 == 0) goto L_0x036d
            r0 = r37
            java.lang.Object r0 = r0.object     // Catch:{ all -> 0x034d }
            r50 = r0
            goto L_0x0336
        L_0x036d:
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r4 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x034d }
            r0 = r37
            r1 = r38
            r4.<init>(r0, r1)     // Catch:{ all -> 0x034d }
            r0 = r47
            r0.addResolveTask(r4)     // Catch:{ all -> 0x034d }
            r4 = 1
            r0 = r47
            r0.resolveStatus = r4     // Catch:{ all -> 0x034d }
            goto L_0x0336
        L_0x0381:
            java.lang.String r4 = "$"
            r0 = r38
            boolean r4 = r4.equals(r0)     // Catch:{ all -> 0x034d }
            if (r4 == 0) goto L_0x03bc
            r39 = r15
        L_0x038d:
            r0 = r39
            com.alibaba.fastjson.parser.ParseContext r4 = r0.parent     // Catch:{ all -> 0x034d }
            if (r4 == 0) goto L_0x039a
            r0 = r39
            com.alibaba.fastjson.parser.ParseContext r0 = r0.parent     // Catch:{ all -> 0x034d }
            r39 = r0
            goto L_0x038d
        L_0x039a:
            r0 = r39
            java.lang.Object r4 = r0.object     // Catch:{ all -> 0x034d }
            if (r4 == 0) goto L_0x03a7
            r0 = r39
            java.lang.Object r0 = r0.object     // Catch:{ all -> 0x034d }
            r50 = r0
            goto L_0x0336
        L_0x03a7:
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r4 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x034d }
            r0 = r39
            r1 = r38
            r4.<init>(r0, r1)     // Catch:{ all -> 0x034d }
            r0 = r47
            r0.addResolveTask(r4)     // Catch:{ all -> 0x034d }
            r4 = 1
            r0 = r47
            r0.resolveStatus = r4     // Catch:{ all -> 0x034d }
            goto L_0x0336
        L_0x03bc:
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r4 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x034d }
            r0 = r38
            r4.<init>(r15, r0)     // Catch:{ all -> 0x034d }
            r0 = r47
            r0.addResolveTask(r4)     // Catch:{ all -> 0x034d }
            r4 = 1
            r0 = r47
            r0.resolveStatus = r4     // Catch:{ all -> 0x034d }
            goto L_0x0336
        L_0x03cf:
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x034d }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x034d }
            r5.<init>()     // Catch:{ all -> 0x034d }
            java.lang.String r7 = "illegal ref, "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ all -> 0x034d }
            java.lang.String r7 = com.alibaba.fastjson.parser.JSONToken.name(r42)     // Catch:{ all -> 0x034d }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ all -> 0x034d }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x034d }
            r4.<init>(r5)     // Catch:{ all -> 0x034d }
            throw r4     // Catch:{ all -> 0x034d }
        L_0x03ec:
            r4 = 16
            r0 = r30
            r0.nextToken(r4)     // Catch:{ all -> 0x034d }
            r0 = r47
            r1 = r50
            r2 = r49
            r0.setContext(r15, r1, r2)     // Catch:{ all -> 0x034d }
            if (r13 == 0) goto L_0x0402
            r0 = r50
            r13.object = r0
        L_0x0402:
            r0 = r47
            r0.setContext(r15)
            r12 = r50
            goto L_0x0010
        L_0x040b:
            java.lang.String r4 = com.alibaba.fastjson.JSON.DEFAULT_TYPE_KEY     // Catch:{ all -> 0x034d }
            if (r4 != r6) goto L_0x04a8
            r4 = 4
            r0 = r30
            r0.nextTokenWithColon(r4)     // Catch:{ all -> 0x034d }
            int r4 = r30.token()     // Catch:{ all -> 0x034d }
            r5 = 4
            if (r4 != r5) goto L_0x04a0
            java.lang.String r43 = r30.stringVal()     // Catch:{ all -> 0x034d }
            r4 = 16
            r0 = r30
            r0.nextToken(r4)     // Catch:{ all -> 0x034d }
            r0 = r46
            com.alibaba.fastjson.util.JavaBeanInfo r4 = r0.beanInfo     // Catch:{ all -> 0x034d }
            java.lang.String r4 = r4.typeName     // Catch:{ all -> 0x034d }
            r0 = r43
            boolean r4 = r0.equals(r4)     // Catch:{ all -> 0x034d }
            if (r4 == 0) goto L_0x0444
            int r4 = r30.token()     // Catch:{ all -> 0x034d }
            r5 = 13
            if (r4 != r5) goto L_0x06cb
            r30.nextToken()     // Catch:{ all -> 0x034d }
            r9 = r27
            goto L_0x01a0
        L_0x0444:
            com.alibaba.fastjson.parser.ParserConfig r14 = r47.getConfig()     // Catch:{ all -> 0x034d }
            r0 = r46
            com.alibaba.fastjson.util.JavaBeanInfo r4 = r0.beanInfo     // Catch:{ all -> 0x034d }
            r0 = r46
            r1 = r43
            com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer r16 = r0.getSeeAlso(r14, r4, r1)     // Catch:{ all -> 0x034d }
            r44 = 0
            if (r16 != 0) goto L_0x047e
            java.lang.ClassLoader r4 = r14.getDefaultClassLoader()     // Catch:{ all -> 0x034d }
            r0 = r43
            java.lang.Class r44 = com.alibaba.fastjson.util.TypeUtils.loadClass(r0, r4)     // Catch:{ all -> 0x034d }
            java.lang.Class r19 = com.alibaba.fastjson.util.TypeUtils.getClass(r48)     // Catch:{ all -> 0x034d }
            if (r19 == 0) goto L_0x0474
            if (r44 == 0) goto L_0x0497
            r0 = r19
            r1 = r44
            boolean r4 = r0.isAssignableFrom(r1)     // Catch:{ all -> 0x034d }
            if (r4 == 0) goto L_0x0497
        L_0x0474:
            com.alibaba.fastjson.parser.ParserConfig r4 = r47.getConfig()     // Catch:{ all -> 0x034d }
            r0 = r44
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r16 = r4.getDeserializer((java.lang.reflect.Type) r0)     // Catch:{ all -> 0x034d }
        L_0x047e:
            r0 = r16
            r1 = r47
            r2 = r44
            r3 = r49
            java.lang.Object r12 = r0.deserialze(r1, r2, r3)     // Catch:{ all -> 0x034d }
            if (r13 == 0) goto L_0x0490
            r0 = r50
            r13.object = r0
        L_0x0490:
            r0 = r47
            r0.setContext(r15)
            goto L_0x0010
        L_0x0497:
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x034d }
            java.lang.String r5 = "type not match"
            r4.<init>(r5)     // Catch:{ all -> 0x034d }
            throw r4     // Catch:{ all -> 0x034d }
        L_0x04a0:
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x034d }
            java.lang.String r5 = "syntax error"
            r4.<init>(r5)     // Catch:{ all -> 0x034d }
            throw r4     // Catch:{ all -> 0x034d }
        L_0x04a8:
            if (r50 != 0) goto L_0x06c7
            if (r27 != 0) goto L_0x06c7
            java.lang.Object r50 = r46.createInstance((com.alibaba.fastjson.parser.DefaultJSONParser) r47, (java.lang.reflect.Type) r48)     // Catch:{ all -> 0x034d }
            if (r50 != 0) goto L_0x06c3
            java.util.HashMap r9 = new java.util.HashMap     // Catch:{ all -> 0x034d }
            r0 = r46
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer[] r4 = r0.fieldDeserializers     // Catch:{ all -> 0x034d }
            int r4 = r4.length     // Catch:{ all -> 0x034d }
            r9.<init>(r4)     // Catch:{ all -> 0x034d }
        L_0x04bc:
            r0 = r47
            r1 = r50
            r2 = r49
            com.alibaba.fastjson.parser.ParseContext r13 = r0.setContext(r15, r1, r2)     // Catch:{ all -> 0x0110 }
        L_0x04c6:
            if (r33 == 0) goto L_0x0536
            if (r45 != 0) goto L_0x04ee
            r0 = r21
            r1 = r47
            r2 = r50
            r3 = r48
            r0.parseField(r1, r2, r3, r9)     // Catch:{ all -> 0x0110 }
        L_0x04d5:
            int r4 = r30.token()     // Catch:{ all -> 0x0110 }
            r5 = 16
            if (r4 == r5) goto L_0x01cc
            int r4 = r30.token()     // Catch:{ all -> 0x0110 }
            r5 = 13
            if (r4 != r5) goto L_0x0561
            r4 = 16
            r0 = r30
            r0.nextToken(r4)     // Catch:{ all -> 0x0110 }
            goto L_0x01a0
        L_0x04ee:
            if (r50 != 0) goto L_0x0502
            r0 = r23
            java.lang.String r4 = r0.name     // Catch:{ all -> 0x0110 }
            r0 = r26
            r9.put(r4, r0)     // Catch:{ all -> 0x0110 }
        L_0x04f9:
            r0 = r30
            int r4 = r0.matchStat     // Catch:{ all -> 0x0110 }
            r5 = 4
            if (r4 != r5) goto L_0x04d5
            goto L_0x01a0
        L_0x0502:
            if (r26 != 0) goto L_0x052c
            java.lang.Class r4 = java.lang.Integer.TYPE     // Catch:{ all -> 0x0110 }
            r0 = r20
            if (r0 == r4) goto L_0x04f9
            java.lang.Class r4 = java.lang.Long.TYPE     // Catch:{ all -> 0x0110 }
            r0 = r20
            if (r0 == r4) goto L_0x04f9
            java.lang.Class r4 = java.lang.Float.TYPE     // Catch:{ all -> 0x0110 }
            r0 = r20
            if (r0 == r4) goto L_0x04f9
            java.lang.Class r4 = java.lang.Double.TYPE     // Catch:{ all -> 0x0110 }
            r0 = r20
            if (r0 == r4) goto L_0x04f9
            java.lang.Class r4 = java.lang.Boolean.TYPE     // Catch:{ all -> 0x0110 }
            r0 = r20
            if (r0 == r4) goto L_0x04f9
            r0 = r21
            r1 = r50
            r2 = r26
            r0.setValue((java.lang.Object) r1, (java.lang.Object) r2)     // Catch:{ all -> 0x0110 }
            goto L_0x04f9
        L_0x052c:
            r0 = r21
            r1 = r50
            r2 = r26
            r0.setValue((java.lang.Object) r1, (java.lang.Object) r2)     // Catch:{ all -> 0x0110 }
            goto L_0x04f9
        L_0x0536:
            r4 = r46
            r5 = r47
            r7 = r50
            r8 = r48
            boolean r32 = r4.parseField(r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0110 }
            if (r32 != 0) goto L_0x0551
            int r4 = r30.token()     // Catch:{ all -> 0x0110 }
            r5 = 13
            if (r4 != r5) goto L_0x01cc
            r30.nextToken()     // Catch:{ all -> 0x0110 }
            goto L_0x01a0
        L_0x0551:
            int r4 = r30.token()     // Catch:{ all -> 0x0110 }
            r5 = 17
            if (r4 != r5) goto L_0x04d5
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0110 }
            java.lang.String r5 = "syntax error, unexpect token ':'"
            r4.<init>(r5)     // Catch:{ all -> 0x0110 }
            throw r4     // Catch:{ all -> 0x0110 }
        L_0x0561:
            int r4 = r30.token()     // Catch:{ all -> 0x0110 }
            r5 = 18
            if (r4 == r5) goto L_0x0570
            int r4 = r30.token()     // Catch:{ all -> 0x0110 }
            r5 = 1
            if (r4 != r5) goto L_0x01cc
        L_0x0570:
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0110 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0110 }
            r5.<init>()     // Catch:{ all -> 0x0110 }
            java.lang.String r7 = "syntax error, unexpect token "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ all -> 0x0110 }
            int r7 = r30.token()     // Catch:{ all -> 0x0110 }
            java.lang.String r7 = com.alibaba.fastjson.parser.JSONToken.name(r7)     // Catch:{ all -> 0x0110 }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ all -> 0x0110 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0110 }
            r4.<init>(r5)     // Catch:{ all -> 0x0110 }
            throw r4     // Catch:{ all -> 0x0110 }
        L_0x0591:
            r0 = r46
            com.alibaba.fastjson.util.JavaBeanInfo r4 = r0.beanInfo     // Catch:{ all -> 0x0110 }
            com.alibaba.fastjson.util.FieldInfo[] r0 = r4.fields     // Catch:{ all -> 0x0110 }
            r24 = r0
            r0 = r24
            int r0 = r0.length     // Catch:{ all -> 0x0110 }
            r40 = r0
            r0 = r40
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0110 }
            r36 = r0
            r28 = 0
        L_0x05a6:
            r0 = r28
            r1 = r40
            if (r0 >= r1) goto L_0x0615
            r23 = r24[r28]     // Catch:{ all -> 0x0110 }
            r0 = r23
            java.lang.String r4 = r0.name     // Catch:{ all -> 0x0110 }
            java.lang.Object r35 = r9.get(r4)     // Catch:{ all -> 0x0110 }
            if (r35 != 0) goto L_0x05c9
            r0 = r23
            java.lang.reflect.Type r0 = r0.fieldType     // Catch:{ all -> 0x0110 }
            r25 = r0
            java.lang.Class r4 = java.lang.Byte.TYPE     // Catch:{ all -> 0x0110 }
            r0 = r25
            if (r0 != r4) goto L_0x05ce
            r4 = 0
            java.lang.Byte r35 = java.lang.Byte.valueOf(r4)     // Catch:{ all -> 0x0110 }
        L_0x05c9:
            r36[r28] = r35     // Catch:{ all -> 0x0110 }
            int r28 = r28 + 1
            goto L_0x05a6
        L_0x05ce:
            java.lang.Class r4 = java.lang.Short.TYPE     // Catch:{ all -> 0x0110 }
            r0 = r25
            if (r0 != r4) goto L_0x05da
            r4 = 0
            java.lang.Short r35 = java.lang.Short.valueOf(r4)     // Catch:{ all -> 0x0110 }
            goto L_0x05c9
        L_0x05da:
            java.lang.Class r4 = java.lang.Integer.TYPE     // Catch:{ all -> 0x0110 }
            r0 = r25
            if (r0 != r4) goto L_0x05e6
            r4 = 0
            java.lang.Integer r35 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x0110 }
            goto L_0x05c9
        L_0x05e6:
            java.lang.Class r4 = java.lang.Long.TYPE     // Catch:{ all -> 0x0110 }
            r0 = r25
            if (r0 != r4) goto L_0x05f3
            r4 = 0
            java.lang.Long r35 = java.lang.Long.valueOf(r4)     // Catch:{ all -> 0x0110 }
            goto L_0x05c9
        L_0x05f3:
            java.lang.Class r4 = java.lang.Float.TYPE     // Catch:{ all -> 0x0110 }
            r0 = r25
            if (r0 != r4) goto L_0x05ff
            r4 = 0
            java.lang.Float r35 = java.lang.Float.valueOf(r4)     // Catch:{ all -> 0x0110 }
            goto L_0x05c9
        L_0x05ff:
            java.lang.Class r4 = java.lang.Double.TYPE     // Catch:{ all -> 0x0110 }
            r0 = r25
            if (r0 != r4) goto L_0x060c
            r4 = 0
            java.lang.Double r35 = java.lang.Double.valueOf(r4)     // Catch:{ all -> 0x0110 }
            goto L_0x05c9
        L_0x060c:
            java.lang.Class r4 = java.lang.Boolean.TYPE     // Catch:{ all -> 0x0110 }
            r0 = r25
            if (r0 != r4) goto L_0x05c9
            java.lang.Boolean r35 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x0110 }
            goto L_0x05c9
        L_0x0615:
            r0 = r46
            com.alibaba.fastjson.util.JavaBeanInfo r4 = r0.beanInfo     // Catch:{ all -> 0x0110 }
            java.lang.reflect.Constructor<?> r4 = r4.creatorConstructor     // Catch:{ all -> 0x0110 }
            if (r4 == 0) goto L_0x0666
            r0 = r46
            com.alibaba.fastjson.util.JavaBeanInfo r4 = r0.beanInfo     // Catch:{ Exception -> 0x0640 }
            java.lang.reflect.Constructor<?> r4 = r4.creatorConstructor     // Catch:{ Exception -> 0x0640 }
            r0 = r36
            java.lang.Object r50 = r4.newInstance(r0)     // Catch:{ Exception -> 0x0640 }
        L_0x0629:
            r0 = r46
            com.alibaba.fastjson.util.JavaBeanInfo r4 = r0.beanInfo     // Catch:{ all -> 0x0110 }
            java.lang.reflect.Method r11 = r4.buildMethod     // Catch:{ all -> 0x0110 }
            if (r11 != 0) goto L_0x06a2
            if (r13 == 0) goto L_0x0637
            r0 = r50
            r13.object = r0
        L_0x0637:
            r0 = r47
            r0.setContext(r15)
            r12 = r50
            goto L_0x0010
        L_0x0640:
            r17 = move-exception
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0110 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0110 }
            r5.<init>()     // Catch:{ all -> 0x0110 }
            java.lang.String r7 = "create instance error, "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ all -> 0x0110 }
            r0 = r46
            com.alibaba.fastjson.util.JavaBeanInfo r7 = r0.beanInfo     // Catch:{ all -> 0x0110 }
            java.lang.reflect.Constructor<?> r7 = r7.creatorConstructor     // Catch:{ all -> 0x0110 }
            java.lang.String r7 = r7.toGenericString()     // Catch:{ all -> 0x0110 }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ all -> 0x0110 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0110 }
            r0 = r17
            r4.<init>(r5, r0)     // Catch:{ all -> 0x0110 }
            throw r4     // Catch:{ all -> 0x0110 }
        L_0x0666:
            r0 = r46
            com.alibaba.fastjson.util.JavaBeanInfo r4 = r0.beanInfo     // Catch:{ all -> 0x0110 }
            java.lang.reflect.Method r4 = r4.factoryMethod     // Catch:{ all -> 0x0110 }
            if (r4 == 0) goto L_0x0629
            r0 = r46
            com.alibaba.fastjson.util.JavaBeanInfo r4 = r0.beanInfo     // Catch:{ Exception -> 0x067c }
            java.lang.reflect.Method r4 = r4.factoryMethod     // Catch:{ Exception -> 0x067c }
            r5 = 0
            r0 = r36
            java.lang.Object r50 = r4.invoke(r5, r0)     // Catch:{ Exception -> 0x067c }
            goto L_0x0629
        L_0x067c:
            r17 = move-exception
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0110 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0110 }
            r5.<init>()     // Catch:{ all -> 0x0110 }
            java.lang.String r7 = "create factory method error, "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ all -> 0x0110 }
            r0 = r46
            com.alibaba.fastjson.util.JavaBeanInfo r7 = r0.beanInfo     // Catch:{ all -> 0x0110 }
            java.lang.reflect.Method r7 = r7.factoryMethod     // Catch:{ all -> 0x0110 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0110 }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ all -> 0x0110 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0110 }
            r0 = r17
            r4.<init>(r5, r0)     // Catch:{ all -> 0x0110 }
            throw r4     // Catch:{ all -> 0x0110 }
        L_0x06a2:
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x06b8 }
            r0 = r50
            java.lang.Object r12 = r11.invoke(r0, r4)     // Catch:{ Exception -> 0x06b8 }
            if (r13 == 0) goto L_0x06b1
            r0 = r50
            r13.object = r0
        L_0x06b1:
            r0 = r47
            r0.setContext(r15)
            goto L_0x0010
        L_0x06b8:
            r17 = move-exception
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0110 }
            java.lang.String r5 = "build object error"
            r0 = r17
            r4.<init>(r5, r0)     // Catch:{ all -> 0x0110 }
            throw r4     // Catch:{ all -> 0x0110 }
        L_0x06c3:
            r9 = r27
            goto L_0x04bc
        L_0x06c7:
            r9 = r27
            goto L_0x04c6
        L_0x06cb:
            r9 = r27
            goto L_0x01cc
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object, java.lang.Object, int):java.lang.Object");
    }

    public boolean parseField(DefaultJSONParser parser, String key, Object object, Type objectType, Map<String, Object> fieldValues) {
        JSONLexer lexer = parser.lexer;
        FieldDeserializer fieldDeserializer = smartMatch(key);
        if (fieldDeserializer != null) {
            lexer.nextTokenWithColon(fieldDeserializer.getFastMatchToken());
            fieldDeserializer.parseField(parser, object, objectType, fieldValues);
            return true;
        } else if (!lexer.isEnabled(Feature.IgnoreNotMatch)) {
            throw new JSONException("setter not found, class " + this.clazz.getName() + ", property " + key);
        } else {
            parser.parseExtra(object, key);
            return false;
        }
    }

    public FieldDeserializer smartMatch(String key) {
        if (key == null) {
            return null;
        }
        FieldDeserializer fieldDeserializer = getFieldDeserializer(key);
        if (fieldDeserializer == null) {
            boolean startsWithIs = key.startsWith("is");
            FieldDeserializer[] fieldDeserializerArr = this.sortedFieldDeserializers;
            int length = fieldDeserializerArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                FieldDeserializer fieldDeser = fieldDeserializerArr[i];
                FieldInfo fieldInfo = fieldDeser.fieldInfo;
                Class<?> fieldClass = fieldInfo.fieldClass;
                String fieldName = fieldInfo.name;
                if (!fieldName.equalsIgnoreCase(key)) {
                    if (startsWithIs && ((fieldClass == Boolean.TYPE || fieldClass == Boolean.class) && fieldName.equalsIgnoreCase(key.substring(2)))) {
                        fieldDeserializer = fieldDeser;
                        break;
                    }
                    i++;
                } else {
                    fieldDeserializer = fieldDeser;
                    break;
                }
            }
        }
        if (fieldDeserializer != null) {
            return fieldDeserializer;
        }
        boolean snakeOrkebab = false;
        String key2 = null;
        int i2 = 0;
        while (true) {
            if (i2 >= key.length()) {
                break;
            }
            char ch = key.charAt(i2);
            if (ch == '_') {
                snakeOrkebab = true;
                key2 = key.replaceAll("_", "");
                break;
            } else if (ch == '-') {
                snakeOrkebab = true;
                key2 = key.replaceAll("-", "");
                break;
            } else {
                i2++;
            }
        }
        if (!snakeOrkebab) {
            return fieldDeserializer;
        }
        FieldDeserializer fieldDeserializer2 = getFieldDeserializer(key2);
        if (fieldDeserializer2 != null) {
            return fieldDeserializer2;
        }
        for (FieldDeserializer fieldDeser2 : this.sortedFieldDeserializers) {
            if (fieldDeser2.fieldInfo.name.equalsIgnoreCase(key2)) {
                return fieldDeser2;
            }
        }
        return fieldDeserializer2;
    }

    public int getFastMatchToken() {
        return 12;
    }

    public Object createInstance(Map<String, Object> map, ParserConfig config) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (this.beanInfo.creatorConstructor == null && this.beanInfo.buildMethod == null && this.beanInfo.factoryMethod == null) {
            Object object = createInstance((DefaultJSONParser) null, (Type) this.clazz);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Object value = entry.getValue();
                FieldDeserializer fieldDeser = smartMatch(entry.getKey());
                if (fieldDeser != null) {
                    Method method = fieldDeser.fieldInfo.method;
                    if (method != null) {
                        method.invoke(object, new Object[]{TypeUtils.cast(value, method.getGenericParameterTypes()[0], config)});
                    } else {
                        fieldDeser.fieldInfo.field.set(object, TypeUtils.cast(value, fieldDeser.fieldInfo.fieldType, config));
                    }
                }
            }
            return object;
        }
        FieldInfo[] fieldInfoList = this.beanInfo.fields;
        int size = fieldInfoList.length;
        Object[] params = new Object[size];
        for (int i = 0; i < size; i++) {
            params[i] = map.get(fieldInfoList[i].name);
        }
        if (this.beanInfo.creatorConstructor != null) {
            try {
                return this.beanInfo.creatorConstructor.newInstance(params);
            } catch (Exception e) {
                throw new JSONException("create instance error, " + this.beanInfo.creatorConstructor.toGenericString(), e);
            }
        } else if (this.beanInfo.factoryMethod == null) {
            return null;
        } else {
            try {
                return this.beanInfo.factoryMethod.invoke((Object) null, params);
            } catch (Exception e2) {
                throw new JSONException("create factory method error, " + this.beanInfo.factoryMethod.toString(), e2);
            }
        }
    }

    public Type getFieldType(int ordinal) {
        return this.sortedFieldDeserializers[ordinal].fieldInfo.fieldType;
    }

    /* access modifiers changed from: protected */
    public Object parseRest(DefaultJSONParser parser, Type type, Object fieldName, Object instance, int features) {
        return deserialze(parser, type, fieldName, instance, features);
    }

    /* access modifiers changed from: protected */
    public JavaBeanDeserializer getSeeAlso(ParserConfig config, JavaBeanInfo beanInfo2, String typeName) {
        if (beanInfo2.jsonType == null) {
            return null;
        }
        for (Class<?> seeAlsoClass : beanInfo2.jsonType.seeAlso()) {
            ObjectDeserializer seeAlsoDeser = config.getDeserializer((Type) seeAlsoClass);
            if (seeAlsoDeser instanceof JavaBeanDeserializer) {
                JavaBeanDeserializer seeAlsoJavaBeanDeser = (JavaBeanDeserializer) seeAlsoDeser;
                JavaBeanInfo subBeanInfo = seeAlsoJavaBeanDeser.beanInfo;
                if (subBeanInfo.typeName.equals(typeName)) {
                    return seeAlsoJavaBeanDeser;
                }
                JavaBeanDeserializer subSeeAlso = getSeeAlso(config, subBeanInfo, typeName);
                if (subSeeAlso != null) {
                    return subSeeAlso;
                }
            }
        }
        return null;
    }

    protected static void parseArray(Collection collection, ObjectDeserializer deser, DefaultJSONParser parser, Type type, Object fieldName) {
        JSONLexerBase lexer = (JSONLexerBase) parser.lexer;
        int token = lexer.token();
        if (token == 8) {
            lexer.nextToken(16);
            int token2 = lexer.token();
            return;
        }
        if (token != 14) {
            parser.throwException(token);
        }
        if (lexer.getCurrent() == '[') {
            lexer.next();
            lexer.setToken(14);
        } else {
            lexer.nextToken(14);
        }
        if (lexer.token() == 15) {
            lexer.nextToken();
            return;
        }
        int index = 0;
        while (true) {
            collection.add(deser.deserialze(parser, type, Integer.valueOf(index)));
            index++;
            if (lexer.token() != 16) {
                break;
            } else if (lexer.getCurrent() == '[') {
                lexer.next();
                lexer.setToken(14);
            } else {
                lexer.nextToken(14);
            }
        }
        int token3 = lexer.token();
        if (token3 != 15) {
            parser.throwException(token3);
        }
        if (lexer.getCurrent() == ',') {
            lexer.next();
            lexer.setToken(16);
            return;
        }
        lexer.nextToken(16);
    }
}

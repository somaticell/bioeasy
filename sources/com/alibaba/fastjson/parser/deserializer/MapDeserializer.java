package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MapDeserializer implements ObjectDeserializer {
    public static MapDeserializer instance = new MapDeserializer();

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        if (type == JSONObject.class && parser.getFieldTypeResolver() == null) {
            return parser.parseObject();
        }
        JSONLexer lexer = parser.lexer;
        if (lexer.token() == 8) {
            lexer.nextToken(16);
            return null;
        }
        Map<Object, Object> map = createMap(type);
        ParseContext context = parser.getContext();
        try {
            parser.setContext(context, map, fieldName);
            return deserialze(parser, type, fieldName, map);
        } finally {
            parser.setContext(context);
        }
    }

    /* access modifiers changed from: protected */
    public Object deserialze(DefaultJSONParser parser, Type type, Object fieldName, Map map) {
        if (!(type instanceof ParameterizedType)) {
            return parser.parseObject(map, fieldName);
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type keyType = parameterizedType.getActualTypeArguments()[0];
        Type valueType = parameterizedType.getActualTypeArguments()[1];
        if (String.class == keyType) {
            return parseMap(parser, map, valueType, fieldName);
        }
        return parseMap(parser, map, keyType, valueType, fieldName);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        r4 = r14.getConfig().getDeserializer((java.lang.reflect.Type) r2);
        r7.nextToken(16);
        r14.setResolveStatus(2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0187, code lost:
        if (r3 == null) goto L_0x0192;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x018d, code lost:
        if ((r17 instanceof java.lang.Integer) != false) goto L_0x0192;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x018f, code lost:
        r14.popContext();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0192, code lost:
        r11 = (java.util.Map) r4.deserialze(r14, r2, r17);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x019a, code lost:
        r14.setContext(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:?, code lost:
        return r11;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Map parseMap(com.alibaba.fastjson.parser.DefaultJSONParser r14, java.util.Map<java.lang.String, java.lang.Object> r15, java.lang.reflect.Type r16, java.lang.Object r17) {
        /*
            com.alibaba.fastjson.parser.JSONLexer r7 = r14.lexer
            int r11 = r7.token()
            r12 = 12
            if (r11 == r12) goto L_0x0027
            com.alibaba.fastjson.JSONException r11 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "syntax error, expect {, actual "
            java.lang.StringBuilder r12 = r12.append(r13)
            int r13 = r7.token()
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r12 = r12.toString()
            r11.<init>(r12)
            throw r11
        L_0x0027:
            com.alibaba.fastjson.parser.ParseContext r3 = r14.getContext()
            r5 = 0
        L_0x002c:
            r7.skipWhitespace()     // Catch:{ all -> 0x0080 }
            char r1 = r7.getCurrent()     // Catch:{ all -> 0x0080 }
            com.alibaba.fastjson.parser.Feature r11 = com.alibaba.fastjson.parser.Feature.AllowArbitraryCommas     // Catch:{ all -> 0x0080 }
            boolean r11 = r7.isEnabled((com.alibaba.fastjson.parser.Feature) r11)     // Catch:{ all -> 0x0080 }
            if (r11 == 0) goto L_0x004a
        L_0x003b:
            r11 = 44
            if (r1 != r11) goto L_0x004a
            r7.next()     // Catch:{ all -> 0x0080 }
            r7.skipWhitespace()     // Catch:{ all -> 0x0080 }
            char r1 = r7.getCurrent()     // Catch:{ all -> 0x0080 }
            goto L_0x003b
        L_0x004a:
            r11 = 34
            if (r1 != r11) goto L_0x0085
            com.alibaba.fastjson.parser.SymbolTable r11 = r14.getSymbolTable()     // Catch:{ all -> 0x0080 }
            r12 = 34
            java.lang.String r6 = r7.scanSymbol(r11, r12)     // Catch:{ all -> 0x0080 }
            r7.skipWhitespace()     // Catch:{ all -> 0x0080 }
            char r1 = r7.getCurrent()     // Catch:{ all -> 0x0080 }
            r11 = 58
            if (r1 == r11) goto L_0x0128
            com.alibaba.fastjson.JSONException r11 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0080 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0080 }
            r12.<init>()     // Catch:{ all -> 0x0080 }
            java.lang.String r13 = "expect ':' at "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x0080 }
            int r13 = r7.pos()     // Catch:{ all -> 0x0080 }
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x0080 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x0080 }
            r11.<init>(r12)     // Catch:{ all -> 0x0080 }
            throw r11     // Catch:{ all -> 0x0080 }
        L_0x0080:
            r11 = move-exception
            r14.setContext(r3)
            throw r11
        L_0x0085:
            r11 = 125(0x7d, float:1.75E-43)
            if (r1 != r11) goto L_0x0098
            r7.next()     // Catch:{ all -> 0x0080 }
            r7.resetStringPosition()     // Catch:{ all -> 0x0080 }
            r11 = 16
            r7.nextToken(r11)     // Catch:{ all -> 0x0080 }
            r14.setContext(r3)
        L_0x0097:
            return r15
        L_0x0098:
            r11 = 39
            if (r1 != r11) goto L_0x00de
            com.alibaba.fastjson.parser.Feature r11 = com.alibaba.fastjson.parser.Feature.AllowSingleQuotes     // Catch:{ all -> 0x0080 }
            boolean r11 = r7.isEnabled((com.alibaba.fastjson.parser.Feature) r11)     // Catch:{ all -> 0x0080 }
            if (r11 != 0) goto L_0x00ac
            com.alibaba.fastjson.JSONException r11 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0080 }
            java.lang.String r12 = "syntax error"
            r11.<init>(r12)     // Catch:{ all -> 0x0080 }
            throw r11     // Catch:{ all -> 0x0080 }
        L_0x00ac:
            com.alibaba.fastjson.parser.SymbolTable r11 = r14.getSymbolTable()     // Catch:{ all -> 0x0080 }
            r12 = 39
            java.lang.String r6 = r7.scanSymbol(r11, r12)     // Catch:{ all -> 0x0080 }
            r7.skipWhitespace()     // Catch:{ all -> 0x0080 }
            char r1 = r7.getCurrent()     // Catch:{ all -> 0x0080 }
            r11 = 58
            if (r1 == r11) goto L_0x0128
            com.alibaba.fastjson.JSONException r11 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0080 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0080 }
            r12.<init>()     // Catch:{ all -> 0x0080 }
            java.lang.String r13 = "expect ':' at "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x0080 }
            int r13 = r7.pos()     // Catch:{ all -> 0x0080 }
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x0080 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x0080 }
            r11.<init>(r12)     // Catch:{ all -> 0x0080 }
            throw r11     // Catch:{ all -> 0x0080 }
        L_0x00de:
            com.alibaba.fastjson.parser.Feature r11 = com.alibaba.fastjson.parser.Feature.AllowUnQuotedFieldNames     // Catch:{ all -> 0x0080 }
            boolean r11 = r7.isEnabled((com.alibaba.fastjson.parser.Feature) r11)     // Catch:{ all -> 0x0080 }
            if (r11 != 0) goto L_0x00ee
            com.alibaba.fastjson.JSONException r11 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0080 }
            java.lang.String r12 = "syntax error"
            r11.<init>(r12)     // Catch:{ all -> 0x0080 }
            throw r11     // Catch:{ all -> 0x0080 }
        L_0x00ee:
            com.alibaba.fastjson.parser.SymbolTable r11 = r14.getSymbolTable()     // Catch:{ all -> 0x0080 }
            java.lang.String r6 = r7.scanSymbolUnQuoted(r11)     // Catch:{ all -> 0x0080 }
            r7.skipWhitespace()     // Catch:{ all -> 0x0080 }
            char r1 = r7.getCurrent()     // Catch:{ all -> 0x0080 }
            r11 = 58
            if (r1 == r11) goto L_0x0128
            com.alibaba.fastjson.JSONException r11 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0080 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0080 }
            r12.<init>()     // Catch:{ all -> 0x0080 }
            java.lang.String r13 = "expect ':' at "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x0080 }
            int r13 = r7.pos()     // Catch:{ all -> 0x0080 }
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x0080 }
            java.lang.String r13 = ", actual "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x0080 }
            java.lang.StringBuilder r12 = r12.append(r1)     // Catch:{ all -> 0x0080 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x0080 }
            r11.<init>(r12)     // Catch:{ all -> 0x0080 }
            throw r11     // Catch:{ all -> 0x0080 }
        L_0x0128:
            r7.next()     // Catch:{ all -> 0x0080 }
            r7.skipWhitespace()     // Catch:{ all -> 0x0080 }
            char r1 = r7.getCurrent()     // Catch:{ all -> 0x0080 }
            r7.resetStringPosition()     // Catch:{ all -> 0x0080 }
            java.lang.String r11 = com.alibaba.fastjson.JSON.DEFAULT_TYPE_KEY     // Catch:{ all -> 0x0080 }
            if (r6 != r11) goto L_0x01a0
            com.alibaba.fastjson.parser.Feature r11 = com.alibaba.fastjson.parser.Feature.DisableSpecialKeyDetect     // Catch:{ all -> 0x0080 }
            boolean r11 = r7.isEnabled((com.alibaba.fastjson.parser.Feature) r11)     // Catch:{ all -> 0x0080 }
            if (r11 != 0) goto L_0x01a0
            com.alibaba.fastjson.parser.SymbolTable r11 = r14.getSymbolTable()     // Catch:{ all -> 0x0080 }
            r12 = 34
            java.lang.String r9 = r7.scanSymbol(r11, r12)     // Catch:{ all -> 0x0080 }
            com.alibaba.fastjson.parser.ParserConfig r11 = r14.getConfig()     // Catch:{ all -> 0x0080 }
            java.lang.ClassLoader r11 = r11.getDefaultClassLoader()     // Catch:{ all -> 0x0080 }
            java.lang.Class r2 = com.alibaba.fastjson.util.TypeUtils.loadClass(r9, r11)     // Catch:{ all -> 0x0080 }
            java.lang.Class<java.util.Map> r11 = java.util.Map.class
            boolean r11 = r11.isAssignableFrom(r2)     // Catch:{ all -> 0x0080 }
            if (r11 == 0) goto L_0x0176
            r11 = 16
            r7.nextToken(r11)     // Catch:{ all -> 0x0080 }
            int r11 = r7.token()     // Catch:{ all -> 0x0080 }
            r12 = 13
            if (r11 != r12) goto L_0x01e4
            r11 = 16
            r7.nextToken(r11)     // Catch:{ all -> 0x0080 }
            r14.setContext(r3)
            goto L_0x0097
        L_0x0176:
            com.alibaba.fastjson.parser.ParserConfig r11 = r14.getConfig()     // Catch:{ all -> 0x0080 }
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r4 = r11.getDeserializer((java.lang.reflect.Type) r2)     // Catch:{ all -> 0x0080 }
            r11 = 16
            r7.nextToken(r11)     // Catch:{ all -> 0x0080 }
            r11 = 2
            r14.setResolveStatus(r11)     // Catch:{ all -> 0x0080 }
            if (r3 == 0) goto L_0x0192
            r0 = r17
            boolean r11 = r0 instanceof java.lang.Integer     // Catch:{ all -> 0x0080 }
            if (r11 != 0) goto L_0x0192
            r14.popContext()     // Catch:{ all -> 0x0080 }
        L_0x0192:
            r0 = r17
            java.lang.Object r11 = r4.deserialze(r14, r2, r0)     // Catch:{ all -> 0x0080 }
            java.util.Map r11 = (java.util.Map) r11     // Catch:{ all -> 0x0080 }
            r14.setContext(r3)
            r15 = r11
            goto L_0x0097
        L_0x01a0:
            r7.nextToken()     // Catch:{ all -> 0x0080 }
            if (r5 == 0) goto L_0x01a8
            r14.setContext(r3)     // Catch:{ all -> 0x0080 }
        L_0x01a8:
            int r11 = r7.token()     // Catch:{ all -> 0x0080 }
            r12 = 8
            if (r11 != r12) goto L_0x01d1
            r10 = 0
            r7.nextToken()     // Catch:{ all -> 0x0080 }
        L_0x01b4:
            r15.put(r6, r10)     // Catch:{ all -> 0x0080 }
            r14.checkMapResolve(r15, r6)     // Catch:{ all -> 0x0080 }
            r14.setContext(r3, r10, r6)     // Catch:{ all -> 0x0080 }
            r14.setContext(r3)     // Catch:{ all -> 0x0080 }
            int r8 = r7.token()     // Catch:{ all -> 0x0080 }
            r11 = 20
            if (r8 == r11) goto L_0x01cc
            r11 = 15
            if (r8 != r11) goto L_0x01d8
        L_0x01cc:
            r14.setContext(r3)
            goto L_0x0097
        L_0x01d1:
            r0 = r16
            java.lang.Object r10 = r14.parseObject((java.lang.reflect.Type) r0, (java.lang.Object) r6)     // Catch:{ all -> 0x0080 }
            goto L_0x01b4
        L_0x01d8:
            r11 = 13
            if (r8 != r11) goto L_0x01e4
            r7.nextToken()     // Catch:{ all -> 0x0080 }
            r14.setContext(r3)
            goto L_0x0097
        L_0x01e4:
            int r5 = r5 + 1
            goto L_0x002c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.MapDeserializer.parseMap(com.alibaba.fastjson.parser.DefaultJSONParser, java.util.Map, java.lang.reflect.Type, java.lang.Object):java.util.Map");
    }

    public static Object parseMap(DefaultJSONParser parser, Map<Object, Object> map, Type keyType, Type valueType, Object fieldName) {
        JSONLexer lexer = parser.lexer;
        if (lexer.token() == 12 || lexer.token() == 16) {
            ObjectDeserializer keyDeserializer = parser.getConfig().getDeserializer(keyType);
            ObjectDeserializer valueDeserializer = parser.getConfig().getDeserializer(valueType);
            lexer.nextToken(keyDeserializer.getFastMatchToken());
            ParseContext context = parser.getContext();
            while (lexer.token() != 13) {
                if (lexer.token() != 4 || !lexer.isRef() || lexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                    try {
                        if (map.size() == 0 && lexer.token() == 4 && JSON.DEFAULT_TYPE_KEY.equals(lexer.stringVal()) && !lexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                            lexer.nextTokenWithColon(4);
                            lexer.nextToken(16);
                            if (lexer.token() == 13) {
                                lexer.nextToken();
                                return map;
                            }
                            lexer.nextToken(keyDeserializer.getFastMatchToken());
                        }
                        Object key = keyDeserializer.deserialze(parser, keyType, (Object) null);
                        if (lexer.token() != 17) {
                            throw new JSONException("syntax error, expect :, actual " + lexer.token());
                        }
                        lexer.nextToken(valueDeserializer.getFastMatchToken());
                        Object value = valueDeserializer.deserialze(parser, valueType, key);
                        parser.checkMapResolve(map, key);
                        map.put(key, value);
                        if (lexer.token() == 16) {
                            lexer.nextToken(keyDeserializer.getFastMatchToken());
                        }
                    } finally {
                        parser.setContext(context);
                    }
                } else {
                    Object object = null;
                    lexer.nextTokenWithColon(4);
                    if (lexer.token() == 4) {
                        String ref = lexer.stringVal();
                        if ("..".equals(ref)) {
                            object = context.parent.object;
                        } else if ("$".equals(ref)) {
                            ParseContext rootContext = context;
                            while (rootContext.parent != null) {
                                rootContext = rootContext.parent;
                            }
                            object = rootContext.object;
                        } else {
                            parser.addResolveTask(new DefaultJSONParser.ResolveTask(context, ref));
                            parser.setResolveStatus(1);
                        }
                        lexer.nextToken(13);
                        if (lexer.token() != 13) {
                            throw new JSONException("illegal ref");
                        }
                        lexer.nextToken(16);
                        parser.setContext(context);
                        return object;
                    }
                    throw new JSONException("illegal ref, " + JSONToken.name(lexer.token()));
                }
            }
            lexer.nextToken(16);
            parser.setContext(context);
            return map;
        }
        throw new JSONException("syntax error, expect {, actual " + lexer.tokenName());
    }

    /* access modifiers changed from: protected */
    public Map<Object, Object> createMap(Type type) {
        if (type == Properties.class) {
            return new Properties();
        }
        if (type == Hashtable.class) {
            return new Hashtable();
        }
        if (type == IdentityHashMap.class) {
            return new IdentityHashMap();
        }
        if (type == SortedMap.class || type == TreeMap.class) {
            return new TreeMap();
        }
        if (type == ConcurrentMap.class || type == ConcurrentHashMap.class) {
            return new ConcurrentHashMap();
        }
        if (type == Map.class || type == HashMap.class) {
            return new HashMap();
        }
        if (type == LinkedHashMap.class) {
            return new LinkedHashMap();
        }
        if (type instanceof ParameterizedType) {
            return createMap(((ParameterizedType) type).getRawType());
        }
        Class<?> clazz = (Class) type;
        if (clazz.isInterface()) {
            throw new JSONException("unsupport type " + type);
        }
        try {
            return (Map) clazz.newInstance();
        } catch (Exception e) {
            throw new JSONException("unsupport type " + type, e);
        }
    }

    public int getFastMatchToken() {
        return 12;
    }
}

package cn.com.bioeasy.app.retrofit;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemJsonDeserializer<T> implements JsonDeserializer {
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<String> list;
        Object obj = null;
        try {
            JsonObject jsonObj = json.getAsJsonObject();
            Class clazz = (Class) typeOfT;
            Field[] fields = clazz.getDeclaredFields();
            obj = clazz.newInstance();
            for (Field f : fields) {
                f.setAccessible(true);
                String name = f.getName();
                JsonElement element = jsonObj.get(name);
                if (element == null && (list = getSerializedName(f)) != null && list.size() > 0) {
                    list.add(name);
                    Iterator<String> it = list.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        String s = it.next();
                        if (jsonObj.get(s) != null) {
                            element = jsonObj.get(s);
                            break;
                        }
                    }
                }
                parseJsonElement(context, element, f, obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    private void parseJsonElement(JsonDeserializationContext context, JsonElement element, Field f, Object obj) {
        if (element != null) {
            try {
                String type = f.getType().getName();
                if (type.endsWith("String")) {
                    f.set(obj, parseAsString(element));
                } else if (type.endsWith("int") || type.endsWith("Integer")) {
                    f.set(obj, Integer.valueOf(element.getAsInt()));
                } else if (type.endsWith("List")) {
                    f.set(obj, context.deserialize(element, f.getGenericType()));
                } else {
                    f.set(obj, context.deserialize(element, Class.forName(type)));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            }
        }
    }

    private List<String> getSerializedName(Field field) {
        if (!field.isAnnotationPresent(SerializedName.class)) {
            return null;
        }
        List<String> list = new ArrayList<>();
        SerializedName serializedName = (SerializedName) field.getAnnotation(SerializedName.class);
        String s1 = serializedName.value();
        String[] s2 = serializedName.alternate();
        list.add(s1);
        for (String add : s2) {
            list.add(add);
        }
        return list;
    }

    private String parseAsString(JsonElement element) {
        if (element.isJsonArray() || element.isJsonObject()) {
            return element.toString();
        }
        if (!element.isJsonNull() && element.isJsonPrimitive()) {
            return element.getAsJsonPrimitive().getAsString();
        }
        return "";
    }
}

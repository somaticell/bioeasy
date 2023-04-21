package com.mob.tools.utils;

import android.text.TextUtils;
import com.alipay.sdk.util.h;
import com.mob.tools.MobLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Hashon {
    public <T> HashMap<String, T> fromJson(String jsonStr) {
        if (TextUtils.isEmpty(jsonStr)) {
            return new HashMap<>();
        }
        try {
            if (jsonStr.startsWith("[") && jsonStr.endsWith("]")) {
                jsonStr = "{\"fakelist\":" + jsonStr + h.d;
            }
            return fromJson(new JSONObject(jsonStr));
        } catch (Throwable t) {
            MobLog.getInstance().w(jsonStr, new Object[0]);
            MobLog.getInstance().w(t);
            return new HashMap<>();
        }
    }

    private <T> HashMap<String, T> fromJson(JSONObject json) throws JSONException {
        HashMap<String, T> map = new HashMap<>();
        Iterator<String> iKey = json.keys();
        while (iKey.hasNext()) {
            String key = iKey.next();
            Object value = json.opt(key);
            if (JSONObject.NULL.equals(value)) {
                value = null;
            }
            if (value != null) {
                if (value instanceof JSONObject) {
                    value = fromJson((JSONObject) value);
                } else if (value instanceof JSONArray) {
                    value = fromJson((JSONArray) value);
                }
                map.put(key, value);
            }
        }
        return map;
    }

    private ArrayList<Object> fromJson(JSONArray array) throws JSONException {
        ArrayList<Object> list = new ArrayList<>();
        int size = array.length();
        for (int i = 0; i < size; i++) {
            Object value = array.opt(i);
            if (value instanceof JSONObject) {
                value = fromJson((JSONObject) value);
            } else if (value instanceof JSONArray) {
                value = fromJson((JSONArray) value);
            }
            list.add(value);
        }
        return list;
    }

    public <T> String fromHashMap(HashMap<String, T> map) {
        try {
            JSONObject obj = getJSONObject(map);
            if (obj == null) {
                return "";
            }
            return obj.toString();
        } catch (Throwable t) {
            MobLog.getInstance().w(t);
            return "";
        }
    }

    private <T> JSONObject getJSONObject(HashMap<String, T> map) throws JSONException {
        JSONObject json = new JSONObject();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof HashMap) {
                value = getJSONObject((HashMap) value);
            } else if (value instanceof ArrayList) {
                value = getJSONArray((ArrayList) value);
            } else if (isBasicArray(value)) {
                value = getJSONArray(arrayToList(value));
            }
            json.put(entry.getKey(), value);
        }
        return json;
    }

    private boolean isBasicArray(Object value) {
        return (value instanceof byte[]) || (value instanceof short[]) || (value instanceof int[]) || (value instanceof long[]) || (value instanceof float[]) || (value instanceof double[]) || (value instanceof char[]) || (value instanceof boolean[]) || (value instanceof String[]);
    }

    private ArrayList<?> arrayToList(Object value) {
        int i = 0;
        if (value instanceof byte[]) {
            ArrayList<Byte> list = new ArrayList<>();
            byte[] value2 = (byte[]) value;
            int length = value2.length;
            while (i < length) {
                list.add(Byte.valueOf(value2[i]));
                i++;
            }
            return list;
        } else if (value instanceof short[]) {
            ArrayList<Short> list2 = new ArrayList<>();
            short[] value3 = (short[]) value;
            int length2 = value3.length;
            while (i < length2) {
                list2.add(Short.valueOf(value3[i]));
                i++;
            }
            return list2;
        } else if (value instanceof int[]) {
            ArrayList<Integer> list3 = new ArrayList<>();
            int[] iArr = (int[]) value;
            int length3 = iArr.length;
            while (i < length3) {
                list3.add(Integer.valueOf(iArr[i]));
                i++;
            }
            return list3;
        } else if (value instanceof long[]) {
            ArrayList<Long> list4 = new ArrayList<>();
            long[] jArr = (long[]) value;
            int length4 = jArr.length;
            while (i < length4) {
                list4.add(Long.valueOf(jArr[i]));
                i++;
            }
            return list4;
        } else if (value instanceof float[]) {
            ArrayList<Float> list5 = new ArrayList<>();
            float[] fArr = (float[]) value;
            int length5 = fArr.length;
            while (i < length5) {
                list5.add(Float.valueOf(fArr[i]));
                i++;
            }
            return list5;
        } else if (value instanceof double[]) {
            ArrayList<Double> list6 = new ArrayList<>();
            double[] dArr = (double[]) value;
            int length6 = dArr.length;
            while (i < length6) {
                list6.add(Double.valueOf(dArr[i]));
                i++;
            }
            return list6;
        } else if (value instanceof char[]) {
            ArrayList<Character> list7 = new ArrayList<>();
            char[] value4 = (char[]) value;
            int length7 = value4.length;
            while (i < length7) {
                list7.add(Character.valueOf(value4[i]));
                i++;
            }
            return list7;
        } else if (value instanceof boolean[]) {
            ArrayList<Boolean> list8 = new ArrayList<>();
            boolean[] value5 = (boolean[]) value;
            int length8 = value5.length;
            while (i < length8) {
                list8.add(Boolean.valueOf(value5[i]));
                i++;
            }
            return list8;
        } else if (!(value instanceof String[])) {
            return null;
        } else {
            ArrayList<String> list9 = new ArrayList<>();
            String[] strArr = (String[]) value;
            int length9 = strArr.length;
            while (i < length9) {
                list9.add(strArr[i]);
                i++;
            }
            return list9;
        }
    }

    private JSONArray getJSONArray(ArrayList<Object> list) throws JSONException {
        JSONArray array = new JSONArray();
        Iterator<Object> it = list.iterator();
        while (it.hasNext()) {
            Object value = it.next();
            if (value instanceof HashMap) {
                value = getJSONObject((HashMap) value);
            } else if (value instanceof ArrayList) {
                value = getJSONArray((ArrayList) value);
            }
            array.put(value);
        }
        return array;
    }

    public String format(String jsonStr) {
        try {
            return format("", (HashMap<String, Object>) fromJson(jsonStr));
        } catch (Throwable t) {
            MobLog.getInstance().w(t);
            return "";
        }
    }

    private String format(String sepStr, HashMap<String, Object> map) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\n");
        String mySepStr = sepStr + "\t";
        int i = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (i > 0) {
                sb.append(",\n");
            }
            sb.append(mySepStr).append('\"').append(entry.getKey()).append("\":");
            Object value = entry.getValue();
            if (value instanceof HashMap) {
                sb.append(format(mySepStr, (HashMap<String, Object>) (HashMap) value));
            } else if (value instanceof ArrayList) {
                sb.append(format(mySepStr, (ArrayList<Object>) (ArrayList) value));
            } else if (value instanceof String) {
                sb.append('\"').append(value).append('\"');
            } else {
                sb.append(value);
            }
            i++;
        }
        sb.append(10).append(sepStr).append('}');
        return sb.toString();
    }

    private String format(String sepStr, ArrayList<Object> list) {
        StringBuffer sb = new StringBuffer();
        sb.append("[\n");
        String mySepStr = sepStr + "\t";
        int i = 0;
        Iterator<Object> it = list.iterator();
        while (it.hasNext()) {
            Object value = it.next();
            if (i > 0) {
                sb.append(",\n");
            }
            sb.append(mySepStr);
            if (value instanceof HashMap) {
                sb.append(format(mySepStr, (HashMap<String, Object>) (HashMap) value));
            } else if (value instanceof ArrayList) {
                sb.append(format(mySepStr, (ArrayList<Object>) (ArrayList) value));
            } else if (value instanceof String) {
                sb.append('\"').append(value).append('\"');
            } else {
                sb.append(value);
            }
            i++;
        }
        sb.append(10).append(sepStr).append(']');
        return sb.toString();
    }
}

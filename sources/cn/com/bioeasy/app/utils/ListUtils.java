package cn.com.bioeasy.app.utils;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static final String DEFAULT_JOIN_SEPARATOR = ",";

    private ListUtils() {
        throw new AssertionError();
    }

    public static <V> int getSize(List<V> sourceList) {
        if (sourceList == null) {
            return 0;
        }
        return sourceList.size();
    }

    public static <V> boolean isEmpty(List<V> sourceList) {
        return sourceList == null || sourceList.size() == 0;
    }

    public static <V> boolean isEquals(ArrayList<V> actual, ArrayList<V> expected) {
        boolean z = true;
        if (actual == null) {
            if (expected != null) {
                z = false;
            }
            return z;
        } else if (expected == null || actual.size() != expected.size()) {
            return false;
        } else {
            for (int i = 0; i < actual.size(); i++) {
                if (!ObjectUtils.isEquals(actual.get(i), expected.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    public static String join(List<String> list) {
        return join(list, DEFAULT_JOIN_SEPARATOR);
    }

    public static String join(List<String> list, char separator) {
        return join(list, new String(new char[]{separator}));
    }

    public static String join(List<String> list, String separator) {
        return list == null ? "" : TextUtils.join(separator, list);
    }

    public static <V> boolean addDistinctEntry(List<V> sourceList, V entry) {
        if (sourceList == null || sourceList.contains(entry)) {
            return false;
        }
        return sourceList.add(entry);
    }

    public static <V> int addDistinctList(List<V> sourceList, List<V> entryList) {
        if (sourceList == null || isEmpty(entryList)) {
            return 0;
        }
        int sourceCount = sourceList.size();
        for (V entry : entryList) {
            if (!sourceList.contains(entry)) {
                sourceList.add(entry);
            }
        }
        return sourceList.size() - sourceCount;
    }

    public static <V> int distinctList(List<V> sourceList) {
        if (isEmpty(sourceList)) {
            return 0;
        }
        int sourceCount = sourceList.size();
        int sourceListSize = sourceList.size();
        for (int i = 0; i < sourceListSize; i++) {
            int j = i + 1;
            while (j < sourceListSize) {
                if (sourceList.get(i).equals(sourceList.get(j))) {
                    sourceList.remove(j);
                    sourceListSize = sourceList.size();
                    j--;
                }
                j++;
            }
        }
        return sourceCount - sourceList.size();
    }

    public static <V> boolean addListNotNullValue(List<V> sourceList, V value) {
        if (sourceList == null || value == null) {
            return false;
        }
        return sourceList.add(value);
    }

    public static <V> V getLast(List<V> sourceList, V value) {
        if (sourceList == null) {
            return null;
        }
        return ArrayUtils.getLast(sourceList.toArray(), value, true);
    }

    public static <V> V getNext(List<V> sourceList, V value) {
        if (sourceList == null) {
            return null;
        }
        return ArrayUtils.getNext(sourceList.toArray(), value, true);
    }

    public static <V> List<V> invertList(List<V> sourceList) {
        if (isEmpty(sourceList)) {
            return sourceList;
        }
        List<V> invertList = new ArrayList<>(sourceList.size());
        for (int i = sourceList.size() - 1; i >= 0; i--) {
            invertList.add(sourceList.get(i));
        }
        return invertList;
    }
}

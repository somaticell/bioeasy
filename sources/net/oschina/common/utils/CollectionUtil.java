package net.oschina.common.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class CollectionUtil {

    public interface Checker<T> {
        boolean check(T t);
    }

    public static <T> T[] toArray(List<T> items, Class<T> tClass) {
        if (items == null || items.size() == 0) {
            return null;
        }
        try {
            return items.toArray((Object[]) Array.newInstance(tClass, items.size()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T[] toArray(Set<T> items, Class<T> tClass) {
        if (items == null || items.size() == 0) {
            return null;
        }
        try {
            return items.toArray((Object[]) Array.newInstance(tClass, items.size()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> HashSet<T> toHashSet(T[] items) {
        if (items == null || items.length == 0) {
            return null;
        }
        HashSet<T> set = new HashSet<>();
        Collections.addAll(set, items);
        return set;
    }

    public static <T> ArrayList<T> toArrayList(T[] items) {
        if (items == null || items.length == 0) {
            return null;
        }
        ArrayList<T> list = new ArrayList<>();
        Collections.addAll(list, items);
        return list;
    }

    public static <T> Collection<T> move(List<T> collection, int fromPosition, int toPosition) {
        int maxPosition = collection.size() - 1;
        if (fromPosition != toPosition && fromPosition <= maxPosition && toPosition <= maxPosition) {
            if (fromPosition < toPosition) {
                T fromModel = collection.get(fromPosition);
                T toModel = collection.get(toPosition);
                collection.remove(fromPosition);
                collection.add(collection.indexOf(toModel) + 1, fromModel);
            } else {
                T fromModel2 = collection.get(fromPosition);
                collection.remove(fromPosition);
                collection.add(toPosition, fromModel2);
            }
        }
        return collection;
    }

    public static <T> List<T> filter(List<T> source, Checker<T> checker) {
        Iterator<T> iterator = source.iterator();
        while (iterator.hasNext()) {
            if (!checker.check(iterator.next())) {
                iterator.remove();
            }
        }
        return source;
    }

    public static <T> List<T> filter(T[] source, Checker<T> checker) {
        return filter(toArrayList(source), checker);
    }
}

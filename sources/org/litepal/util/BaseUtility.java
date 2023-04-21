package org.litepal.util;

import android.text.TextUtils;
import java.io.IOException;
import java.util.Collection;
import java.util.Locale;
import org.litepal.LitePalApplication;
import org.litepal.exceptions.DataSupportException;
import org.litepal.parser.LitePalAttr;
import org.litepal.util.Const;

public class BaseUtility {
    private BaseUtility() {
    }

    public static String changeCase(String string) {
        if (string == null) {
            return null;
        }
        String cases = LitePalAttr.getInstance().getCases();
        if (Const.Config.CASES_KEEP.equals(cases)) {
            return string;
        }
        if (Const.Config.CASES_UPPER.equals(cases)) {
            return string.toUpperCase(Locale.US);
        }
        return string.toLowerCase(Locale.US);
    }

    public static boolean containsIgnoreCases(Collection<String> collection, String string) {
        if (collection == null) {
            return false;
        }
        if (string == null) {
            return collection.contains((Object) null);
        }
        for (String element : collection) {
            if (string.equalsIgnoreCase(element)) {
                return true;
            }
        }
        return false;
    }

    public static String capitalize(String string) {
        if (!TextUtils.isEmpty(string)) {
            return String.valueOf(string.substring(0, 1).toUpperCase(Locale.US)) + string.substring(1);
        }
        if (string == null) {
            return null;
        }
        return "";
    }

    public static int count(String string, String mark) {
        if (TextUtils.isEmpty(string) || TextUtils.isEmpty(mark)) {
            return 0;
        }
        int count = 0;
        int index = string.indexOf(mark);
        while (index != -1) {
            count++;
            string = string.substring(mark.length() + index);
            index = string.indexOf(mark);
        }
        return count;
    }

    public static void checkConditionsCorrect(String... conditions) {
        int conditionsSize;
        if (conditions != null && (conditionsSize = conditions.length) > 0 && conditionsSize != count(conditions[0], "?") + 1) {
            throw new DataSupportException(DataSupportException.UPDATE_CONDITIONS_EXCEPTION);
        }
    }

    public static boolean isFieldTypeSupported(String fieldType) {
        if ("boolean".equals(fieldType) || "java.lang.Boolean".equals(fieldType) || "float".equals(fieldType) || "java.lang.Float".equals(fieldType) || "double".equals(fieldType) || "java.lang.Double".equals(fieldType) || "int".equals(fieldType) || "java.lang.Integer".equals(fieldType) || "long".equals(fieldType) || "java.lang.Long".equals(fieldType) || "short".equals(fieldType) || "java.lang.Short".equals(fieldType) || "char".equals(fieldType) || "java.lang.Character".equals(fieldType) || "[B".equals(fieldType) || "[Ljava.lang.Byte;".equals(fieldType) || "java.lang.String".equals(fieldType) || "java.util.Date".equals(fieldType)) {
            return true;
        }
        return false;
    }

    public static boolean isGenericTypeSupported(String genericType) {
        if (!"java.lang.String".equals(genericType) && !"java.lang.Integer".equals(genericType) && !"java.lang.Float".equals(genericType) && !"java.lang.Double".equals(genericType) && !"java.lang.Long".equals(genericType) && !"java.lang.Short".equals(genericType) && !"java.lang.Boolean".equals(genericType) && !"java.lang.Character".equals(genericType)) {
            return false;
        }
        return true;
    }

    public static boolean isLitePalXMLExists() {
        try {
            String[] fileNames = LitePalApplication.getContext().getAssets().list("");
            if (fileNames == null || fileNames.length <= 0) {
                return false;
            }
            for (String fileName : fileNames) {
                if (Const.Config.CONFIGURATION_FILE_NAME.equalsIgnoreCase(fileName)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}

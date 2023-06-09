package cn.com.bioeasy.app.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {
    public static String PREFERENCE_NAME = "library_sp";

    private SpUtil() {
        throw new AssertionError();
    }

    public static boolean putString(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, 0).edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getString(Context context, String key) {
        return getString(context, key, (String) null);
    }

    public static String getString(Context context, String key, String defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(key, defaultValue);
    }

    public static boolean putInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, 0).edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getInt(key, defaultValue);
    }

    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, 0).edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    public static long getLong(Context context, String key, long defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getLong(key, defaultValue);
    }

    public static boolean putFloat(Context context, String key, float value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, 0).edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1.0f);
    }

    public static float getFloat(Context context, String key, float defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getFloat(key, defaultValue);
    }

    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, 0).edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(key, defaultValue);
    }
}

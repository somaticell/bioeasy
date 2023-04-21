package com.anthony.citypicker.utils;

import android.text.TextUtils;
import com.alipay.sdk.cons.a;
import java.util.regex.Pattern;

public class PinyinUtils {
    public static String getFirstLetter(String pinyin) {
        if (TextUtils.isEmpty(pinyin)) {
            return "定位";
        }
        String c = pinyin.substring(0, 1);
        if (Pattern.compile("^[A-Za-z]+$").matcher(c).matches()) {
            return c.toUpperCase();
        }
        if (!"0".equals(c) && a.e.equals(c)) {
            return "热门";
        }
        return "定位";
    }
}

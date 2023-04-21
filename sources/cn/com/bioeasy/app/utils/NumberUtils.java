package cn.com.bioeasy.app.utils;

import android.text.TextUtils;

public class NumberUtils {
    public static boolean isMobileNO(String mobiles) {
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        }
        return mobiles.matches("[1][34578]\\d{9}");
    }
}

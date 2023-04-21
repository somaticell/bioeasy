package cn.com.bioeasy.app.device;

import android.content.Context;

public class DpUtil {
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int dp2px(Context context, float dp) {
        return (int) ((getScreenDensity(context) * dp) + 0.5f);
    }

    public static int px2dp(Context context, float px) {
        if (px == 0.0f) {
            return 0;
        }
        return (int) ((px / getScreenDensity(context)) + 0.5f);
    }
}

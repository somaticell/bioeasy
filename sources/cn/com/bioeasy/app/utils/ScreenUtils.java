package cn.com.bioeasy.app.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenUtils {
    private static int screenH;
    private static int screenW;

    public static int getScreenW(Activity mActivity) {
        if (screenW == 0) {
            initScreen(mActivity);
        }
        return screenW;
    }

    public static int getScreenH(Activity mActivity) {
        if (screenH == 0) {
            initScreen(mActivity);
        }
        return screenH;
    }

    private static void initScreen(Activity mActivity) {
        DisplayMetrics metric = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenW = metric.widthPixels;
        screenH = metric.heightPixels;
    }

    public static int dip2px(Context context, float dipValue) {
        return (int) ((dipValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}

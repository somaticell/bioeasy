package com.chanven.lib.cptr.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class PtrLocalDisplay {
    public static float SCREEN_DENSITY;
    public static int SCREEN_HEIGHT_DP;
    public static int SCREEN_HEIGHT_PIXELS;
    public static int SCREEN_WIDTH_DP;
    public static int SCREEN_WIDTH_PIXELS;

    public static void init(Context context) {
        if (context != null) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(dm);
            SCREEN_WIDTH_PIXELS = dm.widthPixels;
            SCREEN_HEIGHT_PIXELS = dm.heightPixels;
            SCREEN_DENSITY = dm.density;
            SCREEN_WIDTH_DP = (int) (((float) SCREEN_WIDTH_PIXELS) / dm.density);
            SCREEN_HEIGHT_DP = (int) (((float) SCREEN_HEIGHT_PIXELS) / dm.density);
        }
    }

    public static int dp2px(float dp) {
        return (int) ((dp * SCREEN_DENSITY) + 0.5f);
    }

    public static int designedDP2px(float designedDp) {
        if (SCREEN_WIDTH_DP != 320) {
            designedDp = (((float) SCREEN_WIDTH_DP) * designedDp) / 320.0f;
        }
        return dp2px(designedDp);
    }

    public static void setPadding(View view, float left, float top, float right, float bottom) {
        view.setPadding(designedDP2px(left), dp2px(top), designedDP2px(right), dp2px(bottom));
    }
}

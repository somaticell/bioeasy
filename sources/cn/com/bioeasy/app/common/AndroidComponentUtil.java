package cn.com.bioeasy.app.common;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.widget.ActivityChooserView;

public final class AndroidComponentUtil {
    public static void toggleComponent(Context context, Class componentClass, boolean enable) {
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, componentClass), enable ? 1 : 2, 1);
    }

    public static boolean isServiceRunning(Context context, Class serviceClass) {
        for (ActivityManager.RunningServiceInfo service : ((ActivityManager) context.getSystemService("activity")).getRunningServices(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

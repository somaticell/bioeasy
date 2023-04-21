package cn.com.bioeasy.app.base;

import android.app.Activity;
import android.os.Process;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class AppManager {
    private static List<BaseActivity> mActivities = null;

    @Inject
    public AppManager() {
        mActivities = new ArrayList();
    }

    public int size() {
        return mActivities.size();
    }

    public synchronized Activity getForwardActivity() {
        return size() > 0 ? mActivities.get(size() - 1) : null;
    }

    public synchronized void addActivity(BaseActivity activity) {
        mActivities.add(activity);
    }

    public synchronized void removeActivity(BaseActivity activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }

    public synchronized void clear() {
        for (int i = mActivities.size() - 1; i > -1; i = mActivities.size() - 1) {
            BaseActivity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
        }
    }

    public synchronized void clearTop() {
        for (int i = mActivities.size() - 2; i > -1; i = (mActivities.size() - 1) - 1) {
            BaseActivity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
        }
    }

    public static void exitApplication() {
        for (BaseActivity activity : mActivities) {
            if (activity != null) {
                activity.finish();
            }
        }
        Process.killProcess(Process.myPid());
    }
}

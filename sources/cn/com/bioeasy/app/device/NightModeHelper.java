package cn.com.bioeasy.app.device;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import java.lang.ref.WeakReference;

public class NightModeHelper {
    private static final String PREF_KEY = "nightModeState";
    private static int sUiNightMode = 0;
    private WeakReference<Activity> mActivity;
    private SharedPreferences mPrefs;

    public NightModeHelper(Activity activity) {
        int currentMode = activity.getResources().getConfiguration().uiMode & 48;
        this.mPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
        init(activity, -1, this.mPrefs.getInt(PREF_KEY, currentMode));
    }

    public NightModeHelper(Activity activity, int theme) {
        int currentMode = activity.getResources().getConfiguration().uiMode & 48;
        this.mPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
        init(activity, theme, this.mPrefs.getInt(PREF_KEY, currentMode));
    }

    public NightModeHelper(Activity activity, int theme, int defaultUiMode) {
        init(activity, theme, defaultUiMode);
    }

    private void init(Activity activity, int theme, int defaultUiMode) {
        this.mActivity = new WeakReference<>(activity);
        if (sUiNightMode == 0) {
            sUiNightMode = defaultUiMode;
        }
        updateConfig(sUiNightMode);
        if (theme != -1) {
            activity.setTheme(theme);
        }
    }

    private void updateConfig(int uiNightMode) {
        Activity activity = (Activity) this.mActivity.get();
        if (activity == null) {
            throw new IllegalStateException("Activity went away?");
        }
        Configuration newConfig = new Configuration(activity.getResources().getConfiguration());
        newConfig.uiMode &= -49;
        newConfig.uiMode |= uiNightMode;
        activity.getResources().updateConfiguration(newConfig, (DisplayMetrics) null);
        sUiNightMode = uiNightMode;
        if (this.mPrefs != null) {
            this.mPrefs.edit().putInt(PREF_KEY, sUiNightMode).apply();
        }
    }

    public static int getUiNightMode() {
        return sUiNightMode;
    }

    public void toggle() {
        if (sUiNightMode == 32) {
            notNight();
        } else {
            night();
        }
    }

    public void notNight() {
        updateConfig(16);
        System.gc();
        System.runFinalization();
        System.gc();
        ((Activity) this.mActivity.get()).recreate();
    }

    public void night() {
        updateConfig(32);
        System.gc();
        System.runFinalization();
        System.gc();
        ((Activity) this.mActivity.get()).recreate();
    }
}

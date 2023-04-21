package cn.com.bioeasy.app.storage.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import javax.inject.Inject;

public class PreferencesHelper {
    private static final String FIRST_TIME = "firstTime";
    private static final boolean FIRST_TIME_DEFAULT = true;
    private static final String ID = "USER_ID";
    private static final String ISLOGIN = "isLogin";
    private static final String PREF_KEY_SIGNED_IN_RIBOT = "PREF_KEY_SIGNED_IN_RIBOT";
    private static final String SETTING = "setting";
    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(Context context) {
        this.mPref = context.getSharedPreferences("setting", 0);
    }

    public void saveCurrentUserId(String id) {
        this.mPref.edit().putString(ID, id).apply();
    }

    public String getCurrentUserId() {
        return this.mPref.getString(ID, (String) null);
    }

    public boolean isLogined() {
        return this.mPref.getBoolean(ISLOGIN, false);
    }

    public void setIslogin(boolean islogin) {
        SharedPreferences.Editor editor = this.mPref.edit();
        editor.putBoolean(ISLOGIN, islogin);
        editor.apply();
    }

    public boolean isFirstTime() {
        return this.mPref.getBoolean(FIRST_TIME, true);
    }

    public void saveFirstTime(boolean isFirst) {
        SharedPreferences.Editor editor = this.mPref.edit();
        editor.putBoolean(FIRST_TIME, isFirst);
        editor.apply();
    }

    public void Clear() {
        this.mPref.edit().clear().apply();
    }
}

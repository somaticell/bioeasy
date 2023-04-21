package cn.sharesdk.framework;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import cn.sharesdk.framework.utils.d;
import com.alipay.sdk.cons.a;
import com.mob.tools.utils.Hashon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlatformDb {
    private static final String DB_NAME = "cn_sharesdk_weibodb";
    private SharedPreferences db;
    private String platformNname;
    private int platformVersion;

    public PlatformDb(Context context, String name, int version) {
        this.db = context.getSharedPreferences("cn_sharesdk_weibodb_" + name + "_" + version, 0);
        this.platformNname = name;
        this.platformVersion = version;
    }

    public void put(String key, String value) {
        SharedPreferences.Editor edit = this.db.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public String get(String key) {
        return this.db.getString(key, "");
    }

    public String getToken() {
        return this.db.getString("token", "");
    }

    public void putToken(String token) {
        SharedPreferences.Editor edit = this.db.edit();
        edit.putString("token", token);
        edit.commit();
    }

    public String getTokenSecret() {
        return this.db.getString("secret", "");
    }

    public void putTokenSecret(String secret) {
        SharedPreferences.Editor edit = this.db.edit();
        edit.putString("secret", secret);
        edit.commit();
    }

    public long getExpiresIn() {
        try {
            return this.db.getLong("expiresIn", 0);
        } catch (Throwable th) {
            return 0;
        }
    }

    public void putExpiresIn(long expires) {
        SharedPreferences.Editor edit = this.db.edit();
        edit.putLong("expiresIn", expires);
        edit.putLong("expiresTime", System.currentTimeMillis());
        edit.commit();
    }

    public long getExpiresTime() {
        return this.db.getLong("expiresTime", 0) + (getExpiresIn() * 1000);
    }

    public int getPlatformVersion() {
        return this.platformVersion;
    }

    public String getPlatformNname() {
        return this.platformNname;
    }

    public void putUserId(String platformId) {
        SharedPreferences.Editor edit = this.db.edit();
        edit.putString("userID", platformId);
        edit.commit();
    }

    public String getUserId() {
        String string = this.db.getString("userID", "");
        if (TextUtils.isEmpty(string)) {
            return this.db.getString("weibo", "");
        }
        return string;
    }

    public String getUserName() {
        return this.db.getString("nickname", "");
    }

    public String getUserIcon() {
        return this.db.getString("icon", "");
    }

    public void removeAccount() {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, ?> key : this.db.getAll().entrySet()) {
            arrayList.add(key.getKey());
        }
        SharedPreferences.Editor edit = this.db.edit();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            edit.remove((String) it.next());
        }
        edit.commit();
    }

    public String exportData() {
        try {
            HashMap hashMap = new HashMap();
            hashMap.putAll(this.db.getAll());
            return new Hashon().fromHashMap(hashMap);
        } catch (Throwable th) {
            d.a().d(th);
            return null;
        }
    }

    public void importData(String json) {
        try {
            HashMap fromJson = new Hashon().fromJson(json);
            if (fromJson != null) {
                SharedPreferences.Editor edit = this.db.edit();
                for (Map.Entry entry : fromJson.entrySet()) {
                    Object value = entry.getValue();
                    if (value instanceof Boolean) {
                        edit.putBoolean((String) entry.getKey(), ((Boolean) value).booleanValue());
                    } else if (value instanceof Float) {
                        edit.putFloat((String) entry.getKey(), ((Float) value).floatValue());
                    } else if (value instanceof Integer) {
                        edit.putInt((String) entry.getKey(), ((Integer) value).intValue());
                    } else if (value instanceof Long) {
                        edit.putLong((String) entry.getKey(), ((Long) value).longValue());
                    } else {
                        edit.putString((String) entry.getKey(), String.valueOf(value));
                    }
                }
                edit.commit();
            }
        } catch (Throwable th) {
            d.a().d(th);
        }
    }

    public boolean isValid() {
        String token = getToken();
        if (token == null || token.length() <= 0) {
            return false;
        }
        if (getExpiresIn() == 0 || getExpiresTime() > System.currentTimeMillis()) {
            return true;
        }
        return false;
    }

    public String getUserGender() {
        String string = this.db.getString("gender", "2");
        if ("0".equals(string)) {
            return "m";
        }
        if (a.e.equals(string)) {
            return "f";
        }
        return null;
    }
}

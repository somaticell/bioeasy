package cn.sharesdk.framework.b.a;

import android.content.Context;
import android.text.TextUtils;
import com.mob.tools.utils.SharePrefrenceHelper;

/* compiled from: SharePrefrenceUtil */
public class e {
    private static e c;
    private Context a;
    private SharePrefrenceHelper b = new SharePrefrenceHelper(this.a);

    private e(Context context) {
        this.a = context.getApplicationContext();
        this.b.open("share_sdk", 1);
    }

    public static e a(Context context) {
        if (c == null) {
            c = new e(context.getApplicationContext());
        }
        return c;
    }

    public long a() {
        return this.b.getLong("service_time");
    }

    public boolean b() {
        String string = this.b.getString("upload_device_info");
        if (TextUtils.isEmpty(string)) {
            return true;
        }
        return Boolean.parseBoolean(string);
    }

    public boolean c() {
        String string = this.b.getString("upload_user_info");
        if (TextUtils.isEmpty(string)) {
            return true;
        }
        return Boolean.parseBoolean(string);
    }

    public boolean d() {
        String string = this.b.getString("trans_short_link");
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        return Boolean.parseBoolean(string);
    }

    public int e() {
        String string = this.b.getString("upload_share_content");
        if ("true".equals(string)) {
            return 1;
        }
        if ("false".equals(string)) {
            return -1;
        }
        return 0;
    }

    public void a(String str) {
        this.b.putString("trans_short_link", str);
    }

    public void b(String str) {
        this.b.putString("upload_device_info", str);
    }

    public void c(String str) {
        this.b.putString("upload_user_info", str);
    }

    public void d(String str) {
        this.b.putString("upload_share_content", str);
    }

    public void a(String str, String str2) {
        this.b.putString("buffered_snsconf_" + str, str2);
    }

    public String e(String str) {
        return this.b.getString("buffered_snsconf_" + str);
    }

    public void a(long j) {
        this.b.putLong("device_time", Long.valueOf(j));
    }

    public Long f() {
        return Long.valueOf(this.b.getLong("device_time"));
    }

    public void a(boolean z) {
        this.b.putBoolean("connect_server", Boolean.valueOf(z));
    }

    public boolean g() {
        return this.b.getBoolean("connect_server");
    }

    public void b(long j) {
        this.b.putLong("connect_server_time", Long.valueOf(j));
    }

    public Long h() {
        return Long.valueOf(this.b.getLong("connect_server_time"));
    }

    public void b(boolean z) {
        this.b.putBoolean("sns_info_buffered", Boolean.valueOf(z));
    }

    public boolean i() {
        return this.b.getBoolean("sns_info_buffered");
    }

    public void a(String str, Long l) {
        this.b.putLong(str, l);
    }

    public long f(String str) {
        return this.b.getLong(str);
    }

    public void a(String str, int i) {
        this.b.putInt(str, Integer.valueOf(i));
    }

    public int g(String str) {
        return this.b.getInt(str);
    }

    public void a(String str, Object obj) {
        this.b.put(str, obj);
    }

    public Object h(String str) {
        return this.b.get(str);
    }
}

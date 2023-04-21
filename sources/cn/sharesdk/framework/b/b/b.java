package cn.sharesdk.framework.b.b;

import android.text.TextUtils;
import android.util.Base64;
import cn.sharesdk.framework.utils.d;
import com.mob.tools.utils.Data;

/* compiled from: AuthEvent */
public class b extends c {
    private static int n;
    private static long o;
    public int a;
    public String b;
    public String c;
    public String d;

    /* access modifiers changed from: protected */
    public String a() {
        return "[AUT]";
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append('|').append(this.a);
        sb.append('|').append(this.b);
        sb.append('|');
        if (!TextUtils.isEmpty(this.d)) {
            try {
                String encodeToString = Base64.encodeToString(Data.AES128Encode(this.f.substring(0, 16), this.d), 0);
                if (!TextUtils.isEmpty(encodeToString) && encodeToString.contains("\n")) {
                    encodeToString = encodeToString.replace("\n", "");
                }
                sb.append(encodeToString);
            } catch (Throwable th) {
                d.a().d(th);
            }
        }
        sb.append('|');
        if (!TextUtils.isEmpty(this.m)) {
            sb.append(this.m);
        }
        sb.append('|');
        if (!TextUtils.isEmpty(this.c)) {
            sb.append(this.c);
        }
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public int b() {
        return 5000;
    }

    /* access modifiers changed from: protected */
    public int c() {
        return 5;
    }

    /* access modifiers changed from: protected */
    public long d() {
        return (long) n;
    }

    /* access modifiers changed from: protected */
    public long e() {
        return o;
    }

    /* access modifiers changed from: protected */
    public void f() {
        n++;
    }

    /* access modifiers changed from: protected */
    public void a(long j) {
        o = j;
    }
}

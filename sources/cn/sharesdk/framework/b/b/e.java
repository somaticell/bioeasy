package cn.sharesdk.framework.b.b;

import android.content.Context;
import android.text.TextUtils;

/* compiled from: ExitEvent */
public class e extends c {
    private static int b;
    private static long c;
    public long a;

    /* access modifiers changed from: protected */
    public String a() {
        return "[EXT]";
    }

    /* access modifiers changed from: protected */
    public int b() {
        return 5000;
    }

    /* access modifiers changed from: protected */
    public int c() {
        return 5;
    }

    public boolean a(Context context) {
        cn.sharesdk.framework.b.a.e a2 = cn.sharesdk.framework.b.a.e.a(context);
        b = a2.g("insertExitEventCount");
        c = a2.f("lastInsertExitEventTime");
        return super.a(context);
    }

    public void b(Context context) {
        super.b(context);
        cn.sharesdk.framework.b.a.e a2 = cn.sharesdk.framework.b.a.e.a(context);
        a2.a("lastInsertExitEventTime", Long.valueOf(c));
        a2.a("insertExitEventCount", b);
    }

    /* access modifiers changed from: protected */
    public long d() {
        return (long) b;
    }

    /* access modifiers changed from: protected */
    public long e() {
        return c;
    }

    /* access modifiers changed from: protected */
    public void f() {
        b++;
    }

    /* access modifiers changed from: protected */
    public void a(long j) {
        c = j;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append('|');
        if (!TextUtils.isEmpty(this.m)) {
            sb.append(this.m);
        }
        sb.append('|').append(Math.round(((float) this.a) / 1000.0f));
        return sb.toString();
    }
}

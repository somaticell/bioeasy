package cn.sharesdk.framework.b.b;

import android.content.Context;
import android.text.TextUtils;
import cn.sharesdk.framework.b.a.e;

/* compiled from: StartEvent */
public class g extends c {
    private static int a;
    private static long b;

    /* access modifiers changed from: protected */
    public String a() {
        return "[RUN]";
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
        e a2 = e.a(context);
        a = a2.g("insertRunEventCount");
        b = a2.f("lastInsertRunEventTime");
        return super.a(context);
    }

    public void b(Context context) {
        super.b(context);
        e a2 = e.a(context);
        a2.a("lastInsertRunEventTime", Long.valueOf(b));
        a2.a("insertRunEventCount", a);
    }

    /* access modifiers changed from: protected */
    public long d() {
        return (long) a;
    }

    /* access modifiers changed from: protected */
    public long e() {
        return b;
    }

    /* access modifiers changed from: protected */
    public void f() {
        a++;
    }

    /* access modifiers changed from: protected */
    public void a(long j) {
        b = j;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append('|');
        if (!TextUtils.isEmpty(this.m)) {
            sb.append(this.m);
        }
        return sb.toString();
    }
}

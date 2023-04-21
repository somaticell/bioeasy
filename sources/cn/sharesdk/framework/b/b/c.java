package cn.sharesdk.framework.b.b;

import android.content.Context;

/* compiled from: BaseEvent */
public abstract class c {
    public long e;
    public String f;
    public String g;
    public String h;
    public int i;
    public String j;
    public int k;
    public String l;
    public String m;

    /* access modifiers changed from: protected */
    public abstract String a();

    /* access modifiers changed from: protected */
    public abstract void a(long j2);

    /* access modifiers changed from: protected */
    public abstract int b();

    /* access modifiers changed from: protected */
    public abstract int c();

    /* access modifiers changed from: protected */
    public abstract long d();

    /* access modifiers changed from: protected */
    public abstract long e();

    /* access modifiers changed from: protected */
    public abstract void f();

    public boolean a(Context context) {
        int b = b();
        int c = c();
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - e() >= ((long) b)) {
            a(currentTimeMillis);
            return true;
        } else if (d() < ((long) c)) {
            return true;
        } else {
            return false;
        }
    }

    public void b(Context context) {
        f();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(a()).append(':');
        sb.append(this.e).append('|');
        sb.append(this.f).append('|');
        sb.append(this.g).append('|');
        sb.append(this.h).append('|');
        sb.append(this.i).append('|');
        sb.append(this.j).append('|');
        sb.append(this.k).append('|');
        sb.append(this.l);
        return sb.toString();
    }
}

package cn.sharesdk.framework.b.b;

/* compiled from: DemoEvent */
public class d extends c {
    private static int d;
    private static long n;
    public String a;
    public int b;
    public String c = "";

    /* access modifiers changed from: protected */
    public String a() {
        return "[EVT]";
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append('|').append(this.a);
        sb.append('|').append(this.b);
        sb.append('|').append(this.c);
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public int b() {
        return 5000;
    }

    /* access modifiers changed from: protected */
    public int c() {
        return 30;
    }

    /* access modifiers changed from: protected */
    public long d() {
        return (long) d;
    }

    /* access modifiers changed from: protected */
    public long e() {
        return n;
    }

    /* access modifiers changed from: protected */
    public void f() {
        d++;
    }

    /* access modifiers changed from: protected */
    public void a(long j) {
        n = j;
    }
}

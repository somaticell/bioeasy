package cn.sharesdk.framework.authorize;

import android.content.Intent;

/* compiled from: SSOProcessor */
public abstract class d {
    protected c a;
    protected int b;
    protected SSOListener c;

    public abstract void a();

    public d(c cVar) {
        this.a = cVar;
        this.c = cVar.a().getSSOListener();
    }

    public void a(int i) {
        this.b = i;
    }

    public void a(int i, int i2, Intent intent) {
    }

    /* access modifiers changed from: protected */
    public void a(Intent intent) {
    }
}

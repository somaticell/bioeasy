package com.baidu.platform.comjni.map.favorite;

import android.os.Bundle;

public class a {
    private long a;
    private JNIFavorite b;

    /* renamed from: com.baidu.platform.comjni.map.favorite.a$a  reason: collision with other inner class name */
    public static class C0019a {
        public static boolean a = false;

        /* access modifiers changed from: private */
        public static void b() {
            a = true;
        }
    }

    public a() {
        this.a = 0;
        this.b = null;
        this.b = new JNIFavorite();
    }

    public int a(Bundle bundle) {
        try {
            return this.b.GetAll(this.a, bundle);
        } catch (Throwable th) {
            return 0;
        }
    }

    public long a() {
        this.a = this.b.Create();
        return this.a;
    }

    public boolean a(int i) {
        return this.b.SetType(this.a, i);
    }

    public boolean a(String str) {
        return this.b.Remove(this.a, str);
    }

    public boolean a(String str, String str2) {
        C0019a.b();
        return this.b.Add(this.a, str, str2);
    }

    public boolean a(String str, String str2, String str3, int i, int i2, int i3) {
        return this.b.Load(this.a, str, str2, str3, i, i2, i3);
    }

    public int b() {
        return this.b.Release(this.a);
    }

    public String b(String str) {
        try {
            return this.b.GetValue(this.a, str);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean b(String str, String str2) {
        C0019a.b();
        return this.b.Update(this.a, str, str2);
    }

    public boolean c() {
        return this.b.Clear(this.a);
    }

    public boolean c(String str) {
        try {
            return this.b.IsExist(this.a, str);
        } catch (Throwable th) {
            return false;
        }
    }

    public boolean d() {
        return this.b.SaveCache(this.a);
    }
}

package com.loc;

import java.util.HashMap;
import java.util.Map;

/* compiled from: AuthRequest */
class x extends at {
    private Map<String, String> d = new HashMap();
    private String e;
    private Map<String, String> f = new HashMap();

    x() {
    }

    /* access modifiers changed from: package-private */
    public void a(Map<String, String> map) {
        this.d.clear();
        this.d.putAll(map);
    }

    /* access modifiers changed from: package-private */
    public void a(String str) {
        this.e = str;
    }

    /* access modifiers changed from: package-private */
    public void b(Map<String, String> map) {
        this.f.clear();
        this.f.putAll(map);
    }

    public String c() {
        return this.e;
    }

    public Map<String, String> a() {
        return this.d;
    }

    public Map<String, String> b() {
        return this.f;
    }
}

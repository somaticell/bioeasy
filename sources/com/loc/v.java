package com.loc;

/* compiled from: SDKInfo */
public class v {
    String a;
    String b;
    String c;
    private boolean d;
    private String e;
    private String[] f;

    private v(a aVar) {
        this.d = true;
        this.e = "standard";
        this.f = null;
        this.a = aVar.a;
        this.c = aVar.b;
        this.b = aVar.c;
        this.d = aVar.d;
        this.e = aVar.e;
        this.f = aVar.f;
    }

    /* compiled from: SDKInfo */
    public static class a {
        /* access modifiers changed from: private */
        public String a;
        /* access modifiers changed from: private */
        public String b;
        /* access modifiers changed from: private */
        public String c;
        /* access modifiers changed from: private */
        public boolean d = true;
        /* access modifiers changed from: private */
        public String e = "standard";
        /* access modifiers changed from: private */
        public String[] f = null;

        public a(String str, String str2, String str3) {
            this.a = str2;
            this.c = str3;
            this.b = str;
        }

        public a a(String[] strArr) {
            this.f = (String[]) strArr.clone();
            return this;
        }

        public v a() throws l {
            if (this.f != null) {
                return new v(this);
            }
            throw new l("sdk packages is null");
        }
    }

    public String a() {
        return this.c;
    }

    public String b() {
        return this.a;
    }

    public String c() {
        return this.b;
    }

    public String d() {
        return this.e;
    }
}

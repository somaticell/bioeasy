package com.loc;

import android.content.Context;
import android.text.TextUtils;
import com.loc.ah;
import com.loc.am;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* compiled from: DynamicClassLoader */
class ai extends ClassLoader {
    /* access modifiers changed from: private */
    public static ai c = null;
    volatile boolean a = true;
    private final Context b;
    private final Map<String, Class<?>> d = new HashMap();
    private DexFile e = null;
    private String f;
    private v g;

    /* access modifiers changed from: package-private */
    public boolean a() {
        return this.e != null;
    }

    private void c() {
        d();
        this.d.clear();
    }

    private void d() {
        if (this.e != null) {
            try {
                this.e.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private void a(String str, String str2) {
        try {
            this.d.clear();
            d();
            this.e = DexFile.loadDex(str, str2, 0);
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    static synchronized ai a(final Context context, v vVar, final String str, final String str2, String str3, ClassLoader classLoader) {
        ai aiVar = null;
        synchronized (ai.class) {
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                aj.a(context, vVar);
                File file = new File(str);
                if (!file.exists()) {
                    ah.b(context, vVar.a(), vVar.b());
                } else {
                    if (c == null) {
                        new Date().getTime();
                        try {
                            c = new ai(context.getApplicationContext(), classLoader);
                            c.g = vVar;
                            c.a(str, str2 + File.separator + ah.a(file.getName()));
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                        new Date().getTime();
                        new Thread() {
                            public void run() {
                                try {
                                    ai.c.a(context, str, str2);
                                } catch (Throwable th) {
                                    th.printStackTrace();
                                }
                            }
                        }.start();
                    }
                    aiVar = c;
                }
            }
        }
        return aiVar;
    }

    static synchronized void b(Context context, v vVar, String str, String str2, String str3, ClassLoader classLoader) {
        synchronized (ai.class) {
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                aj.a(context, vVar);
                try {
                    File file = new File(str);
                    if (!file.exists()) {
                        ah.b(context, vVar.a(), vVar.b());
                    } else {
                        if (c != null) {
                            c.c();
                        }
                        c = new ai(context.getApplicationContext(), classLoader);
                        c.g = vVar;
                        c.a(context, str, str2);
                        c.a(str, str2 + File.separator + ah.a(file.getName()));
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        }
        return;
    }

    private boolean a(aa aaVar, v vVar, String str) {
        return ah.a(aaVar, ah.b(vVar.a(), vVar.b()), str, vVar);
    }

    private boolean a(aa aaVar, String str, String str2) {
        String a2 = ah.a(this.b, str);
        if (ah.a(aaVar, str, a2, this.g)) {
            return true;
        }
        if (ah.a.a(aaVar, str) != null) {
            return false;
        }
        if (!TextUtils.isEmpty(this.f)) {
            ah.a.a(aaVar, new am.a(str, s.a(a2), this.g.a(), this.g.b(), str2).a("useodex").a(), al.b(str));
        }
        return true;
    }

    private void a(aa aaVar, String str) {
        am a2 = ah.a.a(aaVar, str);
        if (a2 != null) {
            this.f = a2.e();
        }
    }

    /* access modifiers changed from: private */
    public void a(Context context, String str, String str2) {
        new Date().getTime();
        try {
            aa aaVar = new aa(context, ak.c());
            File file = new File(str);
            a(aaVar, file.getName());
            if (!a(aaVar, this.g, file.getAbsolutePath())) {
                this.a = false;
                ah.b(this.b, aaVar, file.getName());
                String a2 = ah.a(this.b, aaVar, this.g);
                if (!TextUtils.isEmpty(a2)) {
                    this.f = a2;
                    a(str, str2 + File.separator + ah.a(file.getName()));
                    this.a = true;
                }
            }
            if (file.exists()) {
                String str3 = str2 + File.separator + ah.a(file.getName());
                File file2 = new File(str3);
                if (file2.exists()) {
                    if (!a(aaVar, ah.a(file.getName()), this.f)) {
                        a(str, str2 + File.separator + ah.a(file.getName()));
                        a(file2, str3, this.f, aaVar);
                    }
                    new Date().getTime();
                }
                a(str, str2 + File.separator + ah.a(file.getName()));
                c.a(file2, str3, this.f, aaVar);
                new Date().getTime();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void a(File file, String str, String str2, aa aaVar) {
        if (!TextUtils.isEmpty(this.f)) {
            String a2 = s.a(str);
            String name = file.getName();
            ah.a.a(aaVar, new am.a(name, a2, this.g.a(), this.g.b(), str2).a("useodex").a(), al.b(name));
        }
    }

    private ai(Context context, ClassLoader classLoader) {
        super(classLoader);
        this.b = context;
    }

    /* access modifiers changed from: protected */
    public Class<?> findClass(String str) throws ClassNotFoundException {
        try {
            if (this.e == null) {
                throw new ClassNotFoundException(str);
            }
            Class<?> cls = this.d.get(str);
            if (cls == null) {
                cls = this.e.loadClass(str, this);
                this.d.put(str, cls);
                if (cls == null) {
                    throw new ClassNotFoundException(str);
                }
            }
            return cls;
        } catch (Throwable th) {
            th.printStackTrace();
            throw new ClassNotFoundException(str);
        }
    }
}

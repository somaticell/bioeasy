package com.alipay.apmobilesecuritysdk.a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.alipay.apmobilesecuritysdk.d.e;
import com.alipay.apmobilesecuritysdk.f.g;
import com.alipay.apmobilesecuritysdk.f.h;
import com.alipay.apmobilesecuritysdk.f.i;
import com.alipay.b.a.a.b.b;
import com.alipay.b.a.a.c.a.c;
import com.alipay.b.a.a.c.a.d;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public final class a {
    private Context a;
    private com.alipay.apmobilesecuritysdk.b.a b = com.alipay.apmobilesecuritysdk.b.a.a();
    private int c = 4;

    public a(Context context) {
        this.a = context;
    }

    public static String a(Context context) {
        String b2 = b(context);
        return com.alipay.b.a.a.a.a.a(b2) ? h.f(context) : b2;
    }

    public static String a(Context context, String str) {
        try {
            String a2 = i.a(str);
            if (!com.alipay.b.a.a.a.a.a(a2)) {
                return a2;
            }
            String a3 = g.a(context, str);
            i.a(str, a3);
            return com.alipay.b.a.a.a.a.a(a3) ? "" : a3;
        } catch (Throwable th) {
        }
    }

    private static boolean a() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] strArr = {"2016-11-10 2016-11-11", "2016-12-11 2016-12-12"};
        int random = ((int) (Math.random() * 24.0d * 60.0d * 60.0d)) * 1;
        int i = 0;
        while (i < 2) {
            try {
                String[] split = strArr[i].split(" ");
                if (split != null && split.length == 2) {
                    Date date = new Date();
                    Date parse = simpleDateFormat.parse(split[0] + " 00:00:00");
                    Date parse2 = simpleDateFormat.parse(split[1] + " 23:59:59");
                    Calendar instance = Calendar.getInstance();
                    instance.setTime(parse2);
                    instance.add(13, random);
                    Date time = instance.getTime();
                    if (date.after(parse) && date.before(time)) {
                        return true;
                    }
                }
                i++;
            } catch (Exception e) {
            }
        }
        return false;
    }

    private boolean a(Map<String, String> map, String str) {
        long j;
        boolean z;
        long j2 = 0;
        if (a()) {
            return com.alipay.b.a.a.a.a.a(a(this.a, str)) || com.alipay.b.a.a.a.a.a(b(this.a));
        }
        e.a();
        if (!com.alipay.b.a.a.a.a.a(e.b(this.a, map), i.c())) {
            return true;
        }
        try {
            j = Long.parseLong(h.b(this.a));
            try {
                b.a();
                j2 = Long.parseLong(b.o());
                z = false;
            } catch (Throwable th) {
                z = true;
                return Math.abs(j2 - j) > 3000 ? true : true;
            }
        } catch (Throwable th2) {
            j = 0;
        }
        if (Math.abs(j2 - j) > 3000 && !z) {
            String a2 = com.alipay.b.a.a.a.a.a(map, com.alipay.sdk.cons.b.c, "");
            String a3 = com.alipay.b.a.a.a.a.a(map, com.alipay.sdk.cons.b.g, "");
            if (!com.alipay.b.a.a.a.a.b(a2) || com.alipay.b.a.a.a.a.a(a2, i.d())) {
                return (com.alipay.b.a.a.a.a.b(a3) && !com.alipay.b.a.a.a.a.a(a3, i.e())) || !i.a(this.a, str) || com.alipay.b.a.a.a.a.a(a(this.a, str)) || com.alipay.b.a.a.a.a.a(b(this.a));
            }
            return true;
        }
    }

    private c b(Map<String, String> map) {
        com.alipay.apmobilesecuritysdk.f.b b2;
        com.alipay.apmobilesecuritysdk.f.b c2;
        try {
            Context context = this.a;
            d dVar = new d();
            String a2 = a(context, com.alipay.b.a.a.a.a.a(map, "appName", ""));
            String a3 = com.alipay.apmobilesecuritysdk.e.a.a();
            String e = h.e(context);
            dVar.c(a2);
            dVar.d(a3);
            dVar.e(e);
            dVar.a("android");
            String str = "";
            String str2 = "";
            String str3 = "";
            String str4 = "";
            com.alipay.apmobilesecuritysdk.f.c c3 = com.alipay.apmobilesecuritysdk.f.d.c(context);
            if (c3 != null) {
                str2 = c3.a();
                str3 = c3.c();
            }
            if (com.alipay.b.a.a.a.a.a(str2) && (c2 = com.alipay.apmobilesecuritysdk.f.a.c(context)) != null) {
                str2 = c2.a();
                str3 = c2.c();
            }
            com.alipay.apmobilesecuritysdk.f.c b3 = com.alipay.apmobilesecuritysdk.f.d.b();
            if (b3 != null) {
                str = b3.a();
                str4 = b3.c();
            }
            if (com.alipay.b.a.a.a.a.a(str) && (b2 = com.alipay.apmobilesecuritysdk.f.a.b()) != null) {
                str = b2.a();
                str4 = b2.c();
            }
            dVar.g(str2);
            dVar.f(str);
            if (com.alipay.b.a.a.a.a.a(str2)) {
                dVar.b(str);
                dVar.h(str4);
            } else {
                dVar.b(str2);
                dVar.h(str3);
            }
            dVar.a(e.a(context, map));
            return com.alipay.b.a.a.c.d.a(this.a, this.b.c()).a(dVar);
        } catch (Throwable th) {
            com.alipay.apmobilesecuritysdk.c.a.a(th);
            return null;
        }
    }

    private static String b(Context context) {
        try {
            String b2 = i.b();
            if (!com.alipay.b.a.a.a.a.a(b2)) {
                return b2;
            }
            com.alipay.apmobilesecuritysdk.f.c b3 = com.alipay.apmobilesecuritysdk.f.d.b(context);
            if (b3 != null) {
                i.a(b3);
                String a2 = b3.a();
                if (com.alipay.b.a.a.a.a.b(a2)) {
                    return a2;
                }
            }
            com.alipay.apmobilesecuritysdk.f.b b4 = com.alipay.apmobilesecuritysdk.f.a.b(context);
            if (b4 != null) {
                i.a(b4);
                String a3 = b4.a();
                return !com.alipay.b.a.a.a.a.b(a3) ? "" : a3;
            }
        } catch (Throwable th) {
        }
    }

    public final int a(Map<String, String> map) {
        int i;
        char c2 = 2;
        try {
            com.alipay.apmobilesecuritysdk.c.a.a(this.a, com.alipay.b.a.a.a.a.a(map, com.alipay.sdk.cons.b.c, ""), com.alipay.b.a.a.a.a.a(map, com.alipay.sdk.cons.b.g, ""), a(this.a));
            String a2 = com.alipay.b.a.a.a.a.a(map, "appName", "");
            b(this.a);
            a(this.a, a2);
            i.a();
            boolean a3 = a(map, a2);
            Context context = this.a;
            b.a();
            h.b(context, String.valueOf(b.o()));
            if (!a3) {
                i = 0;
            } else {
                Context context2 = this.a;
                new com.alipay.apmobilesecuritysdk.c.b();
                Context context3 = this.a;
                com.alipay.apmobilesecuritysdk.b.a.a().b();
                com.alipay.apmobilesecuritysdk.e.a.b();
                c b2 = b(map);
                if (b2 != null) {
                    if (b2.a) {
                        if (!com.alipay.b.a.a.a.a.a(b2.c)) {
                            c2 = 1;
                        }
                    } else if ("APPKEY_ERROR".equals(b2.b)) {
                        c2 = 3;
                    }
                }
                switch (c2) {
                    case 1:
                        h.a(this.a, com.alipay.sdk.cons.a.e.equals(b2.e));
                        h.d(this.a, b2.f == null ? "0" : b2.f);
                        h.e(this.a, b2.g);
                        h.a(this.a, b2.h);
                        h.f(this.a, b2.i);
                        i.c(e.b(this.a, map));
                        i.a(a2, b2.d);
                        i.b(b2.c);
                        i.d(b2.j);
                        String a4 = com.alipay.b.a.a.a.a.a(map, com.alipay.sdk.cons.b.c, "");
                        if (!com.alipay.b.a.a.a.a.b(a4) || com.alipay.b.a.a.a.a.a(a4, i.d())) {
                            a4 = i.d();
                        } else {
                            i.e(a4);
                        }
                        i.e(a4);
                        String a5 = com.alipay.b.a.a.a.a.a(map, com.alipay.sdk.cons.b.g, "");
                        if (!com.alipay.b.a.a.a.a.b(a5) || com.alipay.b.a.a.a.a.a(a5, i.e())) {
                            a5 = i.e();
                        } else {
                            i.f(a5);
                        }
                        i.f(a5);
                        i.a();
                        com.alipay.apmobilesecuritysdk.f.d.a(this.a, i.g());
                        Context context4 = this.a;
                        com.alipay.apmobilesecuritysdk.f.d.a();
                        com.alipay.apmobilesecuritysdk.f.a.a(this.a, new com.alipay.apmobilesecuritysdk.f.b(i.b(), i.c(), i.f()));
                        Context context5 = this.a;
                        com.alipay.apmobilesecuritysdk.f.a.a();
                        g.a(this.a, a2, i.a(a2));
                        Context context6 = this.a;
                        g.a();
                        h.a(this.a, a2, System.currentTimeMillis());
                        break;
                    case 3:
                        i = 1;
                        break;
                    default:
                        if (b2 != null) {
                            com.alipay.apmobilesecuritysdk.c.a.a("Server error, result:" + b2.b);
                        } else {
                            com.alipay.apmobilesecuritysdk.c.a.a("Server error, returned null");
                        }
                        if (com.alipay.b.a.a.a.a.a(a(this.a, a2))) {
                            i = 4;
                            break;
                        }
                        break;
                }
                i = 0;
                Context context7 = this.a;
                a(this.a, a2);
                h.f(this.a);
            }
            this.c = i;
            com.alipay.b.a.a.c.b.a a6 = com.alipay.b.a.a.c.d.a(this.a, this.b.c());
            Context context8 = this.a;
            ConnectivityManager connectivityManager = (ConnectivityManager) context8.getSystemService("connectivity");
            NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
            if ((activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.getType() == 1) && h.d(context8)) {
                new com.alipay.b.a.a.e.b(context8.getFilesDir().getAbsolutePath() + "/log/ap", a6).a();
            }
        } catch (Exception e) {
            com.alipay.apmobilesecuritysdk.c.a.a((Throwable) e);
        }
        return this.c;
    }
}

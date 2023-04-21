package com.alipay.sdk.app.statistic;

import android.content.Context;
import android.text.TextUtils;

public final class a {
    public static final String a = "alipay_cashier_statistic_record";
    private static c b;

    public static void a(Context context) {
        if (b == null) {
            b = new c(context);
        }
    }

    private static void b(Context context, String str) {
        new Thread(new b(context, str)).start();
    }

    public static synchronized void a(Context context, String str) {
        String str2;
        String format;
        String str3 = null;
        synchronized (a.class) {
            if (b != null) {
                c cVar = b;
                if (TextUtils.isEmpty(cVar.Q)) {
                    format = "";
                } else {
                    String[] split = str.split(com.alipay.sdk.sys.a.b);
                    if (split != null) {
                        str2 = null;
                        for (String split2 : split) {
                            String[] split3 = split2.split("=");
                            if (split3 != null && split3.length == 2) {
                                if (split3[0].equalsIgnoreCase(c.F)) {
                                    split3[1].replace("\"", "");
                                } else if (split3[0].equalsIgnoreCase(c.G)) {
                                    str2 = split3[1].replace("\"", "");
                                } else if (split3[0].equalsIgnoreCase(c.H)) {
                                    str3 = split3[1].replace("\"", "");
                                }
                            }
                        }
                    } else {
                        str2 = null;
                    }
                    String a2 = c.a(str3);
                    String a3 = c.a(str2);
                    cVar.J = String.format("%s,%s,-,%s,-,-,-", new Object[]{a2, a3, c.a(a3)});
                    format = String.format("[(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s)]", new Object[]{cVar.I, cVar.J, cVar.K, cVar.L, cVar.M, cVar.N, cVar.O, cVar.P, cVar.Q, cVar.R});
                }
                new Thread(new b(context, format)).start();
                b = null;
            }
        }
    }

    public static void a(String str, Throwable th) {
        if (b != null && th.getClass() != null) {
            b.a(str, th.getClass().getSimpleName(), th);
        }
    }

    private static void a(String str, String str2, Throwable th, String str3) {
        if (b != null) {
            b.a(str, str2, c.a(th), str3);
        }
    }

    public static void a(String str, String str2, Throwable th) {
        if (b != null) {
            b.a(str, str2, th);
        }
    }

    public static void a(String str, String str2, String str3) {
        if (b != null) {
            b.a(str, str2, str3);
        }
    }
}

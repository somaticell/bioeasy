package com.alipay.sdk.app;

public final class h {
    public static String a;

    private static void a(String str) {
        a = str;
    }

    private static String b() {
        return a;
    }

    public static String a() {
        i a2 = i.a(i.CANCELED.h);
        return a(a2.h, a2.i, "");
    }

    private static String c() {
        i a2 = i.a(i.DOUBLE_REQUEST.h);
        return a(a2.h, a2.i, "");
    }

    private static String d() {
        i a2 = i.a(i.PARAMS_ERROR.h);
        return a(a2.h, a2.i, "");
    }

    public static String a(int i, String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("resultStatus={").append(i).append("};memo={").append(str).append("};result={").append(str2).append(com.alipay.sdk.util.h.d);
        return sb.toString();
    }
}

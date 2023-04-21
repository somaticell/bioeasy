package com.alipay.b.a.a.e;

import cn.com.bioeasy.app.utils.ListUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class a {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;

    public a(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = str5;
        this.f = str6;
        this.g = str7;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(Calendar.getInstance().getTime()));
        stringBuffer.append(ListUtils.DEFAULT_JOIN_SEPARATOR + this.a);
        stringBuffer.append(ListUtils.DEFAULT_JOIN_SEPARATOR + this.b);
        stringBuffer.append(ListUtils.DEFAULT_JOIN_SEPARATOR + this.c);
        stringBuffer.append(ListUtils.DEFAULT_JOIN_SEPARATOR + this.d);
        if (com.alipay.b.a.a.a.a.a(this.e) || this.e.length() < 20) {
            stringBuffer.append(ListUtils.DEFAULT_JOIN_SEPARATOR + this.e);
        } else {
            stringBuffer.append(ListUtils.DEFAULT_JOIN_SEPARATOR + this.e.substring(0, 20));
        }
        if (com.alipay.b.a.a.a.a.a(this.f) || this.f.length() < 20) {
            stringBuffer.append(ListUtils.DEFAULT_JOIN_SEPARATOR + this.f);
        } else {
            stringBuffer.append(ListUtils.DEFAULT_JOIN_SEPARATOR + this.f.substring(0, 20));
        }
        if (com.alipay.b.a.a.a.a.a(this.g) || this.g.length() < 20) {
            stringBuffer.append(ListUtils.DEFAULT_JOIN_SEPARATOR + this.g);
        } else {
            stringBuffer.append(ListUtils.DEFAULT_JOIN_SEPARATOR + this.g.substring(0, 20));
        }
        return stringBuffer.toString();
    }
}

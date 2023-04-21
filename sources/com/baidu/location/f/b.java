package com.baidu.location.f;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import com.baidu.android.bbalbs.common.util.CommonParam;
import com.baidu.location.a.m;
import com.baidu.location.f;
import java.util.Locale;

public class b {
    public static String d = null;
    public static String e = null;
    public static String f = null;
    public static String g = null;
    private static b h = null;
    public String a = null;
    public String b = null;
    public String c = null;
    private boolean i = false;

    private b() {
        if (f.getServiceContext() != null) {
            a(f.getServiceContext());
        }
    }

    public static b a() {
        if (h == null) {
            h = new b();
        }
        return h;
    }

    public String a(boolean z) {
        return a(z, (String) null);
    }

    public String a(boolean z, String str) {
        StringBuffer stringBuffer = new StringBuffer(256);
        stringBuffer.append("&sdk=");
        stringBuffer.append(7.53f);
        if (z) {
            if (j.g.equals("all")) {
                stringBuffer.append("&addr=allj");
            }
            if (j.h || j.j || j.k || j.i) {
                stringBuffer.append("&sema=");
                if (j.h) {
                    stringBuffer.append("aptag|");
                }
                if (j.i) {
                    stringBuffer.append("aptagd|");
                }
                if (j.j) {
                    stringBuffer.append("poiregion|");
                }
                if (j.k) {
                    stringBuffer.append("regular");
                }
            }
        }
        if (z) {
            if (str == null) {
                stringBuffer.append("&coor=gcj02");
            } else {
                stringBuffer.append("&coor=");
                stringBuffer.append(str);
            }
        }
        if (this.b == null) {
            stringBuffer.append("&im=");
            stringBuffer.append(this.a);
        } else {
            stringBuffer.append("&cu=");
            stringBuffer.append(this.b);
            if (this.a != null && !this.a.equals("NULL") && !this.b.contains(new StringBuffer(this.a).reverse().toString())) {
                stringBuffer.append("&Aim=");
                stringBuffer.append(this.a);
            }
        }
        if (this.c != null) {
            stringBuffer.append("&Aid=");
            stringBuffer.append(this.c);
        }
        stringBuffer.append("&fw=");
        stringBuffer.append(f.getFrameVersion());
        stringBuffer.append("&lt=1");
        stringBuffer.append("&mb=");
        stringBuffer.append(Build.MODEL);
        String b2 = j.b();
        if (b2 != null) {
            stringBuffer.append("&laip=");
            stringBuffer.append(b2);
        }
        float d2 = m.a().d();
        if (d2 != 0.0f) {
            stringBuffer.append("&altv=");
            stringBuffer.append(String.format(Locale.US, "%.5f", new Object[]{Float.valueOf(d2)}));
        }
        stringBuffer.append("&resid=");
        stringBuffer.append("12");
        stringBuffer.append("&os=A");
        stringBuffer.append(Build.VERSION.SDK);
        if (z) {
            stringBuffer.append("&sv=");
            String str2 = Build.VERSION.RELEASE;
            if (str2 != null && str2.length() > 6) {
                str2 = str2.substring(0, 6);
            }
            stringBuffer.append(str2);
        }
        return stringBuffer.toString();
    }

    public void a(Context context) {
        if (context != null && !this.i) {
            try {
                this.a = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            } catch (Exception e2) {
                this.a = "NULL";
            }
            try {
                this.b = CommonParam.a(context);
            } catch (Exception e3) {
                this.b = null;
            }
            try {
                this.c = com.baidu.android.bbalbs.common.util.b.b(context);
            } catch (Exception e4) {
                this.c = null;
            }
            try {
                d = context.getPackageName();
            } catch (Exception e5) {
                d = null;
            }
            j.n = "" + this.b;
            this.i = true;
        }
    }

    public void a(String str, String str2) {
        e = str;
        d = str2;
    }

    public String b() {
        return this.b != null ? "v7.53|" + this.b + "|" + Build.MODEL : "v7.53|" + this.a + "|" + Build.MODEL;
    }

    public String c() {
        StringBuffer stringBuffer = new StringBuffer(200);
        if (this.b != null) {
            stringBuffer.append("&cu=");
            stringBuffer.append(this.b);
        } else {
            stringBuffer.append("&im=");
            stringBuffer.append(this.a);
        }
        try {
            stringBuffer.append("&mb=");
            stringBuffer.append(Build.MODEL);
        } catch (Exception e2) {
        }
        stringBuffer.append("&pack=");
        try {
            stringBuffer.append(d);
        } catch (Exception e3) {
        }
        stringBuffer.append("&sdk=");
        stringBuffer.append(7.53f);
        return stringBuffer.toString();
    }

    public String d() {
        return d != null ? b() + "|" + d : b();
    }
}

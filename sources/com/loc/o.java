package com.loc;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import com.alipay.sdk.cons.a;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.PublicKey;
import java.util.Map;

/* compiled from: ClientInfo */
public class o {
    public static String a(Context context, v vVar, Map<String, String> map, boolean z) {
        byte[] a;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            a(byteArrayOutputStream, q.l(context));
            a(byteArrayOutputStream, q.d(context));
            String a2 = q.a(context);
            if (a2 == null) {
                a2 = "";
            }
            a(byteArrayOutputStream, a2);
            a(byteArrayOutputStream, m.b(context));
            a(byteArrayOutputStream, Build.MODEL);
            a(byteArrayOutputStream, Build.MANUFACTURER);
            a(byteArrayOutputStream, Build.DEVICE);
            a(byteArrayOutputStream, m.a(context));
            a(byteArrayOutputStream, m.c(context));
            a(byteArrayOutputStream, String.valueOf(Build.VERSION.SDK_INT));
            a(byteArrayOutputStream, q.m(context));
            a(byteArrayOutputStream, q.k(context));
            a(byteArrayOutputStream, q.h(context) + "");
            a(byteArrayOutputStream, q.g(context) + "");
            a(byteArrayOutputStream, q.n(context));
            a(byteArrayOutputStream, q.f(context));
            if (z) {
                a(byteArrayOutputStream, "");
            } else {
                a(byteArrayOutputStream, q.c(context));
            }
            if (z) {
                a(byteArrayOutputStream, "");
            } else {
                a(byteArrayOutputStream, q.b(context));
            }
            if (z) {
                a(byteArrayOutputStream, "");
                a(byteArrayOutputStream, "");
            } else {
                String[] e = q.e(context);
                a(byteArrayOutputStream, e[0]);
                a(byteArrayOutputStream, e[1]);
            }
            byte[] a3 = w.a(byteArrayOutputStream.toByteArray());
            PublicKey a4 = w.a(context);
            if (a3.length > 117) {
                byte[] bArr = new byte[117];
                System.arraycopy(a3, 0, bArr, 0, 117);
                byte[] a5 = r.a(bArr, a4);
                a = new byte[((a3.length + 128) - 117)];
                System.arraycopy(a5, 0, a, 0, 128);
                System.arraycopy(a3, 117, a, 128, a3.length - 117);
            } else {
                a = r.a(a3, a4);
            }
            return r.a(a);
        } catch (Throwable th) {
            y.a(th, "CInfo", "InitXInfo");
            return null;
        }
    }

    private static void a(ByteArrayOutputStream byteArrayOutputStream, String str) {
        byte length;
        if (!TextUtils.isEmpty(str)) {
            if (str.getBytes().length > 255) {
                length = -1;
            } else {
                length = (byte) str.getBytes().length;
            }
            try {
                a(byteArrayOutputStream, length, str.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                a(byteArrayOutputStream, length, str.getBytes());
            }
        } else {
            a(byteArrayOutputStream, (byte) 0, new byte[0]);
        }
    }

    private static void a(ByteArrayOutputStream byteArrayOutputStream, byte b, byte[] bArr) {
        boolean z = true;
        try {
            byteArrayOutputStream.write(new byte[]{b});
            boolean z2 = b > 0;
            if ((b & BLEServiceApi.CONNECTED_STATUS) >= 255) {
                z = false;
            }
            if (z && z2) {
                byteArrayOutputStream.write(bArr);
            } else if ((b & BLEServiceApi.CONNECTED_STATUS) == 255) {
                byteArrayOutputStream.write(bArr, 0, 255);
            }
        } catch (IOException e) {
            y.a(e, "CInfo", "writeField");
        }
    }

    public static String a() {
        Throwable th;
        String str;
        try {
            String valueOf = String.valueOf(System.currentTimeMillis());
            try {
                int length = valueOf.length();
                return valueOf.substring(0, length - 2) + a.e + valueOf.substring(length - 1);
            } catch (Throwable th2) {
                Throwable th3 = th2;
                str = valueOf;
                th = th3;
            }
        } catch (Throwable th4) {
            Throwable th5 = th4;
            str = null;
            th = th5;
            y.a(th, "CInfo", "getTS");
            return str;
        }
    }

    public static String a(Context context, String str, String str2) {
        try {
            return s.b(m.d(context) + ":" + str.substring(0, str.length() - 3) + ":" + str2);
        } catch (Throwable th) {
            y.a(th, "CInfo", "Scode");
            return null;
        }
    }
}

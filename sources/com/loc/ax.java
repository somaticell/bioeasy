package com.loc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.alipay.sdk.util.e;
import com.autonavi.aps.amapapi.model.AmapLoc;
import com.loc.k;

/* compiled from: ConnectionServiceManager */
public class ax {
    a a = null;
    private String b = null;
    private Context c = null;
    private boolean d = true;
    /* access modifiers changed from: private */
    public k e = null;
    private ServiceConnection f = null;
    private ServiceConnection g = null;
    private Intent h = new Intent();
    private String i = "com.autonavi.minimap";
    private String j = "com.amap.api.service.AMapService";
    private String k = "com.taobao.agoo.PushService";
    private boolean l = false;
    private boolean m = false;
    private String n = "invaid type";
    private String o = "empty appkey";
    private String p = "refused";
    private String q = e.b;

    /* compiled from: ConnectionServiceManager */
    public interface a {
        void a(int i);
    }

    ax(Context context) {
        this.c = context;
        try {
            this.b = r.a(bh.a(m.e(context).getBytes("UTF-8"), "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDCEYwdO3V2ANrhApjqyk7X8FH5AEaWly58kP9IDAhMqwtIbmcJrUK9oO9Afh3KZnOlDtjiowy733YqpLRO7WBvdbW/c4Dz/d3dy/m+6HMqxaak+GQQRHw/VPdKciaZ3eIZp4MWOyIQwiFSQvPTAo/Na8hV4SgBZHB3lGFw0yu+BmG+h32eIE6p4Y8EDCn+G+yzekX+taMrWTQIysledrygZSGPv1ukbdFDnH/xZEI0dCr9pZT+AZQl3o9a2aMyuRrHM0oupXKKiYl69Y8fKh1Tyd752rF6LrR5uOb9aOfXt18hb+3YL5P9rQ+ZRYbyHYFaxzBPA2jLq0KUQ+Dmg7YhAgMBAAECggEAL9pj0lF3BUHwtssNKdf42QZJMD0BKuDcdZrLV9ifs0f54EJY5enzKw8j76MpdV8N5QVkNX4/BZR0bs9uJogh31oHFs5EXeWbb7V8P7bRrxpNnSAijGBWwscQsyqymf48YlcL28949ujnjoEz3jQjgWOyYnrCgpVhphrQbCGmB5TcZnTFvHfozt/0tzuMj5na5lRnkD0kYXgr0x/SRZcPoCybSpc3t/B/9MAAboGaV/QQkTotr7VOuJfaPRjvg8rzyPzavo3evxsjXj7vDXbN4w0cbk/Uqn2JtvPQ8HoysmF2HdYvILZibvJmWH1hA58b4sn5s6AqFRjMOL7rHdD+gQKBgQD+IzoofmZK5tTxgO9sWsG71IUeshQP9fe159jKCehk1RfuIqqbRP0UcxJiw4eNjHs4zU0HeRL3iF5XfUs0FQanO/pp6YL1xgVdfQlDdTdk6KFHJ0sUJapnJn1S2k7IKfRKE1+rkofSXMYUTsgHF1fDp+gxy4yUMY+h9O+JlKVKOwKBgQDDfaDIblaSm+B0lyG//wFPynAeGd0Q8wcMZbQQ/LWMJZhMZ7fyUZ+A6eL/jB53a2tgnaw2rXBpMe1qu8uSpym2plU0fkgLAnVugS5+KRhOkUHyorcbpVZbs5azf7GlTydR5dI1PHF3Bncemoa6IsEvumHWgQbVyTTz/O9mlFafUwKBgQCvDebms8KUf5JY1F6XfaCLWGVl8nZdVCmQFKbA7Lg2lI5KS3jHQWsupeEZRORffU/3nXsc1apZ9YY+r6CYvI77rRXd1KqPzxos/o7d96TzjkZhc9CEjTlmmh2jb5rqx/Ns/xFcZq/GGH+cx3ODZvHeZQ9NFY+9GLJ+dfB2DX0ZtwKBgQC+9/lZ8telbpqMqpqwqRaJ8LMn5JIdHZu0E6IcuhFLr+ogMW3zTKMpVtGGXEXi2M/TWRPDchiO2tQX4Q5T2/KW19QCbJ5KCwPWiGF3owN4tNOciDGh0xkSidRc0xAh8bnyejSoBry8zlcNUVztdkgMLOGonvCjZWPSOTNQnPYluwKBgCV+WVftpTk3l+OfAJTaXEPNYdh7+WQjzxZKjUaDzx80Ts7hRo2U+EQT7FBjQQNqmmDnWtujo5p1YmJC0FT3n1CVa7g901pb3b0RcOziYWAoJi0/+kLyeo6XBhuLeZ7h90S70GGh1o0V/j/9N1jb5DCL4xKkvdYePPTSTku0BM+n"));
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void a() {
        c();
        this.c = null;
        this.e = null;
        this.f = null;
        this.g = null;
        if (this.a != null) {
            this.a.a(-1);
        }
        this.d = true;
        this.l = false;
        this.m = false;
    }

    public void a(final a aVar) {
        try {
            this.a = aVar;
            if (this.f == null) {
                this.f = new ServiceConnection() {
                    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                        k unused = ax.this.e = k.a.a(iBinder);
                        aVar.a(0);
                    }

                    public void onServiceDisconnected(ComponentName componentName) {
                        k unused = ax.this.e = null;
                    }
                };
            }
            if (this.g == null) {
                this.g = new ServiceConnection() {
                    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    }

                    public void onServiceDisconnected(ComponentName componentName) {
                    }
                };
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean b() {
        try {
            this.h.putExtra(com.alipay.sdk.sys.a.f, this.b);
            this.h.setComponent(new ComponentName(this.i, this.j));
            this.l = this.c.bindService(this.h, this.f, 1);
            Intent intent = new Intent();
            intent.putExtra(com.alipay.sdk.sys.a.f, this.b);
            intent.setComponent(new ComponentName(this.i, this.k));
            this.m = this.c.bindService(intent, this.g, 1);
            if (!this.l || !this.m) {
                return false;
            }
            return true;
        } catch (Exception e2) {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void c() {
        try {
            if (this.l) {
                this.c.unbindService(this.f);
            }
            if (this.m) {
                this.c.unbindService(this.g);
            }
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
        }
        this.e = null;
    }

    /* access modifiers changed from: package-private */
    public AmapLoc d() {
        try {
            if (!this.d || !this.l) {
                return null;
            }
            Bundle bundle = new Bundle();
            bundle.putString("type", "corse");
            bundle.putString(com.alipay.sdk.sys.a.f, this.b);
            this.e.a(bundle);
            if (bundle.size() >= 1) {
                return a(bundle);
            }
            return null;
        } catch (RemoteException e2) {
            return null;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.autonavi.aps.amapapi.model.AmapLoc a(android.os.Bundle r9) {
        /*
            r8 = this;
            r1 = 0
            if (r9 != 0) goto L_0x0004
        L_0x0003:
            return r1
        L_0x0004:
            java.lang.String r0 = "key"
            boolean r0 = r9.containsKey(r0)
            if (r0 == 0) goto L_0x007d
            java.lang.String r0 = "key"
            java.lang.String r0 = r9.getString(r0)
            byte[] r0 = com.loc.r.a((java.lang.String) r0)
            java.lang.String r2 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDCEYwdO3V2ANrhApjqyk7X8FH5AEaWly58kP9IDAhMqwtIbmcJrUK9oO9Afh3KZnOlDtjiowy733YqpLRO7WBvdbW/c4Dz/d3dy/m+6HMqxaak+GQQRHw/VPdKciaZ3eIZp4MWOyIQwiFSQvPTAo/Na8hV4SgBZHB3lGFw0yu+BmG+h32eIE6p4Y8EDCn+G+yzekX+taMrWTQIysledrygZSGPv1ukbdFDnH/xZEI0dCr9pZT+AZQl3o9a2aMyuRrHM0oupXKKiYl69Y8fKh1Tyd752rF6LrR5uOb9aOfXt18hb+3YL5P9rQ+ZRYbyHYFaxzBPA2jLq0KUQ+Dmg7YhAgMBAAECggEAL9pj0lF3BUHwtssNKdf42QZJMD0BKuDcdZrLV9ifs0f54EJY5enzKw8j76MpdV8N5QVkNX4/BZR0bs9uJogh31oHFs5EXeWbb7V8P7bRrxpNnSAijGBWwscQsyqymf48YlcL28949ujnjoEz3jQjgWOyYnrCgpVhphrQbCGmB5TcZnTFvHfozt/0tzuMj5na5lRnkD0kYXgr0x/SRZcPoCybSpc3t/B/9MAAboGaV/QQkTotr7VOuJfaPRjvg8rzyPzavo3evxsjXj7vDXbN4w0cbk/Uqn2JtvPQ8HoysmF2HdYvILZibvJmWH1hA58b4sn5s6AqFRjMOL7rHdD+gQKBgQD+IzoofmZK5tTxgO9sWsG71IUeshQP9fe159jKCehk1RfuIqqbRP0UcxJiw4eNjHs4zU0HeRL3iF5XfUs0FQanO/pp6YL1xgVdfQlDdTdk6KFHJ0sUJapnJn1S2k7IKfRKE1+rkofSXMYUTsgHF1fDp+gxy4yUMY+h9O+JlKVKOwKBgQDDfaDIblaSm+B0lyG//wFPynAeGd0Q8wcMZbQQ/LWMJZhMZ7fyUZ+A6eL/jB53a2tgnaw2rXBpMe1qu8uSpym2plU0fkgLAnVugS5+KRhOkUHyorcbpVZbs5azf7GlTydR5dI1PHF3Bncemoa6IsEvumHWgQbVyTTz/O9mlFafUwKBgQCvDebms8KUf5JY1F6XfaCLWGVl8nZdVCmQFKbA7Lg2lI5KS3jHQWsupeEZRORffU/3nXsc1apZ9YY+r6CYvI77rRXd1KqPzxos/o7d96TzjkZhc9CEjTlmmh2jb5rqx/Ns/xFcZq/GGH+cx3ODZvHeZQ9NFY+9GLJ+dfB2DX0ZtwKBgQC+9/lZ8telbpqMqpqwqRaJ8LMn5JIdHZu0E6IcuhFLr+ogMW3zTKMpVtGGXEXi2M/TWRPDchiO2tQX4Q5T2/KW19QCbJ5KCwPWiGF3owN4tNOciDGh0xkSidRc0xAh8bnyejSoBry8zlcNUVztdkgMLOGonvCjZWPSOTNQnPYluwKBgCV+WVftpTk3l+OfAJTaXEPNYdh7+WQjzxZKjUaDzx80Ts7hRo2U+EQT7FBjQQNqmmDnWtujo5p1YmJC0FT3n1CVa7g901pb3b0RcOziYWAoJi0/+kLyeo6XBhuLeZ7h90S70GGh1o0V/j/9N1jb5DCL4xKkvdYePPTSTku0BM+n"
            byte[] r0 = com.loc.bh.b(r0, r2)     // Catch:{ Exception -> 0x0079 }
        L_0x001c:
            java.lang.String r2 = "result"
            boolean r2 = r9.containsKey(r2)
            if (r2 == 0) goto L_0x0003
            java.lang.String r2 = "result"
            java.lang.String r2 = r9.getString(r2)
            byte[] r2 = com.loc.r.a((java.lang.String) r2)
            byte[] r0 = com.loc.bh.a((byte[]) r0, (byte[]) r2)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r2 = new java.lang.String     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r3 = "utf-8"
            r2.<init>(r0, r3)     // Catch:{ Exception -> 0x00c6 }
            if (r2 == 0) goto L_0x0003
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x00c6 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r0 = "error"
            boolean r0 = r3.has(r0)     // Catch:{ Exception -> 0x00c6 }
            if (r0 == 0) goto L_0x007f
            java.lang.String r0 = "error"
            java.lang.String r0 = r3.getString(r0)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r2 = r8.n     // Catch:{ Exception -> 0x00c6 }
            boolean r2 = r2.equals(r0)     // Catch:{ Exception -> 0x00c6 }
            if (r2 == 0) goto L_0x005a
            r2 = 0
            r8.d = r2     // Catch:{ Exception -> 0x00c6 }
        L_0x005a:
            java.lang.String r2 = r8.o     // Catch:{ Exception -> 0x00c6 }
            boolean r2 = r2.equals(r0)     // Catch:{ Exception -> 0x00c6 }
            if (r2 == 0) goto L_0x0065
            r2 = 0
            r8.d = r2     // Catch:{ Exception -> 0x00c6 }
        L_0x0065:
            java.lang.String r2 = r8.p     // Catch:{ Exception -> 0x00c6 }
            boolean r2 = r2.equals(r0)     // Catch:{ Exception -> 0x00c6 }
            if (r2 == 0) goto L_0x0070
            r2 = 0
            r8.d = r2     // Catch:{ Exception -> 0x00c6 }
        L_0x0070:
            java.lang.String r2 = r8.q     // Catch:{ Exception -> 0x00c6 }
            boolean r0 = r2.equals(r0)     // Catch:{ Exception -> 0x00c6 }
            if (r0 == 0) goto L_0x0003
            goto L_0x0003
        L_0x0079:
            r0 = move-exception
            r0.printStackTrace()
        L_0x007d:
            r0 = r1
            goto L_0x001c
        L_0x007f:
            com.autonavi.aps.amapapi.model.AmapLoc r0 = new com.autonavi.aps.amapapi.model.AmapLoc     // Catch:{ Exception -> 0x00c6 }
            r0.<init>((org.json.JSONObject) r3)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r2 = "lbs"
            r0.c((java.lang.String) r2)     // Catch:{ Exception -> 0x00c6 }
            r2 = 7
            r0.a((int) r2)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r2 = "WGS84"
            java.lang.String r3 = r0.l()     // Catch:{ Exception -> 0x00c6 }
            boolean r2 = r2.equals(r3)     // Catch:{ Exception -> 0x00c6 }
            if (r2 == 0) goto L_0x00c3
            double r2 = r0.i()     // Catch:{ Exception -> 0x00c6 }
            double r4 = r0.h()     // Catch:{ Exception -> 0x00c6 }
            boolean r2 = com.loc.e.a(r2, r4)     // Catch:{ Exception -> 0x00c6 }
            if (r2 == 0) goto L_0x00c3
            android.content.Context r2 = r8.c     // Catch:{ Exception -> 0x00c6 }
            double r4 = r0.h()     // Catch:{ Exception -> 0x00c6 }
            double r6 = r0.i()     // Catch:{ Exception -> 0x00c6 }
            com.amap.api.location.DPoint r2 = com.loc.j.a(r2, r4, r6)     // Catch:{ Exception -> 0x00c6 }
            double r4 = r2.getLatitude()     // Catch:{ Exception -> 0x00c6 }
            r0.b((double) r4)     // Catch:{ Exception -> 0x00c6 }
            double r2 = r2.getLongitude()     // Catch:{ Exception -> 0x00c6 }
            r0.a((double) r2)     // Catch:{ Exception -> 0x00c6 }
        L_0x00c3:
            r1 = r0
            goto L_0x0003
        L_0x00c6:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0003
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.ax.a(android.os.Bundle):com.autonavi.aps.amapapi.model.AmapLoc");
    }
}

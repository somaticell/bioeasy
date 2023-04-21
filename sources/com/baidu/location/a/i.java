package com.baidu.location.a;

import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.d.g;
import com.baidu.location.d.h;
import com.baidu.location.f;
import com.baidu.location.f.e;
import com.baidu.location.f.j;
import java.util.HashMap;
import java.util.Locale;

public abstract class i {
    public static String c = null;
    public g a = null;
    public com.baidu.location.d.a b = null;
    final Handler d = new a();
    private boolean e = true;
    private boolean f = true;
    private boolean g = false;
    /* access modifiers changed from: private */
    public String h = null;
    /* access modifiers changed from: private */
    public String i = null;

    public class a extends Handler {
        public a() {
        }

        public void handleMessage(Message message) {
            if (f.isServing) {
                switch (message.what) {
                    case 21:
                        i.this.a(message);
                        return;
                    case 62:
                    case 63:
                        i.this.a();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    class b extends e {
        String a = null;
        String b = null;

        public b() {
            this.k = new HashMap();
        }

        public void a() {
            this.h = j.c();
            if (!((!j.h && !j.i) || i.this.h == null || i.this.i == null)) {
                this.b += String.format(Locale.CHINA, "&ki=%s&sn=%s", new Object[]{i.this.h, i.this.i});
            }
            String encodeTp4 = Jni.encodeTp4(this.b);
            this.b = null;
            if (this.a == null) {
                this.a = v.b();
            }
            this.k.put("bloc", encodeTp4);
            if (this.a != null) {
                this.k.put("up", this.a);
            }
            this.k.put("trtm", String.format(Locale.CHINA, "%d", new Object[]{Long.valueOf(System.currentTimeMillis())}));
        }

        public void a(String str) {
            this.b = str;
            c(j.f);
        }

        public void a(boolean z) {
            BDLocation bDLocation;
            if (!z || this.j == null) {
                Message obtainMessage = i.this.d.obtainMessage(63);
                obtainMessage.obj = "HttpStatus error";
                obtainMessage.sendToTarget();
            } else {
                try {
                    String str = this.j;
                    i.c = str;
                    try {
                        bDLocation = new BDLocation(str);
                        if (bDLocation.getLocType() == 161) {
                            h.a().a(str);
                        }
                        bDLocation.setOperators(com.baidu.location.d.b.a().h());
                        if (n.a().d()) {
                            bDLocation.setDirection(n.a().e());
                        }
                    } catch (Exception e) {
                        bDLocation = new BDLocation();
                        bDLocation.setLocType(0);
                    }
                    this.a = null;
                    if (bDLocation.getLocType() == 0 && bDLocation.getLatitude() == Double.MIN_VALUE && bDLocation.getLongitude() == Double.MIN_VALUE) {
                        Message obtainMessage2 = i.this.d.obtainMessage(63);
                        obtainMessage2.obj = "HttpStatus error";
                        obtainMessage2.sendToTarget();
                    } else {
                        Message obtainMessage3 = i.this.d.obtainMessage(21);
                        obtainMessage3.obj = bDLocation;
                        obtainMessage3.sendToTarget();
                    }
                } catch (Exception e2) {
                    Message obtainMessage4 = i.this.d.obtainMessage(63);
                    obtainMessage4.obj = "HttpStatus error";
                    obtainMessage4.sendToTarget();
                }
            }
            if (this.k != null) {
                this.k.clear();
            }
        }
    }

    public String a(String str) {
        String m;
        if (this.h == null) {
            this.h = j.b(f.getServiceContext());
        }
        if (this.i == null) {
            this.i = j.c(f.getServiceContext());
        }
        if (this.b == null || !this.b.a()) {
            this.b = com.baidu.location.d.b.a().f();
        }
        if (this.a == null || !this.a.i()) {
            this.a = h.a().p();
        }
        Location h2 = com.baidu.location.d.e.a().j() ? com.baidu.location.d.e.a().h() : null;
        if ((this.b == null || this.b.d() || this.b.c()) && ((this.a == null || this.a.a() == 0) && h2 == null)) {
            return null;
        }
        String b2 = b();
        if (h.a().d() == -2) {
            b2 = b2 + "&imo=1";
        }
        int b3 = j.b(f.getServiceContext());
        if (b3 >= 0) {
            b2 = b2 + "&lmd=" + b3;
        }
        String str2 = ((this.a == null || this.a.a() == 0) && (m = h.a().m()) != null) ? m + b2 : b2;
        if (!this.f) {
            return j.a(this.b, this.a, h2, str2, 0);
        }
        this.f = false;
        return j.a(this.b, this.a, h2, str2, 0, true);
    }

    public abstract void a();

    public abstract void a(Message message);

    public String b() {
        String format;
        String f2 = a.a().f();
        if (h.j()) {
            format = "&cn=32";
        } else {
            format = String.format(Locale.CHINA, "&cn=%d", new Object[]{Integer.valueOf(com.baidu.location.d.b.a().e())});
        }
        if (this.e) {
            this.e = false;
            String s = h.a().s();
            if (!TextUtils.isEmpty(s) && !s.equals("02:00:00:00:00:00")) {
                format = String.format(Locale.CHINA, "%s&mac=%s", new Object[]{format, s.replace(":", "")});
            }
            if (Build.VERSION.SDK_INT > 17) {
            }
        } else if (!this.g) {
            String f3 = v.f();
            if (f3 != null) {
                format = format + f3;
            }
            this.g = true;
        }
        return format + f2;
    }
}

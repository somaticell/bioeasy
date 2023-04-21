package com.loc;

import android.content.Context;
import android.os.Build;
import com.loc.ah;
import com.loc.am;
import com.loc.aq;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

/* compiled from: DexDownLoad */
public class ad extends Thread implements aq.a {
    private ae a;
    private aq b;
    private v c;
    private String d;
    private RandomAccessFile e;
    private String f;
    private Context g;
    private String h;
    private String i;
    private String j;
    private String k;
    private int l;
    private int m;

    public ad(Context context, ae aeVar, v vVar) {
        try {
            this.g = context.getApplicationContext();
            this.c = vVar;
            if (aeVar != null) {
                this.a = aeVar;
                this.b = new aq(new ag(this.a));
                String[] split = this.a.a().split("/");
                this.f = split[split.length - 1];
                String[] split2 = this.f.split("_");
                this.h = split2[0];
                this.i = aeVar.c();
                this.j = split2[1];
                this.l = Integer.parseInt(split2[3]);
                this.m = Integer.parseInt(split2[4].split("\\.")[0]);
                this.k = aeVar.b();
                this.d = ah.a(context, this.f);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void a() {
        try {
            start();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void run() {
        try {
            if (e()) {
                this.b.a(this);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private boolean a(aa aaVar, am amVar, String str, String str2, String str3, String str4) {
        if ("errorstatus".equals(amVar.f())) {
            if (!new File(ah.a(this.g, this.c.a(), this.c.b())).exists()) {
                ah.a(this.g, aaVar, this.c);
                ai.b(this.g, this.c, ah.a(this.g, this.h, this.c.b()), ah.a(this.g), (String) null, this.g.getClassLoader());
            }
            return true;
        } else if (!new File(this.d).exists()) {
            return false;
        } else {
            List c2 = aaVar.c(al.a(ah.b(str, str2), str, str2, str3), new al());
            if (c2 != null && c2.size() > 0) {
                return true;
            }
            try {
                ah.a(this.g, aaVar, this.c, new am.a(ah.b(str, this.c.b()), str4, str, str2, str3).a("usedex").a(), this.d);
                ai.b(this.g, this.c, ah.a(this.g, this.h, this.c.b()), ah.a(this.g), (String) null, this.g.getClassLoader());
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
            }
            return true;
        }
    }

    private boolean a(String str, String str2, String str3, String str4, String str5) {
        aa aaVar = new aa(this.g, ak.c());
        try {
            List c2 = aaVar.c(al.b(str3, "usedex"), new al());
            if (c2 != null && c2.size() > 0 && an.a(((am) c2.get(0)).e(), this.j) > 0) {
                return true;
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        am a2 = ah.a.a(aaVar, str);
        if (a2 != null) {
            return a(aaVar, a2, str3, str4, str2, str5);
        }
        return false;
    }

    private boolean a(String str, String str2) {
        if (this.c != null && this.c.a().equals(str) && this.c.b().equals(str2)) {
            return true;
        }
        return false;
    }

    private boolean d() {
        return Build.VERSION.SDK_INT >= this.m && Build.VERSION.SDK_INT <= this.l;
    }

    private boolean a(Context context) {
        return q.h(context) == 1;
    }

    private boolean e() {
        try {
            if (a(this.h, this.i)) {
                if (!a(this.f, this.j, this.h, this.i, this.k) && a(this.g) && d()) {
                    a(this.c.a());
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            ah.a(th);
            th.printStackTrace();
            return false;
        }
    }

    private void a(String str) {
        aa aaVar = new aa(this.g, ak.c());
        List c2 = aaVar.c(al.b(str, "copy"), new al());
        ah.a((List<am>) c2);
        if (c2 != null && c2.size() > 1) {
            int size = c2.size();
            for (int i2 = 1; i2 < size; i2++) {
                ah.a(this.g, aaVar, ((am) c2.get(i2)).a());
            }
        }
    }

    public void a(byte[] bArr, long j2) {
        try {
            if (this.e == null) {
                File file = new File(this.d);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                this.e = new RandomAccessFile(file, "rw");
            }
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
            return;
        }
        try {
            this.e.seek(j2);
            this.e.write(bArr);
        } catch (IOException e3) {
            e3.printStackTrace();
        }
    }

    public void a(Throwable th) {
        try {
            if (this.e != null) {
                this.e.close();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }

    public void c() {
        try {
            if (this.e != null) {
                try {
                    this.e.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                String b2 = this.a.b();
                if (ah.a(this.d, b2)) {
                    String c2 = this.a.c();
                    aa aaVar = new aa(this.g, ak.c());
                    ah.a.a(aaVar, new am.a(this.f, b2, this.h, c2, this.j).a("copy").a(), al.a(this.f, this.h, c2, this.j));
                    ah.a(this.g, aaVar, this.c, new am.a(ah.b(this.h, this.c.b()), b2, this.h, c2, this.j).a("usedex").a(), this.d);
                    ai.b(this.g, this.c, ah.a(this.g, this.h, this.c.b()), ah.a(this.g), (String) null, this.g.getClassLoader());
                    return;
                }
                try {
                    new File(this.d).delete();
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
        } catch (IOException e4) {
            e4.printStackTrace();
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }

    public void b() {
    }
}

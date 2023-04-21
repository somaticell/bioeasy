package com.loc;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.alipay.android.phone.mrpc.core.gwprotocol.JsonSerializer;
import com.loc.aq;
import com.loc.v;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

/* compiled from: SDKCoordinatorDownload */
public class u extends Thread implements aq.a {
    private static String h = "sodownload";
    private static String i = "sofail";
    private aq a = new aq(this.b);
    private a b;
    private RandomAccessFile c;
    private String d;
    private String e;
    private String f;
    private Context g;

    public u(Context context, String str, String str2, String str3) {
        this.g = context;
        this.f = str3;
        this.d = a(context, str + "temp.so");
        this.e = a(context, "libwgs2gcj.so");
        this.b = new a(str2);
    }

    public static String a(Context context, String str) {
        return context.getFilesDir().getAbsolutePath() + File.separator + "libso" + File.separator + str;
    }

    private static String b(Context context, String str) {
        return a(context, str);
    }

    public void a() {
        if (this.b != null && !TextUtils.isEmpty(this.b.c()) && this.b.c().contains("libJni_wgs2gcj.so") && this.b.c().contains(Build.CPU_ABI) && !new File(this.e).exists()) {
            start();
        }
    }

    public void run() {
        try {
            File file = new File(b(this.g, "tempfile"));
            if (file.exists()) {
                n.a(this.g, new v.a(i, JsonSerializer.VERSION, "sodownload_1.0.0").a(new String[0]).a());
                file.delete();
            }
            this.a.a(this);
        } catch (Throwable th) {
            y.a(th, "SDKCoordinatorDownload", "run");
            d();
        }
    }

    public void a(byte[] bArr, long j) {
        try {
            if (this.c == null) {
                File file = new File(this.d);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                this.c = new RandomAccessFile(file, "rw");
            }
        } catch (FileNotFoundException e2) {
            y.a(e2, "SDKCoordinatorDownload", "onDownload");
            d();
        } catch (Throwable th) {
            d();
            y.a(th, "SDKCoordinatorDownload", "onDownload");
            return;
        }
        try {
            this.c.seek(j);
            this.c.write(bArr);
        } catch (IOException e3) {
            d();
            y.a(e3, "SDKCoordinatorDownload", "onDownload");
        }
    }

    public void b() {
        d();
    }

    public void c() {
        try {
            if (this.c != null) {
                this.c.close();
            }
            if (!s.a(this.d).equalsIgnoreCase(this.f)) {
                d();
                n.a(this.g, new v.a(i, JsonSerializer.VERSION, "sodownload_1.0.0").a(new String[0]).a());
            } else if (new File(this.e).exists()) {
                d();
            } else {
                new File(this.d).renameTo(new File(this.e));
                n.a(this.g, new v.a(h, JsonSerializer.VERSION, "sodownload_1.0.0").a(new String[0]).a());
            }
        } catch (Throwable th) {
            d();
            File file = new File(this.e);
            if (file.exists()) {
                file.delete();
            }
            try {
                n.a(this.g, new v.a(i, JsonSerializer.VERSION, "sodownload_1.0.0").a(new String[0]).a());
            } catch (l e2) {
                e2.printStackTrace();
            }
            y.a(th, "SDKCoordinatorDownload", "onDownload");
        }
    }

    public void a(Throwable th) {
        try {
            if (this.c != null) {
                this.c.close();
            }
            d();
            File file = new File(b(this.g, "tempfile"));
            if (!file.exists()) {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdir();
                }
                file.createNewFile();
            }
        } catch (IOException e2) {
            y.a(e2, "SDKCoordinatorDownload", "onException");
        } catch (Throwable th2) {
            y.a(th2, "SDKCoordinatorDownload", "onException");
        }
    }

    private void d() {
        File file = new File(this.d);
        if (file.exists()) {
            file.delete();
        }
    }

    /* compiled from: SDKCoordinatorDownload */
    private static class a extends at {
        private String d;

        a(String str) {
            this.d = str;
        }

        public Map<String, String> a() {
            return null;
        }

        public Map<String, String> b() {
            return null;
        }

        public String c() {
            return this.d;
        }
    }
}

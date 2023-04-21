package com.baidu.location.indoor.mapversion.b;

import android.content.Context;
import cn.com.bioeasy.app.utils.ListUtils;
import com.baidu.location.BDLocation;
import com.baidu.platform.comapi.location.CoordinateType;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.HashMap;

public class a {
    private static a a;
    /* access modifiers changed from: private */
    public c b;
    /* access modifiers changed from: private */
    public String c;
    /* access modifiers changed from: private */
    public boolean d = false;
    /* access modifiers changed from: private */
    public String e = "rn";
    private b f;
    private String g = CoordinateType.GCJ02;
    private HashMap<String, d> h = new HashMap<>();

    /* renamed from: com.baidu.location.indoor.mapversion.b.a$a  reason: collision with other inner class name */
    public static class C0011a {
        public double a;
        public double b;
        public double c;
        public double d;
        public double e;
        public double f;
        public double g;
        public double h;

        public C0011a(String str) {
            a(str);
        }

        public void a(String str) {
            String[] split = str.trim().split("\\|");
            this.a = Double.valueOf(split[0]).doubleValue();
            this.b = Double.valueOf(split[1]).doubleValue();
            this.c = Double.valueOf(split[2]).doubleValue();
            this.d = Double.valueOf(split[3]).doubleValue();
            this.e = Double.valueOf(split[4]).doubleValue();
            this.f = Double.valueOf(split[5]).doubleValue();
            this.g = Double.valueOf(split[6]).doubleValue();
            this.h = Double.valueOf(split[7]).doubleValue();
        }
    }

    private class b extends Thread {
        private String b;
        private String c;

        public b(String str, String str2) {
            this.b = str;
            this.c = str2;
        }

        public void run() {
            String str;
            boolean z;
            try {
                File file = new File(a.this.e);
                if (!file.exists() || !file.isDirectory()) {
                    file.mkdirs();
                }
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://loc.map.baidu.com/cfgs/indoorloc/indoorroadnet").openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.addRequestProperty("Accept-encoding", "gzip");
                httpURLConnection.getOutputStream().write(("bldg=" + this.b + "&md5=" + (this.c == null ? "null" : this.c)).getBytes());
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    String headerField = httpURLConnection.getHeaderField("Hash");
                    InputStream inputStream = httpURLConnection.getInputStream();
                    File file2 = new File(file, a.this.a(this.b, headerField));
                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (read < 0) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, read);
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    if (a.a(file2).equalsIgnoreCase(headerField)) {
                        a.this.b(this.b, this.c);
                        String unused = a.this.c = this.b;
                        z = a.this.c();
                        str = "OK";
                    } else {
                        str = "Download failed";
                        file2.delete();
                        z = false;
                    }
                } else if (responseCode == 304) {
                    String unused2 = a.this.c = this.b;
                    z = a.this.c();
                    str = "No roadnet update for " + this.b + ListUtils.DEFAULT_JOIN_SEPARATOR + this.c;
                } else if (responseCode == 204) {
                    str = "Not found bldg " + this.b;
                    z = false;
                } else {
                    str = null;
                    z = false;
                }
                if (a.this.b != null) {
                    a.this.b.a(z, str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            boolean unused3 = a.this.d = false;
        }
    }

    public interface c {
        void a(boolean z, String str);
    }

    public static class d implements Serializable {
        public String a;
        public String b;
        public C0011a c;
        public C0011a d;
        public C0011a e;
        public C0011a f = this.d;
        public short[][] g;
        private String h = CoordinateType.GCJ02;

        public d(String str) {
            this.a = str;
        }

        public double a(double d2) {
            return (this.f.d + d2) * this.f.c;
        }

        public C0011a a() {
            return this.f;
        }

        public void a(String str) {
            if (str != null) {
                this.h = str.toLowerCase();
                if (this.h.startsWith(CoordinateType.WGS84)) {
                    this.f = this.c;
                } else if (this.h.startsWith(BDLocation.BDLOCATION_GCJ02_TO_BD09)) {
                    this.f = this.e;
                } else if (this.h.startsWith(CoordinateType.GCJ02)) {
                    this.f = this.d;
                }
            }
        }

        public double b(double d2) {
            return (this.f.f + d2) * this.f.e;
        }

        public void b(String str) {
            String[] split = str.split("\\t");
            this.b = split[1];
            this.c = new C0011a(split[2]);
            this.e = new C0011a(split[3]);
            this.d = new C0011a(split[4]);
            this.f = this.d;
            this.g = (short[][]) Array.newInstance(Short.TYPE, new int[]{(int) this.f.g, (int) this.f.h});
            for (int i = 0; ((double) i) < this.f.g; i++) {
                for (int i2 = 0; ((double) i2) < this.f.h; i2++) {
                    this.g[i][i2] = (short) (split[5].charAt((((int) this.f.h) * i) + i2) - '0');
                }
            }
        }

        public double c(double d2) {
            return (d2 / this.f.c) - this.f.d;
        }

        public double d(double d2) {
            return (d2 / this.f.e) - this.f.f;
        }
    }

    private a(Context context) {
        this.e = new File(context.getCacheDir(), this.e).getAbsolutePath();
    }

    public static a a() {
        return a;
    }

    public static a a(Context context) {
        if (a == null) {
            a = new a(context);
        }
        return a;
    }

    public static String a(File file) {
        String str;
        Exception e2;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            MappedByteBuffer map = fileInputStream.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(map);
            String bigInteger = new BigInteger(1, instance.digest()).toString(16);
            try {
                fileInputStream.close();
                str = bigInteger;
                int length = 32 - bigInteger.length();
                while (length > 0) {
                    try {
                        length--;
                        str = "0" + str;
                    } catch (Exception e3) {
                        e2 = e3;
                        e2.printStackTrace();
                        return str;
                    }
                }
            } catch (Exception e4) {
                Exception exc = e4;
                str = bigInteger;
                e2 = exc;
                e2.printStackTrace();
                return str;
            }
        } catch (Exception e5) {
            e2 = e5;
            str = null;
        }
        return str;
    }

    /* access modifiers changed from: private */
    public String a(String str, String str2) {
        return c(str) + "_" + str2;
    }

    /* access modifiers changed from: private */
    public void b(String str, String str2) {
        try {
            File file = new File(this.e + "/" + a(str, str2));
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public String c(String str) {
        return str;
    }

    /* access modifiers changed from: private */
    public boolean c() {
        if (this.c == null) {
            return false;
        }
        File e2 = e(this.c);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (!c.a(e2, byteArrayOutputStream)) {
            return false;
        }
        this.h.clear();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                d dVar = new d(this.c);
                dVar.b(readLine);
                dVar.a(this.g);
                this.h.put(dVar.b.toLowerCase(), dVar);
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
        bufferedReader.close();
        return true;
    }

    private String d(String str) {
        int i = 0;
        File file = new File(this.e);
        if (!file.exists() || !file.isDirectory()) {
            return null;
        }
        File[] listFiles = file.listFiles(new b(this, str));
        if (listFiles == null || listFiles.length != 1) {
            while (listFiles != null && i < listFiles.length) {
                listFiles[i].delete();
                i++;
            }
            return null;
        }
        String[] split = listFiles[0].getName().split("_");
        if (split.length >= 2) {
            return split[1];
        }
        return null;
    }

    private File e(String str) {
        return new File(this.e + "/" + a(str, d(str)));
    }

    private boolean f(String str) {
        File e2 = e(str);
        return e2.exists() && e2.length() > 0;
    }

    private boolean g(String str) {
        return System.currentTimeMillis() - e(str).lastModified() > 1296000000;
    }

    private void h(String str) {
        if (!this.d) {
            this.d = true;
            this.f = new b(str, d(str));
            this.f.start();
        }
    }

    public void a(String str) {
        this.g = str;
    }

    public void a(String str, c cVar) {
        if (this.c == null || !str.equals(this.c)) {
            this.b = cVar;
            if (!f(str) || g(str)) {
                h(str);
                return;
            }
            this.c = str;
            c();
            if (this.b != null) {
                this.b.a(true, "OK");
            }
        }
    }

    public d b(String str) {
        return this.h.get(str.toLowerCase());
    }

    public void b() {
        this.h.clear();
        this.c = null;
        this.d = false;
    }
}

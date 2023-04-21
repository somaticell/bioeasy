package com.loc;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;

/* compiled from: BaseNetManager */
public class ap {
    private static ap a;

    public static ap a() {
        if (a == null) {
            a = new ap();
        }
        return a;
    }

    public HttpURLConnection a(at atVar, boolean z) throws l {
        Proxy proxy;
        ar arVar;
        try {
            c(atVar);
            if (atVar.c == null) {
                proxy = null;
            } else {
                proxy = atVar.c;
            }
            if (z) {
                arVar = new ar(atVar.a, atVar.b, proxy, true);
            } else {
                arVar = new ar(atVar.a, atVar.b, proxy, false);
            }
            HttpURLConnection a2 = arVar.a(atVar.d(), atVar.a(), true);
            byte[] e = atVar.e();
            if (e != null && e.length > 0) {
                DataOutputStream dataOutputStream = new DataOutputStream(a2.getOutputStream());
                dataOutputStream.write(e);
                dataOutputStream.close();
            }
            a2.connect();
            return a2;
        } catch (l e2) {
            throw e2;
        } catch (Throwable th) {
            th.printStackTrace();
            throw new l("未知的错误");
        }
    }

    public byte[] a(at atVar) throws l {
        try {
            au b = b(atVar, true);
            if (b != null) {
                return b.a;
            }
            return null;
        } catch (l e) {
            throw e;
        } catch (Throwable th) {
            throw new l("未知的错误");
        }
    }

    public byte[] b(at atVar) throws l {
        try {
            au b = b(atVar, false);
            if (b != null) {
                return b.a;
            }
            return null;
        } catch (l e) {
            throw e;
        } catch (Throwable th) {
            y.a(th, "BaseNetManager", "makeSyncPostRequest");
            throw new l("未知的错误");
        }
    }

    /* access modifiers changed from: protected */
    public void c(at atVar) throws l {
        if (atVar == null) {
            throw new l("requeust is null");
        } else if (atVar.c() == null || "".equals(atVar.c())) {
            throw new l("request url is empty");
        }
    }

    /* access modifiers changed from: protected */
    public au b(at atVar, boolean z) throws l {
        Proxy proxy;
        try {
            c(atVar);
            if (atVar.c == null) {
                proxy = null;
            } else {
                proxy = atVar.c;
            }
            return new ar(atVar.a, atVar.b, proxy, z).a(atVar.d(), atVar.a(), atVar.e());
        } catch (l e) {
            throw e;
        } catch (Throwable th) {
            th.printStackTrace();
            throw new l("未知的错误");
        }
    }
}

package com.tencent.mm.opensdk.diffdev.a;

import android.os.AsyncTask;
import android.util.Log;
import com.tencent.mm.opensdk.diffdev.OAuthErrCode;
import com.tencent.mm.opensdk.diffdev.OAuthListener;

final class f extends AsyncTask<Void, Void, a> {
    private int A;
    private OAuthListener r;
    private String u;
    private String url;

    static class a {
        public String B;
        public int C;
        public OAuthErrCode t;

        a() {
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static com.tencent.mm.opensdk.diffdev.a.f.a b(byte[] r9) {
            /*
                r8 = 1
                r7 = 0
                com.tencent.mm.opensdk.diffdev.a.f$a r0 = new com.tencent.mm.opensdk.diffdev.a.f$a
                r0.<init>()
                if (r9 == 0) goto L_0x000c
                int r1 = r9.length
                if (r1 != 0) goto L_0x0018
            L_0x000c:
                java.lang.String r1 = "MicroMsg.SDK.NoopingResult"
                java.lang.String r2 = "parse fail, buf is null"
                android.util.Log.e(r1, r2)
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_NetworkErr
                r0.t = r1
            L_0x0017:
                return r0
            L_0x0018:
                java.lang.String r1 = new java.lang.String     // Catch:{ Exception -> 0x0068 }
                java.lang.String r2 = "utf-8"
                r1.<init>(r9, r2)     // Catch:{ Exception -> 0x0068 }
                org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x004f }
                r2.<init>(r1)     // Catch:{ Exception -> 0x004f }
                java.lang.String r1 = "wx_errcode"
                int r1 = r2.getInt(r1)     // Catch:{ Exception -> 0x004f }
                r0.C = r1     // Catch:{ Exception -> 0x004f }
                java.lang.String r1 = "MicroMsg.SDK.NoopingResult"
                java.lang.String r3 = "nooping uuidStatusCode = %d"
                r4 = 1
                java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x004f }
                r5 = 0
                int r6 = r0.C     // Catch:{ Exception -> 0x004f }
                java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Exception -> 0x004f }
                r4[r5] = r6     // Catch:{ Exception -> 0x004f }
                java.lang.String r3 = java.lang.String.format(r3, r4)     // Catch:{ Exception -> 0x004f }
                android.util.Log.d(r1, r3)     // Catch:{ Exception -> 0x004f }
                int r1 = r0.C     // Catch:{ Exception -> 0x004f }
                switch(r1) {
                    case 402: goto L_0x009a;
                    case 403: goto L_0x00a0;
                    case 404: goto L_0x008f;
                    case 405: goto L_0x0081;
                    case 408: goto L_0x0094;
                    case 500: goto L_0x00a6;
                    default: goto L_0x004a;
                }     // Catch:{ Exception -> 0x004f }
            L_0x004a:
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_NormalErr     // Catch:{ Exception -> 0x004f }
                r0.t = r1     // Catch:{ Exception -> 0x004f }
                goto L_0x0017
            L_0x004f:
                r1 = move-exception
                java.lang.String r2 = "MicroMsg.SDK.NoopingResult"
                java.lang.String r3 = "parse json fail, ex = %s"
                java.lang.Object[] r4 = new java.lang.Object[r8]
                java.lang.String r1 = r1.getMessage()
                r4[r7] = r1
                java.lang.String r1 = java.lang.String.format(r3, r4)
                android.util.Log.e(r2, r1)
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_NormalErr
                r0.t = r1
                goto L_0x0017
            L_0x0068:
                r1 = move-exception
                java.lang.String r2 = "MicroMsg.SDK.NoopingResult"
                java.lang.String r3 = "parse fail, build String fail, ex = %s"
                java.lang.Object[] r4 = new java.lang.Object[r8]
                java.lang.String r1 = r1.getMessage()
                r4[r7] = r1
                java.lang.String r1 = java.lang.String.format(r3, r4)
                android.util.Log.e(r2, r1)
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_NormalErr
                r0.t = r1
                goto L_0x0017
            L_0x0081:
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_OK     // Catch:{ Exception -> 0x004f }
                r0.t = r1     // Catch:{ Exception -> 0x004f }
                java.lang.String r1 = "wx_code"
                java.lang.String r1 = r2.getString(r1)     // Catch:{ Exception -> 0x004f }
                r0.B = r1     // Catch:{ Exception -> 0x004f }
                goto L_0x0017
            L_0x008f:
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_OK     // Catch:{ Exception -> 0x004f }
                r0.t = r1     // Catch:{ Exception -> 0x004f }
                goto L_0x0017
            L_0x0094:
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_OK     // Catch:{ Exception -> 0x004f }
                r0.t = r1     // Catch:{ Exception -> 0x004f }
                goto L_0x0017
            L_0x009a:
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_Timeout     // Catch:{ Exception -> 0x004f }
                r0.t = r1     // Catch:{ Exception -> 0x004f }
                goto L_0x0017
            L_0x00a0:
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_Cancel     // Catch:{ Exception -> 0x004f }
                r0.t = r1     // Catch:{ Exception -> 0x004f }
                goto L_0x0017
            L_0x00a6:
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_NormalErr     // Catch:{ Exception -> 0x004f }
                r0.t = r1     // Catch:{ Exception -> 0x004f }
                goto L_0x0017
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.mm.opensdk.diffdev.a.f.a.b(byte[]):com.tencent.mm.opensdk.diffdev.a.f$a");
        }
    }

    public f(String str, OAuthListener oAuthListener) {
        this.u = str;
        this.r = oAuthListener;
        this.url = String.format("https://long.open.weixin.qq.com/connect/l/qrconnect?f=json&uuid=%s", new Object[]{str});
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object doInBackground(Object[] objArr) {
        if (this.u == null || this.u.length() == 0) {
            Log.e("MicroMsg.SDK.NoopingTask", "run fail, uuid is null");
            a aVar = new a();
            aVar.t = OAuthErrCode.WechatAuth_Err_NormalErr;
            return aVar;
        }
        while (!isCancelled()) {
            String str = this.url + (this.A == 0 ? "" : "&last=" + this.A);
            long currentTimeMillis = System.currentTimeMillis();
            byte[] a2 = e.a(str, 60000);
            long currentTimeMillis2 = System.currentTimeMillis();
            a b = a.b(a2);
            Log.d("MicroMsg.SDK.NoopingTask", String.format("nooping, url = %s, errCode = %s, uuidStatusCode = %d, time consumed = %d(ms)", new Object[]{str, b.t.toString(), Integer.valueOf(b.C), Long.valueOf(currentTimeMillis2 - currentTimeMillis)}));
            if (b.t == OAuthErrCode.WechatAuth_Err_OK) {
                this.A = b.C;
                if (b.C == g.UUID_SCANED.getCode()) {
                    this.r.onQrcodeScanned();
                } else if (b.C != g.UUID_KEEP_CONNECT.getCode() && b.C == g.UUID_CONFIRM.getCode()) {
                    if (b.B != null && b.B.length() != 0) {
                        return b;
                    }
                    Log.e("MicroMsg.SDK.NoopingTask", "nooping fail, confirm with an empty code!!!");
                    b.t = OAuthErrCode.WechatAuth_Err_NormalErr;
                    return b;
                }
            } else {
                Log.e("MicroMsg.SDK.NoopingTask", String.format("nooping fail, errCode = %s, uuidStatusCode = %d", new Object[]{b.t.toString(), Integer.valueOf(b.C)}));
                return b;
            }
        }
        Log.i("MicroMsg.SDK.NoopingTask", "IDiffDevOAuth.stopAuth / detach invoked");
        a aVar2 = new a();
        aVar2.t = OAuthErrCode.WechatAuth_Err_Auth_Stopped;
        return aVar2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void onPostExecute(Object obj) {
        a aVar = (a) obj;
        this.r.onAuthFinish(aVar.t, aVar.B);
    }
}

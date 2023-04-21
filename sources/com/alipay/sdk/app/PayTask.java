package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.sdk.data.c;
import com.alipay.sdk.packet.impl.d;
import com.alipay.sdk.sys.b;
import com.alipay.sdk.util.H5PayResultModel;
import com.alipay.sdk.util.e;
import com.alipay.sdk.util.h;
import com.alipay.sdk.util.i;
import com.alipay.sdk.util.j;
import com.alipay.sdk.util.l;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class PayTask {
    static final Object a = e.class;
    private Activity b;
    private com.alipay.sdk.widget.a c;
    private String d = "wappaygw.alipay.com/service/rest.htm";
    private String e = "mclient.alipay.com/service/rest.htm";
    private String f = "mclient.alipay.com/home/exterfaceAssign.htm";
    private Map<String, a> g = new HashMap();

    public PayTask(Activity activity) {
        this.b = activity;
        b a2 = b.a();
        Activity activity2 = this.b;
        c.a();
        a2.a((Context) activity2);
        com.alipay.sdk.app.statistic.a.a(activity);
        this.c = new com.alipay.sdk.widget.a(activity, com.alipay.sdk.widget.a.b);
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x007d A[Catch:{ Throwable -> 0x0130, Throwable -> 0x013a }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00b9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.String pay(java.lang.String r11, boolean r12) {
        /*
            r10 = this;
            r3 = 0
            r0 = 0
            monitor-enter(r10)
            if (r12 == 0) goto L_0x0008
            r10.b()     // Catch:{ all -> 0x015a }
        L_0x0008:
            java.lang.String r1 = "service=alipay.acquire.mr.ord.createandpay"
            boolean r1 = r11.contains(r1)     // Catch:{ all -> 0x015a }
            if (r1 == 0) goto L_0x0013
            r1 = 1
            com.alipay.sdk.cons.a.r = r1     // Catch:{ all -> 0x015a }
        L_0x0013:
            boolean r1 = com.alipay.sdk.cons.a.r     // Catch:{ all -> 0x015a }
            if (r1 == 0) goto L_0x002b
            java.lang.String r1 = "https://wappaygw.alipay.com/home/exterfaceAssign.htm?"
            boolean r1 = r11.startsWith(r1)     // Catch:{ all -> 0x015a }
            if (r1 == 0) goto L_0x009e
            java.lang.String r1 = "https://wappaygw.alipay.com/home/exterfaceAssign.htm?"
            int r1 = r11.indexOf(r1)     // Catch:{ all -> 0x015a }
            int r1 = r1 + 53
            java.lang.String r11 = r11.substring(r1)     // Catch:{ all -> 0x015a }
        L_0x002b:
            com.alipay.sdk.sys.a r1 = new com.alipay.sdk.sys.a     // Catch:{ Throwable -> 0x013a }
            android.app.Activity r2 = r10.b     // Catch:{ Throwable -> 0x013a }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x013a }
            java.lang.String r2 = r1.a(r11)     // Catch:{ Throwable -> 0x013a }
            java.lang.String r1 = "paymethod=\"expressGateway\""
            boolean r1 = r2.contains(r1)     // Catch:{ Throwable -> 0x013a }
            if (r1 != 0) goto L_0x00b4
            android.app.Activity r1 = r10.b     // Catch:{ Throwable -> 0x013a }
            boolean r1 = com.alipay.sdk.util.l.c((android.content.Context) r1)     // Catch:{ Throwable -> 0x013a }
            if (r1 == 0) goto L_0x00b4
            com.alipay.sdk.util.e r4 = new com.alipay.sdk.util.e     // Catch:{ Throwable -> 0x013a }
            android.app.Activity r1 = r10.b     // Catch:{ Throwable -> 0x013a }
            com.alipay.sdk.app.g r5 = new com.alipay.sdk.app.g     // Catch:{ Throwable -> 0x013a }
            r5.<init>(r10)     // Catch:{ Throwable -> 0x013a }
            r4.<init>(r1, r5)     // Catch:{ Throwable -> 0x013a }
            java.lang.String r1 = r4.a((java.lang.String) r2)     // Catch:{ Throwable -> 0x013a }
            r5 = 0
            r4.a = r5     // Catch:{ Throwable -> 0x013a }
            java.lang.String r4 = "failed"
            boolean r4 = android.text.TextUtils.equals(r1, r4)     // Catch:{ Throwable -> 0x013a }
            if (r4 != 0) goto L_0x00b4
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x013a }
            if (r2 == 0) goto L_0x006b
            java.lang.String r1 = com.alipay.sdk.app.h.a()     // Catch:{ Throwable -> 0x013a }
        L_0x006b:
            android.app.Activity r2 = r10.b     // Catch:{ Throwable -> 0x013a }
            android.content.Context r5 = r2.getApplicationContext()     // Catch:{ Throwable -> 0x013a }
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x0130 }
            if (r2 == 0) goto L_0x00b9
        L_0x0077:
            boolean r2 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x0130 }
            if (r2 != 0) goto L_0x0082
            java.lang.String r2 = "pref_trade_token"
            com.alipay.sdk.util.i.a(r5, r2, r0)     // Catch:{ Throwable -> 0x0130 }
        L_0x0082:
            com.alipay.sdk.data.a r0 = com.alipay.sdk.data.a.b()     // Catch:{ all -> 0x015a }
            android.app.Activity r2 = r10.b     // Catch:{ all -> 0x015a }
            android.content.Context r2 = r2.getApplicationContext()     // Catch:{ all -> 0x015a }
            r0.a((android.content.Context) r2)     // Catch:{ all -> 0x015a }
            r10.c()     // Catch:{ all -> 0x015a }
            android.app.Activity r0 = r10.b     // Catch:{ all -> 0x015a }
            android.content.Context r0 = r0.getApplicationContext()     // Catch:{ all -> 0x015a }
            com.alipay.sdk.app.statistic.a.a((android.content.Context) r0, (java.lang.String) r11)     // Catch:{ all -> 0x015a }
            r0 = r1
        L_0x009c:
            monitor-exit(r10)
            return r0
        L_0x009e:
            java.lang.String r1 = "https://mclient.alipay.com/home/exterfaceAssign.htm?"
            boolean r1 = r11.startsWith(r1)     // Catch:{ all -> 0x015a }
            if (r1 == 0) goto L_0x002b
            java.lang.String r1 = "https://mclient.alipay.com/home/exterfaceAssign.htm?"
            int r1 = r11.indexOf(r1)     // Catch:{ all -> 0x015a }
            int r1 = r1 + 52
            java.lang.String r11 = r11.substring(r1)     // Catch:{ all -> 0x015a }
            goto L_0x002b
        L_0x00b4:
            java.lang.String r1 = r10.b(r2)     // Catch:{ Throwable -> 0x013a }
            goto L_0x006b
        L_0x00b9:
            java.lang.String r2 = ";"
            java.lang.String[] r6 = r1.split(r2)     // Catch:{ Throwable -> 0x0130 }
            r4 = r3
        L_0x00c0:
            int r2 = r6.length     // Catch:{ Throwable -> 0x0130 }
            if (r4 >= r2) goto L_0x0077
            r2 = r6[r4]     // Catch:{ Throwable -> 0x0130 }
            java.lang.String r7 = "result={"
            boolean r2 = r2.startsWith(r7)     // Catch:{ Throwable -> 0x0130 }
            if (r2 == 0) goto L_0x0116
            r2 = r6[r4]     // Catch:{ Throwable -> 0x0130 }
            java.lang.String r7 = "}"
            boolean r2 = r2.endsWith(r7)     // Catch:{ Throwable -> 0x0130 }
            if (r2 == 0) goto L_0x0116
            r2 = r6[r4]     // Catch:{ Throwable -> 0x0130 }
            r7 = 8
            r8 = r6[r4]     // Catch:{ Throwable -> 0x0130 }
            int r8 = r8.length()     // Catch:{ Throwable -> 0x0130 }
            int r8 = r8 + -1
            java.lang.String r2 = r2.substring(r7, r8)     // Catch:{ Throwable -> 0x0130 }
            java.lang.String r7 = "&"
            java.lang.String[] r7 = r2.split(r7)     // Catch:{ Throwable -> 0x0130 }
            r2 = r3
        L_0x00ef:
            int r8 = r7.length     // Catch:{ Throwable -> 0x0130 }
            if (r2 >= r8) goto L_0x0116
            r8 = r7[r2]     // Catch:{ Throwable -> 0x0130 }
            java.lang.String r9 = "trade_token=\""
            boolean r8 = r8.startsWith(r9)     // Catch:{ Throwable -> 0x0130 }
            if (r8 == 0) goto L_0x011a
            r8 = r7[r2]     // Catch:{ Throwable -> 0x0130 }
            java.lang.String r9 = "\""
            boolean r8 = r8.endsWith(r9)     // Catch:{ Throwable -> 0x0130 }
            if (r8 == 0) goto L_0x011a
            r0 = r7[r2]     // Catch:{ Throwable -> 0x0130 }
            r8 = 13
            r2 = r7[r2]     // Catch:{ Throwable -> 0x0130 }
            int r2 = r2.length()     // Catch:{ Throwable -> 0x0130 }
            int r2 = r2 + -1
            java.lang.String r0 = r0.substring(r8, r2)     // Catch:{ Throwable -> 0x0130 }
        L_0x0116:
            int r2 = r4 + 1
            r4 = r2
            goto L_0x00c0
        L_0x011a:
            r8 = r7[r2]     // Catch:{ Throwable -> 0x0130 }
            java.lang.String r9 = "trade_token="
            boolean r8 = r8.startsWith(r9)     // Catch:{ Throwable -> 0x0130 }
            if (r8 == 0) goto L_0x012d
            r0 = r7[r2]     // Catch:{ Throwable -> 0x0130 }
            r2 = 12
            java.lang.String r0 = r0.substring(r2)     // Catch:{ Throwable -> 0x0130 }
            goto L_0x0116
        L_0x012d:
            int r2 = r2 + 1
            goto L_0x00ef
        L_0x0130:
            r0 = move-exception
            java.lang.String r2 = "biz"
            java.lang.String r3 = "SaveTradeTokenError"
            com.alipay.sdk.app.statistic.a.a((java.lang.String) r2, (java.lang.String) r3, (java.lang.Throwable) r0)     // Catch:{ Throwable -> 0x013a }
            goto L_0x0082
        L_0x013a:
            r0 = move-exception
            java.lang.String r0 = com.alipay.sdk.app.h.a()     // Catch:{ all -> 0x015d }
            com.alipay.sdk.data.a r1 = com.alipay.sdk.data.a.b()     // Catch:{ all -> 0x015a }
            android.app.Activity r2 = r10.b     // Catch:{ all -> 0x015a }
            android.content.Context r2 = r2.getApplicationContext()     // Catch:{ all -> 0x015a }
            r1.a((android.content.Context) r2)     // Catch:{ all -> 0x015a }
            r10.c()     // Catch:{ all -> 0x015a }
            android.app.Activity r1 = r10.b     // Catch:{ all -> 0x015a }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ all -> 0x015a }
            com.alipay.sdk.app.statistic.a.a((android.content.Context) r1, (java.lang.String) r11)     // Catch:{ all -> 0x015a }
            goto L_0x009c
        L_0x015a:
            r0 = move-exception
            monitor-exit(r10)
            throw r0
        L_0x015d:
            r0 = move-exception
            com.alipay.sdk.data.a r1 = com.alipay.sdk.data.a.b()     // Catch:{ all -> 0x015a }
            android.app.Activity r2 = r10.b     // Catch:{ all -> 0x015a }
            android.content.Context r2 = r2.getApplicationContext()     // Catch:{ all -> 0x015a }
            r1.a((android.content.Context) r2)     // Catch:{ all -> 0x015a }
            r10.c()     // Catch:{ all -> 0x015a }
            android.app.Activity r1 = r10.b     // Catch:{ all -> 0x015a }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ all -> 0x015a }
            com.alipay.sdk.app.statistic.a.a((android.content.Context) r1, (java.lang.String) r11)     // Catch:{ all -> 0x015a }
            throw r0     // Catch:{ all -> 0x015a }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.app.PayTask.pay(java.lang.String, boolean):java.lang.String");
    }

    public synchronized Map<String, String> payV2(String str, boolean z) {
        return j.a(pay(str, z));
    }

    public synchronized String fetchTradeToken() {
        return i.b(this.b.getApplicationContext(), h.a, "");
    }

    public synchronized String fetchOrderInfoFromH5PayUrl(String str) {
        String str2;
        try {
            if (!TextUtils.isEmpty(str)) {
                String trim = str.trim();
                if (trim.startsWith("https://" + this.d) || trim.startsWith("http://" + this.d)) {
                    String trim2 = trim.replaceFirst("(http|https)://" + this.d + "\\?", "").trim();
                    if (!TextUtils.isEmpty(trim2)) {
                        str2 = "_input_charset=\"utf-8\"&ordertoken=\"" + l.a("<request_token>", "</request_token>", l.a(trim2).get("req_data")) + "\"&pay_channel_id=\"alipay_sdk\"&bizcontext=\"" + new com.alipay.sdk.sys.a(this.b).a("sc", "h5tonative") + "\"";
                    }
                }
                if (trim.startsWith("https://" + this.e) || trim.startsWith("http://" + this.e)) {
                    String trim3 = trim.replaceFirst("(http|https)://" + this.e + "\\?", "").trim();
                    if (!TextUtils.isEmpty(trim3)) {
                        str2 = "_input_charset=\"utf-8\"&ordertoken=\"" + l.a("<request_token>", "</request_token>", l.a(trim3).get("req_data")) + "\"&pay_channel_id=\"alipay_sdk\"&bizcontext=\"" + new com.alipay.sdk.sys.a(this.b).a("sc", "h5tonative") + "\"";
                    }
                }
                if ((trim.startsWith("https://" + this.f) || trim.startsWith("http://" + this.f)) && trim.contains("alipay.wap.create.direct.pay.by.user") && !TextUtils.isEmpty(trim.replaceFirst("(http|https)://" + this.f + "\\?", "").trim())) {
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("url", str);
                        jSONObject.put("bizcontext", new com.alipay.sdk.sys.a(this.b).a("sc", "h5tonative"));
                        str2 = "new_external_info==" + jSONObject.toString();
                    } catch (Throwable th) {
                    }
                }
                if (Pattern.compile("^(http|https)://(maliprod\\.alipay\\.com\\/w\\/trade_pay\\.do.?|mali\\.alipay\\.com\\/w\\/trade_pay\\.do.?|mclient\\.alipay\\.com\\/w\\/trade_pay\\.do.?)").matcher(str).find()) {
                    String a2 = l.a("?", "", str);
                    if (!TextUtils.isEmpty(a2)) {
                        Map<String, String> a3 = l.a(a2);
                        StringBuilder sb = new StringBuilder();
                        if (a(false, true, com.alipay.sdk.app.statistic.c.H, sb, a3, com.alipay.sdk.app.statistic.c.H, "alipay_trade_no")) {
                            a(true, false, "pay_phase_id", sb, a3, "payPhaseId", "pay_phase_id", "out_relation_id");
                            sb.append("&biz_sub_type=\"TRADE\"");
                            sb.append("&biz_type=\"trade\"");
                            String str3 = a3.get("app_name");
                            if (TextUtils.isEmpty(str3) && !TextUtils.isEmpty(a3.get("cid"))) {
                                str3 = "ali1688";
                            } else if (TextUtils.isEmpty(str3) && (!TextUtils.isEmpty(a3.get("sid")) || !TextUtils.isEmpty(a3.get("s_id")))) {
                                str3 = "tb";
                            }
                            sb.append("&app_name=\"" + str3 + "\"");
                            if (!a(true, true, "extern_token", sb, a3, "extern_token", "cid", "sid", "s_id")) {
                                str2 = "";
                            } else {
                                a(true, false, "appenv", sb, a3, "appenv");
                                sb.append("&pay_channel_id=\"alipay_sdk\"");
                                a aVar = new a(this, (byte) 0);
                                aVar.a = a3.get("return_url");
                                aVar.b = a3.get("pay_order_id");
                                str2 = sb.toString() + "&bizcontext=\"" + new com.alipay.sdk.sys.a(this.b).a("sc", "h5tonative") + "\"";
                                this.g.put(str2, aVar);
                            }
                        }
                    }
                }
                if (trim.contains("mclient.alipay.com/cashier/mobilepay.htm") || (EnvUtils.isSandBox() && trim.contains("mobileclientgw.alipaydev.com/cashier/mobilepay.htm"))) {
                    String a4 = new com.alipay.sdk.sys.a(this.b).a("sc", "h5tonative");
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("url", trim);
                    jSONObject2.put("bizcontext", a4);
                    str2 = String.format("new_external_info==%s", new Object[]{jSONObject2.toString()});
                }
            }
        } catch (Throwable th2) {
        }
        str2 = "";
        return str2;
    }

    private class a {
        String a;
        String b;

        private a() {
            this.a = "";
            this.b = "";
        }

        /* synthetic */ a(PayTask payTask, byte b2) {
            this();
        }

        private String a() {
            return this.a;
        }

        private void a(String str) {
            this.a = str;
        }

        private String b() {
            return this.b;
        }

        private void b(String str) {
            this.b = str;
        }
    }

    private static boolean a(boolean z, boolean z2, String str, StringBuilder sb, Map<String, String> map, String... strArr) {
        String str2;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                str2 = "";
                break;
            }
            String str3 = strArr[i];
            if (!TextUtils.isEmpty(map.get(str3))) {
                str2 = map.get(str3);
                break;
            }
            i++;
        }
        if (TextUtils.isEmpty(str2)) {
            if (z2) {
                return false;
            }
        } else if (z) {
            sb.append(com.alipay.sdk.sys.a.b).append(str).append("=\"").append(str2).append("\"");
        } else {
            sb.append(str).append("=\"").append(str2).append("\"");
        }
        return true;
    }

    public synchronized H5PayResultModel h5Pay(String str, boolean z) {
        H5PayResultModel h5PayResultModel;
        String str2;
        synchronized (this) {
            H5PayResultModel h5PayResultModel2 = new H5PayResultModel();
            try {
                str.trim();
                String[] split = pay(str, z).split(h.b);
                HashMap hashMap = new HashMap();
                for (String str3 : split) {
                    String substring = str3.substring(0, str3.indexOf("={"));
                    String str4 = substring + "={";
                    hashMap.put(substring, str3.substring(str4.length() + str3.indexOf(str4), str3.lastIndexOf(h.d)));
                }
                if (hashMap.containsKey(j.a)) {
                    h5PayResultModel2.setResultCode((String) hashMap.get(j.a));
                }
                if (hashMap.containsKey("callBackUrl")) {
                    h5PayResultModel2.setReturnUrl((String) hashMap.get("callBackUrl"));
                } else if (hashMap.containsKey(j.c)) {
                    try {
                        String str5 = (String) hashMap.get(j.c);
                        if (str5.length() > 15) {
                            a aVar = this.g.get(str);
                            if (aVar != null) {
                                if (TextUtils.isEmpty(aVar.b)) {
                                    h5PayResultModel2.setReturnUrl(aVar.a);
                                } else {
                                    h5PayResultModel2.setReturnUrl(com.alipay.sdk.data.a.b().j.replace("$OrderId$", aVar.b));
                                }
                                this.g.remove(str);
                                h5PayResultModel = h5PayResultModel2;
                            } else {
                                String a2 = l.a("&callBackUrl=\"", "\"", str5);
                                if (TextUtils.isEmpty(a2)) {
                                    a2 = l.a("&call_back_url=\"", "\"", str5);
                                    if (TextUtils.isEmpty(a2)) {
                                        a2 = l.a(com.alipay.sdk.cons.a.o, "\"", str5);
                                        if (TextUtils.isEmpty(a2)) {
                                            a2 = URLDecoder.decode(l.a(com.alipay.sdk.cons.a.p, com.alipay.sdk.sys.a.b, str5), "utf-8");
                                            if (TextUtils.isEmpty(a2)) {
                                                a2 = URLDecoder.decode(l.a("&callBackUrl=", com.alipay.sdk.sys.a.b, str5), "utf-8");
                                            }
                                        }
                                    }
                                }
                                if (!TextUtils.isEmpty(a2) || TextUtils.isEmpty(str5) || !str5.contains("call_back_url")) {
                                    str2 = a2;
                                } else {
                                    str2 = l.b("call_back_url=\"", "\"", str5);
                                }
                                if (TextUtils.isEmpty(str2)) {
                                    str2 = com.alipay.sdk.data.a.b().j;
                                }
                                h5PayResultModel2.setReturnUrl(str2);
                            }
                        } else {
                            a aVar2 = this.g.get(str);
                            if (aVar2 != null) {
                                h5PayResultModel2.setReturnUrl(aVar2.a);
                                this.g.remove(str);
                                h5PayResultModel = h5PayResultModel2;
                            }
                        }
                    } catch (Throwable th) {
                    }
                }
            } catch (Throwable th2) {
            }
            h5PayResultModel = h5PayResultModel2;
        }
        return h5PayResultModel;
    }

    private static String a(String str, String str2) {
        String str3 = str2 + "={";
        return str.substring(str3.length() + str.indexOf(str3), str.lastIndexOf(h.d));
    }

    private e.a a() {
        return new g(this);
    }

    private void b() {
        if (this.c != null) {
            this.c.a();
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        if (this.c != null) {
            this.c.b();
            this.c = null;
        }
    }

    private String a(String str) {
        String a2 = new com.alipay.sdk.sys.a(this.b).a(str);
        if (a2.contains("paymethod=\"expressGateway\"")) {
            return b(a2);
        }
        if (!l.c((Context) this.b)) {
            return b(a2);
        }
        e eVar = new e(this.b, new g(this));
        String a3 = eVar.a(a2);
        eVar.a = null;
        if (TextUtils.equals(a3, e.b)) {
            return b(a2);
        }
        if (TextUtils.isEmpty(a3)) {
            return h.a();
        }
        return a3;
    }

    public String getVersion() {
        return com.alipay.sdk.cons.a.f;
    }

    private String b(String str) {
        i iVar;
        com.alipay.sdk.tid.a aVar;
        b();
        try {
            List<com.alipay.sdk.protocol.b> a2 = com.alipay.sdk.protocol.b.a(new d().a(this.b.getApplicationContext(), str).a().optJSONObject(com.alipay.sdk.cons.c.c).optJSONObject(com.alipay.sdk.cons.c.d));
            for (int i = 0; i < a2.size(); i++) {
                if (a2.get(i).a == com.alipay.sdk.protocol.a.Update) {
                    String[] strArr = a2.get(i).b;
                    if (strArr.length == 3 && TextUtils.equals(com.alipay.sdk.cons.b.c, strArr[0])) {
                        Context context = b.a().a;
                        com.alipay.sdk.tid.b a3 = com.alipay.sdk.tid.b.a();
                        if (!TextUtils.isEmpty(strArr[1]) && !TextUtils.isEmpty(strArr[2])) {
                            a3.a = strArr[1];
                            a3.b = strArr[2];
                            aVar = new com.alipay.sdk.tid.a(context);
                            aVar.a(com.alipay.sdk.util.a.a(context).a(), com.alipay.sdk.util.a.a(context).b(), a3.a, a3.b);
                            aVar.close();
                        }
                    }
                }
            }
            c();
            for (int i2 = 0; i2 < a2.size(); i2++) {
                if (a2.get(i2).a == com.alipay.sdk.protocol.a.WapPay) {
                    String a4 = a(a2.get(i2));
                    c();
                    return a4;
                }
            }
            c();
            iVar = null;
        } catch (Exception e2) {
            aVar.close();
        } catch (IOException e3) {
            i a5 = i.a(i.NETWORK_ERROR.h);
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.a, (Throwable) e3);
            c();
            iVar = a5;
        } catch (Throwable th) {
            c();
            throw th;
        }
        if (iVar == null) {
            iVar = i.a(i.FAILED.h);
        }
        return h.a(iVar.h, iVar.i, "");
    }

    private String a(com.alipay.sdk.protocol.b bVar) {
        String[] strArr = bVar.b;
        Intent intent = new Intent(this.b, H5PayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", strArr[0]);
        if (strArr.length == 2) {
            bundle.putString("cookie", strArr[1]);
        }
        intent.putExtras(bundle);
        this.b.startActivity(intent);
        synchronized (a) {
            try {
                a.wait();
            } catch (InterruptedException e2) {
                return h.a();
            }
        }
        String str = h.a;
        if (TextUtils.isEmpty(str)) {
            return h.a();
        }
        return str;
    }
}

package com.alipay.b.a.a.c.a;

import com.alipay.b.a.a.a.a;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportResult;
import java.util.Map;

public final class b {
    public static c a(DataReportResult dataReportResult) {
        c cVar = new c();
        if (dataReportResult == null) {
            return null;
        }
        cVar.a = dataReportResult.success;
        cVar.b = dataReportResult.resultCode;
        Map<String, String> map = dataReportResult.resultData;
        if (map != null) {
            cVar.c = map.get("apdid");
            cVar.d = map.get("apdidToken");
            cVar.g = map.get("dynamicKey");
            cVar.h = map.get("timeInterval");
            cVar.i = map.get("webrtcUrl");
            cVar.j = "";
            String str = map.get("drmSwitch");
            if (a.b(str)) {
                if (str.length() > 0) {
                    cVar.e = new StringBuilder().append(str.charAt(0)).toString();
                }
                if (str.length() >= 3) {
                    cVar.f = new StringBuilder().append(str.charAt(2)).toString();
                }
            }
        }
        return cVar;
    }
}

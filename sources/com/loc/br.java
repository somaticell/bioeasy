package com.loc;

import android.text.TextUtils;
import com.autonavi.aps.amapapi.model.AmapLoc;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/* compiled from: Parser */
public class br {
    public AmapLoc a(String str) {
        ByteArrayInputStream byteArrayInputStream;
        if (!str.contains("SuccessCode")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.reverse();
            try {
                str = new String(r.a(sb.toString()), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            sb.delete(0, sb.length());
        }
        if (str.contains("SuccessCode=\"0\"")) {
        }
        try {
            byteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e2) {
            byteArrayInputStream = null;
        }
        SAXParserFactory newInstance = SAXParserFactory.newInstance();
        a aVar = new a();
        try {
            SAXParser newSAXParser = newInstance.newSAXParser();
            if (byteArrayInputStream != null) {
                newSAXParser.parse(byteArrayInputStream, aVar);
                byteArrayInputStream.close();
            }
            aVar.a.c("network");
            return aVar.a;
        } catch (Exception e3) {
            Exception exc = e3;
            AmapLoc amapLoc = new AmapLoc();
            amapLoc.b(5);
            aw.c.append("parser error:" + exc.getMessage());
            amapLoc.b(aw.c.toString());
            return amapLoc;
        }
    }

    /* compiled from: Parser */
    private class a extends DefaultHandler {
        public AmapLoc a;
        private String c;

        private a() {
            this.a = new AmapLoc();
            this.c = "";
        }

        public void characters(char[] cArr, int i, int i2) {
            this.c = String.valueOf(cArr, i, i2);
        }

        public void startElement(String str, String str2, String str3, Attributes attributes) {
            this.c = "";
        }

        public void endElement(String str, String str2, String str3) {
            if (str2.equals("retype")) {
                this.a.g(this.c);
            } else if (str2.equals("rdesc")) {
                this.a.h(this.c);
            } else if (str2.equals("adcode")) {
                this.a.k(this.c);
            } else if (str2.equals("citycode")) {
                this.a.i(this.c);
            } else if (str2.equals("radius")) {
                try {
                    this.a.a(Float.parseFloat(this.c));
                } catch (Exception e) {
                    this.a.a(3891.0f);
                }
            } else if (str2.equals("cenx")) {
                try {
                    this.a.a(Double.parseDouble(this.c));
                } catch (Exception e2) {
                    this.a.a(0.0d);
                }
            } else if (str2.equals("ceny")) {
                try {
                    this.a.b(Double.parseDouble(this.c));
                } catch (Exception e3) {
                    this.a.b(0.0d);
                }
            } else if (str2.equals("desc")) {
                this.a.j(this.c);
            } else if (str2.equals("country")) {
                this.a.l(this.c);
            } else if (str2.equals("province")) {
                this.a.m(this.c);
            } else if (str2.equals("city")) {
                this.a.n(this.c);
            } else if (str2.equals("district")) {
                this.a.o(this.c);
            } else if (str2.equals("road")) {
                this.a.p(this.c);
            } else if (str2.equals("street")) {
                this.a.q(this.c);
            } else if (str2.equals("number")) {
                this.a.r(this.c);
            } else if (str2.equals("poiname")) {
                this.a.s(this.c);
            } else if (str2.equals("BIZ")) {
                if (this.a.D() == null) {
                    this.a.a(new JSONObject());
                }
                try {
                    this.a.D().put("BIZ", this.c);
                } catch (Exception e4) {
                }
            } else if (str2.equals("cens")) {
                this.a.t(this.c);
            } else if (str2.equals("pid")) {
                this.a.u(this.c);
            } else if (str2.equals("flr")) {
                this.a.v(this.c);
            } else if (str2.equals("coord")) {
                if (TextUtils.isEmpty(e.g)) {
                    e.g = this.c;
                }
                this.a.w(this.c);
            } else if (str2.equals("mcell")) {
                this.a.x(this.c);
            } else if (!str2.equals("gkeyloc") && !str2.equals("gkeygeo") && str2.equals("apiTime")) {
                this.a.a(Long.parseLong(this.c));
            }
        }
    }

    public AmapLoc b(String str) {
        AmapLoc amapLoc = new AmapLoc();
        amapLoc.b(7);
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.has("status") || !jSONObject.has("info")) {
                aw.c.append("json is error " + str);
            }
            String string = jSONObject.getString("status");
            String string2 = jSONObject.getString("info");
            if (string.equals(com.alipay.sdk.cons.a.e)) {
                aw.c.append("json is error " + str);
            }
            if (string.equals("0")) {
                aw.c.append("auth fail:" + string2);
            }
        } catch (JSONException e) {
            aw.c.append("json exception error:" + e.getMessage());
        }
        amapLoc.b(aw.c.toString());
        return amapLoc;
    }
}

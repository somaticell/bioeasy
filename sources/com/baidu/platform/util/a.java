package com.baidu.platform.util;

import com.baidu.platform.comjni.util.AppMD5;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class a implements ParamBuilder<a> {
    protected Map<String, String> a;

    public a a(String str, String str2) {
        if (this.a == null) {
            this.a = new LinkedHashMap();
        }
        this.a.put(str, str2);
        return this;
    }

    public String a() {
        if (this.a == null || this.a.isEmpty()) {
            return null;
        }
        String str = new String();
        Iterator<String> it = this.a.keySet().iterator();
        int i = 0;
        while (true) {
            int i2 = i;
            String str2 = str;
            if (!it.hasNext()) {
                return str2;
            }
            String next = it.next();
            String encodeUrlParamsValue = AppMD5.encodeUrlParamsValue(this.a.get(next));
            str = i2 == 0 ? str2 + next + "=" + encodeUrlParamsValue : str2 + com.alipay.sdk.sys.a.b + next + "=" + encodeUrlParamsValue;
            i = i2 + 1;
        }
    }
}

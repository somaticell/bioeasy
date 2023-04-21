package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import android.text.TextUtils;
import com.alipay.sdk.util.h;
import com.alipay.sdk.util.j;
import java.util.Map;

public class PayResult {
    private String memo;
    private String result;
    private String resultStatus;

    public PayResult(Map<String, String> rawResult) {
        if (rawResult != null) {
            for (String key : rawResult.keySet()) {
                if (TextUtils.equals(key, j.a)) {
                    this.resultStatus = rawResult.get(key);
                } else if (TextUtils.equals(key, j.c)) {
                    this.result = rawResult.get(key);
                } else if (TextUtils.equals(key, j.b)) {
                    this.memo = rawResult.get(key);
                }
            }
        }
    }

    public String toString() {
        return "resultStatus={" + this.resultStatus + "};memo={" + this.memo + "};result={" + this.result + h.d;
    }

    public String getResultStatus() {
        return this.resultStatus;
    }

    public String getMemo() {
        return this.memo;
    }

    public String getResult() {
        return this.result;
    }
}

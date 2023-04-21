package com.alipay.android.phone.mrpc.core.gwprotocol;

import com.alipay.a.a.f;
import com.alipay.android.phone.mrpc.core.RpcException;
import java.util.ArrayList;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class JsonSerializer extends AbstractSerializer {
    private static final String TAG = "JsonSerializer";
    public static final String VERSION = "1.0.0";
    private Object mExtParam;
    private int mId;

    public JsonSerializer(int i, String str, Object obj) {
        super(str, obj);
        this.mId = i;
    }

    public int getId() {
        return this.mId;
    }

    public byte[] packet() {
        try {
            ArrayList arrayList = new ArrayList();
            if (this.mExtParam != null) {
                arrayList.add(new BasicNameValuePair("extParam", f.a(this.mExtParam)));
            }
            arrayList.add(new BasicNameValuePair("operationType", this.mOperationType));
            arrayList.add(new BasicNameValuePair("id", new StringBuilder().append(this.mId).toString()));
            new StringBuilder("mParams is:").append(this.mParams);
            arrayList.add(new BasicNameValuePair("requestData", this.mParams == null ? "[]" : f.a(this.mParams)));
            return URLEncodedUtils.format(arrayList, "utf-8").getBytes();
        } catch (Exception e) {
            Exception exc = e;
            throw new RpcException(9, new StringBuilder("request  =").append(this.mParams).append(":").append(exc).toString() == null ? "" : exc.getMessage(), exc);
        }
    }

    public void setExtParam(Object obj) {
        this.mExtParam = obj;
    }

    public void setId(int i) {
        this.mId = i;
    }
}

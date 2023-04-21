package cn.com.bioeasy.app.retrofit;

import rx.functions.Func1;

public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
    public T call(HttpResult<T> httpResult) {
        if (httpResult.result == 0) {
            return httpResult.data;
        }
        throw new RuntimeException(httpResult.errMsg.toString());
    }
}

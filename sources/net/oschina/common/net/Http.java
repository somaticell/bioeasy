package net.oschina.common.net;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.oschina.common.net.io.IOParam;
import net.oschina.common.net.io.Param;
import net.oschina.common.net.io.StrParam;
import org.json.JSONArray;
import org.json.JSONObject;

public class Http<T> {
    private Deliver<T> mDeliver;

    private Http(Deliver<T> deliver) {
        this.mDeliver = deliver;
    }

    public static <T> Http<T> create(Deliver<T> deliver) {
        return new Http<>(deliver);
    }

    public Deliver<T> getDeliver() {
        return this.mDeliver;
    }

    public String getSync(String url) {
        return getSync(url, new StrParam[0]);
    }

    public String getSync(String url, StrParam... strParams) {
        return (String) getSync(String.class, url, strParams);
    }

    public String getSync(String url, Object tag) {
        return (String) getSync(String.class, url, tag);
    }

    public <T> T getSync(Class<T> tClass, String url) {
        return getSync(tClass, url, new StrParam[0]);
    }

    public <T> T getSync(Class<T> tClass, String url, StrParam... strParams) {
        return getSync(tClass, url, (Object) null, strParams);
    }

    public <T> T getSync(Class<T> tClass, String url, List<StrParam> strParams) {
        return getSync(tClass, url, (Object) null, (StrParam[]) Util.listToParams(strParams, StrParam.class));
    }

    public <T> T getSync(Class<T> tClass, String url, Map<String, String> params) {
        return getSync(tClass, url, (Object) null, Util.mapToStringParams(params));
    }

    public <T> T getSync(Class<T> tClass, String url, Object tag) {
        return getSync(tClass, url, tag, new StrParam[0]);
    }

    public <T> T getSync(Class<T> tClass, String url, Object tag, StrParam... strParams) {
        return executeGetSync(tClass, (Callback) null, url, tag, strParams);
    }

    public <T> T getSync(Class<T> tClass, String url, Object tag, List<StrParam> strParams) {
        return getSync(tClass, url, tag, (StrParam[]) Util.listToParams(strParams, StrParam.class));
    }

    public <T> T getSync(Class<T> tClass, String url, Object tag, Map<String, String> params) {
        return getSync(tClass, url, tag, Util.mapToStringParams(params));
    }

    public <T> T getSync(String url, Callback<T> callback) {
        return getSync(url, (Object) null, callback);
    }

    public <T> T getSync(String url, Object tag, Callback<T> callback) {
        return getSync(url, tag, callback, new StrParam[0]);
    }

    public <T> T getSync(String url, Callback<T> callback, StrParam... strParams) {
        return getSync(url, (Object) null, callback, strParams);
    }

    public <T> T getSync(String url, Callback<T> callback, List<StrParam> strParams) {
        return getSync(url, (Object) null, callback, (StrParam[]) Util.listToParams(strParams, StrParam.class));
    }

    public <T> T getSync(String url, Callback<T> callback, Map<String, String> params) {
        return getSync(url, (Object) null, callback, Util.mapToStringParams(params));
    }

    public <T> T getSync(String url, Object tag, Callback<T> callback, StrParam... strParams) {
        return executeGetSync((Class) null, callback, url, tag, strParams);
    }

    public <T> T getSync(String url, Object tag, Callback<T> callback, List<StrParam> strParams) {
        return getSync(url, tag, callback, (StrParam[]) Util.listToParams(strParams, StrParam.class));
    }

    public <T> T getSync(String url, Object tag, Callback<T> callback, Map<String, String> params) {
        return getSync(url, tag, callback, Util.mapToStringParams(params));
    }

    public void getAsync(String url, Callback callback) {
        getAsync(url, callback, new StrParam[0]);
    }

    public void getAsync(String url, Callback callback, StrParam... strParams) {
        getAsync(url, (Object) null, callback, strParams);
    }

    public void getAsync(String url, Callback callback, List<StrParam> strParams) {
        getAsync(url, (Object) null, callback, (StrParam[]) Util.listToParams(strParams, StrParam.class));
    }

    public void getAsync(String url, Callback callback, Map<String, String> params) {
        getAsync(url, (Object) null, callback, Util.mapToStringParams(params));
    }

    public void getAsync(String url, Object tag, Callback callback) {
        getAsync(url, tag, callback, new StrParam[0]);
    }

    public void getAsync(String url, Object tag, Callback callback, StrParam... strParams) {
        executeGetAsync(callback, url, tag, strParams);
    }

    public void getAsync(String url, Object tag, Callback callback, List<StrParam> strParams) {
        getAsync(url, tag, callback, (StrParam[]) Util.listToParams(strParams, StrParam.class));
    }

    public void getAsync(String url, Object tag, Callback callback, Map<String, String> params) {
        getAsync(url, tag, callback, Util.mapToStringParams(params));
    }

    public String postSync(String url, List<StrParam> strParams) {
        return (String) postSync(String.class, url, (Object) null, (StrParam[]) Util.listToParams(strParams, StrParam.class));
    }

    public String postSync(String url, Map<String, String> params) {
        return (String) postSync(String.class, url, (Object) null, Util.mapToStringParams(params));
    }

    public String postSync(String url, StrParam... strParams) {
        return (String) postSync(String.class, url, (Object) null, strParams);
    }

    public <T> T postSync(Class<T> tClass, String url, List<StrParam> strParams) {
        return postSync(tClass, url, (Object) null, (StrParam[]) Util.listToParams(strParams, StrParam.class));
    }

    public <T> T postSync(Class<T> tClass, String url, Map<String, String> params) {
        return postSync(tClass, url, (Object) null, Util.mapToStringParams(params));
    }

    public <T> T postSync(Class<T> tClass, String url, StrParam... strParams) {
        return postSync(tClass, url, (Object) null, strParams);
    }

    public <T> T postSync(Class<T> tClass, String url, Object tag, List<StrParam> strParams) {
        return postSync(tClass, url, tag, (StrParam[]) Util.listToParams(strParams, StrParam.class));
    }

    public <T> T postSync(Class<T> tClass, String url, Object tag, Map<String, String> params) {
        return postSync(tClass, url, tag, Util.mapToStringParams(params));
    }

    public <T> T postSync(Class<T> tClass, String url, Object tag, StrParam... strParams) {
        return executeGetSync(tClass, (Callback) null, url, tag, strParams);
    }

    public <T> T postSync(String url, Callback<T> callback, List<StrParam> strParams) {
        return postSync(url, (Object) null, callback, (StrParam[]) Util.listToParams(strParams, StrParam.class));
    }

    public <T> T postSync(String url, Callback<T> callback, Map<String, String> params) {
        return postSync(url, (Object) null, callback, Util.mapToStringParams(params));
    }

    public <T> T postSync(String url, Callback<T> callback, StrParam... strParams) {
        return postSync(url, (Object) null, callback, strParams);
    }

    public <T> T postSync(String url, Object tag, Callback<T> callback, List<StrParam> strParams) {
        return postSync(url, tag, callback, (StrParam[]) Util.listToParams(strParams, StrParam.class));
    }

    public <T> T postSync(String url, Object tag, Callback<T> callback, Map<String, String> params) {
        return postSync(url, tag, callback, Util.mapToStringParams(params));
    }

    public <T> T postSync(String url, Object tag, Callback<T> callback, StrParam... strParams) {
        return executeGetSync((Class) null, callback, url, tag, strParams);
    }

    public void postAsync(String url, Callback callback, List<StrParam> strParams) {
        postAsync(url, (Object) null, callback, (StrParam[]) Util.listToParams(strParams, StrParam.class));
    }

    public void postAsync(String url, Callback callback, Map<String, String> params) {
        postAsync(url, (Object) null, callback, Util.mapToStringParams(params));
    }

    public void postAsync(String url, Callback callback, StrParam... strParams) {
        postAsync(url, (Object) null, callback, strParams);
    }

    public void postAsync(String url, Object tag, Callback callback, List<StrParam> strParams) {
        postAsync(url, tag, callback, (StrParam[]) Util.listToParams(strParams, StrParam.class));
    }

    public void postAsync(String url, Object tag, Callback callback, Map<String, String> params) {
        postAsync(url, tag, callback, Util.mapToStringParams(params));
    }

    public void postAsync(String url, Object tag, Callback callback, StrParam... strParams) {
        executePostAsync(callback, url, tag, strParams);
    }

    public void postAsync(String url, Callback callback, String bodyStr) {
        postAsync(url, (Object) null, callback, bodyStr);
    }

    public void postAsync(String url, Object tag, Callback callback, String bodyStr) {
        executePostAsync(callback, url, tag, bodyStr);
    }

    public void postAsync(String url, Callback callback, byte[] bytes) {
        postAsync(url, (Object) null, callback, bytes);
    }

    public void postAsync(String url, Object tag, Callback callback, byte[] bytes) {
        executePostAsync(callback, url, tag, bytes);
    }

    public void postAsync(String url, Callback callback, File file) {
        postAsync(url, (Object) null, callback, file);
    }

    public void postAsync(String url, Object tag, Callback callback, File file) {
        executePostAsync(callback, url, tag, file);
    }

    public void postAsync(String url, Callback callback, JSONObject jsonObject) {
        postAsync(url, (Object) null, callback, jsonObject);
    }

    public void postAsync(String url, Object tag, Callback callback, JSONObject jsonObject) {
        executePostAsync(callback, url, tag, jsonObject);
    }

    public void postAsync(String url, Callback callback, JSONArray jsonArray) {
        postAsync(url, (Object) null, callback, jsonArray);
    }

    public void postAsync(String url, Object tag, Callback callback, JSONArray jsonArray) {
        executePostAsync(callback, url, tag, jsonArray);
    }

    public void uploadAsync(String url, String key, File file, Callback callback) {
        uploadAsync(url, (Object) null, callback, new IOParam(key, file));
    }

    public void uploadAsync(String url, Object tag, String key, File file, Callback callback) {
        uploadAsync(url, tag, callback, new IOParam(key, file));
    }

    public void uploadAsync(String url, Callback callback, IOParam... params) {
        uploadAsync(url, (Object) null, callback, (StrParam[]) null, params);
    }

    public void uploadAsync(String url, Object tag, Callback callback, IOParam... params) {
        uploadAsync(url, tag, callback, (StrParam[]) null, params);
    }

    public void uploadAsync(String url, Callback callback, Param... params) {
        uploadAsync(url, (Object) null, callback, params);
    }

    public void uploadAsync(String url, Object tag, Callback callback, Param... params) {
        List<IOParam> IOParams = new ArrayList<>();
        List<StrParam> stringStrParams = new ArrayList<>();
        if (params != null && params.length > 0) {
            for (Param param : params) {
                if (param.isFile()) {
                    IOParams.add(param.getFileParam());
                } else {
                    stringStrParams.add(param.getStringParam());
                }
            }
        }
        uploadAsync(url, tag, callback, (StrParam[]) Util.listToParams(stringStrParams, StrParam.class), (IOParam[]) Util.listToParams(IOParams, IOParam.class));
    }

    public void uploadAsync(String url, Callback callback, StrParam[] strParams, IOParam... IOParams) {
        uploadAsync(url, (Object) null, callback, strParams, IOParams);
    }

    public void uploadAsync(String url, Object tag, Callback callback, StrParam[] strParams, IOParam... IOParams) {
        executeUploadAsync(callback, url, tag, strParams, IOParams);
    }

    private <T> T executeGetSync(Class<T> cls, Callback<T> callback, String url, Object tag, StrParam... strParams) {
        return this.mDeliver.executeGet(false, callback, url, tag, strParams);
    }

    private void executeGetAsync(Callback callback, String url, Object tag, StrParam... strParams) {
        this.mDeliver.executeGet(true, callback, url, tag, strParams);
    }

    /* access modifiers changed from: protected */
    public void executePostAsync(Callback callback, String url, Object tag, StrParam... params) {
        this.mDeliver.executePost(true, callback, url, tag, params);
    }

    /* access modifiers changed from: protected */
    public void executePostAsync(Callback callback, String url, Object tag, String str) {
    }

    /* access modifiers changed from: protected */
    public void executePostAsync(Callback callback, String url, Object tag, byte[] bytes) {
    }

    /* access modifiers changed from: protected */
    public void executePostAsync(Callback callback, String url, Object tag, File file) {
    }

    /* access modifiers changed from: protected */
    public void executePostAsync(Callback callback, String url, Object tag, JSONObject jsonObject) {
    }

    /* access modifiers changed from: protected */
    public void executePostAsync(Callback callback, String url, Object tag, JSONArray jsonArray) {
    }

    /* access modifiers changed from: protected */
    public void executeUploadAsync(Callback callback, String url, Object tag, StrParam[] strParams, IOParam... IOParams) {
    }
}

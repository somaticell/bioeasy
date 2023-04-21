package com.baidu.platform.base;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.alipay.sdk.util.j;
import com.baidu.mapapi.http.AsyncHttpClient;
import com.baidu.mapapi.http.HttpClient;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.platform.comapi.util.PermissionCheck;
import com.baidu.platform.core.a.b;
import com.baidu.platform.core.a.c;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class a {
    protected final Lock a = new ReentrantLock();
    /* access modifiers changed from: private */
    public AsyncHttpClient b = new AsyncHttpClient();
    private Handler c = new Handler(Looper.getMainLooper());
    private boolean d = true;
    private DistrictResult e = null;

    private void a(AsyncHttpClient asyncHttpClient, HttpClient.ProtoResultCallback protoResultCallback, SearchResult searchResult) {
        asyncHttpClient.get(new c(((DistrictResult) searchResult).getCityName()).a(), protoResultCallback);
    }

    /* access modifiers changed from: private */
    public void a(HttpClient.HttpStateError httpStateError, d dVar, Object obj) {
        a(dVar.a("{SDK_InnerError:{httpStateError:" + httpStateError + "}}"), obj, dVar);
    }

    private void a(SearchResult searchResult, Object obj, d dVar) {
        this.c.post(new c(this, dVar, searchResult, obj));
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        if (!b(str)) {
            Log.e("BaseSearch", "Permission check unfinished, try again");
            int permissionCheck = PermissionCheck.permissionCheck();
            if (permissionCheck != 0) {
                Log.e("BaseSearch", "The authorized result is: " + permissionCheck);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, d dVar, Object obj, AsyncHttpClient asyncHttpClient, HttpClient.ProtoResultCallback protoResultCallback) {
        SearchResult a2 = dVar.a(str);
        a2.status = c(str);
        if (a(dVar, a2)) {
            a(asyncHttpClient, protoResultCallback, a2);
        } else if (dVar instanceof b) {
            if (this.e != null) {
                ((DistrictResult) a2).setCityCode(this.e.getCityCode());
                ((DistrictResult) a2).setCenterPt(this.e.getCenterPt());
            }
            a(a2, obj, dVar);
            this.d = true;
            this.e = null;
            ((b) dVar).a(false);
        } else {
            a(a2, obj, dVar);
        }
    }

    private boolean a(d dVar, SearchResult searchResult) {
        if (!(dVar instanceof b)) {
            return false;
        }
        if (SearchResult.ERRORNO.RESULT_NOT_FOUND != ((DistrictResult) searchResult).error) {
            return false;
        }
        if (((DistrictResult) searchResult).getCityName() == null) {
            return false;
        }
        if (!this.d) {
            return false;
        }
        this.d = false;
        this.e = (DistrictResult) searchResult;
        ((b) dVar).a(true);
        return true;
    }

    private boolean b(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.has("SDK_InnerError") || !jSONObject.optJSONObject("SDK_InnerError").has("PermissionCheckError")) {
                return true;
            }
            Log.e("BaseSearch", "Permission check unfinished");
            return false;
        } catch (JSONException e2) {
            Log.e("BaseSearch", "Create JSONObject failed");
            return false;
        }
    }

    private int c(String str) {
        if (str == null || str.equals("")) {
            return 10204;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("status")) {
                return jSONObject.getInt("status");
            }
            if (jSONObject.has("status_sp")) {
                return jSONObject.getInt("status_sp");
            }
            if (jSONObject.has(j.c)) {
                return jSONObject.optJSONObject(j.c).optInt("error");
            }
            return 10204;
        } catch (JSONException e2) {
            Log.e("BaseSearch", "Create JSONObject failed when get response result status");
            return 10204;
        }
    }

    /* access modifiers changed from: protected */
    public boolean a(e eVar, Object obj, d dVar) {
        if (dVar == null) {
            Log.e(a.class.getSimpleName(), "The SearchParser is null, must be applied.");
            return false;
        }
        String a2 = eVar.a();
        if (a2 == null) {
            Log.e("BaseSearch", "The sendurl is: " + a2);
            a(dVar.a("{SDK_InnerError:{PermissionCheckError:Error}}"), obj, dVar);
            return false;
        }
        this.b.get(a2, new b(this, dVar, obj));
        return true;
    }
}

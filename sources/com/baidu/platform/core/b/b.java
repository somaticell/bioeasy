package com.baidu.platform.core.b;

import com.alipay.sdk.util.j;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.platform.base.d;
import com.facebook.common.util.UriUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class b extends d {
    private boolean a(String str, GeoCodeResult geoCodeResult) {
        JSONObject optJSONObject;
        JSONObject optJSONObject2;
        JSONObject optJSONObject3;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject == null || (optJSONObject = jSONObject.optJSONObject(j.c)) == null || optJSONObject.optInt("error") != 0 || (optJSONObject2 = jSONObject.optJSONObject(UriUtil.LOCAL_CONTENT_SCHEME)) == null || (optJSONObject3 = optJSONObject2.optJSONObject("coord")) == null) {
                return false;
            }
            geoCodeResult.setLocation(CoordUtil.mc2ll(new GeoPoint((double) optJSONObject3.optInt("y"), (double) optJSONObject3.optInt("x"))));
            geoCodeResult.setAddress(optJSONObject2.optString("wd"));
            geoCodeResult.error = SearchResult.ERRORNO.NO_ERROR;
            return true;
        } catch (JSONException e) {
            geoCodeResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
            e.printStackTrace();
        }
    }

    public SearchResult a(String str) {
        GeoCodeResult geoCodeResult = new GeoCodeResult();
        if (str == null || str.equals("")) {
            geoCodeResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
        } else {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("SDK_InnerError")) {
                    JSONObject optJSONObject = jSONObject.optJSONObject("SDK_InnerError");
                    if (optJSONObject.has("PermissionCheckError")) {
                        geoCodeResult.error = SearchResult.ERRORNO.PERMISSION_UNFINISHED;
                    } else if (optJSONObject.has("httpStateError")) {
                        String optString = optJSONObject.optString("httpStateError");
                        if (optString.equals("NETWORK_ERROR")) {
                            geoCodeResult.error = SearchResult.ERRORNO.NETWORK_ERROR;
                        } else if (optString.equals("REQUEST_ERROR")) {
                            geoCodeResult.error = SearchResult.ERRORNO.REQUEST_ERROR;
                        } else {
                            geoCodeResult.error = SearchResult.ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
                        }
                    }
                }
                if (!a(str, geoCodeResult, false) && !a(str, geoCodeResult)) {
                    geoCodeResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
                }
            } catch (Exception e) {
                geoCodeResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
            }
        }
        return geoCodeResult;
    }

    public void a(SearchResult searchResult, Object obj) {
        if (obj != null && (obj instanceof OnGetGeoCoderResultListener)) {
            ((OnGetGeoCoderResultListener) obj).onGetGeoCodeResult((GeoCodeResult) searchResult);
        }
    }
}

package com.baidu.platform.core.f;

import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import com.alipay.android.phone.mrpc.core.Headers;
import com.alipay.sdk.util.j;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.platform.base.d;
import com.baidu.platform.comapi.util.CoordTrans;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class c extends d {
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.lang.String r3, com.baidu.mapapi.search.sug.SuggestionResult r4) {
        /*
            r2 = 0
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0019 }
            r0.<init>(r3)     // Catch:{ JSONException -> 0x0019 }
            if (r0 != 0) goto L_0x0009
        L_0x0008:
            return r2
        L_0x0009:
            java.lang.String r1 = "status"
            int r1 = r0.optInt(r1)     // Catch:{ JSONException -> 0x0019 }
            if (r1 == 0) goto L_0x002c
            switch(r1) {
                case 1: goto L_0x0022;
                case 2: goto L_0x0027;
                default: goto L_0x0014;
            }     // Catch:{ JSONException -> 0x0019 }
        L_0x0014:
            com.baidu.mapapi.search.core.SearchResult$ERRORNO r0 = com.baidu.mapapi.search.core.SearchResult.ERRORNO.RESULT_NOT_FOUND     // Catch:{ JSONException -> 0x0019 }
            r4.error = r0     // Catch:{ JSONException -> 0x0019 }
            goto L_0x0008
        L_0x0019:
            r0 = move-exception
            r0.printStackTrace()
            com.baidu.mapapi.search.core.SearchResult$ERRORNO r0 = com.baidu.mapapi.search.core.SearchResult.ERRORNO.RESULT_NOT_FOUND
            r4.error = r0
            goto L_0x0008
        L_0x0022:
            com.baidu.mapapi.search.core.SearchResult$ERRORNO r0 = com.baidu.mapapi.search.core.SearchResult.ERRORNO.SEARCH_SERVER_INTERNAL_ERROR     // Catch:{ JSONException -> 0x0019 }
            r4.error = r0     // Catch:{ JSONException -> 0x0019 }
            goto L_0x0008
        L_0x0027:
            com.baidu.mapapi.search.core.SearchResult$ERRORNO r0 = com.baidu.mapapi.search.core.SearchResult.ERRORNO.SEARCH_OPTION_ERROR     // Catch:{ JSONException -> 0x0019 }
            r4.error = r0     // Catch:{ JSONException -> 0x0019 }
            goto L_0x0008
        L_0x002c:
            a((org.json.JSONObject) r0, (com.baidu.mapapi.search.sug.SuggestionResult) r4)     // Catch:{ JSONException -> 0x0019 }
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.core.f.c.a(java.lang.String, com.baidu.mapapi.search.sug.SuggestionResult):boolean");
    }

    private static boolean a(JSONObject jSONObject, SuggestionResult suggestionResult) {
        int i = 0;
        if (jSONObject == null) {
            return false;
        }
        suggestionResult.error = SearchResult.ERRORNO.NO_ERROR;
        JSONArray optJSONArray = jSONObject.optJSONArray(j.c);
        if (optJSONArray == null || optJSONArray.length() == 0) {
            suggestionResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
            return true;
        }
        ArrayList arrayList = new ArrayList();
        while (true) {
            int i2 = i;
            if (i2 < optJSONArray.length()) {
                JSONObject jSONObject2 = (JSONObject) optJSONArray.opt(i2);
                if (jSONObject2 != null) {
                    SuggestionResult.SuggestionInfo suggestionInfo = new SuggestionResult.SuggestionInfo();
                    String optString = jSONObject2.optString("name");
                    if (optString != null) {
                        suggestionInfo.key = optString;
                    }
                    String optString2 = jSONObject2.optString("city");
                    if (optString2 != null) {
                        suggestionInfo.city = optString2;
                    }
                    String optString3 = jSONObject2.optString("district");
                    if (optString3 != null) {
                        suggestionInfo.district = optString3;
                    }
                    String optString4 = jSONObject2.optString("uid");
                    if (optString4 != null) {
                        suggestionInfo.uid = optString4;
                    }
                    String optString5 = jSONObject2.optString("tag");
                    if (optString5 != null) {
                        suggestionInfo.tag = optString5;
                    }
                    String optString6 = jSONObject2.optString(ActionConstant.ORDER_ADDRESS);
                    if (optString6 != null) {
                        suggestionInfo.address = optString6;
                    }
                    JSONObject optJSONObject = jSONObject2.optJSONObject(Headers.LOCATION);
                    if (optJSONObject != null) {
                        LatLng latLng = new LatLng(optJSONObject.optDouble("lat"), optJSONObject.optDouble("lng"));
                        if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
                            latLng = CoordTrans.baiduToGcj(latLng);
                        }
                        suggestionInfo.pt = latLng;
                    }
                    arrayList.add(suggestionInfo);
                }
                i = i2 + 1;
            } else {
                suggestionResult.setSuggestionInfo(arrayList);
                return true;
            }
        }
    }

    public SearchResult a(String str) {
        SuggestionResult suggestionResult = new SuggestionResult();
        if (str == null || str.equals("")) {
            suggestionResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
        } else {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("SDK_InnerError")) {
                    JSONObject optJSONObject = jSONObject.optJSONObject("SDK_InnerError");
                    if (optJSONObject.has("PermissionCheckError")) {
                        suggestionResult.error = SearchResult.ERRORNO.PERMISSION_UNFINISHED;
                    } else if (optJSONObject.has("httpStateError")) {
                        String optString = optJSONObject.optString("httpStateError");
                        if (optString.equals("NETWORK_ERROR")) {
                            suggestionResult.error = SearchResult.ERRORNO.NETWORK_ERROR;
                        } else if (optString.equals("REQUEST_ERROR")) {
                            suggestionResult.error = SearchResult.ERRORNO.REQUEST_ERROR;
                        } else {
                            suggestionResult.error = SearchResult.ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
                        }
                    }
                }
                if (!a(str, suggestionResult, true)) {
                    a(str, suggestionResult);
                }
            } catch (Exception e) {
                suggestionResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
            }
        }
        return suggestionResult;
    }

    public void a(SearchResult searchResult, Object obj) {
        if (obj != null && (obj instanceof OnGetSuggestionResultListener)) {
            ((OnGetSuggestionResultListener) obj).onGetSuggestionResult((SuggestionResult) searchResult);
        }
    }
}

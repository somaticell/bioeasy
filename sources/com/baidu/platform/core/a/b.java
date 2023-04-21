package com.baidu.platform.core.a;

import com.alipay.sdk.util.j;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.baidu.platform.base.d;
import com.facebook.common.util.UriUtil;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b extends d {
    boolean b = false;
    String c = null;

    private boolean a(String str, DistrictResult districtResult) {
        JSONObject optJSONObject;
        JSONArray optJSONArray;
        JSONArray optJSONArray2;
        int length;
        if (str == null || "".equals(str) || districtResult == null) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject == null) {
                return false;
            }
            JSONObject optJSONObject2 = jSONObject.optJSONObject(j.c);
            JSONObject optJSONObject3 = jSONObject.optJSONObject("city_result");
            if (optJSONObject2 == null || optJSONObject3 == null || optJSONObject2.optInt("error") != 0 || (optJSONObject = optJSONObject3.optJSONObject(UriUtil.LOCAL_CONTENT_SCHEME)) == null) {
                return false;
            }
            JSONObject optJSONObject4 = optJSONObject.optJSONObject("sgeo");
            if (!(optJSONObject4 == null || (optJSONArray = optJSONObject4.optJSONArray("geo_elements")) == null || optJSONArray.length() <= 0)) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject optJSONObject5 = optJSONArray.optJSONObject(i);
                    if (!(optJSONObject5 == null || (optJSONArray2 = optJSONObject5.optJSONArray("point")) == null || (length = optJSONArray2.length()) <= 0)) {
                        ArrayList arrayList2 = new ArrayList();
                        int i2 = 0;
                        int i3 = 0;
                        for (int i4 = 0; i4 < length; i4++) {
                            int optInt = optJSONArray2.optInt(i4);
                            if (i4 % 2 == 0) {
                                i3 += optInt;
                            } else {
                                i2 += optInt;
                                arrayList2.add(CoordUtil.mc2ll(new GeoPoint((double) i2, (double) i3)));
                            }
                        }
                        arrayList.add(arrayList2);
                    }
                }
                if (arrayList.size() > 0) {
                    districtResult.setPolylines(arrayList);
                    districtResult.setCenterPt(CoordUtil.decodeLocation(optJSONObject.optString("geo")));
                    districtResult.setCityCode(optJSONObject.optInt("code"));
                    districtResult.setCityName(optJSONObject.optString("cname"));
                    districtResult.error = SearchResult.ERRORNO.NO_ERROR;
                    return true;
                }
            }
            districtResult.setCityName(optJSONObject.optString("uid"));
            this.c = optJSONObject.optString("cname");
            districtResult.setCenterPt(CoordUtil.decodeLocation(optJSONObject.optString("geo")));
            districtResult.setCityCode(optJSONObject.optInt("code"));
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0081  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean b(java.lang.String r7, com.baidu.mapapi.search.district.DistrictResult r8) {
        /*
            r6 = this;
            r1 = 0
            r0 = 0
            if (r7 == 0) goto L_0x000e
            java.lang.String r2 = ""
            boolean r2 = r7.equals(r2)
            if (r2 != 0) goto L_0x000e
            if (r8 != 0) goto L_0x000f
        L_0x000e:
            return r0
        L_0x000f:
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x006c }
            r2.<init>(r7)     // Catch:{ JSONException -> 0x006c }
            if (r2 == 0) goto L_0x000e
            java.lang.String r3 = "result"
            org.json.JSONObject r3 = r2.optJSONObject(r3)
            java.lang.String r4 = "content"
            org.json.JSONObject r2 = r2.optJSONObject(r4)
            if (r3 == 0) goto L_0x000e
            if (r2 == 0) goto L_0x000e
            java.lang.String r4 = "error"
            int r3 = r3.optInt(r4)
            if (r3 != 0) goto L_0x000e
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.lang.String r0 = r6.c
            if (r0 == 0) goto L_0x0075
            java.lang.String r0 = "geo"
            java.lang.String r0 = r2.optString(r0)     // Catch:{ Exception -> 0x0071 }
            java.util.List r0 = com.baidu.mapapi.model.CoordUtil.decodeLocationList2D(r0)     // Catch:{ Exception -> 0x0071 }
        L_0x0041:
            if (r0 == 0) goto L_0x007b
            java.util.Iterator r2 = r0.iterator()
        L_0x0047:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x007b
            java.lang.Object r0 = r2.next()
            java.util.List r0 = (java.util.List) r0
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.util.Iterator r5 = r0.iterator()
        L_0x005c:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L_0x0077
            java.lang.Object r0 = r5.next()
            com.baidu.mapapi.model.LatLng r0 = (com.baidu.mapapi.model.LatLng) r0
            r4.add(r0)
            goto L_0x005c
        L_0x006c:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x000e
        L_0x0071:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0075:
            r0 = r1
            goto L_0x0041
        L_0x0077:
            r3.add(r4)
            goto L_0x0047
        L_0x007b:
            int r0 = r3.size()
            if (r0 <= 0) goto L_0x0084
            r8.setPolylines(r3)
        L_0x0084:
            java.lang.String r0 = r6.c
            r8.setCityName(r0)
            com.baidu.mapapi.search.core.SearchResult$ERRORNO r0 = com.baidu.mapapi.search.core.SearchResult.ERRORNO.NO_ERROR
            r8.error = r0
            r6.c = r1
            r0 = 1
            goto L_0x000e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.core.a.b.b(java.lang.String, com.baidu.mapapi.search.district.DistrictResult):boolean");
    }

    public SearchResult a(String str) {
        DistrictResult districtResult = new DistrictResult();
        if (str == null || str.equals("")) {
            districtResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
        } else {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("SDK_InnerError")) {
                    JSONObject optJSONObject = jSONObject.optJSONObject("SDK_InnerError");
                    if (optJSONObject.has("PermissionCheckError")) {
                        districtResult.error = SearchResult.ERRORNO.PERMISSION_UNFINISHED;
                    } else if (optJSONObject.has("httpStateError")) {
                        String optString = optJSONObject.optString("httpStateError");
                        if (optString.equals("NETWORK_ERROR")) {
                            districtResult.error = SearchResult.ERRORNO.NETWORK_ERROR;
                        } else if (optString.equals("REQUEST_ERROR")) {
                            districtResult.error = SearchResult.ERRORNO.REQUEST_ERROR;
                        } else {
                            districtResult.error = SearchResult.ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
                        }
                    }
                }
                if (!a(str, districtResult, false)) {
                    if (this.b) {
                        b(str, districtResult);
                    } else if (!a(str, districtResult)) {
                        districtResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
                    }
                }
            } catch (Exception e) {
                districtResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
            }
        }
        return districtResult;
    }

    public void a(SearchResult searchResult, Object obj) {
        if (obj != null && (obj instanceof OnGetDistricSearchResultListener)) {
            ((OnGetDistricSearchResultListener) obj).onGetDistrictResult((DistrictResult) searchResult);
        }
    }

    public void a(boolean z) {
        this.b = z;
    }
}

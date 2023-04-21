package com.baidu.platform.core.c;

import com.alipay.sdk.util.j;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.platform.base.SearchType;
import com.baidu.platform.base.d;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class g extends d {
    int b;
    int c;

    g(int i, int i2) {
        this.b = i;
        this.c = i2;
    }

    private boolean a(String str, PoiResult poiResult) {
        JSONObject optJSONObject;
        int length;
        if (str == null || str.equals("")) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject optJSONObject2 = jSONObject.optJSONObject(j.c);
            if (optJSONObject2 == null || optJSONObject2.optInt("error") != 0 || (optJSONObject = jSONObject.optJSONObject("poi_result")) == null) {
                return false;
            }
            JSONObject optJSONObject3 = optJSONObject.optJSONObject("option");
            JSONArray optJSONArray = optJSONObject.optJSONArray("contents");
            if (optJSONObject3 == null || optJSONArray == null || (length = optJSONArray.length()) <= 0) {
                return false;
            }
            int optInt = optJSONObject3.optInt("total");
            poiResult.setTotalPoiNum(optInt);
            poiResult.setCurrentPageCapacity(length);
            poiResult.setCurrentPageNum(this.b);
            if (length != 0) {
                poiResult.setTotalPageNum((optInt % this.c > 0 ? 1 : 0) + (optInt / this.c));
            }
            JSONObject optJSONObject4 = optJSONObject.optJSONObject("current_city");
            String str2 = null;
            if (optJSONObject4 != null) {
                str2 = optJSONObject4.optString("name");
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < length; i++) {
                JSONObject optJSONObject5 = optJSONArray.optJSONObject(i);
                PoiInfo poiInfo = new PoiInfo();
                if (optJSONObject5 != null) {
                    poiInfo.name = optJSONObject5.optString("name");
                    poiInfo.address = optJSONObject5.optString("addr");
                    poiInfo.uid = optJSONObject5.optString("uid");
                    poiInfo.phoneNum = optJSONObject5.optString("tel");
                    poiInfo.type = PoiInfo.POITYPE.fromInt(optJSONObject5.optInt("poiType"));
                    poiInfo.isPano = optJSONObject5.optInt("pano") == 1;
                    if (!(poiInfo.type == PoiInfo.POITYPE.BUS_LINE || poiInfo.type == PoiInfo.POITYPE.SUBWAY_LINE)) {
                        poiInfo.location = CoordUtil.decodeLocation(optJSONObject5.optString("geo"));
                    }
                    poiInfo.city = str2;
                    JSONObject optJSONObject6 = optJSONObject5.optJSONObject("ext");
                    if (!(optJSONObject6 == null || !"cater".equals(optJSONObject6.optString("src_name")) || optJSONObject5.optJSONObject("detail_info") == null)) {
                        poiInfo.hasCaterDetails = true;
                    }
                    arrayList.add(poiInfo);
                }
            }
            if (arrayList.size() > 0) {
                poiResult.setPoiInfo(arrayList);
            }
            JSONArray optJSONArray2 = optJSONObject.optJSONArray("addrs");
            ArrayList arrayList2 = new ArrayList();
            if (optJSONArray2 != null) {
                for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                    JSONObject optJSONObject7 = optJSONArray2.optJSONObject(i2);
                    PoiAddrInfo poiAddrInfo = new PoiAddrInfo();
                    if (optJSONObject7 != null) {
                        poiAddrInfo.name = optJSONObject7.optString("name");
                        poiAddrInfo.address = optJSONObject7.optString("addr");
                        poiAddrInfo.location = CoordUtil.decodeLocation(optJSONObject7.optString("geo"));
                        arrayList2.add(poiAddrInfo);
                    }
                }
            }
            if (arrayList2.size() > 0) {
                poiResult.setAddrInfo(arrayList2);
                poiResult.setHasAddrInfo(true);
            }
            if (arrayList2.size() <= 0 && arrayList.size() <= 0) {
                return false;
            }
            poiResult.error = SearchResult.ERRORNO.NO_ERROR;
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean b(String str, PoiResult poiResult) {
        JSONArray optJSONArray;
        if (str == null || str.equals("") || poiResult == null) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject == null) {
                return false;
            }
            JSONObject optJSONObject = jSONObject.optJSONObject(j.c);
            JSONObject optJSONObject2 = jSONObject.optJSONObject("traffic_citys");
            if (optJSONObject == null || optJSONObject2 == null || optJSONObject.optInt("type") != 7 || optJSONObject.optInt("error") != 0 || (optJSONArray = optJSONObject2.optJSONArray("contents")) == null || optJSONArray.length() <= 0) {
                return false;
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < optJSONArray.length(); i++) {
                JSONObject optJSONObject3 = optJSONArray.optJSONObject(i);
                if (optJSONObject3 != null) {
                    CityInfo cityInfo = new CityInfo();
                    cityInfo.num = optJSONObject3.optInt("num");
                    cityInfo.city = optJSONObject3.optString("name");
                    arrayList.add(cityInfo);
                }
            }
            if (arrayList.size() <= 0) {
                return false;
            }
            poiResult.setSuggestCityList(arrayList);
            poiResult.error = SearchResult.ERRORNO.AMBIGUOUS_KEYWORD;
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public SearchResult a(String str) {
        boolean z = false;
        PoiResult poiResult = new PoiResult();
        if (str == null || str.equals("")) {
            poiResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
        } else {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("SDK_InnerError")) {
                    JSONObject optJSONObject = jSONObject.optJSONObject("SDK_InnerError");
                    if (optJSONObject.has("PermissionCheckError")) {
                        poiResult.error = SearchResult.ERRORNO.PERMISSION_UNFINISHED;
                    } else if (optJSONObject.has("httpStateError")) {
                        String optString = optJSONObject.optString("httpStateError");
                        if (optString.equals("NETWORK_ERROR")) {
                            poiResult.error = SearchResult.ERRORNO.NETWORK_ERROR;
                        } else if (optString.equals("REQUEST_ERROR")) {
                            poiResult.error = SearchResult.ERRORNO.REQUEST_ERROR;
                        } else {
                            poiResult.error = SearchResult.ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
                        }
                    }
                }
                SearchType searchType = this.a;
                if (SearchType.POI_IN_CITY_SEARCH == a()) {
                    z = a(str, poiResult, false);
                }
                if (!z && !b(str, poiResult) && !a(str, poiResult)) {
                    poiResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
                }
            } catch (Exception e) {
                poiResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
            }
        }
        return poiResult;
    }

    public void a(SearchResult searchResult, Object obj) {
        if (obj != null && (obj instanceof OnGetPoiSearchResultListener)) {
            switch (a()) {
                case POI_NEAR_BY_SEARCH:
                case POI_IN_CITY_SEARCH:
                case POI_IN_BOUND_SEARCH:
                    ((OnGetPoiSearchResultListener) obj).onGetPoiResult((PoiResult) searchResult);
                    return;
                default:
                    return;
            }
        }
    }
}

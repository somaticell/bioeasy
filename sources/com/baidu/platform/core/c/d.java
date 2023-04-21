package com.baidu.platform.core.c;

import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import com.alipay.android.phone.mrpc.core.Headers;
import com.alipay.sdk.util.j;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.platform.comapi.util.CoordTrans;
import org.json.JSONException;
import org.json.JSONObject;

public class d extends com.baidu.platform.base.d {
    private boolean a(String str, PoiDetailResult poiDetailResult) {
        JSONObject optJSONObject;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt("status") != 0 || (optJSONObject = jSONObject.optJSONObject(j.c)) == null) {
                return false;
            }
            poiDetailResult.name = optJSONObject.optString("name");
            JSONObject optJSONObject2 = optJSONObject.optJSONObject(Headers.LOCATION);
            if (optJSONObject2 != null) {
                double optDouble = optJSONObject2.optDouble("lat");
                double optDouble2 = optJSONObject2.optDouble("lng");
                if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
                    poiDetailResult.location = CoordTrans.baiduToGcj(new LatLng(optDouble, optDouble2));
                } else {
                    poiDetailResult.location = new LatLng(optDouble, optDouble2);
                }
            }
            poiDetailResult.address = optJSONObject.optString(ActionConstant.ORDER_ADDRESS);
            poiDetailResult.telephone = optJSONObject.optString("telephone");
            poiDetailResult.uid = optJSONObject.optString("uid");
            JSONObject optJSONObject3 = optJSONObject.optJSONObject("detail_info");
            if (optJSONObject3 != null) {
                poiDetailResult.tag = optJSONObject3.optString("tag");
                poiDetailResult.detailUrl = optJSONObject3.optString("detail_url");
                poiDetailResult.type = optJSONObject3.optString("type");
                poiDetailResult.price = optJSONObject3.optDouble("price", 0.0d);
                poiDetailResult.overallRating = optJSONObject3.optDouble("overall_rating", 0.0d);
                poiDetailResult.tasteRating = optJSONObject3.optDouble("taste_rating", 0.0d);
                poiDetailResult.serviceRating = optJSONObject3.optDouble("service_rating", 0.0d);
                poiDetailResult.environmentRating = optJSONObject3.optDouble("environment_rating", 0.0d);
                poiDetailResult.facilityRating = optJSONObject3.optDouble("facility_rating", 0.0d);
                poiDetailResult.hygieneRating = optJSONObject3.optDouble("hygiene_rating", 0.0d);
                poiDetailResult.technologyRating = optJSONObject3.optDouble("technology_rating", 0.0d);
                poiDetailResult.imageNum = optJSONObject3.optInt("image_num");
                poiDetailResult.grouponNum = optJSONObject3.optInt("groupon_num");
                poiDetailResult.commentNum = optJSONObject3.optInt("comment_num");
                poiDetailResult.favoriteNum = optJSONObject3.optInt("favorite_num");
                poiDetailResult.checkinNum = optJSONObject3.optInt("checkin_num");
                poiDetailResult.shopHours = optJSONObject3.optString("shop_hours");
            }
            poiDetailResult.error = SearchResult.ERRORNO.NO_ERROR;
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public SearchResult a(String str) {
        PoiDetailResult poiDetailResult = new PoiDetailResult();
        if (str == null || str.equals("")) {
            poiDetailResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
        } else {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("SDK_InnerError")) {
                    JSONObject optJSONObject = jSONObject.optJSONObject("SDK_InnerError");
                    if (optJSONObject.has("PermissionCheckError")) {
                        poiDetailResult.error = SearchResult.ERRORNO.PERMISSION_UNFINISHED;
                    } else if (optJSONObject.has("httpStateError")) {
                        String optString = optJSONObject.optString("httpStateError");
                        if (optString.equals("NETWORK_ERROR")) {
                            poiDetailResult.error = SearchResult.ERRORNO.NETWORK_ERROR;
                        } else if (optString.equals("REQUEST_ERROR")) {
                            poiDetailResult.error = SearchResult.ERRORNO.REQUEST_ERROR;
                        } else {
                            poiDetailResult.error = SearchResult.ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
                        }
                    }
                }
                if (!a(str, poiDetailResult)) {
                    poiDetailResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
                }
            } catch (Exception e) {
                poiDetailResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
            }
        }
        return poiDetailResult;
    }

    public void a(SearchResult searchResult, Object obj) {
        if (obj != null && (obj instanceof OnGetPoiSearchResultListener)) {
            ((OnGetPoiSearchResultListener) obj).onGetPoiDetailResult((PoiDetailResult) searchResult);
        }
    }
}

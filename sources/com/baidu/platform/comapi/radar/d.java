package com.baidu.platform.comapi.radar;

import com.alipay.android.phone.mrpc.core.Headers;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.UIMsg;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.radar.RadarNearbyInfo;
import com.baidu.mapapi.radar.RadarNearbyResult;
import com.baidu.mapapi.radar.RadarSearchError;
import com.baidu.platform.comapi.util.CoordTrans;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class d {
    public static RadarNearbyResult a(String str, int i, int i2) {
        if (str == null || str.equals("")) {
            return null;
        }
        RadarNearbyResult radarNearbyResult = new RadarNearbyResult();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject == null) {
                return null;
            }
            int optInt = jSONObject.optInt("total");
            radarNearbyResult.totalNum = optInt;
            JSONArray optJSONArray = jSONObject.optJSONArray("pois");
            int length = optJSONArray != null ? optJSONArray.length() : 0;
            radarNearbyResult.pageNum = (optInt % i2 > 0 ? 1 : 0) + (optInt / i2);
            radarNearbyResult.pageIndex = i;
            if (length <= 0) {
                return radarNearbyResult;
            }
            ArrayList arrayList = new ArrayList();
            for (int i3 = 0; i3 < length; i3++) {
                RadarNearbyInfo radarNearbyInfo = new RadarNearbyInfo();
                JSONObject optJSONObject = optJSONArray.optJSONObject(i3);
                if (optJSONObject != null) {
                    radarNearbyInfo.userID = optJSONObject.optString("userid");
                    JSONArray optJSONArray2 = optJSONObject.optJSONArray(Headers.LOCATION);
                    if (optJSONArray2 != null) {
                        radarNearbyInfo.pt = new LatLng(optJSONArray2.optDouble(1), optJSONArray2.optDouble(0));
                        if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
                            radarNearbyInfo.pt = CoordTrans.baiduToGcj(radarNearbyInfo.pt);
                        }
                    }
                    radarNearbyInfo.distance = optJSONObject.optInt("distance");
                    radarNearbyInfo.mobileName = optJSONObject.optString("mb");
                    radarNearbyInfo.mobileOS = optJSONObject.optString("os");
                    radarNearbyInfo.comments = optJSONObject.optString("comments");
                    radarNearbyInfo.timeStamp = new Date(((long) optJSONObject.optDouble("ctm")) * 1000);
                    arrayList.add(radarNearbyInfo);
                }
            }
            radarNearbyResult.infoList = arrayList;
            return radarNearbyResult;
        } catch (JSONException e) {
            e.printStackTrace();
            return radarNearbyResult;
        }
    }

    public static RadarSearchError a(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject == null) {
                return null;
            }
            int optInt = jSONObject.optInt("status");
            RadarSearchError radarSearchError = RadarSearchError.RADAR_NO_ERROR;
            switch (optInt) {
                case 0:
                    return RadarSearchError.RADAR_NO_ERROR;
                case 2:
                case UIMsg.l_ErrorNo.NETWORK_ERROR_404:
                    return RadarSearchError.RADAR_NETWORK_ERROR;
                case 8:
                    return RadarSearchError.RADAR_NETWORK_TIMEOUT;
                case 500:
                case UIMsg.d_ResultType.LOC_INFO_UPLOAD:
                    return RadarSearchError.RADAR_AK_ERROR;
                case UIMsg.d_ResultType.NEWVERSION_DOWNLOAD:
                    return RadarSearchError.RADAR_FORBID_BY_USER;
                case UIMsg.d_ResultType.CELLID_LOCATE_REQ:
                    return RadarSearchError.RADAR_FORBID_BY_ADMIN;
                case UIMsg.d_ResultType.SUGGESTION_SEARCH:
                    return RadarSearchError.RADAR_AK_NOT_BIND;
                case 507:
                    return RadarSearchError.RADAR_USERID_NOT_EXIST;
                case UIMsg.d_ResultType.LONG_URL:
                    return RadarSearchError.RADAR_PERMISSION_UNFINISHED;
                default:
                    return RadarSearchError.RADAR_NO_RESULT;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

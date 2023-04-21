package com.baidu.platform.comapi.radar;

import cn.com.bioeasy.app.utils.ListUtils;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.http.HttpClient;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.radar.RadarNearbySearchOption;
import com.baidu.mapapi.radar.RadarNearbySearchSortType;
import com.baidu.mapapi.radar.RadarUploadInfo;
import com.baidu.platform.comapi.util.CoordTrans;
import com.baidu.platform.comjni.util.AppMD5;
import java.util.Calendar;

public class c {
    public static String a(RadarNearbySearchOption radarNearbySearchOption, String str, LatLng latLng) {
        boolean z;
        LatLng latLng2;
        boolean z2 = false;
        if (str == null || str.equals("") || radarNearbySearchOption == null || (radarNearbySearchOption.mCenterPt == null && latLng == null)) {
            return null;
        }
        if (radarNearbySearchOption.mRadius <= 0) {
            return null;
        }
        if (radarNearbySearchOption.sortType == RadarNearbySearchSortType.distance_from_far_to_near || radarNearbySearchOption.sortType == RadarNearbySearchSortType.distance_from_near_to_far) {
            if (radarNearbySearchOption.sortType == RadarNearbySearchSortType.distance_from_far_to_near) {
                z = false;
                z2 = true;
            } else {
                z = false;
            }
        } else if (radarNearbySearchOption.sortType == RadarNearbySearchSortType.time_from_past_to_recent) {
            z2 = true;
            z = true;
        } else {
            z = true;
        }
        if (radarNearbySearchOption.mCenterPt != null) {
            latLng2 = radarNearbySearchOption.mCenterPt;
        } else if (latLng == null) {
            return null;
        } else {
            latLng2 = latLng;
        }
        if (latLng2 != null && SDKInitializer.getCoordType() == CoordType.GCJ02) {
            latLng2 = CoordTrans.gcjToBaidu(latLng2);
        }
        String str2 = (((((((((new String() + (HttpClient.isHttpsEnable ? "https://api.map.baidu.com/sdkproxy/lbs_androidsdk/RadarService/" : "http://api.map.baidu.com/sdkproxy/lbs_androidsdk/RadarService/")) + "nearby?") + "userid=" + AppMD5.encodeUrlParamsValue(str)) + "&coord_type=3") + "&longitude=" + latLng2.longitude) + "&latitude=" + latLng2.latitude) + "&radius=" + radarNearbySearchOption.mRadius) + "&page_index=" + radarNearbySearchOption.mPageNum) + "&page_size=" + radarNearbySearchOption.mPageCapacity) + "&sortby=";
        String str3 = (!z ? str2 + AppMD5.encodeUrlParamsValue("distance:") : str2 + AppMD5.encodeUrlParamsValue("ctm:")) + (!z2 ? AppMD5.encodeUrlParamsValue("-1") : 1);
        if (radarNearbySearchOption.mStart == null || radarNearbySearchOption.mEnd == null) {
            return str3;
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(radarNearbySearchOption.mStart);
        long timeInMillis = instance.getTimeInMillis();
        instance.setTime(radarNearbySearchOption.mEnd);
        long timeInMillis2 = instance.getTimeInMillis();
        if (timeInMillis >= timeInMillis2) {
            return null;
        }
        return str3 + "&filter=" + AppMD5.encodeUrlParamsValue(String.valueOf(timeInMillis / 1000) + ListUtils.DEFAULT_JOIN_SEPARATOR + String.valueOf(timeInMillis2 / 1000));
    }

    public static String a(RadarUploadInfo radarUploadInfo, String str) {
        if (str == null || str.equals("") || radarUploadInfo == null || radarUploadInfo.pt == null) {
            return null;
        }
        LatLng latLng = radarUploadInfo.pt;
        if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
            latLng = CoordTrans.gcjToBaidu(latLng);
        }
        return ((((((new String() + (HttpClient.isHttpsEnable ? "https://api.map.baidu.com/sdkproxy/lbs_androidsdk/RadarService/" : "http://api.map.baidu.com/sdkproxy/lbs_androidsdk/RadarService/")) + "upload_poi?") + "userid=" + AppMD5.encodeUrlParamsValue(str)) + "&coord_type=3") + "&longitude=" + AppMD5.encodeUrlParamsValue(latLng.longitude + "")) + "&latitude=" + AppMD5.encodeUrlParamsValue(latLng.latitude + "")) + "&comments=" + AppMD5.encodeUrlParamsValue(radarUploadInfo.comments + "");
    }

    public static String a(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        return new String() + (HttpClient.isHttpsEnable ? "https://api.map.baidu.com/sdkproxy/lbs_androidsdk/RadarService/" : "http://api.map.baidu.com/sdkproxy/lbs_androidsdk/RadarService/") + "delete_poi?userid=" + AppMD5.encodeUrlParamsValue(str);
    }
}

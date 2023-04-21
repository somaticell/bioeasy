package com.baidu.mapapi.radar;

public interface RadarSearchListener {
    void onGetClearInfoState(RadarSearchError radarSearchError);

    void onGetNearbyInfoList(RadarNearbyResult radarNearbyResult, RadarSearchError radarSearchError);

    void onGetUploadState(RadarSearchError radarSearchError);
}

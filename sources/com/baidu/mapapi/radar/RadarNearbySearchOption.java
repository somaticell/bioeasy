package com.baidu.mapapi.radar;

import com.baidu.mapapi.model.LatLng;
import java.util.Date;

public class RadarNearbySearchOption {
    public LatLng mCenterPt;
    public Date mEnd;
    public int mPageCapacity = 10;
    public int mPageNum = 0;
    public int mRadius = -1;
    public Date mStart;
    public RadarNearbySearchSortType sortType = RadarNearbySearchSortType.distance_from_near_to_far;

    public RadarNearbySearchOption centerPt(LatLng latLng) {
        if (latLng != null) {
            this.mCenterPt = latLng;
        }
        return this;
    }

    public RadarNearbySearchOption pageCapacity(int i) {
        this.mPageCapacity = i;
        return this;
    }

    public RadarNearbySearchOption pageNum(int i) {
        this.mPageNum = i;
        return this;
    }

    public RadarNearbySearchOption radius(int i) {
        this.mRadius = i;
        return this;
    }

    public RadarNearbySearchOption sortType(RadarNearbySearchSortType radarNearbySearchSortType) {
        if (radarNearbySearchSortType != null) {
            this.sortType = radarNearbySearchSortType;
        }
        return this;
    }

    public RadarNearbySearchOption timeRange(Date date, Date date2) {
        if (!(date == null || date2 == null)) {
            this.mStart = date;
            this.mEnd = date2;
        }
        return this;
    }
}

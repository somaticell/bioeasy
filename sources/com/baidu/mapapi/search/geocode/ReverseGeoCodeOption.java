package com.baidu.mapapi.search.geocode;

import com.baidu.mapapi.model.LatLng;

public class ReverseGeoCodeOption {
    public int latest_admin = 0;
    public LatLng mLocation = null;

    public ReverseGeoCodeOption location(LatLng latLng) {
        this.mLocation = latLng;
        return this;
    }

    public ReverseGeoCodeOption newVersion(int i) {
        this.latest_admin = i;
        return this;
    }
}

package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable;

final class e implements Parcelable.Creator<PoiInfo> {
    e() {
    }

    /* renamed from: a */
    public PoiInfo createFromParcel(Parcel parcel) {
        return new PoiInfo(parcel);
    }

    /* renamed from: a */
    public PoiInfo[] newArray(int i) {
        return new PoiInfo[i];
    }
}

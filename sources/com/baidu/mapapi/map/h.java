package com.baidu.mapapi.map;

import android.os.Parcel;
import android.os.Parcelable;

final class h implements Parcelable.Creator<MapStatus> {
    h() {
    }

    /* renamed from: a */
    public MapStatus createFromParcel(Parcel parcel) {
        return new MapStatus(parcel);
    }

    /* renamed from: a */
    public MapStatus[] newArray(int i) {
        return new MapStatus[i];
    }
}

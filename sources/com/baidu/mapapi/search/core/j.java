package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable;

final class j implements Parcelable.Creator<TaxiInfo> {
    j() {
    }

    /* renamed from: a */
    public TaxiInfo createFromParcel(Parcel parcel) {
        return new TaxiInfo(parcel);
    }

    /* renamed from: a */
    public TaxiInfo[] newArray(int i) {
        return new TaxiInfo[i];
    }
}

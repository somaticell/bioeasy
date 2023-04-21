package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable;

final class f implements Parcelable.Creator<PriceInfo> {
    f() {
    }

    /* renamed from: a */
    public PriceInfo createFromParcel(Parcel parcel) {
        return new PriceInfo(parcel);
    }

    /* renamed from: a */
    public PriceInfo[] newArray(int i) {
        return new PriceInfo[i];
    }
}

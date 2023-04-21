package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable;

final class l implements Parcelable.Creator<TransitBaseInfo> {
    l() {
    }

    /* renamed from: a */
    public TransitBaseInfo createFromParcel(Parcel parcel) {
        return new TransitBaseInfo(parcel);
    }

    /* renamed from: a */
    public TransitBaseInfo[] newArray(int i) {
        return new TransitBaseInfo[i];
    }
}

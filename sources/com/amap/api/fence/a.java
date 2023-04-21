package com.amap.api.fence;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: Fence */
class a implements Parcelable.Creator<Fence> {
    a() {
    }

    /* renamed from: a */
    public Fence createFromParcel(Parcel parcel) {
        return new Fence(parcel, (a) null);
    }

    /* renamed from: a */
    public Fence[] newArray(int i) {
        return new Fence[i];
    }
}

package com.baidu.platform.comjni.tools;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class ParcelItem implements Parcelable {
    public static final Parcelable.Creator<ParcelItem> CREATOR = new b();
    private Bundle a;

    public int describeContents() {
        return 0;
    }

    public Bundle getBundle() {
        return this.a;
    }

    public void setBundle(Bundle bundle) {
        this.a = bundle;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.a);
    }
}

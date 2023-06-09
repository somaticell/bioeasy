package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable;

public class PlaneInfo extends TransitBaseInfo {
    public static final Parcelable.Creator<PlaneInfo> CREATOR = new d();
    private double a;
    private String b;
    private double c;
    private String d;

    public PlaneInfo() {
    }

    protected PlaneInfo(Parcel parcel) {
        super(parcel);
        this.a = parcel.readDouble();
        this.b = parcel.readString();
        this.c = parcel.readDouble();
        this.d = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public String getAirlines() {
        return this.b;
    }

    public String getBooking() {
        return this.d;
    }

    public double getDiscount() {
        return this.a;
    }

    public double getPrice() {
        return this.c;
    }

    public void setAirlines(String str) {
        this.b = str;
    }

    public void setBooking(String str) {
        this.d = str;
    }

    public void setDiscount(double d2) {
        this.a = d2;
    }

    public void setPrice(double d2) {
        this.c = d2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeDouble(this.a);
        parcel.writeString(this.b);
        parcel.writeDouble(this.c);
        parcel.writeString(this.d);
    }
}

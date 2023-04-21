package com.baidu.mapapi.model;

import android.os.Parcel;
import android.os.Parcelable;

public final class LatLngBounds implements Parcelable {
    public static final Parcelable.Creator<LatLngBounds> CREATOR = new b();
    public final LatLng northeast;
    public final LatLng southwest;

    public static final class Builder {
        private double a;
        private double b;
        private double c;
        private double d;
        private boolean e = true;

        public LatLngBounds build() {
            return new LatLngBounds(new LatLng(this.b, this.d), new LatLng(this.a, this.c));
        }

        public Builder include(LatLng latLng) {
            if (latLng != null) {
                if (this.e) {
                    this.e = false;
                    double d2 = latLng.latitude;
                    this.a = d2;
                    this.b = d2;
                    double d3 = latLng.longitude;
                    this.c = d3;
                    this.d = d3;
                }
                double d4 = latLng.latitude;
                double d5 = latLng.longitude;
                if (d4 < this.a) {
                    this.a = d4;
                }
                if (d4 > this.b) {
                    this.b = d4;
                }
                if (d5 < this.c) {
                    this.c = d5;
                }
                if (d5 > this.d) {
                    this.d = d5;
                }
            }
            return this;
        }
    }

    protected LatLngBounds(Parcel parcel) {
        this.northeast = (LatLng) parcel.readParcelable(LatLng.class.getClassLoader());
        this.southwest = (LatLng) parcel.readParcelable(LatLng.class.getClassLoader());
    }

    LatLngBounds(LatLng latLng, LatLng latLng2) {
        this.northeast = latLng;
        this.southwest = latLng2;
    }

    public boolean contains(LatLng latLng) {
        if (latLng == null) {
            return false;
        }
        double d = this.southwest.latitude;
        double d2 = this.northeast.latitude;
        double d3 = this.southwest.longitude;
        double d4 = this.northeast.longitude;
        double d5 = latLng.latitude;
        double d6 = latLng.longitude;
        return d5 >= d && d5 <= d2 && d6 >= d3 && d6 <= d4;
    }

    public int describeContents() {
        return 0;
    }

    public LatLng getCenter() {
        return new LatLng(((this.northeast.latitude - this.southwest.latitude) / 2.0d) + this.southwest.latitude, ((this.northeast.longitude - this.southwest.longitude) / 2.0d) + this.southwest.longitude);
    }

    public String toString() {
        return "southwest: " + this.southwest.latitude + ", " + this.southwest.longitude + "\n" + "northeast: " + this.northeast.latitude + ", " + this.northeast.longitude;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.northeast, i);
        parcel.writeParcelable(this.southwest, i);
    }
}

package com.amap.api.fence;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.loc.bw;

public class Fence implements Parcelable {
    public static final Parcelable.Creator<Fence> CREATOR = new a();
    public PendingIntent a;
    public String b;
    public double c;
    public double d;
    public float e;
    public long f;
    public int g;
    public long h;
    private long i;
    private int j;

    /* synthetic */ Fence(Parcel parcel, a aVar) {
        this(parcel);
    }

    public Fence() {
        this.a = null;
        this.b = null;
        this.c = 0.0d;
        this.d = 0.0d;
        this.e = 0.0f;
        this.f = -1;
        this.i = -1;
        this.j = 3;
        this.g = -1;
        this.h = -1;
    }

    private Fence(Parcel parcel) {
        this.a = null;
        this.b = null;
        this.c = 0.0d;
        this.d = 0.0d;
        this.e = 0.0f;
        this.f = -1;
        this.i = -1;
        this.j = 3;
        this.g = -1;
        this.h = -1;
        if (parcel != null) {
            this.b = parcel.readString();
            this.c = parcel.readDouble();
            this.d = parcel.readDouble();
            this.e = parcel.readFloat();
            this.f = parcel.readLong();
            this.i = parcel.readLong();
            this.j = parcel.readInt();
            this.g = parcel.readInt();
            this.h = parcel.readLong();
        }
    }

    public int a() {
        return this.j;
    }

    public long b() {
        return this.f;
    }

    public void a(long j2) {
        if (j2 < 0) {
            this.f = -1;
        } else {
            this.f = bw.b() + j2;
        }
    }

    public long c() {
        return this.i;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.b);
        parcel.writeDouble(this.c);
        parcel.writeDouble(this.d);
        parcel.writeFloat(this.e);
        parcel.writeLong(this.f);
        parcel.writeLong(this.i);
        parcel.writeInt(this.j);
        parcel.writeInt(this.g);
        parcel.writeLong(this.h);
    }
}

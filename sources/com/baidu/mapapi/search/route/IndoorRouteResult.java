package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable;
import com.baidu.mapapi.search.core.SearchResult;
import java.util.List;

public class IndoorRouteResult extends SearchResult {
    public static final Parcelable.Creator<IndoorRouteResult> CREATOR = new h();
    private List<IndoorRouteLine> a;

    public IndoorRouteResult() {
    }

    protected IndoorRouteResult(Parcel parcel) {
        super(parcel);
        this.a = parcel.createTypedArrayList(IndoorRouteLine.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public List<IndoorRouteLine> getRouteLines() {
        return this.a;
    }

    public void setRouteLines(List<IndoorRouteLine> list) {
        this.a = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.a);
    }
}

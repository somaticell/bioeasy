package cn.com.bioeasy.app.upload;

import android.os.Parcel;
import android.os.Parcelable;

public class UploadParam implements Parcelable {
    public static final Parcelable.Creator<UploadParam> CREATOR = new Parcelable.Creator<UploadParam>() {
        public UploadParam[] newArray(int size) {
            return new UploadParam[size];
        }

        public UploadParam createFromParcel(Parcel in) {
            UploadParam param = new UploadParam();
            param.key = in.readString();
            param.value = in.readString();
            return param;
        }
    };
    public String key;
    public String value;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.value);
    }
}

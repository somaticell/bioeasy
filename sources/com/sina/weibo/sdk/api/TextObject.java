package com.sina.weibo.sdk.api;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class TextObject implements Parcelable {
    public static final Parcelable.Creator<TextObject> CREATOR = new Parcelable.Creator<TextObject>() {
        public TextObject createFromParcel(Parcel in) {
            return new TextObject(in);
        }

        public TextObject[] newArray(int size) {
            return new TextObject[size];
        }
    };
    public String text;

    public TextObject() {
    }

    public TextObject(Parcel in) {
        this.text = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
    }

    public boolean checkArgs() {
        if (TextUtils.isEmpty(this.text) || this.text.length() > 1024) {
            return false;
        }
        return true;
    }

    public int getObjType() {
        return 1;
    }
}

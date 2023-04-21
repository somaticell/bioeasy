package com.sina.weibo.sdk.api;

import android.os.Parcel;
import android.os.Parcelable;
import cn.sharesdk.framework.utils.d;

public class WebpageObject implements Parcelable {
    public static final Parcelable.Creator<WebpageObject> CREATOR = new Parcelable.Creator<WebpageObject>() {
        public WebpageObject createFromParcel(Parcel in) {
            return new WebpageObject(in);
        }

        public WebpageObject[] newArray(int size) {
            return new WebpageObject[size];
        }
    };
    public static final String EXTRA_KEY_DEFAULTTEXT = "extra_key_defaulttext";
    public String actionUrl;
    public String defaultText;
    public String description;
    public String identify;
    public String schema;
    public byte[] thumbData;
    public String title;

    public WebpageObject() {
    }

    public WebpageObject(Parcel in) {
        this.actionUrl = in.readString();
        this.schema = in.readString();
        this.identify = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.thumbData = in.createByteArray();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.actionUrl);
        dest.writeString(this.schema);
        dest.writeString(this.identify);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeByteArray(this.thumbData);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x002d A[SYNTHETIC, Splitter:B:15:0x002d] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x003e A[SYNTHETIC, Splitter:B:22:0x003e] */
    /* JADX WARNING: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setThumbImage(android.graphics.Bitmap r4) {
        /*
            r3 = this;
            r2 = 0
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x0022, all -> 0x003a }
            r1.<init>()     // Catch:{ Throwable -> 0x0022, all -> 0x003a }
            android.graphics.Bitmap$CompressFormat r0 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ Throwable -> 0x004d }
            r2 = 85
            r4.compress(r0, r2, r1)     // Catch:{ Throwable -> 0x004d }
            byte[] r0 = r1.toByteArray()     // Catch:{ Throwable -> 0x004d }
            r3.thumbData = r0     // Catch:{ Throwable -> 0x004d }
            if (r1 == 0) goto L_0x0018
            r1.close()     // Catch:{ Throwable -> 0x0019 }
        L_0x0018:
            return
        L_0x0019:
            r0 = move-exception
            com.mob.tools.log.NLog r1 = cn.sharesdk.framework.utils.d.a()
            r1.d(r0)
            goto L_0x0018
        L_0x0022:
            r0 = move-exception
            r1 = r2
        L_0x0024:
            com.mob.tools.log.NLog r2 = cn.sharesdk.framework.utils.d.a()     // Catch:{ all -> 0x004b }
            r2.d(r0)     // Catch:{ all -> 0x004b }
            if (r1 == 0) goto L_0x0018
            r1.close()     // Catch:{ Throwable -> 0x0031 }
            goto L_0x0018
        L_0x0031:
            r0 = move-exception
            com.mob.tools.log.NLog r1 = cn.sharesdk.framework.utils.d.a()
            r1.d(r0)
            goto L_0x0018
        L_0x003a:
            r0 = move-exception
            r1 = r2
        L_0x003c:
            if (r1 == 0) goto L_0x0041
            r1.close()     // Catch:{ Throwable -> 0x0042 }
        L_0x0041:
            throw r0
        L_0x0042:
            r1 = move-exception
            com.mob.tools.log.NLog r2 = cn.sharesdk.framework.utils.d.a()
            r2.d(r1)
            goto L_0x0041
        L_0x004b:
            r0 = move-exception
            goto L_0x003c
        L_0x004d:
            r0 = move-exception
            goto L_0x0024
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.api.WebpageObject.setThumbImage(android.graphics.Bitmap):void");
    }

    public boolean checkArgs() {
        if (this.actionUrl == null || this.actionUrl.length() > 512) {
            d.a().d("checkArgs fail, actionUrl is invalid", new Object[0]);
            return false;
        } else if (this.identify == null || this.identify.length() > 512) {
            d.a().d("checkArgs fail, identify is invalid", new Object[0]);
            return false;
        } else if (this.thumbData == null || this.thumbData.length > 32768) {
            new Throwable("checkArgs fail, thumbData is invalid,size is " + (this.thumbData != null ? this.thumbData.length : -1) + "! more then 32768.").printStackTrace();
            return false;
        } else if (this.title == null || this.title.length() > 512) {
            d.a().d("checkArgs fail, title is invalid", new Object[0]);
            return false;
        } else if (this.description != null && this.description.length() <= 1024) {
            return true;
        } else {
            d.a().d("checkArgs fail, description is invalid", new Object[0]);
            return false;
        }
    }

    public int getObjType() {
        return 5;
    }

    public int describeContents() {
        return 0;
    }
}

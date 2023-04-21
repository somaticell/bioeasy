package cn.sharesdk.wechat.utils;

import android.os.Bundle;
import cn.sharesdk.framework.utils.d;
import cn.sharesdk.wechat.utils.WXMediaMessage;
import java.io.File;

public class WXAppExtendObject implements WXMediaMessage.IMediaObject {
    public String extInfo;
    public byte[] fileData;
    public String filePath;

    public WXAppExtendObject() {
    }

    public WXAppExtendObject(String paramString, byte[] paramArrayOfByte) {
        this.extInfo = paramString;
        this.fileData = paramArrayOfByte;
    }

    public WXAppExtendObject(String paramString1, String paramString2) {
        this.extInfo = paramString1;
        this.filePath = paramString2;
    }

    public void serialize(Bundle paramBundle) {
        paramBundle.putString("_wxappextendobject_extInfo", this.extInfo);
        paramBundle.putByteArray("_wxappextendobject_fileData", this.fileData);
        paramBundle.putString("_wxappextendobject_filePath", this.filePath);
    }

    public void unserialize(Bundle paramBundle) {
        this.extInfo = paramBundle.getString("_wxappextendobject_extInfo");
        this.fileData = paramBundle.getByteArray("_wxappextendobject_fileData");
        this.filePath = paramBundle.getString("_wxappextendobject_filePath");
    }

    public int type() {
        return 7;
    }

    public boolean checkArgs() {
        if ((this.extInfo == null || this.extInfo.length() == 0) && ((this.filePath == null || this.filePath.length() == 0) && (this.fileData == null || this.fileData.length == 0))) {
            d.a().d("checkArgs fail, all arguments is null", new Object[0]);
            return false;
        } else if (this.extInfo != null && this.extInfo.length() > 2048) {
            d.a().d("checkArgs fail, extInfo is invalid", new Object[0]);
            return false;
        } else if (this.filePath != null && this.filePath.length() > 10240) {
            d.a().d("checkArgs fail, filePath is invalid", new Object[0]);
            return false;
        } else if (this.filePath != null && new File(this.filePath).length() > 10485760) {
            d.a().d("checkArgs fail, fileSize is too large", new Object[0]);
            return false;
        } else if (this.fileData == null || this.fileData.length <= 10485760) {
            return true;
        } else {
            d.a().d("checkArgs fail, fileData is too large", new Object[0]);
            return false;
        }
    }
}

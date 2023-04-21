package cn.sharesdk.wechat.utils;

import android.os.Bundle;
import cn.sharesdk.framework.utils.d;
import cn.sharesdk.wechat.utils.WXMediaMessage;
import java.io.File;

public class WXFileObject implements WXMediaMessage.IMediaObject {
    public byte[] fileData;
    public String filePath;

    public WXFileObject() {
        this.fileData = null;
        this.filePath = null;
    }

    public WXFileObject(byte[] paramArrayOfByte) {
        this.fileData = paramArrayOfByte;
    }

    public WXFileObject(String paramString) {
        this.filePath = paramString;
    }

    public void setFileData(byte[] paramArrayOfByte) {
        this.fileData = paramArrayOfByte;
    }

    public void setFilePath(String paramString) {
        this.filePath = paramString;
    }

    public void serialize(Bundle paramBundle) {
        paramBundle.putByteArray("_wxfileobject_fileData", this.fileData);
        paramBundle.putString("_wxfileobject_filePath", this.filePath);
    }

    public void unserialize(Bundle paramBundle) {
        this.fileData = paramBundle.getByteArray("_wxfileobject_fileData");
        this.filePath = paramBundle.getString("_wxfileobject_filePath");
    }

    public int type() {
        return 6;
    }

    public boolean checkArgs() {
        if ((this.fileData == null || this.fileData.length == 0) && (this.filePath == null || this.filePath.length() == 0)) {
            d.a().d("checkArgs fail, both arguments is null", new Object[0]);
            return false;
        } else if (this.fileData != null && this.fileData.length > 10485760) {
            d.a().d("checkArgs fail, fileData is too large", new Object[0]);
            return false;
        } else if (this.filePath == null || new File(this.filePath).length() <= 10485760) {
            return true;
        } else {
            d.a().d("checkArgs fail, fileSize is too large", new Object[0]);
            return false;
        }
    }
}

package cn.sharesdk.wechat.utils;

import android.graphics.Bitmap;
import android.os.Bundle;
import cn.sharesdk.framework.utils.d;
import cn.sharesdk.wechat.utils.WXMediaMessage;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class WXImageObject implements WXMediaMessage.IMediaObject {
    public byte[] imageData;
    public String imagePath;
    public String imageUrl;

    public WXImageObject() {
    }

    public WXImageObject(byte[] paramArrayOfByte) {
        this.imageData = paramArrayOfByte;
    }

    public WXImageObject(Bitmap paramBitmap) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            paramBitmap.compress(Bitmap.CompressFormat.JPEG, 85, byteArrayOutputStream);
            this.imageData = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        } catch (Exception e) {
            d.a().d(e);
        }
    }

    public void serialize(Bundle paramBundle) {
        paramBundle.putByteArray("_wximageobject_imageData", this.imageData);
        paramBundle.putString("_wximageobject_imagePath", this.imagePath);
        paramBundle.putString("_wximageobject_imageUrl", this.imageUrl);
    }

    public void unserialize(Bundle paramBundle) {
        this.imageData = paramBundle.getByteArray("_wximageobject_imageData");
        this.imagePath = paramBundle.getString("_wximageobject_imagePath");
        this.imageUrl = paramBundle.getString("_wximageobject_imageUrl");
    }

    public int type() {
        return 2;
    }

    public boolean checkArgs() {
        if ((this.imageData == null || this.imageData.length == 0) && ((this.imagePath == null || this.imagePath.length() == 0) && (this.imageUrl == null || this.imageUrl.length() == 0))) {
            d.a().d("checkArgs fail, all arguments are null", new Object[0]);
            return false;
        } else if (this.imageData != null && this.imageData.length > 10485760) {
            d.a().d("checkArgs fail, content is too large", new Object[0]);
            return false;
        } else if (this.imagePath != null && this.imagePath.length() > 10240) {
            d.a().d("checkArgs fail, path is invalid", new Object[0]);
            return false;
        } else if (this.imagePath != null && new File(this.imagePath).length() > 10485760) {
            d.a().d("checkArgs fail, image content is too large", new Object[0]);
            return false;
        } else if (this.imageUrl == null || this.imageUrl.length() <= 10240) {
            return true;
        } else {
            d.a().d("checkArgs fail, url is invalid", new Object[0]);
            return false;
        }
    }
}

package cn.sharesdk.wechat.utils;

import android.os.Bundle;
import android.text.TextUtils;
import cn.sharesdk.framework.utils.d;
import cn.sharesdk.wechat.utils.WXMediaMessage;
import java.io.File;

public class WXEmojiObject implements WXMediaMessage.IMediaObject {
    public byte[] emojiData;
    public String emojiPath;

    public WXEmojiObject() {
    }

    public WXEmojiObject(byte[] paramArrayOfByte) {
        this.emojiData = paramArrayOfByte;
    }

    public WXEmojiObject(String paramString) {
        this.emojiPath = paramString;
    }

    public void setEmojiData(byte[] paramArrayOfByte) {
        this.emojiData = paramArrayOfByte;
    }

    public void setEmojiPath(String paramString) {
        this.emojiPath = paramString;
    }

    public void serialize(Bundle paramBundle) {
        paramBundle.putByteArray("_wxemojiobject_emojiData", this.emojiData);
        paramBundle.putString("_wxemojiobject_emojiPath", this.emojiPath);
    }

    public void unserialize(Bundle paramBundle) {
        this.emojiData = paramBundle.getByteArray("_wxemojiobject_emojiData");
        this.emojiPath = paramBundle.getString("_wxemojiobject_emojiPath");
    }

    public int type() {
        return 8;
    }

    public boolean checkArgs() {
        if ((this.emojiData == null || this.emojiData.length == 0) && TextUtils.isEmpty(this.emojiPath)) {
            d.a().d("MicroMsg.SDK.WXEmojiObject", "checkArgs fail, both arguments is null");
            return false;
        } else if (this.emojiData == null || this.emojiData.length <= 10485760) {
            if (this.emojiPath != null) {
                File file = new File(this.emojiPath);
                if (!file.exists()) {
                    d.a().d("MicroMsg.SDK.WXEmojiObject", "checkArgs fail, emojiPath not found");
                    return false;
                } else if (file.length() > 10485760) {
                    d.a().d("MicroMsg.SDK.WXEmojiObject", "checkArgs fail, emojiSize is too large");
                    return false;
                }
            }
            return true;
        } else {
            d.a().d("MicroMsg.SDK.WXEmojiObject", "checkArgs fail, emojiData is too large");
            return false;
        }
    }
}

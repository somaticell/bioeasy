package cn.sharesdk.wechat.utils;

import android.os.Bundle;
import cn.sharesdk.framework.utils.d;
import cn.sharesdk.wechat.utils.WXMediaMessage;

public class WXTextObject implements WXMediaMessage.IMediaObject {
    public String text;

    public WXTextObject() {
        this((String) null);
    }

    public WXTextObject(String paramString) {
        this.text = paramString;
    }

    public void serialize(Bundle paramBundle) {
        paramBundle.putString("_wxtextobject_text", this.text);
    }

    public void unserialize(Bundle paramBundle) {
        this.text = paramBundle.getString("_wxtextobject_text");
    }

    public int type() {
        return 1;
    }

    public boolean checkArgs() {
        if (this.text != null && this.text.length() != 0 && this.text.length() <= 10240) {
            return true;
        }
        d.a().d("checkArgs fail, text is invalid", new Object[0]);
        return false;
    }
}

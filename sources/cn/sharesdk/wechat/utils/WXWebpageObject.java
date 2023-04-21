package cn.sharesdk.wechat.utils;

import android.os.Bundle;
import cn.sharesdk.framework.utils.d;
import cn.sharesdk.wechat.utils.WXMediaMessage;

public class WXWebpageObject implements WXMediaMessage.IMediaObject {
    public String webpageUrl;

    public WXWebpageObject() {
    }

    public WXWebpageObject(String paramString) {
        this.webpageUrl = paramString;
    }

    public void serialize(Bundle paramBundle) {
        paramBundle.putString("_wxwebpageobject_webpageUrl", this.webpageUrl);
    }

    public void unserialize(Bundle paramBundle) {
        this.webpageUrl = paramBundle.getString("_wxwebpageobject_webpageUrl");
    }

    public int type() {
        return 5;
    }

    public boolean checkArgs() {
        if (this.webpageUrl != null && this.webpageUrl.length() != 0 && this.webpageUrl.length() <= 10240) {
            return true;
        }
        d.a().d("checkArgs fail, webpageUrl is invalid", new Object[0]);
        return false;
    }
}

package cn.sharesdk.wechat.utils;

import android.os.Bundle;
import cn.sharesdk.framework.utils.d;
import cn.sharesdk.wechat.utils.WXMediaMessage;

public class WXMiniProgramObject implements WXMediaMessage.IMediaObject {
    public String path;
    public String userName;
    public String webpageUrl;

    public void serialize(Bundle paramBundle) {
        paramBundle.putString("_wxminiprogram_webpageurl", this.webpageUrl);
        paramBundle.putString("_wxminiprogram_username", this.userName);
        paramBundle.putString("_wxminiprogram_path", this.path);
    }

    public void unserialize(Bundle paramBundle) {
        this.webpageUrl = paramBundle.getString("_wxminiprogram_webpageurl");
        this.userName = paramBundle.getString("_wxminiprogram_username");
        this.path = paramBundle.getString("_wxminiprogram_path");
    }

    public int type() {
        return 36;
    }

    public boolean checkArgs() {
        if (this.webpageUrl == null || this.webpageUrl.length() == 0 || this.webpageUrl.length() > 10240) {
            d.a().d("checkArgs fail, webpageUrl is invalid", new Object[0]);
            return false;
        } else if (this.userName == null || this.userName.length() == 0 || this.userName.length() > 10240) {
            d.a().d("checkArgs fail, webpageUrl is invalid", new Object[0]);
            return false;
        } else if (this.path != null && this.path.length() != 0 && this.path.length() <= 10240) {
            return true;
        } else {
            d.a().d("checkArgs fail, webpageUrl is invalid", new Object[0]);
            return false;
        }
    }
}

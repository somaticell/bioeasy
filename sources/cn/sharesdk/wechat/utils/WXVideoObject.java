package cn.sharesdk.wechat.utils;

import android.os.Bundle;
import cn.sharesdk.framework.utils.d;
import cn.sharesdk.wechat.utils.WXMediaMessage;

public class WXVideoObject implements WXMediaMessage.IMediaObject {
    public String videoLowBandUrl;
    public String videoUrl;

    public void serialize(Bundle paramBundle) {
        paramBundle.putString("_wxvideoobject_videoUrl", this.videoUrl);
        paramBundle.putString("_wxvideoobject_videoLowBandUrl", this.videoLowBandUrl);
    }

    public void unserialize(Bundle paramBundle) {
        this.videoUrl = paramBundle.getString("_wxvideoobject_videoUrl");
        this.videoLowBandUrl = paramBundle.getString("_wxvideoobject_videoLowBandUrl");
    }

    public int type() {
        return 4;
    }

    public boolean checkArgs() {
        if ((this.videoUrl == null || this.videoUrl.length() == 0) && (this.videoLowBandUrl == null || this.videoLowBandUrl.length() == 0)) {
            d.a().d("both arguments are null", new Object[0]);
            return false;
        } else if (this.videoUrl != null && this.videoUrl.length() > 10240) {
            d.a().d("checkArgs fail, videoUrl is too long", new Object[0]);
            return false;
        } else if (this.videoLowBandUrl == null || this.videoLowBandUrl.length() <= 10240) {
            return true;
        } else {
            d.a().d("checkArgs fail, videoLowBandUrl is too long", new Object[0]);
            return false;
        }
    }
}

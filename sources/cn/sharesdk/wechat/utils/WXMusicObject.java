package cn.sharesdk.wechat.utils;

import android.os.Bundle;
import android.text.TextUtils;
import cn.sharesdk.framework.utils.d;
import cn.sharesdk.wechat.utils.WXMediaMessage;

public class WXMusicObject implements WXMediaMessage.IMediaObject {
    public String musicDataUrl;
    public String musicLowBandDataUrl;
    public String musicLowBandUrl;
    public String musicUrl;

    public void serialize(Bundle paramBundle) {
        paramBundle.putString("_wxmusicobject_musicUrl", this.musicUrl);
        paramBundle.putString("_wxmusicobject_musicLowBandUrl", this.musicLowBandUrl);
        paramBundle.putString("_wxmusicobject_musicDataUrl", this.musicDataUrl);
        paramBundle.putString("_wxmusicobject_musicLowBandDataUrl", this.musicLowBandDataUrl);
    }

    public void unserialize(Bundle paramBundle) {
        this.musicUrl = paramBundle.getString("_wxmusicobject_musicUrl");
        this.musicLowBandUrl = paramBundle.getString("_wxmusicobject_musicLowBandUrl");
        this.musicDataUrl = paramBundle.getString("_wxmusicobject_musicDataUrl");
        this.musicLowBandDataUrl = paramBundle.getString("_wxmusicobject_musicLowBandDataUrl");
    }

    public int type() {
        return 3;
    }

    public boolean checkArgs() {
        if (TextUtils.isEmpty(this.musicUrl) && TextUtils.isEmpty(this.musicLowBandUrl)) {
            d.a().d("both musicUrl and musicLowBandUrl are null", new Object[0]);
            return false;
        } else if (this.musicUrl != null && this.musicUrl.length() > 10240) {
            d.a().d("checkArgs fail, musicUrl is too long", new Object[0]);
            return false;
        } else if (this.musicLowBandUrl == null || this.musicLowBandUrl.length() <= 10240) {
            return true;
        } else {
            d.a().d("checkArgs fail, musicLowBandUrl is too long", new Object[0]);
            return false;
        }
    }
}

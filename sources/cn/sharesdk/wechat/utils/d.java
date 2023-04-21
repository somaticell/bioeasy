package cn.sharesdk.wechat.utils;

import android.os.Bundle;
import android.support.v4.view.PointerIconCompat;
import cn.sharesdk.wechat.utils.WXMediaMessage;

/* compiled from: SendMessageReq */
public class d extends j {
    public WXMediaMessage a;
    public int b;

    public int a() {
        return 2;
    }

    public void a(Bundle bundle) {
        super.a(bundle);
        bundle.putAll(WXMediaMessage.a.a(this.a));
        bundle.putInt("_wxapi_sendmessagetowx_req_scene", this.b);
    }

    public boolean b() {
        if (this.a.getType() == 8 && (this.a.thumbData == null || this.a.thumbData.length <= 0)) {
            cn.sharesdk.framework.utils.d.a().d("checkArgs fail, thumbData should not be null when send emoji", new Object[0]);
            return false;
        } else if (this.a.thumbData != null && this.a.thumbData.length > 32768) {
            cn.sharesdk.framework.utils.d.a().d("checkArgs fail, thumbData is invalid", new Object[0]);
            return false;
        } else if (this.a.title == null || this.a.title.length() <= 512) {
            if (this.a.description != null && this.a.description.length() > 1024) {
                this.a.description = this.a.description.substring(0, PointerIconCompat.TYPE_GRABBING) + "...";
            }
            if (this.a.mediaObject != null) {
                return this.a.mediaObject.checkArgs();
            }
            cn.sharesdk.framework.utils.d.a().d("checkArgs fail, mediaObject is null", new Object[0]);
            return false;
        } else {
            cn.sharesdk.framework.utils.d.a().d("checkArgs fail, title is invalid", new Object[0]);
            return false;
        }
    }
}

package cn.com.bioeasy.healty.app.healthapp.wxapi;

import android.widget.Toast;
import cn.sharesdk.wechat.utils.WXAppExtendObject;
import cn.sharesdk.wechat.utils.WXMediaMessage;
import cn.sharesdk.wechat.utils.WechatHandlerActivity;

public class WXEntryActivity extends WechatHandlerActivity {
    public void onGetMessageFromWXReq(WXMediaMessage msg) {
        if (msg != null) {
            startActivity(getPackageManager().getLaunchIntentForPackage(getPackageName()));
        }
    }

    public void onShowMessageFromWXReq(WXMediaMessage msg) {
        if (msg != null && msg.mediaObject != null && (msg.mediaObject instanceof WXAppExtendObject)) {
            Toast.makeText(this, ((WXAppExtendObject) msg.mediaObject).extInfo, 0).show();
        }
    }
}

package cn.sharesdk.onekeyshare;

import cn.sharesdk.framework.Platform;

public interface ShareContentCustomizeCallback {
    void onShare(Platform platform, Platform.ShareParams shareParams);
}

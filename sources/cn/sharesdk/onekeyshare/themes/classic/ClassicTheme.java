package cn.sharesdk.onekeyshare.themes.classic;

import android.content.Context;
import android.content.Intent;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;
import cn.sharesdk.onekeyshare.themes.classic.land.EditPageLand;
import cn.sharesdk.onekeyshare.themes.classic.land.PlatformPageLand;
import cn.sharesdk.onekeyshare.themes.classic.port.EditPagePort;
import cn.sharesdk.onekeyshare.themes.classic.port.PlatformPagePort;

public class ClassicTheme extends OnekeyShareThemeImpl {
    /* access modifiers changed from: protected */
    public void showPlatformPage(Context context) {
        PlatformPage page;
        if (context.getResources().getConfiguration().orientation == 1) {
            page = new PlatformPagePort(this);
        } else {
            page = new PlatformPageLand(this);
        }
        page.show(context, (Intent) null);
    }

    /* access modifiers changed from: protected */
    public void showEditPage(Context context, Platform platform, Platform.ShareParams sp) {
        EditPage page;
        if (context.getResources().getConfiguration().orientation == 1) {
            page = new EditPagePort(this);
        } else {
            page = new EditPageLand(this);
        }
        page.setPlatform(platform);
        page.setShareParams(sp);
        page.show(context, (Intent) null);
    }
}

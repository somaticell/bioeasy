package cn.sharesdk.onekeyshare.themes.classic.port;

import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;
import cn.sharesdk.onekeyshare.themes.classic.FriendListPage;
import com.mob.tools.utils.ResHelper;

public class FriendListPagePort extends FriendListPage {
    private static final int DESIGN_SCREEN_WIDTH = 720;
    private static final int DESIGN_TITLE_HEIGHT = 96;

    public FriendListPagePort(OnekeyShareThemeImpl impl) {
        super(impl);
    }

    /* access modifiers changed from: protected */
    public float getRatio() {
        return ((float) ResHelper.getScreenWidth(this.activity)) / 720.0f;
    }

    /* access modifiers changed from: protected */
    public int getDesignTitleHeight() {
        return 96;
    }
}

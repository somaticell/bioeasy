package cn.sharesdk.onekeyshare;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import com.mob.tools.FakeActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class OnekeySharePage extends FakeActivity {
    private OnekeyShareThemeImpl impl;

    public OnekeySharePage(OnekeyShareThemeImpl impl2) {
        this.impl = impl2;
    }

    /* access modifiers changed from: protected */
    public final boolean isDialogMode() {
        return this.impl.dialogMode;
    }

    /* access modifiers changed from: protected */
    public final HashMap<String, Object> getShareParamsMap() {
        return this.impl.shareParamsMap;
    }

    /* access modifiers changed from: protected */
    public final boolean isSilent() {
        return this.impl.silent;
    }

    /* access modifiers changed from: protected */
    public final ArrayList<CustomerLogo> getCustomerLogos() {
        return this.impl.customerLogos;
    }

    /* access modifiers changed from: protected */
    public final HashMap<String, String> getHiddenPlatforms() {
        return this.impl.hiddenPlatforms;
    }

    /* access modifiers changed from: protected */
    public final PlatformActionListener getCallback() {
        return this.impl.callback;
    }

    /* access modifiers changed from: protected */
    public final ShareContentCustomizeCallback getCustomizeCallback() {
        return this.impl.customizeCallback;
    }

    /* access modifiers changed from: protected */
    public final boolean isDisableSSO() {
        return this.impl.disableSSO;
    }

    /* access modifiers changed from: protected */
    public final void shareSilently(Platform platform) {
        this.impl.shareSilently(platform);
    }

    /* access modifiers changed from: protected */
    public final Platform.ShareParams formateShareData(Platform platform) {
        if (this.impl.formateShareData(platform)) {
            return this.impl.shareDataToShareParams(platform);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final boolean isUseClientToShare(Platform platform) {
        return this.impl.isUseClientToShare(platform);
    }
}

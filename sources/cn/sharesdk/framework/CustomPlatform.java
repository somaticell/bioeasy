package cn.sharesdk.framework;

import android.content.Context;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.b.b.f;
import java.util.HashMap;

public abstract class CustomPlatform extends Platform {
    /* access modifiers changed from: protected */
    public abstract boolean checkAuthorize(int i, Object obj);

    public abstract String getName();

    public CustomPlatform(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public final void initDevInfo(String name) {
    }

    public int getVersion() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public final int getPlatformId() {
        return -Math.abs(getCustomPlatformId());
    }

    /* access modifiers changed from: protected */
    public int getCustomPlatformId() {
        return 1;
    }

    /* access modifiers changed from: protected */
    public final void setNetworkDevinfo() {
    }

    /* access modifiers changed from: protected */
    public void doAuthorize(String[] permissions) {
    }

    /* access modifiers changed from: protected */
    public void doShare(Platform.ShareParams params) {
    }

    /* access modifiers changed from: protected */
    public void follow(String account) {
    }

    /* access modifiers changed from: protected */
    public void timeline(int count, int page, String account) {
    }

    /* access modifiers changed from: protected */
    public void userInfor(String account) {
    }

    /* access modifiers changed from: protected */
    public void doCustomerProtocol(String url, String method, int customerAction, HashMap<String, Object> hashMap, HashMap<String, String> hashMap2) {
    }

    /* access modifiers changed from: protected */
    public final f.a filterShareContent(Platform.ShareParams params, HashMap<String, Object> hashMap) {
        return null;
    }

    /* access modifiers changed from: protected */
    public void getFriendList(int count, int cursor, String account) {
    }

    /* access modifiers changed from: protected */
    public HashMap<String, Object> getFollowings(int count, int page, String account) {
        return null;
    }

    /* access modifiers changed from: protected */
    public HashMap<String, Object> getFollowers(int count, int cursor, String account) {
        return null;
    }

    /* access modifiers changed from: protected */
    public HashMap<String, Object> getBilaterals(int count, int cursor, String account) {
        return null;
    }

    /* access modifiers changed from: protected */
    public HashMap<String, Object> filterFriendshipInfo(int action, HashMap<String, Object> hashMap) {
        return null;
    }

    public boolean hasShareCallback() {
        return false;
    }
}

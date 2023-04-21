package cn.sharesdk.framework.authorize;

import android.app.Activity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import cn.sharesdk.framework.TitleLayout;

public class AuthorizeAdapter {
    private Activity activity;
    private boolean noTitle;
    private String platform;
    private boolean popUpAnimationDisable;
    private RelativeLayout rlBody;
    private TitleLayout title;
    private WebView webview;

    /* access modifiers changed from: package-private */
    public void setActivity(Activity activity2) {
        this.activity = activity2;
    }

    public Activity getActivity() {
        return this.activity;
    }

    /* access modifiers changed from: package-private */
    public void setTitleView(TitleLayout title2) {
        this.title = title2;
    }

    public TitleLayout getTitleLayout() {
        return this.title;
    }

    /* access modifiers changed from: package-private */
    public void setWebView(WebView webview2) {
        this.webview = webview2;
    }

    public WebView getWebBody() {
        return this.webview;
    }

    /* access modifiers changed from: package-private */
    public void setNotitle(boolean noTitle2) {
        this.noTitle = noTitle2;
    }

    /* access modifiers changed from: package-private */
    public boolean isNotitle() {
        return this.noTitle;
    }

    /* access modifiers changed from: package-private */
    public void setPlatformName(String platform2) {
        this.platform = platform2;
    }

    public String getPlatformName() {
        return this.platform;
    }

    /* access modifiers changed from: package-private */
    public void setBodyView(RelativeLayout rlBody2) {
        this.rlBody = rlBody2;
    }

    public RelativeLayout getBodyView() {
        return this.rlBody;
    }

    public void onCreate() {
    }

    public void onResize(int w, int h, int oldw, int oldh) {
    }

    public void onDestroy() {
    }

    public boolean onKeyEvent(int keyCode, KeyEvent event) {
        return false;
    }

    /* access modifiers changed from: protected */
    public void disablePopUpAnimation() {
        this.popUpAnimationDisable = true;
    }

    /* access modifiers changed from: package-private */
    public boolean isPopUpAnimationDisable() {
        return this.popUpAnimationDisable;
    }

    public void onStart() {
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void onStop() {
    }

    public void onRestart() {
    }

    public boolean onFinish() {
        return false;
    }

    public void hideShareSDKLogo() {
        getTitleLayout().getChildAt(getTitleLayout().getChildCount() - 1).setVisibility(8);
    }
}

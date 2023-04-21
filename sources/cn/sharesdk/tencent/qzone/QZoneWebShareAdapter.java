package cn.sharesdk.tencent.qzone;

import android.app.Activity;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import cn.sharesdk.framework.TitleLayout;

public class QZoneWebShareAdapter {
    private Activity activity;
    private boolean noTitle;
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
    public void setBodyView(RelativeLayout rlBody2) {
        this.rlBody = rlBody2;
    }

    public RelativeLayout getBodyView() {
        return this.rlBody;
    }

    public void onCreate() {
    }

    public void onDestroy() {
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
}

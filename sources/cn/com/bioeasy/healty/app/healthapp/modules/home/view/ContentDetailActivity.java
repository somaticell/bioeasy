package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.injection.module.NetworkModule;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.Content;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.RefreshPopupWindow;
import cn.sharesdk.onekeyshare.HealthAppShare;

public class ContentDetailActivity extends BIBaseActivity {
    /* access modifiers changed from: private */
    public Content content;
    @Bind({2131231183})
    ImageView mIvShare;
    private RefreshPopupWindow refreshPopupWindow;
    @Bind({2131231008})
    TextView tvTitle;
    /* access modifiers changed from: private */
    public String webUrl;
    @Bind({2131230939})
    WebView webViewFood;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return null;
    }

    @OnClick({2131231050, 2131231183})
    public void onClicks(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                return;
            case R.id.iv_share:
                createPopupWindow();
                this.refreshPopupWindow.tab1OnClick(this.mIvShare);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_food_detail;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.tvTitle.setText(getString(R.string.information_food));
        this.mIvShare.setVisibility(0);
        initWebView();
    }

    /* access modifiers changed from: private */
    public void initWebView() {
        showProgress(getString(R.string.error_view_loading));
        this.content = (Content) getIntent().getSerializableExtra(ActionConstant.CONTENT_ID);
        if (this.content != null) {
            this.webUrl = NetworkModule.APP_SERVER_URL + "/app/content/detail?id=" + this.content.getId();
        }
        this.webViewFood.getSettings().setJavaScriptEnabled(true);
        this.webViewFood.getSettings().setDefaultTextEncodingName("UTF-8");
        this.webViewFood.getSettings().setCacheMode(2);
        this.webViewFood.loadUrl(this.webUrl);
        this.webViewFood.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.reload();
                return true;
            }

            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                ContentDetailActivity.this.hideProgress();
            }
        });
        this.webViewFood.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress >= 100) {
                    ContentDetailActivity.this.hideProgress();
                }
            }
        });
    }

    private void createPopupWindow() {
        if (this.refreshPopupWindow == null) {
            this.refreshPopupWindow = new RefreshPopupWindow(this);
            this.refreshPopupWindow.setShowShareDialog(new RefreshPopupWindow.ShowShareDialog() {
                public void showDialog() {
                    HealthAppShare.shareWxContent(ContentDetailActivity.this, ContentDetailActivity.this.content, ContentDetailActivity.this.webUrl);
                }
            });
            this.refreshPopupWindow.setReload(new RefreshPopupWindow.ReloadWebView() {
                public void reload() {
                    ContentDetailActivity.this.initWebView();
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.refreshPopupWindow != null) {
            this.refreshPopupWindow.dismiss();
            this.refreshPopupWindow = null;
        }
    }
}

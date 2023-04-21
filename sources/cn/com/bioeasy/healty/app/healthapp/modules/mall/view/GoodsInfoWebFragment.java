package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import cn.com.bioeasy.healty.app.healthapp.R;

public class GoodsInfoWebFragment extends Fragment {
    private LayoutInflater inflater;
    private boolean isFirstLoad;
    private String mHtml;
    /* access modifiers changed from: private */
    public WebSettings webSettings;
    public WebView wv_detail;

    @Nullable
    public View onCreateView(LayoutInflater inflater2, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater2;
        View rootView = inflater2.inflate(R.layout.fragment_item_info_web, (ViewGroup) null);
        initWebView(rootView);
        return rootView;
    }

    public void initWebView(View rootView) {
        this.isFirstLoad = true;
        this.wv_detail = (WebView) rootView.findViewById(R.id.wv_detail);
        this.wv_detail.setFocusable(false);
        this.webSettings = this.wv_detail.getSettings();
        this.wv_detail.getSettings().setDefaultTextEncodingName("UTF-8");
        this.webSettings.setCacheMode(1);
        this.webSettings.setCacheMode(1);
        this.wv_detail.setWebViewClient(new GoodsDetailWebViewClient());
    }

    public void loadData(String mHtml2) {
        if (this.isFirstLoad && this.wv_detail != null) {
            this.wv_detail.loadDataWithBaseURL((String) null, mHtml2 + "<head><style>img{max-width:100% !important;} table{max-width:100% !important;}</style></head>", "text/html", "utf-8", (String) null);
            this.isFirstLoad = false;
        }
    }

    public void initData(String productDetail) {
        if (productDetail != null) {
            loadData(productDetail);
        }
    }

    private class GoodsDetailWebViewClient extends WebViewClient {
        private GoodsDetailWebViewClient() {
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            GoodsInfoWebFragment.this.webSettings.setBlockNetworkImage(false);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }
}

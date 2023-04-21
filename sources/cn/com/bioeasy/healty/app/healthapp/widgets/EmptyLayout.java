package cn.com.bioeasy.healty.app.healthapp.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.bioeasy.app.widgets.netstatus.NetUtils;
import cn.com.bioeasy.healty.app.healthapp.R;
import net.oschina.common.widget.Loading;

public class EmptyLayout extends LinearLayout implements View.OnClickListener {
    public static final int HIDE_LAYOUT = 4;
    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_LOADING = 2;
    public static final int NODATA = 3;
    public static final int NODATA_ENABLE_CLICK = 5;
    public static final int NO_LOGIN = 6;
    /* access modifiers changed from: private */
    public boolean clickEnable = true;
    private final Context context;
    public ImageView img;
    /* access modifiers changed from: private */
    public View.OnClickListener listener;
    private int mErrorState;
    private Loading mLoading;
    private boolean mLoadingFriend;
    private boolean mLoadingLocalFriend;
    private String strNoDataContent = "";
    private TextView tv;

    public EmptyLayout(Context context2) {
        super(context2);
        this.context = context2;
        init();
    }

    public EmptyLayout(Context context2, AttributeSet attrs) {
        super(context2, attrs);
        this.context = context2;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_error_layout, this, false);
        this.img = (ImageView) view.findViewById(R.id.img_error_layout);
        this.tv = (TextView) view.findViewById(R.id.tv_error_layout);
        this.mLoading = (Loading) view.findViewById(R.id.animProgress);
        setBackgroundColor(-1);
        setOnClickListener(this);
        this.img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (EmptyLayout.this.clickEnable && EmptyLayout.this.listener != null) {
                    EmptyLayout.this.listener.onClick(v);
                }
            }
        });
        addView(view);
        changeErrorLayoutBgMode(this.context);
    }

    public void changeErrorLayoutBgMode(Context context1) {
    }

    public void dismiss() {
        this.mErrorState = 4;
        setVisibility(8);
    }

    public int getErrorState() {
        return this.mErrorState;
    }

    public boolean isLoadError() {
        return this.mErrorState == 1;
    }

    public boolean isLoading() {
        return this.mErrorState == 2;
    }

    public void onClick(View v) {
        if (this.clickEnable && this.listener != null) {
            this.listener.onClick(v);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        onSkinChanged();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void onSkinChanged() {
    }

    public void setErrorMessage(String msg) {
        this.tv.setText(msg);
    }

    public void setErrorImag(int imgResource) {
        try {
            this.img.setImageResource(imgResource);
        } catch (Exception e) {
        }
    }

    public void setErrorType(int i) {
        setVisibility(0);
        switch (i) {
            case 1:
                this.mErrorState = 1;
                if (NetUtils.isNetworkAvailable(this.context)) {
                    this.tv.setText(R.string.error_view_load_error_click_to_refresh);
                    this.img.setBackgroundResource(R.drawable.ic_tip_fail);
                } else {
                    this.tv.setText(R.string.error_view_network_error_click_to_refresh);
                    this.img.setBackgroundResource(R.drawable.page_icon_network);
                }
                this.img.setVisibility(0);
                this.mLoading.stop();
                this.mLoading.setVisibility(8);
                this.clickEnable = true;
                return;
            case 2:
                this.mErrorState = 2;
                this.mLoading.setVisibility(0);
                this.mLoading.start();
                this.img.setVisibility(8);
                this.clickEnable = false;
                return;
            case 4:
                this.mLoading.stop();
                setVisibility(8);
                return;
            default:
                return;
        }
    }

    public void setNoDataContent(String noDataContent) {
        this.strNoDataContent = noDataContent;
    }

    public void setOnLayoutClickListener(View.OnClickListener listener2) {
        this.listener = listener2;
    }

    public void setVisibility(int visibility) {
        if (visibility == 8) {
            this.mErrorState = 4;
        }
        super.setVisibility(visibility);
    }
}

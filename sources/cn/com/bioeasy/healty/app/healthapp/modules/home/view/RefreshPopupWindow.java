package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import cn.com.bioeasy.healty.app.healthapp.R;

public class RefreshPopupWindow extends PopupWindow implements View.OnClickListener {
    static Context cxt;
    private LinearLayout refalsh;
    public ReloadWebView reloadWebView;
    private LinearLayout share;
    public ShowShareDialog showShareDialog;
    private View view;

    public interface ReloadWebView {
        void reload();
    }

    public interface ShowShareDialog {
        void showDialog();
    }

    public void setShowShareDialog(ShowShareDialog showShareDialog2) {
        this.showShareDialog = showShareDialog2;
    }

    public void setReload(ReloadWebView reloadWebView2) {
        this.reloadWebView = reloadWebView2;
    }

    public RefreshPopupWindow(Context context) {
        super(context);
        cxt = context;
        initPopup();
    }

    private void initPopup() {
        this.view = LayoutInflater.from(cxt).inflate(R.layout.popup_refresh_layout, (ViewGroup) null);
        this.share = (LinearLayout) this.view.findViewById(R.id.ll_share);
        this.refalsh = (LinearLayout) this.view.findViewById(R.id.ll_refalsh);
        setContentView(this.view);
        setWidth(-2);
        setHeight(-2);
        setBackgroundDrawable(new PaintDrawable());
        setFocusable(true);
        setOutsideTouchable(false);
        setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
            }
        });
        this.share.setOnClickListener(this);
        this.refalsh.setOnClickListener(this);
    }

    public void tab1OnClick(ImageView author) {
        if (isShowing()) {
            dismiss();
        } else {
            showAsDropDown(author, -75, 0);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_refalsh:
                this.reloadWebView.reload();
                dismiss();
                return;
            case R.id.ll_share:
                if (this.showShareDialog != null) {
                    this.showShareDialog.showDialog();
                }
                dismiss();
                return;
            default:
                return;
        }
    }
}

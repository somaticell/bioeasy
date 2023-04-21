package cn.com.bioeasy.app.widgets.dialog;

import android.content.Context;
import cn.com.bioeasy.app.commonlib.R;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class DialogManager {
    private static SweetAlertDialog mDialog;

    public interface ConfirmDialogListener {
        void clickOk(int i);
    }

    public static void showWarningDialog(Context context, String title, String content, final ConfirmDialogListener confirmDialogListener, final int actionType) {
        if (mDialog != null) {
            mDialog = null;
        }
        mDialog = new SweetAlertDialog(context, 3).setTitleText(title).setContentText(content).setConfirmText("确定").setCancelText("取消").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                if (confirmDialogListener != null) {
                    confirmDialogListener.clickOk(actionType);
                }
            }
        }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        mDialog.show();
    }

    public static void showErrorDialog(Context context, String title, String content, SweetAlertDialog.OnSweetClickListener listener) {
        if (mDialog != null) {
            mDialog = null;
        }
        mDialog = new SweetAlertDialog(context, 1).setConfirmText("确定").setTitleText(title).setContentText(content).setConfirmClickListener(listener);
        mDialog.show();
    }

    public static void showProgressDialog(Context context, String title, String message) {
        if (mDialog != null) {
            mDialog = null;
        }
        mDialog = new SweetAlertDialog(context, 5);
        mDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimary));
        mDialog.getProgressHelper().setRimColor(context.getResources().getColor(R.color.red_btn_bg_color));
        mDialog.setTitleText(title);
        mDialog.setContentText(message);
        mDialog.setCancelable(true);
        mDialog.show();
    }

    public static void showProgressDialog(Context context, String title, String message, int progress) {
        if (mDialog != null) {
            mDialog = null;
        }
        mDialog = new SweetAlertDialog(context, 5);
        mDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimary));
        mDialog.setTitleText(title);
        mDialog.setContentText(message);
        mDialog.setCancelable(true);
        mDialog.getProgressHelper().setProgress((float) progress);
        mDialog.show();
    }

    public static void hideProgressDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}

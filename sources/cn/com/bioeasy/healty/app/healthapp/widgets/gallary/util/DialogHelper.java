package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import cn.com.bioeasy.healty.app.healthapp.R;

public final class DialogHelper {
    public static AlertDialog.Builder getDialog(Context context) {
        return new AlertDialog.Builder(context, R.style.App_Theme_Dialog_Alert);
    }

    public static AlertDialog.Builder getMessageDialog(Context context, String title, String message, boolean cancelable) {
        return getDialog(context).setCancelable(cancelable).setTitle((CharSequence) title).setMessage((CharSequence) message).setPositiveButton((CharSequence) "确定", (DialogInterface.OnClickListener) null);
    }

    public static AlertDialog.Builder getMessageDialog(Context context, String title, String message) {
        return getMessageDialog(context, title, message, false);
    }

    public static AlertDialog.Builder getMessageDialog(Context context, String message) {
        return getMessageDialog(context, "", message, false);
    }

    public static AlertDialog.Builder getMessageDialog(Context context, String title, String message, String positiveText) {
        return getDialog(context).setCancelable(false).setTitle((CharSequence) title).setMessage((CharSequence) message).setPositiveButton((CharSequence) positiveText, (DialogInterface.OnClickListener) null);
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String title, View view, DialogInterface.OnClickListener positiveListener) {
        return getDialog(context).setTitle((CharSequence) title).setView(view).setPositiveButton((CharSequence) "确定", positiveListener).setNegativeButton((CharSequence) "取消", (DialogInterface.OnClickListener) null);
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String title, String message, String positiveText, String negativeText, boolean cancelable, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        return getDialog(context).setCancelable(cancelable).setTitle((CharSequence) title).setMessage((CharSequence) message).setPositiveButton((CharSequence) positiveText, positiveListener).setNegativeButton((CharSequence) negativeText, negativeListener);
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        return getDialog(context).setMessage((CharSequence) message).setPositiveButton((CharSequence) "确定", positiveListener).setNegativeButton((CharSequence) "取消", negativeListener);
    }

    public static AlertDialog.Builder getSingleChoiceDialog(Context context, String title, String[] arrays, int selectIndex, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setSingleChoiceItems((CharSequence[]) arrays, selectIndex, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle((CharSequence) title);
        }
        builder.setNegativeButton((CharSequence) "取消", (DialogInterface.OnClickListener) null);
        return builder;
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String title, String message, String positiveText, String negativeText, boolean cancelable, DialogInterface.OnClickListener positiveListener) {
        return getConfirmDialog(context, title, message, positiveText, negativeText, cancelable, positiveListener, (DialogInterface.OnClickListener) null);
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String title, String message, String positiveText, String negativeText, DialogInterface.OnClickListener positiveListener) {
        return getConfirmDialog(context, title, message, positiveText, negativeText, false, positiveListener, (DialogInterface.OnClickListener) null);
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String title, String message, String positiveText, String negativeText, boolean cancelable) {
        return getConfirmDialog(context, title, message, positiveText, negativeText, cancelable, (DialogInterface.OnClickListener) null, (DialogInterface.OnClickListener) null);
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String message, String positiveText, String negativeText, boolean cancelable) {
        return getConfirmDialog(context, "", message, positiveText, negativeText, cancelable, (DialogInterface.OnClickListener) null, (DialogInterface.OnClickListener) null);
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String title, String message, boolean cancelable) {
        return getConfirmDialog(context, title, message, "确定", "取消", cancelable, (DialogInterface.OnClickListener) null, (DialogInterface.OnClickListener) null);
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String message, boolean cancelable, DialogInterface.OnClickListener positiveListener) {
        return getConfirmDialog(context, "", message, "确定", "取消", cancelable, positiveListener, (DialogInterface.OnClickListener) null);
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener positiveListener) {
        return getConfirmDialog(context, "", message, "确定", "取消", positiveListener);
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String title, String message) {
        return getConfirmDialog(context, title, message, "确定", "取消", false, (DialogInterface.OnClickListener) null, (DialogInterface.OnClickListener) null);
    }

    public static AlertDialog.Builder getInputDialog(Context context, String title, AppCompatEditText editText, String positiveText, String negativeText, boolean cancelable, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        return getDialog(context).setCancelable(cancelable).setTitle((CharSequence) title).setView((View) editText).setPositiveButton((CharSequence) positiveText, positiveListener).setNegativeButton((CharSequence) negativeText, negativeListener);
    }

    public static AlertDialog.Builder getInputDialog(Context context, String title, AppCompatEditText editText, String positiveText, String negativeText, boolean cancelable, DialogInterface.OnClickListener positiveListener) {
        return getInputDialog(context, title, editText, positiveText, negativeText, cancelable, positiveListener, (DialogInterface.OnClickListener) null);
    }

    public static AlertDialog.Builder getInputDialog(Context context, String title, AppCompatEditText editText, boolean cancelable, DialogInterface.OnClickListener positiveListener) {
        return getInputDialog(context, title, editText, "确定", "取消", cancelable, positiveListener, (DialogInterface.OnClickListener) null);
    }

    public static AlertDialog.Builder getInputDialog(Context context, String title, AppCompatEditText editText, String positiveText, boolean cancelable, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        return getInputDialog(context, title, editText, positiveText, "取消", cancelable, positiveListener, negativeListener);
    }

    public static AlertDialog.Builder getInputDialog(Context context, String title, AppCompatEditText editText, boolean cancelable, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        return getInputDialog(context, title, editText, "确定", "取消", cancelable, positiveListener, negativeListener);
    }

    public static ProgressDialog getProgressDialog(Context context) {
        return new ProgressDialog(context);
    }

    public static ProgressDialog getProgressDialog(Context context, boolean cancelable) {
        ProgressDialog dialog = getProgressDialog(context);
        dialog.setCancelable(cancelable);
        return dialog;
    }

    public static ProgressDialog getProgressDialog(Context context, String message) {
        ProgressDialog dialog = getProgressDialog(context);
        dialog.setMessage(message);
        return dialog;
    }

    public static ProgressDialog getProgressDialog(Context context, String title, String message, boolean cancelable) {
        ProgressDialog dialog = getProgressDialog(context);
        dialog.setCancelable(cancelable);
        dialog.setTitle(title);
        dialog.setMessage(message);
        return dialog;
    }

    public static ProgressDialog getProgressDialog(Context context, String message, boolean cancelable) {
        ProgressDialog dialog = getProgressDialog(context);
        dialog.setCancelable(cancelable);
        dialog.setMessage(message);
        return dialog;
    }

    public static AlertDialog.Builder getSelectDialog(Context context, String title, String[] items, String positiveText, DialogInterface.OnClickListener itemListener) {
        return getDialog(context).setTitle((CharSequence) title).setItems((CharSequence[]) items, itemListener).setPositiveButton((CharSequence) positiveText, (DialogInterface.OnClickListener) null);
    }

    public static AlertDialog.Builder getSelectDialog(Context context, String[] items, String positiveText, DialogInterface.OnClickListener itemListener) {
        return getDialog(context).setItems((CharSequence[]) items, itemListener).setPositiveButton((CharSequence) positiveText, (DialogInterface.OnClickListener) null);
    }

    public static AlertDialog.Builder getSelectDialog(Context context, View view, String positiveText, DialogInterface.OnClickListener itemListener) {
        return getDialog(context).setView(view).setPositiveButton((CharSequence) positiveText, (DialogInterface.OnClickListener) null);
    }
}

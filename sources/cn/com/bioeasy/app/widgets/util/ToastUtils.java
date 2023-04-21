package cn.com.bioeasy.app.widgets.util;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.bioeasy.app.commonlib.R;
import javax.inject.Inject;

public class ToastUtils {
    public static final int BOTTOM = 1;
    public static final int CENTER = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;
    public static final int TOAST_TYPE_FAILED = 2;
    public static final int TOAST_TYPE_SUCCESS = 1;
    public static final int TOAST_TYPE_WARNING = 3;
    public static final int TOP = 0;
    /* access modifiers changed from: private */
    public static Context mContent;
    /* access modifiers changed from: private */
    public static Toast toast;
    public static Typeface typeface;

    @Inject
    public ToastUtils(Context mContent2) {
        mContent = mContent2;
    }

    public static void showToast(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                if (ToastUtils.toast == null) {
                    Toast unused = ToastUtils.toast = new Toast(ToastUtils.mContent);
                }
                ToastUtils.toast.setDuration(0);
                View toastlayout = LayoutInflater.from(ToastUtils.mContent).inflate(R.layout.toast_bioeasy, (ViewGroup) null);
                ((TextView) toastlayout.findViewById(R.id.common_toast_text)).setText(message);
                ToastUtils.toast.setView(toastlayout);
                ToastUtils.toast.setGravity(17, 0, 0);
                ToastUtils.toast.show();
            }
        });
    }

    public static void showToast(final int resId) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                if (ToastUtils.toast == null) {
                    Toast unused = ToastUtils.toast = new Toast(ToastUtils.mContent);
                }
                ToastUtils.toast.setDuration(0);
                View toastlayout = LayoutInflater.from(ToastUtils.mContent).inflate(R.layout.toast_bioeasy, (ViewGroup) null);
                ((TextView) toastlayout.findViewById(R.id.common_toast_text)).setText(ToastUtils.mContent.getString(resId));
                ToastUtils.toast.setView(toastlayout);
                ToastUtils.toast.setGravity(17, 0, 0);
                ToastUtils.toast.show();
            }
        });
    }

    public static void showToast(String message, int toastType) {
        if (toast == null) {
            toast = new Toast(mContent);
        }
        toast.setDuration(0);
        View toastlayout = LayoutInflater.from(mContent).inflate(R.layout.toast_bioeasy, (ViewGroup) null);
        ((TextView) toastlayout.findViewById(R.id.common_toast_text)).setText(message);
        toast.setView(toastlayout);
        toast.setGravity(17, 0, 0);
        toast.show();
    }

    public static void showToast(final int duration, final String message, final int position) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                if (ToastUtils.toast == null) {
                    Toast unused = ToastUtils.toast = new Toast(ToastUtils.mContent);
                }
                ToastUtils.toast.setDuration(duration);
                View toastlayout = LayoutInflater.from(ToastUtils.mContent).inflate(R.layout.toast_bioeasy, (ViewGroup) null);
                ((TextView) toastlayout.findViewById(R.id.common_toast_text)).setText(message);
                ToastUtils.toast.setView(toastlayout);
                switch (position) {
                    case 0:
                        ToastUtils.toast.setGravity(48, 0, 0);
                        break;
                    case 1:
                        ToastUtils.toast.setGravity(80, 0, 0);
                        break;
                    case 2:
                        ToastUtils.toast.setGravity(17, 0, 0);
                        break;
                    case 3:
                        ToastUtils.toast.setGravity(3, 0, 0);
                        break;
                    case 4:
                        ToastUtils.toast.setGravity(5, 0, 0);
                        break;
                }
                ToastUtils.toast.show();
            }
        });
    }

    public void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}

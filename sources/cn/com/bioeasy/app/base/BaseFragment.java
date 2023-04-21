package cn.com.bioeasy.app.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import cn.com.bioeasy.app.widgets.dialog.DialogManager;
import cn.com.bioeasy.app.widgets.util.ToastUtils;
import cn.pedant.SweetAlert.SweetAlertDialog;
import javax.inject.Inject;

public abstract class BaseFragment extends Fragment implements BaseView {
    protected BaseActivity baseActivity;
    @Inject
    protected Context context;
    protected boolean isCanShowLoading;
    protected BasePresenter presenter;
    @Inject
    ToastUtils toastUtils;

    /* access modifiers changed from: protected */
    public abstract int getContentViewID();

    public abstract BasePresenter getPresenter();

    /* access modifiers changed from: protected */
    public abstract void initViewsAndEvents(View view);

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getContentViewID() == 0) {
            return null;
        }
        View contentView = inflater.inflate(getContentViewID(), container, false);
        ButterKnife.bind((Object) this, contentView);
        this.presenter = getPresenter();
        if (this.presenter != null) {
            this.presenter.onCreate(savedInstanceState, this.context);
            this.presenter.initialize(this);
        }
        initViewsAndEvents(contentView);
        return contentView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.presenter != null) {
            this.presenter.onSave(outState);
        }
    }

    public void onResume() {
        super.onResume();
        if (this.presenter != null) {
            this.presenter.onResume(this);
        }
    }

    public void onPause() {
        super.onPause();
        if (this.presenter != null) {
            this.presenter.onPause();
        }
        this.isCanShowLoading = false;
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.presenter != null) {
            this.presenter.onDestroy();
            this.presenter = null;
        }
    }

    public void showMessage(String msg) {
        ToastUtils toastUtils2 = this.toastUtils;
        ToastUtils.showToast(msg);
    }

    public void showMessage(int resId) {
        ToastUtils toastUtils2 = this.toastUtils;
        ToastUtils.showToast(getString(resId));
    }

    public void showMessage(int duration, String message, int position) {
        ToastUtils toastUtils2 = this.toastUtils;
        ToastUtils.showToast(duration, message, position);
    }

    public void close() {
    }

    public void showProgress(String message) {
        DialogManager.showProgressDialog(this.context, message, "");
    }

    public void showProgress(int resId) {
        DialogManager.showProgressDialog(this.context, getString(resId), "");
    }

    public void showProgress(String message, int progress) {
        DialogManager.showProgressDialog(this.context, message, "", progress);
    }

    public void showProgress(String title, String msg) {
        DialogManager.showProgressDialog(this.context, title, msg);
    }

    public void hideProgress() {
        DialogManager.hideProgressDialog();
    }

    public void showErrorMessage(String msg, String content) {
        DialogManager.showErrorDialog(this.context, msg, content, new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
    }

    public void showErrorMessage(int resId, int contentResId) {
        DialogManager.showErrorDialog(this.context, getString(resId), getString(contentResId), new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
    }

    public void showWarnMessage(String msg, String content, DialogManager.ConfirmDialogListener confirmDialogListener, int type) {
        DialogManager.showWarningDialog(getActivity(), msg, content, confirmDialogListener, type);
    }
}

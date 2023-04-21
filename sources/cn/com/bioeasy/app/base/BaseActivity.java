package cn.com.bioeasy.app.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import cn.com.bioeasy.app.event.EventPosterHelper;
import cn.com.bioeasy.app.widgets.dialog.DialogManager;
import cn.com.bioeasy.app.widgets.util.ToastUtils;
import cn.pedant.SweetAlert.SweetAlertDialog;
import javax.inject.Inject;
import rx.Subscription;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    @Inject
    AppManager appManager;
    @Inject
    protected Context context;
    @Inject
    EventPosterHelper eventPosterHelper;
    protected Context mContext = null;
    protected Subscription mSubscription;
    protected BasePresenter presenter;
    @Inject
    ToastUtils toastUtils;

    /* access modifiers changed from: protected */
    public abstract int getContentViewID();

    /* access modifiers changed from: protected */
    public abstract BasePresenter getPresenter();

    /* access modifiers changed from: protected */
    public abstract void initViewsAndEvents();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        this.appManager.addActivity(this);
        if (getContentViewID() != 0) {
            setContentView(getContentViewID());
        }
        ButterKnife.bind((Activity) this);
        this.eventPosterHelper.getBus().register(this);
        this.presenter = getPresenter();
        if (this.presenter != null) {
            this.presenter.onCreate(savedInstanceState, this.context);
            this.presenter.initialize(this);
        }
        initViewsAndEvents();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.presenter != null) {
            this.presenter.onResume(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        if (this.presenter != null) {
            this.presenter.onPause();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.presenter != null) {
            this.presenter.onSave(outState);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (this.mSubscription != null && !this.mSubscription.isUnsubscribed()) {
            this.mSubscription.unsubscribe();
        }
        if (this.presenter != null) {
            this.presenter.onDestroy();
            this.presenter = null;
        }
    }

    public void finish() {
        super.finish();
        this.appManager.removeActivity(this);
    }

    public void showMessage(String msg) {
        ToastUtils toastUtils2 = this.toastUtils;
        ToastUtils.showToast(msg);
    }

    public void showMessage(int duration, String message, int position) {
        ToastUtils toastUtils2 = this.toastUtils;
        ToastUtils.showToast(duration, message, position);
    }

    public void close() {
        finish();
    }

    public void showProgress(String message) {
        DialogManager.showProgressDialog(this.mContext, message, "");
    }

    public void showProgress(String message, int progress) {
        DialogManager.showProgressDialog(this.mContext, message, "", progress);
    }

    public void hideProgress() {
        DialogManager.hideProgressDialog();
    }

    public void showErrorMessage(String msg, String content) {
        DialogManager.showErrorDialog(this.mContext, msg, content, new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
    }

    public void showWarnMessage(String msg, String content, DialogManager.ConfirmDialogListener confirmDialogListener, int type) {
        DialogManager.showWarningDialog(this.mContext, msg, content, confirmDialogListener, type);
    }

    public void showMessage(int resId) {
        ToastUtils toastUtils2 = this.toastUtils;
        ToastUtils.showToast(getString(resId));
    }

    public void showProgress(int resId) {
        DialogManager.showProgressDialog(this.mContext, getString(resId), "");
    }

    public void showProgress(String title, String msg) {
        DialogManager.showProgressDialog(this.mContext, title, msg);
    }

    public void showErrorMessage(int resId, int contentResId) {
        DialogManager.showErrorDialog(this.mContext, getString(resId), getString(contentResId), new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
    }
}

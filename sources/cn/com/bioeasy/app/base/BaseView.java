package cn.com.bioeasy.app.base;

import cn.com.bioeasy.app.widgets.dialog.DialogManager;

public interface BaseView {
    void close();

    void hideProgress();

    void showErrorMessage(int i, int i2);

    void showErrorMessage(String str, String str2);

    void showMessage(int i);

    void showMessage(int i, String str, int i2);

    void showMessage(String str);

    void showProgress(int i);

    void showProgress(String str);

    void showProgress(String str, int i);

    void showProgress(String str, String str2);

    void showWarnMessage(String str, String str2, DialogManager.ConfirmDialogListener confirmDialogListener, int i);
}

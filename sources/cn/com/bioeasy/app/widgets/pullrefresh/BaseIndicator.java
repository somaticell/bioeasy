package cn.com.bioeasy.app.widgets.pullrefresh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseIndicator {
    public abstract View createView(LayoutInflater layoutInflater, ViewGroup viewGroup);

    public abstract void onAction();

    public abstract void onLoading();

    public abstract void onRestore();

    public abstract void onUnaction();
}

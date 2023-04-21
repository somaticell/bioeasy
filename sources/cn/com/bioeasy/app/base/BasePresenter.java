package cn.com.bioeasy.app.base;

import android.content.Context;
import android.os.Bundle;
import cn.com.bioeasy.app.base.BaseRepository;
import cn.com.bioeasy.app.base.BaseView;
import icepick.Icepick;
import icepick.State;

public abstract class BasePresenter<V extends BaseView, R extends BaseRepository> {
    @State
    protected String baseMessage;
    protected Context context;
    protected R mRepository;
    protected V mUiView;

    public interface ICallback<T> {
        void onResult(T t);
    }

    public BasePresenter(R mRepository2) {
        this.mRepository = mRepository2;
    }

    public void onCreate(Bundle savedInstanceState, Context context2) {
        Icepick.restoreInstanceState(this, savedInstanceState);
        this.context = context2;
    }

    public void initialize(V mUiView2) {
        this.mUiView = mUiView2;
    }

    public void onSave(Bundle state) {
        Icepick.saveInstanceState(this, state);
    }

    public void onResume(V uiView) {
        this.mUiView = uiView;
    }

    public void onPause() {
        this.mUiView = null;
    }

    public void onDestroy() {
        this.mUiView = null;
    }

    public void cancleLoading() {
        this.mRepository.cancleRequest();
    }

    private void loadComplete() {
        this.mUiView.hideProgress();
    }

    private void loadErrorOccured(Throwable e) {
        this.mUiView.hideProgress();
        e.printStackTrace();
    }
}

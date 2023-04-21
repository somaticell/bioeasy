package cn.com.bioeasy.healty.app.healthapp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.com.bioeasy.app.base.BaseFragment;
import cn.com.bioeasy.app.base.BaseView;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.injection.component.DaggerFragmentComponent;
import cn.com.bioeasy.healty.app.healthapp.injection.component.FragmentComponent;
import cn.com.bioeasy.healty.app.healthapp.injection.module.FragmentModule;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.LoginActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public abstract class BIBaseFragment extends BaseFragment implements BaseView {
    protected FragmentComponent fragmentComponent;
    private RequestManager mImgLoader;

    /* access modifiers changed from: protected */
    public abstract void inject(FragmentComponent fragmentComponent2);

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inject(getFragmentComponent());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public FragmentComponent getFragmentComponent() {
        if (this.fragmentComponent == null) {
            this.fragmentComponent = DaggerFragmentComponent.builder().fragmentModule(new FragmentModule(this)).applicationComponent(HealthApp.x.getAppComponent()).build();
        }
        return this.fragmentComponent;
    }

    public synchronized RequestManager getImgLoader() {
        if (this.mImgLoader == null) {
            this.mImgLoader = Glide.with((Fragment) this);
        }
        return this.mImgLoader;
    }

    /* access modifiers changed from: protected */
    public void toLoginPage() {
        switchPage(LoginActivity.class);
    }

    /* access modifiers changed from: protected */
    public void switchPage(Class cls) {
        getActivity().startActivity(new Intent(getActivity(), cls));
    }
}

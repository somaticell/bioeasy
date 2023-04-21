package cn.com.bioeasy.healty.app.healthapp.modules.slidemenu;

import android.view.View;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.injection.component.FragmentComponent;

public class SlideMenuFragment extends BIBaseFragment {
    /* access modifiers changed from: protected */
    public void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    public BasePresenter getPresenter() {
        return null;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.fragment_slide_menu;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents(View rootView) {
    }
}

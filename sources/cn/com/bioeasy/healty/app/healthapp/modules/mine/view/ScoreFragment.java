package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.os.Bundle;
import android.view.View;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.injection.component.FragmentComponent;

public class ScoreFragment extends BIBaseFragment {
    private int type;

    public static ScoreFragment getInstance(int type2) {
        ScoreFragment sf = new ScoreFragment();
        sf.type = type2;
        return sf;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public BasePresenter getPresenter() {
        return null;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        if (this.type == 0) {
            return R.layout.fragment_score_daily;
        }
        return R.layout.fragment_score_novice;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents(View rootView) {
    }

    /* access modifiers changed from: protected */
    public void inject(FragmentComponent fragmentComponent) {
    }
}

package cn.com.bioeasy.app.base;

import android.os.Bundle;
import android.view.View;
import com.anthony.ultimateswipetool.SwipeHelper;
import com.anthony.ultimateswipetool.activity.SwipeBackActivityBase;
import com.anthony.ultimateswipetool.activity.SwipeBackLayout;

public abstract class SwipeBackActivity extends BaseActivity implements SwipeBackActivityBase {
    private SwipeHelper mHelper;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mHelper = new SwipeHelper(this);
        this.mHelper.onActivityCreate();
    }

    /* access modifiers changed from: protected */
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.mHelper.onPostCreate();
    }

    public View findViewById(int id) {
        View v = super.findViewById(id);
        return (v != null || this.mHelper == null) ? v : this.mHelper.findViewById(id);
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return this.mHelper.getSwipeBackLayout();
    }

    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    public void scrollToFinishActivity() {
        SwipeHelper.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    public void setScrollDirection(int edgeFlags) {
        getSwipeBackLayout().setEdgeTrackingEnabled(edgeFlags);
    }
}

package com.anthony.ultimateswipetool.dialogFragment;

import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.anthony.ultimateswipetool.SwipeHelper;
import com.anthony.ultimateswipetool.dialogFragment.SwipeLayout;

public class SwipeDialogFragment extends DialogFragment {
    private boolean mSwipeLayoutGenerated = false;
    /* access modifiers changed from: private */
    public boolean mSwipeable = true;
    private boolean mTiltEnabled = true;
    private SwipeLayout swipeLayout;

    public void setSwipeable(boolean swipeable) {
        this.mSwipeable = swipeable;
    }

    public boolean isSwipeable() {
        return this.mSwipeable;
    }

    public void setTiltEnabled(boolean tiltEnabled) {
        this.mTiltEnabled = tiltEnabled;
        if (this.swipeLayout != null) {
            this.swipeLayout.setTiltEnabled(tiltEnabled);
        }
    }

    public boolean isTiltEnabled() {
        return this.mTiltEnabled;
    }

    public boolean onSwipedAway(boolean toRight) {
        return false;
    }

    public void onStart() {
        super.onStart();
        if (!this.mSwipeLayoutGenerated && getShowsDialog()) {
            Window window = getDialog().getWindow();
            this.swipeLayout = new SwipeLayout(getActivity());
            SwipeHelper.replaceContentView(window, this.swipeLayout);
            this.swipeLayout.addSwipeListener((ViewGroup) window.getDecorView(), "layout", new SwipeLayout.DismissCallbacks() {
                public boolean canDismiss(Object token) {
                    return SwipeDialogFragment.this.isCancelable() && SwipeDialogFragment.this.mSwipeable;
                }

                public void onDismiss(View view, boolean toRight, Object token) {
                    if (!SwipeDialogFragment.this.onSwipedAway(toRight)) {
                        SwipeDialogFragment.this.dismiss();
                    }
                }
            });
            this.swipeLayout.setTiltEnabled(this.mTiltEnabled);
            this.swipeLayout.setClickable(true);
            this.mSwipeLayoutGenerated = true;
        }
    }
}
